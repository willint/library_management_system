package com.library.demo.book.service;

import com.library.demo.book.dao.entity.BookInfo;
import com.library.demo.book.vo.BookInfoVo;
import com.library.demo.core.service.ICommonService;

public interface BookInfoService extends ICommonService<BookInfo> {

	
	public boolean saveOrUpdate(BookInfoVo bookVo);
	
	public boolean deleteBook(String id);

}
