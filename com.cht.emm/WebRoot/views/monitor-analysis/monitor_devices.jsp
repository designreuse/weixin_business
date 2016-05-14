<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">监控分析</a></li>
			<li><a href="#">设备监控</a></li>
		</ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="box no-drop">
			<div class="box-header">
				<div class="box-name">
					<i class="fa fa-mobile"></i>
					<span>设备列表</span>
				</div>
				<div class="no-move"></div>
			</div>
			<div class="box-content" style="margin-bottom: 0.5em">
				<div class="row">
					<div class="col-xs-8">
					</div>
					<div class="col-xs-4 input-group searchInput">
						<input type="text" class="form-control"> <span
							class="input-group-addon" onClick="search(this)"><i
							class="fa fa-search"></i></span>
					</div>
				</div>
			</div>
			<div class="box-content no-padding table-responsive">

				<table
					class="table table-bordered table-striped table-hover table-heading table-datatable"
					id="monitorDeviceList">
					<thead>
						<tr>
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
var monitorDeviceTable = null;
function search(o) {
	OSettings = monitorDeviceTable.DataTable.models.oSettings;
	var oPreviousSearch = OSettings.oPreviousSearch;
	val = $(o).closest("div").find("input[type=text]").first().val();

	OSettings.oPreviousSearch = {
		"sSearch" : val,
		"bRegex" : false,
		"bSmart" : true,
		"bCaseInsensitive" : true
	};
	OSettings.sSearch = val;
	monitorDeviceTable._fnFilterComplete(OSettings, {
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
					"mData" : "name",
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
						return "<a href=\"#\" class=\"link_status\" onclick=\"monitorDevice('"
								+ row.id + "')\">监控</a>";
					},
					"bSearchable" : false,
					"bSortable" : false
				}

	]
};
function monitorDevice(id) {
	//OpenBigDialog("设备监控", 'console/device_monitor?id='+id, 'device_monitor', true, true);
}

function loadAppTypes() {
	monitorDeviceTable = $("#monitorDeviceList").dataTable(tableSetting);
	LoadSelect2Script(MakeSelect);
}
$(document).ready(function() {
	LoadDataTablesScripts(loadAppTypes);
});
</script>
