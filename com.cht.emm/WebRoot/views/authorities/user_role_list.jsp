<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">权限管理</a>
			</li>
			<li><a href="#">角色管理</a>
			</li>
		</ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="box no-drop">
			<div class="box-header">
				<div class="box-name subTitle">
					<span>权限管理/角色管理</span>
				</div>
				<div class="box-icons"></div>
				<div class="no-move"></div>
			</div>
			<div class="box-content">
				<div class="row">
					<div class="col-xs-10">
						<ul class="operate">
							<li class="tool_box_left"><a href="javascript:createRole();"
								title="新增角色"> <i class="fa fa-plus-square"></i><span
									class="hidden-xs">新增角色</span> </a>
							</li>
							<li class="tool_box_left"><a href="#"
								onClick="deleteRole(this)" title="删除角色" class="deleteIcon">
									<i class="fa fa-minus-square"></i><span class="hidden-xs">删除角色
								</span> </a>
							</li>
						</ul>
					</div>
					<div class="col-xs-2 input-group searchInput">
						<input type="text" class="form-control" placeholder="搜索">
						<span class="input-group-addon" onClick="search(this)"><i
							class="fa fa-search"></i> </span>
					</div>
				</div>
			</div>
			<div class="box-content no-padding table-responsive">

				<table
					class="table table-bordered table-striped table-hover table-heading table-datatable"
					id="datatable-4">
					<thead>
						<tr>
							<th><input type="checkbox" class="selectAll"
								onclick="selectAll(this)"></th>
							<th>角色名称</th>
							<th class="hidden-xs">描述</th>
							<th>角色用户数</th>
							<!-- th class="hidden-xs">状态</th -->
							<th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	// Run Datables plugin and create 3 variants of settings
	var roleTable = null;
	function search(o) {
		OSettings = roleTable.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		roleTable._fnFilterComplete(OSettings, {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		});

	}

	tableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "rest/role/all/pages",
		"aaSorting" : [ [ 0, "asc" ] ],
		"aLengthMenu" : [ [ 10, 20, 50 ], [ 10, 20, 50 ] ],
		"sDom" : "rt<'box-content'<'col-sm-12'<'tool_box_pageInfo' ilp>> <'clearfix'>>",
		"sPaginationType" : "bootstrap",
		"oLanguage" : {
			"sSearch" : ""
		},
		"aoColumns" : [
				{
					"mData" : "id",
					"mRender" : function(data, type, full) {
						//return data;
						var disabled = false;
						if (full.isSystem != 0) {
							disabled = true;
						}
						return "<input type=\"checkbox\" value=\"" + data
								+ "\"" + (disabled ? 'disabled' : '') + ">";
					},
					"bSortable" : false,
					"bSearchable" : false
				},
				{
					"mData" : "roleName",
					"mRender" : function(data, type, full) {
						return "<a href=\"javascript:getRoleInfo('" + full.id
								+ "')\">" + data + "</a>";
					}
				},
				{
					"mData" : "roleDesc",
					"bSortable" : false,
					"sClass" : "hidden-xs"
				},
				{
					"mData" : "userNum",
					"bSearchable" : false,
					"bSortable" : false
				},
				{
					"mData" : null,
					"mRender" : function(data, type, row) {
						var click = 'notAllowed()';
						if (row.isSystem != 1) {
							click = "editRoleInfo('" + row.id + "')";
						}
						return "<a class=\"link_status op\" onclick=\""+click+"\">编辑</a>";
					},
					"bSearchable" : false,
					"bSortable" : false
				}

		]
	};

	// 加载 用户组
	function loadRoles() {
		roleTable = $("#datatable-4").dataTable(tableSetting);
		LoadSelect2Script(MakeSelect);
	}

	$(document).ready(function() {
		LoadDataTablesScripts(loadRoles);
	});

	function getRoleInfo(id) {

		OpenBigDialog('用户角色详情', 'console/role/info?id=' + id, 'user_role',
				true, true);

	}

	function createRole() {
		OpenBigDialogWithConfirm("创建角色", 'console/role/add', 'create_role',
				true, true, aftercreateRole);
	}

	function editRoleInfo(id) {
		OpenBigDialogWithConfirm("编辑角色", 'console/role/edit?id=' + id,
				'edit_role', true, true, aftereditRole);
	}

	function aftercreateRole() {
		checkResult = false;
		$("#form_create_role").bootstrapValidator('validate');
		var result = $("#form_create_role").data("bootstrapValidator")
				.isValid();
		//console.log(result);
		if (result) {
			roleName = $("#create_role").find("input[name='roleName']").first()
					.val();

			roleDesc = $("#create_role").find("input[name='roleDesc']").first()
					.val();
			userTypes='';
			
		 $("#form_create_role").find("select[name='userType']").find("option:selected").each(
		 	function() {
		 		if (userTypes != '') { 
		 			userTypes += ','; 
		 		} 
		 		userTypes += $(this).val();
		  
		  });
		 		

			$.ajaxSettings.async = false;
			$.ajax({
				url : "rest/role/add",
				data : {
					"roleName" : roleName,
					"roleDesc" : roleDesc,
					"userTypes" : userTypes
				},
				type : "post",
				success : function(data) {
					if (data.successful == true) {
						freshPage(roleTable, {});
						checkResult = true;
					}
				}
			});
		}
		return checkResult;
	}

	function aftereditRole() {
		checkResult = false;
		$("#form_edit_role").bootstrapValidator('validate');
		var result = $("#form_edit_role").data("bootstrapValidator").isValid();
		if (result) {
			roleId = $("#edit_role").find("input[name='roleId']").first().val();

			roleName = $("#edit_role").find("input[name='roleName']").first()
					.val();

			roleDesc = $("#edit_role").find("input[name='roleDesc']").first()
					.val();
			userTypes='';
			
		 	$("#edit_role").find("select[name='userType']").find("option:selected").each(
		 	function() {
		 		if (userTypes != '') { 
		 			userTypes += ','; 
		 		} 
		 		userTypes += $(this).val();
		  
		  });
			$.ajaxSettings.async = false;
			$.ajax({
				url : "rest/role/edit",
				data : {
					"id" : roleId,
					"roleName" : roleName,
					"roleDesc" : roleDesc,
					"userTypes":userTypes
				},
				method : "post",
				success : function(result) {
					if (result.successful == true) {
						freshPage(roleTable, {});
						checkResult = true;
					}
				}
			});
		}
		return checkResult;
	}
	function deleteRole(o) {
		var ids = getSelectedIds(o);
		if (ids.length == 0) {
			alert("请选择要删除的角色");
		} else {
			confirm("确定要删除？", function() {
				$.ajax({
					url : "rest/deleteEntity",
					data : {
						"type" : "role",
						"ids" : ids
					},
					type : "get",
					success : function(result) {
						if (result.successful == true) {
							deleteCheckedRows(roleTable);
						} else {
							alert("删除失败，请通知管理员");
						}
					}
				});
			});
		}
	}
</script>