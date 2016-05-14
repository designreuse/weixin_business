<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!--Start Breadcrumb-->
<style>
li{
	 list-style: none;
}
</style>
<div id="dashboard-header" class="row">
	<div class="col-xs-10 col-sm-2">
		<h3>${device.name }</h3>
	</div>
</div>
<div class="row-fluid">
	<div id="dashboard_links" class="col-xs-12 col-sm-2 pull-right">
		<ul class="nav nav-pills nav-stacked">
			<li class="active"><a href="#" class="link_dash" id="overview">摘要</a></li>
			<li><a href="#" class="link_dash" id="clients">应用</a></li>
			<li><a href="#" class="link_dash" id="graph">数据审计</a></li>
		</ul>
	</div>
	<div id="dashboard_tabs" class="col-xs-12 col-sm-10">
		<!--Start Dashboard Tab 1-->
		<div id="dashboard-overview" class="row" style="display: block; position: relative;">
<div class="row">
	<div class="col-xs-6">
				<div class="row">
					<div class="col-xs-12">
						<div class="box no-drop">
							<div class="box-content">
								<h3><p class="page-header"><i class="fa fa-exclamation-circle"></i> 设备信息</p></h3>
								<h4><p>启动时间</p></h4>
								<p>${device.start_time }</p>
								<h4><p>启用地址</p></h4>
								<p>${device.start_addr }</p>
								<h4><p>当前地址</p></h4>
								<p>${device.addr }</p>
							</div>
						</div>
					</div>
				</div>
	</div>
	<div class="col-xs-6">
		<div class="row">
			<div class="col-xs-12">
				<div class="row">
					<div class="col-xs-12">
						<div class="box no-drop">
							<div class="box-content">
								<h3><p class="page-header"><i class="fa fa-key"></i> 安全性</p></h3>
								<p>
									<c:choose>
										<c:when test="${device.compliance ==1 }"><i class="fa  fa-check"></i> 设备合规</c:when>
										<c:otherwise><i class="fa fa-times"></i> 设备不合规</c:otherwise>
									</c:choose>
								</p>
								<p>
									<c:choose>
										<c:when test="${device.password ==1 }"><i class="fa  fa-check"></i> 密码已修改</c:when>
										<c:otherwise><i class="fa fa-times"></i> 密码未修改</c:otherwise>
									</c:choose>
								</p>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<div class="box no-drop">
							<div class="box-content">
								<h3><p class="page-header"><i class="fa  fa-signal"></i> 网络</p></h3>
								<p>
									<c:choose>
										<c:when test="${device.net ==1 }"> 3G</c:when>
										<c:otherwise> WIFI</c:otherwise>
									</c:choose>
								</p>
							</div>
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
		<div id="dashboard-clients" class="row" style="display: none; position: relative;">
			
			<table
				class="table table-bordered table-striped table-hover table-heading table-datatable"
				id="datatable-10">
				<thead>
					<tr>
						<th>应用名称</th>
						<th>应用状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var ="app" items="${device.apps }">
						<tr>
							<td>${app.name}</td>
							<td>
							
								<c:choose>
									<c:when test="${app.status =='1' }">下载安装</c:when>
									<c:when test="${app.status =='2' }">升级安装</c:when>
									<c:when test="${app.status =='4' }">打开应用</c:when>
									<c:when test="${app.status =='8' }">删除应用</c:when>
									<c:when test="${app.status =='16' }">使用</c:when>
									<c:when test="${app.status =='32' }">业务收藏</c:when>
									<c:when test="${app.status =='64' }">取消收藏</c:when>
									<c:when test="${app.status =='128' }">置顶</c:when>
									<c:when test="${app.status =='256' }">取消置顶</c:when>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!--End Dashboard Tab 2-->
		<!--Start Dashboard Tab 3-->
		<div id="dashboard-graph" class="row" style="display: none; position: relative;" >
			<table
				class="table table-bordered table-striped table-hover table-heading table-datatable"
				id="datatable-11">
				<thead>
					<tr>
						<th>接入用户</th>
						<th>操作时间</th>
						<th>接入系统</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var ="log" items="${device.logs }">
						<tr>
							<td>${log.username}</td>
							<td>${log.time}</td>
							<td>${log.system}</td>
						</tr>
					</c:forEach>
				</tbody>
				
			</table>
		</div>
		<!--End Dashboard Tab 3-->
	</div>
	<div class="clearfix"></div>
</div>
<!--End Dashboard 2 -->
<div style="height: 40px;"></div>
<script type="text/javascript">
// Array for random data for Sparkline
var sparkline_arr_1 = SparklineTestData();
var sparkline_arr_2 = SparklineTestData();
var sparkline_arr_3 = SparklineTestData();
function AllTables(){
	TestTable10();
	TestTable11();
}
$(document).ready(function() {
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
	// Make all JS-activity for dashboard
	DashboardTabChecker();
	// Load Knob plugin and run callback for draw Knob charts for dashboard(tab-servers)
	LoadKnobScripts(DrawKnobDashboard);
	// Load Sparkline plugin and run callback for draw Sparkline charts for dashboard(top of dashboard + plot in tables)
	LoadSparkLineScript(DrawSparklineDashboard);
	// Load Morris plugin and run callback for draw Morris charts for dashboard
	LoadMorrisScripts(MorrisDashboard);
	// Make beauty hover in table
	$("#ticker-table").beautyHover();
});
</script>
