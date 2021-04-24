<template>
  <footer class="copy">
    <h4 style="color:#9563e7">数据量总计<span style="color:red">{{dataCount}}</span>条,较上月数据量增加<span style="color:red">{{addCount}}</span>条</h4>
  </footer>
</template>

<script>
export default{
	name: 'dataCompare',
	props: ['dataType'],
	data() {
		return {
			dataCount: 0,
			addCount: 0
		}
	},
	created() {
		let me = this;
		this.$axios.get('getDataCount', {dataType:this.dataType},function(r){
			me.dataCount = r.data.total;
			me.addCount = r.data.addNum;
		});
	}
}
</script>

<style type="text/css">
	.copy{
		position:absolute;
    text-align: left;
    left: 15px;
		bottom: -60px;
		width: 500px;
    height: 50px;
    z-index: 999;
	}
</style>