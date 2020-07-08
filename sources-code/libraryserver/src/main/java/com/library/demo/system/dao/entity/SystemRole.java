package com.library.demo.system.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;


/**
 * 角色表
 *
 */
@SuppressWarnings("serial")
@TableName("system_role")
public class SystemRole implements java.io.Serializable{

  @TableId(value = "ID", type = IdType.UUID)
  private String id;
  @TableField(value = "NAME")
  private String name;// 角色名称
  @TableField(value = "IS_SYS")
  private String isSys;// 是否系统数据:0:否；1：是
  @TableField(value = "REMARKS")
  private String remarks;
  @TableField(value = "CREATE_DATE")
  private Date createDate = new Date();
  @TableField(value = "UPDATE_DATE")
  private Date updateDate;
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getIsSys() {
    return isSys;
  }
  public void setIsSys(String isSys) {
    this.isSys = isSys;
  }
  public String getRemarks() {
    return remarks;
  }
  public void setRemarks(String remarks) {
    this.remarks = remarks;
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
  
}
