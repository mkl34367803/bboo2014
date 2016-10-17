package com.smart.oo.service.trigger;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.comm.entity.BaseAccountEntity;
import com.smart.comm.entity.GjSaleOrderEntity;
import com.smart.comm.entity.GjSalePassengerEntity;
import com.smart.comm.utils.BBOOConstants;
import com.smart.oo.domain.res.GjOrderGetDetailResult;
import com.smart.oo.domain.res.GjSaleOrderVO;
import com.smart.oo.domain.res.GjSalePassengerVO;
import com.smart.oo.service.imp.FactoryServiceImpl;
import com.smart.oo.vo.OrderAndPassengerParamVo;
import com.smart.utils.DateUtils;

@Service("BBOOModifyOrderStateTrigger")
public class ModifyOrderStateTrigger {

	@Autowired
	private FactoryServiceImpl factoryService;

	/**
	 * 首先从数据库中查询订单（查询一个月的订单出来） 然后通过订单里面的orderNo去调第三方接口， 将有票号的乘机人的票号填到我们系统中，<br/>
	 * 修改订单的状态。（这里调用梅其写好的填票号的方法，实现手动和自动同步）<br/>
	 * 这个是国际订单状态的同步<br/>
	 */
	public void syncGjOrderState() {
		try {
			Date endTime = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endTime);
			calendar.add(Calendar.MONTH, -1); // 时间减一个月
			Date startTime = calendar.getTime();
			OrderAndPassengerParamVo orderAndPassengerParamVo = new OrderAndPassengerParamVo();
			orderAndPassengerParamVo.setStartCreateTime(DateUtils.formatDate(startTime, "yyyy-MM-dd HH:mm:ss"));
			orderAndPassengerParamVo.setEndCreateTime(DateUtils.formatDate(endTime, "yyyy-MM-dd HH:mm:ss"));
			orderAndPassengerParamVo.setSlfStatus(BBOOConstants.GjSaleOrderEntity_SLFSTATUS_FIVE);// 出票中的订单
			orderAndPassengerParamVo.setFlightClass(BBOOConstants.GjSaleOrderEntity_flightClass_I);// I表示国际的订单
			// List<SaleOrderAndPassengerVo> saleOrderAndPassengerVos =
			// factoryService.getSaleOrderService().queryOrderAndPassengerByCreateTime(orderAndPassengerParamVo);
			List<GjSaleOrderEntity> gjSaleOrderEntities = factoryService.getSaleOrderService().queryOrderAndPassengerByCreateTime1(
					orderAndPassengerParamVo);
			if (gjSaleOrderEntities != null && gjSaleOrderEntities.size() > 0) {
				Iterator<GjSaleOrderEntity> iterator = gjSaleOrderEntities.iterator();
				while (iterator.hasNext()) {
					GjSaleOrderEntity saleOrderAndPassengerVo = iterator.next();
					BaseAccountEntity baseAccountEntity = factoryService.getBaseAccountService().queryBaseAccountByID(
							saleOrderAndPassengerVo.getAccountid());
					if (baseAccountEntity != null) {
						// 这里获取分销商平台的订单信息
						GjOrderGetDetailResult rlt = factoryService.getAllApiService().getOrderInfoByInteface(baseAccountEntity,
								saleOrderAndPassengerVo.getOrderNo());
						if (rlt != null && rlt.getOrders() != null && rlt.getOrders().size() > 0) {
							GjSaleOrderVO gjSaleOrderVO = rlt.getOrders().get(0);
							Set<GjSalePassengerEntity> gjSalePassengerEntities = saleOrderAndPassengerVo.getPassengers();
							List<GjSalePassengerVO> gjSalePassengerVOs = gjSaleOrderVO.getPassengers();
							JSONArray jsonArray = new JSONArray();
							if (gjSalePassengerEntities != null && gjSaleOrderEntities.size() > 0 && gjSalePassengerVOs != null
									&& gjSalePassengerVOs.size() > 0) {
								for (GjSalePassengerEntity gjSalePassengerEntity : gjSalePassengerEntities) {
									for (GjSalePassengerVO gjSalePassengerVO : gjSalePassengerVOs) {
										if (gjSalePassengerVO.getName() != null
												&& gjSalePassengerVO.getName().equals(gjSalePassengerEntity.getName())) {
											if (gjSalePassengerVO.getCardNum() != null
													&& gjSalePassengerVO.getCardNum().equals(gjSalePassengerEntity.getCardNum())) {
												// 如果乘机人的姓名和证件号相等，我们认为是一个人。
												// 更新票号(首先验证票号的有效性，不是有效的票号不要更新到数据库中来)
												if (factoryService.getCommonMethodService().isTicketNumberValid(gjSalePassengerVO.getEticketNum())) {
													JSONObject jsonObject = new JSONObject();
													jsonObject.put("id", gjSalePassengerEntity.getId());
													jsonObject.put("eticketNum", gjSalePassengerVO.getEticketNum().replace("-", "").trim());// 将电子票号传过来
													jsonArray.add(jsonObject);
												}
											}
										}
									}
								}
								if (jsonArray.size() > 0) {
									// 由于我不需要更新pnrCode，所以还是将原来的pnrCode传给方法
									boolean bool = factoryService.getSaleOrderService().updateEticketAndPnr(saleOrderAndPassengerVo.getId(),
											saleOrderAndPassengerVo.getPnrCode(), jsonArray);
									if(bool&&gjSalePassengerEntities.size()==jsonArray.size()){//为什么这里要这么判断,因为有拆单的情况,会导致分销商的乘机人>订单系统中乘机人
										GjSaleOrderEntity paramVo=new GjSaleOrderEntity();
										paramVo.setId(saleOrderAndPassengerVo.getId());
										paramVo.setBackno(BBOOConstants.GjSaleOrderEntity_backno_one);
										factoryService.getSaleOrderService().updateByID(paramVo); //如果数据库中订单在第三方系统中都有票号了,那么回填票号状态改成回填成功.
									}
									if (bool) {
										factoryService.getSysLogService().saveAutoDealSysLog(saleOrderAndPassengerVo.getId(),
												baseAccountEntity.getMno(), "从分销商获取票号回填进系统成功 ");
									}
								}
							}
						}
					}
					iterator.remove();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 首先从数据库中查询订单（查询一个月的订单出来） 然后通过订单里面的orderNo去调第三方接口， 将有票号的乘机人的票号填到我们系统中，
	 * 修改订单的状态。（这里调用梅其写好的填票号的方法，实现手动和自动同步）
	 */
	public void syncGnOrderState() {
		try {
			Date endTime = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endTime);
			calendar.add(Calendar.MONTH, -1); // 时间减一个月
			Date startTime = calendar.getTime();
			OrderAndPassengerParamVo orderAndPassengerParamVo = new OrderAndPassengerParamVo();
			orderAndPassengerParamVo.setStartCreateTime(DateUtils.formatDate(startTime, "yyyy-MM-dd HH:mm:ss"));
			orderAndPassengerParamVo.setEndCreateTime(DateUtils.formatDate(endTime, "yyyy-MM-dd HH:mm:ss"));
			orderAndPassengerParamVo.setSlfStatus(BBOOConstants.GjSaleOrderEntity_SLFSTATUS_FIVE);// 出票中的订单
			orderAndPassengerParamVo.setFlightClass(BBOOConstants.GjSaleOrderEntity_flightClass_N);// I表示国际的订单
			// List<SaleOrderAndPassengerVo> saleOrderAndPassengerVos =
			// factoryService.getSaleOrderService().queryOrderAndPassengerByCreateTime(orderAndPassengerParamVo);
			List<GjSaleOrderEntity> gjSaleOrderEntities = factoryService.getSaleOrderService().queryOrderAndPassengerByCreateTime1(
					orderAndPassengerParamVo);
			if (gjSaleOrderEntities != null && gjSaleOrderEntities.size() > 0) {
				Iterator<GjSaleOrderEntity> iterator = gjSaleOrderEntities.iterator();
				while (iterator.hasNext()) {
					GjSaleOrderEntity saleOrderAndPassengerVo = iterator.next();
					BaseAccountEntity baseAccountEntity = factoryService.getBaseAccountService().queryBaseAccountByID(
							saleOrderAndPassengerVo.getAccountid());
					if (baseAccountEntity != null) {
						// 这里获取分销商平台的订单信息
						GjOrderGetDetailResult rlt = factoryService.getAllApiService().getOrderInfoByInteface(baseAccountEntity,
								saleOrderAndPassengerVo.getOrderNo());
						if (rlt != null && rlt.getOrders() != null && rlt.getOrders().size() > 0) {
							GjSaleOrderVO gjSaleOrderVO = rlt.getOrders().get(0);
							Set<GjSalePassengerEntity> gjSalePassengerEntities = saleOrderAndPassengerVo.getPassengers();
							List<GjSalePassengerVO> gjSalePassengerVOs = gjSaleOrderVO.getPassengers();
							JSONArray jsonArray = new JSONArray();
							if (gjSalePassengerEntities != null && gjSaleOrderEntities.size() > 0 && gjSalePassengerVOs != null
									&& gjSalePassengerVOs.size() > 0) {
								for (GjSalePassengerEntity gjSalePassengerEntity : gjSalePassengerEntities) {
									for (GjSalePassengerVO gjSalePassengerVO : gjSalePassengerVOs) {
										if (gjSalePassengerVO.getName() != null
												&& gjSalePassengerVO.getName().equals(gjSalePassengerEntity.getName())) {
											if (gjSalePassengerVO.getCardNum() != null
													&& gjSalePassengerVO.getCardNum().equals(gjSalePassengerEntity.getCardNum())) {
												// 如果乘机人的姓名和证件号相等，我们认为是一个人。
												// 更新票号(首先验证票号的有效性，不是有效的票号不要更新到数据库中来)
												if (factoryService.getCommonMethodService().isTicketNumberValid(gjSalePassengerVO.getEticketNum())) {
													JSONObject jsonObject = new JSONObject();
													jsonObject.put("id", gjSalePassengerEntity.getId());
													jsonObject.put("eticketNum", gjSalePassengerVO.getEticketNum().replace("-", "").trim());// 将电子票号传过来
													jsonArray.add(jsonObject);
												}
											}
										}
									}
								}
								if (jsonArray.size() > 0) {
									// 由于我不需要更新pnrCode，所以还是将原来的pnrCode传给方法
									boolean bool = factoryService.getSaleOrderService().updateEticketAndPnr(saleOrderAndPassengerVo.getId(),
											saleOrderAndPassengerVo.getPnrCode(), jsonArray);
									if(bool&&gjSalePassengerEntities.size()==jsonArray.size()){//为什么这里要这么判断,因为有拆单的情况,会导致分销商的乘机人>订单系统中乘机人
										GjSaleOrderEntity paramVo=new GjSaleOrderEntity();
										paramVo.setId(saleOrderAndPassengerVo.getId());
										paramVo.setBackno(BBOOConstants.GjSaleOrderEntity_backno_one);
										factoryService.getSaleOrderService().updateByID(paramVo); //如果数据库中订单在第三方系统中都有票号了,那么回填票号状态改成回填成功.
									}
									if (bool) {
										factoryService.getSysLogService().saveAutoDealSysLog(saleOrderAndPassengerVo.getId(),
												baseAccountEntity.getMno(), "从分销商获取票号回填进系统成功 ");
									}
								}
							}
						}
					}
					iterator.remove();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 首先从数据库中查询订单（查询一个月的订单出来） 然后通过订单里面的orderNo去调第三方接口， 将有票号的乘机人的票号填到我们系统中，
	 * 修改订单的状态。（这里调用梅其写好的填票号的方法，实现手动和自动同步）
	 */
	@Deprecated
	public void synchronizeTicketNumberTwo() {
		try {
			Date endTime = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endTime);
			calendar.add(Calendar.MONTH, -1); // 时间减一个月
			Date startTime = calendar.getTime();
			List<GjSaleOrderEntity> gjSaleOrderEntities = factoryService.getSaleOrderService().queryByCreateTime(
					DateUtils.formatDate(startTime, "yyyy-MM-dd HH:mm:ss"), DateUtils.formatDate(endTime, "yyyy-MM-dd HH:mm:ss"));
			if (gjSaleOrderEntities != null && gjSaleOrderEntities.size() > 0) {
				for (GjSaleOrderEntity gjSaleOrderEntity : gjSaleOrderEntities) {
					BaseAccountEntity baseAccountEntity = factoryService.getBaseAccountService().queryBaseAccountByID(
							gjSaleOrderEntity.getAccountid());
					if (baseAccountEntity != null) {
						Thread.sleep(3000);
						GjOrderGetDetailResult rlt = factoryService.getAllApiService().getOrderInfoByInteface(baseAccountEntity,
								gjSaleOrderEntity.getOrderNo());
						if (rlt != null && rlt.getOrders() != null && rlt.getOrders().size() > 0) {
							GjSaleOrderVO gjSaleOrderVO = rlt.getOrders().get(0);
							JSONArray jsonArray = new JSONArray();
							for (GjSalePassengerVO gjSalePassengerVO : gjSaleOrderVO.getPassengers()) {
								for (GjSalePassengerEntity gjSalePassengerEntity : gjSaleOrderEntity.getPassengers()) {
									if (gjSalePassengerVO.getName() != null && gjSalePassengerVO.getName().equals(gjSalePassengerEntity.getName())) {
										if (gjSalePassengerVO.getCardNum() != null
												&& gjSalePassengerVO.getCardNum().equals(gjSalePassengerEntity.getCardNum())) {
											// 如果乘机人的姓名和证件号相等，我们认为是一个人。
											// 更新票号
											if (StringUtils.isNotEmpty(gjSalePassengerVO.getEticketNum())) {
												JSONObject jsonObject = new JSONObject();
												jsonObject.put("id", gjSalePassengerEntity.getId());
												jsonObject.put("eticketNum", gjSalePassengerVO.getEticketNum());// 将电子票号传过来
												jsonArray.add(jsonObject);
											}
										}
									}
								}
							}
							if (jsonArray.size() > 0) {
								// 由于我不需要更新pnrCode，所以还是将原来的pnrCode传给方法
								factoryService.getSaleOrderService().updateEticketAndPnr(gjSaleOrderEntity.getId(), gjSaleOrderEntity.getPnrCode(),
										jsonArray);
							}
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
