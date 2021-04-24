<template>
  <div class="app-container">
    <myheader :name="pageName"></myheader>
    <div class="filter-container">
      <el-row>

          <span>5G基站索引号:</span>
          <el-input v-model="jzsyh" placeholder="请输入5G基站索引号" style="width: 180px;" clearable  @keyup.enter.native="searchData" />

          <span style="margin-left: 15px;">台站设置使用人:</span>
          <el-input v-model="tzszsyr" placeholder="请输入台站设置使用人" style="width: 190px;" clearable  @keyup.enter.native="searchData" />

          <span style="margin-left: 15px;">联系人:</span>
          <el-input v-model="lxr" placeholder="请输入联系人" style="width: 150px;" clearable  @keyup.enter.native="searchData" />

          <span style="margin-left: 15px;">基站类型:</span>
          <el-select v-model="jzlx" filterable clearable placeholder="请选择基站类型" style="width: 150px">
            <el-option
              v-for="item in jzlxs"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>

      </el-row>
      <el-row style="margin-top:10px">
          <span>基站状态:</span>
          <el-select v-model="jzzt" filterable clearable placeholder="请选择基站状态" style="width: 150px">
            <el-option
              v-for="item in jzzts"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>

           <span style="margin-left: 15px;">建站起始时间:</span>
          <el-date-picker
            v-model="startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择起始时间"
            style="width: 160px;">
          </el-date-picker>

           <span style="margin-left: 15px;">建站截止时间:</span>
          <el-date-picker
            v-model="endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择截止时间"
            style="width: 160px;">
          </el-date-picker>
          <el-button type="primary" class="filter-item" icon="el-icon-search" @click="searchData" style="margin-left: 15px;">
            查询
          </el-button>
      </el-row>



    </div>
    <div style="padding:15px 15px 0 15px;height:calc(100vh - 334px);background:#fff;position:relative">
      <el-table v-loading="loading" :data="list"  height="100%" stripe fit highlight-current-row >

        <el-table-column label=5G基站索引号 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.jzsyh}}</span>
          </template>
        </el-table-column>

        <el-table-column label=台站设置使用人 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.tzszsyr}}</span>
          </template>
        </el-table-column>

        <el-table-column label=联系人 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.lxr}}</span>
          </template>
        </el-table-column>

        <el-table-column label=联系电话 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.lxdh}}</span>
          </template>
        </el-table-column>

        <el-table-column label=台址 width="150px" align="left">
          <template slot-scope="{row}">
            <span>{{row.sxtxdqz}}</span>
          </template>
        </el-table-column>

        <el-table-column label=东经坐标（°） width="150px" align="center">
          <template slot-scope="{row}">
            <span v-if="row.djzb.indexOf('.')>0">{{row.djzb.substr(0,row.djzb.indexOf('.')+4)}}</span>
            <span v-if="row.djzb.indexOf('.')<=0">{{row.djzb}}</span>
          </template>
        </el-table-column>

        <el-table-column label=北纬坐标（°） width="150px" align="center">
          <template slot-scope="{row}">
            <span v-if="row.bwzb.indexOf('.')>0">{{row.bwzb.substr(0,row.bwzb.indexOf('.')+4)}}</span>
            <span v-if="row.bwzb.indexOf('.')<=0">{{row.bwzb}}</span>
          </template>
        </el-table-column>

        <el-table-column label=工作频段（MHz） width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.zdgzpd}}-{{row.zggzpd}}</span>
          </template>
        </el-table-column>

        <el-table-column label=基站类型（室内基站、室外基站） width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.jzlx}}</span>
          </template>
        </el-table-column>

        <el-table-column label=基站全球小区码 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.jzqqxqm}}</span>
          </template>
        </el-table-column>

        <el-table-column label=基站状态（规划、已建设、已许可） width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.jzzt}}</span>
          </template>
        </el-table-column>

        <el-table-column label=建站时间 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.jzsj.substr(0,10)}}</span>
          </template>
        </el-table-column>

        <el-table-column label=电台执照编号 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.dtzzbh}}</span>
          </template>
        </el-table-column>

        <el-table-column label=是否需要协调 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.sfxyxt}}</span>
          </template>
        </el-table-column>

        <el-table-column label=应与哪些台协调 width="250px" align="center">
          <template slot-scope="{row}">
            <span>{{row.yynxtxt}}</span>
          </template>
        </el-table-column>

        <el-table-column label=是否完成协调 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.sfwcxt}}</span>
          </template>
        </el-table-column>

      </el-table>
       <dataCount dataType="BASESTATION_USE_SCHEDULE" />
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
    components: { dataCount,myheader },
    data(){
      return{
        pageName:'5G基站使用进度数据',
        list:[],
        loading:false,
        pageParam:{
          currentPage:1,
          total:10,
        },
        jzsyh:'',
        tzszsyr:'',
        lxr:'',
        jzlx:'',
        jzzt:'',
        startTime:'',
        endTime:'',
        jzlxs:['室内基站','室外基站'],
        jzzts:['规划','已建设','已许可']
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
        this.$axios.get('5G/getUseSchedule',{pageNum: pageNum,jzsyh:this.jzsyh,tzszsyr:this.tzszsyr,lxr:this.lxr,jzlx:this.jzlx,jzzt:this.jzzt,startTime:this.startTime,endTime:this.endTime}, function(r){
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

