package com.smart.comm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.smart.comm.utils.BBOOObject;

/**
 * 员工 上班时刻表
 * 
 * @author sibtim
 * 
 */
@Entity
@Table(name = "t_staff_work")
public class StaffWorkEntity extends BBOOObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1744006942507661278L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	private Integer id;
	/**
	 * 商户号
	 */
	@Column(name = "mno", length = 40)
	private String mno;

	@Column(name = "timestamp", columnDefinition = "timestamp", insertable = false, updatable = false)
	private String timestamp;

	@Column(name = "stamptime", insertable = false)
	private Long stamptime = System.currentTimeMillis();
	/**
	 * 用户外键ID
	 */
	@Column(name = "fkUserId")
	private Integer fkUserId;
	/**
	 * 工作开始时间
	 */
	@Column(name = "wtimeStart", length = 10)
	private String wtimeStart;
	/**
	 * 工作截至
	 */
	@Column(name = "wtimeEnd", length = 10)
	private String wtimeEnd;
	/**
	 * 周期
	 */
	@Column(name = "weeks", length = 20)
	private String weeks;

	/**
	 * 签到 1上班 2下班 3暂停
	 */
	@Column(name = "signIn")
	private Integer signIn;

	/**
	 * N：国内 I：国际
	 */
	@Column(name = "flightClass", length = 3)
	private String flightClass;

	/**
	 * C：出票 T：退票 L:留票 Z:政策
	 */
	@Column(name = "workType", length = 3)
	private String workType;
	/**
	 * 派单订单个数
	 */
	@Column(name = "amount")
	private Integer amount;
	/**
	 * 创建时间
	 */
	@Column(name = "createTime", length = 25)
	private String createTime;

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

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public Long getStamptime() {
		return stamptime;
	}

	public void setStamptime(Long stamptime) {
		this.stamptime = stamptime;
	}

	public Integer getFkUserId() {
		return fkUserId;
	}

	public void setFkUserId(Integer fkUserId) {
		this.fkUserId = fkUserId;
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

	public Integer getSignIn() {
		return signIn;
	}

	public void setSignIn(Integer signIn) {
		this.signIn = signIn;
	}

	public String getFlightClass() {
		return flightClass;
	}

	public void setFlightClass(String flightClass) {
		this.flightClass = flightClass;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

}
