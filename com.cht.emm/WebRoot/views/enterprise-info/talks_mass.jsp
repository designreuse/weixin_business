<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal" var="authentication" />
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">企业信息</a></li>
			<li><a href="#">群发消息</a></li>
		</ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<form class="form-horizontal" id="form_create_msg">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-2 control-label">接收用户（多选）<span
						class="required">*</span></label>
					<div class="col-sm-8">
						<select id="users" multiple="multiple"
							class="populate placeholder" name="users">
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">消息内容<span
						class="required">*</span></label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="msg" id="msg" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-9 col-sm-2">
						<a href="javascript:sendMsg()">
							<i class="fa fa-envelope-o"></i> 发送
						</a>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</div>
<script type="text/javascript">
	function sendMsg() {
		var successful = false;
		$('#form_create_msg').bootstrapValidator('validate');
		var result = $("#form_create_msg").data("bootstrapValidator").isValid();
		if (result) {
			var users = $("#users")[0].selectedOptions;
			var userList = [];
			for ( var i = 0; i < users.length; i++) {
				userList.push(users[i].value);
			}
			var msg = $("#msg").val();
			var user = "${authentication.username}";
			var json = "{\"fromUser\":\"" + user + "\",\"toUser\":\""
					+ userList.join(",") + "\",\"msg\":\"" + msg + "\"}";
			$.ajax({
				url : "rest/talks/send",
				type : "POST",
				contentType : 'application/json',
				processData : false,
				dataType : 'json',
				async : false,
				data : json,
				success : function(p_resultValue) {
					if (!p_resultValue.successful) {
						alert(p_resultValue.resultMessage);
						return;
					}
				},
				error : function(p_request, p_status, p_err) {
				}
			});
		}
	}
	function validMsg() {
		$('#form_create_msg').bootstrapValidator({
			message : "invalid",
			fields : {
				users : {
					validators : {
						notEmpty : {
							message : "接收用户必选"
						}
					}

				},
				msg : {
					validators : {
						notEmpty : {
							message : "消息内容必填"
						},
						stringLength : {
							max : 64,
							message : "消息内容不超过64个字符"
						}
					}

				}

			}

		});
	}
	function DemoSelect2() {
		$('#users').select2({
			placeholder : "-- 选择接收用户 --"
		});
	}
	$(document)
			.ready(
					function() {
						$('#users')[0].innerHTML = "";
						$
								.getJSON(
										"rest/device/users",
										function(result) {
											userList = result.resultValue;
											$
													.each(
															userList,
															function(index,
																	user) {
																$('#users')[0].innerHTML += "<option value='"+user.id+"'>"
																		+ user.username
																		+ "</option>";
															});
										});
						$('.form-control').tooltip();
						LoadSelect2Script(DemoSelect2);
						LoadBootstrapValidatorScript(validMsg);
					});
</script>
