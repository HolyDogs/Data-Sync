package com.thinvent.gyjcEnum;

import lombok.Getter;

/**
 * 法人查询接口枚举
 * @author xufeng
 */

@Getter
public enum FrApiResultEnum {

    //1企业登记信息查询
    QYDJXXCX("DwdGsQydjxx1_2170292r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_COMPANY_INFO_REGIST", "tyshxydm", "企业登记信息查询"),
    //2个体登记信息查询
    GTDJXXCX("DwdGsGtdjxx1_2170522r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_GETI_INFO_REGIST", "zch", "个体登记信息查询"),
    //3教育机构信息查询
    JYJGXXCX("DwdGsGtdjxx1_2170522r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_EDU_ORG_INFO", "xxbsm", "教育机构信息查询"),
    //4变更情况查询
    BGQKCX("DwdGsBgqk_2170840r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_CHANGE_STATE", "tyshxydm", "变更情况查询"),
    //5监管企业名录表查询
    JGQYMLBCX("DwdSftJgqyml_2170926r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_REGULATION_COMPANY_LIST", "society_code", "监管企业名录表查询"),
    //6企业异常名录查询
    QYYCMLCX("DwdGsQyycml_2171024r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_COMPANY_EXCEPTION_LIST", "tyshxydm", "企业异常名录查询"),
    //7个体异常名录查询
    GTYCMLCX("DwdGsGtycml_2171158r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_GETI_EXCEPTION_LIST", "zch", "个体异常名录查询"),
    //8案件信息表查询
    ANJXXBCX("DwdSftAjxx1_2171436r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_LAW_INFO", "sys_num", "案件信息表查询"),
    //9企业严重违法失信查询
    QYYZWFSXCX("DwdGsQyyzwfsx_2171318r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_COMPANY_SERIOUS_WFSX", "tyshxydm", "企业严重违法失信查询"),
    //10 12315投诉举报信息查询
    OTTOFTSJBXXCX("DwdGs12315tsjb_2171272r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_12315_COMPLAINTS", "regno", "12315投诉举报信息查询"),
    //11主要人员查询
    ZYRYCX("DwdGsZyry_2183306r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_MAIN_PERSON", "tyshxydm", "主要人员查询"),
    //12财务负责人查询
    CWFZRCX("DwdGsCwfzr_2183548r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_FINANCE_FZR", "tyshxydm", "财务负责人查询"),
    //13个体经营者查询
    GTJYZCX("DwdGsGtjyzxx_2183614r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_GETI_MANAGER", "zch", "个体经营者查询"),
    //14股东情况查询
    GDQKCX("DwdGsQygdqk_2183768r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_GD_INFO", "tyshxydm", "股东情况查询"),
    //15动产抵押信息查询
    DCDYXXCX("DwdGsDcdyxx_2184170r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_DONGCHAN_DIYA_INFO", "tyshxydm", "动产抵押信息查询"),
    //16个体年报行政许可信息查询
    GTNBXZXKXXCX("DwdGsAnPbLicenceinfo_2184276r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_GETI_YEAR_REPORT_ALLOW", "zch", "个体年报行政许可信息查询"),
    //17许可信息查询
    XKXXCX("DwdGsQydjXkxx_2184350r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_ALLOW_INFO", "tyshxydm", "许可信息查询"),
    //18企业登记备案查询
    QYDJBANCX("DwdGsGtdjba_2184456r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_COMPANY_REGIST_COPY", "zch", "企业登记备案查询"),
    //19个体年报修改信息查询
    GTNBXGXXCX("DwdGsAnPbUpdateinfo_2185412r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_GETI_YEAR_REPORT_UPDINFO", "zch", "个体年报修改信息查询"),
    //20企业年报修改信息查询
    QYNBXGXXCX("DwdGsAnUpdateinfo_2185490r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_COMPANY_YEAR_REPORT_UPDINFO", "tyshxydm", "企业年报修改信息查询"),
    //21企业年报基本信息查询
    QYNBJBXXCX("DwdGsAnBaseinfo_2184912r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_COMPANY_YEAR_REPORT_BASEINFO", "uniscid", "企业年报基本信息查询"),
    //22个体年报基本信息查询
    GTNBJBXXCX("DwdGsAnPbBaseinfo_2185234r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_GETI_YEAR_REPORT_BASEINFO", "regno", "个体年报基本信息查询"),
    //23企业年报对外提供保证担保信息查询
    QYNBDWTGBZDBXXCX("DwdGsAnForguaranteeinfo_2185564r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_COMPANY_YEAR_REPORT_OUT_PROMIS", "tyshxydm", "企业年报对外提供保证担保信息查询"),
    //24企业名录扩展信息查询
    QYMLKZXXCX("DwdSftCompanyExpand_2185658r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_COMPANY_LIST_EXPAND_INFO", "tyshxydm", "企业名录扩展信息查询"),
    //25涉刑移送案件表查询
    SXYSANJBCX("DwdSftScCrimeCase_2184534r"
            , "e8eebfff9f3d400cb1125271cbf71d44"
            , "FR_PUNISH_MOVE_LAW", "tyshxydm", "涉刑移送案件表查询")
    ;



    private String apiAddress;
    private String authHeader;
    private String tableName;
    private String paramName;
    private String apiName;

    /**
     * @param apiAddress api地址
     * @param authHeader 请求认证头
     * @param tableName 表名
     * @param paramName 参数名
     */
    FrApiResultEnum(String apiAddress, String authHeader, String tableName, String paramName, String apiName) {
        this.apiAddress = apiAddress;
        this.authHeader = authHeader;
        this.tableName = tableName;
        this.paramName = paramName;
        this.apiName = apiName;
    }
}
