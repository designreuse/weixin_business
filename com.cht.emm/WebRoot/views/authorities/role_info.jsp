<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<style>
style>li {
	list-style: none;
}

.table-datatable span {
	margin-left: 10px;
}
</style>
<div class="row-fluid box" id="roleInfo">
	<div id="dashboard-header" class="row ">
		<div class="col-xs-10 col-sm-10">
			<h3>${ role.roleName}</h3>
		</div>
	</div>
	<div id="dashboard_links" class="col-xs-12 col-sm-2 pull-right">
		<ul class="nav nav-pills nav-stacked">
			<li class="active"><a href="#" class="link_dash" id="overview">一般信息</a>
			</li>
			<li><a href="#" class="link_dash" id="clients">角色用户</a></li>
			<li><a href="#" class="link_dash" id="graph">角色资源</a></li>
		</ul>
	</div>
	<div id="dashboard_tabs" class="col-xs-12 col-sm-10">
		<!--Start Dashboard Tab 1-->

		<div id="dashboard-overview" class="row"
			style="visibility: visible; position: relative;">
			<div class="row">
				<div class="col-xs-6">
					<div class="row">
						<div class="col-xs-12">
							<div class="row">
								<div class="col-xs-12">
									<div class="box no-drop">
										<div class="box-content">
											<h3 class="page-header">
												<i class="fa fa-exclamation-circle"></i>角色描述
											</h3>
											<p>
											<c:choose>
												<c:when test="${role.roleDesc ==null || role.roleDesc ==''}">无</c:when>
												<c:otherwise>${role.roleDesc }</c:otherwise>
											</c:choose>
											</p>
										</div>
									</div>
								</div>
							</div>

						</div>
					</div>
				</div>
				<div class="col-xs-6">
					<div class="row">
						<div class="col-xs-12">
							<div class="box no-drop">
								<div class="box-content">
									<h3 class="page-header">
										<i class="fa fa-mobile"></i>角色用户
									</h3>
									<p>
										<span id="role_info_user_count"><c:choose>
												<c:when test="${role.users!=null }">${fn:length(role.users) }</c:when>
												<c:otherwise>0</c:otherwise>
											</c:choose> </span>个用户
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<div class="row">
						<div class="col-xs-12">
							<div class="box no-drop">
								<div class="box-content">
									<h3 class="page-header">
										<i class="fa fa-mobile"></i>资源
									</h3>
									<p>
										<span id="role_info_resource_count"> <c:choose>
												<c:when test="${role.resources!=null }">${fn:length(role.resources) }</c:when>
												<c:otherwise>0</c:otherwise>
											</c:choose> </span>个资源
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--End Dashboard Tab 1-->
		<!--Start Dashboard Tab 2-->
		<div id="dashboard-clients" class="row"
			style="display: none; position: relative;">
			<div class="row one-list-message" >
						<a href="javascript:selectRoleUsers('${role.id}')"> <i
					class="fa fa-plus-square"></i><span class="hidden-xs">添加用户</span> </a>
				<a href="javascript:deltRoleUsers( )"> <i
					class="fa fa-minus-square"></i><span class="hidden-xs">删除用户</span>
				</a>
			</div>
			<div class="row one-list-message">
				<div id="roleUserLs">
					<c:forEach var="user" items="${role.users }">
						<div class="checkbox-inline">
							<label><input type="checkbox" value="${user.id }">${user.username}<i
								class="fa fa-square-o"></i> </label>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<!--Start Dashboard Tab 3-->
		<div id="dashboard-graph" class="row"
			style="display: none; position: relative;">
			<div class="row one-list-message" <c:if test="${role.isSystem == 1 }">style="display:none"</c:if> >
				<a href="javascript:selectRoleResource('${role.id}')"> <i
					class="fa fa-plus-square"><span class="hidden-xs"> 关联资源</span>
				</i> </a>
			</div>
			<div class="row one-list-message">
				<div class="row">
					<div class="col-xs-8">
						<table
							class="table table-bordered table-striped table-hover table-heading table-datatable"
							id="datatable-roleresource">
							<thead>
								<tr>
									<th>资源</th>
									<th>权限</th>
									<c:if test="${role.isSystem != 1 }"><th>操作</th></c:if>
									
								</tr>
							</thead>
							<tbody>
								<c:forEach var="resource" items="${role.resources }">
									<tr>
										<td><input type="hidden" name="resourceId"
											value="${resource.resource.id }">${resource.resource.name}</td>
										<td><c:forEach var="ra" items="${resource.resourceAuths}">
												<span id="${ra.auth.id }"
													<c:if test="${ra.selected==false }">style="display: none;"</c:if>>
													${ra.auth.name} </span>

											</c:forEach></td>
											
										<c:if test="${role.isSystem != 1 }"><td><a href="#" class="op_mod">修改</a> <a href="#"
											class="op_del">删除</a>
										</td></c:if>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="col-xs-4">
						<div id="role_jstree"></div>
					</div>
				</div>
			</div>

		</div>
		<!--End Dashboard Tab 3-->
	</div>
	<div class="clearfix"></div>
</div>


<script type="text/javascript">
	var allAuth = null;
	var roleId = '${role.id}';
	var resourceTable = null;
	function ResourceTable() {
		resourceTable = TableXDefaultPageSize("datatable-roleresource");
		LoadSelect2Script(MakeSelect);

	}

	function loadRoleMenu() {

		$('#role_jstree').jstree({
			'core' : {
				'data' : {
					"url" : "rest/menu/nodes/roleIds?roleIds=" + roleId,
					"dataType" : "json",
					"type" : "POST"// needed only if you do not supply JSON headers
				}
			}
		});

	}

	function op_mod(o) {

		td = $(o).parent().prev();
		//console.log(td);
		if ($(o).text() == '修改') {
			text = $(td).find("span");

			for (x = 0; x < text.length; x++) {
				//console.log(text[x]);
				if ($(text[x]).is(":hidden")) {
					$(text[x]).prepend("<input type='checkbox' />");
					$(text[x]).show();
				} else {
					$(text[x]).prepend("<input type='checkbox' checked/>");
				}
			}
			$(o).text('保存');
		} else {
			auths = '';
			$(td).find("input:checked").each(function() {
				if (auths != '') {
					auths += ',';
				}
				auths += $(this).parent("span").attr('id');
			});
			resourceId = $(td).parent("tr").find("input:hidden").first().val();
			;
			$.ajax({
				url : "rest/resourcerole/save",
				data : {
					"resourceId" : resourceId,
					"roleId" : roleId,
					"authId" : auths
				},
				type : "post",
				success : function(result) {
					if (result.successful) {
						$(o).text('修改');
						$(td).find("input:checked").remove();
						$(td).find("input[type='checkbox']").not(
								"input:checked").parent().hide();
						$(td).find("input[type='checkbox']").not(
								"input:checked").remove();
						reloadJsTree();
					}
				}
			});
		}

	}

	function reloadJsTree() {
		ref = $('#role_jstree').jstree(true);
		ref.refresh();
	}

	function op_del(o) {
		confirm("确定要删除？", function() {
			resourceId = $(o).closest('tr').find("input:hidden").first().val();
			$
					.ajax({
						url : "rest/deleteUnionEntity",
						data : {
							"sids" : resourceId,
							"pid" : roleId,
							"type" : "roleresource"
						},
						type : "get",
						success : function(result) {
							if (result.successful) {
								//$(o).closest('tr').remove();
								deleteRowsByIds(resourceTable, resourceId);
								count = parseInt($("#role_info_resource_count")
										.text());
								$("#role_info_resource_count").text(count - 1);
								reloadJsTree();
							}
						}
					});
		});
	}
	$(document).ready(function() {
		allAuth = getAllAuth();
		loadJsTree(loadRoleMenu);
		var ids = new Array();
		$("a.link_dash").click(function() {
			if (ids != null && ids.length == 0) {
				$("a.link_dash").each(function() {
					ids.push($(this).attr("id"));
				});
			}

			id = $(this).attr("id");
			for ( var i in ids) {
				$("#dashboard-" + ids[i]).hide();
			}
			$(this).closest('ul').find('li').each(function() {
				$(this).removeClass('active');
			});
			$("#dashboard-" + id).show();
			$("#" + id).parent('li').addClass('active');
		});

		$("a.op_mod").click(function() {
			op_mod(this);
		});
		$("a.op_del").click(function() {
			op_del(this);
		});
		LoadDataTablesScripts(ResourceTable);
	});

	function selectRoleUsers(id) {
		selectUsers('role', id, saveRoleUser);
	}

	function saveRoleUser() {
		// 保存用户和组关联
		var userIds = '';
		resultCheck = false;
		var checked = $("#userselectTable").find(
				"input[type='checkbox']:checked").not(".selectAll");
		if (checked.length == 0) {
			alert("请选择至少一个用户组");
			resultCheck = false;
		} else {
			$(checked).each(function() {
				if (userIds != '') {
					userIds += ',';
				}
				userIds += $(this).val();
			});

			$.ajaxSettings.async = false;
			$
					.ajax({
						url : "rest/roleuser/save",
						data : {
							"userIds" : userIds,
							"roleId" : roleId
						},
						type : "post",
						success : function(result) {
							if (result.successful == true) {
								users = result.resultValue;
								if (users == null || users.length == 0) {

								} else {
									$("#roleUserLs").empty();
									for (i = 0; i < users.length; i++) {
										$("#roleUserLs")
												.append(
														"<div class='checkbox-inline'><label><input type='checkbox'  value='"+users[i].id+"'>"
																+ users[i].username
																+ "<i class='fa fa-square-o'></i></label></div>");
									}
									$("#role_info_user_count").text(
											users.length);
								}
								resultCheck = true;
							}
						}
					});
		}
		return resultCheck;
	}

	function selectRoleResource() {
		selectResources('role', roleId, saveRoleResource);
	}

	function saveRoleResource() {
		var resourceIds = '';
		resultCheck = false;
		$("#select_all_resource").find("input[type='checkbox']:checked").not(
				".selectAll").each(function() {
			//	console.log($(this));
			if (resourceIds != '') {
				resourceIds += ',';
			}
			resourceIds += $(this).val();
		});

		$.ajaxSettings.async = false;
		$
				.ajax({
					url : "rest/roleresource/add",
					data : {
						"resourceIds" : resourceIds,
						"roleId" : roleId
					},
					type : "post",
					success : function(result) {
						if (result.successful == true) {
							resources = result.resultValue;
							if (resources == null || resources.length == 0) {

							} else {

								for (i = 0; i < resources.length; i++) {
									resourcename = "<input type=\"hidden\" name=\"resourceId\" value=\""+resources[i].resource.id+"\">"
											+ resources[i].resource.name;
									resourceAuths = resources[i].resourceAuths;
									auths = '';
									if (resourceAuths != null)
										for (j = 0; j < resourceAuths.length; j++) {
											diplay = resourceAuths[j].selected == true ? ""
													: "style='display:none'";
											auths += "<span id='" + resourceAuths[j].auth.id+ "' " + diplay+ ">"
													+ resourceAuths[j].auth.name
													+ "</span>";

										}
									resourceTable = $("#datatable-roleresource")
											.dataTable();
									resourceTable
											.fnAddData(
													[
															resourcename,
															auths,
															"<a href=\"#\" class=\"op_mod\" onclick=\"op_mod(this)\">修改</a> <a href=\"#\" onclick=\"op_del(this)\" class=\"op_del\">删除</a>" ],
													true);
								}
								count = parseInt($("#role_info_resource_count")
										.text());
								$("#role_info_resource_count").text(count + 1);
							}
							reloadJsTree();
							resultCheck = true;
						}
					}
				});
		return resultCheck;
	}

	//删除用户-角色关联
	function deltRoleUsers() {
		// div_dash_group_list
		var checked = $("#roleUserLs").find("input[type='checkbox']:checked")
				.not(".selectAll");
		resultCheck = false;
		if (checked.length == 0) {
			alert("请选择至少一个角色用户！");
			resultCheck = false;
		} else {
			confirm("确定要删除？", function() {
				var ids = '';
				delcount = 0;
				$(checked).each(function() {
					if (ids != '') {
						ids += ',';
					}
					ids += $(this).val();
					delcount++;
				});
				$.ajaxSettings.async = false;
				$.ajax({
						url : "rest/roleuser/remove",
						data : {
							"roleId" : roleId,
							"userIds" : ids
						},
						type : "post",
						success : function(result) {
							if (result.successful == true) {
								$(checked).each(function() {
									$(this).parent().parent().remove();
	
								});
								count = parseInt($("#role_info_user_count")
										.text());
								$("#role_info_user_count").text(
										count - delcount);
								resultCheck = true;
							}
						}
					});
			});
		}
		return resultCheck;
	}
</script>