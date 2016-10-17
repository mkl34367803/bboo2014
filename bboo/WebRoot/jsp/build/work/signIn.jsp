<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/share/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工签到</title>
<%@include file="/jsp/share/JS.jsp"%>
<%@include file="/jsp/share/CSS.jsp"%>
<script src="${ctx }/static/js/build/work/signIn.js" type="text/javascript"></script>
<style type="text/css">
.t-content {
	overflow-y: auto;
	max-height: 390px;
}

.leftDiv {
	float: left;
	width: 49.8%;
}
.rightDiv {
	float: right;
	width: 49.8%;
}
</style>
<script type="text/javascript">
	var ctx = "${ctx}";
</script>
</head>
<body style="margin: 10px; min-width: 920px;">
	<div>
		<div class="mod-hd" style="margin-top: 10px">
			<h2>员工签到</h2>
		</div>
		<div class="g-content">
			<form action="">
				<input type="button" class="signIn g-btn" value="签到" />
				<input type="button" class="pause g-btn" value="暂停" />
				<input type="button" class="offWork g-btn " value="下班" />
			</form>
		</div>
	</div>
	
	<div class="leftDiv">
		<div class="mod-hd" style="margin-top: 10px">
			<h2>上班时间表</h2>
		</div>
		<div class="g-content t-content">
			<div>
				<span style="float: left;line-height: 28px; margin: 0px 10px 5px 10px">姓名</span>
				<span style="float: left; margin-right: 10px;" class="g-ipt-text"> <input type="text" name="searchName" id="searchName" /> </span>
				<input type="button" name="searchStaffWork" value="查询" class="g-btn" />
			</div>
			<table class="g-table table-list" style="min-width:100px;width: 100%">
				<thead class="table-hd ">
					<tr>
						<th class="pcol15">姓名</th>
						<th class="pcol15">航班类型</th>
						<th class="pcol15">工作类别</th>
						<th class="pcol20">工作周期</th>
						<th class="pcol35">上班时间</th>
					</tr>
				</thead>
				<tbody class="table-bd staff-work-bd"></tbody>
			</table>
			<div id="fade" class="black_overlay"></div>
			<div id="pageNav"></div>
		</div>
	</div>
	
	<div class="rightDiv">
		<div class="mod-hd" style="margin-top: 10px">
			<h2>签到日志</h2>
		</div>
		<div class="g-content t-content">
			<table class="g-table table-list" style="min-width:100px;width: 100%">
				<thead class="table-hd ">
					<tr>
						<th class="">姓名</th>
						<th class="">签到类别</th>
						<th class="">签到时间</th>
					</tr>
				</thead>
				<tbody class="table-bd process-log-bd"></tbody>
			</table>
		</div>
	</div>
	
</body>
</html>