package com.smart.oo.dao.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.comm.entity.StaffWorkEntity;
import com.smart.comm.utils.ProduceHqlUtil;
import com.smart.framework.base.BaseDAO;
import com.smart.framework.base.Page;
import com.smart.oo.dao.IStaffWorkDao;
import com.smart.oo.from.StaffWorkVo;
import com.smart.utils.StringUtils;

@Repository("BBOOStaffWorkImpl")
public class StaffWorkDaoImpl extends BaseDAO<StaffWorkEntity, Integer> implements IStaffWorkDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8743941943734826024L;
	
	@Override
	public List<Object[]> queryByPage(StaffWorkVo vo, Page page)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sb.append("select bu.user_id,bu.name,");
		sb.append("sw.id,sw.wtime_start,sw.wtime_end,sw.weeks,sw.flight_class,sw.work_type,sw.create_time ");
		sb.append(" from t_base_user bu");
		sb.append(" left join t_staff_work sw on sw.fk_user_id=bu.user_id");
		sb.append(" where 1=1");
		if (StringUtils.isNotEmpty(vo.getName())) {
			sb.append(" and bu.name=?");
			params.add(vo.getName());
		}
		if (StringUtils.isNotEmpty(vo.getFlightClass())) {
			sb.append(" and sw.flight_class=?");
			params.add(vo.getFlightClass());
		}
		if (StringUtils.isNotEmpty(vo.getWorkType())) {
			sb.append(" and sw.work_type=?");
			params.add(vo.getWorkType());
		}
		sb.append(" order by sw.flight_class desc,sw.work_type,bu.name");
		return this.findBySQL(sb.toString(), params.toArray(), page);
	}

	@Override
	public List<StaffWorkEntity> queryList(StaffWorkEntity entity)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sb.append("from StaffWorkEntity where 1=1");
		if (null != entity.getId()) {
			sb.append(" and id=?");
			params.add(entity.getId());
		}
		if (null != entity.getFkUserId()) {
			sb.append(" and fkUserId=?");
			params.add(entity.getFkUserId());
		}
		if (null != entity.getSignIn()) {
			sb.append(" and signIn=?");
			params.add(entity.getSignIn());
		}
		if (StringUtils.isNotEmpty(entity.getFlightClass())) {
			sb.append(" and flightClass=?");
			params.add(entity.getFlightClass());
		}
		if (StringUtils.isNotEmpty(entity.getWorkType())) {
			sb.append(" and workType=?");
			params.add(entity.getWorkType());
		}
		
		sb.append(" and mno=?");
		params.add(entity.getMno());
		
		return this.find(sb.toString(), params.toArray());
	}

	@Override
	public void saveEntity(StaffWorkEntity entity) throws Exception {
		this.save(entity);
	}

	@Override
	public void updateEntity(StaffWorkEntity entity) throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sb.append("update StaffWorkEntity set wtimeStart=?");
		params.add(entity.getWtimeStart());
		sb.append(", wtimeEnd=?");
		params.add(entity.getWtimeEnd());
		sb.append(", weeks=?");
		params.add(entity.getWeeks());
		sb.append(", flightClass=?");
		params.add(entity.getFlightClass());
		sb.append(", workType=?");
		params.add(entity.getWorkType());
		
		sb.append(" where id=? and mno=?");
		params.add(entity.getId());
		params.add(entity.getMno());
		
		this.executeHql(sb.toString(), params.toArray());
	}

	@Override
	public void deleteEntity(Integer id) throws Exception {
		this.delete(id);
	}

	@Override
	public Integer signIn(StaffWorkEntity entity) throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sb.append("update t_staff_work set sign_in=?");
		params.add(entity.getSignIn());
		sb.append(" where fk_user_id=?");
		params.add(entity.getFkUserId());
		sb.append(" and mno=?");
		params.add(entity.getMno());
		
		return this.executeSql(sb.toString(), params.toArray());
	}

	@Override
	public StaffWorkEntity queryStaffByStamptimeOrder(StaffWorkEntity entity) throws Exception {
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("from StaffWorkEntity where 1=1");
		stringBuffer.append(" and mno='"+entity.getMno()+"'");
		stringBuffer.append(" and flightClass='"+entity.getFlightClass()+"'");
		stringBuffer.append(" and workType='"+entity.getWorkType()+"'");
		stringBuffer.append(" and signIn='"+entity.getSignIn()+"'");
		stringBuffer.append(" order by stamptime");
		List<StaffWorkEntity> staffWorkEntities=this.find(stringBuffer.toString());
		if(staffWorkEntities!=null&&staffWorkEntities.size()>0){
			return staffWorkEntities.get(0);
		}else{
			return null;
		}
	}

	@Override
	public boolean updateById(StaffWorkEntity entity) throws Exception {
		String hql=ProduceHqlUtil.getUpdateByIdHql(entity);
		int result=this.executeHql(hql);
		if(result>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<Object[]> queryList(StaffWorkVo vo) throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sb.append("select bu.user_id,bu.name,");
		sb.append("sw.id,sw.wtime_start,sw.wtime_end,sw.weeks,sw.flight_class,sw.work_type,sw.create_time ");
		sb.append(" from t_base_user bu");
		sb.append(" left join t_staff_work sw on sw.fk_user_id=bu.user_id");
		sb.append(" where 1=1");
		if (StringUtils.isNotEmpty(vo.getName())) {
			sb.append(" and bu.name=?");
			params.add(vo.getName());
		}
		if (StringUtils.isNotEmpty(vo.getFlightClass())) {
			sb.append(" and sw.flight_class=?");
			params.add(vo.getFlightClass());
		}
		if (StringUtils.isNotEmpty(vo.getWorkType())) {
			sb.append(" and sw.work_type=?");
			params.add(vo.getWorkType());
		}
		sb.append(" and mno=?");
		params.add(vo.getMno());
		sb.append(" order by sw.flight_class desc,sw.work_type,bu.name");
		return this.findBySQL(sb.toString(), params.toArray());
	}

}
