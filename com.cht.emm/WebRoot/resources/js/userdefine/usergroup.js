var groupTable = null;
// 加载 用户组
function search(o) {
	OSettings = groupTable.DataTable.models.oSettings;
	var oPreviousSearch = OSettings.oPreviousSearch;
	val = $(o).closest("div").find("input[type=text]").first().val();

	OSettings.oPreviousSearch = {
		"sSearch" : val,
		"bRegex" : false,
		"bSmart" : true,
		"bCaseInsensitive" : true
	};
	OSettings.sSearch = val;
	groupTable._fnFilterComplete(OSettings, {
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
	"sDom" : "rt<'box-content'<'col-sm-12'<'tool_box_pageInfo' ilp>> <'clearfix'>>",
	"sPaginationType" : "bootstrap",
	"oLanguage" : {
		"sSearch" : ""
	},
	"aoColumns" : [
			{
				"mData" : "id",
				"mRender" : function(data, type, full) {
					// return data;
					return "<input type=\"checkbox\" value=\"" + data + "\">";
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
				"bSortable" : false,
				"sClass" : "hidden-xs"
			},
			{
				"mData" : "parentGroup",
				"mRender" : function(data, type, full) {
					if (data != null) {
						return "<a href=\"javascript:getGroupInfo('" + data.id
								+ "')\">" + data.groupName + "</a>";
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
					// console.log(data);
					// console.log(data.length);
					if (data != null && data.length > 0) {
						subgroups = '';
						$(data).each(function() {
							if (subgroups != '') {
								subgroups += ",";
							}
							subgroups += this.groupName;
							if (subgroups.length > 25) {
								subgroups = subgroups.substring(20) + '....';
							}
						});
						return subgroups;
					} else {
						return "无";
					}
				},
				"bSearchable" : false,
				"bSortable" : false
			},
			// {
			// "mData" : "status",
			// "mRender" : function(data, type, row) {
			// return data == 1 ? "已启用" : "已禁用";
			// },
			// "bSearchable" : false,
			// "bSortable" : false,
			// "sClass":"hidden-xs"
			// },
			{
				"mData" : null,
				"mRender" : function(data, type, row) {
					return "<div class=\"tb-opt-box\" onclick=\"showOps(this)\"><img style=\"cursor: pointer;\" src=\"resources/img/icons/devrule.png\" >"
							+ "<div class=\"tb-opt-main\" ><img style=\"right:-22px;top:-24px;position: absolute;width:9px;height:8px;\" src=\"resources/img/icons/oper-arrow.png\">"
							+ "<a href=\"javascript:getGroupInfo('"
							+ row.id
							+ "')\"> <img src=\"resources/img/icons/Book-16x16.png\">查看组</a><a href=\"javascript:editGroupInfo('"
							+ row.id
							+ "')\"><img src=\"resources/img/icons/Edit-16x16.png\">编辑组</a> </div></div></td>";
				},
				"bSearchable" : false,
				"bSortable" : false
			} ]
};

function loadGroups() {
	$.ajaxSettings.async = true;
	groupTable = $("#datatable-4").dataTable(tableSetting);
	LoadSelect2Script(MakeSelect);
}

$(document).ready(function() {
	// LoadDataTablesScripts(loadGroups);

});

function getGroupInfo(id) {
	OpenBigDialog("组详情", 'console/group/info?groupId=' + id, 'user_group_info',
			true, true);
}

function editGroupInfo(id) {
	OpenBigDialogWithConfirm("创建组", 'console/group/edit?id=' + id,
			'edit_group', true, true, afterEditGroup);
}
function createGroup() {
	OpenBigDialogWithConfirm("创建组", 'console/group/add', 'create_group', true,
			true, aftercreateGroup);
}

function testReturn() {

	return true;
}
function aftercreateGroup() {

	$('#form_create_group').bootstrapValidator('validate');
	var result = $("#form_create_group").data("bootstrapValidator").isValid();
	checkResult = false;
	if (result) {
		parentId = $("#create_group").find("#parentId").val();
		groupName = $("#create_group").find("input[name='groupName']").first()
				.val();
		desc = $("input[name='desc']").first().val();
		parentId = $("#create_group").find("input[name='parentId']").first()
				.val();
		// console.log(parentId);
		$.ajaxSettings.async = false;
		$.ajax({
			url : "rest/group/add",
			data : {
				"groupName" : groupName,
				"desc" : desc,
				"parentId" : parentId
			},
			type : "post",
			success : function(result) {
				if (result.successful == true) {
					freshPage(groupTable, {});
					// console.log("return true");
					checkResult = true;
				}
			}
		});
	}

	return checkResult;

}
function afterEditGroup() {
	checkResult = false;
	$('#form_edit_group').bootstrapValidator('validate');
	var result = $("#form_edit_group").data("bootstrapValidator").isValid();
	if (result) {
		groupid = $("#edit_group").find("input[name='groupId']").first().val();
		groupName = $("#edit_group").find("input[name='groupName']").first()
				.val();
		desc = $("input[name='desc']").first().val();
		$.ajaxSettings.async = false;
		$.ajax({
			url : "rest/group/edit",
			data : {
				"id" : groupid,
				"groupName" : groupName,
				"desc" : desc
			},
			type : "get",
			success : function(data) {
				if (data.successful == true) {
					freshPage(groupTable, {});
					checkResult = true;
				}
			}
		});
	}
	return checkResult;

}

function deleteGroup(o) {
	var ids = getSelectedIds(o);
	if (ids.length == 0) {
		alert("请选择要删除的群组");
	} else {
		confirm("确定要删除？", function() {
			$.ajax({
				url : "rest/deleteEntity",
				data : {
					"type" : "group",
					"ids" : ids
				},
				type : "get",
				success : function(result) {
					if (result.successful == true) {
						deleteCheckedRows(groupTable);
					} else {
						alert("删除失败，请通知管理员");
					}
				}
			});
		});
	}
}

function saveGroup() {
	groupName = getValueByName('form_group_info', 'groupName');
	groupDesc = getValueByName('form_group_info', 'groupDesc');
	id = getValueByName('form_group_info', 'groupId');
	$("#form_group_info").bootstrapValidator('validate');
	var result = $("#form_group_info").data("bootstrapValidator").isValid();
	if (result) {
		$.ajax({
			url : "rest/group/edit",
			data : {
				"id" : id,
				"groupName" : groupName,
				"desc" : groupDesc
			},
			type : "get",
			success : function(data) {
				if (data.successful == true) {
					refreshTree("group_tree", id);
				}
			}
		});
	}

}
function loadGroup() {
	$("#group_tree")
			.jstree(
					{
						'plugins' : [ 'state', 'dnd', 'contextmenu' ],
						'core' : {
							'data' : {
								"url" : "rest/group/depart/nodes?groupId="
										+ groupId,
								"dataType" : "json",
								"type" : "POST"
							},
							'check_callback' : true,
							'themes' : {
								'responsive' : false
							}
						},
						'contextmenu' : {
							'items' : function(node) {
								var tmp = $.jstree.defaults.contextmenu.items();
								delete tmp.ccp;
								//debugger;
								console.log("thirdType:"+node.original.other);
								if (node.original.root == true||node.original.other=='third') {
									delete tmp.remove;
								}
								tmp.create.action = function(data) {
									var inst = $.jstree
											.reference(data.reference), obj = inst
											.get_node(data.reference);
									if (obj.icon == 'jstree-file') {
										obj.icon = 'jstree-folder';
									}
									inst.create_node(obj, {
										"text" : "新部门",
										"icon" : "jstree-file"
									}, "last", function(new_node) {
										setTimeout(function() {
											inst.edit(new_node);
										}, 0);
									});
								};
								/**
								 *如果是1，则表示是第三方应用的顶级组，该组不能手动创建组，必须由配置第三方平台处理，包括删除。
								 */
								if(node.original.thirdPartType==1){
									delete tmp.create;
								}
								return tmp;
							}
						}
					}).on('delete_node.jstree', function(e, data) {

				$.get('rest/deleteEntity', {
					'ids' : data.node.id,
					'type' : 'group'
				}).success(function(json) {
					if (json.successful) {
						$("#group_info").hide();
					}
					return json.successful;
				}).fail(function() {
					data.instance.refresh();
				});
			}).on('create_node.jstree', function(e, data) {
				$.ajax({
					url : 'rest/group/add',
					data : {
						'parentId' : data.node.parent,
						'groupName' : data.node.text

					},
					type : "POST",
					success : function(d) {
						recieve = d.resultValue;
						data.instance.set_id(data.node, recieve.id);
						data.instance.set_text(data.node, recieve.groupName);
					},
					error : function() {
						data.instance.refresh();
					}
				});
			}).on('rename_node.jstree', function(e, data) {
				// console.log("title: " + data.text);
				$.ajax({
					url : 'rest/group/rename',
					data : {
						'id' : data.node.id,
						'groupName' : data.text
					},
					type : "POST",
					success : function(d) {
						if (d.successful) {
							return true;
						} else {
							data.instance.refresh();
							alert("名称名称已存在，请更改");
							return false;
						}
					},
					error : function() {
						data.instance.refresh();
					}
				});
			}).on(
					'changed.jstree',
					function(e, data) {
						// console.log(data);
						id = data.selected.join(":");

						if (data && data.selected && data.selected.length) {
							$.post('rest/group/get?id='
									+ data.selected.join(':'),
									function(d) {
										// console.log(data.node.parent);
										// var menu_item = d.resultValue;
										// $("#menu_show").find("#menu_leaf").val(
										// menu_item.leaf);
										if (d.successful) {
											group = d.resultValue;
											if (group.isSystem == 1) {
												$("#group_btn").attr(
														"disabled", true);
											} else {
												$("#group_btn").attr(
														"disabled", false);
											}
											setValueByName('form_group_info',
													'groupName',
													group.groupName);
											setValueByName('form_group_info',
													'groupDesc',
													group.groupDesc);
											setValueByName('form_group_info',
													'groupId', group.id);
											$("#group_info").show();
											$("#form_group_info").bootstrapValidator('revalidateField','groupName');
										}

									});
						}
					});

}

$(document).ready(function() {
	loadJsTree(loadGroup);
});
