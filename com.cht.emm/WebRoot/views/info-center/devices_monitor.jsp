<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">监控分析</a></li>
			<li><a href="#">设备监控</a></li>
		</ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="box no-drop">
			<div class="box-header">
				<div class="box-name">
					<i class="fa fa-mobile"></i>
					<span>设备列表</span>
				</div>
				<div class="no-move"></div>
			</div>
			<div class="box-content no-padding table-responsive">
				<table class="table table-bordered table-striped table-hover table-heading table-datatable" id="datatable-4">
					<thead>
						<tr>
							<th>设备标识</th>
							<th>操作系统</th>
							<th>品牌</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="device" items="${devices}">
							<tr>
								<td>${device.device_id }</td>
								<td>${device.os_name }</td>
								<td>${device.brand }</td>
								
								<td><a href="javascript:monitorDevice('${device.device_id }')" class="ajax_link">监控</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function monitorDevice(id) {
	OpenBigDialog("设备监控", 'console/device_monitor?id='+id, 'device_monitor', true, true);
}


// Run Datables plugin and create 3 variants of settings
function AllTables(){
	TestTable4();
	LoadSelect2Script(MakeSelect2);
}
function MakeSelect2(){
	MakeSelect();
	$('.dataTables_filter').each(function(){
		$(this).find('label input[type=text]').attr('placeholder', '搜索');
	});
}
$(document).ready(function() {
	// Load Datatables and run plugin on tables 
	LoadDataTablesScripts(AllTables);
	// Add Drag-n-Drop feature
	//WinMove();
});
</script>
