package com.smart.oo.domain.res;

public class FlightDetailVO {

	/**
	 * 航班号
	 */
	private String flightNum;
	/**
	 * 共享航班号
	 */
	private String shareNum;
	/**
	 * 舱位
	 */
	private String cabin;
	/**
	 * 出发机场三字码
	 */
	private String depAircode;
	/**
	 * 到达机场三字码
	 */
	private String arrAircode;
	/**
	 * 出发日期
	 */
	private String departureDate;
	/**
	 * 起飞时间
	 */
	private String departureTime;

	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}

	public String getShareNum() {
		return shareNum;
	}

	public void setShareNum(String shareNum) {
		this.shareNum = shareNum;
	}

	public String getCabin() {
		return cabin;
	}

	public void setCabin(String cabin) {
		this.cabin = cabin;
	}

	public String getDepAircode() {
		return depAircode;
	}

	public void setDepAircode(String depAircode) {
		this.depAircode = depAircode;
	}

	public String getArrAircode() {
		return arrAircode;
	}

	public void setArrAircode(String arrAircode) {
		this.arrAircode = arrAircode;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

}
