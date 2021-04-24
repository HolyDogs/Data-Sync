<template>
  <div class="login">
     <div class="theform reg">
        <div class="loginText">工业大数据融合平台</div>
        <el-form class="loginform" :label-position="labelPosition" :rules="rules" ref="regisform" label-width="0px" :model="rform">
          <div class="form-title" style="margin-bottom:0">注册</div>
          <el-form-item prop="name">
            <el-input prefix-icon="el-icon-user" autoComplete="off" placeholder="账号" v-model="rform.name" @keydown.tab.native="inputSpace" @keydown.space.native="inputSpace"></el-input>
            <el-input style="position: fixed;bottom: -9999px" ></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input style="position: fixed;bottom: -9999px" type="password"></el-input>
            <el-input prefix-icon="el-icon-lock" placeholder="密码" autoComplete="new-password" type="password" @keydown.tab.native="inputSpace" @keydown.space.native="inputSpace" v-model="rform.password"></el-input>
          </el-form-item>
          <el-form-item prop="cpassword">
            <el-input prefix-icon="el-icon-lock" placeholder="请再次输入密码" @keydown.space.native="inputSpace" type="password" v-model="rform.cpassword"></el-input>
          </el-form-item>
          <el-form-item class="code" prop="identifyCode">
            <el-row :span="24">
              <el-col :span="15">
                  <el-input placeholder="输入验证码" @keydown.space.native="inputSpace" v-model="rform.identifyCode" type="text" />
              </el-col>
              <el-col :span="8">
                <div @click="refreshCode">
                    <sidentify :identifyCode="identifyCode" />
                </div>
              </el-col>
            </el-row>
          </el-form-item>
          <el-form-item>
            <!-- <el-button type="primary" @click="tologin" plain>返回登录</el-button> -->
            <el-button type="success" @click="register" plain>确认注册</el-button>
          </el-form-item>
          <div class="form-foot">
              已有账号？ 请<span @click="tologin">登录</span>
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
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else if(value.length < 6 || value.length > 20) {
        callback(new Error('密码长度在6到20个字符之间'))
      } else if(this.checkPass(value) < 3) {
        callback(new Error('密码复杂度过低，需同时包含大小写字母和数字'))
      } else {
        callback()
      }
    };
    var validateCpass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else{
        if (value !== this.rform.password) {
          callback(new Error('两次输入密码不一致'))
        } else{
          callback();
        }
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
       //send:"发送验证码",
       //register:"注册",
       //loginmessage:"已有账号?登陆",
       pflag:false,
       sflag:false,
       rform:{
         name:"",
         password:"",
         cpassword:"",
         identifyCode:""
       },

       theight:'',
       labelPosition:'right',
       identifyCode:'',
       identifyCodes: '1234567890',
       rules:{
        name:[
            {validator:validateName, trigger: 'blur'}
        ],
        password:[
            {validator:validatePass, trigger: 'blur'}
        ],
        cpassword:[
            {validator:validateCpass, trigger: 'blur'}
        ],
        identifyCode:[
            {validator:validateCode, trigger: 'blur'}
        ]
       }
    }
  },
  created() {
     this.theight=window.innerHeight+'px';
  },
  mounted(){
      this.refreshCode();
  },
  methods: {
      tologin:function(){
        this.$router.push({path:'/login'});
      },
      register:function() {
        let me = this;
        let param=qs.stringify(this.rform);
        //进行规则校验
        this.$refs['regisform'].validate((valid) => {
          if (valid) {
            //校验成功
            this.$axios.post('register', param, function(r){
              if (r.errCode === 201) {
                me.$message({
                  type:"error",
                  message:r.errMsg
                });
                me.rform.identifyCode='';
                me.refreshCode();
              } else{
                me.$message({
                  type:"success",
                  message:"注册成功！"
                });
                me.refreshCode();
                me.$router.push({path:'/login'});
              }
            })

          }else{
            me.rform.identifyCode='';
            //校验失败
            this.refreshCode();
            return false;
          }
        });

      },
      changePw:function(){
        var reg=/[a-zA-Z0-9]{3,8}$/;
        if(!reg.test(this.rform.password)){
          this.pflag=true;
          return;
        }
        this.pflag=false;
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
      checkPass(pass){
        var str = 0;
        if(pass.match(/([a-z])+/)){
            str++;
        }
        if(pass.match(/([0-9])+/)){
            str++;
        }
        if(pass.match(/([A-Z])+/)){
            str++;
        }
        return str;
    },
    inputSpace(event) {
      event.returnValue = false;
    }
  }
}
</script>

<style type="text/css" src="@/style/login.css"></style>
