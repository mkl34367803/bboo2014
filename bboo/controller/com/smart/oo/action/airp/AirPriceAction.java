package com.smart.oo.action.airp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import com.smart.comm.entity.AirPriceEntity;
import com.smart.comm.utils.OOLogUtil;
import com.smart.framework.base.BaseAction;
import com.smart.framework.base.Page;
import com.smart.oo.service.imp.FactoryServiceImpl;
import com.smart.timer.base.DateFormat;
import com.smart.utils.StringUtils;
import com.smart.utils.UniqId;

public class AirPriceAction extends BaseAction {
	private static final long serialVersionUID = -7279341421725142627L;
	@Autowired
	private FactoryServiceImpl factoryService;
	/**
	 * 查询
	 */
	public void queryByPage() {
		try {
			String startPage = request.getParameter("page");
			String rows = request.getParameter("rows");
			String carrier=request.getParameter("carrier");
			String depCode=request.getParameter("depCode");
			String arrCode=request.getParameter("arrCode");
			String baseCabin=request.getParameter("baseCabin");
			String beginDate=request.getParameter("beginDate");
			String endDate=request.getParameter("endDate");
			String cabin=request.getParameter("cabin");
			String price=request.getParameter("price");
			String basePrice=request.getParameter("basePrice");
			String discount=request.getParameter("discount");
			String predaysStart=request.getParameter("predaysStart");
			String predaysEnd=request.getParameter("predaysEnd");
			String createTime=request.getParameter("createTime");
			String keys=request.getParameter("keys");
			String IsDelete=request.getParameter("isDelete");
			Page page = null;
			if (StringUtils.isNotEmpty(startPage) && StringUtils.isNotEmpty(rows)) {
				page = new Page();
				page.setStartPage(Integer.parseInt(startPage));
				page.setPageSize(Integer.parseInt(rows));
			}
			AirPriceEntity entity = new AirPriceEntity();
			entity.setArrCode(arrCode);
			entity.setBaseCabin(baseCabin);
			entity.setBeginDate(beginDate);
			entity.setEndDate(endDate);
			entity.setCabin(cabin);
			entity.setCarrier(carrier);
			entity.setCreateTime(createTime);
			entity.setDepCode(depCode);
			entity.setKeys(keys);
			entity.setEndDate(endDate);
			//判断非空
			if (StringUtils.isNotEmpty(IsDelete)) {
				entity.setIsDelete(Integer.parseInt(IsDelete));
			}
			if (StringUtils.isNotEmpty(basePrice)) {
				entity.setBasePrice(Double.parseDouble(basePrice));
			}
			if (StringUtils.isNotEmpty(discount)) {
				entity.setDiscount(Double.parseDouble(discount));
			}
			if (StringUtils.isNotEmpty(predaysEnd)) {
				entity.setPredaysEnd(Integer.parseInt(predaysEnd));
			}
			if (StringUtils.isNotEmpty(predaysStart)) {
				entity.setPredaysStart(Integer.parseInt(predaysStart));
			}
			if (StringUtils.isNotEmpty(price)) {
				entity.setPrice(Double.parseDouble(price));
			}
			List<AirPriceEntity> airPricesList = this.factoryService.getAirPriceService().queryByPage(entity, page);
			/*转json*/
			JSONObject result = new JSONObject();
			if (airPricesList != null && airPricesList.size() > 0) {
				String list = JSON.toJSONString(airPricesList);
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
			String carrier=request.getParameter("carrier");
			String depCode=request.getParameter("depCode");
			String arrCode=request.getParameter("arrCode");
			String baseCabin=request.getParameter("baseCabin");
			String beginDate=request.getParameter("beginDate");
			String endDate=request.getParameter("endDate");
			String cabin=request.getParameter("cabin");
			String price=request.getParameter("price");
			String basePrice=request.getParameter("basePrice");
			String discount=request.getParameter("discount");
			String predaysStart=request.getParameter("predaysStart");
			String predaysEnd=request.getParameter("predaysEnd");
			String keys=request.getParameter("keys");
			String IsDelete=request.getParameter("isDelete");
			AirPriceEntity entity = new AirPriceEntity();
			entity.setArrCode(arrCode);
			entity.setBaseCabin(baseCabin);
			entity.setBeginDate(beginDate);
			entity.setEndDate(endDate);
			entity.setCabin(cabin);
			entity.setCarrier(carrier);
			entity.setDepCode(depCode);
			if (StringUtils.isNotEmpty(IsDelete)) {
				entity.setIsDelete(Integer.parseInt(IsDelete));
			}
			if (StringUtils.isNotEmpty(basePrice)) {
				entity.setBasePrice(Double.parseDouble(basePrice));
			}
			if (StringUtils.isNotEmpty(discount)) {
				entity.setDiscount(Double.parseDouble(discount));
			}
			if (StringUtils.isNotEmpty(predaysStart)) {
				entity.setPredaysStart(Integer.parseInt(predaysStart));
			}
			if (StringUtils.isNotEmpty(predaysEnd)) {
				entity.setPredaysEnd(Integer.parseInt(predaysEnd));
			}
			if (StringUtils.isNotEmpty(price)) {
				entity.setPrice(Double.parseDouble(price));
			}
			JSONObject result = new JSONObject();
			if (StringUtils.isNotEmpty(id)) {
				entity.setId(id);
				entity.setKeys(DateFormat.getStandardSysdate());
				this.factoryService.getAirPriceService().updateAirPrice(entity);
				result.put(SUCCESS, "修改成功");
				info("AirPrice修改成功, id 为"+id);
			} else {
				entity.setId(UniqId.getInstance().getStrId());
				entity.setCreateTime(DateFormat.getStandardSysdate());//创建时间
				entity.setKeys(DateFormat.getStandardSysdate());
				this.factoryService.getAirPriceService().saveAirPrice(entity);
				result.put(SUCCESS, "添加成功");
				info("AirPrice添加成功");
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
			AirPriceEntity entity = new AirPriceEntity();
			String id = request.getParameter("id");
			JSONObject result = new JSONObject();
			if (StringUtils.isNotEmpty(id)) {
				entity.setId(id);
				this.factoryService.getAirPriceService().deleteAirPrice(id);
				result.put(SUCCESS, "删除成功");
				info("AirPrice删除成功，id为："+id);
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
		OOLogUtil.info(msg, AirPriceAction.class, null);
	}
}
