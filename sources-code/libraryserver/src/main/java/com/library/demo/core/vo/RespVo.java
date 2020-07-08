package com.library.demo.core.vo;

public class RespVo {

  private String state = "0";

  private String msg = "成功";

  private Object body;

  public RespVo(String state, String msg) {
    this.state = state;
    this.msg = msg;
  }

  public RespVo(String state, String msg, Object body) {
    this.state = state;
    this.msg = msg;
    this.body = body;
  }
  
  public RespVo() {
  }

  public RespVo(Object body) {
    this.body = body;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Object getBody() {
    return body;
  }

  public void setBody(Object body) {
    this.body = body;
  }


}
