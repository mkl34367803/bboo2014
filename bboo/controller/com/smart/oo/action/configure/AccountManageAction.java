package com.smart.oo.action.configure;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.smart.comm.entity.AccountManageEntity;
import com.smart.entity.User;
import com.smart.framework.base.BaseAction;
import com.smart.framework.base.Page;
import com.smart.framework.security.SecurityContext;
import com.smart.oo.service.imp.FactoryServiceImpl;
import com.smart.timer.base.DateFormat;
import com.smart.utils.StringUtils;

public class AccountManageAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1566505440205449407L;
	@Autowired
	private FactoryServiceImpl factoryService;
	
	// 分页查询
	public void queryByPage() {
		try {
			String startPage = request.getParameter("startPage");
			String pageSize = request.getParameter("pageSize");
			
			String accountType = request.getParameter("accountType");
			String account = request.getParameter("account");
			String aircode = request.getParameter("aircode");
			String isu = request.getParameter("isu");
			
			Page page = null;
			JSONObject result = new JSONObject();
			if (StringUtils.isNotEmpty(startPage) && StringUtils.isNotEmpty(pageSize)) {
				page = new Page();
				page.setStartPage(Integer.parseInt(startPage));
				page.setPageSize(Integer.parseInt(pageSize));
			} else {
				result.put(ERROR, "页码为空");
				this.writeStream(result, "utf-8");
				return;
			}
			
			AccountManageEntity entity = new AccountManageEntity();
			entity.setAccountType(accountType);
			entity.setAccount(account);
			entity.setAircode(aircode);
			if (StringUtils.isNotEmpty(isu)) {
				entity.setIsu(Integer.parseInt(isu));
			}
			User user = SecurityContext.getUser();
			String mno = user.getMert().getMno();
			entity.setMno(mno);
			List<AccountManageEntity> accountManageEntities = this.factoryService.getAccountManageService().findByPage(entity, page);
			String list = null;
			if (null != accountManageEntities && accountManageEntities.size() > 0) {
				list = JSON.toJSONString(accountManageEntities);
			} else {
				list = "[]";
			}
			result.put(SUCCESS, "查询成功");
			result.put("list", list);
			result.put("total", page.getTotalCount());
			this.writeStream(result, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put(ERROR, e.toString());
			this.writeStream(result, "utf-8");
		}
	}
	
	// 查询
	public void queryList() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put(ERROR, e.toString());
			this.writeStream(result, "utf-8");
		}
	}
	
	// 保存和修改
	public void saveOrUpdate() {
		try {
			String id = request.getParameter("id");
			String accountType = request.getParameter("accountType");
			String val = request.getParameter("val");
			String valCh = request.getParameter("valCh");
			String account = request.getParameter("account");
			String aircode = request.getParameter("aircode");
			String isu = request.getParameter("isu");
			String pass = request.getParameter("pass");
			
			AccountManageEntity entity = new AccountManageEntity();
			entity.setAccountType(accountType);
			entity.setVal(val);
			entity.setValCh(valCh);
			entity.setAccount(account);
			entity.setAircode(aircode.toUpperCase());
			entity.setIsu(Integer.parseInt(isu));
			entity.setPassword(pass);
			
			User user = SecurityContext.getUser();
			String mno = user.getMert().getMno();
			entity.setMno(mno);
			
			JSONObject result = new JSONObject();
			if (StringUtils.isNotEmpty(id)) {// 修改
				entity.setId(Integer.parseInt(id));
				this.factoryService.getAccountManageService().updateAccountManage(entity);
				result.put(SUCCESS, "修改成功");
			} else {// 添加
				entity.setMno(mno);
				entity.setCreateTime(DateFormat.getStandardSysdate());
				this.factoryService.getAccountManageService().saveAccountManage(entity);
				result.put(SUCCESS, "添加成功");
			}
			this.writeStream(result, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put(ERROR, e.toString());
			this.writeStream(result, "utf-8");
		}
	}
	
	// 删除
	public void delete() {
		try {
			String id = request.getParameter("id");
			JSONObject result = new JSONObject();
			if (StringUtils.isNotEmpty(id)) {
				User user = SecurityContext.getUser();
				String mno = user.getMert().getMno();
				this.factoryService.getAccountManageService().deleteById(Integer.parseInt(id), mno);
				result.put(SUCCESS, "删除成功");
			} else {
				result.put(ERROR, "id为空");
			}
			this.writeStream(result, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put(ERROR, e.toString());
			this.writeStream(result, "utf-8");
		}
	}
}
