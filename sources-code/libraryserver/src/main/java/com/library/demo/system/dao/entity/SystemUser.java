package com.library.demo.system.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;


/**
 * 系统用户
 */
@SuppressWarnings("serial")
@TableName("system_user")
public class SystemUser implements java.io.Serializable{
  
  /**
   * 是否锁定（1：正常；-1：删除；0：锁定；）
   */
  public static final String STATUS_DELETE = "-1";
  public static final String STATUS_LOCKED = "0";
  public static final String STATUS_NORMAL = "1";

  @TableId(value = "ID", type = IdType.UUID)
  private String id;

  @TableField(value = "PHONE")
  private String phone;  // 联系电话

  @TableField(value = "USER_NAME")
  private String username;  // 姓名

  @TableField(value = "PASSWORD")
  private String password;  // 密码
  
  @TableField(value = "SALT")
  private String salt;// 盐
  
  @TableField(value = "EMAIL")
  private String email;// 邮件
  
  @TableField(value = "IS_SYS")
  private String isSys = "0";// 是否系统数据:0:否；1：是
  
  @TableField(value = "CREATE_DATE")
  private Date createDate = new Date();//创建时间
  
  @TableField(value = "UPDATE_DATE")
  private Date updateDate;//修改时间
  
  @TableField(value = "STATUS")
  private String status = STATUS_NORMAL;//系统用户的状态

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getIsSys() {
    return isSys;
  }

  public void setIsSys(String isSys) {
    this.isSys = isSys;
  }

}
