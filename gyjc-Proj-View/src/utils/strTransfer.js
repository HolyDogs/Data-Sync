export default {
  lyidTransfer(str) {
    switch (str) {
      case "20200629003":
        return "全社会用电分类（全口径）";
      case "20200628001":
        return "重点企业财务状况主要经济指标";
      case "20200629002":
        return "分行业工业生产者出厂价格指数";
      case "20200629004":
        return "中国制造业采购经理指数";
      case "20200629005":
        return "重点工业园区主要经济指标";
      case "20200629006":
        return "重点企业产销总值及主要产品产量";
      case "20200701004":
        return "江西省工业分县（市、区）主要经济指标";
      case "20200701005":
        return "全国各地区工业增加值（只抽江西的）";
      case "20200701006":
        return "全国各地区工业产品销售率（只抽江西的）";
      case "20200701007":
        return "全国分地区工业增加值和产品销售率完成情况";
      case "20200702001":
        return "全国分地区主要工业产品生产完成情况（一）";
      case "20200702002":
        return "全国分地区主要工业产品生产完成情况（二）";
      case "20200629001":
        return "分行业工业企业主要经济指标";
      case "20200630001":
        return "重点企业主要产品销售、库存、订货及价格";
      case "20200630002":
        return "重点企业主要经济指标月报表";
      case "20200630003":
        return "园区工业企业主要经济指标";
      case "20200630004":
        return "各设区市主要经济指标";
      case "20200630005":
        return "工业企业主要经济指标";
      case "20200701001":
        return "工业园区主要经济指标";
      case "20200701002":
        return "工业增加值完成情况";
      case "20200701003":
        return "工业主要产品产量";
      default:
        return "未知分类";
    }
  },

  dataSourceTransfer (str) {
    switch(str) {
      case "YW_SYSTEM_DATA_MOVE":
        return "工业运行监测";
      case "DIC_STATISTIC_ZB":
        return "国家数据网";
      case "STATISTIC_ZB":
        return "国家数据网";
      case "EXCEL_HAND_IMPORT":
        return "手动导入";
      default:
        return "未知来源";

    }
  },

  frParamTransfer (str) {
    switch(str) {
      case "tyshxydm":
        return "统一社会信用代码";
      case "zch":
        return "注册号";
      case "xxbsm":
        return "学校标识码";
      case "society_code":
        return "统一社会信用代码";
      case "sys_num":
        return "案件编号";
      case "regno":
        return "注册号";
      case "uniscid":
        return "统一社会信用代码";
    }
  },

  explain(str) {
    switch(str) {
      case 'id':
        return '主键';
      case 'qymc':
        return '企业名称';
      case 'zch':
        return '注册号';
      case 'tyshxydm':
        return '统一社会信用代码';
    }
  },

  qyztExplain(str) {
    switch(Number(str)) {
      case 1:
        return '开业';
      case 2:
        return '吊销';
      case 3:
        return '注销';
      case 4:
        return '注吊销';
      case 5:
        return '迁入';
      case 6:
        return '迁出';
      case 7:
        return '名称';
      case 9:
        return '不明';
    }
  },

  sfExplain(str) {
    switch(Number(str)) {
      case 0:
        return '否';
      case 1:
        return '是';
    }
  },

  frDateFormat(str) {
    if (str != null && str != '' && str.length > 8) {
      return str.substr(0, 4) + '年' + str.substr(4, 2) + '月' + str.substr(6, 2) + '日';
    }
  }, 

  dateFormat(str) {
    if (str != null && str != '' && str.length > 9) {
      return str.substr(0,10);
    }
  },

  timeIdFormat(str) {
    if (str != null && str != '' && str.length > 7) {
      return str.substr(2, 4) + '年' + str.substr(6, 2) + '月';
    }
  }
}
