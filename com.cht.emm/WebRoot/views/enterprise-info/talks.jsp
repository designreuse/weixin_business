<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">企业信息</a></li>
			<li><a href="#">我的好友</a></li>
		</ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="box no-drop">
			<div class="box-header">
				<div class="box-name">
					<i class="fa fa-user"></i>
					<span>我的好友</span>
				</div>
				<div class="box-icons">
					<a href="javascript:addRow();" title="添加行"> <i class="fa fa-plus-square"></i>
					</a>
					<a href="javascript:delRow();" title="删除行"> <i class="fa fa-minus-square"></i>
					</a>
				</div>
				<div class="no-move"></div>
			</div>
			<div class="box-content no-padding table-responsive">
				<table class="table table-bordered table-striped table-hover table-heading table-datatable" id="datatable-100">
					<thead>
						<tr>
							<th>好友名称</th>
							<th>最近联系</th>
							<th>好友状态</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>好友1</td>
							<td>1天前</td>
							<td>已验证</td>
						</tr>
						<tr>
							<td>好友2</td>
							<td>1分钟前</td>
							<td>已验证</td>
						</tr>
						<tr>
							<td>好友3</td>
							<td>无</td>
							<td>待验证</td>
						</tr>
						<tr>
							<td>好友4</td>
							<td>无</td>
							<td>待验证</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">

//保存dataTable的实例
var dataTable=null;
// Run Datables plugin and create 3 variants of settings
function AllTables(){
	//创建datatable,使用添加的新方法
	dataTable = TestTableX("datatable-100");
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

//示例代码，为表格添加行
function addRow(){
	
	//dataTable.fnAddData([{"好友名称":"1","最近联系":"1","好友状态":"1"}],true);
	dataTable.fnAddData(["<a>1ddddddd</a>","1","1"],true);
};

//示例代码，删除表格的某一行
function delRow(){
	
	dataTable.fnDeleteRow(0,null,true);
}

</script>
