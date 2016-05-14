<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row-fluid">
	<div id="dashboard_links" class="col-xs-12 col-sm-2 pull-right">
		<ul class="nav nav-pills nav-stacked">
			<li class="active"><a href="#" class="link_dash" id="overview">常规</a></li>
			<li><a href="#" class="link_dash" id="clients">密码</a></li>
			<li><a href="#" class="link_dash" id="graph">限制</a></li>
			<li><a href="#" class="link_dash" id="servers">WIFI</a></li>
			<li><a href="#" class="link_dash" id="vpn">VPN</a></li>
			<li><a href="#" class="link_dash" id="app">应用</a></li>
		</ul>
	</div>
	<div id="dashboard_tabs" class="col-xs-12 col-sm-10">
		<!--Start Dashboard Tab 1-->
		<div id="dashboard-overview" class="row"
			style="display: block; position: relative;">
			<form class="form-horizontal" id="form_create_config">
				<div class="form-group">
					<label class="col-sm-2 control-label">配置名称<span
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
		<!--End Dashboard Tab 1-->
		<!--Start Dashboard Tab 2-->
		<div id="dashboard-clients" class="row"
			style="display: none; position: relative;">
			<form class="form-horizontal" role="form">
				<div class="form-group">
					<label class="col-sm-3 control-label">密码强度</label>
					<div class="col-sm-8">
						<select id="pwd_intensity" class="populate placeholder">
							<option value="-1">-- 选择密码强度 --</option>
							<option value="0">无限制</option>
							<option value="1">包含字母</option>
							<option value="2">包含数字</option>
							<option value="3">包含字母和数字</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">密码最小长度</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="pwd_length_min"
							name="pwdMinLength" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">密码最大输入次数</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="pwd_time_max" />
					</div>
				</div>
			</form>
		</div>
		<!--End Dashboard Tab 2-->
		<!--Start Dashboard Tab 3-->
		<div id="dashboard-graph" class="row"
			style="display: none; position: relative;">
			<form class="form-horizontal" role="form">
				<div class="form-group">
					<label class="col-sm-3 control-label">数据加密</label>
					<div class="col-sm-8">
						<select id="data_encrypt" class="populate placeholder">
							<option value="-1">-- 选择数据加密 --</option>
							<option value="1">启用</option>
							<option value="2">禁用</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">审计功能</label>
					<div class="col-sm-8">
						<select id="audit" class="populate placeholder">
							<option value="-1">-- 选择审计功能 --</option>
							<option value="1">启用</option>
							<option value="2">禁用</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">蓝牙</label>
					<div class="col-sm-8">
						<select id="bluetooth" class="populate placeholder">
							<option value="-1">-- 选择蓝牙 --</option>
							<option value="1">启用</option>
							<option value="2">禁用</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">摄像头</label>
					<div class="col-sm-8">
						<select id="camera" class="populate placeholder">
							<option value="-1">-- 选择摄像头 --</option>
							<option value="1">启用</option>
							<option value="2">禁用</option>
						</select>
					</div>
				</div>
			</form>
		</div>
		<!--End Dashboard Tab 3-->
		<!--Start Dashboard Tab 4-->
		<div id="dashboard-servers" class="row"
			style="display: none; position: relative;">
			<div>
				<a href="javascript:addWifi()">
					<i class="fa fa-plus"></i> 添加WIFI
				</a>
				<a href="javascript:deleteWifi()">
					<i class="fa fa-minus"></i> 删除WIFI
				</a>
			</div>
			<table
				class="table table-bordered table-striped table-hover table-heading table-datatable"
				id="datatable-5">
				<thead>
					<tr>
						<th><input type="checkbox" onclick="checkAllWifi(this)" /></th>
						<th>WIFI名称</th>
						<th>WIFI密码</th>
					</tr>
				</thead>
			</table>
		</div>
		<!--End Dashboard Tab 4-->
		<!--Start Dashboard Tab 5-->
		<div id="dashboard-vpn" class="row"
			style="display: none; position: relative;">
			<div>
				<a href="javascript:addVpn()">
					<i class="fa fa-plus"></i> 添加VPN
				</a>
				<a href="javascript:deleteVpn()">
					<i class="fa fa-minus"></i> 删除VPN
				</a>
			</div>
			<table
				class="table table-bordered table-striped table-hover table-heading table-datatable"
				id="datatable-6">
				<thead>
					<tr>
						<th><input type="checkbox" onclick="checkAllVpn(this)" /></th>
						<th>VPN名称</th>
						<th>VPN密码</th>
					</tr>
				</thead>
			</table>
		</div>
		<!--End Dashboard Tab 5-->
		<!--Start Dashboard Tab 6-->
		<div id="dashboard-app" class="row"
			style="display: none; position: relative;">
			<form class="form-horizontal" role="form">
				<div class="form-group">
					<label class="col-sm-3 control-label">名单类型</label>
					<div class="col-sm-8">
						<select id="app_list_type" class="populate placeholder">
							<option value="-1">-- 选择名单类型 --</option>
							<option value="1">黑名单</option>
							<option value="2">白名单</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">应用名称（多选）</label>
					<div class="col-sm-8">
						<select id="apps" multiple="multiple" class="populate placeholder">
						</select>
					</div>
				</div>
			</form>
		</div>
		<!--End Dashboard Tab 6-->
	</div>
	<div class="clearfix"></div>
</div>
<!--End Dashboard 2 -->
<div style="height: 40px;"></div>
<script type="text/javascript">
	function validConfig() {
		$('#form_create_config').bootstrapValidator({
			message : "invalid",
			fields : {
				name : {
					validators : {
						notEmpty : {
							message : "配置名称必填"
						},
						stringLength : {
							max : 16,
							message : "配置名称长度不超过16个字符"
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
	function addWifi() {
		OpenBigDialogWithConfirm2("添加WIFI", 'console/configs_wifi',
				'configs_wifi', true, true, doAddWifi);
	}
	function doAddWifi() {
		$('#form_create_wifi').bootstrapValidator('validate');
		var result = $("#form_create_wifi").data("bootstrapValidator")
				.isValid();
		if (result) {
			var wifiName = $("#wifiName").val();
			var wifiPassword = $("#wifiPassword").val();
			var dataTable = $('#datatable-5').dataTable();
			dataTable.fnAddData([ '<input name="appCheck" type="checkbox" />',
					wifiName, wifiPassword ], true);
			return true;
		}
		return false;
	}
	function deleteWifi() {
		var dataTable = $('#datatable-5').dataTable();
		var nNodes = dataTable.fnGetNodes();
		for ( var i = 0; i < nNodes.length; i++) {
			if (nNodes[i].children[0].firstChild.checked) {
				dataTable.fnDeleteRow(i, null, true);
				nNodes = dataTable.fnGetNodes();
				i--;
			}
		}
	}
	function checkAllWifi(checkbox) {
		var dataTableFrom = $('#datatable-5').dataTable();
		var nNodes = dataTableFrom.fnGetNodes();
		for ( var i = 0; i < nNodes.length; i++) {
			nNodes[i].children[0].firstChild.checked = checkbox.checked;
		}
	}
	function addVpn() {
		OpenBigDialogWithConfirm2("添加VPN", 'console/configs_vpn',
				'configs_vpn', true, true, doAddVpn);
	}
	function doAddVpn() {
		$('#form_create_vpn').bootstrapValidator('validate');
		var result = $("#form_create_vpn").data("bootstrapValidator").isValid();
		if (result) {
			var vpnName = $("#vpnName").val();
			var vpnPassword = $("#vpnPassword").val();
			var dataTable = $('#datatable-6').dataTable();
			dataTable.fnAddData([ '<input name="appCheck" type="checkbox" />',
					vpnName, vpnPassword ], true);
			return true;
		}
		return false;
	}
	function deleteVpn() {
		var dataTable = $('#datatable-6').dataTable();
		var nNodes = dataTable.fnGetNodes();
		for ( var i = 0; i < nNodes.length; i++) {
			if (nNodes[i].children[0].firstChild.checked) {
				dataTable.fnDeleteRow(i, null, true);
				nNodes = dataTable.fnGetNodes();
				i--;
			}
		}
	}
	function checkAllVpn(checkbox) {
		var dataTableFrom = $('#datatable-6').dataTable();
		var nNodes = dataTableFrom.fnGetNodes();
		for ( var i = 0; i < nNodes.length; i++) {
			nNodes[i].children[0].firstChild.checked = checkbox.checked;
		}
	}
	function AllTables() {
		TestTableX("datatable-5");
		TestTableX("datatable-6");
		LoadSelect2Script(DemoSelect2);
	}
	function DemoSelect2() {
		MakeSelect();
		$('.dataTables_filter').each(function() {
			$(this).find('label input[type=text]').attr('placeholder', '搜索');
		});
		$('#users').select2({
			placeholder : "-- 选择关联用户 --"
		});
		$('#pwd_intensity').select2();
		$('#app_list_type').select2();
		$('#apps').select2({
			placeholder : "-- 选择应用 --"
		});
		$('#data_encrypt').select2();
		$('#audit').select2();
		$('#bluetooth').select2();
		$('#camera').select2();
	}
	$(document)
			.ready(
					function() {
						$.ajaxSettings.async = false;
						devices = [];
						apps = [];
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
											"rest/device/config/" + id,
											function(result) {
												config = result.resultValue;
												$('#name')[0].value = config.name;
												$('#desc')[0].value = config.desc;
												if (config.users) {
													devices = config.users
															.split(",");
												} 
												var pwd_intensityOptions = document
														.getElementById('pwd_intensity').children;
												for ( var i = 0; i < pwd_intensityOptions.length; i++) {
													if (pwd_intensityOptions[i].value
															&& pwd_intensityOptions[i].value == config.pwd_intensity) {
														pwd_intensityOptions[i].selected = true;
														break;
													}
												}
												if (config.pwd_length_min != 0) {
													$('#pwd_length_min')[0].value = config.pwd_length_min;
												}
												if (config.pwd_time_max != 0) {
													$('#pwd_time_max')[0].value = config.pwd_time_max;
												}

												var data_encryptOptions = document
														.getElementById('data_encrypt').children;
												for ( var i = 0; i < data_encryptOptions.length; i++) {
													if (data_encryptOptions[i].value == config.data_encrypt) {
														data_encryptOptions[i].selected = true;
														break;
													}
												}
												var auditOptions = document
														.getElementById('audit').children;
												for ( var i = 0; i < auditOptions.length; i++) {
													if (auditOptions[i].value == config.audit) {
														auditOptions[i].selected = true;
														break;
													}
												}
												var bluetoothOptions = document
														.getElementById('bluetooth').children;
												for ( var i = 0; i < bluetoothOptions.length; i++) {
													if (bluetoothOptions[i].value == config.bluetooth) {
														bluetoothOptions[i].selected = true;
														break;
													}
												}
												var cameraOptions = document
														.getElementById('camera').children;
												for ( var i = 0; i < cameraOptions.length; i++) {
													if (cameraOptions[i].value == config.camera) {
														cameraOptions[i].selected = true;
														break;
													}
												}
												var app_list_typeOptions = document
														.getElementById('app_list_type').children;
												for ( var i = 0; i < app_list_typeOptions.length; i++) {
													if (app_list_typeOptions[i].value == config.app_list_type) {
														app_list_typeOptions[i].selected = true;
														break;
													}
												}
												if (config.apps) {
													apps = config.apps
															.split(",");
												} 
												$
														.each(
																config.vpns,
																function(index,
																		vpn) {
																	$(
																			"#datatable-6")
																			.append(
																					"<tr>"
																							+ "<td><input name=\"appCheck\" type=\"checkbox\"/></td>"
																							+ "<td>"
																							+ vpn.name
																							+ "</td>"
																							+ "<td>"
																							+ vpn.password
																							+ "</td>"
																							+ "</tr>");
																});
												$
														.each(
																config.wifies,
																function(index,
																		wifi) {
																	$(
																			"#datatable-5")
																			.append(
																					"<tr>"
																							+ "<td><input name=\"appCheck\" type=\"checkbox\"/></td>"
																							+ "<td>"
																							+ wifi.name
																							+ "</td>"
																							+ "<td>"
																							+ wifi.password
																							+ "</td>"
																							+ "</tr>");
																});
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

						$('#apps')[0].innerHTML = "";
						$
								.getJSON(
										"rest/app/all",
										function(result) {
											appList = result.resultValue;
											$
													.each(
															appList,
															function(index, app) {
																var exist = false;
																for ( var i = 0; i < apps.length; i++) {
																	if (apps[i] == app.id) {
																		$('#apps')[0].innerHTML += "<option value='"+app.id+"' selected='selected'>"
																				+ app.name
																				+ "</option>";
																		exist = true;
																		break;
																	}
																}
																if (!exist) {
																	$('#apps')[0].innerHTML += "<option value='"+app.id+"'>"
																			+ app.name
																			+ "</option>";
																}
															});
										});

						var ides = new Array();
						$("a.link_dash").click(function() {
							if (ides != null && ides.length == 0) {
								$("a.link_dash").each(function() {
									ides.push($(this).attr("id"));
								});
							}

							var pageid = $(this).attr("id");
							for ( var i in ides) {
								$("#dashboard-" + ides[i]).hide();
							}
							$(this).closest('ul').find('li').each(function() {
								$(this).removeClass('active');
							});
							$("#dashboard-" + pageid).show();
							$("#" + pageid).parent('li').addClass('active');
						});

						$('.form-control').tooltip();
						LoadBootstrapValidatorScript(validConfig);
						LoadDataTablesScripts(AllTables);
					});
</script>
