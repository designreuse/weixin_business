<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="dashboard-header" class="row">
	<div class="col-xs-10 col-sm-4">
		<h3 id="name"></h3>
	</div>
</div>
<div class="row-fluid">
	<div id="dashboard-overview" class="row"
		style="display: block; position: relative;">
		<div class="row">
			<div class="col-xs-5">
				<div class="row">
					<div class="col-xs-12">
						<div class="row">
							<div class="col-xs-12">
								<div class="box no-drop">
									<div class="box-content">
										<h3>
											<p class="page-header">未授权部门</p>
										</h3>
										<div id="lazy" class="demo"></div>
									</div>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
			<div class="col-xs-2" style="height: 380px; line-height: 380px;">
				<div class="row">
					<div class="col-xs-12">
						<div class="box no-drop">
							<div class="box-content">
								<a href="javascript:authorize()">应用授权<i class="fa fa-arrow-right"></i></a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-5">

				<div class="row">
					<div class="col-xs-12">
						<div class="box no-drop">
							<div class="box-content">
								<h3>
									<p class="page-header">已授权部门</p>
								</h3>
								<div class="box-content no-padding table-responsive">

									<table
										class="table table-bordered table-striped table-hover table-heading table-datatable"
										id="appAuthList">
										<thead>
											<tr>
												<th>部门</th>
												<th>权限</th>
												<th>操作</th>
											</tr>
										</thead>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<script type="text/javascript">
	var appAuthTable = null;
	function authorize() {
		OpenBigDialogWithConfirm2("应用权限", 'console/apps_permission',
				'apps_permission', true, true, authorizeApp);
	}

	function authorizeApp() {
		var successful = false;
		var groups = $("#lazy").jstree("get_checked").join(",");
		if (!groups) {
			alert("请选择部门！");
			return false;
		}
		var type = $("#apps_permission").find("input[id='install']")[0].checked ? 1
				: -1;
		var json = "{\"app_id\":\"" + id + "\",\"group_id\":\"" + groups
				+ "\",\"type\":\"" + type + "\"}";
		$.ajax({
			url : "rest/appauth/add",
			type : "POST",
			contentType : 'application/json',
			processData : false,
			dataType : 'json',
			async : false,
			data : json,
			success : function(p_resultValue) {
				if (!p_resultValue.successful) {
					alert(p_resultValue.resultMessage);
					return;
				}
				successful = true;
				$('#lazy').jstree().refresh();
				freshPage(appAuthTable, {});
			},
			error : function(p_request, p_status, p_err) {
				successful = false;
			}
		});
		return successful;
	}
	tableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "rest/appauth/" + id,
		"aaSorting" : [ [ 0, "asc" ] ],
		"aLengthMenu" : [ [ 10, 20, 50 ], [ 10, 20, 50 ] ],
		"sDom" : "rt<'box-content'<'col-sm-12'<'tool_box_pageInfo' ilp>> <'clearfix'>>",
		"sPaginationType" : "bootstrap",
		"oLanguage" : {
			"sSearch" : "",
			"sLengthMenu" : "",
			"sInfo" : "",
			"sInfoEmpty": ""
		},
		"aoColumns" : [

				{
					"mData" : "group_name",
					"bSortable" : false,
					"bSearchable" : false
				},
				{
					"mData" : "type",
					"mRender" : function(data, type, full) {
						return data == 1 ? "允许安装" : "禁止安装";
					},
					"bSortable" : false,
					"bSearchable" : false
				},
				{
					"mData" : null,
					"mRender" : function(data, type, row) {
						return "<a href=\"#\" class=\"link_status op\" onclick=\"cancelAppAuth('"
								+ row.id + "')\">取消授权</a>";
					},
					"bSearchable" : false,
					"bSortable" : false
				}

		]
	};
	function loadAppAuthTable() {
		appAuthTable = $("#appAuthList").dataTable(tableSetting);
		LoadSelect2Script(MakeSelect);
	}
	function cancelAppAuth(appAuthID) {
		$.ajax({
			url : "rest/appauth/delete?id=" + appAuthID,
			type : "POST",
			contentType : 'application/json',
			processData : false,
			dataType : 'json',
			async : false,
			success : function(p_resultValue) {
				if (!p_resultValue.successful) {
					alert(p_resultValue.resultMessage);
					return;
				}
				successful = true;
				$('#lazy').jstree().refresh();
				freshPage(appAuthTable, {});
			},
			error : function(p_request, p_status, p_err) {
				successful = false;
			}
		});
	}
	$(document).ready(function() {
		document.getElementById('name').innerHTML = name;
		$('#lazy').jstree({
			"plugins" : [ "checkbox" ],
			"checkbox" : {
				"three_state" : false
			},
			'core' : {
				'data' : {
					"url" : "rest/appauth/grouptree?id=" + id,
					"dataType" : "json"
				}
			}
		});
		LoadDataTablesScripts(loadAppAuthTable);
	});
</script>
