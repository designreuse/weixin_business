<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal" var="authentication" />
<div class="row">
	<div class="col-xs-9 col-sm-9">
		<form class="form-horizontal" id="form_edit_config">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-3 control-label">第三方名称<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="hidden" name="id" value="${config.id}">
						<input tepe="text" class="form-control" value="${config.name}" readOnly>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">远程验证地址<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="remoteUrl" value="${config.remoteUrl }" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label">处理类<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<select name="className" id="className">
							<c:forEach items="${classes }" var="cls">
								<option value="${cls}" <c:if test="${cls == config.className }">selected</c:if> > ${cls }
								</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">其他参数</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="other" value="${config.other}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">组名称<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input tepe="text" class="form-control" value="${config.groupName }" readOnly>
					</div>
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
	$(document).ready(function(){
		LoadBootstrapValidatorScript(validThirdPart);
		LoadSelect2Script(DemoSelect2);
	});
	
	
</script>
