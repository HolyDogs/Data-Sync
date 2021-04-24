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
          @node-click="nodeClick"
          ref="tree">
          <template slot-scope="{node,data}" class="customize-tree-p">
            <span class="tree-node-flex flex">
              <span class="el-tree-node__label" :class="{'hasNew': data.hasNew=='true' }">{{node.hasNew}}{{node.label}}</span>
              <i :class="{'el-icon-success active': data.icon }"></i>
            </span>
          </template>
        </el-tree>
      </el-aside>

  <el-main>
    <myheader :name="pageName"></myheader>
    <div class="filter-container">
<!--       <span>来源名称：</span>
      <el-select v-model="listQuery.lymc" clearable placeholder="请选择">
        <el-option
          v-for="item in selectOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select> -->

<!--       <span>指标分类：</span>
      <el-select v-model="listQuery.zbfl" filterable clearable placeholder="请选择">
        <el-option
          v-for="item in zbflList"
          :key="item"
          :label="item"
          :value="item">
        </el-option>
      </el-select> -->

      <span>指标名称：</span>
      <el-input v-model="listQuery.zbmc" placeholder="请输入指标名称" clearable style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />

      <el-button class="filter-item" type="primary" style="margin-left: 15px;" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>

      <el-badge :value="selectItems.length" class="item" style="margin-left: 15px;" align="right">
          <el-button type="primary" class="filter-item" icon="el-icon-upload2" @click="createZTtable">创建主题表</el-button>
      </el-badge>

      <el-dialog
        title="使用选择的指标创建主题表"
        v-loading="loading"
        width="900px"
        element-loading-text="指标数据同步中"
        :visible.sync="dialogVisible">
        <div class="normal-title flex">
          <i class="el-icon-warning"></i>
          <span>已选择的指标（会将绑定指标一起加入专题）</span>
        </div>
        <div class="table-wrap">
          <el-table :data="selectItems" height="26.04%"  fit style="max-height:350px" :default-sort = "{prop: 'zbkey', order: 'ascending'}">
          <!-- <el-table-column label="已选择的指标（会将绑定指标一起加入专题）" align="center"> -->

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
            <!-- </el-table-column> -->

            </el-table-column>
          </el-table>
        </div>

        <div class="bgform-wrap">
          <el-form class="bgform" :model="newTableForm" ref="newTableForm" :label-position="labelPosition" label-width="120px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="起始时间:">
                  <el-date-picker type="month" v-model="startTime" placeholder="起始时间" style="width:260px;"
                      value-format="yyyy-MM-dd" ></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="截止时间:">
                  <el-date-picker type="month" v-model="endTime" placeholder="截止时间" style="width:260px;"
                      value-format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="输入主题表名:" prop="tableName" :rules="[{required:true, message: '请输入主题表名', trigger:'blur'}]">
                  <el-input v-model="newTableForm.tableName" style="width:260px;" placeholder="大写字母与下划线组合"  maxlength="10"><template slot="prepend">ZB_</template></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <span style="color:#4A7FEA;line-height:40px">例：ZB_</span>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="输入主题描述:" prop="tableComments" :rules="[{required:true, message: '请输入主题描述'}]">
                  <el-input v-model="newTableForm.tableComments"  style="width: 260px;" maxlength="20"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <span style="color:#FF9414;line-height:40px">注：主题表命名方式为大写字母与下划线的组</span>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="主题库类型:" prop="tableType" :rules="[{required:true, message: '请选择主题库类型'}]">
                  <el-select v-model="newTableForm.tableType" style="width: 260px;" clearable placeholder="请选择">
                      <el-option
                        v-for="item in tableTypeList"
                        :key="item.itemKey"
                        :label="item.itemValue"
                        :value="item.itemKey">
                      </el-option>
                    </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="createNewTable">确 定</el-button>
          <el-button @click="cancelClick">取 消</el-button>
        </span>

      </el-dialog>

    </div>


<div style="padding:15px 15px 0 15px;height:calc(100vh - 290px);background:#fff">
    <el-table v-loading="listLoading" :data="list" ref="zbListRef" :row-key="getRowKey" fit height="100%" highlight-current-row style="width: 100%"
      @selection-change="handleSelectionChange">

      <el-table-column
        type="selection"
        width="55"
        :reserve-selection="true"
      ></el-table-column>

      <el-table-column align="center" label="指标编码" prop="zbkey">
        <template slot-scope="{row}">
          <span>{{ row.zbkey }}</span>
        </template>
      </el-table-column>

      <el-table-column align="left" min-width="200px" label="指标名称">
        <template slot-scope="{row}">
          <span style="vertical-align: super;">{{ row.zbmc }}</span>
          <span v-if="row.isNewZb==='true'"><img src="@/images/new_pic.png" class="newzb"/></span>
        </template>
      </el-table-column>

      <el-table-column align="left" label="指标来源">
        <template slot-scope="{row}">
          <span>{{ $strTransfer.dataSourceTransfer(row.sourceMark) }}</span>
        </template>
      </el-table-column>

      <el-table-column align="left" min-width="200px" label="来源表">
        <template slot-scope="{row}">
          <span>{{ row.sourceMark }}</span>
        </template>
      </el-table-column>

      <el-table-column align="left" min-width="200px" label="指标分类">
        <template slot-scope="{row}">
          <span>{{ row.zbfl }}</span>
        </template>
      </el-table-column>

      <el-table-column align="left" label="指标别名">
        <template slot-scope="{row}">
          <span>{{ row.comments }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="创建时间">
        <template slot-scope="{row}">
          <span v-if="row.createTime!=null">{{ row.createTime.substr(0,10) }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作">
        <template slot-scope="{row}">
          <el-button type="primary"  @click="bindZbView(row.bindZb)" size="small">
            查看绑定指标
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
</div>

      <el-dialog
        title="查看绑定指标"
        width="850px"
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
        layout="prev, pager, next, jumper"
        v-bind.sync="pageParam"
        @current-change="changePage"
        >
    </el-pagination>

      </el-main>
    </el-container>

  </div>
</template>
<script>
import myheader from '@/components/header'
import bindZB from '@/components/bindZB'

export default {
  name: 'newTableView',
  components:{bindZB,myheader},
  data() {
    return {
      pageName:'创建主题',
      labelPosition: "right",
      zbflList: null,
      list: [],
      dialogVisible: false,
      bindzbViewVisible: false,
      bindzbLoading: false,
      bindZbData: [],
      newTableForm: {
        tableComments: '',
        tableName:'',
        tableType:''
      },
      listLoading: false,
      loading: false,
      listQuery:{
        zbmc: '',
        zbfl: '',
        lyTable: ''
      },
      pageParam:{
        currentPage: 1,
        total: 10
      },
      selectItems:[],
/*      selectOptions:[{
        value: 'DIC_STATISTIC_ZB',
        label: '国家数据网'
      },{
        value: 'YW_SYSTEM_DATA_MOVE',
        label: '工业运行监测'
      }],*/
      filterText: '',
      treeData:[],
      defaultProps: {
        children: 'children',
        label: 'label',
        value: 'value',
        icon: 'icon'
      },
      startTime:'',
      endTime:'',
      tableTypeList:[],

      ysArr:[{
        label:'一网通办系统',
        value:'一网通办系统',
        children:[{label:'',value:''}]
      },{
        label:'精准帮扶系统',
        value:'精准帮扶系统',
        children:[{label:'',value:''}]
      },{
        label:'电子政务系统',
        value:'电子政务系统',
        children:[{label:'',value:''}]
      },{
        label:'智慧园区系统',
        value:'智慧园区系统',
        children:[{label:'',value:''}]
      },{
        label:'工业能耗在线监测',
        value:'工业能耗在线监测',
        children:[{label:'',value:''}]
      },{
        label:'能源预测预警',
        value:'能源预测预警',
        children:[{label:'',value:''}]
      }]
    }
  },
  created() {
    this.getTreeData();
    this.getList(1);
    this.getzbflList();
    this.getTableTypeList();
  },
  watch: {
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
      if(date!=null) {
        let startDate = new Date(this.startTime.toString().replace(/-/g, "/"));
        let endDate = new Date(date.toString().replace(/-/g, "/"));
        if (startDate.getTime() > endDate.getTime()) {
          this.$message({
            type: "error",
            message: "截止时间必须大于起始时间",
          })
          this.endTime = ''
        }
      }
    }
  },
  methods: {
    getTableTypeList() {
      let me = this;
      this.$axios.get('getTableTypeSelectList', {}, function(r) {
        me.tableTypeList = r.data;
      });
    },
    getTreeData() {
      let me = this;
      this.$axios.get('getTreeList', {}, function(r) {
        //TODO XFF 验收修改
        me.treeData = me.ysArr.concat( r.data );
        //me.treeData = r.data;
      })
    },
    getzbflList() {
      let me = this;
      this.$axios.get('getZbflList',{}, function(r){
        me.zbflList = r.data;
      });
    },
    async getList(pageNum, zbmc, zbfl, lyTable) {
      let me = this;
      //设置加载动画
      this.listLoading = true;
      this.$axios.get('zbget',{pageNum: pageNum, zbmc:zbmc, zbfl:zbfl, lyTable:lyTable}, function(r){
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
    handleSelectionChange(val){
      console.log(val)
      let tree = this.treeData;
      //清空勾选的指标分类
      for (var i = 0; i < tree.length; i++) {
        tree[i].icon = false;
        for (var z = 0;z < tree[i].children.length; z++) {
          tree[i].children[z].icon = false;
        }
      }

      this.selectItems = val;
      for (var i = 0; i < val.length; i++) {
        for (var a = 0; a < tree.length; a++) {
          if (tree[a].value == val[i].sourceMark) {
            tree[a].icon = true;
            for (var m = 0; m < tree[a].children.length; m++) {
              if (tree[a].children[m].value == val[i].zbfl) {
                tree[a].children[m].icon = true;
              }
            }
          }
        }
      }

      this.treeData = tree;
    },
    getRowKey(row) {
      return row.id;
    },
    nodeClick(data, node, self) {
      if (node.level === 2) {
        //二级子树
        this.listQuery.lyTable = node.parent.data.value;
        this.listQuery.zbfl = data.value;
      } else{
        //一级
        this.listQuery.lyTable = data.value;
        this.listQuery.zbfl = '';
      }
      //设置第一页
      this.pageParam.currentPage = 1;
      this.flushTable();

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
    createNewTable() {

      let validFlag = 0;
      //表单验证必填项
      this.$refs['newTableForm'].validate((valid) => {
        if (valid) {
          validFlag = 1;
        } else {
          validFlag = 0;
        }
      });
      if (validFlag === 0) {
        return;
      }


      var zbList = '';
      //获取所有指标id以及绑定指标并拼接
      for (var i = this.selectItems.length - 1; i >= 0; i--) {
        zbList = zbList + this.selectItems[i].id + ",";

        //绑定指标改为在后台进行连带插入
        /*var bindzb = this.selectItems[i].bindZb;
        if (bindzb != null && bindzb != '') {
          zbList = zbList + bindzb + ",";
        }*/

      }

      //去掉最后一个逗号
      zbList = zbList.substr(0, zbList.length-1);
      let me = this;
      //加载动画
      me.loading = true;
      //请求创建表
      this.$axios.get('tableCreate', {tableName:me.newTableForm.tableName, zbList:zbList, tableComments: me.newTableForm.tableComments.trim()
        ,startTime:me.startTime,endTime:me.endTime, tableType:me.newTableForm.tableType}, function(r){
          if(r.errMsg === '创建主题表成功!'){
            me.$message({
              type:"success",
              message:r.errMsg
            });
          }else{
            me.$message({
              type:"error",
              message:r.errMsg
            });
          }
          //清空状态
          me.newTableForm.tableName = '';
          me.newTableForm.tableComments = '';
          me.newTableForm.tableType = '';
          me.loading = false;
          //关闭弹窗
          me.dialogVisible = false;
          if (r.errMsg === '创建主题表成功!') {
            me.$router.push({path:'/tableManage'});
          }
      });

    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    flushTable() {
      this.getList(this.pageParam.currentPage, this.listQuery.zbmc, this.listQuery.zbfl, this.listQuery.lyTable);
    },
    cancelClick() {
      this.dialogVisible = false;
      //清空输入信息，修复bug
      this.newTableForm.tableName = '';
      this.newTableForm.tableComments = '';
      this.newTableForm.tableType = '';
    },
    createZTtable(){
      if(this.selectItems.length<=0){
        this.$message({
          type:"error",
          message:"请至少勾选一个指标"
        });
      }else{
        this.newTableForm.tableName = '';
        this.newTableForm.tableComments='';
        this.newTableForm.tableType='';
        this.startTime='';
        this.endTime='';
        this.dialogVisible = true;
      }
    }
  }
}
</script>

<style type="style/css">
.el-form-item__error{
  padding-top:unset;
}
</style>
