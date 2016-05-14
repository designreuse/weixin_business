<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal" var="authentication" />
<div class="row">
	<div class="col-xs-9 col-sm-9">
		<form class="form-horizontal bv-form" id="form_create_config">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-3 control-label">第三方名称<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="thirdPartName" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">远程验证地址<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="remoteUrl" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label">处理类<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<select name="className" id="className" class="populate placeholder">
							<c:forEach items="${classes }" var="cls">
								<option value="${cls}">${cls }</option>
							</c:forEach>
							
						
						</select>
						<!-- input type="text" class="form-control" id="className" name="className" /-->
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">其他参数</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="others" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">组名称<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="groupName" />
					</div>
				</div>
				<div class="form-group">
				
					<div class="col-sm-9 col-sm-offset-3"><input type="button" value="验证" class="btn btn-primary btn-block" onclick="validRemote()"></div>
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
	
	function validRemote(){
	
	}
	
	function validThirdPart(){
		addThirdPart = $('#form_create_config').bootstrapValidator({
			message : "invalid",
			fields : {
				thirdPartName : {
					validators : {
						notEmpty : {
							message : "第三方名称必填"
						},
						stringLength : {
							min : 4,
							max : 16,
							message : "第三方名称长度在4-16个字符之间"
						},
						remote : {
							type : 'POST',
							message : "第三方名称已存在,请重新填写",
							url : 'rest/thirdpart/checkNameExists'
						}
					}
				},
				remoteUrl : {
					validators : {
						notEmpty : {
							message : '验证地址必填'
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
				groupName : {
					validators : {
						notEmpty : {
							message : '组名称必填'
						},
						remote:{
							type : 'POST',
							message : "组名称已存在,请重新填写",
							url : 'rest/thirdpart/checkGroupNameExists'
						}
					}
				} 
			}
		});
	}
	
	$(document).ready(function(){
		LoadBootstrapValidatorScript(validThirdPart);
		LoadSelect2Script(DemoSelect2);
	});
</script>
