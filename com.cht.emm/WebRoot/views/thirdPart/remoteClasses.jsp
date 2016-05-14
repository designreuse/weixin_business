<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div class="col-xs-12">
		<div class="box no-drop">
			<div class="box-header">
				<div class="box-name subTitle">
					<i class="fa fa-user"></i> <span>验证类列表</span>
				</div>
				<div class="box-icons"></div>
				<div class="no-move"></div>
			</div>
			<div class="box-content" >

				<div class="row">
					<div class="col-xs-10">
						<ul class="operate">
							<li class="tool_box_left"><a href="javascript:createRemoteClass();"
								title="新增验证类"> <i class="fa fa-plus-square"></i><span class="hidden-xs">新增验证类</span> </a>
							</li>
						</ul>
					</div>
					<div class="col-xs-2 input-group searchInput">
						<input type="text" class="form-control" placeholder="搜索"> <span
							class="input-group-addon" onClick="search(this)"><i
							class="fa fa-search"></i> </span>
					</div>
				</div>
			</div>
			<div class="box-content no-padding table-responsive">

				<table
					class="table table-bordered table-striped table-hover table-heading table-datatable"
					id="remoteClassTable">
					<thead>
						<tr>
							<th><input type="checkbox" class="selectAll"
								onclick="selectAll(this)"></th>
							<th>类名</th>
							<th>描述</th>							 
							<th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	// Run Datables plugin and create 3 variants of settings
	var remoteClassTable = null;
	function search(o) {
		OSettings = remoteClassTable.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		remoteClassTable._fnFilterComplete(OSettings, {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		});

	}

	tableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "rest/thirdpart/remote/pages",
		"aaSorting" : [ [ 3, "asc" ] ],
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
						//return data;
						return "<input type=\"checkbox\" value=\""+data+"\">";
					},
					"bSortable" : false,
					"bSearchable" : false
				},
				{
					"mData" : "className",
					"bSortable" : true,
					"bSearchable" : true
				},
				{
					"mData" : "classDesc",
					"bSortable" : false
				},
				{
					"mData" : null,
					"mRender" : function(data, type, row) {
						return "<a class=\"link_status op\" onclick=\"editRemoteClass('"
								+ row.id + "')\">编辑</a>";
					},
					"bSearchable" : false,
					"bSortable" : false
				}

		]
	};

	// 加载 用户组
	function loadRemoteClasses() {
		remoteClassTable = $("#remoteClassTable").dataTable(tableSetting);
		LoadSelect2Script(MakeSelect);
	}

	$(document).ready(function() {
		LoadDataTablesScripts(loadRemoteClasses);
	});

	function createRemoteClass() {
		OpenBigDialogWithConfirm("创建远程验证类", 'console/thirdpart/remote/create',
				'create_remote_class', true, true, afterCreateRemoteClass);
	}

	function editRemoteClass(id) {
		OpenBigDialogWithConfirm("编辑类", 'console/thirdpart/remote/edit?id=' + id,
				'edit_remote_class', true, true, afterEditRemoteClass);
	}

	function afterCreateRemoteClass() {
		checkResult = false;
		$("#form_create_remoteClass").bootstrapValidator('validate');
		var result = $("#form_create_remoteClass").data("bootstrapValidator")
				.isValid();
		if (result) {
		    var compiled = $("#compilePass").val();
		   // debugger
		    if(compiled!="0"){
		    name = $("#create_remote_class").find("input[name='className']").first().val();
			packageName = getSelectValByName("form_create_remoteClass","packageName");
			desc = $("#create_remote_class").find("input[name='classDesc']").first().val();
			content = javaEditor.getValue();
			 $("#create_remote_class").find("input[name='enabled']").each(function(){
			 	if (this.checked==true) {
			 		enabled=1;
			 	}
			 });
			$.ajaxSettings.async = false;
			$.ajax({
				url : "rest/thirdpart/remote/save",
				data : {
					"className" : name,
					"classDesc" : desc,
					"packageName":packageName,
					"content" : content,
					"enabled":enabled
				},
				type : "post",
				success : function(result) {
					if (result.successful == true) {
						freshPage(remoteClassTable, {});
						checkResult = true;
					} else {
						alert("操作失败，请稍后再试");
					}
				}
			});
		    
		    }else{
		    	alert("请事先编译");
		    }
			

		}
		return checkResult;

	}

	function afterEditRemoteClass() {
		checkResult = false;
		$("#form_edit_remoteClass").bootstrapValidator('validate');
		var result = $("#form_edit_remoteClass").data("bootstrapValidator").isValid();
		if (result) {
			id = $("#edit_remote_class").find("input[name='id']").first().val();
			desc = $("#edit_remote_class").find("input[name='classDesc']").first().val();
			//console.log("descp :" + descp);
			content = javaEditor.getValue();
			//debugger;
			 $("#edit_remote_class").find("input[name='enabled']").each(function(){
			 	console.log($(this).checked);
			 	console.log(this.checked)
			 	if (this.checked==true) {
			 		enabled=1;
			 	}
			 });
			 
			$.ajaxSettings.async = false;
			$.ajax({
				url : "rest/thirdpart/remote/update",
				data : {
					"id" : id,
					"className" : name,
					"classDesc" : desc,
					"content" : content,
					"enabled":enabled
				},
				type : "post",
				success : function(result) {
					if (result.successful == true) {
						freshPage(remoteClassTable, {});
						checkResult = true;
					} else {
						alert("操作失败，请稍后再试");
					}
				}
			});
		}
		return checkResult;
	}
</script>