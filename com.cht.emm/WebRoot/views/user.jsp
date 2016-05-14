<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal" var="authentication" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>***平台</title>
<meta name="description" content="description">
<meta name="author" content="DevOOPS">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="${pageContext.request.contextPath}/devoops/plugins/bootstrap/bootstrap.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/devoops/plugins/jquery-ui/jquery-ui.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/devoops/font-awesome/css/font-awesome.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/devoops/css/google.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/devoops/plugins/fancybox/jquery.fancybox.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/devoops/plugins/fullcalendar/fullcalendar.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/devoops/plugins/xcharts/xcharts.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/devoops/plugins/select2/select2.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/devoops/css/style.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/main.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/jstree-master/dist/themes/default/style.css"
	rel="stylesheet">
</head>
<body>
	<!--Start Header-->
	<div id="screensaver">
		<canvas id="canvas"></canvas>
		<i class="fa fa-lock" id="screen_unlock"></i>
	</div>
	<div id="modalbox">
		<div class="devoops-modal">
			<div class="devoops-modal-header">
				<div class="modal-header-name">
					<span>Basic table</span>
				</div>
				<div class="box-icons">
					<a class="close-link"> <i class="fa fa-times"></i> </a>
				</div>
			</div>
			<div class="devoops-modal-inner"></div>
			<div class="devoops-modal-bottom"></div>
		</div>
	</div>
	<header class="navbar">
		<div class="container-fluid expanded-panel">
			<div class="row" id="top_nav">
				<div id="logo" class="col-xs-2 col-sm-2">
					<h2 id="label-company" class="hidden-from-md">******平台</h2>
				</div>
				<div id="top-panel" class="col-xs-12 col-sm-10">
					<div class="row">
						<div class="col-xs-8 col-sm-4">
							<a href="#" class="show-sidebar">
							  <i class="fa fa-bars"></i>
							</a>
						</div>
						<div class="col-xs-4 col-sm-8 top-panel-right">
							<ul class="nav navbar-nav pull-right panel-menu">
								<li class="hidden-xs"><span class="welcome">欢迎您，</span> <span
									id="user">MIP User</span>
								</li>
								<li><a href="<c:url value='j_spring_security_logout'/>">
										<i style="font-size:16px;padding: 5px 0px;padding-top: 4px;" class="fa fa-power-off"></i> <span class="hidden-sm text">注销</span>
								</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>
	<!--End Header-->
	<!--Start Container-->
	<div id="main" class="container-fluid">
		<div class="row">
			<div id="sidebar-left" class="col-xs-2 col-sm-2">
				<ul id="parentMenu" class="nav main-menu">
				</ul>
			</div>
			<!--Start Content-->
			<div id="content" class="col-xs-12 col-sm-10">
				<!-- 收缩栏 
				<div style="height:0;left:-15px">
					<div id="left_side_div">
						<a id="left_side_bar"><i class="fa fa-chevron-left"></i>
						</a>
					</div>
				</div>
				 -->
				<div style="height:100%;min-height:100%">
					<div class="preloader" style="background:rgb(0,0,0);opacity:0.6">
						 <div style="padding:10px 10px 10px 10px;width:200px;height:50px;text-align:center;background:#fff;left:50%;top:50%;position:absolute;position:fixed !important">
						 	<img alt="" src="${pageContext.request.contextPath}/resources/img/loading1.gif"> 正在努力加载中……</div>
					</div>
					<div id="ajax-content" style="height:auto;min-height:100%;overflow-x:hidden;overflow-y:auto;padding-bottom:26px;"></div>
					<iframe id="ajax-content-iframe" src="#" marginheight="0"
						marginwidth="0" frameborder="0" scrolling="auto" width="100%"
						height="100%" name="ajax-content-iframe" style="display: none;"></iframe>
				</div>
			</div>
			<!--End Content-->
		</div>
	</div>
	<jsp:include page="alert.jsp" />
	<!--End Container-->
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="${pageContext.request.contextPath}/devoops/plugins/jquery/jquery-2.1.0.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/devoops/plugins/jquery-ui/jquery-ui.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script
		src="${pageContext.request.contextPath}/devoops/plugins/bootstrap/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/devoops/plugins/justified-gallery/jquery.justifiedgallery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/devoops/plugins/tinymce/tinymce.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/devoops/plugins/tinymce/jquery.tinymce.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.md5.js"></script>
	<!-- All functions for this theme + document.ready processing -->
	<script src="${pageContext.request.contextPath}/devoops/js/devoops.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/ckeditor/ckeditor.js"></script>

	<script
		src="${pageContext.request.contextPath}/jstree-master/dist/jstree.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.md5.js"></script>
	<script type="text/javascript">
		var ajax_url =null; 
		function wrap(menuItem) {
			if (menuItem != null) {
				cls = menuItem.className == null ? "" : " class='"
						+ menuItem.className + "' ";
				title = menuItem.title;

				trigger = menuItem.trigger == null ? "" : " onClick='"
						+ menuItem.trigger + "'";
				beforeTitle = menuItem.beforeTitle == null ? ""
						: menuItem.beforeTitle;
				afterTitle = menuItem.afterTitle == null ? ""
						: menuItem.afterTitle;
				titleClass = menuItem.titleClass == null ? "" : " class ='"
						+ menuItem.titleClass + "'";
				linkClass = menuItem.linkClass == null ? "" : " class ='"
						+ menuItem.linkClass + "'";
				
			

				if (menuItem.leaf) {
				    url = menuItem.url;
				    //第一个要显示的page url
				    if(ajax_url ==null){
				    	//如果url不是以http大头，及内部地址
				    	if(url.indexOf("http")==-1)
				    			ajax_url = "${pageContext.request.contextPath}"+url;
				    		else
				    			ajax_url = url;
				    }
				    if(url!=null && url !="#"){
				    	if(url.indexOf("http")==-1){
				    		url = "${pageContext.request.contextPath}"+url;
				    	}
				    }
					return $("<li><a "+linkClass+" href=\""+url+"\">"
							+ beforeTitle
							+ "<span "+titleClass+">"
							+ title
							+ "</span>" + afterTitle + "</a></li>");
				} else {
					parent = $("<li "+cls+" ><a "+linkClass+" href=\"#\">"
							+ beforeTitle + "<span "+titleClass+">" + title
							+ "</span>" + afterTitle + "</a></li>");

					if (menuItem.subItems != null
							&& menuItem.subItems.length > 0) {
						subUl = $("<ul class='dropdown-menu'></ul>")
						for (j = 0; j < menuItem.subItems.length; j++) {
							item = menuItem.subItems[j];
							$(subUl).append(wrap(item));
						}
						$(parent).append(subUl);
					}
					return parent;

				}
			} else {
				return null;
			}

		}
		function initMenus() {
			var userId = "${authentication.userId}";
			 
			$
					.ajax({
						url : "rest/menu/menus",
						data : {
							"userId" : userId
						},
						type : "POST",
						success : function(json) {
							if (json.successful) {
								menus = json.resultValue;
								for (i = 0; i < menus.length; i++) {
									//console.log(i);
									menu = menus[i];
									sub = wrap(menu);
									if (sub != null) {
										$("#parentMenu").append(sub);
									}
								}
								if (ajax_url!=null && ajax_url.length > 1) {
									LoadAjaxContent(ajax_url);
								}
							} else {

								if (json.relogin) {
									window.location = "${pageContext.request.contextPath}";
								} else {
									alert(json.resultMessage);
								}

							}

						}

					});

		}
		$(document).ready(
				function() {
				     // 产生菜单
				    initMenus();
					//设置登陆用户
					var user = "${authentication.alias}";
					$("#user").text(user);
					var flag = true;
					$("#a_sidebar").click(
							function() {
								if (flag) {
									$("#a_sidebar>i").attr("class",
											"fa fa-angle-double-right");
									flag = false;
								} else {
									$("#a_sidebar>i").attr("class",
											"fa fa-angle-double-left");
									flag = true;
								}
							});
					
					
				});
	</script>

</body>
</html>
