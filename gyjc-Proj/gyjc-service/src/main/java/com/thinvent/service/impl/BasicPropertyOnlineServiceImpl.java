package com.thinvent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.thinvent.entity.SDictionaryItems;
import com.thinvent.entity.secure.BasicPropertyOnline;
import com.thinvent.entity.secure.LeakInfo;
import com.thinvent.entity.secure.PropertyOnlineUploadRecord;
import com.thinvent.mapper.SDictionaryItemsMapper;
import com.thinvent.mapper.secure.BasicPropertyOnlineMapper;
import com.thinvent.mapper.secure.LeakInfoMapper;
import com.thinvent.mapper.secure.PropertyOnlineUploadRecordMapper;
import com.thinvent.service.secure.BasicPropertyOnlineService;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xff
 * @since 2021-01-25
 */
@Service
@AllArgsConstructor
public class BasicPropertyOnlineServiceImpl extends ServiceImpl<BasicPropertyOnlineMapper, BasicPropertyOnline> implements BasicPropertyOnlineService {

    private final BasicPropertyOnlineMapper basicPropertyOnlineMapper;

    private final SDictionaryItemsMapper sDictionaryItemsMapper;

    private final LeakInfoMapper leakInfoMapper;

    private final PropertyOnlineUploadRecordMapper propertyOnlineUploadRecordMapper;

    @Override
    public PageInfo<BasicPropertyOnline> getOnlineDataList(int pageNum, String dataType
            , String dqmc, String company, String manufacturer) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(dataType)) {
            queryWrapper.eq("TYPE", dataType);
        }
        if (!StringUtils.isEmpty(dqmc)) {
            queryWrapper.eq("CITY", dqmc);
        }
        if (!StringUtils.isEmpty(company)) {
            queryWrapper.like("BELONG_COMPANY", company);
        }
        if (!StringUtils.isEmpty(manufacturer)) {
            queryWrapper.like("MANUFACTURER", manufacturer);
        }
        queryWrapper.orderByDesc("FOUND_TIME");
        PageHelper.startPage(pageNum, 10);
        List<BasicPropertyOnline> basicPropertyOnlineList = basicPropertyOnlineMapper.selectList(queryWrapper);
        return new PageInfo<>(basicPropertyOnlineList);
    }

    @Override
    public String uploadBasicPorpertyOnline(MultipartFile file) {
        //全量数据，首先删除数据
        basicPropertyOnlineMapper.truncateTable();

        //存放漏洞编号，用于更新漏洞描述
        HashMap<String, String> map = new HashMap<>();
        //存放地区编号
        HashMap<String, String> areaMap = new HashMap<>();
        QueryWrapper<SDictionaryItems> areaQuery = new QueryWrapper<>();
        areaQuery.eq("GROUP_NO",1);
        areaQuery.eq("AREA_LEVEL",2);
        areaQuery.like("ITEM_KEY","36");
        areaQuery.select("ITEM_KEY","ITEM_VALUE");
        List<SDictionaryItems> sDictionaryItems = sDictionaryItemsMapper.selectList(areaQuery);
        for(SDictionaryItems sd : sDictionaryItems){
            areaMap.put(sd.getItemValue(),sd.getItemKey());
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String origiName = file.getOriginalFilename();
        if (StringUtils.isEmpty(origiName)) {
            this.saveBasicPropertyRecord(origiName, "导入失败" ,"excel文件名为空");
            return "excel文件名为空";
        }
        if (origiName.indexOf("在线监测数据") < 0 ){
            this.saveBasicPropertyRecord(origiName, "导入失败" ,"excel文件名不符合规范");
            return "excel文件名错误";
        }
        String fileName = origiName.substring(0, origiName.indexOf("."));
        //错误原因
        StringBuffer errorData = new StringBuffer();
        //行数
        int rowNum = 1;
        try {
            Reader reader = new InputStreamReader(file.getInputStream(), "GBK");
            CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
            for (CSVRecord record : parser) {
                rowNum++;
                BasicPropertyOnline basicPropertyOnline = new BasicPropertyOnline();
                basicPropertyOnline.setId(UUID.randomUUID().toString().replace("-", ""));
                basicPropertyOnline.setIp(record.get(0));
                basicPropertyOnline.setPort(StringUtils.isEmpty(record.get(1)) ? null : Integer.valueOf(record.get(1)));
                basicPropertyOnline.setType(record.get(2));
                basicPropertyOnline.setLng(StringUtils.isEmpty(record.get(3)) ? null : Double.valueOf(record.get(3)));
                basicPropertyOnline.setLat(StringUtils.isEmpty(record.get(4)) ? null : Double.valueOf(record.get(4)));
                basicPropertyOnline.setManufacturer(record.get(5));
                basicPropertyOnline.setModel(record.get(6));
                basicPropertyOnline.setOperators(record.get(7));
                basicPropertyOnline.setProvince(record.get(8));
                basicPropertyOnline.setCity(record.get(9));
                basicPropertyOnline.setInfo(record.get(10));
                basicPropertyOnline.setBelongCompany(record.get(11));
                basicPropertyOnline.setFoundTime(StringUtils.isEmpty(record.get(12))? null :formatter.parse(record.get(12)));
                basicPropertyOnline.setLeakId(record.get(13));
                basicPropertyOnline.setAreaId(areaMap.get(record.get(9)));
                if(!StringUtils.isEmpty(record.get(13))){
                    map.put(record.get(13),"");
                }
                this.save(basicPropertyOnline);
            }
            QueryWrapper<LeakInfo> leakInfoQuery = null;
            UpdateWrapper<BasicPropertyOnline> updateWrapper = null;
            for(String key : map.keySet()){
                StringBuffer leakNames = new StringBuffer();
                String[] leakIds = key.split("，");
                leakInfoQuery = new QueryWrapper<>();
                leakInfoQuery.in("LEAK_NAME",leakIds);
                leakInfoQuery.select("distinct SIMPLE_NAME");
                List<Object> simpleNames = leakInfoMapper.selectObjs(leakInfoQuery);
                for(int i = 0;i < simpleNames.size(); i++){
                    leakNames.append(simpleNames.get(i));
                    if(i != simpleNames.size()-1){
                        leakNames.append(",");
                    }
                }
                updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("LEAK_ID",key);
                updateWrapper.set("LEAK_DESC",leakNames.toString());
                basicPropertyOnlineMapper.update(null, updateWrapper);
            }
            this.saveBasicPropertyRecord(fileName, "导入成功", null);
        }catch (Exception e){
            e.printStackTrace();
            errorData.append("第"+rowNum+"行数据有问题");
            this.saveBasicPropertyRecord(fileName,"导入失败",errorData.toString());
        }
        return errorData.toString();
    }

    @Override
    public void saveBasicPropertyRecord(String excelName, String result, String reason) {
        PropertyOnlineUploadRecord propertyOnlineUploadRecord = new PropertyOnlineUploadRecord();
        propertyOnlineUploadRecord.setId(UUID.randomUUID().toString().replace("-", ""));
        propertyOnlineUploadRecord.setExcelName(excelName);
        propertyOnlineUploadRecord.setImportDate(new Date());
        propertyOnlineUploadRecord.setReason(reason);
        propertyOnlineUploadRecord.setResult(result);
        propertyOnlineUploadRecordMapper.insert(propertyOnlineUploadRecord);
    }
}
