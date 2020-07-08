package com.library.demo.core.utils;

import java.util.Map;
import org.springframework.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

public class CommonUtil {

  public static <T> Wrapper<T> wrapper(Map<String, Object> param, Class<T> clazz) {
    EntityWrapper<T> entityWrapper = new EntityWrapper<>();
    for (String key : param.keySet()) {
      if (param.get(key) != null && param.get(key) instanceof String
          && !StringUtils.isEmpty((String) param.get(key))) {
        entityWrapper.eq(key, param.get(key));
      }
    }
    return entityWrapper;
  }
}
