<template>
  <div class="app-container">
    <myheader :name="pageName"></myheader>
    <div class="filter-container">
      <span>搜索类型：</span>
      <template>
        <el-select v-model="listQuery.apiName" filterable placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </template>

      <span style="margin-left: 15px;">{{ paramLabel }}：</span>
      <el-input v-model="listQuery.paramName" placeholder="参数" clearable style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter" style="margin-left: 15px;">
        查询
      </el-button>
    </div>

<!--     <ul style="width:30%;" v-loading="listLoading" v-for="map in tableList">
      <li v-for="(value,key) in map">
        {{ $strTransfer.explain(key)}} : {{value}}
      </li>
      <hr/>
    </ul> -->

    <table v-for="map in tableList" class="mytable" border="1">
      
      <tr class="mytr">
        <td class="tdhead">
          企业名称
        </td>
        <td class="tdcontent">{{map.qymc}}</td>
        <td class="tdhead">
          注册号
        </td>
        <td class="tdcontent">{{map.zch}}</td>
      </tr>
      <tr class="mytr">
        <td class="tdhead">
          统一社会信用代码
        </td>
        <td class="tdcontent">{{map.rowkey}}</td>
        <td class="tdhead">
          法定代表人
        </td>
        <td class="tdcontent">{{map.fddbr}}</td>
      </tr>
      <tr class="mytr">
        <td class="tdhead">
          企业类型(小类)
        </td>
        <td class="tdcontent">{{map.qylx}}</td>
        <td class="tdhead">
          记录企业状态
        </td>
        <td class="tdcontent">{{ $strTransfer.qyztExplain(map.djjg) }}</td>
      </tr>

      <tr class="mytr">
        <td class="tdhead">
          行业门类
        </td>
        <td class="tdcontent">{{map.hyml}}</td>
        <td class="tdhead">
          行业代码
        </td>
        <td class="tdcontent">{{map.hydm}}</td>
      </tr>
      <tr class="mytr">
        <td class="tdhead">
          成立日期
        </td>
        <td class="tdcontent">{{ $strTransfer.frDateFormat(map.clrq) }}</td>
        <td class="tdhead">
          邮政编码
        </td>
        <td class="tdcontent">{{map.yzbm}}</td>
      </tr>
      <tr class="mytr">
        <td class="tdhead">
          电子邮件
        </td>
        <td class="tdcontent">{{map.dzyj}}</td>
        <td class="tdhead">
          许可经营项目
        </td>
        <td class="tdcontent">{{map.xkjyxm}}</td>
      </tr>
      <tr class="mytr">
        <td class="tdhead">
          管辖机关
        </td>
        <td class="tdcontent">{{map.gxjg}}</td>
        <td class="tdhead">
          经营期限起
        </td>
        <td class="tdcontent">{{ $strTransfer.frDateFormat(map.jyqxstart) }}</td>
      </tr>
      <tr class="mytr">
        <td class="tdhead">
          经营期限止
        </td>
        <td class="tdcontent">{{ $strTransfer.frDateFormat(map.jyqxend) }}</td>
        <td class="tdhead">
          租赁期限起
        </td>
        <td class="tdcontent">{{ $strTransfer.frDateFormat(map.zlqxstart) }}</td>
      </tr>

      <tr class="mytr">
        <td class="tdhead">
          租赁期限止
        </td>
        <td class="tdcontent">{{ $strTransfer.frDateFormat(map.zlqxend) }}</td>
        <td class="tdhead">
          信用等级
        </td>
        <td class="tdcontent">{{map.xydj}}</td>
      </tr>
      <tr class="mytr">
        <td class="tdhead">
          住所所在地行政区划
        </td>
        <td class="tdcontent">{{map.zsszdxzqh}}</td>
        <td class="tdhead">
          住所
        </td>
        <td class="tdcontent">{{map.zs}}</td>
      </tr>
      <tr class="mytr">
        <td class="tdhead">
          住所产权
        </td>
        <td class="tdcontent">{{map.zscq}}</td>
        <td class="tdhead">
          住所所在经济开发区
        </td>
        <td class="tdcontent">{{map.zsszjjkfq}}</td>
      </tr>
      <tr class="mytr">
        <td class="tdhead">
          是否重热点
        </td>
        <td class="tdcontent">{{ $strTransfer.sfExplain(map.sfzrd) }}</td>
        <td class="tdhead">
          重点行业热点行业
        </td>
        <td class="tdcontent">{{map.zrdhy}}</td>
      </tr>
      <tr class="mytr">
        <td class="tdhead">
          重热点行业内容
        </td>
        <td class="tdcontent">{{map.zdhynr}}</td>
        <td class="tdhead">
          是否重点区域
        </td>
        <td class="tdcontent">{{ $strTransfer.sfExplain(map.sfzdqy) }}</td>
      </tr>
      <tr class="mytr">
        <td class="tdhead">
          重点区域内容
        </td>
        <td class="tdcontent">{{map.zdqynr}}</td>
        <td class="tdhead">
          经营期限是否长期有效
        </td>
        <td class="tdcontent">{{ $strTransfer.sfExplain(map.sfcqyx) }}</td>
      </tr>
      <tr class="mytr">
        <td class="tdhead">
          核准日期
        </td>
        <td class="tdcontent">{{ $strTransfer.frDateFormat(map.hzrq) }}</td>
        <td class="tdhead">
          管片组
        </td>
        <td class="tdcontent">{{map.gpz}}</td>
      </tr>

      <tr class="mytr">
        <td class="tdhead">
          插入时间
        </td>
        <td class="tdcontent">{{ $strTransfer.frDateFormat(map.i_date) }}</td>
        <td class="tdhead">
          更新时间
        </td>
        <td class="tdcontent">{{ $strTransfer.frDateFormat(map.u_date) }}</td>
      </tr>

      <tr class="mytr">
        <td class="tdhead">
          是否外商投资企业投资
        </td>
        <td class="tdcontent">{{ $strTransfer.sfExplain(map.sfwstzqytz) }}</td>
        <td class="tdhead">
          属地监管工商所
        </td>
        <td class="tdcontent">{{map.sdjggss}}</td>
      </tr>
      <tr class="mytr">
        <td class="tdhead">
          主营业务
        </td>
        <td class="tdcontent">{{map.ybjyxm}}</td>
      </tr>

    </table>
    <table>
      
    </table>

  </div>
</template>
<script>
import myheader from '@/components/header'
export default {
  name: 'FrListView',
  components:{myheader},
  data() {
    return {
      pageName:'接口管理',
      options: null,
      listLoading: false,
      tableList: null,
      listQuery:{
        apiName: '',
        paramName: ''
      },
      paramLabel: '参数值'
    }
  },
  created() {
    this.getSelectList();
  },
  watch: {
    //监听接口选择的变化，然后自动变更需要的参数名
    "listQuery.apiName":{
      handler(newValue) {
        for (var i = this.options.length - 1; i >= 0; i--) {
          if(this.options[i].value == newValue) {
            this.paramLabel = this.$strTransfer.frParamTransfer(this.options[i].param);
          }
        }
      },
      deep: true
    }
  },
  methods: {
    getSelectList() {
      let me = this;
      this.$axios.get('getSelectList', {}, function(r){
        me.options=r.data;
      })
    },
    async getList(apiName, paramName) {
      let me = this;
      this.listLoading = true;
      this.$axios.get('api/' + apiName + '/' + paramName,{}, function(r){
        me.tableList = r.data;
        me.listLoading = false;
      })
    },
    handleFilter() {
      if (this.listQuery.apiName === '' || this.listQuery.paramName === '') {
        this.$message({
          type: "warning",
          message: "请选择接口类型并输入参数后再试！"
        });
        return;
      }
      this.tableList = [];
      if (this.listQuery.apiName != 'QYDJXXCX') {
        return;
      }
      this.getList(this.listQuery.apiName, this.listQuery.paramName);
    }
  }
}
</script>

<style type="text/css" scoped>

.my-autocomplete {
  li {
    line-height: normal;
    padding: 7px;

    .name {
      text-overflow: ellipsis;
      overflow: hidden;
    }
    .addr {
      font-size: 12px;
      color: #b4b4b4;
    }

    .highlighted .addr {
      color: #ddd;
    }
  }
}

.tdhead{
 width: 20%;
 background-color: #C2C094;
}

.mytable {
  margin-left: 300px;
  margin-right: 300px;
  border: 1px;
}
.mytr {
  border:1px;
}
</style>
