<template>
  <div class="app-container">
    <myheader :name="pageName"></myheader>
    <div class="filter-container">
      <el-row>
      	<span>类型:</span>
        <el-select v-model="dataType" filterable clearable style="width: 200px;" placeholder="请选择类型">
          <el-option
            v-for="item in typeList"
            :value="item"
            :key="item"
            :label="item">
          </el-option>
        </el-select>
        <span style="margin-left: 15px;">厂商:</span>
        <el-input v-model="manufacturer" placeholder="请输入厂商名称" style="width: 200px;" clearable  @keyup.enter.native="searchData" />
        <span style="margin-left: 15px;">所属企业:</span>
        <el-input v-model="company" placeholder="请输入企业名称" style="width: 200px;" clearable  @keyup.enter.native="searchData" />

        <!-- TODO 请选择地区名称 -->
        <span style="margin-left: 15px;">地区名称:</span>
        <tree-select placeholder="请选择地区名称" :searchable="false" :options="options" v-model="dqmc" style="width: 160px;display: inline-flex;vertical-align: middle"></tree-select>

        <el-button type="primary" class="filter-item" icon="el-icon-search" @click="searchData" style="margin-left: 15px;">
          查询
        </el-button>
      </el-row>
    </div>
    <div style="padding:15px 15px 0 15px;height:calc(100vh - 288px);background:#fff;position:relative">
      <el-table v-loading="loading" :data="list"  height="100%" stripe fit highlight-current-row >

        <el-table-column label=IP min-width="130px" align="center">
          <template slot-scope="{row}">
            <span>{{row.ip}}</span>
          </template>
        </el-table-column>

        <el-table-column label=端口号 align="center">
          <template slot-scope="{row}">
            <span>{{row.port}}</span>
          </template>
        </el-table-column>

        <el-table-column label=类型 min-width="110px" align="center">
          <template slot-scope="{row}">
            <span>{{row.type}}</span>
          </template>
        </el-table-column>

        <el-table-column label=经度 min-width="110px" align="center">
          <template slot-scope="{row}">
            <span>{{row.lng}}</span>
          </template>
        </el-table-column>

        <el-table-column label=纬度 min-width="110px" align="center">
          <template slot-scope="{row}">
            <span>{{row.lat}}</span>
          </template>
        </el-table-column>

        <el-table-column label=厂商 min-width="120px" align="left">
          <template slot-scope="{row}">
            <span>{{row.manufacturer}}</span>
          </template>
        </el-table-column>

        <el-table-column label=型号 min-width="140px" align="left">
          <template slot-scope="{row}">
            <span>{{row.model}}</span>
          </template>
        </el-table-column>

        <el-table-column label=运营商 min-width="100px" align="center">
          <template slot-scope="{row}">
            <span>{{row.operators}}</span>
          </template>
        </el-table-column>

        <el-table-column label=地区名称 min-width="100px" align="center">
          <template slot-scope="{row}">
            <span>{{row.city}}</span>
          </template>
        </el-table-column>

        <el-table-column label=资产信息 min-width="240px" align="center">
          <template slot-scope="{row}">
            <!-- <span>{{row.info}}</span> -->
            <el-input type="textarea" v-model="row.info" readonly :autosize="{ minRows: 1, maxRows: 3}"/>
          </template>
        </el-table-column>

        <el-table-column label=所属企业 min-width="120px" align="left">
          <template slot-scope="{row}">
            <span>{{row.belongCompany}}</span>
          </template>
        </el-table-column>

        <el-table-column label=漏洞编号 min-width="160px" align="center">
          <template slot-scope="{row}">
            <el-input type="textarea" v-model="row.leakId" readonly :autosize="{ minRows: 1, maxRows: 3}"/>
          </template>
        </el-table-column>

				<el-table-column label=发现时间 min-width="120px" align="center">
          <template slot-scope="{row}">
            <span>{{row.foundTime}}</span>
          </template>
        </el-table-column>

      </el-table>
      <dataCount dataType="BASIC_PROPERTY_ONLINE" />
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
        pageName:'在线监测系统',
        list:[],
        loading:false,
        pageParam:{
          currentPage:1,
          total:10,
        },
        dataType:'',
        dqmc: null,
        manufacturer: '',
        company: '',
        typeList:[],
        options:[]
      }
    },
    created(){
    	this.getSelectList();
      this.getData(1);
      this.getTreeOptions();
    },
    methods:{
    	getSelectList() {
    		let me = this;
    		this.$axios.get('secure/onlineMonitor/getSelectList', {}, function(r) {
    			me.typeList = r.data;
    		});
    	},
      getTreeOptions() {
        this.$axios.get('getAreaTree',{},(r) =>{
          this.options=r.data;
        })
      },
      async getData(pageNum){
        let me = this;
        me.loading=true;
        this.$axios.get('secure/onlineMonitor/getList',{pageNum: pageNum, dataType: this.dataType, dqmc: this.dqmc
          , company: this.company, manufacturer:this.manufacturer}, function(r){
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
      }
    },
  }

</script>

