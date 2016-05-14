<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="dashboard-header" class="row">
	<div class="col-xs-10 col-sm-4">
		<h3 id="name"></h3>
	</div>
	<div class="col-xs-2 col-sm-6 col-sm-offset-1">
		<a href="javascript:lockDevice()"> <i class="fa fa-lock"></i> 锁定 </a>
		<a href="javascript:unlockDevice()"> <i class="fa fa-bell"></i> 解锁
		</a> <a href="javascript:wipeDevice()"> <i class="fa fa-eraser"></i>
			擦除 </a>
	</div>
</div>
<div class="row-fluid">
	<div id="dashboard_links" class="col-xs-12 col-sm-2 pull-right">
		<ul class="nav nav-pills nav-stacked">
			<li class="active"><a href="#" class="link_dash" id="overview">摘要</a>
			</li>
			<li><a href="#" class="link_dash" id="clients">合规性</a></li>
			<li><a href="#" class="link_dash" id="graph">配置文件</a></li>
			<li><a href="#" class="link_dash" id="servers">应用</a></li>
		</ul>
	</div>
	<div id="dashboard_tabs" class="col-xs-12 col-sm-10">
		<!--Start Dashboard Tab 1-->
		<div id="dashboard-overview" class="row"
			style="display: block; position: relative;">
			<div class="row">
				<div class="col-xs-6">
					<div class="row">
						<div class="col-xs-12">
							<div class="box no-drop">
								<div class="box-content">
									<h3>
										<p class="page-header">
											<i class="fa fa-exclamation-circle"></i> 设备信息
										</p>
									</h3>
									<h4>
										<p>设备码</p>
									</h4>
									<p id="id"></p>
									<h4>
										<p>电源状态</p>
									</h4>
									<p id="battery"></p>
									<h4>
										<p>内存状态</p>
									</h4>
									<p id="memory"></p>
									<h4>
										<p>ip地址</p>
									</h4>
									<p id="ip"></p>
									<h4>
										<p>平台版本号</p>
									</h4>
									<p id="appVersion"></p>
									<h4>
										<p>操作系统版本号</p>
									</h4>
									<p id="osVersion"></p>
									<h4>
										<p>SDK版本号</p>
									</h4>
									<p id="sdk"></p>
									<h4>
										<p>设备注册时间</p>
									</h4>
									<p id="register"></p>

								</div>
							</div>
						</div>
					</div>

				</div>
				<div class="col-xs-6">
					<div class="row">
						<div class="col-xs-12">
							<div class="box no-drop">
								<div class="box-content">
									<h4>
										<p>屏幕宽度</p>
									</h4>
									<p id="width"></p>
									<h4>
										<p>屏幕高度</p>
									</h4>
									<p id="height"></p>
									<h4>
										<p>手机号</p>
									</h4>
									<p id="phone"></p>
									<h4>
										<p>厂商</p>
									</h4>
									<p id="product"></p>
									<h4>
										<p>mac地址</p>
									</h4>
									<p id="mac"></p>
									<h4>
										<p>imei</p>
									</h4>
									<p id="imei"></p>
									<h4>
										<p>imsi</p>
									</h4>
									<p id="imsi"></p>
									<h4>
										<p>sim卡号</p>
									</h4>
									<p id="sim"></p>
									<h4>
										<p>经度</p>
									</h4>
									<p id="longitude"></p>
									<h4>
										<p>维度</p>
									</h4>
									<p id="latitude"></p>
									<h4>
										<p>海拔高度</p>
									</h4>
									<p id="altitude"></p>
								</div>
							</div>
						</div>
					</div>

				</div>

			</div>
		</div>
		<!--End Dashboard Tab 1-->
		<!--Start Dashboard Tab 2-->
		<div id="dashboard-clients" class="row"
			style="display: none; position: relative;">
			<div class="box no-drop">
				<div class="box-content" style="margin-bottom: 0.5em">
					<div class="row">
						<div class="col-xs-8"></div>
						<div class="col-xs-4 input-group searchInput">
							<input type="text" class="form-control"> <span
								class="input-group-addon" onClick="searchAppScoreTable(this)"><i
								class="fa fa-search"></i> </span>
						</div>
					</div>
				</div>
				<div class="box-content no-padding table-responsive">
					<table
						class="table table-bordered table-striped table-hover table-heading table-datatable"
						id="appScoreList">
						<thead>
							<tr>
								<th>关联用户</th>
								<th>策略名称</th>
								<th>策略描述</th>
								<th>创建时间</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!--End Dashboard Tab 2-->
		<!--Start Dashboard Tab 3-->
		<div id="dashboard-graph" class="row"
			style="display: none; position: relative;">
			<div class="box no-drop">
				<div class="box-content" style="margin-bottom: 0.5em">
					<div class="row">
						<div class="col-xs-8"></div>
						<div class="col-xs-4 input-group searchInput">
							<input type="text" class="form-control"> <span
								class="input-group-addon" onClick="searchAppVersionTable(this)"><i
								class="fa fa-search"></i> </span>
						</div>
					</div>
				</div>
				<div class="box-content no-padding table-responsive">
					<table
						class="table table-bordered table-striped table-hover table-heading table-datatable"
						id="appVersionList">
						<thead>
							<tr>
								<th>关联用户</th>
								<th>配置名称</th>
								<th>配置描述</th>
								<th>创建时间</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!--End Dashboard Tab 3-->
		<!--Start Dashboard Tab 4-->
		<div id="dashboard-servers" class="row"
			style="display: none; position: relative;">
			<div class="box no-drop">
				<div class="box-content" style="margin-bottom: 0.5em">
					<div class="row">
						<div class="col-xs-8"></div>
						<div class="col-xs-4 input-group searchInput">
							<input type="text" class="form-control"> <span
								class="input-group-addon" onClick="searchAppDeployTable(this)"><i
								class="fa fa-search"></i> </span>
						</div>
					</div>
				</div>
				<div class="box-content no-padding table-responsive">
					<table
						class="table table-bordered table-striped table-hover table-heading table-datatable"
						id="appDeployList">
						<thead>
							<tr>
								<th>应用名称</th>
								<th>版本名称</th>
								<th>用户</th>
								<th>下载时间</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!--End Dashboard Tab 4-->
	</div>
	<div class="clearfix"></div>
</div>
<!--End Dashboard 2 -->
<div style="height: 40px;"></div>
<script type="text/javascript">
	var appScoreTable = null;
	function searchAppScoreTable(o) {
		OSettings = appScoreTable.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		appScoreTable._fnFilterComplete(OSettings, {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		});

	}
	appScoreTableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "rest/device/strategyDevices/pages/" + id,
		"aaSorting" : [ [ 0, "asc" ] ],
		"aLengthMenu" : [ [ 10, 20, 50 ], [ 10, 20, 50 ] ],
		"sDom" : "rt<'box-content'<'col-sm-12'<'tool_box_pageInfo' ilp>> <'clearfix'>>",
		"sPaginationType" : "bootstrap",
		"oLanguage" : {
			"sSearch" : ""
		},
		"aoColumns" : [ {
			"mData" : "creator",
			"bSortable" : false,
			"bSearchable" : false
		}, {
			"mData" : "name",
			"bSortable" : false,
			"bSearchable" : false
		}, {
			"mData" : "desc",
			"bSortable" : false,
			"bSearchable" : false
		}, {
			"mData" : "time",
			"bSortable" : false,
			"bSearchable" : false
		} ]
	};
	var appVersionTable = null;
	function searchAppVersionTable(o) {
		OSettings = appVersionTable.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		appVersionTable._fnFilterComplete(OSettings, {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		});

	}
	appVersionTableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "rest/device/configDevices/pages/" + id,
		"aaSorting" : [ [ 0, "asc" ] ],
		"aLengthMenu" : [ [ 10, 20, 50 ], [ 10, 20, 50 ] ],
		"sDom" : "rt<'box-content'<'col-sm-12'<'tool_box_pageInfo' ilp>> <'clearfix'>>",
		"sPaginationType" : "bootstrap",
		"oLanguage" : {
			"sSearch" : ""
		},
		"aoColumns" : [ {
			"mData" : "creator",
			"bSortable" : false,
			"bSearchable" : false
		}, {
			"mData" : "name",
			"bSortable" : false,
			"bSearchable" : false
		}, {
			"mData" : "desc",
			"bSortable" : false,
			"bSearchable" : false
		}, {
			"mData" : "time",
			"bSortable" : false,
			"bSearchable" : false
		} ]
	};
	var appDeployTable = null;
	function searchAppDeployTable(o) {
		OSettings = appDeployTable.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		appDeployTable._fnFilterComplete(OSettings, {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		});

	}
	appDeployTableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "rest/device/appDeploys/pages/" + id,
		"aaSorting" : [ [ 0, "asc" ] ],
		"aLengthMenu" : [ [ 10, 20, 50 ], [ 10, 20, 50 ] ],
		"sDom" : "rt<'box-content'<'col-sm-12'<'tool_box_pageInfo' ilp>> <'clearfix'>>",
		"sPaginationType" : "bootstrap",
		"oLanguage" : {
			"sSearch" : ""
		},
		"aoColumns" : [ {
			"mData" : "appname",
			"bSortable" : false,
			"bSearchable" : true
		}, {
			"mData" : "version",
			"bSortable" : false,
			"bSearchable" : false
		}, {
			"mData" : "username",
			"bSortable" : false,
			"bSearchable" : true
		}, {
			"mData" : "time",
			"bSortable" : true,
			"bSearchable" : false
		} ]
	};
	function AllTables() {
		appScoreTable = $("#appScoreList").dataTable(appScoreTableSetting);
		appDeployTable = $("#appDeployList").dataTable(appDeployTableSetting);
		appVersionTable = $("#appVersionList")
				.dataTable(appVersionTableSetting);
		LoadSelect2Script(MakeSelect);
	}
	function lockDevice() {
		$.ajax({
			url : "rest/device/lock?p_deviceID=" + id,
			type : "POST",
			contentType : 'application/json',
			processData : false,
			dataType : 'json',
			async : false,
			success : function(p_resultValue) {
				if (p_resultValue.successful && p_resultValue.resultValue) {
					confirm(p_resultValue.resultMessage, function() {
						$.ajax({
							url : "rest/device/offline?msg=lock&p_deviceID="
									+ id,
							type : "POST",
							contentType : 'application/json',
							processData : false,
							dataType : 'json',
							async : false,
							success : function(p_resultValue) {
							},
							error : function(p_request, p_status, p_err) {
							}
						});
					});
					return;
				}
				alert(p_resultValue.resultMessage);
			},
			error : function(p_request, p_status, p_err) {
			}
		});
	}
	function unlockDevice() {
		$.ajax({
			url : "rest/device/unlock?p_deviceID=" + id,
			type : "POST",
			contentType : 'application/json',
			processData : false,
			dataType : 'json',
			async : false,
			success : function(p_resultValue) {
				if (p_resultValue.successful && p_resultValue.resultValue) {
					confirm(p_resultValue.resultMessage,function(){
						$.ajax({
							url : "rest/device/offline?msg=unlock&p_deviceID="
									+ id,
							type : "POST",
							contentType : 'application/json',
							processData : false,
							dataType : 'json',
							async : false,
							success : function(p_resultValue) {
							},
							error : function(p_request, p_status, p_err) {
							}
						});
					});
					return;
				}
			},
			error : function(p_request, p_status, p_err) {
			}
		});
	}
	function wipeDevice() {
		$.ajax({
			url : "rest/device/wipe?p_deviceID=" + id,
			type : "POST",
			contentType : 'application/json',
			processData : false,
			dataType : 'json',
			async : false,
			success : function(p_resultValue) {
				if (p_resultValue.successful && p_resultValue.resultValue) {
					confirm(p_resultValue.resultMessage,function(){
						$.ajax({
							url : "rest/device/offline?msg=wipe&p_deviceID="
									+ id,
							type : "POST",
							contentType : 'application/json',
							processData : false,
							dataType : 'json',
							async : false,
							success : function(p_resultValue) {
							},
							error : function(p_request, p_status, p_err) {
							}
						});
					});
					return;
				}
			},
			error : function(p_request, p_status, p_err) {
			}
		});
	}
	function loadDeviceDetail(id) {
		$.ajaxSettings.async = false;
		$
				.getJSON(
						"rest/device/detail/" + id,
						function(result) {
							deviceDetail = result.resultValue;
							document.getElementById('name').innerHTML = deviceDetail.device.name;
							document.getElementById('id').innerHTML = deviceDetail.device.id;
							document.getElementById('battery').innerHTML = deviceDetail.device.battery
									+ "剩余";
							document.getElementById('memory').innerHTML = deviceDetail.device.mem
									+ "M 可用/"
									+ deviceDetail.device.totalMem
									+ "M 总计";
							document.getElementById('ip').innerHTML = deviceDetail.device.ip;
							document.getElementById('appVersion').innerHTML = deviceDetail.device.appVersion;
							document.getElementById('osVersion').innerHTML = deviceDetail.device.osVersion;
							document.getElementById('register').innerHTML = deviceDetail.device.register;
							document.getElementById('width').innerHTML = deviceDetail.device.width;
							document.getElementById('height').innerHTML = deviceDetail.device.height;
							document.getElementById('phone').innerHTML = deviceDetail.device.phone;
							document.getElementById('mac').innerHTML = deviceDetail.device.mac;
							document.getElementById('sim').innerHTML = deviceDetail.device.sim;
							document.getElementById('latitude').innerHTML = deviceDetail.device.latitude;
							document.getElementById('longitude').innerHTML = deviceDetail.device.longitude;
							document.getElementById('altitude').innerHTML = deviceDetail.device.altitude;
							document.getElementById('product').innerHTML = deviceDetail.device.product;
							document.getElementById('imsi').innerHTML = deviceDetail.device.imsi;
							document.getElementById('imei').innerHTML = deviceDetail.device.imei;
							document.getElementById('sdk').innerHTML = deviceDetail.device.sdk;
						});
	}
	$(document).ready(function() {
		loadDeviceDetail(id);
		LoadDataTablesScripts(AllTables);
		var ids = new Array();
		$("a.link_dash").click(function() {
			if (ids != null && ids.length == 0) {
				$("a.link_dash").each(function() {
					ids.push($(this).attr("id"));
				});
			}

			id = $(this).attr("id");
			for ( var i in ids) {
				$("#dashboard-" + ids[i]).hide();
			}
			$(this).closest('ul').find('li').each(function() {
				$(this).removeClass('active');
			});
			$("#dashboard-" + id).show();
			$("#" + id).parent('li').addClass('active');
		});
	});
</script>
