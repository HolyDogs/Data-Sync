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
            <template slot="title">
               <img src="@/images/menu_icon.png" alt="">
              <span>卫星地球站与5G基站</span>
            </template>
            <el-menu-item index='/dataSource/basestation/earthStation'>
              <span slot="title">卫星地球站数据</span>
            </el-menu-item>
            <el-menu-item index="/dataSource/basestation/useSchedule">
              <span slot="title">5G基站使用进度数据</span>
            </el-menu-item>
            <el-menu-item index="/dataSource/basestation/coordinationSchedule">
              <span slot="title">5G基站协调进度数据</span>
            </el-menu-item>
          </el-submenu>

          <el-submenu index="2">
            <template slot="title">
               <img src="@/images/menu_icon.png" alt="">
              <span>特色工业指标</span>
            </template>
            <el-menu-item index='/dataSource/basestation/tsgyzb'>
              <span slot="title">特色工业指标</span>
            </el-menu-item>
          </el-submenu>

          <!-- <el-submenu index="3">
            <template slot="title">
              <img src="@/images/menu_icon.png" alt="">
              <span>安全态势感知</span>
            </template>
            <el-menu-item index='/dataSource/basestation/activeConnect'>
              <span slot="title">工业互联网协议通联记录</span>
            </el-menu-item>
          </el-submenu> -->

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
  }

</script>
