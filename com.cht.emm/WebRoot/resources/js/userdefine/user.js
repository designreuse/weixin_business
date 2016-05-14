var targetUrl = null;
var targetTable = null;
// if(pageType==1){
// targetUrl = "rest/user/all/pages";
// }else{
targetUrl = "rest/group/depart/users/pages";
// }
function search(o) {
	OSettings = targetTable.DataTable.models.oSettings;
	var oPreviousSearch = OSettings.oPreviousSearch;
	val = $(o).closest("div").find("input[type=text]").first().val();

	OSettings.oPreviousSearch = {
		"sSearch" : val,
		"bRegex" : false,
		"bSmart" : true,
		"bCaseInsensitive" : true
	};
	OSettings.sSearch = val;
	targetTable._fnFilterComplete(OSettings, {
		"sSearch" : val,
		"bRegex" : false,
		"bSmart" : true,
		"bCaseInsensitive" : true
	});

}
tableSetting = {
	"bprocessing" : true,
	"bServerSide" : true,
	"sAjaxSource" : targetUrl,
	"aaSorting" : [ [ 0, "asc" ] ],
	"aLengthMenu" : [ [ 10, 20, 50 ], [ 10, 20, 50 ] ],
	"sDom" : "rt<'box-content'<'col-sm-12'<'tool_box_pageInfo' ilp>> <'clearfix'>>",
	"sPaginationType" : "bootstrap",
	"oLanguage" : {
		"sSearch" : ""
	}

};
if (pageType == 1) {
	tableSetting.aoColumns = [
			{
				"mData" : "id",
				"mRender" : function(data, type, full) {
					// return data;
					var disabled = false;
					if (full.isSystem == 1) {
						disabled = true;
					}
					return "<input type=\"checkbox\" value=\"" + data + "\" "
							+ (disabled ? 'disabled' : '') + ">";
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
			},
			{
				"mData" : "userAlias"
			},
			{
				"mData" : "userType",
				"mRender" : function(data, type, row) {
					title = "其他";
					switch (data) {
					case 1:
						title = "系统管理员";
						break;
					case 2:
						title = "机构管理员";
						break;
					case 3:
						title = "部门管理员";
						break;
					case 4:
						title = "普通员工";
						break;
					default:
						break;
					}

					return title;
				},
				"bSearchable" : false,
				"bSortable" : false,
				"sClass" : "hidden-xs"
			},
			{
				"mData" : "mobile",
				"sClass" : "hidden-xs"
			},
			{
				"mData" : "deviceNum",
				"bSearchable" : false,
				"bSortable" : false,
				"sClass" : "hidden-xs"
			},
			/*
			 * { "mData" : "status", "mRender" : function(data, type, row) {
			 * return data == 1 ? "已启用" : "已禁用"; }, "bSearchable" : false,
			 * "bSortable" : false, "sClass" : "hidden-xs" },
			 */
			{
				"mData" : null,
				"mRender" : function(data, type, row) {
					console.log("is System: " + row.isSystem);
					var click = 'notAllowed()';
					if (row.isSystem != 1) {
						click = "editUserInfo('" + row.id + "')";
					}
					return "<a href=\"#\" class=\"link_status op\" onclick=\""
							+ click + "\">编辑</a>";
				},
				"bSearchable" : false,
				"bSortable" : false
			}

	];
} else {
	tableSetting.aoColumns = [
			{
				"mData" : "id",
				"mRender" : function(data, type, full) {
					// return data;
					return "<input type=\"checkbox\" value=\"" + data + "\">";
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
			},
			{
				"mData" : "userAlias"
			},
			{
				"mData" : "userType",
				"mRender" : function(data, type, row) {
					title = "其他";
					switch (data) {
					case 1:
						title = "系统管理员";
						break;
					case 2:
						title = "机构管理员";
						break;
					case 3:
						title = "部门管理员";
						break;
					case 4:
						title = "普通员工";
						break;
					default:
						break;
					}

					return title;
				},
				"bSearchable" : false,
				"bSortable" : false,
				"sClass" : "hidden-xs"
			},
			{
				"mData" : "mobile",
				"sClass" : "hidden-xs"
			},
			{
				"mData" : null,
				"mRender" : function(data, type, row) {
					return "<a href=\"#\" class=\"link_status op\" onclick=\"editUserInfo('"
							+ row.id + "')\">编辑</a>";
				},
				"bSearchable" : false,
				"bSortable" : false
			}

	];

}
tableSetting.fnServerParams = function(aoData) {
	aoData.push({
		"name" : "groupId",
		"value" : gpGroupId
	});
}
function loadUsers() {
	//console.log("pageType: " + pageType);
	if (pageType == 1) {
		userListTable = targetTable = $("#user_list_table").dataTable(
				tableSetting);
	} else {
		departUserListTable = targetTable = $("#group_user_list_table")
				.dataTable(tableSetting);
	}
//	console.log(targetTable);
	LoadSelect2Script(MakeSelect);
}
function getUserInfo(userId) {
	OpenBigDialog("用户详情", "console/user/info?id=" + userId, 'user_info', true,
			true);
}
function createUser(pid) {

	OpenBigDialogWithConfirm("创建用户", 'console/user/add?groupId=' + pid,
			'create_user', true, true, afterCreateUser);
}
// Run Datables plugin and create 3 variants of settings

$(document).ready(function() {
	// 初始化数据
	// $('#content').mask("加载中...");
	LoadDataTablesScripts(loadUsers);
});

function editUserInfo(userId) {
	OpenBigDialogWithConfirm("用户详情", "console/user/edit?id=" + userId,
			'edit_user_info', true, true, afterEditUser);
}

function afterCreateUser() {
	checkResult = false;
	addUserForm.bootstrapValidator('validate');
	var result = $("#form_create_user").data("bootstrapValidator").isValid();
	console.log("valid :" + result);
	if (result) {
		var username_obj = $("#create_user").find("input[name='username']");
		// console.log(username_obj);
		username = $("#create_user").find("input[name='username']").first()
				.val();

		password = $("#create_user").find("input[name='password']").first()
				.val();
		groups = '';
		roles = '';
		alias = $("#create_user").find("input[name='userAlias']").first().val();
		email = $("#create_user").find("input[name='email']").first().val();
		mobile = $("#create_user").find("input[name='mobile']").first().val();
		sex = $("#create_user").find("select[name='sex']").find(
				"option:selected").first().val();
		userType = $("#create_user").find("select[name='userType']").find(
				"option:selected").first().val();

		/*
		 * $("#create_user").find("select[name='groups']").find(
		 * "option:selected").each(function() { if (groups != '') { groups +=
		 * ','; } groups += $(this).val();
		 * 
		 * });
		 */
		groups = $("#create_user").find("input[name='groups']").first().val();
		$("#create_user").find("select[name='roles']").find("option:selected")
				.each(function() {
					if (roles != '') {
						roles += ',';
					}
					roles += $(this).val();
				});

		$.ajaxSettings.async = false;
		$.ajax({
			url : "rest/user/add",
			data : {
				"username" : username,
				"password" : $.md5(password),
				"groups" : groups,
				"roles" : roles,
				"userAlias" : alias,
				"mobile" : mobile,
				"email" : email,
				"sex" : sex,
				"userType" : userType

			},
			type : "post",
			success : function(data) {
				if (data.successful == true) {
					freshPage(targetTable, {});
					checkResult = true;
				}
			}
		});
	}
	return checkResult;
}

function afterEditUser() {
	checkResult = false;
	$("#form_edit_user").bootstrapValidator('validate');
	var result = $("#form_edit_user").data("bootstrapValidator").isValid();
	// console.log(result);
	if (result) {
		userId = $("#edit_user_info").find("input[name='userId']").first()
				.val();
		//debugger;
		username = $("#edit_user_info").find("input[name='username']").first()
				.val();
		userType2 = $("#create_user").find("select[name='userType']").find(
		"option:selected").first().val();
		if(userType2){
			userType= userType2;
		}
		password = $("#edit_user_info").find("input[name='password']").first()
				.val();
		oldPass = $("#oldPassword").val();
		if (oldPass != password) {
			password = $.md5(password);
		}
		groups = $("#edit_user_info").find("input[name='groups']").first()
				.val();
		roles = '';
		alias = $("#edit_user_info").find("input[name='userAlias']").first()
				.val();
		email = $("#edit_user_info").find("input[name='email']").first().val();
		mobile = $("#edit_user_info").find("input[name='mobile']").first()
				.val();
		sex = $("#edit_user_info").find("select[name='sex']").find(
				"option:selected").first().val();

		$("#edit_user_info").find("select[name='roles']").find(
				"option:selected").each(function() {
			if (roles != '') {
				roles += ',';
			}
			roles += $(this).val();
		});

		$.ajaxSettings.async = false;
		$.ajax({
			url : "rest/user/edit",
			data : {
				"id" : userId,
				"username" : username,
				"password" : password,
				"groups" : groups,
				"roles" : roles,
				"userAlias" : alias,
				"mobile" : mobile,
				"email" : email,
				"sex" : sex,
				"userType":userType
			},
			type : "post",
			success : function(result) {
				if (result.successful == true) {
					freshPage(targetTable, {});
					checkResult = true;
				}
			}
		});
	}
	return checkResult;
}

function deleteUser(o) {

	var ids = getSelectedIds(o);
	if (ids.length == 0) {
		alert("请选择要删除的用户");
	} else {
		confirm("确定要删除？", function() {
			$.ajax({
				url : "rest/deleteEntity",
				data : {
					"type" : "user",
					"ids" : ids
				},
				type : "get",
				success : function(result) {
					if (result.successful == true) {
						deleteCheckedRows(targetTable);
					} else {
						alert("删除失败，请通知管理员");
					}
				}
			});
		});

	}
}