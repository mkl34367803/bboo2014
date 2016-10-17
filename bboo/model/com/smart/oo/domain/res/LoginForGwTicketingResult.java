package com.smart.oo.domain.res;

public class LoginForGwTicketingResult {

	private String PERSONUUID;
	/**
	 * 深圳莫林
	 */
	private String PERSONNAME;
	/**
	 * 余大国
	 */
	private String OWN;
	/**
	 * admin
	 */
	private String ACCOUNT;
	private String SAFETOKEN;

	public String getPERSONUUID() {
		return PERSONUUID;
	}

	public void setPERSONUUID(String pERSONUUID) {
		PERSONUUID = pERSONUUID;
	}

	public String getPERSONNAME() {
		return PERSONNAME;
	}

	public void setPERSONNAME(String pERSONNAME) {
		PERSONNAME = pERSONNAME;
	}

	public String getOWN() {
		return OWN;
	}

	public void setOWN(String oWN) {
		OWN = oWN;
	}

	public String getACCOUNT() {
		return ACCOUNT;
	}

	public void setACCOUNT(String aCCOUNT) {
		ACCOUNT = aCCOUNT;
	}

	public String getSAFETOKEN() {
		return SAFETOKEN;
	}

	public void setSAFETOKEN(String sAFETOKEN) {
		SAFETOKEN = sAFETOKEN;
	}

}
