<template>
  <div class="app-container page_02" style="height:calc(100vh - 64px)">

    <div class="upload-wrap flex">
        <div class="upload-box flex">
          <div class="item flex">
            <img src="@/images/upload_01.png" alt="">
              <el-upload
                class="upload-demo"
                action="/gyProj/manualUpload"
                multiple
                :headers="headers"
                :on-success="uploadSuccess"
                accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                :file-list="fileList"
                >
                <el-button slot="trigger" size="small" icon="el-icon-upload" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">只能上传excel文件(xls、xlsx)
                  <br><a type="primary" href="/指标分类" target="_blank">下载模板</a>
                </div>
              </el-upload>
            </div>
            <div class="item flex">
              <img src="@/images/upload_02.png" alt="">
              <el-button size="small" type="primary" @click="watchRecord">查看导入记录</el-button>
            </div>
          </div>

          <div class="upload-lsit">
            <div id="tableDiv" style="text-align: center;margin-top: -20%;">
              <span style="
                height: 32px;
                font-size: 22px;
                font-weight: bold;
                font-family: AlibabaPuHuiTiB;
                color: #46494F;
              ">模板说明</span>
              <img src="@/images/hand-imp-intro.png" width="100%">
            </div>
          </div>

          <el-dialog
            v-if="recordVisible"
            :visible.sync="recordVisible"
            title="导入记录"
            align="center"
            append-to-body>

              <div class="filter-container" style="margin:0;padding-top:0">
                <span>Excel名称：</span>
                <el-input v-model="excelName" placeholder="请输入excel名称" clearable style="width: 200px;" class="filter-item" @keyup.enter.native="recordFilter" />

                <el-button class="filter-item" type="primary" icon="el-icon-search" @click="recordFilter" style="margin-left: 15px;">
                  查询
                </el-button>
              </div>

              <br/>

              <el-table v-loading="recordLoading" :data="recordList" height="500px" fit highlight-current-row>

                <el-table-column align="left" label="Excel名称">
                  <template slot-scope="{row}">
                    <span>{{ row.excelName }}</span>
                  </template>
                </el-table-column>

                <el-table-column align="left" label="导入结果">
                  <template slot-scope="{row}">
                    <span>{{ row.result }}</span>
                  </template>
                </el-table-column>

                <el-table-column align="left" label="导入时间">
                  <template slot-scope="{row}">
                    <span>{{ row.importDate }}</span>
                  </template>
                </el-table-column>

                <el-table-column align="center" label="新增指标数">
                  <template slot-scope="{row}">
                    <span>{{ row.insertNum }}</span>
                  </template>
                </el-table-column>

                <el-table-column align="center" label="更新指标数">
                  <template slot-scope="{row}">
                    <span>{{ row.updateNum }}</span>
                  </template>
                </el-table-column>

                <el-table-column width="200px" align="center" label="失败原因">
                  <template slot-scope="{row}">
                    <el-input type="textarea" v-model="row.reason" readonly :autosize="{ minRows: 1, maxRows: 4}"/>
                    <!-- <span>{{ row.reason }}</span> -->
                  </template>
                </el-table-column>

              </el-table>

              <el-pagination
                background
                layout="prev, pager, next"
                v-bind.sync="pageParam"
                @current-change="changePage"
                >
              </el-pagination>

            </el-dialog>
    </div>
  </div>

</template>
<script>
//全局变量me，用来在this改变时调用到其它变量
let me;

export default {
  name: 'excel-imp-hand',
  data() {
    return {
      fileList:[],
      list: [],
      recordVisible: false,
      pageParam:{
        total:10,
        currentPage:1
      },
      excelName:'',
      recordList:[]
    }
  },
  created() {
    me = this;
  },
  computed:{
    headers: function(){
      return {
        "Authorization": this.$cookie.getCookie('token')
      };
    }
  },
  methods: {
    uploadSuccess(response, file, fileList) {
      if (fileList.length > 3) {
        //连续上传超过3个excel时，删除前面的两个上传记录，避免上传列表过长
        fileList.splice(0, 2);
      }
      if (response.errCode === 204) {
        this.$message({
          type: 'error',
          message: response.errMsg,
          duration: 8000
        });
        return;
      }
      this.$message({
          type:'success',
          message:'上传成功'
        }
      );
    },
    async getRecordList() {
      this.recordLoading = true;
      this.$axios.get('getHandRecordList', {pageNum:me.pageParam.currentPage, excelName: me.excelName}, function(r){
        me.pageParam.total = r.data.total;
        me.recordList = r.data.list;
        me.recordLoading = false;
      })
    },
    handleFilter() {
      this.getOldZbList();
    },
    changePage() {
      this.getRecordList();
    },
    recordFilter() {
      this.pageParam.currentPage = 1;
      this.getRecordList();
    },
    watchRecord() {
      this.recordVisible = true;
      this.getRecordList();
    }
  }
}
</script>

<style type="text/css">
.el-upload-list__item-name:hover {
  color: black !important;
  text-decoration: none !important;
  cursor: default !important;
/*  position: relative;
  left: 1px;
  top: 1px;*/
}
</style>