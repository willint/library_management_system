<template>
    <div>
      <vue-picker :selected-index="selectedIndex" @select="handleSelect" :data="linkageData"
          ref="picker" :title="title" @change="handleChange" :cancelTxt="cancelTxt" :confirmTxt="confirmTxt"></vue-picker>	
    </div>
</template>
<script>
    import vuePicker from 'vue-bspicker'
    import {
        province,
        city,
        area
    } from '../../service/common/city-data.js'
    	const COMPONENT_NAME = 'vue-city-picker'	
    export default {
        name: COMPONENT_NAME,
components: {
			vuePicker
		},
		props: {
	      	title: {
	        	type: String,
	        	default: '请选择'
	      	},
	      	cancelTxt: {
	        	type: String,
	        	default: '取消'
	      	},
	      	confirmTxt: {
	        	type: String,
	        	default: '确认'
	      	},
	      	selectedIndex: {
		        type: Array,
		        default() {
		          return [0,0,0]
		        }
		    },
		   
	    },
	    computed: {
	      	linkageData: function() {
	      		this.data = this.data.length > 0 ? this.data : [province, city, area]
	      		const provinceList = this.data[0]
      			const cityList = this.data[1].filter((item) => {
				 	return item.parentId === provinceList[this.tempIndex[0]].value
				})

				let areaList = [];

      			if(cityList.length > 0){
					areaList = this.data[2].filter((item) => {
						return item.parentId === cityList[this.tempIndex[1]].value
					})
      			}
				
		        return [provinceList, cityList, areaList]
	      	}
	    },

		data: function(){
			return {
				tempIndex: this.selectedIndex.slice(),
				data:[]
		    }
		},
		watch: {
	        linkageData: function() {
	        	this.$refs.picker.refresh()
	        }
	    },
		methods: {
			show: function() {
	        	this.$refs.picker.show()
	      	},
	      	handleSelect: function() {
		        this.$emit('select', ...arguments)
		    },
	      	handleChange: function(i, newIndex){
	      		if (newIndex !== this.tempIndex[i]) {
		          	for (let j = 2; j > i; j--) {
		            	this.tempIndex.splice(j, 1, 0)
		            	this.$refs.picker.scrollTo(j, 0)
		          	}
		          	this.tempIndex.splice(i, 1, newIndex)
		        }
	      	}
		}
    }
</script>
<style scoped>
div.wheel{
    -webkit-box-flex: 1;
    -ms-flex: 1;
    flex: 1;
    text-align: center;
    height: 173px;
    overflow: hidden;
    font-size: 10px !important;
}
</style>
