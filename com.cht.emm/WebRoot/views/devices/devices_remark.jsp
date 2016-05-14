<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<form class="form-horizontal" id="form_create_remark">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="remark" name="remark" />
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</div>
<script type="text/javascript">
	function validRemark() {
		$('#form_create_remark').bootstrapValidator({
			message : "invalid",
			fields : {
				remark : {
					validators : {
						stringLength : {
							max : 64,
							message : "备注长度不超过64个字符"
						}
					}

				}

			}

		});
	}
	$(document).ready(function() {
		$('.form-control').tooltip();
		LoadBootstrapValidatorScript(validRemark);
	});
</script>
