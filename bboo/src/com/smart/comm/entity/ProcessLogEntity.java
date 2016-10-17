package com.smart.comm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.smart.comm.utils.BBOOObject;

@Entity
@Table(name = "t_process_log")
public class ProcessLogEntity extends BBOOObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3586831760667968281L;
	/**
	 * 
	 */
	@Id
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0, length = 55)
	private String id;

	@Column(name = "mno", length = 40)
	private String mno;
	/**
	 * 日志類型 1 導出報表 2 查看統計  3签到
	 */
	@Column(name = "logType", length = 15)
	private String logType;
	/**
	 * 日志内容
	 */
	@Column(name = "content", length = 100)
	private String content;
	/**
	 * 操作人
	 */
	@Column(name = "operator", length = 35)
	private String operator;
	/**
	 * 创建时间
	 */
	@Column(name = "createTime", length = 30)
	private String createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMno() {
		return mno;
	}

	public void setMno(String mno) {
		this.mno = mno;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
