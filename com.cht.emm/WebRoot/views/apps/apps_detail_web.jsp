<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="dashboard-header" class="row">
	<div class="col-xs-10 col-sm-4">
		<h3 id="name"></h3>
	</div>
</div>
<div class="row-fluid">
	<div id="dashboard_links" class="col-xs-12 col-sm-2 pull-right">
		<ul class="nav nav-pills nav-stacked">
			<li class="active"><a href="#" class="link_dash" id="overview">摘要信息</a></li>
			<li><a href="#" class="link_dash" id="clients">用户评级</a></li>
		</ul>
	</div>
	<div id="dashboard_tabs" class="col-xs-12 col-sm-10">
		<!--Start Dashboard Tab 1-->
		<div id="dashboard-overview" class="row"
			style="display: block; position: relative;">
			<div class="row">
				<div class="col-xs-6">
					<div class="row">
						<div class="col-xs-12">
							<div class="row">
								<div class="col-xs-12">
									<div class="box no-drop">
										<div class="box-content">
											<h3>
												<p class="page-header">
													<i class="fa fa-exclamation-circle"></i> 应用信息
												</p>
											</h3>
											<h4 style="font-weight: bold">
												<p>应用网站地址</p>
											</h4>
											<p id="pkg_name"></p>
											<h4 style="font-weight: bold">
												<p>应用分类</p>
											</h4>
											<p id="category"></p>
											<h4 style="font-weight: bold">
												<p>发布时间</p>
											</h4>
											<p id="create"></p>
											<h4 style="font-weight: bold">
												<p>更新时间</p>
											</h4>
											<p id="update"></p>
											<h4 style="font-weight: bold">
												<p>发布者</p>
											</h4>
											<p id="publisher"></p>
											<h4 style="font-weight: bold">
												<p>描述</p>
											</h4>
											<p id="desc"></p>

										</div>
									</div>
								</div>
							</div>

						</div>
					</div>
				</div>
				<div class="col-xs-6">

					<div class="row">
						<div class="col-xs-12">
							<div class="box no-drop">
								<div class="box-content">
									<h3>
										<p class="page-header">
											<i class="fa fa-star"></i> 用户评级
										</p>
									</h3>
									<p id="score" />
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div class="box">
								<div class="box-content">
									<h3>
										<p class="page-header">
											<i class="fa fa-star"></i> 应用截图
										</p>
									</h3>
									<div class=" imgmod-box" id="phone-imgs"
										style="display: block; position: relative; overflow: auto;">
										<ul class="img-list scroll-img-list clearfix"
											id="appImageListPhone">
											<li class="img-item"><img
												src="resources/img/upload_pic.png">
										</ul>
									</div>
									<div class=" imgmod-box" id="phone-imgs"
										style="display: block; position: relative; overflow: auto;">
										<ul class="img-list scroll-img-list clearfix"
											id="appImageListPhone_1">
											<li class="img-item"><img
												src="resources/img/upload_pic.png">
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--End Dashboard Tab 1-->
		<!--Start Dashboard Tab 2-->
		<div id="dashboard-clients" class="row"
			style="display: none; position: relative;">
			<div class="box no-drop">
				<div class="box-content" style="margin-bottom: 0.5em">
					<div class="row">
						<div class="col-xs-10"></div>
						<div class="col-xs-2 input-group searchInput">
							<input type="text" class="form-control" placeholder="搜索">
							<span class="input-group-addon"
								onClick="searchAppScoreTable(this)"><i
								class="fa fa-search"></i></span>
						</div>
					</div>
				</div>
				<div class="box-content no-padding table-responsive">
					<table
						class="table table-bordered table-striped table-hover table-heading table-datatable"
						id="appScoreList">
						<thead>
							<tr>
								<th>评级</th>
								<th>备注</th>
								<th>创建日期</th>
								<th>用户</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!--End Dashboard Tab 2-->
	</div>
	<div class="clearfix"></div>
</div>
<script type="text/javascript">
	function getAppImageListStr(src) {
		var appImageListStr = '<li class="img-item">';
		if (src) {
			appImageListStr += '<img src="'+src+'"/>';
		} else {
			appImageListStr += '<img src="resources/img/upload_pic.png"/>';
		}
		appImageListStr += '</li>';
		return appImageListStr;
	}
	var appScoreTable = null;
	function searchAppScoreTable(o) {
		OSettings = appScoreTable.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		appScoreTable._fnFilterComplete(OSettings, {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		});

	}
	appScoreTableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "rest/app/appScores/pages/" + id,
		"aaSorting" : [ [ 0, "asc" ] ],
		"aLengthMenu" : [ [ 10, 20, 50 ], [ 10, 20, 50 ] ],
		"sDom" : "rt<'box-content'<'col-sm-12'<'tool_box_pageInfo' ilp>> <'clearfix'>>",
		"sPaginationType" : "bootstrap",
		"oLanguage" : {
			"sSearch" : "",
			"sLengthMenu" : "",
			"sInfo" : "",
			"sInfoEmpty" : ""
		},
		"aoColumns" : [ {
			"mData" : "score",
			"mRender" : function(data, type, full) {
				return getStar(data);
			},
			"bSortable" : true,
			"bSearchable" : false
		}, {
			"mData" : "comment",
			"bSortable" : false,
			"bSearchable" : true
		}, {
			"mData" : "time",
			"bSortable" : true,
			"bSearchable" : false
		}, {
			"mData" : "user",
			"bSortable" : true,
			"bSearchable" : true
		} ]
	};
	function AllTables() {
		appScoreTable = $("#appScoreList").dataTable(appScoreTableSetting);
		LoadSelect2Script(MakeSelect);
	}
	function loadAppDetail(id) {
		$.ajaxSettings.async = false;
		$
				.getJSON(
						"rest/app/detail/" + id,
						function(result) {
							appDetail = result.resultValue;
							document.getElementById('name').innerHTML = appDetail.app.name;
							document.getElementById('pkg_name').innerHTML = appDetail.app.pkg_name;
							document.getElementById('category').innerHTML = appDetail.app.category;
							document.getElementById('create').innerHTML = appDetail.app.create;
							document.getElementById('update').innerHTML = appDetail.app.update;
							document.getElementById('publisher').innerHTML = appDetail.app.publisher;
							document.getElementById('desc').innerHTML = appDetail.app.desc;
							document.getElementById('score').innerHTML = getStar(appDetail.app.score);
							var screenshot = appDetail.app.screenshot;
							if (screenshot) {
								screenshotArray = screenshot.split(",");
								for ( var i = 0; i < screenshotArray.length; i++) {
									if (i < 3) {
										$("#appImageListPhone li:last")
												.before(
														getAppImageListStr(screenshotArray[i]));
									} else {
										$("#appImageListPhone_1 li:last")
												.before(
														getAppImageListStr(screenshotArray[i]));
									}
								}
							}
							$("#appImageListPhone li:last").remove();
							$("#appImageListPhone_1 li:last").remove();
						});
	}
	$(document).ready(function() {
		loadAppDetail(id);
		LoadDataTablesScripts(AllTables);
		var ids = new Array();
		$("a.link_dash").click(function() {
			if (ids != null && ids.length == 0) {
				$("a.link_dash").each(function() {
					ids.push($(this).attr("id"));
				});
			}

			id = $(this).attr("id");
			for ( var i in ids) {
				$("#dashboard-" + ids[i]).hide();
			}
			$(this).closest('ul').find('li').each(function() {
				$(this).removeClass('active');
			});
			$("#dashboard-" + id).show();
			$("#" + id).parent('li').addClass('active');
		});
	});
</script>
