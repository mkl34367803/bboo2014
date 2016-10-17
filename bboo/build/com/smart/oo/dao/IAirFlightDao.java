package com.smart.oo.dao;

import java.util.List;

import com.smart.comm.entity.AirFlightEntity;
import com.smart.framework.base.Page;

public interface IAirFlightDao {

	Integer saveData(AirFlightEntity d) throws Exception;

	List<AirFlightEntity> findByLineno(AirFlightEntity e) throws Exception;

	List<AirFlightEntity> findShares(AirFlightEntity e) throws Exception;

	void deleteAll() throws Exception;
	
	void deleteData(AirFlightEntity e) throws Exception;
	
	/**
	 * 查询
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	List<AirFlightEntity> queryAirFlight(AirFlightEntity entity) throws Exception;
	
	/**
	 * 分页查询
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	List<AirFlightEntity> queryByPage(AirFlightEntity entity, Page page) throws Exception;
	
	/**
	 * 保存
	 * @param entity
	 * @throws Exception
	 */
	void saveAirFlight(AirFlightEntity entity) throws Exception;
	
	
	/**
	 * 修改
	 * @param entity
	 * @throws Exception
	 */
	void updateAirFlight(AirFlightEntity entity) throws Exception;
}
