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
	<div id="dashboard_links" class="col-xs-12 col-sm-2 pull-right">
		<ul class="nav nav-pills nav-stacked">
			<li class="active"><a href="#" class="link_dash" id="overview">一般信息</a>
			</li>
			<li><a href="#" class="link_dash" id="clients">子组</a></li>
			<li><a href="#" class="link_dash" id="graph">组成员</a></li>
		</ul>
	</div>
	<div id="dashboard_tabs" class="col-xs-12 col-sm-10">
		<!--Start Dashboard Tab 1-->
		<div id="dashboard-overview" class="row">
			<div class="row">
				<div class="col-xs-6">
					<div class="row">
						<div class="col-xs-12">
							<div class="row">
								<div class="col-xs-12">
									<div class="box no-drop">
										<div class="box-content">
											<h3 class="page-header">
												<i class="fa fa-exclamation-circle"></i> ${group.groupName}
											</h3>
											<h4>父组</h4>
											<p>
												<c:choose>
													<c:when test="${group.parentGroup!=null }">${group.parentGroup.groupName }</c:when>
													<c:otherwise>无</c:otherwise>
												</c:choose>
											</p>
											<h4>状态</h4>
											<p>启用</p>
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
										<i class="fa fa-mobile"></i> 子组
									</h3>
									<p>
										<c:choose>
											<c:when test="${group.subGroups!=null }">
												<span id="group_info_subgroup_count">${fn:length(group.subGroups)
													}</span>个子组</c:when>
											<c:otherwise>
												<span id="group_info_subgroup_count">0</span>个子组</c:otherwise>
										</c:choose>
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
										<i class="fa  fa-certificate"></i> 组内用户
									</h3>
									<p>
										<span id="group_info_user_count"><c:choose>
												<c:when test="${group.users!=null }">${fn:length(group.users) }</c:when>
												<c:otherwise>0</c:otherwise>
											</c:choose> </span>个用户
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
		<div id="dashboard-clients" class="row" style="display:none">
			<div class="row one-list-message">
				<a href="javascript:getSubGroup('${group.id}')"> <i
					class="fa fa-plus-square"></i><span class="hidden-xs"> 添加子组</span>
				</a> <a href="javascript:delSubGroup()"> <i
					class="fa fa-minus-square"></i><span class="hidden-xs"> 删除子组</span>
				</a>
			</div>
			<div class="row one-list-message">
				<ul class="object_list" id="subGroupsLS">
					<c:choose>
						<c:when test="${group.subGroups!=null }">
							<c:forEach var="sub" items="${group.subGroups}">
								<li><input type="checkbox" value="${sub.id }">${sub.groupName
									}</li>
							</c:forEach>
						</c:when>
					</c:choose>
				</ul>

			</div>
		</div>
		<!--End Dashboard Tab 2-->
		<!--Start Dashboard Tab 3-->
		<div id="dashboard-graph" class="row" style="display:none">
			<div class="row one-list-message">
				<a href="javascript:selectGroupUsers('${group.id}')"> <i
					class="fa fa-plus-square"></i> <span class="hidden-xs"> 添加用户</span>
				</a> <a href="javascript:deleteGroupUsers('${group.id}')"> <i
					class="fa fa-minus-square"></i><span class="hidden-xs"> 删除用户</span>
				</a>
			</div>
			<div class="row one-list-message">
				<ul class="object_list" id="groupUserLs">
					<c:choose>
						<c:when test="${group.users!=null }">
							<c:forEach var="user" items="${group.users}">
								<li><input type="checkbox" value="${user.id }">${user.username
									}</li>
							</c:forEach>
						</c:when>
					</c:choose>
				</ul>

			</div>
		</div>

		<!--End Dashboard Tab 4-->
	</div>
	<div class="clearfix"></div>
</div>
<script type="text/javascript">
	var groupId = "${group.id}";

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

	function selectGroupUsers(id) {
		selectUsers('group', id, afterSelectGroupUsers);
	}

	function afterSelectGroupUsers() {
		var userIds = '';
		var checked = $("#select_user").find("input[type='checkbox']:checked")
				.not(".selectAll");
		var result2 = false;
		//console.log(checked);
		if (checked.length == 0) {
			alert("请选择至少一个用户");
		} else {
			$(checked).each(function() {
				//console.log($(this));
				if (userIds != '') {
					userIds += ',';
				}
				userIds += $(this).val();
			});

			$.ajaxSettings.async = false;
			$.ajax({
				url : "rest/groupuser/add",
				data : {
					"userIds" : userIds,
					"groupId" : groupId
				},
				type : "post",
				success : function(result) {
					if (result.successful == true) {
						users = result.resultValue;
						if (users == null || users.length == 0) {

						} else {
							$("#groupUserLs").empty();
							for (i = 0; i < users.length; i++) {
								$("#groupUserLs").append(
										"<li><input type='checkbox'  value='"+users[i].id+"'>"
												+ users[i].username + "</li>");
							}
							$("#group_info_user_count").text(users.length);
						}
						result2 = true;
					}
				}
			});
		}
		return result2;
	}

	// deleteGroupUsers 删除用户和组关联
	function deleteGroupUsers() {

		var checked = $("#groupUserLs").find("input[type='checkbox']:checked")
				.not(".selectAll");

		if (checked.length == 0) {
			alert("请选择至少一个组用户！");
			return false;
		} else {
			confirm("确定要删除？", function() {
				var ids = '';
				$(checked).each(function() {
					if (ids != '') {
						ids += ',';
					}
					ids += $(this).val();
				});
				$.ajaxSettings.async = false;
				$.ajax({
					url : "rest/groupuser/remove",
					data : {
						"groupId" : groupId,
						"userIds" : ids
					},
					type : "post",
					success : function(result) {
						if (result.successful == true) {
							$(checked).each(function() {
								$(this).parent().remove();

							});
							var groupcount = parseInt($(
									"#group_info_user_count").text());
							$("#group_info_user_count").text(
									groupcount - checked.length);
							return true;
						} else {
							return false;
						}
					}
				});
			});
		}
	}
</script>