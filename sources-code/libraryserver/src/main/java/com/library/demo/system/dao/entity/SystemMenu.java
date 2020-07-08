package com.library.demo.system.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;


/**
 * 系统菜单
 */
@SuppressWarnings("serial")
@TableName("system_menu")
public class SystemMenu implements java.io.Serializable {
  
  @TableId(value = "ID", type = IdType.UUID)
  private String id;

  @TableField(value = "C_ICON")
  private String icon;//图标
  
  @TableField(value = "C_INDEX")
  private String index;//访问路由
  
  @TableField(value = "C_TITLE")
  private String title;//标题
  
  @TableField(value = "C_PERMISSION")
  private String permission;//权限
  
  @TableField(value = "C_DESCRIPTION")
  private String description;//说明
  
  @TableField(value = "C_PARENT")
  private String parent;//父菜单
  
  @TableField(value = "C_SEQ")
  private int seq;//排序
  
  @TableField(value = "C_TYPE")
  private int type;//父菜单:0-菜单；1-按钮

  @TableField(value = "C_STATE")
  private int state;//状态：1-有效；0-无效
  
  @TableField(value = "CREATE_DATE")
  private Date createDate;//创建时间

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

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

  public String getPermission() {
    return permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getParent() {
    return parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
  }

  public int getSeq() {
    return seq;
  }

  public void setSeq(int seq) {
    this.seq = seq;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
  
}
