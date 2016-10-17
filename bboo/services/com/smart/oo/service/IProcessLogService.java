package com.smart.oo.service;

import java.util.List;

import com.smart.comm.entity.ProcessLogEntity;
import com.smart.framework.base.Page;

public interface IProcessLogService {

	/**
	 * 查询
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	List<ProcessLogEntity> queryList(ProcessLogEntity entity, String endDate) throws Exception;

	/**
	 * 保存
	 * @param entity
	 * @throws Exception
	 */
	void saveEntity(ProcessLogEntity entity) throws Exception;
	
	/**
	 * 修改
	 * @param entity
	 * @throws Exception
	 */
	void updateEntity(ProcessLogEntity entity) throws Exception;
	
	/**
	 * 删除
	 * @param id
	 * @throws Exception
	 */
	void deleteEntity(String id) throws Exception;
	
	/**
	 * 分页查询
	 * @param entity
	 * @param endDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<ProcessLogEntity> queryByPage(ProcessLogEntity entity, String endDate, Page page) throws Exception;
}
