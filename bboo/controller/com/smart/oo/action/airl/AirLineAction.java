package com.smart.oo.action.airl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import com.smart.comm.entity.AirLineEntity;
import com.smart.comm.utils.OOLogUtil;
import com.smart.framework.base.BaseAction;
import com.smart.framework.base.Page;
import com.smart.oo.service.imp.FactoryServiceImpl;
import com.smart.timer.base.DateFormat;
import com.smart.utils.StringUtils;

public class AirLineAction extends BaseAction {
	@Autowired
	private FactoryServiceImpl factoryService;
	/**
	 * 查询
	 */
	public void queryByPage() {
		try {
			String startPage = request.getParameter("page");
			String rows = request.getParameter("rows");
			String air=request.getParameter("air");
			String arr=request.getParameter("arr");
			String ct=request.getParameter("ct");
			String dep=request.getParameter("dep");
			String inat=request.getParameter("inat");
			String isu=request.getParameter("isu");
			Page page = null;
			if (StringUtils.isNotEmpty(startPage) && StringUtils.isNotEmpty(rows)) {
				page = new Page();
				page.setStartPage(Integer.parseInt(startPage));
				page.setPageSize(Integer.parseInt(rows));
			}
			AirLineEntity entity = new AirLineEntity();
			entity.setAir(air);
			entity.setArr(arr);
			entity.setCt(ct);
			entity.setDep(dep);
			if (StringUtils.isNotEmpty(inat)) {
				entity.setInat(Integer.parseInt(inat));
			}
			if (StringUtils.isNotEmpty(isu)) {
				entity.setIsu(Integer.parseInt(isu));
			}
			List<AirLineEntity> airLineList=this.factoryService.getAirLineService().queryByPage(entity, page);
			/*转json*/
			JSONObject result = new JSONObject();
			if (airLineList != null && airLineList.size() > 0) {
				String list = JSON.toJSONString(airLineList);
				result.put("list", list);
			} else {
				result.put("list", "[]");
			}
			result.put(SUCCESS, "查询成功");
			result.put("total", page.getTotalCount());
			this.writeStream(result, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put(ERROR, e.toString());
			this.writeStream(result, "utf-8");
		}
	}
	/**
	 * 修改添加
	 */
	public void saveOrUpdate() {
		try {
			
			String id=request.getParameter("id");
			String startPage = request.getParameter("page");
			String rows = request.getParameter("rows");
			String air=request.getParameter("air");
			String arr=request.getParameter("arr");
			String dep=request.getParameter("dep");
			String inat=request.getParameter("inat");
			String isu=request.getParameter("isu");
			Page page = null;
			if (StringUtils.isNotEmpty(startPage) && StringUtils.isNotEmpty(rows)) {
				page = new Page();
				page.setStartPage(Integer.parseInt(startPage));
				page.setPageSize(Integer.parseInt(rows));
			}
			AirLineEntity entity = new AirLineEntity();
			entity.setAir(air);
			entity.setArr(arr);
			entity.setDep(dep);
			if (StringUtils.isNotEmpty(inat)) {
				entity.setInat(Integer.parseInt(inat));
			}
			if (StringUtils.isNotEmpty(isu)) {
				entity.setIsu(Integer.parseInt(isu));
			}if (StringUtils.isNotEmpty(id)) {
				entity.setId(Integer.parseInt(id));
			}
			JSONObject result = new JSONObject();
			if (StringUtils.isNotEmpty(id)) {
				this.factoryService.getAirLineService().updateAirLine(entity);
				result.put(SUCCESS, "修改成功");
				info("AirLine修改成功, id 为"+id);
			} else {
				entity.setCt(DateFormat.getStandardSysdate());//系统时间
				this.factoryService.getAirLineService().saveAirLine(entity);
				result.put(SUCCESS, "添加成功");
				info("AirLine添加成功");
			}
			this.writeStream(result, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put(ERROR, e.toString());
			this.writeStream(result, "utf-8");
		}
	}
	/**
	 * 删除
	 */
	public void delete() {
		try {
			String id = request.getParameter("id");
			JSONObject result = new JSONObject();
			if (StringUtils.isNotEmpty(id)) {
				this.factoryService.getAirLineService().deleteAirLine(Integer.parseInt(id));
				result.put(SUCCESS, "删除成功");
				info("AirLine删除成功，id为："+id);
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
	private void info(String msg) {
		OOLogUtil.info(msg, AirLineAction.class, null);
	}
}
