package com.smart.oo.dao.imp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.comm.entity.ProcessLogEntity;
import com.smart.comm.utils.DateUtil;
import com.smart.comm.utils.ProduceHqlUtil;
import com.smart.framework.base.BaseDAO;
import com.smart.framework.base.Page;
import com.smart.oo.dao.IProcessLogDao;
import com.smart.utils.StringUtils;

@Repository("BBOOProcessLogDaoImpl")
public class ProcessLogDaoImpl extends BaseDAO<ProcessLogEntity, String> implements IProcessLogDao {

	private static final long serialVersionUID = -1847756396851268513L;

	@Override
	public List<ProcessLogEntity> queryList(ProcessLogEntity entity, String endDate)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sb.append("from ProcessLogEntity where 1=1");
		if (StringUtils.isNotEmpty(entity.getLogType())) {
			sb.append(" and logType = ?");
			params.add(entity.getLogType());
		}
		if (StringUtils.isNotEmpty(entity.getOperator())) {
			sb.append(" and operator=?");
			params.add(entity.getOperator());
		}
		if (StringUtils.isNotEmpty(entity.getCreateTime()) && StringUtils.isNotEmpty(endDate)) {
			sb.append(" and createTime >= ? and createTime < ? ");
			params.add(entity.getCreateTime());
			params.add(DateUtil.stringDatePlusOne(endDate));
		} else {
			Calendar c = Calendar.getInstance();
			int y = c.get(Calendar.YEAR);
			sb.append(" and createTime >= ? and createTime < ? ");
			params.add(y+"");
			params.add((y+1)+"");
		}
		sb.append(" order by createTime desc");
		return this.find(sb.toString(), params.toArray());
	}

	@Override
	public void saveEntity(ProcessLogEntity entity) throws Exception {
		this.save(entity);
	}

	@Override
	public void updateEntity(ProcessLogEntity entity) throws Exception {
		String hql = ProduceHqlUtil.getUpdateByIdHql(entity);
		this.executeHql(hql);
	}

	@Override
	public void deleteEntity(String id) throws Exception {
		this.delete(id);
	}

	@Override
	public List<ProcessLogEntity> queryByPage(ProcessLogEntity entity,
			String endDate, Page page) throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sb.append("from ProcessLogEntity where 1=1");
		if (StringUtils.isNotEmpty(entity.getLogType())) {
			sb.append(" and logType = ?");
			params.add(entity.getLogType());
		}
		if (StringUtils.isNotEmpty(entity.getOperator())) {
			sb.append(" and operator=?");
			params.add(entity.getOperator());
		}
		if (StringUtils.isNotEmpty(entity.getCreateTime()) && StringUtils.isNotEmpty(endDate)) {
			sb.append(" and createTime >= ? and createTime < ? ");
			params.add(entity.getCreateTime());
			params.add(DateUtil.stringDatePlusOne(endDate));
		} else {
			Calendar c = Calendar.getInstance();
			int y = c.get(Calendar.YEAR);
			sb.append(" and createTime >= ? and createTime < ? ");
			params.add(y+"");
			params.add((y+1)+"");
		}
		sb.append(" order by createTime desc");
		return this.find(sb.toString(), params.toArray(), page);
	}

}
