<template>
  <div class="app-container page_02" align="center" style="height:calc(100vh - 64px)">
    <myheader :name="pageName"></myheader>
    <div class="filter-container">

      <span>指标名称：</span>
      <el-input v-model="listQuery.zbmc" placeholder="请输入指标名称" clearable style="width: 150px;" class="filter-item" @keyup.enter.native="handleFilter" />


      <span style="margin-left: 15px;">指标分类：</span>
      <el-select style="width: 150px" v-model="listQuery.zbfl" filterable clearable placeholder="请选择">
        <el-option
          v-for="item in zbflList"
          :key="item"
          :label="item"
          :value="item">
        </el-option>
      </el-select>

      <span style="margin-left: 15px;">地区名称：</span>
      <el-input v-model="listQuery.dqmc" placeholder="请输入地区名称" clearable style="width: 150px;" class="filter-item" @keyup.enter.native="handleFilter" />

      <span style="margin-left: 15px;">起始时间：</span>
      <el-date-picker type="month" v-model="listQuery.startTime" placeholder="请选择"
                      value-format="yyyy-MM-dd" style="width: 130px"></el-date-picker>
      <span style="margin-left: 15px;">截止时间：</span>
      <el-date-picker type="month" v-model="listQuery.endTime" placeholder="请选择"
                      value-format="yyyy-MM-dd" style="width: 130px"></el-date-picker>

      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter" style="margin-left: 15px;">
        查询
      </el-button>

    </div>


<div style="padding:15px 15px 0 15px;height:calc(100vh - 285px);background:#fff;position:relative">
    <el-table v-loading="listLoading" :data="list" height="100%" stripe  fit highlight-current-row
    >

      <!-- ID不显示 -->

      <el-table-column align="left" label="指标名称">
        <template slot-scope="{row}">
          <span>{{ row.zbmc }}</span>
        </template>
      </el-table-column>

      <el-table-column align="left" label="指标分类">
        <template slot-scope="{row}">
          <span>{{ row.zbfl }}</span>
        </template>
      </el-table-column>

      <el-table-column align="left" label="计量单位">
        <template slot-scope="{row}">
          <span>{{ row.jldw }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="数值">
        <template slot-scope="{row}">
          <span>{{ row.sz }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="地区名称">
        <template slot-scope="{row}">
          <span>{{ row.dqmc }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="年月">
        <template slot-scope="{row}">
          <span>{{ $strTransfer.timeIdFormat(row.sjid) }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="导入时间">
        <template slot-scope="{row}">
          <span>{{ $strTransfer.dateFormat(row.createDate) }}</span>
        </template>
      </el-table-column>

      <!-- 绑定多个指标时显示不合理 -->
      <!--       <el-table-column width="180px" align="center" label="绑定指标来源表">
              <template slot-scope="{row}">
                <span>{{ row.bindZbTable }}</span>
              </template>
            </el-table-column> -->

    </el-table>
     <dataCount dataType="EXCEL_HAND_IMPORT" />
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

//引入组件
import dataCount from '@/components/dataCompare'
import myheader from '@/components/header'

  export default {
    name: 'newTableView',
    components: { dataCount ,myheader},
    data() {
      return {
        pageName:'数据中心-手动导入',
        zbflList: null,
        list: null,
        dialogVisible: false,
        tableName: '',
        listLoading: false,
        loading: false,
        listQuery:{
          zbmc: '',
          zbfl: '',
          dqmc: '',
          startTime:'',
          endTime:'',
        },
        pageParam:{
          currentPage: 1,
          total: 10
        },
        selectItems:[]
      }
    },
    created() {
      this.getList(1);
      this.getzbflList();
    },
    watch:{
      "listQuery.startTime":{
        handler(date){
          if(date!=null){
            let startDate=new Date(date.toString().replace(/-/g,"/"));
            let endDate=new Date(this.listQuery.endTime.toString().replace(/-/g,"/"));
            if(startDate.getTime()>endDate.getTime()){
              this.$message({
                type:"error",
                message:"起始时间必须小于截止时间",
              })
              this.listQuery.startTime=''
            }
          }
        },deep:true
      },
      "listQuery.endTime":{
        handler(date){
          if(date!=null) {
            let startDate = new Date(this.listQuery.startTime.toString().replace(/-/g, "/"));
            let endDate = new Date(date.toString().replace(/-/g, "/"));
            if (startDate.getTime() > endDate.getTime()) {
              this.$message({
                type: "error",
                message: "截止时间必须大于起始时间",
              })
              this.listQuery.endTime = ''
            }
          }
        },deep:true
      }
    },
    methods: {
      getzbflList() {
        let me = this;
        this.$axios.get('handImport/getZbflList',{}, function(r){
          me.zbflList = r.data;
        });
      },
      async getList(pageNum, zbmc, zbfl, dqmc,startTime,endTime) {
        let me = this;
        //设置加载动画
        this.listLoading = true;
        this.$axios.get('handImport/getList',{pageNum: pageNum, zbmc: zbmc, zbfl: zbfl, dqmc: dqmc,startTime:startTime,endTime:endTime}, function(r){
          //页数
          me.pageParam.total = r.data.total;
          //设置表单数据
          me.list = r.data.list;
          //关闭加载动画
          me.listLoading = false;
        })

      },
      handleFilter() {
        //设置当前第一页
        this.pageParam.currentPage = 1;
        //查询
        this.getList(this.pageParam.currentPage, this.listQuery.zbmc, this.listQuery.zbfl, this.listQuery.dqmc,this.listQuery.startTime, this.listQuery.endTime);
      },
      changePage() {
        //查询
        this.getList(this.pageParam.currentPage, this.listQuery.zbmc, this.listQuery.zbfl, this.listQuery.dqmc,this.listQuery.startTime, this.listQuery.endTime);
      },
      getRowKey(row) {
        return row.id;
      }
    }
  }
</script>
