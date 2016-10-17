package com.smart.oo.domain.res;

import java.util.List;

public class OrderDetailVO {
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 乘客总数
	 */
	private int passengerCount;
	/**
	 * 
	 */
	private int childrenCount;
	/**
	 * 联系人姓名
	 */
	private String contactName;
	/**
	 * 联系人手机
	 */
	private String contactMob;
	/**
	 * 联系人电话
	 */
	private String contactTel;
	/**
	 * 联系人邮箱
	 */
	private String contactEmail;
	
	private String flightClass;
	
	private String distributor;
	
	private List<PassengerDetailVO> passengers;
	private List<FlightDetailVO> flights;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getPassengerCount() {
		return passengerCount;
	}

	public void setPassengerCount(int passengerCount) {
		this.passengerCount = passengerCount;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMob() {
		return contactMob;
	}

	public void setContactMob(String contactMob) {
		this.contactMob = contactMob;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public List<PassengerDetailVO> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<PassengerDetailVO> passengers) {
		this.passengers = passengers;
	}

	public List<FlightDetailVO> getFlights() {
		return flights;
	}

	public void setFlights(List<FlightDetailVO> flights) {
		this.flights = flights;
	}

	public String getFlightClass() {
		return flightClass;
	}

	public void setFlightClass(String flightClass) {
		this.flightClass = flightClass;
	}

	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}

	public int getChildrenCount() {
		return childrenCount;
	}

	public void setChildrenCount(int childrenCount) {
		this.childrenCount = childrenCount;
	}

}
