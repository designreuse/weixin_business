<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal" var="authentication" />
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">权限管理</a></li>
			<li><a href="#">用户管理</a></li>
		</ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="box no-drop">
			<div class="box-header">
				<div class="box-name subTitle">
					<span>第三方接入管理/第三方列表</span>
				</div>
				<div class="box-icons"></div>
				<div class="no-move"></div>
			</div>
			<div class="box-content">
				<div class="row">
					<div class="col-xs-10">
						<ul class="operate">
							<li class="tool_box_left"><a
								href="javascript:createNewConfig();" title="新增第三方接入"> <i
									class="fa fa-plus-square"></i><span class="hidden-xs">新增第三方接入</span>
							</a></li>
							<li class="tool_box_left"><a href="#"
								onClick="deleteConfig(this)" title="删除第三方接入" class="deleteIcon">
									<i class="fa fa-minus-square"></i><span class="hidden-xs">删除第三方接入</span>
							</a></li>
							<li class="tool_box_left"><a href="#"
								onClick="manageRemoteClass()" title="管理验证类" class="deleteIcon">
									<i class="fa fa-minus-square"></i><span class="hidden-xs">管理验证类</span>
							</a></li>
							<li class="tool_box_left"><a href="#"
								onClick="manageAccess()" title="设置接入方式" class="deleteIcon">
									<i class="fa fa-minus-square"></i><span class="hidden-xs">设置接入方式</span>
							</a></li>
						</ul>
					</div>
					<div class="col-xs-2 input-group searchInput">
						<input type="text" class="form-control" placeholder="搜索">
						<span class="input-group-addon" onClick="search(this)"><i
							class="fa fa-search"></i> </span>
					</div>
				</div>
				<div class="row">
					<table
						class="table table-bordered table-striped table-hover table-heading table-datatable"
						id="config_list_table">
						<thead>
							<tr>
								<th><input type="checkbox" class="selectAll"
									onclick="selectAll(this)">
								</th>
								<th>名称</th>
								<th>验证地址</th>
								<th>处理类</th>
								<th>对应组</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var configListTable = null;
	var targetUrl = 'rest/thirdpart/pages';
	tableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : targetUrl,
		"aaSorting" : [ [ 0, "asc" ] ],
		"aLengthMenu" : [ [ 10, 20, 50 ], [ 10, 20, 50 ] ],
		"sDom" : "rt<'box-content'<'col-sm-12'<'tool_box_pageInfo' ilp>> <'clearfix'>>",
		"sPaginationType" : "bootstrap",
		"oLanguage" : {
			"sSearch" : ""
		}
	};
	tableSetting.aoColumns = [
			{
				"mData" : "id",
				"mRender" : function(data, type, full) {
					// return data;
					var disabled = false;
					if (full.isSystem == 1) {
						disabled = true;
					}
					return "<input type=\"checkbox\" value=\"" + data + "\" "
							+ (disabled ? 'disabled' : '') + ">";
				},
				"bSortable" : false,
				"bSearchable" : false
			},
			{
				"mData" : "name",
				"mRender" : function(data, type, full) {
					return "<a href=\"javascript:getConfigInfo('" + full.id
							+ "')\">" + data + "</a>";
				}
			},
			{
				"mData" : "remoteUrl",
				"mRender" : function(data, type, full) {
					if (data.length > 20) {
						return data.substr(0, 18) + "...";
					} else {
						return data;
					}
				}
			},
			{
				"mData" : "className",
				"bSearchable" : false,
				"bSortable" : false,
				"sClass" : "hidden-xs"
			},
			{
				"mData" : "groupName",
				"bSearchable" : false,
				"bSortable" : false,
				"sClass" : "hidden-xs"
			},
			{
				"mData" : null,
				"mRender" : function(data, type, row) {
					console.log("is System: " + row.isSystem);
					var click = 'notAllowed()';
					if (row.isSystem != 1) {
						click = "editConfigInfo('" + row.id + "')";
					}
					return "<a href=\"#\" class=\"link_status op\" onclick=\""
							+ click + "\">编辑</a>";
				},
				"bSearchable" : false,
				"bSortable" : false
			}

	];

	function loadConfig() {
		configListTable = $("#config_list_table").dataTable(tableSetting);
	}

	$(document).ready(function() {
		LoadDataTablesScripts(loadConfig);
	});

	function createNewConfig() {
		OpenBigDialogWithConfirm("创建第三方接入平台", 'console/thirdpart/create',
				'create_thirdpart', true, true, afterCreateThirdPart);
	}

	function afterCreateThirdPart() {
		checkResult = false;
		$("#form_create_config").bootstrapValidator('validate');
		checkResult = $("#form_create_config").data("bootstrapValidator").isValid();
		if(checkResult){
			name = getValueByName('create_thirdpart', "thirdPartName");
			remoteUrl = getValueByName('create_thirdpart', "remoteUrl");
			className = getSelectValByName('create_thirdpart', "className");
			groupName = getValueByName('create_thirdpart', "groupName");
			other = getValueByName('create_thirdpart', "others");
			//debugger;
			$.ajaxSettings.async = false;
			$.ajax({
				url : "rest/thirdpart/save",
				data : {
					"name" : name,
					"remoteUrl" : remoteUrl,
					"className" : className,
					"other" : other,
					"groupName" : groupName
				},
				type : "post",
				success : function(data) {
					if (data.successful == true) {
						freshPage(configListTable, {});
						checkResult = true;
					}
				}
			});
		}
		return checkResult;
	}
	
	function getConfigInfo(id){
		OpenBigDialog("详情第三方接入平台", 'console/thirdpart/info?id='+id,
				'thirdpart_info', true, true);
	}
	
	function editConfigInfo(id){
		OpenBigDialogWithConfirm("详情第三方接入平台", 'console/thirdpart/edit?id='+id,
				'thirdpart_edit', true, true,afterEditThirdPart);
	}
	
	function afterEditThirdPart(){
		checkResult = false;
		$("#form_edit_config").bootstrapValidator('validate');
		checkResult = $("#form_edit_config").data("bootstrapValidator").isValid();
		if(checkResult){
			id = getValueByName('form_edit_config', "id");
			remoteUrl = getValueByName('form_edit_config', "remoteUrl");
			className = getSelectValByName('form_edit_config', "className");
			//groupName = getValueByName('form_edit_config', "groupName");
			other = getValueByName('form_edit_config', "other");
			//debugger;
			$.ajaxSettings.async = false;
			$.ajax({
				url : "rest/thirdpart/edit",
				data : {
					"id":id,
					"remoteUrl" : remoteUrl,
					"className" : className,
					"other" : other
				},
				type : "POST",
				success : function(data) {
					if (data.successful == true) {
						freshPage(configListTable, {});
						checkResult = true;
					}
				}
			});
		}
		return checkResult;
	}
	
	function deleteConfig(o){
		var ids = getSelectedIds(o);
		if (ids.length == 0) {
			alert("请选择要删除的第三方接入配置？");
		} else {
			confirm("确定要删除？", function() {
				$.ajax({
					url : "rest/deleteEntity",
					data : {
						"type" : "thirdpart",
						"ids" : ids
					},
					type : "get",
					success : function(result) {
						if (result.successful == true) {
							deleteCheckedRows(configListTable);
						} else {
							alert("删除失败，请通知管理员");
						}
					}
				});
			});
		}
	
	}
	
	
	function manageRemoteClass(){
		OpenBigDialog("详情第三方接入平台", 'console/thirdpart/remote/list',
				'remoteClass_list', true, true);
	} 
	
	
	
	function manageAccess(){
		OpenBigDialogWithConfirm("设置接入方式管理", 'console/thirdpart/accessManage',
				'thirdpart_manage', true, true,afterManageThirdPart);
	}
	
	function afterManageThirdPart(){
		accessId = $("#selectableValidator").val();
		result= false;
		$.ajaxSettings.async = false;
		$.ajax({
			url:"rest/thirdpart/access/save",
			type:"post",
			data:{"id":accessId},
			success:function(json){
				if(json.successful){
					alert("设置成功！");
					result=true;
				}else{
					alert("设置失败，请重试！");
				}
			}
		});
		return result;
	}
	
</script>


