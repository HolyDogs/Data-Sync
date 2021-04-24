<template>
  <header class="flex">
    <img src="@/images/cur_page.png" alt="">
  	<span>{{name}}</span>
  </header>
</template>
<script>
export default {
  name:'myheader',
  props:{
      name:String
    
  }
}
</script>
<style type="text/css" scoped>
  .flex span{
    height: 32px;
    font-size: 22px;
    font-weight: bold;
    font-family: AlibabaPuHuiTiB;
    color: #46494F;
  }
</style>