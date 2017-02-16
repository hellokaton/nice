package com.nice.model;

import java.io.Serializable;

import com.blade.jdbc.annotation.Table;

//
@Table(name = "t_acode", pk = "id")
public class Acode implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	// 激活码
	private String code;

	private String username;
	// 类型
	private String type;

	// 是否使用
	private Boolean used;

	// 创建时间
	private Integer created;

	// 过期时间
	private Integer expired;

	public Acode(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getUsed() {
		return used;
	}

	public void setUsed(Boolean used) {
		this.used = used;
	}

	public Integer getCreated() {
		return created;
	}

	public void setCreated(Integer created) {
		this.created = created;
	}

	public Integer getExpired() {
		return expired;
	}

	public void setExpired(Integer expired) {
		this.expired = expired;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}