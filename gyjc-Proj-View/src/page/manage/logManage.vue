<template>
  <div class="log" >
     <myheader :name="pageName"></myheader>
     <div class="filter-container">
       <span>用户名：</span>
      <el-input v-model="searchUserName" placeholder="请输入用户名" style="width: 130px;" clearable @keyup.enter.native="searchLog" />
      <span style="margin-left: 15px;">账号：</span>
      <el-input v-model="searchLoginName" placeholder="请输入账号" style="width: 130px;" clearable @keyup.enter.native="searchLog" />
      <span style="margin-left: 15px;">日志记录起始时间：</span>
      <el-date-picker
        v-model="startTime"
        type="date"
        value-format="yyyy-MM-dd"
        placeholder="请选择"
        style="width: 140px;">
      </el-date-picker>
      <span style="margin-left: 15px;">日志记录截止时间：</span>
      <el-date-picker
        v-model="endTime"
        type="date"
        value-format="yyyy-MM-dd"
        placeholder="请选择"
        style="width: 140px;">
      </el-date-picker>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="searchLog" style="margin-left: 15px;">
        查询
      </el-button>
     </div>
     <div class="systable-wrap" style="padding:15px 15px 0 15px;height:calc(100vh - 285px);background:#fff">
       <el-table v-loading="loading" :data="list" height="100%" stripe fit highlight-current-row >

          <el-table-column label="用户名" min-width="35%" align="center">
            <template slot-scope="{row}">
              <span>{{row.fullName}}</span>
            </template>
          </el-table-column>

          <el-table-column label="账号" min-width="35%" align="center">
            <template slot-scope="{row}">
              <span>{{row.userName}}</span>
            </template>
          </el-table-column>

          <el-table-column label="客户端IP" min-width="35%" align="center">
            <template slot-scope="{row}">
              <span>{{row.clientIp}}</span>
            </template>
          </el-table-column>

          <el-table-column label="日志信息" min-width="60%" align="center">
            <template slot-scope="{row}">
              <el-input type="textarea" v-model="row.log" readonly :autosize="{ minRows: 1, maxRows: 4}"/>
            </template>
          </el-table-column>

          <el-table-column label="日志记录时间" min-width="35%" align="center">
            <template slot-scope="{row}">
              <span>{{row.logTime}}</span>
            </template>
          </el-table-column>


        </el-table>
     </div>
    

    <el-pagination
      background
      layout="prev, pager, next, jumper"
      v-bind.sync="pageParam"
      @current-change="changePage"
    >
    </el-pagination>
  </div>
</template>

<script>

import myheader from '@/components/header'
  export default {
    components:{myheader},
    data(){
      return{
        pageName:'日志管理',
        list:[],
        loading:false,
        pageParam:{
          currentPage:1,
          total:10,
        },
        searchLoginName:'',
        searchUserName:'',
        startTime:'',
        endTime:''
      }
    },
    created(){
      this.getData(1);
    },
    watch:{
      startTime(date){
        let startDate=new Date(date.toString().replace(/-/g,"/"));
        let endDate=new Date(this.endTime.toString().replace(/-/g,"/"));
        if(startDate.getTime()>endDate.getTime()){
          this.$message({
            type:"error",
            message:"起始时间必须小于截止时间",
          })
          this.startTime=''
        }
      },
      endTime(date){
        let startDate=new Date(this.startTime.toString().replace(/-/g,"/"));
        let endDate=new Date(date.toString().replace(/-/g,"/"));
        if(startDate.getTime()>endDate.getTime()){
          this.$message({
            type:"error",
            message:"截止时间必须大于起始时间",
          })
          this.endTime=''
        }
      }
    },
    methods:{
      async getData(pageNum){
        let me = this;
        me.loading=true;
        this.$axios.get('getLogInfo',{pageNum: pageNum,searchLoginName:this.searchLoginName,searchUserName:this.searchUserName,startTime:this.startTime,endTime:this.endTime}, function(r){
          me.loading=false;
          me.list=r.data.list;
          me.pageParam.total=r.data.total;
        })
      },
      changePage(){
        this.personVisible=false;
        this.getData(this.pageParam.currentPage);
      },
      searchLog(){
        this.pageParam.currentPage=1;
        this.getData(this.pageParam.currentPage)
      }
    },
  }

</script>

<style>
  .log .el-textarea__inner {
    border: none;
    resize: none;
    background: transparent;
  }
</style>

