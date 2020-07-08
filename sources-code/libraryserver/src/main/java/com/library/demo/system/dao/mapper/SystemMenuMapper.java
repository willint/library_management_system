package com.library.demo.system.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.library.demo.system.dao.entity.SystemMenu;

@Mapper
public interface SystemMenuMapper extends BaseMapper<SystemMenu> {
  
  /**
   * 查询系统管理员菜单
   * @return
   */
  List<SystemMenu> findSysMenu();

	/**
	 * 
	 * @title: findMenuByUserId
	 * @description: 通过用户查找菜单
	 * @param userId
	 * @return
	 * @return: List<Menu>
	 */
	List<SystemMenu> findMenuByUserId(String userId);
	
	/**
	 * 
	 * @title: findMenuByRoleId
	 * @description: 通过角色查找菜单
	 * @param roleId
	 * @return
	 * @return: List<Menu>
	 */
	List<SystemMenu> findMenuByRoleId(String roleId);


	/**
	 *
	 * @title: findMenuByUserId
	 * @description: 通过用户查找菜单
	 * @param userId
	 * @return
	 * @return: List<Menu>
	 */
	List<String> findPermissionByUserId(String userId);

	/**
	 *
	 * @title: findMenuByRoleId
	 * @description: 通过角色查找菜单
	 * @param roleId
	 * @return
	 * @return: List<Menu>
	 */
	List<String> findPermissionByRoleId(String roleId);
	
	/**
	 * 查询角色所拥有的菜单id,包含按钮
	 * @param userId
	 * @return
	 */
	List<String> findAllMenuIdByRoleId(String roleId);

}