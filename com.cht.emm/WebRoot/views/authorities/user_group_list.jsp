<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">权限管理</a>
			</li>
			<li><a href="#">群组管理</a>
			</li>
		</ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="box no-drop">
			<div class="box-header">
				<div class="box-name subTitle">
					  <span>权限管理/组织架构管理</span>
				</div>
				<div class="box-icons"></div>
				<div class="no-move"></div>
			</div>
			 
			<div class="box-content no-padding table-responsive" style="min-height:600px">
				<div class="col-xs-3"
					style="border: 1px solid #d6d6d6;border-radius: 3px;min-height:565px">
					<div id="group_tree"></div>
				</div>
				<div class="col-xs-9"
					style="border: 1px solid #d6d6d6;border-radius: 3px;min-height:565px">
					<form class="form-horizontal" id="form_group_info">
						<fieldset>
							<input type="hidden" name="groupId">
							<div class="form-group">
								<label class="col-sm-3 control-label">群组名称<span
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
									style="margin-left:50%" id="group_btn" value="修改"
									onclick="saveGroup()">
							</div>
					</fieldset></form>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
   var groupId = '${groupId}';
   function validGroup() {
   		console.log("id:"+getValueByName('form_group_info', 'groupId'));
		$("#form_group_info").bootstrapValidator({
			message : "invalid",
			fields : {
				groupName : {
					validators : {
						notEmpty : {
							message : "群组名称必填"
						},
						remote : {
							type : 'POST',
							message : "群组名称已存在,请重新填写",
							url : 'rest/group/checkGroupNameExists',
							data: function(validator) {
	                            return {
	                                id: validator.getFieldElements('groupId').val()
	                            };
	                        },
						}
					}
				}
			}
		});

	}
   $(document).ready(function() {
	   LoadBootstrapValidatorScript(validGroup);
});
</script>
<script type="text/javascript"
	src="resources/js/userdefine/usergroup.js"></script>