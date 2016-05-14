<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<style>
	
 
 
</style>
<sec:authentication property="principal" var="authentication" />
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">系统配置</a>
			</li>
			<li><a href="#">参数配置</a>
			</li>
		</ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="box no-drop">
			<div class="box-header">
				<div class="box-name">
					<span>系统配置/参数配置</span>
				</div>
				<div class="box-icons"></div>
				<div class="no-move"></div>
			</div>
			<div class="box-content no-padding table-responsive">
			<div class="row">
					<div class="col-xs-10">
						<ul class="operate">
							<li class="tool_box_left"><a
								href="javascript:setModifiedProperties();" title="可修改配置项"> <i
									class="fa fa-plus-square"></i><span class="hidden-xs">设置可修改配置项</span>
							</a></li>
						</ul>
					</div>
					 
				</div>
			<div class="row">
				<form id="form_properties" action="" class="form-horizontal">
					
					 
						<c:forEach items="${paramList}" var="para" varStatus="status">
						 	<c:if test="${status.count%2 ==1}">
						 		<div class ="form-group row">
						 	</c:if>
						 	<label class="col-sm-2 control-label">${para.title }:</label>
						 		<div class="col-sm-4"> 
						 		 
						 			<input type="text" class="form-control" name ="${para.key }" value= "${para.value }" >
						 		</div>
						 	<c:if test="${status.count%2 ==0 }"></div></c:if>
						 	
						 	<c:if test="${status.count%2 ==1 && status.last }" ></div></c:if>
						</c:forEach>
				</form>
				<div class="col-sm-12 col-sm-offset-3">
							<input type="button" class="btn btn-default" value="保存" onClick="saveProperties()"> 
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		loadConfigedProperties();
	});
	
	
	function loadConfigedProperties(){
		$("#form_properties").empty();
		$.ajax({
			url:"rest/sysConfig/properties/list",
			type:"post",
			success:function(json){
				if(json.successful){
				debugger
					var list = json.resultValue;
					var content ='';
					if(list.length >0){
						for(i =0; i< list.length; i++){
							if(i%2==0){
								content+="<div class =\"form-group row\">";
							}
							content+="<label class=\"col-sm-2 control-label\">"+list[i].title +":</label>"+
						 			 "<div class=\"col-sm-4\">"+ 
						 		 	 "<input type=\"text\" class=\"form-control\" name =\""+list[i].key +
						 		 	 "\" value= \""+list[i].value +"\" ></div>";
							if(i%2==1){
								content+="</div>"
							}
							
							if(i==list.length-1 && i%2==0){
								content+="</div>";
							}
						}
					}
					$("#form_properties").append(content);
				}
			
			}
		});
	}
 	function saveProperties(){
 		var datas ={};
 		$("#form_properties").find("input[type=text]").each(function(){
 				console.log(this.name)
 				datas[this.name] =this.value;
 		});
 		console.log(datas);
 		$.ajax({
 			url:"rest/sysConfig/properties/save",
 			type:"post",
 			data:datas,
 			success:function(json){
 				if(json.successful){
 					alert("保存成功");
 				}else{
 					alert("保存失败")
 				}
 			}
 		
 		});
 	}
 	
 	function setModifiedProperties(){
 		OpenBigDialogWithConfirm("设置可配置属性", 'console/sysconfig/params/config',
				'properties_config', true, true, afterPropertiesConfig);
 	}
 
 	function afterPropertiesConfig(){
 		allChecked =true;
 		$("#form_update_configedProperties").find("input[type=text]").each(function(){
 			if(this.value.replace(/(^\s*)|(\s*$)/g, "")==''){
 				allChecked =false;
 				 
 				if($(this).parent().find("small[class=help-block]").length==0)
 					$(this).parent().append("<small class=\"help-block\"  data-bv-result=\"INVALID\" style=\"\">必填</small>");
 			}else{
 				$(this).parent().find("small .help-block").remove();
 			}
 		});
 		if(allChecked){
 			allChecked=false;
 			var datas ={};
 			$("#form_update_configedProperties").find("input[type=text]").each(function(){
 				datas[this.name] = this.value;
 			});
 			$.ajaxSettings.async = false;
 			$.ajax({
 				url:"rest/sysConfig/properties/saveconfiged",
 				type:"post",
 				data:datas,
 				success:function(json){
 					loadConfigedProperties();
 					allChecked =true;
 				}
 			});
 		
 		}
 		return allChecked;
 	}
 	
 	
</script>
