<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<form class="form-horizontal" id="form_edit_group">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-2 control-label"> 组名<span
						class="required">*</span>
					</label>
					<div class="col-sm-8">
						<input type="hidden" name="groupId" value="${group.id }">
						<input type="text" class="form-control" name="groupName"
							value="${group.groupName }" readonly />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"> 描述 </label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="desc"
							value="${group.groupDesc }" />
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
		$('#s2_with_tag').select2();
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

	function validGroup() {
		$('#form_create_group').bootstrapValidator({
			message : "invalid",
			fields : {
				desc : {
					validators : {
						notEmpty : {
							message : '描述信息必填'
						}
					}

				}
			}

		});

	}
	$(document).ready(function() {
		// Create Wysiwig editor for textare
		TinyMCEStart('#wysiwig_simple', null);
		TinyMCEStart('#wysiwig_full', 'extreme');
		// Add slider for change test input length
		FormLayoutExampleInputLength($(".slider-style"));
		// Initialize datepicker
		$('#input_date').datepicker({
			setDate : new Date()
		});
		// Load Timepicker plugin
		LoadTimePickerScript(DemoTimePicker);
		// Add tooltip to form-controls
		$('.form-control').tooltip();
		LoadSelect2Script(DemoSelect2);
		// Load example of form validation
		LoadBootstrapValidatorScript(validGroup);
		// Add drag-n-drop feature to boxes
		WinMove();
	});
</script>
