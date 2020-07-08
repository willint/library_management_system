package com.library.demo.book.service.impl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.demo.book.dao.entity.CategoryCode;
import com.library.demo.book.dao.mapper.CategoryCodeMapper;
import com.library.demo.book.service.CategoryCodeService;
import com.library.demo.core.service.impl.CommonServiceImpl;

@Transactional
@Service
public class CategoryCodeServiceImpl extends CommonServiceImpl<CategoryCodeMapper, CategoryCode>
    implements CategoryCodeService {
  
}
