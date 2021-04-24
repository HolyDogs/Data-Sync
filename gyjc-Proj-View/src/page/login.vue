<template>
    <div class="login">
        <div class="theform">
          <div class="loginText">工业大数据融合平台</div>
          <el-form class="loginform"  :label-position="labelPosition" :rules="rules" label-width="0px" ref="loginform" :model="theform">
            <div class="form-title">登录</div>
            <el-form-item prop="name">
              <el-input prefix-icon="el-icon-user" placeholder="账号" width="200px" @keydown.space.native="inputSpace" v-model="theform.name"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input prefix-icon="el-icon-lock" type="password" placeholder="密码" @keydown.space.native="inputSpace" v-model="theform.password"></el-input>
            </el-form-item>
            <el-form-item class="code" prop="identifyCode">
              <el-row :span="24">
                <el-col :span="15">
                    <el-input placeholder="输入验证码" @keydown.space.native="inputSpace" @keyup.enter.native="login" v-model="theform.identifyCode" type="text" />
                </el-col>
                <el-col :span="8">
                  <div @click="refreshCode">
                      <sidentify :identifyCode="identifyCode" />
                  </div>
                </el-col>
              </el-row>
            </el-form-item>
            <el-form-item>
              <el-button type="success" @click="login" plain>{{loginmessage}}</el-button>
              <!-- <el-button type="primary" @click="toRegister" plain>{{register}}</el-button> -->
            </el-form-item>
            <div class="form-foot">
              还没有账号？ 请<span @click="toRegister">注册</span>
            </div>
          </el-form>
        </div>
    </div>
</template>

<script>
import qs from 'qs'
import sidentify from '@/components/sidentify'

export default {
  components: { sidentify },
  data() {
    var validateName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入账号'))
      } else if (value.length < 3 || value.length > 20) {
        callback(new Error('账号长度在3到20个字符之间'))
      } else if (value.match(/[^a-zA-Z0-9]+/)) {
        callback(new Error('请勿输入特殊字符'))
      } else {
        callback();
      }
    };
    var validateCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入验证码'));
      } else{
        if (value !== this.identifyCode) {
            callback(new Error('验证码输入错误'));
            //this.refreshCode();
        }else{
          callback();
        }
      }
    };
    return {
      labelPosition:"right",
       loginmessage:"登录",
       theform:{
          name:"",
          password:"",
          identifyCode:""
       },
       pflag:false,
       theight:'',
       identifyCode:'',
       identifyCodes: '1234567890',
       rules: {
          name: [
            {validator:validateName, trigger: 'blur'}
          ],
          password: [
            {required: true,message:'请输入密码',trigger: 'blur'}
          ],
          identifyCode: [
            {validator:validateCode, trigger: 'blur'}
          ]
       }
    };

  },
  created() {
     this.theight=window.innerHeight+'px';
  },
  mounted(){
      this.refreshCode();
  },
  methods: {
      toRegister:function(){
        this.$router.push({path:'/register'});
      },
      login:function(){

        this.$refs['loginform'].validate((valid) => {
          if (valid) {
            this.$cookie.setCookie('type', '', -1);
            this.$cookie.setCookie('token', '', -1);
            //localStorage.removeItem('token');
            let me=this;
            let params=qs.stringify(this.theform);
            this.$axios.post('login',params,function(r){
              if(r.errCode === 201){
                me.$message({
                  type:"error",
                  message:r.errMsg
                });
                me.$router.push({path:'/login'});
                me.refreshCode();
                me.theform.password='';
                me.theform.identifyCode='';
                me.$refs.inputPassword.focus();
              }else{
                //me.state.login(r.name,r.ismanager,r.email);
                //me.$cookie.setCookie('token',r.data);
                if (r.data.type == 1) {
                  me.$cookie.setCookie('type', r.data.type);
                }
                me.$cookie.setCookie('username', me.theform.name);
                me.$cookie.setCookie('fullname', r.data.name);
                me.$router.push({path:'/'});
              }
            });
          }else{
            this.theform.identifyCode='';
            this.refreshCode();
            return false;
          }
        });
      },
      refreshCode(){
        this.identifyCode = '';
        this.makeCode(this.identifyCodes, 4)
      },
      makeCode(o, l) {
        for (var i = 0; i < l; i++) {
          this.identifyCode += this.identifyCodes[this.randomNum(0, this.identifyCodes.length)]
        }
      },
      randomNum(min, max) {
        return Math.floor(Math.random() * (max-min) + min)
      },
      inputSpace(event) {
        event.returnValue = false;
      }
  }
}
</script>

<style type="text/css" src="@/style/login.css"></style>
