package com.library.demo.system.dao.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 角色菜单
 *
 */
@SuppressWarnings("serial")
@TableName("system_role_menu")
public class SystemRoleMenu implements java.io.Serializable {

  /** 编号 */
  @TableId(value = "ID", type = IdType.UUID)
  private String id;
  /** 菜单编号 */
  @TableField(value = "MENU_ID")
  private String menuId;
  private SystemMenu systemMenu;
  /** 角色编号 */
  @TableField(value = "ROLE_ID")
  private String roleId;
  private SystemRole systemRole;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMenuId() {
    return menuId;
  }

  public void setMenuId(String menuId) {
    this.menuId = menuId;
  }

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public SystemMenu getSystemMenu() {
    return systemMenu;
  }

  public void setSystemMenu(SystemMenu systemMenu) {
    this.systemMenu = systemMenu;
  }

  public SystemRole getSystemRole() {
    return systemRole;
  }

  public void setSystemRole(SystemRole systemRole) {
    this.systemRole = systemRole;
  }

}
