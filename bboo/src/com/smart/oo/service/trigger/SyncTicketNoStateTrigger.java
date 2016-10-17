package com.smart.oo.service.trigger;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.comm.entity.BaseAccountEntity;
import com.smart.comm.entity.GjBuyOrderEntity;
import com.smart.comm.entity.GjSaleOrderEntity;
import com.smart.comm.entity.GjSalePassengerEntity;
import com.smart.comm.entity.SysLogEntity;
import com.smart.comm.utils.BBOOConstants;
import com.smart.comm.utils.DateUtil;
import com.smart.comm.utils.OOLogUtil;
import com.smart.oo.dao.imp.FactoryDaoImpl;
import com.smart.oo.domain.res.AirPsgVO;
import com.smart.oo.domain.res.DistributionModifyOrderInfoResult;
import com.smart.oo.domain.res.GetOrderForPb2bResult;
import com.smart.oo.domain.res.GetPnrInfoResult;
import com.smart.oo.domain.res.GjSaleOrderVO;
import com.smart.oo.domain.res.Pb2bOrderElementVO;
import com.smart.oo.domain.res.RtPnrPassengerVO;
import com.smart.oo.service.imp.FactoryServiceImpl;
import com.smart.utils.DateUtils;
import com.smart.utils.StringUtils;

/**
 * 定时获取采购方的订单信息,并将订单的状态同步到本地数据库,以及第三方分销商平台!
 * @author Administrator
 *
 */
@Service("BBOOSyncTicketNoStateTrigger")
public class SyncTicketNoStateTrigger {
	@Autowired
	private FactoryServiceImpl factoryService;
	@Autowired
	private FactoryDaoImpl factoryDao;
	public void syncTicketNoState(){
		try {
			//查询进入系统超过2分钟还没有回填票号的订单
			//List<String> orderIds= factoryDao.getGjBuyOrderDao().queryOrderIdNeededTicketNo();
			List<String> orderIds= factoryDao.getSaleOrderDao().queryOrderIdNeededTicketNo();
			// 筛选一下商户，将禁用的商户踢出，将非国际的商户踢出。
			
			if (orderIds != null && orderIds.size() > 0) {
				ExecutorService executorService = Executors.newCachedThreadPool();
				Future<String> f = null;
				List<Future<String>> flist = new ArrayList<Future<String>>();
				final Semaphore semp = new Semaphore(orderIds.size());
				Callable<String> call = null;
				String startTime = DateUtils.getDateys(); // 线程池开始时间
				for (int i=0;i*20<orderIds.size();i++) {
					final List<String> tempOrderIds=new ArrayList<String>();
					if(i*20+20>orderIds.size()){
						for(int j=i*20;j<orderIds.size();j++){
							tempOrderIds.add(orderIds.get(j));
						}
					}else{
						for(int j=i*20;j<(i+1)*20;j++){
							tempOrderIds.add(orderIds.get(j));
						}
					}
					call = new Callable<String>() {
						@Override
						public String call() {
							// 获取许可
							try {
								semp.acquire();
								syncTicketNoByThread(tempOrderIds);
								// 访问完后，释放
								semp.release();
							} catch (Exception e) {
								e.printStackTrace();
							}
							return "true";
						}
					};
					f = executorService.submit(call);
					flist.add(f);

				}
				executorService.shutdown();
				if (flist.size() > 0) {
					while (!executorService.isTerminated()) {
						// 等待所有子线程结束，才退出主线程
						OOLogUtil.info("同步票号任务正在进行中...", SyncNationOrderTrigger.class, null);
						try {
							executorService.awaitTermination(30, TimeUnit.SECONDS);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					OOLogUtil.info("同步票号任务结束..." + "START-TIME:" + startTime + "END-TIME:" + DateUtils.getDateys(), QueryTicketStateTrigger.class, null);
					executorService.shutdownNow();
				}
			} else {
				OOLogUtil.info("同步票号任务开启无数据...", SyncNationOrderTrigger.class, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 单个线程，用于同步一批订单的票号的单线程方法
	 */
	public void syncTicketNoByThread(List<String> orderIds) throws Exception{
		if(orderIds!=null&&orderIds.size()>0){
			for(String id:orderIds){
				GjSaleOrderEntity gjSaleOrderEntity=factoryDao.getSaleOrderDao().queryOrderByID(id);
				GjBuyOrderEntity gjBuyOrderEntity= factoryDao.getGjBuyOrderDao().queyOrderByID(id);
				BaseAccountEntity saleBaseAccountEntity=factoryDao.getBaseAccountDao().queryBaseAccountByID(gjSaleOrderEntity.getAccountid());
				BaseAccountEntity buyBaseAccountEntity=factoryDao.getBaseAccountDao().queryBaseAccountByID(gjBuyOrderEntity.getAccountid());
				if(buyBaseAccountEntity!=null){
					if(StringUtils.isNotEmpty(gjBuyOrderEntity.getPurchaseNo())){ //注意,通过bsp出票的订单是没有采购订单号的,所以去采购平台也查不到订单信息
						//这里条用b2b的接口获取票号信息,如果这里b2b接口异常了，或者没有返回信息，那么还是调用rt的接口获取票号信息。
						 GetOrderForPb2bResult getOrderForPb2bResult=factoryService.getAllApiService().getPurOrderDetial(gjBuyOrderEntity, buyBaseAccountEntity);
						 if(getOrderForPb2bResult!=null&&getOrderForPb2bResult.getRlts()!=null){
							 syncTicketNoByB2bResult(getOrderForPb2bResult,gjSaleOrderEntity,saleBaseAccountEntity);
						 }else{
							 if(gjSaleOrderEntity.getBacktimes()<3){
								 syncTicketNoByRt(gjSaleOrderEntity,saleBaseAccountEntity);
							 }
						 }
					}else{
						 if(gjSaleOrderEntity.getBacktimes()<3){
							 syncTicketNoByRt(gjSaleOrderEntity,saleBaseAccountEntity);
						 }
					}
				}else{
					 if(gjSaleOrderEntity.getBacktimes()<3){
						 syncTicketNoByRt(gjSaleOrderEntity,saleBaseAccountEntity);
					 }
				}
				
			}
			
		}
	}
	
	
	private void syncTicketNoByB2bResult(GetOrderForPb2bResult getOrderForPb2bResult,
			GjSaleOrderEntity gjSaleOrderEntity, BaseAccountEntity saleBaseAccountEntity) throws Exception {
		if(getOrderForPb2bResult!=null&&BBOOConstants.GjBuyOrderEntity_slfStatus_two.equals(getOrderForPb2bResult.getOrderState())){
			Pb2bOrderElementVO pb2bOrderElementVO=getOrderForPb2bResult.getRlts();
			if(pb2bOrderElementVO!=null){
				List<AirPsgVO> airPsgVOs=pb2bOrderElementVO.getPsgList();
				if(airPsgVOs!=null&&airPsgVOs.size()>0){
					int passengerCount=0;
					for(GjSalePassengerEntity gjSalePassengerEntity:gjSaleOrderEntity.getPassengers()){
						for(AirPsgVO airPsgVO:airPsgVOs){
							if(gjSalePassengerEntity.getName().equals(airPsgVO.getPsgName())||gjSalePassengerEntity.getCardNum().equals(airPsgVO.getCertNo())){
								if(StringUtils.isNotEmpty(airPsgVO.getTicketNo())){
									passengerCount++;
									gjSalePassengerEntity.setEticketNum(airPsgVO.getTicketNo());
									factoryDao.getSalePassengerDao().updateTicketNum(gjSaleOrderEntity.getId(), airPsgVO.getTicketNo());
									factoryDao.getGjBuyPassengerDao().updateTicketNum(gjSaleOrderEntity.getId(), airPsgVO.getTicketNo());
								}
							}
						}
					}
					if(passengerCount==gjSaleOrderEntity.getPassengers().size()){		//如果rt结果有票号的和乘机人的人数一样多,需要进行订单状态的更新
						fillOrderStatus(gjSaleOrderEntity, saleBaseAccountEntity);
					}
				}
			}
		}
	}

	private void syncTicketNoByRt(GjSaleOrderEntity gjSaleOrderEntity,BaseAccountEntity saleBaseAccountEntity) throws Exception {
		GetPnrInfoResult getPnrInfoResult=factoryService.getAllApiService().getRtByThirdInterface(gjSaleOrderEntity, false);
		if(getPnrInfoResult!=null){
			List<RtPnrPassengerVO> rtPnrPassengerVOs=getPnrInfoResult.getPassengers();
			if(rtPnrPassengerVOs!=null&&rtPnrPassengerVOs.size()>0){
				if(factoryService.getCommonMethodService().isPnrMatchPassenger(getPnrInfoResult, gjSaleOrderEntity)){
					int passengerCount=0;
					for(GjSalePassengerEntity gjSalePassengerEntity:gjSaleOrderEntity.getPassengers()){
						for(RtPnrPassengerVO rtPnrPassengerVO:rtPnrPassengerVOs){
							//证件号和名字满足一个即可
							if(gjSalePassengerEntity.getName().equals(rtPnrPassengerVO.getName())||gjSalePassengerEntity.getCardNum().equals(rtPnrPassengerVO.getCertNo())){
								if(StringUtils.isNotEmpty(rtPnrPassengerVO.getTicketNo())){ //前提是票号不为空,才做更新
									passengerCount++;
									gjSalePassengerEntity.setEticketNum(rtPnrPassengerVO.getTicketNo());
									factoryDao.getSalePassengerDao().updateTicketNum(gjSaleOrderEntity.getId(), rtPnrPassengerVO.getTicketNo());
									factoryDao.getGjBuyPassengerDao().updateTicketNum(gjSaleOrderEntity.getId(), rtPnrPassengerVO.getTicketNo());
								}
							}
						}
						
					}
					if(passengerCount==gjSaleOrderEntity.getPassengers().size()){		//如果rt结果有票号的和乘机人的人数一样多,需要进行订单状态的更新
						fillOrderStatus(gjSaleOrderEntity, saleBaseAccountEntity);
					}else{
						//如果回填票号rt3次乘机人的票号都没有全部回填成功,?那么也认为回填票号失败.
						GjSaleOrderEntity saleParamVo2=new GjSaleOrderEntity();
						saleParamVo2.setId(gjSaleOrderEntity.getId());
						saleParamVo2.setBacktimes(gjSaleOrderEntity.getBacktimes()+1);
						if(saleParamVo2.getBacktimes()>=3){
							saleParamVo2.setBackno(BBOOConstants.GjSaleOrderEntity_backno_three);
						}
						factoryDao.getSaleOrderDao().updateByID(saleParamVo2);
					}
				}else{
					//如果回填票号rt3次都没有获取到值?那么也认为回填票号失败.
					GjSaleOrderEntity saleParamVo2=new GjSaleOrderEntity();
					saleParamVo2.setId(gjSaleOrderEntity.getId());
					saleParamVo2.setBacktimes(gjSaleOrderEntity.getBacktimes()+1);
					if(saleParamVo2.getBacktimes()>=3){
						saleParamVo2.setBackno(BBOOConstants.GjSaleOrderEntity_backno_three);
					}
					factoryDao.getSaleOrderDao().updateByID(saleParamVo2);
				}
			}else{
				//如果回填票号rt3次都没有获取到值?那么也认为回填票号失败.
				GjSaleOrderEntity saleParamVo2=new GjSaleOrderEntity();
				saleParamVo2.setId(gjSaleOrderEntity.getId());
				saleParamVo2.setBacktimes(gjSaleOrderEntity.getBacktimes()+1);
				if(saleParamVo2.getBacktimes()>=3){
					saleParamVo2.setBackno(BBOOConstants.GjSaleOrderEntity_backno_three);
				}
				factoryDao.getSaleOrderDao().updateByID(saleParamVo2);
			}
		}else{
			//如果回填票号rt3次都没有获取到值?那么也认为回填票号失败.
			GjSaleOrderEntity saleParamVo2=new GjSaleOrderEntity();
			saleParamVo2.setId(gjSaleOrderEntity.getId());
			saleParamVo2.setBacktimes(gjSaleOrderEntity.getBacktimes()+1);
			if(saleParamVo2.getBacktimes()>=3){
				saleParamVo2.setBackno(BBOOConstants.GjSaleOrderEntity_backno_three);
			}
			factoryDao.getSaleOrderDao().updateByID(saleParamVo2);
		}
		
	}

	/**
	 * 填写订单相关字段的信息
	 * @param gjSaleOrderEntity
	 * @param saleBaseAccountEntity
	 * @throws Exception
	 */
	private void fillOrderStatus(GjSaleOrderEntity gjSaleOrderEntity, BaseAccountEntity saleBaseAccountEntity)
			throws Exception {
			//如果所有的乘机人都有票号,那么更新订单状态为出票完成.
			GjSaleOrderEntity saleParamVo=new GjSaleOrderEntity();
			saleParamVo.setId(gjSaleOrderEntity.getId());
			saleParamVo.setStatus(BBOOConstants.GjBuyOrderEntity_slfStatus_two);
			saleParamVo.setSlfStatus(BBOOConstants.GjBuyOrderEntity_slfStatus_two);
			factoryDao.getSaleOrderDao().updateByID(saleParamVo);
			GjBuyOrderEntity buyParamVo=new GjBuyOrderEntity();
			buyParamVo.setId(gjSaleOrderEntity.getId());
			buyParamVo.setStatus(BBOOConstants.GjBuyOrderEntity_slfStatus_two);
			buyParamVo.setSlfStatus(BBOOConstants.GjBuyOrderEntity_slfStatus_two);
			factoryDao.getGjBuyOrderDao().updateById(buyParamVo);
			//有票号,就让他回填,回填多少次都没有关系的!
			try {
				DistributionModifyOrderInfoResult distributionModifyOrderInfoResult=factoryService.getAllApiService().modifyOrderByThirdInterface(gjSaleOrderEntity,saleBaseAccountEntity);
				if(distributionModifyOrderInfoResult!=null&&distributionModifyOrderInfoResult.isIsuss()){
					GjSaleOrderEntity saleParamVo2=new GjSaleOrderEntity();
					saleParamVo2.setId(gjSaleOrderEntity.getId());
					saleParamVo2.setBackno(BBOOConstants.GjSaleOrderEntity_backno_one);
					//saleParamVo2.setBacktimes(gjSaleOrderEntity.getBacktimes()+1);//严格的讲,这个backtime和真正调分销商接口回填没有任何关系,只和rt的次数有关系.
					factoryDao.getSaleOrderDao().updateByID(saleParamVo2);
					factoryService.getSysLogService().saveAutoDealSysLog(gjSaleOrderEntity.getId(), gjSaleOrderEntity.getMno(), "自动回填第三方票号成功");
				}else{
					GjSaleOrderEntity saleParamVo2=new GjSaleOrderEntity();
					saleParamVo2.setId(gjSaleOrderEntity.getId());
					saleParamVo2.setBackno(BBOOConstants.GjSaleOrderEntity_backno_three);
					//saleParamVo2.setBacktimes(gjSaleOrderEntity.getBacktimes()+1);//严格的讲,这个backtime和真正调分销商接口回填没有任何关系,只和rt的次数有关系.
					factoryDao.getSaleOrderDao().updateByID(saleParamVo2);
					factoryService.getSysLogService().saveAutoDealSysLog(gjSaleOrderEntity.getId(), gjSaleOrderEntity.getMno(), "自动回填第三方票号失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
				GjSaleOrderEntity saleParamVo2=new GjSaleOrderEntity();
				saleParamVo2.setId(gjSaleOrderEntity.getId());
				saleParamVo2.setBackno(BBOOConstants.GjSaleOrderEntity_backno_three);
				//saleParamVo2.setBacktimes(gjSaleOrderEntity.getBacktimes()+1);//严格的讲,这个backtime和真正调分销商接口回填没有任何关系,只和rt的次数有关系.
				factoryDao.getSaleOrderDao().updateByID(saleParamVo2);
			}
	}

}
