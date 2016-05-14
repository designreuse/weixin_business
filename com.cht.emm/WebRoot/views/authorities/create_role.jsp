<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<form class="form-horizontal" id="form_create_role">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-2 control-label">角色名称<span
						class="required">*</span> </label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="roleName" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">适用用户类型<span
						class="required">*</span>
					</label>
					<div class="col-sm-8">
						<select multiple="multiple" id="userType" name="userType"
							class="populate placeholder">
							<option value="1">超级管理员</option>
							<option value="2">机构管理员</option>
							<option value="3">部门管理员</option>
							<option value="4">员工</option>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">描述</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="roleDesc" />
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
	}

	//验证 角色
	function validRole() {
		$('#form_create_role').bootstrapValidator({
			message : "invalid",
			fields : {
				roleName : {
					validators : {
						notEmpty : {
							message : '角色名必填'
						},
						stringLength : {
							min : 2,
							message : "角色名长度不小于2个字符"
						},
						remote : {
							type : 'POST',
							message : '角色名已存在,请重新填写',
							url : 'rest/role/checkRoleNameExists'
						}
					}

				},
				userType : {
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
		// Create Wysiwig editor for textare
		// Add tooltip to form-controls
		$('.form-control').tooltip();
		LoadSelect2Script(DemoSelect2);
		// Load example of form validation
		LoadBootstrapValidatorScript(validRole);
		// Add drag-n-drop feature to boxes
		WinMove();
	});
</script>
