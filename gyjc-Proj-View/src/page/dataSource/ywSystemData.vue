<template>
  <div class="app-container page_02" align="center" style="height:calc(100vh - 64px)">
    <myheader :name="pageName"></myheader>
    <div class="filter-container">
      <el-row>
          <span>指标分类：</span>
          <el-select style="width:180px;" v-model="listQuery.lyid" filterable clearable placeholder="请选择">
            <el-option
              v-for="item in zbflList"
              :key="item.LABEL"
              :label="item.LABEL"
              :value="item.VALUE">
            </el-option>
          </el-select>
            <span style="margin-left: 15px;">指标名称：</span>
            <el-input v-model="listQuery.zbmc" placeholder="请输入指标名称" clearable style="width: 180px;" class="filter-item" @keyup.enter.native="handleFilter" />

           <span style="margin-left: 15px;">指标名称附注：</span>
            <el-input v-model="listQuery.zbfz" placeholder="请输入指标名称附注" clearable style="width: 180px;" class="filter-item" @keyup.enter.native="handleFilter" />

           <span style="margin-left: 15px;">单位名称：</span>
            <el-input v-model="listQuery.dwmc" placeholder="请输入单位名称" clearable style="width: 180px;" class="filter-item" @keyup.enter.native="handleFilter" />

      </el-row>
      <el-row style="margin-top:10px">

          <span>地区名称：</span>
          <tree-select placeholder="请选择" :searchable="false" :options="options" v-model="listQuery.dqmc" style="width: 180px;display: inline-flex;vertical-align: middle"></tree-select>

           <span style="margin-left: 15px;">起始时间：</span>
           <el-date-picker type="month" v-model="listQuery.startTime" placeholder="请选择"
                      value-format="yyyy-MM-dd" style="width: 180px"></el-date-picker>

           <span style="margin-left: 15px;">截止时间：</span>
          <el-date-picker type="month" v-model="listQuery.endTime" placeholder="请选择"
                          value-format="yyyy-MM-dd" style="width: 180px"></el-date-picker>

          <el-button style="margin-left: 15px;" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
            查询
          </el-button>
          <el-button style="margin-left: 15px;" class="filter-item" type="primary" :loading="btnflag" icon="el-icon-refresh-left" @click="updateYwData">
            同步最新数据
          </el-button>

      </el-row>
    </div>
<div style="z-index:1;padding:15px 15px 0 15px;height:calc(100vh - 330px);background:#fff;position:relative">
    <el-table v-loading="listLoading" :data="list" height="100%" stripe  fit highlight-current-row
      >

      <!-- ID不显示 -->

      <el-table-column align="left" label="指标名称">
        <template slot-scope="{row}">
          <span>{{ row.zbmc }}</span>
        </template>
      </el-table-column>

      <el-table-column align="left" label="指标分类">
        <template slot-scope="{row}">
            <span>{{ $strTransfer.lyidTransfer(row.lyid) }}</span>
        </template>
      </el-table-column>

      <el-table-column align="left" label="指标名称附注">
        <template slot-scope="{row}">
          <span>{{ row.zbmcfz }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="数值">
        <template slot-scope="{row}">
          <span>{{ row.sz }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="起始时间">
        <template slot-scope="{row}">
          <span>{{ row.qssj }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="截止时间">
        <template slot-scope="{row}">
            <span>{{ row.jzsj }}</span>
        </template>
      </el-table-column>

      <el-table-column align="left" label="单位名称">
        <template slot-scope="{row}">
            <span>{{ row.dwmc }}</span>
        </template>
      </el-table-column>

      <el-table-column align="left" label="地区名称">
        <template slot-scope="{row}">
            <span>{{ row.dqmc }}</span>
        </template>
      </el-table-column>


<!-- 绑定多个指标时显示不合理 -->
<!--       <el-table-column width="180px" align="center" label="绑定指标来源表">
        <template slot-scope="{row}">
          <span>{{ row.bindZbTable }}</span>
        </template>
      </el-table-column> -->

    </el-table>
     <dataCount dataType="YW_SYSTEM_DATA_MOVE" />
  </div>


    <el-pagination
        background
        layout="prev, pager, next,jumper"
        v-bind.sync="pageParam"
        @current-change="changePage"
        >
    </el-pagination>

    <dataCount dataType="YW_SYSTEM_DATA_MOVE" />

  </div>
</template>
<script>
//引入组件
import dataCount from '@/components/dataCompare'
import TreeSelect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import myheader from '@/components/header'

export default {
  name: 'newTableView',
  components: { dataCount,TreeSelect,myheader },
  data() {
    return {
      pageName:'数据中心-工业运行监测',
      zbflList: null,
      list: null,
      dialogVisible: false,
      tableName: '',
      listLoading: false,
      loading: false,
      listQuery:{
        zbmc: '',
        lyid: '',
        zbfz: '',
        dwmc: '',
        dqmc:null,
        startTime:'',
        endTime:'',
      },
      pageParam:{
        currentPage: 1,
        total: 10
      },
      selectItems:[],
      options:[],
      btnflag:false
    }
  },
  created() {
    this.getList(1);
    this.getzbflList();
    this.getAreaTree();
  },
  watch:{
    "listQuery.startTime":{
      handler(date){
        if(date!=null){
          let startDate=new Date(date.toString().replace(/-/g,"/"));
          let endDate=new Date(this.listQuery.endTime.toString().replace(/-/g,"/"));
          if(startDate.getTime()>endDate.getTime()){
            this.$message({
              type:"error",
              message:"起始时间必须小于截止时间",
            })
            this.listQuery.startTime=''
          }
        }
      },deep:true
    },
    "listQuery.endTime":{
      handler(date){
        if(date!=null) {
          let startDate = new Date(this.listQuery.startTime.toString().replace(/-/g, "/"));
          let endDate = new Date(date.toString().replace(/-/g, "/"));
          if (startDate.getTime() > endDate.getTime()) {
            this.$message({
              type: "error",
              message: "截止时间必须大于起始时间",
            })
            this.listQuery.endTime = ''
          }
        }
      },deep:true
    }
  },
  methods: {
    getzbflList() {
      let me = this;
      this.$axios.get('ywsystem/getZbflList',{}, function(r){
        me.zbflList = r.data;
      });
    },
    async getAreaTree(){
      this.$axios.get('getAreaTree',{},(r) =>{
        this.options=r.data;
        //console.log(r.data);
      })
    },
    async getList(pageNum, zbmc, zbfl, zbfz,dwmc,dqmc, startTime, endTime) {
      let me = this;
      //设置加载动画
      this.listLoading = true;
      this.$axios.get('ywsystem/getList',{pageNum: pageNum, zbmc: zbmc, lyid: zbfl, zbfz: zbfz,dwmc:dwmc,dqmc:dqmc,startTime:startTime, endTime:endTime}, function(r){
        //页数
        me.pageParam.total = r.data.total;
        //设置表单数据
        me.list = r.data.list;
        //关闭加载动画
        me.listLoading = false;
      })

    },
    handleFilter() {
      //设置当前第一页
      this.pageParam.currentPage = 1;
      //查询
      this.flushPage();
    },
    changePage() {
      //查询
      this.flushPage();
    },
    getRowKey(row) {
      return row.id;
    },
    flushPage() {
      this.getList(this.pageParam.currentPage, this.listQuery.zbmc, this.listQuery.lyid, this.listQuery.zbfz,this.listQuery.dwmc,this.listQuery.dqmc, this.listQuery.startTime, this.listQuery.endTime);
    },
    updateYwData() {
      //同步最新数据
      let me = this;
      this.btnflag = true;
      this.$axios.get('ywsystem/updateYwData', {}, function(r) {
        if (r.errCode === 200) {
          me.$message({
            type: 'success',
            message: '数据同步完成'
          });
        } else if (r.errCode === 202) {
          me.$message({
            type: 'error',
            message: '数据同步失败，请联系管理员处理'
          });

        } else if (r.errCode === 204) {
          me.$message({
            type: 'warning',
            message: '当前仍有同步任务在执行，请稍后再试'
          });
        } else if (r.errCode === 201) {
          me.$message({
            type: 'success',
            message: '同步任务已启动，请等待任务完成后重新加载数据'
          });
        }
        me.btnflag = false;
      });
    }
  }
}
</script>
