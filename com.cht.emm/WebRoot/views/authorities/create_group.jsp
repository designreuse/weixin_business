<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link
	href="${pageContext.request.contextPath}/resources/js/jstree/themes/default/style.css"
	rel="stylesheet">
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<form class="form-horizontal" id="form_create_group">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-2 control-label"> 组名<span
						class="required">*</span> </label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="groupName" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"> 描述 </label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="desc" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"> 父组<span
						class="required">*</span> </label>
					<div class="col-sm-8">
						<!-- <input type="text" class="form-control" name="desc" /> -->
						<div id="parentGroup">
						</div>
						<input type="hidden" name="parentId" id="parentId">
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

	function loadParentGroup() {
		$("#parentGroup").jstree({
			'core' : {
				'data' : {
					'url' : 'rest/group/nodes',
					'data' : function(node) {
						return {
							'parentId' : node.id
						};
					},
					'type' : 'POST'
				},
				'check_callback' : true,
				'themes' : {
					'responsive' : false
				}
			},
			'types' : {
				'node' : {
					'icon' : 'folder'
				},
				'leaf' : {
					'icon' : 'file'
				}
			},
			'sort' : function(a, b) {
				return 0;
			},
			'plugins' : [ 'state', 'dnd', 'contextmenu', 'wholerow' ]
		}).on('changed.jstree', function(e, data) {
			//console.log(data);
			id = data.selected.join(":");
			$("#create_group").find("#parentId").val(id);
		});

	}
	function validGroup() {
		$('#form_create_group').bootstrapValidator({
			message : "invalid",
			fields : {
				groupName : {
					validators : {
						notEmpty : {
							message : '组名必填'
						},
						stringLength : {
							min : 2,
							message : "用户组名长度不小于2个字符"
						},
						remote : {
							type : 'POST',
							message : '组名已存在,请重新填写',
							url : 'rest/group/checkGroupNameExists'
						}
					}

				}
			}

		});

	}
	$(document).ready(function() {
		$('.form-control').tooltip();
		LoadSelect2Script(DemoSelect2);
		// Load example of form validation
		LoadBootstrapValidatorScript(validGroup);
		// Add drag-n-drop feature to boxes
		WinMove();
		loadJsTree(loadParentGroup);
	});
</script>
