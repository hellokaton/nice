package com.nice.model.entity;

import com.blade.jdbc.annotation.Table;
import com.blade.jdbc.core.ActiveRecord;
import com.blade.validator.annotation.NotEmpty;
import lombok.Data;

//
@Data
@Table("t_topic")
public class Topic extends ActiveRecord {

    // 主题id
    private String id;
    // 发布人
    private String username;
    // 标题
    @NotEmpty(message = "标题不能为空")
    private String title;
    // 图片
    @NotEmpty(message = "内容不能为空")
    private String content;
    // 点赞数
    private Integer stars;
    // 评论数
    private Integer comments;
    // 创建时间
    private Integer created;

}