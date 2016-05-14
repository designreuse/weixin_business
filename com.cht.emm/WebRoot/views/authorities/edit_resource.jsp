<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<form class="form-horizontal" id="form_edit_resource">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-2 control-label">资源名称<span
						class="required">*</span>
					</label>
					<div class="col-sm-8">
						<input type="hidden" name="resourceId" value="${resource.id }">
						<input type="text" class="form-control" name="resourceName"
							value="${resource.name }" readonly />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"> 资源类型<span
						class="required">*</span>
					</label>
					<div class="col-sm-8">
						<select id="s2_with_tag" class="populate placeholder"
							name="resourceType">
							<c:forEach var="type" items="${types}">
								<option value="${type.value }"
									<c:if test="${type.value==resource.type }">selected</c:if>>${type.key
									}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"> 资源uri<span
						class="required">*</span>
					</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="resourceUri"
							value="${resource.uri }" />
					</div>
				</div>
				<div class="row form-group">
					<label class="col-sm-2 control-label">是否菜单项 
					</label>
					<div class="col-sm-8">
						<div class="checkbox">
							<label style="margin-left: 0.5em"><input type="checkbox" class="form-control" name="isItem" <c:if test="${resource.isItem ==1 }">checked</c:if>    ><i class="fa fa-square-o"></i></label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"> 操作 
					</label>
					<div class="col-sm-8">
						<c:forEach var="auth" items="${auths}">
							<div class="checkbox-inline">
								<label><input type="checkbox" name="resourceAuth"
								value="${auth.id }"
								<c:forEach var="au" items="${resource.resourceAuths }"> 
									<c:if test="${au.auth.id eq auth.id }"> checked</c:if> 
								</c:forEach>>${auth.name}<i class="fa fa-square-o"></i>
							</label></div>
						</c:forEach>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</div>

<script type="text/javascript">
	// Run Select2 plugin on elements
	function DemoSelect2() {
		$('#s2_with_tag').select2();
	}
	function validResource() {
		$("#form_edit_resource").bootstrapValidator({
			message : "invalid",
			fields : {
				resourceType : {
					validators : {
						notEmpty : {
							message : "资源类型必选"
						}
					}

				},
				resourceUri : {
					validators : {
						notEmpty : {
							message : "资源uri必填"
						}
					}
				},
				resourceAuth : {
					validators : {
						//choice : {
						//	min : 1,
						//	message : "至少选择一个操作"
						//}
					}
				}
			}

		});
	}

	$(document).ready(function() {
		// Add tooltip to form-controls
		$('.form-control').tooltip();
		LoadSelect2Script(DemoSelect2);
		// Load example of form validation
		LoadBootstrapValidatorScript(validResource);
		// Add drag-n-drop feature to boxes
		WinMove();
	});
</script>
