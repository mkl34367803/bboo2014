package com.smart.oo.action.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.smart.comm.entity.BaseAccountEntity;
import com.smart.comm.utils.DateUtil;
import com.smart.entity.User;
import com.smart.framework.base.BaseAction;
import com.smart.framework.base.Page;
import com.smart.oo.service.IBaseAccountService;

public class BaseAccountAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private IBaseAccountService baseAccountService;

	public void queryBaseAccount() {
		this.isPage=false;
		try {
			String startpage = request.getParameter("page"); // 注意这里是getParameter方法
			String rows = request.getParameter("rows");
			User user = (User) request.getSession().getAttribute(
					"SESSION_KEY_CURRENT_USER");
			String mno = null;
			if (user!=null&&user.getMert() != null) {
				mno = user.getMert().getMno(); // 每个用户对应一个航司的商户号
			}
			String userName = request.getParameter("userName");
			BaseAccountEntity baseAccountEntity = null;
			if (StringUtils.isNotBlank(mno) || StringUtils.isNotBlank(userName)) {
				baseAccountEntity = new BaseAccountEntity();
				baseAccountEntity.setMno(mno);
				baseAccountEntity.setUserName(userName);
			}
			Page page = null;
			if (StringUtils.isNotBlank(startpage)
					&& StringUtils.isNotBlank(startpage)) {
				page = new Page();
				page.setStartPage(Integer.parseInt(startpage));
				page.setPageSize(Integer.parseInt(rows));
			}
			List<BaseAccountEntity> list = baseAccountService.queryBaseAccount(page, baseAccountEntity);
			String str = JSON.toJSONString(list);
			JSONObject result = new JSONObject();
			result.put("rows", str);
			result.put("total", page.getTotalCount());
			result.put("success", "查询成功");
			this.writeStream(result, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put("error", e.toString());
			this.writeStream(result, "utf-8");
		}
	}

	public void saveAndUpdateBaseAccount() {
		this.isPage=false;
		try {
			BaseAccountEntity baseAccountEntity = new BaseAccountEntity();
			String id = request.getParameter("id");
			User user = (User) request.getSession().getAttribute(
					"SESSION_KEY_CURRENT_USER");
			String mno = null;
			if (user!=null&&user.getMert() != null) {
				mno = user.getMert().getMno(); // 每个用户对应一个航司的商户号
			}
			
			String userName = request.getParameter("userName"); //用户名,必填字段
			if(StringUtils.isEmpty(userName)){
				throw new Exception("用户名不能为空");
			}
			String bigcid = request.getParameter("bigcid");
			String secret = request.getParameter("secret");   //密码,必填字段
			if(StringUtils.isEmpty(secret)){
				throw new Exception("密码不能为空");
			}
			String appkey = request.getParameter("appkey");
			String codes = request.getParameter("codes");
			String sessions = request.getParameter("sessions");
			String url = request.getParameter("url");
			String ignoredShopNames = request.getParameter("ignoredShopNames");
			/**
			 * QUA QUNAR CTRIP TONGC 获取分销订单接口(QUNAR_O_SALE CTRIP_O_SALE TAOBAO_O_SALE
			 * KUXUN_O_SALE HBGJ_O_SALE TUNIU_O_SALE TONGC_O_SALE TONGC_N_SALE
			 * TAOBAO_N_SALE QUNAR_N_SALE) TFARE_GDS 获取政策（CP_517NA CP_BAITOUR CP_8000YI
			 * CP_51BOOK CP_19E CP_LH800 CP_AIR_B2B_EBAO CP_LOCALBSP CP_QUA）
			 */
			String acctype = request.getParameter("acctype");  //账户类型,必填字段
			if(StringUtils.isEmpty(acctype)){
				throw new Exception("账户类型不能为空");
			}
			String describe = request.getParameter("describe"); //描述,必填字段
			if(StringUtils.isEmpty(describe)){
				throw new Exception("描述不能为空");
			}
			String acckind = request.getParameter("acckind");
			String accpay = request.getParameter("accpay");
			String paypwd = request.getParameter("paypwd");
			String paytype = request.getParameter("paytype");
			String isu = request.getParameter("isu");
//			String ctime = request.getParameter("ctime");
			String apiHost = request.getParameter("apiHost");
			String apiServiceName = request.getParameter("apiServiceName");
			String apiClassName = request.getParameter("apiClassName");
			String apiMethodName = request.getParameter("apiMethodName");
			String office = request.getParameter("office");
			String sdoffice = request.getParameter("sdoffice");
			String issd = request.getParameter("issd");
			baseAccountEntity.setAcckind(stringToInteger(acckind));
			baseAccountEntity.setAccpay(accpay);
			baseAccountEntity.setAcctype(acctype);
			baseAccountEntity.setApiClassName(apiClassName);
			baseAccountEntity.setApiHost(apiHost);
			baseAccountEntity.setApiMethodName(apiMethodName);
			baseAccountEntity.setOffice(office);
			baseAccountEntity.setApiServiceName(apiServiceName);
			baseAccountEntity.setAppkey(appkey);
			baseAccountEntity.setBigcid(bigcid);
			baseAccountEntity.setCodes(codes);
			baseAccountEntity.setCtime(DateUtil.DateToStr(new Date()));
			baseAccountEntity.setDescribe(describe);
			baseAccountEntity.setIgnoredShopNames(ignoredShopNames);
			baseAccountEntity.setIsu(stringToInteger(isu));
			baseAccountEntity.setMno(mno);
			baseAccountEntity.setPaypwd(paypwd);
			baseAccountEntity.setPaytype(paytype);
			baseAccountEntity.setSecret(secret);
			baseAccountEntity.setSessions(sessions);
			baseAccountEntity.setUrl(url);
			baseAccountEntity.setUserName(userName);
			baseAccountEntity.setSdoffice(sdoffice);
			baseAccountEntity.setIssd(stringToInteger(issd));
			if (StringUtils.isNotEmpty(id)) {
				baseAccountEntity.setId(stringToInteger(id));
			}
			Integer integer = baseAccountService
					.saveAndUpdateBaseAccount(baseAccountEntity);
			JSONObject jsonObject = new JSONObject();
			if (integer > 0) {
				jsonObject.put("success", "true");
			} else {
				jsonObject.put("errorMsg", "保存或者更新失败");
				jsonObject.put("error", "保存或者更新失败");
			}
			this.writeStream(jsonObject, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("errorMsg", e.toString());
			jsonObject.put("error", e.toString());
			this.writeStream(jsonObject, "utf-8");
		}
	}

	private Integer stringToInteger(String string) {
		return Integer.parseInt(string);
	}
	public void deleteBaseAccount(){
		try {
			String id = request.getParameter("id"); // 注意这里是getParameter方法
			if(StringUtils.isEmpty(id)){
				JSONObject result = new JSONObject();
				result.put("error", "id不能为空");
				this.writeStream(result, "utf-8");
			}
			baseAccountService.deleteBaseAccountByID(Integer.parseInt(id));
			JSONObject result = new JSONObject();
			result.put("success", "删除成功");
			this.writeStream(result, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put("error", e.toString());
			this.writeStream(result, "utf-8");
		}
	}
}
