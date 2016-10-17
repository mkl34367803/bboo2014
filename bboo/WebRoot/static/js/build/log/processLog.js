
// 日志類型 1 導出報表 2 查看統計
function getLogType(logType) {
	if (logType == 1) {
		return "[导出报表]";
	} else if (logType == 2) {
		return "[查看统计]";
	} else if (logType == 3) {
		return "[签到]";
	} else {
		return logType;
	}
}
function addTbodyData(data) {
	$(".log-table").html("");
	$.each(data, function(i, item) {
		var var_tr = "<td>" + item.createTime + "</td>";
		var_tr += "<td>" + item.operator + "</td>";
		var_tr += "<td>" + getLogType(item.logType) + "</td>";
		var_tr += "<td>" + item.content + "</td>";
		var_tr = "<tr>" + var_tr + "</tr>";
		$(".log-table").append(var_tr);
	});
}
function getDatas(startPage) {
	var data = {};
	data.startDate = $.trim($("startDate").val());
	data.endDate = $.trim($("endDate").val());
	data.operator = $.trim($("#searchOperator").val());
	data.logType = $("select[name='searchLogType']").val();
	data.startPage = startPage;
	data.pageSize = 40;
	$.ajax({
		type: "post",
		dataType: "json",
		data: data,
		url: ctx + "/syslog/process-log!queryByPage.do",
		success : function(result) {
			if (result.error) {
				alert(result.error);
			}
			if (result.success) {
				if (result.list) {
					addTbodyData(result.list);
				}
				if (result.total == 0) {
					pageNav.go(startPage, Math.ceil(1));
				} else if (result.total) {
					pageNav.go(startPage, Math.ceil(result.total / data.pageSize));
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求出错了");
		}
	});
}
$(function() {
	getDatas(1);
	$("#searchButton").click(function() {
		getDatas(1);
	});
});