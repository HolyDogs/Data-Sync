<template>
  <div class="app-container" align="center">
    <div class="filter-container" style="margin-top:0;padding-top:0;border-bottom:1px solid #e2e2e2">
      <el-row>
        <el-col :span="12">
          <span>指标来源：</span>
          <el-select v-model="listQuery.lymc" clearable placeholder="请选择" style="width: 250px">
            <el-option
              v-for="item in selectOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="10">
          <span>指标分类：</span>
          <el-select v-model="listQuery.zbfl" filterable clearable placeholder="请选择" style="width: 250px">
            <el-option
              v-for="item in zbflSelectList"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-col>
      </el-row>
      <el-row style="margin-top:10px">
        <el-col :span="12">
          <span>指标名称：</span>
          <el-input v-model="listQuery.zbmc" placeholder="请输入指标名称" clearable style="width: 170px;" class="filter-item" @keyup.enter.native="handleFilter" />

          <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter" style="margin-left: 15px;">
            查询
          </el-button>
        </el-col>
      </el-row>

      <el-dialog
        title="增加指标"
        v-loading="loading"
        element-loading-text="指标数据同步中"
        :visible.sync="dialogVisible"
        append-to-body>

        <el-table :data="selectItems" height="500" fit :default-sort = "{prop: 'zbkey', order: 'ascending'}">
          <el-table-column label="已选择的指标（会将绑定指标一起加入专题）" align="center">

            <el-table-column align="center" label="指标编码" prop="zbkey">
              <template slot-scope="{row}">
                <span>{{ row.zbkey }}</span>
              </template>
            </el-table-column>

            <el-table-column align="left" label="指标名称">
              <template slot-scope="{row}">
                <span>{{ row.zbmc }}</span>
              </template>
            </el-table-column>

            <el-table-column align="left" label="指标来源">
              <template slot-scope="{row}">
                <span>{{ $strTransfer.dataSourceTransfer(row.sourceMark) }}</span>
              </template>
            </el-table-column>

            <el-table-column align="left" label="指标分类">
              <template slot-scope="{row}">
                <span>{{ row.zbfl }}</span>
              </template>
            </el-table-column>

            <el-table-column align="center" label="绑定指标数">
              <template slot-scope="{row}">
                <span>{{ row.bindZb === null?'0':row.bindZb.split(',').length }}</span>
              </template>
            </el-table-column>

          </el-table-column>
        </el-table>


        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="createNewTable">确 定</el-button>
          <el-button @click="dialogVisible = false">取 消</el-button>
        </span>

      </el-dialog>

    </div>


    <div class="flex" style="justify-content:flex-end;margin-bottom:15px">
      <el-badge :value="selectItems.length" class="item" align="right">
          <el-button type="primary" icon="el-icon-plus" @click="addZbFunc">增加指标</el-button>
      </el-badge>
    </div>
    <el-table v-loading="listLoading" :data="list" :row-key="getRowKey" border height="460px" fit highlight-current-row style="width: 100%"
      @selection-change="handleSelectionChange">

      <el-table-column
        type="selection"
        width="40"
        :reserve-selection="true"
      ></el-table-column>

      <!-- ID不显示 -->

      <el-table-column align="left" label="指标名称">
        <template slot-scope="{row}">
          <span>{{ row.zbmc }}</span>
        </template>
      </el-table-column>

      <el-table-column align="left" label="指标来源">
        <template slot-scope="{row}">
          <span>{{ $strTransfer.dataSourceTransfer(row.sourceMark) }}</span>
        </template>
      </el-table-column>

      <el-table-column align="left" label="来源表">
        <template slot-scope="{row}">
          <span>{{ row.sourceMark }}</span>
        </template>
      </el-table-column>

      <el-table-column align="left" label="指标分类">
        <template slot-scope="{row}">
          <span>{{ row.zbfl }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作">
        <template slot-scope="{row}">
          <el-button type="primary"  @click="bindZbView(row.bindZb)" size="small">
            查看绑定指标
          </el-button>
        </template>
      </el-table-column>

<!--       <el-table-column width="240px" align="center" label="绑定指标">
        <template slot-scope="{row}">
          <el-tooltip content="多个指标之间以空格分隔，指标名称与指标类型之间以'|'分隔" placement="bottom" effect="light">
            <span>{{ row.bindZb }}</span>
          </el-tooltip>
        </template>
      </el-table-column> -->

<!-- 绑定多个指标时显示不合理 -->
<!--       <el-table-column width="180px" align="center" label="绑定指标来源表">
        <template slot-scope="{row}">
          <span>{{ row.bindZbTable }}</span>
        </template>
      </el-table-column> -->

    </el-table>

      <el-dialog
        title="查看绑定指标"
        v-loading="bindzbLoading"
        :visible.sync="bindzbViewVisible"
        v-if="bindzbViewVisible"
        append-to-body>

        <bindZB :bindZbData="bindZbData"></bindZB>

        <span slot="footer" class="dialog-footer">
          <el-button @click="bindzbViewVisible = false">关 闭</el-button>
        </span>

      </el-dialog>


    <el-pagination
        background
        layout="prev, pager, next ,jumper"
        v-bind.sync="pageParam"
        @current-change="changePage"
        >
    </el-pagination>

  </div>
</template>
<script>

import bindZB from '@/components/bindZB'

export default {
  name: 'addTableView',
  components: { bindZB},
  props: ['childdata'],
  data() {
    return {
      zbflList: null,
      zbflSelectList: null,
      list: [],
      dialogVisible: false,
      tableName: '',
      listLoading: false,
      bindzbViewVisible: false,
      bindzbLoading: false,
      bindZbData: [],
      loading: false,
      listQuery:{
        zbmc: '',
        zbfl: '',
        lymc: ''
      },
      pageParam:{
        currentPage: 1,
        total: 10
      },
      selectItems:[],
      selectOptions:[]
    }
  },
  created() {
    this.getList(1);
    this.getzbflList();
    this.getSourceSelectOptions();
  },
  watch:{
    "listQuery.lymc":{
      handler(newValue) {
        var zbflArr = [];
        let me = this;
        for (var i = 0; i < me.zbflList.length; i++) {
          if (me.zbflList[i].ZBLY === newValue || newValue === '') {
            zbflArr.push(me.zbflList[i].ZBFL);
          }
        }
        me.zbflSelectList = zbflArr;
      },
      deep: true
    }
  },
  methods: {
    getSourceSelectOptions(){
      let me = this;
      this.$axios.get('getSourceSelectOptions', {}, function(r){
        me.selectOptions = r.data;
      })
    },
    getzbflList() {
      let me = this;
      this.$axios.get('getZbflList',{}, function(r){
        me.zbflList = r.data;
        var zbflArr = [];
        for (var i = 0; i < me.zbflList.length; i++) {
          zbflArr.push(me.zbflList[i].ZBFL);
        }
        me.zbflSelectList = zbflArr;
      });
    },
    async getList(pageNum, zbmc, zbfl, lymc) {
      let me = this;
      //设置加载动画
      this.listLoading = true;
      this.$axios.get('getZbByFl',{pageNum: pageNum, zbmc:zbmc, zbfl:zbfl, sourceMark: lymc}, function(r){
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
      this.getList(this.pageParam.currentPage, this.listQuery.zbmc, this.listQuery.zbfl, this.listQuery.lymc);
    },
    changePage() {
      //查询
      this.getList(this.pageParam.currentPage, this.listQuery.zbmc, this.listQuery.zbfl, this.listQuery.lymc);
    },
    handleSelectionChange(val){
      this.selectItems = val;
    },
    getRowKey(row) {
      return row.id;
    },
    createNewTable() {
      var zbList = '';
      if (this.selectItems.length < 1) {
        this.$message({
          type:"error",
          message:'请至少勾选一个指标'
        });
        this.dialogVisible = false;
        return;
      }
      //获取所有指标id并拼接
      for (var i = this.selectItems.length - 1; i >= 0; i--) {
        zbList = zbList + this.selectItems[i].id + ",";
      }
      //去掉最后一个逗号
      zbList = zbList.substr(0, zbList.length-1);
      let me = this;
      //加载动画
      me.loading = true;
      //请求增加指标
      this.$axios.get('zbDataAddToTable', {tableId:this.childdata.id, zbList:zbList}, function(r){
          if (r.errCode === 201) {
            me.$message({
              type:"warning",
              message:r.errMsg
            })
          } else{
            me.$message({
              type:"success",
              message:r.errMsg
            });
          }
          //清空状态
          me.loading = false;
          //关闭弹窗
          me.dialogVisible = false;

          me.$emit("zchange", "123");

      });

    },
    bindZbView(bindzb){
      this.bindzbViewVisible = true;
      this.bindzbLoading = true;
      let me = this;
      this.bindZbData=[];
      this.$axios.get("/getZbByIdList", {idList: bindzb}, function(r) {
        me.bindZbData = r.data||[];
        me.bindzbLoading = false;
      })
    },
    addZbFunc() {
      if (this.selectItems.length === 0) {
        this.$message({
          type:"error",
          message:'请至少勾选一个指标'
        });
        return;
      }
      this.dialogVisible = true;
    }
  }
}
</script>
