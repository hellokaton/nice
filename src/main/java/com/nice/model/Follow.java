package com.nice.model;

import com.blade.jdbc.annotation.Table;
import com.blade.jdbc.core.ActiveRecord;
import lombok.Data;

//
@Data
@Table("t_follow")
public class Follow extends ActiveRecord {

    private Integer id;
    // 我的用户名
    private String  me;
    // 我关注的人
    private String  following;
    // 关注时间
    private Integer created;

}