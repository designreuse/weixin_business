<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">权限管理</a>
			</li>
			<li><a href="#">资源管理</a>
			</li>
		</ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="box no-drop">
			<div class="box-header">
				<div class="box-name subTitle">
					<span>权限管理/资源管理</span>
				</div>
				<div class="box-icons"></div>
				<div class="no-move"></div>
			</div>
			<div class="box-content">

				<div class="row">
					<div class="col-xs-10">
						<ul class="operate">
							<li class="tool_box_left"><a
								href="javascript:createResource();" title="新增资源"> <i
									class="fa fa-plus-square"></i><span class="hidden-xs">新增资源</span></a></li>
							<li class="tool_box_left"><a href="#"
								onClick="deleteResource(this)" title="删除资源" class="deleteIcon">
									<i class="fa fa-minus-square"></i><span class="hidden-xs">删除资源</span></a></li>
							<li class="tool_box_left"><a href="javascript:authsList();"
								title="操作权限列表"> <i class="fa fa-gavel"></i><span class="hidden-xs">操作权限列表</span></a></li>
						</ul>
					</div>
					<div class="col-xs-2 input-group searchInput">
						<input type="text" class="form-control" placeholder="搜索"> <span
							class="input-group-addon" onClick="search(this)"><i
							class="fa fa-search"></i> </span>
					</div>
				</div>

			</div>
			<div class="box-content no-padding table-responsive">

				<table
					class="table table-bordered table-striped table-hover table-heading table-datatable"
					id="resourceListTable">
					<thead>
						<tr>
							<th><input type="checkbox" class="selectAll"
								onclick="selectAll(this)"></th>
							<th>名称</th>
							<th>路径</th>
							<th>类型</th>
							<th>权限</th>
							<!-- <th>状态</th> -->
							<th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var resourceTable = null;

	function search(o) {
		OSettings = resourceTable.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		resourceTable._fnFilterComplete(OSettings, {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		});

	}

	tableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "rest/resource/all/pages",
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
						var disabled=false;
						if(full.isSystem!=0){
							disabled =true;
						}
						return "<input type=\"checkbox\" value=\""+data+"\""+(disabled?'disabled':'')+">";
					},
					"bSortable" : false,
					"bSearchable" : false
				},
				{
					"mData" : "name",
					"mRender" : function(data, type, full) {
						return "<a href=\"javascript:getResourceInfo('"
								+ full.id + "')\">" + data + "</a>";
					}
				},
				{
					"mData" : "uri"
				},
				{
					"mData" : "type",
					"mRender" : function(data, type, row) {
						if (data == 2) {
							return "文件";
						} else {
							return "url";
						}
					},
					"bSearchable" : false,
					"sClass":"hidden-xs"
				},
				{
					"mData" : "resourceAuths",
					"mRender" : function(data, type, full) {
						//console.log(data);
						auths = '';

						if (data != null) {

							$(data).each(function() {
								if (auths != '') {
									auths += ',';
								}
								auths += "<span>" + this.auth.name + "</span>";
							});
						} else {
							auths = '无';
						}

						return auths;
					},
					"bSortable" : false,
					"sClass":"hidden-xs"
				},
				/*
				{
					"mData" : "status",
					"mRender" : function(data, type, row) {
						return data == 1 ? "已启用" : "已禁用";
					},
					"bSearchable" : false,
					"bSortable" : false
				},
				 */
				{
					"mData" : null,
					"mRender" : function(data, type, row) {
					 	var click ='notAllowed()';
						if(row.isSystem!=1){
							click ="editResourceInfo('"
									+ row.id + "')";
						}
						return "<a class=\"link_status op\" onclick=\""+click+"\">编辑</a>";
					},
					"bSearchable" : false,
					"bSortable" : false
				}

		]
	};
	// 加载 用户组
	function loadResources() {
		resourceTable = $("#resourceListTable").dataTable(tableSetting);
		LoadSelect2Script(MakeSelect);
	}

	$(document).ready(function() {
		LoadDataTablesScripts(loadResources);
	});

	function authsList() {
		OpenBigDialog("权限列表", 'console/auth/list', 'user_auth_list', true, true);
	}

	function getResourceInfo(id) {
		OpenBigDialog("资源信息", 'console/resource/info?id=' + id,
				'user_group_info', true, true);
	}

	function editResourceInfo(id) {
		OpenBigDialogWithConfirm("编辑资源", 'console/resource/edit?id=' + id,
				'user_resource_edit', true, true, afterEditResource);
	}
	function createResource() {
		OpenBigDialogWithConfirm("创建资源", 'console/resource/add',
				'user_resource_create', true, true, aftercreateResource);
	}

	function aftercreateResource() {
		checkResult = false;
		$("#form_create_resource").bootstrapValidator('validate');
		var result = $("#form_create_resource").data("bootstrapValidator")
				.isValid();
		if (result) {
			resourceName = $("#user_resource_create").find(
					"input[name='resourceName']").first().val();
			resourceType = $("#user_resource_create").find(
					"select[name='resourceType']").first().val();
			resourceUri = $("#user_resource_create").find(
					"input[name='resourceUri']").first().val();
			isItem = $("#user_resource_create").find(
					"input[name='isItem']").first().is(":checked")?1:0;
			resourceAuth = '';
			$("input:checkbox[name=resourceAuth]:checked").each(function() {
				if (resourceAuth != '') {
					resourceAuth += ',';
				}
				resourceAuth += $(this).val();
			});
			$.ajaxSettings.async = false;
			$.ajax({
				url : "rest/resource/save",
				data : {
					"resourceName" : resourceName,
					"resourceType" : resourceType,
					"resourceUri" : resourceUri,
					"resourceAuth" : resourceAuth,
					"isItem":isItem
				},
				type : "post",
				success : function(result) {
					if (result.successful == true) {
						freshPage(resourceTable, {});
						checkResult = true;
					}
				}
			});
		}
		return checkResult;

	}

	function afterEditResource() {
		checkResult = false;
		$("#form_edit_resource").bootstrapValidator('validate');
		var result = $("#form_edit_resource").data("bootstrapValidator")
				.isValid();
		if (result) {
			resourceId = $("#user_resource_edit").find(
					"input[name='resourceId']").first().val();
			resourceName = $("#user_resource_edit").find(
					"input[name='resourceName']").first().val();
			resourceType = $("#user_resource_edit").find(
					"select[name='resourceType']").first().val();
			resourceUri = $("#user_resource_edit").find(
					"input[name='resourceUri']").first().val();
			isItem = $("#user_resource_edit").find(
					"input[name='isItem']").first().is(":checked")?1:0;
			resourceAuth = '';
			$("#user_resource_edit").find(
					"input:checkbox[name=resourceAuth]:checked").each(
					function() {
						if (resourceAuth != '') {
							resourceAuth += ',';
						}
						resourceAuth += $(this).val();
					});
			$.ajaxSettings.async = false;
			$.ajax({
				url : "rest/resource/edit",
				data : {
					"id" : resourceId,
					"resourceName" : resourceName,
					"resourceType" : resourceType,
					"resourceUri" : resourceUri,
					"resourceAuth" : resourceAuth,
					"isItem":isItem
					
				},
				type:"post",
				success : function(result) {
					if (result.successful == true) {
						freshPage(resourceTable, {});
						checkResult = true;
					}
				}
			});
		}
		return checkResult;
	}

	function deleteResource(o) {
		var ids = getSelectedIds(o);
		if (ids.length == 0) {
			alert("请选择要删除的群组");
		} else {
			 confirm("确定要删除？",function(){
				$.ajax({
					url : "rest/deleteEntity",
					data : {
						"type" : "resource",
						"ids" : ids
					},
					type : "get",
					success : function(result) {
						if (result.successful == true) {
							deleteCheckedRows(resourceTable);
						} else {
							alert("删除失败，请通知管理员");
						}
					}
				});
			});
		}
	}
</script>