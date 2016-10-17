package com.smart.oo.action.ticket;

import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.smart.comm.entity.TicketStateEntity;
import com.smart.comm.utils.OOUtils;
import com.smart.framework.base.BaseAction;
import com.smart.framework.security.SecurityContext;
import com.smart.oo.service.trigger.QueryTicketStateTrigger;
import com.smart.oo.vo.DetrInfoResVO;
import com.smart.utils.JsonPluginsUtil;

/**
 * 手动查询客票状态
 * 
 * @author eric
 * 
 */
@Results({ @Result(name = "q_ticket", location = "/jsp/build/ticket/TicketDetail.jsp") })
public class TicketHandAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8832143468170544026L;
	@Resource(name = "BBOOQueryTicketStateTrigger")
	private QueryTicketStateTrigger trigger;

	public String query() {
		return "q_ticket";
	}

	public void queryTicket() {
		String mno = OOUtils.getUserMerchant(SecurityContext.getUser());
		Map<String, String> parames = this.getParameters();
		String ticketNo = parames.get("tno");
		String pname = parames.get("pname");
		TicketStateEntity t = new TicketStateEntity();
		t.setNo(ticketNo);
		t.setPassengerName(pname);
		t.setMno(mno);
		DetrInfoResVO detrRs = trigger.getTicketInfo(t);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("root", JsonPluginsUtil.beanToJson(detrRs));
		this.writeStream(jsonObject, "utf-8");
	}
}
