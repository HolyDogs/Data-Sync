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

        <el-table-column label=类型 min-width="140px" align="left">
          <template slot-scope="{row}">
            <span>{{row.typeName}}</span>
          </template>
        </el-table-column>

        <el-table-column label=名称 min-width="140px" align="left">
          <template slot-scope="{row}">
            <span>{{row.name}}</span>
          </template>
        </el-table-column>

        <el-table-column label=所属级别 min-width="100px" align="center">
          <template slot-scope="{row}">
            <span>{{row.jb}}</span>
          </template>
        </el-table-column>

        <el-table-column label=年份 align="center">
          <template slot-scope="{row}">
            <span>{{row.nf}}</span>
          </template>
        </el-table-column>

        <el-table-column label=行业名称 min-width="120px" align="left">
          <template slot-scope="{row}">
            <span>{{row.hymc}}</span>
          </template>
        </el-table-column>

        <el-table-column label=地区名称 min-width="140px" align="left">
          <template slot-scope="{row}">
            <span>{{row.dqmc}}</span>
          </template>
        </el-table-column>

        <el-table-column label=所属单位 min-width="140px" align="left">
          <template slot-scope="{row}">
            <span>{{row.ssdw}}</span>
          </template>
        </el-table-column>

        <el-table-column label=单位性质 min-width="140px" align="left">
          <template slot-scope="{row}">
            <span>{{row.qyxz}}</span>
          </template>
        </el-table-column>

        <el-table-column label=产品名称 min-width="140px" align="left">
          <template slot-scope="{row}">
            <span>{{row.cpmc}}</span>
          </template>
        </el-table-column>

        <el-table-column label=计量单位 min-width="100px" align="center">
          <template slot-scope="{row}">
            <span>{{row.jldw}}</span>
          </template>
        </el-table-column>

        <el-table-column label=许可能力 min-width="100px" align="center">
          <template slot-scope="{row}">
            <span>{{row.xknl}}</span>
          </template>
        </el-table-column>

        <el-table-column label=生产量 align="center">
          <template slot-scope="{row}">
            <span>{{row.scl}}</span>
          </template>
        </el-table-column>

				<el-table-column label=销售量 align="center">
          <template slot-scope="{row}">
            <span>{{row.xsl}}</span>
          </template>
        </el-table-column>

        <el-table-column label=质量标杆名称 min-width="120px"  align="center">
          <template slot-scope="{row}">
            <span>{{row.zlbgmc}}</span>
          </template>
        </el-table-column>

        <el-table-column label=服务型制造模式 min-width="140px" align="center">
          <template slot-scope="{row}">
            <span>{{row.fwxzzms}}</span>
          </template>
        </el-table-column>

        <el-table-column label=导入时间 min-width="100px" align="center">
          <template slot-scope="{row}">
            <span>{{ $strTransfer.dateFormat(row.createTime) }}</span>
          </template>
        </el-table-column>

      </el-table>
      <dataCount dataType="ZB_TSGYZB_ZS_TJ" />
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
        pageName:'特色工业指标',
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
        this.$axios.get('tsgyzb/getList',{pageNum: pageNum,dataType:me.dataType,theName:me.theName,dqmc:me.dqmc}, function(r){

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

