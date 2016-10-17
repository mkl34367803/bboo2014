<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/share/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/jsp/share/JS.jsp"%>
<%@include file="/jsp/share/CSS.jsp"%>
<script src="${ctx }/static/js/build/account/accountManage.js" type="text/javascript"></script>
<style type="text/css">

.white_content {
	top: 15%;
	left: 36%;
	width: 300px;
	height: auto;
}

.light-label {
	float: left;
	width: 55px;
	padding-right: 10px;
	text-align: right;
	line-height: 28px
}

.light-label-info {
	padding-left: 65px
}

.lable-row {
	margin-bottom: 10px;
}
.roleSelect {
	position: absolute;
	background-color: #fff;
	width: 198px;
	height: auto;
	z-index: 70;
	display: none;
	padding: 5px 5px 5px 5px;
	border: solid 1px #e4e4e4;
	max-height: 400px;
}
.roleSelect ul li {
	margin-bottom: 5px;
}
</style>
<script type="text/javascript">
	//显示非当前页
	pageNav.pHtml = function(pageNo, pn, showPageNo) {
		showPageNo = showPageNo || pageNo;
		var H = " <a href='javascript:getDates(" + pageNo
				+ ");' class='pageNum'>" + showPageNo + "</a> ";
		return H;
	
	};
	
	var ctx = "${ctx}";
	
</script>
</head>
<body style="margin: 10px; min-width: 920px;">
	<div>
		<div class="mod-hd" style="margin-top: 10px">
			<h2 id="title">账户管理</h2>
		</div>
		<form action="">
			<table class="m-search g-clear g-table">
				<tr class="rows">
					<td class="col">
						<span class="label">航司</span>
						<div class="label-info">
							<span class="g-ipt-text"> <input type="text" name="searchAircode" id="searchAircode" maxlength="2" /> </span>
						</div>
					</td>
					<td class="col">
						<span class="label">账户</span>
						<div class="label-info">
							<span class="g-ipt-text"> <input type="text" name="searchAccount" id="searchAccount" /> </span>
						</div>
					</td>
					<td class="col">
						<span class="label">账户类型</span>
						<div class="label-info">
							<label> <input name="searchAccountType" value="" type="radio" class="g-input-check" checked="checked">全部</label> 
							<label> <input name="searchAccountType" value="C" type="radio" class="g-input-check">采购</label> 
							<label> <input name="searchAccountType" value="Z" type="radio" class="g-input-check">支付</label>
						</div>
					</td>
				</tr>
				<tr class="rows">
					<td class="col">
						<span class="label">可用</span>
						<div class="label-info">
							<label> <input name="searchIsu" value="" type="radio" class="g-input-check" checked="checked">全部</label> 
							<label> <input name="searchIsu" value="1" type="radio" class="g-input-check">可用</label> 
							<label> <input name="searchIsu" value="2" type="radio" class="g-input-check">禁用</label>
						</div>
					</td>
				</tr>
				<tr class="rows">
					<td class="col">
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
					<th class="pcol10">账户</th>
					<th class="pcol10">账户类型</th>
					<th class="pcol10">账户描述</th>
					<th class="pcol10">航司</th>
					<th class="pcol10">可用</th>
					<th class="pcol15">系统时间</th>
					<th class="pcol15">操作</th>
				</tr>
			</thead>
			<tbody class="table-bd"></tbody>
		</table>
	</div>
	
	<div id="light" class="white_content">
		
		<div style="display: none">
			<span>id</span><input type="text" name="id" id="id" />
		</div>
		
		<div class="lable-row">
			<span class="light-label">航司</span>
			<div class="light-label-info">
				<span class="g-ipt-text"> <input type="text" name="aircode" id="aircode" maxlength="2" /> </span>
			</div>
		</div>
		
		<div class="lable-row">
			<span class="light-label">账户类型</span>
			<div class="light-label-info">
				<label> <input name="accountType" value="C" type="radio" class="g-input-check" checked="checked">采购</label> 
				<label> <input name="accountType" value="Z" type="radio" class="g-input-check">支付</label>
			</div>
		</div>
		
		<div class="lable-row">
			<span class="light-label">账户描述</span>
			<div class="light-label-info">
				<!-- <span class="g-ipt-text"> <input type="text" name="val" id="val" /> </span> -->
				<span class="g-select purchase-select" style="width: 208px">
					<oms:select showEmpty="true" id="purchasePlace" name="purchasePlace" style="width: 100%">
						<oms:options></oms:options>
					</oms:select>
				</span>
				<span class="g-select pay-select" style="width: 208px">
					<oms:select showEmpty="true" id="payWay" name="payWay" style="width: 100%">
						<oms:options></oms:options>
					</oms:select>
				</span>
			</div>
		</div>
		
		<div class="lable-row">
			<span class="light-label">账户</span>
			<div class="light-label-info">
				<span class="g-ipt-text"> <input type="text" name="account" id="account" /> </span>
			</div>
		</div>
		
		<div class="lable-row pass">
			<span class="light-label">密码</span>
			<div class="light-label-info">
				<span class="g-ipt-text"> <input type="text" name="pass" id="pass" /> </span>
			</div>
		</div>
		
		<div class="lable-row">
			<span class="light-label" style="line-height:12px">是否可用</span>
			<div class="light-label-info">
				<label> <input name="isu" value="1" type="radio" class="g-input-check" checked="checked">可用</label> 
				<label> <input name="isu" value="2" type="radio" class="g-input-check">禁用</label>
			</div>
		</div>
		
		<div>
			<span class="light-label">&nbsp;</span>
			<div class="light-label-info">
				<input type="button" id="lightOk" class="g-btn" value="确定" />
				<input type="button" id="lightCancel" class="g-btn" value="取消" />
			</div>
		</div>
		
	</div>
	<div id="fade" class="black_overlay"></div>
	
	<div id="pageNav"></div>
</body>
</html>