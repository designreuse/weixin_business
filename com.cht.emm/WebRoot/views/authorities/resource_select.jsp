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
			id="resourceSelectTable">
			<thead>
				<tr>
					<th><input type="checkbox" class="selectAll"
						onclick="selectAll(this)"></th>
					<th>资源名称</th>
					<th>资源url</th>
					<th>权限</th>
					<th>角色</th>
				</tr>
			</thead>
			<!-- 
					<tbody>
						<c:forEach var="resource" items="${resources }">
							<tr>
								<th><input type="checkbox" value="${resource.id }"/>
								</th>
								<td><a href="javascript:getResourceInfo()">${resource.name }</a>
								</td>
								<td>${resource.uri }</td>
								<td><c:forEach var="op" items="${resource.resourceAuths }"  varStatus="rowstatus">
								<c:if test="${rowstatus.first==false }">,</c:if>
								${op.auth.name}
							</c:forEach></td>
							<td><a style="padding-left:15px">
								<c:forEach var="role" items="${resource.roles }"  varStatus="rowstatus">
									<c:if test="${rowstatus.first==false }">,</c:if>
									${role.role.roleName}
								</c:forEach></a>
							</td>
						</tr>
						</c:forEach>
					</tbody>
					 -->
		</table>
	</div>
</div>
<script type="text/javascript">
	// Run Datables plugin and create 3 variants of settings
	var resourceSelectTable = null;
	// 加载 用户组
	function search(o) {
		OSettings = resourceSelectTable.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		resourceSelectTable._fnFilterComplete(OSettings, {
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
			"mData" : "name"
		}, {
			"mData" : "uri",
			"bSortable" : false
		}, {
			"mData" : "resourceAuths",
			"mRender" : function(data, type, full) {
				var auths = "";
				$(data).each(function() {
					if (auths != "") {
						auths += ",";
					}
					auths += this.auth.name;
				});
				return auths;
			},
			"bSortable" : false
		}, {
			"mData" : "roles",
			"mRender" : function(data, type, full) {
				var roles = "";
				$(data).each(function() {
					if (roles != "") {
						roles += ",";
					}
					roles += this.role.roleName;
				});
				return roles;
			},
			"bSearchable" : true,
			"bSortable" : false
		} ]
	};
	function loadResources() {
		resourceSelectTable = $("#resourceSelectTable").dataTable(tableSetting);
		LoadSelect2Script(MakeSelect);
	}
	$(document).ready(function() {
		LoadDataTablesScripts(loadResources);
		WinMove();
	});
</script>