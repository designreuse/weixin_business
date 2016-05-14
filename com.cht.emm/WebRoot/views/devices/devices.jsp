<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">设备管理</a>
			</li>
			<li><a href="#">设备列表</a>
			</li>
		</ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="box no-drop">
			<div class="box-header">
				<div class="box-name">
					<i class="fa fa-mobile"></i> <span>设备列表</span>
				</div>
				<div class="box-icons"></div>
				<div class="no-move"></div>
			</div>
			<div class="box-content" style="margin-bottom: 0.5em">
				<div class="row">
					<div class="col-xs-10">
						<ul class="operate">
							<li class="tool_box_left"><a
								href="javascript:createDevice();" title="添加设备"> <i
									class="fa fa-plus-square"></i><span class="hidden-xs">添加设备</span>
							</a>
							</li>
							<li class="tool_box_left"><a href="#"
								onClick="deleteDevice(this)" title="删除设备" class="deleteIcon">
									<i class="fa fa-minus-square"></i>删除设备 </a>
							</li>
						</ul>
					</div>
					<div class="col-xs-2 input-group searchInput">
						<input type="text" class="form-control"> <span
							class="input-group-addon" onClick="search(this)"><i
							class="fa fa-search"></i>
						</span>
					</div>
				</div>
			</div>
			<div class="box-content no-padding table-responsive">

				<table
					class="table table-bordered table-striped table-hover table-heading table-datatable"
					id="deviceList">
					<thead>
						<tr>
							<th><input type="checkbox" class="selectAll"
								onclick="selectAll(this)">
							</th>
							<th>设备型号</th>
							<th>用户</th>
							<th>操作系统</th>
							<th>设备类型</th>
							<th>生命周期</th>
							<th>是否在线</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var deviceTable = null;
	function search(o) {
		OSettings = deviceTable.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		deviceTable._fnFilterComplete(OSettings, {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		});

	}
	tableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "rest/device/all/pages",
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
						return "<a href=\"#\" class=\"link_status\" onclick=\"getDeviceDetails('"
								+ full.id + "')\">" + data + "</a>";
					},
					"bSortable" : false,
					"bSearchable" : true
				},

				{
					"mData" : "users",
					"bSortable" : false,
					"bSearchable" : false
				},
				{
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
				},
				{
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
					"mData" : "status",
					"mRender" : function(value, type, full) {
						if (value == 0) {
							return "注册";
						} else if (value == 2) {
							return "驳回";
						} else if (value == 1) {
							return "激活";
						} else if (value == 3) {
							return "注销";
						}
					},
					"bSortable" : false,
					"bSearchable" : false
				},
				{
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
				},
				{
					"mData" : null,
					"mRender" : function(data, type, row) {
						return "<div class=\"tb-opt-box\" onclick=\"showOps(this)\"><img style=\"cursor: pointer;\" src=\"resources/img/icons/devrule.png\" >"+
				"<div class=\"tb-opt-main\" ><img style=\"right:-22px;top:-24px;position: absolute;width:9px;height:8px;\" src=\"resources/img/icons/oper-arrow.png\">"
				+"<a href=\"javascript:sendMessageTo('"+row.id+"')\"> <img src=\"resources/img/icons/Mail-16x16.png\">发送消息</a><a href=\"javascript:editDevice('"+row.id+"')\"><img src=\"resources/img/icons/Edit-16x16.png\">编辑</a> </div></div>";
					},
					"bSearchable" : false,
					"bSortable" : false
				}

		]
	};
	function getDeviceDetails(deviceId) {
		id = deviceId;
		OpenBigDialog("设备详情", "console/devices_detail", 'devices_detail', true,
				true);
	}
	function createDevice() {
		id = "";
		OpenBigDialogWithConfirm2("添加设备", 'console/devices_new', 'devices_new',
				true, true, addDevice);
	}

	function addDevice() {
		var successful = false;
		$('#form_create_device').bootstrapValidator('validate');
		var result = $("#form_create_device").data("bootstrapValidator")
				.isValid();
		if (result) {
			var id = $("#id").val();
			if (!id) {
				alert("设备码不能为空！");
				return false;
			}
			var name = $("#name").val();
			if (!name) {
				alert("设备型号不能为空！");
				return false;
			}
			var os = $("#os").find("option:selected").val();
			if (!os) {
				alert("操作系统不能为空！");
				return false;
			}
			var type = $("#type").find("option:selected").val();
			if (!type) {
				alert("设备类型不能为空！");
				return false;
			}
			var users = $("#users")[0].selectedOptions;
			var userList = [];
			for ( var i = 0; i < users.length; i++) {
				userList.push(users[i].value);
			}
			var json = "{\"id\":\"" + id + "\",\"users\":\""
					+ userList.join(",") + "\",\"name\":\"" + name
					+ "\",\"os\":\"" + os + "\",\"type\":\"" + type
					+ "\",\"status\":\"" + 0 + "\"}";
			$.ajax({
				url : "rest/device/add",
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
					freshPage(deviceTable, {});
					successful = true;
				},
				error : function(p_request, p_status, p_err) {
					successful = false;
				}
			});
		}
		return successful;
	}
	function editDevice(deviceId) {
		id = deviceId;
		OpenBigDialogWithConfirm2("编辑设备", 'console/devices_new', 'devices_new',
				true, true, updateDevice);
	}

	function updateDevice() {
		var successful = false;
		$('#form_create_device').bootstrapValidator('validate');
		var result = $("#form_create_device").data("bootstrapValidator")
				.isValid();
		if (result) {
			var id = $("#id").val();
			if (!id) {
				alert("设备码不能为空！");
				return false;
			}
			var name = $("#name").val();
			if (!name) {
				alert("设备型号不能为空！");
				return false;
			}
			var os = $("#os").find("option:selected").val();
			if (!os) {
				alert("操作系统不能为空！");
				return false;
			}
			var type = $("#type").find("option:selected").val();
			if (!type) {
				alert("设备类型不能为空！");
				return false;
			}
			var users = $("#users")[0].selectedOptions;
			var userList = [];
			for ( var i = 0; i < users.length; i++) {
				userList.push(users[i].value);
			}
			var json = "{\"id\":\"" + id + "\",\"users\":\""
					+ userList.join(",") + "\",\"name\":\"" + name
					+ "\",\"os\":\"" + os + "\",\"type\":\"" + type
					+ "\",\"status\":\"" + status + "\"}";
			$.ajax({
				url : "rest/device/add",
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
					freshPage(deviceTable, {});
					successful = true;
				},
				error : function(p_request, p_status, p_err) {
					successful = false;
				}
			});
		}
		return successful;
	}
	function deleteDevice(o) {
		var ids = getSelectedIds(o);
		if (ids.length == 0) {
			alert("请选择待删除的设备！");
			return;
		}
		confirm("确定要删除选中的设备吗？", function() {
			$.ajax({
				url : "rest/device/delete",
				type : "POST",
				async : true,
				data : {
					ids : ids
				},
				success : function(p_resultValue) {
					freshPage(deviceTable, {});
				},
				error : function(p_request, p_status, p_err) {
				}
			});
		});
	}
	function loadAppTypes() {
		deviceTable = $("#deviceList").dataTable(tableSetting);
		LoadSelect2Script(MakeSelect);
	}
	
	function sendMessageTo(deviceId){
		openMiddleDialogWithConfirm("发送消息给设备","console/device/msg?deviceId="+deviceId,"sendMessageDiv",sendMessage);
	}
	function sendMessage(){
		result = false;
		deviceId = getValueByName("sendMessageDiv","deviceId");
		msgContent = $("#sendMessageDiv").find("textarea[id=mscontent]").val();
		debugger;
		if(msgContent ==''){
			alert("消息不能为空！");
			return false;
		}
		$.ajaxSettings.async=false;
		
		$.ajax({
			url:"rest/device/sendMsg",
			type:"post",
			data:{"deviceID":deviceId,
				  "content":msgContent
				},
			success:function(json){
				if(json.successful){
					result = true;
					alert("消息发送成功！");
				}else{
					alert("消息发送失败："+json.resultMessage);
				}
			}
		});
		$.ajaxSettings.async=true;
		return result;
	}
	
	$(document).ready(function() {
		LoadDataTablesScripts(loadAppTypes);
	});
	
	
</script>
