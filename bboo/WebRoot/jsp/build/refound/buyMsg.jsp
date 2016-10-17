<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/jsp/share/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="${ctx }/static/js/build/refund/buyMsg.js"></script>
</head>
<body>
		
	<div class="detail-model">
		<div class="mod-hd">
			<span class="show">收起</span>
			<h2>采购信息</h2>
		</div>
		<div class="g-content">
			<!-- 采购信息 -->
			<table class="g-table table-list" style="width: 700px;">
				<thead class="table-hd ">
					<tr>
						<th class="pcol12">采购地</th>
						<th class="pcol18">订单号</th>
						<th class="pcol13">PNR</th>
						<th class="pcol13">大编</th>
						<th class="pcol15">支付方式</th>
						<th class="pcol12">采购人</th>
						<th class="pcol17">订单创建时间</th>
					</tr> 
				</thead>
				<tbody class="table-bd">
					<tr>
						<td>${buyOrderEntity.purchasePlaceCh }</td>
						<td>${buyOrderEntity.purchaseNo }</td>
						<td><a href="javascript:void(0)" class="rtMsg">${buyOrderEntity.pnrCode }</a></td>
						<td>${buyOrderEntity.pnrCodeBig }</td>
						<td>
							<c:if test="${buyOrderEntity.payWay == 1}">财付通非担保</c:if>
							<c:if test="${buyOrderEntity.payWay == 2}">支付宝非担保</c:if>
							<c:if test="${buyOrderEntity.payWay == 10}">快钱非担保</c:if>
							<c:if test="${buyOrderEntity.payWay == 11}">快钱担保</c:if>
							<c:if test="${buyOrderEntity.payWay == 12}">支付宝担保</c:if>
							<c:if test="${buyOrderEntity.payWay == 13}">财付通担保</c:if>
							<c:if test="${buyOrderEntity.payWay == -1}">其它</c:if>
						</td>
						<td>${buyOrderEntity.lockUser }</td>
						<td>${buyOrderEntity.createTime }</td>
					</tr>
				</tbody>
				<!-- 采购乘客信息 -->
				<thead class="table-hd ">
					<tr>
						<th>姓名</th>
						<th>票面价</th>
						<th>基建</th>
						<th>燃油</th>
						<th>结算价</th>
						<th colspan="2">票号</th>
					</tr>
				</thead>
				<tbody class="table-bd">
					<c:forEach items="${buyOrderEntity.passengers}" var="passenger" varStatus="i" >
					<tr>
						<td>${passenger.name }</td>
						<td class="redFont">${passenger.printPrice }</td>
						<td class="redFont">${passenger.taxFee }</td>
						<td class="redFont">${passenger.oilFee }</td>
						<td class="redFont">${passenger.cost }</td>
						<td colspan="2"><a href="javascript:void(0)" class="ticketNum">${passenger.eticketNum }</a></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="ticketDiv"></div>
		</div>
	</div>
		
		
</body>
</html>