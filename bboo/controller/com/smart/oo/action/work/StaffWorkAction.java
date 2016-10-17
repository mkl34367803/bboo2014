package com.smart.oo.action.work;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.smart.comm.entity.StaffWorkEntity;
import com.smart.entity.User;
import com.smart.framework.base.BaseAction;
import com.smart.framework.base.Page;
import com.smart.framework.security.SecurityContext;
import com.smart.oo.action.syslog.ProcessLogAction;
import com.smart.oo.from.StaffWorkVo;
import com.smart.oo.service.imp.FactoryServiceImpl;
import com.smart.timer.base.DateFormat;
import com.smart.utils.StringUtils;

public class StaffWorkAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4535072155752713232L;
	
	@Autowired
	private FactoryServiceImpl factoryService;
	
	@Resource(name = "ProcessLogAction")
	private ProcessLogAction processLog;
	
	public void queryByPage() {
		try {
			String startPage = request.getParameter("page");
			String rows = request.getParameter("rows");
			
			String name = request.getParameter("name");
			String flightClass = request.getParameter("flightClass");
			String workType = request.getParameter("workType");
			StaffWorkVo vo = new StaffWorkVo();
			vo.setName(name);
			vo.setFlightClass(flightClass);
			vo.setWorkType(workType);
			
			Page page = null;
			if (StringUtils.isNotEmpty(startPage) && StringUtils.isNotEmpty(rows)) {
				page = new Page();
				page.setStartPage(Integer.parseInt(startPage));
				page.setPageSize(Integer.parseInt(rows));
			}
			List<StaffWorkVo> voList = this.factoryService.getStaffWorkService().queryByPage(vo, page);
			
			JSONObject result = new JSONObject();
			String list = "[]";
			if (null != voList && voList.size() > 0) {
				list = JSON.toJSONString(voList);
			}
			result.put("list", list);
			result.put("total", page.getTotalCount());
			result.put(SUCCESS, "查询成功");
			this.writeStream(result, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put(ERROR, e.toString());
			this.writeStream(result, "utf-8");
		}
	}

	public void saveOrUpdate() {
		try {
			String id = request.getParameter("id");
			String fkUserId = request.getParameter("fkUserId");
			String wtimeStart = request.getParameter("wtimeStart");
			String wtimeEnd = request.getParameter("wtimeEnd");
			String weeks = request.getParameter("weeks");
			String flightClass = request.getParameter("flightClass");
			String workType = request.getParameter("workType");
			
			JSONObject result = new JSONObject();
			if (StringUtils.isEmpty(fkUserId)) {
				result.put(ERROR, "用户ID为空");
				this.writeStream(result, "utf-8");
				return;
			}
			
			StaffWorkEntity entity = new StaffWorkEntity();
			entity.setFkUserId(Integer.parseInt(fkUserId));
			entity.setWtimeStart(wtimeStart);
			entity.setWtimeEnd(wtimeEnd);
			entity.setWeeks(weeks);
			entity.setFlightClass(flightClass);
			entity.setWorkType(workType);
			
			User user = SecurityContext.getUser();
			entity.setMno(user.getMert().getMno());
			
			if (StringUtils.isNotEmpty(id)) {// 修改
				entity.setId(Integer.parseInt(id));
				this.factoryService.getStaffWorkService().updateEntity(entity);
				result.put(SUCCESS, "修改成功");
			} else {// 保存
				entity.setCreateTime(DateFormat.getStandardSysdate());
				this.factoryService.getStaffWorkService().saveEntity(entity);
				result.put(SUCCESS, "保存成功");
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
				this.factoryService.getStaffWorkService().deleteEntity(Integer.parseInt(id));
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
	
	// 签到
	public void signIn() {
		try {
			String signIn = request.getParameter("signIn");
			
			JSONObject result = new JSONObject();
			if (StringUtils.isNotEmpty(signIn)) {
				StaffWorkEntity entity = new StaffWorkEntity();
				User user = SecurityContext.getUser();
				entity.setMno(user.getMert().getMno());
				entity.setFkUserId(user.getUserId());
				entity.setSignIn(Integer.parseInt(signIn));
				String signResult = this.factoryService.getStaffWorkService().signIn(entity);
				if (SUCCESS.equals(signResult)) {
					result.put(SUCCESS, "签到成功");
					processLog.addProcessLog(user, null, "3", getSignInCh(entity.getSignIn()));
				} else if (ERROR.equals(signResult)) {
					result.put(ERROR, "签到失败");
				} else {
					result.put(ERROR, signResult);
				}
			} else {
				result.put(ERROR, "签到类型为空");
			}
			this.writeStream(result, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put(ERROR, e.toString());
			this.writeStream(result, "utf-8");
		}
	}
	
	public void queryListWithName() {
		try {
			String id = request.getParameter("id");
			String flightClass = request.getParameter("flightClass");
			String workType = request.getParameter("workType");
			String name = request.getParameter("name");
			
			User user = SecurityContext.getUser();
			StaffWorkVo vo = new StaffWorkVo();
			vo.setMno(user.getMert().getMno());
			if (StringUtils.isNotEmpty(id)) {
				vo.setId(Integer.parseInt(id));
			}
			vo.setFlightClass(flightClass);
			vo.setWorkType(workType);
			vo.setName(name);
			
			List<StaffWorkVo> swList = this.factoryService.getStaffWorkService().queryList(vo);
			JSONObject result = new JSONObject();
			String list = "[]";
			if (null != swList && swList.size() > 0) {
				list = JSON.toJSONString(swList);
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
	
	// 查询签到状态
	public void querySignIn() {
		try {
			String signIn = this.factoryService.getStaffWorkService().querySignIn();
			JSONObject result = new JSONObject();
			
			if (null == signIn) {
				
			} else if (signIn.length() > 1) {
				result.put(ERROR, signIn);
			} else {
				result.put("signIn",signIn);
			}
			result.put(SUCCESS, "查询成功");
			this.writeStream(result, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put(ERROR, e.toString());
			this.writeStream(result, "utf-8");
		}
	}
	
	//签到 1上班 2下班 3暂停
	private String getSignInCh(Integer signIn) throws Exception {
		switch (signIn) {
		case 1:
			return "签到";
		case 2:
			return "下班";
		case 3:
			return "暂停";
		default:
				return signIn+"";
		}
	}
}
