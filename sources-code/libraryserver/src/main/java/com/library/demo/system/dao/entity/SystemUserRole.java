package com.library.demo.system.dao.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 用户角色关联表
 * @author 陈乾楷
 */
@SuppressWarnings("serial")
@TableName("system_user_role")
public class SystemUserRole implements java.io.Serializable{

  /** 编号 */
  @TableId(value = "ID", type = IdType.UUID)
  private String id;
  /** 用户编号 */
  @TableField(value = "USER_ID")
  private String userId;
  private SystemUser systemUser;
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
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public SystemUser getSystemUser() {
    return systemUser;
  }
  public void setSystemUser(SystemUser systemUser) {
    this.systemUser = systemUser;
  }
  public String getRoleId() {
    return roleId;
  }
  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }
  public SystemRole getSystemRole() {
    return systemRole;
  }
  public void setSystemRole(SystemRole systemRole) {
    this.systemRole = systemRole;
  }
}
