<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div class="col-xs-10"></div>
	<div class="col-xs-2 input-group searchInput">
		<input type="text" class="form-control" placeholder="搜索"> <span
			class="input-group-addon" onClick="search(this)"><i
			class="fa fa-search"></i>
		</span>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<table
			class="table table-bordered table-striped table-hover table-heading table-datatable"
			id="selectRolesTable">
			<thead>
				<tr>
					<th><input type="checkbox" class="selectAll"
						onclick="selectAll(this)"></th>
					<th>角色名</th>
					<th>描述</th>
					<th>状态</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	// Run Datables plugin and create 3 variants of settings

	var roleSelectTable = null;
	// 加载 用户组
	function search(o) {
		OSettings = roleSelectTable.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		roleSelectTable._fnFilterComplete(OSettings, {
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
		"fnServerParams" : function(aoData) {
			aoData.push({
				"name" : "type",
				"value" : "${type}"
			}, {
				"name" : "id",
				"value" : "${id}"
			});
		},
		"sDom" : "rt<'box-content'<'col-sm-12'<'tool_box_pageInfo' ilp>> <'clearfix'>>",
		"sPaginationType" : "bootstrap",
		"oLanguage" : {
			"sSearch" : ""
		},
		"aoColumns" : [ {
			"mData" : "id",
			"mRender" : function(data, type, full) {
				//return data;
				return "<input type=\"checkbox\" value=\""+data+"\">";
			},
			"width" : "5%",
			"bSortable" : false,
			"bSearchable" : false
		}, {
			"mData" : "roleName"
		}, {
			"mData" : "roleDesc",
			"bSortable" : false
		}, {
			"mData" : "status",
			"mRender" : function(data, type, full) {
				if (data == 1) {
					return "已启用";
				} else {
					return "停用";
				}
			},
			"bSearchable" : false,
			"bSortable" : false
		} ]
	};
	function loadRoles() {
		roleSelectTable = $("#selectRolesTable").dataTable(tableSetting);
		LoadSelect2Script(MakeSelect);
	}
	$(document).ready(function() {
		// Load Datatables and run plugin on tables 
		LoadDataTablesScripts(loadRoles);
		// Add Drag-n-Drop feature
		WinMove();
	});
</script>