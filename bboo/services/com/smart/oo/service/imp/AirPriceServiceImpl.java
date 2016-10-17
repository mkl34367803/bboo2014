package com.smart.oo.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.comm.entity.AirPriceEntity;
import com.smart.framework.base.Page;
import com.smart.oo.dao.imp.FactoryDaoImpl;
import com.smart.oo.service.IAirPriceService;

@Service("BBOOAirPriceServiceImpl")
public class AirPriceServiceImpl implements IAirPriceService {

	@Autowired
	private FactoryDaoImpl factory;
	@Autowired 
	private FactoryDaoImpl factoryDao;
	@Override
	public String saveData(AirPriceEntity en) throws Exception {
		// TODO Auto-generated method stub
		return factory.getAirPriceDao().saveData(en);
	}

	@Override
	public void saveDataAndDelByLine(List<AirPriceEntity> ens) throws Exception {
		// TODO Auto-generated method stub
		if (ens != null && ens.size() > 0) {
			AirPriceEntity delE = null;
			for (AirPriceEntity en : ens) {
				factory.getAirPriceDao().saveData(en);
				if (delE == null)
					delE = en;
			}
			if (delE != null) {
				AirPriceEntity delEn = new AirPriceEntity();
				delEn.setDepCode(delE.getDepCode());
				delEn.setArrCode(delE.getArrCode());
				delEn.setKeys(String.valueOf(Long.parseLong(delE.getKeys()) - 1 * 60 * 60));
				delEn.setKeyID(delE.getKeyID());
				factory.getAirPriceDao().del(delEn);
			}
		}
	}

	@Override
	public void saveDataAndDelByKey(List<AirPriceEntity> ens) throws Exception {
		// TODO Auto-generated method stub
		if (ens != null && ens.size() > 0) {
			AirPriceEntity delE = null;
			for (AirPriceEntity en : ens) {
				if (en.getIsDelete() == 0)
					factory.getAirPriceDao().saveData(en);
				delE = en;
				if (delE != null) {
					AirPriceEntity delEn = new AirPriceEntity();
					delEn.setDepCode(delE.getDepCode());
					delEn.setArrCode(delE.getArrCode());
					delEn.setKeyID(delE.getKeyID());
					delEn.setKeys(String.valueOf(Long.parseLong(delE.getKeys()) - 1 * 60 * 60));
					factory.getAirPriceDao().del(delEn);
				}
			}
		}
	}

	@Override
	public void del(AirPriceEntity entity) throws Exception {
		// TODO Auto-generated method stub
		factory.getAirPriceDao().del(entity);
	}

	@Override
	public List<AirPriceEntity> findDatas(AirPriceEntity en) throws Exception {
		// TODO Auto-generated method stub
		return factory.getAirPriceDao().findDatas(en);
	}

	@Override
	public List<AirPriceEntity> findDatas(AirPriceEntity en, Page p)
			throws Exception {
		// TODO Auto-generated method stub
		return factory.getAirPriceDao().findDatas(en, p);
	}

	@Override
	public AirPriceEntity barrierById(String id) throws Exception {
		// TODO Auto-generated method stub
		return factory.getAirPriceDao().barrierById(id);
	}

	
	
	/**
	 * 查询所有
	 */
	@Override
	public List<AirPriceEntity> queryAirPrice(AirPriceEntity entity)
			throws Exception {
		// TODO Auto-generated method stub
		
		return this.factoryDao.getAirPriceDao().queryAirPrice(entity);
	}
	/**
	 * 分页
	 */
	@Override
	public List<AirPriceEntity> queryByPage(AirPriceEntity entity,
			Page page) throws Exception {
		return this.factoryDao.getAirPriceDao().queryByPage(entity, page);
	}
	/**
	 * 添加保存
	 */
	@Override
	public void saveAirPrice(AirPriceEntity entity) throws Exception {
		this.factoryDao.getAirPriceDao().saveAirPrice(entity);
	}
	/**
	 * 删除
	 */
	@Override
	public void deleteAirPrice(String id) throws Exception {
		this.factoryDao.getAirPriceDao().deleteAirPrice(id);
	}
	/**
	 * 修改
	 */
	@Override
	public void updateAirPrice(AirPriceEntity entity) throws Exception {
		this.factoryDao.getAirPriceDao().updateAirPrice(entity);
	}

	
	
}
