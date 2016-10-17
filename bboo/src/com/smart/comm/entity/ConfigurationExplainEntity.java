package com.smart.comm.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.smart.comm.utils.BBOOObject;

/**
 * 配置 字段说明
 * 
 * @author sibtim
 * 
 */
@Entity
@Table(name = "t_configuration_explain")
public class ConfigurationExplainEntity extends BBOOObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4309992697965035628L;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = ConfigurationEntity.class, cascade = { CascadeType.ALL })
	@JoinColumn(name = "fkid")
	private ConfigurationEntity base;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	private Integer id;

	/**
	 * t_configuration ID
	 */
	@Column(name = "fkid", length = 55, insertable = false, updatable = false)
	private Integer fkid;
	/**
	 * 例如 A1 A2 A3
	 */
	@Column(name = "fieldName", length = 10)
	private String fieldName;

	/**
	 * 账号
	 */
	@Column(name = "fieldVal", length = 50)
	private String fieldVal;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFkid() {
		return fkid;
	}

	public void setFkid(Integer fkid) {
		this.fkid = fkid;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldVal() {
		return fieldVal;
	}

	public void setFieldVal(String fieldVal) {
		this.fieldVal = fieldVal;
	}

	public ConfigurationEntity getBase() {
		return base;
	}

	public void setBase(ConfigurationEntity base) {
		this.base = base;
	}

}
