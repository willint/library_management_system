package com.library.demo.core.utils;
import com.alibaba.fastjson.JSONObject;

/**
 * Json 工具类
 *
 */
public class JsonUtils {

  public static String toJson(Object obj) {
    return JSONObject.toJSONString(obj);
  }

  public static <T> T toBean(Class<T> entityClass, String jsonString) {
    return JSONObject.parseObject(jsonString, entityClass);
  }

}
