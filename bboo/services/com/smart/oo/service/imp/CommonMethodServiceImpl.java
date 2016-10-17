package com.smart.oo.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.comm.entity.BaseAccountEntity;
import com.smart.comm.entity.BaseContactsEntity;
import com.smart.comm.entity.GjSaleFlightEntity;
import com.smart.comm.entity.GjSaleOrderEntity;
import com.smart.comm.entity.GjSalePassengerEntity;
import com.smart.comm.utils.BBOOConstants;
import com.smart.oo.dao.imp.FactoryDaoImpl;
import com.smart.oo.domain.res.FlightDetailVO;
import com.smart.oo.domain.res.GetPnrInfoResult;
import com.smart.oo.domain.res.GjOrderGetDetailResult;
import com.smart.oo.domain.res.GjSaleFlightVO;
import com.smart.oo.domain.res.GjSaleOrderVO;
import com.smart.oo.domain.res.GjSalePassengerVO;
import com.smart.oo.domain.res.OrderDetailVO;
import com.smart.oo.domain.res.PassengerDetailVO;
import com.smart.oo.domain.res.RtPnrLineVO;
import com.smart.oo.domain.res.RtPnrPassengerVO;
import com.smart.oo.domain.res.SelectOrderDetailResult;
import com.smart.oo.service.ICommonMethodService;

@Service("CommonMethodServiceImpl")
public class CommonMethodServiceImpl implements ICommonMethodService {
	@Autowired
	private FactoryDaoImpl factoryDao;

	@Autowired
	private FactoryServiceImpl factoryService;

	@Override
	public String getSlfStatusCH(String slfStatus) {
		if (StringUtils.isNotEmpty(slfStatus)) {
			switch (Integer.parseInt(slfStatus)) {
			case 0:
				return "订座成功等待支付";
			case 2:
				return "出票完成";
			case 5:
				return "出票中";
			case 12:
				return "订单取消";
			case 20:
				return "等待座位确认";
			case 30:
				return " 退票申请中";
			case 31:
				return "退票完成等待退款";
			case 39:
				return "退款完成";
			case 40:
				return "改签申请中";
			case 42:
				return "改签完成";
			case 50:
				return "未出票申请退款";
			case 51:
				return "订座成功等待价格确认";
			case 61:
				return "暂缓出票";
			case 62:
				return "订单超时";
			case -1:
				return "其它";
			default:
				return slfStatus;
			}
		} else {
			return "";
		}
	}

	
	/**
	 * 这里的orderNo是未知的东西,本地数据库中订单表的id,也可能是订单表中order_no
	 */
	@Override
	public SelectOrderDetailResult getOrderDetail(String orderNo, String accountId, String pnrContent) throws Exception {
		SelectOrderDetailResult selectOrderDetailResult = new SelectOrderDetailResult();
		if (StringUtils.isEmpty(orderNo)||orderNo.trim().endsWith("END")) {
			if (StringUtils.isNotEmpty(pnrContent)) { // 如果参数中pnrcontent有内容,那么通过rt获取订单信息
				BaseAccountEntity baseAccountEntity = factoryDao.getBaseAccountDao().queryBaseAccountByID(
						Integer.parseInt(accountId));
				if (baseAccountEntity != null) {
					GjSaleOrderEntity paramVo = new GjSaleOrderEntity();
					paramVo.setDistributor(getDistributorFromAcctype(baseAccountEntity.getAcctype()));  //通过账户表可以间接得到分销商
					paramVo.setMno(baseAccountEntity.getMno());
					paramVo.setRtOffno(baseAccountEntity.getOffice());
					if(baseAccountEntity.getAcctype().contains("_O_")){
						paramVo.setFlightClass(BBOOConstants.GjSaleOrderEntity_flightClass_I);
					}else if(baseAccountEntity.getAcctype().contains("_N_")){
						paramVo.setFlightClass(BBOOConstants.GjSaleOrderEntity_flightClass_N);
					}
					paramVo.setPnrText(pnrContent.trim().length()>6?pnrContent:"");
					paramVo.setPnrCode(pnrContent.trim().length()==6?pnrContent:"");
					paramVo.setOrderNo(orderNo);
					GetPnrInfoResult getPnrInfoResult = factoryService.getAllApiService().getRtByThirdInterface(
							paramVo, true);
					if (getPnrInfoResult != null) {
						selectOrderDetailResult=copyFieldsFromGetPnrInfoResultToSelectOrderDetailResult(paramVo, getPnrInfoResult,
								selectOrderDetailResult);
						selectOrderDetailResult.setCode(BBOOConstants.SelectOrderDetailResult_code_zero);
						return selectOrderDetailResult;  //直接返回,就不会执行下面的代码了
					}

				}
			}
			selectOrderDetailResult.setCode(BBOOConstants.SelectOrderDetailResult_code_one);
			selectOrderDetailResult.setMsg("订单号不能为空!");
			return selectOrderDetailResult;
		}
		if (isOrderNoMatchOurOwnSystemFormat(orderNo)) {
			SelectOrderDetailResult dbResult = getOrderDetailFromDB(orderNo);
			if (dbResult != null) { // 如果从数据库中查询到了订单的信息，那么就用数据库中信息的信息，否则还是要从第三方接口获取！
				return dbResult;
			} else {
				return getOrderDetailByThirdInterface(orderNo, accountId);
			}
		} else {
			return getOrderDetailByThirdInterface(orderNo, accountId);
		}
	}

	/**
	 * 将rt返回的接口封装成我想要的接口model
	 * 
	 * @param getPnrInfoResult
	 * @param selectOrderDetailResult
	 * @throws Exception
	 */
	private SelectOrderDetailResult copyFieldsFromGetPnrInfoResultToSelectOrderDetailResult(GjSaleOrderEntity gjSaleOrderEntity,
			GetPnrInfoResult getPnrInfoResult, SelectOrderDetailResult selectOrderDetailResult) throws Exception {
		if (gjSaleOrderEntity != null && getPnrInfoResult != null && selectOrderDetailResult != null) {
			OrderDetailVO order = new OrderDetailVO();
			order.setDistributor(gjSaleOrderEntity.getDistributor());
			order.setFlightClass(gjSaleOrderEntity.getFlightClass());
			order.setOrderNo(gjSaleOrderEntity.getOrderNo());
			List<FlightDetailVO> flights = new ArrayList<FlightDetailVO>();
			List<RtPnrLineVO> rtPnrLineVOs = getPnrInfoResult.getLines();
			if (rtPnrLineVOs != null && rtPnrLineVOs.size() > 0) {
				for (RtPnrLineVO rtPnrLineVO : rtPnrLineVOs) {
					FlightDetailVO flightDetailVO = new FlightDetailVO();
					flightDetailVO.setArrAircode(rtPnrLineVO.getArrCityCode());
					flightDetailVO.setCabin(rtPnrLineVO.getCabinCode());
					flightDetailVO.setDepAircode(rtPnrLineVO.getDepCityCode());
					flightDetailVO.setDepartureDate(rtPnrLineVO.getDepDate());
					flightDetailVO.setDepartureTime(rtPnrLineVO.getStartTime());
					flightDetailVO.setFlightNum(rtPnrLineVO.getFlightNo());
					flightDetailVO.setShareNum("");
					flights.add(flightDetailVO);
				}
			}
			order.setFlights(flights);
			List<PassengerDetailVO> passengers = new ArrayList<PassengerDetailVO>();
			List<RtPnrPassengerVO> rtPnrPassengerVOs = getPnrInfoResult.getPassengers();
			if (rtPnrPassengerVOs != null && rtPnrPassengerVOs.size() > 0) {
				order.setPassengerCount(rtPnrPassengerVOs.size());
				int childrenCount=0;
				for (RtPnrPassengerVO rtPnrPassengerVO : rtPnrPassengerVOs) {
					PassengerDetailVO passengerDetailVO = new PassengerDetailVO();
					passengerDetailVO.setAgeType(Integer.parseInt(rtPnrPassengerVO.getState()));
					passengerDetailVO.setBirthday(getBirthdayFromIdCard(rtPnrPassengerVO.getCertNo()));
					passengerDetailVO.setCardExpired("");
					passengerDetailVO.setCardIssuePlace("");
					passengerDetailVO.setCardNum(rtPnrPassengerVO.getCertNo());
					passengerDetailVO.setCardType("");
					passengerDetailVO.setCost(0d);
					passengerDetailVO.setGender(getSexFromIdCard(rtPnrPassengerVO.getCertNo()));
					passengerDetailVO.setName(rtPnrPassengerVO.getName());
					passengerDetailVO.setNationality("");
					passengerDetailVO.setOilFee(0d);
					passengerDetailVO.setTaxFee(0d);
					passengers.add(passengerDetailVO);
					if(rtPnrPassengerVO.getName()!=null&&rtPnrPassengerVO.getName().trim().endsWith("CHD")){
						childrenCount++;
					}
				}
				order.setChildrenCount(childrenCount);
			}
			order.setPassengers(passengers);
			BaseContactsEntity param = new BaseContactsEntity();
			param.setMno(gjSaleOrderEntity.getMno());
			List<BaseContactsEntity> baseContactsEntities = factoryDao.getBaseContactsDao().queryContacts(param);
			// 注意要用采购方面的联系人
			if (baseContactsEntities != null && baseContactsEntities.size() > 0) {
				BaseContactsEntity baseContactsEntity = baseContactsEntities.get(0);
				order.setContactEmail(baseContactsEntity.getEmail());
				order.setContactMob(baseContactsEntity.getPhone());
				order.setContactName(baseContactsEntity.getLinkman());
				order.setContactTel(baseContactsEntity.getLinktel());
			}
			selectOrderDetailResult.setCode(BBOOConstants.SelectOrderDetailResult_code_zero);
			selectOrderDetailResult.setOrder(order);
		}
		return selectOrderDetailResult;
	}

	public SelectOrderDetailResult getOrderDetailFromDB(String orderNo) throws Exception {
		SelectOrderDetailResult selectOrderDetailResult = new SelectOrderDetailResult();
		if (StringUtils.isEmpty(orderNo)) {
			selectOrderDetailResult.setCode(BBOOConstants.SelectOrderDetailResult_code_one);
			selectOrderDetailResult.setMsg("订单号不能为空!");
			return selectOrderDetailResult;
		}
		GjSaleOrderEntity gjSaleOrderEntity = factoryDao.getSaleOrderDao().queryOrderByID(orderNo);
		if (gjSaleOrderEntity != null) {
			OrderDetailVO order = new OrderDetailVO();
			copyFieldFromGjSaleOrderEntityToOrderDetailVO(gjSaleOrderEntity, order);
			BaseContactsEntity param = new BaseContactsEntity();
			param.setMno(gjSaleOrderEntity.getMno());
			List<BaseContactsEntity> baseContactsEntities = factoryDao.getBaseContactsDao().queryContacts(param);
			// 注意要用采购方面的联系人
			if (baseContactsEntities != null && baseContactsEntities.size() > 0) {
				BaseContactsEntity baseContactsEntity = baseContactsEntities.get(0);
				order.setContactEmail(baseContactsEntity.getEmail());
				order.setContactMob(baseContactsEntity.getPhone());
				order.setContactName(baseContactsEntity.getLinkman());
				order.setContactTel(baseContactsEntity.getLinktel());
			}
			selectOrderDetailResult.setCode(BBOOConstants.SelectOrderDetailResult_code_zero);
			selectOrderDetailResult.setOrder(order);
			return selectOrderDetailResult;
		} else {
			return null;
		}
	}

	@Override
	public SelectOrderDetailResult getOrderDetailByThirdInterface(String orderNo, String accountId) throws Exception {
		SelectOrderDetailResult selectOrderDetailResult = new SelectOrderDetailResult();
		if (StringUtils.isEmpty(orderNo)) {
			selectOrderDetailResult.setCode(BBOOConstants.SelectOrderDetailResult_code_one);
			selectOrderDetailResult.setMsg("订单号不能为空!");
			return selectOrderDetailResult;
		}
		if (StringUtils.isEmpty(accountId)) {
			selectOrderDetailResult.setCode(BBOOConstants.SelectOrderDetailResult_code_one);
			selectOrderDetailResult.setMsg("账户id不能为空!");
			return selectOrderDetailResult;
		}
		BaseAccountEntity baseAccountEntity = factoryDao.getBaseAccountDao().queryBaseAccountByID(
				Integer.parseInt(accountId));
		if (baseAccountEntity != null) {
			GjOrderGetDetailResult gjOrderGetDetailResult = factoryService.getAllApiService().getOrderInfoByInteface(
					baseAccountEntity, orderNo);
			if (gjOrderGetDetailResult != null) {
				if (BBOOConstants.GjOrderGetDetailResult_code_zero.equals(gjOrderGetDetailResult.getCode())) {
					List<GjSaleOrderVO> gjSaleOrderVOs = gjOrderGetDetailResult.getOrders();
					if (gjSaleOrderVOs != null && gjSaleOrderVOs.size() > 0) {
						GjSaleOrderVO gjSaleOrderVO = gjSaleOrderVOs.get(0);
						OrderDetailVO order = new OrderDetailVO();
						copyFieldFromGjSaleOrderVOToOrderDetailVO(gjSaleOrderVO, order);
						BaseContactsEntity param = new BaseContactsEntity();
						param.setMno(baseAccountEntity.getMno());
						List<BaseContactsEntity> baseContactsEntities = factoryDao.getBaseContactsDao().queryContacts(
								param);
						// 注意要用采购方面的联系人
						if (baseContactsEntities != null && baseContactsEntities.size() > 0) {
							BaseContactsEntity baseContactsEntity = baseContactsEntities.get(0);
							order.setContactEmail(baseContactsEntity.getEmail());
							order.setContactMob(baseContactsEntity.getPhone());
							order.setContactName(baseContactsEntity.getLinkman());
							order.setContactTel(baseContactsEntity.getLinktel());
						}
						selectOrderDetailResult.setCode(BBOOConstants.SelectOrderDetailResult_code_zero);
						selectOrderDetailResult.setOrder(order);
					} else {
						selectOrderDetailResult.setCode(BBOOConstants.SelectOrderDetailResult_code_one);
						selectOrderDetailResult.setMsg("未查询到订单信息！");
					}
				} else {
					selectOrderDetailResult.setCode(BBOOConstants.SelectOrderDetailResult_code_one);
					selectOrderDetailResult.setMsg(gjOrderGetDetailResult.getMsg());
				}
			} else {
				selectOrderDetailResult.setCode(BBOOConstants.SelectOrderDetailResult_code_one);
				selectOrderDetailResult.setMsg("未查询到订单信息！");
			}
		} else {
			selectOrderDetailResult.setCode(BBOOConstants.SelectOrderDetailResult_code_one);
			selectOrderDetailResult.setMsg("未查询到账户信息！");
		}
		return selectOrderDetailResult;
	}

	/**
	 * 从一个vo复制值到另外一个vo
	 * 
	 * @param gjSaleOrderEntity
	 * @param order
	 * @throws Exception
	 */
	private void copyFieldFromGjSaleOrderEntityToOrderDetailVO(GjSaleOrderEntity gjSaleOrderEntity, OrderDetailVO order)
			throws Exception {
		if (gjSaleOrderEntity != null && order != null) {
			int childrenCount=0;
			order.setOrderNo(gjSaleOrderEntity.getOrderNo());
			order.setDistributor(gjSaleOrderEntity.getDistributor());
			order.setPassengerCount(gjSaleOrderEntity.getPassengerCount());
			Set<GjSaleFlightEntity> gjSaleFlightVOs = gjSaleOrderEntity.getFlights();
			if (gjSaleFlightVOs != null && gjSaleFlightVOs.size() > 0) {
				List<FlightDetailVO> flights = new ArrayList<FlightDetailVO>();
				for (GjSaleFlightEntity gjSaleFlightEntity : gjSaleFlightVOs) {
					FlightDetailVO flightDetailVO = new FlightDetailVO();
					BeanUtils.copyProperties(flightDetailVO,gjSaleFlightEntity);
					flights.add(flightDetailVO);
				}
				order.setFlights(flights);
			}
			Set<GjSalePassengerEntity> gjSalePassengerVOs = gjSaleOrderEntity.getPassengers();
			if (gjSalePassengerVOs != null && gjSaleFlightVOs.size() > 0) {
				List<PassengerDetailVO> passengerDetailVOs = new ArrayList<PassengerDetailVO>();
				for (GjSalePassengerEntity gjSalePassengerEntity : gjSalePassengerVOs) {
					PassengerDetailVO passengerDetailVO = new PassengerDetailVO();
					BeanUtils.copyProperties(passengerDetailVO,gjSalePassengerEntity);
					passengerDetailVOs.add(passengerDetailVO);
					if(1==gjSalePassengerEntity.getAgeType()){
						childrenCount++;
					}
				}
				order.setPassengers(passengerDetailVOs);
			}
			order.setChildrenCount(childrenCount);
		}
	}
	
	
	/**
	 * 从一个vo赋值到另外一个vo
	 * 
	 * @param gjSaleOrderVO
	 * @param order
	 * @throws Exception
	 */
	private void copyFieldFromGjSaleOrderVOToOrderDetailVO(GjSaleOrderVO gjSaleOrderVO, OrderDetailVO order)
			throws Exception {
		if (gjSaleOrderVO != null && order != null) {
			int childrenCount=0;
			order.setOrderNo(gjSaleOrderVO.getOrderNo());
			List<GjSaleFlightVO> gjSaleFlightVOs = gjSaleOrderVO.getFlights();
			order.setPassengerCount(gjSaleOrderVO.getPassengerCount());
			order.setDistributor(gjSaleOrderVO.getDistributor());
			order.setFlightClass(gjSaleOrderVO.getFlightClass());
			if (gjSaleFlightVOs != null && gjSaleFlightVOs.size() > 0) {
				List<FlightDetailVO> flights = new ArrayList<FlightDetailVO>();
				for (GjSaleFlightVO gjSaleFlightVO : gjSaleFlightVOs) {
					FlightDetailVO flightDetailVO = new FlightDetailVO();
					BeanUtils.copyProperties(flightDetailVO,gjSaleFlightVO);
					flights.add(flightDetailVO);
				}
				order.setFlights(flights);
			}
			List<GjSalePassengerVO> gjSalePassengerVOs = gjSaleOrderVO.getPassengers();
			if (gjSalePassengerVOs != null && gjSaleFlightVOs.size() > 0) {
				List<PassengerDetailVO> passengerDetailVOs = new ArrayList<PassengerDetailVO>();
				for (GjSalePassengerVO gjSalePassengerVO : gjSalePassengerVOs) {
					PassengerDetailVO passengerDetailVO = new PassengerDetailVO();
					BeanUtils.copyProperties(passengerDetailVO,gjSalePassengerVO);
					passengerDetailVOs.add(passengerDetailVO);
					if(1==gjSalePassengerVO.getAgeType()){
						childrenCount++;
					}
				}
				order.setPassengers(passengerDetailVOs);
			}
			order.setChildrenCount(childrenCount);
		}
	}

	@Override
	public boolean isTicketNumberValid(String ticketNumber) throws Exception {
		if (StringUtils.isNotEmpty(ticketNumber)) {
			/**
			 * 正则表达式解释： 0-9数字先出现3次 然后？表示出现0次或者1次，即-出现0次或者1次 然后0-9再出现10次
			 * 搞定，正则表达式就是好用啊！
			 */
			Pattern pattern = Pattern.compile("[0-9]{3}-?[0-9]{10}");
			Matcher isNum = pattern.matcher(ticketNumber);
			if (!isNum.matches()) {
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public void isSatisfyCreateOrderCoditionByThirdInterface(GjSaleOrderEntity gjSaleOrderEntity,
			BaseAccountEntity baseAccountEntity) throws Exception {
		if (!BBOOConstants.GjSaleOrderEntity_distributor_N.equals(gjSaleOrderEntity.getDistributor())) { // 途牛的不需要做验证(不为途牛的进入)
			// 开始验证订单在分销商的状态，最主要是防止已经未出票申请退款的情况
			GjOrderGetDetailResult gjOrderGetDetailResult = factoryService.getAllApiService().getOrderInfoByInteface(
					baseAccountEntity, gjSaleOrderEntity.getOrderNo());
			if (gjOrderGetDetailResult != null) {
				if (BBOOConstants.GjOrderGetDetailResult_code_zero.equals(gjOrderGetDetailResult.getCode())) {
					List<GjSaleOrderVO> gjSaleOrderVOs = gjOrderGetDetailResult.getOrders();
					if (gjSaleOrderVOs != null && gjSaleOrderVOs.size() > 0) {
						GjSaleOrderVO gjSaleOrderVO = gjSaleOrderVOs.get(0);
						String status = gjSaleOrderVO.getStatus();
						if (!BBOOConstants.GjSaleOrderEntity_SLFSTATUS_ONE.equals(status)
								&& !BBOOConstants.GjSaleOrderEntity_SLFSTATUS_FIVE.equals(status)) {
							// 不满足任意一种状态，说明订单可能是已经申请退款的订单，这样的订单是不允许出票的。
							throw new RuntimeException("该订单可能在分销平台的订单状态是:" + getSlfStatusCH(status));
						}
					} else {
						throw new RuntimeException("在分销平台没有查询到该订单的信息！");
					}
				} else {
					throw new RuntimeException(gjOrderGetDetailResult.getMsg());
				}
			} else {
				throw new RuntimeException("查询分销平台的订单信息的接口没有返回任何东西！");
			}
		}
	}

	/**
	 * 判断一下传过来的订单是否和我们自己系统的订单格式一样<br/>
	 * 我们系统中订单的格式：1472619658080000898<br/>
	 * 
	 * @param orderNo
	 *            订单号
	 * @return
	 * @throws Exception
	 */
	private boolean isOrderNoMatchOurOwnSystemFormat(String orderNo) throws Exception {
		// 我们系统中订单的格式：1472619658080000898
		if (StringUtils.isNotEmpty(orderNo)) {
			Pattern pattern = Pattern.compile("[0-9]{19}"); // 是否是19个数字组成订单号？
			Matcher matcher = pattern.matcher(orderNo);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}

	public boolean isPnrMatchPassenger(GetPnrInfoResult rtrs, GjSaleOrderEntity gjSaleOrderEntity) throws Exception {
		if (rtrs != null && gjSaleOrderEntity != null) {
			if (BBOOConstants.GetPnrInfoResult_code_zero.equals(rtrs.getCode())) {
				List<RtPnrPassengerVO> rtPnrPassengerVOs = rtrs.getPassengers();
				Set<GjSalePassengerEntity> gjSalePassengerEntities = gjSaleOrderEntity.getPassengers();
				if (rtPnrPassengerVOs != null && rtPnrPassengerVOs.size() > 0 && gjSalePassengerEntities != null
						&& gjSalePassengerEntities.size() > 0) {
					for (RtPnrPassengerVO rtPnrPassengerVO : rtPnrPassengerVOs) {
						boolean bool = false;
						for (GjSalePassengerEntity gjSalePassengerEntity : gjSalePassengerEntities) {
							String passengerName = rtPnrPassengerVO.getName();
							String certNo = rtPnrPassengerVO.getCertNo();
							// 名字和证件号,有一个满足条件,就认为是匹配的
							if ((passengerName != null && passengerName.equals(gjSalePassengerEntity.getName()))
									|| (certNo != null && certNo.equals(gjSalePassengerEntity.getCardNum()))) {
								bool = true;
							}
						}
						if (!bool) {
							return false; // 只要有一个乘客信息不匹配,那么这个rt得到的结果和订单的信息就是不匹配的.
						}
					}
					return true; // 只有满足上面这么多条件,同时没有中途乘机人姓名不满足而跳出,才算得上pnr匹配
				}
			}
		}
		return false;
	}

	// 获取18位身份证的年月日
	public String getBirthdayFromIdCard(String idCard) {
		if (idCard.length() == 18) {
			String year = idCard.substring(6, 10);
			String month = idCard.substring(10, 12);
			String day = idCard.substring(12, 14);
			return year + "-" + month + "-" + day;
		} else {
			return "";
		}
	}

	/**
	 * 从身份证获取性别 M男性,F女性
	 * 
	 * @return
	 */
	public String getSexFromIdCard(String idCard) {
		if (idCard.length() == 18) {
			String sex = idCard.substring(16, 17);
			if (Integer.parseInt(sex) % 2 == 0) {
				return "F";
			} else {
				return "M";
			}
		} else {
			return "";
		}
	}

	
	/**
	 * 
	 * Distributor:  T淘宝    Q去哪儿   C携程     H航班管家   K酷讯       N途牛       O同程
	 *
	 * @param acctype 	
	 * QUA QUNAR CTRIP TONGC 获取分销订单接口(QUNAR_O_SALE CTRIP_O_SALE TAOBAO_O_SALE
	 * KUXUN_O_SALE HBGJ_O_SALE TUNIU_O_SALE TONGC_O_SALE TONGC_N_SALE
	 * TAOBAO_N_SALE QUNAR_N_SALE) TFARE_GDS 获取政策（CP_517NA CP_BAITOUR CP_8000YI
	 * CP_51BOOK CP_19E CP_LH800 CP_AIR_B2B_EBAO CP_LOCALBSP CP_QUA）
	 *
	 * @return
	 */
	public String  getDistributorFromAcctype(String acctype){
		if(acctype.contains("QUNAR")){
			return "Q";
		}else if(acctype.contains("CTRIP")){
			return "C";
		}else if(acctype.contains("TAOBAO")){
			return "T";
		}else if(acctype.contains("KUXUN")){
			return "K";
		}else if(acctype.contains("TUNIU")){
			return "N";
		}else if(acctype.contains("TONGC")){
			return "O";
		}else{
			return "";
		}
	}
	public static void main(String[] args) throws Exception {
//		String idCard = "61010319890416162X";
//		System.out.println(new CommonMethodServiceImpl().getSexFromIdCard(idCard));	

		GjSalePassengerEntity gjSalePassengerEntity=new GjSalePassengerEntity();
		gjSalePassengerEntity.setBirthday("2016-11-11");
		PassengerDetailVO passengerDetailVO = new PassengerDetailVO();
		
		BeanUtils.copyProperties(passengerDetailVO,gjSalePassengerEntity);
		
		System.out.println(passengerDetailVO.getBirthday());
	
	}
}
