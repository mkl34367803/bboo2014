<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%> 
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
Date datetime = new Date();  %>
<%@include file="/jsp/share/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>航司运价</title>
<%@include file="/jsp/share/JS.jsp"%>
<%@include file="/jsp/share/CSS.jsp"%>
<script src="${ctx }/static/js/build/price/airPrice.js" type="text/javascript"></script>
<style type="text/css">
.white_content {
	top: 10%;
	left: 38%;
	width: 300px;
	height: auto;
	float:left;
}
.light-label {
	float: left;
	width: 55px;
	padding-right: 10px;
	text-align: right;
	line-height: 28px
}
.light-label-info {
	padding-left: 60px
}
#div_box{
   top: 5%;
	left: 36%;
	width: 300px;
	height: auto;
}

.qcbox {
	width: 95px;
	float: left;
	border: 1px solid #e4e4e4;
    background-color: #fff;
    color: #333;
    height: 26px;
    position: relative;
    display: block;
}
.split {
    float: left;
    padding: 0 2px;
    line-height: 28px;
}
</style>
<script type="text/javascript">
	//显示非当前页
	pageNav.pHtml = function(pageNo, pn, showPageNo) {
		showPageNo = showPageNo || pageNo;
		var H = " <a href='javascript:getDatas(" + pageNo
				+ ");' class='pageNum'>" + showPageNo + "</a> ";
		return H;

	};
	var ctx = "${ctx}";
</script>
</head>
<body style="margin: 10px; min-width: 920px;">
	<div>
		<div class="mod-hd" style="margin-top: 10px">
			<h2>航班运价</h2>
		</div>
		<form action="">
			<table class="m-search g-clear g-table">
				<tr class="rows">
					<td class="col">
						<span class="label">航司</span>
						<div class="label-info">
							<span class="g-ipt-text"><input type="text" name="searchCarrier" id="carrier" /></span>
						</div>
					</td>
					<td class="col">
						<span class="label">始发地</span>
						<div class="label-info">
							<span class="g-ipt-text"><input type="text" name="searchDepCode" id="depCode" /></span>
						</div>
					</td>
					<td class="col">
						<span class="label">目的地</span>
						<div class="label-info">
							<span class="g-ipt-text"><input type="text" name="searchArrCode" id="arrCode" /></span>
						</div>
					</td>
					<td class="col">
						<span class="label">舱位</span>
						<div class="label-info">
							<span class="g-ipt-text"><input type="text" name="searchCabin" id="cabin" /></span>
						</div>
					</td>
				</tr>
				<tr class="rows">
					<td class="col" colspan="3">
						<span class="label">&nbsp;</span>
						<div class="label-info">
							<input type="button" id="searchButton" class="g-btn" value="查询" />
							<input type="button" id="addButton" class="g-btn" value="添加" />
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="datagrid" class="g-content">
		<table class="g-table table-list">
			<thead class="table-hd ">
				<tr>
					<th class="col08">航司</th>
					<th class="col02">始发地</th>
					<th class="col02">目的地</th>
					<th class="col05">基础舱位</th>
					<th class="col05">舱位</th>
					<th class="col02">折扣</th>
					<th class="col02">基础价</th>
					<th class="col05">销售价</th>
					<th class="col05">状态</th>
					<th class="col08">有效期开始</th>
					<th class="col08">有效期结束</th>
					<th class="col05">操作</th>
				</tr>
			</thead>
			<tbody class="table-bd content-td"></tbody>
		</table>
	</div>
	<div id="light" class="white_content">
		<div style="display: none">
			<span>id</span><input type="text" name="id" id="id" />
		</div>
		<span class="light-label" >航&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司</span>
		<div class="light-label-info" >
			<span class="g-ipt-text" > 
			<input type="text" name="carrier"title="航司信息不能为空" size="5" maxlength="5" onblur="if(this.value.replace(/^ +| +$/g,'')=='')alert('请填写航司信息!')" onkeyup="value=value.replace(/[\W]/g,'')"/> 
			</span>
		</div>
		<span class="light-label" style="margin-top:5px;">有&nbsp;&nbsp;效&nbsp;&nbsp;期</span>
		<div class="light-label-info"style="margin-top:5px;">
			<div class="qcbox" >
				<input name="beginDate" id="beginDate" type="text" class="cinput"   style="width:90px;height:25px;" >
			</div>
			<span class="split">至</span>
			<div class="qcbox" >
				<input name="endDate" id="endDate" type="text" class="cinput"  style="width:90px;height:25px;" >
			</div>
		</div>
		<span class="light-label" style="margin-top:5px;">飞行区间</span>
		<div class="light-label-info" style="margin-top:5px;">
			<div class="qcbox" style="margin-top:5px;">
				<input name="depCode"    type="text" class="cinput"  style="width:90px;height:23px;"title="始发地信息不能为空" size="5" maxlength="5" onkeyup="value=value.replace(/[^a-zA-Z]/g,'')" >
			</div>
			<span class="split" style="margin-top:5px;">至</span>
			<div class="qcbox" style="margin-top:5px;">
				<input name="arrCode"  type="text" class="cinput" style="width:90px;height:23px;"title="目的地信息不能为空" size="5" maxlength="5" onkeyup="value=value.replace(/[^a-zA-Z]/g,'')" >
			</div>
		</div>
		<span class="light-label"style="margin-top:5px;">提前天数</span>
		<div class="light-label-info" style="margin-top:5px;">
			<span class="g-ipt-text" style="margin-top:5px;"> <input type="text" name="predaysStart" size="2" maxlength="2"onKeyUp="value=value.replace(/\D/g,'')" onafterpaste="value=value.replace(/\D/g,'')"/></span>
		</div>
		<span class="light-label"style="margin-top:5px;">提前结束</span>
		<div class="light-label-info"style="margin-top:5px;">
			<span class="g-ipt-text"> <input type="text" name="predaysEnd" size="3" maxlength="3"onKeyUp="value=value.replace(/\D/g,'')" onafterpaste="value=value.replace(/\D/g,'')"/></span>
		</div>
		<span class="light-label"style="margin-top:5px;" >基础舱位</span>
		<div class="light-label-info"style="margin-top:5px;">
			<span class="g-ipt-text"> <input type="text" name="baseCabin" size="5" maxlength="5" onblur="if(this.value.replace(/^ +| +$/g,'')=='')" onkeyup="value=value.replace(/[^a-zA-Z]/g,'')" /></span>
		</div>
		<span class="light-label"style="margin-top:5px;">舱&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位</span>
		<div class="light-label-info"style="margin-top:5px;">
			<span class="g-ipt-text"> <input type="text" name="cabin"title="舱位信息不能为空" size="5" maxlength="5" onblur="if(this.value.replace(/^ +| +$/g,'')=='')alert('请填写舱位信息!')" onkeyup="value=value.replace(/[^a-zA-Z]/g,'')" /></span>
		</div>
		<span class="light-label"style="margin-top:5px;">折&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;扣</span>
		<div class="light-label-info"style="margin-top:5px;">
			<span class="g-ipt-text"> <input type="text" name="discount"size="4" maxlength="4" onkeyup="value=value.replace(/[^\d\.]/g,'')" /></span>
		</div>
		<span class="light-label"style="margin-top:5px;">基&nbsp;&nbsp;础&nbsp;&nbsp;价</span>
		<div class="light-label-info"style="margin-top:5px;">
			<span class="g-ipt-text"> <input type="text" name="basePrice" size="7" maxlength="7" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></span>
		</div>
		<span class="light-label"style="margin-top:5px;">销&nbsp;&nbsp;售&nbsp;&nbsp;价</span>
		<div class="light-label-info"style="margin-top:5px;">
			<span class="g-ipt-text"> <input type="text" name="price" size="7" maxlength="7" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></span>
		</div>
		<span class="light-label"style="margin-top:5px;display: none">keys</span>
		<div class="light-label-info"style="margin-top:5px;display: none">
			<span class="g-ipt-text"> <input type="text" name="keys" value="<%=sdf.format(datetime)%>"/></span>
		</div>
		<span class="light-label"style="margin-top:5px;display: none">创建时间</span>
		<div class="light-label-info"style="margin-top:5px;display: none">
			<span class="g-ipt-text"> <input type="text" name="createTime" value="<%=sdf.format(datetime)%>"/></span>
		</div>
		<span class="light-label" style="line-height:16px;margin-top:10px;">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态</span>
		<div class="light-label-info" style="margin-top:10px;">
			<label> <input name="isDelete" value="0" type="radio" checked="checked" style="margin-top:2px;" >开启</label> 
			<label> <input name="isDelete" value="1" type="radio" style="margin-top:2px;margin-left:20px;" >关闭</label> 
		</div>
		<span class="light-label">&nbsp;</span>
		<div class="light-label-info"  style="margin-top:10px;">
			<input type="button" id="lightOk" class="g-btn" value="确定" />
			<input type="button" id="lightCancel" class="g-btn" value="取消" />
		</div>
		</div>
	<!-- 详情 -->
	<div id="div_box" class="white_content" style="float:left;display:none; width:730px;margin-left:-200px;margin-top:60px; " >
		<div style="display: none">
			<span>id</span><input type="text" name="id" id="id" />
		</div>
	<table id="tab" class="g-table table-list" style="min-width: 730px;">
		<thead class="table-hd ">
			<tr>
				<th class="pcol10">航司</th>
				<th class="pcol10">提前开始天数</th>
				<th class="pcol10">提前结束天数</th>
				<th class="pcol10">始发地</th>
				<th class="pcol10">目的地</th>
				<th class="pcol10">基础舱位</th>
			</tr>
		</thead>
		<tbody class="table-bd" style="text-align:center;">
			<tr>
				<td class="carrier"></td>
				<td class="predaysStart"></td>
				<td class="predaysEnd"></td>
				<td class="depCode"></td>
				<td class="arrCode"></td>
				<td class="baseCabin"></td>
			</tr>
		</tbody>
		<thead class="table-hd ">
			<tr>
			    <th>舱位</th>
				<th>折扣</th>
				<th>基础价</th>
				<th>销售价</th>
				<th>状态</th>
				<th>有效期开始</th>
			</tr>
		</thead>
		<tbody class="table-bd">
			<tr>
			    <td class="cabin"></td>
				<td class="discount"></td>
				<td class="basePrice"></td>
				<td class="price"></td>
				<td class="isDeletes-td"></td>
				<td class="beginDate"></td>
			</tr>
		</tbody>
		<thead class="table-hd ">
			<tr>
			    <th>有效期结束</th>
				<th colspan="2">创建时间</th>
				<th>keyID</th>
				<th>renderID</th>
				<th>travleType</th>
			</tr>
		</thead>
		<tbody class="table-bd">
			<tr>
			    <td class="endDate"></td>
				<td class="createTime" colspan="2"></td>
				<td class="keyID"></td>
				<td class="renderID"></td>
				<td class="travleType"></td>
			</tr>
			</tbody>
			<thead class="table-hd ">
			<tr>
			    <th>weekLimit</th>
			    <th>priceType</th>
				<th>stopTime</th>
			    <th>keys</th>
			    <th colspan="2">id</th>
			</tr>
		</thead>
		<tbody class="table-bd">
			<tr>
			    <td class="weekLimit"></td>
			    <td class="priceType"></td>
				<td class="stopTime"></td>
				<td class="keys" ></td>
			    <td class="id" colspan="2"></td>
			</tr>
		</tbody>
		<thead class="table-hd ">
			<tr>
			    <th colspan="2">a</th>
				<th colspan="2">n</th>
				<th colspan="2">s</th>
			</tr>
		</thead>
		<tbody class="table-bd">
			<tr>
			    <td class="a" colspan="2"></td>
				<td class="n" colspan="2"></td>
				<td class="s" colspan="2"></td>
			</tr>
		    <tr>
			    <td colspan="6"><button id="tableCancels" class="g-btn" style="color:white">返回</button></td>
		    </tr>
		</tbody>
	</table>
		</div> 
	<div id="fade" class="black_overlay"></div>
	<div id="pageNav"></div>
</body>
</html>