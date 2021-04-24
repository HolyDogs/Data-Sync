<template>
  <div class="app-container" align="center" >
    <div class="filter-container" style="margin-top:0;padding-top:0;">
      <el-row>
        <el-col :span="6">
          <span>指标来源：</span>
          <el-select v-model="listQuery.lymc" clearable placeholder="请选择" style="width: 160px;">
            <el-option
              v-for="item in selectOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="6">
          <span>指标名称：</span>
          <el-input v-model="listQuery.zbmc" placeholder="请输入指标名称" clearable style="width: 160px;" class="filter-item" @keyup.enter.native="handleFilter" />
        </el-col>
        <el-col :span="6">
           <span>起始日期：</span>
          <el-date-picker
            v-model="listQuery.startDate"
            type="date"
            placeholder="起始日期"
            style="width: 160px">
          </el-date-picker>
        </el-col>
        <el-col :span="6">
          <span>截止日期：</span>
          <el-date-picker
            v-model="listQuery.endDate"
            type="date"
            placeholder="截止日期"
            style="width: 150px">
          </el-date-picker>
        </el-col>
      </el-row>
      <el-row style="margin-top:10px">
        <el-col :span="6">
          <span>单位名称：</span>
          <el-input v-model="listQuery.dwmc" placeholder="请输入单位名称" clearable style="width: 160px;" class="filter-item" @keyup.enter.native="handleFilter" />
        </el-col>
        <el-col :span="18">
          <span>地区名称：</span>
          <el-input v-model="listQuery.dqmc" placeholder="请输入地区名称" clearable style="width: 160px;" class="filter-item" @keyup.enter.native="handleFilter" />
          <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter" style="margin-left: 15px;">
            查询
          </el-button>
          <el-button class="filter-item" type="primary" icon="el-icon-refresh-left" :loading="btnflag" @click="updateTableData" style="margin-left: 15px;">
            更新指标数据
          </el-button>
        </el-col>
      </el-row>
    </div>



    <el-table v-loading="listLoading" :data="list" border fit highlight-current-row height="465px" style="width: 100%"
      >

<!--       <el-table-column
        type="selection"
        width="55"
        :reserve-selection="true"
      ></el-table-column> -->

      <!-- ID不显示 -->

      <el-table-column width="240px" align="center" label="指标名称">
        <template slot-scope="{row}">
          <span>{{ row.zbmc }}</span>
        </template>
      </el-table-column>

      <el-table-column width="180px" align="center" label="指标来源">
        <template slot-scope="{row}">
          <span>{{ $strTransfer.dataSourceTransfer(row.dataSource) }}</span>
        </template>
      </el-table-column>

      <el-table-column width="180px" align="center" label="来源表">
        <template slot-scope="{row}">
          <span>{{ row.dataSource }}</span>
        </template>
      </el-table-column>

      <el-table-column width="100px" align="center" label="数值">
        <template slot-scope="{row}">
            <span>{{ row.value }}</span>
        </template>
      </el-table-column>

      <el-table-column v-if="this.zsFlag" width="100px" align="center" label="最新数值">
        <template slot-scope="{row}">
            <span>{{ row.newValue }}</span>
        </template>
      </el-table-column>

      <el-table-column width="160px" align="center" label="起始日期">
        <template slot-scope="{row}">
          <span>{{ row.startDate }}</span>
        </template>
      </el-table-column>

      <el-table-column width="160px" align="center" label="截止日期">
        <template slot-scope="{row}">
          <span>{{ row.endDate }}</span>
        </template>
      </el-table-column>

      <el-table-column width="260px" header-align="center" align="left" label="单位名称">
        <template slot-scope="{row}">
          <span>{{ row.dwmc }}</span>
        </template>
      </el-table-column>

      <el-table-column width="160px" align="center" label="地区名称">
        <template slot-scope="{row}">
          <span>{{ row.dqmc }}</span>
        </template>
      </el-table-column>

      <el-table-column v-if="this.zsFlag" label="Action" align="center" width="120">
        <template slot-scope="{row,$index}">
          <el-button type="primary"  @click="editValue(row)">
            修改值
          </el-button>
        </template>
      </el-table-column>
<!-- 绑定多个指标时显示不合理 -->
<!--       <el-table-column width="180px" align="center" label="绑定指标来源表">
        <template slot-scope="{row}">
          <span>{{ row.bindZbTable }}</span>
        </template>
      </el-table-column> -->

    </el-table>



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

  import TreeSelect from '@riophae/vue-treeselect'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'

export default {
  name: 'tableDataView',
  props: ['selectTableParam','zsFlag'],
  components: { TreeSelect },
  data() {
    return {
      list: null,
      dialogVisible: false,
      listLoading: false,
      listQuery:{
        zbmc: '',
        lymc: '',
        startDate: '',
        endDate: '',
        dwmc: '',
        dqmc: null
      },
      pageParam:{
        currentPage: 1,
        total: 10
      },
      selectItems:[],
      selectOptions:[],
      options:[],
      btnflag: false
    }
  },
  created() {
    this.getList(1,null,null,this.selectTableParam);
    this.getSourceSelectOptions();
    this.getAreaTree();
  },
  watch:{
    "listQuery.startDate":{handler(date){
      if(date!=null){
        let startDate=new Date(date.toString().replace(/-/g,"/"));
        let endDate=new Date(this.listQuery.endDate.toString().replace(/-/g,"/"));
        if(startDate.getTime()>endDate.getTime()){
          this.$message({
            type:"error",
            message:"起始日期必须小于截止日期",
          })
          this.listQuery.startDate=''
        }
      }
    },deep:true
    },
    "listQuery.endDate": {handler(date) {
      if(date!=null){
        let endDate = new Date(date.toString().replace(/-/g, "/"));
        let startDate = new Date(this.listQuery.startDate.toString().replace(/-/g, "/"));
        if (startDate.getTime() > endDate.getTime()) {
          this.$message({
            type: "error",
            message: "截止日期必须大于起始日期",
          })
          this.listQuery.endDate = ''
        }
      }
    },deep:true
    }
  },
  methods: {
    getSourceSelectOptions(){
      let me = this;
      this.$axios.get('getSourceSelectOptions', {flag:true}, function(r){
        me.selectOptions = r.data;
      })
    },
    async getAreaTree(){
      this.$axios.get('getAreaTree',{},(r) =>{
        this.options=r.data;
      })
    },
    async getList(pageNum, zbmc, lymc, tableName, startDate, endDate, dwmc, dqmc) {
      let me = this;
      //设置加载动画
      this.listLoading = true;
      this.$axios.get('getNewTableData',{pageNum: pageNum, zbmc:zbmc, sourceTable: lymc
        , tableName: tableName, startDate: startDate, endDate: endDate, dwmc:dwmc, dqmc:dqmc}, function(r){
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
    flushPage() {
      this.getList(this.pageParam.currentPage, this.listQuery.zbmc, this.listQuery.lymc, this.selectTableParam
        , this.listQuery.startDate, this.listQuery.endDate, this.listQuery.dwmc, this.listQuery.dqmc);
    },
    getRowKey(row) {
      return row.id;
    },
    editValue(row) {
      this.$prompt('请输入修改后的数值', '编辑', {
          cancelButtonClass:'btn-custom-cancel',
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern: /^[0-9]+(\.\d+)?$/,
          inputErrorMessage: '请输入正确的数字'
        }).then(({ value }) => {
          let me = this;
          this.$axios.get('editZbNewValue', {tableName:this.selectTableParam, id:row.id, value:parseFloat(value)}, function(r) {

            me.$message({
              type: 'success',
              message: '修改成功'
            });
            me.getList(me.pageParam.currentPage, me.listQuery.zbmc, me.listQuery.lymc, me.selectTableParam, me.listQuery.startDate, me.listQuery.endDate);
          });

        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消修改'
          });
        });
    },
    updateTableData() {
      let me = this;
      this.btnflag=true;
      this.$axios.get('updateTableData', {tableName: this.selectTableParam}, function(r) {
        //设置当前第一页
        me.pageParam.currentPage = 1;
        me.flushPage();
        me.$message({
          type: 'success',
          message: '更新完成!'
        });
        me.btnflag=false;
      });
    }
  }
}
</script>
