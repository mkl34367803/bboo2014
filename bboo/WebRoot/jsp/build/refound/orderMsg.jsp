<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/jsp/share/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body>

	<div class="detail-model">
		<div class="mod-hd" id="js-mod-orderInfo">
			<span class="show">收起</span>
			<script type="text/javascript">
			var flightClass="${orderDetail.flightClass}";
			if(flightClass=="I"){
				document.write("<h2>国际订单信息</h2>");			
			}else{
				document.write("<h2>国内订单信息</h2>");
			}
			</script>
		</div>
		<div class="g-content">
			<c:if test="${ERROR ne null and ERROR ne '' }">
				<div style="background-color: #fff; padding: 5px; color: red"><h3>${ERROR }</h3></div>
			</c:if>
			<div>
				<table>
					<tr>
						<td style="width: 250px;">
							<c:choose>
								<c:when test="${orderDetail.distributor == 'C'}">
									主订单号：${orderDetail.billId} <br/>子订单号： ${orderDetail.orderNo}
								</c:when>
								<c:otherwise>
									订单号： ${orderDetail.orderNo}
								</c:otherwise>
							</c:choose>
						</td>
						<td style="width: 300px;">订单来源： ${orderDetail.distributorCh }---${orderDetail.shopNameCh }</td>
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
						<td>原编码： ${orderDetail.salePnrCode }</td>
						<td class="detail" style="width: 300px;">
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
				
				<table class="g-table table-list" >
					<thead class="table-hd ">
						<tr>
							<th class="col01">业务线</th>
							<th class="col02">订单来源</th>
							<th class="col02">价格来源</th>
							<th class="col01">生单PNR</th>
							<th class="col02">政策代码</th>
							<th class="col02">大编码</th>
							<th class="col02">政策类型</th>
							<th class="col01">报价类型</th>
							<th class="col02">产品类型</th>
							<th class="col02">指派方式</th>
						</tr>
					</thead>
					<tbody class="table-bd">
						<tr>
							<td><!-- 国际TTS包装出票任务 -->-</td>
							<td>
								<c:if test="${orderDetail.source == 'self' }">普通政策</c:if>
								<c:if test="${orderDetail.source == 'qfare' }">加价政策</c:if>
								<c:if test="${orderDetail.source == '加价政策' }">特殊政策</c:if>
								<c:if test="${orderDetail.source == 'othe' }">其它</c:if>
							</td>
							<td>-</td>
							<td>${orderDetail.pnrCode }</td>
							<td>${orderDetail.policyCode }</td>
							<td>${orderDetail.pnrCodeBig }</td>
							<td>
								<c:if test="${orderDetail.policyType == '1' }">NFD</c:if>
								<c:if test="${orderDetail.policyType == '2' }"> 清仓产品</c:if>
								<c:if test="${orderDetail.policyType == '3' }">商旅产品</c:if>
								<c:if test="${orderDetail.policyType == '5' }">中转产品</c:if>
								<c:if test="${orderDetail.policyType == '6' }">包机</c:if>
								<c:if test="${orderDetail.policyType == '7' }">切位</c:if>
								<c:if test="${orderDetail.policyType == '8' }">航司VIP卡</c:if>
								<c:if test="${orderDetail.policyType == '10000' }">普通供应商</c:if>
								<c:if test="${orderDetail.policyType == '86' }">OpenAPI对外接口规范</c:if>
								<c:if test="${orderDetail.policyType == '10001' }">规则运价</c:if>
								<c:if test="${orderDetail.policyType == '-1' }">其它</c:if>
							</td>
							<td>-</td>
							<td>-</td>
							<td>-</td>
						</tr>
					</tbody>
				</table>
				
				<!-- 报价类型 -->
				<table class="g-table table-list">
					<thead class="table-hd ">
						<tr>
							<th class="pcol05">行程类型</th>
							<th class="pcol08">航班号</th>
							<th class="pcol05">舱位</th>
							<th class="pcol08">实际航班号</th>
							<th class="pcol07">实际舱位</th>
							<th class="pcol11">起降机场</th>
							<th class="pcol16">起飞时间</th>
							<th class="pcol16">降落时间</th>
							<th class="pcol07">行李额</th>
							<th class="pcol07">航班信息</th>
							<th class="pcol10">用户支付金额</th>
						</tr>
					</thead>
					<tbody class="table-bd">
						<c:forEach items="${orderDetail.flights }" var="flight" varStatus="i" >
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
							<td>${flight.flightNum } 
							<c:if test="${flight.isShared == 'Y' }">(${flight.shareNum})</c:if>
<%--									<c:if test="${flight.isShared == 'N' }">不共享</c:if>--%>
<%--									<c:if test="${flight.isShared == 'T' }">其他</c:if>--%>
							</td>
							<td>${flight.cabin }</td>
							<td>${flight.actFltno } 
							<td>${flight.actCabin }</td>
							<td>${flight.depAircodeCh }-${flight.arrAircodeCh }(${flight.depAircode }-${flight.arrAircode }) </td>
							<td>${flight.departureDate}&nbsp;${flight.departureTime }</td>
							<td>
								${flight.arrivalDate}&nbsp;${flight.arrivalTime }
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
														var arrivalDate="${flight.arrivalDate}";
														var departureTime="${flight.departureTime}";
														var arrivalTime="${flight.arrivalTime}";
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
									<a href="javascript:;" class=""> <i class="rmb">${orderDetail.currency }</i>${orderDetail.allprice } </a>
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
													<td><i class="rmb">${orderDetail.currency }</i> ${orderDetail.adultTax }</td>
													<td>${orderDetail.adultsCount }</td>
												</tr>
												<tr>
													<td>快递费</td>
													<td><i class="rmb">${orderDetail.currency }</i> 0.00</td>
													<td>1</td>
												</tr>
												<tr>
													<td>成人Price</td>
													<td><i class="rmb">${orderDetail.currency }</i> ${orderDetail.adultPrice }</td>
													<td>${orderDetail.adultsCount }</td>
												</tr>
			
												<tr style="color:#f60;">
													<td>订单总价</td>
													<td><i class="rmb">${orderDetail.currency }</i> ${orderDetail.allprice }</td>
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
				<table class="g-table table-list" >
					<thead class="table-hd ">
						<tr>
							<th class="pcol05">序号</th>
							<th class="pcol08">乘客姓名</th>
							<th class="pcol10">乘客类型/性别</th>
							<th class="pcol09">国籍</th>
							<th class="pcol08">证件类型</th>
							<th class="pcol16">证件号(生日)</th>
							<th class="pcol08">票面价</th>
							<th class="pcol06">基建</th>
							<th class="pcol06">燃油</th>
							<th class="pcol08">销售价</th>
							<th class="pcol06">佣金</th>
							<th class="pcol10">PNR</th>
						</tr>
					</thead>
					<tbody class="table-bd">
						<c:forEach items="${orderDetail.passengers}" var="passenger" varStatus="i" >
							<tr>
								<td>${passenger.pindex }</td>
								<td>${passenger.name }</td>
								<td>
									<c:if test="${passenger.ageType == '-2' }">其他</c:if>
									<c:if test="${passenger.ageType == '-1' }">留学生</c:if>
									<c:if test="${passenger.ageType == '0' }">成人</c:if>
									<c:if test="${passenger.ageType == '1' }">儿童</c:if>
									/
									<c:if test="${passenger.gender == 'M' }">男</c:if>
									<c:if test="${passenger.gender == 'F' }">女</c:if>
								</td>
								<td>${passenger.nationality }</td>
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
								<td class="redFont">${passenger.price }</td>
								<td class="redFont">${passenger.taxFee }</td>
								<td  class="redFont">${passenger.oilFee }</td>
								<td class="redFont">
									${passenger.cost }
								</td>
								<td class="redFont">
									${passenger.commission }
								</td>
								<td>${passenger.pnr}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
			</div>
		</div>
	</div>
		
</body>
</html>