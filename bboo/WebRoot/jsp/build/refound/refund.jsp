<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/jsp/share/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>退票处理页面</title>
<%@include file="/jsp/share/JS.jsp"%>
<%@include file="/jsp/share/CSS.jsp"%>
<script type="text/javascript" src="${ctx }/static/js/build/refund/refundTools.js"></script>
<script type="text/javascript" src="${ctx }/static/js/build/refund/refund.js"></script>
<style type="text/css">
body {
	min-width: 800px;
	padding-right: 20px;
}
.mod-hd {
    background: #56a4e0;
    height: 20px;
    padding: 4px 6px 4px 10px;
    line-height: 20px;
    color: #fff;
    vertical-align: middle;
    cursor: pointer;
}
.refoundDiv input[type="text"] {
	width: 100px;
}
.redFont {
	color: red;
}
.refund-hd {
    background: #56a4e0;
    height: 20px;
    padding: 4px 6px 4px 10px;
    line-height: 20px;
    color: #fff;
    vertical-align: middle;
    cursor: pointer;
}
.refundRuleDiv {
	display: none;
}
</style>
<script type="text/javascript">
	var ctx = "${ctx }";
	var fkid = "${orderDetail.id}";
	var buyorderId = "${buyOrderEntity.id}";
	var passengerCount = "${orderDetail.passengerCount }";
	var airlineCount = "${orderDetail.airlineCount }";
	var error = "${error }";
	if (error) {
		alert(error);
	}
	var fligntNumArr = [];
	var sharNumArr = [];
	var depAircodeArr = [];
	var arrAircodeArr = [];
	var departureDateArr = [];
	var departureTimeArr = [];
	var passengerIdArr = [];
	
	var operator = "refund";
	var brid = null;
	
	var carrierArr = [];
	var cabinArr = [];
</script>
</head>
<body>
<form action="" method="post" name="savePurchForm" id="savePurchForm"></form>
	<div class="m-content" style="width: 100%">
		
		<!-- 订单详情 -->
		<jsp:include page="orderMsgSelect.jsp"></jsp:include>
		
		<!-- 采购信息 -->
		<jsp:include page="buyMsg.jsp"></jsp:include>
		
		<div class="detail-model">
			<div class="mod-hd">
				<span class="show">收起</span>
				<h2>退票信息</h2>
			</div>
			<div class="g-content">
				<div class="psgRuleDiv">
					乘客退票规则：${buyOrderEntity.returnTicketRule }<br><br>
					乘客改期规则：${buyOrderEntity.changeDateRule }
				</div>
				<div class="refundRuleDiv">
				</div>
				
				
				<div class="refoundDiv psgRefund" style="margin-bottom: 10px;">
					<div class="refund-hd" style="background-color: #87CEFA">
						<h2>乘客退票信息</h2>
					</div>
					<div class="g-content" style="margin-bottom: 10px">
						<table class="g-table table-list" >
							<thead class="table-hd ">
								<tr>
									<th class="pcol20">舱位票面价</th>
									<th class="pcol20">退票的票面</th>
									<th class="pcol20">燃油</th>
									<th class="pcol20">机建</th>
									<th class="pcol20">退票费率</th>
								</tr>
							</thead>
							<tbody class="table-bd">
								<tr>
									<c:forEach items="${orderDetail.passengers}" var="passenger" varStatus="i" end="0" >
									<c:forEach items="${orderDetail.flights}" var="flight" varStatus="i" end="0" >
										<td><input name="face" value="${passenger.price }" type="text" onkeyup="caculate('')" /></td>
										<td><input name="baseFace" value="${passenger.price }" type="text" onkeyup="caculate('')" /></td>
										<td><input name="yq" value="${passenger.oilFee }" type="text" onkeyup="caculate('')" /></td>
										<td><input name="tax" value="${passenger.taxFee }" type="text" onkeyup="caculate('')" /></td>
										<td><input name="rate" value="" type="text" onkeyup="caculate('')" /></td>
									<script type="text/javascript">
										var cost = ${passenger.cost};
									</script>
									</c:forEach>
									</c:forEach>
								</tr>
							</tbody>
							<thead class="table-hd ">
								<tr>
									<th>退票费</th>
									<th>退款金额</th>
									<th>实际退款金额(含税)</th>
									<th>是否自愿</th>
									<th>退票</th>
								</tr>
							</thead>
							<tbody class="table-bd">
								<tr>
									<td><input name="fee" value="" type="text" onkeyup="caculateByFee('')" /></td>
									<td><input name="refund" value="" type="text" disabled="disabled" /></td>
									<td><input name="actRefund" value="" type="text" disabled="disabled" /></td>
									<td>
										<label> <input name="rtype" value="0" type="radio" checked="checked">自愿</label> 
										<label style="margin-left: 10px;"> <input name="rtype" value="1" type="radio">非自愿</label> 
									</td>
									<td>
										<label> <input name="isvoid" value="0" type="radio">作废</label> 
										<label style="margin-left: 10px;"> <input name="isvoid" value="1" type="radio" checked="checked">退票</label> 
									</td>
								</tr>
								<tr>
									<td colspan="5">
										<input name="nextBtn" type="button" class="g-btn" value="下一步" />
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				
				<div class="refoundDiv supRefund">
					<div class="refund-hd" style="background-color: #87CEFA">
						<h2>供应商退票信息</h2>
					</div>
					<div class="g-content" style="margin-bottom: 10px">
						<table class="g-table table-list" >
							<thead class="table-hd ">
								<tr>
									<th class="pcol17">舱位票面价</th>
									<th class="pcol17">退票的票面</th>
									<th class="pcol16">燃油</th>
									<th class="pcol16">机建</th>
									<th class="pcol17">退票费率</th>
									<th class="pcol17">退票费</th>
								</tr>
							</thead>
							<tbody class="table-bd">
								<tr>
									<c:forEach items="${buyOrderEntity.passengers}" var="passenger" varStatus="i" end="0" >
									<c:forEach items="${buyOrderEntity.flights}" var="flight" varStatus="i" end="0" >
										<td><input name="face2" value="${flight.printPrice }" type="text" onkeyup="caculate(2)" /></td>
										<td><input name="baseFace2" value="${flight.actPrice }" type="text" onkeyup="caculate(2)" /></td>
										<td><input name="yq2" value="${passenger.oilFee }" type="text" onkeyup="caculate(2')" /></td>
										<td><input name="tax2" value="${passenger.taxFee }" type="text" onkeyup="caculate(2)" /></td>
										<td><input name="rate2" value="" type="text" onkeyup="caculate(2)" /></td>
										<td><input name="fee2" value="" type="text" onkeyup="caculateByFee(2)" /></td>
										<script type="text/javascript">
											var cost2 = ${passenger.cost};
											var baseFace2 = "${flight.actPrice }";
											if (baseFace2 == "") {
												$("input[name='baseFace2']").val("${flight.printPrice }");
											}
										</script>
									</c:forEach>
									</c:forEach>
								</tr>
							</tbody>
							<thead class="table-hd ">
								<tr>
									<th>退款金额</th>
									<th>实际退款金额(含税)</th>
									<th>是否自愿</th>
									<th>退票</th>
									<th>是否留票</th>
									<th>利润</th>
								</tr>
							</thead>
							<tbody class="table-bd">
								<tr>
									<td><input name="refund2" value="" type="text" disabled="disabled" /></td>
									<td><input name="actRefund2" value="" type="text" disabled="disabled" /></td>
									<td>
										<label> <input name="rtype2" value="0" type="radio" checked="checked">自愿</label> 
										<label style="margin-left: 10px;"> <input name="rtype2" value="1" type="radio">非自愿</label> 
									</td>
									<td>
										<label> <input name="isvoid2" value="0" type="radio">作废</label> 
										<label style="margin-left: 10px;"> <input name="isvoid2" value="1" type="radio" checked="checked">退票</label> 
									</td>
									<td>
										<label> <input name="state" value="1" type="radio">退票</label> 
										<label style="margin-left: 10px;"> <input name="state" value="2" type="radio" checked="checked">留票</label> 
									</td>
									<td><input name="profit" value="" type="text" disabled="disabled" /></td>
								</tr>
								<tr>
									<td colspan="6">
										<input name="backBtn" type="button" class="g-btn" value="上一步" />
										<input name="refundBtn" type="button" class="g-btn" value="确定" />
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				
			</div>
		</div>
		
		<div class="detail-model">
			<div class="mod-hd" id="js-mod-logs">
				<span class="show">收起</span>
				<h2>舱位退改签信息</h2>
			</div>
			<div class="g-content">
				<div class="cabinRuldDiv">
				
				</div>
			</div>
		</div>
		
		<div class="detail-model">
			<div class="mod-hd" id="js-mod-logs">
				<span class="show">收起</span>
				<h2>航班时刻信息</h2>
			</div>
			<div class="g-content">
				<table class="flightDynamics g-table table-list" style="width: 650px;min-width: 650px;">
					<thead class="table-hd">
						<tr>
							<th class="pcol16">航班号</th>
							<th class="pcol08">出发地</th>
							<th class="pcol08">目的地</th>
							<th class="pcol14">起飞日期</th>
							<th class="pcol14">起飞时间</th>
							<th class="pcol14">到达时间</th>
							<th class="pcol14">航程时间</th>
							<th class="pcol12">状态</th>
						</tr>
					</thead>
					<tbody class="flightDynamics-td table-bd">
					</tbody>
				</table>
			</div>
		</div>
		
		<div class="detail-model">
			<div class="mod-hd" id="js-mod-logs">
				<span class="show">收起</span>
				<h2>操作日志</h2>
			</div>
			<div class="g-content">
				<%-- <ul>
					<c:forEach items="${sysLogEntities }" var="sysLog" varStatus="i">
						<li><i></i>${sysLog.operatime } ${sysLog.userName } <c:if test="${sysLog.logType == 'ORDER_LOG' }">[出票]</c:if> ${sysLog.contents }</li>
					</c:forEach>
				</ul> --%>
			</div>
		</div>
	</div>
	
	
	<c:forEach items="${orderDetail.flights }" var="flight" varStatus="i" >
	<script>
		fligntNumArr.push("${flight.flightNum}");
		sharNumArr.push("${flight.shareNum}");
		depAircodeArr.push("${flight.depAircode}");
		arrAircodeArr.push("${flight.arrAircode}");
		departureDateArr.push("${flight.departureDate}");
		departureTimeArr.push("${flight.departureTime}");
	</script>
	</c:forEach>
	
	<c:forEach items="${buyOrderEntity.flights}" var="flight" varStatus="i">
	<script>
		carrierArr.push("${flight.carrier}");
		cabinArr.push("${flight.cabin}");
	</script>
	</c:forEach>
	
	<c:forEach items="${buyOrderEntity.passengers}" var="passenger" varStatus="i" >
	<script>
		passengerIdArr.push("${passenger.id}");
	</script>
	</c:forEach>

</body>
</html>