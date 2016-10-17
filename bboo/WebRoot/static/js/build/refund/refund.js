
// 缓存已退票信息
var chacheRefund;

/**
 * 检查乘机人id
 * @param id
 */
function checkPsg(id) {
	$("input[name='passengerID'").each(function() {
		if ($(this).val() == id) {
			$(this).attr("checked", false);
			$(this).attr("disabled", "disabled");
		}
	});
};

/**
 * 检查航班id
 * @param id
 */
function checkFlt(id) {
	$("input[name='flightID'").each(function() {
		if ($(this).val() == id) {
			$(this).attr("checked", false);
			$(this).attr("disabled", "disabled");
		}
	});
};

/**
 * 检查是否退票
 */
function checkRefund() {
	var data = {};
	data.fkid = fkid;
	$.ajax({
		type : "post",
		dataType : "json",
		data: data,
		url: ctx + "/refund/refund!queryByBaseRefund.do",
		success: function(result) {
			if (result.error) {
				alert(result.error);
			}
			if (result.success) {
				if (result.list) {
					chacheRefund = result.list;
					$.each(result.list, function(i, item) {
						var psgid = item.psgid;
						var fltid = item.fltid;
						checkPsg(psgid);
					});
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求出错了");
		}
	});
}

/**
 * 获取退票数据
 * @returns
 */
function getData() {
	var data = {};
	data = getRefundData();
	if (null == data) {
		return null;
	}
	data.fkid = fkid;
	var flightID = [];
	$("input[name='flightID']:checked").each(function(i, item) {
		flightID.push($(this).val());
	});
	var passengerID = [];
	$("input[name='passengerID']:checked").each(function(i, item) {
		passengerID.push($(this).val());
	});
	
	if (flightID.length == 0 || passengerID.length == 0) {
		alert("请选择航程和乘客");
		return null;
	}
	
	var state = $("input[name='state']:checked").val();
	data.fltids = flightID.toString();
	data.psgids = passengerID.toString();
	if ("1" == state) {
		data.orderStatus = "31";
	} else {
		data.orderStatus = "70";
	}
	data.state = 1;
	
	return data;
};


$(function () {
	
	checkRefund();
	
	$("input[name='flightID']").click(function() {
		if ($(this).attr("checked") == "checked") {
			var fltid = $(this).val();
			checkAll("passengerID");
			$.each(chacheRefund, function(i, item) {
				if (fltid == item.fltid) {
					var psgid = item.psgid;
					checkPsg(psgid);
				}
			});
		}
	});
	
	// 退票  留票
	$("input[name='refundBtn'").click(function() {
		var data = getData();
		if (data == null) {
			return;
		}
		data.passengerCount = passengerCount;
		data.airlineCount = airlineCount;
		$.ajax({
			type: "post",
			data: data,
			dataType: "json",
			url: ctx + "/refund/refund!save.do",
			success: function(result) {
				if (result.error) {
					alert(result.error);
				}
				if (result.success) {
					window.location.href = ctx + "/jsp/build/refound/refundQuery.jsp";
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("请求出错了");
			}
		});
	});
	
	
});