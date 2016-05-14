<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<form class="form-horizontal" id="form_create_vpn">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-2 control-label">VPN名称<span
						class="required">*</span></label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="vpnName"
							name="vpnName" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">VPN密码<span
						class="required">*</span></label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="vpnPassword"
							name="vpnPassword" />
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</div>
<script type="text/javascript">
	function validVpn() {
		$('#form_create_vpn').bootstrapValidator({
			message : "invalid",
			fields : {
				vpnName : {
					validators : {
						notEmpty : {
							message : "VPN名称必填"
						},
						stringLength : {
							max : 16,
							message : "VPN名称长度不超过16个字符"
						}
					}

				},
				vpnPassword : {
					validators : {
						notEmpty : {
							message : "VPN密码必填"
						},
						stringLength : {
							max : 16,
							message : "VPN密码长度不超过16个字符"
						}
					}

				}

			}

		});
	}
	$(document).ready(function() {
		$('.form-control').tooltip();
		LoadBootstrapValidatorScript(validVpn);
	});
</script>
