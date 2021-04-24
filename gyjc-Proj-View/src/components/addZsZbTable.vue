<template>
  <div class="app-container" align="center">
    <div class="filter-container" style="margin-top:0;padding-top:0;border-bottom:1px solid #e2e2e2">
       <el-row>
         <el-col :span="12">
            <span>指标来源：</span>
            <el-select v-model="listQuery.lymc" clearable placeholder="请选择">
        <el-option
          v-for="item in selectOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
         </el-col>
         <el-col :span="12">
           <span>指标名称：</span>
            <el-input v-model="listQuery.zbmc" clearable placeholder="请输入指标名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
            <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter" style="margin-left: 15px;">
            查询
          </el-button>
         </el-col>
       </el-row>
     

      

<!--       <span>指标分类：</span>
      <el-select v-model="listQuery.zbfl" filterable clearable placeholder="请选择">
        <el-option
          v-for="item in zbflList"
          :key="item"
          :label="item"
          :value="item">
        </el-option>
      </el-select> -->    

      
      <el-dialog
        title="增加指标"
        v-loading="loading"
        element-loading-text="指标数据同步中"
        :visible.sync="dialogVisible"
        append-to-body>

        <el-table :data="selectItems" height="500" fit :default-sort = "{prop: 'zbkey', order: 'ascending'}">
          <el-table-column label="已选择的指标" align="center">

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

          </el-table-column>
        </el-table>
<!--
        <br/>
        <span style="font-weight: bolder;">为选择的指标选定一个时间筛选区间（可不填）：</span>
        <el-date-picker
          v-model="startDate"
          type="date"
          placeholder="起始日期"
          style="width: 140px">
        </el-date-picker>
        ---
        <el-date-picker
          v-model="endDate"
          type="date"
          placeholder="截止日期"
          style="width: 140px">
        </el-date-picker> -->


        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="addZbToZsTable">确 定</el-button>
          <el-button @click="dialogVisible = false">取 消</el-button>
        </span>

      </el-dialog>

    </div>


  <div class="flex" style="justify-content:flex-end;margin-bottom:15px">
    <el-badge :value="selectItems.length" class="item" align="right">
          <el-button type="primary" icon="el-icon-plus" @click="addZbFunc">增加指标</el-button>
      </el-badge>
    </div>
    <el-table v-loading="listLoading" :data="list" :row-key="getRowKey" height="460px" border fit highlight-current-row style="width: 100%"
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

  </div>
</template>
<script>

export default {
  name: 'addTableView',
  props: ['childdata'],
  data() {
    return {
      zbflList: null,
      list: [],
      dialogVisible: false,
      tableName: '',
      listLoading: false,
      loading: false,
      listQuery:{
        zbmc: '',
        lymc: ''
      },
      selectItems:[],
      selectOptions:[]
    }
  },
  created() {
    this.getList();
    this.getSourceSelectOptions();
    //this.getzbflList();
  },
  methods: {
/*    getzbflList() {
      let me = this;
      this.$axios.get('getZbflList',{}, function(r){
        me.zbflList = r.data;
      });
    },*/
    getSourceSelectOptions(){
      let me = this;
      this.$axios.get('getSourceSelectOptions', {}, function(r){
        me.selectOptions = r.data;
      })
    },
    async getList( zbmc, lymc) {
      let me = this;
      //设置加载动画
      this.listLoading = true;
      this.$axios.get('getZsZbList',{ tableId:this.childdata.id, zbmc:zbmc, sourceMark: lymc}, function(r){
        //设置表单数据
        me.list = r.data;
        //关闭加载动画
        me.listLoading = false;
      })

    },
    handleFilter() {
      //查询
      this.getList( this.listQuery.zbmc, this.listQuery.lymc);
    },
    handleSelectionChange(val){
      this.selectItems = val;
    },
    getRowKey(row) {
      return row.id;
    },
    addZbToZsTable() {
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
      this.$axios.get('addZbToZsTable', {tableId:this.childdata.id, zbList:zbList/*, startDate:this.startDate, endDate:this.endDate*/}, function(r){
          me.$message({
            type:"success",
            message:r.errMsg
          });
          //清空状态
          me.loading = false;
          //关闭弹窗
          me.dialogVisible = false;

          me.$emit("zchange", "123");

      });

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
