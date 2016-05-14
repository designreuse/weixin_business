<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div class="col-xs-12 col-sm-12">
	    <div class="box no-drop">
	    <div class="box-content">
		<form class="form-horizontal" id="form_create_resource">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-2 control-label">资源名称<span
						class="required">*</span>
					</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="resourceName" />
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
								<option value="${type.value }">${type.key }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"> 资源uri<span
						class="required">*</span>
					</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="resourceUri" />
					</div>
				</div>
				<div class="row form-group">
					<label class="col-sm-2 control-label">是否菜单项 
					</label>
					<div class="col-sm-8">
						<div class="checkbox">
							<label style="margin-left: 0.5em"><input type="checkbox" class="form-control" name="isItem" ><i class="fa fa-square-o"></i></label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"> 操作
					</label>
					<div class="col-sm-8">
						<c:forEach var="auth" items="${auths}">
							<div class="checkbox-inline"><label><input type="checkbox" name="resourceAuth"
								value="${auth.id }">${auth.name }<i class="fa fa-square-o"></i> </label></div>
						</c:forEach>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
	</div>
	</div>
</div>

<script type="text/javascript">
	// Run Select2 plugin on elements
	function DemoSelect2() {
		$("#s2_with_tag").select2();
		$("#s2_country").select2({
			placeholder : "-- 选择角色 --"
		});
	}

	function validResource() {
		$("#form_create_resource").bootstrapValidator({
			message : "invalid",
			fields : {
				resourceName : {
					validators : {
						notEmpty : {
							message : "资源名称必填"
						},
						stringLength : {
							min : 2,
							message : "资源名长度不小于2个字符"
						},
						remote : {
							type : "POST",
							message : "资源名已存在,请重新填写",
							url : "rest/resource/checkResourceNameExists"
						}
					}

				},
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
		// Create Wysiwig editor for textare

		// Add slider for change test input length
		FormLayoutExampleInputLength($(".slider-style"));
		// Initialize datepicker

		// Add tooltip to form-controls
		$(".form-control").tooltip();
		LoadSelect2Script(DemoSelect2);
		// Load example of form validation
		LoadBootstrapValidatorScript(validResource);
		// Add drag-n-drop feature to boxes
		WinMove();
	});
</script>
