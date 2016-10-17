package com.smart.oo.action.order;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.smart.comm.entity.GjSaleOrderEntity;
import com.smart.entity.User;
import com.smart.framework.base.BaseAction;
import com.smart.framework.base.Page;
import com.smart.framework.security.SecurityContext;
import com.smart.oo.from.SaleOrderParamVo;
import com.smart.oo.service.imp.FactoryServiceImpl;

public class GjOrderSummaryAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 @Autowired
	 private FactoryServiceImpl factoryService;

	// 分页查询订单的汇总情况
	public void queryOrderByPage() {
		try {
			String flightClass = request.getParameter("flightClass"); // 表单的东西，传过来至少是个空串。
			String startDate = request.getParameter("startDate"); // 表单的东西，传过来至少是个空串。
			String endDate = request.getParameter("endDate");
			String contactMob = request.getParameter("contactMob");
			String flightType = request.getParameter("flightType");
			String orderNo = request.getParameter("orderNo");
			String passengerName = request.getParameter("passengerName");
			String ticketNum = request.getParameter("ticketNum");
			String distributorCh = request.getParameter("distributorCh");
			String shopNameCh = request.getParameter("shopNameCh");
			String pnrCode = request.getParameter("pnrCode");
			String orderStatus = request.getParameter("orderStatus");
			String backno = request.getParameter("backno");
			String carrier = request.getParameter("carrier");
			String statement = request.getParameter("statement");
			if(distributorCh!=null){
				request.getSession().setAttribute("distributorCh",distributorCh);
			}
			if(shopNameCh!=null){
				request.getSession().setAttribute("shopNameCh",shopNameCh);
			}
			if(carrier!=null){
				request.getSession().setAttribute("carrier",carrier);
			}
			if(statement!=null){
				request.getSession().setAttribute("statement",statement);
			}
			
			
			SaleOrderParamVo saleOrderParamEntity = null; // 一种策略，Dao层判断为null的时候，就不去查询了
			if (StringUtils.isNotEmpty(flightClass)||StringUtils.isNotEmpty(startDate) || StringUtils.isNotEmpty(endDate) || StringUtils.isNotEmpty(contactMob) || StringUtils.isNotEmpty(flightType) || StringUtils.isNotEmpty(orderNo)
					|| StringUtils.isNotEmpty(passengerName) || StringUtils.isNotEmpty(ticketNum)) {
				saleOrderParamEntity = new SaleOrderParamVo();
				if (StringUtils.isNotEmpty(flightClass)) {
					saleOrderParamEntity.setFlightClass(flightClass);
				}
				if (StringUtils.isNotEmpty(startDate)) {
					saleOrderParamEntity.setStartDate(startDate);
				}
				if (StringUtils.isNotEmpty(endDate)) {
					saleOrderParamEntity.setEndDate(endDate);
				}
				if (StringUtils.isNotEmpty(contactMob)) {
					saleOrderParamEntity.setContactMob(contactMob);
				}
				if (StringUtils.isNotEmpty(flightType)) {
					saleOrderParamEntity.setFlightType(flightType);
				}
				if (StringUtils.isNotEmpty(orderNo)) {
					saleOrderParamEntity.setOrderNo(orderNo);
				}
				if (StringUtils.isNotEmpty(passengerName)) {
					saleOrderParamEntity.setPassengerName(passengerName);
				}
				if (StringUtils.isNotEmpty(ticketNum)) {
					saleOrderParamEntity.setTicketNum(ticketNum);
				}
				if (StringUtils.isNotEmpty(distributorCh)) {
					saleOrderParamEntity.setDistributorCh(distributorCh);
				}
				if (StringUtils.isNotEmpty(shopNameCh)) {
					saleOrderParamEntity.setShopNameCh(shopNameCh);
				}
				if (StringUtils.isNotEmpty(orderStatus)) {
					saleOrderParamEntity.setOrderStatus(orderStatus);
				}
				if (StringUtils.isNotEmpty(pnrCode)) {
					saleOrderParamEntity.setPnrCode(pnrCode);
				}
				if (StringUtils.isNotEmpty(backno)) {
					saleOrderParamEntity.setBackno(Integer.parseInt(backno));
				}
				if (StringUtils.isNotEmpty(carrier)) {
					saleOrderParamEntity.setCarrier(carrier);
				}
				if (StringUtils.isNotEmpty(statement)) {
					saleOrderParamEntity.setStatement(statement);
				}
			}
			/*--------------------------订单查询时涉及到的表单参数-----------------------------*/
			String page = request.getParameter("page");
			if (StringUtils.isEmpty(page)) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("error", "查询页为必填字段");
				this.writeStream(jsonObject, "utf-8");
				return;
			}
			Page pageEntity = new Page();
			pageEntity.setPageSize(20); // 20个订单做为一页
			pageEntity.setStartPage(Integer.parseInt(page));
			// 要从下面的user信息中取商户号mno，查询的时候带上商户mno
			User user = (User) request.getSession().getAttribute("SESSION_KEY_CURRENT_USER");
			//List<OrderSummaryVo> list = factoryService.getSaleOrderService().queryOrderSummaryByPage(pageEntity, user, saleOrderParamEntity);
			//List<OrderSummaryVo> list = factoryService.getSaleOrderService().readOrderSummaryByPage(pageEntity, user, saleOrderParamEntity);
			List<GjSaleOrderEntity> list = factoryService.getSaleOrderService().readOrderSummaryByPage1(pageEntity, user, saleOrderParamEntity);
			String jsonString = JSON.toJSONString(list);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("total", pageEntity.getTotalCount());
			jsonObject.put("list", jsonString);
			this.writeStream(jsonObject, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("error", e.toString());
			this.writeStream(jsonObject, "utf-8");
		}
	}
	
	/**
	 * 获取分销商中文，和英文
	 */
	public void queryDistributors(){
		try {
			User user = SecurityContext.getUser();
			String mno=user.getMert().getMno();
			String flightClass = request.getParameter("flightClass"); // 表单的东西，传过来至少是个空串。
			String orderStatus = request.getParameter("orderStatus");
			List<GjSaleOrderEntity> list=factoryService.getSaleOrderService().queryDistributors(mno,flightClass,orderStatus);
			String jsonString = JSON.toJSONString(list);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("success", "查询成功");
			jsonObject.put("list", jsonString);
			this.writeStream(jsonObject, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("error", e.toString());
			this.writeStream(jsonObject, "utf-8");
		}
	}
	/**
	 * 获取分销商中文，和英文
	 */
	public void queryShopnames(){
		try {
			User user = SecurityContext.getUser();
			String mno=user.getMert().getMno();
			String flightClass = request.getParameter("flightClass"); // 表单的东西，传过来至少是个空串。
			String orderStatus = request.getParameter("orderStatus");
			List<GjSaleOrderEntity> list=factoryService.getSaleOrderService().queryShopnames(mno,flightClass,orderStatus);
			String jsonString = JSON.toJSONString(list);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("success", "查询成功");
			jsonObject.put("list", jsonString);
			this.writeStream(jsonObject, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("error", e.toString());
			this.writeStream(jsonObject, "utf-8");
		}
	}
}
