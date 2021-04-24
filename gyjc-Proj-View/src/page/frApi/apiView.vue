<template>
  <div class="log app-container">
    <myheader :name="pageName"></myheader>
    <div class="filter-container">
    <el-row>
      <span>接口提供方：</span>
      <el-input v-model="provider" placeholder="请输入接口提供方" style="width: 200px;" clearable @keyup.enter.native="searchApi" />
      <span style="margin-left: 15px;">接口调用方：</span>
      <el-input v-model="producer" placeholder="请输入接口调用方" style="width: 200px;" clearable @keyup.enter.native="searchApi" />
      <span style="margin-left: 15px;">调用结果：</span>
      <el-select v-model="result" filterable clearable placeholder="请选择调用结果" style="width: 150px">
        <el-option
          v-for="item in results"
          :key="item"
          :label="item"
          :value="item">
        </el-option>
      </el-select>
    </el-row>
    <el-row style="margin-top:10px">
      <span>调用起始时间：</span>
      <el-date-picker
        v-model="startTime"
        type="date"
        value-format="yyyy-MM-dd"
        placeholder="请选择"
        style="width: 140px;">
      </el-date-picker>
      <span style="margin-left: 15px;">调用截止时间：</span>
      <el-date-picker
        v-model="endTime"
        type="date"
        value-format="yyyy-MM-dd"
        placeholder="请选择"
        style="width: 140px;">
      </el-date-picker>
      <el-button class="filter-item" type="primary" icon="el-icon-search" style="margin-left: 15px;" @click="searchApi">
        查询
      </el-button>
    </el-row>
    </div>
     <div style="padding:15px 15px 0 15px;height:calc(100vh - 339px);background:#fff;">
      <el-table v-loading="loading" :data="list" height="100%" stripe fit highlight-current-row >

        <el-table-column label="接口提供方" min-width="35%" align="center">
          <template slot-scope="{row}">
            <span>{{row.jktgf}}</span>
          </template>
        </el-table-column>

        <el-table-column label="接口调用方" min-width="35%" align="center">
          <template slot-scope="{row}">
            <span>{{row.jkdyf}}</span>
          </template>
        </el-table-column>

        <el-table-column label="调用方IP" min-width="35%" align="center">
          <template slot-scope="{row}">
            <span>{{row.ip}}</span>
          </template>
        </el-table-column>

        <el-table-column label="调用结果" min-width="35%" align="center">
          <template slot-scope="{row}">
            <span>{{row.result}}</span>
          </template>
        </el-table-column>

        <el-table-column label="调用日志" min-width="60%" align="center">
          <template slot-scope="{row}">
            <el-input type="textarea" v-model="row.resultLog" readonly :autosize="{ minRows: 1, maxRows: 4}"/>
          </template>
        </el-table-column>

        <el-table-column label="调用时间" min-width="35%" align="center">
          <template slot-scope="{row}">
            <span>{{row.dyTime.substr(0,10)}}</span>
          </template>
        </el-table-column>


      </el-table>
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
  export default {
    components:{myheader},
    data(){
      return{
        pageName:'接口监控',
        list:[],
        loading:false,
        pageParam:{
          currentPage:1,
          total:10,
        },
        provider:'',
        producer:'',
        startTime:'',
        endTime:'',
        result:'',
        results:['成功','失败']
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
        this.$axios.get('getApiResult',{pageNum: pageNum,provider:this.provider,producer:this.producer,result:this.result,startTime:this.startTime,endTime:this.endTime}, function(r){
          me.loading=false;
          me.list=r.data.list;
          me.pageParam.total=r.data.total;
        })
      },
      changePage(){
        this.personVisible=false;
        this.getData(this.pageParam.currentPage);
      },
      searchApi(){
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
  }
</style>

