var cacheData;
function getDatas(page) {
	var data = {};
	var carrier =  $.trim($("input[name='searchCarrier']").val());
	var cabin   =  $.trim($("input[name='searchCabin']").val());
	var depCode =  $.trim($("input[name='searchDepCode']").val());
	var arrCode =  $.trim($("input[name='searchArrCode']").val());
	data.cabin=cabin;
	data.carrier = carrier;
	data.depCode = depCode;
	data.arrCode = arrCode;
	data.page = page; // 分页查询
	data.rows = 15; // 每页展示多少条记录
	$.ajax({
		type : "post",
		dataType : "json",
		data : data,
		url : ctx + "/airp/air-price!queryByPage.do",
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
		var_tr = "<td>" + checkNumm(item.carrier) + "</td>";
		var_tr += "<td>" + checkNumm(item.depCode) + "</td>";
		var_tr += "<td>" + checkNumm(item.arrCode) + "</td>";
		var_tr += "<td>" + checkNumm(item.baseCabin) + "</td>";
		var_tr += "<td>" + checkNumm(item.cabin) + "</td>";
		var_tr += "<td>" + checkNumm(item.discount) + "</td>";
		var_tr += "<td>" + checkNumm(item.basePrice) + "</td>";
		var_tr += "<td>" + checkNumm(item.price) + "</td>";
		if (item.isDelete == "1") {
			var_tr += "<td>无效</td>";
		} else if (item.isDelete =="0") {
			var_tr += "<td>有效</td>";
		}else{
			var_tr += "<td>" +checkNumm(item.isDelete) + "</td>";
		}
		var_tr += "<td>" + checkNumm(item.beginDate) + "</td>";
		var_tr += "<td>" + checkNumm(item.endDate) + "</td>";
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
			$("input[name='beginDate']").val(item.beginDate);
			$("input[name='endDate']").val(item.endDate);
			$("input[name='carrier']").val(item.carrier);
			$("input[name='predaysStart']").val(item.predaysStart); 
			$("input[name='predaysEnd']").val(item.predaysEnd); 
			$("input[name='depCode']").val(item.depCode);
			$("input[name='arrCode']").val(item.arrCode);
			$("input[name='baseCabin']").val(item.baseCabin);
			$("input[name='cabin']").val(item.cabin);
			$("input[name='discount']").val(item.discount);
			$("input[name='basePrice']").val(item.basePrice);
			$("input[name='price']").val(item.price);
			$("input[name='keys']").val(item.keys);
			$("input:radio[name='isDelete'][value='"+item.isDelete+"']").attr("checked", true);
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
			$("#id").val(item.id);
			$(".beginDate").text(item.beginDate);
			$(".endDate").text(item.endDate);
			$(".carrier").text(item.carrier);
			$(".predaysStart").text(item.predaysStart); 
			$(".predaysEnd").text(item.predaysEnd); 
			$(".depCode").text(item.depCode);
			$(".arrCode").text(item.arrCode);
			$(".baseCabin").text(item.baseCabin);
			$(".cabin").text(item.cabin);
			$(".discount").text(item.discount);
			$(".basePrice").text(item.basePrice);
			$(".price").text(item.price);
			$(".createTime").text(item.createTime);
			$(".isDeletes").text(item.isDelete);
			$(".keyID").text(item.keyID);
			$(".a").text(item.a);
			$(".n").text(item.n);
			$(".s").text(item.s);
			$(".id").text(item.id);
			$(".keys").text(item.keys);
			if (item.isDelete == 0) {
				$(".isDeletes-td").text("有效");
			} else {
				$(".isDeletes-td").text("无效");
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
			url : ctx + "/airp/air-price!delete.do",
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
			$("input[name='beginDate']").val("");
			$("input[name='endDate']").val("");
			$("input[name='carrier']").val("");
			$("input[name='predaysStart']").val("");
			$("input[name='predaysEnd']").val("");
			$("input[name='depCode']").val("");
			$("input[name='arrCode']").val("");
			$("input[name='baseCabin']").val("");
			$("input[name='cabin']").val("");
			$("input[name='discount']").val("");
			$("input[name='basePrice']").val("");
			$("input[name='price']").val("");
			$("input:radio[name='isDelete'][isDelete='1']").attr("checked", true);
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
		var beginDate = $.trim($("input[name='beginDate']").val());
		var endDate = $.trim($("input[name='endDate']").val());
		var carrier = $.trim($("input[name='carrier']").val());
		var predaysStart = $.trim($("input[name='predaysStart']").val());
		var predaysEnd = $.trim($("input[name='predaysEnd']").val());
		var depCode = $.trim($("input[name='depCode']").val());
		var arrCode = $.trim($("input[name='arrCode']").val());
		var baseCabin = $.trim($("input[name='baseCabin']").val());
		var cabin = $.trim($("input[name='cabin']").val());
		var discount = $.trim($("input[name='discount']").val());
		var basePrice = $.trim($("input[name='basePrice']").val());
		var price = $.trim($("input[name='price']").val());
		var createTime = $.trim($("input[name='createTime']").val());
		var keys = $.trim($("input[name='keys']").val());
		var IsDelete = $("input[name='isDelete']:checked").val();
		if(carrier != "" & cabin != "" &depCode != "" & arrCode != "" ) {
			var entity = {};
			entity.id = $("#id").val();
			entity.beginDate = beginDate;
			entity.endDate = endDate;
			entity.carrier = carrier;
			entity.predaysStart = predaysStart;
			entity.predaysEnd = predaysEnd;
			entity.depCode = depCode;
			entity.arrCode = arrCode;
			entity.baseCabin = baseCabin;
			entity.cabin = cabin;
			entity.discount = discount;
			entity.basePrice = basePrice;
			entity.price = price;
			entity.createTime = createTime;
			entity.keys = keys;
			entity.isDelete=IsDelete;
			$.ajax({
				type : "post",
				dataType : "json",
				data : entity,
				url : ctx + "/airp/air-price!saveOrUpdate.do",
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

$(document).ready(function() {
	var startDate = {
			  elem: '#beginDate',
			  format: 'YYYY-MM-DD ',
			  /*min: laydate.now(), //设定最小日期为当前日期*/
			  max: '2099-06-16 ', //最大日期
			  festival: true, //显示节日
			  istime: false,
			  istoday: true,
			  choose: function(datas){
			     endDate.min = datas; //开始日选好后，重置结束日的最小日期
			     endDate.start = datas //将结束日的初始值设定为开始日
			  }
			};
			var endDate = {
			  elem: '#endDate',
			  format: 'YYYY-MM-DD',
			  min: laydate.now(),//设定最小日期为当前日期
			  max: '2099-06-16 ',
			  festival: true, //显示节日
			  istime: false,
			  istime: false,
			  istoday: true,
			  choose: function(datas){
			    startDate.max = datas; //结束日选好后，重置开始日的最大日期
			  }
			};
			laydate(startDate);
			laydate(endDate);
});

