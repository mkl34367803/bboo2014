package com.smart.oo.service;

import java.util.List;

import com.smart.comm.entity.AirLineEntity;
import com.smart.framework.base.Page;

public interface IAirLineService {
	/**
	 * 查询
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	List<AirLineEntity> queryAirLine(AirLineEntity entity) throws Exception;
	
	/**
	 * 分页查询
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	List<AirLineEntity> queryByPage(AirLineEntity entity, Page page) throws Exception;
	
	/**
	 * 保存
	 * @param entity
	 * @throws Exception
	 */
	void saveAirLine(AirLineEntity entity) throws Exception;
	
	/**
	 * 删除
	 * @param id
	 * @throws Exception
	 */
	void deleteAirLine(Integer id) throws Exception;
	
	/**
	 * 修改
	 * @param entity
	 * @throws Exception
	 */
	void updateAirLine(AirLineEntity entity) throws Exception;

}
