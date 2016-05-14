/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights
 *          reserved. For licensing, see LICENSE.html or
 *          http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function(config) {
	// Define changes to default configuration here.
	// For complete reference see:
	// http://docs.ckeditor.com/#!/api/CKEDITOR.config
//	config.toolbar = 'Full';
//	
//	config.toolbar_Full = [
//	                       ['Source','-','Save','NewPage','Preview','-','Templates'],
//	                       ['Cut','Copy','Paste','PasteText','PasteFromWord','-','Print','SpellChecker', 'Scayt'],
//	                       ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
//	                       ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select','Button', 'ImageButton', 'HiddenField'],
//	                        '/',
//	                       ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
//	                        ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
//	                        ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
//	                        ['Link','Unlink','Anchor'],
//	                       ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
//	                       '/',
//	                         ['Styles','Format','Font','FontSize'],
//	                        ['TextColor','BGColor']
//	                     ];
	// The toolbar groups arrangement, optimized for two toolbar rows.
	config.toolbarGroups = [ {
		name : 'clipboard',
		groups : [ 'clipboard', 'undo' ]
	}, {
		name : 'editing',
		groups : [ 'find', 'selection', 'spellchecker' ]
	}, {
		name : 'links'
	}, {
		name : 'insert'
	}, {
		name : 'forms'
	}, {
		name : 'tools'
	}, {
		name : 'document',
		groups : [ 'mode', 'document', 'doctools' ]
	}, {
		name : 'others'
	}, '/', {
		name : 'basicstyles',
		groups : [ 'basicstyles', 'cleanup' ]
	}, {
		name : 'paragraph',
		groups : [ 'list', 'indent', 'blocks', 'align', 'bidi' ]
	}, {
		name : 'styles'
	}, {
		name : 'colors'
	}, {
		name : 'about'
	} ];

	// Remove some buttons provided by the standard plugins, which are
	// not needed in the Standard(s) toolbar.
	config.removeButtons = 'Underline,Subscript,Superscript';

	// Set the most common block elements.
	config.format_tags = 'p;h1;h2;h3;pre';

	// Simplify the dialog windows.
	config.removeDialogTabs = 'image:advanced;link:advanced';

	// config.filebrowserImageBrowseUrl =
	// '../ckfinder/ckfinder.html?Type=Images';
	// config.filebrowserFlashBrowseUrl =
	// '../ckfinder/ckfinder.html?Type=Flash';
	// config.filebrowserUploadUrl =
	// '../ckfinder/core/connector/aspx/connector.aspx?command=QuickUpload&type=Files';
	// config.filebrowserImageUploadUrl =
	// '../ckfinder/core/connector/aspx/connector.aspx?command=QuickUpload&type=Images';
	// config.filebrowserFlashUploadUrl =
	// '../ckfinder/core/connector/aspx/connector.aspx?command=QuickUpload&type=Flash';
	// config.filebrowserWindowWidth = '800'; //“浏览服务器”弹出框的size设置
	// config.filebrowserWindowHeight = '500';

	config.filebrowserUploadUrl = "/upload/receiver";

	var pathName = window.document.location.pathname;
	// 获取带"/"的项目名，如：/uimcardprj
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	config.filebrowserImageUploadUrl = projectName + '/upload/receiver'; // 固定路径

	config.extraPlugins = 'justify';
};
