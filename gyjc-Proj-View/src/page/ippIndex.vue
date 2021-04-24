<template>
  <div align="center" style="max-height: calc(100vh - 64px);overflow: auto">
    <div >
    <el-container>
      <el-aside width="65vw" style="background: white">
        <div >
          <div class="indexTitleLeft">|&nbsp;&nbsp;我的应用</div>
          <div style="width: 60vw;margin-top: 5vh">
            <div class="appList">
              <a href="http://111.75.198.249/gyjc/managerLogin" target="_blank">
                <img src="../images/工业运行监测.png" style="width: 4vw"/>
                <span class="appLink">工业运行监测系统</span>
              </a>
            </div>
            <div class="appList">
              <a href="http://192.168.35.1:8080/test/index.shtml" target="_blank">
                <img src="../images/工业云门户.png" style="width: 4vw"/>
                <span class="appLink">江西省工业云门户</span>
              </a>
            </div>
            <div class="appList">
              <a href="http://192.168.35.2:18460/viewer" target="_blank">
                <img src="../images/工业大数据.png" style="width: 4vw"/>
                <span class="appLink">工业大数据融合平台</span>
              </a>
            </div>
          </div>
          <div style="margin-top: 2vh">
            <div class="appList">
              <a href="http://192.168.35.8:8008/" target="_blank">
                <img src="../images/企业能耗监测.png" style="width: 4vw"/>
                <span class="appLink">企业能耗监测系统</span>
              </a>
            </div>
            <div class="appList">
              <a href="https://192.168.200.201/login.html" target="_blank">
                <img src="../images/在线监测系统.png" style="width: 4vw"/>
                <span class="appLink">在线监测系统</span>
              </a>
            </div>
            <div class="appList">
              <a href="https://192.168.200.210:8000" target="_blank">
                <img src="../images/威胁诱捕系统.png" style="width: 4vw"/>
                <span class="appLink">威胁诱捕系统</span>
              </a>
            </div>
          </div>
        </div>
      </el-aside>

      <el-main width="30vw" style="background: white">
        <div style="width: 70%;margin-left: -10%;height: 30vh;border: 1px solid black;background: #fafafa">
          <div style=" width: 100%;height: 8vh;font-size: 14px;background: #f5f5f5;line-height: 8vh">
            <span>今天是 </span>
            <span>{{new Date().getFullYear()}}年{{new Date().getMonth()+1}}月{{new Date().getDate()}}日</span>
            <span v-if="new Date().getDay()==0">星期日</span>
            <span v-if="new Date().getDay()==1">星期一</span>
            <span v-if="new Date().getDay()==2">星期二</span>
            <span v-if="new Date().getDay()==3">星期三</span>
            <span v-if="new Date().getDay()==4">星期四</span>
            <span v-if="new Date().getDay()==5">星期五</span>
            <span v-if="new Date().getDay()==6">星期六</span>
          </div>
          <div style="margin-top: 3vh">
            <span style="margin-left: -1vw;display: inline-block;"><img style="height: 15vh" src="../images/admin.gif"/></span>
            <span style="margin-left: 2vw;display: inline-block;vertical-align: top">
              <div style="font-size: 32px;color: #4a7fea;font-weight: 800;">{{this.$cookie.getCookie('username')}}</div>
              <br>
              <div style="font-size: 26px;margin-top: -2vh">{{this.$cookie.getCookie('fullname')}}</div>
            </span>
          </div>
        </div>
      </el-main>
    </el-container>
    </div>
    <div style="margin-top: 2vh">
      <el-container>
        <el-aside width="65vw" style="margin-top: 6vh;background: white;height: 520px">
          <div style="width: 860px;">
            <el-carousel trigger="click" height="428px" @change="changePic">
              <el-carousel-item >
                <img src="../images/轮播图一.jpg" class="absolutePicSize"/>
              </el-carousel-item>
              <el-carousel-item>
                <img src="../images/轮播图二.jpg" class="absolutePicSize"/>
              </el-carousel-item>
              <el-carousel-item>
                <img src="../images/轮播图三.jpg" class="absolutePicSize"/>
              </el-carousel-item>
              <el-carousel-item>
                <img src="../images/轮播图四.jpg" class="absolutePicSize"/>
              </el-carousel-item>
              <el-carousel-item>
                <img src="../images/轮播图五.jpg" class="absolutePicSize"/>
              </el-carousel-item>
            </el-carousel>
            <div style="margin-top: 1vh">{{picTitle}}</div>
          </div>
        </el-aside>

        <el-main width="30vw" style="background: white">
          <div>
            <div style="margin-bottom: 3vh">
              <span class="indexTitleRight">|&nbsp;&nbsp;工作动态</span>
              <span @click="openMore('GZDT')" class="more">更多&nbsp;>></span>
            </div>
            <div v-for="gzdt in gzdtList" style="height: 6vh">
              <div @click="openNotice(gzdt.id)" class="hiddenFont" style="text-overflow:ellipsis;">{{gzdt.noticeSubject}}</div>
              <div class="timeStyle">{{gzdt.publishTime.substring(0,10)}}</div>
              <br>
            </div>
          </div>
        </el-main>
      </el-container>
    </div>
    <div >
      <el-container>
        <el-aside width="32.5vw" style="overflow: hidden">
          <div>
            <div style="margin-bottom: 3vh">
              <span class="indexTitleRight">|&nbsp;&nbsp;通知公告</span>
              <span @click="openMore('TZGG')" class="more">更多&nbsp;>></span>
            </div>
            <div v-for="gzdt in tzggList" style="height: 6vh">
              <div @click="openNotice(gzdt.id)" class="hiddenFont" style="text-overflow:ellipsis;">{{gzdt.noticeSubject}}</div>
              <div class="timeStyle">{{gzdt.publishTime.substring(0,10)}}</div>
              <br>
            </div>
          </div>
        </el-aside>

        <el-aside width="32.5vw" style="overflow: hidden">
          <div>
            <div style="margin-bottom: 3vh">
              <span class="indexTitleRight">|&nbsp;&nbsp;政策解读</span>
              <span @click="openMore('ZCJD')" class="more">更多&nbsp;>></span>
            </div>
            <div v-for="gzdt in zcjdList" style="height: 6vh">
              <div @click="openNotice(gzdt.id)" class="hiddenFont" style="text-overflow:ellipsis;">{{gzdt.noticeSubject}}</div>
              <div class="timeStyle">{{gzdt.publishTime.substring(0,10)}}</div>
              <br>
            </div>
          </div>
        </el-aside>

        <el-main width="30vw" style="margin-top: -20px">
          <div>
            <div style="margin-bottom: 3vh">
              <span class="indexTitleRight">|&nbsp;&nbsp;常用下载</span>
              <span class="more">更多&nbsp;>></span>
            </div>
            <div>
              <div style="position: relative">
                <a type="primary" style="margin-left: -16vw" href="/static/word.doc" target="_blank">
                  <div style="display: inline-block"><img src="../images/word.gif" style="height: 7vh"/></div>
                  <div class="wordList">江西省工业信息公开申请表.word</div>
                </a>
                <br>
                <a type="primary" style="margin-left: -16vw" href="/static/word1.docx" target="_blank">
                  <div style="display: inline-block"><img src="../images/word.gif" style="height: 7vh"/></div>
                  <div class="wordList">江西省工业信息公开申请表.word</div>
                </a>
                <br>
                <a type="primary" style="margin-left: -16vw" href="/static/word2.docx" target="_blank">
                  <div style="display: inline-block"><img src="../images/word.gif" style="height: 7vh"/></div>
                  <div class="wordList">江西省工业信息公开申请表.word</div>
                </a>
                <br>
                <a type="primary" style="margin-left: -16vw" href="/static/监控化学品行政许可申请表.rar" target="_blank">
                  <div style="display: inline-block"><img src="../images/word.gif" style="height: 7vh"/></div>
                  <div class="wordList">监控化学品行政许可申请表.rar</div>
                </a>
              </div>
            </div>
          </div>
        </el-main>
      </el-container>
    </div>
  </div>

</template>
<script>

import myheader from '@/components/header'
import bindZB from '@/components/bindZB'

export default {
  name: 'index',

  data() {
    return {
      picTitle:'厅领导前往省行政中心集中办事大厅工信服务窗口检查指导工作',
      gzdtList:[],
      tzggList:[],
      zcjdList:[]
    }
  },
  created(){
    this.getNoticeList('GZDT');
    this.getNoticeList('TZGG');
    this.getNoticeList('ZCJD');
  },
  methods:{
    changePic(i,x){
      if(i==0){
        this.picTitle='厅领导前往省行政中心集中办事大厅工信服务窗口检查指导工作'
      }
      if(i==1){
        this.picTitle='厅领导调研景德镇航空产业'
      }
      if(i==2){
        this.picTitle='省工信厅机关离退休干部工作座谈会召开'
      }
      if(i==3){
        this.picTitle='“十三五”江西工业和信息化发展成就新闻发布会召开 杨贵平谈“十三五”江西工信十大亮点'
      }
      if(i==4){
        this.picTitle='省工业强省办召开全省产业链链长制工作调度推进会'
      }
    },
    getNoticeList(title){
      this.$axios.get('getNoticeList', {pageNum:1, title:title }, (r) =>{
        if(title=='GZDT'){
          this.gzdtList = r.data.list;
        }
        if(title=='TZGG'){
          this.tzggList = r.data.list;
        }
        if(title=='ZCJD'){
          this.zcjdList = r.data.list;
        }
      })
    },
    openNotice(id){
      let routeData = this.$router.resolve({
        name: "noticeInfo",
        params:{id:id,flag:1}
      });
      window.open(routeData.href, '_blank');
    },
    openMore(title){
      let moreNotices = this.$router.resolve({
        name: "moreNotices",
        params:{title:title}
      });
      window.open(moreNotices.href, '_blank');
    },
  }
}
</script>

<style>
  .indexTitleLeft{
    color: rgb(74, 127, 234);
    font-weight: 800;
    width: 15vw;
    margin-left: -50vw;
    font-size: 24px;
  }
  .indexTitleRight{
    color: rgb(74, 127, 234);
    font-weight: 800;
    width: 15vw;
    font-size: 24px;
  }
  .more{
    margin-left: 15vw;
    color: rgb(74, 127, 234);
    font-size: 20px;
    cursor: pointer;
  }
  .appList{
    display: inline-block;
    margin-left: 3vw;
    width: 16vw;
    text-align: left;
    font-size: 14px;
    position: relative;
  }
  .appList a{
    text-decoration: none;
  }
  .appLink{
    position: absolute;
    top: 4vh;
    margin-left: 0.8vw;
  }
  .absolutePicSize{
    width: 770px;
    height: 428px;
  }
  .hiddenFont{
    white-space:nowrap;
    width:18vw;
    overflow:hidden;
    text-align: left;
    line-height: 6vh;
    display: inline-block;
    cursor: pointer;
  }
  .hiddenFont:hover{
    color: cornflowerblue;
  }
  .timeStyle{
    line-height: 6vh;
    display: inline-block;
    white-space:nowrap;
    vertical-align: top;
    margin-left: 2vw;
  }
  .wordList{
    display: inline-block;
    position: absolute;
    margin-left: 0.8vw;
    margin-top: 2vh;
  }
</style>
