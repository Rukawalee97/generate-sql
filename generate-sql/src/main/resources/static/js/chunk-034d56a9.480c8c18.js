(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-034d56a9"],{"1fce":function(t,a,e){"use strict";e.r(a);var o=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"account-settings-info-view"},[e("a-row",{attrs:{gutter:16}},[e("a-col",{attrs:{md:24,lg:16}},[e("a-form",{attrs:{layout:"vertical"}},[e("a-form-item",{attrs:{label:t.$t("account.settings.basic.nickname")}},[e("a-input",{attrs:{placeholder:t.$t("account.settings.basic.nickname-message")}})],1),e("a-form-item",{attrs:{label:t.$t("account.settings.basic.profile")}},[e("a-textarea",{attrs:{rows:"4",placeholder:t.$t("account.settings.basic.profile-message")}})],1),e("a-form-item",{attrs:{label:t.$t("account.settings.basic.email"),required:!1}},[e("a-input",{attrs:{placeholder:"example@ant.design"}})],1),e("a-form-item",[e("a-button",{attrs:{type:"primary"}},[t._v(t._s(t.$t("account.settings.basic.update")))])],1)],1)],1),e("a-col",{style:{minHeight:"180px"},attrs:{md:24,lg:8}},[e("div",{staticClass:"ant-upload-preview",on:{click:function(a){return t.$refs.modal.edit(1)}}},[e("a-icon",{staticClass:"upload-icon",attrs:{type:"cloud-upload-o"}}),e("div",{staticClass:"mask"},[e("a-icon",{attrs:{type:"plus"}})],1),e("img",{attrs:{src:t.option.img}})],1)])],1),e("avatar-modal",{ref:"modal",on:{ok:t.setavatar}})],1)},i=[],n=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("a-modal",{attrs:{title:"修改头像",visible:t.visible,maskClosable:!1,confirmLoading:t.confirmLoading,width:800,footer:null},on:{cancel:t.cancelHandel}},[e("a-row",[e("a-col",{style:{height:"350px"},attrs:{xs:24,md:12}},[e("vue-cropper",{ref:"cropper",attrs:{img:t.options.img,info:!0,autoCrop:t.options.autoCrop,autoCropWidth:t.options.autoCropWidth,autoCropHeight:t.options.autoCropHeight,fixedBox:t.options.fixedBox},on:{realTime:t.realTime}})],1),e("a-col",{style:{height:"350px"},attrs:{xs:24,md:12}},[e("div",{staticClass:"avatar-upload-preview"},[e("img",{style:t.previews.img,attrs:{src:t.previews.url}})])])],1),e("br"),e("a-row",[e("a-col",{attrs:{lg:2,md:2}},[e("a-upload",{attrs:{name:"file",beforeUpload:t.beforeUpload,showUploadList:!1}},[e("a-button",{attrs:{icon:"upload"}},[t._v("选择图片")])],1)],1),e("a-col",{attrs:{lg:{span:1,offset:2},md:2}},[e("a-button",{attrs:{icon:"plus"},on:{click:function(a){return t.changeScale(1)}}})],1),e("a-col",{attrs:{lg:{span:1,offset:1},md:2}},[e("a-button",{attrs:{icon:"minus"},on:{click:function(a){return t.changeScale(-1)}}})],1),e("a-col",{attrs:{lg:{span:1,offset:1},md:2}},[e("a-button",{attrs:{icon:"undo"},on:{click:t.rotateLeft}})],1),e("a-col",{attrs:{lg:{span:1,offset:1},md:2}},[e("a-button",{attrs:{icon:"redo"},on:{click:t.rotateRight}})],1),e("a-col",{attrs:{lg:{span:2,offset:6},md:2}},[e("a-button",{attrs:{type:"primary"},on:{click:function(a){return t.finish("blob")}}},[t._v("保存")])],1)],1)],1)},s=[],r=(e("d3b7"),e("3ca3"),e("ddb0"),e("2b3d"),{data:function(){return{visible:!1,id:null,confirmLoading:!1,fileList:[],uploading:!1,options:{img:"",autoCrop:!0,autoCropWidth:200,autoCropHeight:200,fixedBox:!0},previews:{}}},methods:{edit:function(t){this.visible=!0,this.id=t},close:function(){this.id=null,this.visible=!1},cancelHandel:function(){this.close()},changeScale:function(t){t=t||1,this.$refs.cropper.changeScale(t)},rotateLeft:function(){this.$refs.cropper.rotateLeft()},rotateRight:function(){this.$refs.cropper.rotateRight()},beforeUpload:function(t){var a=this,e=new FileReader;return e.readAsDataURL(t),e.onload=function(){a.options.img=e.result},!1},finish:function(t){var a=this,e=this,o=new FormData;"blob"===t?this.$refs.cropper.getCropBlob((function(t){var i=window.URL.createObjectURL(t);a.model=!0,a.modelSrc=i,o.append("file",t,a.fileName),a.$http.post("https://www.mocky.io/v2/5cc8019d300000980a055e76",o,{contentType:!1,processData:!1,headers:{"Content-Type":"application/x-www-form-urlencoded"}}).then((function(t){e.$message.success("上传成功"),e.$emit("ok",t.url),e.visible=!1}))})):this.$refs.cropper.getCropData((function(t){a.model=!0,a.modelSrc=t}))},okHandel:function(){var t=this;t.confirmLoading=!0,setTimeout((function(){t.confirmLoading=!1,t.close(),t.$message.success("上传头像成功")}),2e3)},realTime:function(t){this.previews=t}}}),c=r,l=(e("3508"),e("2877")),u=Object(l["a"])(c,n,s,!1,null,"0caba3e2",null),p=u.exports,d={components:{AvatarModal:p},data:function(){return{preview:{},option:{img:"/avatar2.jpg",info:!0,size:1,outputType:"jpeg",canScale:!1,autoCrop:!0,autoCropWidth:180,autoCropHeight:180,fixedBox:!0,fixed:!0,fixedNumber:[1,1]}}},methods:{setavatar:function(t){this.option.img=t}}},f=d,m=(e("d3f8"),Object(l["a"])(f,o,i,!1,null,"5e6cf7ca",null));a["default"]=m.exports},2442:function(t,a,e){},3508:function(t,a,e){"use strict";var o=e("2442"),i=e.n(o);i.a},b5b4:function(t,a,e){},d3f8:function(t,a,e){"use strict";var o=e("b5b4"),i=e.n(o);i.a}}]);