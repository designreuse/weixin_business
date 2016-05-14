<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
.boxname_float {
	border: 0px;
	background: #FFF;
}

.dash_content {
	padding: 10px 5px;
	height: 250px;
}

.dash_labels {
	display: block;
	fill: rgb(102, 102, 102);
	font-family: Ubuntu, sans-serif;
	font-size: 12px;
	height: 13px;
	line-height: 11px;
	white-space: nowrap;
	width: 36px;
	padding-left: 15px;
}

.dash_labels_2 {
	display: block;
	fill: rgb(102, 102, 102);
	font-family: Ubuntu, sans-serif;
	font-size: 12px;
	height: 13px;
	line-height: 11px;
	white-space: nowrap;
	width: 36px;
}

.dash_value_big {
	font-size: 4.5em;
	font-weight: 200;
	color: rgb(17, 168, 224);
}

.dash_value_middle {
	color: rgb(18, 99, 137);
	font-size: 3em;
}
</style>

<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#">监控分析</a>
			</li>
			<li><a href="#">信息中心</a>
			</li>
		</ol>
	</div>
</div>

<div class="row">
	<div class="box no-drop">
		<div class="box-header boxname_float">
			<div class="box-name">
				<span>应用分析</span>
			</div>
			<div class="box-icons">
				<a href="#"> <i class="fa fa-user"></i> </a> <a class="expand-link"><i
					class="fa fa-expand"></i>
				</a>
			</div>
		</div>
		<div class="box-content">
			<div class="row">
				<div class="col-xs-8"
					style="border: 1px solid #d6d6d6;border-radius: 3px;">
					应用下载Top10 <i class="fa fa-refresh"></i>
					<div id="app_dl_top10_view" class="dash_content"></div>
				</div>
				<div class="col-xs-4"
					style="border: 1px solid #d6d6d6;border-radius: 3px;">
					应用概况<i class="fa fa-refresh"></i>
					<div id="register_history_classify_view" class="dash_content">
						<div class="row">
							<div class="col-xs-6" style="padding:45px 0px;">
								<div style="float:right;">
									<div class="dash_labels_2" style="text-align:right;width:100%">过去一天</div>
									<div class="dash_value_big">${day }</div>
								</div>
							</div>
							<div class="col-xs-6"
								style="border-left:3px solid #ccc;padding:0px 20px;">
								<div class="row"
									style="height:100px;padding: 10px 0;font-weight: 200;">
									<p class="dash_labels_2">最近一周</p>
									<p class="dash_value_middle">${week }</p>
								</div>
								<div class="row"
									style="height:100px;padding: 10px 0;font-weight: 200;">
									<p class="dash_labels_2">最近一个月</p>
									<p class="dash_value_middle">${month }</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="row">
	<div class="box no-drop">
		<div class="box-header boxname_float">
			<div class="box-name boxname_float">
				<span>设备概况</span>
			</div>
			<div class="box-icons">
				<a href="#"> <i class="fa fa-user"></i> </a> <a class="expand-link"><i
					class="fa fa-expand"></i>
				</a>
			</div>
		</div>
		<div class="box-content">
			<div class="row">
				<div class="col-xs-4"
					style="border: 1px solid #d6d6d6;border-radius: 3px;">
					设备品牌<i class="fa fa-refresh"></i>
					<div id="device_barnd_view" class="dash_content"></div>
				</div>
				<div class="col-xs-4"
					style="border: 1px solid #d6d6d6;border-radius: 3px;">
					在线用户<i class="fa fa-refresh"></i>
					<div id="device_online_view" class="dash_content"></div>
				</div>

				<div class="col-xs-4"
					style="border: 1px solid #d6d6d6;border-radius: 3px;">
					系统 <i class="fa fa-refresh"></i>
					<div id="device_pie_ops_view" class="dash_content"></div>
				</div>

			</div>
			<div class="row">
				<div class="col-xs-4"
					style="border: 1px solid #d6d6d6;border-radius: 3px;">
					合规性<i class="fa fa-refresh"></i>
					<div id="device_pie_legal_view" class="dash_content"></div>
				</div>
				<div class="col-xs-4"
					style="border: 1px solid #d6d6d6;border-radius: 3px;">
					报警 <i class="fa fa-refresh"></i>
					<div id="device_pie_alarm_view" class="dash_content"></div>
				</div>
				<div class="col-xs-4"></div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	function DrawAllMorrisCharts() {
		DeviceCountView();
		DeviceOpsView();
		top10AppView();
		DeviceLegalView();
		DeviceAlarmView();
		DeviceOnlineView();
	}
	$(document).ready(function() {
		LoadMorrisScripts(DrawAllMorrisCharts);
		WinMove();
	});

	function DeviceCountView() {

		var title = new Array();
		var max = 0;
		var num = 0;

		$.ajaxSettings.async = false;
		$
				.getJSON(
						"rest/infocenter/device/brand",
						function(result) {
							var counts = result.resultValue;
							var datas = [];
							if (counts != null) {
								for (i = 0; i < counts.length; i++) {
									if (counts[i].count > max) {
										max = counts[i].count;
									}
									count = new Array(counts[i].name,
											counts[i].count, color_cold[i]);
									title.push(count);
									++num;
								}

								for ( var i = 0; i < num; i++) {
									p = title[i][1] / max * 100;
									if (p < 8) {
										p = 7;
									}
									$("#device_barnd_view")
											.append(
													"<div class='row' style=padding:5px;><div id='l"
															+ i
															+ "_0' style='height:25px;width:"
															+ p
															+ "%;background:"
															+ title[i][2]
															+ ";display:block;text-align:right;vertical-align:middle;padding-right: 5px;padding-top: 8px;'>"
															+ title[i][1]
															+ "</div></div><div class='row dash_labels' >"
															+ title[i][0]
															+ "</div>");
								}
							} else {
								$("#device_barnd_view").append("没有数据");
							}
						});

	}

	function top10AppView() {
		var title = new Array();

		$.ajaxSettings.async = false;
		$.getJSON("rest/infocenter/app/tops", function(result) {
			var counts = result.resultValue;
			var datas = [];
			if (counts != null) {
				for (i = 0; i < counts.length; i++) {
					count = new Array(counts[i].name, counts[i].count,
							color_cold[i]);
					title.push(count);
					datas.push({
						x : counts[i].name,
						y : counts[i].count
					});
				}

				Morris.Bar({
					element : 'app_dl_top10_view',

					data : datas,
					xkey : 'x',
					ykeys : [ 'y' ],
					labels : [ '数量' ],
					barColors : function(row, series, type) {
						if (type === 'bar') {
							var red = Math.ceil(255 * row.y / this.ymax);
							return 'rgb(' + red + ',8,4)';
						} else {
							return '#000';
						}
					}
				});
			} else {
				$("#app_dl_top10_view").append("没有数据");
			}

		});

	}

	function DeviceOpsView() {

		var title = new Array();
		$.ajaxSettings.async = false;
		var total = 0;
		$.getJSON("rest/infocenter/device/ops", function(result) {
			var counts = result.resultValue;
			var datas = [];
			if (counts != null) {
				for (i = 0; i < counts.length; i++) {
					count = new Array(counts[i].name, counts[i].count,
							color_cold[i]);
					title.push(count);
					datas.push({
						label : counts[i].name,
						value : counts[i].count
					});
					total += counts[i].count;
				}
				Morris.Donut({
					element : 'device_pie_ops_view',

					data : datas,
					backgroundColor : '#ccc',
					labelColor : '#060',
					formatter : function(x) {
						return (x / total * 100).toPrecision(3) + "%";
					}
				});
			} else {
				$("#device_pie_ops_view").append("没有数据");
			}

		});

	}
	function DeviceLegalView() {

		var title = new Array();
		var total = 0;
		$.ajaxSettings.async = false;
		$.getJSON("rest/infocenter/device/count", function(result) {
			var counts = result.resultValue;
			var datas = [];
			if (counts != null && counts.length > 0) {
				for (i = 0; i < counts.length; i++) {
					count = new Array(counts[i].name, counts[i].count,
							color_cold[i]);
					title.push(count);
					datas.push({
						label : counts[i].name,
						value : counts[i].count
					});
					total += counts[i].count;
				}
				Morris.Donut({
					element : 'device_pie_legal_view',
					data : datas,
					backgroundColor : '#ccc',
					labelColor : '#060',
					colors : [ '#f22','#179' ],
					formatter : function(x) {
						return (x / total * 100).toPrecision(3) + "%";
					}
				});

			} else {
				$("#device_pie_legal_view").append("没有数据");
			}
		});

	}

	function DeviceAlarmView() {
		var title = new Array();
		var total = 0;
		$.ajaxSettings.async = false;
		$.getJSON("rest/infocenter/device/alarm", function(result) {
			var counts = result.resultValue;
			var datas = [];
			if (counts != null) {
				for (i = 0; i < counts.length; i++) {
					count = new Array(counts[i].name, counts[i].count,
							color_cold[i]);
					title.push(count);
					datas.push({
						label : counts[i].name,
						value : counts[i].count
					});
					total += counts[i].count;
				}
				Morris.Donut({
					element : 'device_pie_alarm_view',

					data : datas,
					backgroundColor : '#ccc',
					labelColor : '#060',
					colors : [ '#f22','#179'  ],
					formatter : function(x) {
						return (x / total * 100).toPrecision(3) + "%";
					}
				});
			} else {
				$("#device_pie_alarm_view").append("没有数据");
			}

		});

	}

	function DeviceOnlineView() {
		var title = new Array();
		var total = 0;
		$.ajaxSettings.async = false;
		$.getJSON("rest/infocenter/device/online", function(result) {
			var counts = result.resultValue;
			var datas = [];
			if (counts != null) {
				for (i = 0; i < counts.length; i++) {
					count = new Array(counts[i].name, counts[i].count,
							color_cold[i]);
					title.push(count);
					datas.push({
						label : counts[i].name,
						value : counts[i].count
					});
					total += counts[i].count;
				}
				Morris.Donut({
					element : 'device_online_view',

					data : datas,
					backgroundColor : '#ccc',
					labelColor : '#060',
					colors : [ '#f22','#179' ],
					formatter : function(x) {
						return (x / total * 100).toPrecision(3) + "%";
					}
				});
			} else {
				$("#device_online_view").append("没有数据");
			}
		});

	}
</script>