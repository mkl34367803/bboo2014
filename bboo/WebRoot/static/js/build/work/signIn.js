// 签到状态
function signState(signIn) {
	if ("1" == signIn) {
		clicked($(".signIn"));
	} else if ("2" == signIn) {
		clicked($(".offWork"));
	} else if ("3" == signIn) {
		clicked($(".pause"));
	}
}

// 获取签到状态
function getSignIn() {
	$.ajax({
		type: "post",
		dataType: "json",
		url: ctx+"/work/staff-work!querySignIn.do",
		success: function(result){
			if (result.error) {
				alert(result.error);
			}
			if (result.success) {
				signState(result.signIn);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求出错了");
		}
	});
}

// 签到
function signIn(signIn, item) {
	$.ajax({
		type: "post",
		dataType: "json",
		data: {"signIn": signIn},
		url: ctx+"/work/staff-work!signIn.do",
		success: function(result){
			if (result.error) {
				alert(result.error);
			}
			if (result.success) {
				clicked(item);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求出错了");
		}
	});
}

// 能否点击
function clicked(item) {
	$("input:button").removeAttr("disabled");
	$("input:button").css("background", "#52a3e2");
	$(item).attr("disabled", "disabled");
	$(item).css("background", "grey");
}

// 获取员工上班时间信息
function getStaffWork() {
	var data = {};
	data.name = $.trim($("#searchName").val());
	$.ajax({
		type : "post",
		dataType : "json",
		data : data,
		url : ctx + "/work/staff-work!queryListWithName.do",
		success : function(result) {
			if (result.error) {
				alert(result.error);
			}
			if (result.success) {
				if (result.list) {
					addStaffWorkBody(result.list);
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求出错了");
		}
	});
}

//航班类型中文
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

function addStaffWorkBody(data) {
	$(".staff-work-bd").html("");
	$.each(data, function(i, item) {
		var var_tr = "<td>" + item.name + "</td>";
		var_tr += "<td>" + getFlightClassCh(item.flightClass) + "</td>";
		var_tr += "<td>" + getWorkType(item.workType) + "</td>";
		var_tr += "<td>" + item.weeks + "</td>";
		if (item.wtimeStart) {
			var_tr += "<td>" + item.wtimeStart + " - " + item.wtimeEnd + "</td>";
		} else {
			var_tr += "<td></td>";
		}
		var_tr = "<tr>" + var_tr + "</tr>";
		$(".staff-work-bd").append(var_tr);
	});
	$(".staff-work-bd tr:even").addClass("even");
	$(".staff-work-bd tr:odd").addClass("odd");
}

// n天后的日期
function getAfterDate(n) {
	var date = new Date();
	date.setDate(date.getDate() + n);
	var month = date.getMonth()+1;
	if (month >= 1 && month < 10) {
		month = "0" + month;
	}
	var strDate = date.getDate();
	if (strDate >= 0 && strDate < 10) {
		strDate = "0" + strDate;
	}
	return date.getFullYear()+"-"+month+"-"+strDate;
}

// 获取当前日期
function getCurrentDate() {
	var date = new Date();
	var month = date.getMonth()+1;
	if (month >= 1 && month < 10) {
		month = "0" + month;
	}
	var strDate = date.getDate();
	if (strDate >= 0 && strDate < 10) {
		strDate = "0" + strDate;
	}
	return date.getFullYear()+"-"+month+"-"+strDate;
}

// 获取签到日志记录
function getSignLog() {
	var data = {};
	data.logType = 3;
	data.startDate = getAfterDate(-5);
	data.endDate = getCurrentDate();
	$.ajax({
		type: "post",
		dataType: "json",
		data: data,
		url: ctx + "/syslog/process-log!queryList.do",
		success : function(result) {
			if (result.error) {
				alert(result.error);
			}
			if (result.success) {
				if (result.list) {
					addSignLogBody(result.list);
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求出错了");
		}
	});
}

function addSignLogBody(data) {
	$(".process-log-bd").html("");
	$.each(data, function(i, item) {
		var var_tr = "<td>" + item.operator + "</td>";
		var_tr += "<td>" + item.content + "</td>";
		var_tr += "<td>" + item.createTime + "</td>";
		var_tr = "<tr>" + var_tr + "</tr>";
		$(".process-log-bd").append(var_tr);
	});
	$(".process-log-bd tr:even").addClass("even");
	$(".process-log-bd tr:odd").addClass("odd");
}

$(function() {
	getSignIn();
	
	getStaffWork();
	
	getSignLog();
	
	// 签到
	$(".signIn").click(function() {
		signIn(1, this);
	});
	// 下班
	$(".offWork").click(function() {
		signIn(2, this);
	});
	// 暂停
	$(".pause").click(function() {
		signIn(3, this);
	});
	
	// 搜索员工上班时间
	$("input[name='searchStaffWork']").click(function() {
		getStaffWork();
	});
});