package com.nice.model;

import java.io.Serializable;

import com.blade.jdbc.annotation.Table;

//
@Table(name = "t_stars", pk = "id")
public class Stars implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	// 主题id
	private String tid;

	// 点赞用户
	private String username;

	public Stars(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


}