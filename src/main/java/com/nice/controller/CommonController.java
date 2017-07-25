package com.nice.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.kit.PatternKit;
import com.blade.kit.StringKit;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.multipart.FileItem;
import com.blade.mvc.ui.RestResponse;
import com.blade.validator.annotation.Valid;
import com.nice.config.Constant;
import com.nice.exception.TipException;
import com.nice.model.entity.User;
import com.nice.model.param.LoginParam;
import com.nice.model.param.SignupParam;
import com.nice.service.UserService;
import com.nice.utils.SessionUtils;
import com.nice.utils.UUID;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Path
public class CommonController {

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

    /**
     * 上传
     */
    @Route(value = "/uploadfile", method = HttpMethod.POST)
    public void upload(Request request, Response response, @MultipartParam FileItem fileItem) {
        User user = SessionUtils.getLoginUser();
        if (null == user) {
            return;
        }
        if (null == fileItem) {
            return;
        }

        Optional<String> type = request.query("type");

        String suffix = StringKit.fileExt(fileItem.getFileName());
        if (StringKit.isNotBlank(suffix)) {
            suffix = "." + suffix;
        }

        if (!PatternKit.isImage(suffix)) {
            return;
        }
        String savePath = "";
        if (type.equals("avatar")) {
            savePath = type + "/users/" + user.getUsername() + suffix;
        } else {
            String fileName = UUID.UU32() + suffix;
            // topic/biezhi/s6vunp10vihq9rb239ln9lotjh.jpg
            savePath = type + "/" + user.getUsername() + "/" + fileName;
        }

        String filePath = Constant.UPLOAD_FOLDER + File.separator + savePath;
        File   file     = new File(filePath);
        try {
            if (file.exists()) {
                file.delete();
            }
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(fileItem.getData());
            fos.close();

            Map<String, Object> map = new HashMap<>();
            map.put("status", 200);
            map.put("savekey", savePath);
            map.put("url", Constant.IMG_URL + "/" + savePath);
            response.json(map);

        } catch (Exception e) {
            log.error("上传失败", e);
        }
    }

}