<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="row">
	<div class="col-xs-12">
		<div class="box no-drop">
		
			<div class="box-content" style="margin-bottom: 0.5em">
				<div class="row">
					<div class="col-xs-10"></div>
					<div class="col-xs-2 input-group searchInput">
						<input type="text" class="form-control"  placeholder="搜索"> <span
							class="input-group-addon" onClick="search(this)"><i
							class="fa fa-search"></i></span>
					</div>
				</div>

			</div> 
			<div class="box-content no-padding table-responsive">
				<table
					class="table table-bordered table-striped table-hover table-heading table-datatable"
					id="appTypeAppsList">
					<thead>
						<tr>
						<!-- 
							<th><input type="checkbox" class="selectAll"
								onclick="selectAll(this)"></th> -->
							<th>名称</th>
							<th>发布者</th>
							<th>版本</th>
							<th>平台</th>
							<th>推荐应用</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var appTypeAppsTable = null;
	function search(o) {
		OSettings = appTypeAppsTable.DataTable.models.oSettings;
		var oPreviousSearch = OSettings.oPreviousSearch;
		val = $(o).closest("div").find("input[type=text]").first().val();

		OSettings.oPreviousSearch = {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		};
		OSettings.sSearch = val;
		appTypeAppsTable._fnFilterComplete(OSettings, {
			"sSearch" : val,
			"bRegex" : false,
			"bSmart" : true,
			"bCaseInsensitive" : true
		});

	}
	tableSetting = {
		"bprocessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "rest/apptype/apps/pages/" + appTypeId,
		"aaSorting" : [ [ 0, "asc" ] ],
		"aLengthMenu" : [ [ 10, 20, 50 ], [ 10, 20, 50 ] ],
		"sDom" : "rt<'box-content'<'col-sm-12'<'tool_box_pageInfo' ilp>> <'clearfix'>>",
		"sPaginationType" : "bootstrap",
		"oLanguage" : {
			"sSearch" : ""
		},
		"aoColumns" : [
				/*{
					"mData" : "id",
					"mRender" : function(data, type, full) {
						return "<input type=\"checkbox\" value=\""+data+"\">";
					},
					"bSortable" : false,
					"bSearchable" : false
				},*/
				{
					"mData" : "name",
					"mRender" : function(data, type, full) {
						return "<a href=\"javascript:getAppDetails('" + full.id
								+ "','" + full.pkg_name + "')\">" + data
								+ "</a>";
					}
				}, {
					"mData" : "publisher",
					"bSearchable" : false,
					"bSortable" : false
				}, {
					"mData" : "version_name",
					"bSearchable" : false,
					"bSortable" : false
				}, {
					"mData" : "os",
					"bSearchable" : false,
					"bSortable" : false
				}, {
					"mData" : "type",
					"mRender" : function(data, type, row) {
						return data == 1 ? "是" : "否";
					},
					"bSearchable" : false,
					"bSortable" : false
				}

		]
	};
	function getAppDetails(appId, pkgName) {
		id = appId;
		pkg_name = pkgName;
		OpenBigDialog("应用详情", "console/apps_detail", 'apps_detail', true, true);
	}

	function loadAppsByType() {
		appTypeAppsTable = $("#appTypeAppsList").dataTable(tableSetting);
		LoadSelect2Script(MakeSelect);
	}
	$(document).ready(function() {
		LoadDataTablesScripts(loadAppsByType);
	});
</script>
