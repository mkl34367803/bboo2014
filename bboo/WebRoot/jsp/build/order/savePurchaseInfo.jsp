<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/jsp/share/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>采购信息录入</title>
<%@include file="/jsp/share/JS.jsp"%>
<%@include file="/jsp/share/CSS.jsp"%>
<script type="text/javascript">
var ctx = "${ctx}";

function caculate(){
// 	if(!$("#purchasePlace").val()){
// 		alert("请先选择采购地");
// 	}
	var printPrice=Number($("#printPrice").val());
	var backPoint=Number($("#backPoint").val());
	var reward=Number($("#reward").val());
	var price=printPrice*(1-backPoint/100)-reward;
	if($("#purchasePlace").val()==2){  //2表示今日天下
		price=Math.round(price);
	}else if($("#purchasePlace").val()==1){
		price=Math.ceil(price);
	}
	$("#price").numberbox('setValue', price); //支付票款
	var taxFee=Number($("#taxFee").val());  //基建
	var oilFee=Number($("#oilFee").val());  //燃油
	$("#payPirce").val(price+taxFee+oilFee);  //结算价
	var totalPrice=0;  //总的销售价格
	<c:forEach items="${orderDetail.passengers }" var="passenger" varStatus="i" >
	var p_cost=${passenger.cost};
	var p_oilFee=${passenger.oilFee};
	var p_taxFee=${passenger.taxFee};
	var p_commission=${passenger.commission};
	totalPrice+=Number(p_cost)+Number(p_oilFee)+Number(p_taxFee)-Number(p_commission);
	</c:forEach>
	var passenger_number="${fn:length(orderDetail.passengers)}";
	var len=Number(passenger_number);
// 	$("#allprice").val((price+taxFee+oilFee)*len);
	var allprice=(price+taxFee+oilFee)*len;
	$("#allprice").numberbox('setValue',allprice);
// 	利润=销售价- （printPrice*（1-（backPoint+afterPoint+stickingPoint）/100）-reward）*人数
	var afterPoint=Number($("#afterPoint").val());
// 	var stickingPoint=Number($("#stickingPoint").val());
	var lr=totalPrice-allprice;
	$("#lr").numberbox('setValue', lr);
	$("#profitExtend").html("利润:"+lr.toFixed(2));
}
function caculateAllPrice(){
	var printPrice=Number($("#printPrice").val());
	var backPoint=Number($("#backPoint").val());
	var taxFee=Number($("#taxFee").val());  //基建
	var oilFee=Number($("#oilFee").val());  //燃油
	var len=Number("${fn:length(orderDetail.passengers)}");
	var allprice=Number($("#allprice").val());
	var payPirce=allprice/len;
	$("#payPirce").numberbox('setValue',payPirce);  //单个人结算价
	var price=allprice/len-taxFee-oilFee;
	$("#price").numberbox('setValue',price);  //支付票款
	var reward=printPrice*(1-backPoint/100)-(allprice/len-taxFee-oilFee);
	$("#reward").numberbox('setValue', reward);
	var totalPrice=0;  //总的销售价格
	<c:forEach items="${orderDetail.passengers}" var="passenger" varStatus="i" >
	var p_cost=${passenger.cost};
	var p_oilFee=${passenger.oilFee};
	var p_taxFee=${passenger.taxFee};
	var p_commission=${passenger.commission};
	totalPrice+=Number(p_cost)+Number(p_oilFee)+Number(p_taxFee)-Number(p_commission);
	</c:forEach>
	var lr=totalPrice-allprice;
	$("#lr").numberbox('setValue', lr);
	$("#profitExtend").html("利润:"+lr.toFixed(2));
	if(backPoint==0){
		$("#backPoint").val(0);
	}
}

function initPurchaseInfo(){
	var isExsitAdult=false;
	var isExsitChild=false;
	var adultPrice=0;
	var adultOilFee=0;
	var adultTaxFee=0;
	var childPrice=0;
	var childOilFee=0;
	var childTaxFee=0;
	
	<c:if test="${empty buyOrderEntity.pnrCode}">
		$("#pnrCode").val("${orderDetail.pnrCode}");
	</c:if>
	<c:forEach items="${orderDetail.passengers}" var="passenger" varStatus="i" >
		var ageType=${passenger.ageType};
		if(ageType==0){
			isExsitAdult=true;
			adultPrice=${passenger.price};
			adultOilFee=${passenger.oilFee};
			adultTaxFee=${passenger.taxFee};
		} else if(ageType==1){
			isExsitChild=true;
			childPrice=${passenger.price};
			childOilFee=${passenger.oilFee};
			childTaxFee=${passenger.taxFee};
		}
	</c:forEach>
	var rtPrintPrice="";
	<c:forEach items="${orderDetail.flights}" var="flight" varStatus="i" >
		rtPrintPrice="${flight.actPrice}";
	</c:forEach>
	if(isExsitAdult&&!isExsitChild){
	<c:if test="${empty buyOrderEntity.printPrice}">
		if(rtPrintPrice!=""){
			$("#printPrice").numberbox('setValue', rtPrintPrice);  //票面价，如果rt有信息，用rt出来的flight表里的实际票面价
		}else{
			$("#printPrice").numberbox('setValue', adultPrice);  //票面价，如果rt有信息，用rt出来的flight表里的实际票面价
		}
	</c:if>
	<c:if test="${empty buyOrderEntity.taxFee}">
		$("#taxFee").numberbox('setValue', adultTaxFee);
	</c:if>
	<c:if test="${empty buyOrderEntity.oilFee}">
		$("#oilFee").numberbox('setValue', adultOilFee);
	</c:if>
	}else if(!isExsitAdult&&isExsitChild){
	<c:if test="${empty buyOrderEntity.printPrice}">
		$("#printPrice").numberbox('setValue', childPrice);
	</c:if>
	<c:if test="${empty buyOrderEntity.taxFee}">
		$("#taxFee").numberbox('setValue', childTaxFee);
	</c:if>
	<c:if test="${empty buyOrderEntity.oilFee}">
		$("#oilFee").numberbox('setValue', childOilFee);
	</c:if>
	}
	caculate();  //最后调用一次计算的方法
}

/**
 * 验证订单是否满足创单条件
 */
function isSatisfyCreateOrderCondition(id,flightClass){
	$.ajax({
		type : "post",
		dataType : "json",
		data : {"id":id},
		url : ctx + "/order/gj-order-deal!isSatisfyCreateOrderCoditionByThirdInterface.do",
		success : function(result) {
			if (result.error) {
				alert(result.error);
			}
			if (result.success) {
				alert(result.success);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求出错了");
		}
	});
}

/**
 * 传入订单的id值,航班类型I表示国际订单，N表示国内订单
 * extend,扩展的方法,用于先判断一下,订单是否满足创单条件.
 */
function savePurchaseInfoExtend(id,flightClass){
	if ($("#purchasePlace").val() == null || $("#purchasePlace").val() == '') {
		alert("采购地为必填字段");
		$("#savePurchase").attr("disabled",false);
		return;
	}
	if ($("#payWay").val() == null || $("#payWay").val() == '') {
		alert("支付方式为必填字段");
		$("#savePurchase").attr("disabled",false);
		return;
	}
	if ($("#printPrice").val() == "") {
		alert("票面价不能为空");
		$("#savePurchase").attr("disabled",false);
		return;
	}
	if ($("#backPoint").val() == "") {
		alert("返点不能为空");
		$("#savePurchase").attr("disabled",false);
		return;
	}
	var distributor="${orderDetail.distributor}";
	if(distributor!="N"){
		$.ajax({
			type : "post",
			dataType : "json",
			data : {"id":id},
			url : ctx + "/order/gj-order-deal!isSatisfyCreateOrderCoditionByThirdInterface.do",
			success : function(result) {
				if (result.error) {
					if(confirm(result.error+":是否还要继续创建订单?")){
						savePurchaseInfo(id,flightClass);
					}
				}
				if (result.success) {
					savePurchaseInfo(id,flightClass);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$("#savePurchase").attr("disabled", false);
				alert("请求出错了");
			}
		});
	}else{
		savePurchaseInfo(id,flightClass);
	}
	
}

/**
 * 传入订单的id值,航班类型I表示国际订单，N表示国内订单
 */
function savePurchaseInfo(id,flightClass) {  
	$("#savePurchase").attr("disabled",true); //随便点，后台判断了是插入还是更新
	var data = {};
	var ticketNos=[];
	//又有新的东西添加进来,票号
	$("input[name='ticketNo']").each(function(i){
       var id = $(this).attr("id"); 
       var ticketNo= $(this).val();
       ticketNos[i]={"id":id,"ticketNo":ticketNo};
	});
	data.ticketNos=ticketNos;
	data.id = id;
	// data.purchaseNo=$("#purchaseNo").find("option:selected").text();
	data.purchaseNo = $("#purchaseNo").val(); // 采购订单号
	// data.pnrCode=$("#pnrCode").find("option:selected").text();
	data.pnrCode = $("#pnrCode").val();
	data.purchasePlaceCh = $("#purchasePlace").find("option:selected").text();
	data.purchasePlace = $("#purchasePlace").val();
	data.purchaseAccount = $("#purchaseAccount").find("option:selected").text();
	if ($("#purchasePlace").val() == null || $("#purchasePlace").val() == '') {
		alert("采购地为必填字段");
		$("#savePurchase").attr("disabled",false);
		return;
	}
	// data.payWay=$("#payWay").find("option:selected").text();
	if ($("#payWay").val() == null || $("#payWay").val() == '') {
		alert("支付方式为必填字段");
		$("#savePurchase").attr("disabled",false);
		return;
	}
	data.payWay = $("#payWay").val();
	data.payAccount = $("#payAccount").find("option:selected").text();
	data.transactionNo = $("#transactionNo").val();
	data.printPrice = $("#printPrice").val();
	if ($("#printPrice").val() == "") {
		alert("票面价不能为空");
		$("#savePurchase").attr("disabled",false);
		return;
	}
	if ($("#backPoint").val() == "") {
		alert("返点不能为空");
		$("#savePurchase").attr("disabled",false);
		return;
	}
	data.backPoint = $("#backPoint").val();
	data.afterPoint = $("#afterPoint").val();
	data.etdzOffno = $("#etdzOffno").val();
	data.etdzNo = $("#etdzNo").val();
	data.stickingPoint = $("#stickingPoint").val();
	data.reward = $("#reward").val();
	data.price = $("#price").val();
	data.payPirce = $("#payPirce").val();
	data.oilFee = $("#oilFee").val();
	data.taxFee = $("#taxFee").val(); // 基建
	data.allprice = $("#allprice").val(); // 结算总价
	data.lr = $("#lr").val(); // 利润
	data.slfRemark = $("#slfRemark").val(); // 备注
	data.lossReason = $("#lossReasonInput").val(); 
	
	var purchaseInfoVo=JSON.stringify(data);
	$.ajax({
		type : "post",
		dataType : "json",
		data : {"purchaseInfoVo":purchaseInfoVo},
		url : ctx + "/order/gj-order-deal!savePurchaseInfo.do",
		success : function(result) {
			if (result.error) {
				$("#savePurchase").attr("disabled", false); // 让按钮变得可用
				alert(result.error);
			}
			if (result.success) {
				var flightClass="${orderDetail.flightClass}";
				var startPage="<%=request.getSession().getAttribute("startPage")%>";
				redirectStartPage(flightClass,startPage,ctx);  //跳转到起始页面
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#savePurchase").attr("disabled", false);
			alert("请求出错了");
		}
	});
}

function modifyPnr(){
	if($("#pnrCode").val().length!=6){
		alert("pnr必须是6位");
		return;
	}
	showLoadings("正在修改pnr... ...",1);
// 	$("#loading").css("display", "block");
// 	$("#fade").css("display", "block");
	$.ajax({
			url : "${ctx }/order/gj-order-deal!modifyPnrAndProductData.do",
			dataType : "json",
			data :{"id":"${orderDetail.id}","pnrCode":$("#pnrCode").val()},
			type : "POST",
			success : function (result) {
				hideLoadings(1);
				if(result.error){
// 					$("#loading").css("display", "none");
// 					$("#fade").css("display", "none");
					alert(result.error);
				}else if(result.success){
					//重新刷新一次页面
					window.location.href = "${ctx }/order/gj-order-detail!queryOrderByID.do?id="+result.id;
				}
// 				$("#loading").css("display", "none");
// 				$("#fade").css("display", "none");
			},
	});
}

function showReserverPnrDiv(){
	$("#reservePnrDiv").css("display", "block");
	$("#fade").css("display", "block");
	$.ajax({
		url : "${ctx }/order/gj-order-deal!getsdOfficeBymno.do",
		dataType : "json",
		type : "POST",
		success : function (result) {
			if(result.error){
				alert(result.error);
			}else if(result.success){
				//首先置空table_body
				$("#officeNoList").html("");
				$.each(result.list,function(i,item){
					var office=item.office;
					var radio="<input type='radio' name='office' value='"+office+"'/>"+office;
					$("#officeNoList").append(radio);
				});
			}
		},
	});
}

function reservePnrCode(){
	$("#reserve").attr("disabled",true);
	$.ajaxSettings.traditional=true;
	var param={};
	param.flights=[];
	param.office=$("input[name='office']:checked").val();
	if(param.office==undefined){
		alert("office号为必选字段");
		$("#reserve").attr("disabled",false);
		return;
	}
	param.id="${orderDetail.id}";
	$("#flightBody").find("tr").each(function(i,item){
		var tdArr=$(this).children();
		var id=tdArr.eq(0).find("input").val();
		var flightNum=tdArr.eq(1).find("input").val();
		var cabin=tdArr.eq(2).find("input").val();
		var flight={};
		flight.id=id;
		flight.flightNum=flightNum;
		flight.cabin=cabin;
		param.flights[i]=flight;
	});
	var jsonString=JSON.stringify(param);
	$.ajax({
		url : "${ctx }/order/gj-order-deal!reservePnrCode.do",
		dataType : "json",
		type : "POST",
		data:{param:jsonString},
		success : function (result) {
			if(result.error){
				alert(result.error);
				$("#reserve").attr("disabled",false);
			}else if(result.success){
				//重新刷新一次页面
				window.location.href = "${ctx }/order/gj-order-detail!queryOrderByID.do?id="+result.id;
			}
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求出错了");
		}
	});
}

function reservePnrCodeCancel(){
	$("#reserve").attr("disabled",false);
	$("#reservePnrDiv").css("display", "none");
	$("#fade").css("display", "none");
}

/**
 * 查询采购账号
 */
function queryPurchaseAccount(){
	var purchaseAccount="${buyOrderEntity.purchaseAccount}";
	var purchasePlace= $("#purchasePlace").val();
	if(purchasePlace==""){
		alert("请先选择采购地！");
		return;
	}
	$.ajax({
		url : "${ctx }/order/gj-order-deal!queryPurchaseAccount.do",
		dataType : "json",
		async:false,
		type : "POST",
		data :{"purchasePlace":purchasePlace},
		success : function (result) {
			if(result.error){
				alert(result.error);
			}else if(result.success){
				$("#purchaseAccount").html("");
				if(result.list){
					var str="";
					$.each(result.list,function(i,item){
						if(purchaseAccount==item.account){
							str+="<option selected='selected' value='"+item.id+"'>"+item.account+"</option>";
						}else{
							str+="<option value='"+item.id+"'>"+item.account+"</option>";
						}
					});
					$("#purchaseAccount").append(str);
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求出错了");
		}
	});
}


/**
 * 查询支付账号
 */
function queryPayAccount(){
	var payAccount="${buyOrderEntity.payAccount}"
	var payWay= $("#payWay").val();
	if(payWay==""){
		alert("请先选择支付方式！");
		return;
	}
	$.ajax({
		url : "${ctx }/order/gj-order-deal!queryPayAccount.do",
		dataType : "json",
		async:false,
		type : "POST",
		data :{"payWay":payWay},
		success : function (result) {
			if(result.error){
				alert(result.error);
			}else if(result.success){
				$("#payAccount").html("");
				if(result.list){
					$.each(result.list,function(i,item){
						if(payAccount==item.account){
							$("#payAccount").append("<option selected='selected' value='"+item.id+"'>"+item.account+"</option>");
						}else{
							$("#payAccount").append("<option value='"+item.id+"'>"+item.account+"</option>");
						}
					});
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求出错了");
		}
	});
}

function purchasePlaceChange(){
	caculate();
	queryPurchaseAccount();
}

function lossReasonChange(){
	var lossReasonSelect=$("#lossReason").find("option:selected").text()
	$("#lossReasonInput").val(lossReasonSelect);
}

$(document).ready(function(){
	//$("#purchaseInfoTable tr td input").css("width","100px");
	initPurchaseInfo();
	var orderDealTag="${orderDealTag}";
	if(orderDealTag!="true"){
		queryPurchaseAccount();
		queryPayAccount();
	}
	$("#purchaseInfoTable input").addClass("g-ipt-text");
	$("#purchaseInfoTable input").css("width","100px");

});
</script>
</head>
<body>
	
		
		<div class="detail-model">
			<div class="mod-hd" id="js-mod-orderPurchase">
				<span class="show">收起</span>
				<h2>采购信息</h2>
			</div>
			<div class="g-content">
				<form >
						<table class="g-table table-list" id="purchaseInfoTable">
							<thead  class="table-hd">
								<tr>
									<th>采购pnr</th>
									<th>采购订单号</th>
									<th>采购地</th>
									<th>采购账号 </th>
									<th>支付方式</th>
									<th>支付账号</th>
									<th>票面价</th>
									<th>返点</th>
						
								</tr>
						   </thead>
						   <tbody>
								<tr>	
									<td>
										<input name="pnrCode" id="pnrCode" value="${buyOrderEntity.pnrCode}">
										<script type="text/javascript">
											var orderDealTag="${orderDealTag}";
											if(orderDealTag=="true"){
												document.write('<a><img alt="" src="${ctx }/images/build/pencil.png" onclick="javascript:modifyPnr();"></a>');
											}
											
										</script>
									</td>
									<td>
										<input name="purchaseNo"  id="purchaseNo" value="${buyOrderEntity.purchaseNo}">
									</td>
								
									<td>
										<oms:select showEmpty="true" id="purchasePlace"
										name="purchasePlace"  onchange="purchasePlaceChange()">
											<oms:options value="${buyOrderEntity.purchasePlace}"></oms:options>
										</oms:select>
									</td>
									<td>
										<select  id="purchaseAccount">
											<option>--------</option>
										</select>
									</td>
									<td>
										<oms:select showEmpty="true" id="payWay"
										name="payWay"  onchange="queryPayAccount()">
											<oms:options value="${buyOrderEntity.payWay}"></oms:options>
										</oms:select>
									</td>
									<td>
										<select id="payAccount">
											<option>--------</option>
										</select>
									</td>
									<td>
										<input name="printPrice" id="printPrice" onkeyup="caculate()" class="easyui-numberbox" data-options="precision:2"     value="${buyOrderEntity.printPrice}">
									</td>
									<td>
										<input name="backPoint" id="backPoint" onkeyup="caculate()" class="easyui-numberbox" data-options="precision:2"   value="${buyOrderEntity.backPoint}">
									</td>
									
								</tr>
								
							</tbody>
							<thead  class="table-hd">
								<tr>
									<th>返现 </th>
									<th>基建</th>
									<th>燃油</th>
									<th>交易流水号 </th>
									<th>后返</th>
									<th>出票配置</th>
									<th>打票机号</th>
									<th>支付机票款</th>
								</tr>
						   </thead>
						   <tbody>
								<tr>
									<td>
										<input name="reward" id="reward" onkeyup="caculate()" class="easyui-numberbox" data-options="precision:2" value="${buyOrderEntity.reward}">
									</td>
									<td>
										<input name="taxFee" id="taxFee" onkeyup="caculate()" class="easyui-numberbox" data-options="precision:2" value="${buyOrderEntity.taxFee}">
									</td>
									<td>
										<input name="oilFee" id="oilFee" onkeyup="caculate()" class="easyui-numberbox" data-options="precision:2" value="${buyOrderEntity.oilFee}">
									</td>
									
									<td>
										
										<input name="transactionNo" id="transactionNo" value="${buyOrderEntity.transactionNo}">
									</td>
									<td>
										<input name="afterPoint" id="afterPoint" onkeyup="caculate()" class="easyui-numberbox" data-options="precision:2" value="${buyOrderEntity.afterPoint}">
									</td>
									<td>
										<input name="etdzOffno" id="etdzOffno" value="${buyOrderEntity.etdzOffno}">
									</td>
									<td>
										<input name="etdzNo" id="etdzNo"  value="${buyOrderEntity.etdzNo}">
									</td>
									<td>
										<input name="price" id="price" onkeyup="caculatePrice()" class="easyui-numberbox" data-options="precision:2"  disabled="disabled" value="${buyOrderEntity.cost}">
									</td>
								</tr>
							</tbody>
							<thead  class="table-hd">
								<tr>
									
									<th>结算价 </th>
									<th>结算总价</th>
									<th>利润</th>
									<th>添加采购备注</th>
									<th>亏损原因</th>
									<th></th>
									<th></th>
									<th></th>
								</tr>
						   </thead>
						   <tbody>
								<tr>
									
									<td>
										<input name="payPirce" id="payPirce" disabled="disabled"  class="easyui-numberbox"  value="${buyOrderEntity.payPirce}">
									</td>
									<td>
										<input name="allprice" id="allprice"  onkeyup="caculateAllPrice()" class="easyui-numberbox" data-options="precision:2"  value="${buyOrderEntity.allprice}">
									</td>
									<td>
										<input name="lr" id="lr"  disabled="disabled" class="easyui-numberbox" data-options="precision:2"  value="${buyOrderEntity.lr}">
									</td>
									<td><input  type="text" name="slfRemark"  id="slfRemark"  value="${buyOrderEntity.slfRemark}"></td>
									<td>
										<oms:select showEmpty="true" id="lossReason" name="lossReason"  onchange="lossReasonChange()">
											<oms:options value="${buyOrderEntity.lossReason}"></oms:options>
										</oms:select>
									</td>
									<td><input type="text" id="lossReasonInput" value="${buyOrderEntity.lossReason}"></td>
								</tr>
								</tr>
							</tbody>
						</table>
					</form>
					<div class="btn-box" style=" margin:0 auto; text-align:center;padding: 10px">
						<button id="isSatisfyCreateOrderCondition" class="g-btn js_submit" onclick="isSatisfyCreateOrderCondition('${orderDetail.id}','${orderDetail.flightClass}')">
							<span>是否可以创单</span>
						</button>
						<button id="savePurchase" class="g-btn js_submit" onclick="savePurchaseInfoExtend('${orderDetail.id}','${orderDetail.flightClass}')">
							<span>保存采购信息</span>
						</button>
						<script type="text/javascript">
							var orderDealTag="${orderDealTag}";
							if(orderDealTag=="true"){
								document.write('<button id="reservePnrButton" class="g-btn js_submit" onclick="showReserverPnrDiv();">');
								document.write('<span>预订编码</span>');
								document.write('</button>');
							}
							
						</script>
					<div style="float:right;padding-right:50px;color: red;font-size: 20px;width:300px " id="profitExtend">
						袁巧
					</div>
					</div>
				</div>
		</div>		
		<div id="reservePnrDiv" class="white_content" style="top:15%;left:15%;width: 900px;height: 350px">
			<div>
				<div>
					<h2>航班信息</h2>
				</div>
				<table class="g-table table-list">
					<thead  class="table-hd">
						<tr>
							<th style="display: none;">id</th>
							<th>航班号</th>
							<th>舱位</th>
							<th>起飞城市</th>
							<th>到达城市</th>
							<th>起飞时间</th>
							<th>到达时间</th>
						</tr>
					</thead>
					<tbody class="table-bd"  id="flightBody">
						<c:forEach items="${orderDetail.flights}" var="flight" varStatus="i">
							<tr>
								<td style="display: none;"><input name="id" value="${flight.id}" /></td>
								<td><input name="flightNum" value="${flight.flightNum}"/></td>
								<td><input name="cabin" value="${flight.cabin}"></td>
								<td>${flight.depAircode}</td>
								<td>${flight.arrAircode}</td>
								<td>${flight.departureDate}&nbsp;${flight.departureTime }</td>
								<td>${flight.arrivalDate}&nbsp;${flight.arrivalTime }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div>
				<h2>乘客信息</h2>
				<table class="g-table table-list">
					<thead class="table-hd">
						<tr>
							<th>乘客姓名</th>
							<th>证件类型</th>
							<th>证件号</th>
						</tr>
					</thead>
					<tbody class="table-bd">
						<c:forEach  items="${orderDetail.passengers}" var="passenger" varStatus="i" >
							<tr>
								<td>${passenger.name}</td>
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
								<td>${passenger.cardNum}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div>
				<h2>office号</h2>
				<div id="officeNoList" style="padding: 10px"></div>
			</div>
			<div class="btn-box" style=" margin:0 auto; text-align:center;padding: 10px">
				<button id="reserve" class="g-btn js_submit" onclick="reservePnrCode()">
					<span>预订</span>
				</button>
				<button id="reserveCancel" class="g-btn js_submit" onclick="reservePnrCodeCancel()">
					<span>取消</span>
				</button>
			</div>
		</div>
</body>
</html>