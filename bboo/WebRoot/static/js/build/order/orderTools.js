/**
 * 获取订单的图标
 * @param distributor
 * @returns
 */
function getOrderPic(distributor) {
	if(distributor=="Q"){  //去哪儿的图标
		return "<img src='"+ctx+"/static/img/build/qunaer.png'/>";
	}else if(distributor=="C"){  //携程的图标
		return "<img src='"+ctx+"/static/img/build/trip.png'/>";
	}else if(distributor=="T"){  //淘宝的图标
		return "<img src='"+ctx+"/static/img/build/taob.png'/>";
	}else if(distributor=="N"){  //途牛的图标
		return "<img src='"+ctx+"/static/img/build/tuniu.jpg'/>";
	}else if(distributor=="O"){  //同程的图标
		return "<img src='"+ctx+"/static/img/build/tongc.png'/>";
	}
	return "";
}

/**
 * 获取航程类型 中文
 * @param flightType
 * @returns
 */
function getFlightTypeCh(flightType) {
	if(flightType=="S"){
		return "单程";
	}else if(flightType=="D"){
		return "往返";
	}else if(flightType=="T"){
		return "其它";
	}else if(flightType=="L"){
		return "联程";
	}else if(flightType=="X"){
		return "多程";
	}
	return flightType;
}

/**
 * 获取订单状态 中文
 * @param slfStatu
 * @returns
 */
function getSlfStatusCH(slfStatu) {
	if (slfStatu == 0) {
		return "订座成功等待支付";
	} else if (slfStatu == 1) {
		return "支付成功等待出票";
	} else if (slfStatu == 2) {
		return "出票完成";
	} else if (slfStatu == 5) {
		return "出票中";
	} else if (slfStatu == 12) {
		return "订单取消";
	} else if (slfStatu == 20) {
		return "等待座位确认";
	} else if (slfStatu == 30) {
		return "退票申请中";
	} else if (slfStatu == 31) {
		return "退票完成等待退款";
	} else if (slfStatu == 39) {
		return "退款完成";
	} else if (slfStatu == 40) {
		return "改签申请中";
	} else if (slfStatu == 42) {
		return "改签完成";
	} else if (slfStatu == 50) {
		return "未出票申请退款";
	} else if (slfStatu == 51) {
		return "订座成功等待价格确认";
	} else if (slfStatu == 60) {
		return "支付待确认";
	} else if (slfStatu == 61) {
		return "暂缓出票";
	} else if (slfStatu == 62) {
		return "订单超时";
	} else if (slfStatu == 99) {
		return "待支付（系统）";
	} else if (slfStatu == 70) {
		return "退票留票中";
	} else if (slfStatu == -1) {
		return "其它";
	} else {
		return slfStatu;
	}
}


//根据参数名称获取参数值  (获取浏览器地址的参数)
function getParamValue(name) {
	var paramsArray = getUrlParams();
	if (paramsArray != null) {
		for (var i = 0; i < paramsArray.length; i++) {
			for (var j in paramsArray[i]) {
				if (j == name) {
					return paramsArray[i][j];
				}
			}
		}
	}
	return null;
}
// 获取地址栏的参数数组  
function getUrlParams() {
	var search = window.location.search;
	// 写入数据字典  
	var tmparray = search.substr(1, search.length).split("&");
	var paramsArray = new Array;
	if (tmparray != null) {
		for (var i = 0; i < tmparray.length; i++) {
			var reg = /[=|^==]/; // 用=进行拆分，但不包括==  
			var set1 = tmparray[i].replace(reg, '&');
			var tmpStr2 = set1.split('&');
			var array = new Array;
			array[tmpStr2[0]] = tmpStr2[1];
			paramsArray.push(array);
		}
	}
	// 将参数数组进行返回  
	return paramsArray;
}