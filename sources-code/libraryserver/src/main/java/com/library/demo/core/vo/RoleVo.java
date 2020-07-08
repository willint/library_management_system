package com.library.demo.core.vo;

import java.util.Set;

public class RoleVo {

  private String id;
  
  private Set<String> ids;

  private String name;  // 联系电话

  private String remarks;  // 姓名
  
  private String userId;  // 用户id

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Set<String> getIds() {
    return ids;
  }

  public void setIds(Set<String> ids) {
    this.ids = ids;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
  
}
