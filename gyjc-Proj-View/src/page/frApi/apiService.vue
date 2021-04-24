<template>
  <div class="systemManage app-container" style="height:calc(100vh - 64px)">
    <el-container style="height:100%">
      <el-aside width="16.66666%">
        <el-menu
          :default-openeds="['1']"
          :default-active="$route.path"
          class="el-menu-vertical-demo"
          router
          ref="manageTree">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>接口服务管理</span>
            </template>
            <el-menu-item index='/apiService/frView'>
              <i class="el-icon-menu"></i>
              <span slot="title">接口管理</span>
            </el-menu-item>
            <el-menu-item index="/apiService/apiView">
              <i class="el-icon-document"></i>
              <span slot="title">接口监控</span>
            </el-menu-item>
          </el-submenu>
        </el-menu>

      </el-aside>
      <el-main style="height:100%">
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script>

  export default {
    data() {
      return {}
    }
  }

</script>
