<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal" var="authentication" />
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">权限管理</a>
			</li>
			<li><a href="#">用户管理</a>
			</li>
		</ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="box no-drop">
			<div class="box-header">
				<div class="box-name subTitle">
					<span>权限管理/用户管理</span>
				</div>
				<div class="box-icons"></div>
				<div class="no-move"></div>
			</div>
			<div class="box-content no-padding table-responsive">
				<div class="row">
					<div class="col-xs-3"
						style="border: 1px solid #d6d6d6;border-radius: 3px;min-height:565px;min-width:180px;overfloat:hidden">
						<div id="group_tree"></div>
					</div>
					<div class="col-xs-9"
						style="border: 1px solid #d6d6d6;border-radius: 3px;min-height:565px;padding-top: 5px;">
						<div class="row">
							<div class="col-xs-10">
								<ul class="operate">
									<li class="tool_box_left"><a
										href="javascript:createNewUser();" title="新增用户"> <i
											class="fa fa-plus-square"></i><span class="hidden-xs">新增用户</span>
									</a>
									</li>
									<li class="tool_box_left"><a href="#"
										onClick="deleteUser(this)" title="删除用户" class="deleteIcon">
											<i class="fa fa-minus-square"></i><span class="hidden-xs">删除用户</span>
									</a>
									</li>
								</ul>
							</div>
							<div class="col-xs-2 input-group searchInput">
								<input type="text" class="form-control" placeholder="搜索">
								<span class="input-group-addon" onClick="search(this)"><i
									class="fa fa-search"></i> </span>
							</div>


						</div>

						<div class="row">
							<table
								class="table table-bordered table-striped table-hover table-heading table-datatable"
								id="user_list_table">
								<thead>
									<tr>
										<th><input type="checkbox" class="selectAll"
											onclick="selectAll(this)"></th>
										<th>账号</th>
										<th>姓名</th>
										<th>账号类型</th>
										<th class="hidden-xs">手机号</th>
										<th class="hidden-xs">设备数</th>
										<!-- th class="hidden-xs">状态</th -->
										<th>操作</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>

						</div>

					</div>

				</div>

			</div>

		</div>
	</div>
</div>
<script type="text/javascript">
	var pageType = 1;
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/userdefine/user.js" />
<script type="text/javascript">
	var userListTable = null;
	var gpGroupId = '${groupId}';

	function createNewUser() {
		createUser(gpGroupId);
	}

	function loadDep() {
		$("#group_tree").jstree({
			'core' : {
				'data' : {
					"url" : "rest/group/depart/nodes?groupId=" + gpGroupId,
					"dataType" : "json",
					"type" : "POST"
				},
				'check_callback' : true,
				'themes' : {
					'responsive' : false
				}
			},
			'types' : {
				'node' : {
					'icon' : 'folder'
				},
				'leaf' : {
					'icon' : 'file'
				}
			},
			'sort' : function(a, b) {
				return 0;
			},
			'plugins' : [ 'state', 'dnd' ]
		}).on('changed.jstree', function(e, data) {
			id = data.selected.join(":");
			if (id == "#" || id == "-1") {
				$("#dep_info").find("#menu_btn").attr("disabled", "disabled");
			} else {
				$("#dep_info").find("#menu_btn").removeAttr("disabled");
			}
			if (data && data.selected && data.selected.length) {
				gpGroupId = data.selected.join(':');
				console.log(targetTable);
				userListTable.fnDraw();
			}
		});
	}

	$(document).ready(function() {
		loadJsTree(loadDep);
		
	});
</script>


