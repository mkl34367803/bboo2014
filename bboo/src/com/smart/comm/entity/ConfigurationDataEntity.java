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

@Entity
@Table(name = "t_configuration_data")
public class ConfigurationDataEntity extends BBOOObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2976954405608556970L;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = ConfigurationEntity.class, cascade = { CascadeType.ALL })
	@JoinColumn(name = "fkid")
	private ConfigurationEntity base;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	private Integer id;
	/**
	 * 表主键
	 */
	@Column(name = "fkid", length = 55, insertable = false, updatable = false)
	private String fkid;

	@Column(name = "A1", length = 55)
	private String A1;
	@Column(name = "A2", length = 55)
	private String A2;
	@Column(name = "A3", length = 55)
	private String A3;
	@Column(name = "A4", length = 55)
	private String A4;
	@Column(name = "A5", length = 55)
	private String A5;
	@Column(name = "A6", length = 55)
	private String A6;
	@Column(name = "A7", length = 55)
	private String A7;
	@Column(name = "A8", length = 55)
	private String A8;
	@Column(name = "A9", length = 55)
	private String A9;
	@Column(name = "A10", length = 55)
	private String A10;
	@Column(name = "A11", length = 55)
	private String A11;
	@Column(name = "A12", length = 55)
	private String A12;
	@Column(name = "A13", length = 55)
	private String A13;
	@Column(name = "A14", length = 55)
	private String A14;
	@Column(name = "A15", length = 55)
	private String A15;
	@Column(name = "A16", length = 55)
	private String A16;
	@Column(name = "A17", length = 55)
	private String A17;
	@Column(name = "A18", length = 55)
	private String A18;
	@Column(name = "A19", length = 55)
	private String A19;
	@Column(name = "A20", length = 55)
	private String A20;
	@Column(name = "A21", length = 55)
	private String A21;
	@Column(name = "A22", length = 55)
	private String A22;
	@Column(name = "A23", length = 55)
	private String A23;
	@Column(name = "A24", length = 55)
	private String A24;
	@Column(name = "A25", length = 55)
	private String A25;
	@Column(name = "A26", length = 55)
	private String A26;
	@Column(name = "A27", length = 55)
	private String A27;
	@Column(name = "A28", length = 55)
	private String A28;
	/**
	 * 創建時間
	 */
	@Column(name = "CT", length = 35)
	private String CT;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getA1() {
		return A1;
	}

	public void setA1(String a1) {
		A1 = a1;
	}

	public String getA2() {
		return A2;
	}

	public void setA2(String a2) {
		A2 = a2;
	}

	public String getA3() {
		return A3;
	}

	public void setA3(String a3) {
		A3 = a3;
	}

	public String getA4() {
		return A4;
	}

	public void setA4(String a4) {
		A4 = a4;
	}

	public String getA5() {
		return A5;
	}

	public void setA5(String a5) {
		A5 = a5;
	}

	public String getA6() {
		return A6;
	}

	public void setA6(String a6) {
		A6 = a6;
	}

	public String getA7() {
		return A7;
	}

	public void setA7(String a7) {
		A7 = a7;
	}

	public String getA8() {
		return A8;
	}

	public void setA8(String a8) {
		A8 = a8;
	}

	public String getA9() {
		return A9;
	}

	public void setA9(String a9) {
		A9 = a9;
	}

	public String getA10() {
		return A10;
	}

	public void setA10(String a10) {
		A10 = a10;
	}

	public String getA11() {
		return A11;
	}

	public void setA11(String a11) {
		A11 = a11;
	}

	public String getA12() {
		return A12;
	}

	public void setA12(String a12) {
		A12 = a12;
	}

	public String getA13() {
		return A13;
	}

	public void setA13(String a13) {
		A13 = a13;
	}

	public String getA14() {
		return A14;
	}

	public void setA14(String a14) {
		A14 = a14;
	}

	public String getA15() {
		return A15;
	}

	public void setA15(String a15) {
		A15 = a15;
	}

	public String getA16() {
		return A16;
	}

	public void setA16(String a16) {
		A16 = a16;
	}

	public String getA17() {
		return A17;
	}

	public void setA17(String a17) {
		A17 = a17;
	}

	public String getA18() {
		return A18;
	}

	public void setA18(String a18) {
		A18 = a18;
	}

	public String getA19() {
		return A19;
	}

	public void setA19(String a19) {
		A19 = a19;
	}

	public String getA20() {
		return A20;
	}

	public void setA20(String a20) {
		A20 = a20;
	}

	public String getA21() {
		return A21;
	}

	public void setA21(String a21) {
		A21 = a21;
	}

	public String getA22() {
		return A22;
	}

	public void setA22(String a22) {
		A22 = a22;
	}

	public String getA23() {
		return A23;
	}

	public void setA23(String a23) {
		A23 = a23;
	}

	public String getA24() {
		return A24;
	}

	public void setA24(String a24) {
		A24 = a24;
	}

	public String getA25() {
		return A25;
	}

	public void setA25(String a25) {
		A25 = a25;
	}

	public String getA26() {
		return A26;
	}

	public void setA26(String a26) {
		A26 = a26;
	}

	public String getA27() {
		return A27;
	}

	public void setA27(String a27) {
		A27 = a27;
	}

	public String getA28() {
		return A28;
	}

	public void setA28(String a28) {
		A28 = a28;
	}

	public String getCT() {
		return CT;
	}

	public void setCT(String cT) {
		CT = cT;
	}

	public ConfigurationEntity getBase() {
		return base;
	}

	public void setBase(ConfigurationEntity base) {
		this.base = base;
	}

	public String getFkid() {
		return fkid;
	}

	public void setFkid(String fkid) {
		this.fkid = fkid;
	}

}
