package com.nice.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.ui.RestResponse;
import com.blade.validator.annotation.Valid;
import com.nice.config.Constant;
import com.nice.exception.TipException;
import com.nice.model.entity.User;
import com.nice.model.param.LoginParam;
import com.nice.model.param.SignupParam;
import com.nice.service.UserService;
import com.nice.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 认证控制器
 *
 * @author biezhi
 * @date 2017/7/25
 */
@Slf4j
@Path
public class AuthController {

    @Inject
    private UserService userService;

    /**
     * 用户登录
     */
    @PostRoute("signin")
    @JSON
    public RestResponse signin(@Valid LoginParam loginParam,
                               Request request, Response response) {

        RestResponse restResponse = new RestResponse();
        try {
            User user = userService.signin(loginParam);
            SessionUtils.setLoginUser(request.session(), user);
            SessionUtils.setCookie(response, Constant.USER_IN_COOKIE, user.getUsername());
            restResponse.setSuccess(true);
        } catch (Exception e) {
            if (e instanceof TipException) {
                restResponse.setMsg(e.getMessage());
            } else {
                log.error("登录失败", e);
            }
        }
        return restResponse;
    }

    /**
     * 账户注册
     *
     * @return
     */
    @Route(value = "signup", method = HttpMethod.POST)
    @JSON
    public RestResponse signup(@Valid SignupParam signupParam) {
        RestResponse restResponse = new RestResponse();
        try {
            userService.signup(signupParam);
            restResponse.setSuccess(true);
        } catch (Exception e) {
            if (e instanceof TipException) {
                restResponse.setMsg(e.getMessage());
            } else {
                log.error("注册失败", e);
            }
        }
        return restResponse;
    }

    /**
     * 激活
     *
     * @return
     */
    @Route(value = "active/:code")
    public String activeCode(@PathParam String code, Request request) {
        try {
            userService.active(code);
            request.attribute("success", true);
        } catch (Exception e) {
            request.attribute("success", false);
            if (e instanceof TipException) {
                request.attribute("msg", e.getMessage());
            } else {
                log.error("激活失败", e);
            }
        }
        return "active";
    }

    /**
     * 注销
     */
    @Route("logout")
    public void logout(Request request, Response response) {
        SessionUtils.removeUser(request.session());
        SessionUtils.removeCookie(response);
        response.redirect("/");
    }

}