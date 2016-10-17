package com.smart.oo.domain.res;

import com.smart.oo.domain.SelectOrderDetailDomain;
import com.smart.oo.response.ErrResponse;

public class SelectOrderDetailResult {

	/**
	 * 0表示成功，1表示失败
	 */
	private String code;
	private String msg;
	private OrderDetailVO order;
	private ErrResponse err;
	private SelectOrderDetailDomain request;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public OrderDetailVO getOrder() {
		return order;
	}

	public void setOrder(OrderDetailVO order) {
		this.order = order;
	}

	public ErrResponse getErr() {
		return err;
	}

	public void setErr(ErrResponse err) {
		this.err = err;
	}

	public SelectOrderDetailDomain getRequest() {
		return request;
	}

	public void setRequest(SelectOrderDetailDomain request) {
		this.request = request;
	}

}
