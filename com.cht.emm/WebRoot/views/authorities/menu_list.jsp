<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
<!--
-->
a.normal {
	
}
</style>

<script type="text/javascript">
	$(document).ready(function() {
		loadJsTree(loadMenuTree);
		$("#menu_show").find("input").attr("readOnly", "readOnly");
	});

	function loadMenuTree() {
		$("#menu_tree")
				.jstree(
						{
							'core' : {
								'data' : {
									'url' : 'rest/menu/list',
									'data' : function(node) {
										return {
											'parentId' : node.id
										};
									},
									'type' : 'POST'
								},
								'check_callback' : true,
								'themes' : {
									'responsive' : false
								}
							},
							'contextmenu' : {
								'items' : function(node) {

									var tmp = $.jstree.defaults.contextmenu
											.items();
									console.log(node);
									if (node.original.assystem) {
										delete tmp.rename;
										delete tmp.remove;
									}
									console.log(node.state);
									if (node.state.disabled) {
										delete tmp.rename;
										delete tmp.remove;
									}
									delete tmp.create.action;
									tmp.create.label = "新建";
									if (node.original.root == true) {
										tmp.create.submenu = {
											"create_folder" : {
												"separator_after" : true,
												"label" : "子目录",
												"action" : function(data) {
													var inst = $.jstree
															.reference(data.reference), obj = inst
															.get_node(data.reference);
													inst
															.create_node(
																	obj,
																	{
																		type : "node",
																		icon : 'jstree-folder'
																	},
																	"last",
																	function(
																			new_node) {
																		setTimeout(
																				function() {
																					inst
																							.edit(new_node);
																				},
																				0);
																	});
												}
											},
											"create_item" : {
												"label" : "子项",
												"action" : function(data) {
													var inst = $.jstree
															.reference(data.reference), obj = inst
															.get_node(data.reference);
													inst
															.create_node(
																	obj,
																	{
																		type : "leaf",
																		icon : 'jstree-file'
																	},
																	"last",
																	function(
																			new_node) {
																		setTimeout(
																				function() {
																					inst
																							.edit(new_node);
																				},
																				0);
																	});
												}
											}
										};
									} else {
										if (node.original.type === "node") {
											tmp.create.submenu = {
												"create_item" : {
													"label" : "子项",
													"action" : function(data) {
														var inst = $.jstree
																.reference(data.reference), obj = inst
																.get_node(data.reference);
														inst
																.create_node(
																		obj,
																		{
																			type : "leaf",
																			icon : 'jstree-file'
																		},
																		"last",
																		function(
																				new_node) {
																			setTimeout(
																					function() {
																						inst
																								.edit(new_node);
																					},
																					0);
																		});
													}
												}
											};

										}
									}

									if (node.original.type === "leaf") {
										delete tmp.create;
									}
									delete tmp.ccp;
									return tmp;
								}
							},
							"dnd" : {
								is_draggable : function(data) {
									if (data[0].original.assystem) {
										return false;
									} else {
										return true;
									}

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
							'plugins' : [ 'state', 'dnd', 'contextmenu', 'crrm' ]
						}).on('delete_node.jstree', function(e, data) {
					confirm("确定要删除？", function() {
						$.post('rest/menu/del', {
							'id' : data.node.id
						}).fail(function() {
							data.instance.refresh();
						});
					});
				}).on('create_node.jstree', function(e, data) {
					$.ajax({
						url : 'rest/menu/add',
						data : {
							'parentId' : data.node.parent,
							'title' : data.node.text,
							'type' : data.node.original.type
						},
						type : "POST",
						success : function(d) {
							data.instance.set_id(data.node, d.id);
						},
						error : function() {
							data.instance.refresh();
						}
					});
				}).on('rename_node.jstree', function(e, data) {
					//console.log("title: " + data.text);
					$.ajax({
						url : 'rest/menu/rename',
						data : {
							'id' : data.node.id,
							'title' : data.text
						},
						type : "POST",
						error : function() {
							data.instance.refresh();
						}
					});
				}).on('move_node.jstree', function(e, data) {

					$.post('rest/menu/move', {
						'id' : data.node.id,
						'parentId' : data.parent
					}).fail(function() {
						data.instance.refresh();
					});
				}).on(
						'changed.jstree',
						function(e, data) {
							console.log(data);
							id = data.selected.join(":");
							if (id == "#" || id == -1) {
								$("#menu_show").find("#menu_btn").attr(
										"disabled", "disabled");
							} else {
								$("#menu_show").find("#menu_btn").removeAttr(
										"disabled");
							}
							if (data && data.selected && data.selected.length) {
								$.post('rest/menu/get?id='
										+ data.selected.join(':'), function(d) {
									//console.log(data.node.parent);
									var menu_item = d.resultValue;
									$("#menu_show").find("#menu_leaf").val(
											menu_item.leaf);
									setValueByName('menu_show', 'parent_id',
											data.node.parent);
									setValueByName('menu_show', 'menu_name',
											menu_item.title);
									setValueByName('menu_show', 'title_class',
											menu_item.titleClass);
									setValueByName('menu_show', 'before_title',
											menu_item.beforeTitle);
									setValueByName('menu_show', 'after_title',
											menu_item.afterTitle);
									setValueByName('menu_show', 'link_class',
											menu_item.linkClass);
									setValueByName('menu_show', 'menu_class',
											menu_item.className);
									setValueByName('menu_show', 'menu_style',
											menu_item.style);
									setValueByName('menu_show', 'menu_trigger',
											menu_item.trigger);
									setValueByName('menu_show', 'menu_url',
											menu_item.url);
									setValueByName('menu_show', 'menu_index',
											(menu_item.index + 1));
									if(menu_item.isSystem==1){
										$("#menu_show").find("#menu_btn").attr("disabled", "disabled");
									}else{
										$("#menu_show").find("#menu_btn").removeAttr("disabled");
									}
									if (menu_item.leaf) {
										$("#menu_show").find("#leaf_tag")
												.removeClass("fa-square-o")
												.addClass("fa-check-square-o");
									} else {
										$("#menu_show").find("#leaf_tag")
												.removeClass(
														"fa-check-square-o")
												.addClass("fa-square-o");
									}

									setValueByName('menu_show', 'menu_id',
											menu_item.id);
									if (menu_item.leaf) {
										if (menu_item.resource != null) {
											setValueByName('menu_show',
													'resource_auth',
													menu_item.resource.name);
										} else {
											setValueByName('menu_show',
													'resource_auth', "自定义");
										}

										$("#menu_show").find(
												"#menu_resource_auth_div")
												.show();
										$("#menu_show").find("#menu_url_div")
												.show();
									} else {
										$("#menu_show").find(
												"#menu_resource_auth_div")
												.hide();
										$("#menu_show").find("#menu_url_div")
												.hide();
									}
								});
							} else {
								$('#data .content').hide();
							}
						});
	}

	function editMenu() {
		id = getValueByName('menu_show', 'menu_id');
		if (id == null || id == "") {
		} else {
			OpenBigDialogWithConfirm("编辑菜单项", "console/menu/edit?id=" + id,
					'edit_menu_info', true, true, saveMenuItem);
		}

	}

	function saveMenuItem() {
		result = false;
		$("#form_edit_menu").bootstrapValidator('validate');
		var result = $("#form_edit_menu").data("bootstrapValidator")
				.isValid();
		debugger;
		if(result){
			result =false;
			id = getValueByName('edit_menu_info', 'menu_id');
			title = getValueByName('edit_menu_info', 'menu_title');
			index = getSelectValByName('edit_menu_info', 'menu_index');
			//leaf = $('#edit_menu_info')
			//	.find("input[name='menu_leaf']").first().attr("checked") == true;
			titleClass = getValueByName('edit_menu_info', 'title_class');
			beforeTitle = getValueByName('edit_menu_info', 'before_title');
			afterTitle = getValueByName('edit_menu_info', 'after_title');
			trigger = getValueByName('edit_menu_info', 'menu_trigger');
			menuClass = getValueByName('edit_menu_info', 'menu_class');
			isLeaf = $("#menu_show").find("#menu_leaf").val();
			parentId = getValueByName("menu_show", "parent_id");
			//console.log("parentId:" + parentId);
			url = null;
			link_class = getValueByName('edit_menu_info', 'link_class');
			resource_id = null;
			//resource_auth_id = null;
			if (isLeaf) {
				//console.log("isLeaf: " + isLeaf);
				url = getValueByName('edit_menu_info', 'menu_url');
				resource_id = getSelectValByName('edit_menu_info', 'resource');
				//console.log("resource_id: " + resource_id);
				//resource_auth_id = getSelectValByName('edit_menu_info',
				//		'resource_auth');
				//console.log("resource_auth_id: " + resource_auth_id);
			}
			$.ajax({
				url : "rest/menu/update",
				async : false,
				data : {
					"id" : id,
					"title" : title,
					"isLeaf" : isLeaf,
					"index" : index,
					"titleClass" : titleClass,
					"beforeTitle" : beforeTitle,
					"afterTitle" : afterTitle,
					"trigger" : trigger,
					"url" : url,
					"className" : menuClass,
					"resourceId" : resource_id,
					"parentId" : parentId,
					"linkClass" : link_class
				},
				type : "POST",
				success : function(d) {
					if (d.successful) {
						alert("保存成功");
						ref = $('#menu_tree').jstree(true);
						parent = $('#edit_menu_info').find('#parent_id').val();
						ref.refresh("#" + parent);
						result = true;
					} else {
						alert("系统内部错误，请稍后再试");
					}
				}
			});
		
		}
		
		return result;
	}
</script>
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">权限管理</a></li>
			<li><a href="#">菜单管理</a></li>
		</ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="box no-drop">
			<div class="box-header">
				<div class="box-name subTitle">
					<span>权限管理/菜单管理 </span>
				</div>
				<div class="box-icons"></div>
				<div class="no-move"></div>
			</div>
			<div class="box-content" style="margin-bottom:0.5em;min-height:600px">
				<div class="rows">
					<div class="col-xs-12 col-sm-12">
						<div id="menu_tree" class="col-xs-4"
							style="border: 1px solid #d6d6d6;border-radius: 3px;min-height:565px"></div>
						<div id="menu_show" class="col-xs-8"
							style="border: 1px solid #d6d6d6;border-radius: 3px;min-height:565px">
							<form class="form-horizontal" action="">
								<fieldset>
									<input type="hidden" name="menu_id"> <input
										type="hidden" name="parent_id">
									<div class="form-group">
										<label class="col-sm-3 control-label">标题<span
											class="required">*</span> </label>
										<div class="col-sm-7">
											<input type="text" class="form-control" name="menu_name" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">标题样式-类</label>
										<div class="col-sm-7">
											<input type="text" class="form-control" name="title_class" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">标题前内容<span
											class="required">*</span> </label>
										<div class="col-sm-7">
											<input type="text" class="form-control" name="before_title" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label">标题后方内容</label>
										<div class="col-sm-7">
											<input type="text" class="form-control" name=after_title " />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">菜单顺序<span
											class="required">*</span> </label>
										<div class="col-sm-7">
											<input class="form-control" type="text" name="menu_index">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">叶子节点?<span
											class="required">*</span> </label>
										<div class="col-sm-7">

											<div class="checkbox-inline">
												<input type="hidden" id="menu_leaf"> <label>
													<i id="leaf_tag" class="fa fa-square-o"></i> </label>
											</div>

										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">菜单样式-类 </label>
										<div class="col-sm-7">
											<input type="text" class="form-control" name="menu_class" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label">点击事件 </label>
										<div class="col-sm-7">
											<input type="text" class="form-control" name="menu_trigger" />
										</div>
									</div>
									<div class="form-group" id="menu_resource_auth_div">
										<label class="col-sm-3 control-label">资源访问权限<span
											class="required">*</span> </label>
										<div class="col-sm-7">
											<input type="text" class="form-control" name="resource_auth">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">链接样式-类<span
											class="required">*</span> </label>
										<div class="col-sm-7">
											<input type="text" class="form-control" name="link_class" />
										</div>
									</div>
									<div class="form-group" id="menu_url_div">
										<label class="col-sm-3 control-label">url地址<span
											class="required">*</span> </label>
										<div class="col-sm-7">
											<input type="text" class="form-control" name="menu_url" />
										</div>
									</div>
									<div class="form-group">
										<input type="button" class="btn btn-default"
											style="margin-left:50%" id="menu_btn" value="修改"
											onclick="editMenu()">
									</div>

								</fieldset>

							</form>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
</div>

