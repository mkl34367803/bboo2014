package com.smart.oo.service.trigger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.comm.entity.GjBuyOrderEntity;
import com.smart.comm.utils.OOLogUtil;
import com.smart.oo.dao.imp.FactoryDaoImpl;
import com.smart.oo.service.imp.FactoryServiceImpl;
import com.smart.utils.DateUtils;

@Service("BBOOAutoPayOrderTrigger")
public class AutoPayOrderTrigger {
	@Autowired
	private FactoryServiceImpl factoryService;
	@Autowired
	private FactoryDaoImpl factoryDao;
	/**
	 * 自动支付某些订单<br>
	 * 首先将需要自动支付的订单找出来,然后支付<br>
	 */
	public void autoPayOrder(){
		try {
			//查询进入系统超过2分钟还没有回填票号的订单
			List<String> orderIds= factoryDao.getGjBuyOrderDao().queryOrderIdNeedAutoPay();
			// 筛选一下商户，将禁用的商户踢出，将非国际的商户踢出。
			
			if (orderIds != null && orderIds.size() > 0) {
				ExecutorService executorService = Executors.newCachedThreadPool();
				Future<String> f = null;
				List<Future<String>> flist = new ArrayList<Future<String>>();
				final Semaphore semp = new Semaphore(orderIds.size());
				Callable<String> call = null;
				String startTime = DateUtils.getDateys(); // 线程池开始时间
				//老大说支付的时候,一个订单开一个线程,因为支付比较慢!
				for (final String orderId:orderIds) {
//					final List<String> tempOrderIds=new ArrayList<String>();
//					if(i*20+20>orderIds.size()){
//						for(int j=i*20;j<orderIds.size();j++){
//							tempOrderIds.add(orderIds.get(j));
//						}
//					}else{
//						for(int j=i*20;j<(i+1)*20;j++){
//							tempOrderIds.add(orderIds.get(j));
//						}
//					}
					call = new Callable<String>() {
						@Override
						public String call() {
							// 获取许可
							try {
								semp.acquire();
								autoPayOrderByThread(orderId);
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
						OOLogUtil.info("自动支付任务正在进行中...", SyncNationOrderTrigger.class, null);
						try {
							executorService.awaitTermination(30, TimeUnit.SECONDS);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					OOLogUtil.info("自动支付任务结束..." + "START-TIME:" + startTime + "END-TIME:" + DateUtils.getDateys(), QueryTicketStateTrigger.class, null);
					executorService.shutdownNow();
				}
			} else {
				OOLogUtil.info("自动支付任务开启无数据...", SyncNationOrderTrigger.class, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void autoPayOrderByThread(String tempOrderId) throws Exception {
			GjBuyOrderEntity gjBuyOrderEntity=factoryDao.getGjBuyOrderDao().queyOrderByID(tempOrderId);
			if(gjBuyOrderEntity!=null){
				factoryService.getGjBuyOrderService().payOrderByThirdInterface(gjBuyOrderEntity);
			}
	}
}
