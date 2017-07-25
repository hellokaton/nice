package com.nice.model.param;

import com.blade.validator.annotation.NotEmpty;
import lombok.Data;

/**
 * @author biezhi
 * @date 2017/7/25
 */
@Data
public class LoginParam {

    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;

}
