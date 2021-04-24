<template>
  <div class="app-container page_02" style="height:calc(100vh - 64px)">
    <div class="upload-wrap flex">
        <div class="upload-box flex">
          <div class="item flex">
            <img src="@/images/upload_01.png" alt="">
            <el-upload
              class="upload-demo"
              action="/gyProj/trapUpload"
              multiple
              :headers="headers"
              :on-success="uploadSuccess"
              :on-progress="showMessage"
              accept=".csv"
              :file-list="fileList"
              >
              <el-button slot="trigger" size="small" icon="el-icon-upload" type="primary" @click="clickUpload">点击上传</el-button>

              <div slot="tip" class="el-upload__tip">只能上传excel CSV文件<br><a type="primary" href="/static/威胁诱捕数据.csv" target="_blank">下载模板</a></div>
            </el-upload>
          </div>
          <div class="item flex">
            <img src="@/images/upload_02.png" alt="">
            <el-button size="small" type="primary" @click="watchRecord">查看导入记录</el-button>
          </div>
        </div>
        <div class="upload-lsit">
          <div id="tableDiv" style="text-align: center;margin-top: -21px;max-height: 500px;overflow: auto;"></div>
        </div>
    </div>

    <el-dialog
    v-if="recordVisible"
    :visible.sync="recordVisible"
    title="导入记录"
    align="center"
    append-to-body>


      <div class="filter-container" style="margin:0;padding-top:0">
        <span>excel名称：</span>
        <el-input v-model="excelName" placeholder="请输入Excel名称" clearable style="width: 200px;" class="filter-item" @keyup.enter.native="recordFilter" />

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

        <el-table-column width="200px" align="left" label="导入结果">
          <template slot-scope="{row}">
            <span>{{ row.result }}</span>
          </template>
        </el-table-column>

        <el-table-column align="left" label="导入时间">
          <template slot-scope="{row}">
            <span>{{ row.importDate.substring(0,10) }}</span>
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
</template>
<script>
//全局变量me，用来在this改变时调用到其它变量
let me;

export default {
  name: 'excel-imp',
  data() {
    return {
      showMessageFlag:true,
      fileList:[],
      ddTableViewVisible: false,
      listLoading: false,
      jzblistLoading: false,
      oldzbDialogVisible: false,
      recordVisible: false,
      list: [],
      jzbList: [],
      recordList: [],
      selectItems: [],
      jzbParam:{
        zbquery:'',
        currentZb: '',
        currentZbmc:''
      },
      excelName:'',
      pageParam:{
        currentPage:1,
        total:10
      },
      uploadBody: ''
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
    async getRecordList() {
      this.recordLoading = true;
      this.$axios.get('getTrapLogRecord', {pageNum:me.pageParam.currentPage, excelName: me.excelName}, function(r){
        me.pageParam.total = r.data.total;
        me.recordList = r.data.list;
        me.recordLoading = false;
      })
    },
    showMessage(){
      if(this.showMessageFlag){
        this.$message({
          type: 'success',
          message: '文件正在上传中，请稍后...'
        });
        this.showMessageFlag = false;
      }
    },
    uploadSuccess(response, file, fileList) {
      this.showMessageFlag = true;
      if (fileList.length > 3) {
        //连续上传超过3个excel时，删除前面的两个上传记录，避免上传列表过长
        fileList.splice(0, 2);
      }
      let type="error";
      if (response.errCode === 500) {
        me.$message({
          type: 'error',
          message: '数据导入失败'
        });
      }
      if (response.errCode === 204) {
        me.$message({
          type: 'error',
          message: '有其他数据正在导入'
        });
      }
      if (response.errCode === 200) {
        me.$message({
          type: 'success',
          message: '数据导入成功'
        });
      }
    },
    handleSelectionChange(val) {
      this.selectItems = val;
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
    },
    clickUpload() {
      this.uploadBody = '';
      document.querySelector('#tableDiv').innerHTML=this.uploadBody;
    }
  }
}
</script>

<style type="text/css">
      .tableImp{
     width: 100%;
    }
    .tableImp{
      border: 1px solid #E2E2E2;
      color: #7A7A7A;
    }
    .tableImp th {
      background: #F5F8FF;;
      color: #444444;
      line-height: 50px;
      padding-left: 15px;
    }
    .tableImp td{
      background: #FFF;;
      color: #7A7A7A;
      line-height: 50px;
      padding-left: 15px;
     border-bottom:1px solid #E2E2E2;
    }

    .el-upload-list__item-name:hover {
  color: black !important;
  text-decoration: none !important;
  cursor: default !important;
/*  position: relative;
  left: 1px;
  top: 1px;*/
}

</style>
