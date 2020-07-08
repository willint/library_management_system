package com.library.demo.system.dao.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.library.demo.system.dao.entity.SystemRole;

@Mapper
public interface SystemRoleMapper extends BaseMapper<SystemRole> {
	/**
	 * 
	 * @title: findRoleByUserId   
	 * @description: 通过用户查找角色
	 * @param userId
	 * @return      
	 * @return: List<Role>
	 */
	List<SystemRole> findRoleByUserId(String userId);
	
	List<SystemRole> findRoleNoUserId(String userId);
}
