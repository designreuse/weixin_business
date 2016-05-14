<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link
	href="${pageContext.request.contextPath}/jstree-master/dist/themes/default/style.css"
	rel="stylesheet">

<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">权限管理</a>
			</li>
			<li><a href="#">部门管理</a>
			</li>
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
					style="border: 1px solid #d6d6d6;border-radius: 3px;min-height:565px">
					<div id="dep_tree"></div>
				</div>
				<div class="col-xs-9"
					style="border: 1px solid #d6d6d6;border-radius: 3px;min-height:565px">
					<div id="dep_info">
						<fieldset>
							<input type="hidden" name="groupId">
							<div class="form-group">
								<label class="col-sm-3 control-label">部门名称<span
									class="required">*</span> </label>
								<div class="col-sm-7">
									<input type="text" class="form-control" name="groupName" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">描述</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" name="groupDesc" />
								</div>
							</div>

							<div class="form-group">
								<input type="button" class="btn btn-default"
									style="margin-left:50%" id="menu_btn" value="修改"
									onclick="saveGroup()">
							</div>
					</div>
				</div>

			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var groupId = '${groupId}';
	function saveGroup() {
		groupName = getValueByName('dep_info', 'groupName');
		groupDesc = getValueByName('dep_info', 'groupDesc');
		id = getValueByName('dep_info', 'groupId');
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
					refreshTree("dep_tree", id);
				}
			}
		});

	}
	function loadDep() {
		$("#dep_tree")
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
									var tmp = $.jstree.defaults.contextmenu
											.items();
									delete tmp.ccp;
									if (node.original.root == true) {
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
									return tmp;
								}
							}

						})

				.on('delete_node.jstree', function(e, data) {
					confirm("确定要删除？", function() {
						$.get('rest/deleteEntity', {
							'ids' : data.node.id,
							'type' : 'group'
						}).success(function(json) {
							if (json.successful) {
								$("#dep_info").hide();
							}
							return json.successful;
						}).fail(function() {
							data.instance.refresh();
						});
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
							data.instance.set_id(data.node, d.resultValue);
						},
						error : function() {
							data.instance.refresh();
						}
					});
				}).on('rename_node.jstree', function(e, data) {
					//console.log("title: " + data.text);
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
							}
						},
						error : function() {
							data.instance.refresh();
						}
					});
				}).on(
						'changed.jstree',
						function(e, data) {
							//console.log(data);
							id = data.selected.join(":");

							if (data && data.selected && data.selected.length) {
								$.post('rest/group/get?id='
										+ data.selected.join(':'), function(d) {
									//	console.log(data.node.parent);
									//	var menu_item = d.resultValue;
									//	$("#menu_show").find("#menu_leaf").val(
									//			menu_item.leaf);
									if (d.successful) {
										group = d.resultValue;
										setValueByName('dep_info', 'groupName',
												group.groupName);
										setValueByName('dep_info', 'groupDesc',
												group.groupDesc);
										setValueByName('dep_info', 'groupId',
												group.id);
										$("#dep_info").show();
									}

								});
							}
						});

	}

	$(document).ready(function() {
		loadJsTree(loadDep);
	});
</script>