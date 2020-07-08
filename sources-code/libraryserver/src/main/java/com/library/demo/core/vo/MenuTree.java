package com.library.demo.core.vo;

import java.util.List;

@SuppressWarnings("serial")
public class MenuTree implements java.io.Serializable{
  
  private String icon;
  
  private String index;
  
  private String title;
  
  private List<MenuTree> subs;

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getIndex() {
    return index;
  }

  public void setIndex(String index) {
    this.index = index;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<MenuTree> getSubs() {
    return subs;
  }

  public void setSubs(List<MenuTree> subs) {
    this.subs = subs;
  }
  
}
