<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../share/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>订单信息</title>
<script type="text/javascript" src="${ctx }/static/js/build/order/orderDeal.js"></script>
<script type="text/javascript">
	// 相差天数
	function getDays(departureDate, arrivalDate) {
		var dateArray, dDate, aDate, days;
		dateArray = departureDate.split("-");
		dDate = new Date(dateArray[1]+"-"+dateArray[2]+"-"+dateArray[0]);
		
		dateArray = arrivalDate.split("-");
		aDate = new Date(dateArray[1]+"-"+dateArray[2]+"-"+dateArray[0]);
		days = parseInt((aDate - dDate) / 1000 / 60 / 60 / 24);
		return Math.abs(days);
	}
	// 获取飞行时间
	function getTime(departureDate, arrivalDate, departureTime, arrivalTime) {
		var dTimes,
		aTimes,
		dTime,
		aTime;
		if (departureDate == arrivalDate) {
			dTimes = departureTime.split(":");
			aTimes = arrivalTime.split(":");
			dTime = parseInt(dTimes[0]) * 60 + parseInt(dTimes[1]);
			aTime = parseInt(aTimes[0]) * 60 + parseInt(aTimes[1]);
		} else {
			var dateDif = getDays(departureDate, arrivalDate);
			dTimes = departureTime.split(":");
			aTimes = arrivalTime.split(":");
			dTime = parseInt(dTimes[0]) * 60 + parseInt(dTimes[1]);
			aTime = parseInt(aTimes[0]) * 60 + parseInt(aTimes[1]) + 24 * 60 * dateDif;
		}
		var h = parseInt((aTime - dTime) / 60) + "小时" ;
		var m =  parseInt((aTime - dTime) % 60) + "分钟";
		return h + m;
	}
	
	$(document).ready(function() {
		$('.rmb').text(function(){
			var currency = "${orderDetail.currency }";
			if(currency == "RMB"){
				return "¥";
			} else {
				return currency;
			}
		});
	});
</script>

</head>
<body>
	<c:if test="${ERROR ne null and ERROR ne '' }">
		<div style="background-color: #fff; padding: 5px; color: red"><h3>${ERROR }</h3></div>
	</c:if>
	<table>
		<tr>
			<td style="width: 180px;">
				<c:choose>
					<c:when test="${orderDetail.distributor == 'C'}">
						主订单号：${orderDetail.billId} <br/>子订单号： ${orderDetail.orderNo}
					</c:when>
					<c:otherwise>
						订单号： ${orderDetail.orderNo}
					</c:otherwise>
				</c:choose>
			</td>
			<td style="width: 260px;">订单来源： ${orderDetail.distributorCh }---${orderDetail.shopNameCh }</td>
			<td class="detail" rowspan="2">
				<h1>
					<c:if test="${orderDetail.status == '0' }">订座成功等待支付</c:if>
					<c:if test="${orderDetail.status == '1' }">支付成功等待出票</c:if>
					<c:if test="${orderDetail.status == '2' }">出票完成</c:if>
					<c:if test="${orderDetail.status == '5' }">出票中</c:if>
					<c:if test="${orderDetail.status == '12' }">订单取消</c:if>
					<c:if test="${orderDetail.status == '20' }">等待座位确认</c:if>
					<c:if test="${orderDetail.status == '30' }"> 退票申请中</c:if>
					<c:if test="${orderDetail.status == '31' }">退票完成等待退款</c:if>
					<c:if test="${orderDetail.status == '39' }">退款完成</c:if>
					<c:if test="${orderDetail.status == '40' }">改签申请中</c:if>
					<c:if test="${orderDetail.status == '42' }">改签完成</c:if>
					<c:if test="${orderDetail.status == '50' }">未出票申请退款</c:if>
					<c:if test="${orderDetail.status == '51' }">订座成功等待价格确认</c:if>
					<c:if test="${orderDetail.status == '60' }">支付待确认</c:if>
					<c:if test="${orderDetail.status == '61' }">暂缓出票</c:if>
					<c:if test="${orderDetail.status == '62' }">订单超时</c:if>
					<c:if test="${orderDetail.status == '99' }">订座成功等待支付（系统）</c:if>
					<c:if test="${orderDetail.status == '70' }">退票留票中</c:if>
					<c:if test="${orderDetail.status == '-1' }">其它</c:if>
				</h1> 
			</td>
		</tr>
		<tr>
			<td>编码： ${orderDetail.pnrCode }</td>
			<td class="textred">最晚出票时限：<!-- 2016-05-23 15:40:08 -->-</td>
		</tr>
		<tr>
			<td colspan="2">原编码： ${orderDetail.salePnrCode }</td>
			<td class="detail" rowspan="2">
				<h1>
					SELF 状态：
					<c:if test="${orderDetail.slfStatus == '0' }">订座成功等待支付</c:if>
					<c:if test="${orderDetail.slfStatus == '1' }">支付成功等待出票</c:if>
					<c:if test="${orderDetail.slfStatus == '2' }">出票完成</c:if>
					<c:if test="${orderDetail.slfStatus == '5' }">出票中</c:if>
					<c:if test="${orderDetail.slfStatus == '12' }">订单取消</c:if>
					<c:if test="${orderDetail.slfStatus == '20' }">等待座位确认</c:if>
					<c:if test="${orderDetail.slfStatus == '30' }"> 退票申请中</c:if>
					<c:if test="${orderDetail.slfStatus == '31' }">退票完成等待退款</c:if>
					<c:if test="${orderDetail.slfStatus == '39' }">退款完成</c:if>
					<c:if test="${orderDetail.slfStatus == '40' }">改签申请中</c:if>
					<c:if test="${orderDetail.slfStatus == '42' }">改签完成</c:if>
					<c:if test="${orderDetail.slfStatus == '50' }">未出票申请退款</c:if>
					<c:if test="${orderDetail.slfStatus == '51' }">订座成功等待价格确认</c:if>
					<c:if test="${orderDetail.slfStatus == '60' }">支付待确认</c:if>
					<c:if test="${orderDetail.slfStatus == '61' }">暂缓出票</c:if>
					<c:if test="${orderDetail.slfStatus == '62' }">订单超时</c:if>
					<c:if test="${orderDetail.slfStatus == '99' }">订座成功等待支付（系统）</c:if>
					<c:if test="${orderDetail.slfStatus == '70' }">退票留票中</c:if>
					<c:if test="${orderDetail.slfStatus == '-1' }">其它</c:if>
				</h1> 
			</td>
		</tr>
		<tr>
			<td colspan="3">政策说明： ${orderDetail.statement}</td>
		</tr>
	</table>
	
	
	<table class="g-table table-list">
		<thead class="table-hd ">
			<tr>
				<th class="pcol11">政策类型</th>
				<th class="pcol11">产品类型</th>
				<th class="pcol11">大编码</th>
				<th class="pcol11">业务线</th>
				<th class="pcol11">订单来源</th>
				<th class="pcol11">价格来源</th>
				<th class="pcol11">快递费</th>
				<th class="pcol11">保险费/份数</th>
				<th class="pcol12">指派方式</th>
			</tr>
		</thead>
		<tbody class="table-bd">
			<tr>
				<td>
					<script type="text/javascript">
						var policyType=covertPolicyTypeToChinese(${orderDetail.policyType});
						document.write(policyType);
					</script>
				</td>
				<td>${orderDetail.productType}</td>
				<td>${orderDetail.pnrCodeBig }</td>
				<td><!-- 国际TTS包装出票任务 -->-</td>
				<td>
					<c:if test="${orderDetail.source == 'self' }">普通政策</c:if>
					<c:if test="${orderDetail.source == 'qfare' }">加价政策</c:if>
					<c:if test="${orderDetail.source == '加价政策' }">特殊政策</c:if>
					<c:if test="${orderDetail.source == 'othe' }">其它</c:if>
				</td>
				<td>
					<c:if test="${orderDetail.saleType == 'AirLineMarketing' }">航司直销</c:if>
					<c:if test="${orderDetail.saleType == 'PriorityPackage' }">优选套餐</c:if>
					<c:if test="${orderDetail.saleType == 'BusinessPriority' }">商务优选</c:if>
					<c:if test="${orderDetail.saleType == 'TravelPackage' }">旅行套餐</c:if>
					<!-- 国际 -->
					<c:if test="${orderDetail.saleType == 'Prioritizing' }">商务优选</c:if>
					<c:if test="${orderDetail.saleType == 'Exclusive' }">旅行套餐</c:if>
				</td>
				<td style="background-color: yellow">${orderDetail.journeySheetPrice }</td>
				<td style="background-color: yellow">${orderDetail.bxFee}/${orderDetail.bxCount}</td>
				<td>-</td>
			</tr>
		</tbody>
	</table>
	
	<!-- 报价类型 -->
	<table class="g-table table-list">
		<thead class="table-hd ">
			<tr>
				<th class="pcol08">行程类型</th>
				<th class="pcol06">航段</th>
				<th class="pcol08">航班号</th>
				<th class="pcol08">承运航班号</th>
				<th class="pcol06">舱位</th>
				<th class="pcol10">起降机场</th>
				<th class="pcol12">起飞时间</th>
				<th class="pcol12">降落时间</th>
				<th class="pcol10">行李额</th>
				<th class="pcol10">航班信息</th>
				<th>用户支付金额</th>
			</tr>
		</thead>
		<tbody class="table-bd">
			<c:forEach items="${orderDetail.flights }" var="flight" varStatus="i">
				<tr>
				<c:if test="${i.index == 0 }">
					<td rowspan="${orderDetail.airlineCount }">
						<c:if test="${orderDetail.flightType == 'S' }">单程</c:if>
						<c:if test="${orderDetail.flightType == 'D' }">往返</c:if>
						<c:if test="${orderDetail.flightType == 'T' }">其它</c:if>
						<c:if test="${orderDetail.flightType == 'L' }">联程</c:if>
						<c:if test="${orderDetail.flightType == 'X' }">多程</c:if>
					</td>
				</c:if>
					<td>${flight.sequence } </td>
					<td>${flight.flightNum } </td>
					<td>${flight.shareNum } </td>
					<td>${flight.cabin }</td>
					<td>${flight.depAircode }-${flight.arrAircode }</td>
					<td>${flight.departureDate}<br/> ${flight.departureTime }
					</td>
					<td>
						${flight.arrivalDate}<br/>${flight.arrivalTime }
					</td>
					<td>
						<div class="g-drift">
							<a href="javascript:;" class="">详情</a>
							<div class="g-tip g-tip-w tip-b-r">
								<div class="info">
									<p>${orderDetail.luggageRule }</p>
								</div>
							</div>
						</div>
					</td>
					<td>
						<div class="g-drift">
							<a href="javascript:;" class="">详情</a>
							<div class="g-tip g-tip-w tip-b-r">
								<div class="info">
									<p>航空公司：${flight.airCodeCh }</p>
									<div>
										<span>机型：${flight.planeModule }</span> 
										<span id="flightTime">飞行时间：
											<script>
												var departureDate="${flight.departureDate}";
												var arrivalDate="${flight.arrivalDate }";
												var departureTime="${flight.departureTime }";
												var arrivalTime="${flight.arrivalTime }";
												var time = getTime(departureDate, arrivalDate, departureTime, arrivalTime);
												document.write(time);
											</script>
										</span>
									</div>
								</div>
							</div>
						</div>
					</td>
				<c:if test="${i.index == 0 }">
					<td rowspan="${orderDetail.airlineCount }">
						<div class="g-drift">
							<a href="javascript:;" class=""> <i class="rmb"></i> ${orderDetail.allprice } </a>
							<div class="g-tip g-tip-w tip-b-r order-info">
								<table class="g-table" style="width: 200px; margin: 10px 0;min-width: 170px;">
									<thead class="table-hd">
										<tr>
											<th>类型</th>
											<th>价格</th>
											<th>份数</th>
										</tr>
									</thead>
									<tbody class="table-bd">
										<tr>
											<td>成人Tax</td>
											<td><i class="rmb"></i> ${orderDetail.adultTax }</td>
											<td>${orderDetail.adultsCount }</td>
										</tr>
										<tr>
											<td>快递费</td>
											<td><i class="rmb"></i> 0.00</td>
											<td>1</td>
										</tr>
										<tr>
											<td>成人Price</td>
											<td><i class="rmb"></i> ${orderDetail.adultPrice }</td>
											<td>${orderDetail.adultsCount }</td>
										</tr>
	
										<tr style="color:#f60;">
											<td>订单总价</td>
											<td><i class="rmb"></i> ${orderDetail.allprice }</td>
											<td>1</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</td>
				</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- 国际航班价格 -->
	<table class="g-table table-list" style="width: 100%">
		<thead class="table-hd ">
			<tr>
				<th class="pcol08">乘客姓名</th>
				<th class="pcol06">性别</th>
				<th class="pcol06">类型</th>
				<th class="pcol10">证件类型<br>(国籍)</th>
				<th class="pcol17">证件号(生日)</th>
				<th class="pcol10">证件签发地<br>(证件有效期)</th>
				<th class="pcol13">票号</th>
				<th class="pcol09">票面价<br>销售价</th>
				<th class="pcol07">税费</th>
				<th class="pcol07">佣金</th>
				<th>PNR</th>
			</tr>
		</thead>
		<tbody class="table-bd">
			<c:forEach items="${orderDetail.passengers }" var="passenger" varStatus="i">
				<tr>
					<td>${passenger.name }</td>
					<td>
						<c:if test="${passenger.gender == 'M' }">男</c:if>
						<c:if test="${passenger.gender == 'F' }">女</c:if>
					</td>
					<td>
						<c:if test="${passenger.ageType == '-2' }">其他</c:if>
						<c:if test="${passenger.ageType == '-1' }">留学生</c:if>
						<c:if test="${passenger.ageType == '0' }">成人</c:if>
						<c:if test="${passenger.ageType == '1' }">儿童</c:if>
					</td>
					<td>
						<c:if test="${passenger.cardType == 'PP' }">护照</c:if>
						<c:if test="${passenger.cardType == 'HX' }">回乡证</c:if>
						<c:if test="${passenger.cardType == 'TB' }">台胞证</c:if>
						<c:if test="${passenger.cardType == 'GA' }">港澳通行证</c:if>
						<c:if test="${passenger.cardType == 'HY' }">国际海员证</c:if>
						<c:if test="${passenger.cardType == 'NI' }">身份证</c:if>
						<c:if test="${passenger.cardType == 'XS' }">学生证</c:if>
						<c:if test="${passenger.cardType == 'JR' }">军人证</c:if>
						<c:if test="${passenger.cardType == 'JS' }">驾驶证</c:if>
						<c:if test="${passenger.cardType == 'TW' }">台湾通行证</c:if>
						<c:if test="${passenger.cardType == 'SB' }">士兵证</c:if>
						<c:if test="${passenger.cardType == 'LN' }">临时身份证</c:if>
						<c:if test="${passenger.cardType == 'HK' }">户口簿</c:if>
						<c:if test="${passenger.cardType == 'JG' }">警官证</c:if>
						<c:if test="${passenger.cardType == 'TH' }">其它</c:if>
						<c:if test="${passenger.cardType == 'CS' }">出生证明</c:if>
						<c:if test="${passenger.nationality != '' }">
							<br>(${passenger.nationality })
						</c:if>
					</td>
					<td>
						<div class="g-drift">
							<a href="javascript:;" class="">${passenger.cardNum }</a>
							<div class="g-tip g-tip-w tip-b-r" style="width: 100px;">
								<div class="info">
									<p>${passenger.birthday }</p>
								</div>
							</div>
						</div>
					</td>
					<td>
						${passenger.cardIssuePlace }
						<c:if test="${passenger.cardIssuePlace != '' && passenger.cardExpired != ''}"><br></c:if>
						<c:if test="${passenger.cardExpired != ''}">(${passenger.cardExpired })</c:if>
					</td>
					<td>
						${passenger.eticketNum }
						<c:if test="${passenger.ticketStatus } != ''">
							<br><span style="color: green">${passenger.ticketStatus }</span>
						</c:if>
					</td>
					<td>${passenger.price }<br>${passenger.cost }
					
					</td>
					<td>${passenger.oilFee + passenger.taxFee }</td>
					<td>${passenger.commission}</td>
					<td>${passenger.pnr}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
