package com.smart.oo.dao;

import java.util.List;

import com.smart.base.exception.SqlException;
import com.smart.framework.base.Page;
import com.smart.oo.from.OrderReportVo;

public interface IOrderReportDao {

	/**
	 * 查询
	 * @param page
	 * @return X
	 * @throws SqlException
	 */
	List<Object[]> getSaleOrderList(OrderReportVo from, OrderReportVo voDate)throws Exception;
	
	/**
	 * 分页查询
	 * @param from
	 * @param voDate
	 * @return
	 * @throws Exception
	 */
	List<Object[]> getSaleOrderListByPage(OrderReportVo from, OrderReportVo voDate, Page page)throws Exception;
}
