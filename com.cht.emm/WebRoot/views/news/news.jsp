<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">资讯管理</a></li>
			<li><a href="#">资讯列表</a></li>
		</ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="box no-drop">
			<div class="box-header">
				<div class="box-name">
					</i> <span>资讯管理/资讯列表</span>
				</div>
				<div class="box-icons"></div>
				<div class="no-move"></div>
			</div>
			<div class="box-content" style="margin-bottom: 0.5em">
				<div class="row">
					<div class="col-xs-10">
						<ul class="operate">

							<li class="tool_box_left"><a
								href="javascript:publishNews();" title="添加资讯"> <i
									class="fa fa-plus-square"></i><span class="hidden-xs">添加资讯</span>
							</a></li>
							<li class="tool_box_left"><a href="#"
								onClick="deleteNews(this)" title="删除资讯" class="deleteIcon">
									<i class="fa fa-minus-square"></i><span class="hidden-xs">删除资讯</span>
							</a></li>
						</ul>
					</div>
					<div class="col-xs-2 input-group searchInput">
						<input type="text" class="form-control" placeholder="搜索">
						<span class="input-group-addon" onClick="search(this)"><i
							class="fa fa-search"></i> </span>
					</div>
				</div>
			</div>
			<div class="box-content no-padding table-responsive">

				<table
					class="table table-bordered table-striped table-hover table-heading table-datatable"
					id="newsList">
					<thead>
						<tr>
							<th><input type="checkbox" class="selectAll"
								onclick="selectAll(this)"></th>
							<th>标题</th>
							<th>作者</th>
							<th>时间</th>
							<th>标记</th>
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
var newsTable = null;
function search(o) {
	OSettings = newsTable.DataTable.models.oSettings;
	var oPreviousSearch = OSettings.oPreviousSearch;
	val = $(o).closest("div").find("input[type=text]").first().val();

	OSettings.oPreviousSearch = {
		"sSearch" : val,
		"bRegex" : false,
		"bSmart" : true,
		"bCaseInsensitive" : true
	};
	OSettings.sSearch = val;
	newsTable._fnFilterComplete(OSettings, {
		"sSearch" : val,
		"bRegex" : false,
		"bSmart" : true,
		"bCaseInsensitive" : true
	});

}
tableSetting = {
	"bprocessing" : true,
	"bServerSide" : true,
	"sAjaxSource" : "rest/news/all/pages",
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
			{"mData" : "title",
				"mRender" : function(data, type, full) {
					return "<a href=\"#\" class=\"link_status\" onclick=\"editNews('"
					+ full.id + "')\">"+data+"</a>";
				},
				"bSortable" : false,
				"bSearchable" : true
			},
			
			{"mData" : "editor",
				"bSortable" : false,
				"bSearchable" : false},
			{"mData" : "news_time",
					"bSortable" : true,
					"bSearchable" : false},
			{"mData" : "mark",
				"mRender" : function(data, type, full) {
					if(data==0){
						return "推荐";
					}else if(data==1){
						return "热门";
					}else if(data==2){
						return "首发";
					}else if(data==3){
						return "独家";
					}else if(data==4){
						return "收藏";
					}else{
						return "";
					}
				},
				"bSortable" : false,
				"bSearchable" : false
			},
			{
				"mData" : null,
				"mRender" : function(data, type, row) {
					return "<a href=\"#\" class=\"link_status op\" onclick=\"editNews('"
					+ row.id + "')\">编辑</a>";
				},
				"bSearchable" : false,
				"bSortable" : false
			}

	]
};
function publishNews() {
	id = "";
	//TODO 添加取消按钮和关闭按钮的点击事件，对已上传的文件进行清理
	OpenBigDialogWithConfirm2("发布资讯", 'console/news_new', 'news_new', true,
			true, createNews);
}
function createNews() {
	var successful = false;
	$('#form_create_news').bootstrapValidator('validate');
	var result = $("#form_create_news").data("bootstrapValidator")
			.isValid();
	if (result) {
	var title = $("#title").val();
	if (!title) {
		alert("资讯标题不能为空！");
		return false;
	}
	var editor = $("#editor").val();
	if (!editor) {
		alert("资讯作者不能为空！");
		return false;
	}
	var local = $("#local").val();
	var mark = $("#mark").val();
	var type = $("#news_new").find("input[id='type']")[0].checked;
	var screenshot = "";
	if (type) {
		type = 1;
		screenshot = document
		.getElementById('bigImg').src;
		if(screenshot.indexOf("resources/img/upload_pic.png") > -1){
			alert("资讯图片不能为空！");
			return false;
		}
	} else {
		type = 0;
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
	}
	var body = CKEDITOR.instances.wysiwig_simple.getData().replace(/[\r\n]/g,
			"").replace(/\"/g, "'").replace(/>\s+</g, "><");
	if (!body) {
		alert("资讯详情不能为空！");
		return false;
	}
	/*
	var screenshot = "";
	 var len = keys.length;     
    for(var i=0;i<len;i++){     
        var k = keys[i];     
        if(i>0){screenshot = screenshot+",";}  
        screenshot = screenshot+urlValues[k];
    } */
	var json = "{\"title\":\"" + title + "\",\"pics\":\"" + screenshot
	+ "\",\"editor\":\"" + editor + "\",\"local\":\""
	+ local+ "\",\"mark\":\"" + mark + "\",\"type\":\"" + type + "\",\"body\":\"" + body + "\"}";
	$.ajax({
		url : "rest/news/add",
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
			freshPage(newsTable,{});
			successful = true;
		},
		error : function(p_request, p_status, p_err) {
			successful = false;
		}
	});
	}
	return successful;
}
function editNews(news_id) {
	id = news_id;
	OpenBigDialogWithConfirm2("编辑资讯", 'console/news_new', 'news_new', true,
			true, updateNews);
}
function updateNews() {
	var successful = false;
	$('#form_create_news').bootstrapValidator('validate');
	var result = $("#form_create_news").data("bootstrapValidator")
			.isValid();
	if (result) {
	var title = $("#title").val();
	if (!title) {
		alert("资讯标题不能为空！");
		return false;
	}
	var editor = $("#editor").val();
	if (!editor) {
		alert("资讯作者不能为空！");
		return false;
	}
	var local = $("#local").val();
	var mark = $("#mark").val();
	var type = $("#news_new").find("input[id='type']")[0].checked;
	var screenshot = "";
	if (type) {
		type = 1;
		screenshot = document
		.getElementById('bigImg').src;
		if(screenshot.indexOf("resources/img/upload_pic.png") > -1){
			alert("资讯图片不能为空！");
			return false;
		}
	} else {
		type = 0;
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
	}
	var body = CKEDITOR.instances.wysiwig_simple.getData().replace(/[\r\n]/g,
			"").replace(/\"/g, "'").replace(/>\s+</g, "><");
	if (!body) {
		alert("资讯详情不能为空！");
		return false;
	}
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
	var json = "{\"title\":\"" + title + "\",\"url\":\"" + url + "\",\"id\":\""
			+ id + "\",\"pics\":\"" + screenshot
			+ "\",\"editor\":\"" + editor + "\",\"local\":\""
			+ local+ "\",\"mark\":\"" + mark + "\",\"type\":\"" + type + "\",\"body\":\"" + body + "\"}";
	$
			.ajax({
				url : "rest/news/add",
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
					freshPage(newsTable,{});
					successful = true;
				},
				error : function(p_request, p_status, p_err) {
					successful = false;
				}
			});
	}
	return successful;
}


function deleteNews(o){
	var ids = getSelectedIds(o);
	if(ids.length==0){
		alert("请选择待删除的资讯！");return;
	}
	confirm("确定要删除选中的资讯吗？",function(){
		$.ajax({
			url : "rest/news/delete",
			type : "POST",
			async : true,
			data: {ids: ids},
			success : function(p_resultValue) {
				freshPage(newsTable,{});
			},
			error : function(p_request, p_status, p_err) {
			}
		});
	});
}
function loadAppTypes() {
	newsTable = $("#newsList").dataTable(tableSetting);
	LoadSelect2Script(MakeSelect);
}
$(document).ready(function() {
	LoadDataTablesScripts(loadAppTypes);
});
</script>