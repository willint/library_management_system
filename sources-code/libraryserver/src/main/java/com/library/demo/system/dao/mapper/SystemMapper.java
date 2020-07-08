package com.library.demo.system.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.library.demo.system.dao.entity.SystemTest;

@Mapper
public interface SystemMapper {
	
	@Select("${sql}")
    List<SystemTest> selectBySql(@Param("sql")String sql);

}
