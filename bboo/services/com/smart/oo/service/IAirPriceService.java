package com.smart.oo.service;

import java.util.List;

import com.smart.comm.entity.AirPriceEntity;
import com.smart.framework.base.Page;

public interface IAirPriceService {

	String saveData(AirPriceEntity en) throws Exception;

	void del(AirPriceEntity entity) throws Exception;

	void saveDataAndDelByLine(List<AirPriceEntity> ens) throws Exception;

	void saveDataAndDelByKey(List<AirPriceEntity> ens) throws Exception;

	List<AirPriceEntity> findDatas(AirPriceEntity en) throws Exception;

	List<AirPriceEntity> findDatas(AirPriceEntity en, Page p) throws Exception;

	AirPriceEntity barrierById(String id) throws Exception;
	
	
	
	/**
	 * 查询
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	List<AirPriceEntity> queryAirPrice(AirPriceEntity entity) throws Exception;
	
	/**
	 * 分页查询
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	List<AirPriceEntity> queryByPage(AirPriceEntity entity, Page page) throws Exception;
	
	/**
	 * 保存
	 * @param entity
	 * @throws Exception
	 */
	void saveAirPrice(AirPriceEntity entity) throws Exception;
	
	/**
	 * 删除
	 * @param id
	 * @throws Exception
	 */
	void deleteAirPrice(String id) throws Exception;
	
	/**
	 * 修改
	 * @param entity
	 * @throws Exception
	 */
	void updateAirPrice(AirPriceEntity entity) throws Exception;

}
