package com.thinvent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thinvent.entity.*;
import com.thinvent.mapper.DicMapper;
import com.thinvent.mapper.IppMapper;
import com.thinvent.service.*;
import com.thinvent.utils.GyjcConstant;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xufeng
 * @version 1.0
 * @date 2020/9/9 08:51
 **/
@Service
public class ZbDataServiceImpl implements ZbDataService {

    @Autowired
    private StatisticZbService statisticZbService;

    @Autowired
    private ExcelUnderminedService excelUnderminedService;

    @Autowired
    private DicStatisticZbService dicStatisticZbService;

    @Autowired
    private DicDateZbService dicDateZbService;

    @Autowired
    @SuppressWarnings("all")
    private IppMapper ippMapper;

    @Autowired
    @SuppressWarnings("all")
    private DicMapper dicMapper;

    @Autowired
    private SDictionaryItemsService dictionaryItemsService;

    @Override
    public int excelImp(String fileName, List<String> title, List data, String first, ExcelImportRecord excelImportRecord,String zbKey) {
        //导入EXCEL表时,判断是否为首次导入
        int result=1;
        if("first".equals(first)){
            //如果是首次导入直接插入数据
            result=importNewExcel(fileName, title, data,zbKey,first);

            //记录插入的新指标数
            excelImportRecord.setInsertNum(1 + excelImportRecord.getInsertNum());
        } else {
            //处理文件名  文件名示例  zbfl-zdlx
            String[] staArr = fileName.split("-");
            //指标分类
            String zbfl = staArr[0];
            //指标名称
            String zbmc = (String) data.get(0);

            QueryWrapper<StatisticZb> statisticZbQueryWrapper = new QueryWrapper<>();
            statisticZbQueryWrapper.eq("ZBFL", zbfl);
            statisticZbQueryWrapper.eq("ZBMC", zbmc);
            List<StatisticZb> statisticZbs = statisticZbService.list(statisticZbQueryWrapper);
            if (null != statisticZbs && statisticZbs.size() > 0) {
                //循环指标列名 年-月
                for (int i = 1; i < title.size(); i++) {
                    //是否更新标志
                    boolean flag = false;

                    String dateStr = title.get(i).toString();
                    String[] dateArr = dateStr.split("-");
                    String year = "";
                    String month = "";
                    if (dateArr.length > 1) {
                        //年份
                        year = title.get(i).toString().split("-")[0];
                        //月份
                        month = title.get(i).toString().split("-")[1];
                    } else {
                        if (dateStr.endsWith("年")) {
                            //年度数据
                            year = dateStr.replace("年", "");
                            month = "";
                        }
                    }

                    String sz = null;
                    if (i >= data.size()){
                        sz = null;
                    } else {
                        sz = (String) data.get(i);
                    }
                    //格式化数值 add by xufeng on 2020/11/16 保留两位小数
                    if (!org.springframework.util.StringUtils.isEmpty(sz)) {
                        Double num = Double.parseDouble(sz);
                        sz = String.format("%.2f", num);
                    }
                    //循环该ZBFL
                    for (int j = 0; j < statisticZbs.size(); j++){
                        StatisticZb statisticZb = statisticZbs.get(j);
                        if ((year.equals(statisticZb.getNf())
                                && month.equals(statisticZb.getYf()))
                                || (year.equals(statisticZb.getNf())
                                && StringUtils.isEmpty(month)
                                && StringUtils.isEmpty(statisticZb.getYf()))) {
                            //数值
                            statisticZb.setSz(sz);
                            //statisticZbService.updateById()
                            //更新
                            statisticZbService.updateById(statisticZb);
                            //设置这个年月份已更新
                            flag = true;
                            break;
                        }
                    }
                    //如果当前年月份未更新，则插入
                    if (!flag) {
                        StatisticZb statisticZb = statisticZbs.get(0);
                        StatisticZb myzb = new StatisticZb();
                        myzb.setZbtjId(UUID.randomUUID().toString().replaceAll("-", ""));
                        myzb.setSz(sz);
                        myzb.setCreateTime(new Date());
                        myzb.setNf(year);
                        myzb.setYf(month);
                        myzb.setSjly(statisticZb.getSjly());
                        myzb.setZbfl(statisticZb.getZbfl());
                        myzb.setZbid(statisticZb.getZbid());
                        myzb.setZbmc(statisticZb.getZbmc());
                        myzb.setDqmc("江西省");
                        myzb.setDqbm("360000");
                        myzb.setZbkey(zbKey);
                        String sjId;
                        if (dateStr.endsWith("年")) {
                            sjId = "Y-" + year;
                        } else {
                            sjId = "M-" + year + month;
                        }
                        myzb.setSjid(sjId);
                        statisticZbService.save(myzb);

                        //判断该时间ID是否在时间字典表中存在，若不存在则新增
                        saveIfNotExistDateDic(sjId, year, month);
                    }
                }

                //记录更新的指标数
                excelImportRecord.setUpdateNum(excelImportRecord.getUpdateNum() + 1);
            } else {
                //如有该指标分类，但是没有该指标名称，存入待定表
                ExcelUndermined ex = null;
                String titleStr = StringUtils.join(title, ",");
                String dataStr = StringUtils.join(data, ",");
                QueryWrapper<ExcelUndermined> excelUnderminedQueryWrapper = new QueryWrapper<>();
                excelUnderminedQueryWrapper.eq("ZBMC", zbmc);
                excelUnderminedQueryWrapper.eq("EXCEL_NAME", fileName);
                List<ExcelUndermined> excelUnderminedList = excelUnderminedService.list(excelUnderminedQueryWrapper);
                if (null != excelUnderminedList && excelUnderminedList.size() > 0) {
                    ex = excelUnderminedList.get(0);
                    //第一行标题
                    ex.setTitle(titleStr);
                    //值
                    ex.setData(dataStr);
                    excelUnderminedService.updateById(ex);
                } else {
                    ex = new ExcelUndermined();
                    ex.setTitle(titleStr);
                    ex.setExcelName(fileName);
                    ex.setZbmc(zbmc);
                    ex.setData(dataStr);
                    ex.setCreateTime(new Date());
                    excelUnderminedService.save(ex);
                }

                //记录待定的指标数
                excelImportRecord.setUnderminedNum(excelImportRecord.getUnderminedNum() + 1);
            }
        }
        return result;

    }

    //1-成功
    //0-失败
    private int importNewExcel(String fileName, List<String> title, List data,String zbkey,String first) {
        String zbmc  = (String) data.get(0);
        String zbfl = fileName.split("-")[0];//指标分类
        String zdlx = fileName.split("-")[1];//指标类型
        String jldw = "";//计量单位
        String zbmcSelf = "";//去计量单位的指标名称
        if(zbmc.contains("(") && zbmc.contains(")")){
            jldw = zbmc.substring(zbmc.lastIndexOf("(")+1,zbmc.lastIndexOf(")"));
            zbmcSelf = zbmc.substring(0, zbmc.lastIndexOf("("));
        } else {
            zbmcSelf = zbmc;
        }
        //保存一个新的指标字典数据到字典表
        DicStatisticZb dic = null;
        String uuid = "";//主键
        dic = new DicStatisticZb();
        uuid = UUID.randomUUID().toString().replaceAll("-", "");
        dic.setZbtjzdId(uuid);//主键
        dic.setZbbm(uuid);//指标别名
        dic.setZdlx(zdlx);//指标类型
        dic.setJldw(jldw);//计量单位
        dic.setZbmc(zbmcSelf);//去计量单位的指标名称
        dic.setCreateTime(new Date());
        dicStatisticZbService.save(dic);

        //判断该指标在指标池是否存在，若不存在则插入，同时插入字典表
        int countPoolZb = ippMapper.checkPoolZb(zbmcSelf, GyjcConstant.DIC_STATISTIC_ZB, zbfl);
        //第一次导入excel但是却有该指标（第一次excel多个相同指标）
        if("first".equals(first)&&(countPoolZb > 0)){
            return 0;
        }
        if (!(countPoolZb > 0)) {
            PoolZb poolZb = new PoolZb();
            poolZb.setId(UUID.randomUUID().toString().replace("-", ""));
            poolZb.setSourceMark(GyjcConstant.DIC_STATISTIC_ZB);
            poolZb.setZbmc(zbmcSelf);
            poolZb.setJldw(jldw);
            poolZb.setZbfl(zbfl);
            poolZb.setZbkey(zbkey);
            ippMapper.insertPoolZb(poolZb);

            SDictionaryItems sDictionaryItems = sDictionaryItems = new SDictionaryItems();
            sDictionaryItems.setItemGuid(UUID.randomUUID().toString().replace("-", ""));
            sDictionaryItems.setGroupNo(GyjcConstant.ZBMC_GROUPNO.longValue());
            sDictionaryItems.setItemKey(zbkey);
            sDictionaryItems.setItemParent(zbkey.substring(0,4));
            sDictionaryItems.setItemValue(zbmcSelf);
            sDictionaryItems.setItemFullValue(zbmcSelf);
            sDictionaryItems.setEnable(1);
            sDictionaryItems.setVersion(Long.parseLong("1"));
            sDictionaryItems.setCreateTime(new Date());
            sDictionaryItems.setUpdateTime(new Date());
            sDictionaryItems.setDataType(1);
            dictionaryItemsService.save(sDictionaryItems);
        }

        StatisticZb zb = null;
        for (int i = 1; i < title.size(); i++) {
            String dateStr = title.get(i).toString();
            String[] dateArr = dateStr.split("-");
            String year = "";
            String month = "";
            if (dateArr.length > 1) {
                year = title.get(i).toString().split("-")[0];//年份
                month = title.get(i).toString().split("-")[1];//月份
            } else {
                if (dateStr.endsWith("年")) {
                    //年度数据
                    year = dateStr.replace("年", "");
                    month = "";
                }
            }

            zb = new StatisticZb();
            uuid = UUID.randomUUID().toString().replaceAll("-", "");//主键
            //修复表格末尾出现空值导致越界的bug
            String sz;
            if (i >= data.size()){
                sz = null;
            } else {
                sz = (String) data.get(i);
            }
            zb.setZbtjId(uuid);
            zb.setZbmc(zbmc);//指标名称
            zb.setNf(year);//年份
            zb.setYf(month);//月份
            zb.setSz(sz);//数值
            zb.setSjly("国家数据网");//数据来源
            zb.setZbfl(zbfl);//指标分类
            zb.setZbid(dic.getZbtjzdId());//指标别名
            zb.setCreateTime(new Date());
            zb.setDqmc("江西省");
            zb.setDqbm("360000");
            zb.setZbkey(zbkey);
            String sjId;
            if (dateStr.endsWith("年")) {
                sjId = "Y-" + year;
            } else {
                sjId = "M-" + year + month;
            }
            zb.setSjid(sjId);
            statisticZbService.save(zb);

            //判断该时间ID是否在时间字典表中存在，若不存在则新增
            saveIfNotExistDateDic(sjId, year, month);
        }
        return 1;
    }

    private void saveIfNotExistDateDic(String sjId, String year, String month) {
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


    @Override
    public Map<String, Object> importExcel(String fileName, String fileType, InputStream inputStream) {
        HashMap excelMap = new HashMap<String, Object>();
        String message ="";
        String state ="0";
        List title = null;//存放第一行列名
        List<String> datas = null;// 存放指标名称和值
        try {
            Workbook wb = null;
            // 判断Excel版本,创建xls对象，并初始化导入的Excel
            if ("xlsx".equalsIgnoreCase(fileType)) {//Excel2007
                wb = new XSSFWorkbook(inputStream);
            } else if ("xls".equalsIgnoreCase(fileType)) {//Excel2003
                wb = new HSSFWorkbook(inputStream);
            }
            int nodata = 1;// 休正列数。排除前一行字段名
            boolean flag = true;//正常标识
            boolean breakFlag=false;//中间出错跳出
            // 获取第一张表的信息
            Sheet sheet = wb.getSheetAt(0);

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
                            //修复空格子bug
                            if (e instanceof NullPointerException) {
                                break;
                            }
                            //兼容年度数据
                            String dateStr = xssrow.getCell(n).toString();
                            if (dateStr.endsWith("年")) {
                                title.add(dateStr);
                            } else {
                                throw e;
                            }
                        }

                    }
                }

                for (int i = nodata; i <= row; i++) {
                    flag = true;
                    if (sheet.getRow(i) == null || "".equals(sheet.getRow(i))) {
                        i = row;
                    }else{
                        //获取指定行数据
                        xssrow = sheet.getRow(i);
                        //将所有字段值放在List data里
                        String dataStr = "";
                        //循环遍历该行所有列
                        for (int n = 0; n < sheet.getRow(0).getLastCellNum(); n++) {
                            String vallue = "";
                            if (xssrow.getCell(n) != null)
                                vallue = xssrow.getCell(n).toString();
                            if (("".equals(vallue)&& n==0)){
                                //out.write("<font color='#FF0000'>指标名称不能为空！该条数据导入失败！</font>");
                                flag = false;
                                break;
                            }else{
                                if(n!=0&&!"".equals(vallue)) {
                                    try {
                                        Double.parseDouble(vallue);
                                    } catch (Exception e) {
                                        state = "2";
                                        message = "指标\"" + xssrow.getCell(0).toString() + "\"在"
                                                + title.get(n).toString().replace("-", "年")
                                                + "月的数据"
                                                + "\""+vallue + "\"填写的不符合格式要求，请按照正确格式填写！";
                                        e.printStackTrace();
                                        breakFlag=true;
                                        break;
                                    }
                                }
                                if((""+vallue).indexOf("E")!=-1 || (""+vallue).indexOf("e")!=-1 || (""+vallue).indexOf("+")!=-1){
                                    xssrow.getCell(n).setCellType(CellType.STRING);;
                                    vallue = xssrow.getCell(n).toString();
                                }
                                dataStr+=vallue+",";

                            }
                        }
                        if(breakFlag){
                            breakFlag=false;
                            break;
                        }
                        if(flag == false){
                            continue;
                        }
                        if(!"".equals(dataStr)){
                            dataStr = dataStr.substring(0,dataStr.length()-1);
                        }
                        datas.add(dataStr);
                    }
                }
            }else{
                state="1";
                message=fileName+"无数据";
                //System.out.println("EXCEL表("+fileName+")无数据");
            }


        } catch (Exception e) {
            state="2";
            message=fileName+"为非规范表格";
            e.printStackTrace();
        }
        if("0".equals(state)){
            excelMap.put("title", title);
            excelMap.put("data", datas);
        }
        excelMap.put("state", state);
        excelMap.put("message", message);

        return excelMap;
    }

    @Override
    public void insertNewZb(String[] zbIds) {
        for (String zbid:zbIds) {
            ExcelUndermined excelUndermined = excelUnderminedService.getById(zbid);
            if (null == excelUndermined) {
                continue;
            }
            //excel名
            String fileName = excelUndermined.getExcelName();
            //表头
            List<String> title = Arrays.asList(excelUndermined.getTitle().split(","));
            //指标数据
            List<String> data = Arrays.asList(excelUndermined.getData().split(","));
            String zbmc  = (String) data.get(0);
            String zbmcSelf = "";//去计量单位的指标名称
            if(zbmc.contains("(") && zbmc.contains(")")){
                zbmcSelf = zbmc.substring(0, zbmc.lastIndexOf("("));
            } else {
                zbmcSelf = zbmc;
            }
            String currentKey = getCurrentKey(fileName.split("-")[0],zbmcSelf);
            //作为新指标导入
            importNewExcel(fileName, title, data,currentKey,"second");
            //删除该待定指标
            excelUnderminedService.removeById(zbid);
        }
    }

    @Override
    public void insertOldZb(String currentZbId, String jzbId) {
        ExcelUndermined excelUndermined = excelUnderminedService.getById(currentZbId);
        if (null == excelUndermined) {
            return;
        }
        DicStatisticZb dicStatisticZb = dicStatisticZbService.getById(jzbId);
        if (null == dicStatisticZb) {
            return;
        }
        //表名
        String fileName = excelUndermined.getExcelName();
        //表头
        List<String> title = Arrays.asList(excelUndermined.getTitle().split(","));
        //指标数据
        List<String> data = Arrays.asList(excelUndermined.getData().split(","));
        //作为旧指标导入
        importOldExcel(fileName, title, data, dicStatisticZb);
        //删除该待定指标
        excelUnderminedService.removeById(currentZbId);
    }

    private void importOldExcel(String fileName, List<String> title, List<String> data, DicStatisticZb dicStatisticZb) {
        String zbmc  = (String) data.get(0);
        String zbfl = fileName.split("-")[0];//指标分类
        String zdlx = fileName.split("-")[1];//指标类型
        String uuid = "";//主键
        String jldw = "";//计量单位
        String zbmcSelf = "";//去计量单位的指标名称
        if(zbmc.contains("(") && zbmc.contains(")")){
            jldw=zbmc.substring(zbmc.lastIndexOf("(")+1,zbmc.lastIndexOf(")"));
            zbmcSelf = zbmc.substring(0, zbmc.lastIndexOf("("));
        } else {
            zbmcSelf = zbmc;
        }
        String zbkey = this.getCurrentKey(fileName.split("-")[0],zbmcSelf);
        DicStatisticZb dic = new DicStatisticZb();
        uuid = UUID.randomUUID().toString().replaceAll("-", "");
        dic.setZbtjzdId(uuid);//主键
        dic.setZbbm(dicStatisticZb.getZbbm());//指标别名
        dic.setZdlx(zdlx);//指标类型
        dic.setJldw(jldw);//计量单位
        dic.setZbmc(zbmcSelf);//指标名称
        dic.setCreateTime(new Date());//创建时间
        dicStatisticZbService.save(dic);

        //查询该待定指标是否存在于指标池内
        if (!(ippMapper.checkPoolZb(zbmcSelf,GyjcConstant.DIC_STATISTIC_ZB,zbfl)>0)) {
            PoolZb poolZb = new PoolZb();
            poolZb.setId(UUID.randomUUID().toString().replace("-", ""));
            //标志来源为表格导入
            poolZb.setSourceMark("DIC_STATISTIC_ZB");
            poolZb.setZbmc(zbmcSelf);
            poolZb.setZbfl(zbfl);
            //modified by xufeng 增加计量单位
            poolZb.setJldw(jldw);
            poolZb.setZbkey(zbkey);
            ippMapper.insertPoolZb(poolZb);
            SDictionaryItems sDictionaryItems = sDictionaryItems = new SDictionaryItems();
            sDictionaryItems.setItemGuid(UUID.randomUUID().toString().replace("-", ""));
            sDictionaryItems.setGroupNo(GyjcConstant.ZBMC_GROUPNO.longValue());
            sDictionaryItems.setItemKey(zbkey);
            sDictionaryItems.setItemParent(zbkey.substring(0,4));
            sDictionaryItems.setItemValue(zbmcSelf);
            sDictionaryItems.setItemFullValue(zbmcSelf);
            sDictionaryItems.setEnable(1);
            sDictionaryItems.setVersion(Long.parseLong("1"));
            sDictionaryItems.setCreateTime(new Date());
            sDictionaryItems.setUpdateTime(new Date());
            sDictionaryItems.setDataType(1);
            dictionaryItemsService.save(sDictionaryItems);
        }

        StatisticZb zb = null;
        for (int i = 1; i < title.size(); i++) {

            String dateStr = title.get(i).toString();
            String[] dateArr = dateStr.split("-");
            String year = "";
            String month = "";
            if (dateArr.length > 1) {
                year = title.get(i).toString().split("-")[0];//年份
                month = title.get(i).toString().split("-")[1];//月份
            } else {
                if (dateStr.endsWith("年")) {
                    //年度数据
                    year = dateStr.replace("年", "");
                    month = "";
                }
            }


            QueryWrapper<StatisticZb> statisticZbQueryWrapper = new QueryWrapper<>();
            statisticZbQueryWrapper.eq("ZBMC", zbmc);
            statisticZbQueryWrapper.eq("NF", year);
            statisticZbQueryWrapper.eq("YF", month);
            List<StatisticZb> zbList = statisticZbService.list(statisticZbQueryWrapper);
            String sz;//数值
            if (i >= data.size()){
                sz = null;
            } else {
                sz = (String) data.get(i);
            }
            if(zbList!=null && zbList.size()>0){
                zb = zbList.get(0);
                zb.setSz(sz);//数值
                statisticZbService.updateById(zb);
            }else{
                zb = new StatisticZb();
                uuid = UUID.randomUUID().toString().replaceAll("-", "");//主键
                zb.setZbtjId(uuid);
                zb.setZbmc(zbmc);//指标名称
                zb.setNf(year);//年份
                zb.setYf(month);//月份
                zb.setSz(sz);//数值
                zb.setSjly("国家数据网");//数据来源
                zb.setZbfl(zbfl);//指标分类
                zb.setZbid(dic.getZbtjzdId());//指标别名
                zb.setCreateTime(new Date());
                //地区名称
                zb.setDqmc("江西省");
                //地区编码
                zb.setDqbm("360000");
                zb.setZbkey(zbkey);
                String sjId;
                if (dateStr.endsWith("年")) {
                    sjId = "Y-" + year;
                } else {
                    sjId = "M-" + year + month;
                }
                zb.setSjid(sjId);
                statisticZbService.save(zb);

                //判断该时间ID是否在时间字典表中存在，若不存在则新增
                saveIfNotExistDateDic(sjId, year, month);
            }
        }

    }

    @Override
    public String getCurrentKey(String zbfl,String zbmc){
        String current=null;
        String zbflKey=null;
        //获取判断是否需要添加指标分类
        List<String> maxList = dicMapper.getMaxOrSelf("A", GyjcConstant.ZBFL_GROUPNO, zbfl);
        zbflKey=maxList.get(0);
        //大于1则有指标分类
        if(maxList.size()>1){
            //获取判断该指标是否有zbkey
            List<String> zbmcSelf = dicMapper.getMaxOrSelf(zbflKey, GyjcConstant.ZBMC_GROUPNO, zbmc);
            if(zbmcSelf!=null&&zbmcSelf.size()>1&&!StringUtils.isEmpty(zbmcSelf.get(0))){
                //有则直接获取
                current=zbmcSelf.get(0);
            }else if(zbmcSelf!=null&&zbmcSelf.size()>0&&!StringUtils.isEmpty(zbmcSelf.get(0))) {
                //没有则获取该分类最大的，进而获取zbkey
                current=this.getNextZBkey(zbmcSelf.get(0));
            }else{
                //该分类没有指标则获取000，进而获取zbkey
                current=this.getNextZBkey(zbflKey+"000");
                }
        }else{
            //没有该指标分类，则获取最大的指标分类key
            int zbflKey2=Integer.valueOf(maxList.get(0).substring(1)).intValue();
            zbflKey2+=1;
            zbflKey=zbflKey.substring(0,1)+String.format("%03d",zbflKey2);
            SDictionaryItems sDictionaryItems = sDictionaryItems = new SDictionaryItems();
            //主键
            sDictionaryItems.setItemGuid(UUID.randomUUID().toString().replace("-", ""));
            //字典类型
            sDictionaryItems.setGroupNo(GyjcConstant.ZBFL_GROUPNO.longValue());
            sDictionaryItems.setItemKey(zbflKey);
            sDictionaryItems.setItemParent("A");
            sDictionaryItems.setItemValue(zbfl);
            sDictionaryItems.setItemFullValue(zbfl);
            sDictionaryItems.setEnable(1);
            sDictionaryItems.setVersion(Long.parseLong("1"));
            sDictionaryItems.setCreateTime(new Date());
            sDictionaryItems.setUpdateTime(new Date());
            sDictionaryItems.setDataType(1);
            dictionaryItemsService.save(sDictionaryItems);
            current=this.getNextZBkey(zbflKey+"000");
        }
        return current;
    }

    /**
     * 根据当前zbkey获取下一个zbKey
     * @param zbkey
     * @return
     */
    public String getNextZBkey(String zbkey){
        int zbmcKey=Integer.valueOf(zbkey.substring(4)).intValue();
        zbmcKey+=1;
        zbkey=zbkey.substring(0,4)+String.format("%03d",zbmcKey);
        return zbkey;
    }

}
