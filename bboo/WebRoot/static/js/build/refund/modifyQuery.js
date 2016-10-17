var cacheData;
function getDatas(page) {
	var data = {};
	//从浏览器地址获取flightClass参数
	data.flightClass=flightClass;
	var startRefund =  $.trim($("input[name='startDate']").val());
	var endRefund =  $.trim($("input[name='endDate']").val());
	var orderNo =  $.trim($("input[name='orderNo']").val());
	var carrier =  $.trim($("input[name='carrier']").val());
	var pnrCode =  $.trim($("input[name='pnrCode']").val());
	var state = $("input[name='searchState']:checked").val()
	data.startRefund = startRefund;
	data.endRefund = endRefund;
	data.orderNo = orderNo;
	data.carrier = carrier;
	data.pnrCode = pnrCode;
	data.state = state;
	data.page = page; // 分页查询
	data.pageSize = 20; // 每页展示多少条记录
	$.ajax({
		type : "post",
		dataType : "json",
		data : data,
		url : ctx + "/refund/base-refund!queryOrdersByPage.do",
		success : function(result) {
			if (result.error) {
				alert(result.error);
			}
			if (result.success) {
				if (result.list) {
					addTbodyData(result.list);
				}
				if (result.total == 0) {
					pageNav.go(page, Math.ceil(1));
				} else if (result.total) {
					pageNav.go(page, Math.ceil(result.total / data.pageSize));
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求出错了");
		}
	});
}

function addTbodyData(data) {
	$(".table-bd").html("");
	cacheData = data; // 页面上缓存数据。
	$.each(data, function(i, item) {
		var img_tag=getOrderPic(item.distributor);
		var td_orderNo="";
		var temp_orderNo="";
		if(item.oldOrderNo!="0"&&item.oldOrderNo!=""){
			temp_orderNo=item.orderNo+"&nbsp;"+"<span style='color:red'>改</span>";
		}else {
			temp_orderNo=item.orderNo;
		}
		if(item.distributor=="C"){
			td_orderNo="<td>"+img_tag+item.shopNameCh+"<br/>"+item.billId+"<br/>"+temp_orderNo+"</td>"; //订单编号(携程的订单号加上主订单号)
		}else{
			td_orderNo="<td>"+img_tag+item.shopNameCh+"<br/>"+temp_orderNo+"</td>"; //订单编号
		}
		var createTimes=item.createTime.split(" ");
		var td_createTime="<td>"+createTimes[0]+"<br/>"+createTimes[1]+"</td>"; //订单时间
		var td_flightType="<td>"+getFlightTypeCh(item.flightType)+"</td>";  //航程类型：单程、往返
		var td_dep_arr="";
		var td_flightDate_flightNo="";  //用来存储航班日期，航班号
		if(item.flightVos){
			$.each(item.flightVos,function(j,flight){
				var each_departureTime=flight.departureTime;
				var each_depAircode=flight.depAircode;
				var each_arrivalTime=flight.arrivalTime;
				var each_arrAircode=flight.arrAircode;
				var each_dep_arr=each_departureTime+" "+each_depAircode+"<br/>"+each_arrivalTime+" "+each_arrAircode;
				if(j<item.flightVos.length-1){
					each_dep_arr=each_dep_arr+"<hr style='height:1px;border:none;border-top:1px solid #555555;'/>";
				}
				td_dep_arr=td_dep_arr+each_dep_arr;
				
				var flightDate_flightNo=flight.departureDate+"<hr style='height:1px;border:none;border-top:1px dashed #0066CC;'/>"; //航班日期
				flightDate_flightNo=flightDate_flightNo+flight.flightNum+"("+flight.cabin+")"; //航班号+仓位
				if(j<item.flightVos.length-1){
					flightDate_flightNo=flightDate_flightNo+"<hr style='height:1px;border:none;border-top:1px solid #555555;'/>";
				}
				td_flightDate_flightNo=td_flightDate_flightNo+flightDate_flightNo;
			});
		}
		td_dep_arr="<td>"+td_dep_arr+"</td>";
		td_flightDate_flightNo="<td>"+td_flightDate_flightNo+"</td>";
		var td_pnrCode="<td>"+item.pnrCode+"</td>"; //pnr
		
		var td_allprice="<td>"+item.allprice+"/"; //订单总价
		var td_passengerCount=""+item.passengerCount+"</td>"; //乘客总人数
		var td_slfStatus="<td>"+getSlfStatusCH(item.slfStatus)+"</td>"; //订单状态//这个字段暂时不确定
		
		var td_remark = "<td>"+item.operator+"</td>"; //操作人
		var var_tr=td_orderNo+td_createTime+td_flightType+td_dep_arr+td_flightDate_flightNo+td_pnrCode+td_allprice+td_passengerCount+td_slfStatus+td_remark;
		var var_a="";
								
		var_a += "<a href='"+ctx +"/order/gj-order-detail!showOrderDetail.do?id="+item.id+"&operate=refundQuery&flightClass="+flightClass+"'>查看</a>";
		var_a=var_a+"<br/>"+"<a href='"+ctx +"/refund/base-refund!queryModifyById.do?brid="+item.brid+"'>修改</a>";
		
		var_tr="<tr>"+var_tr+"<td class='operate' >"+var_a+"</td></tr>";
		$("#table-bd").append(var_tr);
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
function addData() {
	openDialog();
	reset();
}

function modifyData(id) {
	$.each(cacheData, function(i, item) {
		if (item.id == id) {
			clearCheckBox();
			$("#id").val(item.id);
			$("select[name='shopName']").val(item.shopName);
			$("select[name='policyType']").val(item.policyType);
			/*$("#bookChannel").val(item.bookChannel);*/
			var bcArray = (item.bookChannel).split(",");
			for ( var i = 0; i < bcArray.length; i++) {
				$("input[name='bookChannel'][value='"+bcArray[i]+"']").attr("checked", "checked");
				if (i == 0) {
					$("input[name='bookChannelCount']").val("");
				} else {
					$("input[name='bookChannelCount']").val("选中"+(i+1)+"个");
				}
			}
			$("input:radio[name='isDrop'][value='"+item.isDrop+"']").attr("checked", true);
			$("input:radio[name='isuse'][value='"+item.isuse+"']").attr("checked", true);
			$("#lr").val(item.lr);
			$("#includeRule").val(item.includeRule);
			$("#ignoredRules").val(item.ignoredRules);
		}
	});
	openDialog();
}

function deleteData(id) {
	if(confirm("确定删除？")){
		$.ajax({
			type : "post",
			dataType : "json",
			data : {
				id : id
			},
			url : ctx + "/tool/book-rule!delete.do",
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

function reset() {
	$("#id").val("");
	$("select[name='shopName']").val("");
	$("select[name='policyType']").val("");
	clearCheckBox();
	$("input:radio[name='isDrop'][value='1']").attr("checked", true);
	$("input:radio[name='isuse'][value='1']").attr("checked", true);
	$("#lr").val("");
	$("#includeRule").val("");
	$("#ignoredRules").val("");
}


$(document).ready(function() {
	
	$("#searchButton").click(function() {
		getDatas(1);
	});
	
	$("#lightCancel").click(function() {
		closeDialog();
	});
	$("input[name='bookChannelCount'").click(function() {
		$(".bookChannelSelect").css({"display": "block"});
	});
	$("#lightOk").click(function() {
		var shopName = $.trim($("select[name='shopName']").val());
		var policyType = $.trim($("select[name='policyType']").val());
		var bookChannel = [];
		$("input[name='bookChannel']:checked").each(function() {
			bookChannel.push($(this).val());
		});
		var isDrop = $.trim($("input[name='isDrop']:checked").val());
		var isuse = $.trim($("input[name='isuse']:checked").val());
		var lr = $.trim($("#lr").val());
		var includeRule = $.trim($("#includeRule").val());
		var ignoredRules = $.trim($("#ignoredRules").val());
		if(shopName != "" && policyType != "") {
			var bookRule = {};
			bookRule.id = $("#id").val();
			bookRule.shopName = shopName;
			bookRule.policyType = policyType;
			bookRule.bookChannel = bookChannel.toString();
			bookRule.isDrop = isDrop;
			bookRule.isuse = isuse;
			bookRule.lr = lr;
			bookRule.includeRule = includeRule;
			bookRule.ignoredRules = ignoredRules;
			$.ajax({
				type : "post",
				dataType : "json",
				data : bookRule,
				url : ctx + "/tool/book-rule!saveOrUpdate.do",
				success : function(result) {
					if (result.error) {
						alert(result.error);
					}
					if (result.success) {
						//alert(result.success);
						closeDialog();
						reset();
						var page = $("#page").html();
						getDatas(page);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("请求出错了");
				}
			});
		} else {
			alert("店铺名称和政策类型不能为空");
		}
	});
});