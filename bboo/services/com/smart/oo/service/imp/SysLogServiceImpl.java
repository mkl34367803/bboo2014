package com.smart.oo.service.imp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.comm.entity.SysLogEntity;
import com.smart.comm.utils.BBOOConstants;
import com.smart.comm.utils.DateUtil;
import com.smart.oo.dao.imp.FactoryDaoImpl;
import com.smart.oo.service.ISysLogService;
import com.smart.utils.UniqId;
@Service("SysLogServiceImpl")
public class SysLogServiceImpl implements ISysLogService{
	@Autowired
	private FactoryDaoImpl factoryDaoImpl;
	@Override
	public String saveSysLog(SysLogEntity sysLogEntity) throws Exception {
		return factoryDaoImpl.getSysLogDao().saveSysLog(sysLogEntity);
	}
	@Override
	public List<SysLogEntity> querySysLogByForeginKey(String foreginKey)
			throws Exception {
		return this.factoryDaoImpl.getSysLogDao().querySysLogByForeginKey(foreginKey);
	}
	@Override
	public String saveAutoDealSysLog(String foreignKey, String mno, String content) throws Exception {
		//增加一个记录日志的功能
		SysLogEntity sysLogEntity=new SysLogEntity();
		sysLogEntity.setId(UniqId.getInstance().getStrId());
		sysLogEntity.setContents(content);
		sysLogEntity.setForeginKey(foreignKey);
		sysLogEntity.setMno(mno);
		sysLogEntity.setLogType(BBOOConstants.SysLogEntity_logType_ORDER_LOG);
		sysLogEntity.setOperatime(DateUtil.DateToStr(new Date()));
		sysLogEntity.setUserName("system");
		sysLogEntity.setVisitip("127.0.0.1");
		return factoryDaoImpl.getSysLogDao().saveSysLog(sysLogEntity);
	}

}
