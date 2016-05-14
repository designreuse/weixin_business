var color_cold = new Array("#179", "#28a", "#39b", "#4ac", "#5bd", "#6ce",
		"#7df", "#8f1", "#a12", "#b23");
var color_warm = new Array("#f00", "#e11", "#d22", "#c33", "#b44", "#a55",
		"#966", "#877", "#788", "#699");
$.urlParam = function(name) {
	var results = new RegExp('[\?&amp;]' + name + '=([^&amp;#]*)')
			.exec(window.location.href);
	return results[1] || 0;
};
// var window_height = null;
// var window_width = null;
$(document).ready(function() {
// window_height = $(window).height() * 0.9;
// window_width = $(window).width() * 0.8;
});

function window_height(){
	height = $(window).height();
	width = $(window).width();
	if(width >600){
		height = height*0.9;
	}
	return height;
}

function window_width(){
	width = $(window).width();
	if(width >600){
		width = width*0.8;
	}
	return width;
	
	
}
function OpenDialog(title, url, div_id, v_height, v_width, v_autoOpen, v_modal,
		v_dragable) {
	var div = $("<div id='" + div_id + "' title='" + title + "'></div>")
			.appendTo($("BODY"));

	$.get(url, {
		action : "get"
	}, function(data, textStutus) {
		$("#" + div_id + "").html(data);
	});

	div.css({
		"overflow-x" : "hidden",
		"overflow-y" : "auto"
	});
	div.dialog({
		autoOpen : v_autoOpen,
		width : v_width,
		height : v_height,
		modal : v_modal,
		resizable : false,
		bgiframe : true,
		draggable : false,
		position : {
			my : "top",
			at : "top",
			of : window
		},
		close : function(evt, ui) {
			div.dialog("destroy");
			div.html("").remove();
		}
	});
}
function OpenDialogWithConfirm(title, url, div_id, v_height, v_width,
		v_autoOpen, v_modal, v_dragable, confirmcall) {
	var div = $("<div id='" + div_id + "' title='" + title + "' class='openDialog'></div>")
			.appendTo($("BODY"));

	$.get(url, {
		action : "get"
	}, function(data, textStutus) {
		$("#" + div_id + "").html(data);
	});

	div.css({
		"overflow-x" : "hidden",
		"overflow-y" : "auto"
	});

	div.dialog({
		autoOpen : v_autoOpen,
		width : v_width,
		height : v_height,
		modal : v_modal,
		resizable : false,

		bgiframe : true,
		draggable : v_dragable,
		position : {
			my : "top",
			at : "top",
			of : window
		},
		close : function(evt, ui) {
			div.dialog("destroy");
			div.html("").remove();

		},
		buttons : {
			'确 定' : function() {
				$.ajaxSettings.async = false;
				var result = confirmcall();
				if(  result==true){
					$(this).dialog('close');
				}
			},
			'取 消' : function() {
				$(this).dialog('close');
			}
		}
	});
}

function OpenBigDialogWithConfirm2(title, url, div_id, v_autoOpen, v_modal,
		confirmcall) {
	var div = $("<div id='" + div_id + "' title='" + title + "' class='openDialog'></div>")
			.appendTo($("BODY"));

	$.get(url, {
		action : "get"
	}, function(data, textStutus) {
		$("#" + div_id + "").html(data);
	});

	div.css({
		"overflow-x" : "hidden",
		"overflow-y" : "auto"
	});

	div.dialog({
		autoOpen : v_autoOpen,
		width : window_width(),
		height : window_height(),
		modal : v_modal,
		resizable : false,

		bgiframe : true,
		draggable : false,
		position : {
			my : "top",
			at : "top",
			of : window
		},
		close : function(evt, ui) {
			div.dialog("destroy");
			div.html("").remove();

		},
		buttons : {
			'确 定' : function() {
				$.ajaxSettings.async = false;
				result = confirmcall();
				
				if (result==undefined || result==true) {
					$(this).dialog('close');
				}
			},
			'取 消' : function() {
				$(this).dialog('close');
			}
		}
	});

}
function OpenBigDialogWithConfirm3(title, url, div_id, v_autoOpen, v_modal,
		confirmcall, param1, param2) {
	var div = $("<div id='" + div_id + "' title='" + title + "' class='openDialog'></div>")
			.appendTo($("BODY"));

	$.get(url, {
		action : "get"
	}, function(data, textStutus) {
		$("#" + div_id + "").html(data);
	});

	div.css({
		"overflow-x" : "hidden",
		"overflow-y" : "auto"
	});

	div.dialog({
		autoOpen : v_autoOpen,
		width : window_width(),
		height : window_height(),
		modal : v_modal,
		resizable : false,
		bgiframe : true,
		draggable : false,
		position : {
			my : "top",
			at : "top",
			of : window
		},
		close : function(evt, ui) {
			div.dialog("destroy");
			div.html("").remove();

		},
		buttons : {
			'确 定' : function() {
				$.ajaxSettings.async = false;
				result = confirmcall((param1, param2));
				if ( result==undefined || result==true) {
					$(this).dialog('close');
				}
			},
			'取 消' : function() {
				$(this).dialog('close');
			}
		}
	});

}

function OpenBigDialog(title, url, div_id, v_auto_open, v_modal) {
	OpenDialog(title, url, div_id, window_height(), window_width(), v_auto_open,
			v_modal, false);
}
function OpenBigDialogWithConfirm(title, url, div_id, v_auto_open, v_modal,
		confirmcall) {
	OpenDialogWithConfirm(title, url, div_id, window_height(), window_width(),
			v_auto_open, v_modal, false, confirmcall);
}

function OpenSubDialog(parent_id, title, url, div_id, v_auto_open, v_modal) {
	OpenBigDialog(title, url, div_id, v_auto_open, v_modal);
}

function openDiv(id) {

	var divs = $(".div_views.active");
	divs.removeClass("active").addClass("inactive");
	$("#div_" + id).removeClass("inactive").addClass("active");

}

function selectUsers(type, id, call) {
	OpenBigDialogWithConfirm('选择用户', 'console/user/select?type=' + type
			+ "&id=" + id, 'select_user', true, false, call);
}

function selectResources(type, id, callback) {
	OpenBigDialogWithConfirm('选择资源', 'console/resource/select?type=' + type
			+ '&id=' + id, 'select_all_resource', true, false, callback);
}

function getAllGroup(type, id, callback) {
	OpenBigDialogWithConfirm('选择组', 'console/group/select?type=' + type
			+ '&id=' + id, 'select_group', true, false, callback);
}

function getSubGroup(id) {
	getAllGroup('group', id, updateSubGroup);
}

function getAllResourceRole(id) {
	OpenBigDialogWithConfirm('选择角色', 'console/role/select?type=resource'
			+ '&id=' + id, 'all_role', true, false, updateResourceRole);
}

function delArrayVal(array, val) {
	i = 0;
	for (; i < array.length; i++) {
		if (array[i] == val) {
			break;
		}
	}

	if (i < array.length) {
		array.splice(i, 1);
	}

}

function AllTables4() {
	table = TestTable4();
	LoadSelect2Script(MakeSelect2);

}
 

function loadUserInfo(id) {
	$.getJSON("data/userData.json", function(result) {
		userMap = result.usermap;
		user = usermap.get(id);
	});
}

// 保存子组
function updateSubGroup() {
	var checked = $("#selectGroupTable").find("input[type='checkbox']:checked").not(
			".selectAll");
	if (checked.length == 0) {
		alert("请选择至少一个用户组");
		return false;
	} else {
		var ids = '';
		$(checked).each(function() {
			if (ids != '') {
				ids += ',';
			}
			ids += $(this).val();
		});
		return saveSubGroup(groupId, ids);

	}
}

// 保存子组
function saveSubGroup(groupId, ids) {
	$.ajaxSettings.async = false;
	resultCheck =false;
	$.getJSON("rest/group/saveSubGroup", {
		"groupId" : groupId,
		"subIds" : ids
	}, function(result) {
		if (result.successful == true) {
			$("#subGroupsLS").empty();
			subs = result.resultValue;
			if (subs == null || subs.length == 0) {

			} else {
				$("#subGroupsLS").empty();
				for (i = 0; i < subs.length; i++) {
					$("#subGroupsLS").append(
							"<li><input type='checkbox'  value='" + subs[i].id
									+ "'>" + subs[i].groupName + "</li>");
				}
				// var
				// groupcount=parseInt($("#group_info_subgroup_count").text());
				$("#group_info_subgroup_count").text(subs.length);
			}
			resultCheck = true;
		} 
	});
	return resultCheck;
}

// 删除子组
function delSubGroup() {
	var checked = $("#subGroupsLS").find("input[type='checkbox']:checked").not(
			".selectAll");
	if (checked.length == 0) {
		alert("请选择至少一个子组");
		return false;
	} else {
		 confirm("确定要删除？",function(){
			var ids = '';
			delcount = 0;
			$(checked).each(function() {
				if (ids != '') {
					ids += ',';
				}
				ids += $(this).val();
				delcount++;
			});
			$.ajaxSettings.async = false;
			$.getJSON("rest/group/delSubGroup", {
				"groupId" : groupId,
				"subIds" : ids
			}, function(result) {
				if (result.successful == true) {
					$(checked).each(function() {
						$(this).parent().remove();

					});
					var count = parseInt($("#group_info_subgroup_count").text());
					$("#group_info_subgroup_count").text(count - delcount);
					return true;
				} else {
					return false;
				}
			});
		});
	}
}

function getAllAuth() {
	var array = new Array();
	$.ajaxSettings.async = false;
	$.getJSON("rest/auths/all", function(result) {
		var auths = result.resultValue;
		for (i = 0; i < auths.length; i++) {
			auth = new Array(auths[i].name, auths[i].id, '');
			array.push(auth);
		}

	});

	return array;
}

function OpenDialogWithClose(title, url, div_id, v_autoOpen, v_modal, closeCall) {
	var div = $("<div id='" + div_id + "' title='" + title + "'></div>")
			.appendTo($("BODY"));
	// var content = div.load(url, {});
	// $("#" + div_id + "").data("url", url);

	$.get(url, {
		action : "get"
	}, function(data, textStutus) {
// console.log(data);
		$("#" + div_id + "").html(data);
	});

	div.css({
		"overflow-x" : "hidden",
		"overflow-y" : "auto"
	});

	div.dialog({
		autoOpen : v_autoOpen,
		width : window_width,
		height : window_height,
		modal : v_modal,
		resizable : false,
		bgiframe : true,
		draggable : false,
		position : {
			my : "top",
			at : "top",
			of : window
		},
		close : function(evt, ui) {
			closeCall();
			div.dialog("destroy");
			div.html("").remove();
		}
	});

}

function deleteEntity(type, ids, afterDelete) {
	$.getJSON("user/rest/deleteEntity", {
		"type" : type,
		"ids" : ids
	}, function(result) {
		if (result.successful) {
			afterDelete();
		}
	});
}

function deleteUnionEntity(type, pid, sids, afterDelete) {
	$.getJSON("user/rest/deleteUnionEntity", {
		"type" : type,
		"pid" : pid,
		"sids" : sids
	}, function(result) {
		if (result.successful) {
			afterDelete();
		}
	});
}

function getStar(score) {
	var star = "";
	if (score < 10) {
		star = '<i class="fa fa-star-o"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i>';
	} else if (score < 20) {
		star = '<i class="fa fa-star-half-o"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i>';
	} else if (score < 30) {
		star = '<i class="fa fa-star"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i>';
	} else if (score < 40) {
		star = '<i class="fa fa-star"></i><i class="fa fa-star-half-o"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i>';
	} else if (score < 50) {
		star = '<i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i>';
	} else if (score < 60) {
		star = '<i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star-half-o"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i>';
	} else if (score < 70) {
		star = '<i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i>';
	} else if (score < 80) {
		star = '<i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star-half-o"></i><i class="fa fa-star-o"></i>';
	} else if (score < 90) {
		star = '<i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star-o"></i>';
	} else if (score < 100) {
		star = '<i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star-half-o"></i>';
	} else {
		star = '<i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i>';
	}
	return star;
}


function downloadApp() {
	$.getJSON("rest/app/download/" + id, function(result) {
		window.open(result.resultValue);
	});
}







function getSelectedIds(o) {
	ids = '';
	table = $(o).closest(".box").find("tbody").first();

	$(table).find("input[type=checkbox]:checked").not(".selectAll").each(
			function() {
				id = $(this).val();
				if (ids != '') {
					ids += ',';
				}
				ids += id;
			});
	return ids;
}

function selectAll(o) {
	val = $(o).prop("checked");
	table = $(o).closest("table").find("tbody").first();

	$(table).find("input[type=checkbox]").each(function() {
		if(!$(this).prop("disabled"))
			$(this).prop("checked", val);
	});

}

function deleteCheckedRows(table) {
 
	freshPage(table,{});
	
}

function deleteRowsByIds(table, id) {

	var nNodes = table.fnGetNodes();
	for ( var i = 0; i < nNodes.length;) {
// console.log(nNodes[i].children[0].firstChild.value);
		if (nNodes[i].children[0].firstChild.value == id) {
			table.fnDeleteRow(i, null, true);
			break;
		}
		i++;
	}
}

function checkAll(checkbox) {
	var getCK = document.getElementsByName('appCheck');
	ids = [];
	for ( var i = 0; i < getCK.length; i++) {
		whichObj = getCK[i];
		whichObj.checked = checkbox.checked;
		if (checkbox.checked) {
			ids.push(whichObj.value);
		}
	}
}
function check(checkbox) {
	if (checkbox.checked) {
		ids.push(checkbox.value);
	} else {
		for ( var i = 0; i < ids.length; i++) {
			if (ids[i] == checkbox.value) {
				ids.splice(i, 1);
				break;
			}
		}
	}
}



function showOps(o) {
// console.log($(o).find("img").first());
	if ($(o).find("img").first().attr("src").indexOf("devrulegrey.png") != -1) {
		return;
	}
	var _this = $(o);
	// _this.parents(".dataTables_wrapper").css("z-index","8")
	var top = _this.offset().top;
	var _thisChild = _this.find("div.tb-opt-main");
	$(_thisChild).hover(function() {
		$(this).show();
	}, function() {
		$(this).hide();
		// $(this).parents(".dataTables_wrapper").css("z-index","1")
	});
	if (_thisChild.is(":visible")) {
		_thisChild.hide();
	} else {
		$("div.tb-opt-main").not(_thisChild).hide();
		if (_thisChild.outerHeight(true) <= 2 * top) {
			var splitH = Math.floor(_thisChild.outerHeight(true) / 2);
			_thisChild.css("top", "-" + (splitH - 8) + "px").show();
			_thisChild.find("img:first").css("top", splitH - 4 + "px");
		} else {
			_thisChild.find("img:first").css("top", top - 4 + "px");
			_thisChild.css("top", "-" + top + "px").show();
		}

	}

}

function getText(type, value) {
	if (type == "os") {
		if (value == 0) {
			return "Android";
		} else if (value == 1) {
			return "IOS";
		} else if (value == 2) {
			return "Windows";
		}
	}
	if (type == "type") {
		if (value == 0) {
			return "个人";
		} else if (value == 1) {
			return "企业";
		} else if (value == 2) {
			return "共用";
		}
	}
	if (type == "status") {
		if (value == 0) {
			return "注册";
		} else if (value == 2) {
			return "驳回";
		} else if (value == 1) {
			return "激活";
		} else if (value == 3) {
			return "注销";
		}
	}
	if (type == "online") {
		if (value) {
			return "<td  style='color: blue'>在线</td>";
		} else {
			return "<td>离线</td>";
		} 
	}
}

function getDevice(deviceId) {
	id = deviceId;
	OpenDialogWithClose("设备详情", "console/devices_detail", 'devices_detail',
			true, true, returnPage);
}
pageid = "registerPage";
function returnPage() {
	$("#dashboard-" + pageid).show();
	$("#" + pageid).parent('li').addClass('active');
}



function checkAllDevices(checkbox) {
	var dataTableFrom = $('#datatable-9').dataTable();
	if (pageid == "activate") {
		dataTableFrom = $('#datatable-7').dataTable();
	} else if (pageid == "deny") {
		dataTableFrom = $('#datatable-6').dataTable();
	} else if (pageid == "cancel") {
		dataTableFrom = $('#datatable-8').dataTable();
	}
	ids = [];
	var nNodes = dataTableFrom.fnGetNodes();
	for ( var i = 0; i < nNodes.length; i++) {
		nNodes[i].children[0].firstChild.checked = checkbox.checked;
		if (checkbox.checked) {
			ids.push(nNodes[i].children[2].innerHTML);
		}
	}
}

function MakeSelect() {
	$('select').select2({width:50,height:24});
}

function freshPage(table,settings){
	OSettings = table.DataTable.models.oSettings;
	table._fnFilterComplete(OSettings, settings);
}

function getValueByName(parent_id,name) {
	return $('#'+parent_id).find("input[name='" + name + "']").first().val();
}
function setValueByName(parent_id,name, value) {
	$('#'+parent_id).find("input[name='" + name + "']").first().val(value);
}

function getSelectValByName(parent_id,name) {
	return $('#'+parent_id).find("select[name='" + name + "']").first()
			.val();
}

function setSelectValByName(parent_id,name, value) {
	$('#'+parent_id).find("select[name='" + name + "']").first().val(value);
}
 

function clearSecurityCache(){
	$.get("rest/refreshMemoryResource",function(data){
		if(data.successful){
			alert("刷新成功");}
		else{
			alert("刷新失败，请稍后再试");
		}
		
	});

}


/**
 * 刷新整个树
 * 
 * @param id
 *            jstree的id
 */
function refreshTree(id){
	ref = $('#'+id).jstree(true);
	ref.refresh();
}

/**
 * 刷新树的特定节点
 * 
 * @param treeid
 *            树id
 * @param itemId
 *            节点分支
 */
function refreshTree(treeid,itemId){
	ref = $('#'+id).jstree(true);
	ref.refresh("#"+itemId);
}

/**
 * 自动提示框工具,默认使用post方法获得
 * 
 * @param inputId
 *            输入框的id
 * @param datasource
 *            数据源url
 */
function autoCompleteTips(inputObj,datasource){
	 var $autocomplete = $("<ul class='autocomplete'></ul>").hide().insertAfter(inputObj);
     $(inputObj).keyup(function() {

         $.post(datasource, { "search": $(inputObj).val() }, function(data) {
        	 if(data.successful){
        		 tips = data.resultValue;
        		 if (tips.length) {
                     $autocomplete.empty();

                     var arr = tips;
                     $.each(arr, function(index, term) {
                         $("<li></li>").text(term).appendTo($autocomplete).mouseover(function() {
                             $(this).css("background", "#ccc");
                         }).mouseout(function() {
                             $(this).css("background", "white");
                         })
                         .click(function() {
                             $(inputObj).val(term);
                             $autocomplete.hide();
                         });
                     });
                     $autocomplete.show();
                 }else{
                	 $autocomplete.empty();
                	 $autocomplete.hide();
                 }
        	 }
         });
     }).blur(function() {
     setTimeout(function() {
     $autocomplete.hide();
         },500);
        
     });
}

function message(txt){
	$("#dialog-msg").find("#message_msg").html(txt);
	$( "#dialog-msg" ).dialog({
		modal: true,
		height:400,
		width:600,
		resizable:false,
		draggable:false,
		dialogClass: "my-dialog",
		buttons: {
			"确定": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
}

function alert(txt){
	$("#dialog-message").find("#message_alert").html(txt);
	$( "#dialog-message" ).dialog({
		modal: true,
		height:140,
		resizable:false,
		draggable:false,
		dialogClass: "my-dialog",
		buttons: {
			"确定": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}

function confirm(txt,callback){
	
	$("#dialog-confirm").find("#message_confirm").html(txt);
	$("#dialog-confirm").dialog({
		resizable: false,
		height:140,
		modal: true,
		resizable:false,
		draggable:false,
		dialogClass: "my-dialog",
		buttons: {
			"确定": function() {
				callback();
				$( this ).dialog( "close" );
				 
			},
			"取消": function() {
				$( this ).dialog( "close" );
				return false;
			}
		}
	});
	$("#dialog-confirm").parent().find(".ui-dialog-buttonset").each(function(){
		$(this).css("padding-left","30%");
	});
}

function notAllowed(){
	alert("系统默认元素不能更改");
	
}