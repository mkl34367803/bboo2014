<%@ page language="java" import="java.util.*,com.smart.entity.User" pageEncoding="UTF-8"%>
<%@include file="../../share/taglibs.jsp"%>
<%@ taglib prefix="t" uri="/WEB-INF/tlds/common.tld" %>
<%
	request.getSession().setAttribute("startPage", "orderListInfo");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>订单查询</title>
<%@include file="/jsp/share/JS.jsp"%>
<%@include file="/jsp/share/CSS.jsp"%>
<script src="${ctx }/static/js/build/order/orderDeal.js" type="text/javascript"></script>
<style type="text/css">
.black_overlay {
	display: none;
	position: fixed;
	top: 0%;
	left: 0%;
	width: 100%;
	height: 100%;
	background-color: black;
	z-index: 1001;
	-moz-opacity: 0.8;
	opacity: .80;
	filter: alpha(opacity = 80);
}

.white_content {
	display: none;
	position: fixed;
	top: 40%;
	left: 40%;
	width: 225px;
	height: 110px;
	padding: 16px;
	border: 16px solid #52a3e2;
	background-color: white;
	z-index: 1002;
	overflow: auto;
}
</style>

<script>
var ctx = "${ctx}";

//显示非当前页（分页的页脚代码）
pageNav.pHtml = function(pageNo, pn, showPageNo) {
	showPageNo = showPageNo || pageNo;
	var H = " <a href='javascript:queryOrderSummary({\"page\":" + pageNo
			+ "})' class='pageNum'>" + showPageNo + "</a> ";
	return H;

};
//这里开始分页查询订单数据
queryOrderSummary=function(data){
		//从浏览器地址获取flightClass参数
		var flightClass=getParamValue("flightClass");
		data.flightClass=flightClass;
		var startDate = $("input[name='startDate']").val(), 
			endDate = $("input[name='endDate']").val(), 
			contactMob = $("input[name='contactMob']").val(), 
			flightType = $("input[name='flightType']:checked").val(), 
			orderNo = $("input[name='orderNo']").val(), 
			passengerName = $("input[name='passengerName']").val(), 
			ticketNum = $("input[name='ticketNum']").val(),
			distributorCh = $("#distributorCh").find("option:selected").text(),
			shopNameCh = $("#shopNameCh").find("option:selected").text();
			carrier = $("input[name='carrier']").val(), 
			statement = $("input[name='statement']").val(), 
		data.startDate=startDate;
		data.endDate=endDate;
		data.contactMob=contactMob;
		data.flightType=flightType;
		data.orderNo=orderNo;
		data.passengerName=passengerName;
		data.ticketNum=ticketNum;
		data.distributorCh=distributorCh;
		data.shopNameCh=shopNameCh;
		data.carrier=carrier;
		data.statement=statement;
		data.orderStatus='1';  //1表示支付成功，等待出票的订单
		$.ajax({
			url:"${ctx }/order/gj-order-summary!queryOrderByPage.do?time="+new Date(),
			dataType:"json",
			data:data,
			type:"POST",
			success:function(result){
				if(result.error){
					alert(result.error);
				}else{
					$("#table-bd").html("");
					if(result.total>data.page*20){
						$("#totalNumber").text(20);
					}else{
						$("#totalNumber").text(result.total-(data.page-1)*20);
					}
					pageNav.go(data.page,Math.ceil(result.total/20));
					if(result.list){ //如果list这个属性存在，说明有数据返回
						tableDataList=result.list;
						$.each(result.list,function(i,item){
							var img_tag="";
							if(item.distributor=="Q"){  //去哪儿的图标
								img_tag="<img src='${ctx }/static/img/build/qunaer.png'/>";
							}else if(item.distributor=="C"){  //携程的图标
								img_tag="<img src='${ctx }/static/img/build/trip.png'/>";
							}else if(item.distributor=="T"){  //淘宝的图标
								img_tag="<img src='${ctx }/static/img/build/taob.png'/>";
							}else if(item.distributor=="N"){  //途牛的图标
								img_tag="<img src='${ctx }/static/img/build/tuniu.jpg'/>";
							}else if(item.distributor=="O"){  //同程的图标
								img_tag="<img src='${ctx }/static/img/build/tongc.png'/>";
							}
							var td_orderNo="";
							var temp_orderNo="";
							if(item.oldOrderNo!="0"&&item.oldOrderNo!=""){
								temp_orderNo=item.orderNo+"&nbsp;"+"<span style='color:red'>改</span>";
							}else {
								temp_orderNo=item.orderNo;
							}
							var temp_split="";
							if(item.isSplit==true){
								temp_split="<span style='color:blue'>(拆)</span>";
								
							}
							if(item.distributor=="C"){
								td_orderNo=img_tag+item.shopNameCh+temp_split+"<br/>"+item.billId+"<br/>"+temp_orderNo; //订单编号(携程的订单号加上主订单号)
							}else{
								td_orderNo=img_tag+item.shopNameCh+temp_split+"<br/>"+temp_orderNo; //订单编号
							}
							td_orderNo="<td>"+td_orderNo+"</td>";
<%--							if(item.isSplit==true){--%>
<%--								td_orderNo="<td ><table style='margin:0px auto'><tr><td>"+td_orderNo+"</td><td><img src='${ctx}/images/build/split.gif'/></td></tr></table></td>";--%>
<%--							}else{--%>
<%--								td_orderNo="<td>"+td_orderNo+"</td>";--%>
<%--							}--%>
<%--							var createTimes=item.createTime.split(" ");--%>
<%--							var td_createTime="<td>"+createTimes[0]+"<br/>"+createTimes[1]+"</td>"; //订单时间--%>
<%--							if(item.flightType=="S"){--%>
<%--								item.flightType="单程";--%>
<%--							}else if(item.flightType=="D"){--%>
<%--								item.flightType="往返";--%>
<%--							}--%>
							var isLowspace="";
							if(item.isLowspace==1){
								isLowspace="有低舱";
							}
							var seats=item.seats;
							var td_flightType="";
							var nowDate = new Date();
							var slfAddtime=item.slfAddtime;
							var paramDate = new Date(slfAddtime.replace(/-/,"/"));
							var temp_overtime=Math.floor((nowDate.valueOf()-paramDate.valueOf())/(1000*60*60));
							if(temp_overtime>1){
								td_flightType="<span style='background-color:#ff9797'>"+temp_overtime+"小时</span><br/>";   //超时时间（单位小时）"
							}else{
								td_flightType="<br/>";
							}
								
							if(seats>=0&&seats<=5){
								td_flightType+="<span style='color:red'><img src='${ctx}/images/build/urgent.gif'></span>"+"<br/>"+isLowspace;  //航程类型：单程、往返
							}else{
								td_flightType+="<br/>"+isLowspace;  //航程类型：单程、往返
							}
							td_flightType="<td>"+td_flightType+"</td>";
							var td_dep_arr="";
							if(item.flights){
								$.each(item.flights,function(j,flight){
										var each_departureTime=flight.departureTime;
										var each_depAircode=flight.depAircode;
										var each_arrivalTime=flight.arrivalTime;
										var each_arrAircode=flight.arrAircode;
										var each_dep_arr="<a href='javascript:void(0);' onclick='getAvhInfo(\""+item.id+"\",\""+flight.id+"\")'>"+each_departureTime+" "+each_depAircode+"<br/>"+each_arrivalTime+" "+each_arrAircode+"</a>";
										if(j<item.flights.length-1){
											each_dep_arr=each_dep_arr+"<hr/>";
										}
										td_dep_arr=td_dep_arr+each_dep_arr;
								});
							}
							td_dep_arr="<td>"+td_dep_arr+"</td>";
// 							var td_departureTime="<td>"+item.departureTime+"</td>"; //起飞时间00:00 格式
// 							var td_depAircode="<td>"+item.depAircode+"</td>"; //起飞城市三字码
// 							var td_arrivalTime="<td>"+item.arrivalTime+"</td>"; //到达时间00:00格式
// 							var td_arrAircode="<td>"+item.arrAircode+"</td>"; //到达城市三字码
							var td_flightDate_flightNo="";  //用来存储航班日期，航班号
							if(item.flights){
								$.each(item.flights,function(j,flight){
									var flightDate_flightNo="";
									if(compareFlightDate(flight.departureDate+" "+flight.departureTime)=="red"){
										flightDate_flightNo="<span style='background-color:red'>"+flight.departureDate+"</span><br/>"; //航班日期
									}else if(compareFlightDate(flight.departureDate+" "+flight.departureTime)=="yellow"){
										flightDate_flightNo="<span style='background-color:yellow'>"+flight.departureDate+"</span><br/>"; //航班日期
									}else{
										flightDate_flightNo=flight.departureDate+"<br/>"; //航班日期
									}
									flightDate_flightNo=flightDate_flightNo+flight.flightNum; //航班号+仓位
									if(flight.isShared=="Y"){
										flightDate_flightNo=flightDate_flightNo+"<span style='color:red'>共享</span>";
									}
									if(j<item.flights.length-1){
											flightDate_flightNo=flightDate_flightNo+"<hr/>";
									}
									td_flightDate_flightNo=td_flightDate_flightNo+flightDate_flightNo;
								});
							}
							td_flightDate_flightNo="<td>"+td_flightDate_flightNo+"</td>";
							
							
							var td_lowestPrice="";  //用来存低舱的
							if(item.flights){
								$.each(item.flights,function(j,flight){
									td_lowestPrice+="<span style='font-size:18px'>"+flight.cabin+":"+flight.cabinNum+"("+flight.printPrice+");</span>"; //仓位+舱位剩余数
									if(flight.lowspace!=undefined){
										var temp_lowspace=flight.lowspace;
										var temp_cabin=temp_lowspace.substring(0,temp_lowspace.length-1);
										var temp_cabinNum=temp_lowspace.substring(temp_lowspace.length-1,temp_lowspace.length);
										td_lowestPrice+="<span style='color:red;font-size:18px'>"+temp_cabin+":"+temp_cabinNum+"</span>";
									}
									if(j<item.flights.length-1){
										td_lowestPrice+="<hr/>";
									}
								});
							}
							var td_lowestPrice="<td>"+td_lowestPrice+"</td>";
							
							var isNewCode=item.isNewCode;
							var td_pnrCode="";
							if(isNewCode==1){
								td_pnrCode="<td>"+item.pnrCode+"<sup><img src='${ctx}/images/build/new.gif'></sup>"+"</td>"; //pnr
							}else{
								td_pnrCode="<td>"+item.pnrCode+"</td>"; //pnr
							}
							
							var td_allprice="<td>"+item.allprice+"/"; //订单总价
							var td_passengerCount=""+item.passengerCount+"<br/>"+convertSaleOrderEntityPnrType(item.pnrType)+"</td>"; //乘客总人数
							//var td_pnrNoTime="<td><input name=\"pnrNoTime\" size='10' type=\"text\" onclick=\"javascript:laydate()\" disabled='disabled' value=\""+item.pnrNoTime+"\"><button onclick='dealPnrNoTime(\""+item.id+"\",\""+item.pnrNoTime+"\",\""+item.leaveRemark+"\")'>修改</button><hr/>"; //pnr no位
							var var_detail='';
							var var_onclick="onclick='dealPnrNoTime(\""+item.id+"\",\""+item.pnrNoTime+"\",\""+item.leaveRemark+"\")'";
							if(compareDate(item.pnrNoTime)=='yellow'){
							var_detail='<div class="g-drift" style="background-color:yellow"><a href="javascript:;" class="" '+var_onclick+'>'+item.pnrNoTime+'</a><div class="g-tip g-tip-w tip-b-r"><div class="info"><p>pnrNoTime：'+item.pnrNoTime+'</p><p>备注信息：'+item.leaveRemark+'</p></div></div></div>';
							}else if(compareDate(item.pnrNoTime)=='red'){
							var_detail='<div class="g-drift" style="background-color:red"><a href="javascript:;" class="" style="color:white" '+var_onclick+'>'+item.pnrNoTime+'</a><div class="g-tip g-tip-w tip-b-r"><div class="info"><p>pnrNoTime：'+item.pnrNoTime+'</p><p>备注信息：'+item.leaveRemark+'</p></div></div></div>';
							}else {
							var_detail='<div class="g-drift"><a href="javascript:;" class="" '+var_onclick+'>'+item.pnrNoTime+'</a><div class="g-tip g-tip-w tip-b-r"><div class="info"><p>pnrNoTime：'+item.pnrNoTime+'</p><p>备注信息：'+item.leaveRemark+'</p></div></div></div>';
							}
							var td_pnrNoTime="<td>"+var_detail+"<br></td>";
<%--							var td_pnrNoTime="<td>"+var_detail+"<button onclick='dealPnrNoTime(\""+item.id+"\",\""+item.pnrNoTime+"\",\""+item.leaveRemark+"\")'>修改</button><br/>"; //pnr no位--%>
							var td_time="<td>";
							td_time+=item.createTime+"<br>";
							td_time+=item.slfAddtime+"<br>";
							if(item.deadline){
								td_time+="<span style='color:red'>"+item.deadline+"</span><br>";
							}
							td_time+="</td>";
						
<%--							var td_slfStatus=""+getSlfStatusCH(item.slfStatus)+"</td>"; //订单状态//这个字段暂时不确定--%>
							//var td_remark="<td>"+item.remark+"</td>"; //服务说明
							var temp_statement=item.statement;
							if(temp_statement.length>8){
								temp_statement=temp_statement.substring(0,8)+"*";
							}
							var td_statement='<div class="g-drift"><a href="javascript:;" class="">'+temp_statement+'</a><div class="g-tip g-tip-w tip-b-r"><div class="info"><p>'+item.statement+'</p></div></div></div>';
							var td_policyType=covertPolicyTypeToChinese(item.policyType);
							td_statement="<td>"+td_statement+"<br/>"+td_policyType+"</td>";
							var td_lockUser="<td>"+item.lockUser+"</td>"; //锁定人
							var var_tr=td_orderNo+td_pnrCode+td_allprice+td_passengerCount+td_dep_arr+td_flightDate_flightNo+td_lowestPrice+td_pnrNoTime+td_time+td_statement+td_flightType+td_lockUser;
							var var_a="<a href='${ctx }/order/gj-order-detail!showOrderDetail.do?id="+item.id+"&operate=orderList&flightClass="+flightClass+"'>查看</a>";
							if(item.isAuto!=1){
								var currentLockUser='<%=request.getSession().getAttribute("SESSION_KEY_CURRENT_USERNAME")%>';
									var_a=var_a+"<br/>"+"<a href='javascript:cancelOrder(\""+item.id+"\")'>取消</a>";
								if(item.lockUser==null||item.lockUser==""){//如果锁定人等于自己，这里一个处理，处理和强制处理调用同一个页面
									var_a=var_a+"<br/>"+"<a href='javascript:dealOrderById(\""+item.id+"\")'>处理</a>";
								}else if(item.lockUser==currentLockUser){
									var_a=var_a+"<br/>"+"<a href='${ctx }/order/gj-order-detail!queryOrderByID.do?id="+item.id+"'>处理</a>";
									var_a=var_a+"<br/>"+"<a href='javascript:unlockOrderForThirdInterface(\"确定要解除锁定\",\""+item.id+"\")'>解除锁定</a>";
								}else {
									//这里一个强制解锁的标签
									var_a=var_a+"<br/>"+"<a href='javascript:unlockOrderForThirdInterface(\"确定要强制解锁\",\""+item.id+"\")'>强制解锁</a>";
								}
							}
							var_tr="<tr>"+var_tr+"<td class='operate' >"+var_a+"</td></tr>";
							$("#table-bd").append(var_tr);
						});
						$(".table-bd tr:even").addClass("even");
						$(".table-bd tr:odd").addClass("odd");
					}
				}			
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("请求出错了");
			}
		});
};

function dealOrderById(id){
	$.ajax({
		type : "post",
		dataType : "json",
		data : {
			id : id,
			operatorType:1      //1表示认领第三方的订单
		},
		url : "${ctx }/order/gj-order-detail!updateLockUserForThirdInterface.do",
		success : function(result) {
			if (result.error) {
				if(confirm("锁定第三方订单出现如下错误："+result.error+",是否继续处理订单？")){
					location.href="${ctx }/order/gj-order-detail!queryOrderByID.do?id="+id;
				}
			} else if (result.success) {
				location.href="${ctx }/order/gj-order-detail!queryOrderByID.do?id="+id; //成功直接跳转页面
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求出错了");
		}
	});
	
}

//解除锁定和强制解锁
function cancelOrder(orderID) {
	if (confirm("取消订单号，该订单将无法再出票，请考虑清楚，是否要取消订单？")) {
		$.ajax({
			type : "post",
			dataType : "json",
			data : {
				id : orderID
			},
			url : "${ctx }/order/gj-order-detail!cancelOrderByID.do",
			success : function(result) {
				if (result.error) {
					alert(result.error);
				} else if (result.success) {
					var page = $("#page").text();
					var data = {
						"page" : page
					};
					queryOrderSummary(data);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("请求出错了");
			}
		});
	}
}

/**
 * message可以是强制解锁的信息，也可以是解除锁定的信息
 * id是订单的id
 */
function unlockOrderForThirdInterface(message, id){
	$.ajax({
		type : "post",
		dataType : "json",
		data : {
			id : id,
			operatorType:0     //0表示取消第三方的订单
		},
		url : "${ctx }/order/gj-order-detail!updateLockUserForThirdInterface.do",
		success : function(result) {
			if (result.error) {
				if(confirm(message+"出现如下错误："+result.error+",是否继续处理订单？")){
					unlockOrder(message, id);
				}
			} else if (result.success) {
				unlockOrder(message, id);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求出错了");
		}
	});
}

//解除锁定和强制解锁
function unlockOrder(message, id) {
	if (confirm(message)) {
		$.ajax({
			type : "post",
			dataType : "json",
			data : {
				id : id,
				lockUser : ''
			},
			url : "${ctx }/order/gj-order-detail!updateLockUser.do",
			success : function(result) {
				if (result.error) {
					alert(result.error + "失败");
				} else if (result.success) {
					var page = $("#page").text();
					var data = {
						"page" : page
					};
					queryOrderSummary(data);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("请求出错了");
			}
		});
	}
}
/**
*传一个订单的id，pnrNoTime,和留票备注过来。
*/
function dealPnrNoTime(id,pnrNoTime,leaveRemark){
	$("#light").css('display','block');  
	$("#fade").css('display','block');  
	$("#pnrNoTime").val(pnrNoTime);
	$("#leaveRemark").val(leaveRemark);
	$("#lightID").val(id);//这个是订单的ID
}
function light_ok(){
	var notime=$("#pnrNoTime").val();
	if(notime!=''&&notime.length>16){
		notime=notime.substring(0,16);
	}
	$.ajax({
		type : "post",
		dataType : "json",
		data : {
			id : $("#lightID").val(),
			pnrNoTime : notime,
			leaveRemark : $("#leaveRemark").val()
		},
		url : "${ctx }/order/gj-order-detail!updatePnrNoTime.do",
		success : function(result) {
			if (result.error) {
				alert(result.error + "失败");
			} else if (result.success) {
				var page = $("#page").text();
				var data = {
					"page" : page
				};
				queryOrderSummary(data);
				$("#light").css('display','none');  
				$("#fade").css('display','none');  
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求出错了");
		}
	});
}

function light_cancel(){
	$("#light").css('display','none');  
	$("#fade").css('display','none');  
}

function queryButton() {
	var page = $("#page").text();
	var data = {
		"page" : page
	};
	queryOrderSummary(data);
}

/**
*查看指定的时间字符串是否在未来24小时内
*/
function compareDate(dateString) {
	//yyyy-MM-dd HH:mm:ss
	if (dateString != undefined) {
		var nowDate = new Date();
		dateString=dateString.substring(0,19);
		var paramDate = new Date(dateString.replace(/-/,"/"));
		if((nowDate.valueOf() + 1 * 3 * 60 * 60 * 1000)<=paramDate.valueOf()&&paramDate.valueOf()<(nowDate.valueOf() + 1 * 24 * 60 * 60 * 1000)){
			return 'yellow';
		}else if(paramDate.valueOf()<(nowDate.valueOf() + 1 * 3 * 60 * 60 * 1000)){
			return 'red';
		}else {
			return 'default';
		}
	}
}

/**
*查看指定的时间字符串是否在未来24小时内
*/
function compareFlightDate(dateString) {
	//yyyy-MM-dd HH:mm:ss
	if (dateString != undefined) {
		var nowDate = new Date();
		dateString=dateString.substring(0,19);
		var paramDate = new Date(dateString.replace(/-/,"/"));
		if((nowDate.valueOf() )<=paramDate.valueOf()&&paramDate.valueOf()<(nowDate.valueOf() + 1 * 24 * 60 * 60 * 1000)){
			return 'red';
		}else if(paramDate.valueOf()<(nowDate.valueOf())){
			return 'yellow';
		}else {
			return 'default';
		}
	}
}

function addOptionForDistributor(){
	var flightClass=getParamValue("flightClass");
	$.ajax({
		async: false, //这个不能异步处理，必须页面加载完成前出来
		type : "post",
		dataType : "json",
		data:{"flightClass":flightClass,"orderStatus":"1"},
		url : "${ctx }/order/gj-order-summary!queryDistributors.do",
		success : function(result) {
			if (result.error) {
				alert(result.error);
			} else if (result.success) {
				$("#distributorCh").html("");
				$("#distributorCh").append("<option></option>");
				var distributorCh="<%=request.getSession().getAttribute("distributorCh")%>";
				$.each(result.list,function(i,item){
					if(distributorCh==item.distributorCh){
						$("#distributorCh").append("<option  value='"+item.distributor+"' selected='selected'>"+item.distributorCh+"</option>");
					}else{
						$("#distributorCh").append("<option  value='"+item.distributor+"'>"+item.distributorCh+"</option>");
					}
				}); 
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求出错了");
		}
	});
}
function addOptionForShopname(){
	var flightClass=getParamValue("flightClass");
	$.ajax({
		async: false, //这个不能异步处理，必须页面加载完成前出来
		type : "post",
		dataType : "json",
		data:{"flightClass":flightClass,"orderStatus":"1"},
		url : "${ctx }/order/gj-order-summary!queryShopnames.do",
		success : function(result) {
			if (result.error) {
				alert(result.error);
			} else if (result.success) {
				$("#shopNameCh").html("");
				$("#shopNameCh").append("<option></option>");
				var shopNameCh="<%=request.getSession().getAttribute("shopNameCh")%>";
				$.each(result.list,function(i,item){
					if(shopNameCh==item.shopNameCh){
						$("#shopNameCh").append("<option  value='"+item.shopName+"' selected='selected'>"+item.shopNameCh+"</option>");
					}else{
						$("#shopNameCh").append("<option  value='"+item.shopName+"'>"+item.shopNameCh+"</option>");
					}
				}); 
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求出错了");
		}
	});
}

$(document).ready(function(){

	$("#J_Btn").click(function() {
		var page = "1"; //查询还是要从第一页开始
		var data = {
			"page" : page
		};
		queryOrderSummary(data);
	});
	
	var error = "${error}";
	if(error) {
		alert(error);
	}

	function formatMD(d){
		if(d < 10){
			return "0"+d;
		}else{
			return d;
		}
	}
	// 格式化日期, d为默认日期, next为相差天数
	function formatDate(d, next) {
		if (d) {
			if (next) {
				d = new Date(d.getTime() + next * 24 * 60 * 60 * 1000);
			}
			return d.getFullYear() + "-" + formatMD(d.getMonth() + 1) + "-"+ formatMD(d.getDate());
		}
	}

	// 初始化日期
	window.SERVICE_TIME = new Date();
	var defaultTime = window.SERVICE_TIME;
	curDate = formatDate(defaultTime, 0);
	nextDate = formatDate(defaultTime, -30);

	$("input[name='startDate']").val(nextDate);
	$("input[name='endDate']").val(curDate);
	
	// 出发时间，到达时间控件
	var startDate = {
		elem: '#startDate',
		format: 'YYYY-MM-DD',
		istoday: true,
		istime: false,
		isclear: false,
		choose: function(datas){
			endDate.min = datas;
			endDate.start = datas;
		}
	};
	var endDate = {
		elem: '#endDate',
		format: 'YYYY-MM-DD',
		min: laydate.now(-30),
		istoday: true,
		istime: false,
		isclear: false,
		choose: function(datas){
			startDate.max = datas;
		}
	};
	laydate(startDate);
	laydate(endDate);

	//这个顺序很重要啊
	addOptionForDistributor();
	addOptionForShopname();
	var carrier="<%=request.getSession().getAttribute("carrier")%>";
	if(carrier!="null"){
		$("input[name='carrier']").val(carrier); //页面进入后设置初识值,初始值从session里面获取
	}
	var statement="<%=request.getSession().getAttribute("statement")%>";
	if(statement!="null"){
		$("input[name='statement']").val(statement); //页面进入后设置初识值,初始值从session里面获取
	}
	queryOrderSummary({
		"page" : "1"
	});
});

</script>
</head>
<body style="margin: 10px; min-width: 920px;">
	<div class="mod-hd" >
		<script type="text/javascript">
			if(getParamValue("flightClass")=="I"){
				document.write("<h2>国际待出票订单</h2>");			
			}else{
				document.write("<h2>国内待出票订单</h2>");
			}
		</script>
	</div>
	<form id="J_Form" action="">
		<table class="m-search g-clear g-table" style="width: 100%">
			<tr class="rows">
				<td class="col">
					<span class="label">下单日期</span>
					<div class="label-info">
						<div class="qcbox start-date">
							<input id="startDate" name="startDate" type="text" class="cinput">
						</div>
						<span class="split">到</span>
						<div class="qcbox stop-date">
							<input id="endDate" name="endDate" type="text" class="cinput">
						</div>
					</div>
				</td>
				<td class="col">
					<span class="label">联系人手机</span>
					<div class="label-info">
						<span class="g-ipt-text"> <input name="contactMob" type="text" value=""> </span>
					</div>
				</td>
				<td class="col">
					<span class="label">航程类型</span>
					<div class="label-info">
						<label> <input name="flightType" value="0" type="radio" checked="checked" class="g-input-check">全部</label> 
						<label> <input name="flightType" value="1" type="radio" class="g-input-check">单程</label> 
						<label> <input name="flightType" value="2" type="radio" class="g-input-check">往返</label>
						<label> <input name="flightType" value="3" type="radio" class="g-input-check">多程</label>
					</div>
				</td>
			</tr>
			<tr class="rows">
				<td class="col">
					<span class="label">订单号</span>
					<div class="label-info">
						<span class="g-ipt-text"> <input name="orderNo" type="text"> </span>
					</div>
				</td>
				<td class="col">
					<span class="label">乘机人姓名</span>
					<div class="label-info">
						<span class="g-ipt-text"> <input name="passengerName" type="text" value=""> </span>
					</div>
				</td>
				<td class="col" colspan="2">
					<span class="label">票号</span>
					<div class="label-info">
						<span class="g-ipt-text"> <input name="ticketNum" type="text"> </span>
					</div>
				</td>
			</tr>
			<tr class="rows">
				<td class="col" >
					<span class="label">订单来源</span>
					<div class="label-info">
						<span class="g-select"><select name="distributorCh" id="distributorCh"></select></span>
					</div>
				</td>
				<td class="col" >
					<span class="label">店铺名称</span>
					<div class="label-info">
						<span class="g-select"> <select name="shopNameCh" id="shopNameCh"></span>
					</div>
				</td>
				<td class="col" >
					<span class="label">航司</span>
					<div class="label-info">
						<span class="g-ipt-text"> <input name="carrier" type="text"></span>
					</div>
				</td>
<!-- 				<td class="col" > -->
<!-- 					<span class="label">全部店铺</span> -->
<!-- 					<div class="label-info" > -->
<!-- 						<label><input name="allShopNameCh" type="checkbox"  value="全部店铺" class="g-checkbox"> </label>  -->
<!-- 					</div> -->
<!-- 				</td> -->
			</tr>
				<tr class="rows">
				<td class="col" >
					<span class="label">服务说明</span>
					<div class="label-info">
						<span class="g-ipt-text"><input name="statement" type="text"></span>
					</div>
				</td>
			</tr>
			<tr class="rows">
				<td class="col">
					<span class="label">&nbsp;</span>
					<div class="label-info">
						<input type="button" id="J_Btn" class="g-btn" value="查询" />
					</div>
				</td>
			</tr>
		</table>
	</form>
	
	<div class="g-content">
		<table class="g-table table-list">
			<thead class="table-hd ">
				<tr>
					<th>订单号</th>
<%--					<th class="col08">订单日期</th>--%>
					<th>编码</th>
					<th>成交价/人数<br/>乘客类型</th>
					<th>起飞-到达</th>
					<th>航班日期(低舱)<br>航班号(舱位)</th>
					<th>最低票面价</th>
					<th>ADTK</th>
					<th>下单/导单/最晚出票</th>
					<th>服务说明<br/>政策类型</th>
					<th>超时时间<br>座位紧急度</th>
					<th>锁定人</th>
					<th>操作</th>
				</tr>
			</thead> 
			<tbody class="table-bd" id="table-bd">
			</tbody>
		</table>
	</div>
	<div id="avhQuery" class="white_content" style="width: 530px;height: 320px"> 
		<div>
			<textarea id="avhInfo"  cols="100" rows="15" style="background-color: black;color: green;"></textarea>		
		</div>
		<br>
		<div >
			<button  id="avhCancel" class="g-btn" onclick="closeAvhDialog()">关闭</button>
		</div>
	</div>
	<div id="light" class="white_content"> 
		<div>
		    <input id="lightID" name="lightID" type="hidden" >
			<span class="label">pnrNoTime</span><input id="pnrNoTime" name="pnrNoTime" type="text" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
		</div>
		<br>
		<div >
			<span>留&nbsp;票&nbsp;备&nbsp;注&nbsp;</span><input id="leaveRemark" name="leaveRemark" type="text">
		</div>
		<br>
		<div >
			<button id="lightOk" class="g-btn" onclick="light_ok()">确定</button>&nbsp;&nbsp;<button  id="lightCancel" class="g-btn" onclick="light_cancel()">取消</button>
		</div>
	</div>
	<div id="fade" class="black_overlay">
	</div>
	<div style="float:left">
		<span>当前页面总条数：</span>
		<span id="totalNumber"></span>
	</div>
	<div id="pageNav" >
	</div>
</body>
</html>