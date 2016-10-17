package com.smart.oo.domain;

public class LockOrderDomain {

	private GjOrderAccountVO account;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 操作类型：1 代表认领，0 代表取消认领
	 */
	private int operatorType;
	/**
	 * 子订单号
	 */
	private String issueBillID;
	/**
	 * N 国内 I 国际
	 */
	private String orderType;

	/**
	 * 分销商 T 淘宝 Q 去哪儿 C 携程 H 航班管家 K 酷讯 N 途牛 O 同程
	 */
	private String orderSource;

	public GjOrderAccountVO getAccount() {
		return account;
	}

	public void setAccount(GjOrderAccountVO account) {
		this.account = account;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(int operatorType) {
		this.operatorType = operatorType;
	}

	public String getIssueBillID() {
		return issueBillID;
	}

	public void setIssueBillID(String issueBillID) {
		this.issueBillID = issueBillID;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

}
