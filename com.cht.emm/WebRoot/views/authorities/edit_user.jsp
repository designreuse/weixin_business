<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div class="col-xs-9 col-sm-9">
		<form class="form-horizontal" id="form_edit_user">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-3 control-label">用户名称<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="hidden" name="userId" value="${user.id }"> <input
							type="text" class="form-control" name="username"
							value="${user.username }" readonly />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">密码<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="password" class="form-control" name="password"
							value="${user.password }" />
						<input type="hidden"   id="oldPassword"
							value="${user.password }" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">确认密码<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="password" class="form-control" name="repassword"
							value="${user.password }" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label">姓名<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="userAlias"
							value="${user.userAlias}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">手机/电话<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="mobile"
							value="${user.mobile}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">email<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="email"
							value="${user.email}" />
					</div>
				</div>
				<div class="form-group" id="userTypeDiv">
					<label class="col-sm-3 control-label">用户类型<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<select name="userType" id="userType" class="populate placeholder"
							onchange="changeRole2(this)">
							<option value="3">部门管理员</option>
							<option value="4">普通员工</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">性别<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<select name="sex" id="s2_with_sex" class="populate placeholder">
							<option value="1" <c:if test="${user.gander==1}">selected</c:if>>男</option>
							<option value="0" <c:if test="${user.gander==0}">selected</c:if>>女</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">用户组<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<!-- 
						<select id="s2_with_tag" class="populate placeholder"
							name="groups">
							<c:forEach var="group" items="${groups }">
								<option value="${group.id }"
									<c:forEach var="gid" items="${user.groupIds }"> <c:if test="${ gid eq group.id }">selected</c:if>  </c:forEach>>${group.groupName
									}</option>
							</c:forEach>
						</select>
						 -->
						<div id="group_tree_3"></div>
						<input type="hidden" name="groups"
							<c:choose>
								<c:when test="${user.groupIds != null }">value="${user.groupIds[0]}"</c:when>
								<c:otherwise>value=""</c:otherwise>
							</c:choose>>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">角色（多选）<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<select id="s2_country" multiple="multiple"
							class="populate placeholder" name="roles"
							onchange="reloadMenus()">
							<c:forEach var="role" items="${roles }">
								<option value="${role.id }"
									<c:forEach var="rid" items="${user.roleIds }"> <c:if test="${ rid eq role.id }">selected</c:if></c:forEach>>${role.roleName
									}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</fieldset>
			<div class="form-group"></div>
		</form>
	</div>
	<div class="col-xs-3 col-sm-3">
		<div id="user_menu_tree"></div>
	</div>
</div>

<script type="text/javascript">
	// Run Select2 plugin on elements
	topGroupId = '${topGroupId}';
	groupId = '${groupId}';
	userType = ${user.userType};
	orgGroupId = '${orgGroupId}';
	function DemoSelect2() {
		$('#s2_with_tag').select2();
		$('#s2_country').select2({
			placeholder : "-- 选择角色 --"
		});
		$('#userType').select2();
		$('#s2_with_sex').select2();
	}
	// Run timepicker
	function DemoTimePicker() {
		$('#input_time').timepicker({
			setDate : new Date()
		});
	}
    
    function loadUserType(){
    	if(userType > 2){
    		$("#userTypeDiv").show();
    		$("#userType").val(userType);
    		$('#userType').select2();
    	}else{
    		$("#userTypeDiv").hide();
    	}
    }
    
    function loadUserRole2(userType){
		$.ajax({
			url : "rest/role/userType",
			data : {
				"userType" : userType
			},
			type : "post",
			success : function(data) {
				if (data.successful == true) {
					 roles = data.resultValue;
					 $("#s2_country").empty();
					 for(i=0;i<roles.length;i++){
					 	$("#s2_country").append("<option value='"+roles[i].id+"'>"
					 		+roles[i].roleName+"</option>");
					 }
					 $('#s2_country').select2();
				}
			}
		});
	}
	
	function changeRole2(o){
		//debugger;
		userType = $(o).val();
		loadUserRole2(userType);
	}
    
    
	function validUser() {
		$('#form_edit_user').bootstrapValidator({
			message : "invalid",
			fields : {
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
							message : '用户组必选'
						}
					}
				},
				groups : {
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
				},
			}
		});
	}

	function loadRoleMenus() {
		var roles = '';
		$("#edit_user_info").find("select[name='roles']").find(
				"option:selected").each(function() {
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

	function loadGroup() {
		$("#group_tree_3").jstree({
			'core' : {
				'data' : {
					"url" : "rest/group/depart/nodes?groupId=" + topGroupId,
					"dataType" : "json",
					"type" : "POST"// needed only if you do not supply JSON headers
				},
				"multiple" : false,
				'check_callback' : true,
				'themes' : {
					'responsive' : false
				}
			},
			'selected' : [ '${groupId}' ],
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
			   //debugger;
			   if(orgGroupId ==id){
			   		//如果userType为1,2，只能选择topGroupId
			   		if(userType ==1 || userType==2){
			   			//$("#group_tree_3").jstree("select_node",topGroupId);
			   			//groupId = topGroupId;
			   		}else{
			   			//如果不是，则只能选择上次选择的那个，不能选择
			   			$("#group_tree_3").jstree("deselect_node",id);
			   			$("#group_tree_3").jstree("select_node",groupId);
			   		}
			   }else if(id!="" && id!=groupId){
			   		if(userType ==1 || userType==2){
			   			$("#group_tree_3").jstree("deselect_node",id);
			   			$("#group_tree_3").jstree("select_node",orgGroupId);
			   			//$("#group_tree_3").jstree("select_node",topGroupId);
			   			//groupId = topGroupId;
			   		}else{
			   			//如果不是，则只能选择上次选择的那个，不能选择
			   			$("#group_tree_3").jstree("deselect_node",groupId);
			   			$("#group_tree_3").jstree("select_node",id);
			   			groupId = id;
			   		}
			   	 	
			   		
			   }
			  

		});

	}

	function reloadMenus() {
		loadJsTree(loadRoleMenus);
	}
	$(document).ready(function() {

		$('.form-control').tooltip();

		LoadSelect2Script(DemoSelect2);
		// Load example of form validation
		LoadBootstrapValidatorScript(DemoFormValidator);
		// Add drag-n-drop feature to boxes

		LoadBootstrapValidatorScript(validUser);
		loadJsTree(loadRoleMenus);
		loadJsTree(loadGroup);
		//debugger;
		loadUserType();
		WinMove();
	});
</script>
