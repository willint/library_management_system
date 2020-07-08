package com.library.demo.book.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 */
@SuppressWarnings("serial")
@TableName("tb_books")
public class BookInfo implements java.io.Serializable {

	@TableId(value = "ID", type = IdType.UUID)
	private String id;

	@TableField(value = "name")
	private String name;//

	@TableField(value = "introduction")
	private String introduction;//

	@TableField(value = "category")
	private String category;//

	@TableField(value = "author")
	private String author;//

	@TableField(value = "location")
	private String location;//
	
	@TableField(value = "index_code")
	private String indexCode;//

	@TableField(value = "img_url")
	private String imgUrl;//

	@TableField(value = "del_flag")
	private String delFlag;//

	@TableField(value = "create_time")
	private Date createTime;//

	@TableField(value = "update_time")
	private Date updateTime;//

	@TableField(value = "operator")
	private String operator;//

	@TableField(value = "remark")
	private String remark;//

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

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}
	
	

}
