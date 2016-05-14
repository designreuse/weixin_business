<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal" var="authentication" />
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">系统配置</a>
			</li>
			<li><a href="#">平台客户端维护</a>
			</li>
		</ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="box no-drop">
			<div class="box-header">
				<div class="box-name">
					<span>平台客户端维护/更新日志</span>
				</div>
				<div class="box-icons"></div>
				<div class="no-move"></div>
			</div>
			<div class="box-content" style="margin-bottom: 0.5em">
				<div class="row">
					<div class="col-xs-10">
						<ul class="operate">
							<li class="tool_box_left"><a
								href="javascript:publishAgent();" title="新增平台客户端"> <i
									class="fa fa-plus-square"></i><span class="hidden-xs">新增平台客户端</span>
							</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="box-content no-padding table-responsive">

				<table
					class="table table-bordered table-striped table-hover table-heading table-datatable"
					id="appList">
					<thead>
						<tr>
							<th><input type="checkbox" class="selectAll"
								onclick="selectAll(this)">
							</th>
							<th>图标</th>
							<th>发布者</th>
							<th>版本</th>
							<th>平台</th>
							<th>发布时间</th>
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
var agentTable = null;
var userId = '${authentication.userId}';
 
tableSetting = {
	"bprocessing" : true,
	"bServerSide" : true,
	"sAjaxSource" : "rest/sysConfig/platform_agent/page",
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
			{"mData" : "iconUrl",
				"mRender" : function(data, type, full) {
					return "<img src=\"${pageContext.request.contextPath}/"+data+"\" class='app_icon'>";
				},
				"bSortable" : false,
				"bSearchable" : false
			},
			{
				"mData" : "createUser",
			 	"bSortable" : false,
			 	"bSearchable" : false
			},
			{"mData" : "versionName",
				"bSortable" : false,
				"bSearchable" : false},
			{"mData" : "os",
					"bSortable" : false,
					"bSearchable" : false},
			{"mData" : "createTime",
				"bSortable" : false,
				"bSearchable" : false
			},
			{
				"mData" : "url",
				"mRender" : function(data, type, row) {
					return  "<a href=\"${pageContext.request.contextPath}/"+data+"\">下载</a>";
				},
				"bSearchable" : false,
				"bSortable" : false
			}

	]
};
function publishAgent() {
	id = "";
	//TODO 添加取消按钮和关闭按钮的点击事件，对已上传的文件进行清理
	OpenBigDialogWithConfirm2("更新客户端", 'console/platform_agent/update', 'agent_new', true, true, createAgent);
}

function createAgent() {
	var successful = false;
	//debugger;
	$("#form_create_agent").bootstrapValidator('validate');
	var result = $("#form_create_agent").data("bootstrapValidator")
			.isValid();
	if (result) {
		if (!iconPath) {
			alert("未上传应用图标！");
			return false;
		}
		 
		if (!filePath) {
			alert("未上传应用程序文件！");
			return false;
		}
	 
		var os = $("#agent_new").find("select[id='os']").find("option:selected")
			.val();
	
		var version_name = "";
		var version_code = "";
	 
		version_code = $("#agent_new").find("input[id='version_code']").first()
			.val();
		if (!version_code) {
			alert("应用版本号不能为空！");
			return false;
		}
		version_name = $("#agent_new").find("input[id='version_name']").first()
			.val();
		if (!version_name) {
			alert("应用版本名称不能为空！");
			return false;
		} 
 
    
		var url = "";
		if(filePath){
			url = filePath;
		}
   		var user="${authentication.userId}";
   		$.ajaxSettings.async = false;	
		$.ajax({
			url : "rest/sysConfig/platform_agent/update",
			type : "post",
			data : {
				"icon":iconUrl,
				"path":filePath,
				"versionName":version_name,
				"versionCode":version_code,
				"publisher":user,
				"pkgName":pkgName,
				"os":os,
				"url":url
			},
			success : function(p_resultValue) {
				if(!p_resultValue.successful){
					alert(p_resultValue.resultMessage);
					return;
				}
				freshPage(appTable,{});
				successful = true;
			},
			error : function(p_request, p_status, p_err) {
				successful = false;
			}
		});
	}
	 
	return successful;
}
 
function loadAppTypes() {
	appTable = $("#appList").dataTable(tableSetting);
	LoadSelect2Script(MakeSelect);
}
$(document).ready(function() {
	LoadDataTablesScripts(loadAppTypes);
});
</script>
