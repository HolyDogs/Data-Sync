<template>
	<div>
    <div>
      <el-row :gutter="0">
        <el-col :span="4">
          <div class="logo">工业大数据融合平台</div>
        </el-col>
        <el-col :span="20">
          <div class="menu-wrap flex juct-sb">
            <div class="menu-toggle" style="visibility: hidden"></div>
            <div class="flex juct-sb">
              <el-menu :default-active="this.$route.path" class="el-menu-demo" mode="horizontal" router>
                <div v-for="(item,i) in navList" :key="i" style="display: inline;float: left;" >
                  <el-submenu :index="item.name" v-if="item.children && item.children.length > 0">
                    <template slot="title">
                      {{item.navItem}}
                    </template>
                    <template >
                      <el-menu-item v-for="(child,index) in item.children" :index="item.name+child.path" :key="index">
                          <span>{{child.name}}</span>
                      </el-menu-item>
                    </template>
                  </el-submenu>
                  <el-menu-item v-else :key="i" :index="item.name">
                      {{ item.navItem }}
                  </el-menu-item>
                </div>

              </el-menu>
              <div class="sys-out flex">
                <div class="user-name">
                  欢迎您！
                  <span>{{this.$cookie.getCookie('username')}}</span>
                </div>
                <span @click="logout"> 退出</span>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>

		<router-view></router-view>

	</div>
</template>


<script type="text/javascript">
    export default{
        data(){
            return{
                routes:'',
                navList:[
                	{name:'/', navItem:'首页'},
                  {name:'/inlineTable', navItem:'指标池管理'},
                	{name:'/newZbTable', navItem:'创建主题'},
                	{name:'/tableManage', navItem:'主题库'},
                  {name:'/zsTableManage', navItem:'展示库'},
                  {name:'/apiService', navItem:'接口服务管理'},
                  {name:'/dataSource', navItem:'数据中心', children:[
                      {path:'/ywdatamove', name:'工业运行监测'},
                      {path:'/statisticData', name:'国家数据网'},
                      {path:'/secureData', name:'安全态势数据'},
                      {path:'/handImpData', name:'手动导入'},
                      {path:'/basestation', name:'其他数据'},
                    ]
                  },
                  {name:'/dataTrans', navItem:'数据传输'}
                ]
            }
        },
        created(){
            if(this.$cookie.getCookie('type')=='1'){
              this.navList=[
                {name:'/', navItem:'首页'},
                {name:'/inlineTable', navItem:'指标池管理'},
                {name:'/newZbTable', navItem:'创建主题'},
                {name:'/tableManage', navItem:'主题库'},
                {name:'/zsTableManage', navItem:'展示库'},
                {name:'/apiService', navItem:'接口服务管理'},
                {name:'/dataSource', navItem:'数据中心', children:[
                  {path:'/ywdatamove', name:'工业运行监测'},
                  {path:'/statisticData', name:'国家数据网'},
                  {path:'/secureData', name:'安全态势数据'},
                  {path:'/handImpData', name:'手动导入'},
                  {path:'/basestation', name:'其他数据'},
                ]
                },
                {name:'/dataTrans', navItem:'数据传输'},
                {name:'/systemManage', navItem:'系统管理'}
              ]
            }
        },
        mounted(){
            this.routes = this.$router.options.routes;
        },
        methods:{
            logout() {
                this.$confirm('确定要退出登录吗？', '确认信息', {
                    cancelButtonClass:'btn-custom-cancel',
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    //点击确认
                    this.$cookie.setCookie('token', '', -1);
                    this.$cookie.setCookie('type', '', -1);
                    this.$router.push({path:'/login'});
                }).catch();
            }
        }
    }
</script>
<style type="text/css" src="@/style/reset.css"></style>
<style type="text/css" src="@/style/style.css"></style>

<style type="text/css">
   .el-menu--horizontal .el-menu-item:focus{
     color: #f7f7f7;
   }

</style>
