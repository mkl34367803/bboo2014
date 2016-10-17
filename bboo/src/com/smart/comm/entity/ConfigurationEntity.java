package com.smart.comm.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.smart.comm.utils.BBOOObject;

/**
 * 基礎數據配置表
 * 
 * @author sibtim
 * 
 */
@Entity
@Table(name = "t_configuration")
public class ConfigurationEntity extends BBOOObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7228120180556666539L;

	@OneToMany(targetEntity = ConfigurationDataEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "base")
	@OrderBy("id desc")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<ConfigurationDataEntity> datas = new HashSet<ConfigurationDataEntity>(
			0);

	@OneToMany(targetEntity = ConfigurationExplainEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "base")
	@OrderBy("id desc")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<ConfigurationExplainEntity> exps = new HashSet<ConfigurationExplainEntity>(
			0);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	private Integer id;
	/**
	 * 商户号
	 */
	@Column(name = "mno", length = 40)
	private String mno;

	/**
	 * 配置 GW_TICKETING_ACCOUNT(GTA)
	 */
	@Column(name = "configurationKey", length = 30)
	private String configurationKey;

	/**
	 * 名称 官网出票卡号 航司官網出票賬號
	 */
	@Column(name = "configurationName", length = 100)
	private String configurationName;

	/**
	 * 类型 1 單配置 2多配置
	 */
	@Column(name = "configurationType")
	private Integer configurationType;

	/**
	 * 是否共享 1 是 2否
	 */
	@Column(name = "isShare")
	private Integer isShare;
	/**
	 * TIME
	 */
	@Column(name = "createTime", length = 30)
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

	public String getConfigurationKey() {
		return configurationKey;
	}

	public void setConfigurationKey(String configurationKey) {
		this.configurationKey = configurationKey;
	}

	public String getConfigurationName() {
		return configurationName;
	}

	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}

	public Integer getConfigurationType() {
		return configurationType;
	}

	public void setConfigurationType(Integer configurationType) {
		this.configurationType = configurationType;
	}

	public Integer getIsShare() {
		return isShare;
	}

	public void setIsShare(Integer isShare) {
		this.isShare = isShare;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Set<ConfigurationDataEntity> getDatas() {
		return datas;
	}

	public void setDatas(Set<ConfigurationDataEntity> datas) {
		this.datas = datas;
	}

	public Set<ConfigurationExplainEntity> getExps() {
		return exps;
	}

	public void setExps(Set<ConfigurationExplainEntity> exps) {
		this.exps = exps;
	}

}
