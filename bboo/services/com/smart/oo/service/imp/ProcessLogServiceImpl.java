package com.smart.oo.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.comm.entity.ProcessLogEntity;
import com.smart.framework.base.Page;
import com.smart.oo.dao.imp.FactoryDaoImpl;
import com.smart.oo.service.IProcessLogService;

@Service("BBOOProcessLogServiceImpl")
public class ProcessLogServiceImpl implements IProcessLogService {
	
	@Autowired
	private FactoryDaoImpl factoryDaoImpl;

	@Override
	public List<ProcessLogEntity> queryList(ProcessLogEntity entity, String endDate)
			throws Exception {
		return this.factoryDaoImpl.getProcessLogDao().queryList(entity, endDate);
	}

	@Override
	public void saveEntity(ProcessLogEntity entity) throws Exception {
		this.factoryDaoImpl.getProcessLogDao().saveEntity(entity);
	}

	@Override
	public void updateEntity(ProcessLogEntity entity) throws Exception {
		this.factoryDaoImpl.getProcessLogDao().updateEntity(entity);
	}

	@Override
	public void deleteEntity(String id) throws Exception {
		this.factoryDaoImpl.getProcessLogDao().deleteEntity(id);
	}

	@Override
	public List<ProcessLogEntity> queryByPage(ProcessLogEntity entity,
			String endDate, Page page) throws Exception {
		return this.factoryDaoImpl.getProcessLogDao().queryByPage(entity, endDate, page);
	}

}
