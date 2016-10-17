package com.smart.oo.service;

import java.util.List;

import com.smart.oo.from.OrderChartsVo;

public interface IOrderStatisticService {

	/**
	 * 统计票量图表
	 * @param vo
	 * @return
	 */
	List<OrderChartsVo> statisticTicketCharts(OrderChartsVo vo, String chartType) throws Exception;
	
	/**
	 * 统计利润图表
	 * @param vo
	 * @return
	 */
	List<OrderChartsVo> statisticProfitCharts(OrderChartsVo vo, String chartType) throws Exception;
	
	/**
	 * 统计销售额图表
	 * @param vo
	 * @param chartType
	 * @return
	 * @throws Exception
	 */
	List<OrderChartsVo> statisticSaleroomCharts(OrderChartsVo vo) throws Exception;
	
	/**
	 * 获取全部票量
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	double getAllTicket(OrderChartsVo vo) throws Exception;
	
	/**
	 * 获取全部利润
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	double getAllProfit(OrderChartsVo vo) throws Exception;
	
	/**
	 * 获取全部销售额
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	double getAllSaleroom(OrderChartsVo vo) throws Exception;
	
	/**
	 * 根据日期统计票量图表
	 * @param vo
	 * @return
	 */
	List<OrderChartsVo> statisticTicketChartsByCrt(OrderChartsVo vo, String chartType) throws Exception;
	
	/**
	 * 根据日期统计利润图表
	 * @param vo
	 * @return
	 */
	List<OrderChartsVo> statisticProfitChartsByCrt(OrderChartsVo vo, String chartType) throws Exception;
	
	/**
	 * 获取订单统计图表信息
	 * @param vo
	 * @param type
	 * @param chartType
	 * @param showTypeArr
	 * @return
	 * @throws Exception
	 */
	List<OrderChartsVo> getOrderChartList(OrderChartsVo vo, String type, String chartType, String[] showTypeArr) throws Exception;
	
	/**
	 * 根据统计类型获取利润or票量or销售额
	 * @param vo
	 * @param type
	 * @return
	 * @throws Exception
	 */
	double getAllCal(OrderChartsVo vo, String type) throws Exception;
	
	/**
	 * 根据日期统计销售额图表
	 * @param vo
	 * @param chartType
	 * @return
	 * @throws Exception
	 */
	List<OrderChartsVo> statisticSaleroomChartsByCrt(OrderChartsVo vo) throws Exception;
	
	/**
	 * 获取统计数据
	 * @param vo
	 * @param type
	 * @param chartType
	 * @param showTypeArr
	 * @return
	 * @throws Exception
	 */
	String getOrderChartData(OrderChartsVo vo, String type, String chartType, String[] showTypeArr) throws Exception;
	
}
