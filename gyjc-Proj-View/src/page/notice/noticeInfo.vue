<template>
  <div style="max-height: calc(100vh - 64px);overflow: auto">
    <div style="text-align: center;margin-bottom: 5vh">
      <div class="noticeTitle">{{head.title}}</div>
      <div class="titleBak">
        <span>发布时间&nbsp;：&nbsp;{{head.publishTime}}</span>
        &nbsp;|&nbsp;
        <span>浏览量&nbsp;：&nbsp;{{head.clickCount}}</span>
      </div>
    </div>
    <hr style="width: 90vw;margin-left:5vw;">
    <div class="noticeBody">{{content}}</div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        id:'',
        flag:'',
        head:{
          title:'',
          publishTime:'',
          clickCount:''
        },
        content:''
      }
    },
    mounted(){
      this.id = this.$route.params.id;
      this.flag=this.$route.params.flag;
      this.getInfo();
    },
    methods:{
      async getInfo(){
        this.$axios.get('getNoticeInfo',{id:this.id,flag:this.flag}, (r)=>{
          this.head.title = r.data.noticeSubject;
          this.head.publishTime = r.data.publishTime;
          this.head.clickCount = r.data.clickCount;
          this.content = r.data.noticeContent;
        })
      }
    }
  }

</script>

<style>
  .noticeTitle{
    font-weight: 800;
    font-size: 24px;
    margin-top: 5vh;
  }
  .titleBak{
    font-size: 15px;
    margin-top: 1vh;
  }
  .noticeBody{
    width: 90vw;
    margin-left: 5vw;
    font-size: 16px;
    margin-top: 5vh;
    white-space: break-spaces;
    word-wrap: break-word;
    margin-bottom: 8vh;
  }
</style>
