<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="messages" class="container-fluid">
	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<li><a href="#">企业信息</a></li>
				<li><a href="#">通知公告</a></li>
			</ol>
		</div>
	</div>
	<div class="row" id="test">
		<div class="col-xs-12">
			<div class="row">
				<ul id="messages-menu" class="nav msg-menu">
					<li><a href="index.html" class="" id="msg-inbox"> <i
							class="fa fa-inbox"></i> <span class="hidden-xs">已发送 (100)</span>
					</a></li>
					<li><a href="index.html" class="" id="msg-starred"> <i
							class="fa fa-star"></i> <span class="hidden-xs">草稿箱 (20)</span>
					</a></li>
					<li><a href="index.html" class="" id="msg-draft"> <i
							class="fa fa-pencil"></i> <span class="hidden-xs">发送失败 (2)</span>
					</a></li>

				</ul>
				<div id="messages-list" class="col-xs-10 col-xs-offset-2">
					<div class="row one-list-message msg-inbox-item" id="msg-one">
						<div class="col-xs-9 message-title">
							<input type="checkbox"> <b>新年快乐！</b>
						</div>
						<div class="col-xs-2 message-date">12/31/13</div>
					</div>
					<div class="row one-list-message msg-starred-item">
						<div class="col-xs-9 message-title">
							<input type="checkbox"><b>天气预报：明天有雨。</b>
						</div>
						<div class="col-xs-2 message-date">12/31/13</div>
					</div>
					<div class="row one-list-message msg-inbox-item">
						<div class="col-xs-9 message-title">
							<input type="checkbox"><b>请更新客户端应用。</b>
						</div>
						<div class="col-xs-2 message-date">12/31/13</div>
					</div>
					<div class="row one-list-message msg-inbox-item" id="msg-one">
						<div class="col-xs-9 message-title">
							<input type="checkbox"> <b>新年快乐！</b>
						</div>
						<div class="col-xs-2 message-date">12/31/13</div>
					</div>
					<div class="row one-list-message msg-starred-item">
						<div class="col-xs-9 message-title">
							<input type="checkbox"><b>天气预报：明天有雨。</b>
						</div>
						<div class="col-xs-2 message-date">12/31/13</div>
					</div>
					<div class="row one-list-message msg-inbox-item">
						<div class="col-xs-9 message-title">
							<input type="checkbox"><b>请更新客户端应用。</b>
						</div>
						<div class="col-xs-2 message-date">12/31/13</div>
					</div>
					<div class="row one-list-message msg-one-item"
						style="display: none;">
						<div class="box no-drop">
							<div class="avatar">
								<img src="img/avatar.jpg" alt="Jane" />
							</div>
							<div class="page-feed-content">
								<small class="time">Jane Devops, 12 min ago</small>
								<p>Linux is a Unix-like and POSIX-compliant computer
									operating system assembled under the model of free and open
									source software development and distribution. Maemo - Software
									platform developed by Nokia and then handed over to Hildon
									Foundation for smartphones and Internet tablets</p>
								<div class="likebox">
									<ul class="nav navbar-nav">
										<li><i class="fa fa-thumbs-up"></i> 138</li>
										<li><i class="fa fa-thumbs-down"></i> 47</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	// Add listener for redraw menu when windows resized
	window.onresize = MessagesMenuWidth;
	$(document).ready(function() {
		// Add class for correctly view of messages page
		$('#content').addClass('full-content');
		// Run script for change menu width
		MessagesMenuWidth();
		$('#content').on('click', '[id^=msg-]', function(e) {
			e.preventDefault();
			$('[id^=msg-]').removeClass('active');
			$(this).addClass('active');
			$('.one-list-message').slideUp('fast');
			$('.' + $(this).attr('id') + '-item').slideDown('fast');
		});
		$('html').animate({
			scrollTop : 0
		}, 'slow');
	});
</script>
