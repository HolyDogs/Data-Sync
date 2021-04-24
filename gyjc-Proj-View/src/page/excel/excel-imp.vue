<template>
  <div class="app-container page_02" style="height:calc(100vh - 64px)">
    <div class="upload-wrap flex">
        <div class="upload-box flex">
          <div class="item flex">
            <img src="@/images/upload_01.png" alt="">
            <el-upload
              class="upload-demo"
              action="/gyProj/upload"
              multiple
              :headers="headers"
              :on-success="uploadSuccess"
              accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
              :file-list="fileList"
              >
              <el-button slot="trigger" size="small" icon="el-icon-upload" type="primary" @click="clickUpload">点击上传</el-button>

              <div slot="tip" class="el-upload__tip">只能上传excel文件(xls、xlsx)<br><a type="primary" href="/指标分类XXX-ZBFLXXX" target="_blank">下载模板</a></div>
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

        <el-table-column align="center" label="待定指标数">
          <template slot-scope="{row}">
            <span>{{ row.underminedNum }}</span>
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



    <el-dialog
    v-if="ddTableViewVisible"
    :visible.sync="ddTableViewVisible"
    title="待定指标处理">

    <div class="filter-container">

      <el-button class="filter-item" type="primary" :disabled="this.selectItems.length === 0" icon="el-icon-plus" @click="setNewZb">
        新指标
      </el-button>
    </div>

    <br/>

    <el-table v-loading="listLoading" :data="list" height="500px" :row-key="getRowKey" border fit highlight-current-row
      @selection-change="handleSelectionChange">

      <el-table-column
        type="selection"
        width="55"
        :reserve-selection="true"
      ></el-table-column>

      <el-table-column  align="left" label="excel名">
        <template slot-scope="{row}">
          <span>{{ row.excelName }}</span>
        </template>
      </el-table-column>

      <el-table-column  align="left" label="指标名称">
        <template slot-scope="{row}">
          <span>{{ row.zbmc }}</span>
        </template>
      </el-table-column>

      <el-table-column align="left" label="导入时间">
        <template slot-scope="{row}">
            <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>

      <el-table-column label="选择为旧指标" align="center">
        <template slot-scope="{row,$index}">
          <el-button type="primary" icon="el-icon-plus"  @click="openOldZb(row)">
            旧指标
          </el-button>
        </template>
      </el-table-column>

    </el-table>

    </el-dialog>


    <el-dialog
    v-if="oldzbDialogVisible"
    :visible.sync="oldzbDialogVisible"
    title="选择旧指标"
    align="center"
    append-to-body>


      <div class="filter-container">
        <span>指标名称：</span>
        <el-input v-model="jzbParam.zbquery" placeholder="指标名称" clearable style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />

        <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
          查询
        </el-button>
      </div>

      <br/>
      <!-- overflow滚动条 -->
    <div style="max-height: 500px;overflow: auto;">
      <el-table v-loading="jzblistLoading" :data="jzbList" border fit highlight-current-row style="width: 100%;overflow: auto;">

        <el-table-column align="center" label="指标名称">
          <template slot-scope="{row}">
            <span>{{ row.zbmc }}</span>
          </template>
        </el-table-column>

        <el-table-column width="180px" align="center" label="计量单位">
          <template slot-scope="{row}">
            <span>{{ row.jldw }}</span>
          </template>
        </el-table-column>

        <el-table-column label="作为旧指标导入" align="center">
          <template slot-scope="{row,$index}">
            <el-button type="primary" icon="el-icon-plus"  @click="setOldZb(row)">
              确定
            </el-button>
          </template>
        </el-table-column>

      </el-table>
    </div>
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
    async getUnderminedList() {
      this.listLoading = true;
      var fileNameList = [];
      for (var i = this.fileList.length - 1; i >= 0; i--) {
        var str = this.fileList[i].name;
        fileNameList.push(str.split(".")[0]);
      }
      this.$axios.get('getUnderminedList', {fileNameList:fileNameList + ''}, function(r) {
        me.list = r.data;
        me.listLoading = false;
      });
    },
    async getOldZbList() {
      this.jzblistLoading = true;
      this.$axios.get('getJzbList', {zbmc:me.jzbParam.zbquery}, function(r) {
          me.jzbList = r.data;
          me.jzblistLoading = false;
      });
    },
    async getRecordList() {
      this.recordLoading = true;
      this.$axios.get('getRecordList', {pageNum:me.pageParam.currentPage, excelName: me.excelName}, function(r){
        me.pageParam.total = r.data.total;
        me.recordList = r.data.list;
        me.recordLoading = false;
      })
    },
    getRowKey(row) {
      return row.id;
    },
    uploadSuccess(response, file, fileList) {
      if (fileList.length > 3) {
        //连续上传超过3个excel时，删除前面的两个上传记录，避免上传列表过长
        fileList.splice(0, 2);
      }
      let type="error";
      if (response.errMsg === '1') {
        me.ddTableViewVisible = true;
        me.fileList = fileList;
        me.getUnderminedList();
      } else{
        if(response.errMsg.indexOf("导入成功")>0){
          type="success";
        }
        me.$message({
          type:type,
          showClose: true,
          message: response.errMsg
        });
      }
      if (response.data != null) {

        var titles = response.data.title;
        var contents = response.data.data;
        var tbody = '';
        if ('' === me.uploadBody || null === me.uploadBody) {
          tbody = "<h2 style='width:100%;'>导入成功！本次导入数据如下</h2>";
        }
        tbody += "<table class='tableImp'>";

        tbody += "<tr>";
        for (var j=0;j<titles.length;j++){
          tbody += "<th style='min-width:140px;'>" + titles[j] + "</th>";
        }
        tbody += "</tr>";

        for (var k=0;k<contents.length;k++){
          tbody += "<tr>";
          for (var m=0;m<contents[k].length;m++){
            tbody += "<td style='text-align:left'>" + contents[k][m] + "</td>";
          }
          tbody += "</tr>";
        }

        tbody += "</table>";

        me.uploadBody += tbody;
        document.querySelector('#tableDiv').innerHTML=me.uploadBody;
        //$("#tableDiv").attr("innerHTML", tbody);

      }
    },
    handleSelectionChange(val) {
      this.selectItems = val;
    },
    setNewZb(){
      var zbIds = [];
      for (var i = this.selectItems.length - 1; i >= 0; i--) {
        zbIds.push(this.selectItems[i].id);
      }
      this.$axios.get('xzb', {zbIds:zbIds+''}, function(r) {
        me.$message({
          type:"success",
          message:"新指标增加成功!"
        });
        me.getUnderminedList();
      })
    },
    openOldZb(row) {
      this.oldzbDialogVisible = true;
      this.jzbParam.currentZb = row.id;
      this.jzbParam.currentZbmc = row.zbmc;
      this.getOldZbList();
    },
    setOldZb(row) {
      console.log(row)
      console.log(this.jzbParam.currentZbmc)
      this.$confirm('您是否确认将"'+row.zbmc+'"作为"'+this.jzbParam.currentZbmc+'"的旧指标？', '提示', {
        cancelButtonClass:'btn-custom-cancel',
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
          this.$axios.get('jzb', {currentZb: me.jzbParam.currentZb, jzb:row.zbtjzdId}, function(r){
          me.oldzbDialogVisible = false;
          me.getUnderminedList();
          me.$message({
            type:"success",
            message:"旧指标插入成功"
          });
        })
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
