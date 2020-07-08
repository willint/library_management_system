package com.library.demo.core.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.library.demo.core.http.DuplicateValid;
import com.library.demo.core.page.Page;
import com.library.demo.core.vo.PageRequestVo;

public interface ICommonService<T> extends IService<T> {

     Page<T> list(PageRequestVo pageRequestVo);

     Page<T> list(PageRequestVo pageRequestVo, Wrapper<T> wrapper);

     List<T> listWithNoPage(PageRequestVo pageRequestVo);

     List<T> listWithNoPage(PageRequestVo pageRequestVo, Wrapper<T> wrapper);

     Boolean doValid(DuplicateValid duplicateValid, Wrapper<T> wrapper);

    Wrapper<T> maptoWrapper(Map<String, Object> param);

}