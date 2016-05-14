<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal" var="authentication" />
<div class="row">
	<div class="col-xs-9 col-sm-9">
		<form class="form-horizontal" id="form_create_user">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-3 control-label">用户名称<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="username" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">密码<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="password" class="form-control" name="password" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label">确认密码<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="password" class="form-control" name="repassword" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">姓名<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="userAlias" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">手机号<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="mobile" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">email<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="email" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">用户类型<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<select name="userType" id="userType" class="populate placeholder"
							onchange="changeRole(this)">
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">性别<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<select name="sex" id="cu_sex" class="populate placeholder">
							<option value="1" selected>男</option>
							<option value="0">女</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">用户组<span
						class="required">*</span> </label>
					<div class="col-sm-9">


						<!-- <select  class="populate placeholder"
							id="cu_groups" name="groups">
							<c:forEach var="group" items="${groups }">
								<option value="${group.id }">${group.groupName }</option>
							</c:forEach>
						</select>
						 -->
						<div>
							<div id="group_tree_2"></div>
						</div>
						<input type="hidden" name="groups" value="${groupId}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">角色（多选）<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<select multiple="multiple" class="populate placeholder"
							name="roles" id="cu_roles" onchange="reloadMenus()">
							<!--<c:forEach var="role" items="${roles }">
								<option value="${role.id }">${role.roleName }</option>
							</c:forEach>
							-->

						</select>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
	<div class="col-xs-3 col-sm-3">
		<div id="user_menu_tree"></div>
	</div>
</div>

<script type="text/javascript">
	var addUserForm = null;
	var groupId = '${topGroupId}';
	var isTopGroup = ${isTopGroup};

	// Run Select2 plugin on elements
	function DemoSelect2() {
		$('#cu_roles').select2({
			placeholder : "-- 选择角色 --"
		});
		$('#cu_sex').select2();
		$('#userType').select2();
	}
	// Run timepicker
	function DemoTimePicker() {
		$('#input_time').timepicker({
			setDate : new Date()
		});
	}
	function validUser() {
		addUserForm = $('#form_create_user').bootstrapValidator({
			message : "invalid",
			fields : {
				username : {
					validators : {
						notEmpty : {
							message : "用户名必填"
						},
						stringLength : {
							min : 4,
							max : 16,
							message : "用户名长度在4-16个字符之间"
						},
						remote : {
							type : 'POST',
							message : "用户已存在,请重新填写",
							url : 'rest/user/checkUserNameExists'
						}
					}
				},
				password : {
					validators : {
						notEmpty : {
							message : '密码必填'
						},
						stringLength : {
							min : 6,
							message : "密码长度不小于6个字符"
						},
						identical : {
							field : 'repassword',
							message : '两次密码必须相同'
						}
					}
				},
				repassword : {
					validators : {
						notEmpty : {
							message : '确认密码必填'
						},
						identical : {
							field : 'password',
							message : '两次密码必须相同'
						}
					}
				},
				userAlias : {
					validators : {
						notEmpty : {
							message : '姓名必填'
						},
						stringLength : {
							min : 2,
							max : 16,
							message : "姓名长度需在2-16个字符之间"
						}
					}
				},
				email : {
					validators : {
						notEmpty : {
							message : '邮箱必填'
						},
						emailAddress : {
							message : "请填写正确的email地址"
						}
					}
				},
				userType : {
					validators : {
						notEmpty : {
							message : '用户类型必选'
						}
					}
				},
				sex : {
					validators : {
						notEmpty : {
							message : '性别必选'
						}
					}
				},
				roles : {
					validators : {
						notEmpty : {
							message : '用户角色必选'
						}
					}
				},
				mobile : {
					validators : {
						notEmpty : {
							message : '手机号必选'
						},
						mobile : {
							message : "请填写11位格式正确的手机号码"
						}
					}
				}
			}
		});
	}

	function getUserType(selectId, isTop) {

		usertype = ${ authentication.userType };
		array = new Array("系统管理员", "机构管理员", "部门管理员", "普通员工");
		from = 0;
		to = 4;
		if (usertype != 4) {
			from = usertype - 1;
			to = 4;
			// 如果是顶级组即机构，只能选择机构管理员这一种类型
			if (isTop) {
				to = from + 1;
			} else {
				from = usertype;
			}
			$("#" + selectId).empty();
			for (i = from; i < to; i++) {
				tag = '';
				if (i == from + 1) {
					tag = " selected ";
				}

				$("#" + selectId).append(
						"<option " + tag + "value=\"" + (i + 1) + "\">"
								+ array[i] + "</option>");
			}
		}
		changeRole($("#userType"));
	}

	function loadRoleMenus() {
		var roles = '';
		$("#create_user").find("select[name='roles']").find("option:selected")
				.each(function() {
					if (roles != '') {
						roles += ',';
					}
					roles += $(this).val();
				});

		if (roles != '') {
			$("#user_menu_tree").jstree("destroy");
			$("#user_menu_tree").jstree({
				'core' : {
					'data' : {
						"url" : "rest/menu/nodes/roleIds?roleIds=" + roles,
						"dataType" : "json",
						"type" : "POST"// needed only if you do not supply JSON headers
					}
				}
			});
		} else {
			$("#user_menu_tree").jstree("destroy");
		}
	}

	function reloadMenus() {
		loadJsTree(loadRoleMenus);
	}

	function loadGroup() {
		$("#group_tree_2").jstree({
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
			selected : [ '${groupId}' ],
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
			} else {
			//debugger;
				setValueByName('create_user', 'groups', id);
				//修改用户类型 当选择的是机构时
				isTop = false;
				if (isTopGroup == 1 && id == groupId) {
					isTop = true;
				}
				getUserType("userType", isTop);
				$('#userType').select2();
			}
		});

	}
	function loadUserRole(userType){
		$.ajax({
			url : "rest/role/userType",
			data : {
				"userType" : userType
			},
			type : "post",
			success : function(data) {
				if (data.successful == true) {
					 roles = data.resultValue;
					 $("#cu_roles").empty();
					 for(i=0;i<roles.length;i++){
					 	$("#cu_roles").append("<option value='"+roles[i].id+"'>"
					 		+roles[i].roleName+"</option>");
					 }
					 $('#cu_roles').select2();
				}
			}
		});
	}
	
	function changeRole(o){
		userType = $(o).val();
		loadUserRole(userType);
	}
	$(document).ready(function() {

		$('.form-control').tooltip();
		getUserType("userType", isTopGroup == 1);
		LoadSelect2Script(DemoSelect2);
		// Load example of form validation
		WinMove();
		
		LoadBootstrapValidatorScript(validUser);
		loadJsTree(loadRoleMenus);
		loadJsTree(loadGroup);
	});
</script>
