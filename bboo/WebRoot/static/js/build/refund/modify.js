/**
 * 获取退票数据
 * @returns
 */
function getData() {
	var data = getRefundData();
	if (null == data) {
		return null;
	}
	data.fkid = fkid;
	var state = $("input[name='state']:checked").val();
	if ("1" == state) {
		data.orderStatus = "31";
	} else {
		data.orderStatus = "70";
	}
	
	return data;
};

$(function () {
	
	// 修改 退票  留票
	$("input[name='refundBtn'").click(function() {
		var data = getData();
		if (data == null) {
			return;
		}
		$.ajax({
			type: "post",
			data: data,
			dataType: "json",
			url: ctx + "/refund/refund!update.do",
			success: function(result) {
				if (result.error) {
					alert(result.error);
				}
				if (result.success) {
					window.location.href = ctx + "/jsp/build/refound/modifyQuery.jsp";
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("请求出错了");
			}
		});
	});
	
});