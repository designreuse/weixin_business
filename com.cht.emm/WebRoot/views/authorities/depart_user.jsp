<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">权限管理</a></li>
			<li><a href="#">部门管理</a></li>
		</ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="box no-drop">
			<div class="box-header">
				<div class="box-name subTitle">
					<span>权限管理/部门管理</span>
				</div>
				<div class="box-icons"></div>
				<div class="no-move"></div>
			</div>
			<div class="box-content no-padding" style="min-height:600px">
				<div class="col-xs-3"
					style="border: 1px solid #d6d6d6;border-radius: 3px;min-height:565px;min-width:180px;">
					<div id="dep_tree"></div>
				</div>
				
				<div class="col-xs-9"
					style="border: 1px solid #d6d6d6;border-radius: 3px;min-height:565px">
					<div class="row">
					<div class="col-xs-10">
						<ul class="operate">
							<li class="tool_box_left"><a href="javascript:createUser();"
								title="新增用户"> <i class="fa fa-plus-square"></i><span
									class="hidden-xs">新增用户</span> </a></li>
							<li class="tool_box_left"><a href="#"
								onClick="deleteUser(this)" title="删除用户" class="deleteIcon">
									<i class="fa fa-minus-square"></i><span class="hidden-xs">删除用户</span>
							</a></li>
						</ul>
					</div>
					</div>
					<div id="depart_user_list" class="row">
						 <table
					class="table table-bordered table-striped table-hover table-heading table-datatable"
					id="group_user_list_table">
					<thead>
						<tr>
							<th><input type="checkbox" class="selectAll"
								onclick="selectAll(this)">
							</th>
							<th>账号</th>
							<th>姓名</th>
							<th>账号类型</th>
							<th class="hidden-xs">手机号</th>
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
<script type="text/javascript">
 var pageType=2;
</script>
<script type="text/javascript" src="resources/js/userdefine/user.js"/>
<script type="text/javascript">
	var groupId = '${groupId}';
	var departUserListTable = null;
	/*
	function search(o) {
		OSettings = group_user_list_table.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		userTable._fnFilterComplete(OSettings, {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		});

	}
	tableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "rest/group/depart/users/pages",
		"aaSorting" : [ [ 0, "asc" ] ],
		"aLengthMenu" : [ [ 10, 20, 50 ], [ 10, 20, 50 ] ],
		"sDom" : "rt<'box-content'<'col-sm-12'<'tool_box_pageInfo' ilp>> <'clearfix'>>",
		"sPaginationType" : "bootstrap",
		"oLanguage" : {
			"sSearch" : "",
			"sLengthMenu" : ",每页显示_MENU_条"
		}
		
	};

	function loadGroupUser() {
		group_user_list_table = $("#group_user_list_table").dataTable(tableSetting);
		LoadSelect2Script(MakeSelect);
	}
*/
	function loadDep() {
		$("#dep_tree").jstree({
			'core' : {
				'data' : {
					"url" : "rest/group/depart/nodes?groupId=" + groupId,
					"dataType" : "json",
					"type" : "POST"// needed only if you do not supply JSON headers
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
		}).on('changed.jstree',
				function(e, data) {
					id = data.selected.join(":");
					if (id == "#" || id == "-1") {
						$("#dep_info").find("#menu_btn").attr(
								"disabled", "disabled");
					} else {
						$("#dep_info").find("#menu_btn").removeAttr(
								"disabled");
					}
					if (data && data.selected && data.selected.length) {
						groupId = data.selected.join(':');
						departUserListTable.fnDraw();
					} 
				});
	}

	$(document).ready(function() {
		loadJsTree(loadDep);
	});
</script>

