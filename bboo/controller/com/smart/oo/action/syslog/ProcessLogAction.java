package com.smart.oo.action.syslog;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.smart.comm.entity.ProcessLogEntity;
import com.smart.comm.utils.OOLogUtil;
import com.smart.entity.User;
import com.smart.framework.base.BaseAction;
import com.smart.framework.base.Page;
import com.smart.oo.service.imp.FactoryServiceImpl;
import com.smart.timer.base.DateFormat;
import com.smart.utils.StringUtils;
import com.smart.utils.UniqId;

@Controller("ProcessLogAction")
public class ProcessLogAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4429990660586018745L;
	@Autowired
	private FactoryServiceImpl factoryService;
	
	public void queryByPage() {
		try {
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String operator = request.getParameter("operator");
			String logType = request.getParameter("logType");
			
			String startPage = request.getParameter("startPage");
			String pageSize = request.getParameter("pageSize");
			Page page = null;
			if (StringUtils.isNotEmpty(startPage) && StringUtils.isNotEmpty(pageSize)) {
				page = new Page();
				page.setStartPage(Integer.parseInt(startPage));
				page.setPageSize(Integer.parseInt(pageSize));
			}
			
			ProcessLogEntity entity = new ProcessLogEntity();
			entity.setOperator(operator);
			entity.setLogType(logType);
			entity.setCreateTime(startDate);
			List<ProcessLogEntity> plList = this.factoryService.getProcessLogService().queryByPage(entity, endDate, page);
			JSONObject result = new JSONObject();
			String list = "";
			if (null != plList && plList.size() > 0) {
				list = JSON.toJSONString(plList);
			} else {
				list = "[]";
			}
			result.put("total", page.getTotalCount());
			result.put("list", list);
			result.put(SUCCESS, "查询成功");
			this.writeStream(result, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put(ERROR, e.toString());
			this.writeStream(result, "utf-8");
		}
	}
	
	public void queryList() {
		try {
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String operator = request.getParameter("operator");
			String logType = request.getParameter("logType");
			
			ProcessLogEntity entity = new ProcessLogEntity();
			entity.setOperator(operator);
			entity.setLogType(logType);
			entity.setCreateTime(startDate);
			List<ProcessLogEntity> plList = this.factoryService.getProcessLogService().queryList(entity, endDate);
			JSONObject result = new JSONObject();
			String list = "";
			if (null != plList && plList.size() > 0) {
				list = JSON.toJSONString(plList);
			} else {
				list = "[]";
			}
			result.put("list", list);
			result.put(SUCCESS, "查询成功");
			this.writeStream(result, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put(ERROR, e.toString());
			this.writeStream(result, "utf-8");
		}
	}
	
	public void addProcessLog(User user, String mno, String loginType, String content) throws Exception {
		ProcessLogEntity entity = new ProcessLogEntity();
		entity.setId(UniqId.getInstance().getStrId());
		if (null == mno) {
			mno = user.getMert().getMno();
		}
		entity.setMno(mno);
		entity.setLogType(loginType);
		entity.setContent(content);
		entity.setOperator(user.getName());
		entity.setCreateTime(DateFormat.getStandardSysdate());
		this.factoryService.getProcessLogService().saveEntity(entity);
		info("日志插入成功");
	}
	
	public static void info(String msg){
		OOLogUtil.info(msg, ProcessLogAction.class, null);
	}
}
