package com.nice.interceptor;

import com.nice.config.Constant;
import com.nice.model.User;
import com.nice.service.UserService;
import com.nice.utils.SessionUtils;
import com.nice.utils.Utils;
import com.blade.ioc.annotation.Inject;
import com.blade.kit.StringKit;
import com.blade.mvc.annotation.Intercept;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercept
public class BaseInterceptor implements Interceptor {
	
	private static final Logger LOGGE = LoggerFactory.getLogger(BaseInterceptor.class);
	
	@Inject
	private UserService userService;
	
	@Override
	public boolean before(Request request, Response response) {
		
		LOGGE.info("UA >>> " + request.userAgent());
		LOGGE.info("用户访问地址 >>> " + request.raw().getRequestURI() + ", 来路地址  >>> " + Utils.getIpAddr(request));

		User user = SessionUtils.getLoginUser();
		if(null == user){
			String username = SessionUtils.getCookie(request, Constant.USER_IN_COOKIE);
			if(StringKit.isNotBlank(username)){
				user = userService.getUserById(username);
				SessionUtils.setLoginUser(request.session(), user);
			} else {
				response.removeCookie(Constant.USER_IN_COOKIE);
			}
		}
		return true;
	}
	

	@Override
	public boolean after(Request request, Response response) {
		return true;
	}
	
}
