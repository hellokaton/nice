package com.nice.controller;


import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.QueryParam;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.nice.ext.HomeTopic;
import com.nice.model.User;
import com.nice.service.StarsService;
import com.nice.service.TopicService;
import com.nice.service.UserService;
import com.nice.utils.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

	@Inject
	private TopicService topicService;

	@Inject
	private StarsService starsService;

	@Inject
	private UserService userService;

	/**
	 * 首页
	 *
	 * @param request
	 * @return
	 */
    @GetRoute
    public String index(Request request){
		String username = null != SessionUtils.getLoginUser() ? SessionUtils.getLoginUser().getUsername() : null;
		List<HomeTopic> homeTopics = topicService.getTopics(username, null, 1, 9);
		request.attribute("topics", homeTopics);
		return "index";
	}

	/**
	 * 发现页面
	 *
	 * @return
	 */
    @GetRoute("/explore")
    public String explore(){
		return "explore";
	}

    @GetRoute("/search")
    public String search(@QueryParam String q, Request request){
		List<User> users = userService.getUsers(q, 1, 999);
		request.attribute("users", users);
		request.attribute("q", q);
		return "search";
	}

    @GetRoute("/about")
    public String about(){
		return "about";
	}

    @GetRoute("/faq")
    public String faq(){
		return "faq";
	}

    @GetRoute("/api")
    public String api(){
		return "api";
	}

    @GetRoute("/donate")
    public String donate(){
		return "donate";
	}

}
