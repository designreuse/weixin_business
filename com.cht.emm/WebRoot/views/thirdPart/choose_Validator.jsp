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
			<label class="col-sm-3 control-label">可用接入方式<span
				class="required">*</span> </label>
			<div class="col-sm-9">
				<select name="selectableValidator" id="selectableValidator">
					<option value="local">本地验证</option>
				</select>
			</div>

		</div>
	</div>
</div>

<script type="text/javascript">
	// 将未选项加入到可配置选项中去，需要同步删除select中的内容
	var defaultValidator = '${validator}';
	function selectableValidator() {
		$.ajax({
			url : "rest/thirdpart/list",
			type : "post",
			success : function(json) {
		 		list = json.list;
				if (list.length > 0) {
					for (i = 0; i < list.length; i++) {
						$("#selectableValidator").append(
								"<option value='"+list[i].id+"'>" + list[i].name
										+ "</option>");
					}
				}
				$("#selectableValidator").val(defaultValidator);
			 }
		});

	}

	 
	function DemoSelect2() {
		$('#selectableValidator').select2();
	}

	$(document).ready(function() {
		selectableValidator()
		LoadSelect2Script(DemoSelect2);

	});
</script>
