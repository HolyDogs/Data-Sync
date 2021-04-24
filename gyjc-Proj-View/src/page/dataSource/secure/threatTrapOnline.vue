<template>
  <div class="app-container">
    <myheader :name="pageName"></myheader>
    <div class="filter-container">
      <el-row>
      	<span>日志类型:</span>
        <el-select v-model="logType" filterable clearable style="width: 200px;" placeholder="请选择日志类型">
          <el-option
            v-for="item in typeList"
            :value="item.value"
            :key="item.key"
            :label="item.key">
          </el-option>
        </el-select>
        <span>日志等级:</span>
        <el-select v-model="logLevel" filterable clearable style="width: 200px;" placeholder="请选择日志等级">
          <el-option
            v-for="item in levelList"
            :value="item.value"
            :key="item.key"
            :label="item.key">
          </el-option>
        </el-select>
        <span style="margin-left: 15px;">协议名称:</span>
        <el-input v-model="protocol" placeholder="请输入协议名称" style="width: 200px;" clearable  @keyup.enter.native="searchData" />
        <span style="margin-left: 15px;">攻击者国家:</span>
        <el-input v-model="country" placeholder="请输入攻击者国家" style="width: 200px;" clearable  @keyup.enter.native="searchData" />

        <el-button type="primary" class="filter-item" icon="el-icon-search" @click="searchData" style="margin-left: 15px;">
          查询
        </el-button>
      </el-row>
    </div>
    <div style="padding:15px 15px 0 15px;height:calc(100vh - 288px);background:#fff;position:relative">
      <el-table v-loading="loading" :data="list" :row-style="{height: '60px'}" height="100%"  stripe fit highlight-current-row >

        <el-table-column label=日志类型 min-width="110px" align="center">
          <template slot-scope="{row}">
            <span v-if="row.logType==0">攻击日志</span>
            <span v-if="row.logType==1">告警日志</span>
            <span v-if="row.logType==2">告警事件</span>
            <span v-if="row.logType==3">状态上传</span>
            <span v-if="row.logType==4">任务拉取</span>
            <span v-if="row.logType==5">任务执行</span>
          </template>
        </el-table-column>

        <el-table-column label=日志等级 min-width="110px" align="center">
          <template slot-scope="{row}">
            <span v-if="row.levelId==0">未知</span>
            <span v-if="row.levelId==1">低级</span>
            <span v-if="row.levelId==2">中级</span>
            <span v-if="row.levelId==3">高级</span>
          </template>
        </el-table-column>

        <el-table-column label=是否通过 min-width="110px" align="center">
          <template slot-scope="{row}">
            <span v-if="row.passed==1">是</span>
            <span v-if="row.passed==0">否</span>
          </template>
        </el-table-column>

        <el-table-column label=协议名称 min-width="110px" align="left">
          <template slot-scope="{row}">
            <span>{{row.protocol}}</span>
          </template>
        </el-table-column>

        <el-table-column label=被攻击者IP min-width="130px" align="center">
          <template slot-scope="{row}">
            <span>{{row.dstIp}}</span>
          </template>
        </el-table-column>

        <el-table-column label=被攻击者经度 min-width="120px" align="center">
          <template slot-scope="{row}">
            <span>{{row.dstLongitude}}</span>
          </template>
        </el-table-column>

        <el-table-column label=被攻击者纬度 min-width="120px" align="center">
          <template slot-scope="{row}">
            <span>{{row.dstLatitude}}</span>
          </template>
        </el-table-column>

        <el-table-column label=攻击者IP min-width="130px" align="center">
          <template slot-scope="{row}">
            <span>{{row.srcIp}}</span>
          </template>
        </el-table-column>

        <el-table-column label=攻击者经度 min-width="120px" align="center">
          <template slot-scope="{row}">
            <span>{{row.srcLatitude}}</span>
          </template>
        </el-table-column>

        <el-table-column label=攻击者纬度 min-width="120px" align="center">
          <template slot-scope="{row}">
            <span>{{row.srcLongitude}}</span>
          </template>
        </el-table-column>

        <el-table-column label=攻击者国家 min-width="120px" align="center">
          <template slot-scope="{row}">
            <span v-if="row.srcCountry!==null&&row.srcCountry.indexOf('?')<0">{{row.srcCountry}}</span>
          </template>
        </el-table-column>

        <el-table-column label=攻击者运营商 min-width="160px" align="left">
          <template slot-scope="{row}">
            <span>{{row.srcIsp}}</span>
          </template>
        </el-table-column>

				<el-table-column label=创建时间 min-width="120px" align="center">
          <template slot-scope="{row}">
            <span>{{row.createTimeDay.substring(0,10)}}</span>
          </template>
        </el-table-column>

      </el-table>
      <dataCount dataType="THREAT_TRAP_ONLINE_LOG" />
    </div>

    <el-pagination
      background
      layout="prev, pager, next,jumper"
      v-bind.sync="pageParam"
      @current-change="changePage"
    >
    </el-pagination>


  </div>
</template>

<script>
  import myheader from '@/components/header'
  import dataCount from '@/components/dataCompare'
  import TreeSelect from '@riophae/vue-treeselect'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'
  export default {
    components: { dataCount,myheader,TreeSelect},
    data(){
      return{
        pageName:'威胁诱捕系统',
        list:[],
        loading:false,
        pageParam:{
          currentPage:1,
          total:10,
        },
        logType:'',
        logLevel:'',
        protocol:'',
        country:'',
        typeList:[{
          key:'攻击日志',
          value:0
        },{
          key :'告警日志',
          value:1
        },{
          key :'告警事件',
          value:2
        },{
          key :'状态上传',
          value:3
        },{
          key:'任务拉取',
          value:4
        }, {
          key: '任务执行',
          value:5
        }],
        levelList:[{
          key:'未知',
          value:0
        }, {
          key:'低',
          value:1
        }, {
          key:'中',
          value:2
        }, {
          key:'高',
          value:3
        }],
        options:[]
      }
    },
    created(){
      this.getData(1);
    },
    methods:{
      async getData(pageNum){
        let me = this;
        me.loading=true;
        this.$axios.get('secure/threatTrapOnline/getList',{pageNum: pageNum, logType: this.logType,
          logLevel: this.logLevel, protocol:this.protocol, country: this.country}, function(r){
          me.loading=false;
          me.list=r.data.list;
          me.pageParam.total=r.data.total;
        })
      },
      changePage(){
        this.getData(this.pageParam.currentPage);
      },
      searchData(){
        this.pageParam.currentPage=1;
        this.getData(this.pageParam.currentPage)
      },
    },
  }

</script>

