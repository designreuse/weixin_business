<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="row">
	<div class="col-xs-12 col-sm-12">
		<form class="form-horizontal" id="form_create_app">
			<fieldset>
				<div class="form-group">
					<label class="col-sm-2 control-label">应用安装</label>
					<div class="col-sm-8">
						<div class="radio-inline">
							<label>
								<input id="install" value="1" type="radio" name="radio-inline" checked> 允许安装
								<i class="fa fa-circle-o"></i>
							</label>
						</div>
						<div class="radio-inline">
							<label>
								<input value="-1" type="radio" name="radio-inline"> 禁止安装
								<i class="fa fa-circle-o"></i>
							</label>
						</div>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</div>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
					});
</script>
