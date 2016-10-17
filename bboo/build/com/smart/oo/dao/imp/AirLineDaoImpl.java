package com.smart.oo.dao.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.comm.entity.AirLineEntity;
import com.smart.framework.base.BaseDAO;
import com.smart.framework.base.Page;
import com.smart.oo.dao.IAirLineDao;
import com.smart.utils.StringUtils;

@Repository("BBOOAirLineDaoImpl")
public  class AirLineDaoImpl extends BaseDAO<AirLineEntity, Integer>implements IAirLineDao {

	 /**
     * 查询所有
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirLineEntity> queryAirLine(AirLineEntity entity)
			throws Exception {
		// TODO Auto-generated method stub
		StringBuffer buf = new StringBuffer();
		@SuppressWarnings("rawtypes")
		List list = new ArrayList();
		buf.append("from AirLineEntity where 1=1 ");
		if (StringUtils.isNotEmpty(entity.getAir())) {
			buf.append(" and air=?");
			list.add(entity.getAir());
		}
		if (StringUtils.isNotEmpty(entity.getArr())) {
			buf.append(" and arr=?");
			list.add(entity.getArr());
		}
		if (StringUtils.isNotEmpty(entity.getDep())) {
			buf.append(" and dep=?");
			list.add(entity.getDep());
		}
		buf.append(" order by id desc");
		return this.find(buf.toString(), list.toArray());
	}

	/**
	 * 条件分页
	 */
	@Override
	public List<AirLineEntity> queryByPage(AirLineEntity entity, Page page)
			throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("from AirLineEntity where 1=1 ");
		if (StringUtils.isNotEmpty(entity.getDep())) {
			sb.append(" and dep='" + entity.getDep() + "'");
		}
		if (StringUtils.isNotEmpty(entity.getAir())) {
			sb.append(" and air='" + entity.getAir() + "'");
		}
		if (StringUtils.isNotEmpty(entity.getArr())) {
			sb.append(" and arr='"+entity.getArr()+"'");
		}
		/*sb.append(" and isDelete=0");*/
		sb.append(" order by id desc");
		return this.find(sb.toString(), page);
	}

	/**
	 * 添加保存
	 */
	@Override
	public void saveAirLine(AirLineEntity entity) throws Exception {
		// TODO Auto-generated method stub
		this.save(entity);
	}
	/**
	 * 删除
	 */
	@Override
	public void deleteAirLine(Integer id) throws Exception {
		// TODO Auto-generated method stub
		this.delete(id);
	}

	/**
	 * 修改
	 */
	@Override
	public void updateAirLine(AirLineEntity entity) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("update AirLineEntity ");
		sb.append(" set air='"+entity.getAir()+"'");
		sb.append(" , arr='"+entity.getArr()+"'");
		sb.append(" , dep='"+entity.getDep()+"'");
		sb.append(" , inat='"+entity.getInat()+"'");
		sb.append(" , isu='"+entity.getIsu()+"'");
		sb.append(" where id="+entity.getId());
		this.executeHql(sb.toString());
	}

}
