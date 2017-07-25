package com.nice.model.entity;

import com.blade.jdbc.annotation.Table;
import com.blade.jdbc.core.ActiveRecord;
import lombok.Data;

//
@Data
@Table(value = "t_user", pk = "username")
public class User extends ActiveRecord {

    // 用户名
    private String  username;
    // 密码
    private String  password;
    // 昵称
    private String  nickname;
    // 邮箱
    private String  email;
    // 头像
    private String  avatar;
    // 个性签名
    private String  signature;
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

}