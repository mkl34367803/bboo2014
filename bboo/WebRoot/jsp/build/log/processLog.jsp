<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/share/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ProcessLog查询</title>
<%@include file="/jsp/share/JS.jsp"%>
<%@include file="/jsp/share/CSS.jsp"%>
<script src="${ctx }/static/js/build/log/processLog.js" type="text/javascript"></script>
<style type="text/css">
	.log-table tr td {
		padding-right: 15px;
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
			<h2>ProcessLog查询</h2>
		</div>
		<form action="">
			<table class="m-search g-clear g-table">
				<tr class="rows">
					<td class="col">
						<span class="label">时间</span>
						<div class="label-info">
							<div class="qcbox start-date">
								<input id="startDate" name="startDate" type="text" class="cinput" onclick="laydate()">
							</div>
							<span class="split">到</span>
							<div class="qcbox stop-date">
								<input id="endDate" name="endDate" type="text" class="cinput" onclick="laydate()">
							</div>
						</div>
					</td>
					<td class="col">
						<span class="label">日志类型</span>
						<div class="label-info">
							<span class="g-select"> 
								<select name="searchLogType">
									<option value="">全部</option>
									<option value="1">导出报表</option>
									<option value="2">查看统计</option>
									<option value="3">签到</option>
								</select> 
							</span>
						</div>
					</td>
					<td class="col">
						<span class="label">操作人</span>
						<div class="label-info">
							<span class="g-ipt-text"> <input type="text" name="searchOperator" id="searchOperator" /> </span>
						</div>
					</td>
				</tr>
				<tr class="rows">
					<td class="col">
						<span class="label">&nbsp;</span>
						<div class="label-info">
							<input type="button" id="searchButton" class="g-btn" value="查询" style="margin-right: 6px;" />
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="datagrid" class="g-content">
		<table class="log-table"></table>
	</div>
	
	<div id="fade" class="black_overlay"></div>
	<div id="pageNav"></div>
</body>
</html>