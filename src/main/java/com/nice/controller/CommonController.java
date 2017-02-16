package com.nice.controller;

import com.nice.config.Constant;
import com.nice.exception.TipException;
import com.nice.model.User;
import com.nice.service.UserService;
import com.nice.utils.SessionUtils;
import com.nice.utils.UUID;
import com.nice.utils.Utils;
import com.blade.ioc.annotation.Inject;
import com.blade.kit.FileKit;
import com.blade.kit.PatternKit;
import com.blade.kit.StringKit;
import com.blade.kit.json.JSONObject;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.multipart.FileItem;
import com.blade.mvc.view.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@Controller
public class CommonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

    @Inject
    private UserService userService;

    /**
     * 用户登录
     *
     * @param loginname
     * @param password
     * @return
     */
    @Route(value = "signin", method = HttpMethod.POST)
    @JSON
    public RestResponse signin(@QueryParam String loginname, @QueryParam String password,
                               Request request, Response response) {

        RestResponse restResponse = new RestResponse();
        try {
            User user = userService.signin(loginname, password);
            SessionUtils.setLoginUser(request.session(), user);
            SessionUtils.setCookie(response, Constant.USER_IN_COOKIE, user.getUsername());
            restResponse.setSuccess(true);
        } catch (Exception e) {
            if (e instanceof TipException) {
                restResponse.setMsg(e.getMessage());
            } else {
                LOGGER.error("登录失败", e);
            }
        }
        return restResponse;
    }

    /**
     * 账户注册
     *
     * @param username
     * @param password
     * @param email
     * @param avatar
     * @return
     */
    @Route(value = "signup", method = HttpMethod.POST)
    @JSON
    public RestResponse signup(@QueryParam String username, @QueryParam String password,
                               @QueryParam String email, @QueryParam String avatar) {

        RestResponse restResponse = new RestResponse();
        try {
            userService.signup(username, password, email, avatar);
            restResponse.setSuccess(true);
        } catch (Exception e) {
            if (e instanceof TipException) {
                restResponse.setMsg(e.getMessage());
            } else {
                LOGGER.error("注册失败", e);
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
                LOGGER.error("激活失败", e);
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
        response.go("/");
    }

    /**
     * 上传
     */
    @Route(value = "/uploadfile", method = HttpMethod.POST)
    public void upload(Request request, Response response) {
        User user = SessionUtils.getLoginUser();
        if (null == user) {
            return;
        }
        FileItem[] fileItems = request.files();
        if (null != fileItems && fileItems.length > 0) {

            FileItem fileItem = fileItems[0];

            String type = request.querys().get("type");

            String suffix = FileKit.getExtension(fileItem.fileName());
            if (StringKit.isNotBlank(suffix)) {
                suffix = "." + suffix;
            }

            if (!PatternKit.isImage(suffix)) {
                return;
            }
            String savePath = "";
            if(type.equals("avatar")){
                savePath = type + "/users/" + user.getUsername() + suffix;
            } else {
                String fileName = UUID.UU32() + suffix;
                // topic/biezhi/s6vunp10vihq9rb239ln9lotjh.jpg
                savePath = type + "/" + user.getUsername() + "/" + fileName;
            }

            String filePath = Constant.UPLOAD_FOLDER + File.separator + savePath;
            File file = new File(filePath);
            try {
                if(file.exists()){
                    file.delete();
                }
                if (!FileKit.exist(file.getParent())) {
                    file.getParentFile().mkdirs();
                }
                Utils.copyFileUsingFileChannels(fileItem.file(), file);
                JSONObject res = new JSONObject();
                res.put("status", 200);
                res.put("savekey", savePath);
                res.put("url", Constant.IMG_URL + "/" + savePath);
                response.json(res.toString());
            } catch (Exception e) {
                LOGGER.error("上传失败", e);
            }
        }
    }

}