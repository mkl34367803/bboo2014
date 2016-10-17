package com.smart.oo.service.trigger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;

import com.smart.comm.entity.GjSaleOrderEntity;
import com.smart.comm.entity.GjSalePassengerEntity;
import com.smart.comm.entity.ProductDataEntity;
import com.smart.comm.entity.ProductPriceEntity;
import com.smart.comm.utils.BBOOConstants;
import com.smart.oo.dao.imp.GjSaleOrderDaoImpl;
import com.smart.oo.dao.imp.GjSalePassengerDaoImpl;
import com.smart.oo.dao.imp.ProductPriceDaoImpl;
import com.smart.oo.domain.res.SelectOrderDetailResult;
import com.smart.oo.service.imp.CommonMethodServiceImpl;
import com.smart.oo.service.imp.GjSaleOrderServiceImpl;
import com.smart.oo.service.imp.ProductDataServiceImpl;
import com.smart.oo.vo.OrderAndPassengerParamVo;
import com.smart.oo.vo.SaleOrderAndPassengerVo;
import com.smart.utils.DateUtils;
import com.smart.utils.JsonPluginsUtil;

public class MainTest {
	/**
	 * 获得一个对象各个属性的字节流
	 */
	@SuppressWarnings("rawtypes")
	public static void getProperty(Object entityName) throws Exception {
		Class c = entityName.getClass();
		Field field[] = c.getDeclaredFields();
		for (Field f : field) {
			Object v = invokeMethod(entityName, f.getName(), null);
			if (v != null) {
				System.out.println(f.getName() + "\t" + v + "\t" + v.toString().length());
			}
		}
	}

	/**
	 * 获得对象属性的值
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object invokeMethod(Object owner, String methodName, Object[] args) throws Exception {
		Class ownerClass = owner.getClass();
		methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
		Method method = null;
		try {
			method = ownerClass.getMethod("get" + methodName);
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
			return " can't find 'get" + methodName + "' method";
		}
		return method.invoke(owner);
	}
	public static void main7(String[] args) throws Exception {
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		GjSaleOrderDaoImpl gjSaleOrderDao=(GjSaleOrderDaoImpl) context.getBean("GjSaleOrderDaoImpl");
		GjSaleOrderServiceImpl gjSaleOrderServiceImpl=(GjSaleOrderServiceImpl) context.getBean("GjSaleOrderServiceImpl");
		GjSalePassengerDaoImpl gjSalePassengerDaoImpl=(GjSalePassengerDaoImpl)context.getBean("GjSalePassengerDaoImpl");
		GjSaleOrderEntity entity=gjSaleOrderServiceImpl.queryOrderByID("1472619658080000898");
		entity.setRemark("woshiyuanqiao");
//		getProperty(entity);
//		gjSaleOrderServiceImpl.fillCabinNumber(entity);
//		gjSaleOrderDao.update(entity);
//		entity.setId("1470048261703000027");
//		entity.setAccountInfo("accountyuanqiao");
//		gjSaleOrderDao.update(entity);
	}
	public static void main22(String[] args) throws Exception {
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		ProductDataServiceImpl productDataDaoImpl=(ProductDataServiceImpl) context.getBean("ProductDataServiceImpl");
		List<ProductDataEntity> productDataEntities= productDataDaoImpl.getProductDataByOrderId("1473314206826015812");
		System.out.println("hello world");
	}
	public static void main5(String[] args) throws Exception {
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		ProductPriceDaoImpl productDataDaoImpl=(ProductPriceDaoImpl) context.getBean("ProductPriceDaoImpl");
		List<? extends ProductPriceEntity> list=productDataDaoImpl.getProductPriceByOrderIdBySQL("1473314206826015812");
		System.out.println(list.size());
	}
	public static void main9(String[] args) throws Exception {
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		GjSaleOrderDaoImpl gjSaleOrderDao=(GjSaleOrderDaoImpl) context.getBean("GjSaleOrderDaoImpl");
		GjSaleOrderEntity entity=gjSaleOrderDao.queryOrderByID("1472006912454000466");
		//GjSaleOrderEntity entity=new GjSaleOrderEntity();
		entity.setId("1472006912454000466");
		entity.setAccountInfo("216481701");
		entity.setPnrCode("132");
		entity.setOldPnrCode("123");
		gjSaleOrderDao.updateByID(entity);
	}
	/**
	 * 用于测试自动同步国内订单的方法的。
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		SyncNationOrderTrigger syncNationOrderTrigger=(SyncNationOrderTrigger) context.getBean("BBOOSyncNationOrderTrigger");
		syncNationOrderTrigger.syncNationalOrder();
	}
	/**
	 * 通过国际的订单
	 * @param args
	 */
	public static void main2(String[] args) {
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		SyncNationOrderTrigger syncNationOrderTrigger=(SyncNationOrderTrigger) context.getBean("BBOOSyncNationOrderTrigger");
		syncNationOrderTrigger.syncOrder();
	}
	/**
	 * 从分销商同步（国内）票号到本地数据库
	 * @param args
	 */
	public static void main11(String[] args) {
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		ModifyOrderStateTrigger modifyOrderStateTrigger=(ModifyOrderStateTrigger) context.getBean("BBOOModifyOrderStateTrigger");
		modifyOrderStateTrigger.syncGnOrderState();
	}
	/**
	 * 从分销商同步（国际）票号到本地数据库
	 * @param args
	 */
	public static void main12(String[] args) {
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		ModifyOrderStateTrigger modifyOrderStateTrigger=(ModifyOrderStateTrigger) context.getBean("BBOOModifyOrderStateTrigger");
		modifyOrderStateTrigger.syncGjOrderState();
	}
	

	
	public static void main1(String[] args) {
		List<String> strings=new ArrayList<String>();
		System.out.println(strings.size());
		for(String string:strings){
			System.out.println(string);
		}
	}
	
	public static void main3(String[] args) throws Exception {
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		GjSaleOrderDaoImpl gjSaleOrderDao=(GjSaleOrderDaoImpl) context.getBean("GjSaleOrderDaoImpl");
		GjSaleOrderServiceImpl gjSaleOrderServiceImpl=(GjSaleOrderServiceImpl) context.getBean("GjSaleOrderServiceImpl");
		GjSaleOrderEntity entity1=gjSaleOrderDao.queryOrderByID("1473133250727000497");
		GjSaleOrderEntity entity2=gjSaleOrderDao.queryOrderByID("1473133250727000497");
		System.out.println(entity1.hashCode());
		System.out.println(entity2.hashCode());
		System.out.println("i am here!");
	}
	public static void main33(String[] args) throws Exception {
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		GjSaleOrderDaoImpl gjSaleOrderDao=(GjSaleOrderDaoImpl) context.getBean("GjSaleOrderDaoImpl");
		GjSaleOrderServiceImpl gjSaleOrderServiceImpl=(GjSaleOrderServiceImpl) context.getBean("GjSaleOrderServiceImpl");
		Date endTime = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endTime);
		calendar.add(Calendar.MONTH, -1); // 时间减一个月
		Date startTime = calendar.getTime();
		OrderAndPassengerParamVo orderAndPassengerParamVo = new OrderAndPassengerParamVo();
		orderAndPassengerParamVo.setStartCreateTime(DateUtils.formatDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		orderAndPassengerParamVo.setEndCreateTime(DateUtils.formatDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		orderAndPassengerParamVo.setSlfStatus(BBOOConstants.GjSaleOrderEntity_SLFSTATUS_FIVE);// 出票中的订单
		orderAndPassengerParamVo.setFlightClass("I");// I表示国际的订单
		List<GjSaleOrderEntity> gjSaleOrderEntities=gjSaleOrderServiceImpl.queryOrderAndPassengerByCreateTime1(orderAndPassengerParamVo);
		System.out.println("I am here!");
	
	}
	@SuppressWarnings("unused")
	public static void main20(String[] args) throws Exception {
		
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		ProductPriceDaoImpl yuanQiaoDaoImpl=(ProductPriceDaoImpl) context.getBean("ProductPriceDaoImpl");
		List<ProductPriceEntity> result=yuanQiaoDaoImpl.getProductPriceByOrderId("1473314206768015233");
		System.out.println("hello world");
	}
	
	public static void main10(String[] args) {
		GjSaleOrderEntity gjSaleOrderEntity=new GjSaleOrderEntity();
		System.out.println(gjSaleOrderEntity.getIsSplit());
		List<String> list=new ArrayList<String>();
		list.add("test");
		list.add("test");
		list.add("test");
		list.add("test");
		list.add("test");
		list.add("test");
		list.add("test");
		for(int i=0;i<list.size();i++){
			if(i==3){
				break;
			}
			System.out.println(i);
		}
	}
	
	public static void main13(String[] args) throws Exception {
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		CommonMethodServiceImpl yuanQiaoDaoImpl=(CommonMethodServiceImpl) context.getBean("CommonMethodServiceImpl");
		SelectOrderDetailResult selectOrderDetailResult= yuanQiaoDaoImpl.getOrderDetailByThirdInterface("1471946961519000121", "10");
		String jsonString=JsonPluginsUtil.beanToJson(selectOrderDetailResult);
		System.out.println(jsonString);
	}
	
	
	/**
	 * 同步票号
	 * @param args
	 */
	public static void main34(String[] args) {
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		SyncTicketNoStateTrigger syncTicketNoStateTrigger=(SyncTicketNoStateTrigger) context.getBean("BBOOSyncTicketNoStateTrigger");
		syncTicketNoStateTrigger.syncTicketNoState();
	}
	
	/**
	 * 系统自动支付订单
	 * @param args
	 */
	public static void main4(String[] args) {
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		AutoPayOrderTrigger autoPayOrderTrigger=(AutoPayOrderTrigger) context.getBean("BBOOAutoPayOrderTrigger");
		autoPayOrderTrigger.autoPayOrder();
	}
}
