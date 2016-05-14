<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="row">
	<div class="col-xs-12 col-sm-12">
		<form class="form-horizontal" id="form_create_strategy">
			<div class="form-group">
				<label class="col-sm-2 control-label">策略名称<span
					class="required">*</span></label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="name" name="name" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">描述</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="desc" name="desc" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">越狱</label>
				<div class="col-sm-8">
					<select id="breakout" class="populate placeholder">
						<option value="-1">-- 无限制 --</option>
						<option value="1">越狱</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">应用</label>
				<div class="col-sm-8">
					<select id="appList" class="populate placeholder">
						<option value="-1">-- 无限制 --</option>
						<option value="1">不符合应用名单要求</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">密码</label>
				<div class="col-sm-8">
					<select class="populate placeholder" id="password">
						<option value="-1">-- 无限制 --</option>
						<option value="1">未设置密码</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">操作系统版本</label>
				<div class="col-sm-8">
					<select class="populate placeholder" id="osVersion">
						<option value="-1">-- 无限制 --</option>
						<option value="1">ANDROID 4.0</option>
						<option value="2">IOS 6.0</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">数据安全</label>
				<div class="col-sm-8">
					<select class="populate placeholder" id="encrypt">
						<option value="-1">-- 无限制 --</option>
						<option value="1">未启用数据加密</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">若匹配</label>
				<div class="col-sm-8">
					<select class="populate placeholder" id="condition">
						<option value="0" selected="selected">全部条件</option>
						<option value="1">任意条件</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">则执行</label>
				<div class="col-sm-8">
					<select class="populate placeholder" id="operation">
						<option value="0" selected="selected">企业擦除</option>
						<option value="1">推送通知</option>
					</select>
				</div>
			</div>
			<div class="form-group" id="users_group">
				<label class="col-sm-2 control-label">分配（多选）</label>
				<div class="col-sm-8">
					<select id="users" multiple="multiple"
						class="populate placeholder">
					</select>
				</div>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript">
	function validStrategy() {
		$('#form_create_strategy').bootstrapValidator({
			message : "invalid",
			fields : {
				name : {
					validators : {
						notEmpty : {
							message : "策略名称必填"
						},
						stringLength : {
							max : 16,
							message : "策略名称长度不超过16个字符"
						}
					}

				},
				desc : {
					validators : {
						stringLength : {
							max : 64,
							message : "描述长度不超过64个字符"
						}
					}

				}

			}

		});
	}
	function DemoSelect2() {
		$('#breakout').select2();
		$('#appList').select2();
		$('#password').select2();
		$('#osVersion').select2();
		$('#encrypt').select2();
		$('#condition').select2();
		$('#operation').select2();
		$('#users').select2({
			placeholder : "-- 选择关联用户 --"
		});
	}
	$(document)
			.ready(
					function() {
						$.ajaxSettings.async = false;
						devices = [];
						if (id) {
							if(id=="0"){
								document.getElementById('users_group').hidden = true;
								document.getElementById('name').readOnly = true;
								document.getElementById('desc').readOnly = true;
							}else{
								document.getElementById('users_group').hidden = false;
								document.getElementById('name').readOnly = false;
								document.getElementById('desc').readOnly = false;
							}
							$
									.getJSON(
											"rest/device/strategy/" + id,
											function(result) {
												strategy = result.resultValue;
												$('#name')[0].value = strategy.name;
												$('#desc')[0].value = strategy.desc;
												var breakoutOptions = document
														.getElementById('breakout').children;
												for ( var i = 0; i < breakoutOptions.length; i++) {
													if (breakoutOptions[i].value == strategy.breakout) {
														breakoutOptions[i].selected = true;
														break;
													}
												}
												var appListOptions = document
														.getElementById('appList').children;
												for ( var i = 0; i < appListOptions.length; i++) {
													if (appListOptions[i].value == strategy.appList) {
														appListOptions[i].selected = true;
														break;
													}
												}
												var passwordOptions = document
														.getElementById('password').children;
												for ( var i = 0; i < passwordOptions.length; i++) {
													if (passwordOptions[i].value == strategy.password) {
														passwordOptions[i].selected = true;
														break;
													}
												}
												var osVersionOptions = document
														.getElementById('osVersion').children;
												for ( var i = 0; i < osVersionOptions.length; i++) {
													if (osVersionOptions[i].value == strategy.osVersion) {
														osVersionOptions[i].selected = true;
														break;
													}
												}
												var encryptOptions = document
														.getElementById('encrypt').children;
												for ( var i = 0; i < encryptOptions.length; i++) {
													if (encryptOptions[i].value == strategy.encrypt) {
														encryptOptions[i].selected = true;
														break;
													}
												}
												var conditionOptions = document
														.getElementById('condition').children;
												for ( var i = 0; i < conditionOptions.length; i++) {
													if (conditionOptions[i].value == strategy.condition) {
														conditionOptions[i].selected = true;
														break;
													}
												}
												var operationOptions = document
														.getElementById('operation').children;
												for ( var i = 0; i < operationOptions.length; i++) {
													if (operationOptions[i].value == strategy.operation) {
														operationOptions[i].selected = true;
														break;
													}
												}

												devices = strategy.users
														.split(",");

											});
						}
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
																var exist = false;
																for ( var i = 0; i < devices.length; i++) {
																	if (devices[i] == user.id) {
																		$('#users')[0].innerHTML += "<option value='"+user.id+"' selected='selected'>"
																				+ user.username
																				+ "</option>";
																		exist = true;
																		break;
																	}
																}
																if (!exist) {
																	$('#users')[0].innerHTML += "<option value='"+user.id+"'>"
																			+ user.username
																			+ "</option>";
																}
															});
										});

						$('.form-control').tooltip();
						LoadSelect2Script(DemoSelect2);
						LoadBootstrapValidatorScript(validStrategy);
					});
</script>
