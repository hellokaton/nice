package com.nice.model;

import com.blade.jdbc.annotation.Table;
import com.blade.jdbc.core.ActiveRecord;
import lombok.Data;

//
@Data
@Table("t_tags")
public class Tags extends ActiveRecord {

    private String  id;
    // 标签名
    private String  name;
    private String  tid;
    // 1:正常 0:删除
    private Integer state;
    // 标签创建时间
    private Integer created;
    // 最后更新的时间
    private Integer updated;

}