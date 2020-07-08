package com.library.demo.core.vo;

import java.util.Map;

public class PageRequestVo {

  private int page = 0;
  
  private int size = 10;
  
  private Map<String,Object> fromData;

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public Map<String, Object> getFromData() {
    return fromData;
  }

  public void setFromData(Map<String, Object> fromData) {
    this.fromData = fromData;
  }
  
}
