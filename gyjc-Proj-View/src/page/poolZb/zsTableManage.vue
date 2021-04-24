<template>
  <div class="app-container page_01" align="center" style="height:calc(100vh - 64px)">

    <el-container>
      <el-aside width="16.66666%">
        <el-input
          placeholder="输入关键字进行过滤"
          class="filterTreeModel"
          v-model="filterText"
          clearable>
          <i slot="prefix" class="el-input__icon el-icon-search" style="margin-left: 15px;margin-top: -2px"></i>
        </el-input>

        <el-tree
          class="filter-tree"
          :data="treeData"
          :props="defaultProps"
          :filter-node-method="filterNode"
          :default-expand-all="true"
          @node-click="nodeClick"
          ref="tree">
        </el-tree>
      </el-aside>
      <el-main>
        <myheader :name="pageName"></myheader>
    <div class="filter-container">
      <span>展示表名称：</span>
      <el-input v-model="tableName" placeholder="请输入展示表名称" clearable style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <span style="margin-left: 15px;">创建起始时间：</span>
      <el-date-picker
        v-model="createStartTime"
        type="date"
        value-format="yyyy-MM-dd"
        placeholder="请选择"
        style="width: 140px;">
      </el-date-picker>
      <span style="margin-left: 15px;">创建截止时间：</span>
      <el-date-picker
        v-model="createEndTime"
        type="date"
        value-format="yyyy-MM-dd"
        placeholder="请选择"
        style="width: 140px;">
      </el-date-picker>
      <el-button class="filter-item" type="primary" icon="el-icon-search" style="margin-left: 15px;" @click="handleFilter">
        查询
      </el-button>

      <!--<el-button type="success" icon="el-icon-check" @click="dialogVisible = true">创建新表</el-button> -->

    </div>


<div style="padding:15px 15px 0 15px;height:calc(100vh - 290px);background:#fff">
    <el-table v-loading="listLoading" class="customer-table" :data="list" :row-key="getRowKey" height="100%" fit highlight-current-row style="width: 100%"
    >

      <!-- ID不显示 -->

      <el-table-column type="expand">
        <template slot-scope="{row}">

          <el-table :data="row.poolZbList" class="customer-table" :ref="row.tableName" row-key="getRowKey" @selection-change="handleSelectionChange($event,row)" fit>

            <el-table-column
              type="selection"
              width="55"
              :reserve-selection="true"
            ></el-table-column>

            <el-table-column prop="zbmc" align="left" label="指标名称">
            </el-table-column>

            <el-table-column prop="zbly" align="left" label="指标来源">
            </el-table-column>

            <el-table-column prop="zbfl" align="left" label="指标分类">
            </el-table-column>

<!--             <el-table-column width="165px" align="center" label="设置数据展示起始时间">
              <template slot-scope="scope">
                <el-date-picker
                  v-model="scope.row.startDate"
                  type="date"
                  placeholder="起始日期"
                  style="width: 140px">
                </el-date-picker>
              </template>
            </el-table-column>

            <el-table-column width="165px" align="center" label="设置数据展示截止时间">
              <template slot-scope="scope">
                <el-date-picker
                  v-model="scope.row.endDate"
                  type="date"
                  placeholder="截止日期"
                  style="width: 140px">
                </el-date-picker>
              </template>
            </el-table-column> -->

            <el-table-column align="center" label="操作">
              <template slot-scope="scope">
                <!-- <el-button type="primary" title="修改指标展示时间区间" icon="el-icon-edit" @click="editZsZbDateRange(row, scope.row)" circle></el-button> -->
                <el-button type="danger" title="删除展示指标" icon="el-icon-delete" @click="deleteZb(row,scope.row)" circle></el-button>
                <!-- <el-button type="primary" icon="el-icon-s-data" title="生成统计数据"  @click="countData(row,scope.row)">生成统计数据</el-button> -->
              </template>
            </el-table-column>

          </el-table>
        </template>
      </el-table-column>

      <el-table-column align="left" label="展示表名称">
        <template slot-scope="{row}">
          <span>{{ row.tableName }}</span>
        </template>
      </el-table-column>

      <el-table-column align="left" label="创建时间">
        <template slot-scope="{row}">
          <span>{{ row.createTime.substr(0,10) }}</span>
        </template>
      </el-table-column>

      <el-table-column align="left" label="展示表备注">
        <template slot-scope="{row}">
          <span>{{ row.tableComments }}</span>
        </template>
      </el-table-column>

      <el-table-column align="left" label="主题表名称">
        <template slot-scope="{row}">
          <span>{{ row.parentTable }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="630px">
        <template slot-scope="{row,$index}">
          <el-button type="primary"  @click="addZb(row)" size="small">
            增加指标
          </el-button>
          <el-button type="danger"  @click="deleteZbs(row)" size="small">
            删除指标
          </el-button>
          <el-button type="danger"  @click="deleteTable(row)" size="small">
            删除该表
          </el-button>
          <el-button type="primary"  @click="selectTable(row)" size="small">
            查看统计数据
          </el-button>
          <el-button type="success" icon="el-icon-s-data" plain @click="countData(row)" size="small">
            生成统计数据
          </el-button>
        </template>
      </el-table-column>

    </el-table>
</div>
<!-- :visible.sync="addTableViewVisible" -->
    <!-- <el-button class="timeSet" type="primary" title="设置指标展示时间" icon="el-icon-edit" @click="displayRangeDialog" v-if="selectItems.length > 0" round>设置指标展示时间</el-button> -->

    <!-- 统计维度选择 -->
    <el-dialog
    v-loading = "countLoading"
    element-loading-text="拼命计算中"
    v-if="countDialogVisible"
    :visible.sync="countDialogVisible"
    title="生成统计数据"
    width="600px"
    style="text-align: center;"
    append-to-body
    >
      <div class="el-form-item">
        <font size="4">统计维度选择：</font>
        <el-tooltip class="item" effect="dark" content="温馨提示：生成同比、环比数据时需要先生成对应的月季度数据" placement="top">
          <el-select clearable v-model="countParam.dimension" @change="changeDimension" placeholder="请选择">
            <el-option
              v-for="item in dimensions"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-tooltip>
      </div>
      <div class="el-form-item" v-if="countParam.dimension === '月度值' || countParam.dimension === '年度值'
      || countParam.dimension === '月度累计值' || countParam.dimension === '获取年度值' || countParam.dimension === '企业排名'">
        <span>起始日期：</span>
        <el-date-picker
          v-model="startDate"
          :type="countParam.dimension === '月度值' || countParam.dimension === '月度累计值' || countParam.dimension === '企业排名' ? 'month':'year'"
          placeholder="起始日期"
          value-format="yyyy-MM-dd"
          style="width: 150px">
        </el-date-picker>

        <span>截止日期：</span>
        <el-date-picker
          v-model="endDate"
          :type="countParam.dimension === '月度值' || countParam.dimension === '月度累计值' || countParam.dimension === '企业排名' ? 'month':'year'"
          placeholder="截止日期"
          value-format="yyyy-MM-dd"
          style="width: 150px">
        </el-date-picker>
      </div>

      <div class="el-form-item" v-if="countParam.dimension === '季度值' || countParam.dimension === '获取季度值'">
        <span>
          起始时间
          <mark
            style="position:fixed;top:0;bottom:0;left:0;right:0;background:rgba(0,0,0,0);z-index:999;"
            v-show="showSeason1"
            @click.stop="showSeason1=false"
          ></mark>
          <el-input placeholder="请选择季度" v-model="startDate" style="width:160px;" @focus="showSeason1=true" clearable>
            <i slot="prefix" class="el-input__icon el-icon-date"></i>
          </el-input>
          <transition name="slide-fade">
          <el-card
            class="box-card"
            style="width:322px;padding: 0 3px 20px;margin-left: 115px;margin-top:10px;position:fixed;z-index:9999"
            v-show="showSeason1"
          >
            <div slot="header" class="clearfix" style="text-align:center;padding:0">
              <button
                type="button"
                aria-label="前一年"
                class="el-picker-panel__icon-btn el-date-picker__prev-btn el-icon-d-arrow-left"
                @click="prev(1)"
              ></button>
              <span>{{year1}}年</span>
              <button
                type="button"
                aria-label="后一年"
                @click="next(1)"
                class="el-picker-panel__icon-btn el-date-picker__next-btn el-icon-d-arrow-right"
              ></button>
            </div>
            <div class="text item" style="text-align:center;">
              <el-button
                type="text"
                size="medium"
                style="width:40%;color: #606266;float:left;"
                @click="selectSeason1(0)"
              >第一季度</el-button>
              <el-button
                type="text"
                size="medium"
                style="float:right;width:40%;color: #606266;"
                @click="selectSeason1(1)"
              >第二季度</el-button>
            </div>
            <div class="text item" style="text-align:center;">
              <el-button
                type="text"
                size="medium"
                style="width:40%;color: #606266;float:left;"
                @click="selectSeason1(2)"
              >第三季度</el-button>
              <el-button
                type="text"
                size="medium"
                style="float:right;width:40%;color: #606266;"
                @click="selectSeason1(3)"
              >第四季度</el-button>
            </div>
            </el-card>
          </transition>
          </span>


          <span>
          结束时间
          <mark
            style="position:fixed;top:0;bottom:0;left:0;right:0;background:rgba(0,0,0,0);z-index:999;"
            v-show="showSeason2"
            @click.stop="showSeason2=false"
          ></mark>
          <el-input placeholder="请选择季度" v-model="endDate" style="width:160px;" @focus="showSeason2=true" clearable>
            <i slot="prefix" class="el-input__icon el-icon-date"></i>
          </el-input>
            <transition name="slide-fade">
          <el-card
            class="box-card"
            style="width:322px;padding: 0 3px 20px;margin-left: 340px;margin-top:10px;position:fixed;z-index:9999"
            v-show="showSeason2"
          >
            <div slot="header" class="clearfix" style="text-align:center;padding:0">
              <button
                type="button"
                aria-label="前一年"
                class="el-picker-panel__icon-btn el-date-picker__prev-btn el-icon-d-arrow-left"
                @click="prev(2)"
              ></button>
              <span>{{year2}}年</span>
              <button
                type="button"
                aria-label="后一年"
                @click="next(2)"
                class="el-picker-panel__icon-btn el-date-picker__next-btn el-icon-d-arrow-right"
              ></button>
            </div>
            <div class="text item" style="text-align:center;">
              <el-button
                type="text"
                size="medium"
                style="width:40%;color: #606266;float:left;"
                @click="selectSeason2(0)"
              >第一季度</el-button>
              <el-button
                type="text"
                size="medium"
                style="float:right;width:40%;color: #606266;"
                @click="selectSeason2(1)"
              >第二季度</el-button>
            </div>
            <div class="text item" style="text-align:center;">
              <el-button
                type="text"
                size="medium"
                style="width:40%;color: #606266;float:left;"
                @click="selectSeason2(2)"
              >第三季度</el-button>
              <el-button
                type="text"
                size="medium"
                style="float:right;width:40%;color: #606266;"
                @click="selectSeason2(3)"
              >第四季度</el-button>
            </div>
            </el-card>
            </transition>
          </span>
      </div>

      <div class="el-form-item" v-if="countParam.dimension === '企业排名'">
          <span>统计数目：</span>
          <el-input-number v-model="countParam.limit" :precision="0" :step="10" :min="1" :max="200"/>
      </div>

        <span slot="footer" class="dialog-footer">
          <el-button type="danger" @click="deleteCountData">删 除</el-button>
          <el-button type="primary" @click="countForDimension">生 成</el-button>
          <el-button @click="countDialogVisible = false">取 消</el-button>
        </span>

    </el-dialog>

<!--     <el-dialog
    v-if="displayRangeDialogVisible"
    :visible.sync="displayRangeDialogVisible"
    title="设置指标展示时间区间"
    >

      <el-date-picker
          v-model="startDate"
          type="date"
          placeholder="起始日期"
          style="width: 140px">
        </el-date-picker>

        <el-date-picker
          v-model="endDate"
          type="date"
          placeholder="截止日期"
          style="width: 140px">
        </el-date-picker>

        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="editZbDisplayRange">确 定</el-button>
          <el-button @click="displayRangeDialogVisible = false">取 消</el-button>
        </span>

    </el-dialog>
 -->
    <el-dialog
     title="增加指标"
    v-if="addTableViewVisible"
    :visible.sync="addTableViewVisible"
    width="950px">

      <!-- zchange用来给子组件调用，然后传送一个参数给父组件。 childdata为绑定传给子组件的值 -->
      <addZsTableView @zchange="zchange($event)" v-bind:childdata="childdata"/>

    </el-dialog>

    <el-dialog
    title="查看详细"
    v-if="selectTableViewVisible"
    :visible.sync="selectTableViewVisible"
    width="1200px">

      <!-- zchange用来给子组件调用，然后传送一个参数给父组件。 childdata为绑定传给子组件的值 -->
      <selctTableData v-bind:zsFlag="zsFlag" v-bind:selectTableParam="selectTableParam"/>

    </el-dialog>

    <el-pagination
        background
        layout="prev, pager, next"
        v-bind.sync="pageParam"
        :page-size="10"
        @current-change="changePage"
        >
    </el-pagination>

      </el-main>
    </el-container>
  </div>
</template>
<script>
import myheader from '@/components/header'
import addZsTableView from '@/components/addZsZbTable'
//import selctTableData from '@/components/tableDataView' 改成下面的新组件
import selctTableData from '@/components/zstjDataView'

export default {
  name: 'newTableView',
  components: { addZsTableView, selctTableData ,myheader},
  data() {
    return {
      pageName:'展示库',
      showSeason1: false,
      showSeason2: false,
      season: '',
      year1: new Date().getFullYear(),
      year2: new Date().getFullYear(),
      showValue: '',
      zbflList: null,
      list: [],
      dialogVisible: false,
      tableName: '',
      listLoading: false,
      countLoading: false,
      pageParam:{
        currentPage: 1,
        total: 10
      },
      addTableViewVisible: false,
      selectTableViewVisible: false,
      //displayRangeDialogVisible: false,
      countDialogVisible: false,
      startDate:'',
      endDate:'',
      selectItems: [],
      currentTable: '',
      //父组件传给子组件的变量
      childdata: '',
      selectTableParam: '',
      zsFlag: true,
      countParam: {
        tableName: '',
        zbmc: '',
        dataSource: '',
        dimension: '',
        limit: 50
      },
      dimensions: [],
      filterText: '',
      treeData:[],
      defaultProps: {
        children: 'children',
        label: 'LABEL',
        value: 'VALUE'
      },
      tableType:'',
      createStartTime:'',
      createEndTime:'',
    }
  },
  created() {
    this.getList(1);
    this.getTJTree();
    this.getTreeData();
  },
watch:{
    filterText(val) {
      this.$refs.tree.filter(val.trim());
    },
    startDate(date){
      if(date!=null){
        let startDate=new Date(date.toString().replace(/-/g,"/").replace("第","/").replace("季度",""));
        let endDate=new Date(this.endDate.toString().replace(/-/g,"/").replace("第","/").replace("季度",""));
        if(startDate.getTime()>endDate.getTime()){
          this.$message({
            type:"error",
            message:"起始日期必须小于截止日期",
          })
          this.startDate=''
        }
      }
    },
    endDate(date){
      if(date!=null){
        let endDate=new Date(date.toString().replace(/-/g,"/"));
        let startDate=new Date(this.startDate.toString().replace(/-/g,"/"));
        if(startDate.getTime()>endDate.getTime()){
          this.$message({
            type:"error",
            message:"截止日期必须大于起始日期",
          })
          this.endDate=''
        }
      }
    },
    createStartTime(date){
      if(date!=null){
        let startDate=new Date(date.toString().replace(/-/g,"/"));
        let endDate=new Date(this.createEndTime.toString().replace(/-/g,"/"));
        if(startDate.getTime()>endDate.getTime()){
          this.$message({
            type:"error",
            message:"起始时间必须小于截止时间",
          })
          this.createStartTime=''
        }
      }
    },
    createEndTime(date){
      if(date!=null){
        let startDate=new Date(this.createStartTime.toString().replace(/-/g,"/"));
        let endDate=new Date(date.toString().replace(/-/g,"/"));
        if(startDate.getTime()>endDate.getTime()){
          this.$message({
            type:"error",
            message:"截止时间必须大于起始时间",
          })
          this.createEndTime=''
        }
      }
    },
  },
  methods: {
    getTreeData() {
      let me = this;
      this.$axios.get('getTableTypeTree', {}, function(r) {
        me.treeData = r.data;
      })
    },
    prev(i) {
      if (i === 1) {
        this.year1 = this.year1 * 1 - 1
      } else{
        this.year2 = this.year2 * 1 - 1
      }
    },
    next(i) {
      if (i === 1) {
        this.year1 = this.year1 * 1 + 1
      } else{
        this.year2 = this.year2 * 1 + 1
      }
    },
    selectSeason1(i) {
      let that = this
      that.season = i + 1
      this.startDate = that.year1 + '第' + (i + 1)+'季度';
      that.showSeason1 = false
    },
    selectSeason2(i) {
      let that = this
      that.season = i + 1
      this.endDate = that.year2 + '第' + (i + 1)+'季度';
      that.showSeason2 = false
    },
    changeDimension() {
      //清空输入框
      this.startDate = '';
      this.endDate = '';
    },
    async getTJTree(){
      this.$axios.get('getTJTree',{},(r) =>{
        for(let i=0;i<r.data.length;i++){
          this.dimensions.push(r.data[i].ITEM_VALUE)
        }
      })
    },
    async getList(pageNum, tableName, tableType) {
      let me = this;
      //设置加载动画
      this.listLoading = true;
      this.$axios.get('getZsTableList',{pageNum: pageNum, tableName:tableName, tableType:tableType,createStartTime:this.createStartTime,createEndTime:this.createEndTime}, function(r){
        //页数
        me.pageParam.total = r.data.total;
        //设置表单数据
        me.list = r.data.list;
        //关闭加载动画
        me.listLoading = false;
      })
    },
    flushTable() {
      this.getList(this.pageParam.currentPage, this.tableName.toUpperCase(), this.tableType);
    },
    handleFilter() {
      //设置当前第一页
      this.pageParam.currentPage = 1;
      //查询
      this.flushTable();
    },
    changePage() {
      //查询
      this.flushTable();
    },
    getRowKey(row) {
      return row.id;
    },
    addZb(row) {
      this.childdata = row;
      this.addTableViewVisible = true;
    },
    handleSelectionChange(val, row) {
      //取消其它table的选中状态
      if (this.currentTable != row.tableName) {
        var comp = this.$refs;
        for(var zzz in comp) {
          if (zzz != row.tableName) {
            //有可能出现VueComponent失效的情况，然后调用方法报错导致后面的语句不执行
            try {
             this.$refs[zzz].clearSelection();
            } catch(e) {
              console.log(zzz + ' is undefined(closed or delete)!!!')
            }
          }
        }
      }

      //设置选中项
      this.selectItems = val;
      //设置当前选中表
      this.currentTable = row.tableName;
    },
    deleteTable(row) {
      //删除表以及表内的所有数据
      let me = this;
      this.$confirm('您确定删除该展示表及表内所有数据吗？', '提示', {
        cancelButtonClass:'btn-custom-cancel',
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //点击确认
        this.$axios.get('deleteZsTable', {tableName:row.tableName}, function(r){
          //刷新表格
          me.flushTable();
          me.$message({
            type: 'success',
            message: '删除成功!'
          });
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    },
    deleteZb(row,zbrow) {
      //删除指标表内所有关于该指标的数据，以及删除该表与该指标的绑定关系
      let me = this;

      this.$confirm('您确定删除该指标以及指标对应的所有数据吗？', '提示', {
        cancelButtonClass:'btn-custom-cancel',
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //点击确认
          this.$refs[row.tableName].clearSelection();
          this.$axios.get('deleteZsTableZb', {tableId:row.id, zbId:zbrow.id},function(r){
            //刷新表格
            me.flushTable();
            me.$message({
              type: 'success',
              message: '删除成功!'
            });
          });

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });


    },
/*    editZbDisplayRange() {
      //修改指标展示时间区间
      let me = this;
      var param = JSON.stringify(this.selectItems);
      var startDate = JSON.stringify(this.startDate);
      var endDate = JSON.stringify(this.endDate);
      this.$axios.post('editZbDisplayRange/' + this.currentTable, {param, startDate, endDate}, function(r) {
        me.$message({
          type:'info',
          message: '修改成功'
        });
        me.displayRangeDialogVisible = false;

        //清除勾选状态
        me.$refs[me.currentTable].clearSelection();
      })
    },*/
    zchange(result) {
      //result为子组件传给父组件的参数
      let me = this;
      if ("123" === result) {
        //关闭弹窗
        me.addTableViewVisible=false;
        //刷新列表
        me.flushTable();
      }

    },
    selectTable(row) {
      //打开子组件selectTableView
      this.selectTableParam=row.tableName + "_TJ";
      this.selectTableViewVisible=true;

    },
    countData(row) {

      if (this.currentTable != row.tableName || this.selectItems.length === 0) {
        this.$message({
          type:"error",
          message:'请至少勾选一条指标!'
        });
        return;
      }

      this.countParam.tableName = row.tableName;

/*      this.countParam.zbmc = scope.zbmc;
      this.countParam.dataSource = scope.sourceMark;*/
      this.countDialogVisible = true;
    },
    countForDimension() {
      let me = this;
      //不选择维度
      if ('' === me.countParam.dimension) {
        me.$message({
          type:"error",
          message:'请至少选择一个维度'
        });
        return;
      }
      me.countLoading = true;
      //格式化为post传输的参数
      var tableName = me.countParam.tableName;
      var param = JSON.stringify(me.selectItems);
      var dimension = me.countParam.dimension;
      //处理起、止日期
      var startDate = me.startDate;
      var endDate = me.endDate;
      var limit = me.countParam.limit;
      //xx统计
      this.$axios.post('countToTjTable' + '/add', { tableName, param, dimension, startDate, endDate, limit }, function(r) {
          if (r.data === 0) {
            me.$message({
              type:"error",
              message:'生成统计数据失败，不存在统计需要的数据或者已存在该维度的统计数据。'
            });
          } else{
            me.$message({
              type:"success",
              message:'新增统计数据' + r.data + '条'
            });
          }
          me.countLoading = false;

          me.countDialogVisible = false;
      });
    },
    deleteCountData() {
      let me = this;
      //不选择维度
      if ('' === me.countParam.dimension) {
        me.$message({
          type:"error",
          message:'请至少选择一个维度'
        });
        return;
      }
      me.countLoading = true;

      //格式化为post传输的参数
      var tableName = me.countParam.tableName;
      var param = JSON.stringify(me.selectItems);
      var dimension = me.countParam.dimension;
      this.$confirm('确认删除所选指标的"' + me.countParam.dimension + '"统计数据？', '提示', {
          cancelButtonClass:'btn-custom-cancel',
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //删除统计数据
          this.$axios.post('countToTjTable' + '/delete', {tableName, param, dimension }, function(r){
            me.$message({
              type:"success",
              message:'本次删除了' + r.data + '条数据'
            });
            me.countDialogVisible = false;
            me.countLoading = false;
          });

        }).catch(() => {
          me.countLoading = false;
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        })
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.LABEL.indexOf(value) !== -1;
    },
    nodeClick(data, node, self) {
      this.tableType = data.VALUE;
      //设置第一页
      this.pageParam.currentPage = 1;
      this.flushTable();
    },
    deleteZbs(row) {
      if (this.currentTable != row.tableName || this.selectItems.length === 0) {
        this.$message({
          type:"error",
          message:'请至少勾选一条指标!'
        });
        return;
      }
      let me = this;
      this.$confirm('您确定删除勾选的这些指标吗？', '提示', {
        cancelButtonClass:'btn-custom-cancel',
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //点击确认
        let param = JSON.stringify(this.selectItems);
        this.$refs[row.tableName].clearSelection();
        this.$axios.post('deleteZsTableZbList/' + row.id, {param},function(r){
          //刷新表格
          me.flushTable();
          me.$message({
            type: 'success',
            message: '删除成功!'
          });
        });

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    }
  }
}
</script>

<style type="text/css" scoped>

.timeSet{
  position: fixed !important;
  right: 3%;
  bottom: 10%;
}

.customer-table::before{
  width: 0;
}

.slide-fade-enter-active {
  transition: all .2s ease-in;
}
.slide-fade-leave-active {
  transition: all 0s;
}
.slide-fade-enter {
  transform: translateY(-10px);
  opacity: 0;
}

</style>
