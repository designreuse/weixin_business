<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal" var="authentication" />
<% 
    request.setAttribute("vEnter", "\r\n");   
%> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/codeMirror/lib/codemirror.css">
<script  src="${pageContext.request.contextPath}/resources/js/codeMirror/lib/codemirror.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/codeMirror/addon/edit/matchbrackets.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/codeMirror/addon/hint/show-hint.css">
<script src="${pageContext.request.contextPath}/resources/js/codeMirror/addon/hint/show-hint.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/codeMirror/mode/clike/clike.js"></script>

<div class="row">
	<div class="col-xs-9 col-sm-9">
		<form class="form-horizontal" id="form_edit_remoteClass">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-3 control-label">类名<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="hidden" name="id" value="${remoteClass.id}">
						<input tepe="text" class="form-control" name="className" value="${remoteClass.className}" readOnly>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">描述<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="classDesc" value="${remoteClass.classDesc }" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">包名<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="packageName" readOnly value="${remoteClass.packageName }" />
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-3 control-label">内容<span
						class="required">*</span> </label>
					<div class="col-sm-9"> 
					<textarea id="java-code" name="content" onchanged="changeCompiled()"></textarea>
					</div>
					<input type="hidden" id="compilePass" value="0">
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">启用</label>
					<div class="col-sm-9">
						<input type="radio" name="enabled" <c:if test="${remoteClass.enabled ==1}">checked</c:if> value=1 />
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-9 col-sm-offset-3"><input type="button" value="编译并部署" class="btn btn-primary btn-block" onclick="compile2(this)"></div>
				</div>
			</fieldset>
		</form>
	</div>
	 
</div>

<script type="text/javascript">
	 function DemoSelect2() {
		$('#className').select2({
			placeholder : "-- 选择处理类 --"
		});
	}
	
	id = "${remoteClass.id}";
	function validThirdPart(){
		editThirdPart = $('#form_create_config').bootstrapValidator({
			message : "invalid",
			fields : {
				remoteUrl : {
					validators : {
						notEmpty : {
							message : "验证地址必填"
						}
					}
				},
				className : {
					validators : {
						notEmpty : {
							message : "验证类必选"
						}
					}
				}
			}
		});
	}
	var javaEditor = CodeMirror.fromTextArea(document.getElementById("java-code"), {
        lineNumbers: true,
        matchBrackets: true,
        mode: "text/x-java"
      });
	$(document).ready(function(){
		LoadBootstrapValidatorScript(validThirdPart);
		LoadSelect2Script(DemoSelect2);
		 $.ajax({
		 	data:{"id":id},
		 	url:"rest/thirdpart/remote/get",
		 	type:"post",
		 	success:function(data){
		 	//debugger
		 		javaEditor.setValue(data.resultValue.content);
		 	}
		 });
	});
	
	function compile2(){
		className  = getValueByName("form_edit_remoteClass","className");
		packageName  = getValueByName("form_edit_remoteClass","packageName");
		content = javaEditor.getValue();
		$.ajax({
			url:"rest/thirdpart/remote/complie",
			type:"post",
			data:{
				"className":className,
				"packageName":packageName,
				"content":content
			},
			success:function(data){
				if(data.successful){
					alert("编译部署成功");
					$("#compilePass").val(1);
				}else{
					message(data.resultMessage);
				}
			}
		});
	}
	
</script>
