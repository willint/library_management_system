(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-7195da19"],{"99ec":function(e,t,o){"use strict";o.r(t);var a=function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{staticClass:"table",staticStyle:{height:"100%"}},[o("div",{staticClass:"crumbs"},[o("el-breadcrumb",{attrs:{separator:"/"}},[o("el-breadcrumb-item",[o("i",{staticClass:"el-icon-lx-cascades"}),e._v(" 图书管理\n      ")])],1)],1),o("div",{staticClass:"container",staticStyle:{"min-height":"100%"}},[o("div",{staticClass:"handle-box"},[o("el-input",{staticClass:"handle-input mr10",attrs:{placeholder:"图书名称"},model:{value:e.query.name,callback:function(t){e.$set(e.query,"name",t)},expression:"query.name"}}),o("el-input",{staticClass:"handle-input mr10",attrs:{placeholder:"图书索引"},model:{value:e.query.index_code,callback:function(t){e.$set(e.query,"index_code",t)},expression:"query.index_code"}}),o("el-select",{staticClass:"handle-select mr10",attrs:{placeholder:"请选择图书类别"},model:{value:e.query.category,callback:function(t){e.$set(e.query,"category",t)},expression:"query.category"}},e._l(e.categoryList,(function(e){return o("el-option",{key:e.code,attrs:{label:e.name,value:e.code}})})),1),o("el-button",{attrs:{type:"primary",icon:"search"},on:{click:e.search}},[e._v("搜索")]),o("el-button",{directives:[{name:"show",rawName:"v-show",value:e.permissions.add,expression:"permissions.add"}],attrs:{type:"success",icon:"add"},on:{click:e.handleAdd}},[e._v("新增")])],1),o("el-table",{ref:"multipleTable",staticClass:"table",attrs:{data:e.tableData.results,border:"","highlight-current-row":""}},[o("el-table-column",{attrs:{type:"index",width:"50"}}),o("el-table-column",{attrs:{prop:"name",label:"图书名称",width:"120","show-overflow-tooltip":!0}}),o("el-table-column",{attrs:{prop:"category",label:"图书类别",width:"120","show-overflow-tooltip":!0,formatter:e.categoryFormatter}}),o("el-table-column",{attrs:{prop:"indexCode",label:"图书索引",width:"120","show-overflow-tooltip":!0}}),o("el-table-column",{attrs:{prop:"author",label:"作者",width:"120","show-overflow-tooltip":!0}}),o("el-table-column",{attrs:{prop:"location",label:"存放位置","show-overflow-tooltip":!0,width:"120"}}),o("el-table-column",{attrs:{prop:"operator",label:"操作人",width:"120","show-overflow-tooltip":!0}}),o("el-table-column",{attrs:{prop:"updateTime",label:"变更时间",formatter:e.formatter,"show-overflow-tooltip":!0},on:{"current-change":e.handleCurrentChange}}),o("el-table-column",{attrs:{label:"操作",width:"240",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[o("el-button",{directives:[{name:"show",rawName:"v-show",value:e.permissions.edit&&"1"!=t.row.isSys,expression:"permissions.edit && scope.row.isSys != '1'"}],attrs:{type:"text",icon:"el-icon-edit"},on:{click:function(o){return e.handleEdit(t.$index,t.row)}}},[e._v("编辑")]),o("el-button",{directives:[{name:"show",rawName:"v-show",value:e.permissions.edit&&"1"!=t.row.isSys,expression:"permissions.edit && scope.row.isSys != '1'"}],staticClass:"red",attrs:{type:"text",icon:"el-icon-delete"},on:{click:function(o){return e.handleDel(t.$index,t.row)}}},[e._v("删除")])]}}])})],1),o("div",{staticClass:"pagination"},[o("el-pagination",{attrs:{background:"","current-page":e.tableData.page,layout:"prev, pager, next",total:e.tableData.total},on:{"current-change":e.handleCurrentChange}})],1)],1),o("el-dialog",{attrs:{title:e.form.title,visible:e.editVisible,width:"80%"},on:{"update:visible":function(t){e.editVisible=t}}},[o("el-form",{ref:"form",attrs:{inline:!0,model:e.form,rules:e.formRules,"label-width":"100px"}},[o("el-form-item",{attrs:{label:"图书名称",prop:"name"}},[o("el-input",{attrs:{placeholder:"请输入图书名称"},model:{value:e.form.name,callback:function(t){e.$set(e.form,"name",t)},expression:"form.name"}})],1),o("el-form-item",{attrs:{label:"图书类别",prop:"category"}},[o("el-select",{attrs:{filterable:"",placeholder:"请选择图书类别"},model:{value:e.form.category,callback:function(t){e.$set(e.form,"category",t)},expression:"form.category"}},e._l(e.categoryList,(function(e){return o("el-option",{key:e.code,attrs:{label:e.name,value:e.code}})})),1)],1),o("el-form-item",{attrs:{label:"图书索引",prop:"indexCode"}},[o("el-input",{attrs:{placeholder:"请输入图书索引"},model:{value:e.form.indexCode,callback:function(t){e.$set(e.form,"indexCode",t)},expression:"form.indexCode"}})],1),o("el-form-item",{attrs:{label:"作者",prop:"author"}},[o("el-input",{attrs:{placeholder:"请输入作者名"},model:{value:e.form.author,callback:function(t){e.$set(e.form,"author",t)},expression:"form.author"}})],1),o("el-form-item",{attrs:{label:"存放位置",prop:"location"}},[o("el-input",{attrs:{placeholder:"请输入存放位置"},model:{value:e.form.location,callback:function(t){e.$set(e.form,"location",t)},expression:"form.location"}})],1),o("el-form-item",{attrs:{label:"图书摘要",prop:"introduction"}},[o("el-input",{attrs:{type:"textarea"},model:{value:e.form.introduction,callback:function(t){e.$set(e.form,"introduction",t)},expression:"form.introduction"}})],1),o("el-form-item",{attrs:{label:"简介图片",prop:"imgUrl"}},[o("el-upload",{ref:"upload1",staticClass:"idcard",attrs:{accept:"image/*",id:"idcard1",action:e.uploadAction,"list-type":"picture-card","before-upload":e.beforeUpload1,"on-remove":e.onRemove1,"on-error":e.onError1,data:e.uploadData,"on-success":e.onSuccess1}},[o("i",{staticClass:"el-icon-plus"})])],1)],1),o("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[o("el-button",{on:{click:function(t){e.editVisible=!1}}},[e._v("取 消")]),o("el-button",{directives:[{name:"loading",rawName:"v-loading.fullscreen.lock",value:e.fullscreenLoading,expression:"fullscreenLoading",modifiers:{fullscreen:!0,lock:!0}}],attrs:{type:"primary"},on:{click:e.saveEdit}},[e._v("确 定")])],1)],1),o("el-dialog",{attrs:{title:"提示",visible:e.delVisible,width:"300px",center:""},on:{"update:visible":function(t){e.delVisible=t}}},[o("div",{staticClass:"del-dialog-cnt"},[e._v("是否确定删除？")]),o("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[o("el-button",{on:{click:function(t){e.delVisible=!1}}},[e._v("取 消")]),o("el-button",{attrs:{type:"primary"},on:{click:e.deleteRow}},[e._v("确 定")])],1)])],1)},r=[];function l(e,t,o){return t in e?Object.defineProperty(e,t,{value:o,enumerable:!0,configurable:!0,writable:!0}):e[t]=o,e}o("7f7f"),o("57e7");var i=o("e8b6"),s=o("5a30"),n={prod:{serverUrl:"http://localhost:8080/lib-server/mg",imagesUrl:"http://localhost:8080/lib-server/images",fileUrl:"http://localhost:7009/lib-server/files"},dev:{serverUrl:"http://localhost:8080/lib-server/mg",imagesUrl:"http://localhost:8080/lib-server/images",fileUrl:"http://localhost:7009/lib-server/files"},local:{serverUrl:"http://localhost:8080/lib-server/mg",imagesUrl:"http://localhost:8080/lib-server/images",fileUrl:"http://localhost:7009/lib-server/files"}},c={};"127.0.0.1"==location.hostname&&(c=n["prod"]),c="localhost"==location.hostname?n["local"]:n["dev"],"www.baidu.com"==location.hostname&&(c=n["prod"]);var d=c,u={category:{1001:"考试",2001:"历史",20011:"近代史"},categoryList:[{name:"考试",code:"1001"},{name:"历史",code:"2001"},{name:"近代史",code:"20011"}],orderState:{0:"未付款",1:"付款处理中",2:"已付款",3:"等待发货",4:"已发货",5:"交易成功",6:"交易关闭",7:"订单异常",8:"已收货"},mobileCorp:{TEL:"电信",MOB:"移动"},orderType:{1:"入网订单",2:"企业订单",3:"套餐充值",5:"实名登记"},numState:{"10U":"待激活","10A":"在用","10B":"停用","10C":"停机保号","10X":"拆机","10D":"激活中"},payMode:{1:"微信支付",2:"支付宝",3:"代理商扣款"},payState:{0:"未支付",1:"支付中",2:"已支付"},state:{"01":"启用","02":"禁用"},payAttr:{"01":"自付费","02":"代理商扣款"},cardState:{"10U":"未初始化",Active:"已激活-Active",Deactivate:"去激活",Pause:"停机-Pause",Paused:"停机-Paused",Terminated:"拆机",1:"待激活",2:"已激活-2",4:"停机-4",6:"可测试",7:"库存",8:"预销户",9:"已销户"}},m=u,p=o("310e"),g=o.n(p),h={name:"basetable",data:function(){return{dragOptions:{animation:120,scroll:!0,group:"sortlist",ghostClass:"ghost-style"},tableData:{total:0,page:1,results:[]},categoryList:m.categoryList,uploadData:{type:"book"},loginUserId:"",loginInfo:{},multipleSelection:[],del_list:[],showAgentName:"",is_search:!1,editVisible:!1,delVisible:!1,stateVisible:!1,chargeVisible:!1,uploadAction:d.serverUrl+"/images/upload",permissions:{del:s["a"].permCheck("book:del"),edit:s["a"].permCheck("book:edit"),add:s["a"].permCheck("book:add")},query:{id:"",name:"",index_code:""},fullscreenLoading:!1,stateMap:{1:"有效",0:"无效"},form:{title:"",id:"",name:"",introduction:"",category:"",author:"",location:"",imgUrl:"",indexCode:"",createTime:""},chargeBillBalanceForm:{agentId:"",operatorId:"",agentName:"",chargeThreshold:0,chargeAmount:0,remark:""},idx:-1,formRules:{name:[{required:!0,message:"图书名称必填",trigger:"blur"}],category:[{required:!0,message:"图书类别必选",trigger:"change"}],author:[{required:!0,message:"作者信息必填",trigger:"blur"}],indexCode:[{required:!0,message:"图书索引必填",trigger:"blur"}],location:[{required:!0,message:"存放位置必填",trigger:"blur"}],introduction:[{required:!0,message:"摘要必填",trigger:"blur"}]},pwdform:{},restPwdVisible:!1,subAgentVisible:!1,subData:[],subValue:[],selectId:null,filterMethod:function(e,t){return t.label.indexOf(e)>-1},loading:null}},created:function(){this.loginInfo=s["a"].loginInfo(),this.loginUserId=this.loginInfo.user.id,this.getData()},computed:{data:function(){return this.tableData}},components:{draggable:g.a},methods:{handleCurrentChange:function(e){this.tableData.page=e,this.getData()},getData:function(){var e=this,t=e.$loading({lock:!0,text:"Loading",spinner:"el-icon-loading",background:"rgba(0, 0, 0, 0.7)"});i["a"].pageList("/book/pageList",e.tableData.page,10,e.query,(function(o){t.close(),e.tableData=o}))},search:function(){this.is_search=!0,this.handleCurrentChange(1)},formatter:function(e,t){return s["a"].formatter(e.updateTime,"yyyy-MM-dd hh:mm:ss")},handleAdd:function(){this.form={title:"新增",operType:"1"},this.$refs["form"]&&this.$refs["form"].clearValidate(),this.editVisible=!0},handleDel:function(e,t){this.idx=e,this.delVisible=!0},handleEdit:function(e,t){var o;this.idx=e,this.form=(o={operType:"2",id:t.id,name:t.name,introduction:t.introduction,category:t.category,author:t.author,location:t.location,imgUrl:t.imgUrl,indexCode:t.indexCode},l(o,"operType","2"),l(o,"title","编辑"),o),this.$refs["form"]&&this.$refs["form"].clearValidate(),this.editVisible=!0},categoryFormatter:function(e,t){return console.log(+m.category[e.category]),m.category[e.category]},showLoading:function(){this.loading=this.$loading({lock:!0,text:"Loading",spinner:"el-icon-loading",background:"rgba(0, 0, 0, 0.7)"})},hideLoading:function(){this.loading&&this.loading.close()},saveEdit:function(){var e=this,t=this.$loading({lock:!0,text:"Loading",spinner:"el-icon-loading",background:"rgba(0, 0, 0, 0.7)"});this.$refs["form"].validate((function(o){if(!o)return t.close(),!1;console.log(e.form),i["a"].post("/book/saveOrUpdate",e.form,(function(o){t.close(),i["a"].isOK(o)?(e.handleCurrentChange(1),e.editVisible=!1,e.$message.success("操作成功"),e.getData()):e.$message.error(i["a"].getMsg(o))}))}))},deleteRow:function(){var e=this,t=this.tableData.results[this.idx],o=this.$loading({lock:!0,text:"Loading",spinner:"el-icon-loading",background:"rgba(0, 0, 0, 0.7)"});i["a"].post("/book/saveOrUpdate",{id:t.id,operType:"3"},(function(t){o.close(),i["a"].isOK(t)?(e.handleCurrentChange(1),e.$message.success("删除成功")):e.$message.error(i["a"].getMsg(t)),e.delVisible=!1}))},beforeUpload1:function(e){for(var t=document.getElementById("idcard1").getElementsByClassName("el-upload--picture-card"),o=0;o<t.length;o++)t[o].style.display="none"},onRemove1:function(){var e=document.getElementById("idcard1").getElementsByClassName("el-upload--picture-card");setTimeout((function(){for(var t=0;t<e.length;t++)e[t].style.display="block"}),500)},onError1:function(){var e=document.getElementById("idcard1").getElementsByClassName("el-upload--picture-card");setTimeout((function(){for(var t=0;t<e.length;t++)e[t].style.display="block"}),500)},onSuccess1:function(e,t,o){var a=this;i["a"].isOK(e)?a.form.imgUrl=e.body.imageUrl:a.$message.error(i["a"].getMsg(e))}}},f=h,b=(o("e494"),o("d7ea"),o("2877")),v=Object(b["a"])(f,a,r,!1,null,"6db6217c",null);t["default"]=v.exports},cbcc:function(e,t,o){},d7ea:function(e,t,o){"use strict";var a=o("cbcc"),r=o.n(a);r.a},e494:function(e,t,o){"use strict";var a=o("e8d0"),r=o.n(a);r.a},e8d0:function(e,t,o){}}]);