package com.smart.comm.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.smart.oo.from.OrderChartsVo;
import com.smart.utils.StringUtils;

public class ChartsUtils {

	/**
	 * 转换List<OrderChartsVo>为JSON
	 * @param voList
	 * @param showTypeArr
	 * @return
	 * @throws Exception
	 */
	public static String parseOrderCharts(List<OrderChartsVo> voList, String[] showTypeArr) throws Exception {
		if (showTypeArr != null && StringUtilsc.isContain(showTypeArr, "percent")) {
			return getPercentData(voList);
		} else if (showTypeArr != null && StringUtilsc.isContain(showTypeArr, "growthRate")) {
			return getGrowthRateData(voList);
		} else {
			return getData(voList);
		}
	}
	
	/**
	 * 获取增长率数据
	 * @param voList
	 * @return
	 * @throws Exception 
	 */
	private static String getGrowthRateData(List<OrderChartsVo> voList) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		String preData = null;
		boolean preDataIsNull = false;
		for (OrderChartsVo vo : voList) {
			if (preData != null) {
				sb.append("{");
				getCategories(vo, sb);
				preDataIsNull = false;
			} else {
				preDataIsNull = true;
			}
			if (StringUtils.isNotEmpty(vo.getTicketNum())) {
				if (preData != null) {
					sb.append("\"ticketNum\": \""+getGrowthRate(vo.getTicketNum(), preData)+"\"");
				}
				preData = vo.getTicketNum();
			}
			if (StringUtils.isNotEmpty(vo.getProfit())) {
				if (preData != null) {
					sb.append("\"profit\": \""+getGrowthRate(vo.getProfit(), preData)+"\"");
				}
				preData = vo.getProfit();
			}
			if (StringUtils.isNotEmpty(vo.getSaleroom())) {
				if (preData != null) {
					sb.append("\"saleroom\": \""+getGrowthRate(vo.getSaleroom(), preData)+"\"");
				}
				preData = vo.getSaleroom();
			}
			if (!preDataIsNull) {
				sb.append("}");
				sb.append(",");
			}
		}
		if (sb.length() > 1) {
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 获取百分比数据
	 * @param voList
	 * @return
	 * @throws Exception
	 */
	private static String getPercentData(List<OrderChartsVo> voList) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		double all = countAll(voList);
		for (OrderChartsVo vo : voList) {
			sb.append("{");
			getCategories(vo, sb);
			if (StringUtils.isNotEmpty(vo.getTicketNum())) {
				sb.append("\"ticketNum\": \""+getPercentage(vo.getTicketNum(), all)+"\"");
			}
			if (StringUtils.isNotEmpty(vo.getProfit())) {
				sb.append("\"profit\": \""+getPercentage(vo.getProfit(), all)+"\"");
			}
			if (StringUtils.isNotEmpty(vo.getSaleroom())) {
				sb.append("\"saleroom\": \""+getPercentage(vo.getSaleroom(), all)+"\"");
			}
			sb.append("}");
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * 获取数据
	 * @param voList
	 * @return
	 * @throws Exception
	 */
	private static String getData(List<OrderChartsVo> voList) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (OrderChartsVo vo : voList) {
			sb.append("{");
			getCategories(vo, sb);
			if (StringUtils.isNotEmpty(vo.getTicketNum())) {
				sb.append("\"ticketNum\": \""+vo.getTicketNum()+"\"");
			}
			if (StringUtils.isNotEmpty(vo.getProfit())) {
				sb.append("\"profit\": \""+StringUtilsc.formatDoubleStr(vo.getProfit())+"\"");
			}
			if (StringUtils.isNotEmpty(vo.getSaleroom())) {
				sb.append("\"saleroom\": \""+StringUtilsc.formatDoubleStr(vo.getSaleroom())+"\"");
			}
			sb.append("}");
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * 获取总量
	 * @param voList
	 * @return
	 */
	private static Double countAll(List<OrderChartsVo> voList) {
		double all = 0;
		for (OrderChartsVo vo : voList) {
			if (StringUtils.isNotEmpty(vo.getTicketNum())) {
				all += Double.parseDouble(vo.getTicketNum());
			}
			if (StringUtils.isNotEmpty(vo.getProfit())) {
				all += Double.parseDouble(vo.getProfit());
			}
			if (StringUtils.isNotEmpty(vo.getSaleroom())) {
				all += Double.parseDouble(vo.getSaleroom());
			}
		}
		return all;
	}
	
	/**
	 * 获取横坐标
	 * @param vo
	 * @param sb
	 */
	private static void getCategories(OrderChartsVo vo, StringBuffer sb) {
		if (StringUtils.isNotEmpty(vo.getCarrier())) {
			sb.append("\"categories\": \""+vo.getCarrier()+"\",");
		}
		if (StringUtils.isNotEmpty(vo.getPurchasePlaceCh())) {
			sb.append("\"categories\": \""+vo.getPurchasePlaceCh()+"\",");
		}
		if (StringUtils.isNotEmpty(vo.getOperator())) {
			sb.append("\"categories\": \""+vo.getOperator()+"\",");
		}
		if (StringUtils.isNotEmpty(vo.getAgeDes())) {
			sb.append("\"categories\": \""+vo.getAgeDes()+"\",");
		}
		if (StringUtils.isNotEmpty(vo.getDistributor())) {
			sb.append("\"categories\": \""+vo.getDistributor()+"\",");
		}
		if (StringUtils.isNotEmpty(vo.getShopName())) {
			sb.append("\"categories\": \""+vo.getShopName()+"\",");
		}
		if (StringUtils.isNotEmpty(vo.getCreateTime())) {
			sb.append("\"categories\": \""+vo.getCreateTime()+"\",");
		}
	}
	
	
	
	
	
	/**
	 * 转化含有时间的List<OrderChartsVo>为JSON
	 * @param voList
	 * @param showTypeArr
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static String parseOrderChartsWithCtm(List<OrderChartsVo> voList, String[] showTypeArr) throws Exception {
		List<Object> objList = parseMapList(voList, showTypeArr);
		Map<String, Map<String, String>> map = (Map<String, Map<String, String>>) objList.get(0);
		List<String> ctms = (List<String>) objList.get(1);
		Map<String, String> vMap = null;
		
		JSONObject list = new JSONObject();
		JSONArray dataArr = new JSONArray();;
		// 横坐标
		list.put("categories", JSONArray.fromObject(ctms));
		Set<String> keys = map.keySet();
		JSONObject jobj = null;
		JSONArray jsonArr = null;
		for (String key : keys) {
			jobj = new JSONObject();
			jsonArr = new JSONArray();
			vMap = map.get(key);
			Set<String> cates = vMap.keySet();
			jobj.put("name", key);
			for (String ctm : ctms) {
				if (cates.contains(ctm)) {
					jsonArr.add(StringUtilsc.formatDouble2(vMap.get(ctm)));
				} else {
					jsonArr.add(0);
				}
			}
			jobj.put("data", jsonArr);
			dataArr.add(jobj);
		}
		if (showTypeArr != null && StringUtilsc.isContain(showTypeArr, "growthRate")) {
			ctms.remove(0);
			list.put("categories", JSONArray.fromObject(ctms));
			dataArr = getGrowthRateJSONArray(dataArr);
		}
		
		list.put("series", dataArr);
		
		return list.toString();
	}
	
	/**
	 * 获取增长率
	 * @param voList
	 * @return
	 * @throws Exception
	 */
	private static JSONArray getGrowthRateJSONArray(JSONArray dataArr) throws Exception {
		JSONArray arr = new JSONArray();
		JSONObject jobj = null;
		JSONArray jarrData = null;
		JSONArray jNewData = null;
		for (Object obj : dataArr) {
			jobj = (JSONObject) obj;
			jarrData = (JSONArray) jobj.get("data");
			jNewData = new JSONArray();
			for (int i = 1; i < jarrData.size(); i++) {
				jNewData.add(getGrowthRate(jarrData.get(i).toString(), jarrData.get(i-1).toString()));
			}
			jobj.put("data", jNewData);
			arr.add(jobj);
		}
		
		return arr;
	}
	
	/**
	 * List<OrderChartsVo>转化为map并获取横坐标
	 * @param voList
	 * @param showTypeArr
	 * @return
	 * @throws Exception
	 */
	private static List<Object> parseMapList(List<OrderChartsVo> voList, String[] showTypeArr) throws Exception {
		if (showTypeArr != null && StringUtilsc.isContain(showTypeArr, "percent")) {
			return parsePercentDataList(voList);
		} else {
			return parseDataList(voList);
		}
	}
	
	/**
	 * 转化百分比数据为List
	 * @param voList
	 * @return
	 * @throws Exception 
	 */
	private static List<Object> parsePercentDataList(List<OrderChartsVo> voList) throws Exception {
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		Map<String, String> vMap = null;
		List<String> ctms = new ArrayList<String>();
		String name = null;
		Map<String, Double> allMap = countAllByName(voList);
		for (OrderChartsVo vo : voList) {
			Set<String> keys = map.keySet();
			name = getName(vo);
			double all = allMap.get(name);
			if (keys.contains(name)) {
				vMap = map.get(name);
			} else {
				vMap = new TreeMap<String, String>();
			}
			if (!ctms.contains(vo.getCreateTime())) {
				ctms.add(vo.getCreateTime());
			}
			// 获取数据
			if (StringUtils.isNotEmpty(vo.getTicketNum())) {
				vMap.put(vo.getCreateTime(), getPercentage(vo.getTicketNum(), all));
			}
			if (StringUtils.isNotEmpty(vo.getProfit())) {
				vMap.put(vo.getCreateTime(), getPercentage(vo.getProfit(), all));
			}
			if (StringUtils.isNotEmpty(vo.getSaleroom())) {
				vMap.put(vo.getCreateTime(), getPercentage(vo.getSaleroom(), all));
			}
			map.put(name, vMap);
		}
		List<Object> list = new ArrayList<Object>();
		list.add(map);
		list.add(ctms);
		return list;
	}
	
	/**
	 * 转化数据为List
	 * @param voList
	 * @return
	 */
	private static List<Object> parseDataList(List<OrderChartsVo> voList) {
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		Map<String, String> vMap = null;
		List<String> ctms = new ArrayList<String>();
		String name = null;
		for (OrderChartsVo vo : voList) {
			Set<String> keys = map.keySet();
			name = getName(vo);
			if (keys.contains(name)) {
				vMap = map.get(name);
			} else {
				vMap = new TreeMap<String, String>();
			}
			if (!ctms.contains(vo.getCreateTime())) {
				ctms.add(vo.getCreateTime());
			}
			// 获取数据
			if (StringUtils.isNotEmpty(vo.getTicketNum())) {
				vMap.put(vo.getCreateTime(), vo.getTicketNum());
			}
			if (StringUtils.isNotEmpty(vo.getProfit())) {
				vMap.put(vo.getCreateTime(), StringUtilsc.formatDoubleStr(vo.getProfit()));
			}
			if (StringUtils.isNotEmpty(vo.getSaleroom())) {
				vMap.put(vo.getCreateTime(), StringUtilsc.formatDoubleStr(vo.getSaleroom()));
			}
			map.put(name, vMap);
		}
		List<Object> list = new ArrayList<Object>();
		list.add(map);
		list.add(ctms);
		return list;
	}
	
	/**
	 * 统计各类型的数量
	 * @param voList
	 * @return
	 */
	private static Map<String, Double> countAllByName(List<OrderChartsVo> voList) {
		Map<String, Double> map = new HashMap<String, Double>();
		for (OrderChartsVo vo : voList) {
			Set<String> keys = map.keySet();
			String name = getName(vo);
			if (keys.contains(name)) {
				double d = map.get(name);
				d += getCount(vo);
				map.put(name, d);
			} else {
				double d = getCount(vo);
				map.put(name, d);
			}
		}
		return map;
	}
	
	/**
	 * 获取数量
	 * @param vo
	 * @return
	 */
	private static Double getCount(OrderChartsVo vo) {
		if (StringUtils.isNotEmpty(vo.getTicketNum())) {
			return Double.parseDouble(vo.getTicketNum());
		}
		if (StringUtils.isNotEmpty(vo.getProfit())) {
			return Double.parseDouble(vo.getProfit());
		}
		if (StringUtils.isNotEmpty(vo.getSaleroom())) {
			return Double.parseDouble(vo.getSaleroom());
		}
		return 0.0;
	}
	
	/**
	 * 获取统计类型
	 * @param vo
	 * @return
	 */
	private static String getName(OrderChartsVo vo) {
		if (StringUtils.isNotEmpty(vo.getCarrier())) {
			return vo.getCarrier();
		}
		if (StringUtils.isNotEmpty(vo.getPurchasePlaceCh())) {
			return vo.getPurchasePlaceCh();
		}
		if (StringUtils.isNotEmpty(vo.getOperator())) {
			return vo.getOperator();
		}
		if (StringUtils.isNotEmpty(vo.getAgeDes())) {
			return vo.getAgeDes();
		}
		if (StringUtils.isNotEmpty(vo.getDistributor())) {
			return vo.getDistributor();
		}
		if (StringUtils.isNotEmpty(vo.getShopName())) {
			return vo.getShopName();
		}
		return null;
	}
	
	/**
	 * 获取百分比
	 * @param str
	 * @param all
	 * @return
	 */
	private static String getPercentage(String str, double all) throws Exception {
		Double d = Double.parseDouble(str);
		double percent = 0.0;
		if (all >0) {
			percent = d / all * 100;
		} else if (all < 0) {
			//percent = 0 - d / all * 100;
			percent =  d / all * 100;
		} else {
			throw new RuntimeException("总数为0");
		}
		return StringUtilsc.formatDoubleStr(percent);
	}
	
	/**
	 * 增长率
	 * @param preData
	 * @param data
	 * @return
	 */
	public static String getGrowthRate(String data, String preData) throws Exception {
		Double preD = Double.parseDouble(preData);
		Double d = Double.parseDouble(data);
		double percent = 0.0;
		if (preD > 0) {
			percent = (d/preD - 1) * 100;
		} else if(preD < 0) {
			percent = (1 - d/preD) * 100;
		} else {
			if (d == 0) {
				percent = 0;
			} else {
			}
			throw new RuntimeException("除数为0");
		}
		return StringUtilsc.formatDoubleStr(percent);
	}
}
