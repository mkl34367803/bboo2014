package com.smart.oo.service.imp;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.comm.entity.AirLineEntity;
import com.smart.framework.base.Page;
import com.smart.oo.dao.imp.FactoryDaoImpl;
import com.smart.oo.service.IAirLineService;

@Service("BBOOAirLineServiceImpl")
public class AirLineServiceImpl implements IAirLineService {
	@Autowired 
	private FactoryDaoImpl factoryDao;
	/**
	 * 查询
	 */
	@Override
	public List<AirLineEntity> queryAirLine(AirLineEntity entity)
			throws Exception {
		// TODO Auto-generated method stub
		return this.factoryDao.getAirLineDao().queryAirLine(entity);
	}
    /**
     * 条件分页
     */
	@Override
	public List<AirLineEntity> queryByPage(AirLineEntity entity, Page page)
			throws Exception {
		// TODO Auto-generated method stub
		return this.factoryDao.getAirLineDao().queryByPage(entity, page);
	}
	 /**
	  * 添加保存
	  */
	@Override
	public void saveAirLine(AirLineEntity entity) throws Exception {
		// TODO Auto-generated method stub
		this.factoryDao.getAirLineDao().saveAirLine(entity);
	}
	/**
	 * 删除
	 */
	@Override
	public void deleteAirLine(Integer id) throws Exception {
		this.factoryDao.getAirLineDao().deleteAirLine(id);
	}
    /**
     * 修改
     */
	@Override
	public void updateAirLine(AirLineEntity entity) throws Exception {
		// TODO Auto-generated method stub
		this.factoryDao.getAirLineDao().updateAirLine(entity);
	}

}
