var cacheData;
function getDates(startPage) {
	var searchAccount = $.trim($("#searchAccount").val());
	var searchAircode = $.trim($("#searchAircode").val());
	var searchAccountType = $("input[name='searchAccountType']:checked").val();
	var searchIsu = $("input[name='searchIsu']:checked").val();
	var data = {};
	data.account = searchAccount;
	data.aircode = searchAircode;
	data.accountType = searchAccountType;
	data.isu = searchIsu;
	
	data.startPage = startPage; // 分页查询
	data.pageSize = 20; // 每页展示多少条记录
	var oind = layer.open({
		type: 3,
		icon: 1
	});
	$.ajax({
		type : "post",
		dataType : "json",
		data : data,
		url : ctx + "/configure/account-manage!queryByPage.do",
		success : function(result) {
			layer.close(oind);
			if (result.error) {
				alert(result.error);
			}
			if (result.success) {
				if (result.list) {
					addTbodyData(result.list);
				}
				if (result.total == 0) {
					pageNav.go(startPage, Math.ceil(1));
				} else if (result.total) {
					pageNav.go(startPage, Math.ceil(result.total / data.pageSize));
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.close(oind);
			alert("请求出错了");
		}
	});
}

function addTbodyData(data) {
	$(".table-bd").html("");
	cacheData = data; // 页面上缓存数据。
	$.each(data, function(i, item) {
		var var_tr = "<td>" + item.account + "</td>";
		var_tr += "<td>" + getAccountTypeCh(item.accountType) + "</td>";
		var_tr += "<td>" + item.valCh + "</td>";
		var_tr += "<td>" + item.aircode + "</td>";
		var_tr += "<td>" + getIsuCh(item.isu) + "</td>";
		var_tr += "<td>" + item.createTime + "</td>";
		var_tr += "<td><a href='javascript:modifyData(\"" + item.id
			+ "\")'>修改</a>";
		var_tr += "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:deleteData(\""
			+ item.id + "\")'>删除</a>";
		var_tr = "<tr>" + var_tr + "</tr>";
		$(".table-bd").append(var_tr);
	});
	$(".table-bd tr:even").addClass("even");
	$(".table-bd tr:odd").addClass("odd");
}

/**
 * 获取账户类型
 * @param accountType
 */
function getAccountTypeCh(accountType) {
	if (accountType == "C") {
		return "采购";
	} else if (accountType == "Z") {
		return "支付";
	} else {
		return accountType;
	}
}

function getIsuCh(isu) {
	if (isu == "1") {
		return "可用";
	} else {
		return "禁用";
	}
}

function openDialog() {
	$("#light").css('display', 'block');
	$("#fade").css('display', 'block');
}
function closeDialog() {
	$("#light").css('display', 'none');
	$("#fade").css('display', 'none');
}
function addData() {
	openDialog();
	reset();
}
function modifyData(id) {
	$.each(cacheData, function(i, item) {
		if (item.id == id) {
			$("#id").val(item.id);
			$("#aircode").val(item.aircode);
			$("#account").val(item.account);
			$("input[name='accountType'][value='"+item.accountType+"']").attr("checked", true);
			if (item.accountType == "C") {
				$("select[name='purchasePlace']").val(item.val);
				$("select[name='payWay']").val("");
			} else {
				$("select[name='purchasePlace']").val("");
				$("select[name='payWay']").val(item.val);
			}
			$("input[name='isu'][value='"+item.accountType+"']").attr("checked", true);
			showValByAccountType(item.accountType);
			$("#pass").val(item.password);
		}
	});
	openDialog();
}

function deleteData(id) {
	if(confirm("确定删除？")){
		$.ajax({
			type : "post",
			dataType : "json",
			
			data : {
				id : id
			},
			url : ctx + "/configure/account-manage!delete.do",
			success : function(result) {
				if (result.error) {
					alert(result.error);
				}
				if (result.success) {
					//alert(result.success);
					var startPage = $("#page").html();
					getDates(startPage)
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("请求出错了");
			}
		});		
	}
}


function reset() {
	$("#id").val("");
	$("#aircode").val("");
	$("#account").val("");
	$("input[name='accountType'][value='C']").attr("checked", true);
	$("select[name='purchasePlace']").val("");
	$("select[name='payWay']").val("");
	$("input[name='isu'][value='1']").attr("checked", true);
	showValByAccountType("C");
	$("#pass").val("");
}

function showValByAccountType(accountType) {
	if (accountType == "C") {
		$(".purchase-select").show();
		$(".pay-select").hide();
	} else {
		$(".purchase-select").hide();
		$(".pay-select").show();
	}
}

$(document).ready(function() {
	
	$("#searchButton").click(function() {
		getDates(1);
	});
	
	$("#addButton").click(function() {
		addData();
	});
	$("#lightCancel").click(function() {
		closeDialog();
	});
	$("#lightOk").click(function() {
		var id = $.trim($("#id").val());
		var aircode = $.trim($("#aircode").val());
		var account = $.trim($("#account").val());
		var accountType = $("input[name='accountType']:checked").val();
		var val = "",valCh = "";
		if (accountType == "C") {
			val = $("select[name='purchasePlace']").val();
			valCh = $.trim($("select[name='purchasePlace']").find("option:selected").text());
		} else {
			val = $("select[name='payWay']").val();
			valCh = $.trim($("select[name='payWay']").find("option:selected").text());
		}
		
		var isu = $("input[name='isu']:checked").val();
		var pass = $.trim($("#pass").val());
		
		if(account != "" && val != "") {
			var data = {};
			data.id = id;
			data.aircode = aircode;
			data.account = account;
			data.accountType = accountType;
			data.val = val;
			data.valCh = valCh;
			data.isu = isu;
			data.pass = pass;
			$.ajax({
				type : "post",
				dataType : "json",
				data : data,
				url : ctx + "/configure/account-manage!saveOrUpdate.do",
				success : function(result) {
					if (result.error) {
						alert(result.error);
					}
					if (result.success) {
						//alert(result.success);
						closeDialog();
						reset();
						var startPage = $("#page").html();
						if (startPage) {
							getDates(startPage);
						} else {
							getDates(1);
						}
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("请求出错了");
				}
			});
		} else {
			alert("账户描述、账户不能为空");
		}
	});
	
	
	$("input[name='accountType']").change(function() {
		var at = $("input[name='accountType']:checked").val();
		showValByAccountType(at);
	});
	
});