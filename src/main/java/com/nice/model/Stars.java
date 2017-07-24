package com.nice.model;

import com.blade.jdbc.annotation.Table;
import com.blade.jdbc.core.ActiveRecord;
import lombok.Data;

//
@Data
@Table("t_stars")
public class Stars extends ActiveRecord {

    private Integer id;
    // 主题id
    private String  tid;
    // 点赞用户
    private String  username;

}