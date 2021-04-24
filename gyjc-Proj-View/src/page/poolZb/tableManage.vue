<template>
  <div class="app-container page_01" align="center" style="height:calc(100vh - 64px)">

    <el-container>
      <el-aside  width="16.66666%">
        <el-input
          placeholder="输入关键字进行过滤"
          v-model="filterText"
          class="filterTreeModel"
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
      <span>主题表名称：</span>
      <el-input v-model="tableName" placeholder="请输入主题表名称" clearable style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
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

          <el-table :data="row.poolZbList" :ref="row.tableName" class="customer-table" :row-key="getRowKey" @selection-change="handleSelectionChange($event,row)" fit>

            <el-table-column
              type="selection"
              width="55px"
              :reserve-selection="true"
            ></el-table-column>

            <el-table-column prop="zbmc" align="left" label="指标名称">
            </el-table-column>

            <el-table-column prop="zbly" align="left" label="指标来源">
            </el-table-column>

            <el-table-column prop="zbfl" align="left" label="指标分类">
            </el-table-column>

            <el-table-column align="center" label="操作">
              <template slot-scope="scope">
                <el-button type="danger" title="删除" icon="el-icon-delete" @click="deleteZb(row,scope.row)" circle></el-button>
              </template>
            </el-table-column>

          </el-table>
        </template>
      </el-table-column>

      <el-table-column align="left" label="主题表名称">
        <template slot-scope="{row}">
          <span>{{ row.tableName }}</span>
        </template>
      </el-table-column>

      <el-table-column align="left" label="创建时间">
        <template slot-scope="{row}">
          <span>{{ row.createTime.substring(0,10) }}</span>
        </template>
      </el-table-column>

      <el-table-column align="left" label="主题表备注">
        <template slot-scope="{row}">
          <span>{{ row.tableComments }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="580px">
        <template slot-scope="{row,$index}">
          <el-button type="primary"  @click="addZb(row)" size="small">
            增加指标
          </el-button>
          <!-- <el-badge :value="selectItems.length" class="item" align="right"> -->
            <el-button type="danger"  @click="deleteZbs(row)" size="small">
              删除指标
            </el-button>
          <!-- </el-badge> -->
          <el-button type="danger"  @click="deleteTable(row)" size="small">
            删除该表
          </el-button>
          <el-button type="primary"  @click="selectTable(row)" size="small">
            查看详细
          </el-button>
          <el-button type="success" icon="el-icon-s-data" plain  @click="createTable(row)" size="small" >
            生成展示表
          </el-button>
        </template>
      </el-table-column>

    </el-table>
</div>
<!-- :visible.sync="addTableViewVisible" -->

    <el-dialog
        title="使用选择的指标创建展示表"
        v-loading="loading"
        element-loading-text="指标数据同步中"
        :visible.sync="dialogVisible">
        <div class="normal-title flex">
          <i class="el-icon-warning"></i>
          <span>已选择的指标</span>
        </div>
        <div class="table-wrap">
          <el-table :data="selectItems" height="450px"  fit :default-sort = "{prop: 'zbkey', order: 'ascending'}">
          <!-- <el-table-column label="已选择的指标" align="center"> -->

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
            <!-- </el-table-column> -->

            </el-table-column>
          </el-table>
        </div>
        <div class="filter-container" style="margin-top:0">
           起始时间：
          <el-date-picker type="month" v-model="startTime" place-holder="起始时间"
                          value-format="yyyy-MM-dd" style="width: 200px"></el-date-picker>
          截止时间：
          <el-date-picker type="month" v-model="endTime" place-holder="截止时间"
                        value-format="yyyy-MM-dd" style="width: 200px"></el-date-picker>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="displayCreate">确 定</el-button>
          <el-button @click="dialogVisible = false">取 消</el-button>
        </span>

      </el-dialog>


    <el-dialog
    title="增加指标"
    v-if="addTableViewVisible"
    :visible.sync="addTableViewVisible"
    width="950px">

      <!-- zchange用来给子组件调用，然后传送一个参数给父组件。 childdata为绑定传给子组件的值 -->
      <addTableView @zchange="zchange($event)" v-bind:childdata="childdata"/>

    </el-dialog>

    <el-dialog
    title="查看详细"
    v-if="selectTableViewVisible"
    :visible.sync="selectTableViewVisible"
    width="1200px">

      <!-- zchange用来给子组件调用，然后传送一个参数给父组件。 childdata为绑定传给子组件的值 -->
      <selctTableData v-bind:selectTableParam="selectTableParam"/>

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
import addTableView from '@/components/addZbTable'
import selctTableData from '@/components/tableDataView'
export default {
  name: 'newTableView',
  components: { addTableView, selctTableData ,myheader},
  data() {
    return {
      pageName:'主题库',
      zbflList: null,
      list: [],
      dialogVisible: false,
      tableName: '',
      listLoading: false,
      loading: false,
      pageParam:{
        currentPage: 1,
        total: 10
      },
      addTableViewVisible: false,
      selectTableViewVisible: false,
      selectItems: [],
      currentTable: '',
      //父组件传给子组件的变量
      childdata: '',
      selectTableParam: '',
      startTime:'',
      endTime:'',
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
    this.getTreeData();
  },
  watch:{
    filterText(val) {
      this.$refs.tree.filter(val.trim());
    },
    startTime(date){
      if(date!=null){
        let startDate=new Date(date.toString().replace(/-/g,"/"));
        let endDate=new Date(this.endTime.toString().replace(/-/g,"/"));
        if(startDate.getTime()>endDate.getTime()){
          this.$message({
            type:"error",
            message:"起始时间必须小于截止时间",
          })
          this.startTime=''
        }
      }
    },
    endTime(date){
      if(date!=null){
        let startDate=new Date(this.startTime.toString().replace(/-/g,"/"));
        let endDate=new Date(date.toString().replace(/-/g,"/"));
        if(startDate.getTime()>endDate.getTime()){
          this.$message({
            type:"error",
            message:"截止时间必须大于起始时间",
          })
          this.endTime=''
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
    }
  },
  methods: {
    getTreeData() {
      let me = this;
      this.$axios.get('getTableTypeTree', {}, function(r) {
        me.treeData = r.data;
      })
    },
    async getList(pageNum, tableName, tableType) {
      let me = this;
      //设置加载动画
      this.listLoading = true;
      this.$axios.get('getTableList',{pageNum: pageNum, tableName:tableName, tableType:tableType,createStartTime:this.createStartTime,createEndTime:this.createEndTime}, function(r){
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
    deleteTable(row) {
      //删除表以及表内的所有数据
      let me = this;
      this.$confirm('您确定删除该主题表及表内所有数据吗？', '提示', {
        cancelButtonClass:'btn-custom-cancel',
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //点击确认
        me.listLoading = true;
        this.$axios.get('deleteTable', {tableName:row.tableName}, function(r){
          if (r.data === 1) {
            me.$message({
              type: 'error',
              message: '删除失败！请先删除该主题表对应的展示表'
            });
            me.listLoading = false;
            return;
          }

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
      this.$confirm('您确定删除该指标及绑定指标的所有数据吗？', '提示', {
        cancelButtonClass:'btn-custom-cancel',
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //点击确认
        me.listLoading = true;
        this.$refs[row.tableName].clearSelection();
        this.$axios.get('deleteTableZb', {tableId:row.id, zbmc:zbrow.zbmc, zbId:zbrow.id},function(r){
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
      this.selectTableParam=row.tableName;
      this.selectTableViewVisible=true;

    },
    handleSelectionChange(val, row) {
      //取消其它table的选中状态
      if (this.currentTable != row.tableName) {
        var comp = this.$refs;
        console.log(comp);
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
    displayCreate() {
        let me = this;
        /*this.$prompt('请输入展示表名(系统会默认添加前缀ZBZS_)', '创建展示表', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern: /^[A-Z_]{1,}$/,
          inputErrorMessage: '表名格式不正确（只能使用大写字母以及下划线）'
        }).then(({ value }) => {
          //用户输入的表名
          let tableName = value;
          //选中的项
          let param = JSON.stringify(this.selectItems);
          //转圈动画
          me.listLoading = true;
          this.$axios.post('displayCreate/' + this.currentTable + '/' + tableName , {param}, function(r){
            //关闭转圈动画
            me.listLoading = false;
            me.$message({
              type: 'success',
              message: '成功创建表ZBZS_' + tableName + ',并同步' + r.data + '条新数据'
            })
          })

        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入'
          });
        });*/

        //选中的项
        let param = JSON.stringify(this.selectItems);
        let startDate = this.startTime;
        let endDate = this.endTime;
        //转圈动画
        me.loading = true;
        this.$axios.post('displayCreate/' + this.currentTable , {param,'startTime':startDate,'endTime':endDate}, function(r){
          //关闭转圈动画
          me.loading = false;
          if(r.errMsg==="表创建失败，请检查当前表是否已创建展示表！"){
            me.$message({
              type: 'error',
              message: r.errMsg
            });
          }else{
            me.$message({
              type: 'success',
              message: r.errMsg
            });
          }
          //跳转到展示库
          me.$router.push({path:'zsTableManage'});
        })

    },
    createTable(row){
      if (this.currentTable != row.tableName || this.selectItems.length === 0) {
        this.$message({
          type:"error",
          message:'请至少勾选一条指标!'
        });
        return;
      }
      this.startTime='';
      this.endTime='';
      this.dialogVisible=true;
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
    flushTable() {
      this.getList(this.pageParam.currentPage, this.tableName.toUpperCase(), this.tableType);
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
        me.listLoading = true;
        let param = JSON.stringify(this.selectItems);
        this.$refs[row.tableName].clearSelection();
        this.$axios.post('deleteTableZbList/' + row.id, {param},function(r){
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

<style type="text/css">

  .zsBtn{
    position: fixed !important;
    right: 3%;
    bottom: 10%;

  }

.customer-table::before{
  width: 0;
}



/*  .customer-table {
    border: none;
  }

  .customer-table th{
    border:none;
  }
.customer-table td,.customer-table th.is-leaf {
  border:none;
}
// 表格最外边框
.el-table--border, .el-table--group{
  border: none;
}
// 头部边框
.customer-table thead tr th.is-leaf{
  border: 1px solid #EBEEF5;
  border-right: none;
}
.customer-table thead tr th:nth-last-of-type(2){
  border-right: 1px solid #EBEEF5;
}
// 表格最外层边框-底部边框
.el-table--border::after, .el-table--group::after{
  width: 0;
}*/

/*.customer-table .el-table__fixed-right::before,.el-table__fixed::before{
  width: 0;
}*/
/*// 表格有滚动时表格头边框
.el-table--border th.gutter:last-of-type {
    border: 1px solid #EBEEF5;
    border-left: none;
}*/
</style>
