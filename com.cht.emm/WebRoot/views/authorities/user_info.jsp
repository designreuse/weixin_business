<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
li {
	list-style: none;
}
</style>
<div class="row-fluid">
		<div id="dashboard-header" class="row ">
		<div class="col-xs-10 col-sm-10">
			<h3>${ user.username}</h3>
		</div>
	</div>
	<div id="dashboard_links" class="col-xs-12 col-sm-2 pull-right">
		<ul class="nav nav-pills nav-stacked">
			<li class="active"><a href="#" class="link_dash" id="overview">一般信息</a>
			</li>
			<li><a href="#" class="link_dash" id="clients">用户组</a>
			</li>
			<li><a href="#" class="link_dash" id="graph">角色</a>
			</li>
			<!-- li><a href="#" class="link_dash" id="servers">设备</a>
			</li  -->
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
												<i class="fa fa-exclamation-circle"></i> 用户信息
											</h3>
											<h4>所在组</h4>
											<p>
												<span id="user_info_group_count"> <c:choose>
														<c:when test="${ fn:length(user.groups)>0 }">
															 ${ fn:length(user.groups) } 
														</c:when>
														<c:otherwise>0</c:otherwise>
													</c:choose> </span>个组
											</p>
											<p>
											<h4>最近上线时间</h4>

											<p>2014/7/2 9:00</p>
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
										<i class="fa fa-mobile"></i> 设备
									</h3>
									<p>
										<span id="user_info_group_count"><c:choose>
												<c:when test="${ fn:length(user.devices)>0 }">
												 ${ fn:length(user.devices) } 
											</c:when>
												<c:otherwise>0</c:otherwise>
											</c:choose> </span>个设备
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
										<i class="fa  fa-certificate"></i> 拥有角色
									</h3>
									<p>
										<c:choose>
											<c:when test="${ fn:length(user.roles)>0 }">
												<span id="user_info_role_count">${
													fn:length(user.roles) }</span>个角色
									
										</c:when>
											<c:otherwise>
												<span id="user_info_role_count">0</span>个角色</c:otherwise>
										</c:choose>
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
			<!-- 
			<div class="row one-list-message" <c:if test="${user.isSystem==1}">style="display:none"</c:if> >
				<a href="javascript:getUserGroup('${user.id}')"  > <i
					class="fa fa-plus-square"></i> <span class="hidden-xs">
						添加用户组</span> </a> <a href="javascript:delUserGroup();" > <i
					class="fa fa-minus-square"></i><span class="hidden-xs">
						删除用户组</span> </a>
			</div>
			 -->
			<div class="row col-sm-12 one-list-message" id="div_dash_group_list">
				<div id="userGroupsLs">
					<c:forEach var='group' items='${user.groups}'>
						<div class="checkbox-inline">
							<label> <input type="checkbox" value="${group.id}">
								${group.groupName}<i class="fa fa-square-o"></i> </label>
						</div>
					</c:forEach>



				</div>

			</div>
		</div>
		<!--End Dashboard Tab 2-->
		<!--Start Dashboard Tab 3-->
		<div id="dashboard-graph" class="row"
			style="display:none; position: relative;">
			<div class="row one-list-message" <c:if test="${user.isSystem==1}">style="display:none"</c:if>>
				<a href="javascript:getAllRole('user','${user.id}')"> <i
					class="fa fa-plus-square"></i><span class="hidden-xs"> 添加角色</span>
				</a> <a href="javascript:delUserRole();"> <i
					class="fa fa-minus-square"></i><span class="hidden-xs"> 删除角色</span>
				</a>
			</div>
			<div class="row col-sm-12 one-list-message" id="div_dash_role_list">
				<div id="userRolesLs">
					<c:forEach var='role' items='${user.roles}'>
						<div class="checkbox-inline">
							<label><input type="checkbox" value="${role.id}">
								${role.roleName}<i class="fa fa-square-o"></i> </label>
						</div>
					</c:forEach>

				</div>

			</div>
		</div>
		<!--End Dashboard Tab 3-->
		<!--Start Dashboard Tab 4-->
		<div id="dashboard-servers" class="row"
			style="display: none; position: relative;">
			<!-- 
			<div class="row one-list-message">
				<button type="button" class="btn btn-default"
					onclick="createDevice()">
					<i class="fa fa-plus-square"></i> 添加设备
				</button>
				<button type="button" class="btn btn-default">
					<i class="fa fa-minus-square"></i> 删除设备
				</button>
			</div> -->
			<div class="row one-list-message">
				<div class="col-xs-6">
					<b>设备ID</b>
				</div>
				<div class="col-xs-1">
					<b>类型</b>
				</div>
				<div class="col-xs-2">
					<b>设备型号</b>
				</div>
				<div class="col-xs-2">
					<b>操作系统</b>
				</div>
				<div class="col-xs-1">
					<b>状态</b>
				</div>
			</div>
			<c:forEach var="device" items="${user.devices }">
				<div class="row one-list-message">
					<div class="col-xs-6">
						<i class="fa fa-check"></i> ${device.id}
					</div>
					<div class="col-xs-1">${device.type_str}</div>
					<div class="col-xs-2">${device.name}</div>
					<div class="col-xs-2">${device.os_str}</div>
					<div class="col-xs-1">${device.status_str}</div>
				</div>
			</c:forEach>
		</div>
		<!--End Dashboard Tab 4-->
	</div>
	<div class="clearfix"></div>
</div>
<script type="text/javascript">
	var user_id = "${user.id}";
	$(document).ready(function() {

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
	});

	function createDevice() {
		OpenBigDialogWithConfirm('新建设备', 'console/create_device?userId='
				+ user_id, 'create_user_device', true, false, afterCreateDevice);
	}

	function afterCreateDevice() {
		var deviceId = null;
		var deviceName = null;
		var deviceType = null;
		var deviceOp = $("#create_user_device").find("select").first().val();
		$("#create_user_device").find("input[type='text']").each(function() {
			if ($(this).attr("name") == "deviceId") {
				deviceId = (this).val();
			}

			if ($(this).attr("name") == "deviceName") {
				deviceName = (this).val();
			}
			if ($(this).attr("name") == "deviceType") {
				deviceType = (this).val();
			}
		});

		$.ajaxSettings.async = false;
		$.ajax({
			url : "rest/userdevice/save",
			data : {
				"userId" : userId,
				"deviceType" : deviceType,
				"deviceName" : deviceName,
				"deviceId" : deviceId,
				"deviceOp" : deviceOp
			},
			type : "get",
			success : function(result) {
				if (result.successful == true) {
					return true;
				} else {
					return false;
				}
			}
		});

	}

	// 删除用户和组关联
	function delUserGroup() {
		// div_dash_group_list
		var checked = $("#div_dash_group_list").find(
				"input[type='checkbox']:checked").not(".selectAll");
		if (checked.length == 0) {
			alert("请选择至少一个用户组");
			return false;
		} else {
			confirm("确定要删除？",function() {
				var ids = '';
				$(checked).each(function() {
					if (ids != '') {
						ids += ',';
					}
					ids += $(this).val();
				});
				$.ajaxSettings.async = false;
				$.ajax({
					url : "rest/usergroup/remove",
					data : {
						"userId" : user_id,
						"groupIds" : ids
					},
					type : "post",
					success : function(result) {
						if (result.successful == true) {
							$(checked).each(function() {
								$(this).parent().parent().remove();

							});
							var count = parseInt($("#user_info_group_count")
									.text());
							$("#user_info_group_count").text(
									count - checked.length);
							return true;
						} else {
							return false;
						}
					}
				});
			});
		}
	}

	// removeUserRole 删除用户和组关联
	function delUserRole() {
		// div_dash_group_list
		var checked = $("#div_dash_role_list").find(
				"input[type='checkbox']:checked").not(".selectAll");
		if (checked.length == 0) {
			alert("请选择至少一个用户角色！");
			return false;
		} else {
			confirm("确定要删除？",function(){
				var ids = '';
				$(checked).each(function() {
					if (ids != '') {
						ids += ',';
					}
					ids += $(this).val();
				});
				$.ajaxSettings.async = false;
				$.ajax({
					url : "rest/userrole/remove",
					data : {
						"userId" : user_id,
						"roleIds" : ids
					},
					type : "post",
					success : function(result) {
						if (result.successful == true) {
							$(checked).each(function() {
								$(this).parent().parent().remove();

							});
							var rolecount = parseInt($("#user_info_role_count")
									.text());
							$("#user_info_role_count").text(
									rolecount - checked.length);
							return true;
						} else {
							return false;
						}
					}
				});
			});
		}
	}

	//选择用户关联的角色
	function getAllRole(type, id) {
		OpenBigDialogWithConfirm('选择角色', 'console/role/select?type=' + type
				+ '&id=' + id, 'all_role', true, false, updateRole);
	}

	function updateRole() {
		var checked = $("#selectRolesTable").find(
				"input[type='checkbox']:checked").not(".selectAll");
		if (checked.length == 0) {
			alert("请选择至少一个角色");
			return false;
		} else {
			var ids = '';
			$(checked).each(function() {
				if (ids != '') {
					ids += ',';
				}
				ids += $(this).val();
			});
		   return saveUserRole(user_id, ids);

		}
	}

	function saveUserRole(user_id, role_ids) {
		checkResult =false;
		$.ajaxSettings.async = false;
		$.ajax({
			url : "rest/userrole/add",
			data : {
				"userId" : user_id,
				"roleIds" : role_ids
			},
			type : "post",
			success : function(result) {
				if (result.successful == true) {
					roles = result.resultValue;
					if (roles == null || roles.length == 0) {

					} else {
						$("#userRolesLs").empty();
						for (i = 0; i < roles.length; i++) {
							$("#userRolesLs").append(
									"<div  class='checkbox-inline'><label><input type='checkbox'  value='"+roles[i].id+"'>"
											+ roles[i].roleName + "<i class=\"fa fa-square-o\"></i</div>");
						}
						var rolecount = parseInt($("#user_info_role_count")
								.text());
						$("#user_info_role_count").text(roles.length);
					}

					checkResult = true;
				}  
			}
		});
		return checkResult;
	}

	// 选择用户组关联
	function getUserGroup(id) {
		getAllGroup('user', id, updateGroup);
	}

	// 更新用户和组关联
	function updateGroup() {
		var checked = $("#selectGroupTable").find(
				"input[type='checkbox']:checked").not(".selectAll");
		if (checked.length == 0) {
			alert("请选择至少一个用户组");
			return false;
		} else {
			var ids = '';
			$(checked).each(function() {
				if (ids != '') {
					ids += ',';
				}
				ids += $(this).val();
			});
			return saveUserGroup(user_id, ids);

		}
	}

	// 保存用户和组关联
	function saveUserGroup(user_id, ids) {
		resultCkeck =false;
		$.ajaxSettings.async = false;
		$.ajax({
			url : "rest/usergroup/add",
			data : {
				"userId" : user_id,
				"groupIds" : ids
			},
			type : "post",
			success : function(result) {
				if (result.successful == true) {
					groups = result.resultValue;
					if (groups == null || groups.length == 0) {

					} else {
						$("#userGroupsLs").empty();
						for (i = 0; i < groups.length; i++) {
							$("#userGroupsLs").append(
									"<div class='checkbox-inline'><label><input type='checkbox'  value='"+groups[i].id+"'>"
											+ groups[i].groupName + "<i class=\"fa fa-square-o\"></i></label></div>");
						}

						$("#user_info_group_count").text(groups.length);
					}

					resultCkeck = true;
				}  
			}
		});
		return resultCkeck;
	}
</script>