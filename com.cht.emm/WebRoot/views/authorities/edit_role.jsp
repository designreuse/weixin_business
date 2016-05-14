<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<form class="form-horizontal" id="form_edit_role">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-2 control-label">角色名称<span
						class="required">*</span>
					</label>
					<div class="col-sm-8">
						<input type="hidden" name="roleId" value="${role.id }">
						<input 
							type="text" class="form-control" name="roleName"
							value="${role.roleName }" readonly />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">适用用户类型</label>
					<div class="col-sm-8">
						<select multiple="multiple" id="userType" name="userType"
							class="populate placeholder">
							<option value="1" <c:if test="${ selected1 }">selected</c:if> >超级管理员</option>
							<option value="2" <c:if test="${ selected2 }">selected</c:if>  >机构管理员</option>
							<option value="3" <c:if test="${ selected3 }">selected</c:if>  >部门管理员</option>
							<option value="4" <c:if test="${ selected4 }">selected</c:if>  >员工</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">描述</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="roleDesc"
							value="${role.roleDesc}" />
					</div>
				</div>
			</fieldset>
			<div class="form-group"></div>
		</form>
	</div>
</div>

<script type="text/javascript">
	// Run Select2 plugin on elements
	function DemoSelect2() {
		$('#userType').select2();
		$('#s2_country').select2({
			placeholder : "-- 选择角色 --"
		});
	}
	// Run timepicker
	function DemoTimePicker() {
		$('#input_time').timepicker({
			setDate : new Date()
		});
	}

	//验证 角色
	function validRole() {
		$("#form_edit_role").bootstrapValidator({
			message : "invalid",
			fields : {
				userTypes:{
					validators : {
						notEmpty : {
							message : '用户类型必填'
						}
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
		LoadBootstrapValidatorScript(validRole);
		// Add drag-n-drop feature to boxes
		WinMove();
	});
</script>
