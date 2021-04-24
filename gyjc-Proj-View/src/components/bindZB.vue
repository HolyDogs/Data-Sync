<template>
  <div class="table-wrap">
    <div class="input-box">
       <el-input
        placeholder="输入关键词"
        prefix-icon="el-icon-search"
        v-model="filterText"
        clearable
       >
      </el-input>
    </div>
    <el-table :data="tableData" empty-text="当前指标无绑定指标" fit height="400px" :default-sort = "{prop: 'zbkey', order: 'ascending'}">

      <el-table-column align="center" label="指标编码" prop="zbkey">
        <template slot-scope="{row}">
          <span>{{ row.zbkey }}</span>
        </template>
      </el-table-column>

      <el-table-column header-align="center" align="left" column-key="row.zbmc" label="指标名称" prop="zbmc">
        <template slot-scope="{row}">
          <span>{{ row.zbmc }}</span>
        </template>
      </el-table-column>

      <el-table-column header-align="center" align="center" label="指标来源">
        <template slot-scope="{row}">
          <span>{{ $strTransfer.dataSourceTransfer(row.sourceMark) }}</span>
        </template>
      </el-table-column>

      <el-table-column header-align="center" align="left" label="指标分类">
        <template slot-scope="{row}">
          <span>{{ row.zbfl }}</span>
        </template>
      </el-table-column>

    </el-table>

  </div>
</template>

<script>
    export default {
      props:['bindZbData'],
      data() {
          return {
            filterText:''
          }
      },
      computed: {
        tableData: function() {
          const filterText = this.filterText;
          if (filterText) {
              return this.bindZbData.filter(dataNews => {
                  return Object.keys(dataNews).some(key => {
                      //只对下列四个字段进行筛选
                      if (key==='zbkey' || key==='zbmc' || key==='zbly' || key==='zbfl') {
                        return String(dataNews[key]).indexOf(filterText) > -1
                      }
                  })
              })
          } else{
            return this.bindZbData;
          }
        }
      },
      methods:{

      }
    }

</script>

