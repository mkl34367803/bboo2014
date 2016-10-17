package com.smart.comm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.smart.comm.utils.BBOOObject;

/**
 * 账户管理表
 * 
 * @author sibtim
 * 
 */

@Entity
@Table(name = "t_account_manage")
public class AccountManageEntity extends BBOOObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7212726529535573112L;

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
	 * C 采购 Z 支付
	 */
	@Column(name = "accountType", length = 3)
	private String accountType;
	/**
	 * 和 purchasePlace payWay 对应
	 */
	@Column(name = "val", length = 25)
	private String val;

	/**
	 * 中文
	 */
	@Column(name = "valCh", length = 50)
	private String valCh;

	/**
	 * 账户
	 */
	@Column(name = "account", length = 100)
	private String account;
	
	/**
	 * 密碼
	 */
	@Column(name = "password", length = 35)
	private String password;

	/**
	 * 航司二字代码 空匹配全部
	 */
	@Column(name = "aircode", length = 5)
	private String aircode;
	/**
	 * 1 可用 2禁用
	 */
	@Column(name = "isu")
	private Integer isu;
	/**
	 * 系统时间
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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getValCh() {
		return valCh;
	}

	public void setValCh(String valCh) {
		this.valCh = valCh;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAircode() {
		return aircode;
	}

	public void setAircode(String aircode) {
		this.aircode = aircode;
	}

	public Integer getIsu() {
		return isu;
	}

	public void setIsu(Integer isu) {
		this.isu = isu;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
