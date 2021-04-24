<template>
  <div style="height: 100vh">
    <div class="filter-container" style="text-align: center">
      <span>公告标题：</span>
      <el-input v-model="subName" placeholder="请输入公告标题" style="width: 180px;" clearable  @keyup.enter.native="searchNotice" />

      <span style="margin-left: 15px;">发布时间:</span>
      <el-date-picker
        v-model="startTime"
        type="date"
        value-format="yyyy-MM-dd"
        placeholder="请选择起始时间"
        style="width: 160px;">
      </el-date-picker>
      -
      <el-date-picker
        v-model="endTime"
        type="date"
        value-format="yyyy-MM-dd"
        placeholder="请选择截止时间"
        style="width: 160px;">
      </el-date-picker>

      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="searchNotice" style="margin-left: 15px;">
        查询
      </el-button>
    </div>
    <div>
      <div style="padding:15px 15px 0 15px;height:calc(100vh - 245px);background:#fff;position:relative">
        <el-table v-loading="loading" :data="list" height="100%" stripe fit highlight-current-row >

          <el-table-column label=公告标题 align="center" min-width="60%">
            <template slot-scope="{row}">
              <div @click="openNotice(row.id)" class="hiddenFont" style="text-overflow:ellipsis;">{{row.noticeSubject}}</div>
            </template>
          </el-table-column>

          <el-table-column label=发布时间 align="center" min-width="20%">
            <template slot-scope="{row}">
              <span>{{row.publishTime.substring(0,10)}}</span>
            </template>
          </el-table-column>

          <el-table-column label=浏览数 align="center" min-width="20%">
            <template slot-scope="{row}">
              <span>{{row.clickCount}}</span>
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
  </div>
</template>

<script>

  export default {
    data(){
      return {
        pageName:'消息发布',
        list:[],
        loading:false,
        pageParam:{
          currentPage:1,
          total:10,
        },
        subName:'',
        status:'',
        title:'',
        startTime:'',
        endTime:'',
      }
    },
    mounted(){
      this.fetchDate();
    },
    watch:{
      "$route": "fetchDate",
      startTime(date){
        if(this.endTime!='') {
          let startDate = new Date(date.toString().replace(/-/g, "/"));
          let endDate = new Date(this.endTime.toString().replace(/-/g, "/"));
          if (startDate.getTime() > endDate.getTime()) {
            this.$message({
              type: "error",
              message: "起始时间必须小于截止时间",
            })
            this.startTime = ''
          }
        }
      },
      endTime(date){
        if(this.startTime!=''){
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
      }
    },
    methods:{
      fetchDate(){
        this.title = this.$route.params.title;
        this.getData(1,this.$route.params.title);
        this.addFlag = false;
      },
      async getData(pageNum,title){
        let me = this;
        me.loading=true;
        this.$axios.get('getNoticeList', {pageNum:pageNum, title:title, subName:this.subName,
                startTime: this.startTime, endTime: this.endTime}, function(r){
          me.loading=false;
          me.list=r.data.list;
          me.pageParam.total=r.data.total;
        })
      },
      searchNotice(){
        this.getData(this.pageParam.currentPage,this.title);
      },
      flushTable(){
        this.getData(this.pageParam.currentPage, this.title);
      },
      openNotice(id){
        let routeData = this.$router.resolve({
          name: "noticeInfo",
          params:{id:id,flag:1}
        });
        window.open(routeData.href, '_blank');
      },
      changePage(){
        this.getData(this.pageParam.currentPage,this.title);
      },
    }
  }

</script>

<style>
  .hiddenFont{
    white-space:nowrap;
    width:300px;
    overflow:hidden;
    text-align: left;
    line-height: 6vh;
    display: inline-block;
    cursor: pointer;
  }
  .hiddenFont:hover{
    color: cornflowerblue;
  }
</style>

