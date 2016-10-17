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
		url : ctx + "/airl/air-line!queryByPage.do",
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
		var_tr += "<td>" + checkNumm(item.dep) + "</td>";
		var_tr += "<td>" + checkNumm(item.arr) + "</td>";
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
		var_tr += "&nbsp;&nbsp;<a href='javascript:deleteData(\""+ item.id + "\")'>删除</a>";
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
			$("input[name='dep']").val(item.dep); 
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
			$(".ct").text(item.ct);
			$(".dep").text(item.dep);
			$(".inat").text(item.inat);
			$(".isu").text(item.isu);
			if (item.inat == 1) {
				$(".inat").text("国内");
			} if (item.inat == 2){
				$(".inat").text("国际");
			}
			if (item.isu == 1) {
				$(".isu-td").text("可用");
			}if (item.isu == 2){
				$(".isu-td").text("禁用");
			} if (item.isu == 3){
				$(".isu-td").text("审核");
			}
		}
	});
	openDialogTable();
}

function deleteData(id) {
	if(confirm("确定删除？")){
		$.ajax({
			type : "post",
			dataType : "json",
			data : {
				id : id
			},
			url : ctx + "/airl/air-line!delete.do",
			success : function(result) {
				if (result.error) {
					alert(result.error);
				}
				if (result.success) {
					var page = $("#page").html();
					getDatas(page);
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
			$("input[name='air']").val("");
			$("input[name='arr']").val("");
			$("input[name='dep']").val("");
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
		var dep = $.trim($("input[name='dep']").val());
		var inat = $("input[name='inat']:checked").val();
		var isu = $("input[name='isu']:checked").val();
		if(air != "" & arr != ""& dep !="" ) {
			var entity = {};
			entity.id = $("#id").val();
			entity.air = air;
			entity.arr = arr;
			entity.dep = dep;
			entity.inat = inat;
			entity.isu = isu;
			$.ajax({
				type : "post",
				dataType : "json",
				data : entity,
				url : ctx + "/airl/air-line!saveOrUpdate.do",
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
