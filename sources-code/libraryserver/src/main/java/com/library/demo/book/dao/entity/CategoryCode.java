package com.library.demo.book.dao.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 */
@SuppressWarnings("serial")
@TableName("tb_category_code")
public class CategoryCode implements java.io.Serializable {

	@TableId(value = "ID", type = IdType.UUID)
	private String id;

	@TableField(value = "code")
	private String code;//
	
	@TableField(value = "name")
	private String name;//

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
