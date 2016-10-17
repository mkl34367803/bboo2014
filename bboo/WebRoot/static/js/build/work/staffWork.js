var cacheData;
function getDatas(page) {
	var data = {};
	var searchName =  $.trim($("input[name='searchName']").val());
	var searchWorkType =  $.trim($("select[name='searchWorkType']").val());
	var searchFlightClass = $("input[name='searchFlightClass']:checked").val();
	data.name = searchName;
	data.workType = searchWorkType;
	data.flightClass = searchFlightClass;
	data.page = page; // 分页查询
	data.rows = 20; // 每页展示多少条记录
	$.ajax({
		type : "post",
		dataType : "json",
		data : data,
		url : ctx + "/work/staff-work!queryByPage.do",
		success : function(result) {
			if (result.error) {
				alert(result.error);
			}
			if (result.success) {
				if (result.list) {
					addTbodyData(result.list);
				}
				if (result.total== 0) {
					pageNav.go(page, Math.ceil(1));
				} else if (result.total) {
					pageNav.go(page, Math.ceil(result.total / data.rows));
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求出错了");
		}
	});
}

// 航班类型中文
function getFlightClassCh(flightClass) {
	if (flightClass == "N") {
		return "国内";
	} else if (flightClass == "I") {
		return "国际";
	}
	return flightClass;
}

//C：出票 T：退票 L:留票 Z:政策
function getWorkType(workType) {
	if ("C" == workType) {
		return "出票";
	} else if ("T" == workType) {
		return "退票";
	} else if ("L" == workType) {
		return "留票";
	} else if ("Z" == workType) {
		return "政策";
	}
	return workType;
}

function addTbodyData(data) {
	$(".table-bd").html("");
	cacheData = data; // 页面上缓存数据。
	$.each(data, function(i, item) {
		var var_tr = "<td>" + item.name + "</td>";
		var_tr += "<td>" + getFlightClassCh(item.flightClass) + "</td>";
		var_tr += "<td>" + getWorkType(item.workType) + "</td>";
		var_tr += "<td>" + item.weeks + "</td>";
		var_tr += "<td>" + item.wtimeStart + "</td>";
		var_tr += "<td>" + item.wtimeEnd + "</td>";
		var_tr += "<td>" + item.createTime + "</td>";
		var_tr += "<td><a href='javascript:modifyData(\"" + item.id
				+ "\",\"" + item.fkUserId + "\")'>修改</a>";
		var_tr += "&nbsp;&nbsp;<a href='javascript:deleteData(\""
				+ item.id + "\")'>删除</a>";
		var_tr = "<tr>" + var_tr + "</tr>";
		$(".table-bd").append(var_tr);
	});
	$(".table-bd tr:even").addClass("even");
	$(".table-bd tr:odd").addClass("odd");
}

function openDialog() {
	$("#light").css('display', 'block');
	$("#fade").css('display', 'block');
}
function closeDialog() {
	$("#light").css('display', 'none');
	$("#fade").css('display', 'none');
}

function modifyData(id, fkUserId) {
	$.each(cacheData, function(i, item) {
		if (item.id == id || item.fkUserId == fkUserId) {
			$("#id").val(item.id);
			$("#fkUserId").val(item.fkUserId);
			$("input[name='name']").val(item.name);
			$("input[name='flightClass'][value='"+item.flightClass+"']").attr("checked", true);
			$("select[name='workType']").val(item.workType);
			$("input[name='weeks']").val(item.weeks);
			$("input[name='wtimeStart']").val(item.wtimeStart);
			$("input[name='wtimeEnd']").val(item.wtimeEnd);
		}
	});
	openDialog();
}

function deleteData(id) {
	if(confirm("确定删除？")){
		if (id == "undefined") {
			alert("id为空");
			return;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			data : {
				id : id
			},
			url : ctx + "/work/staff-work!delete.do",
			success : function(result) {
				if (result.error) {
					alert(result.error);
				}
				if (result.success) {
					var page = $("#page").html();
					getDatas(page);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("请求出错了");
			}
		});		
	}
}

function checkHM(i) {
	if (i < 10) {
		return "0"+i;
	}
	return i;
}

//小时
function produceHour() {
	$(".time-tb").html("");
	var time_tr = "";
	time_tr += "<tr>";
	for ( var i = 0; i < 24; i++) {
		time_tr += "<td onclick='getHour(this)' onmouseover=\"this.bgColor='grey'\" onmouseout=\"this.bgColor=''\" >"+checkHM(i)+"</td>";
		if ((i+1) % 8 == 0) {
			time_tr += "</tr>";
			time_tr += "<tr>";
		}
	}
	time_tr += "</tr>";
	$(".time-tb").html(time_tr);
}

//分
function produceMinute() {
	$(".time-tb").html("");
	var time_tr = "";
	time_tr += "<tr>";
	for ( var i = 0; i < 60; i++) {
		time_tr += "<td onclick='getMinute(this)' onmouseover=\"this.bgColor='grey'\" onmouseout=\"this.bgColor=''\" >"+checkHM(i)+"</td>";
		if ((i+1) % 8 == 0) {
			time_tr += "</tr>";
			time_tr += "<tr>";
		}
	}
	time_tr += "</tr>";
	$(".time-tb").html(time_tr);
}

var $timeIpt;
var hour;
var minute;
function getHour(h) {
	hour = $(h).text();
	produceMinute();
}

function getMinute(m) {
	minute = $(m).text();
	$(".time-div").hide();
	$timeIpt.val(hour+":"+minute);
}

$(document).ready(function() {
	getDatas(1);
	
	$("#searchButton").click(function() {
		getDatas(1);
	});
	
	$("#lightCancel").click(function() {
		closeDialog();
	});
	
	$("#lightOk").click(function() {
		var id = $("#id").val();
		var fkUserId = $("#fkUserId").val();
		var name = $("input[name='name']").val();
		var flightClass = $("input[name='flightClass']:checked").val();
		var workType = $("select[name='workType']").val();
		var weeks = $("input[name='weeks']").val();
		var wtimeStart = $("input[name='wtimeStart']").val();
		var wtimeEnd = $("input[name='wtimeEnd']").val();
		
		if (workType == "") {
			alert("请选择工作类型！");
			return;
		}
		
		if (weeks == "") {
			alert("请选择工作周期！");
			return;
		}
		
		if (wtimeStart == "" || wtimeStart == "") {
			alert("请选择工作时间！");
			return;
		}
		
		var entity = {};
		entity.id = id;
		entity.fkUserId = fkUserId;
		entity.name = $.trim(name);
		entity.flightClass = $.trim(flightClass);
		entity.workType = $.trim(workType);
		entity.weeks = $.trim(weeks);
		entity.wtimeStart = $.trim(wtimeStart);
		entity.wtimeEnd = $.trim(wtimeEnd);
		$.ajax({
			type : "post",
			dataType : "json",
			data : entity,
			url : ctx + "/work/staff-work!saveOrUpdate.do",
			success : function(result) {
				if (result.error) {
					alert(result.error);
				}
				if (result.success) {
					//alert(result.success);
					closeDialog();
					var page = $("#page").html();
					getDatas(page);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("请求出错了");
			}
		});
	});
	
	var dayWeek=[];
	$("input[name='weeks']").click(function() {
		dayWeek = [];
		$(".week-div").show();
		$(".day-div").css("background-color", "#eaf6fd");
	});
	
	$("input[name='weekSure']").click(function() {
		dayWeek.sort();
		$("input[name='weeks']").val(dayWeek.toString());
		$(".week-div").hide();
	});
	
	$("input[name='weekCancel']").click(function() {
		$(".week-div").hide();
	});
	
	$(".day-div").click(function() {
		var day = $(this).text();
		var color = $(this).css("background-color");
		if (color == "rgb(128, 128, 128)") {
			dayWeek.splice($.inArray(day, dayWeek), 1);
			$(this).css("background-color", "#eaf6fd");
		} else {
			dayWeek.push($(this).text());
			$(this).css("background-color", "grey");
		}
	});
	
	$(".time-select").click(function() {
		$(".time-div").show();
		$timeIpt = $(this);
		produceHour();
	});
	
	/*$("input[name='wtimeStart']").click(function() {
		$(".time-div").show();
		$timeIpt = $(this);
		produceHour();
	});
	
	$("input[name='wtimeEnd']").click(function() {
		$(".time-div").show();
		$timeIpt = $(this);
		produceHour();
	});*/
	
});