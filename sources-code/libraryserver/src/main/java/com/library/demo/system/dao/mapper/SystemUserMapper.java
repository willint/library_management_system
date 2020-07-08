package com.library.demo.system.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.library.demo.system.dao.entity.SystemUser;

@Mapper
public interface SystemUserMapper extends BaseMapper<SystemUser> {
	
	int updatePwd(@Param("password") String password,@Param("id") String id);
	
	
}