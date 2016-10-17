package com.smart.oo.service;

import com.smart.oo.domain.res.ConfigForGwTicketingResult;
import com.smart.oo.domain.res.GetGwAllConfigResult;
import com.smart.oo.domain.res.LoginForGwTicketingResult;
import com.smart.oo.domain.res.LoginResult;
import com.smart.oo.domain.res.OrderDetailForGwTicketingResult;
import com.smart.oo.domain.res.SelectOrderDetailResult;

public interface IInterfaceService {

	LoginResult login(String json) throws Exception;

	LoginForGwTicketingResult loginSys(String json) throws Exception;

	GetGwAllConfigResult getGwConfig(String json) throws Exception;

	ConfigForGwTicketingResult getGwAllConfig(String json) throws Exception;

	String getGwAllConfigJson(String json) throws Exception;

	SelectOrderDetailResult getOrderInfoByOrderNo(String json) throws Exception;

	OrderDetailForGwTicketingResult getOrderDetail(String json)
			throws Exception;
}
