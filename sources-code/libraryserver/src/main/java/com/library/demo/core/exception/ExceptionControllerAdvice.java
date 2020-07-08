package com.library.demo.core.exception;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.library.demo.core.vo.RespVo;

/**
 * 全局异常处理
 * 
 *
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

  @ResponseBody
  @ExceptionHandler(value = ServiceException.class)
  public RespVo serviceException(ServiceException exception) {
    String code = exception.getApiCode();
    String msg = exception.getMsg();
    return new RespVo(StringUtils.isBlank(code) ? "-1" : code,
        StringUtils.isBlank(msg) ? "接口异常" : msg);
  }

}
