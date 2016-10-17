package com.smart.oo.service;

import java.util.List;

import com.smart.comm.entity.StaffWorkEntity;
import com.smart.framework.base.Page;
import com.smart.oo.from.StaffWorkVo;

public interface IStaffWorkService {

	/**
	 * 分页查询
	 * @param entity
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<StaffWorkVo> queryByPage(StaffWorkVo vo, Page page) throws Exception;
	
	/**
	 * 查询
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	List<StaffWorkEntity> queryList(StaffWorkEntity entity) throws Exception;
	
	/**
	 * 保存
	 * @param entity
	 * @throws Exception
	 */
	void saveEntity(StaffWorkEntity entity) throws Exception;
	
	/**
	 * 修改
	 * @param entity
	 * @throws Exception
	 */
	void updateEntity(StaffWorkEntity entity) throws Exception;
	
	/**
	 * 删除
	 * @param id
	 * @throws Exception
	 */
	void deleteEntity(Integer id) throws Exception;
	
	/**
	 * 签到
	 * @param entity
	 * @throws Exception
	 */
	String signIn(StaffWorkEntity entity) throws Exception;
	
	/**
	 * 检查当前时间是否在工作时间内
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	boolean checkInWorkTime() throws Exception;
	
	/**
	 * 查询含有用户名的数据
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	List<StaffWorkVo> queryList(StaffWorkVo vo) throws Exception;
	
	/**
	 * 查询签到状态
	 * @return
	 * @throws Exception
	 */
	String querySignIn() throws Exception;
	
}
