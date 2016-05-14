<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">设备管理</a></li>
			<li><a href="#">生命周期</a></li>
		</ol>
	</div>
</div>
<div class="row-fluid">
	<div id="dashboard_links" class="col-xs-12 col-sm-2 pull-right">
		<ul class="nav nav-pills nav-stacked">
			<li class="active"><a href="#" class="link_dash"
				id="registerPage">注册</a></li>
			<li><a href="#" class="link_dash" id="deny">驳回</a></li>
			<li><a href="#" class="link_dash" id="activate">激活</a></li>
			<li><a href="#" class="link_dash" id="cancel">注销</a></li>
		</ul>
	</div>
	<div id="dashboard_tabs" class="col-xs-12 col-sm-10">
		<!--Start Dashboard Tab 1-->
		<div id="dashboard-registerPage" class="row"
			style="display: block; position: relative;">
			<div class="box no-drop">
				<div class="box-content" style="margin-bottom: 0.5em">
					<div class="row">
						<div class="col-xs-10">
							<ul class="operate">
								<li class="tool_box_left"><a href="#"
									onClick="activateDevice(this)" title="激活设备" class="deleteIcon">
										<i class="fa fa-bolt"></i> 激活设备
								</a></li>
								<li class="tool_box_left"><a href="#"
									onClick="denyDevice(this)" title="驳回申请" class="deleteIcon">
										<i class="fa fa-thumbs-o-down"></i>驳回申请
								</a></li>
							</ul>
						</div>
						<div class="col-xs-2 input-group searchInput">
							<input type="text" class="form-control"> <span
								class="input-group-addon"
								onClick="searchRegisterDeviceTable(this)"><i
								class="fa fa-search"></i></span>
						</div>
					</div>
				</div>
				<div class="box-content no-padding table-responsive">
					<table
						class="table table-bordered table-striped table-hover table-heading table-datatable"
						id="registerDeviceList">
						<thead>
							<tr>
								<th><input type="checkbox" class="selectAll"
									onclick="selectAll(this)"></th>
								<th>设备型号</th>
								<th>用户</th>
								<th>操作系统</th>
								<th>设备类型</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!--End Dashboard Tab 1-->
		<!--Start Dashboard Tab 2-->
		<div id="dashboard-deny" class="row"
			style="display: none; position: relative;">
			<div class="box no-drop">
				<div class="box-content" style="margin-bottom: 0.5em">
					<div class="row">
						<div class="col-xs-10">
							<ul class="operate">
								<li class="tool_box_left"><a href="#"
									onClick="activateDevice(this)" title="激活设备" class="deleteIcon">
										<i class="fa fa-bolt"></i> 激活设备
								</a></li>
								<li class="tool_box_left"><a href="#"
									onClick="deleteDevice(this)" title="删除设备" class="deleteIcon">
										<i class="fa fa-trash-o"></i>删除设备
								</a></li>
							</ul>
						</div>
						<div class="col-xs-2 input-group searchInput">
							<input type="text" class="form-control"> <span
								class="input-group-addon" onClick="searchDenyDeviceTable(this)"><i
								class="fa fa-search"></i></span>
						</div>
					</div>
				</div>
				<div class="box-content no-padding table-responsive">
					<table
						class="table table-bordered table-striped table-hover table-heading table-datatable"
						id="denyDeviceList">
						<thead>
							<tr>
								<th><input type="checkbox" class="selectAll"
									onclick="selectAll(this)"></th>
								<th>设备型号</th>
								<th>用户</th>
								<th>操作系统</th>
								<th>设备类型</th>
								<th>备注</th>
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
		<div id="dashboard-activate" class="row"
			style="display: none; position: relative;">
			<div class="box no-drop">
				<div class="box-content" style="margin-bottom: 0.5em">
					<div class="row">
						<div class="col-xs-10">
							<ul class="operate">
								<li class="tool_box_left"><a href="#"
									onClick="cancelDevice(this)" title="注销设备" class="deleteIcon">
										<i class="fa fa-bolt"></i> 注销设备
								</a></li>
							</ul>
						</div>
						<div class="col-xs-2 input-group searchInput">
							<input type="text" class="form-control"> <span
								class="input-group-addon"
								onClick="searchActivateDeviceTable(this)"><i
								class="fa fa-search"></i></span>
						</div>
					</div>
				</div>
				<div class="box-content no-padding table-responsive">
					<table
						class="table table-bordered table-striped table-hover table-heading table-datatable"
						id="activateDeviceList">
						<thead>
							<tr>
								<th><input type="checkbox" class="selectAll"
									onclick="selectAll(this)"></th>
								<th>设备型号</th>
								<th>用户</th>
								<th>操作系统</th>
								<th>设备类型</th>
								<th>是否在线</th>
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
		<div id="dashboard-cancel" class="row"
			style="display: none; position: relative;">
			<div class="box no-drop">
				<div class="box-content" style="margin-bottom: 0.5em">
					<div class="row">
						<div class="col-xs-10">
							<ul class="operate">
								<li class="tool_box_left"><a href="#"
									onClick="activateDevice(this)" title="激活设备" class="deleteIcon">
										<i class="fa fa-bolt"></i> 激活设备
								</a></li>
								<li class="tool_box_left"><a href="#"
									onClick="deleteDevice(this)" title="删除设备" class="deleteIcon">
										<i class="fa fa-trash-o"></i>删除设备
								</a></li>
							</ul>
						</div>
						<div class="col-xs-2sss input-group searchInput">
							<input type="text" class="form-control"> <span
								class="input-group-addon"
								onClick="searchCancelDeviceTable(this)"><i
								class="fa fa-search"></i></span>
						</div>
					</div>
				</div>
				<div class="box-content no-padding table-responsive">
					<table
						class="table table-bordered table-striped table-hover table-heading table-datatable"
						id="cancelDeviceList">
						<thead>
							<tr>
								<th><input type="checkbox" class="selectAll"
									onclick="selectAll(this)"></th>
								<th>设备型号</th>
								<th>用户</th>
								<th>操作系统</th>
								<th>设备类型</th>
								<th>备注</th>
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
<script type="text/javascript">
	var registerDeviceTable = null;
	function searchRegisterDeviceTable(o) {
		OSettings = registerDeviceTable.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		registerDeviceTable._fnFilterComplete(OSettings, {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		});

	}
	registerDeviceTableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "rest/device/register/pages",
		"aaSorting" : [ [ 0, "asc" ] ],
		"aLengthMenu" : [ [ 10, 20, 50 ], [ 10, 20, 50 ] ],
		"sDom" : "rt<'box-content'<'col-sm-12'<'tool_box_pageInfo' ilp>> <'clearfix'>>",
		"sPaginationType" : "bootstrap",
		"oLanguage" : {
			"sSearch" : ""
		},
		"aoColumns" : [
				{
					"mData" : "id",
					"mRender" : function(data, type, full) {
						return "<input type=\"checkbox\" value=\""+data+"\">";
					},
					"bSortable" : false,
					"bSearchable" : false
				},
				{
					"mData" : "name",
					"mRender" : function(data, type, full) {
						return "<a href=\"#\" class=\"link_status\" onclick=\"getDevice('"
								+ full.id + "')\">" + data + "</a>";
					},
					"bSortable" : false,
					"bSearchable" : true
				},

				{
					"mData" : "users",
					"bSortable" : false,
					"bSearchable" : false
				}, {
					"mData" : "os",
					"mRender" : function(value, type, full) {
						if (value == 0) {
							return "Android";
						} else if (value == 1) {
							return "IOS";
						} else if (value == 2) {
							return "Windows";
						}
					},
					"bSortable" : true,
					"bSearchable" : false
				}, {
					"mData" : "type",
					"mRender" : function(value, type, full) {
						if (value == 0) {
							return "个人";
						} else if (value == 1) {
							return "企业";
						} else if (value == 2) {
							return "共用";
						}
					},
					"bSortable" : false,
					"bSearchable" : false
				} ]
	};
	var denyDeviceTable = null;
	function searchDenyDeviceTable(o) {
		OSettings = denyDeviceTable.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		denyDeviceTable._fnFilterComplete(OSettings, {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		});

	}
	denyDeviceTableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "rest/device/deny/pages",
		"aaSorting" : [ [ 0, "asc" ] ],
		"aLengthMenu" : [ [ 10, 20, 50 ], [ 10, 20, 50 ] ],
		"sDom" : "rt<'box-content'<'col-sm-12'<'tool_box_pageInfo' ilp>> <'clearfix'>>",
		"sPaginationType" : "bootstrap",
		"oLanguage" : {
			"sSearch" : ""
		},
		"aoColumns" : [
				{
					"mData" : "id",
					"mRender" : function(data, type, full) {
						return "<input type=\"checkbox\" value=\""+data+"\">";
					},
					"bSortable" : false,
					"bSearchable" : false
				},
				{
					"mData" : "name",
					"mRender" : function(data, type, full) {
						return "<a href=\"#\" class=\"link_status\" onclick=\"getDevice('"
								+ full.id + "')\">" + data + "</a>";
					},
					"bSortable" : false,
					"bSearchable" : true
				},

				{
					"mData" : "users",
					"bSortable" : false,
					"bSearchable" : false
				}, {
					"mData" : "os",
					"mRender" : function(value, type, full) {
						if (value == 0) {
							return "Android";
						} else if (value == 1) {
							return "IOS";
						} else if (value == 2) {
							return "Windows";
						}
					},
					"bSortable" : true,
					"bSearchable" : false
				}, {
					"mData" : "type",
					"mRender" : function(value, type, full) {
						if (value == 0) {
							return "个人";
						} else if (value == 1) {
							return "企业";
						} else if (value == 2) {
							return "共用";
						}
					},
					"bSortable" : false,
					"bSearchable" : false
				},

				{
					"mData" : "remark",
					"bSortable" : false,
					"bSearchable" : false
				} ]
	};
	var activateDeviceTable = null;
	function searchActivateDeviceTable(o) {
		OSettings = activateDeviceTable.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		activateDeviceTable._fnFilterComplete(OSettings, {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		});

	}
	activateDeviceTableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "rest/device/activate/pages",
		"aaSorting" : [ [ 0, "asc" ] ],
		"aLengthMenu" : [ [ 10, 20, 50 ], [ 10, 20, 50 ] ],
		"sDom" : "rt<'box-content'<'col-sm-12'<'tool_box_pageInfo' ilp>> <'clearfix'>>",
		"sPaginationType" : "bootstrap",
		"oLanguage" : {
			"sSearch" : ""
		},
		"aoColumns" : [
				{
					"mData" : "id",
					"mRender" : function(data, type, full) {
						return "<input type=\"checkbox\" value=\""+data+"\">";
					},
					"bSortable" : false,
					"bSearchable" : false
				},
				{
					"mData" : "name",
					"mRender" : function(data, type, full) {
						return "<a href=\"#\" class=\"link_status\" onclick=\"getDevice('"
								+ full.id + "')\">" + data + "</a>";
					},
					"bSortable" : false,
					"bSearchable" : true
				},

				{
					"mData" : "users",
					"bSortable" : false,
					"bSearchable" : false
				}, {
					"mData" : "os",
					"mRender" : function(value, type, full) {
						if (value == 0) {
							return "Android";
						} else if (value == 1) {
							return "IOS";
						} else if (value == 2) {
							return "Windows";
						}
					},
					"bSortable" : true,
					"bSearchable" : false
				}, {
					"mData" : "type",
					"mRender" : function(value, type, full) {
						if (value == 0) {
							return "个人";
						} else if (value == 1) {
							return "企业";
						} else if (value == 2) {
							return "共用";
						}
					},
					"bSortable" : false,
					"bSearchable" : false
				}, {
					"mData" : "online",
					"mRender" : function(value, type, full) {
						if (value) {
							return "<p  style='color: blue'>在线</p>";
						} else {
							return "<p>离线</p>";
						}
					},
					"bSortable" : false,
					"bSearchable" : false
				} ]
	};
	var cancelDeviceTable = null;
	function searchCancelDeviceTable(o) {
		OSettings = cancelDeviceTable.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		cancelDeviceTable._fnFilterComplete(OSettings, {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		});

	}
	cancelDeviceTableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "rest/device/cancel/pages",
		"aaSorting" : [ [ 0, "asc" ] ],
		"aLengthMenu" : [ [ 10, 20, 50 ], [ 10, 20, 50 ] ],
		"sDom" : "rt<'box-content'<'col-sm-12'<'tool_box_pageInfo' ilp>> <'clearfix'>>",
		"sPaginationType" : "bootstrap",
		"oLanguage" : {
			"sSearch" : ""
		},
		"aoColumns" : [
				{
					"mData" : "id",
					"mRender" : function(data, type, full) {
						return "<input type=\"checkbox\" value=\""+data+"\">";
					},
					"bSortable" : false,
					"bSearchable" : false
				},
				{
					"mData" : "name",
					"mRender" : function(data, type, full) {
						return "<a href=\"#\" class=\"link_status\" onclick=\"getDevice('"
								+ full.id + "')\">" + data + "</a>";
					},
					"bSortable" : false,
					"bSearchable" : true
				},

				{
					"mData" : "users",
					"bSortable" : false,
					"bSearchable" : false
				}, {
					"mData" : "os",
					"mRender" : function(value, type, full) {
						if (value == 0) {
							return "Android";
						} else if (value == 1) {
							return "IOS";
						} else if (value == 2) {
							return "Windows";
						}
					},
					"bSortable" : true,
					"bSearchable" : false
				}, {
					"mData" : "type",
					"mRender" : function(value, type, full) {
						if (value == 0) {
							return "个人";
						} else if (value == 1) {
							return "企业";
						} else if (value == 2) {
							return "共用";
						}
					},
					"bSortable" : false,
					"bSearchable" : false
				},

				{
					"mData" : "remark",
					"bSortable" : false,
					"bSearchable" : false
				} ]
	};
	function deleteDevice(o) {
		var ids = getSelectedIds(o);
		if (ids.length == 0) {
			alert("请选择待删除的设备！");
			return;
		}
		confirm("确定要删除选中的设备吗？",function(){
			$.ajax({
				url : "rest/device/delete",
				type : "POST",
				async : true,
				data : {
					ids : ids
				},
				success : function(p_resultValue) {
					freshPage(denyDeviceTable, {});
					freshPage(cancelDeviceTable, {});
				},
				error : function(p_request, p_status, p_err) {
				}
			});
		});
	}

	function AllTables() {
		registerDeviceTable = $("#registerDeviceList").dataTable(
				registerDeviceTableSetting);
		denyDeviceTable = $("#denyDeviceList")
				.dataTable(denyDeviceTableSetting);
		activateDeviceTable = $("#activateDeviceList").dataTable(
				activateDeviceTableSetting);
		cancelDeviceTable = $("#cancelDeviceList").dataTable(
				cancelDeviceTableSetting);
		LoadSelect2Script(MakeSelect);
	}

	function activateDevice(o) {
		var ids = getSelectedIds(o);
		if (ids.length == 0) {
			alert("请选择待激活的设备！");
			return;
		}
		confirm("确定要激活选中的设备吗？",function(){
			$.ajax({
				url : "rest/device/activate",
				type : "POST",
				async : true,
				data : {
					ids : ids
				},
				success : function(p_resultValue) {
					freshPage(registerDeviceTable, {});
					freshPage(activateDeviceTable, {});
					freshPage(denyDeviceTable, {});
					freshPage(cancelDeviceTable, {});
				},
				error : function(p_request, p_status, p_err) {
				}
			});
		});
	}
	function denyDevice(o) {
		var ids = getSelectedIds(o);
		if (ids.length == 0) {
			alert("请选择待驳回的设备！");
			return;
		}
		confirm("确定要驳回选中的设备注册申请吗？",function(){
			OpenBigDialogWithConfirm3("驳回设备注册申请", 'console/devices_remark',
					'devices_remark', true, true, doDenyDevice, ids, "");
		});
	}
	function doDenyDevice(ids, remark) {
		var successful = false;
		$.ajax({
			url : "rest/device/deny",
			type : "POST",
			async : false,
			data : {
				ids : ids,
				remark : $('#remark').val()
			},
			success : function(p_resultValue) {
				freshPage(registerDeviceTable, {});
				freshPage(denyDeviceTable, {});
				successful = true;
			},
			error : function(p_request, p_status, p_err) {
				successful = false;
			}
		});
		return successful;
	}

	function cancelDevice(o) {
		var ids = getSelectedIds(o);
		if (ids.length == 0) {
			alert("请选择待注销的设备！");
			return;
		}
		
	confirm("确定要注销选中的设备吗？", function() {
			OpenBigDialogWithConfirm3("注销设备", 'console/devices_remark',
					'devices_remark', true, true, doCancelDevice, ids, "");
		});
	}

	function doCancelDevice(ids, remark) {
		var successful = false;
		$.ajax({
			url : "rest/device/cancel",
			type : "POST",
			async : false,
			data : {
				ids : ids,
				remark : $('#remark').val()
			},
			success : function(p_resultValue) {
				freshPage(activateDeviceTable, {});
				freshPage(cancelDeviceTable, {});
				successful = true;
			},
			error : function(p_request, p_status, p_err) {
				successful = false;
			}
		});
		return successful;
	}

	$(document).ready(function() {
		id = null;
		ids = [];
		LoadDataTablesScripts(AllTables);
		var ides = new Array();
		$("a.link_dash").click(function() {
			if (ides != null && ides.length == 0) {
				$("a.link_dash").each(function() {
					ides.push($(this).attr("id"));
				});
			}

			pageid = $(this).attr("id");
			for ( var i in ides) {
				$("#dashboard-" + ides[i]).hide();
			}
			$(this).closest('ul').find('li').each(function() {
				$(this).removeClass('active');
			});
			$("#dashboard-" + pageid).show();
			$("#" + pageid).parent('li').addClass('active');
		});
	});
</script>