package com.smart.oo.from;

public class StaffWorkVo {

	private Integer id;
	private String mno;//商户号
	private Integer fkUserId;//用户外键ID
	private String name;// 用户姓名
	private String flightClass;//N：国内 I：国际
	private String workType;//C：出票 T：退票 L:留票 Z:政策
	private String weeks;//周期
	private String wtimeStart;//工作开始时间
	private String wtimeEnd;//工作截至
	private String createTime;//创建时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMno() {
		return mno;
	}

	public void setMno(String mno) {
		this.mno = mno;
	}

	public Integer getFkUserId() {
		return fkUserId;
	}

	public void setFkUserId(Integer fkUserId) {
		this.fkUserId = fkUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWtimeStart() {
		return wtimeStart;
	}

	public void setWtimeStart(String wtimeStart) {
		this.wtimeStart = wtimeStart;
	}

	public String getWtimeEnd() {
		return wtimeEnd;
	}

	public void setWtimeEnd(String wtimeEnd) {
		this.wtimeEnd = wtimeEnd;
	}

	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	public String getFlightClass() {
		return flightClass;
	}

	public void setFlightClass(String flightClass) {
		this.flightClass = flightClass;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
