<template>
  <div class="container">
    <myheader :name="pageName"></myheader>
    <div class="filter-container">
      <span>用户名：</span>
      <el-input v-model="searchName" placeholder="请输入用户名" style="width: 130px;" clearable  @keyup.enter.native="searchUser" />
      <span style="margin-left: 15px;">账号：</span>
      <el-input v-model="searchLoginName" placeholder="请输入账号" style="width: 130px;" clearable @keyup.enter.native="searchUser" />
      <span style="margin-left: 15px;">手机号：</span>
      <el-input v-model="searchPhone" placeholder="请输入手机号" style="width: 130px;" clearable @keyup.enter.native="searchUser" />
      <!-- 防止修改密码自动填充 -->
      <el-input style="position: fixed;bottom: -9999px" ></el-input>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="searchUser" style="margin-left: 15px;">
        查询
      </el-button>
      <el-button class="filter-item" icon="el-icon-plus" type="primary" @click="createUser" style="margin-left: 15px;">
        新增用户
      </el-button>
    </div>
    <div class="systable-wrap" style="padding:15px 15px 0 15px;height:calc(100vh - 285px);background:#fff">
      <el-table v-loading="loading" :data="list" height="100%" stripe fit highlight-current-row >

        <el-table-column label="用户名" min-width="30%" align="center">
          <template slot-scope="{row}">
            <span>{{row.fullName}}</span>
          </template>
        </el-table-column>

        <el-table-column label="账号" min-width="30%" align="center">
          <template slot-scope="{row}">
            <span>{{row.loginName}}</span>
          </template>
        </el-table-column>

        <el-table-column label="性别" min-width="15%"  align="center">
          <template slot-scope="{row}">
            <span v-if="row.sex==1">男</span>
            <span v-if="row.sex==0">女</span>
          </template>
        </el-table-column>

        <el-table-column label="手机号" min-width="30%" align="center">
          <template slot-scope="{row}">
            <span>{{row.phone}}</span>
          </template>
        </el-table-column>

        <el-table-column label="邮箱" min-width="35%" align="center">
          <template slot-scope="{row}">
            <span>{{row.email}}</span>
          </template>
        </el-table-column>

        <el-table-column label="创建时间" min-width="25%" align="center">
          <template slot-scope="{row}">
            <span>{{row.createTime.substring(0,10)}}</span>
          </template>
        </el-table-column>

        <el-table-column label="操作" align="center" min-width="60%">
          <template slot-scope="{row}">
            <el-button type="primary" size="small" @click="updateUser(row)">
              修改信息
            </el-button>
            <el-button type="primary" size="small" @click="deleteUser(row)" v-if="row.isDelete=='0'">
              禁用
            </el-button>
            <el-button type="primary" size="small" @click="undeleteUser(row)" v-if="row.isDelete=='1'">
              启用
            </el-button>
            <el-button type="primary" size="small" @click="updatePassword(row)">
              修改密码
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog
      :title="infoTitle"
      :visible.sync="userInfoVisible"
      width="768px"
      v-if="userInfoVisible">
        <el-form  style="margin-bottom: -40px" label-position="right" ref="userInfoRef" label-width="80px" :rules="userRule" :model="userInfoForm">
          <el-row>
            <el-col :span="12">
              <el-form-item prop="loginName" label="账号:">
                <el-input v-model="userInfoForm.loginName" style="width:260px;" autoComplete="off" placeholder="请输入账号"  :disabled="!createUserDialog" ></el-input>
                <el-input style="position: fixed;bottom: -9999px" :disabled="!createUserDialog" ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="userName" label="用户名:">
                <el-input v-model="userInfoForm.userName" placeholder="请输入用户名" style="width: 260px;" clearable ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item prop="userPassword" label="密码:" v-if="createUserDialog">
                <el-input style="position: fixed;bottom: -9999px" :disabled="!createUserDialog" type="password"></el-input>
                <el-input prefix-icon="el-icon-lock" style="width:260px;" placeholder="请输入密码" autoComplete="new-password" type="password" @keydown.space.native="inputSpace" v-model="userInfoForm.userPassword" ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="cpassword2" label="确认密码:" v-if="createUserDialog">
                <el-input prefix-icon="el-icon-lock" style="width:260px;" placeholder="请再次输入密码" @keydown.space.native="inputSpace" type="password" v-model="userInfoForm.cpassword2"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="性别:">
                <el-select v-model="userInfoForm.userSex" style="width:260px;" clearable>
                  <el-option v-for="item in mainSex"
                             :key="item.key"
                             :label="item.label"
                             :value="item.key">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="userPhone" label="手机号:">
                <el-input v-model="userInfoForm.userPhone" style="width:260px;" clearable></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item prop="userEmail" label="邮箱:">
                <el-input v-model="userInfoForm.userEmail" style="width:260px;" clearable></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateUserInfo">确 定</el-button>
        <el-button @click="userInfoVisible = false">取 消</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="修改密码"
      :visible.sync="passwordVisible"
      width="400px"
      v-if="passwordVisible"
      >
        <el-form label-position="right"  label-width="80px" :rules="rules" ref="passFormRef" :model="passForm">
          <el-form-item prop="oldPassword"  label="原密码">
            <!-- 防止自动填充表单-->
            <el-input style="position: fixed;bottom: -9999px" type="password"></el-input>
            <el-input prefix-icon="el-icon-lock" placeholder="请输入原密码" type="password" @keydown.space.native="inputSpace" v-model="passForm.oldPassword"></el-input>
          </el-form-item>
          <el-form-item prop="newPassword" label="新密码">
            <el-input prefix-icon="el-icon-lock" placeholder="请输入新密码" type="password" @keydown.space.native="inputSpace" v-model="passForm.newPassword"></el-input>
          </el-form-item>
          <el-form-item prop="cpassword" label="确认密码">
            <el-input prefix-icon="el-icon-lock" placeholder="请再次输入新密码" @keydown.space.native="inputSpace" type="password" v-model="passForm.cpassword"></el-input>
          </el-form-item>
        </el-form>

        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="sendUpdatePassword">确 定</el-button>
          <el-button @click="passwordVisible = false">取 消</el-button>
        </span>
    </el-dialog>

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
import qs from 'qs'
import myheader from '@/components/header'
  export default {
    components:{myheader},
    data(){
      var validateOriginPass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入原密码'));
        } else if(value.length < 6 || value.length > 20) {
          callback(new Error('密码长度在6到20个字符之间'))
        } else if(this.checkPass(value) < 3) {
          callback(new Error('密码格式不符，需同时包含大小写字母和数字'))
        } else {
          callback()
        }
      };
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入新密码'));
        } else if(value.length < 6 || value.length > 20) {
          callback(new Error('密码长度在6到20个字符之间'))
        } else if(this.checkPass(value) < 3) {
          callback(new Error('密码格式不符，需同时包含大小写字母和数字'))
        } else {
          callback()
        }
      };
      var validateCpass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'))
        } else{
          if (value !== this.passForm.newPassword) {
            callback(new Error('两次输入密码不一致'))
          } else{
            callback();
          }
        }
      };
      var validateCUserpass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'))
        } else{
          if (value !== this.userInfoForm.userPassword) {
            callback(new Error('两次输入密码不一致'))
          } else{
            callback();
          }
        }
      };

      var validateUserPass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else if(value.length < 6 || value.length > 20) {
          callback(new Error('密码长度在6到20个字符之间'))
        } else if(this.checkPass(value) < 3) {
          callback(new Error('密码格式不符，需同时包含大小写字母和数字'))
        } else {
          callback()
        }
      };
      var validateUserPhone = (rule, value, callback) => {
        const regMobile = /^(0|86|17951)?(13[0-9]|14[5678]|15[012356789]|16[6]|17[678]|18[0-9]|19[89])[0-9]{8}$/;
        if (value!=null && value!='' && !regMobile.test(value)) {
          callback(new Error('手机号码格式不正确'))
        }else{
          callback();
        }
      };
      var validateEmail = (rule, value, callback) => {
        const regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
        if (value!=null && value!= '' && !regEmail.test(value)) {
          callback(new Error('邮箱格式不正确'))
        }else{
          callback();
        }
      };
      var validateLoginName = (rule, value, callback) => {
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
      var validateName = (rule, value, callback) => {
        if (value==null||value.trim() === '') {
          callback(new Error('请输入用户名'))
        } else if(value.length > 20){
          callback(new Error('用户名不能超过20个字符'))
        } else{
          callback();
        }
      };
      return{
        pageName:'用户管理',
        list:[],
        loading:false,
        pageParam:{
          currentPage:1,
          total:10,
        },
        userInfoVisible:false,
        passwordVisible:false,
        searchName:'',
        searchLoginName:'',
        searchPhone:'',
        mainSex:[{label:'男',key:1},{label:'女',key:0}],
        infoTitle:'修改信息',
        userInfoForm:{
          userId:'',
          userName:'',
          loginName:'',
          userSex:'',
          userPhone:'',
          userEmail:'',
          userPassword:'',
          cpassword2:''
        },
        createUserDialog:false,
        passForm:{
          loginName:'',
          oldPassword:'',
          newPassword:'',
          cpassword:''
        },
        rules:{
          oldPassword:[
              {validator:validateOriginPass, trigger: 'blur'}
          ],
          newPassword:[
              {validator:validatePass, trigger: 'blur'}
          ],
          cpassword:[
              {validator:validateCpass, trigger: 'blur'}
          ],
        },
        userRule:{
          cpassword2:[
            {validator:validateCUserpass, trigger: 'blur'}
          ],
          userPhone:[
            {validator:validateUserPhone, trigger: 'blur'}
          ],
          userEmail:[
            {validator:validateEmail, trigger: 'blur'}
          ],
          loginName:[
            {validator:validateLoginName, trigger: 'blur'}
          ],
          userName:[
            {validator:validateName, trigger: 'blur'}
          ],
          userPassword:[
            {validator:validateUserPass, trigger: 'blur'}
          ],
        },
      }
    },
    created(){
      this.getData(1);
    },
    methods:{
      async getData(pageNum,searchName,searchLoginName,searchPhone){
        let me = this;
        me.loading=true;
        this.$axios.get('getUserInfo',{pageNum: pageNum,searchName:searchName,searchLoginName:searchLoginName,searchPhone:searchPhone}, function(r){
          me.loading=false;
          me.list=r.data.list;
          me.pageParam.total=r.data.total;
        })
      },
      changePage(){
        this.personVisible=false;
        this.getData(this.pageParam.currentPage,this.searchName,this.searchLoginName,this.searchPhone);
      },
      updateUser(row){
        this.userInfoForm.userId=row.id;
        this.userInfoForm.userName=row.fullName;
        this.userInfoForm.loginName=row.loginName;
        this.userInfoForm.userSex=row.sex;
        this.userInfoForm.userPhone=row.phone;
        this.userInfoForm.userEmail=row.email;
        this.infoTitle="修改信息";
        this.createUserDialog=false;
        this.userInfoVisible=true;
      },
      deleteUser(row){
        let me=this;
        let userId=row.id;
        this.$confirm('您是否确认禁用该用户？', '提示', {
          cancelButtonClass:'btn-custom-cancel',
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.post('deleteUser', {userId}, function (r) {
            me.getData(me.pageParam.currentPage);
            let alertType='success';
            if(r.errCode==504){
              alertType='error'
            }
            me.$message({
              type: alertType,
              message: r.errMsg
            });
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消禁用'
          });
        });
      },
      undeleteUser(row){
        let me=this;
        let userId=row.id;
        this.$confirm('您是否确认启用该用户？', '提示', {
          cancelButtonClass:'btn-custom-cancel',
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.post('undeleteUser', {userId}, function (r) {
            me.getData(me.pageParam.currentPage);
            let alertType='success';
            if(r.errCode==504){
              alertType='error'
            }
            me.$message({
              type: alertType,
              message: r.errMsg
            });
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消启用'
          });
        });
      },
      searchUser(){
        this.pageParam.currentPage=1;
        this.getData(this.pageParam.currentPage,this.searchName,this.searchLoginName,this.searchPhone)
      },
      updateUserInfo(){
        let userId=this.userInfoForm.userId
        let userName=this.userInfoForm.userName
        let userSex=this.userInfoForm.userSex
        let userPhone=this.userInfoForm.userPhone
        let userEmail=this.userInfoForm.userEmail
        let loginName=this.userInfoForm.loginName

        this.$refs['userInfoRef'].validate((valid) => {
          if (!valid) {
            //校验失败
            me.$message({
              type:'error',
              message:r.errMsg
            })
            return
          }
        });

        let me=this
        let password=this.userInfoForm.userPassword;
        //是否为创建用户
        let create='0'
        if(this.createUserDialog){
          create='1'
        }
        this.$axios.post('updateUser', {loginName,userId,userName,userSex,userPhone,userEmail,create,password}, function (r) {
          me.userInfoVisible=false;
          me.getData(me.pageParam.currentPage);
          let alertType='success';
          if(r.errCode==201){
            alertType='error'
          }
          me.$message({
            type: alertType,
            message: r.errMsg
          });
        })
      },
      createUser(){
        this.userInfoForm.loginName='';
        this.userInfoForm.userId='';
        this.userInfoForm.userName='';
        this.userInfoForm.userSex='';
        this.userInfoForm.userPhone='';
        this.userInfoForm.userEmail='';
        this.userInfoForm.cpassword2='';
        this.userInfoForm.userPassword='';
        this.infoTitle="新增用户"
        this.createUserDialog=true;
        this.userInfoVisible=true;
      },
      updatePassword(row) {
        this.passForm.cpassword='';
        this.passForm.oldPassword='';
        this.passForm.newPassword='';
        this.passForm.loginName=row.loginName;
        this.passwordVisible = true;
      },
      sendUpdatePassword() {
        let me = this;
        let param=qs.stringify(this.passForm);
        //进行规则校验
        this.$refs['passFormRef'].validate((valid) => {

          if (valid) {
            //校验成功
            me.$axios.post('updatePassword', param, function(r) {
                if(r.errMsg=='更新成功'){
                  me.$message({
                    type:'success',
                    message:r.errMsg
                  });
                  me.passwordVisible=false;
                }else{
                  me.$message({
                    type:'error',
                    message:r.errMsg
                  })
                }
            });
          }
        });
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
    },
  }

</script>

