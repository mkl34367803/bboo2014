<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/share/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工上班时刻表</title>
<%@include file="/jsp/share/JS.jsp"%>
<%@include file="/jsp/share/CSS.jsp"%>
<script src="${ctx }/static/js/build/work/staffWork.js" type="text/javascript"></script>
<style type="text/css">

.white_content {
	top: 20%;
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
	padding-left: 60px
}

.qcbox {
	width: 92px;
	float: left;
}
.split {
    float: left;
    padding-right: 5px;
    padding-left: 5px;
    text-align: center;
    line-height: 28px;
}

.day-div {
	float: left;
	background-color: #eaf6fd;
	width: 23px;
	text-align: center;
    line-height: 28px;
    margin-right: 5px;
    cursor: pointer;
}

.week-div,.time-div {
	position: absolute;
	background-color: #fff;
	width: 198px;
	height: auto;
	z-index: 70;
	display: none;
	padding: 5px 5px 5px 5px;
	border: solid 1px #e4e4e4;
	max-height: 400px;
	margin-left: 5px;
}

.time-div {
	z-index: 2;
	bottom: 90px;
}

.week-btn {
	margin-top: 10px;
	min-width: 30px;
	height: 22px;
}

.time-tb tr td {
	width: 24px;
	text-align: center;
    line-height: 19px;
    cursor: pointer;
    border: 1px solid #ccc;
}

input {
    margin-right: -8px;
    line-height: 24px;
    border: 0 none;
    outline: 0;
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
			<h2>员工上班时刻表管理</h2>
		</div>
		<form action="">
			<table class="m-search g-clear g-table">
				<tr class="rows">
					<td class="col">
						<span class="label">姓名</span>
						<div class="label-info">
							<span class="g-ipt-text"> <input type="text" name="searchName" id="searchName" /> </span>
						</div>
					</td>
					<td class="col">
						<span class="label">工作类别</span>
						<div class="label-info">
							<span class="g-select">
								<select name="searchWorkType">
									<option value="">全部</option>
									<option value="C">出票</option>
									<option value="T">退票</option>
									<option value="L">留票</option>
									<option value="Z">政策</option>
								</select>
							</span>
						</div>
					</td>
					<td class="col">
						<span class="label">航班类型</span>
						<div class="label-info">
							<label> <input name="searchFlightClass" value="" type="radio" class="g-input-check" checked="checked">全部</label> 
							<label> <input name="searchFlightClass" value="N" type="radio" class="g-input-check">国内</label> 
							<label> <input name="searchFlightClass" value="I" type="radio" class="g-input-check">国际</label>
						</div>
					</td>
				</tr>
				<tr class="rows">
					<td class="col" colspan="3">
						<span class="label">&nbsp;</span>
						<div class="label-info">
							<input type="button" id="searchButton" class="g-btn" value="查询" />
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
					<th class="pcol10">姓名</th>
					<th class="pcol10">航班类型</th>
					<th class="pcol10">工作类别</th>
					<th class="pcol15">工作周期</th>
					<th class="pcol15">上班时间</th>
					<th class="pcol15">下班时间</th>
					<th class="pcol15">创建时间</th>
					<th class="pcol10">操作</th>
				</tr>
			</thead>
			<tbody class="table-bd"></tbody>
		</table>
	</div>
	
	
	<div id="light" class="white_content">
		
		<div style="display: none">
			<span>id</span><input type="text" name="id" id="id" />
			<span>id</span><input type="text" name="fkUserId" id="fkUserId" />
		</div>
		
		<span class="light-label">姓名</span>
		<div class="light-label-info">
			<span class="g-ipt-text"> <input type="text" name="name" disabled="disabled" style="background-color: white;color: black;" /> </span>
		</div><br>
		
		<span class="light-label">航班类型</span>
		<div class="light-label-info">
			<label> <input name="flightClass" value="N" type="radio" class="g-input-check" checked="checked">国内</label> 
			<label> <input name="flightClass" value="I" type="radio" class="g-input-check">国际</label>
		</div><br>
		
		<span class="light-label">工作类别</span>
		<div class="light-label-info">
			<span class="g-select" style="width: 207px;">
				<select name="workType">
					<option value="">全部</option>
					<option value="C">出票</option>
					<option value="T">退票</option>
					<option value="L">留票</option>
					<option value="Z">政策</option>
				</select>
			</span>
		</div><br>
		
		<span class="light-label">工作周期</span>
		<div class="light-label-info">
			<span class="g-ipt-text"> <input type="text" name="weeks" /> </span>
			<div class="week-div">
				<div class="day-div">1</div>
				<div class="day-div">2</div>
				<div class="day-div">3</div>
				<div class="day-div">4</div>
				<div class="day-div">5</div>
				<div class="day-div">6</div>
				<div class="day-div">7</div><br>
				<div style="float: right;">
					<input type="button" name="weekSure" class="g-btn week-btn" value="确定" style="font-size: 12px;line-height: 20px; margin-right: 5px;" />
					<input type="button" name="weekCancel" class="g-btn week-btn" value="取消" style="font-size: 12px;line-height: 20px;" />
				</div>
			</div>
		</div><br>
		
		
		<span class="light-label">工作时间</span>
		<div class="light-label-info">
			
			<div class="time-div">
				<table class="time-tb">
				</table>
			</div>
			
			<div class="qcbox">
				<input name="wtimeStart" type="text" class="cinput time-select">
			</div>
			<span class="split">到</span>
			<div class="qcbox">
				<input name="wtimeEnd" type="text" class="cinput time-select">
			</div>
		</div><br>
		
		<div  style="margin-top: 25px;">
			<span class="light-label">&nbsp;</span>
			<div class="light-label-info">
				<input type="button" id="lightOk" class="g-btn" value="确定" style="margin-right: 7px;" />
				<input type="button" id="lightCancel" class="g-btn" value="取消" />
			</div>
		</div>
		
	</div>
	<div id="fade" class="black_overlay"></div>
	<div id="pageNav"></div>
</body>
</html>