<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<form class="form-horizontal" id="form_edit_menu">
			<fieldset>
				<input type="hidden" name="menu_id">
				<div class="form-group">
					<label class="col-sm-2 control-label">菜单标题<span
						class="required">*</span> </label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="menu_name" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">标题样式-类 </label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="title_class" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">标题前内容<span
						class="required">*</span> </label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="before_title" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">标题后方内容 </label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="after_title" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">菜单顺序<span
						class="required">*</span> </label>
					<div class="col-sm-8">
						<select name="menu_index" id="menu_index" class="populate"></select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否叶子<span
						class="required">*</span> </label>
					<div class="col-sm-8">
						<div class="checkbox-inline">
							<label> <i id="leaf_tag" class="fa fa-square-o"></i> </label>
						</div>

					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">菜单样式-类 </label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="menu_class" />
					</div>
				</div>


				<div class="form-group">
					<label class="col-sm-2 control-label">点击触发事件 </label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="menu_trigger" />
					</div>
				</div>
				<div class="form-group" id="resource_auth_div">
					<label class="col-sm-2 control-label">关联资源<span
						class="required">*</span> </label>
					<div class="col-sm-8">
						<select name="resource" id="resource" class="populate"
							onchange="changeResource()" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">链接样式-类<span
						class="required">*</span> </label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="link_class" />
					</div>
				</div>
				<div class="form-group" id="menu_url_div">
					<label class="col-sm-2 control-label">url地址<span
						class="required">*</span> </label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="menu_url"
							name="menu_url" />
					</div>
				</div>
			</fieldset>

		</form>
	</div>
</div>

<script type="text/javascript">
	// Run Select2 plugin on elements
	id = '${id}';

	/*
		将资源信息数据进行缓存，避免重复调用
	 */
	var jsonData = null;

	function getResources() {
		$.ajax({
			url : 'rest/resource/select',
			data : {
				"id" : id,
				"type" : "menu"
			},
			type : "post",
			async : false,
			success : function(json) {
				if (json.status == 200) {
					jsonData = json.resourceList;
				}
			}
		});
	}

	function loadResource(resource_id) {
		//console.log(jsonData);
		selected = '';
		if (resource_id == '' || resource_id == null) {
			selected = 'selected';
		}
		$("#edit_menu_info").find("#resource").append(
				"<option value='' "+selected+" >自定义</option");
		for (i = 0; i < jsonData.length; i++) {
			rs = jsonData[i];
			selected = '';
			if (rs.id == resource_id) {
				selected = 'selected';
			}
			//	console.log(rs);
			$("#edit_menu_info").find("#resource").append(
					"<option value='"+rs.id+"' "+selected+" >" + rs.name
							+ "</option");
		}
		DemoSelect2();
	}

	function changeResource() {
		resource_id = $("#edit_menu_info").find("#resource").val();
		if (resource_id == '') {
			setValueByName('edit_menu_info', 'menu_url', '#');
		} else {
			for (i = 0; i < jsonData.length; i++) {
				rs = jsonData[i];
				if (rs.id == resource_id) {
					setValueByName('edit_menu_info', 'menu_url', rs.uri);
				}
			}
		}
		DemoSelect2();

	}
	/*
	function changeAuth() {
		resource_id = $("#edit_menu_info").find("#resource").val();
		resource_auth_id = $("#edit_menu_info").find("#resource_auth").val();
		for (i = 0; i < jsonData.length; i++) {
			resource = jsonData[i];
			if (resource_id == resource.id) {
				for (j = 0; j < resource.resourceAuths.length; j++) {
					rs = resource.resourceAuths[j];
					if (rs.id == resource_auth_id) {
						setValueByName('edit_menu_info', 'menu_url', rs.subUri);
						break;
					}
				}
				break;
			}

		}
	}*/
	/*
	function loadResourceAuth(resource_id, resource_auth_id) {
		$("#edit_menu_info").find("#resource_auth").empty();
		if (resource_id != null) {
			for (i = 0; i < jsonData.length; i++) {
				r = jsonData[i];
				if (resource_id == r.id) {
					auths = r.resourceAuths;
					if (auths != null) {
						for (j = 0; j < auths.length; j++) {
							rs = auths[j];
							if (j == 0 && resource_auth_id == null) {
								setValueByName('edit_menu_info', 'menu_url',
										rs.subUri);
							}
							$("#edit_menu_info").find("#resource_auth").append(
									"<option value='"+rs.id+"'>" + rs.auth.name
											+ "</option");
							if (resource_auth_id == rs.id) {
								setValueByName('edit_menu_info', 'menu_url',
										rs.subUri);
							}
						}

					}

					if (resource_auth_id != null) {
						$("#edit_menu_info").find("#resource_auth").val(
								resource_auth_id);
					}
					break;
				}
			}
		}
	}
	 */
	function DemoSelect2() {
		$('#resource').select2();
		$('#menu_index').select2();
	}

	function validMenu() {
		$('#form_edit_menu').bootstrapValidator({
			message : "invalid",
			fields : {
				menu_name : {
					validators : {
						notEmpty : {
							message : '菜单标题必填'
						},
						remote : {
							type : 'POST',
							message : "菜单标题已存在,请重新填写",
							url : 'rest/menu/checkNameExists',
							data: function(validator) {
	                            return {
	                                id: validator.getFieldElements('menu_id').val()
	                            };
	                        },
						}
					}

				},
				before_title : {
					validators : {
						notEmpty : {
							message : '标题前内容必填'
						} 
					}
				},
				menu_index : {
					validators : {
						notEmpty : {
							message : '菜单顺序必选'
						}
					}
				},

				resource : {
					validators : {
						notEmpty : {
							message : '关联资源必选'
						}
					}
				},
				link_class : {
					validators : {
						notEmpty : {
							message : '链接样式-类必填'
						}
					}
				},
				menu_url : {
					validators : {
						notEmpty : {
							message : '连接地址必填'
						} 
					}
				} 

			}

		});
	}

	function loadMenu() {
		$
				.ajax({
					url : 'rest/menu/get',
					data : {
						'id' : id
					},
					type : 'POST',
					success : function(json) {
						/*
						     加载数据
						 */
						item = json.resultValue;
						setValueByName('edit_menu_info', 'menu_name',
								item.title);
						setValueByName('edit_menu_info', 'menu_style',
								item.style);
						setValueByName('edit_menu_info', 'menu_class',
								item.className);
						setValueByName('edit_menu_info', 'menu_trigger',
								item.trigger);
						setValueByName('edit_menu_info', 'menu_id', item.id);
						setValueByName('edit_menu_info', 'title_class',
								item.titleClass);
						setValueByName('edit_menu_info', 'before_title',
								item.beforeTitle);
						setValueByName('edit_menu_info', 'after_title',
								item.afterTitle);
						setValueByName('edit_menu_info', 'link_class',
								item.linkClass);
						/*
							标识 节点类型
						 */
						if (item.leaf) {
							$("#edit_menu_info").find("#leaf_tag").removeClass(
									"fa-square-o")
									.addClass("fa-check-square-o");
						} else {
							$("#edit_menu_info").find("#leaf_tag").removeClass(
									"fa-check-square-o")
									.addClass("fa-square-o");
						}

						/* 设置 index 索引*/
						menuIndex = $('#edit_menu_info').find("#menu_index");
						index = item.index;
						maxIndex = item.max_index;
						for (i = 0; i < maxIndex; i++) {
							$(menuIndex).append(
									"<option value='"+i+"'>" + (i + 1)
											+ "</option>");

						}
						$(menuIndex).val(index);
						LoadSelect2Script(DemoSelect2);
						/*
							如果是叶子节点需要设置资源信息
						 */
						if (item.leaf) {
							resource_id = item.resource == null ? null
									: item.resource.id;
							/*
							resource_auth_id = item.resourceAuth == null ? null
							: item.resourceAuth.id;*/
							getResources();
							loadResource(resource_id);
							//console.log(item);
							/*
							if (item.resourceAuth != null) {
								setSelectValByName('edit_menu_info',
										'resource_auth', item.resourceAuth.id);
								setSelectValByName('edit_menu_info',
										'resource',
										item.resourceAuth.resource.id);
							}*/
							setValueByName('edit_menu_info', 'menu_url',
									item.url);
							$("#edit_menu_info").find("#resource_auth_div")
									.show();
							$("#edit_menu_info").find("#menu_url_div").show();
						} else {
							//if (item.resourceAuth.resource != null) {
							//	setValueByName('edit_menu_info','resource', item.resourceAuth.resource.name);
							//}
							$("#edit_menu_info").find("#resource_auth_div")
									.hide();
							$("#edit_menu_info").find("#menu_url_div").hide();
						}
					}
				});

	}

	$(document).ready(function() {

		$('.form-control').tooltip();

		// Load example of form validation
		LoadBootstrapValidatorScript(validMenu);
		// Add drag-n-drop feature to boxes
		WinMove();
		loadMenu();
		autoCompleteTips($("#menu_url"), "rest/service/list");
		LoadSelect2Script(DemoSelect2);
		//loadJsTree(loadRoleMenus);
	});
</script>
