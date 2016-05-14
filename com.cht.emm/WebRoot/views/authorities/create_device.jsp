<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">

	<div class="col-xs-12 col-sm-12">
		<form class="form-horizontal">
			<h4 class="page-header">为用户 ${user.username } 创建设备</h4>
			<div class="form-group">
				<label class="col-sm-2 control-label">设备码</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="deviceId" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">设备名称</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="deviceName" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">操作系统</label>
				<div class="col-sm-8">
					<select id="s2_with_tag" class="populate placeholder"
						name="deviceOp">
						<c:forEach var="op" items="${ops }">
							<option value="${op.key }">${op.value }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">设备类型</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="deviceType" />
				</div>
			</div>

		</form>
	</div>
</div>
<script type="text/javascript">
	// Run Select2 plugin on elements
	function DemoSelect2() {
		$('#s2_with_tag').select2({
			placeholder : "Select OS"
		});
		$('#s2_country').select2({
			placeholder : "-- 选择用户 --"
		});
	}
	// Run timepicker
	function DemoTimePicker() {
		$('#input_time').timepicker({
			setDate : new Date()
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
		LoadBootstrapValidatorScript(DemoFormValidator);
		// Add drag-n-drop feature to boxes
		WinMove();
	});
</script>
