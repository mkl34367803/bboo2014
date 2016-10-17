package com.smart.oo.action.services;

import javax.annotation.Resource;

import com.bbang.api.internal.util.StringUtils;
import com.smart.comm.utils.OOUtils;
import com.smart.framework.base.BaseAction;
import com.smart.oo.domain.res.LoginForGwTicketingResult;
import com.smart.oo.domain.res.OrderDetailForGwTicketingResult;
import com.smart.oo.service.IInterfaceService;
import com.smart.utils.JsonPluginsUtil;

public class InterfaceCommAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3492538794712445477L;

	@Resource(name = "BBOOInterfaceServiceImpl")
	private IInterfaceService interfaces;

	public void entrance() {
		super.isPage = false;
		String json = this.getParameter("json");
		if (StringUtils.isEmpty(json))
			json = this.getParameterStream(null);
		String resjson = null;
		String msg = null;
		if (json != null && json.contains("{")) {
			try {
				if (json.indexOf("api_service_login") != -1) {
					// 登陆接口
					LoginForGwTicketingResult rlt = interfaces.loginSys(json);
					resjson = JsonPluginsUtil.beanToJson(rlt);
				} else if (json.indexOf("api_service_getGwAllConfig") != -1) {
					resjson = interfaces.getGwAllConfigJson(json);
				} else if (json.indexOf("api_service_getOrderDetail") != -1) {
					OrderDetailForGwTicketingResult rlt = interfaces
							.getOrderDetail(json);
					resjson = JsonPluginsUtil.beanToJson(rlt);
				} else {

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				msg = OOUtils.exceptionToString(e);
				e.printStackTrace();
			}
		}
		this.writeStream(StringUtils.isEmpty(resjson) ? (msg != null ? msg
				: "不支持该API接口调用，请与管理员联系") : resjson, null);
		return;
	}

}
