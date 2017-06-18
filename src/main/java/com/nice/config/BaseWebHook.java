package com.nice.config;

import com.blade.ioc.annotation.Bean;
import com.blade.ioc.annotation.Inject;
import com.blade.kit.StringKit;
import com.blade.mvc.hook.Invoker;
import com.blade.mvc.hook.WebHook;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.nice.model.User;
import com.nice.service.UserService;
import com.nice.utils.SessionUtils;
import com.nice.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Bean
public class BaseWebHook implements WebHook {

    private static final Logger LOGGE = LoggerFactory.getLogger(BaseWebHook.class);

    @Inject
    private UserService userService;

    @Override
    public boolean before(Invoker invoker) {
        Request request = invoker.request();
        Response response = invoker.response();

        LOGGE.info("UA >>> " + request.userAgent());
        LOGGE.info("用户访问地址 >>> " + request.uri() + ", 来路地址  >>> " + Utils.getIpAddr(request));

        User user = SessionUtils.getLoginUser();
        if (null == user) {
            String username = SessionUtils.getCookie(request, Constant.USER_IN_COOKIE);
            if (StringKit.isNotBlank(username)) {
                user = userService.getUserById(username);
                SessionUtils.setLoginUser(request.session(), user);
            } else {
                response.removeCookie(Constant.USER_IN_COOKIE);
            }
        }
        return true;
    }

}
