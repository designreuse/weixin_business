<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
<title>***系统</title>
<meta name="description" content="description">
<meta name="author" content="Evgeniya">
<meta name="keyword" content="keywords">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="${pageContext.request.contextPath}/devoops/plugins/bootstrap/bootstrap.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/devoops/font-awesome/css/font-awesome.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/devoops/css/google.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/devoops/css/style.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/devoops/plugins/select2/select2.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/devoops/plugins/jquery/jquery-2.1.0.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/login.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.md5.js"></script>
<script
	src="${pageContext.request.contextPath}/devoops/plugins/jquery-ui/jquery-ui.min.js"></script>
<script
	src="${pageContext.request.contextPath}/devoops/plugins/bootstrap/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/devoops/js/devoops.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
				<script src="http://getbootstrap.com/docs-assets/js/html5shiv.js"></script>
				<script src="http://getbootstrap.com/docs-assets/js/respond.min.js"></script>
		<![endif]-->

<style type="text/css">
.qrIcon {
	width: 100%;
	max-height: 210px;
}
</style>
<script type="text/javascript">
	var scheme = "${pageContext.request.scheme}";
	var server = "${pageContext.request.serverName}";
	var port = "${pageContext.request.serverPort}";
	var context = "${pageContext.request.contextPath}";
	//var defaultHref ="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/";
	var defaultHref = scheme + "://" + server + ":" + port + context;
	var loginHref = defaultHref + "/login";

	var curHref = location.href;
	//用户点击注销按钮
	if (-1 != curHref.indexOf("/logout")) {
		
		location.href = loginHref;
	} 

	$(document).ready(
			function() {
				$(document).keydown(function(event) {

					/**
						注入keydown事件，如果按了enter键，提交form
					 */
					if (event.keyCode == 13) {
						doLogin();
					}
				});

				var type =<%=request.getParameter("type")%>;

				setLoginError(type);

				$("#flashImage").click(
						function() {

							$('#imageF').hide().attr(
									'src',
									'<c:url value="/views/validatecode.jsp"/>'
											+ '?'
											+ Math.floor(Math.random() * 100))
									.fadeIn();
						});
				//loadUserSource();
				LoadSelect2Script(DemoSelect2);
			});

	function doLogin() {

		var username = document.getElementById("username").value;
		if (!username) {
			setLoginError(5);
			return;
		}
		var password = document.getElementById("password").value;
		if (!password) {
			setLoginError(6);
			return;
		}
		var validate_code = document.getElementById("validate_code").value;
		if (!validate_code) {
			setLoginError(7);
			return;
		}
		var b = new Base64();
		var str = b.encode(password);
		var jsonStr = "{\"uname\":\"" + username + "\",\"upwd\":\"" + str
				+ "\",\"validate\":\"2145\"}";

		/*$
				.ajax({
					type : "get",
					async : false,
					url : "http://119.254.111.223:7001/access/adminBasic/safeLogin.htm?json="
							+ jsonStr,
					dataType : "jsonp",
					jsonp : "jsonpCallback",
					jsonpCallback : "jsonpCallback",
					success : function(data) {
						if (undefined == data || (data.resultCode == -1)) {
							setLoginError(1);
						} else {

							$('#password').val($.md5($('#password').val()));
							//alert($.md5($('#password').val()));

							$('#loginForm').submit();

						}
					},
					error : function(p_request, p_status, p_err) {
						setLoginError(4);
					}

				});*/
				
		$('#password').val($.md5($('#password').val()));
		//alert($.md5($('#password').val()));

		$('#loginForm').submit();

	}

	function loadUserSource() {
		$("#userSource").append("<option value='' selected>本地用户</option>");
		$.ajax({
			url : "rest/thirdpart/list",
			type : "post",
			success : function(data) {
				debugger
				if (data.successful == true) {

					list = data.list;
					if (list.length > 0) {
						for (i = 0; i < list.length; i++) {
							$("#userSource").append(
									"<option value='"+list[i].id+"'>"
											+ list[i].name + "</option>");
						}
					}
				}

			}
		});
	}
	function DemoSelect2() {

		$('#userSource').select2({
			placeholder : "-- 选择处理类 --"
		});
	}
</script>

</head>
<body
	style="background: -webkit-radial-gradient(center, ellipse cover, #353644 0, #202029 100%);">
	<div class="container-fluid">
		<div id="page-login" class="row">
			<div id="login_div"
				class="col-xs-12 col-md-8 col-md-offset-2 col-sm-8 col-sm-offset-2">
				<!-- div class="row" style="text-align: center;">
					<img alt=""
						src="${pageContext.request.contextPath}/resources/img/logo.png">
				</div-->
				<div class="row">
					<div class="box no-drop">
						<div class="box-content" style="min-height:250px;">
							<div class="col-xs-12">
								<form id="loginForm" action="j_spring_security_check"
									method="post">
									<div class="form-group" style="text-align:center">
										<label style="font-size: 30px; color: #333">项目名称</label>
									</div>
									<div class="form-group">
										<!-- label class="control-label">用户</label-->
										<input type="text" class="form-control" id="username"
											name="username" placeholder="用户名" />
										<!-- style="padding-left: 25px;background-repeat: no-repeat;background-image: url('${pageContext.request.contextPath}/resources/img/user_16.png');background-position: 4px,10px,10px;" -->
									</div>
									<div class="form-group">
										<!-- label class="control-label">密码</label-->
										<input type="password" class="form-control" id="password"
											name="password" placeholder="密码" />
										<!-- style="padding-left: 25px;background-repeat: no-repeat;background-image: url('${pageContext.request.contextPath}/resources/img/pwd_16.png');background-position: 5px,10px,10px;" -->
									</div>
									<!-- div class="form-group" -->
									<!-- label class="control-label">密码</label-->
									<!-- select name="userSource" id="userSource">
										</select-->
									<!-- style="padding-left: 25px;background-repeat: no-repeat;background-image: url('${pageContext.request.contextPath}/resources/img/pwd_16.png');background-position: 5px,10px,10px;" -->
									<!-- /div -->
									<div class="form-group  has-feedback">
										<!--  label class="control-label col-sm-2" for="validate_code"
										style="text-align: center; padding-left: 0px; padding-right: 0px; color: #333 !important; padding-top: 10px;">
										验证码: </label -->

										<input type='text' id='validate_code' name='validate_code'
											class="form-control" id="validate_code" placeholder="验证码" />
										<span class="form-control-feedback"
											style="margin-top:-25px;display:inline;width:120px"><img
											id="imageF" style="margin-top: -12px;"
											src="<c:url value="/views/validatecode.jsp"/>" /><a href="#"
											id="flashImage"><i class="fa fa-refresh"
												style="font-size: 20px;padding-top: 8px;padding-left: 8px;"></i>
										</a> </span>


									</div>
									<div class="form-group">
										<p id="result" style="color: red;"></p>
										<input type="button" class="form-control" onclick="doLogin()"
											style="background-color: #54a983 !important; color: #fff"
											value="登 录 " />
									</div>

								</form>
							</div>
							<!-- div class="col-xs-6">
								<ul style="padding: 5px 5px">
									<li><img alt="" class='qrIcon'
										src="${pageContext.request.contextPath}/resources/img/qrcode.png">
									</li>
									<li style="text-align:center"><label
										style="text-align:center"><a href="${pageContext.request.contextPath}/resources/apk/nari.mip.console.apk">点击立即下载</a>
									</label>
									</li>
								</ul>
							</div-->
						</div>
					</div>
					<!-- 
					<div style="text-align: center; margin-top: -15px;">©
						***公司 版权所有</div> -->
				</div>
			</div>
		</div>

	</div>

</body>
</html>
