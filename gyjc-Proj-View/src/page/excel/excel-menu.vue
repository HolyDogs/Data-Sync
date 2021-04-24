<template>
  <div class="systemManage app-container" style="height:calc(100vh - 64px)">
    <el-container>
      <el-aside width="16.66666%">
        <el-menu
          :default-openeds="['1']"
          :default-active="$route.path"
          class="el-menu-vertical-demo"
          router
          ref="manageTree">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-s-data"></i>
              <span>数据传输</span>
            </template>
            <el-menu-item index='/dataTrans/excelImp'>
              <i></i>
              <span slot="title">国家数据网导入</span>
            </el-menu-item>
            <el-menu-item index="/dataTrans/handImport">
              <i></i>
              <span slot="title">外部数据导入</span>
            </el-menu-item>
            <el-menu-item index="/dataTrans/trapLog">
              <i></i>
              <span slot="title">威胁诱捕数据导入</span>
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
