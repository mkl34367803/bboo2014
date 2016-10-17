package com.smart.oo.domain.res;

import java.util.List;

public class ConfigForGwTicketingResult {

	private GWAccountVOS airAccounts;

	private List<ShopAccountVO> shopAccounts;

	private ContactsVOS link;

	public GWAccountVOS getAirAccounts() {
		return airAccounts;
	}

	public void setAirAccounts(GWAccountVOS airAccounts) {
		this.airAccounts = airAccounts;
	}

	public List<ShopAccountVO> getShopAccounts() {
		return shopAccounts;
	}

	public void setShopAccounts(List<ShopAccountVO> shopAccounts) {
		this.shopAccounts = shopAccounts;
	}

	public ContactsVOS getLink() {
		return link;
	}

	public void setLink(ContactsVOS link) {
		this.link = link;
	}

}
