package com.smart.oo.service;

import com.smart.comm.entity.BaseAccountEntity;
import com.smart.comm.entity.GjSaleOrderEntity;
import com.smart.oo.domain.res.GetPnrInfoResult;
import com.smart.oo.domain.res.SelectOrderDetailResult;

public interface ICommonMethodService {
	/**
	 * 获得订单状态中文,从数字转化为中文
	 * @param slfStatus 订单状态数字
	 * @return
	 */
	public String getSlfStatusCH(String slfStatus) throws Exception;
	/**
	 * 获取订单信息，如果我们自己系统有订单信息，从我们自己系统获取，否则从第三方接口获取
	 * @param orderNo 订单号，有可能是我们自己系统的订单号，也有可能是第三方的分销商的订单号
	 * @param accountId 账户表BaseAccount里面的id号（如果上面的是我们系统的订单号，这个可以不需要）
	 * @return
	 * @throws Exception
	 */
	public SelectOrderDetailResult getOrderDetail(String orderNo,String accountId,String pnrContent) throws Exception;
	/**
	 * 获取第三方接口订单信息（这个第三方主要指的是分销商的订单信息，然后自己组装成自己想要的model）
	 * @param orderNo 分销商系统中的订单号
	 * @param accountId 账户表BaseAccount里面的id号
	 * @return
	 * @throws Exception
	 */
	public SelectOrderDetailResult getOrderDetailByThirdInterface(String orderNo,String accountId) throws Exception;
	/**
	 * 判断票号是否是有效票号<br/>
	 * 注：票号有两种格式***-**********；*************；<br/>
	 * 第一种，带横杠的13位数字，第二种，不带横杠的13位数字。<br/>
	 * 进入系统后，将横杠替换成空白。然后验证是否是13位数字，是13位数字，返回true，否则返回false<br/>
	 * @param ticketNumber
	 * @return
	 * @throws Exception
	 */
	public boolean isTicketNumberValid(String ticketNumber) throws Exception;
	
	/**
	 * 用于判断订单创单的时候，分销商的订单状态是否可以满足创单条件。<br/>
	 * 如果不满足，直接抛异常。<br/>
	 * @param gjSaleOrderEntity
	 * @param baseAccountEntity
	 * @throws Exception
	 */
	public void isSatisfyCreateOrderCoditionByThirdInterface(GjSaleOrderEntity gjSaleOrderEntity,
			BaseAccountEntity baseAccountEntity) throws Exception;
	
	/**
	 * rt结果是否满足订单系统的乘客信息
	 * @param rtrs
	 * @param gjSaleOrderEntity
	 * @return
	 * @throws Exception
	 */
	public boolean isPnrMatchPassenger(GetPnrInfoResult rtrs,GjSaleOrderEntity gjSaleOrderEntity) throws Exception;
}
