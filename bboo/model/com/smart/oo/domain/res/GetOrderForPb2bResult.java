package com.smart.oo.domain.res;

public class GetOrderForPb2bResult {

	private String executeMsg;
	/**
	 * SUCCESS FAIL
	 */
	private String executeStatus;
	/**
	 * 请求ID
	 */
	private String requestId;
	/**
	 * S0 成功 F1失败
	 */
	private String executeCode;
	/**
	 * 订单状态 0 订座成功等待支付(人工) 1 支付成功等待出票 2 出票完成 5 出票中 12 订单取消 20 等待座位确认 30 退票申请中 31
	 * 退票完成等待退款 39 退款完成 40 改签申请中 42 改签完成 50 未出票申请退款 51 订座成功等待价格确认 60 支付待确认（支付失败）
	 * 61 暂缓出票（出票失败） 62 订单超时 -1其它 99 订座成功等待支付（系统） 70退票留票中 80 审核未通过，拒绝退票 81
	 * 审核未通过，拒绝改签
	 */
	private String orderState;

	private Pb2bOrderElementVO rlts;

	public String getExecuteMsg() {
		return executeMsg;
	}

	public void setExecuteMsg(String executeMsg) {
		this.executeMsg = executeMsg;
	}

	public String getExecuteStatus() {
		return executeStatus;
	}

	public void setExecuteStatus(String executeStatus) {
		this.executeStatus = executeStatus;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getExecuteCode() {
		return executeCode;
	}

	public void setExecuteCode(String executeCode) {
		this.executeCode = executeCode;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public Pb2bOrderElementVO getRlts() {
		return rlts;
	}

	public void setRlts(Pb2bOrderElementVO rlts) {
		this.rlts = rlts;
	}

}
