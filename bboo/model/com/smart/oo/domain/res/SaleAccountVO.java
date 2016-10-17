package com.smart.oo.domain.res;

public class SaleAccountVO {

	private Integer id;
	private String describe;
	/**
	 * 国内 国际
	 */
	private String flightClass;

	/**
	 * 淘宝 去哪儿
	 */
	private String platName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getFlightClass() {
		return flightClass;
	}

	public void setFlightClass(String flightClass) {
		this.flightClass = flightClass;
	}

	public String getPlatName() {
		return platName;
	}

	public void setPlatName(String platName) {
		this.platName = platName;
	}

}
