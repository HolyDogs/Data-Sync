<template>
  <div style="height: 100vh;">
    <div class="filter-container">
      <el-form>
        <div>
          <span>公告标题&nbsp;<b  style="color: red;">*</b></span>
          <span><el-input type="text" maxlength="512" @input="changeTitle()" clearable style="width: 60vw" v-model="notice.noticeSubject"></el-input></span>
        </div>
        <br>
        <div>
          <span>公告内容&nbsp;<b  style="color: red;">*</b></span>
          <span><el-input type="textarea" style="width: 60vw" v-model="notice.noticeContent" :autosize="{ minRows: 5, maxRows: 10}"/></span>
        </div>
        <br>
        <div>
          <span>公告来源&nbsp;&nbsp;&nbsp;</span>
          <span><el-input type="text" maxlength="512" @input="changeTitle()" style="width: 60vw" v-model="notice.noticeSource"></el-input></span>
        </div>
        <br>
        <div>
          <span>公告类型&nbsp;<b  style="color: red;">*</b></span>
          <span>
            <el-select v-model="notice.noticeType" filterable placeholder="请选择公告类型" style="width: 150px">
              <el-option
                v-for="item in noticeTypes"
                :key="item.key"
                :label="item.key"
                :value="item.value">
              </el-option>
            </el-select>
          </span>
          <span style="margin-left: 15px">发布时间&nbsp;<b  style="color: red;">*</b></span>
          <el-date-picker
            v-model="notice.publishTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择发布时间"
            style="width: 160px;">
          </el-date-picker>
          <span style="margin-left: 15px">排序序号&nbsp;&nbsp;&nbsp;</span>
          <el-input-number v-model="notice.sorted" :precision="0" :step="1" :min="0" :max="999999999999"></el-input-number>
        </div>
        <br>
        <div style="margin-left: 25vw">
          <el-button class="filter-item" type="primary" icon="el-icon-receiving" @click="saveNotice" style="margin-left: 15px;">
            保存
          </el-button>
          <el-button class="filter-item" type="primary" icon="el-icon-s-promotion" @click="publishNotice" style="margin-left: 15px;">
            发布
          </el-button>
          <el-button class="filter-item" type="primary" icon="el-icon-close" @click="cancelNotice" style="margin-left: 15px;">
            取消
          </el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
  import qs from 'qs'

  export default {
    props:['notice'],
    data() {
      return {
        notifys:null,
        noticeTypes:[{
          key:'工作动态',
          value:'GZDT'
        },{
          key:'通知公告',
          value:'TZGG'
        },{
          key:'政策解读',
          value:'ZCJD'
        }]
      }
    },
    methods:{
      saveNotice(){
        if(this.notice.noticeSubject==''){
          this.$message({
            type:"error",
            message:"请输入公告标题！"
          })
          return
        }
        if(this.notice.noticeSubject.replace(/[\s\n\t]+/g, '').length > 100){
          this.$message({
            type:"error",
            message:"标题不超过100个字！"
          })
          return
        }
        if(this.notice.noticeContent==''){
          this.$message({
            type:"error",
            message:"请输入公告内容！"
          })
          return
        }
        if(this.notice.noticeType==''){
          this.$message({
            type:"error",
            message:"请选择公告类型！"
          })
          return
        }
        if(this.notice.publishTime==''){
          this.$message({
            type:"error",
            message:"请选择发布时间！"
          })
          return
        }
        if(this.notice.noticeSource!=null){
          if(this.notice.noticeSource.replace(/[\s\n\t]+/g, '').length > 100){
            this.$message({
              type:"error",
              message:"来源不超过100个字！"
            })
            return
          }
        }
        this.$axios.post('saveNotice',this.notice, (r) =>{
          if(r.errCode==200){
            this.$message({
              type:"success",
              message:"保存成功"
            })
            this.sendFlagChange();
          }else{
            this.$message({
              type:"error",
              message:"保存失败"
            })
          }
        })
      },
      publishNotice(){

        if(this.notice.noticeSubject==''){
          this.$message({
            type:"error",
            message:"请输入公告标题！"
          })
          return
        }
        if(this.notice.noticeSubject.replace(/[\s\n\t]+/g, '').length > 100){
          this.$message({
            type:"error",
            message:"标题不超过100个字！"
          })
          return
        }
        if(this.notice.noticeContent==''){
          this.$message({
            type:"error",
            message:"请输入公告内容！"
          })
          return
        }
        if(this.notice.noticeType==''){
          this.$message({
            type:"error",
            message:"请选择公告类型！"
          })
          return
        }
        if(this.notice.publishTime==''){
          this.$message({
            type:"error",
            message:"请选择发布时间！"
          })
          return
        }
        if(this.notice.noticeSource!=null) {
          if (this.notice.noticeSource.replace(/[\s\n\t]+/g, '').length > 100) {
            this.$message({
              type: "error",
              message: "来源不超过100个字！"
            })
            return
          }
        }
        this.$confirm(`您确认保存并发布这篇消息?`, '提示', {
          cancelButtonClass:'btn-custom-cancel',
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=> {
          this.$axios.post('saveAndPublishNotice', this.notice, (r) => {
            if (r.errCode == 200) {
              this.$message({
                type: "success",
                message: "发布成功"
              })
              this.sendFlagChange();
            } else {
              this.$message({
                type: "error",
                message: "发布失败"
              })
            }
          })
        })
      },
      sendFlagChange(){
        this.$emit('changeFlag',false)
      },
      cancelNotice(){
        this.$emit('changeFlag',false)
      },
      changeTitle() {
        const h = this.$createElement;
        var str = this.notice.noticeSubject.replace(/[\s\n\t]+/g, '');
        if (str.length > 100) {
          if(this.notifys!=null){
            this.notifys.close();
          }
          this.notifys=this.$notify({
            title: '提示',
            message: h('i', { style: 'color: red'}, '不超过100个字')
          });
        }
      }
    }
  }

</script>

