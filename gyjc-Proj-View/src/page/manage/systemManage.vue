<template>
  <div class="systemManage app-container" style="height:calc(100vh - 64px)">
    <el-container>
      <el-aside width="16.66666%">
        <el-menu
          :default-active="$route.path"
          class="el-menu-v"
          router
          ref="manageTree">
          <el-submenu index="1">
            <template class="flex" slot="title">
              <img src="@/images/menu_icon.png" alt="">
              <span>系统管理</span>
            </template>
            <el-menu-item index='/systemManage/userManage'>
              <span slot="title">用户管理</span>
            </el-menu-item>
            <el-menu-item index="/systemManage/logManage">
              <span slot="title">日志管理</span>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template class="flex" slot="title">
              <img src="@/images/menu_icon.png" alt="">
              <span>消息发布</span>
            </template>
            <el-menu-item index="/systemManage/noticeManage/GZDT">
              <span slot="title">工作动态</span>
            </el-menu-item>
            <el-menu-item index="/systemManage/noticeManage/TZGG">
              <span slot="title">通知公告</span>
            </el-menu-item>
            <el-menu-item index="/systemManage/noticeManage/ZCJD">
              <span slot="title">政策解读</span>
            </el-menu-item>
          </el-submenu>
        </el-menu>

      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script>

  export default {
    data() {
      return {
      }
    },
    methods:{
    },
    watch:{
      $route:function(value){
        if(value.path=='/systemManage'){

        }
      }
    }
  }

</script>
