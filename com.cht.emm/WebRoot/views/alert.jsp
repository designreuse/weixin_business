<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<style>

 
  
   .my-dialog .ui-dialog-titlebar-close{
    display: none;
   }
	.alert_container{
		width: 100%;
		padding: 10px 10px;
	}
	.confirm_icon{
		font-size: 20px;
		color: red;
		width: 20%;
		float: left;
		text-align: center;
	}
	.alert_icon{
		font-size: 20px;
		color: blue;
		width: 20%;
		float: left;
		text-align: center
	}
	.alert_text{
		width: 100%;
		padding-top: 7px;
		text-align: center;
	}
	
	.msg_text{
		width: 100%;
		padding-top: 7px;
		text-align: left;
	}
	.msg_container{
		with:100%;
		padding-left:20px;
	}
	.msg_icon{
		width: 100%;
		padding-top: 7px;
		text-align: left;
	}
</style>

<div id="dialog-message" title="消息" style="display:none">
	<div class="alert_container">
		 
		<div class="alert_text">
			<span id="message_alert" style="margin-left:1em;padding-bottom:2px;"></span>
		</div>

	</div>
</div>

<div id="dialog-confirm" title="警告" style="display:none">
	<div class="alert_container">
		<div class="alert_text">
			<span id="message_confirm"
				style="margin-left:1em;padding-bottom:2px;"></span>
		</div>
	</div>
</div>



<div id="dialog-msg" title="消息" style="display:none">
	<div class="msg_container">
		<div class="msg_text">
			<span id="message_msg"
				style="margin-left:1em;padding-bottom:2px;text-align:left"></span>
		</div>
	</div>
</div>