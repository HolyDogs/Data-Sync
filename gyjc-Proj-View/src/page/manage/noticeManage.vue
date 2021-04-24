<template>
  <div   class="container" >
    <div v-if="!addFlag">
      <myheader :name="pageName"></myheader>
      <div class="filter-container">
        <span>公告标题：</span>
        <el-input v-model="subName" placeholder="请输入公告标题" style="width: 150px;" clearable  @keyup.enter.native="searchNotice" />
        <span style="margin-left: 15px;">发布状态：</span>
        <el-select v-model="status" filterable clearable placeholder="请选择发布状态" style="width: 150px">
          <el-option
            v-for="item in statusList"
            :key="item.key"
            :label="item.key"
            :value="item.value">
          </el-option>
        </el-select>

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
        <el-button class="filter-item" type="primary" icon="el-icon-plus" @click="addNotice" style="margin-left: 15px;">
          新增
        </el-button>
      </div>


      <div style="padding:15px 15px 0 15px;height:calc(100vh - 285px);background:#fff;position:relative">
        <el-table v-loading="loading" :data="list" height="100%" stripe fit highlight-current-row >

          <el-table-column label=公告标题 align="center" min-width="40%">
            <template slot-scope="{row}">
              <div @click="openNotice(row.id)" class="hiddenFont" style="text-overflow:ellipsis;">{{row.noticeSubject}}</div>
            </template>
          </el-table-column>

          <el-table-column label=浏览数 align="center" min-width="10%">
            <template slot-scope="{row}">
              <span>{{row.clickCount}}</span>
            </template>
          </el-table-column>

          <el-table-column label=发布时间 align="center" min-width="10%">
            <template slot-scope="{row}">
              <span>{{row.publishTime.substring(0,10)}}</span>
            </template>
          </el-table-column>

          <el-table-column label=发布状态 align="center" min-width="10%">
            <template slot-scope="{row}">
              <span v-if="row.publishStatus=='YFB'">已发布</span>
              <span v-if="row.publishStatus=='DFB'">待发布</span>
              <span v-if="row.publishStatus=='XZ'">新增</span>
            </template>
          </el-table-column>

          <el-table-column label="操作" align="center" min-width="30%">
            <template slot-scope="{row}">
              <div v-if="row.publishStatus=='DFB'">
                <el-button type="primary" size="small" @click="updateNotice(row)">
                  修改
                </el-button>
                <el-button type="primary" size="small" @click="deleteNotice(row)">
                  删除
                </el-button>
                <el-button type="primary" size="small" @click="publishNotice(row)">
                  发布
                </el-button>
              </div>
              <div v-if="row.publishStatus=='XZ'">
                <el-button type="primary" size="small" @click="updateNotice(row)">
                  修改
                </el-button>
                <el-button type="primary" size="small" @click="deleteNotice(row)">
                  删除
                </el-button>
                <el-button type="primary" size="small" @click="publishNotice(row)">
                  发布
                </el-button>
              </div>
              <div v-if="row.publishStatus=='YFB'">
                <el-button type="primary" size="small" @click="unpublishNotice(row)">
                  取消发布
                </el-button>
              </div>
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
    <div style="height: 100vh" v-if="addFlag">
      <noticePush :notice="notice" @changeFlag="changeFlag"></noticePush>
    </div>
  </div>
</template>

<script>

import noticePush from '@/components/noticePush'
import myheader from '@/components/header'
  export default {
    components:{myheader,noticePush},
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
        statusList:[{key:'已发布',value:'YFB'},{key:'待发布',value:'DFB'},{key:'新增',value:'XZ'}],
        addFlag:false,
        notice:{
          id:'',
          noticeSubject:'',
          noticeContent:'',
          noticeSource:'',
          noticeType:'',
          publishTime:'',
          sorted:''
        }
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
        this.$axios.get('getNoticeLists',{pageNum: pageNum,title:title,subName:this.subName,
          status:this.status,startTime:this.startTime,endTime:this.endTime}, function(r){
          me.loading=false;
          me.list=r.data.list;
          me.pageParam.total=r.data.total;
        })
      },
      searchNotice(){
        this.getData(this.pageParam.currentPage,this.title);
      },
      changePage(){
        this.getData(this.pageParam.currentPage,this.title);
      },
      addNotice(){
        this.addFlag = true;
        this.notice.id = '',
        this.notice.noticeSubject = '',
        this.notice.noticeContent = '',
        this.notice.noticeSource = '',
        this.notice.noticeType = this.title,
        this.notice.publishTime = '',
        this.notice.sorted = ''
      },
      updateNotice(row){
        this.addFlag = true;
        this.notice.id = row.id,
        this.notice.noticeSubject = row.noticeSubject,
        this.notice.noticeContent = row.noticeContent,
        this.notice.noticeSource = row.noticeSource,
        this.notice.noticeType = row.noticeType,
        this.notice.publishTime = row.publishTime,
        this.notice.sorted = row.sorted
      },
      deleteNotice(row){
        this.$confirm(`您确认删除这篇消息?`, '提示', {
          cancelButtonClass:'btn-custom-cancel',
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=> {
          this.$axios.get('deleteNotice', {id : row.id}, (r) => {
            if (r.errCode == 200) {
              this.$message({
                type: "success",
                message: "删除成功"
              })
              this.flushTable();
            }else{
              this.$message({
                type: "error",
                message: "删除失败"
              })
            }
          })
        })
      },
      publishNotice(row){
        this.$confirm(`您确认发布这篇消息?`, '提示', {
          cancelButtonClass:'btn-custom-cancel',
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=> {
          this.$axios.get('publishNotice', {id : row.id}, (r) => {
            if (r.errCode == 200) {
              this.$message({
                type: "success",
                message: "发布成功"
              })
              this.flushTable();
            } else {
              this.$message({
                type: "error",
                message: "发布失败"
              })
            }
          })
        })
      },
      unpublishNotice(row){
        this.$confirm(`您确认取消发布这篇消息?`, '提示', {
          cancelButtonClass:'btn-custom-cancel',
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=> {
          this.$axios.get('unpublishNotice', {id : row.id}, (r) => {
            if (r.errCode == 200) {
              this.$message({
                type: "success",
                message: "取消发布成功"
              })
              this.flushTable();
            } else {
              this.$message({
                type: "error",
                message: "取消发布发布失败"
              })
            }
          })
        })
      },
      changeFlag(){
        this.addFlag = false;
        this.flushTable();
      },
      flushTable(){
        this.getData(this.pageParam.currentPage, this.title);
      },
      openNotice(id){
        let routeData = this.$router.resolve({
          name: "noticeInfo",
          params:{id:id,flag:0}
        });
        window.open(routeData.href, '_blank');
      }
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

