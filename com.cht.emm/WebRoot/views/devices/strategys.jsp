<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal" var="authentication" />
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">设备管理</a>
			</li>
			<li><a href="#">策略管理</a>
			</li>
		</ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="box no-drop">
			<div class="box-header">
				<div class="box-name">
					<span><a href="#">设备管理</a>/<a href="#">策略管理</a>
					</span>
				</div>
				<div class="box-icons"></div>
				<div class="no-move"></div>
			</div>
			<div class="box-content" style="margin-bottom: 0.5em">
				<div class="row">
					<div class="col-xs-10s">
						<ul class="operate">
							<li class="tool_box_left"><a
								href="javascript:createStrategy();" title="添加策略"> <i
									class="fa fa-plus-square"></i><span class="hidden-xs">添加策略</span>
							</a>
							</li>
							<li class="tool_box_left"><a href="#"
								onClick="deleteStrategy(this)" title="删除策略" class="deleteIcon">
									<i class="fa fa-minus-square"></i>删除策略 </a>
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
					id="strategyList">
					<thead>
						<tr>
							<th><input type="checkbox" class="selectAll"
								onclick="selectAll(this)">
							</th>
							<th>策略名称</th>
							<th>策略描述</th>
							<th>创建者</th>
							<th>创建时间</th>
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
	var strategyTable = null;
	function search(o) {
		OSettings = strategyTable.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		strategyTable._fnFilterComplete(OSettings, {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		});

	}
	tableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "rest/device/strategy/all/pages",
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
						return "<a href=\"#\" class=\"link_status\" onclick=\"editStrategy('"
								+ full.id + "')\">" + data + "</a>";
					},
					"bSortable" : false,
					"bSearchable" : true
				},

				{
					"mData" : "desc",
					"bSortable" : false,
					"bSearchable" : false
				},
				{
					"mData" : "creator",
					"bSortable" : false,
					"bSearchable" : false
				},
				{
					"mData" : "time",
					"bSortable" : true,
					"bSearchable" : false
				},
				{
					"mData" : null,
					"mRender" : function(data, type, row) {
						return "<a href=\"#\" class=\"link_status op\" onclick=\"editStrategy('"
								+ row.id + "')\">编辑</a>";
					},
					"bSearchable" : false,
					"bSortable" : false
				}

		]
	};

	function createStrategy() {
		id = "";
		OpenBigDialogWithConfirm2("创建策略", 'console/strategys_new',
				'strategys_new', true, true, addStrategy);
	}

	function addStrategy() {
		var successful = false;
		$('#form_create_strategy').bootstrapValidator('validate');
		var result = $("#form_create_strategy").data("bootstrapValidator")
				.isValid();
		if (result) {
			var name = $("#name").val();
			if (!name) {
				alert("策略名称不能为空！");
				return false;
			}
			var breakout = $("#breakout").find("option:selected").val();
			var appList = $("#appList").find("option:selected").val();
			var password = $("#password").find("option:selected").val();
			var osVersion = $("#osVersion").find("option:selected").val();
			var encrypt = $("#encrypt").find("option:selected").val();
			if (breakout == -1 && appList == -1 && password == -1
					&& osVersion == -1 && encrypt == -1) {
				alert("策略内容不能为空！");
				return false;
			}
			var devices = $("#users")[0].selectedOptions;
			var deviceList = [];
			for ( var i = 0; i < devices.length; i++) {
				deviceList.push(devices[i].value);
			}
			var desc = $("#desc").val();
			var user = "${authentication.userId}";
			var json = "{\"name\":\"" + name + "\",\"desc\":\"" + desc
					+ "\",\"users\":\"" + deviceList.join(",")
					+ "\",\"creator\":\"" + user + "\",\"breakout\":\""
					+ breakout + "\",\"appList\":\"" + appList
					+ "\",\"password\":\"" + password + "\",\"osVersion\":\""
					+ osVersion + "\",\"encrypt\":\"" + encrypt
					+ "\",\"condition\":\""
					+ $("#condition").find("option:selected").val()
					+ "\",\"operation\":\""
					+ $("#operation").find("option:selected").val() + "\"}";

			$.ajax({
				url : "rest/device/strategy/add",
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
					freshPage(strategyTable, {});
					successful = true;
				},
				error : function(p_request, p_status, p_err) {
					successful = false;
				}
			});
		}
		return successful;
	}
	function editStrategy(strategyId) {
		id = strategyId;
		OpenBigDialogWithConfirm2("策略详情", 'console/strategys_new',
				'strategys_new', true, true, updateStrategy);
	}

	function updateStrategy() {
		var successful = false;
		$('#form_create_strategy').bootstrapValidator('validate');
		var result = $("#form_create_strategy").data("bootstrapValidator")
				.isValid();
		if (result) {
			var name = $("#name").val();
			if (!name) {
				alert("策略名称不能为空！");
				return false;
			}
			var breakout = $("#breakout").find("option:selected").val();
			var appList = $("#appList").find("option:selected").val();
			var password = $("#password").find("option:selected").val();
			var osVersion = $("#osVersion").find("option:selected").val();
			var encrypt = $("#encrypt").find("option:selected").val();
			if (breakout == -1 && appList == -1 && password == -1
					&& osVersion == -1 && encrypt == -1) {
				alert("策略内容不能为空！");
				return false;
			}
			var devices = $("#users")[0].selectedOptions;
			var deviceList = [];
			for ( var i = 0; i < devices.length; i++) {
				deviceList.push(devices[i].value);
			}

			var desc = $("#desc").val();
			// TODO 未对编辑策略的用户权限进行控制
			var user = "${authentication.userId}";
			var json = "{\"name\":\"" + name + "\",\"desc\":\"" + desc
					+ "\",\"users\":\"" + deviceList.join(",") + "\",\"id\":\""
					+ id + "\",\"creator\":\"" + user + "\",\"breakout\":\""
					+ breakout + "\",\"appList\":\"" + appList
					+ "\",\"password\":\"" + password + "\",\"osVersion\":\""
					+ osVersion + "\",\"encrypt\":\"" + encrypt
					+ "\",\"condition\":\""
					+ $("#condition").find("option:selected").val()
					+ "\",\"operation\":\""
					+ $("#operation").find("option:selected").val() + "\"}";

			$.ajax({
				url : "rest/device/strategy/add",
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
					freshPage(strategyTable, {});
					successful = true;
				},
				error : function(p_request, p_status, p_err) {
					successful = false;
				}
			});
		}
		return successful;
	}
	function deleteStrategy(o) {
		var ids = getSelectedIds(o);
		if (ids.length == 0) {
			alert("请选择待删除的策略！");
			return;
		}
		confirm("确定要删除选中的策略吗？[默认策略]无法删除", function() {
			$.ajax({
				url : "rest/device/strategy/delete",
				type : "POST",
				async : true,
				data : {
					ids : ids
				},
				success : function(p_resultValue) {
					freshPage(strategyTable, {});
				},
				error : function(p_request, p_status, p_err) {
				}
			});
		});
	}
	function loadAppTypes() {
		strategyTable = $("#strategyList").dataTable(tableSetting);
		LoadSelect2Script(MakeSelect);
	}
	$(document).ready(function() {
		LoadDataTablesScripts(loadAppTypes);
	});
</script>
