<template>
  <div class="app-container">
    <myheader :name="pageName"></myheader>
    <div class="filter-container">
      <el-row>

          <span>保护清单识别号:</span>
          <el-input v-model="bhqdsbh" placeholder="请输入保护清单识别号" style="width: 190px;" clearable  @keyup.enter.native="searchData" />


          <span style="margin-left: 15px;">用户单位名称:</span>
          <el-input v-model="yhdwmc" placeholder="请输入用户单位名称" style="width: 190px;" clearable  @keyup.enter.native="searchData" />


          <span style="margin-left: 15px;">联系人:</span>
          <el-input v-model="lxr" placeholder="请输入联系人" style="width: 190px;" clearable  @keyup.enter.native="searchData" />

      </el-row>
      <el-row style="margin-top:10px">

           <span>确认起始时间:</span>
          <el-date-picker
            v-model="startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择起始时间"
            style="width: 190px;">
          </el-date-picker>


           <span style="margin-left: 15px;">确认截止时间:</span>
          <el-date-picker
            v-model="endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择截止时间"
            style="width: 190px;">
          </el-date-picker>
          <el-button type="primary" class="filter-item" icon="el-icon-search" @click="searchData" style="margin-left: 15px;">
            查询
          </el-button>

      </el-row>
    </div>
    <div style="padding:15px 15px 0 15px;height:calc(100vh - 334px);background:#fff;position:relative">
      <el-table v-loading="loading" :data="list" height="100%" stripe fit highlight-current-row >

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

        <el-table-column label=联系人 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.lxr}}</span>
          </template>
        </el-table-column>

        <el-table-column label=联系电话 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.lxdh}}</span>
          </template>
        </el-table-column>

        <el-table-column label=双向通信地球站 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.sxtxdqz}}</span>
          </template>
        </el-table-column>

        <el-table-column label=单收地球站 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.dsdqz}}</span>
          </template>
        </el-table-column>

        <el-table-column label=广播电视地球站 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.gbdsdqz}}</span>
          </template>
        </el-table-column>

        <el-table-column label=微波站 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.wbz}}</span>
          </template>
        </el-table-column>

        <el-table-column label=射电天文台 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.sdtwt}}</span>
          </template>
        </el-table-column>

        <el-table-column label=其他 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.qt}}</span>
          </template>
        </el-table-column>

        <el-table-column label=发射频率范围 width="150px" align="center">
          <template slot-scope="{row}">
            <span v-if="(row.zdfsplfw!=null&&row.zdfsplfw!='')||(row.zgfsplfw!=null&&row.zgfsplfw!='')">{{row.zdfsplfw}}-{{row.zgfsplfw}}</span>
          </template>
        </el-table-column>

        <el-table-column label=接收频率范围 width="150px" align="center">
          <template slot-scope="{row}">
            <span v-if="(row.zdjsplfw!=null&&row.zdjsplfw!='')||(row.zgjsplfw!=null&&row.zgjsplfw!='')">{{row.zdjsplfw}}-{{row.zgjsplfw}}</span>
          </template>
        </el-table-column>

        <el-table-column label=空间无线电台名称 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.kjwxdzmc}}</span>
          </template>
        </el-table-column>

        <el-table-column label=位置 width="150px" align="left">
          <template slot-scope="{row}">
            <span>{{row.wz}}</span>
          </template>
        </el-table-column>

        <el-table-column label=东经坐标 width="150px" align="center">
          <template slot-scope="{row}">
            <span v-if="row.djzb.indexOf('.')>0">{{row.djzb.substr(0,row.djzb.indexOf('.')+5)}}</span>
            <span v-if="row.djzb.indexOf('.')<=0">{{row.djzb}}</span>
          </template>
        </el-table-column>

        <el-table-column label=北纬坐标 width="150px" align="center">
          <template slot-scope="{row}">
            <span v-if="row.bwzb.indexOf('.')>0">{{row.bwzb.substr(0,row.bwzb.indexOf('.')+5)}}</span>
            <span v-if="row.bwzb.indexOf('.')<=0">{{row.bwzb}}</span>
          </template>
        </el-table-column>

        <el-table-column label=台站信息确认 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.tzxxqr}}</span>
          </template>
        </el-table-column>

        <el-table-column label=确认时间 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.qrsj}}</span>
          </template>
        </el-table-column>

        <el-table-column label=核验人 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.jyr}}</span>
          </template>
        </el-table-column>

        <el-table-column label=备注 width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{row.bz}}</span>
          </template>
        </el-table-column>
      </el-table>
      <dataCount  dataType="SATELLITE_EARTH_STATION" />
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

  import dataCount from '@/components/dataCompare'
  import myheader from '@/components/header'
  export default {
    components: { dataCount,myheader },
    data(){
      return{
        pageName:'卫星地球站数据',
        list:[],
        loading:false,
        pageParam:{
          currentPage:1,
          total:10,
        },
        bhqdsbh:'',
        yhdwmc:'',
        lxr:'',
        startTime:'',
        endTime:'',
      }
    },
    created(){
      this.getData(1);
    },
    watch:{
      startTime(date){
        let startDate=new Date(date.toString().replace(/-/g,"/"));
        let endDate=new Date(this.endTime.toString().replace(/-/g,"/"));
        if(startDate.getTime()>endDate.getTime()){
          this.$message({
            type:"error",
            message:"起始时间必须小于截止时间",
          })
          this.startTime=''
        }
      },
      endTime(date){
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
    methods:{
      async getData(pageNum,bhqdsbh,yhdwmc,lxr,startTime,endTime){
        let me = this;
        me.loading=true;
        this.$axios.get('5G/getEarthStation',{pageNum: pageNum,bhqdsbh:bhqdsbh,yhdwmc:yhdwmc,lxr:lxr,startTime:startTime,endTime:endTime}, function(r){
          me.loading=false;
          me.list=r.data.list;
          me.pageParam.total=r.data.total;
        })
      },
      changePage(){
        this.getData(this.pageParam.currentPage,this.bhqdsbh,this.yhdwmc,this.lxr,this.startTime,this.endTime);
      },
      searchData(){
        this.pageParam.currentPage=1;
        this.getData(this.pageParam.currentPage,this.bhqdsbh,this.yhdwmc,this.lxr,this.startTime,this.endTime)
      }
    },
  }

</script>

