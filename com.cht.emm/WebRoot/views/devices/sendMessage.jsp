<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<form action="" class="form-horizontal" id="form_send_message">
			<div class="form-group">
				<label class="col-sm-2 control-label">消息内容:<span
					class="required">*</span>
				</label>
				<div class="col-sm-9">
					<input type="hidden" name="deviceId" value="${deviceId }">
					<textarea name="messageContent" class="form-control" rows="12"
						id="mscontent"></textarea>
				</div>
			</div>
		</form>
	</div>
</div>
