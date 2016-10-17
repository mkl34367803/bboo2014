package com.smart.oo.service.imp;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import net.sf.excelutils.ExcelException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.comm.entity.StaffWorkEntity;
import com.smart.comm.utils.DateUtisc;
import com.smart.entity.User;
import com.smart.framework.base.Page;
import com.smart.framework.security.SecurityContext;
import com.smart.oo.dao.imp.FactoryDaoImpl;
import com.smart.oo.from.StaffWorkVo;
import com.smart.oo.service.IStaffWorkService;

@Service("BBOOStaffWorkServiceImpl")
public class StaffWorkServiceImpl implements IStaffWorkService {
	
	@Autowired
	private FactoryDaoImpl factoryDao;

	@Override
	public List<StaffWorkVo> queryByPage(StaffWorkVo vo, Page page)
			throws Exception {
		List<Object[]> objList = this.factoryDao.getStaffWorkDao().queryByPage(vo, page);
		return parseStaffWork(objList);
	}
	
	@Override
	public List<StaffWorkEntity> queryList(StaffWorkEntity entity)
			throws Exception {
		return this.factoryDao.getStaffWorkDao().queryList(entity);
	}

	@Override
	public void saveEntity(StaffWorkEntity entity) throws Exception {
		this.factoryDao.getStaffWorkDao().saveEntity(entity);
	}

	@Override
	public void updateEntity(StaffWorkEntity entity) throws Exception {
		this.factoryDao.getStaffWorkDao().updateEntity(entity);
	}

	@Override
	public void deleteEntity(Integer id) throws Exception {
		this.factoryDao.getStaffWorkDao().deleteEntity(id);
	}

	@Override
	public String signIn(StaffWorkEntity entity) throws Exception {
		Integer signIn = entity.getSignIn();
		if (signIn == 1) {
			if (!checkInWorkTime()) {
				return "不再上班时间不能签到";
			}
		} else if (2 == signIn) {
			if (checkInWorkTime()) {
				return "在上班时间不能下班";
			}
		} else if (3 == signIn) {
			if (isOffWork()) {
				return "签到后才能暂停";
			}
		}
		Integer result = this.factoryDao.getStaffWorkDao().signIn(entity);
		if (result > 0) {
			return "success";
		}
		return "error";
	}
	
	private List<StaffWorkVo> parseStaffWork(List<Object[]> objList) {
		List<StaffWorkVo> list = null;
		if (null != objList && objList.size() > 0) {
			list = new ArrayList<StaffWorkVo>();
			StaffWorkVo vo = null;
			for (Object[] objs : objList) {
				int i = 0;
				vo = new StaffWorkVo();
				vo.setFkUserId(Integer.parseInt(objs[i++].toString()));
				vo.setName(parseString(objs[i++]));
				if (objs[i] != null) {
					vo.setId(Integer.parseInt(objs[i].toString()));
				}
				i++;
				vo.setWtimeStart(parseString(objs[i++]));
				vo.setWtimeEnd(parseString(objs[i++]));
				vo.setWeeks(parseString(objs[i++]));
				vo.setFlightClass(parseString(objs[i++]));
				vo.setWorkType(parseString(objs[i++]));
				vo.setCreateTime(parseString(objs[i++]));
				
				list.add(vo);
			}
		}
		return list;
	}
	
	private String parseString(Object obj) {
		return obj ==  null ? "" : obj.toString();
	}

	@Override
	public boolean checkInWorkTime() throws Exception {
		User user = SecurityContext.getUser();
		StaffWorkEntity entity = new StaffWorkEntity();
		entity.setFkUserId(user.getUserId());
		entity.setMno(user.getMert().getMno());
		List<StaffWorkEntity> list = this.queryList(entity);
		if (null != list && list.size() > 0) {
			StaffWorkEntity sw = list.get(0);
			String curentWeek = DateUtisc.getCurrentWeekOfDate().toString();
			if (sw.getWeeks().indexOf(curentWeek) < 0) {
				return false;
			}
			return checkIsInWtime(sw.getWtimeStart(), sw.getWtimeEnd());
		} else {
			throw new ExcelException("员工上班时刻表中数据不存在");
		}
	}
	
	/**
	 * 是否下班
	 * @return
	 * @throws Exception
	 */
	private boolean isOffWork() throws Exception {
		User user = SecurityContext.getUser();
		StaffWorkEntity entity = new StaffWorkEntity();
		entity.setFkUserId(user.getUserId());
		entity.setMno(user.getMert().getMno());
		List<StaffWorkEntity> list = this.queryList(entity);
		if (null != list && list.size() > 0) {
			StaffWorkEntity sw = list.get(0);
			if (null != sw.getSignIn() && sw.getSignIn() == 2) {
				return true;
			}
			return false;
		} else {
			throw new ExcelException("员工上班时刻表中数据不存在");
		}
	}

	/**
	 * 判断是否在上班时间内
	 * @param entity
	 * @return
	 * @throws ParseException
	 */
	private boolean checkIsInWtime(String wtimeStart, String wtimeEnd) throws ParseException {
		String curentHM = DateUtisc.getCurrentHandM();
		boolean checkSt = DateUtisc.compareTime(curentHM, wtimeStart);
		boolean checkEnd = DateUtisc.compareTime(wtimeEnd, curentHM);
		if (DateUtisc.compareTime(wtimeEnd, wtimeStart)) {
			if (checkSt && checkEnd) {
				return true;
			}
		} else {
			if (checkSt || checkEnd) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<StaffWorkVo> queryList(StaffWorkVo vo) throws Exception {
		List<Object[]> objList = this.factoryDao.getStaffWorkDao().queryList(vo);
		return parseStaffWork(objList);
	}

	@Override
	public String querySignIn() throws Exception {
		User user = SecurityContext.getUser();
		StaffWorkEntity entity = new StaffWorkEntity();
		entity.setMno(user.getMert().getMno());
		entity.setFkUserId(user.getUserId());
		
		List<StaffWorkEntity> swList = this.queryList(entity);
		
		if (null != swList && swList.size() > 0) {
			if (swList.size() > 1) {
				return "员工上班时间表中用户id重复";
			}
			if (null != swList.get(0).getSignIn()) {
				return swList.get(0).getSignIn().toString();
			}
			return null;
		} else {
			return "员工上班时间表中数据不存在";
		}
	}
	

}
