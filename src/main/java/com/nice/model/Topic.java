package com.nice.model;

import java.io.Serializable;

import com.blade.jdbc.annotation.Table;

//
@Table(name = "t_topic", pk = "id")
public class Topic implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主题id
	private String id;

	// 发布人
	private String username;

	// 标题
	private String title;

	// 图片
	private String content;

	// 点赞数
	private Integer stars;

	// 评论数
	private Integer comments;

	// 创建时间
	private Integer created;

	public Topic(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public Integer getComments() {
		return comments;
	}

	public void setComments(Integer comments) {
		this.comments = comments;
	}

	public Integer getCreated() {
		return created;
	}

	public void setCreated(Integer created) {
		this.created = created;
	}


}