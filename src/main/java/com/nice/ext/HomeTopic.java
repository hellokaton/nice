package com.nice.ext;

import java.io.Serializable;

/**
 * Created by biezhi on 2017/2/14.
 */
public class HomeTopic implements Serializable {

    private String id;
    private String username;
    private String nickname;
    private String avatar;
    private Integer created;
    private String content;
    private Integer stars;
    private Integer comments;
    private String title;
    private Integer starid;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getStarid() {
        return starid;
    }

    public void setStarid(Integer starid) {
        this.starid = starid;
    }
}
