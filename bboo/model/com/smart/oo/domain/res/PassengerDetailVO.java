package com.smart.oo.domain.res;

public class PassengerDetailVO {
	/**
	 * 乘客姓名
	 */
	private String name;
	/**
	 * 性别 M 男 F 女
	 */
	private String gender;

	/**
	 * 乘客类别 -1 留学生 0 成人 1 儿童 2婴儿 -2其他
	 */
	private Integer ageType;
	/**
	 * 生日
	 */
	private String birthday;

	/**
	 * 国籍
	 */
	private String nationality;

	/**
	 * 证件类型 PP 护照 HX 回乡证 TB 台胞证 GA 港澳通行证 HY 国际海员证 NI身份证 XS 学生证 JR 军人证 JS 驾驶证 TW
	 * 台湾通行证 SB 士兵证 LN 临时身份证 HK 户口簿 JG 警官证 TH 其它 CS 出生证明
	 */
	private String cardType;

	/**
	 * 证件号码
	 */
	private String cardNum;
	/**
	 * 证件有效期
	 */
	private String cardExpired;
	/**
	 * 证件签发国家
	 */
	private String cardIssuePlace;
	/**
	 * 销售底价 结算价
	 */
	private Double cost;
	/**
	 * 燃油
	 */
	private Double oilFee;
	/**
	 * 基建
	 */
	private Double taxFee;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAgeType() {
		return ageType;
	}

	public void setAgeType(Integer ageType) {
		this.ageType = ageType;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getCardExpired() {
		return cardExpired;
	}

	public void setCardExpired(String cardExpired) {
		this.cardExpired = cardExpired;
	}

	public String getCardIssuePlace() {
		return cardIssuePlace;
	}

	public void setCardIssuePlace(String cardIssuePlace) {
		this.cardIssuePlace = cardIssuePlace;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getOilFee() {
		return oilFee;
	}

	public void setOilFee(Double oilFee) {
		this.oilFee = oilFee;
	}

	public Double getTaxFee() {
		return taxFee;
	}

	public void setTaxFee(Double taxFee) {
		this.taxFee = taxFee;
	}
}
