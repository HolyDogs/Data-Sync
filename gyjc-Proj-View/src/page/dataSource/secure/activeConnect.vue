<template>
  <div class="app-container">
    <myheader :name="pageName"></myheader>
    <div class="filter-container">
      <el-row>
      	<span>类型:</span>
        <el-select v-model="dataType" filterable clearable style="width: 200px;" placeholder="请选择类型">
          <el-option
            v-for="item in typeList"
            :value="item.itemKey"
            :key="item.itemValue"
            :label="item.itemValue">
          </el-option>
        </el-select>
        <span style="margin-left: 15px;">名称:</span>
        <el-input v-model="theName" placeholder="请输入名称" style="width: 200px;" clearable  @keyup.enter.native="searchData" />
        <span style="margin-left: 15px;">地区名称:</span>
        <el-input v-model="dqmc" placeholder="请输入地区名称" style="width: 200px;" clearable  @keyup.enter.native="searchData" />

        <el-button type="primary" class="filter-item" icon="el-icon-search" @click="searchData" style="margin-left: 15px;">
          查询
        </el-button>
      </el-row>
    </div>
    <div style="padding:15px 15px 0 15px;height:calc(100vh - 288px);background:#fff;position:relative">
      <el-table v-loading="loading" :data="list"  height="100%" stripe fit highlight-current-row >

        <el-table-column label=源IP类型 align="left">
          <template slot-scope="{row}">
            <span>{{row.srciptype}}</span>
          </template>
        </el-table-column>

        <el-table-column label=目的IP类型 align="left">
          <template slot-scope="{row}">
            <span>{{row.dstiptype}}</span>
          </template>
        </el-table-column>

        <el-table-column label=源IP min-width="140px" align="center">
          <template slot-scope="{row}">
            <span>{{row.srcip}}</span>
          </template>
        </el-table-column>

        <el-table-column label=源端口 align="center">
          <template slot-scope="{row}">
            <span>{{row.srcport}}</span>
          </template>
        </el-table-column>

        <el-table-column label=源IP单位名称 min-width="120px" align="left">
          <template slot-scope="{row}">
            <span>{{row.srcdwmc}}</span>
          </template>
        </el-table-column>

        <el-table-column label=目的IP min-width="140px" align="left">
          <template slot-scope="{row}">
            <span>{{row.dstip}}</span>
          </template>
        </el-table-column>

        <el-table-column label=目的端口 min-width="140px" align="left">
          <template slot-scope="{row}">
            <span>{{row.dstport}}</span>
          </template>
        </el-table-column>

        <el-table-column label=目的IP单位名称 min-width="140px" align="left">
          <template slot-scope="{row}">
            <span>{{row.dstdwmc}}</span>
          </template>
        </el-table-column>

        <el-table-column label=上行流量 min-width="120px" align="left">
          <template slot-scope="{row}">
            <span>{{row.ultraffic}}kb</span>
          </template>
        </el-table-column>

        <el-table-column label=下行流量 min-width="120px" align="left">
          <template slot-scope="{row}">
            <span>{{row.dltraffic}}kb</span>
          </template>
        </el-table-column>

        <el-table-column label=上行包数量 min-width="100px" align="center">
          <template slot-scope="{row}">
            <span>{{row.ulpacktes}}</span>
          </template>
        </el-table-column>

        <el-table-column label=下行包数量 min-width="100px" align="center">
          <template slot-scope="{row}">
            <span>{{row.dlpacktes}}</span>
          </template>
        </el-table-column>

				<el-table-column label=设备端位置 align="center">
          <template slot-scope="{row}">
            <span>{{row.isdevice}}</span>
          </template>
        </el-table-column>

        <el-table-column label=平台端位置 min-width="120px"  align="center">
          <template slot-scope="{row}">
            <span>{{row.isplat}}</span>
          </template>
        </el-table-column>

        <el-table-column label=传输层协议类型  min-width="140px" align="center">
          <template slot-scope="{row}">
            <span>{{row.proto}}</span>
          </template>
        </el-table-column>

        <el-table-column label=应用层协议类型 min-width="100px" align="center">
          <template slot-scope="{row}">
            <span>{{ row.protocol }}</span>
          </template>
        </el-table-column>

        <el-table-column label=涉及终端厂商 min-width="120px"  align="center">
          <template slot-scope="{row}">
            <span>{{row.manufacturer}}</span>
          </template>
        </el-table-column>

        <el-table-column label=终端类别 min-width="120px"  align="center">
          <template slot-scope="{row}">
            <span>{{row.assettype}}</span>
          </template>
        </el-table-column>

        <el-table-column label=涉及平台名称 min-width="120px"  align="center">
          <template slot-scope="{row}">
            <span>{{row.platform}}</span>
          </template>
        </el-table-column>

        <el-table-column label=通信开始时间 min-width="120px"  align="center">
          <template slot-scope="{row}">
            <span>{{row.starttime}}</span>
          </template>
        </el-table-column>

      </el-table>
      <!-- <dataCount dataType="ZB_TSGYZB_ZS_TJ" /> -->
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
  export default {
    components: { dataCount,myheader},
    data(){
      return{
        pageName:'工业互联网协议通联记录',
        list:[],
        loading:false,
        pageParam:{
          currentPage:1,
          total:10,
        },
        dataType:'',
        typeList:[],
        theName:'',
        dqmc:''
      }
    },
    created(){
    	this.getSelectList();
      this.getData(1);
    },
    methods:{
    	getSelectList() {
    		let me = this;
    		this.$axios.get('tsgyzb/getSelectList', {}, function(r) {
    			me.typeList = r.data;
    		});
    	},
      async getData(pageNum){
        let me = this;
        me.loading=true;
        this.$axios.get('secure/activeConnect/getList',{pageNum: pageNum}, function(r){

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

