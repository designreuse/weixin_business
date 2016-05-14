<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="row">
	<div class="col-xs-12 col-sm-12">
		<form class="form-horizontal" id="form_create_app">
			<fieldset>

				<div class="form-group" id="app_name_group">
					<label class="col-sm-2 control-label">应用名称<span
						class="required">*</span></label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="name" id="name" />
					</div>
				</div>
				<div class="form-group" id="app_kind_group">
					<label class="col-sm-2 control-label">应用种类<span
						class="required">*</span></label>
					<div class="col-sm-8">
						<select class="populate placeholder" id="kind" name="kind"
							onchange="changeKind()">
							<option value="0" selected="selected">原生应用</option>
							<option value="1">HTML应用</option>
							<option value="2">轻应用</option>
						</select>
					</div>
				</div>

				<div class="form-group" id="app_type_group">
					<label class="col-sm-2 control-label">推荐应用</label>
					<div class="col-sm-8">
						<input type="checkbox" id="type">
					</div>
				</div>

				<div class="form-group" id="app_zip_group">
					<label class="col-sm-2 control-label">应用首页地址<span
						class="required">*</span></label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="app_main" />
					</div>
				</div>
				<div class="form-group" id="app_web_group">
					<label class="col-sm-2 control-label">应用网站地址<span
						class="required">*</span></label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="app_web" />
					</div>
				</div>
				<div class="form-group" id="app_category_group">
					<label class="col-sm-2 control-label">应用分类（多选）<span
						class="required">*</span></label>
					<div class="col-sm-8">
						<select class="populate placeholder" multiple="multiple"
							name="apptype" id="category">

						</select>
					</div>
				</div>
				<div class="form-group" id="app_os_group">
					<label class="col-sm-2 control-label">平台类型<span
						class="required">*</span></label>
					<div class="col-sm-8">
						<select class="populate placeholder" id="os" name="ostype">
							<option value="">-- 选择平台类型 --</option>
							<option value="ANDROID">ANDROID</option>
							<option value="IOS">IOS</option>
						</select>
					</div>
				</div>
				<div class="form-group" id="app_os_version_group">
					<label class="col-sm-2 control-label">平台版本号</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="os_version" />
					</div>
				</div>
				<!-- 
				<div class="form-group" id="icon_upload_group">
					<label class="col-sm-2 control-label">应用图标 <span
						class="required">*</span></label>
					<div class="col-sm-4">
						<div id="uploadIcon"></div>
					</div>
				</div> -->
				<div class="form-group" id="icon_upload_group">
					<label class="col-sm-2 control-label">应用图标<span
						class="required">*</span></label>
					<div class="col-sm-8">
						<div class=" imgmod-box"
							style="display: block; position: relative; overflow: auto;">
							<input id="importIconFile" name="importIconFile" type="file"
								size="32" class="file-upload" style="width: 57px; height: 57px"
								onchange="javascript:addAppIcon(this)"> <img
								id="appIcon" src="resources/img/upload.png"
								style="width: 57px; height: 57px">

						</div>
					</div>
				</div>
				<div class="form-group" id="file_upload_apk_group">
					<label class="col-sm-2 control-label">应用程序文件<br>(后缀名为.apk的应用)<span
						class="required">*</span></label>
					<div class="col-sm-4">
						<div id="bootstrapped-fine-uploader"></div>
					</div>
				</div>
				<div class="form-group" id="file_upload_zip_group">
					<label class="col-sm-2 control-label">应用程序文件<br>(后缀名为.zip的应用)<span
						class="required">*</span></label>
					<div class="col-sm-4">
						<div id="bootstrapped-fine-uploader-2"></div>
					</div>
				</div>
				<!-- 
				<div class="form-group" id="app_screenshot_group">
					<label class="col-sm-2 control-label">应用截图</label>
					<div class="col-sm-8">
						<div id="uploadScreenshot"></div>
					</div>
				</div> -->
				<div class="form-group" id="app_screenshot_group">
					<label class="col-sm-2 control-label">应用截图<br>(最多只能上传5张截图)
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
				<div class="form-group" id="app_apk_group_1">
					<label class="col-sm-2 control-label">应用版本名称</label>
					<div class="col-sm-8">
						<input type="text" readonly="true" class="form-control"
							id="version_name" />
					</div>
				</div>
				<div class="form-group" id="app_apk_group_2">
					<label class="col-sm-2 control-label">应用版本号</label>
					<div class="col-sm-8">
						<input type="text" readonly="true" class="form-control"
							id="version_code" />
					</div>
				</div>
				<div class="form-group" id="app_desc_group">
					<label class="col-sm-2 control-label" for="form-styles">应用描述</label>
					<div class="col-sm-8">
						<textarea class="form-control" rows="5" id="wysiwig_simple"></textarea>
					</div>
				</div>
				<div class="form-group" id="app_upgrade_desc_group">
					<label class="col-sm-2 control-label" for="form-styles">版本变更说明</label>
					<div class="col-sm-8">
						<textarea class="form-control" rows="5" id="upgrade_desc"></textarea>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</div>
<script type="text/javascript">
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
	function addAppIcon(obj) {
		var importPicFile = $(obj).val();

		if (importPicFile == "") {
			alert("请选择要上传的图片！");
			return;
		}

		if (importPicFile.substr(importPicFile.lastIndexOf(".")).toLowerCase() != ".png") {
			alert("只能上传后缀名为.PNG格式的图片！");
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
				$("#appIcon").attr("src", obj.url);
				deleteUploadFile(iconPath);
				iconPath = obj.file;
				iconUrl = obj.url;
			}

		};

		xhr.send(form);

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

		if (importPicFile.substr(importPicFile.lastIndexOf(".")).toLowerCase() != ".png") {
			alert("只能上传后缀名为.PNG格式的图片！");
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
				if ($("#appImageListPhone li").length - 5 < 0
						&& $("#appImageListPhone li[rel=current]").index() == ($("#appImageListPhone li").length - 1)) {
					$("#appImageListPhone li[rel=current]").after(
							getAppImageListStr("", true));
				}

			}

		};

		xhr.send(form);

	}
	function DemoSelect2() {
		$('#category').select2({
			placeholder : "-- 选择应用分类 --"
		});
		$('#os').select2();
		$('#kind').select2();
	}
	function validApp() {
		$('#form_create_app').bootstrapValidator({
			message : "invalid",
			fields : {
				name : {
					validators : {
						notEmpty : {
							message : "应用名称必填"
						},
						stringLength : {
							min : 2,
							max : 32,
							message : "应用名称长度在2-32个字符之间"
						}
					}

				},
				kind : {
					validators : {
						notEmpty : {
							message : '应用种类必选'
						}
					}

				},
				apptype : {
					validators : {
						notEmpty : {
							message : '应用分类必选'
						}
					}

				},
				ostype : {
					validators : {
						notEmpty : {
							message : '平台类型必选'
						}
					}

				}

			}

		});
	}
	function changeKind() {
		document.getElementById('app_upgrade_desc_group').hidden = true;
		var kind = $("#apps_new").find("select[id='kind']").find(
				"option:selected").val();
		if (kind == '0') {
			document.getElementById('app_zip_group').hidden = true;
			document.getElementById('file_upload_zip_group').hidden = true;
			document.getElementById('app_web_group').hidden = true;
			document.getElementById('file_upload_apk_group').hidden = false;
			document.getElementById('app_apk_group_1').hidden = false;
			document.getElementById('app_apk_group_2').hidden = false;
		} else if (kind == '1') {
			document.getElementById('app_zip_group').hidden = false;
			document.getElementById('file_upload_zip_group').hidden = false;
			document.getElementById('app_web_group').hidden = true;
			document.getElementById('file_upload_apk_group').hidden = true;
			document.getElementById('app_apk_group_1').hidden = true;
			document.getElementById('app_apk_group_2').hidden = true;
		} else if (kind == '2') {
			document.getElementById('app_zip_group').hidden = true;
			document.getElementById('file_upload_zip_group').hidden = true;
			document.getElementById('app_web_group').hidden = false;
			document.getElementById('file_upload_apk_group').hidden = true;
			document.getElementById('app_apk_group_1').hidden = true;
			document.getElementById('app_apk_group_2').hidden = true;
		}
	}
	function editPage() {
		document.getElementById('name').readOnly = true;
		document.getElementById('kind').readOnly = true;
		document.getElementById('os').readOnly = true;
		document.getElementById('os_version').readOnly = true;
		document.getElementById('icon_upload_group').hidden = true;
		document.getElementById('file_upload_apk_group').hidden = true;
		document.getElementById('file_upload_zip_group').hidden = true;
		document.getElementById('app_os_group').hidden = true;
		document.getElementById('app_os_version_group').hidden = true;
		document.getElementById('app_apk_group_1').hidden = true;
		document.getElementById('app_apk_group_2').hidden = true;
	}
	function upgradePage() {
		document.getElementById('name').readOnly = true;
		document.getElementById('kind').readOnly = true;
		document.getElementById('app_type_group').hidden = true;
		document.getElementById('app_category_group').hidden = true;
		document.getElementById('app_os_group').hidden = true;
		document.getElementById('app_os_version_group').hidden = true;
		document.getElementById('app_screenshot_group').hidden = true;
		document.getElementById('app_desc_group').hidden = true;
		document.getElementById('app_upgrade_desc_group').hidden = false;
		document.getElementById('icon_upload_group').hidden = false;
	}
	$(document)
			.ready(
					function() {
						filePath = "";
						fileUrl = "";
						pkgName = "";
						iconPath = "";
						iconUrl = "";
						category = [];
						screenshot = "";
						screenshotArray = [];
						changeScreenshot = false;
						keys = new Array();
						pathValues = new Object();
						urlValues = new Object();
						CKEDITOR.replace('wysiwig_simple', {
							toolbar : [
									//加粗     斜体，     下划线      穿过线      下标字        上标字
									[ 'Bold', 'Italic', 'Underline', 'Strike',
											'Subscript', 'Superscript' ],
									//数字列表          实体列表            减小缩进    增大缩进
									[ 'NumberedList', 'BulletedList', '-',
											'Outdent', 'Indent' ],
									//左对齐             居中对齐          右对齐          两端对齐
									[ 'JustifyLeft', 'JustifyCenter',
											'JustifyRight', 'JustifyBlock' ],
									//超链接  取消超链接 锚点
									[ 'Link', 'Unlink', 'Anchor' ],
									// 表格       水平线            表情       特殊字符        分页符
									[ 'Table', 'HorizontalRule', 'Smiley',
											'SpecialChar', 'PageBreak' ], '/',
									//样式       格式      字体    字体大小
									[ 'Styles', 'Format', 'Font', 'FontSize' ],
									//文本颜色     背景颜色
									[ 'TextColor', 'BGColor' ],
									//全屏           显示区块
									[ 'Maximize', 'document', 'tools' ] ]
						});
						CKEDITOR.replace('upgrade_desc', {
							toolbar : [
									//加粗     斜体，     下划线      穿过线      下标字        上标字
									[ 'Bold', 'Italic', 'Underline', 'Strike',
											'Subscript', 'Superscript' ],
									//数字列表          实体列表            减小缩进    增大缩进
									[ 'NumberedList', 'BulletedList', '-',
											'Outdent', 'Indent' ],
									//左对齐             居中对齐          右对齐          两端对齐
									[ 'JustifyLeft', 'JustifyCenter',
											'JustifyRight', 'JustifyBlock' ],
									//超链接  取消超链接 锚点
									[ 'Link', 'Unlink', 'Anchor' ],
									// 表格       水平线            表情       特殊字符        分页符
									[ 'Table', 'HorizontalRule', 'Smiley',
											'SpecialChar', 'PageBreak' ], '/',
									//样式       格式      字体    字体大小
									[ 'Styles', 'Format', 'Font', 'FontSize' ],
									//文本颜色     背景颜色
									[ 'TextColor', 'BGColor' ],
									//全屏           显示区块
									[ 'Maximize', 'document', 'tools' ] ]
						});
						LoadFineUploader(FileUpload);
						$.ajaxSettings.async = false;
						changeKind();
						if (id) {
							$
									.getJSON(
											"rest/app/detail/" + id,
											function(result) {
												appDetail = result.resultValue;
												$('#name')[0].value = appDetail.app.name;

												version_code = appDetail.app.version_code;
												version_name = appDetail.app.version_name;
												iconUrl = appDetail.app.icon;
												$("#appIcon").attr("src",
														iconUrl);
												filePath = appDetail.app.url;
												pkgName = appDetail.app.pkg_name;
												kind = appDetail.app.kind;

												screenshot = appDetail.app.screenshot;
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
													if (screenshotArray.length == 5) {
														$(
																"#appImageListPhone li:last")
																.remove();
													}
												}
												if (appDetail.app.type == 1) {
													document
															.getElementById('type').checked = true;
												}
												category = appDetail.app.category
														.split(",");
												var osOptions = document
														.getElementById('os').children;
												for ( var i = 0; i < osOptions.length; i++) {
													if (osOptions[i].value == appDetail.app.os) {
														osOptions[i].selected = true;
														break;
													}
												}
												var kindOptions = document
														.getElementById('kind').children;
												for ( var i = 0; i < kindOptions.length; i++) {
													if (kindOptions[i].value == appDetail.app.kind) {
														kindOptions[i].selected = true;
														break;
													}
												}
												if (appDetail.app.kind == 1) {
													document
															.getElementById('app_main').value = pkgName;
												} else if (appDetail.app.kind == 2) {
													document
															.getElementById('app_web').value = pkgName;
												}
												document
														.getElementById('os_version').value = appDetail.app.os_version;
												CKEDITOR.instances.wysiwig_simple
														.setData(appDetail.app.desc);
											});
							if (edit) {
								document.getElementById('version_name').value = version_name;
								document.getElementById('version_code').value = version_code;
								editPage();
							} else {
								filePath = "";
								oldPkgName = pkgName;
								pkgName = "";
								upgradePage();
							}
						}

						$('#category')[0].innerHTML = "";
						$
								.getJSON(
										"rest/apptype/all",
										function(result) {
											appTypeList = result.resultValue;
											$
													.each(
															appTypeList,
															function(index,
																	appType) {
																var exist = false;
																for ( var i = 0; i < category.length; i++) {
																	if (category[i] == appType.name) {
																		$('#category')[0].innerHTML += "<option value='"+appType.id+"' selected='selected'>"
																				+ appType.name
																				+ "</option>";
																		exist = true;
																		break;
																	}
																}
																if (!exist) {
																	$('#category')[0].innerHTML += "<option value='"+appType.id+"'>"
																			+ appType.name
																			+ "</option>";
																}
															});
										});
						$('.form-control').tooltip();
						LoadSelect2Script(DemoSelect2);
						//LoadBootstrapValidatorScript(DemoFormValidator);
						LoadBootstrapValidatorScript(validApp);
					});
</script>
<script type="text/template" id="qq-template-bootstrap">
	<div class="qq-uploader-selector qq-uploader">
        <div class="qq-upload-button-selector btn btn-default">
            <div><i class="fa fa-upload"></i> 上传</div>
        </div>
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
<!-- 
<script type="text/template" id="qq-template-bootstrap_1">
	<div class="qq-uploader-selector qq-uploader">
        <div class="qq-upload-button-selector btn btn-default">
            <div><i class="fa fa-upload"></i> 上传</div>
        </div>
		<span class="qq-drop-processing-selector qq-drop-processing">
      		<span>(最多只能上传5张截图)</span>
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
<script type="text/template" id="qq-template-bootstrap_2">
	<div class="qq-uploader-selector qq-uploader">
        <div class="qq-upload-button-selector btn btn-default">
            <div><i class="fa fa-upload"></i> 上传</div>
        </div>
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