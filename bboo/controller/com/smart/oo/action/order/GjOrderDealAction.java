package com.smart.oo.action.order;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.smart.comm.entity.AccountManageEntity;
import com.smart.comm.entity.BaseAccountEntity;
import com.smart.comm.entity.BaseOfficeEntity;
import com.smart.comm.entity.GjBuyOrderEntity;
import com.smart.comm.entity.GjSaleOrderEntity;
import com.smart.comm.entity.GjSalePassengerEntity;
import com.smart.comm.entity.ProductPriceEntity;
import com.smart.comm.utils.BBOOConstants;
import com.smart.comm.utils.OOLogUtil;
import com.smart.comm.utils.StringUtilsc;
import com.smart.entity.User;
import com.smart.framework.base.BaseAction;
import com.smart.framework.security.SecurityContext;
import com.smart.oo.action.syslog.SysLogAction;
import com.smart.oo.domain.res.GetPnrInfoResult;
import com.smart.oo.from.PurchaseInfoVo;
import com.smart.oo.from.ReservePnrVo;
import com.smart.oo.service.imp.FactoryServiceImpl;

public class GjOrderDealAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private FactoryServiceImpl factoryService;
	
	@Resource(name="SysLogAction")
	private SysLogAction sysLog;
	/**
	 * 处理页面的拆分订单，主要是拆分同一订单里面有成人和儿童的情况
	 */
	public void splitOrder(){
		try {
			String id=request.getParameter("id");
			String[] passengerIDs=request.getParameterValues("passengerIDs");
			if(StringUtils.isNotEmpty(id)&&passengerIDs!=null&&passengerIDs.length>0){
				factoryService.getSaleOrderService().splitOrder(id, passengerIDs);
				addSysLogMsg(id, "拆分订单");
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("success",id); //将id返回回去
				this.writeStream(jsonObject, "utf-8");
			}else{
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("error", "id和passengerID都是必填字段");
				this.writeStream(jsonObject, "utf-8");
			}
		} catch (Exception e) {
			try {
				this.writeStream(e.toString(), "utf-8");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	/**
	 * 保存采购信息，同时复制卖出订单到买入订单表中去。
	 */
	public void savePurchaseInfo(){
		try {
			String jsonString=request.getParameter("purchaseInfoVo");
//			String id=request.getParameter("id");  //订单id
//			String purchaseNo = request.getParameter("purchaseNo");
//			String purchaseAccount = request.getParameter("purchaseAccount");//采购账户
//			String etdzOffno = request.getParameter("etdzOffno");//采购账户
//			String etdzNo = request.getParameter("etdzNo");//采购账户
//			String pnrCode = request.getParameter("pnrCode");
//			String payWay = request.getParameter("payWay");
//			String payAccount = request.getParameter("payAccount"); //支付账户
//			String transactionNo = request.getParameter("transactionNo");
//			String printPrice = request.getParameter("printPrice");
//			String backPoint = request.getParameter("backPoint");
//			String afterPoint = request.getParameter("afterPoint");
//			String stickingPoint = request.getParameter("stickingPoint");
//			String reward = request.getParameter("reward");
//			String price = request.getParameter("price");//支付机票款(单个人) printPrice*（1-backPoint/100）-reward
////			String tax = request.getParameter("tax"); //税费==基建+燃油
//			String payPirce = request.getParameter("payPirce");  //结算价
//			String oilFee = request.getParameter("oilFee"); //燃油费
//			String taxFee = request.getParameter("taxFee"); //基建费（乘客里面还有个printprice）
//			String lr = request.getParameter("lr");
//			String allprice = request.getParameter("allprice");
//			String purchasePlace = request.getParameter("purchasePlace");
//			String purchasePlaceCh = request.getParameter("purchasePlaceCh");
//			String slfRemark = request.getParameter("slfRemark");
//			String lossReason = request.getParameter("lossReason");
			PurchaseInfoVo purchaseInfoVo=JSON.parseObject(jsonString,PurchaseInfoVo.class);
//			purchaseInfoVo.setId(id);
//			purchaseInfoVo.setAfterPoint(afterPoint);
//			purchaseInfoVo.setAllprice(allprice);
//			purchaseInfoVo.setBackPoint(backPoint);
//			if(StringUtils.isNotEmpty(etdzNo)){
//				purchaseInfoVo.setEtdzNo(Integer.parseInt(etdzNo));
//			}
//			purchaseInfoVo.setEtdzOffno(etdzOffno);
//			purchaseInfoVo.setId(id);
//			purchaseInfoVo.setLr(lr);
//			purchaseInfoVo.setOilFee(oilFee);
//			purchaseInfoVo.setPayAccount(payAccount);
//			purchaseInfoVo.setPayPirce(payPirce);
//			purchaseInfoVo.setPayWay(payWay);
//			purchaseInfoVo.setPnrCode(pnrCode);
//			purchaseInfoVo.setPrice(price);
//			purchaseInfoVo.setPrintPrice(printPrice);
//			purchaseInfoVo.setPurchaseAccount(purchaseAccount);
//			purchaseInfoVo.setPurchaseNo(purchaseNo);
//			purchaseInfoVo.setPurchasePlace(purchasePlace);
//			purchaseInfoVo.setPurchasePlaceCh(purchasePlaceCh);
//			purchaseInfoVo.setReward(reward);
//			purchaseInfoVo.setSlfRemark(slfRemark);
//			purchaseInfoVo.setStickingPoint(stickingPoint);
//			purchaseInfoVo.setTaxFee(taxFee);
//			purchaseInfoVo.setTransactionNo(transactionNo);
//			purchaseInfoVo.setLossReason(lossReason);
			factoryService.getGjBuyOrderService().createOrderBySavePurchaseInfo(purchaseInfoVo);
			addSysLogMsg(purchaseInfoVo.getId(), "保存采购信息");
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success", "保存采购信息成功");
			this.writeStream(jsonObject, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("error",e.toString());
			this.writeStream(jsonObject, "utf-8");
		}
	}
	
	/**
	 * 更新票号和pnr
	 * @return
	 */
	public void updateTicketNumAndPnr(){
		try {
			JSONObject jsonObj = new JSONObject();
			
			String passengers = request.getParameter("salePassengers");
			if (StringUtilsc.isNotEmptyAndNULL(passengers)) {
				JSONObject passObject = JSONObject.fromObject(passengers);
				String orderId = passObject.getString("orderId");
				String pnrCode = passObject.getString("pnrCode");
				String isUpdateIntefaceStr = passObject.getString("isUpdateInteface");
				
				JSONArray passArray = passObject.getJSONArray("passengers");
				
				boolean flag = this.factoryService.getSaleOrderService().updateEticketAndPnr(orderId, pnrCode, passArray);
				if (flag) {
					info("订单状态更新成功，orderId="+orderId);
					addSysLogMsg(orderId, "订单状态更新为出票成功");
				}
				jsonObj.put("success", "票号更新成功");
				
				if(isUpdateAllETicketNum(orderId)) {
					// 是否更新第三方接口
					boolean isUpdateInteface = false;
					if (StringUtilsc.isNotEmpty(isUpdateIntefaceStr)) {
						isUpdateInteface = Boolean.parseBoolean(isUpdateIntefaceStr);
					}
					
					if (isUpdateInteface) {
						String result = this.factoryService.getAllApiService().bookEticketNoThirdInterface(orderId);
						if (result == "") {
							jsonObj.put("error", "第三方接口票号失败");
							GjSaleOrderEntity paramVo=new GjSaleOrderEntity();
							paramVo.setId(orderId);
							paramVo.setBackno(BBOOConstants.GjSaleOrderEntity_backno_three);
							factoryService.getSaleOrderService().updateByID(paramVo);
						} else {
							JSONObject resultObj = JSONObject.fromObject(result);
							boolean isuss = (Boolean) resultObj.get("isuss");
							if (!isuss) {
								jsonObj.put("error", resultObj.get("msg").toString());
								GjSaleOrderEntity paramVo=new GjSaleOrderEntity();
								paramVo.setId(orderId);
								paramVo.setBackno(BBOOConstants.GjSaleOrderEntity_backno_three);
								factoryService.getSaleOrderService().updateByID(paramVo);
							} else {
								jsonObj.put("success", "第三方接口票号成功");
								info("第三方接口票号成功,orderId="+orderId);
								GjSaleOrderEntity paramVo=new GjSaleOrderEntity();
								paramVo.setId(orderId);
								paramVo.setBackno(BBOOConstants.GjSaleOrderEntity_backno_one);
								factoryService.getSaleOrderService().updateByID(paramVo);
							}
							info("票号更新成功");
							addSysLogMsg(orderId, "status订单状态更新为出票成功");
						}
					}
				}
				this.writeStream(jsonObj, "utf-8");
			} else {
				jsonObj.put("error", "数据获取失败");
				this.writeStream(jsonObj, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("error", e.toString());
			this.writeStream(jsonObject, "utf-8");
		}
	}
	
	// 更新第三方接口票号
	public void updateInterfaceTicket() {
		try {
			String orderId = request.getParameter("orderId");
			JSONObject jsonObj = new JSONObject();
			String result = this.factoryService.getAllApiService().bookEticketNoThirdInterface(orderId);
			if (result == "") {
				jsonObj.put("error", "第三方接口票号失败");
			} else {
				JSONObject resultObj = JSONObject.fromObject(result);
				boolean isuss = (Boolean) resultObj.get("isuss");
				if (!isuss) {
					jsonObj.put("error", resultObj.get("msg").toString());
				} else {
					jsonObj.put("success", "第三方接口票号成功");
					info("第三方接口票号成功,orderId="+orderId);
				}
				info("票号更新成功");
				addSysLogMsg(orderId, "第三方接口票号更新成功");
			}
			this.writeStream(jsonObj, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("error", e.toString());
			this.writeStream(jsonObject, "utf-8");
		}
	}
	
	/**
	 * 是否更新所有票号
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private boolean isUpdateAllETicketNum(String id) throws Exception {
		GjSaleOrderEntity saleOrderEntity = this.factoryService.getSaleOrderService().queryOrderByID(id);
		Set<GjSalePassengerEntity> salePassengerEntities = saleOrderEntity.getPassengers();
		int i = 0;
		for (GjSalePassengerEntity gjSalePassengerEntity : salePassengerEntities) {
			if (StringUtilsc.isNotEmptyAndNULL(gjSalePassengerEntity.getEticketNum())) {
				i++;
			}
		}
		if(i == salePassengerEntities.size()){
			return true;
		} else {
			return false;
		}
	}

	
	/**
	 * 更新订单状态
	 * @return
	 */
	public void updateSlfStatus(){
		try {
			String id = request.getParameter("id");
			String slfStatus = request.getParameter("slfStatus");
			if (StringUtilsc.isNotEmpty(id) && StringUtilsc.isNotEmpty(slfStatus)) {
				factoryService.getSaleOrderService().updateSlfStatus(id, slfStatus);
				info("订单状态更新成功");
				String slfStatusCh = factoryService.getCommonMethodService().getSlfStatusCH(slfStatus);
				addSysLogMsg(id, "订单状态更新为"+slfStatusCh+"。");
				this.writeStream("success", "utf-8");
			} else {
				info("订单状态更新失败，订单ID为："+id);
				this.writeStream("订单状态更新失败", "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 添加订单日志
	public void addSysLogMsg(String foreginKey, String contents) throws Exception{
		User user = (User) request.getSession().getAttribute("SESSION_KEY_CURRENT_USER");
		//String visitip = request.getRemoteAddr();
		String visitip = getIpAddr(request);
		sysLog.addSysLog(user, visitip, foreginKey, BBOOConstants.SysLogEntity_logType_ORDER_LOG, contents);
	}
	
    public  String getIpAddr(HttpServletRequest request)  {
        String ip  =  request.getHeader( " x-forwarded-for " );
         if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
            ip  =  request.getHeader( " Proxy-Client-IP " );
        } 
         if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
            ip  =  request.getHeader( " WL-Proxy-Client-IP " );
        } 
         if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
           ip  =  request.getRemoteAddr();
       } 
        return  ip;
   }
	
	
	public void info(String msg){
		OOLogUtil.info(msg, GjOrderDetailAction.class, null);
	}
	/**
	 * 修改pnr同时修改大编码，和pnrtext文本信息
	 */
	public void modifyPnrAndProductData(){
		try {
			//订单导入的时候，rt了一次，进入页面后rt查政策适用换成的pnrTxt
			request.getSession().setAttribute("isUsePnrTxtCache", true);
			String id = request.getParameter("id");
			String pnrCode = request.getParameter("pnrCode");
			addSysLogMsg(id, "修改编码");
			if(StringUtils.isEmpty(id)||StringUtils.isEmpty(pnrCode)){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("error", "id和pnrCode都不能为空");
				this.writeStream(jsonObject, "utf-8");
			}else{
				Boolean bool=factoryService.getSaleOrderService().modifyPnrExtend(id,pnrCode);
				if(bool){
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("id", id);
					jsonObject.put("success", "修改pnr同时查询b2b运价信息成功");
					this.writeStream(jsonObject, "utf-8");
				}else{
					JSONObject jsonObject=new JSONObject();
					jsonObject.put("error", "服务器端有操作失败了");
					this.writeStream(jsonObject, "utf-8");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("error", e.toString());
			this.writeStream(jsonObject, "utf-8");
		}
	}
	/**
	 * 刷新政策信息，直接从第三方接口拉取政策信息
	 * 如果没有拉取到，不删除原来的政策信息
	 */
	public void refreshProductDataByThirdInterface(){
		try {
			String id = request.getParameter("id");
			//String pnrCode = request.getParameter("pnrCode");
			GjSaleOrderEntity gjSaleOrderEntity=new GjSaleOrderEntity();
			if(StringUtils.isEmpty(id)){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("error", "id不能为空");
				this.writeStream(jsonObject, "utf-8");
			}else{
				gjSaleOrderEntity.setId(id);
				//获取政策信息前没有做过rt查询，所以这里不使用缓存pnrtext文本信息查询政策信息
				List<ProductPriceEntity> list=factoryService.getProductPriceService().obtainProductByThirdAuthInterface(id,false);
				String jsonString = JSON.toJSONString(list);
				GjSaleOrderEntity gjOrderEntity=factoryService.getSaleOrderService().queryOrderByID(id);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("list", jsonString);
				jsonObject.put("id", id);
				jsonObject.put("gettime", gjOrderEntity.getGettime());
				jsonObject.put("pnrText", gjOrderEntity.getPnrText());
				jsonObject.put("pnrCodeBig", gjOrderEntity.getPnrCodeBig());
				jsonObject.put("success", "查询b2b运价信息成功");
				this.writeStream(jsonObject, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("error", e.toString());
			this.writeStream(jsonObject, "utf-8");
		}
	}
	
	public void getsdOfficeBymno(){
		try {
			
			User user = SecurityContext.getUser();
			String mno=user.getMert().getMno(); //获取用户的商户号
			if(StringUtils.isEmpty(mno)){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("error", "未获取到用户的商户号");
				this.writeStream(jsonObject, "utf-8");
				return;
			}else{
				BaseOfficeEntity baseOfficeEntity=new BaseOfficeEntity();
				baseOfficeEntity.setMno(mno);
				baseOfficeEntity.setOfftypes(BBOOConstants.BaseOfficeEntity_offtypes_SD);
				baseOfficeEntity.setIsu(BBOOConstants.BaseOfficeEntity_ISU_ONE);
				List<BaseOfficeEntity> baseOfficeEntities=factoryService.getBaseOfficeService().findDataList();
				List<BaseOfficeEntity> result=null;
				if(baseOfficeEntities!=null&&baseOfficeEntities.size()>0){
					result=new ArrayList<BaseOfficeEntity>();
					for(BaseOfficeEntity entity:baseOfficeEntities){
						if(mno.equals(entity.getMno())&&entity.getOfftypes().contains(BBOOConstants.BaseOfficeEntity_offtypes_SD)&&BBOOConstants.BaseOfficeEntity_ISU_ONE.equals(entity.getIsu())){
							result.add(entity);
						}
					}
				}
				String jsonString=JSON.toJSONString(result);
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("list", jsonString);
				jsonObject.put("success", "查询office信息成功");
				this.writeStream(jsonObject, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("error", e.toString());
			this.writeStream(jsonObject, "utf-8");
		}
	}
	
	/**
	 * reservePnrCode
	 */
	public void reservePnrCode(){
		try {
			//订单导入的时候，rt了一次，进入页面后rt查政策适用换成的pnrTxt
			request.getSession().setAttribute("isUsePnrTxtCache", true);
			String param=request.getParameter("param");
			ReservePnrVo reservePnrVo=JSON.parseObject(param, ReservePnrVo.class);
			String orderId=reservePnrVo.getId();
			if(StringUtils.isNotEmpty(orderId)){
				addSysLogMsg(orderId, "预定编码");
				boolean bool=factoryService.getSaleOrderService().reservePnrCode(reservePnrVo);
				if(bool){
					GjSaleOrderEntity result=factoryService.getSaleOrderService().queryOrderByID(orderId);
					if(result!=null){
						GetPnrInfoResult rtrs=factoryService.getAllApiService().getRtByThirdInterface(result, false);
						factoryService.getSaleOrderService().updatePnrTxtAndBigPnrByRtResult(result, rtrs);
//						BaseAccountEntity baseAccountEntity=factoryService.getBaseAccountService().queryBaseAccountByID(result.getAccountid());
//						if(baseAccountEntity!=null){
//							factoryService.getSaleOrderService().updateBigpnrAndImportPolicy(baseAccountEntity, result);
//						}
					}
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("id", reservePnrVo.getId());
					jsonObject.put("success", "预定pnr成功");
					this.writeStream(jsonObject, "utf-8");
				}else{
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("id", reservePnrVo.getId());
					jsonObject.put("error", "预定pnr失败");
					this.writeStream(jsonObject, "utf-8");
				}
			}else{
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("error", "order id cannot be empty");
				this.writeStream(jsonObject, "utf-8");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("error", e.toString());
			this.writeStream(jsonObject, "utf-8");
		}
	}
	
	public void createOrder(){
		try {
			String orderId=request.getParameter("orderId");
			String productPriceId=request.getParameter("productPriceId");
			String payType=request.getParameter("payType");
			String baseOfficeId=request.getParameter("baseOfficeId");
			String lossReason=request.getParameter("lossReason");
			if(StringUtils.isEmpty(orderId)){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("error", "order id cannot be empty");
				this.writeStream(jsonObject, "utf-8");
				return;
			}
			if(StringUtils.isEmpty(productPriceId)){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("error", "productPriceId cannot be empty");
				this.writeStream(jsonObject, "utf-8");
				return;
			}
			if(StringUtils.isEmpty(payType)){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("error", "payType cannot be empty");
				this.writeStream(jsonObject, "utf-8");
				return;
			}
			GjSaleOrderEntity gjSaleOrderEntity=factoryService.getSaleOrderService().queryOrderByID(orderId);
			if(gjSaleOrderEntity==null){
				throw new RuntimeException("未查询到相关订单");
			}
			GjBuyOrderEntity gjBuyOrderEntity=factoryService.getGjBuyOrderService().queyOrderByID(orderId);
			if(gjBuyOrderEntity!=null){
				throw new RuntimeException("该订单已经创建过，请刷新后重试！");
			}
			addSysLogMsg(orderId, "创建订单");
			if(!StringUtils.isEmpty(baseOfficeId)){
				//如果这个东西不为空，说明是bsp的etdz出票，走另外一个流程，（下面流程都不走了）
				boolean result=factoryService.getGjBuyOrderService().createOrderByEtdz(orderId, baseOfficeId, productPriceId,lossReason);
				JSONObject jsonObject=new JSONObject();
				if(result){
					jsonObject.put("success", "创建订单成功");
				}else{
					jsonObject.put("error", "创建订单失败");
				}
				this.writeStream(jsonObject, "utf-8");
				return;
			}
		
			Boolean createOrderResult=factoryService.getGjBuyOrderService().createOrderByThirdInterface(productPriceId, gjSaleOrderEntity,lossReason);
			if(createOrderResult){
				if(payType.equals("notPay")){
					JSONObject jsonObject=new JSONObject();
					jsonObject.put("success", "创建订单成功");
					this.writeStream(jsonObject, "utf-8");
					
				}else if(payType.equals("directPay")){
					JSONObject jsonObject=new JSONObject();
					jsonObject.put("success", "创建订单成功");
					jsonObject.put("directPay", "直接支付");
					this.writeStream(jsonObject, "utf-8");
				}else if(payType.equals("autoPay")){
					GjBuyOrderEntity paramVo=new GjBuyOrderEntity();
					paramVo.setId(orderId);
					paramVo.setSlfStatus(BBOOConstants.GjBuyOrderEntity_slfStatus_ninetynine);
					factoryService.getGjBuyOrderService().updateById(paramVo);
					JSONObject jsonObject=new JSONObject();
					jsonObject.put("success", "创建订单成功");
					this.writeStream(jsonObject, "utf-8");
				}
			}else{
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("error", "创建订单失败");
				this.writeStream(jsonObject, "utf-8");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("error", e.toString());
			this.writeStream(jsonObject, "utf-8");
		}
	}
	/**
	 * 查询支付账号
	 */
	public void queryPayAccount(){
		try {
			String payWay=request.getParameter("payWay");
			User user=SecurityContext.getUser();
			String mno=user.getMert().getMno();
			List<AccountManageEntity> accountManageEntities= factoryService.getAccountManageService().getCaches(mno, true);
			List<AccountManageEntity> list=null;
			if(accountManageEntities!=null&&accountManageEntities.size()>0){
				list=new ArrayList<AccountManageEntity>();
				Iterator<AccountManageEntity> iterator=accountManageEntities.iterator();
				while(iterator.hasNext()){
					AccountManageEntity accountManageEntity=iterator.next();
					if(mno.equals(accountManageEntity.getMno())
							&&payWay.equals(accountManageEntity.getVal())
							&&BBOOConstants.AccountManageEntity_accountType_pay.equals(accountManageEntity.getAccountType())
							&&BBOOConstants.AccountManageEntity_isu_enable.equals(accountManageEntity.getIsu())){
						list.add(accountManageEntity);
					}
				}
			}
			String jsonString=JSON.toJSONString(list);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("list", jsonString);
			jsonObject.put("success", "查询成功");
			this.writeStream(jsonObject, "utf-8");
			 
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("error", e.toString());
			this.writeStream(jsonObject, "utf-8");
			
		}
	}
	/**
	 * 查询采购账号
	 */
	public void queryPurchaseAccount(){
		try {
			String purchasePlace=request.getParameter("purchasePlace");
			User user=SecurityContext.getUser();
			String mno=user.getMert().getMno();
			List<AccountManageEntity> accountManageEntities= factoryService.getAccountManageService().getCaches(mno, true);
			List<AccountManageEntity> list=null;
			if(accountManageEntities!=null&&accountManageEntities.size()>0){
				list=new ArrayList<AccountManageEntity>();
				Iterator<AccountManageEntity> iterator=accountManageEntities.iterator();
				while(iterator.hasNext()){
					AccountManageEntity accountManageEntity=iterator.next();
					if(mno.equals(accountManageEntity.getMno())
							&&purchasePlace.equals(accountManageEntity.getVal())
							&&BBOOConstants.AccountManageEntity_accountType_purchase.equals(accountManageEntity.getAccountType())
							&&BBOOConstants.AccountManageEntity_isu_enable.equals(accountManageEntity.getIsu())){
						list.add(accountManageEntity);
					}
				}
			}
			String jsonString=JSON.toJSONString(list);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("list", jsonString);
			jsonObject.put("success", "查询成功");
			this.writeStream(jsonObject, "utf-8");
			 
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("error", e.toString());
			this.writeStream(jsonObject, "utf-8");
			
		}
	}
	
	/**
	 * 判断订单是否满足创单条件,如果不满足创单条件,让他自己选择是否继续创建订单.
	 */
	public void isSatisfyCreateOrderCoditionByThirdInterface(){
		try {
			String id=request.getParameter("id");  //订单id
			try{
				GjSaleOrderEntity gjSaleOrderEntity=factoryService.getSaleOrderService().queryOrderByID(id);
				BaseAccountEntity baseAccountEntity=factoryService.getBaseAccountService().queryBaseAccountByID(gjSaleOrderEntity.getAccountid());
				factoryService.getCommonMethodService().isSatisfyCreateOrderCoditionByThirdInterface(gjSaleOrderEntity, baseAccountEntity);
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("success", "可以创建订单");
				this.writeStream(jsonObject, "utf-8");
			}catch(Exception e){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("error", e.toString());
				this.writeStream(jsonObject, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("error", e.toString());
			this.writeStream(jsonObject, "utf-8");
		}
	}
}
