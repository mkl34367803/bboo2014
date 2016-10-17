var cacheData;
function getDatas(page) {
	var data = {};
	var air =  $.trim($("input[name='searchAir']").val());
	var arr =  $.trim($("input[name='searchArr']").val());
	var dep =  $.trim($("input[name='searchDep']").val());
	data.dep = dep;
	data.air = air;
	data.arr = arr;
	data.page = page; // 分页查询
	data.rows = 15; // 每页展示多少条记录
	$.ajax({
		type : "post",
		dataType : "json",
		data : data,
		url : ctx + "/airf/air-flight!queryByPage.do",
		success : function(result) {
			if (result.error) {
				alert(result.error);
			}
			if (result.success) {
				if (result.list) {
					addTbodyData(result.list);
				}
				if (result.total== 0) {
					pageNav.go(page, Math.ceil(1));
				} else if (result.total) {
					pageNav.go(page, Math.ceil(result.total / data.rows));
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.messager.alert("提示","请求出错了");
		}
	});
}

function checkNumm(data) {
	return data == null ? "" : data;
}

function addTbodyData(data) {
	$(".content-td").html("");
	cacheData = data; // 页面上缓存数据。
	$.each(data, function(i, item) {
		var var_tr;
		var_tr  = "<td>" + checkNumm(item.air) + "</td>";
		var_tr += "<td>" + checkNumm(item.fno) + "</td>";
		var_tr += "<td>" + checkNumm(item.sno) + "</td>";
		var_tr += "<td>" + checkNumm(item.cabins) + "</td>";
		var_tr += "<td>" + checkNumm(item.dep) + "</td>";
		var_tr += "<td>" + checkNumm(item.arr) + "</td>";
		var_tr += "<td>" + checkNumm(item.dept) + "</td>";
		var_tr += "<td>" + checkNumm(item.arrt) + "</td>";
		if (item.inat == 1) {
			var_tr += "<td>国内</td>";
		} else if (item.inat ==2) {
			var_tr += "<td>国际</td>";
		}else{
			var_tr += "<td>" + checkNumm(item.inat) + "</td>";
		}
		if (item.isu == 1) {
			var_tr += "<td>可用</td>";
		}else if (item.isu ==2) {
			var_tr += "<td>禁用</td>";
		}else if (item.isu ==3) {
			var_tr += "<td>审核</td>";
		}else{
			var_tr += "<td>" + checkNumm(item.isu) + "</td>";
		}
		var_tr += "<td><a href='javascript:modifyData(\"" + item.id+ "\")'>修改</a>";
		var_tr += "&nbsp;&nbsp;<a href='javascript:modifyDatas(\""+ item.id + "\")'>详情</a>";
		var_tr = "<tr>" + var_tr + "</tr>";
		$(".content-td").append(var_tr);
	});
	$(".table-bd tr:even").addClass("even");
	$(".table-bd tr:odd").addClass("odd");
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
			$("input[name='air']").val(item.air);
			$("input[name='arr']").val(item.arr);
			$("input[name='cabins']").val(item.cabins);
			$("input[name='arrt']").val(item.arrt);
			$("input[name='dep']").val(item.dep); 
			$("input[name='dept']").val(item.dept);
			$("input[name='fno']").val(item.fno);
			$("input[name='indexkey']").val(item.indexkey);
			$("input[name='sno']").val(item.sno);
			$("input:radio[name='istop'][value='"+item.istop+"']").attr("checked", true);
			$("input:radio[name='inat'][value='"+item.inat+"']").attr("checked", true);
			$("input:radio[name='isu'][value='"+item.isu+"']").attr("checked", true);
		}
	});
	openDialog();
}

function openDialogTable(){
	$("#div_box").css('display', 'block');
	$("#fade").css('display', 'block');
}
function closeDialogTable() {
	$("#div_box").css('display', 'none');
	$("#fade").css('display', 'none');
}
function modifyDatas(id) {
	$.each(cacheData, function(i, item) {
		if (item.id == id) {
			$(".id-td").text(item.id);
			$(".air").text(item.air);
			$(".arr").text(item.arr);
			$(".arrt").text(item.arrt); 
			$(".cabins").text(item.cabins); 
			$(".ct").text(item.ct);
			$(".dep").text(item.dep);
			$(".dept").text(item.dept);
			$(".fno").text(item.fno);
			$(".indexkey").text(item.indexkey);
			$(".sno").text(item.sno);
			$(".istop").text(item.istop);
			$(".inat").text(item.inat);
			$(".isu").text(item.isu);
			if (item.istop == 1) {
				$(".istop").text("是");
			} if (item.istop == 2){
				$(".istop").text("否");
			}
			if (item.inat == 1) {
				$(".inat").text("国内");
			} if (item.inat == 2){
				$(".inat").text("国际");
			}
			if (item.isu == 1) {
				$(".isu").text("可用");
			}if (item.isu == 2){
				$(".isu").text("禁用");
			} if (item.isu == 3){
				$(".isu").text("审核");
			}
		}
	});
	openDialogTable();
}

function reset() {
	        $("#id").val("");
			$("input[name='air']").val("");
			$("input[name='arr']").val("");
			$("input[name='arrt']").val("");
			$("input[name='cabins']").val("");
			$("input[name='dep']").val("");
			$("input[name='dept']").val("");
			$("input[name='fno']").val("");
			$("input[name='indexkey']").val("");
			$("input[name='sno']").val("");
			$("input[name='istop'][value='2']").attr("checked", true);
			$("input[name='inat'][value='1']").attr("checked", true);
			$("input[name='isu'][value='1']").attr("checked", true);
}

$(document).ready(function() {
	$("#tableCancels").click(function(){
		closeDialogTable();
	})
	
	$("#searchButton").click(function() {
		getDatas(1);
	});
	$("#addButton").click(function() {
		addData();
	});
	$("#lightCancel").click(function() {
		closeDialog();
	});
	$("#lightOk").click(function() {
		var air = $.trim($("input[name='air']").val());
		var arr = $.trim($("input[name='arr']").val());
		var arrt = $.trim($("input[name='arrt']").val());
		var cabins = $.trim($("input[name='cabins']").val());
		var dep = $.trim($("input[name='dep']").val());
		var dept = $.trim($("input[name='dept']").val());
		var fno = $.trim($("input[name='fno']").val());
		var indexkey = $.trim($("input[name='indexkey']").val());
		var sno = $.trim($("input[name='sno']").val());
		var istop = $.trim($("input[name='istop']:checked").val());
		var inat = $.trim($("input[name='inat']:checked").val());
		var isu = $.trim($("input[name='isu']:checked").val());
		if(air != "" & arr != "" & arrt != "" & cabins != "" ) {
			var entity = {};
			entity.id = $("#id").val();
			entity.air = air;
			entity.arr = arr;
			entity.arrt = arrt;
			entity.cabins = cabins;
			entity.dep = dep;
			entity.dept = dept;
			entity.fno = fno;
			entity.indexkey = indexkey;
			entity.sno = sno;
			entity.istop = istop;
			entity.inat = inat;
			entity.isu = isu;
			$.ajax({
				type : "post",
				dataType : "json",
				data : entity,
				url : ctx + "/airf/air-flight!saveOrUpdate.do",
				success : function(result) {
					if (result.error) {
						alert(result.error);
					}
					if (result.success) {
						closeDialog();
						reset();
						var page = $("#page").html();
						if(page) {
							getDatas(page);
						} else {
							getDatas(1);
						}
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("请求出错了");
				}
			});
		}else {
				alert("请完整填写信息");
			}
	});
});
