package com.smart.oo.domain.res;

import java.util.List;

import com.smart.oo.domain.GetGwAllConfigDomain;
import com.smart.oo.response.ErrResponse;

public class GetGwAllConfigResult {

	private List<SaleAccountVO> accounts;

	private LinkerVO link;

	private List<AirAccountVO> airv;

	private ErrResponse err;
	
	private GetGwAllConfigDomain request;

	public ErrResponse getErr() {
		return err;
	}

	public void setErr(ErrResponse err) {
		this.err = err;
	}

	public List<SaleAccountVO> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<SaleAccountVO> accounts) {
		this.accounts = accounts;
	}

	public LinkerVO getLink() {
		return link;
	}

	public void setLink(LinkerVO link) {
		this.link = link;
	}

	public List<AirAccountVO> getAirv() {
		return airv;
	}

	public void setAirv(List<AirAccountVO> airv) {
		this.airv = airv;
	}

	public GetGwAllConfigDomain getRequest() {
		return request;
	}

	public void setRequest(GetGwAllConfigDomain request) {
		this.request = request;
	}

}
