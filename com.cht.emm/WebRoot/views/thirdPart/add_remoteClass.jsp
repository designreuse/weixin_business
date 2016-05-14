<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal" var="authentication" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/codeMirror/lib/codemirror.css">
<script  src="${pageContext.request.contextPath}/resources/js/codeMirror/lib/codemirror.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/codeMirror/addon/edit/matchbrackets.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/codeMirror/addon/hint/show-hint.css">
<script src="${pageContext.request.contextPath}/resources/js/codeMirror/addon/hint/show-hint.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/codeMirror/mode/clike/clike.js"></script>

<div class="row">
	<div class="col-xs-9 col-sm-9">
		<form class="form-horizontal bv-form" id="form_create_remoteClass">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-3 control-label">类名<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="className" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">包名<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<select class="form-control" name="packageName">
							<c:forEach items="${packages}" var="pkg">
								<option value="${pkg }">${pkg }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">说明 <span
						class="required">*</span></label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="classDesc" />
					</div>
				</div>
				 
				<div class="form-group"  id="class_content" style="display:none">
					<label class="col-sm-3 control-label">content<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<textarea id="java-code" name="content" onchanged="changeCompiled()"></textarea>
					</div>
					<input type="hidden" id="compilePass" value="0">
				</div>
				
				<div class="form-group">
					<label class="col-sm-3 control-label">启用 </label>
					<div class="col-sm-9">
						<input type="checkbox" name="enabled" value=1>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-9 col-sm-offset-3"><input type="button" value="生成类" class="btn btn-primary btn-block" onclick="createJavaFile(this)"></div>
				</div>
			</fieldset>
		</form>
	</div>
	 
</div>

<script type="text/javascript">
	function changeCompiled(){
		$("#compilePass").val(0);
	}
     var javaEditor = CodeMirror.fromTextArea(document.getElementById("java-code"), {
        lineNumbers: true,
        matchBrackets: true,
        mode: "text/x-java"
      });
	 function DemoSelect2() {
		 
		$('#className').select2({
			placeholder : "-- 选择处理类 --"
		});
	}
	
	function compile(){
		className  = getValueByName("form_create_remoteClass","className");
		packageName  = getSelectValByName("form_create_remoteClass","packageName");
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
	function validRemote(){
	}
	function createJavaFile(o){
		//debugger;
		className  = getValueByName("form_create_remoteClass","className");
		packageName  = getSelectValByName("form_create_remoteClass","packageName");
		$.ajax({
			url:"rest/thirdpart/remote/template",
			type:"post",
			data:{
				"className":className,
				"packageName":packageName},
			success:function(data){
				if(data.successful){
					javaEditor.setValue(data.resultValue);
					 
					//当内容发生更改时，修改compilePass状态
					$("#form_create_remoteClass").find("input[name='className']").first().attr("readonly","readonly");
					$("#form_create_remoteClass").find("select[name='packageName']").first().attr("readonly","readonly");
					$("#class_content").show();
					$(o).val("编译并部署");
					$(o).attr("onclick","compile()");
				}
			}
		});
	}
	
	
	function validRemoteClass(){
		addThirdPart = $('#form_create_config').bootstrapValidator({
			message : "invalid",
			fields : {
				className : {
					validators : {
						notEmpty : {
							message : "类名必填"
						},
						remote : {
							type : 'POST',
							message : "类名已存在,请重新填写",
							url : 'rest/thirdpart/checkNameExists'
						},
						regexp:{
							pattern:'/^[A-Z]{1}[a-zA-Z]*$/',
							message:'请用字母写出类名，首字母大写'
						}
						
					}
				},
				classDesc : {
					validators : {
						notEmpty : {
							message : '描述必填'
						}
					}
				},
				className : {
					validators : {
						notEmpty : {
							message : '验证类必选'
						}
					}
				}, 
				content : {
					validators : {
						notEmpty : {
							message : '内容必填'
						}
					}
				} 
			}
		});
	}
	
	$(document).ready(function(){
		LoadBootstrapValidatorScript(validRemoteClass);
		//LoadSelect2Script(DemoSelect2);
	});
</script>
