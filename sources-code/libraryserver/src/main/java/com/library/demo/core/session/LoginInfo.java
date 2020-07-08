package com.library.demo.core.session;

import java.util.List;

import com.library.demo.core.vo.MenuTree;
import com.library.demo.system.dao.entity.SystemUser;

@SuppressWarnings("serial")
public class LoginInfo implements java.io.Serializable{
  
  private SystemUser user;
  
  private List<MenuTree> menuTrees;

  private List<String> permissions;
  
  public LoginInfo(SystemUser user, List<MenuTree> menuTrees, List<String> permissions) {
    this.user = user;
    this.menuTrees = menuTrees;
    this.permissions = permissions;
  }

  public SystemUser getUser() {
    return user;
  }

  public void setUser(SystemUser user) {
    this.user = user;
  }

  public List<MenuTree> getMenuTree() {
    return menuTrees;
  }

  public void setMenuTree(List<MenuTree> menuTrees) {
    this.menuTrees = menuTrees;
  }

  public List<String> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<String> permissions) {
    this.permissions = permissions;
  }
  
}
