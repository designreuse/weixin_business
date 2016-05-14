<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal" var="authentication" />
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">设备管理</a></li>
			<li><a href="#">配置管理</a></li>
		</ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="box no-drop">
			<div class="box-header">
				<div class="box-name">
					<i class="fa fa-cog"></i> <span>设备管理/配置管理</span>
				</div>
				<div class="box-icons"></div>
				<div class="no-move"></div>
			</div>
			<div class="box-content" style="margin-bottom: 0.5em">
				<div class="row">
					<div class="col-xs-10">
						<ul class="operate">
							<li class="tool_box_left"><a
								href="javascript:createConfig();" title="添加配置"> <i
									class="fa fa-plus-square"></i><span class="hidden-xs">添加配置</span>
							</a></li>
							<li class="tool_box_left"><a href="#"
								onClick="deleteConfig(this)" title="删除配置" class="deleteIcon">
									<i class="fa fa-minus-square"></i>删除配置 </a></li>
						</ul>
					</div>
					<div class="col-xs-2 input-group searchInput">
						<input type="text" class="form-control"> <span
							class="input-group-addon" onClick="search(this)"><i
							class="fa fa-search"></i> </span>
					</div>
				</div>
			</div>
			<div class="box-content no-padding table-responsive">

				<table
					class="table table-bordered table-striped table-hover table-heading table-datatable"
					id="configList">
					<thead>
						<tr>
							<th><input type="checkbox" class="selectAll"
								onclick="selectAll(this)"></th>
							<th>配置名称</th>
							<th>配置描述</th>
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
var configTable = null;
function search(o) {
	OSettings = configTable.DataTable.models.oSettings;
	var oPreviousSearch = OSettings.oPreviousSearch;
	val = $(o).closest("div").find("input[type=text]").first().val();

	OSettings.oPreviousSearch = {
		"sSearch" : val,
		"bRegex" : false,
		"bSmart" : true,
		"bCaseInsensitive" : true
	};
	OSettings.sSearch = val;
	configTable._fnFilterComplete(OSettings, {
		"sSearch" : val,
		"bRegex" : false,
		"bSmart" : true,
		"bCaseInsensitive" : true
	});

}
tableSetting = {
	"bprocessing" : true,
	"bServerSide" : true,
	"sAjaxSource" : "rest/device/config/all/pages",
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
			{"mData" : "name",
				"mRender" : function(data, type, full) {
					return "<a href=\"#\" class=\"link_status\" onclick=\"editConfig('"
					+ full.id + "')\">"+data+"</a>";
				},
				"bSortable" : false,
				"bSearchable" : true
			},
			
			{"mData" : "desc",
				"bSortable" : false,
				"bSearchable" : false},
			{"mData" : "creator",
				"bSortable" : false,
				"bSearchable" : false
			},
			{"mData" : "time",
				"bSortable" : true,
				"bSearchable" : false
			},
			{
				"mData" : null,
				"mRender" : function(data, type, row) {
					return "<a href=\"#\" class=\"link_status op\" onclick=\"editConfig('"
					+ row.id + "')\">编辑</a>";
				},
				"bSearchable" : false,
				"bSortable" : false
			}

	]
};
function createConfig() {
	id="";
	OpenBigDialogWithConfirm2("创建配置", 'console/configs_new', 'configs_new', true, true, addConfig);
}

function addConfig() {
	var successful = false;
	$('#form_create_config').bootstrapValidator('validate');
	var result = $("#form_create_config").data("bootstrapValidator")
			.isValid();
	if (result) {
	var config = false;
	var name = $("#name").val();
	if (!name) {
		alert("配置名称不能为空！");
		return false;
	}
	var desc = $("#desc").val();
	var devices = $("#users")[0].selectedOptions;
	var deviceList = [];
	for ( var i = 0; i < devices.length; i++) {
		deviceList.push(devices[i].value);
	}
	var pwd_intensity = $("#pwd_intensity").find("option:selected").val();
	if(pwd_intensity!=-1){
		config = true;
	}
	var pwd_length_min = $("#pwd_length_min").val();

	if (pwd_length_min) {
		if (isNaN(pwd_length_min)) {
			alert("密码最小长度必须为数字！");
			return false;
		}
		if (pwd_length_min < 0 || pwd_length_min > 20) {
			alert("密码最小长度必须在0-20之间！");
			return false;
		}
	}else{
		pwd_length_min=-1;
	}
	if(pwd_length_min!=-1){
		config = true;
	}
	var pwd_time_max = $("#pwd_time_max").val();
	if (pwd_time_max) {
		if (isNaN(pwd_time_max)) {
			alert("密码最大输入次数必须为数字！");
			return false;
		}
		if (pwd_time_max < 1 || pwd_time_max > 20) {
			alert("密码最大输入次数必须在1-20之间！");
			return false;
		}
	}else{
		pwd_time_max=-1;
	}
	if(pwd_time_max!=-1){
		config = true;
	}
	var data_encrypt = $("#data_encrypt").find("option:selected").val();
	if(data_encrypt!=-1){
		config = true;
	}
	var audit = $("#audit").find("option:selected").val();
	if(audit!=-1){
		config = true;
	}
	var bluetooth = $("#bluetooth").find("option:selected").val();
	if(bluetooth!=-1){
		config = true;
	}
	var camera = $("#camera").find("option:selected").val();
	if(camera!=-1){
		config = true;
	}
	var wifies = "";
	dataTable = $('#datatable-5').dataTable();
	nNodes = dataTable.fnGetNodes( );
	for(var i=0; i<nNodes.length; i++){
		if(wifies){
			wifies += ",";
		}
		wifies += "{\"name\":\"" + nNodes[i].children[1].innerHTML+ "\",\"password\":\"" + nNodes[i].children[2].innerHTML + "\"}";
	}
	if(wifies){
		config = true;
	}
	var vpns = "";
	var dataTable = $('#datatable-6').dataTable();
	var nNodes = dataTable.fnGetNodes( );
	for(var i=0; i<nNodes.length; i++){
		if(vpns){
			vpns += ",";
		}
		vpns += "{\"name\":\"" + nNodes[i].children[1].innerHTML+ "\",\"password\":\"" + nNodes[i].children[2].innerHTML + "\"}";
	}
	if(vpns){
		config = true;
	}
	
	var apps = $("#apps")[0].selectedOptions;
	var appList = [];
	for ( var i = 0; i < apps.length; i++) {
		appList.push(apps[i].value);
	}
	var app_list_type = $("#app_list_type").find("option:selected").val();
	if (appList.length > 0) {
		if (app_list_type==-1) {
			alert("名单类型不能为空！");
			return false;
		}
	}
	if(app_list_type!=-1){
		config = true;
	}
	if(!config){
		alert("配置内容不能为空！");
		return false;
	}
	var user="${authentication.userId}";	
	var json = "{\"name\":\"" + name + "\",\"apps\":\"" + appList.join(",")
			+ "\",\"users\":\"" + deviceList.join(",")  + "\",\"creator\":\"" + user+ "\",\"pwd_intensity\":\"" + pwd_intensity
			+ "\",\"pwd_length_min\":\"" + pwd_length_min
			+ "\",\"pwd_time_max\":\"" + pwd_time_max + "\",\"desc\":\"" + desc
			+ "\",\"data_encrypt\":\"" + data_encrypt
			+ "\",\"audit\":\"" + audit
			+ "\",\"bluetooth\":\""+ bluetooth
			+ "\",\"camera\":\"" + camera
			+ "\",\"wifies\":[" + wifies+ "],\"vpns\":[" + vpns
			+ "],\"app_list_type\":\"" + app_list_type + "\"}";

	$.ajax({
		url : "rest/device/config/add",
		type : "POST",
		contentType : 'application/json',
		processData : false,
		dataType : 'json',
		async : false,
		data : json,
		success : function(p_resultValue) {
			if(!p_resultValue.successful){
				alert(p_resultValue.resultMessage);
				return;
			}
			freshPage(configTable,{});
			successful = true;
		},
		error : function(p_request, p_status, p_err) {
			successful = false;
		}
	});
	}
	return successful;
}

function editConfig(configId){
	id=configId;
	OpenBigDialogWithConfirm2("配置详情", 'console/configs_new', 'configs_new', true, true, updateConfig);
}
function updateConfig() {
	var successful = false;
	$('#form_create_config').bootstrapValidator('validate');
	var result = $("#form_create_config").data("bootstrapValidator")
			.isValid();
	if (result) {
	var config = false;
	var name = $("#name").val();
	if (!name) {
		alert("配置名称不能为空！");
		return false;
	}
	var desc = $("#desc").val();
	var devices = $("#users")[0].selectedOptions;
	var deviceList = [];
	for ( var i = 0; i < devices.length; i++) {
		deviceList.push(devices[i].value);
	}
	var pwd_intensity = $("#pwd_intensity").find("option:selected").val();
	if(pwd_intensity!=-1){
		config = true;
	}
	var pwd_length_min = $("#pwd_length_min").val();

	if (pwd_length_min) {
		if (isNaN(pwd_length_min)) {
			alert("密码最小长度必须为数字！");
			return false;
		}
		if (pwd_length_min < 0 || pwd_length_min > 20) {
			alert("密码最小长度必须在0-20之间！");
			return false;
		}
	}else{
		pwd_length_min=-1;
	}
	if(pwd_length_min!=-1){
		config = true;
	}
	var pwd_time_max = $("#pwd_time_max").val();
	if (pwd_time_max) {
		if (isNaN(pwd_time_max)) {
			alert("密码最大输入次数必须为数字！");
			return false;
		}
		if (pwd_time_max < 1 || pwd_time_max > 20) {
			alert("密码最大输入次数必须在1-20之间！");
			return false;
		}
	}else{
		pwd_time_max=-1;
	}
	if(pwd_time_max!=-1){
		config = true;
	}
	var data_encrypt = $("#data_encrypt").find("option:selected").val();
	if(data_encrypt!=-1){
		config = true;
	}
	var audit = $("#audit").find("option:selected").val();
	if(audit!=-1){
		config = true;
	}
	var bluetooth = $("#bluetooth").find("option:selected").val();
	if(bluetooth!=-1){
		config = true;
	}
	var camera = $("#camera").find("option:selected").val();
	if(camera!=-1){
		config = true;
	}
	var wifies = "";
	dataTable = $('#datatable-5').dataTable();
	nNodes = dataTable.fnGetNodes( );
	for(var i=0; i<nNodes.length; i++){
		if(wifies){
			wifies += ",";
		}
		wifies += "{\"name\":\"" + nNodes[i].children[1].innerHTML+ "\",\"password\":\"" + nNodes[i].children[2].innerHTML + "\"}";
	}
	if(wifies){
		config = true;
	}
	var vpns = "";
	var dataTable = $('#datatable-6').dataTable();
	var nNodes = dataTable.fnGetNodes( );
	for(var i=0; i<nNodes.length; i++){
		if(vpns){
			vpns += ",";
		}
		vpns += "{\"name\":\"" + nNodes[i].children[1].innerHTML+ "\",\"password\":\"" + nNodes[i].children[2].innerHTML + "\"}";
	}
	if(vpns){
		config = true;
	}
	
	var apps = $("#apps")[0].selectedOptions;
	var appList = [];
	for ( var i = 0; i < apps.length; i++) {
		appList.push(apps[i].value);
	}
	var app_list_type = $("#app_list_type").find("option:selected").val();
	if (appList.length > 0) {
		if (app_list_type==-1) {
			alert("名单类型不能为空！");
			return false;
		}
	}
	if(app_list_type!=-1){
		config = true;
	}
	if(!config){
		alert("配置内容不能为空！");
		return false;
	}
	// TODO 未对编辑配置的用户权限进行控制
	var user="${authentication.userId}";	
	var json = "{\"name\":\"" + name + "\",\"id\":\"" + id+ "\",\"apps\":\"" + appList.join(",")
			+ "\",\"users\":\"" + deviceList.join(",")  + "\",\"pwd_intensity\":\"" + pwd_intensity
			+ "\",\"pwd_length_min\":\"" + pwd_length_min
			+ "\",\"pwd_time_max\":\"" + pwd_time_max + "\",\"desc\":\"" + desc
			+ "\",\"data_encrypt\":\"" + data_encrypt
			+ "\",\"audit\":\"" + audit+ "\",\"creator\":\"" + user
			+ "\",\"bluetooth\":\""+ bluetooth
			+ "\",\"camera\":\"" + camera
			+ "\",\"wifies\":[" + wifies+ "],\"vpns\":[" + vpns
			+ "],\"app_list_type\":\"" + app_list_type + "\"}";

	$
			.ajax({
				url : "rest/device/config/add",
				type : "POST",
				contentType : 'application/json',
				processData : false,
				dataType : 'json',
				async : false,
				data : json,
				success : function(p_resultValue) {
					if(!p_resultValue.successful){
						alert(p_resultValue.resultMessage);
						return;
					}
					freshPage(configTable,{});
					successful = true;
				},
				error : function(p_request, p_status, p_err) {
					successful = false;
				}
			});
	}
	return successful;

}

function deleteConfig(o){
	var ids = getSelectedIds(o);
	if(ids.length==0){
		alert("请选择待删除的配置！");return;
	}
	confirm("确定要删除选中的配置吗？[默认配置]无法删除",function(){
		$.ajax({
			url : "rest/device/config/delete",
			type : "POST",
			async : true,
			data: {ids: ids},
			success : function(p_resultValue) {
				freshPage(configTable,{});
			},
			error : function(p_request, p_status, p_err) {
			}
		});
	});
}
function loadAppTypes() {
	configTable = $("#configList").dataTable(tableSetting);
	LoadSelect2Script(MakeSelect);
}

$(document).ready(function() {
	LoadDataTablesScripts(loadAppTypes);
});
</script>
