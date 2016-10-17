package com.smart.oo.dao;

import java.util.List;

import com.smart.comm.entity.StaffWorkEntity;
import com.smart.framework.base.Page;
import com.smart.oo.from.StaffWorkVo;

public interface IStaffWorkDao {
	
	/**
	 * 分页查询带有用户名
	 * @param entity
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<Object[]> queryByPage(StaffWorkVo vo, Page page) throws Exception;
	
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
	Integer signIn(StaffWorkEntity entity) throws Exception;
	/**
	 * 查询
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public StaffWorkEntity queryStaffByStamptimeOrder(StaffWorkEntity entity) throws Exception;
	/**
	 * 通过id更新东西
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public boolean updateById(StaffWorkEntity entity) throws Exception;
	
	/**
	 * 查询带有用户名的数据
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	List<Object[]> queryList(StaffWorkVo vo) throws Exception;
	
}
