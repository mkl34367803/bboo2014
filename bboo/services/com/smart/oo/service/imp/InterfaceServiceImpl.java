package com.smart.oo.service.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.smart.comm.entity.AccountManageEntity;
import com.smart.comm.entity.BaseAccountEntity;
import com.smart.comm.entity.BaseContactsEntity;
import com.smart.comm.utils.OOComms;
import com.smart.comm.utils.OOLogUtil;
import com.smart.entity.User;
import com.smart.oo.action.syslog.SysLogAction;
import com.smart.oo.domain.GetGwAllConfigDomain;
import com.smart.oo.domain.LoginDomain;
import com.smart.oo.domain.SelectOrderDetailDomain;
import com.smart.oo.domain.res.AirAccountVO;
import com.smart.oo.domain.res.ConfigForGwTicketingResult;
import com.smart.oo.domain.res.ContactsVO;
import com.smart.oo.domain.res.ContactsVOS;
import com.smart.oo.domain.res.FlightDetailVO;
import com.smart.oo.domain.res.GWAccountVO;
import com.smart.oo.domain.res.GWAccountVOS;
import com.smart.oo.domain.res.GetGwAllConfigResult;
import com.smart.oo.domain.res.LinkerVO;
import com.smart.oo.domain.res.LoginForGwTicketingResult;
import com.smart.oo.domain.res.LoginResult;
import com.smart.oo.domain.res.OrderDetailForGwTicketingResult;
import com.smart.oo.domain.res.PassengerDetailVO;
import com.smart.oo.domain.res.SaleAccountVO;
import com.smart.oo.domain.res.SelectOrderDetailResult;
import com.smart.oo.domain.res.ShopAccountVO;
import com.smart.oo.response.ErrResponse;
import com.smart.oo.service.IInterfaceService;
import com.smart.utils.JsonPluginsUtil;
import com.smart.utils.SecurityUtils;
import com.smart.utils.StringUtils;

@Service("BBOOInterfaceServiceImpl")
public class InterfaceServiceImpl implements IInterfaceService {

	@Autowired
	private FactoryServiceImpl factoryService;

	@Resource(name = "SysLogAction")
	private SysLogAction sysLog;

	private static final String GET_AIR_ACCOUNT_KEY = "201610051703";

	public OrderDetailForGwTicketingResult getOrderDetail(String json) throws Exception {
		OrderDetailForGwTicketingResult rlt = new OrderDetailForGwTicketingResult();
		SelectOrderDetailResult rs = getOrderInfoByOrderNo(json);
		if (rs != null && rs.getOrder() != null && rs.getOrder().getPassengers() != null
				&& rs.getOrder().getPassengers().size() > 0) {
			List<PassengerDetailVO> psgList = rs.getOrder().getPassengers();
			StringBuffer psgBuf = new StringBuffer();
			StringBuffer birBuf = new StringBuffer();
			StringBuffer cardBuf = new StringBuffer();
			for (PassengerDetailVO p : psgList) {
				psgBuf.append(p.getName() + ",");
				birBuf.append(p.getBirthday() + ",");
				cardBuf.append(p.getCardNum() + ",");
			}
			rlt.setPASSENGERS(psgBuf.toString().substring(0, psgBuf.toString().length() - 1));
			rlt.setBIRTHDAYS(birBuf.toString().substring(0, birBuf.toString().length() - 1));
			rlt.setCARDNUMS(cardBuf.toString().substring(0, cardBuf.toString().length() - 1));
			rlt.setPOLICY("");
			List<FlightDetailVO> fltList = rs.getOrder().getFlights();
			StringBuffer noBuf = new StringBuffer();
			StringBuffer dateBuf = new StringBuffer();
			StringBuffer sharNos = new StringBuffer();
			StringBuffer deparrBuf = new StringBuffer();
			for (FlightDetailVO f : fltList) {
				noBuf.append(f.getFlightNum() + ",");
				deparrBuf.append(f.getDepAircode() + f.getArrAircode() + ",");
				dateBuf.append(f.getDepartureDate() + " " + f.getDepartureTime() + ",");
				sharNos.append(f.getShareNum() + ",");
				break;
			}
			rlt.setDEPARRS(deparrBuf.toString().substring(0, deparrBuf.toString().length() - 1));
			rlt.setFLIGHTNOS(noBuf.toString().substring(0, noBuf.toString().length() - 1));
			rlt.setDEPTIMES(dateBuf.toString().substring(0, dateBuf.toString().length() - 1));
			rlt.setSHARENOS(sharNos.toString().substring(0, sharNos.toString().length() - 1));
			String fltClass = rs.getOrder().getFlightClass();
			if ("I".equals(fltClass)) {
				rlt.setTYPE("国际");
			} else {
				rlt.setTYPE("国内");
			}
			SelectOrderDetailDomain request = rs.getRequest();
			rlt.setPLATFORMCODE(rs.getOrder().getDistributor());
			rlt.setPWCODE(request != null ? request.getAccountId() : "");
			rlt.setOWN(request != null ? request.getName() : "");
			rlt.setORDERID(rs.getOrder().getOrderNo());
			rlt.setPSGCOUNT(rs.getOrder().getPassengerCount());
			rlt.setCHDCOUNT(rs.getOrder().getChildrenCount());
		} else {
			rlt.setERRMSG("获取订单信息失败，请重新获取或联系管理员");
		}
		return rlt;
	}

	public SelectOrderDetailResult getOrderInfoByOrderNo(String json) throws Exception {
		SelectOrderDetailResult rlt = new SelectOrderDetailResult();
		ErrResponse msgv = new ErrResponse();
		rlt.setErr(msgv);
		SelectOrderDetailDomain vo = null;
		if (json != null && !"".equals(json.trim())) {
			vo = JsonPluginsUtil.jsonToFastBean(json, SelectOrderDetailDomain.class);
			// 订单导入的时候，rt了一次，进入页面后rt查政策适用换成的pnrTxt
			String orderNo = vo.getOrderNo() != null ? vo.getOrderNo().trim() : "";
			// if (StringUtils.isEmpty(orderNo)) {
			// msgv.setMsg("订单号不能为空");
			// msgv.setMsgcode("");
			// return rlt;
			// }
			String accountId = vo.getAccountId() != null ? vo.getAccountId().trim() : "";
			rlt = factoryService.getCommonMethodService().getOrderDetail(orderNo, accountId, vo.getPnrContent());
			OOLogUtil.info("訂單接口返回："+(rlt!=null?JSON.toJSONString(rlt):"null"), InterfaceServiceImpl.class, null);
		} else {
			msgv.setMsg("参数异常");
		}
		rlt.setRequest(vo);
		return rlt;
	}

	public String getConfigJson(ConfigForGwTicketingResult rlt) {

		StringBuffer buf = new StringBuffer();
		// 登陆账户
		if (rlt.getAirAccounts() == null) {
			buf.append("{}");
		} else {
			buf.append(JsonPluginsUtil.beanToJson(rlt.getAirAccounts()).replace("\"c_", "\"C_").replace("\"g5", "\"G5")
					.replace("\"h9", "\"9H").replace("\"u3", "\"3U").replace("\"l8", "\"8L"));
		}
		// 支付类型
		buf.append("^{}");
		// 支付卡号
		buf.append("^{}");
		// 我的配置
		buf.append("^[]");
		// 店铺信息
		if (rlt.getShopAccounts() == null) {
			buf.append("^[]");
		} else {
			buf.append("^" + JsonPluginsUtil.beanListToJson(rlt.getShopAccounts()));
		}
		// XYK信息
		buf.append("^[]");
		// 城市三字码信息
		buf.append("^[]");
		// 版本信息
		buf.append("^1.0");
		// 联系人信息
		if (rlt.getLink() == null) {
			buf.append("^{}");
		} else {
			buf.append("^" + JsonPluginsUtil.beanToJson(rlt.getLink()).replace("\"c_", "\"C_"));
		}
		return buf.toString();
	}

	public String getGwAllConfigJson(String json) throws Exception {
		ConfigForGwTicketingResult rlt = getGwAllConfig(json);
		return this.getConfigJson(rlt);

	}

	public ConfigForGwTicketingResult getGwAllConfig(String json) throws Exception {
		ConfigForGwTicketingResult rlt = new ConfigForGwTicketingResult();
		GetGwAllConfigResult rs = getGwConfig(json);
		if (rs != null) {
			if (rs.getAccounts() != null) {
				List<ShopAccountVO> shopAccounts = new ArrayList<ShopAccountVO>();
				List<SaleAccountVO> accounts = rs.getAccounts();
				for (SaleAccountVO a : accounts) {
					ShopAccountVO sa = new ShopAccountVO();
					sa.setOWN(rs.getRequest().getName());
					String pLATFORMCODE = "";
					if ("淘宝".equals(a.getPlatName())) {
						pLATFORMCODE = "TB";
					} else if ("酷讯".equals(a.getPlatName())) {
						pLATFORMCODE = "KX";
					} else if ("航班管家".equals(a.getPlatName())) {
						pLATFORMCODE = "HBGJ";
					} else if ("途牛".equals(a.getPlatName())) {
						pLATFORMCODE = "TN";
					} else if ("同程".equals(a.getPlatName())) {
						pLATFORMCODE = "TC";
					} else if ("去哪儿".equals(a.getPlatName())) {
						pLATFORMCODE = "QN";
					} else if ("携程".equals(a.getPlatName())) {
						pLATFORMCODE = "XC";
					}
					sa.setPLATFORMCODE(pLATFORMCODE);
					sa.setPWCODE(String.valueOf(a.getId()));
					sa.setPWNAME(a.getPlatName() + "-" + a.getDescribe());
					sa.setTYPE(a.getFlightClass());
					shopAccounts.add(sa);
				}
				rlt.setShopAccounts(shopAccounts);
			}
			if (rs.getLink() != null) {
				ContactsVOS link = new ContactsVOS();
				LinkerVO lv = rs.getLink();
				if (lv != null) {
					List<ContactsVO> aLL = new ArrayList<ContactsVO>();
					ContactsVO cv = new ContactsVO();
					cv.setC_CPYCODES("");
					cv.setC_MAIL(lv.getEmail());
					cv.setC_NAME(lv.getLinkman());
					cv.setC_PHONE(lv.getPhone());
					cv.setC_UUID(rs.getRequest().getName());
					cv.setUUID(String.valueOf(lv.getId()));
					aLL.add(cv);
					link.setALL(aLL);
				}
				rlt.setLink(link);
			}
			if (rs.getAirv() != null) {
				List<AirAccountVO> alvs = rs.getAirv();
				rlt.setAirAccounts(getGWAccountVOS(alvs));
			}
		}
		return rlt;
	}

	private GWAccountVO toGWAccountVO(AirAccountVO v) {
		GWAccountVO gv = new GWAccountVO();
		gv.setUUID(v.getId());
		gv.setC_ACCOUNT(v.getLoginName());
		gv.setC_CPYCODE(v.getPurCode());
		gv.setC_PASSWORD(v.getPassword());
		gv.setC_SORT("");
		gv.setC_TYPE("");
		return gv;
	}

	private GWAccountVOS getGWAccountVOS(List<AirAccountVO> alvs) {
		GWAccountVOS airAccounts = new GWAccountVOS();
		List<GWAccountVO> cA = new ArrayList<GWAccountVO>();
		List<GWAccountVO> pN = new ArrayList<GWAccountVO>();
		List<GWAccountVO> hO = new ArrayList<GWAccountVO>();
		List<GWAccountVO> mU = new ArrayList<GWAccountVO>();
		List<GWAccountVO> cZ = new ArrayList<GWAccountVO>();
		List<GWAccountVO> jD = new ArrayList<GWAccountVO>();
		List<GWAccountVO> kN = new ArrayList<GWAccountVO>();
		List<GWAccountVO> hU = new ArrayList<GWAccountVO>();
		List<GWAccountVO> kY = new ArrayList<GWAccountVO>();
		List<GWAccountVO> sC = new ArrayList<GWAccountVO>();
		List<GWAccountVO> u3 = new ArrayList<GWAccountVO>();
		List<GWAccountVO> zH = new ArrayList<GWAccountVO>();
		List<GWAccountVO> l8 = new ArrayList<GWAccountVO>();
		List<GWAccountVO> mF = new ArrayList<GWAccountVO>();
		List<GWAccountVO> g5 = new ArrayList<GWAccountVO>();
		List<GWAccountVO> uQ = new ArrayList<GWAccountVO>();
		List<GWAccountVO> fU = new ArrayList<GWAccountVO>();
		List<GWAccountVO> dR = new ArrayList<GWAccountVO>();
		List<GWAccountVO> tV = new ArrayList<GWAccountVO>();
		List<GWAccountVO> gX = new ArrayList<GWAccountVO>();
		List<GWAccountVO> h9 = new ArrayList<GWAccountVO>();
		for (AirAccountVO v : alvs) {
			if ("CA".equalsIgnoreCase(v.getPurCode())) {
				cA.add(toGWAccountVO(v));
			} else if ("PN".equalsIgnoreCase(v.getPurCode())) {
				pN.add(toGWAccountVO(v));
			} else if ("hO".equalsIgnoreCase(v.getPurCode())) {
				hO.add(toGWAccountVO(v));
			} else if ("mU".equalsIgnoreCase(v.getPurCode())) {
				mU.add(toGWAccountVO(v));
			} else if ("cZ".equalsIgnoreCase(v.getPurCode())) {
				cZ.add(toGWAccountVO(v));
			} else if ("jD".equalsIgnoreCase(v.getPurCode())) {
				jD.add(toGWAccountVO(v));
			} else if ("kN".equalsIgnoreCase(v.getPurCode())) {
				kN.add(toGWAccountVO(v));
			} else if ("hU".equalsIgnoreCase(v.getPurCode())) {
				hU.add(toGWAccountVO(v));
			} else if ("kY".equalsIgnoreCase(v.getPurCode())) {
				kY.add(toGWAccountVO(v));
			} else if ("sC".equalsIgnoreCase(v.getPurCode())) {
				sC.add(toGWAccountVO(v));
			} else if ("3U".equalsIgnoreCase(v.getPurCode())) {
				u3.add(toGWAccountVO(v));
			} else if ("zH".equalsIgnoreCase(v.getPurCode())) {
				zH.add(toGWAccountVO(v));
			} else if ("8L".equalsIgnoreCase(v.getPurCode())) {
				l8.add(toGWAccountVO(v));
			} else if ("mF".equalsIgnoreCase(v.getPurCode())) {
				mF.add(toGWAccountVO(v));
			} else if ("g5".equalsIgnoreCase(v.getPurCode())) {
				g5.add(toGWAccountVO(v));
			} else if ("uQ".equalsIgnoreCase(v.getPurCode())) {
				uQ.add(toGWAccountVO(v));
			} else if ("fU".equalsIgnoreCase(v.getPurCode())) {
				fU.add(toGWAccountVO(v));
			} else if ("dR".equalsIgnoreCase(v.getPurCode())) {
				dR.add(toGWAccountVO(v));
			} else if ("tV".equalsIgnoreCase(v.getPurCode())) {
				tV.add(toGWAccountVO(v));
			} else if ("gX".equalsIgnoreCase(v.getPurCode())) {
				gX.add(toGWAccountVO(v));
			} else if ("9H".equalsIgnoreCase(v.getPurCode())) {
				h9.add(toGWAccountVO(v));
			}
		}
		airAccounts.setCA(cA);
		airAccounts.setCZ(cZ);
		airAccounts.setDR(dR);
		airAccounts.setFU(fU);
		airAccounts.setG5(g5);
		airAccounts.setGX(gX);
		airAccounts.setH9(h9);
		airAccounts.setHO(hO);
		airAccounts.setHU(hU);
		airAccounts.setJD(jD);
		airAccounts.setKN(kN);
		airAccounts.setKY(kY);
		airAccounts.setL8(l8);
		airAccounts.setMF(mF);
		airAccounts.setMU(mU);
		airAccounts.setPN(pN);
		airAccounts.setSC(sC);
		airAccounts.setTV(tV);
		airAccounts.setU3(u3);
		airAccounts.setUQ(uQ);
		airAccounts.setZH(zH);
		return airAccounts;
	}

	public GetGwAllConfigResult getGwConfig(String json) throws Exception {
		GetGwAllConfigResult rlt = new GetGwAllConfigResult();
		ErrResponse msgv = new ErrResponse();
		rlt.setErr(msgv);
		GetGwAllConfigDomain vo = null;
		if (json != null && !"".equals(json.trim())) {
			vo = JsonPluginsUtil.jsonToFastBean(json, GetGwAllConfigDomain.class);
			if (vo != null) {
				if (vo.getUserId() == null) {
					msgv.setMsg("请求参数错误");
					msgv.setMsgcode("");
					return rlt;
				}
				User user = new User();
				user.setUserId(vo.getUserId());
				user.setDeleted("N");
				List<User> users = factoryService.getUserService().getUser(user);
				User u = users != null && users.size() > 0 ? users.get(0) : null;
				if (u != null) {
					String mno = u.getMert().getMno();
					rlt.setAccounts(getSaleAccount(mno));
					rlt.setLink(getLinker(mno));
					rlt.setAirv(getAirAccount(mno));
				} else {
					msgv.setMsg("校验：用户信息不存在");
				}
			} else {
				msgv.setMsg("参数序列化异常");
			}
		} else {
			msgv.setMsg("参数异常");
		}
		rlt.setRequest(vo);
		return rlt;
	}

	private LinkerVO getLinker(String mno) throws Exception {
		LinkerVO link = null;
		BaseContactsEntity bcv = new BaseContactsEntity();
		bcv.setMno(mno);
		List<BaseContactsEntity> bcvList = factoryService.getBaseContactsService().queryContacts(bcv);
		if (bcvList != null && bcvList.size() > 0) {
			Collections.shuffle(bcvList);
			for (BaseContactsEntity c : bcvList) {
				link = new LinkerVO();
				link.setAddress(c.getAddress());
				link.setEmail(c.getEmail());
				link.setLinkman(c.getLinkman());
				link.setLinktel(c.getLinktel());
				link.setPhone(c.getPhone());
				link.setId(c.getId());
			}
		}
		return link;
	}

	private List<SaleAccountVO> getSaleAccount(String mno) throws Exception {
		List<SaleAccountVO> accounts = new ArrayList<SaleAccountVO>();
		List<BaseAccountEntity> accs = factoryService.getBaseAccountService().queryBaseAccountByMno(mno);
		if (accs != null && accs.size() > 0) {
			for (BaseAccountEntity acc : accs) {
				if (1 == acc.getIsu()
						&& (acc.getAcctype().indexOf("O_SALE") != -1 || acc.getAcctype().indexOf("N_SALE") != -1)) {
					SaleAccountVO sv = new SaleAccountVO();
					sv.setId(acc.getId());
					sv.setDescribe(acc.getDescribe());
					sv.setFlightClass(acc.getAcctype().indexOf("O_SALE") != -1 ? "国际" : "国内");
					if (acc.getAcctype().indexOf("TAOBAO") != -1) {
						sv.setPlatName("淘宝");
					} else if (acc.getAcctype().indexOf("KUXUN") != -1) {
						sv.setPlatName("酷讯");
					} else if (acc.getAcctype().indexOf("HBGJ") != -1) {
						sv.setPlatName("航班管家");
					} else if (acc.getAcctype().indexOf("TUNIU") != -1) {
						sv.setPlatName("途牛");
					} else if (acc.getAcctype().indexOf("TONGC") != -1) {
						sv.setPlatName("同程");
					} else if (acc.getAcctype().indexOf("QUNAR") != -1) {
						sv.setPlatName("去哪儿");
					} else if (acc.getAcctype().indexOf("CTRIP") != -1) {
						sv.setPlatName("携程");
					}
					accounts.add(sv);
				}
			}
		}
		return accounts;
	}

	private List<AirAccountVO> getAirAccount(String mno) throws Exception {
		List<AirAccountVO> accounts = new ArrayList<AirAccountVO>();
		List<AccountManageEntity> accs = factoryService.getAccountManageService().getCaches(GET_AIR_ACCOUNT_KEY, true);
		if (accs != null && accs.size() > 0) {
			for (AccountManageEntity acc : accs) {
				if (acc.getMno().equalsIgnoreCase(mno) && 1 == acc.getIsu()) {
					if ("C".equalsIgnoreCase(acc.getAccountType()) && "CP_GW".equalsIgnoreCase(acc.getVal())) {
						if (StringUtils.isNotEmpty(acc.getAircode())) {
							AirAccountVO cc = new AirAccountVO();
							cc.setLoginName(acc.getAccount());
							cc.setPassword(acc.getPassword());
							cc.setPurCode(acc.getAircode());
							cc.setFlightClass("");
							cc.setId(String.valueOf(acc.getId()));
							accounts.add(cc);
						}
					}
				}
			}
		}
		return accounts;
	}

	public LoginForGwTicketingResult loginSys(String json) throws Exception {
		LoginForGwTicketingResult rlt = new LoginForGwTicketingResult();
		LoginResult rs = login(json);
		if (rs.isLogin()) {
			rlt.setACCOUNT(rs.getLoginName());
			rlt.setOWN(rs.getName());
			rlt.setPERSONNAME(rs.getCompany());
			rlt.setPERSONUUID(String.valueOf(rs.getUserId()));
			rlt.setSAFETOKEN(rs.getToken());
		}
		return rlt;
	}

	public LoginResult login(String json) throws Exception {
		LoginResult rlt = new LoginResult();
		ErrResponse msgv = new ErrResponse();
		rlt.setErr(msgv);
		if (json != null && !"".equals(json.trim())) {
			LoginDomain vo = JsonPluginsUtil.jsonToFastBean(json, LoginDomain.class);
			if (vo != null) {
				if (StringUtils.isEmpty(vo.getLongName())) {
					msgv.setMsg("登陆用户名不能为空");
					msgv.setMsgcode("");
					return rlt;
				}
				if (StringUtils.isEmpty(vo.getPassword())) {
					msgv.setMsg("登陆密码不能为空");
					msgv.setMsgcode("");
					return rlt;
				}
				User user = new User();
				user.setLoginName(vo.getLongName());
				user.setPassword(SecurityUtils.getMD5(vo.getPassword()));
				user.setDeleted("N");
				List<User> users = factoryService.getUserService().getUser(user);
				User u = users != null && users.size() > 0 ? users.get(0) : null;
				if (u != null) {
					rlt.setName(u.getName());
					rlt.setLoginName(u.getLoginName());
					rlt.setSj(u.getSj());
					rlt.setUserId(u.getUserId());
					rlt.setToken(u.getMert().getAppCode());
					rlt.setLogin(true);
					rlt.setCompany(u.getMert().getCompanyName());
					rlt.setVersion(OOComms.VERSION);
				} else {
					rlt.setLogin(false);
					msgv.setMsg("用户或者密码错误");
				}
			} else {
				msgv.setMsg("参数序列化异常");
			}
		} else {
			msgv.setMsg("参数异常");
		}
		return rlt;
	}

}
