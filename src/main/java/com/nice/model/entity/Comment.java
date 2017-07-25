package com.nice.model.entity;

import com.blade.jdbc.annotation.Table;
import com.blade.jdbc.core.ActiveRecord;
import lombok.Data;

//
@Data
@Table("t_comment")
public class Comment extends ActiveRecord {

    private Integer id;
    // 主题id
    private String  tid;
    // 评论人
    private String  username;
    // 评论内容
    private String  content;
    // 评论时间
    private Integer created;

}