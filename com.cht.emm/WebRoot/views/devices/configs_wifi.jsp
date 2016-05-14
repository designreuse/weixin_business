<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<form class="form-horizontal" id="form_create_wifi">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-2 control-label">WIFI名称<span
						class="required">*</span></label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="wifiName"
							name="wifiName" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">WIFI密码<span
						class="required">*</span></label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="wifiPassword"
							name="wifiPassword" />
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</div>
<script type="text/javascript">
	function validWifi() {
		$('#form_create_wifi').bootstrapValidator({
			message : "invalid",
			fields : {
				wifiName : {
					validators : {
						notEmpty : {
							message : "WIFI名称必填"
						},
						stringLength : {
							max : 16,
							message : "WIFI名称长度不超过16个字符"
						}
					}

				},
				wifiPassword : {
					validators : {
						notEmpty : {
							message : "WIFI密码必填"
						},
						stringLength : {
							max : 16,
							message : "WIFI密码长度不超过16个字符"
						}
					}

				}

			}

		});
	}
	$(document).ready(function() {
		$('.form-control').tooltip();
		LoadBootstrapValidatorScript(validWifi);
	});
</script>
