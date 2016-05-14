<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
li {
	list-style: none;
}

.table-datatable span {
	margin-left: 10px;
}
</style>
<div class="row-fluid">
 <div id="dashboard-header" class="row ">
		<div class="col-xs-10 col-sm-10">
			<h3>${ resource.name}</h3>
		</div>
	</div>
	<div id="dashboard_links" class="col-xs-12 col-sm-2 pull-right">
		<ul class="nav nav-pills nav-stacked">
			<li class="active"><a href="#" class="link_dash" id="overview">一般信息</a>
			</li>
			<li><a href="#" class="link_dash" id="clients">操作权限</a>
			</li>
			<li><a href="#" class="link_dash" id="graph">角色</a>
			</li>
		</ul>
	</div>
	<div id="dashboard_tabs" class="col-xs-12 col-sm-10">
		<!--Start Dashboard Tab 1-->
		<div id="dashboard-overview" class="row"
			style="display: block; position: relative;">
			<div class="row">
				<div class="col-xs-6">
					<div class="row">
						<div class="col-xs-12">
							<div class="row">
								<div class="col-xs-12">
									<div class="box no-drop">
										<div class="box-content">
											<h3 class="page-header">
												<i class="fa fa-exclamation-circle"></i>资源信息
											</h3>
											<h4>路径</h4>
											<p>${resource.uri }</p>
											<h4>类型</h4>
											<p>
												<c:choose>
													<c:when test="${ resource.type ==1}">url</c:when>
													<c:otherwise>文件</c:otherwise>
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
										<i class="fa fa-mobile"></i>所有权限
									</h3>
									<p>
										<span id="resource_info_auths_count"> <c:choose>
												<c:when test="${ fn:length(resource.resourceAuths)>0 }">
											${ fn:length(resource.resourceAuths) }
									
										</c:when>
												<c:otherwise>0</c:otherwise>
											</c:choose> </span>个权限

									</p>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div class="box no-drop">
								<div class="box-content">
									<h3 class="page-header">
										<i class="fa  fa-certificate"></i>授权角色
									</h3>
									<p>
										<span id="resource_info_roles_count"><c:choose>
												<c:when test="${ fn:length(resource.roles)>0 }">
												${ fn:length(resource.roles) }
											</c:when>
												<c:otherwise>0</c:otherwise>
											</c:choose> </span>个角色

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
			<div class="row one-list-message" <c:if test="${resource.isSystem == 1 }">style="display:none"</c:if>>
				<a href="javascript:modifyAuth()" id="trigger_bnt"> <i
					class="fa fa-plus-square"></i><span class="hidden-xs">修改</span> </a>
			</div>
			<div class="row one-list-message">
				<div class="row">
					<div class="col-xs-12" id="box_left">
						<table
							class="table table-bordered table-striped table-hover table-heading table-datatable">
							<thead>
								<tr class="head">
									<th>权限名称</th>
									<th>路径</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${ resource.resourceAuths != null}">
										<c:forEach var="auth" items="${resource.resourceAuths}">
											<tr id="left_${auth.auth.id }" class="content">
												<td>${auth.auth.name }</td>
												<td>${auth.subUri }</td>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr></tr>
									</c:otherwise>
								</c:choose>

							</tbody>

						</table>
					</div>
					<div class="col-xs-1" id="box_middle" style="display:none">
						<div style="float:left;padding:70px 5px">
							<a href="javascript:moveLeft()"><i class="fa fa-chevron-left"></i>
							</a>
						</div>
						<div style="float:left;padding:70px 5px">
							<a href="javascript:moveRight()"><i
								class="fa fa-chevron-right"></i> </a>
						</div>

					</div>
					<div class="col-xs-5" id="box_right" style="display:none">
						<table
							class="table table-bordered table-striped table-hover table-heading table-datatable">
							<thead>
								<tr>
									<th></th>
									<th>权限名称</th>
									<th>描述</th>
								</tr>
							</thead>
							<tbody>
							</tbody>

						</table>
					</div>
				</div>
			</div>
		</div>
		<!--End Dashboard Tab 2-->
		<!--Start Dashboard Tab 3-->
		<div id="dashboard-graph" class="row"
			style="display:none; position: relative;">
			<div class="row one-list-message" <c:if test="${resource.isSystem == 1 }">style="display:none"</c:if>>
				<a href="javascript:getAllResourceRole('${resource.id}')"> <i
					class="fa fa-plus-square"></i><span class="hidden-xs">关联角色</span>
				</a>
			</div>
			<div class="row one-list-message">
				<div class="row">
					<div class="col-xs-12">
						<table
							class="table table-bordered table-striped table-hover table-heading table-datatable"
							id="datatable-resourcerole">
							<thead>
								<tr>
									<th>角色</th>
									<th>权限</th>
									<c:if test="${resource.isSystem != 1 }"><th>操作</th></c:if>
									
								</tr>
							</thead>
							<tbody>
								<c:forEach var="role" items="${resource.roles }">
									<tr>
										<td><input type="hidden" name="roleId"
											value="${role.role.id }"> ${role.role.roleName }</td>
										<td><c:forEach var="auth" items="${role.resourceAuth}">
												<span id="${auth.auth.id}"
													<c:if test="${auth.selected==false }"> style="display:none"</c:if>>${auth.auth.name}</span>
											</c:forEach>
											<c:if test="${resource.isSystem != 1 }">
											<td>
												<a href="#" class="op_mod">修改</a> 
												<a href="#"	class="op_del">删除</a>
												</td>
										</c:if>
										
									</tr>
								</c:forEach>
							</tbody>

						</table>
					</div>
				</div>
			</div>
		</div>

		<!--End Dashboard Tab 4-->
	</div>
	<div class="clearfix"></div>
</div>
<script type="text/javascript">
	var allAuth = null;
	var used = new Array();
	var resourceId = '${resource.id}';
	var resourceRoleTable = null;

	function ResourceRoleTable() {
		resourceRoleTable = TableXDefaultPageSize("datatable-resourcerole");
		LoadSelect2Script(MakeSelect);

	}

	function op_delete(o) {
		confirm("确定要删除？",
				function() {
					roleId = $(o).closest("tr").find('input:hidden').first()
							.val();
					$
							.ajax({
								url : "rest/deleteUnionEntity",
								data : {
									"type" : "resourcerole",
									"pid" : resourceId,
									"sids" : roleId
								},
								type : "get",
								success : function(result) {
									if (result.successful) {

										//resourceRoleTable = $("#datatable-resourcerole")
										//		.dataTable();
										deleteRowsByIds(resourceRoleTable,
												roleId);
										///$(o).closest("tr").remove();
										resourceRoleTable.fnDraw();
										count = parseInt($(
												"#resource_info_roles_count")
												.text());
										$("#resource_info_roles_count").text(
												count - 1);
									} else {
										alert("删除失败，请重试");
									}
								}
							});

				});

	}
	function op_mod(o) {
		console.log("resource info");
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
			roleId = $(td).parent("tr").find('input:hidden').first().val();
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
					}
				}
			});

		}
	}
	$(document).ready(function() {
		allAuth = getAllAuth();
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
			op_delete(this);
		});
		LoadDataTablesScripts(ResourceRoleTable);
	});

	function modifyAuth() {
		$("#box_left").toggleClass("col-xs-12").toggleClass("col-xs-6");
		$("#box_middle").show();
		$("#box_right").show();
		//$("#box_left").find('thead').prepend("<th><input type='checkbox' ></th>");
		$("#box_left").find('tr.content').each(
				function() {
					$(this).prepend(
							"<td><input type='checkbox' value='"
									+ $(this).attr('id').split('_')[1]
									+ "'></td>");

				});

		$("#box_left").find('tr.head').prepend("<th></th>");
		$("#trigger_bnt").attr('href', 'javascript:saveAuth()');
		$("#trigger_bnt").find('span').text("保存");
		$("#box_left")
				.find('tr.content')
				.each(
						function() {
							//console.log(this);
							var td = $(this).children('td').eq(2);
							//console.log($(this).children('td').eq(1).text());
							var textValue = td.text();
							//console.log(textValue);
							td
									.empty()
									.prepend(
											"<input type='text' class ='full_input' name='url' value='"+textValue+"'/>");
							used.push($(this).attr('id').split('_')[1]);
						});
		$("#box_left").find(".full_input").each(function() {
			autoCompleteTips(this, "rest/service/list");

		});
		$(allAuth)
				.each(
						function() {
							is_used = true;
							if (used.length > 0) {
								for (i = 0; i < used.length; i++) {
									if (used[i] == this[1]) {
										is_used = false;
									}
								}
							}
							if ((is_used)) {
								$("#box_right")
										.children('table')
										.append(
												"<tr class='content' id='right_"+this[1]+"'><td><input type='checkbox'"+
												" value='"+this[1]+"'></td><td>"
														+ this[0]
														+ "</td><td>"
														+ this[2] + "</td>");
							}

						});
	}

	function moveRight() {
		move_right = new Array();
		//console.log("move right");
		$("#box_left").find("tr.content").each(function() {
			//查找所有选定的checkbox
			//	console.log(this);
			$(this).find("input[type='checkbox']:checked").each(function() {
				val_id = $(this).val();
				//		console.log("选中内容：" + val_id);
				move_right.push(val_id);
				delArrayVal(used, val_id);
				$("#left_" + val_id).remove();

			});
		});
		if (move_right.length == 0) {
			alert("没有选中的权限");
		} else {
			//console.log(move_right);
			$(move_right)
					.each(
							function() {
								$("#" + this).remove();
								for ( var key in allAuth) {
									if (this == allAuth[key][1]) {
										$("#box_right")
												.children('table')
												.append(
														"<tr class='content' id='right_"+this+"'><td><input type='checkbox' "+
														"value='"+allAuth[key][1]+"'></td><td>"
																+ allAuth[key][0]
																+ "</td><td>"
																+ allAuth[key][2]
																+ "</td>");
										break;
									}
								}

							});
		}

	}

	function moveLeft() {
		move_left = new Array();
		//console.log("move left");
		$("#box_right").find("tr.content").each(function() {
			//查找所有选定的checkbox
			//	console.log(this);
			$(this).find("input[type='checkbox']:checked").each(function() {
				val_id = $(this).val();
				//		console.log("选中内容：" + val_id);
				move_left.push(val_id);
				//delArrayVal(used, val_id);
				used.push(val_id);
				$("#right_" + val_id).remove();

			});
		});
		if (move_left.length == 0) {
			alert("没有选中的权限");
		} else {
			//	console.log(move_left);
			$(move_left)
					.each(
							function() {
								//$("#" + this).remove();
								for ( var key in allAuth) {
									if (this == allAuth[key][1]) {
										$("#box_left")
												.children('table')
												.append(
														"<tr class='content' id='left_"+this+"'><td><input type='checkbox'"+
														" value='"+allAuth[key][1]+"'></td><td>"
																+ allAuth[key][0]
																+ "</td><td> <input type='text' class='full_input' name='url'></td>");
										break;
									}
								}

							});
			$("#box_left").find(".full_input").each(function() {
				$(this).unbind();
			});
			$("#box_left").find(".full_input").each(function() {
				autoCompleteTips(this, "rest/service/list");

			});
		}

	}

	function saveAuth() {
		count = 0;
		var authIds = '';
		var uris = '';
		$("#box_left").find("input[type='checkbox']").each(function() {
			idValue = $(this).val();
			if (authIds != '') {
				authIds += ',';
			}
			authIds += idValue;
			count++;
		});

		$("#box_left").find("input[type='text']").each(function() {
			idValue = $(this).val();
			if (uris != '') {
				uris += ',';
			}
			uris += idValue;
		});
		$.ajaxSettings.async = false;
		$.ajax({
			url : "rest/resourceauth/save",
			data : {
				"resourceId" : resourceId,
				"authIds" : authIds,
				"uris" : uris
			},
			type : "post",
			success : function(result) {
				if (result.successful) {
					$("#box_middle").hide();
					$("#box_right").find("tr.content").remove();
					$("#box_right").hide();
					$("#box_left").find("tr.content").each(
							function() {
								//			console.log(this);
								input_obj = $(this).find(
										"input[type='text']:eq(0)");
								pathvalue = input_obj.val();
								check_obj = $(this).find(
										"input[type='checkbox']:eq(0)");

								$(input_obj).parent().append(pathvalue);
								$(input_obj).remove();
							});

					$("#box_left").find("input[type='checkbox']").parent()
							.remove();
					$("#box_left").find("tr.head").find("th:eq(0)").remove();
					$("#box_left").toggleClass("col-xs-6").toggleClass(
							"col-xs-12");
					$("#trigger_bnt").attr('href', 'javascript:modifyAuth()');

					$("#trigger_bnt").find('span').text("修改");
					$("#resource_info_auths_count").text(count);
				}

			}
		});
		$("#box_left").find(".full_input").each(function() {
			$(this).unbind();

		});
	}

	function updateResourceRole() {
		var roleIds = '';
		resultCheck = false;
		$("#selectRolesTable").find("input[type='checkbox']:checked").not(
				".selectAll").each(function() {
			if (roleIds != '') {
				roleIds += ',';
			}
			roleIds += $(this).val();
		});
		//console.log("role ids :" + roleIds);
		$.ajaxSettings.async = false;
		$
				.ajax({
					url : "rest/resourcerole/add",
					data : {
						"resourceId" : resourceId,
						"roleIds" : roleIds
					},
					type : "post",
					success : function(result) {
						if (result.successful == true) {
							roles = result.resultValue;
							if (roles == null || roles.length == 0) {

							} else {

								for (i = 0; i < roles.length; i++) {
									rolename = "<input type='hidden' name='roleId' value='"+roles[i].role.id+"'>"
											+ roles[i].role.roleName;
									resourceAuths = roles[i].resourceAuth;
									auths = '';
									if (resourceAuths != null) {
										for (j = 0; j < resourceAuths.length; j++) {
											display = "";
											if (resourceAuths[j].selected != true) {
												display = "style='display:none'";
											}
											auths += "<span id='"+resourceAuths[j].auth.id+"' "+ display +">"
													+ resourceAuths[j].auth.name
													+ "</span>";

										}
									}

									resourceRoleTable
											.fnAddData(
													[
															rolename,
															auths,
															"<a href=\"#\" class=\"op_mod\" onclick=\"op_mod(this)\" >"
																	+ "修改</a> <a href=\"#\" class=\"op_del\" onclick=\"op_delete(this)\"  >删除</a>" ],
													true);
									resourceRoleTable.fnDraw();
								}
								count = parseInt($("#resource_info_roles_count")
										.text());
								$("#resource_info_roles_count").text(
										count + roles.length);
							}

							resultCheck = true;
						}
					}
				});
		return resultCheck;
	}
</script>