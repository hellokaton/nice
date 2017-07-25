package com.nice.model.param;

import com.blade.validator.annotation.Email;
import com.blade.validator.annotation.NotEmpty;
import lombok.Data;
import lombok.ToString;

/**
 * @author biezhi
 * @date 2017/7/25
 */
@Data
@ToString(callSuper = true)
public class SignupParam extends LoginParam {

    @NotEmpty(message = "邮箱不能为空")
    @Email(message = "非法的邮箱格式")
    private String email;

    private String avatar;

}
