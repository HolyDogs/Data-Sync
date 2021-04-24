package com.thinvent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinvent.entity.*;
import com.thinvent.exception.ExcelHandleException;
import com.thinvent.mapper.DicMapper;
import com.thinvent.mapper.ExcelHandImportMapper;
import com.thinvent.mapper.IppMapper;
import com.thinvent.service.DicDateZbService;
import com.thinvent.service.ExcelHandImportService;
import com.thinvent.service.SDictionaryItemsService;
import com.thinvent.utils.GyjcConstant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xff
 * @since 2020-09-24
 */
@Service
public class ExcelHandImportServiceImpl extends ServiceImpl<ExcelHandImportMapper, ExcelHandImport> implements ExcelHandImportService {

    @Autowired
    @SuppressWarnings("all")
    private ExcelHandImportMapper excelHandImportMapper;

    @Autowired
    @SuppressWarnings("all")
    private IppMapper ippMapper;

    @Autowired
    private DicDateZbService dicDateZbService;

    @Autowired
    @SuppressWarnings("all")
    private DicMapper dicMapper;

    @Autowired
    private SDictionaryItemsService dictionaryItemsService;

    @Override
    public List<HashMap> importExcel(String fileType, InputStream inputStream) throws IOException, ExcelHandleException {
        List<HashMap> mapList = new ArrayList<>();


        Workbook wb = null;
        // 判断Excel版本,创建xls对象，并初始化导入的Excel
        if ("xlsx".equalsIgnoreCase(fileType)) {//Excel2007
            wb = new XSSFWorkbook(inputStream);
        } else if ("xls".equalsIgnoreCase(fileType)) {//Excel2003
            wb = new HSSFWorkbook(inputStream);
        }
        // 休正列数。排除前一行字段名
        int nodata = 1;
        //正常标识
        boolean flag = true;
        //循环
        Iterator iterator = wb.iterator();
        while (iterator.hasNext()) {
            //sheet名为 指标名（计量单位）
            Sheet sheet = (Sheet) iterator.next();
            //用于返回
            HashMap dataMap = new HashMap();
            //存放第一行列名
            List title = null;
            // 存放指标名称和值
            List<String> datas = null;

            String sheetName = sheet.getSheetName();
            String jldw = "";
            String zbmcSelf = "";
            //字符处理
            if(sheetName.contains("(") && sheetName.contains(")")){
                jldw = sheetName.substring(sheetName.lastIndexOf("(")+1,sheetName.lastIndexOf(")"));
                zbmcSelf = sheetName.substring(0, sheetName.lastIndexOf("("));
            } else {
                zbmcSelf = sheetName;
            }
            if (!StringUtils.isEmpty(jldw) && jldw.getBytes("gbk").length > 20) {
                throw new ExcelHandleException("计量单位\"" + jldw + "\"超过长度限制,请确认！");
            }
            //计量单位
            dataMap.put("JLDW", jldw);
            //指标名称 （不带计量单位）
            dataMap.put("ZBMC", zbmcSelf);

            // 获取共要导入的信息条数：（row-1）
            int row = sheet.getLastRowNum();
            Row xssrow = null;
            if (row > 0) {
                title = new ArrayList();
                datas = new ArrayList();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
                xssrow = sheet.getRow(0);//获取第一行数据
                //System.out.println("列数:"+sheet.getRow(0).getLastCellNum());
                for (int n = 0; n < sheet.getRow(0).getLastCellNum(); n++) {
                    xssrow.getCell(0).setCellType(CellType.STRING);
                    Date date = null;
                    if(n==0){
                        String value = xssrow.getCell(n).toString();
                        title.add(value);
                    }else{
                        try {
                            date = (Date) xssrow.getCell(n).getDateCellValue();
                            title.add(formatter.format(date));
                        }catch (Exception e) {

                            String dateStr = xssrow.getCell(n).toString();
                            //兼容年度数据
                            /*
                            if (dateStr.endsWith("年")) {
                                title.add(dateStr);
                            }
                            */
                            throw new ExcelHandleException("sheet页\"" + sheetName + "\"第" +
                                    (n + 1) + "列的日期\"" + dateStr + "\"填写有误，请确认!");

                        }

                    }
                }

                for (int i = nodata; i <= row; i++) {
                    flag = true;
                    if (sheet.getRow(i) == null || "".equals(sheet.getRow(i))) {
                        i = row;
                    } else {
                        //获取指定行数据
                        xssrow = sheet.getRow(i);
                        //将所有字段值放在List data里
                        StringBuilder dataStr = new StringBuilder();
                        //循环遍历该行所有列
                        for (int n = 0; n < sheet.getRow(0).getLastCellNum(); n++) {
                            String vallue = "";
                            if (xssrow.getCell(n) != null)
                                vallue = xssrow.getCell(n).toString();
                            if (("".equals(vallue)&& n==0)){
                                flag = false;
                                break;
                            }else{
                                if(!"".equals(vallue)&&n==0){
                                    vallue=vallue.replace(" ","");
                                    if(StringUtils.isEmpty(vallue)){
                                        throw new ExcelHandleException("sheet页\"" + sheetName + "\"第"+(i+1)+"行数据地区为空，请确认!");
                                    }
                                    if (StringUtils.isEmpty(dicMapper.getItemKeyByValue(vallue, "1"))) {
                                        //若地区名称不能对应地区编码
                                        throw new ExcelHandleException("sheet页\"" + sheetName + "\"第"+(i+1)+"行数据地区名称填写有误，请确认!");
                                    }
                                }
                                if (!"".equals(vallue) && n!=0) {
                                    try {
                                        Double.parseDouble(vallue);
                                    } catch (Exception e) {
                                        throw new ExcelHandleException("sheet页\"" + sheetName + "\"第" +
                                                (n + 1) + "列的数据\"" + vallue + "\"填写有误，请确认!");
                                    }

                                }
                                if(("" + vallue).contains("E") || ("" + vallue).contains("e") || ("" + vallue).contains("+")){
                                    xssrow.getCell(n).setCellType(CellType.STRING);
                                    vallue = xssrow.getCell(n).toString();
                                }
                                dataStr.append(vallue).append(",");

                            }
                        }
                        if(!flag){
                            continue;
                        }
                        if(!"".equals(dataStr.toString())){
                            dataStr = new StringBuilder(dataStr.substring(0, dataStr.length() - 1));
                        }
                        datas.add(dataStr.toString());
                    }
                }
            } else {
                //跳过空sheet页
                continue;
            }
            dataMap.put("title", title);
            dataMap.put("data", datas);
            mapList.add(dataMap);
        }


        return mapList;

    }

    @Override
    public void handleZbData(String zbfl, HashMap hashMap, HandImportRecord handImportRecord) {
        String zbmc = MapUtils.getString(hashMap, "ZBMC");
        String jldw = MapUtils.getString(hashMap, "JLDW");
        List titles = (List) MapUtils.getObject(hashMap, "title");
        List datas = (List) MapUtils.getObject(hashMap, "data");

        //指标分类key
        String zbflKey;
        //指标名称key（即指标编码）
        String zbmcKey;
        zbflKey = funcDicExecutor("1111", "N", zbfl);
        zbmcKey = funcDicExecutor("1112", zbflKey, zbmc);

        //判断该指标在指标池是否存在，若不存在则插入
        if (ippMapper.checkPoolZb(zbmc,GyjcConstant.EXCEL_HAND_IMPORT,zbfl)> 0) {
            //若存在，当成更新指标
            handImportRecord.setUpdateNum(1 + handImportRecord.getUpdateNum());
        } else {
            PoolZb poolZb = new PoolZb();
            poolZb.setId(UUID.randomUUID().toString().replace("-", ""));
            poolZb.setSourceMark(GyjcConstant.EXCEL_HAND_IMPORT);
            poolZb.setZbmc(zbmc);
            poolZb.setZbfl(zbfl);
            poolZb.setZbkey(zbmcKey);
            //modified by xufeng 增加计量单位
            poolZb.setJldw(jldw);
            ippMapper.insertPoolZb(poolZb);
            handImportRecord.setInsertNum(1 + handImportRecord.getInsertNum());
        }

        for (Object data:datas){
            List<String> content = Arrays.asList(data.toString().split(","));
            //地区名称
            String dqmc = content.get(0);
            if (!StringUtils.isEmpty(dqmc)) {
                //去掉空格
                dqmc = dqmc.replace(" ", "");
            }
            if(StringUtils.isEmpty(dqmc)){
                continue;
            }
            //地区编码
            String dqbm = dicMapper.getItemKeyByValue(dqmc, "1");

            for (int i=1;i<titles.size();i++) {
                String dateStr = titles.get(i).toString();
                String[] dateArr = dateStr.split("-");
                String year = "";
                String month = "";
                if (dateArr.length > 1) {
                    year = titles.get(i).toString().split("-")[0];//年份
                    month = titles.get(i).toString().split("-")[1];//月份
                }
                String sjid = "M-" + year + month;
                //修复表格末尾出现空值导致越界的bug
                String sz;
                if (i >= content.size()){
                    sz = null;
                } else {
                    sz = content.get(i);
                }
                if (!StringUtils.isEmpty(sz)) {
                    Double num = Double.parseDouble(sz);
                    sz = String.format("%.2f", num);
                }

                //用于查询指标某月的数据是否已存在于库中
                QueryWrapper<ExcelHandImport> handImportQueryWrapper = new QueryWrapper<>();
                //指标key相等
                handImportQueryWrapper.eq("ZBKEY", zbmcKey);
                handImportQueryWrapper.eq("SJID", sjid);
                handImportQueryWrapper.eq("DQBM", dqbm);
                ExcelHandImport excelHandImport = excelHandImportMapper
                        .selectOne(handImportQueryWrapper);
                if (excelHandImport != null && !StringUtils.isEmpty(excelHandImport.getId())) {
                    //设置新数值
                    excelHandImport.setSz(sz);
                    //已存在的数据进行更新然后跳出
                    excelHandImportMapper.updateById(excelHandImport);
                    continue;
                }
                excelHandImport = new ExcelHandImport();
                //主键
                excelHandImport.setId(UUID.randomUUID().toString().replace("-", ""));
                //地区名称
                excelHandImport.setDqmc(dqmc);
                //地区编码
                excelHandImport.setDqbm(dqbm);
                //计量单位
                excelHandImport.setJldw(jldw);
                //数据来源
                excelHandImport.setSjly("手动导入");
                //指标编码
                excelHandImport.setZbkey(zbmcKey);

                //数值
                excelHandImport.setSz(sz);
                //指标分类
                excelHandImport.setZbfl(zbfl);
                //指标名称
                excelHandImport.setZbmc(zbmc);
                //时间id
                excelHandImport.setSjid(sjid);
                //导入时间
                excelHandImport.setCreateDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                excelHandImportMapper.insert(excelHandImport);

                //判断该时间ID是否在时间字典表中存在，若不存在则新增
                //saveIfNotExistDateDic(sjid, year, month);

            }
        }

    }

    private String funcDicExecutor(String groupNo, String itemParent, String itemValue){
        String value;
        QueryWrapper<SDictionaryItems> queryWrapper = new QueryWrapper<>();
        //字典类型
        queryWrapper.eq("GROUP_NO", Long.parseLong(groupNo));
        //父类型
        queryWrapper.eq("ITEM_PARENT", itemParent);
        //itemvalue
        queryWrapper.eq("ITEM_VALUE", itemValue);
        //查询数据
        SDictionaryItems sDictionaryItems = dictionaryItemsService.getOne(queryWrapper);
        if (sDictionaryItems == null) {
            //如果无该指标分类，则新建插入
            sDictionaryItems = new SDictionaryItems();
            //主键
            sDictionaryItems.setItemGuid(UUID.randomUUID().toString().replace("-", ""));
            //字典类型
            sDictionaryItems.setGroupNo(Long.parseLong(groupNo));
            sDictionaryItems.setItemParent(itemParent);
            sDictionaryItems.setItemValue(itemValue);
            sDictionaryItems.setItemFullValue(itemValue);
            sDictionaryItems.setEnable(1);
            sDictionaryItems.setVersion(Long.parseLong("1"));
            sDictionaryItems.setCreateTime(new Date());
            sDictionaryItems.setUpdateTime(new Date());
            sDictionaryItems.setDataType(1);
            queryWrapper = new QueryWrapper<>();
            //字典类型
            queryWrapper.eq("GROUP_NO", Long.parseLong(groupNo));
            //父字典key
            queryWrapper.eq("ITEM_PARENT", itemParent);
            //倒序排序
            queryWrapper.orderByDesc("ITEM_KEY");
            //查询ITEM_KEY
            queryWrapper.select("ITEM_KEY");
            List<Object> alist = dictionaryItemsService.listObjs(queryWrapper);
            if (alist == null || alist.size() < 1){
                //没有的话生成第一个001
                value = itemParent + "001";
            }else {
                //当前最大排序
                String max = (String) alist.get(0);
                //结尾数字
                String endTh = max.substring(itemParent.length());
                //实际值+1
                int real = Integer.parseInt(endTh) + 1;
                //补0组合成ITEM_KEY
                value = itemParent + String.format("%03d", real);
            }
            sDictionaryItems.setItemKey(value);
            dictionaryItemsService.save(sDictionaryItems);
        } else {
            value = sDictionaryItems.getItemKey();
        }
        return value;
    }

    @Override
    public List<Object> getZbflSelectList() {
        QueryWrapper<ExcelHandImport> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT ZBFL");
        return excelHandImportMapper.selectObjs(queryWrapper);
    }

    @Override
    public PageInfo<ExcelHandImport> getDataList(int pageNum, String zbmc, String zbfl, String dqmc,String startTime,String endTime) {
        QueryWrapper<ExcelHandImport> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(zbmc)) {
            //指标名称 like
            queryWrapper.like("ZBMC", zbmc);
        }
        if (!StringUtils.isEmpty(zbfl)) {
            //指标分类 equal
            queryWrapper.eq("ZBFL", zbfl);
        }
        if (!StringUtils.isEmpty(dqmc)) {
            //地区名称 like
            queryWrapper.like("DQMC", dqmc);
        }
        //起始时间
        if(!StringUtils.isEmpty(startTime)){
            String startString="M-"+startTime.substring(0,4)+""+startTime.substring(5,7);
            queryWrapper.ge("SJID",startString);
        }
        //截止时间
        if(!StringUtils.isEmpty(endTime)){
            String endString="M-"+endTime.substring(0,4)+""+endTime.substring(5,7);
            queryWrapper.le("SJID",endString);
        }
        queryWrapper.orderByDesc("CREATE_DATE").orderByDesc("SJID");
        //查主键，防止全表扫描查询过慢
        queryWrapper.select("ID");
        PageHelper.startPage(pageNum, 10);
        //主键分页
        List<Object> excelHandImportIdList = excelHandImportMapper.selectObjs(queryWrapper);
        queryWrapper = new QueryWrapper<>();
        //获取分页信息
        PageInfo pageInfo = new PageInfo(excelHandImportIdList);
        if (null != excelHandImportIdList && excelHandImportIdList.size() > 0) {
            //设置ID IN (list)
            queryWrapper.in("ID", excelHandImportIdList);
            //根据分页的ID再去查询整条数据
            List<ExcelHandImport> excelHandImportList = excelHandImportMapper.selectList(queryWrapper);

            //设置分页数据
            pageInfo.setList(excelHandImportList);
        }

        return pageInfo;
    }

    @Override
    public void saveIfNotExistDateDic(String sjId, String year, String month) {
        QueryWrapper<DicDateZb> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ID", sjId);
        DicDateZb dicDateZb = dicDateZbService.getOne(queryWrapper);
        if (dicDateZb == null || org.apache.commons.lang.StringUtils.isEmpty(dicDateZb.getId())) {
            dicDateZb = new DicDateZb();
            dicDateZb.setId(sjId);
            if (sjId.startsWith("M")) {
                //月度数据
                dicDateZb.setQssj(year + "-" + month + "-" + "01");
                if (Arrays.asList("01","03","05","07","08","10","12").contains(month)) {
                    dicDateZb.setJzsj(year + "-" + month + "-" + "31");
                } else if ("02".equals(month)){
                    if ((Integer.parseInt(year)%4) == 0) {
                        dicDateZb.setJzsj(year + "-" + month + "-" + "29");
                    } else {
                        dicDateZb.setJzsj(year + "-" + month + "-" + "28");
                    }
                } else {
                    dicDateZb.setJzsj(year + "-" + month + "-" + "30");
                }
                dicDateZb.setSjmc(year + "年" + month + "月");
                dicDateZb.setSjz("月度");
                dicDateZb.setSjbm(year + "年" + month + "月");
            } else if (sjId.startsWith("Y")) {
                //年度数据
                dicDateZb.setQssj(year + "-01-01");
                dicDateZb.setJzsj(year + "-12-31");
                dicDateZb.setSjmc(year + "年");
                dicDateZb.setSjz("年度");
                dicDateZb.setSjbm(year + "年");
            }
            dicDateZbService.save(dicDateZb);
        }

    }
}
