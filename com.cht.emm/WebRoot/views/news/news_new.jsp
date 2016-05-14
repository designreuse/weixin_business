<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<form class="form-horizontal" id="form_create_news">
			<div class="form-group">
				<label class="col-sm-2 control-label">资讯标题<span
					class="required">*</span></label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="title" name="title">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">资讯作者<span
					class="required">*</span></label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="editor">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">资讯标签</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="local">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">资讯标记</label>
				<div class="col-sm-8">
					<select class="populate placeholder" name="country" id="mark">
						<option value="-1">-- 选择标记类型 --</option>
						<option value="0">推荐</option>
						<option value="1">热门</option>
						<option value="2">首发</option>
						<option value="3">独家</option>
						<option value="4">收藏</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">大图展示</label>
				<div class="col-sm-8">
					<input type="checkbox" id="type" onclick="changePage()">
				</div>
			</div>
			<!-- 
			<div class="form-group">
				<label class="col-sm-2 control-label">资讯图片</label>
				<div class="col-sm-8">
					<div id="uploadNewsPic"></div>
				</div>
			</div> -->
			<div class="form-group" id="app_screenshot_group">
				<label class="col-sm-2 control-label">资讯图片<br>(最多只能上传3张图片)
				</label>
				<div class="col-sm-8">
					<div class=" imgmod-box" id="phone-imgs"
						style="display: block; position: relative; overflow: auto;">
						<ul class="img-list scroll-img-list clearfix"
							id="appImageListPhone">
							<li class="img-item"><input id="importPicFile"
								name="importPicFile" type="file" size="32" class="file-upload"
								style="width: 100px; height: 150px"
								onchange="javascript:addAppPic(this)"> <img
								src="resources/img/upload_pic.png"> <a
								href="javascript:void(0);" onclick="appImageDelete(this);"
								hidden="true">删除</a> <input type="hidden" value=""></li>

						</ul>
					</div>
				</div>
			</div>
			<div class="form-group" id="icon_upload_group" hidden="true">
				<label class="col-sm-2 control-label">资讯图片<span
					class="required">*</span></label>
				<div class="col-sm-8">
					<div class=" imgmod-box"
						style="display: block; position: relative; overflow: auto;">
						<input name="importPicFile" type="file" size="32"
							class="file-upload" style="width: 100px; height: 150px"
							onchange="javascript:addAppIcon(this)"> <img id="bigImg"
							src="resources/img/upload_pic.png"
							style="width: 100px; height: 150px">

					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="form-styles">资讯详情<span
					class="required">*</span></label>
				<div class="col-sm-8">
					<textarea class="form-control" rows="5" id="wysiwig_simple"
						name="body"></textarea>
				</div>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript">
	function addAppIcon(obj) {
		var importPicFile = $(obj).val();

		if (importPicFile == "") {
			alert("请选择要上传的图片！");
			return;
		}

		if (importPicFile.substr(importPicFile.lastIndexOf(".")).toLowerCase() != ".png"
				&& importPicFile.substr(importPicFile.lastIndexOf("."))
						.toLowerCase() != ".jpg") {
			alert("只能上传后缀名为.PNG或.JPG格式的图片！");
			return;
		}

		var fileObj = obj.files[0]; // 获取文件对象

		var FileController = "upload/receiver/"; // 接收上传文件的后台地址 

		// FormData 对象

		var form = new FormData();

		form.append("file", fileObj); // 文件对象

		// XMLHttpRequest 对象

		var xhr = new XMLHttpRequest();

		xhr.open("post", FileController, true);

		xhr.onload = function() {

			var obj = JSON.parse(xhr.response);
			if (obj.success) {
				$("#bigImg").attr("src", obj.url);
				deleteUploadFile(bigImgPath);
				bigImgPath = obj.file;
				bigImgUrl = obj.url;
			}

		};

		xhr.send(form);

	}
	function changePage() {
		if (document.getElementById('type').checked) {
			document.getElementById('icon_upload_group').hidden = false;
			document.getElementById('app_screenshot_group').hidden = true;
		} else {
			document.getElementById('icon_upload_group').hidden = true;
			document.getElementById('app_screenshot_group').hidden = false;
		}
	}
	function deleteUploadFile(path) {
		if (path) {
			$.ajax({
				url : "rest/app/cancel",
				type : "POST",
				async : true,
				data : {
					path : path
				},
				success : function(p_resultValue) {
				},
				error : function(p_request, p_status, p_err) {
				}
			});
		}
	}
	function getAppImageListStr(src, hidden) {
		var appImageListStr = '<li class="img-item">';
		appImageListStr += '<input id="importPicFile" name="importPicFile" type="file" size="32" class="file-upload" style="width: 100px; height: 150px" onchange="javascript:addAppPic(this)">'
		if (src) {
			appImageListStr += '<img src="'+src+'"/>';
		} else {
			appImageListStr += '<img src="resources/img/upload_pic.png"/>';
		}
		appImageListStr += '<a href="javascript:void(0);" onclick="appImageDelete(this);"';
		if (hidden) {
			appImageListStr += ' hidden="' + hidden + '"';
		}
		appImageListStr += '>删除</a>';
		appImageListStr += '<input type="hidden" value=""/>';
		appImageListStr += '</li>';
		return appImageListStr;
	}
	function appImageDelete(obj) {
		if ($("#appImageListPhone li:last img").attr("src") != "resources/img/upload_pic.png") {
			$("#appImageListPhone li:last").after(getAppImageListStr("", true));
		}
		if ($(obj).parent().next().length > 0) {
			deleteUploadFile($(obj).parent()[0].children[1].name);
			$(obj).parent().remove();
		}
	}
	function addAppPic(obj) {
		$("#appImageListPhone li").attr("rel", "");
		$(obj).parent().attr("rel", "current");
		var importPicFile = $(obj).val();

		if (importPicFile == "") {
			alert("请选择要上传的图片！");
			return;
		}

		if (importPicFile.substr(importPicFile.lastIndexOf(".")).toLowerCase() != ".png"
				&& importPicFile.substr(importPicFile.lastIndexOf("."))
						.toLowerCase() != ".jpg") {
			alert("只能上传后缀名为.PNG或.JPG格式的图片！");
			return;
		}

		var fileObj = obj.files[0]; // 获取文件对象

		var FileController = "upload/receiver/"; // 接收上传文件的后台地址 

		// FormData 对象

		var form = new FormData();

		form.append("file", fileObj); // 文件对象

		// XMLHttpRequest 对象

		var xhr = new XMLHttpRequest();

		xhr.open("post", FileController, true);

		xhr.onload = function() {

			var obj = JSON.parse(xhr.response);
			if (obj.success) {

				$("#appImageListPhone li[rel=current] img")
						.attr("src", obj.url);
				deleteUploadFile($("#appImageListPhone li[rel=current] img")[0].name);
				$("#appImageListPhone li[rel=current] img").attr("name",
						obj.file);
				$("#appImageListPhone li[rel=current] a").attr("hidden", false);
				if ($("#appImageListPhone li").length - 3 < 0
						&& $("#appImageListPhone li[rel=current]").index() == ($("#appImageListPhone li").length - 1)) {
					$("#appImageListPhone li[rel=current]").after(
							getAppImageListStr("", true));
				}

			}

		};

		xhr.send(form);

	}
	function validNews() {
		$('#form_create_news').bootstrapValidator({
			message : "invalid",
			fields : {
				title : {
					validators : {
						notEmpty : {
							message : "资讯标题必填"
						},
						stringLength : {
							max : 64,
							message : "资讯标题长度不超过64个字符"
						}
					}

				},
				editor : {
					validators : {
						notEmpty : {
							message : "资讯作者必填"
						},
						stringLength : {
							max : 64,
							message : "资讯作者长度不超过64个字符"
						}
					}

				}

			}

		});
	}
	function DemoSelect2() {
		$('#mark').select2();
	}
	$(document)
			.ready(
					function() {
						iconPath = "";
						iconUrl = "";
						url = "";
						screenshot = "";
						changeScreenshot = false;
						keys = new Array();
						pathValues = new Object();
						urlValues = new Object();
						CKEDITOR.replace('wysiwig_simple');
						LoadFineUploader(FileUpload);
						bigImgPath = "";
						bigImgUrl = "";

						$.ajaxSettings.async = false;

						if (id) {
							$
									.getJSON(
											"rest/news/" + id,
											function(result) {
												news = result.resultValue;
												document
														.getElementById('title').value = news.title;
												document
														.getElementById('editor').value = news.editor;
												document
														.getElementById('local').value = news.local;
												var markOptions = document
														.getElementById('mark').children;
												for ( var i = 0; i < markOptions.length; i++) {
													if (markOptions[i].value
															&& markOptions[i].value == news.mark) {
														markOptions[i].selected = true;
														break;
													}
												}
												iconUrl = news.photo;
												url = news.url;
												screenshot = news.pics;

												if (news.type == 1) {
													document
															.getElementById('type').checked = true;
													document
															.getElementById('bigImg').src = screenshot;
												} else {
													if (screenshot) {
														screenshotArray = screenshot
																.split(",");
														for ( var i = 0; i < screenshotArray.length; i++) {
															$(
																	"#appImageListPhone li:last")
																	.before(
																			getAppImageListStr(
																					screenshotArray[i],
																					false));
														}
														if (screenshotArray.length == 3) {
															$(
																	"#appImageListPhone li:last")
																	.remove();
														}
													}
												}
												CKEDITOR.instances.wysiwig_simple
														.setData(news.body);
											});
						}
						changePage();
						FormLayoutExampleInputLength($(".slider-style"));
						$('.form-control').tooltip();
						LoadSelect2Script(DemoSelect2);
						LoadBootstrapValidatorScript(validNews);
					});
</script>
<!-- 
<script type="text/template" id="qq-template-bootstrap_2">
	<div class="qq-uploader-selector qq-uploader">
        <div class="qq-upload-button-selector btn btn-default">
            <div><i class="fa fa-upload"></i> 上传</div>
        </div>
		<span class="qq-drop-processing-selector qq-drop-processing">
      		<span>(最多只能上传3张新闻图片)</span>
      		<span class="qq-drop-processing-spinner-selector qq-drop-processing-spinner"></span>
    	</span>
        <ul class="qq-upload-list-selector qq-upload-list">
            <li>
                <div class="qq-progress-bar-container-selector">
                    <div class="qq-progress-bar-selector qq-progress-bar"></div>
                </div>
				<img class="qq-thumbnail-selector" qq-max-size="100" qq-server-scale>
                <span class="qq-upload-spinner-selector qq-upload-spinner"></span>
                <span class="qq-upload-file-selector qq-upload-file"></span>
                <span class="qq-upload-size-selector qq-upload-size"></span>
                <a class="qq-upload-cancel-selector qq-upload-cancel" href="#">取消</a>
                <span class="qq-upload-status-text-selector qq-upload-status-text"></span>
            </li>
        </ul>
    </div>
</script>
 -->