package com.library.demo.core.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.library.demo.core.http.DuplicateValid;
import com.library.demo.core.page.Page;
import com.library.demo.core.page.PageImpl;
import com.library.demo.core.page.PageRequest;
import com.library.demo.core.page.Pageable;
import com.library.demo.core.page.QueryRequest;
import com.library.demo.core.page.Queryable;
import com.library.demo.core.parse.QueryToWrapper;
import com.library.demo.core.service.ICommonService;
import com.library.demo.core.vo.PageRequestVo;

@Transactional
public class CommonServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T>
    implements ICommonService<T> {

  @Override
  public Page<T> list(PageRequestVo pageRequestVo) {
    EntityWrapper<T> entityWrapper = new EntityWrapper<T>();
    return list(pageRequestVo, entityWrapper);
  }

  @Override
  public Page<T> list(PageRequestVo pageRequestVo, Wrapper<T> wrapper) {
    Queryable queryable = new QueryRequest();
    queryable.setPageable(new PageRequest(pageRequestVo.getPage(), pageRequestVo.getSize()));
    QueryToWrapper<T> queryToWrapper = new QueryToWrapper<T>();
    queryToWrapper.parseCondition(wrapper, queryable);
    // 排序问题
    queryToWrapper.parseSort(wrapper, queryable);
    Pageable pageable = queryable.getPageable();
    com.baomidou.mybatisplus.plugins.Page<T> page = new com.baomidou.mybatisplus.plugins.Page<T>(
        pageable.getPageNumber(), pageable.getPageSize());
    com.baomidou.mybatisplus.plugins.Page<T> content = selectPage(page, wrapper);
    
    return new PageImpl<T>(content.getRecords(), queryable.getPageable(), selectCount(wrapper));
  }

  @Override
  public List<T> listWithNoPage(PageRequestVo pageRequestVo) {
    EntityWrapper<T> entityWrapper = new EntityWrapper<T>();
    return listWithNoPage(pageRequestVo, entityWrapper);
  }

  @Override
  public List<T> listWithNoPage(PageRequestVo pageRequestVo, Wrapper<T> wrapper) {
    QueryToWrapper<T> queryToWrapper = new QueryToWrapper<T>();
    Queryable queryable = new QueryRequest();
    queryToWrapper.parseCondition(wrapper, queryable);
    // 排序问题
    queryToWrapper.parseSort(wrapper, queryable);
    List<T> content = selectList(wrapper);
    return content;
  }
  
  @Override
  public Wrapper<T> maptoWrapper(Map<String, Object> param){
    EntityWrapper<T> entityWrapper = new EntityWrapper<T>();
    for(String key : param.keySet()) {
      if(param.get(key) != null && param.get(key) instanceof String && !StringUtils.isEmpty((String)param.get(key))) {
        entityWrapper.eq(key, param.get(key));
      }
    }
    return entityWrapper;
  }
  
  @Override
  public Boolean doValid(DuplicateValid duplicateValid, Wrapper<T> wrapper) {
    Boolean valid = Boolean.FALSE;
    String queryType = duplicateValid.getQueryType();
    if (StringUtils.isEmpty(queryType)) {
      queryType = "table";
    }
    if (queryType.equals("table")) {
      valid = validTable(duplicateValid, wrapper);
    }
    return valid;
  }

  private Boolean validTable(DuplicateValid duplicateValid, Wrapper<T> wrapper) {
    Integer num = null;
    String extendName = duplicateValid.getExtendName();
    String extendParam = duplicateValid.getExtendParam();
    if (!StringUtils.isEmpty(extendParam)) {
      // [2].编辑页面校验
      wrapper.eq(duplicateValid.getName(), duplicateValid.getParam()).ne(extendName, extendParam);
      num = baseMapper.selectCount(wrapper);
    } else {
      // [1].添加页面校验
      wrapper.eq(duplicateValid.getName(), duplicateValid.getParam());
      num = baseMapper.selectCount(wrapper);
    }

    if (num == null || num == 0) {
      // 该值可用
      return true;
    } else {
      // 该值不可用
      return false;
    }
  }

}
