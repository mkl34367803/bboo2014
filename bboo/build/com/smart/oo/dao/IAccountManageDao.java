package com.smart.oo.dao;

import java.util.List;

import com.smart.comm.entity.AccountManageEntity;
import com.smart.framework.base.Page;

public interface IAccountManageDao {

	/**
	 * 添加
	 * @param e
	 * @return
	 * @throws Exception
	 */
	Integer saveAccountManage(AccountManageEntity entity) throws Exception;

	/**
	 * 查询
	 * @param e
	 * @return
	 * @throws Exception
	 */
	List<AccountManageEntity> findList(AccountManageEntity entity) throws Exception;
	
	/**
	 * 分页查询
	 * @param c
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<AccountManageEntity> findByPage(AccountManageEntity entity, Page page) throws Exception;
	
	/**
	 * 通过ID删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	void deleteById(Integer id, String mno) throws Exception;
	
	/**
	 * 修改
	 * @param c
	 * @return
	 * @throws Exception
	 */
	void updateEntity(AccountManageEntity entity) throws Exception;
}
