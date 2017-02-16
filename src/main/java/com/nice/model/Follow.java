package com.nice.model;

import java.io.Serializable;

import com.blade.jdbc.annotation.Table;

//
@Table(name = "t_follow", pk = "id")
public class Follow implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	// 我的用户名
	private String me;

	// 我关注的人
	private String following;

	// 关注时间
	private Integer created;

	public Follow(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMe() {
		return me;
	}

	public void setMe(String me) {
		this.me = me;
	}

	public String getFollowing() {
		return following;
	}

	public void setFollowing(String following) {
		this.following = following;
	}

	public Integer getCreated() {
		return created;
	}

	public void setCreated(Integer created) {
		this.created = created;
	}


}