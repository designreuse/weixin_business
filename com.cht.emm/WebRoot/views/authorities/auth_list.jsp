<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div class="col-xs-12">
		<div class="box no-drop">
			<div class="box-header">
				<div class="box-name subTitle">
					<i class="fa fa-user"></i> <span>操作权限列表</span>
				</div>
				<div class="box-icons"></div>
				<div class="no-move"></div>
			</div>
			<div class="box-content" >

				<div class="row">
					<div class="col-xs-10">
						<ul class="operate">
							<li class="tool_box_left"><a href="javascript:createAuth();"
								title="新增操作 "> <i class="fa fa-plus-square"></i><span class="hidden-xs">新增操作权限</span> </a>
							</li>
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
					id="authTables">
					<thead>
						<tr>
							<th><input type="checkbox" class="selectAll"
								onclick="selectAll(this)"></th>
							<th>权限名称</th>
							<th>描述</th>
							<th>位索引</th>
							<th>显示索引</th>
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
	var authTable = null;
	function search(o) {
		OSettings = authTable.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		authTable._fnFilterComplete(OSettings, {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		});

	}

	tableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "rest/auths/all/pages",
		"aaSorting" : [ [ 3, "asc" ] ],
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
						return "<input type=\"checkbox\" value=\""+data+"\">";
					},
					"bSortable" : false,
					"bSearchable" : false
				},
				{
					"mData" : "name",
					"bSortable" : true,
					"bSearchable" : true
				},
				{
					"mData" : "descp",
					"bSortable" : false
				},
				{
					"mData" : "locIndex",
					"bSearchable" : false,
					"bSortable" : true
				},
				{
					"mData" : "showIndex",
					"bSearchable" : false,
					"bSortable" : true,
					"sClass":"hidden-xs"
				},
				{
					"mData" : null,
					"mRender" : function(data, type, row) {
						return "<a class=\"link_status op\" onclick=\"editAuthInfo('"
								+ row.id + "')\">编辑</a>";
					},
					"bSearchable" : false,
					"bSortable" : false
				}

		]
	};

	// 加载 用户组
	function loadAuths() {
		authTable = $("#authTables").dataTable(tableSetting);
		LoadSelect2Script(MakeSelect);
	}

	$(document).ready(function() {
		LoadDataTablesScripts(loadAuths);
	});

	function createAuth() {
		OpenBigDialogWithConfirm("创建操作权限", 'console/auth/add',
				'create_auth', true, true, aftercreateAuth);
	}

	function editAuthInfo(id) {
		OpenBigDialogWithConfirm("编辑操作权限", 'console/auth/edit?id=' + id,
				'edit_auth', true, true, aftereditAuth);
	}

	function aftercreateAuth() {
		checkResult = false;
		$("#form_create_auth").bootstrapValidator('validate');
		var result = $("#form_create_auth").data("bootstrapValidator")
				.isValid();
		if (result) {
			name = $("#create_auth").find("input[name='name']").first().val();
			descp = $("#create_auth").find("input[name='descp']").first().val();
			showIndex = $("#create_auth").find("#showIndex").val();
			$.ajaxSettings.async = false;
			$.ajax({
				url : "rest/auth/saveOrupdate",
				data : {
					"name" : name,
					"descp" : descp,
					"showIndex" : showIndex
				},
				type : "post",
				success : function(result) {
					if (result.successful == true) {
						freshPage(authTable, {});
						checkResult = true;
					} else {
						alert("操作失败，请稍后再试");
					}
				}
			});

		}
		return checkResult;

	}

	function aftereditAuth() {
		checkResult = false;
		$("#form_edit_auth").bootstrapValidator('validate');
		var result = $("#form_edit_auth").data("bootstrapValidator").isValid();
		if (result) {
			id = $("#edit_auth").find("input[name='id']").first().val();
			name = $("#edit_auth").find("input[name='name']").first().val();

			descp = $("#edit_auth").find("input[name='descp']").first().val();
			//console.log("descp :" + descp);
			showIndex = $("#edit_auth").find("#showIndex").val();
			//console.log("showIndex :" + showIndex);
			$.ajaxSettings.async = false;
			$.ajax({
				url : "rest/auth/saveOrupdate",
				data : {
					"id" : id,
					"name" : name,
					"descp" : descp,
					"showIndex" : showIndex
				},
				type : "post",
				success : function(result) {
					if (result.successful == true) {
						freshPage(authTable, {});
						checkResult = true;
					} else {
						alert("操作失败，请稍后再试");
					}
				}
			});
		}
		return checkResult;
	}
</script>