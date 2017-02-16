package com.nice.model;

import java.io.Serializable;

import com.blade.jdbc.annotation.Table;

//
@Table(name = "t_user", pk = "username")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	// 用户名
	private String username;

	// 密码
	private String password;

	// 昵称
	private String nickname;

	// 邮箱
	private String email;

	// 头像
	private String avatar;

	// 个性签名
	private String signature;

	// 粉丝数
	private Integer followers;

	// 关注人数
	private Integer following;

	// 发帖数
	private Integer topics;

	// 是否是私密账户,关注可见
	private Boolean privated;

	// 1:正常 0:禁用 2:锁定
	private Integer state;

	// 创建时间
	private Integer created;

	// 最后登录时间
	private Integer logined;

	public User(){}

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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Integer getFollowers() {
		return followers;
	}

	public void setFollowers(Integer followers) {
		this.followers = followers;
	}

	public Integer getFollowing() {
		return following;
	}

	public void setFollowing(Integer following) {
		this.following = following;
	}

	public Integer getTopics() {
		return topics;
	}

	public void setTopics(Integer topics) {
		this.topics = topics;
	}

	public Boolean getPrivated() {
		return privated;
	}

	public void setPrivated(Boolean privated) {
		this.privated = privated;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getCreated() {
		return created;
	}

	public void setCreated(Integer created) {
		this.created = created;
	}

	public Integer getLogined() {
		return logined;
	}

	public void setLogined(Integer logined) {
		this.logined = logined;
	}


}