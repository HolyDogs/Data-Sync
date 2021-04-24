<template>
  <div class="app-container">
    <myheader :name="pageName"></myheader>
    <div class="filter-container">
      <el-row>
        <span>保护清单识别号:</span>
        <el-input v-model="bhqdsbh" placeholder="请输入保护清单识别号" style="width: 190px;" clearable  @keyup.enter.native="searchData" />
        <span style="margin-left: 15px;">用户单位名称:</span>
        <el-input v-model="yhdwmc" placeholder="请输入用户单位名称" style="width: 190px;" clearable  @keyup.enter.native="searchData" />
        <span style="margin-left: 15px;">是否需要协调:</span>
        <el-select v-model="sfxyxt" filterable clearable placeholder="请选择" style="width: 100px">
          <el-option
            v-for="item in trueOrFalse"
            :value="item"
            :key="item"
            :label="item">
          </el-option>
        </el-select>
        <span style="margin-left: 15px;">是否已发起协调请求:</span>
        <el-select v-model="sfyfqxtqq" filterable clearable placeholder="请选择" style="width: 100px">
          <el-option
            v-for="item in trueOrFalse"
            :value="item"
            :key="item"
            :label="item">
          </el-option>
        </el-select>
      </el-row>
      <el-row style="margin-top:10px">
        <span>是否已确认协调请求:</span>
        <el-select v-model="sfyqrxtqq" filterable clearable placeholder="请选择" style="width: 100px">
          <el-option
            v-for="item in trueOrFalse"
            :value="item"
            :key="item"
            :label="item">
          </el-option>
        </el-select>
        <span style="margin-left: 15px;">是否已完成协调:</span>
        <el-select v-model="sfywcxt" filterable clearable placeholder="请选择" style="width: 100px">
          <el-option
            v-for="item in trueOrFalse"
            :value="item"
            :key="item"
            :label="item">
          </el-option>
        </el-select>
        <el-button type="primary" class="filter-item" icon="el-icon-search" @click="searchData" style="margin-left: 15px;">
          查询
        </el-button>
      </el-row>
    </div>
    <div style="padding:15px 15px 0 15px;height:calc(100vh - 334px);background:#fff;position:relative">
      <el-table v-loading="loading" :data="list"  height="100%" stripe fit highlight-current-row >

        <el-table-column label=保护清单识别号 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.bhqdsbh}}</span>
          </template>
        </el-table-column>

        <el-table-column label=用户单位名称 width="150px" align="left">
          <template slot-scope="{row}">
            <span>{{row.yhdwmc}}</span>
          </template>
        </el-table-column>

        <el-table-column label=台站经度（°） width="150px" align="center">
          <template slot-scope="{row}">
            <span v-if="row.tzjd.indexOf('.')>0">{{row.tzjd.substr(0,row.tzjd.indexOf('.')+5)}}</span>
            <span v-if="row.tzjd.indexOf('.')<=0">{{row.tzjd}}</span>
          </template>
        </el-table-column>

        <el-table-column label=台站纬度（°） width="150px" align="center">
          <template slot-scope="{row}">
            <span v-if="row.tzwd.indexOf('.')>0">{{row.tzwd.substr(0,row.tzwd.indexOf('.')+5)}}</span>
            <span v-if="row.tzwd.indexOf('.')<=0">{{row.tzwd}}</span>
          </template>
        </el-table-column>

        <el-table-column label=是否需要协调 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.sfxyxt}}</span>
          </template>
        </el-table-column>

        <el-table-column label=是否已发起协调请求 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.sfyfqxtqq}}</span>
          </template>
        </el-table-column>

        <el-table-column label=是否已确认协调请求 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.sfyqrxtqq}}</span>
          </template>
        </el-table-column>

        <el-table-column label=是否已完成协调 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.sfywcxt}}</span>
          </template>
        </el-table-column>

        <el-table-column label=是否撤站 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.sfcz}}</span>
          </template>
        </el-table-column>

        <el-table-column label=撤站时间 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.czsj}}</span>
          </template>
        </el-table-column>

        <el-table-column label=是否迁址 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.sfqz}}</span>
          </template>
        </el-table-column>

        <el-table-column label=迁址后位置 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.qzhwz}}</span>
          </template>
        </el-table-column>

        <el-table-column label=迁址后东经（°） width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.qzhdj}}</span>
          </template>
        </el-table-column>

        <el-table-column label=迁址后北纬（°） width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.qzhbw}}</span>
          </template>
        </el-table-column>

        <el-table-column label=迁址时间 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.qzsj}}</span>
          </template>
        </el-table-column>

        <el-table-column label=是否调整频率 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.sftzpl}}</span>
          </template>
        </el-table-column>

        <el-table-column label=调整后接收频率（起）MHz width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.tzhjsplq}}</span>
          </template>
        </el-table-column>

        <el-table-column label=调整后接收频率（止）MHz width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.tzhjsplz}}</span>
          </template>
        </el-table-column>

        <el-table-column label=调整频率时间 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.tzplsj}}</span>
          </template>
        </el-table-column>

        <el-table-column label=是否属于受影响台站 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.sfsysyxtz}}</span>
          </template>
        </el-table-column>

      </el-table>
      <dataCount dataType="BASESTATION_COORDINATION" />
    </div>

    <el-pagination
      background
      layout="prev, pager, next,jumper"
      v-bind.sync="pageParam"
      @current-change="changePage"
    >
    </el-pagination>


  </div>
</template>

<script>
  import myheader from '@/components/header'
  import dataCount from '@/components/dataCompare'
  export default {
    components: { dataCount ,myheader},
    data(){
      return{
        pageName:'5G基站协调进度数据',
        list:[],
        loading:false,
        pageParam:{
          currentPage:1,
          total:10,
        },
        bhqdsbh:'',
        yhdwmc:'',
        sfxyxt:'',
        sfyfqxtqq:'',
        sfyqrxtqq:'',
        sfywcxt:'',
        trueOrFalse:['是','否']
      }
    },
    created(){
      this.getData(1);
    },
    methods:{
      async getData(pageNum){
        let me = this;
        me.loading=true;
        this.$axios.get('5G/getCoordination',{pageNum: pageNum,bhqdsbh:this.bhqdsbh,yhdwmc:this.yhdwmc,
          sfxyxt:this.sfxyxt,sfyfqxtqq:this.sfyfqxtqq,sfyqrxtqq:this.sfyqrxtqq,sfywcxt:this.sfywcxt}, function(r){

          me.loading=false;
          me.list=r.data.list;
          console.log(me.list)
          me.pageParam.total=r.data.total;
        })
      },
      changePage(){
        this.getData(this.pageParam.currentPage);
      },
      searchData(){
        this.pageParam.currentPage=1;
        this.getData(this.pageParam.currentPage)
      }
    },
  }

</script>

