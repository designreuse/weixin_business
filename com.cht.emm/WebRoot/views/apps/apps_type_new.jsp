<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<form class="form-horizontal" id="form_create_apptype">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-2 control-label">名称<span
						class="required">*</span></label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="name" id="name" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">描述</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="description"
							id="description" />
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</div>
<script type="text/javascript">
	function validAppType() {
		$('#form_create_apptype').bootstrapValidator({
			message : "invalid",
			fields : {
				name : {
					validators : {
						notEmpty : {
							message : "名称必填"
						},
						stringLength : {
							min : 2,
							max : 16,
							message : "名称长度在2-16个字符之间"
						},
						remote : {
							type : 'POST',
							message : '名称已存在,请重新填写',
							url : 'rest/apptype/checkNameExists',
							data : {
								name : name,
								id : id
							}
						}
					}

				},
				description : {
					validators : {
						stringLength : {
							max : 64,
							message : "描述长度不超过64个字符"
						}
					}

				}

			}

		});
	}
	$(document).ready(function() {
		$('.form-control').tooltip();
		$.ajaxSettings.async = false;

		if (id) {
			$.getJSON("rest/apptype/" + id, function(result) {
				appType = result.resultValue;
				$('#name')[0].value = appType.name;
				$('#description')[0].value = appType.description;
			});
		}

		LoadBootstrapValidatorScript(validAppType);
	});
</script>
