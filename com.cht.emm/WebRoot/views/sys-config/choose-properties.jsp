<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<style>
</style>
<sec:authentication property="principal" var="authentication" />
<div class="row">
	<div class="col-xs-9 col-sm-9">
		<div class="form-group">
			<label class="col-sm-3 control-label">可选配置项<span
				class="required">*</span> </label>
			<div class="col-sm-9">
				<select name="selectableProperties" id="selectableProperties" />
			<input
					type="button" value="新增" onclick="addItem()"></div>

		</div>
	</div>
</div>
<div class="row">
	<div class="col-xs-9 col-sm-9">
		<form class="form-horizontal bv-form"
			id="form_update_configedProperties">
			<c:forEach items="${properties }" var="item">
				<div class="form-group">
					<label class="col-sm-3 control-label">${item.key }<span
						class="required">*</span> </label>
					<div class="col-sm-9">
						<input type="text" class="form-control" name="${item.key }"
							value="${item.value }" /> <input type="button" value="删除"
							onclick="removeItem(this)">
					</div>
				</div>
			</c:forEach>
		</form>
	</div>

</div>

<script type="text/javascript">
	// 将未选项加入到可配置选项中去，需要同步删除select中的内容
	function loadAVConfigProperties() {
		$.ajax({
			url : "rest/sysConfig/properties/unconfiged",
			type : "post",
			success : function(json) {
				if (json.successful) {
					list = json.resultValue;
					if (list.length > 0) {
						for (i = 0; i < list.length; i++) {

							$("#selectableProperties").append(
									"<option value='"+list[i]+"'>" + list[i]
											+ "</option>")
						}
					}
				}

			}
		});

	}

	function addItem() {
		var op = $("#selectableProperties").find("option:selected");
		var val = op.val();
		if (val) {
			$("#form_update_configedProperties")
					.append(
							"<div class='form-group'><label class='col-sm-3 control-label'>"
									+ op.val()
									+ "<span class='required'>*</span> </label><div class='col-sm-9'><input type='text' class="
									+ "'form-control' name='"
									+ op.val()
									+ "' /> <input type='button' value=\"删除\"onclick='removeItem(this)'> "
									+ "</div></div>");
			$(op).remove();
			$.ajax({
				url : "rest/sysConfig/properties/changeop",
				type : "post",
				data : {
					"key" : val,
					"op" : "add"
				},
				success : function(json) {
				}
			});
			$('#selectableProperties').select2();

		}

	}
	// 删除所在的可配置项，需要通知后台，存入到已删除项中去，同时需要添加到select中去
	function removeItem(obj) {
		var div = $(obj).closest(".form-group");
		var input = $(obj).closest("input[type=text]");
		var key = input.name;
		$("#selectableProperties").append(
				"<option value=''"+key+">" + key + "</option>");
		$(div).remove();
		$.ajax({
			url : "rest/sysConfig/properties/changeop",
			type : "post",
			data : {
				"key" : key,
				"op" : "del"
			},
			success : function(json) {
			}
		});
		$('#selectableProperties').select2();
	}

	function DemoSelect2() {
		$('#selectableProperties').select2();
	}

	$(document).ready(function() {
		loadAVConfigProperties()
		LoadSelect2Script(DemoSelect2);

	});
</script>
