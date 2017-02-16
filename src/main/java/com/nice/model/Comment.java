package com.nice.model;

import java.io.Serializable;

import com.blade.jdbc.annotation.Table;

//
@Table(name = "t_comment", pk = "id")
public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	// 主题id
	private String tid;

	// 评论人
	private String username;

	// 评论内容
	private String content;

	// 评论时间
	private Integer created;

	public Comment(){}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCreated() {
		return created;
	}

	public void setCreated(Integer created) {
		this.created = created;
	}


}