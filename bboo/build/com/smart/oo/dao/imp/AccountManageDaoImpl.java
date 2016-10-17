package com.smart.oo.dao.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.comm.entity.AccountManageEntity;
import com.smart.framework.base.BaseDAO;
import com.smart.framework.base.Page;
import com.smart.oo.dao.IAccountManageDao;
import com.smart.utils.StringUtils;

@Repository("BBOOAccountManageDaoImpl")
public class AccountManageDaoImpl extends BaseDAO<AccountManageEntity, Integer> implements IAccountManageDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4479135737853003948L;

	@Override
	public Integer saveAccountManage(AccountManageEntity entity) throws Exception {
		return this.save(entity);
	}

	@Override
	public List<AccountManageEntity> findList(AccountManageEntity entity)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sb.append("from AccountManageEntity where 1=1");
		if (null != entity) {
			if (StringUtils.isNotEmpty(entity.getAccountType())) {
				sb.append(" and accountType = ?");
				params.add(entity.getAccountType());
			}
			if (StringUtils.isNotEmpty(entity.getValCh())) {
				sb.append(" and valCh = ?");
				params.add(entity.getValCh());
			}
			if (StringUtils.isNotEmpty(entity.getAccount())) {
				sb.append(" and account = ?");
				params.add(entity.getAccount());
			}
			if (StringUtils.isNotEmpty(entity.getAircode())) {
				sb.append(" and aircode = ?");
				params.add(entity.getAircode());
			}
			if (null != entity.getIsu()) {
				sb.append(" and isu = ?");
				params.add(entity.getIsu());
			}
			sb.append(" where mno = ?");
			params.add(entity.getMno());
		}
		return this.find(sb.toString(), params.toArray());
	}

	@Override
	public List<AccountManageEntity> findByPage(AccountManageEntity entity, Page page)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sb.append("from AccountManageEntity where 1=1");
		if (StringUtils.isNotEmpty(entity.getAccountType())) {
			sb.append(" and accountType = ?");
			params.add(entity.getAccountType());
		}
		if (StringUtils.isNotEmpty(entity.getValCh())) {
			sb.append(" and valCh = ?");
			params.add(entity.getValCh());
		}
		if (StringUtils.isNotEmpty(entity.getAccount())) {
			sb.append(" and account = ?");
			params.add(entity.getAccount());
		}
		if (StringUtils.isNotEmpty(entity.getAircode())) {
			sb.append(" and aircode = ?");
			params.add(entity.getAircode());
		}
		if (null != entity.getIsu()) {
			sb.append(" and isu = ?");
			params.add(entity.getIsu());
		}
		sb.append(" and mno = ?");
		sb.append(" order by account");
		params.add(entity.getMno());
		return this.find(sb.toString(), params.toArray(), page);
	}

	@Override
	public void deleteById(Integer id, String mno) throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sb.append("delete AccountManageEntity where id=? and mno=?");
		params.add(id);
		params.add(mno);
		this.executeHql(sb.toString(), params.toArray());
	}

	@Override
	public void updateEntity(AccountManageEntity entity) throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sb.append("update AccountManageEntity set");
		sb.append(" accountType = ?");
		params.add(entity.getAccountType());
		sb.append(" ,val = ?");
		params.add(entity.getVal());
		sb.append(" ,valCh = ?");
		params.add(entity.getValCh());
		sb.append(" ,account = ?");
		params.add(entity.getAccount());
		sb.append(" ,aircode = ?");
		params.add(entity.getAircode());
		sb.append(" ,isu = ?");
		params.add(entity.getIsu());
		sb.append(" ,password = ?");
		params.add(entity.getPassword());
		sb.append(" where id = ? and mno = ?");
		params.add(entity.getId());
		params.add(entity.getMno());
		this.executeHql(sb.toString(), params.toArray());
	}


}
