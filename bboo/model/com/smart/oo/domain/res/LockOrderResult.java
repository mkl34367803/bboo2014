package com.smart.oo.domain.res;

public class LockOrderResult {

	/**
	 * 0成功
	 */
	private String code;
	/**
	 * true 成功
	 */
	private boolean result;
	private String description;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
