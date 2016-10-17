package com.smart.oo.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.comm.entity.AccountManageEntity;
import com.smart.framework.base.Page;
import com.smart.oo.cache.CacheService;
import com.smart.oo.dao.imp.FactoryDaoImpl;
import com.smart.oo.service.IAccountManageService;

@Service("BBOOAccountManageServiceImpl")
public class AccountManageServiceImpl extends CacheService<AccountManageEntity> implements IAccountManageService {
	
	@Autowired
	private FactoryDaoImpl daof;

	private static final String CACHENAME = "DB_BBOO_ACCOUNT_MANAGE_DATA_NAME_0006";
	
	@Override
	public List<AccountManageEntity> getCaches(String cacheKey, boolean iscache)
			throws Exception {
		if (iscache) {
			if (!checkKey(CACHENAME, cacheKey)) {
				List<AccountManageEntity> datas = this.daof.getAccountManageDao().findList(null);
				if (null != datas && datas.size() > 0) {
					this.setInCaches(CACHENAME, cacheKey, datas);
					return datas;
				} else {
					return null;
				}
			} else {
				List<AccountManageEntity> datas = this.getInCaches(CACHENAME, cacheKey);
				if (null != datas && datas.size() > 0) {
					return datas;
				} else {
					return null;
				}
			}
		} else {
			return this.daof.getAccountManageDao().findList(null);
		}
	}

	@Override
	public Integer saveAccountManage(AccountManageEntity entity) throws Exception {
		this.delCaches(CACHENAME);
		return this.daof.getAccountManageDao().saveAccountManage(entity);
	}

	@Override
	public List<AccountManageEntity> findList(AccountManageEntity entity)
			throws Exception {
		return this.daof.getAccountManageDao().findList(entity);
	}

	@Override
	public List<AccountManageEntity> findByPage(AccountManageEntity entity, Page page)
			throws Exception {
		return this.daof.getAccountManageDao().findByPage(entity, page);
	}

	@Override
	public void deleteById(Integer id, String mno) throws Exception {
		this.delCaches(CACHENAME);
		this.daof.getAccountManageDao().deleteById(id, mno);
	}

	@Override
	public void updateAccountManage(AccountManageEntity entity) throws Exception {
		this.delCaches(CACHENAME);
		this.daof.getAccountManageDao().updateEntity(entity);
	}

}
