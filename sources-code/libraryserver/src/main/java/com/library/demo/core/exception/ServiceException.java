package com.library.demo.core.exception;

public class ServiceException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private String apiCode;
  
  private String msg;

  public String getApiCode() {
    return apiCode;
  }
  
  public String getMsg() {
    return msg;
  }

  public ServiceException(String apiCode, String msg) {
    super(msg);
    this.apiCode = apiCode;
    this.msg = msg;
  }

  public ServiceException(String msg) {
    super(msg);
    this.msg = msg;
  }

  public ServiceException(String msg, Throwable paramThrowable) {
    super(msg, paramThrowable);
    this.msg = msg;
  }

}
