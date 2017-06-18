package com.nice.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.kit.EncrypKit;
import com.blade.kit.StringKit;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.blade.mvc.ui.RestResponse;
import com.nice.exception.TipException;
import com.nice.model.User;
import com.nice.service.UserService;
import com.nice.utils.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by biezhi on 2017/2/14.
 */
@Path
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Inject
    private UserService userService;

    /**
     * 个人设置页面
     *
     * @return
     */
    @GetRoute("/setting")
    public String settingPage() {
        return "setting";
    }

    @PostRoute("/setting")
    @JSON
    public RestResponse setting(@QueryParam String nickname, @QueryParam String signature,
                                @QueryParam String avatar, Request request){
        RestResponse restResponse = new RestResponse();
        try {
            User user = SessionUtils.getLoginUser();
            if (null == user) {
                restResponse.setMsg("请登录后进行操作");
                return restResponse;
            }
            User temp = new User();
            temp.setUsername(user.getUsername());
            boolean isUp = false;
            if(StringKit.isNotBlank(nickname)){
                isUp = true;
                temp.setNickname(nickname);
            }

            if(StringKit.isNotBlank(signature)){
                isUp = true;
                temp.setSignature(signature);
            }

            if(StringKit.isNotBlank(avatar)){
                isUp = true;
                temp.setAvatar(avatar + "?t_" + System.currentTimeMillis());
            }

            temp = isUp ? temp : null;
            user = userService.update(temp);
            SessionUtils.setLoginUser(request.session(), user);
            restResponse.setSuccess(true);
        } catch (Exception e){
            if (e instanceof TipException) {
                restResponse.setMsg(e.getMessage());
            } else {
                log.error("保存设置失败", e);
            }
        }
        return restResponse;
    }

    @Route(value = "/up_pwd", method = HttpMethod.POST)
    @JSON
    public RestResponse up_pwd(@QueryParam String newpwd){
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
        } catch (Exception e){
            if (e instanceof TipException) {
                restResponse.setMsg(e.getMessage());
            } else {
                log.error("密码修改失败", e);
            }
        }
        return restResponse;
    }

}
