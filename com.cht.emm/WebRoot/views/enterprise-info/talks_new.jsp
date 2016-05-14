<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">企业信息</a></li>
			<li><a href="#">添加好友</a></li>
		</ol>
	</div>
</div>

<div class="row">
	<div class="col-xs-12 col-sm-12">
		<div class="box no-drop">
			<div class="box-header">
				<div class="box-name">
					<i class="fa fa-plus"></i>
					<span></span>
				</div>
				<div class="box-icons">
					<a class="collapse-link">
						<i class="fa fa-chevron-up"></i>
					</a>
					<a class="expand-link">
						<i class="fa fa-expand"></i>
					</a>
					<a class="close-link">
						<i class="fa fa-times"></i>
					</a>
				</div>
				<div class="no-move"></div>
			</div>
			<div class="box-content">
				<form class="form-horizontal" role="form">
					<fieldset>
						<div class="form-group">
							<label class="col-sm-2 control-label">用户（多选）</label>
							<div class="col-sm-8">
								<select id="s2_country" multiple="multiple" class="populate placeholder">
									<option>用户1</option>
									<option>用户2</option>
									<option>用户3</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">验证信息</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="desc" />
							</div>
						</div>
					</fieldset>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-2">
							<button type="cancel" class="btn btn-default btn-label-left">
								<span><i class="fa fa-clock-o txt-danger"></i></span> 取消
							</button>
						</div>
						<div class="col-sm-2">
							<button type="submit" class="btn btn-warning btn-label-left">
								<span><i class="fa fa-clock-o"></i></span> 发送
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
// Run Select2 plugin on elements
function DemoSelect2(){
	$('#s2_with_tag').select2();
	$('#s2_country').select2({placeholder: "-- 选择用户 --"});
}
// Run timepicker
function DemoTimePicker(){
	$('#input_time').timepicker({setDate: new Date()});
}
$(document).ready(function() {
	// Create Wysiwig editor for textare
	TinyMCEStart('#wysiwig_simple', null);
	TinyMCEStart('#wysiwig_full', 'extreme');
	// Add slider for change test input length
	FormLayoutExampleInputLength($( ".slider-style" ));
	// Initialize datepicker
	$('#input_date').datepicker({setDate: new Date()});
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
