package com.smart.oo.domain;

import java.util.List;

public class GetOrderForPb2bDomain {

	private List<AccountVO> accounts;
	private String pnrContent;
	private String patContent;
	private String pnr;
	private String bigPnr;// 大编码
	private String proxyip;
	private String proxyport;
	private String requestId;
	private String orderNo;
	private String saleId;

	public List<AccountVO> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountVO> accounts) {
		this.accounts = accounts;
	}

	public String getPnrContent() {
		return pnrContent;
	}

	public void setPnrContent(String pnrContent) {
		this.pnrContent = pnrContent;
	}

	public String getPatContent() {
		return patContent;
	}

	public void setPatContent(String patContent) {
		this.patContent = patContent;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String getBigPnr() {
		return bigPnr;
	}

	public void setBigPnr(String bigPnr) {
		this.bigPnr = bigPnr;
	}

	public String getProxyip() {
		return proxyip;
	}

	public void setProxyip(String proxyip) {
		this.proxyip = proxyip;
	}

	public String getProxyport() {
		return proxyport;
	}

	public void setProxyport(String proxyport) {
		this.proxyport = proxyport;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSaleId() {
		return saleId;
	}

	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}

}
