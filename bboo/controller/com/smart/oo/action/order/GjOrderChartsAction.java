package com.smart.oo.action.order;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.smart.comm.utils.StringUtilsc;
import com.smart.framework.base.BaseAction;
import com.smart.framework.security.SecurityContext;
import com.smart.oo.action.syslog.ProcessLogAction;
import com.smart.oo.from.OrderChartsVo;
import com.smart.oo.service.imp.FactoryServiceImpl;
import com.smart.utils.StringUtils;

public class GjOrderChartsAction extends BaseAction {

	private static final long serialVersionUID = 7045573912777553660L;
	@Autowired
	private FactoryServiceImpl factoryService;
	
	@Resource(name = "ProcessLogAction")
	private ProcessLogAction processLog;

	// 统计
	public void getStatisticCharts(){
		try {
			String startDate=request.getParameter("startDate");
			String endDate=request.getParameter("endDate");
			String chartType = request.getParameter("chartType");
			String type = request.getParameter("type");
			String flightClass = request.getParameter("flightClass");
			String showType = request.getParameter("showType");
			
			JSONObject jsonObject=new JSONObject();
			if (StringUtils.isEmpty(type)) {
				jsonObject.put(ERROR, "类型为空");
				this.writeStream(jsonObject, "utf-8");
				return;
			}
			
			String[] showTypeArr = null;
			if (StringUtils.isNotEmpty(showType)) {
				showTypeArr = showType.split(",");
			}
			
			boolean timeFlag = false;
			if (showTypeArr != null && StringUtilsc.isContain(showTypeArr, "day")) {
				timeFlag = true;
			}
			
			if ("createTime".equals(chartType) || timeFlag) {
				if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
					if (!StringUtilsc.checkIntervalDate(startDate, endDate, 15)) {
						jsonObject.put(ERROR, "出票日期不能超过15天");
						this.writeStream(jsonObject, "utf-8");
						return;
					}
				} else {
					jsonObject.put(ERROR, "请输入出票日期");
					this.writeStream(jsonObject, "utf-8");
					return;
				}
			}
			
			OrderChartsVo vo = new OrderChartsVo();
			vo.setStartCreateDate(startDate);
			vo.setEndCreateDate(endDate);
			vo.setFlightClass(flightClass);
			
			if (StringUtils.isNotEmpty(chartType)) {
				String list = this.factoryService.getOrderStatisticService().getOrderChartData(vo, type, chartType ,showTypeArr);;
				jsonObject.put("list", list);
				jsonObject.put(SUCCESS, "查询成功");
				processLog.addProcessLog(SecurityContext.getUser(), null, "2", "查看统计图表");
			} else {
				jsonObject.put(ERROR, "统计类型为空");
			}
			this.writeStream(jsonObject, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("error", "服务器出错了："+e.toString());
			this.writeStream(jsonObject, "utf-8");
		}
	}
	
	
		
}