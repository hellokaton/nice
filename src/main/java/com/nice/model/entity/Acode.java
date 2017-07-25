package com.nice.model.entity;

import com.blade.jdbc.annotation.Table;
import com.blade.jdbc.core.ActiveRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("t_acode")
public class Acode extends ActiveRecord {

    private Integer id;
    // 激活码
    private String  code;
    private String  username;
    // 类型
    private String  type;
    // 是否使用
    private Boolean used;
    // 创建时间
    private Integer created;
    // 过期时间
    private Integer expired;

}