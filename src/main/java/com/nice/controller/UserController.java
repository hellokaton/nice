package com.nice.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.kit.EncrypKit;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.blade.mvc.ui.RestResponse;
import com.nice.exception.TipException;
import com.nice.model.entity.User;
import com.nice.service.UserService;
import com.nice.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Created by biezhi on 2017/2/14.
 */
@Slf4j
@Path
public class UserController {

    @Inject
    private UserService userService;

    /**
     * 个人设置页面
     *
     * @return
     */
    @GetRoute("/setting")
    public String setting() {
        return "setting";
    }

    @PostRoute("/setting")
    @JSON
    public RestResponse doSetting(Optional<User> userOptional, Request request) {

        try {
            User user = SessionUtils.getLoginUser();
            if (null == user) {
                return RestResponse.fail("请登录后进行操作");
            }
            userOptional.ifPresent(temp -> temp.update(user.getUsername()));
            SessionUtils.setLoginUser(request.session(), user);
            return RestResponse.ok();
        } catch (Exception e) {
            String msg = "保存设置失败";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                log.error(msg, e);
            }
            return RestResponse.fail(msg);
        }
    }

    @Route(value = "/up_pwd", method = HttpMethod.POST)
    @JSON
    public RestResponse updatePassword(@QueryParam String newpwd) {
        RestResponse restResponse = new RestResponse();
        try {
            User user = SessionUtils.getLoginUser();
            if (null == user) {
                restResponse.setMsg("请登录后进行操作");
                return restResponse;
            }
            User temp = new User();
            temp.setUsername(user.getUsername());
            String pwd = EncrypKit.md5(user.getUsername() + newpwd);
            temp.setPassword(pwd);
            userService.update(temp);
            restResponse.setSuccess(true);
        } catch (Exception e) {
            if (e instanceof TipException) {
                restResponse.setMsg(e.getMessage());
            } else {
                log.error("密码修改失败", e);
            }
        }
        return restResponse;
    }

}
