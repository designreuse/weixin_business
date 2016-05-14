<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal" var="authentication" />
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">应用管理</a></li>
			<li><a href="#">应用列表</a></li>
		</ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="box no-drop">
			<div class="box-header">
				<div class="box-name">
					<span>应用管理/应用列表</span>
				</div>
				<div class="box-icons"></div>
				<div class="no-move"></div>
			</div>
			<div class="box-content" style="margin-bottom: 0.5em">
				<div class="row">
					<div class="col-xs-10">
						<ul class="operate">
							<li class="tool_box_left"><a href="javascript:publishApp();"
								title="新增应用"> <i class="fa fa-plus-square"></i><span
									class="hidden-xs">新增应用</span>
							</a></li>
							<li class="tool_box_left"><a href="#"
								onClick="deleteApp(this)" title="删除应用" class="deleteIcon"> <i
									class="fa fa-minus-square"></i><span class="hidden-xs">删除应用</span>
							</a></li>
							<li class="tool_box_left"><a href="#"
								onClick="disableApp(this)" title="停用应用" class="deleteIcon">
									<i class="fa fa-pause"></i><span class="hidden-xs">停用应用</span>
							</a></li>
							<li class="tool_box_left"><a href="#"
								onClick="enableApp(this)" title="启用应用" class="deleteIcon"> <i
									class="fa fa-play"></i><span class="hidden-xs">启用应用</span>
							</a></li>
						</ul>
					</div>
					<div class="col-xs-2 input-group searchInput">
						<input type="text" class="form-control" placeholder="搜索">
						<span class="input-group-addon" onClick="search(this)"><i
							class="fa fa-search"></i></span>
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
								onclick="selectAll(this)"></th>
							<th style="width: 20%">名称</th>
							<th>发布者</th>
							<th>版本</th>
							<th>下载次数</th>
							<th>平台</th>
							<th>发布时间</th>
							<th>状态</th>
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
var appTable = null;
var userId = '${authentication.userId}';
function search(o) {
	OSettings = appTable.DataTable.models.oSettings;
	var oPreviousSearch = OSettings.oPreviousSearch;
	val = $(o).closest("div").find("input[type=text]").first().val();

	OSettings.oPreviousSearch = {
		"sSearch" : val,
		"bRegex" : false,
		"bSmart" : true,
		"bCaseInsensitive" : true
	};
	OSettings.sSearch = val;
	appTable._fnFilterComplete(OSettings, {
		"sSearch" : val,
		"bRegex" : false,
		"bSmart" : true,
		"bCaseInsensitive" : true
	});

}
tableSetting = {
	"bprocessing" : true,
	"bServerSide" : true,
	"sAjaxSource" : "rest/app/all/pages?userId="+userId,
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
					return "<img src='"+full.icon+"'><a href=\"#\" class=\"link_status\" onclick=\"getAppDetails('"
					+ full.id + "','"+full.pkg_name+ "','"+full.kind+"')\">"+data+"</a>";
				}
			},
			
			{"mData" : "publisher",
				"bSortable" : false,
				"bSearchable" : false},
			{"mData" : "version_name",
					"bSortable" : false,
					"bSearchable" : false},
			{"mData" : "download_count",
					"bSortable" : false,
					"bSearchable" : false},
			{"mData" : "os",
						"bSortable" : false,
						"bSearchable" : false},
			{"mData" : "create",
				"bSortable" : false,
				"bSearchable" : false
			},
			{"mData" : "status",
				"mRender" : function(data, type, full) {
					return data==1?"停用":"启用";
				},
				"bSortable" : false,
				"bSearchable" : false
			},
			{
				"mData" : null,
				"mRender" : function(data, type, row) {
					return row.kind==0?"<div class=\"tb-opt-box\" onclick=\"showOps(this)\"><img style=\"cursor: pointer;\" src=\"resources/img/icons/devrule.png\" >"+
					"<div class=\"tb-opt-main\" ><img style=\"right:-22px;top:-24px;position: absolute;width:9px;height:8px;\" src=\"resources/img/icons/oper-arrow.png\">"
					+"<a href=\"javascript:editApp('"+row.id+"')\"> <img src=\"resources/img/icons/Edit-16x16.png\">编辑应用</a><a href=\"javascript:authApp('"+row.id+ "','" + row.name +"')\"><img src=\"resources/img/icons/Book-16x16.png\">应用授权</a><a href=\"javascript:upgradeApp('"+row.id +"')\"><img src=\"resources/img/icons/Documents-16x16.png\">应用升级</a></div></div></td>"
					:"<div class=\"tb-opt-box\" onclick=\"showOps(this)\"><img style=\"cursor: pointer;\" src=\"resources/img/icons/devrule.png\" >"+
					"<div class=\"tb-opt-main\" ><img style=\"right:-22px;top:-24px;position: absolute;width:9px;height:8px;\" src=\"resources/img/icons/oper-arrow.png\">"
					+"<a href=\"javascript:editApp('"+row.id+"')\"> <img src=\"resources/img/icons/Edit-16x16.png\">编辑应用</a><a href=\"javascript:authApp('"+row.id+ "','" + row.name +"')\"><img src=\"resources/img/icons/Book-16x16.png\">应用授权</a></div></div></td>"
					;
				},
				"bSearchable" : false,
				"bSortable" : false
			}

	]
};
function publishApp() {
	id = "";
	//TODO 添加取消按钮和关闭按钮的点击事件，对已上传的文件进行清理
	OpenBigDialogWithConfirm2("发布应用", 'console/apps_new', 'apps_new', true, true, createApp);
}
function createApp() {
	var successful = false;
	$('#form_create_app').bootstrapValidator('validate');
	var result = $("#form_create_app").data("bootstrapValidator")
			.isValid();
	if (result) {
	var name = $("#apps_new").find("input[id='name']").first().val();
	var kind = $("#apps_new").find("select[id='kind']").find("option:selected")
	.val();
	if (!iconPath) {
		alert("未上传应用图标！");
		return false;
	}
	if(kind==0 || kind==1){
	if (!filePath) {
		alert("未上传应用程序文件！");
		return false;
	}}
	var type = $("#apps_new").find("input[id='type']")[0].checked;
	if (type) {
		type = 1;
	} else {
		type = 0;
	}
	var category = $("#category")[0].selectedOptions;
	var categoryList = [];
	for ( var i = 0; i < category.length; i++) {
		categoryList.push(category[i].value);
	}
	var os = $("#apps_new").find("select[id='os']").find("option:selected")
			.val();
	
	var version_name = "";
	var version_code = "";
	if(kind==0){
		version_code = $("#apps_new").find("input[id='version_code']").first()
		.val();
	if (!version_code) {
		alert("应用版本号不能为空！");
		return false;
	}
	version_name = $("#version_name").val();
	if (!version_name) {
		alert("应用版本名称不能为空！");
		return false;
	}}
	/*
	var screenshot = "";
	var len = keys.length;     
    for(var i=0;i<len;i++){     
        var k = keys[i];     
        if(i>0){screenshot = screenshot+",";}  
        screenshot = screenshot+urlValues[k];
    }*/
    var screenshot = "";
	var screenshotLis = $("#appImageListPhone li img");
	var length = screenshotLis.length;
	for ( var i = 0; i < length; i++) {
		var src = screenshotLis[i].src;
		if (src.indexOf("resources/img/upload_pic.png") == -1) {
			if (i > 0) {
				screenshot = screenshot + ",";
			}
			screenshot = screenshot + src;
		}
	}
    var app_main = $("#app_main").val();
	var app_web = $("#app_web").val();
	if(kind==0){
		if (!pkgName) {
			alert("应用程序文件的包名不能为空！");
			return false;
		}
	}else if(kind==1){
		pkgName = app_main;
		if (!pkgName) {
			alert("应用首页地址不能为空！");
			return false;
		}
	}else if(kind==2){
		pkgName = app_web;
		if (!pkgName) {
			alert("应用网站地址不能为空！");
			return false;
		}
	}
	var url = "";
	if(filePath){
		url = filePath.replace(/\\/g, "\\\\");
	}
    var user="${authentication.userId}";	
	var json = "{\"name\":\"" + name + "\",\"icon\":\"" + iconUrl+ "\",\"screenshot\":\"" + screenshot
			+ "\",\"url\":\"" + url+ "\",\"kind\":\"" + kind 
			+ "\",\"type\":\"" + type + "\",\"category\":\"" + categoryList.join(",")
			+ "\",\"os\":\"" + os + "\",\"os_version\":\""
			+ $("#os_version").val() + "\",\"version_name\":\"" + version_name+ "\",\"publisher\":\"" + user 
			+ "\",\"version_code\":\"" + version_code + "\",\"pkg_name\":\""
			+ pkgName + "\",\"desc\":\"" + CKEDITOR.instances.wysiwig_simple.getData().replace(/[\r\n]/g,
			"").replace(/\"/g, "'").replace(/>\s+</g, "><") + "\"}";
	$.ajax({
		url : "rest/app/add",
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
function getAppDetails(appId, pkgName, kind) {
	id = appId;
	pkg_name = pkgName;
	if(kind==2){
		OpenBigDialog("应用详情", "console/apps_detail_web", 'apps_detail_web', true, true);
	}else if(kind==1){
		OpenBigDialog("应用详情", "console/apps_detail_zip", 'apps_detail_zip', true, true);
	}else{
		OpenBigDialog("应用详情", "console/apps_detail", 'apps_detail', true, true);
	}
}
function editApp(appId){
	id=appId;
	edit = true;
	OpenBigDialogWithConfirm2("编辑应用", 'console/apps_new', 'apps_new', true, true, updateApp);
}
function updateApp() {
	var successful = false;
	$('#form_create_app').bootstrapValidator('validate');
	var result = $("#form_create_app").data("bootstrapValidator")
			.isValid();
	if (result) {
	var name = $("#apps_new").find("input[id='name']").first().val();
	var kind = $("#apps_new").find("select[id='kind']").find("option:selected")
	.val();
	if (!iconUrl) {
		alert("未上传应用图标！");
		return false;
	}
	if(kind==0 || kind==1){
	if (!filePath) {
		alert("未上传应用程序文件！");
		return false;
	}}
	var type = $("#apps_new").find("input[id='type']")[0].checked;
	if (type) {
		type = 1;
	} else {
		type = 0;
	}
	
	var category = $("#category")[0].selectedOptions;
	var categoryList = [];
	for ( var i = 0; i < category.length; i++) {
		categoryList.push(category[i].value);
	}
	
	var os = $("#apps_new").find("select[id='os']").find("option:selected")
			.val();
	var version_name = "";
	var version_code = "";
	if(kind==0){
		version_code = $("#apps_new").find("input[id='version_code']").first()
		.val();
	if (!version_code) {
		alert("应用版本号不能为空！");
		return false;
	}
	version_name = $("#version_name").val();
	if (!version_name) {
		alert("应用版本名称不能为空！");
		return false;
	}}
	/*
	var newscreenshot = "";
	if(changeScreenshot){
		var len = keys.length;     
	     for(var i=0;i<len;i++){     
	         var k = keys[i];     
	         if(i>0){newscreenshot = newscreenshot+",";}  
	         newscreenshot = newscreenshot+urlValues[k];
	     }
	}else{
		newscreenshot = screenshot;
	}*/
	 var screenshot = "";
		var screenshotLis = $("#appImageListPhone li img");
		var length = screenshotLis.length;
		for ( var i = 0; i < length; i++) {
			var src = screenshotLis[i].src;
			if (src.indexOf("resources/img/upload_pic.png") == -1) {
				if (i > 0) {
					screenshot = screenshot + ",";
				}
				screenshot = screenshot + src;
			}
		}
	var app_main = $("#app_main").val();
	var app_web = $("#app_web").val();
	if(kind==0){
		if (!pkgName) {
			alert("应用程序文件的包名不能为空！");
			return false;
		}
	}else if(kind==1){
		pkgName = app_main;
		if (!pkgName) {
			alert("应用首页地址不能为空！");
			return false;
		}
	}else if(kind==2){
		pkgName = app_web;
		if (!pkgName) {
			alert("应用网站地址不能为空！");
			return false;
		}
	}
	var url = "";
	if(filePath){
		url = filePath.replace(/\\/g, "\\\\");
	}
	// TODO 未对编辑应用的用户权限进行控制
	var user="${authentication.userId}";	
	var json = "{\"name\":\"" + name + "\",\"publisher\":\""
			+ user + "\",\"screenshot\":\"" + screenshot+ "\",\"status\":\""
			+ appDetail.app.status + "\",\"create\":\"" + appDetail.app.create
			+ "\",\"download_count\":\"" + appDetail.app.download_count
			+ "\",\"score_count\":\"" + appDetail.app.score_count
			+ "\",\"score\":\"" + appDetail.app.score + "\",\"id\":\"" + id + "\",\"icon\":\""
			+ iconUrl + "\",\"url\":\"" + url
			+ "\",\"type\":\"" + type + "\",\"category\":\"" + categoryList.join(",")
			+ "\",\"os\":\"" + os + "\",\"kind\":\"" + kind + "\",\"os_version\":\""
			+ $("#os_version").val() + "\",\"version_name\":\"" + version_name
			+ "\",\"version_code\":\"" + version_code + "\",\"pkg_name\":\""
			+ pkgName + "\",\"desc\":\"" + CKEDITOR.instances.wysiwig_simple.getData().replace(/[\r\n]/g,
			"").replace(/\"/g, "'").replace(/>\s+</g, "><") + "\"}";
	$
			.ajax({
				url : "rest/app/add",
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
function upgradeApp(appId){
	id=appId;
	edit = false;
	OpenBigDialogWithConfirm2("升级应用", 'console/apps_new', 'apps_new', true, true, upgradeApplication);
}
function upgradeApplication(){
	var successful = false;
	
	if (!iconUrl) {
		alert("未上传应用图标！");
		return false;
	}
	if(kind==0 || kind==1){
	if (!filePath) {
		alert("未上传应用程序文件！");
		return false;
	}}
	var app_main = $("#app_main").val();
	var app_web = $("#app_web").val();
	if(kind==0){
		if (!pkgName) {
			alert("应用程序文件的包名不能为空！");
			return false;
		}
		if(pkgName!=oldPkgName){
			alert("应用程序ID不一致！");
			return false;
		}
	}else if(kind==1){
		pkgName = app_main;
		if (!pkgName) {
			alert("应用首页地址不能为空！");
			return false;
		}
	}else if(kind==2){
		pkgName = app_web;
		if (!pkgName) {
			alert("应用网站地址不能为空！");
			return false;
		}
	}
	var versionName = "";
	var versionCode = "";
	if(kind==0){
		versionCode = $("#apps_new").find("input[id='version_code']").first()
		.val();
	if (!versionCode) {
		alert("应用版本号不能为空！");
		return false;
	}
	versionName = $("#version_name").val();
	if (!versionName) {
		alert("应用版本名称不能为空！");
		return false;
	}
	if(version_code>=versionCode){
		alert("应用版本不能低于当前最新版本！");
		return false;
	}
	}
	
	var url = "";
	if(filePath){
		url = filePath.replace(/\\/g, "\\\\");
	}
	// TODO 未对编辑应用的用户权限进行控制
	var user="${authentication.userId}";	
	var json = "{\"id\":\"" + id + "\",\"publisher\":\""
			+ user + "\",\"icon\":\""
			+ iconUrl + "\",\"url\":\"" + url
			+ "\",\"version_name\":\"" + versionName
			+ "\",\"version_code\":\"" + versionCode + "\",\"pkg_name\":\""
			+ pkgName + "\",\"updateDesc\":\"" + CKEDITOR.instances.upgrade_desc.getData().replace(/[\r\n]/g,
			"").replace(/\"/g, "'").replace(/>\s+</g, "><") + "\"}";
	$
			.ajax({
				url : "rest/app/upgrade",
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
					freshPage(appTable,{});
					successful = true;
				},
				error : function(p_request, p_status, p_err) {
					successful = false;
				}
			});
	return successful;
}
function deleteApp(o){
	var ids = getSelectedIds(o);
	if(ids.length==0){
		alert("请选择待删除的应用！");return;
	}
	 confirm("确定要删除选中的应用吗？",function(){
		$.ajax({
			url : "rest/app/delete",
			type : "POST",
			async : true,
			data: {ids: ids},
			success : function(p_resultValue) {
				freshPage(appTable,{});
			},
			error : function(p_request, p_status, p_err) {
			}
		});
	});
}
function disableApp(o){
	var ids = getSelectedIds(o);
	if(ids.length==0){
		alert("请选择待停用的应用！");return;
	}
	confirm("确定要停用选中的应用吗？",function(){
		$.ajax({
			url : "rest/app/disable",
			type : "POST",
			async : true,
			data: {ids: ids},
			success : function(p_resultValue) {
				freshPage(appTable,{});
			},
			error : function(p_request, p_status, p_err) {
			}
		});
	});
}
function enableApp(o){
	var ids = getSelectedIds(o);
	if(ids.length==0){
		alert("请选择待启用的应用！");return;
	}
	confirm("确定要启用选中的应用吗？",function(){
		$.ajax({
			url : "rest/app/enable",
			type : "POST",
			async : true,
			data: {ids: ids},
			success : function(p_resultValue) {
				freshPage(appTable,{});
			},
			error : function(p_request, p_status, p_err) {
			}
		});
	});
}
function authApp(appId, appName){
	id=appId;
	name=appName;
	OpenBigDialog("应用授权", "console/apps_auth", 'apps_auth', true, true);
}
function loadAppTypes() {
	appTable = $("#appList").dataTable(tableSetting);
	LoadSelect2Script(MakeSelect);
}
$(document).ready(function() {
	LoadDataTablesScripts(loadAppTypes);
});
</script>
