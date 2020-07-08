package com.library.demo.book.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.library.demo.book.dao.entity.BookInfo;

@Mapper
public interface BookInfoMapper extends BaseMapper<BookInfo> {
	
}
