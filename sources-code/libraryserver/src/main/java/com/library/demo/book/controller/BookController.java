package com.library.demo.book.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.library.demo.book.dao.entity.BookInfo;
import com.library.demo.book.service.BookInfoService;
import com.library.demo.book.vo.BookInfoVo;
import com.library.demo.core.annotation.Permission;
import com.library.demo.core.http.PageResponse;
import com.library.demo.core.page.Page;
import com.library.demo.core.vo.PageRequestVo;
import com.library.demo.core.vo.RespVo;

@RestController
@RequestMapping("book")
public class BookController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BookInfoService bookInfoService;

	@RequestMapping("/pageList")
	public PageResponse<BookInfo> pageList(@RequestBody PageRequestVo pageRequestVo) {
		logger.info("book pageList 入参：{}", pageRequestVo.getFromData());
		pageRequestVo.getFromData().put("del_flag", "0");
		Wrapper<BookInfo> wrapper = bookInfoService.maptoWrapper(pageRequestVo.getFromData());
		Page<BookInfo> page = null;
		page = bookInfoService.list(pageRequestVo, wrapper);

//		List<BookInfo> bookList = page.getContent();
//		for (BookInfo b : bookList) {
//			BookInfoVo vo = new BookInfoVo();
//			// 批量赋值
//			BeanUtils.copyProperties(b, vo);
//		}

//		Page<BookInfoVo> pageVo = null;
		PageResponse<BookInfo> pageResponse = new PageResponse<>(page);
		return pageResponse;
	}

	@Permission(key = "book:edit")
	@RequestMapping("/saveOrUpdate")
	public RespVo saveOrUpdateCardPool(@RequestBody BookInfoVo bookInfoVo) {

		String id = bookInfoVo.getId();
		boolean saveFlag = bookInfoService.saveOrUpdate(bookInfoVo);
		if (saveFlag) {
			return new RespVo();
		} else {
			if("1".equals(bookInfoVo.getOperType())) {
				return new RespVo("-1", "新增图书信息失败");
			}else if("2".equals(bookInfoVo.getOperType())) {
				return new RespVo("-1", "修改图书信息失败");
			}else if("3".equals(bookInfoVo.getOperType())) {
				return new RespVo("-1", "删除图书信息失败");
			}else {
				return new RespVo("-1", "图书信息更新失败");
			}
			
		}
	}
}
