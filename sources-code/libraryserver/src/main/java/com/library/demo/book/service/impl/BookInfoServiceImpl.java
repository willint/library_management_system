package com.library.demo.book.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.demo.book.dao.entity.BookInfo;
import com.library.demo.book.dao.mapper.BookInfoMapper;
import com.library.demo.book.service.BookInfoService;
import com.library.demo.book.vo.BookInfoVo;
import com.library.demo.core.service.impl.CommonServiceImpl;
import com.library.demo.core.session.LoginInfo;
import com.library.demo.core.spring.SpringUtil;

@Transactional
@Service
public class BookInfoServiceImpl extends CommonServiceImpl<BookInfoMapper, BookInfo> implements BookInfoService {

	@Override
	public boolean saveOrUpdate(BookInfoVo bookVo) {

		BookInfo bookInfo = null;
		LoginInfo loginInfo = SpringUtil.getLoginInfo();
		if (loginInfo == null) {
			return false;
		}
		String operType =  bookVo.getOperType();
		Date currDate = new Date();
		if ("1".equals(operType)) {
			// 新增
			bookInfo = new BookInfo();
			String[] ignoreList = {};
			BeanUtils.copyProperties(bookVo, bookInfo, ignoreList);
			
			bookInfo.setOperator(loginInfo.getUser().getUsername());
			bookInfo.setCreateTime(currDate);
			bookInfo.setUpdateTime(currDate);

		} else if ("2".equals(operType)) {
			// 修改
			bookInfo = this.selectById(bookVo.getId());
			if(null == bookInfo) {
				return false;
			}
			String[] ignoreList = {"id","createTime"};
			BeanUtils.copyProperties(bookVo, bookInfo, ignoreList);
			bookInfo.setOperator(loginInfo.getUser().getUsername());
			bookInfo.setUpdateTime(currDate);

		}else if("3".equals(operType)) {
			// 逻辑删除记录
			bookInfo = this.selectById(bookVo.getId());
			if(null == bookInfo) {
				return false;
			}
			bookInfo.setOperator(loginInfo.getUser().getUsername());
			bookInfo.setUpdateTime(currDate);
			bookInfo.setDelFlag("1");// 
			
		}
		else {
			return false;
		}

		return insertOrUpdate(bookInfo);
	}

	@Override
	public boolean deleteBook(String id) {
		
		return false;
	}

}
