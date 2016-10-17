package com.smart.oo.service;

import java.util.List;

import com.smart.comm.entity.SysLogEntity;

public interface ISysLogService {
	/**
	 * 保存系统日志
	 * @param sysLogEntity
	 * @return
	 * @throws Exception
	 */
	public String saveSysLog(SysLogEntity sysLogEntity) throws Exception;
	
	/**
	 * 通过外键查询系统操作日志
	 * @param foreginKey
	 * @return
	 */
	public List<SysLogEntity> querySysLogByForeginKey(String foreginKey) throws Exception;
	/**
	 * 记录定时任务产生的日志
	 * @param foreignKey 日志外键,对应的订单的订单id
	 * @param mno 商户号
	 * @param content 日志内容
	 * @return
	 * @throws Exception
	 */
	public String saveAutoDealSysLog(String foreignKey,String mno,String content) throws Exception;
}
