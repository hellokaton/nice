package com.nice.controller;


import com.nice.ext.HomeTopic;
import com.nice.model.User;
import com.nice.service.StarsService;
import com.nice.service.TopicService;
import com.nice.service.UserService;
import com.nice.utils.SessionUtils;
import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.Controller;
import com.blade.mvc.annotation.QueryParam;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
public class IndexController {

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

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
	@Route(value = "/", method = HttpMethod.GET)
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
	@Route(value = "/explore", method = HttpMethod.GET)
	public String explore(){
		return "explore";
	}

	@Route(value = "/search", method = HttpMethod.GET)
	public String search(@QueryParam String q, Request request){
		List<User> users = userService.getUsers(q, 1, 999);
		request.attribute("users", users);
		request.attribute("q", q);
		return "search";
	}

	@Route(value = "/about", method = HttpMethod.GET)
	public String about(){
		return "about";
	}

	@Route(value = "/faq", method = HttpMethod.GET)
	public String faq(){
		return "faq";
	}

	@Route(value = "/api", method = HttpMethod.GET)
	public String api(){
		return "api";
	}

	@Route(value = "/donate", method = HttpMethod.GET)
	public String donate(){
		return "donate";
	}

}
