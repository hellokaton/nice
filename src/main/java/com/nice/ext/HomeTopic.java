package com.nice.ext;

import com.blade.jdbc.core.ActiveRecord;
import lombok.Data;

/**
 * Created by biezhi on 2017/2/14.
 */
@Data
public class HomeTopic extends ActiveRecord {

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

}
