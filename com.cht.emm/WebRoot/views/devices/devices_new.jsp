<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="row">
	<div class="col-xs-12 col-sm-12">
		<form class="form-horizontal" id="form_create_device">
			<div class="form-group">
				<label class="col-sm-2 control-label">设备码<span
					class="required">*</span></label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="id" name="id" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">设备型号<span
					class="required">*</span></label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="name" name="name" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">操作系统<span
					class="required">*</span></label>
				<div class="col-sm-8">
					<select id="os" class="populate placeholder" name="os">
						<option value="">-- 选择操作系统 --</option>
						<option value="0">Android</option>
						<option value="1">IOS</option>
						<option value="2">Windows</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">设备类型<span
					class="required">*</span></label>
				<div class="col-sm-8">
					<select id="type" class="populate placeholder" name="type">
						<option value="">-- 选择设备类型 --</option>
						<option value="0">个人</option>
						<option value="1">企业</option>
						<option value="2">共用</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">关联用户（多选）</label>
				<div class="col-sm-8">
					<select id="users" multiple="multiple" class="populate placeholder">
					</select>
				</div>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript">
	function validDevice() {
		$('#form_create_device').bootstrapValidator({
			message : "invalid",
			fields : {
				id : {
					validators : {
						notEmpty : {
							message : "设备码必填"
						},
						stringLength : {
							max : 36,
							message : "设备码长度不超过36个字符"
						}
					}

				},
				name : {
					validators : {
						notEmpty : {
							message : "设备型号必填"
						},
						stringLength : {
							max : 32,
							message : "设备型号长度不超过32个字符"
						}
					}

				},
				os : {
					validators : {
						notEmpty : {
							message : "操作系统必选"
						}
					}

				},
				type : {
					validators : {
						notEmpty : {
							message : "设备类型必选"
						}
					}

				}

			}

		});
	}
	function DemoSelect2() {
		$('#os').select2();
		$('#type').select2();
		$('#users').select2({
			placeholder : "-- 选择关联用户 --"
		});
	}
	$(document)
			.ready(
					function() {
						status = "";
						users = [];

						if (id) {
							$
									.getJSON(
											"rest/device/" + id,
											function(result) {
												device = result.resultValue;
												$('#id')[0].value = device.id;
												$('#id')[0].readOnly = true;
												$('#name')[0].value = device.name;
												var osOptions = document
														.getElementById('os').children;
												for ( var i = 0; i < osOptions.length; i++) {
													if (osOptions[i].value
															&& osOptions[i].value == device.os) {
														osOptions[i].selected = true;
														break;
													}
												}
												var typeOptions = document
														.getElementById('type').children;
												for ( var i = 0; i < typeOptions.length; i++) {
													if (typeOptions[i].value
															&& typeOptions[i].value == device.type) {
														typeOptions[i].selected = true;
														break;
													}
												}
												status = device.status;
												users = device.users.split(",");
											});
						}
						$.ajaxSettings.async = false;
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
																for ( var i = 0; i < users.length; i++) {
																	if (users[i] == user.username) {
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
						LoadBootstrapValidatorScript(validDevice);
					});
</script>
