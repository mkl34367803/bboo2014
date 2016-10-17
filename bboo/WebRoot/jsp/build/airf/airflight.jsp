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
<title>航班信息</title>
<%@include file="/jsp/share/JS.jsp"%>
<%@include file="/jsp/share/CSS.jsp"%>
<script src="${ctx }/static/js/build/flight/airFlight.js" type="text/javascript"></script>
<style type="text/css">
.white_content {
	top: 13%;
	left: 37%;
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
	left: 39%;
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
			<h2>航班信息</h2>
		</div>
		<form action="">
			<table class="m-search g-clear g-table">
				<tr class="rows">
					<td class="col">
						<span class="label">航司</span>
						<div class="label-info">
							<span class="g-ipt-text"><input type="text" name="searchAir" id="air" /></span>
						</div>
					</td>
					<td class="col">
						<span class="label">始发地</span>
						<div class="label-info">
							<span class="g-ipt-text"><input type="text" name="searchDep" id="dep" /></span>
						</div>
					</td>
					<td class="col">
						<span class="label">目的地</span>
						<div class="label-info">
							<span class="g-ipt-text"><input type="text" name="searchArr" id="arr" /></span>
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
				    <th class="col05">航班号</th>
				    <th class="col05">共享航班号</th>
				    <th class="col05">舱位</th>
				    <th class="col02">始发地</th>
					<th class="col02">目的地</th>
					<th class="col02">起飞时间</th>
					<th class="col02">抵达时间</th>
					<th class="col05">国内/国际</th>
					<th class="col08">是否可用</th>
					<th class="col08">操作</th>
				</tr>
			</thead>
			<tbody class="table-bd content-td"></tbody>
		</table>
	</div>
	
	<div id="light" class="white_content">
		<div style="display: none">
			<span>id</span><input type="text" name="id" id="id" />
		</div>
		<span class="light-label" style="margin-top:5px;">航&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司</span>
		<div class="light-label-info" style="margin-top:5px;">
			<span class="g-ipt-text"> <input type="text" name="air" size="5" maxlength="5" onkeyup="value=value.replace(/[\W]/g,'')"/> </span>
		</div>
		<span class="light-label"style="margin-top:5px;">航&nbsp;&nbsp;班&nbsp;&nbsp;号</span>
		<div class="light-label-info"style="margin-top:5px;">
			<span class="g-ipt-text"> <input type="text" name="fno"  size="5" maxlength="5" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"/> </span>
		</div>
		<span class="light-label"style="margin-top:5px;">共享航班</span>
		<div class="light-label-info"style="margin-top:5px;">
			<span class="g-ipt-text"> <input type="text" name="sno" size="10" maxlength="10" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"/> </span>
		</div>
		<span class="light-label"style="margin-top:5px;">舱&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位</span>
		<div class="light-label-info"style="margin-top:5px;">
			<span class="g-ipt-text"> <input type="text" name="cabins" size="100" maxlength="100" onkeyup="value=value.replace(/[^a-zA-Z]/g,'')"/> </span>
		</div>
		<span class="light-label"style="margin-top:5px;">飞行区间</span>
		<div class="light-label-info">
			<div class="qcbox" style="margin-top:5px;">
				<input name="dep"  type="text" class="cinput" size="5" maxlength="5"  style="width:90px;height:25px;" onkeyup="value=value.replace(/[^a-zA-Z]/g,'')">
			</div>
			<span class="split" style="margin-top:5px;">至</span>
			<div class="qcbox" style="margin-top:5px;">
				<input name="arr"  type="text" class="cinput" size="5" maxlength="5" style="width:90px;height:25px;" onkeyup="value=value.replace(/[^a-zA-Z]/g,'')">
			</div>
		</div>
		<span class="light-label" style="margin-top:5px;">飞行时间</span>
		<div class="light-label-info">
			<div class="qcbox" style="margin-top:5px;">
				<input name="dept" id="startDate"  type="text" class="cinput" size="10" maxlength="10"  style="width:90px;height:23px;" onkeyup="value=value.replace(/[^\w\.\:\'\/]/ig,'')" >
			</div>
			<span class="split" style="margin-top:5px;">至</span>
			<div class="qcbox" style="margin-top:5px;">
				<input name="arrt" id="endDate"  type="text" class="cinput" size="10" maxlength="10"  style="width:90px;height:23px;"onkeyup="value=value.replace(/[^\w\.\:\'\/]/ig,'')" >
			</div>
		</div>
		<span class="light-label"style="margin-top:5px;">indexkey</span>
		<div class="light-label-info"style="margin-top:5px;">
			<span class="g-ipt-text" style="margin-top:5px;"> <input type="text" name="indexkey" size="25" maxlength="25" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"/> </span>
		</div>
		<span class="light-label"style="margin-top:5px;display:none">创建时间</span>
		<div class="light-label-info" style="margin-top:5px;display:none">
			<span class="g-ipt-text"> <input type="text" name="ct"  value=""/> </span>
		</div>
		<span class="light-label"style="line-height:16px;margin-top:10px;">国内/国际</span>
		<div class="light-label-info" style="margin-top:10px;">
			<label> <input name="inat" value="1" type="radio" checked="checked" style="margin-top:3px;" >国内</label> 
			<label> <input name="inat" value="2" type="radio"checked="checked" style="margin-top:3px;margin-left:20px;" >国际</label> 
		</div>
		<span class="light-label"style="line-height:16px;margin-top:10px;">是否经停</span>
		<div class="light-label-info"style="margin-top:10px;">
			<label> <input  type="radio" name="istop" value="1" checked="checked" style="margin-top:3px;"/>是</label>
			<label> <input  type="radio" name="istop" value="2" checked="checked"style="margin-top:3px;margin-left:32px;"/>否</label>
		</div>
		<span class="light-label" style="line-height:16px;margin-top:10px;">是否可用</span>
		<div class="light-label-info" style="margin-top:10px;">
			<label><input name="isu" value="1" type="radio" checked="checked"style="margin-top:3px;">可用</label> 
			<label><input name="isu" value="2" type="radio" checked="checked"style="margin-top:3px;margin-left:20px;">禁用</label> 
			<label><input name="isu" value="3" type="radio" checked="checked"style="margin-top:3px;margin-left:20px;">审核</label> 
		</div>
		<span class="light-label">&nbsp;</span>
		<div class="light-label-info"  style="margin-top:10px;">
			<input type="button" id="lightOk" class="g-btn" value="确定" />
			<input type="button" id="lightCancel" class="g-btn" value="取消" />
		</div>
		</div>
	<!-- 详情 -->
	<div id="div_box" class="white_content" style="float:left;display:none; width:600px;margin-left:-175px;margin-top:100px; " >
		<div style="display: none">
			<span>id</span><input type="text" name="id" id="id" />
		</div>
	<table id="tab" class="g-table table-list" style="min-width:600px;">
		<thead class="table-hd ">
			<tr>
			    <th class="pcol10">航司</th>
				<th class="pcol10">航班号</th>
				<th class="pcol10">舱位</th>
				<th class="pcol10">始发地</th>
				<th class="pcol10">目的地</th>
			</tr>
		</thead>
		<tbody class="table-bd" style="text-align:center;">
			<tr>
			    <td class="air"></td>
				<td class="fno"></td>
				<td class="cabins"></td>
				<td class="dep"></td>
				<td class="arr"></td>
			</tr>
		</tbody>
		<thead class="table-hd ">
			<tr>
			    <th>共享航班号</th>
			    <th>国内/国际</th>
			    <th>是否经停</th>
				<th>是否可用</th>
				<th>起飞时间</th>
			</tr>
		</thead>
		<tbody class="table-bd">
			<tr>
				<td class="sno" ></td>
				<td class="inat"></td>
				<td class="istop"></td>
				<td class="isu"></td>
				<td class="dept"></td>
			</tr>
		</tbody>
		<thead class="table-hd ">
			<tr>
			    <th >到达时间</th>
			    <th colspan="2">indexKey</th>
			    <th colspan="2">创建时间</th>
			</tr>
		</thead>
		<tbody class="table-bd">
			<tr>
				<td class="arrt"></td>
				<td class="indexkey" colspan="2"></td>
				<td class="ct" colspan="2"></td>
			</tr>
		    <tr>
			    <td colspan="5"><button id="tableCancels" class="g-btn" style="color:white">返回</button></td>
		    </tr>
		</tbody>
	</table>
		</div>
	<div id="fade" class="black_overlay"></div>
	<div id="pageNav"></div>
</body>
</html>