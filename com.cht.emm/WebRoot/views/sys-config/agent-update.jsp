<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="row">
	<div class="col-xs-12 col-sm-12">
		<form class="form-horizontal" id="form_create_agent">
			<fieldset>
				<div class="form-group" id="app_os_group">
					<label class="col-sm-2 control-label">平台类型<span
						class="required">*</span></label>
					<div class="col-sm-8">
						<select class="populate placeholder" id="os" name="ostype">
							<option value="0">ANDROID</option>
						</select>
					</div>
				</div>
				<div class="form-group" id="icon_upload_group">
					<label class="col-sm-2 control-label">应用图标<span
						class="required">*</span></label>
					<div class="col-sm-8">
						<div class=" imgmod-box"
							style="display: block; position: relative; overflow: auto;">
							<input id="importIconFile" name="importIconFile" type="file"
								size="32" class="file-upload" style="width: 57px; height: 57px"
								onchange="javascript:addAgentIcon(this)"> <img
								id="appIcon" src="resources/img/upload.png"
								style="width: 57px; height: 57px">

						</div>
					</div>
				</div>
				<div class="form-group" id="file_upload_apk_group">
					<label class="col-sm-2 control-label">应用程序文件<br>(后缀名为.apk的应用)<span
						class="required">*</span></label>
					<div class="col-sm-4">
						<div id="bootstrapped-fine-uploader3"></div>
					</div>
				</div>
				<!-- 
				<div class="form-group" id="app_screenshot_group">
					<label class="col-sm-2 control-label">应用截图</label>
					<div class="col-sm-8">
						<div id="uploadScreenshot"></div>
					</div>
				</div>
				-->
				 
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
	function addAgentIcon(obj) {
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
	 
	function DemoSelect2() {
		$('#category').select2({
			placeholder : "-- 选择应用分类 --"
		});
		$('#os').select2();
		$('#kind').select2();
	}
	function validApp() {
		$('#form_create_agent').bootstrapValidator({
			message : "invalid",
			fields : {
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
	 
	 
	$(document)
			.ready(
					function() {
						filePath = "";
						fileUrl = "";
						pkgName = "";
						iconPath = "";
						iconUrl = "";
						 
						keys = new Array();
						pathValues = new Object();
						urlValues = new Object();
						 
						LoadFineUploader(FileUpload);
						$.ajaxSettings.async = false;
						 
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
 