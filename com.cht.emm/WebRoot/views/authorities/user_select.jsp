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
			id="userselectTable">
			<thead>
				<tr>
					<th><input type="checkbox" class="selectAll"
						onclick="selectAll(this)">
					</th>
					<th>账号</th>
					<th>用户姓名</th>
					<th>所在组</th>
					<th>角色</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	// Run Datables plugin and create 3 variants of settings
	var userSelectTable = null;
	function search(o) {
		OSettings = userSelectTable.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		userSelectTable._fnFilterComplete(OSettings, {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		});

	}
	tableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "rest/user/all/pages",
		"fnServerParams" : function(aoData) {
			aoData.push({
				"name" : "type",
				"value" : "${type}"
			}, {
				"name" : "id",
				"value" : "${id}"
			});
		},
		"aaSorting" : [ [ 1, "asc" ] ],
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
					"mData" : "username",
					"mRender" : function(data, type, full) {
						return "<a href=\"javascript:getUserInfo('" + full.id
								+ "')\">" + data + "</a>";
					}
				}, {
					"mData" : "userAlias"
				}, {
					"mData" : "groups",
					"mRender" : function(data, type, full) {
						group = '';
						if (data == null) {
							group = '无';

						} else {
							//console.log("group: " + data);
							$.each(data, function(i, g) {
								if (group != '') {
									group += ',';
								}
								group += "<span>" + g.groupName + "</span>";
							});

						}

						return group;

					},
					"bSortable" : false
				}, {
					"mData" : "roles",
					"mRender" : function(data, type, full) {
						//console.log("roles: " + data);
						role = '';
						if (data != null) {
							$.each(data, function(i, r) {
								if (role != '') {
									role += ",";
								}
								role += "<span>" + r.roleName + "</span>";

							});
						} else {
							role = "无";

						}

						return role;
					},
					"bSortable" : false
				},

		]
	};

	function loadSelectedUsers() {

		userSelectTable = $("#userselectTable").dataTable(tableSetting);
		LoadSelect2Script(MakeSelect);

	}
	$(document).ready(function() {

		LoadDataTablesScripts(loadSelectedUsers);
	});
</script>