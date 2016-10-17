package com.smart.oo.service;

import java.util.List;

import com.smart.base.exception.SqlException;
import com.smart.framework.base.Page;
import com.smart.oo.from.OrderReportVo;

public interface IOrderReportService {

	/**
	 *  查询
	 * 
	 * @param page
	 * @return X
	 * @throws SqlException
	 */
	List<OrderReportVo> getSaleOrderList(OrderReportVo from, OrderReportVo voDate) throws Exception;
	
	/**
	 * 分页查询
	 * @param from
	 * @param voDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<OrderReportVo> getSaleOrderListByPage(OrderReportVo from, OrderReportVo voDate, Page page) throws Exception;
	
	/**
	 * 下载报表
	 * @param from
	 * @param voDate
	 * @return
	 * @throws Exception
	 */
	String downloadSaleOrder(OrderReportVo from, OrderReportVo voDate) throws Exception;
	
	/**
	 * 分页下载
	 * @param from
	 * @param voDate
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	String downloadByPage(OrderReportVo from, OrderReportVo voDate, Integer pageSize) throws Exception;

}
