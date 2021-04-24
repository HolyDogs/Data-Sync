<template>
  <div class="app-container page_01" align="center" style="height:calc(100vh - 64px)">
    <el-container>
      <el-aside width="16.66666%">
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
            </span>
          </template>
        </el-tree>

      </el-aside>

      <el-main width="60%">
         <myheader :name="pageName"></myheader>
            <div class="filter-container">
      <span>指标名称：</span>
      <el-input v-model="listQuery.zbmc" clearable placeholder="请输入指标名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />

  <!--     <span>绑定指标：</span>
      <el-input v-model="listQuery.bindZb" placeholder="绑定指标" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" /> -->

      <span style="margin-left: 15px;">指标状态：</span>
      <el-select v-model="listQuery.state" clearable placeholder="请选择">
        <el-option
          v-for="item in selectOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>

      <el-button style="margin-left: 15px;" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>

      <el-button style="margin-left: 15px;" class="filter-item" type="primary" icon="el-icon-s-tools" @click="setNoMatch">
        置为无匹配项
      </el-button>

    </div>
    <div style="padding:15px 15px 0 15px;height:calc(100vh - 285px);background:#fff">
      <el-table v-loading="listLoading" :data="list" :stripe="true" height="100%" fit highlight-current-row style="width: 100%;"
      @selection-change="handleSelectionChange">

        <el-table-column
          type="selection"
        ></el-table-column>

  <!--           <el-table-column
              type="index"
              width="50">
            </el-table-column>
  -->

        <el-table-column align="center" min-width="100px" label="指标编码" prop="zbkey">
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

        <el-table-column align="left" min-width="120px" label="指标来源">
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

  <!--
        <el-table-column width="100px" align="center" label="绑定指标">
          <template slot-scope="{row}">
            <el-button type="primary"  @click="bindZbView(row.bindZb)">
              查看
            </el-button>
          </template>
        </el-table-column>
  -->
  <!-- 绑定多个指标时显示不合理 -->
  <!--       <el-table-column width="180px" align="center" label="绑定指标来源表">
          <template slot-scope="{row}">
            <span>{{ row.bindZbTable }}</span>
          </template>
        </el-table-column> -->
        <el-table-column align="center" min-width="180px" label="指标别名">
          <template slot-scope="{row}">
            <el-tooltip content="回车保存" placement="top">
              <el-input v-model="row.comments" maxlength="254" size="medium" type="textarea" :autosize="{ minRows: 1, maxRows: 2}" @input="changeComment(row, row.comments)" placeholder="指标别名" @keyup.enter.native="saveComments(row)"></el-input>
            </el-tooltip>
          </template>
        </el-table-column>

        <el-table-column align="center" min-width="100px" label="计量单位">
          <template slot-scope="{row}">
            <el-tooltip content="回车保存" placement="top">
              <el-input v-model="row.jldw" maxlength="10" @keyup.enter.native="saveJldw(row)"></el-input>
            </el-tooltip>
          </template>
        </el-table-column>

        <el-table-column align="center" min-width="100px" label="创建时间">
          <template slot-scope="{row}">
            <span v-if="row.createTime!=null">{{ row.createTime.substr(0,10) }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center" min-width="100px" label="指标状态">
          <template slot-scope="{row}">
            <div class="flex juct-c stateClu"  v-if="row.state === '0'">
              <el-tooltip content="无操作" placement="bottom" effect="dark">
                <img src="@/images/state_01.png" alt="">
              </el-tooltip>
              <el-tooltip content="无匹配项" placement="bottom" effect="dark">
                <img src="@/images/state_02.png" alt="">
              </el-tooltip>
            </div>
            <div class="flex juct-c stateClu"  v-else-if="row.state === '1'">
              <el-tooltip content="已操作" placement="bottom" effect="dark">
                <img src="@/images/state_ac01.png" alt="">
              </el-tooltip>
              <el-tooltip content="无匹配项" placement="bottom" effect="dark">
                <img src="@/images/state_02.png" alt="">
              </el-tooltip>
            </div>
            <div class="flex juct-c stateClu"  v-else-if="row.state === '2'">
              <el-tooltip content="已操作" placement="bottom" effect="dark">
                <img src="@/images/state_ac01.png" alt="">
              </el-tooltip>
              <el-tooltip content="有匹配项" placement="bottom" effect="dark">
                <img src="@/images/state_ac02.png" alt="">
              </el-tooltip>
            </div>
            <div class="flex juct-c stateClu"  v-else-if="row.state === '3'">
              <el-tooltip content="已操作" placement="bottom" effect="dark">
                <img src="@/images/state_ac01.png" alt="">
              </el-tooltip>
              <el-tooltip content="有疑似匹配项" placement="bottom" effect="dark">
                <img src="@/images/state_ac02.png" alt="">
              </el-tooltip>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="操作" align="center" min-width="300px">
          <template slot-scope="{row,$index}">
            <el-button type="primary"  @click="handleEdit(row)" size="small">
              绑定
            </el-button>
            <el-button type="primary"  @click="unbundZb(row)" size="small" :disabled="row.state=='1'||row.state=='0'?true:false">
              解绑
            </el-button>
            <el-button type="primary"  @click="bindZbView(row.bindZb)" size="small">
              查看绑定指标
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>



      <el-dialog
        title="查看绑定指标"
        v-loading="bindzbLoading"
        :visible.sync="bindzbViewVisible"
        width="850px"
        v-if="bindzbViewVisible"
        append-to-body>
        <bindZB :bindZbData="bindZbData"></bindZB>
        <!--<el-table :data="bindZbData" empty-text="当前指标无绑定指标"  fit>

          <el-table-column align="center" label="指标名称">
            <template slot-scope="{row}">
              <span>{{ row.zbmc }}</span>
            </template>
          </el-table-column>

          <el-table-column align="center" label="指标来源">
            <template slot-scope="{row}">
              <span>{{ $strTransfer.dataSourceTransfer(row.sourceMark) }}</span>
            </template>
          </el-table-column>

          <el-table-column align="center" label="指标分类">
            <template slot-scope="{row}">
              <span>{{ row.zbfl }}</span>
            </template>
          </el-table-column>

        </el-table>
-->
        <span slot="footer" class="dialog-footer">
          <el-button @click="bindzbViewVisible = false">关 闭</el-button>
        </span>

      </el-dialog>

    <el-dialog
      custom-class="bindDialog"
      title="修改绑定指标"
      :visible.sync="dialogVisible"
      v-if="dialogVisible"
      width="1200px">
      <!-- <h3 align="left">当前选择指标：{{dialogItem.zbmc}}</h3> -->
      <el-transfer align="left"
        filterable
        filter-placeholder="查询"
        v-model="dialogItem.bindZb"
        :data="dialogItem.zbList"
        :lazy="20"
        :titles="['可绑定指标', '已绑定指标']"
        :button-texts="['','']"
        :render-content="renderFunc"
        :format="{
          noChecked: '${total}条数据',
          hasChecked: '${checked}/${total}条数据'
        }"
        >
      </el-transfer>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateBindZb">确 定</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>


    <el-pagination
        background
        layout="total, prev, pager, next, jumper"
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
  name: 'InlineEditTable',
  components: { myheader ,bindZB},
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      pageName:'指标池管理',
      list: [],
      listLoading: false,
      dialogVisible: false,
      bindzbViewVisible: false,
      bindzbLoading: false,
      bindZbData: [],
      dialogItem: {
        id: '',
        bindZb: [],
        comments: '',
        zbList: [],
        sourceMark: '',
        zbmc: ''
      },
      listQuery:{
        zbmc: '',
        bindZb: '',
        state: '',
        lyTable: '',
        zbfl: ''
      },
      pageParam:{
        currentPage: 1,
        total: 10
      },
      selectOptions:[{
        value: '0',
        label: '无操作'
      },{
        value: '1',
        label: '已操作，无匹配项'
      },{
        value: '2',
        label: '已操作，有匹配项'
      },{
        value: '3',
        label: '已操作，有疑似匹配项'
      }],
      selectItems:[],

      filterText: '',
      treeData:[],
      defaultProps: {
        children: 'children',
        label: 'label',
        value: 'value',
        icon:'icon'
      },
      notifys:null,
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
    this.getList(1)
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val.trim());
    }
  },
  methods: {
     getTreeData() {
      let me = this;
      this.$axios.get('getTreeList', {}, function(r) {
        //TODO XFF 验收修改
        me.treeData = me.ysArr.concat( r.data );
        //me.treeData = r.data;
      })
    },
    async getList(pageNum, zbmc, bindZb, state, lyTable, zbfl) {
      let me = this;
      //设置加载动画
      this.listLoading = true;
      this.$axios.get('zbget',{pageNum: pageNum, zbmc:zbmc, bindZb:bindZb, state:state, lyTable:lyTable, zbfl:zbfl}, function(r){
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
    handleEdit(row) {
      //打开编辑弹窗
      //当前选择项
      let me = this;
      this.dialogItem.id = row.id;
      this.dialogItem.zbmc = row.zbmc;
      if (row.bindZb != null) {
        //\n分隔
        this.dialogItem.bindZb = row.bindZb.split(",");
      } else {
        //为空时也要置空dialogItem
        this.dialogItem.bindZb = [];
      }
      if (this.dialogItem.zbList === null || this.dialogItem.zbList.length === 0 || me.dialogItem.sourceMark != row.sourceMark) {
        //防止多次请求重复数据增加服务器负担
        me.dialogItem.sourceMark = row.sourceMark;
        //请求指标名称列表
        this.$axios.get('getAllZb', {sourceMark: row.sourceMark}, function(r){
            me.dialogItem.zbList = r.data;
        })
      }
      this.dialogVisible = true;
    },
    querySearch(queryString, cb) {
      this.$axios.get('zbget', {pageNum: 1, zbmc:queryString, bindZb:''}, function(r){
        cb(r.data.list);
      })
    },
    updateBindZb(){
      this.dialogVisible = false;
      let me = this;

      var bindzbval = "";
      if (this.dialogItem.bindZb != null && this.dialogItem.bindZb.length > 0) {
        bindzbval = this.dialogItem.bindZb.join(",");
      }
      //更新指标
      this.$axios.post('zbUpdate/' + me.dialogItem.id, {bindzbval}, function(r){
        let alertType='error'
        if(r.errMsg=='保存成功！'){
          alertType='success'
        }
        //提示更新结果
        me.$message({
          type:alertType,
          message:r.errMsg
        });
        //重新刷新表单
        me.flushTable();
      });
    },
    handleSelectionChange(val){
      this.selectItems = val;
    },
    setNoMatch(){
      if (this.selectItems.length===0) {
        this.$message({
          type:"error",
          message:'请勾选指标后再试'
        });
        return;
      }
      var ids=[];
      for (var i = this.selectItems.length - 1; i >= 0; i--) {
        ids.push(this.selectItems[i].id);
      }
      let me = this;
      this.$axios.get('setPoolZbState',{ids:ids + '', state:"1"},function(r){
        //提示更新结果
        me.$message({
          type:"success",
          message:r.errMsg
        })
        //重新刷新表单
        me.flushTable();
      })
    },
    unbundZb(row){
      let me = this;
      this.$confirm('您是否确认解绑？', '提示', {
          cancelButtonClass:'btn-custom-cancel',
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.$axios.get('unBundZb', {id:row.id}, function(r){
              let alertType='error';
              if(r.errMsg=='解绑成功'){
                alertType='success'
              }
              //提示解绑结果
              me.$message({
                type: alertType,
                message: r.errMsg
              });
              //重新刷新表单
              me.flushTable();
            })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消解绑'
          });
        });
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
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    flushTable(){
      this.getList(this.pageParam.currentPage, this.listQuery.zbmc, this.listQuery.bindZb, this.listQuery.state, this.listQuery.lyTable, this.listQuery.zbfl);
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
    saveComments(row) {
      let me = this;
      var str = row.comments.replace(/[\s\n\t]+/g, '');
      if (str.length > 50) {
        me.$message({
          type:"error",
          message:'保存失败，指标别名不能超过50个字'
        });
        return;
      }
      //更新指标
      this.$axios.get('commentUpdate', {id: row.id, comment: str}, function(r){
        //提示更新结果
        let type="error";
        if(r.errMsg=='更新成功！'){
          type="success"
        }
        me.$message({
          type:type,
          message:r.errMsg
        });
        //重新刷新表单
        me.flushTable();
      });

    },
    saveJldw(row){
      let me = this;
      this.$axios.get('jldwUpdate', {id: row.id, jldw: row.jldw.replace(/[\s\n\t]+/g, '')}, function(r){
        let type="error";
        if(r.errMsg=='更新成功！'){
          type="success"
        }
        me.$message({
          type:type,
          message:r.errMsg
        });
        me.flushTable();
      });
    },
    renderFunc(h,option){
      //穿梭框的自定义样式
      return <span title={option.label + '---' + option.source}>{option.label + '---' + option.source}</span>;
    },
    changeComment(a, b) {
      const h = this.$createElement;
      var str = b.replace(/[\s\n\t]+/g, '');
      if (str.length > 50) {
        if(this.notifys!=null){
          this.notifys.close();
        }
        this.notifys=this.$notify({
          title: '提示',
          message: h('i', { style: 'color: red'}, '不超过50个字')
        });
      }
    }
  }
}
</script>