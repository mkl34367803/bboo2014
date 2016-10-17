package com.smart.oo.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.comm.utils.ChartsUtils;
import com.smart.comm.utils.StringUtilsc;
import com.smart.oo.dao.imp.FactoryDaoImpl;
import com.smart.oo.from.OrderChartsVo;
import com.smart.oo.service.IOrderStatisticService;
import com.smart.utils.StringUtils;

@Service("BBOOOrderStatisticServiceImpl")
public class OrderStatisticServiceImpl implements IOrderStatisticService {

	@Autowired
	private FactoryDaoImpl factoryDao;

	@Override
	public List<OrderChartsVo> statisticTicketCharts(OrderChartsVo vo, String chartType)
			throws Exception {
		List<Object[]> result = null;
		if ("carrier".equals(chartType)) {// 按航司统计
			result = factoryDao.getGjBuyOrderDao().statisticTicketByCarrier(vo);
		} else if ("purchasePlace".equals(chartType)) {// 按采购渠道统计
			result = factoryDao.getGjBuyOrderDao().statisticTicketByPurchasePlace(vo);
		} else if ("operator".equals(chartType)) {// 按出票员统计
			result = factoryDao.getGjBuyOrderDao().statisticTicketByOperator(vo);
		} else if ("ageDes".equals(chartType)) {// 按乘机人类型统计
			result = factoryDao.getGjBuyOrderDao().statisticTicketByAgeDes(vo);
		} else if ("distributor".equals(chartType)) {// 按分销渠道统计
			result = factoryDao.getGjBuyOrderDao().statisticTicketByDistributor(vo);
		} else if ("shopName".equals(chartType)) {// 按店铺名称统计
			result = factoryDao.getGjBuyOrderDao().statisticTicketByShopName(vo);
		} else if ("createTime".equals(chartType)) {// 按日期统计
			result = factoryDao.getGjBuyOrderDao().statisticTicketByCreateTime(vo);
		}
		List<OrderChartsVo> voList = null;
		if (result != null && result.size() > 0) {
			voList = new ArrayList<OrderChartsVo>();
			for (Object[] objects : result) {
				OrderChartsVo orderChartsVo = new OrderChartsVo();
				if (objects[0] != null) {
					if ("carrier".equals(chartType)) {// 按航司统计
						orderChartsVo.setCarrier(objects[0].toString());
					} else if ("purchasePlace".equals(chartType)) {// 按采购渠道统计
						orderChartsVo.setPurchasePlaceCh(objects[0].toString());
					} else if ("operator".equals(chartType)) {// 按出票员统计
						orderChartsVo.setOperator(objects[0].toString());
					} else if ("ageDes".equals(chartType)) {// 按乘机人类型统计
						orderChartsVo.setAgeDes(objects[0].toString());
					} else if ("distributor".equals(chartType)) {// 按分销渠道统计
						orderChartsVo.setDistributor(objects[0].toString());
					} else if ("shopName".equals(chartType)) {// 按店铺名称统计
						orderChartsVo.setShopName(objects[0].toString());
					} else if ("createTime".equals(chartType)) {// 按日期统计
						orderChartsVo.setCreateTime(objects[0].toString());
					}
				}
				if (objects[1] != null) {
					orderChartsVo.setTicketNum(objects[1].toString());
				}
				voList.add(orderChartsVo);
			}
		}
		return voList;
	}
	
	@Override
	public List<OrderChartsVo> statisticProfitCharts(OrderChartsVo vo, String chartType)
			throws Exception {
		List<Object[]> result = null;
		if ("carrier".equals(chartType)) {// 按航司统计
			result = factoryDao.getGjBuyOrderDao().statisticProfitByCarrier(vo);
		} else if ("purchasePlace".equals(chartType)) {// 按采购渠道统计
			result = factoryDao.getGjBuyOrderDao().statisticProfitByPurchasePlace(vo);
		} else if ("operator".equals(chartType)) {// 按出票员统计
			result = factoryDao.getGjBuyOrderDao().statisticProfitByOperator(vo);
		} else if ("distributor".equals(chartType)) {// 按分销渠道统计
			result = factoryDao.getGjBuyOrderDao().statisticProfitByDistributor(vo);
		} else if ("shopName".equals(chartType)) {// 按店铺名称统计
			result = factoryDao.getGjBuyOrderDao().statisticProfitByShopName(vo);
		} else if ("createTime".equals(chartType)) {// 按日期统计
			result = factoryDao.getGjBuyOrderDao().statisticProfitByCreateTime(vo);
		}
		List<OrderChartsVo> voList = null;
		if (result != null && result.size() > 0) {
			voList = new ArrayList<OrderChartsVo>();
			for (Object[] objects : result) {
				OrderChartsVo orderChartsVo = new OrderChartsVo();
				if (objects[0] != null) {
					if ("carrier".equals(chartType)) {// 按航司统计
						orderChartsVo.setCarrier(objects[0].toString());
					} else if ("purchasePlace".equals(chartType)) {// 按采购渠道统计
						orderChartsVo.setPurchasePlaceCh(objects[0].toString());
					} else if ("operator".equals(chartType)) {// 按出票员统计
						orderChartsVo.setOperator(objects[0].toString());
					} else if ("distributor".equals(chartType)) {// 按分销渠道统计
						orderChartsVo.setDistributor(objects[0].toString());
					} else if ("shopName".equals(chartType)) {// 按店铺名称统计
						orderChartsVo.setShopName(objects[0].toString());
					}else if ("createTime".equals(chartType)) {// 按日期统计
						orderChartsVo.setCreateTime(objects[0].toString());
					}
				}
				if (objects[1] != null) {
					orderChartsVo.setProfit(objects[1].toString());
				}
				voList.add(orderChartsVo);
			}
		}
		return voList;
	}

	@Override
	public List<OrderChartsVo> statisticSaleroomCharts(OrderChartsVo vo) throws Exception {
		List<Object[]> result = factoryDao.getGjBuyOrderDao().statisticSaleroomByCarrier(vo);
		List<OrderChartsVo> voList = null;
		if (result != null && result.size() > 0) {
			voList = new ArrayList<OrderChartsVo>();
			for (Object[] objects : result) {
				OrderChartsVo orderChartsVo = new OrderChartsVo();
				if (objects[0] != null) {
					orderChartsVo.setCarrier(objects[0].toString());
				}
				if (objects[1] != null) {
					orderChartsVo.setSaleroom(objects[1].toString());
				}
				voList.add(orderChartsVo);
			}
		}
		return voList;
	}
	
	@Override
	public double getAllTicket(OrderChartsVo vo) throws Exception {
		List<Object[]> result = this.factoryDao.getGjBuyOrderDao().statisticTicket(vo);
		double d = 0.0;
		if (result != null && result.size() > 0) {
			String s = result.toString();
			String sd = s.substring(1, s.length()-1);
			d = parseDouble(sd);
		}
		return d;
	}
	
	@Override
	public double getAllProfit(OrderChartsVo vo) throws Exception {
		List<Object[]> result = this.factoryDao.getGjBuyOrderDao().statisticProfit(vo);
		double d = 0.0;
		if (result != null && result.size() > 0) {
			String s = result.toString();
			String sd = s.substring(1, s.length()-1);
			d = parseDouble(sd);
		}
		return d;
	}
	
	@Override
	public double getAllSaleroom(OrderChartsVo vo) throws Exception {
		List<Object[]> result = this.factoryDao.getGjBuyOrderDao().statisticSaleroom(vo);
		double d = 0.0;
		if (result != null && result.size() > 0) {
			String s = result.toString();
			String sd = s.substring(1, s.length()-1);
			d = parseDouble(sd);
		}
		return d;
	}
	
	private double parseDouble(Object obj) {
		return obj != null&&StringUtils.isFigure(obj.toString()) ? Double.parseDouble(obj.toString()) : 0.0;
	}

	@Override
	public List<OrderChartsVo> statisticTicketChartsByCrt(OrderChartsVo vo,
			String chartType) throws Exception {
		List<Object[]> result = null;
		if ("carrier".equals(chartType)) {// 按航司统计
			result = factoryDao.getGjBuyOrderDao().statisticTicketByCarrierAndCrt(vo);
		} else if ("purchasePlace".equals(chartType)) {// 按采购渠道统计
			result = factoryDao.getGjBuyOrderDao().statisticTicketByPurchasePlaceAndCrt(vo);
		} else if ("operator".equals(chartType)) {// 按出票员统计
			result = factoryDao.getGjBuyOrderDao().statisticTicketByOperatorAndCrt(vo);
		} else if ("ageDes".equals(chartType)) {// 按乘机人类型统计
			result = factoryDao.getGjBuyOrderDao().statisticTicketByAgeDesAndCrt(vo);
		} else if ("distributor".equals(chartType)) {// 按分销渠道统计
			result = factoryDao.getGjBuyOrderDao().statisticTicketByDistributorAndCrt(vo);
		} else if ("shopName".equals(chartType)) {// 按店铺名称统计
			result = factoryDao.getGjBuyOrderDao().statisticTicketByShopNameAndCrt(vo);
		}
		List<OrderChartsVo> voList = null;
		if (result != null && result.size() > 0) {
			voList = new ArrayList<OrderChartsVo>();
			for (Object[] objects : result) {
				OrderChartsVo orderChartsVo = new OrderChartsVo();
				if (objects[0] != null) {
					if ("carrier".equals(chartType)) {// 按航司统计
						orderChartsVo.setCarrier(objects[0].toString());
					} else if ("purchasePlace".equals(chartType)) {// 按采购渠道统计
						orderChartsVo.setPurchasePlaceCh(objects[0].toString());
					} else if ("operator".equals(chartType)) {// 按出票员统计
						orderChartsVo.setOperator(objects[0].toString());
					} else if ("ageDes".equals(chartType)) {// 按乘机人类型统计
						orderChartsVo.setAgeDes(objects[0].toString());
					} else if ("distributor".equals(chartType)) {// 按分销渠道统计
						orderChartsVo.setDistributor(objects[0].toString());
					} else if ("shopName".equals(chartType)) {// 按店铺名称统计
						orderChartsVo.setShopName(objects[0].toString());
					}
				}
				if (objects[1] != null) {
					orderChartsVo.setCreateTime(objects[1].toString());
				}
				if (objects[2] != null) {
					orderChartsVo.setTicketNum(objects[2].toString());
				}
				voList.add(orderChartsVo);
			}
		}
		return voList;
	}

	@Override
	public List<OrderChartsVo> statisticProfitChartsByCrt(OrderChartsVo vo,
			String chartType) throws Exception {
		List<Object[]> result = null;
		if ("carrier".equals(chartType)) {// 按航司统计
			result = factoryDao.getGjBuyOrderDao().statisticProfitByCarrierAndCrt(vo);
		} else if ("purchasePlace".equals(chartType)) {// 按采购渠道统计
			result = factoryDao.getGjBuyOrderDao().statisticProfitByPurchasePlaceAndCrt(vo);
		} else if ("operator".equals(chartType)) {// 按出票员统计
			result = factoryDao.getGjBuyOrderDao().statisticProfitByOperatorAndCrt(vo);
		} else if ("distributor".equals(chartType)) {// 按分销渠道统计
			result = factoryDao.getGjBuyOrderDao().statisticProfitByDistributorAndCrt(vo);
		} else if ("shopName".equals(chartType)) {// 按店铺名称统计
			result = factoryDao.getGjBuyOrderDao().statisticProfitByShopNameAndCrt(vo);
		}
		List<OrderChartsVo> voList = null;
		if (result != null && result.size() > 0) {
			voList = new ArrayList<OrderChartsVo>();
			for (Object[] objects : result) {
				OrderChartsVo orderChartsVo = new OrderChartsVo();
				if (objects[0] != null) {
					if ("carrier".equals(chartType)) {// 按航司统计
						orderChartsVo.setCarrier(objects[0].toString());
					} else if ("purchasePlace".equals(chartType)) {// 按采购渠道统计
						orderChartsVo.setPurchasePlaceCh(objects[0].toString());
					} else if ("operator".equals(chartType)) {// 按出票员统计
						orderChartsVo.setOperator(objects[0].toString());
					} else if ("ageDes".equals(chartType)) {// 按乘机人类型统计
						orderChartsVo.setAgeDes(objects[0].toString());
					} else if ("distributor".equals(chartType)) {// 按分销渠道统计
						orderChartsVo.setDistributor(objects[0].toString());
					} else if ("shopName".equals(chartType)) {// 按店铺名称统计
						orderChartsVo.setShopName(objects[0].toString());
					}
				}
				if (objects[1] != null) {
					orderChartsVo.setCreateTime(objects[1].toString());
				}
				if (objects[2] != null) {
					orderChartsVo.setProfit(objects[2].toString());
				}
				voList.add(orderChartsVo);
			}
		}
		return voList;
	}

	@Override
	public List<OrderChartsVo> getOrderChartList(OrderChartsVo vo, String type,
			String chartType, String[] showTypeArr) throws Exception {
		List<OrderChartsVo> voList = null;
		if (showTypeArr != null && StringUtilsc.isContain(showTypeArr, "day")) {
			if ("ticketNum".equals(type)) {// 统计票号
				voList = this.statisticTicketChartsByCrt(vo, chartType);
			} else if ("profit".equals(type)) {// 统计利润
				voList = this.statisticProfitChartsByCrt(vo, chartType);
			} else if ("saleroom".equals(type)) {// 统计销售额
				voList = this.statisticSaleroomChartsByCrt(vo);
			}
		} else {
			if ("ticketNum".equals(type)) {// 统计票号
				voList = this.statisticTicketCharts(vo, chartType);
			} else if ("profit".equals(type)) {// 统计利润
				voList = this.statisticProfitCharts(vo, chartType);
			} else if ("saleroom".equals(type)) {// 统计销售额
				voList = this.statisticSaleroomCharts(vo);
			}
		}
		return voList;
	}

	@Override
	public double getAllCal(OrderChartsVo vo, String type) throws Exception {
		double all = 0;
		if ("ticketNum".equals(type)) {
			all = this.getAllTicket(vo);
		} else if ("profit".equals(type)) {
			all = this.getAllProfit(vo);
		} else if ("saleroom".equals(type)) {
			all = this.getAllSaleroom(vo);
		}
		return all;
	}

	@Override
	public List<OrderChartsVo> statisticSaleroomChartsByCrt(OrderChartsVo vo) throws Exception {
		List<Object[]> result = factoryDao.getGjBuyOrderDao().statisticSaleroomByCarrierAndCrt(vo);
		List<OrderChartsVo> voList = null;
		if (result != null && result.size() > 0) {
			voList = new ArrayList<OrderChartsVo>();
			for (Object[] objects : result) {
				OrderChartsVo orderChartsVo = new OrderChartsVo();
				if (objects[0] != null) {
					orderChartsVo.setCarrier(objects[0].toString());
				}
				if (objects[1] != null) {
					orderChartsVo.setCreateTime(objects[1].toString());
				}
				if (objects[2] != null) {
					orderChartsVo.setSaleroom(objects[2].toString());
				}
				voList.add(orderChartsVo);
			}
		}
		return voList;
	}

	@Override
	public String getOrderChartData(OrderChartsVo vo, String type,
			String chartType, String[] showTypeArr) throws Exception {
		List<OrderChartsVo> voList = this.getOrderChartList(vo, type, chartType ,showTypeArr);;
		
		if (showTypeArr != null && StringUtilsc.isContain(showTypeArr, "day")) {
			if (voList != null && voList.size() > 0) {
				return ChartsUtils.parseOrderChartsWithCtm(voList, showTypeArr);
			} else {
				return "[]";
			}
		} else {
			if (voList != null && voList.size() > 0) {
				return ChartsUtils.parseOrderCharts(voList, showTypeArr);
			} else {
				return "[]";
			}
		}
	}
}
