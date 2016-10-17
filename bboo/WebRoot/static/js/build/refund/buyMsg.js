// 缓存RT信息
var cacheRtMsg;

// 缓存票号
var cacheTicketData = [];

// 上提示层
function showTopTips(msg, position) {
	var data = msg.replace(/\n/gm, "<br>");
	layer.tips(data, position, {
		tips: [1, '#F90'],
		time: 6000
	});
}

/**
 * 获取RT信息
 */
function getRtMsg(postion) {
	cacheRtMsg = "";
	var data = {};
	data.id = buyorderId;
	data.passengerId = passengerIdArr[0];
	$.ajax({
		type: "post",
		data: data,
		dataType: "json",
		url: ctx + "/refund/base-refund!getEtermInfo.do",
		success: function(result) {
			if (result.error) {
				alert(result.error);
			}
			if (result.success) {
				if (result.eterm) {
					cacheRtMsg = result.eterm;
					showTopTips(cacheRtMsg, postion);
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求出错了");
		}
	});
}

// 获取票号信息
function getTicketMsg(data, $position) {
	$.ajax({
		type: "post",
		dataType: "json",
		data: data,
		url: ctx + "/ticket/ticket-hand!queryTicket.do",
		success: function(result) {
			if (result.error) {
				alert(result.error);
			}
			if (result.root) {
				var data = result.root.data;
				var tr_var = "<tr><td colspan='7' style='text-align: left;'>";
				tr_var += "<textarea rows='5' style='width: 100%;background-color: white;' disabled='disabled'>"+data+"</textarea>";
				tr_var += "</td></tr>";
				$position.parent().parent().after(tr_var);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求出错了");
		}
	});
}

// 检查票号是否缓存
function checkCacheTicket(dataArr, data) {
	for ( var i = 0; i < dataArr.length; i++) {
		var da = dataArr[i];
		if (da.tno == data.tno && da.pname == data.pname) {
			return true;
		}
	}
	return false;
}

$(function(){
	// RT信息
	$(".rtMsg").click(function() {
		if(cacheRtMsg) {
			showTopTips(cacheRtMsg, this);
		} else {
			getRtMsg(this);
		}
	});
	
	// 票号
	$(".ticketNum").click(function() {
		var tno = $(this).text();
		var pname = $(this).parent().siblings().first().text();
		var data = {};
		data.tno = tno;
		data.pname = pname;
		if (!checkCacheTicket(cacheTicketData, data)) {
			cacheTicketData.push(data);
			getTicketMsg(data, $(this));
		}
	});
});