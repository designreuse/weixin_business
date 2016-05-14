<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">应用管理</a>
			</li>
			<li><a href="#">应用分类</a>
			</li>
		</ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="box">
			<div class="box-header">
				<div class="box-name subTitle">
					<span>应用管理/应用分类</span>
				</div>
				<div class="box-icons"></div>
				<div class="no-move"></div>
			</div>
			<div class="box-content" style="margin-bottom: 0.5em">
				<div class="row">
					<div class="col-xs-10">
						<ul class="operate">
							<li class="tool_box_left"><a href="javascript:addAppType();"
								title="新增应用分类"> <i class="fa fa-plus-square"></i><span
									class="hidden-xs">新增应用分类</span> </a>
							</li>
							<li class="tool_box_left"><a href="#"
								onClick="deleteAppType(this)" title="删除应用分类" class="deleteIcon">
									<i class="fa fa-minus-square"></i><span class="hidden-xs">删除应用分类</span>
							</a>
							</li>
						</ul>
					</div>
					<div class="col-xs-2 input-group searchInput">
						<input type="text" class="form-control" placeholder="搜索">
						<span class="input-group-addon" onClick="search(this)"><i
							class="fa fa-search"></i>
						</span>
					</div>
				</div>
			</div>
			<div class="box-content no-padding table-responsive">

				<table
					class="table table-bordered table-striped table-hover table-heading table-datatable"
					id="appTypeList">
					<thead>
						<tr>
							<th><input type="checkbox" class="selectAll"
								onclick="selectAll(this)">
							</th>
							<th>分类名称</th>
							<th>应用个数</th>
							<th>分类描述</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var appTypeTable = null;
	function search(o) {
		OSettings = appTypeTable.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		appTypeTable._fnFilterComplete(OSettings, {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		});

	}
	tableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "rest/apptype/all/pages",
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
						return "<input type=\"checkbox\" value=\""+data+"\">";
					},
					"bSortable" : false,
					"bSearchable" : false
				},
				{
					"mData" : "name"
				},
				{
					"mData" : "count",
					"mRender" : function(data, type, full) {
						return "<a href=\"javascript:getAppsByType('" + full.id
								+ "')\">" + data + "</a>";
					},
					"bSortable" : false,
					"bSearchable" : false
				},
				{
					"mData" : "description"
				},
				{
					"mData" : null,
					"mRender" : function(data, type, row) {
						return "<a href=\"#\" class=\"link_status op\" onclick=\"editAppType('"
								+ row.id + "')\">编辑</a>";
					},
					"bSearchable" : false,
					"bSortable" : false
				}

		]
	};
	function addAppType() {
		id = "";
		name = "";
		description = "";
		OpenBigDialogWithConfirm2("创建应用分类", 'console/apps_type_new',
				'apps_type_new', true, true, createAppType);
	}
	function createAppType() {
		var successful = false;
		$('#form_create_apptype').bootstrapValidator('validate');
		var result = $("#form_create_apptype").data("bootstrapValidator")
				.isValid();
		if (result) {
			var name = $("#apps_type_new").find("input[id='name']").first()
					.val();
			var description = $("#apps_type_new").find(
					"input[id='description']").first().val();

			var json = "{\"name\":\"" + name + "\",\"description\":\""
					+ description + "\"}";

			$.ajax({
				url : "rest/apptype/add",
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
					freshPage(appTypeTable, {});
					successful = true;
				},
				error : function(p_request, p_status, p_err) {
					successful = false;
				}
			});
		}
		return successful;
	}
	function editAppType(appTypeId) {
		if (appTypeId == '0') {
			alert("[默认分类]无法编辑！");
			return;
		}
		id = appTypeId;
		OpenBigDialogWithConfirm2("编辑应用分类", 'console/apps_type_new',
				'apps_type_new', true, true, updateAppType);
	}
	function updateAppType() {
		var successful = false;
		$('#form_create_apptype').bootstrapValidator('validate');
		var result = $("#form_create_apptype").data("bootstrapValidator")
				.isValid();
		if (result) {
			var name = $("#apps_type_new").find("input[id='name']").first()
					.val();
			var description = $("#apps_type_new").find(
					"input[id='description']").first().val();

			var json = "{\"name\":\"" + name + "\",\"description\":\""
					+ description + "\",\"id\":\"" + id + "\"}";
			$.ajax({
				url : "rest/apptype/add",
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
					freshPage(appTypeTable, {});
					successful = true;
				},
				error : function(p_request, p_status, p_err) {
					successful = false;
				}
			});
		}
		return successful;
	}

	function deleteAppType(o) {
		var ids = getSelectedIds(o);
		if (ids.length == 0) {
			alert("请选择待删除的应用分类！");
			return;
		}
		confirm("确定要删除选中的应用分类吗？[默认分类]无法删除", function() {
			$.ajax({
				url : "rest/apptype/delete",
				type : "POST",
				async : true,
				data : {
					ids : ids
				},
				success : function(p_resultValue) {
					freshPage(appTypeTable, {});
				},
				error : function(p_request, p_status, p_err) {
				}
			});
		});
	}
	function getAppsByType(id) {
		appTypeId = id;
		OpenBigDialog("应用列表", "console/apps_type_apps", 'apps_type_apps', true,
				true);
	}
	function loadAppTypes() {
		appTypeTable = $("#appTypeList").dataTable(tableSetting);
		LoadSelect2Script(MakeSelect);
	}
	$(document).ready(function() {
		LoadDataTablesScripts(loadAppTypes);
	});
</script>
