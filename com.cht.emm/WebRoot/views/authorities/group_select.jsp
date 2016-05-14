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
			id="selectGroupTable">
			<thead>
				<tr>
					<th style="width:10%"><input type="checkbox" class="selectAll"
						onclick="selectAll(this)">
					</th>
					<th style="width:20%">组名</th>
					<th style="width:20%">描述</th>
					<th style="width:20%">父组</th>
					<th style="width:30%">子组</th>
				</tr>
			</thead>

			<!-- <tbody>
				 <c:forEach var="group" items="${groups }" >
				<tr>
					<td><input type="checkbox"  value="${group.id }"/>
					</td>
					<td>${group.groupName}</td>
					<td>${group.groupDesc}</td>
					<td>启用</td>
				</tr>
			</c:forEach>
			</tbody>
			  -->
		</table>
	</div>
</div>
<script type="text/javascript">
	var groupSelectTable = null;
	// 加载 用户组
	function search(o) {
		OSettings = groupSelectTable.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		groupSelectTable._fnFilterComplete(OSettings, {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		});

	}

	tableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "rest/group/pages",
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
		"aoColumns" : [
				{
					"mData" : "id",
					"mRender" : function(data, type, full) {
						//return data;
						return "<input type=\"checkbox\" value=\""+data+"\">";
					},
					"width" : "5%",
					"bSortable" : false,
					"bSearchable" : false
				},
				{
					"mData" : "groupName",
					"mRender" : function(data, type, full) {
						return "<a href=\"javascript:getGroupInfo('" + full.id
								+ "')\" >" + data + "</a>";
					}
				},
				{
					"mData" : "groupDesc",
					"bSortable" : false
				},
				{
					"mData" : "parentGroup",
					"mRender" : function(data, type, full) {
						if (data != null) {
							return "<a href=\"javascript:getGroupInfo('"
									+ data.id + "')\">" + data.groupName
									+ "</a>";
						} else {
							return "无";
						}
					},
					"bSearchable" : false,
					"bSortable" : false
				},
				{
					"mData" : "subGroups",
					"mRender" : function(data, type, full) {
						//console.log(data);
						//console.log(data.length);
						if (data != null && data.length > 0) {
							subgroups = '';
							$(data).each(
									function() {
										if (subgroups != '') {
											subgroups += ",";
										}
										subgroups += this.groupName;
										if (subgroups.length > 25) {
											subgroups = subgroups.substring(20)
													+ '....';
										}
									});
							return subgroups;
						} else {
							return "无";
						}
					},
					"bSearchable" : false,
					"bSortable" : false
				}

		]
	};

	function loadGroups() {
		groupSelectTable = $("#selectGroupTable").dataTable(tableSetting);
		LoadSelect2Script(MakeSelect);
	}

	$(document).ready(function() {
		LoadDataTablesScripts(loadGroups);
	});
</script>