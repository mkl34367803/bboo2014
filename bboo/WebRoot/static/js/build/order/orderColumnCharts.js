
// 获取搜索数据
function getSearchData() {
	var data = {};
	var flightClass = $.trim($("input[name='flightClass']:checked").val());
	var startDate = $.trim($("#startDate").val());
	var endDate = $.trim($("#endDate").val());
	var chartType = $.trim($("input[name='chartType']:checked").val());
	var type = $.trim($("input[name='type']:checked").val());
	var isPercent = $.trim($("input[name='isPercent']:checked").val());
	var tongQi = "";
	var showType = "";
	$("input[name='showType']:checked").each(function() {
		var v = $(this).val();
		showType += v + ",";
		if(v == "tongQi") {
			tongQi = v;
		}
	});
	showType = showType.substring(0, showType.length-1);
	data.flightClass = flightClass;
	data.startDate = startDate;
	data.endDate = endDate;
	data.chartType = chartType;
	data.type = type;
	data.isPercent = isPercent;
	data.showType = showType;
	data.tongQi = tongQi;
	return data;
}

function isContain(str, contain) {
	var strArr = str.split(",");
	for ( var i = 0; i < strArr.length; i++) {
		if (strArr[i] == contain) {
			return true;
		}
	}
	return false;
}

function getChartType(charType) {
	if (charType == "carrier") {
		return "航司";
	} else if (charType == "purchasePlace") {
		return "采购渠道";
	} else if (charType == "operator") {
		return "出票员";
	} else if (charType == "ageDes") {
		return "乘机人类型";
	} else if (charType == "distributor") {
		return "分销渠道";
	} else if (charType == "shopName") {
		return "店铺名称";
	} else if (charType == "createTime") {
		return "订单生成时间";
	}
	return charType;
}

function getType(type) {
	if (type == "ticketNum") {
		return "票量";
	} else if (type == "profit") {
		return "利润";
	} else if (type == "saleroom") {
		return "销售额";
	}
}

// 获取图标数据
function getChartData(searchData){
	var data = {};
	var yTitle,toolTip;
	if(isContain(searchData.showType, "percent") || isContain(searchData.showType, "growthRate")){
		if (searchData.type == "ticketNum") {
			yTitle = "票量(%)";
			toolTip = "%";
		} else if (searchData.type == "profit") {
			yTitle = "利润(%)";
			toolTip = "%";
		} else if (searchData.type == "saleroom") {
			yTitle = "销售额(%)";
			toolTip = "%";
		}
	} else {
		if (searchData.type == "ticketNum") {
			yTitle = "票量(张)";
			toolTip = "张";
		} else if (searchData.type == "profit") {
			yTitle = "利润(元)";
			toolTip = "元";
		} else if (searchData.type == "saleroom") {
			yTitle = "销售额(元)";
			toolTip = "元";
		}
	}
	if (searchData.tongQi == "tongQi") {
		data.title = {
				text: "去年同期"+getType(searchData.type)+"统计",
				x: -20 //center
		};
	} else {
		data.title = {
				text: getType(searchData.type)+"统计",
				x: -20 //center
		};
	}
	data.subtitle = {
			text: "按"+getChartType(searchData.chartType)+"统计"+getType(searchData.type),
			x: -20
	};
	data.yAxis = {
			title: {
				text: yTitle
			},
			plotLines: [{
				value: 0,
				width: 1,
				color: '#808080'
			}]
	};
	data.tooltip = {
			valueSuffix: toolTip
	};
	data.legend = {
			layout: 'vertical',
			align: 'right',
			verticalAlign: 'middle',
			borderWidth: 0
	};
	return data;
}

function getData(searchData) {
	$.ajax({
		type: "post",
		dataType: "json",
		data: searchData,
		async: false,
		url: ctx + "/order/gj-order-charts!getStatisticCharts.do",
		success:function(result){
			if(result.error) {
				alert(result.error);
			}
			if(result.success){
				var data = {};
				if (isContain(searchData.showType, "day")) {
					data = getSeries(result.list);
				} else {
					var categories = [],seriesData = [];
					$.each(result.list, function(i, item) {
						if (searchData.type == "ticketNum") {
							categories[i] = item.categories;
							seriesData[i] = parseFloat(item.ticketNum);
						} else if (searchData.type == "profit") {
							categories[i] = item.categories;
							seriesData[i] = parseFloat(item.profit);
						} else if (searchData.type == "saleroom") {
							categories[i] = item.categories;
							seriesData[i] = parseFloat(item.saleroom);
						}
					});
					data.categories = categories; 
					data.series = [{
						name: '莫林航空',
						data: seriesData
					}];
				}
				getCharts(searchData, data);
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert("请求出错了");
		}
	});
}

// 获取显示类型为日期的数据 {"categories":["2016-09-21","2016-09-22"],"series":[{"name":"MF","data":[0,"1"]},{"name":"EU","data":["1","3"]}]}
function getSeries(list) {
	var data = {};
	var series = [];
	var categories = [];
	if (list.series) {
		$.each(list.series, function(i, item) {
			var seriesData = [];
			$.each(item.data, function(j, jitem) {
				seriesData.push(parseFloat(jitem));
			});
			series.push({"name":item.name,"data":seriesData});
		});
		categories = list.categories;
	}
	data.categories = categories;
	data.series = series;
	return data;
}


// 获取X轴数据
function getXAxis(list) {
	var categories = [];
	$.each(item.categories, function(j, cate) {
		var len = categories.length;
		if (len > 0) {
			if(!isContain(categories.toString(), cate)) {
				categories.push(cate);
			}
		} else {
			categories.push(cate);
		}
	});
	return categories;
}


function getCharts(searchData, data) {
	var chartData = getChartData(searchData);
	if (searchData.tongQi == "tongQi") {
		$('#tongqiContainer').highcharts({
			chart: {
				type: "column"
			},
			title: chartData.title,
			subtitle: chartData.subtitle,
			xAxis: {
				categories: data.categories
			},
			yAxis: chartData.yAxis,
			tooltip: chartData.tooltip,
			legend: chartData.legend,
			series: data.series
		});
	} else {
		$('#container').highcharts({
			chart: {
				type: "column"
			},
			title: chartData.title,
			subtitle: chartData.subtitle,
			xAxis: {
				categories: data.categories
			},
			yAxis: chartData.yAxis,
			tooltip: chartData.tooltip,
			legend: chartData.legend,
			series: data.series
		});
		$('#tongqiContainer').html("");
	}
}

function getLastYear(date) {
	var year = date.substring(0, 4);
	var ly = parseInt(year) - 1;
	var last = ly + date.substring(4, date.length);
	return last;
}

function getLYSearchData(searchData) {
	searchData.startDate = getLastYear(searchData.startDate);
	searchData.endDate = getLastYear(searchData.endDate);
	return searchData;
}

$(function () {
	$("#searchButton").click(function() {
		var searchData = getSearchData();
		if (searchData.tongQi == "tongQi") {
			searchData.tongQi = "";
			getData(searchData);
			searchData = getLYSearchData(searchData);
			searchData.tongQi = "tongQi";
			getData(searchData);
		} else {
			getData(searchData);
		}
	});
	
	//统计类型
	$("input[name='chartType']").click(function() {
		var ct = $("input[name='chartType']:checked").val();
		// 乘机人类型
		if (ct == "ageDes") {
			$("input[name='type'][value='ticketNum'").attr("checked","checked");
			$(".profitClass").hide();
		} else {
			$(".profitClass").show();
		}
		// 航司
		if (ct == "carrier") {
			$(".saleroomClass").show();
		} else {
			var tp = $("input[name='type']:checked").val();
			if (tp == "saleroom") {
				$("input[name='type'][value='ticketNum'").attr("checked","checked");
			}
			$(".saleroomClass").hide();
		}
		// 订单生产时间
		if (ct == "createTime") {
			$("input[name='showType'][value='day'").attr("checked",false);
			$(".growthRateCheck").show();
			$(".dayCheck").hide();
		} else {
			$(".dayCheck").show();
			if (!$("input[name='showType'][value='day']").attr("checked")) {
				$(".growthRateCheck").hide();
				$(".percentCheck").show();
				$("input[name='showType'][value='growthRate']").attr("checked", false);
			}
		}
	});
	
	// 显示类型
	// 百分比
	$("input[name='showType'][value='percent']").click(function() {
		if ($(this).attr("checked")) {
			$("input[name='showType'][value='growthRate']").attr("checked", false);
			$(".growthRateCheck").hide();
		} else {
			if ($("input[name='chartType'][value='createTime']").attr("checked") || $("input[name='showType'][value='day']").attr("checked")) {
				$(".growthRateCheck").show();
			}
		}
	});
	
	// 增长率
	$("input[name='showType'][value='growthRate']").click(function() {
		if ($(this).attr("checked")) {
			$("input[name='showType'][value='percent']").attr("checked", false);
			$(".percentCheck").hide();
		} else {
			$(".percentCheck").show();
		}
	});
	$("input[name='showType'][value='day']").click(function() {
		// 日期
		if ($("input[name='showType'][value='day']").attr("checked")) {
			$(".growthRateCheck").show();
		} else {
			$("input[name='showType'][value='growthRate']").attr("checked", false);
			$(".growthRateCheck").hide();
			$(".percentCheck").show();
		}
	});
	
	// 隐藏增长率
	$(".growthRateCheck").hide();
});
