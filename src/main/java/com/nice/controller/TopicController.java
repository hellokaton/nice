package com.nice.controller;

import com.nice.ext.HomeTopic;
import com.nice.exception.TipException;
import com.nice.model.User;
import com.nice.service.CommentService;
import com.nice.service.StarsService;
import com.nice.service.TopicService;
import com.nice.service.UserService;
import com.nice.utils.SessionUtils;
import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.blade.mvc.view.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

@Controller
public class TopicController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopicController.class);

    private Random r = new Random();

    @Inject
    private TopicService topicService;

    @Inject
    private CommentService commentService;

    @Inject
    private StarsService starsService;

    @Inject
    private UserService userService;

    /**
     * 发布主题
     *
     * @return
     */
    @Route(value = "/publish", method = HttpMethod.GET)
    public String publishPage(Request request) {
        int s = r.nextInt(8)%8 + 1;
        request.attribute("rand", s);
        return "publish";
    }

    /**
     * 发布操作
     *
     * @return
     */
    @Route(value = "/publish", method = HttpMethod.POST)
    @JSON
    public RestResponse publish(@QueryParam String content, @QueryParam String title) {

        RestResponse restResponse = new RestResponse();
        User user = SessionUtils.getLoginUser();
        if (null == user) {
            restResponse.setMsg("用户未登录");
            return restResponse;
        }

        String username = user.getUsername();
        try {
            topicService.publish(username, title, content);
            restResponse.setSuccess(true);
        } catch (Exception e) {
            if (e instanceof TipException) {
                restResponse.setMsg(e.getMessage());
            } else {
                LOGGER.error("发布主题失败", e);
            }
        }
        return restResponse;
    }

    /**
     * 评论主题
     *
     * @param id
     * @param comment
     * @return
     */
    @Route(value = "/comment/:id", method = HttpMethod.POST)
    @JSON
    public RestResponse comment(@PathParam String id, @QueryParam String comment) {

        RestResponse restResponse = new RestResponse();
        User user = SessionUtils.getLoginUser();
        if (null == user) {
            restResponse.setMsg("请登录后进行操作");
            return restResponse;
        }
        try {
            String username = user.getUsername();
            commentService.comment(username, id, comment);
            restResponse.setSuccess(true);
        } catch (Exception e) {
            if (e instanceof TipException) {
                restResponse.setMsg(e.getMessage());
            } else {
                LOGGER.error("发布主题失败", e);
            }
        }
        return restResponse;
    }

    /**
     * 给主题点赞
     *
     * @param id
     * @return
     */
    @Route(value = "/star/:id", method = HttpMethod.POST)
    @JSON
    public RestResponse star(@PathParam String id) {

        RestResponse restResponse = new RestResponse();
        User user = SessionUtils.getLoginUser();
        if (null == user) {
            restResponse.setMsg("请登录后进行操作");
            return restResponse;
        }
        try {
            String username = user.getUsername();
            starsService.star(username, id);
            restResponse.setSuccess(true);
        } catch (Exception e) {
            if (e instanceof TipException) {
                restResponse.setMsg(e.getMessage());
            } else {
                LOGGER.error("点赞失败", e);
            }
        }
        return restResponse;
    }

    /**
     * 给主题取消点赞
     *
     * @param id
     * @return
     */
    @Route(value = "/unstar/:id", method = HttpMethod.POST)
    @JSON
    public RestResponse unstar(@PathParam String id) {

        RestResponse restResponse = new RestResponse();
        User user = SessionUtils.getLoginUser();
        if (null == user) {
            restResponse.setMsg("请登录后进行操作");
            return restResponse;
        }
        try {
            String username = user.getUsername();
            starsService.unstar(username, id);
            restResponse.setSuccess(true);
        } catch (Exception e) {
            if (e instanceof TipException) {
                restResponse.setMsg(e.getMessage());
            } else {
                LOGGER.error("点赞失败", e);
            }
        }
        return restResponse;
    }

    /**
     * 用户个人主页
     *
     * @param username
     * @return
     */
    @Route(value = "/u/:username", method = HttpMethod.GET)
    public String homepage(@PathParam String username, Request request) {
        request.attribute("title", username + "的主页");
        User user = userService.getUserById(username);
        request.attribute("user", user);

        String cu = null != SessionUtils.getLoginUser() ? SessionUtils.getLoginUser().getUsername() : null;

        List<HomeTopic> homeTopics = topicService.getTopics(cu, username, 1, 9);
        request.attribute("topics", homeTopics);

        return "homepage";
    }

    @Route(value = "/topics/:isuser/:page", method = HttpMethod.GET)
    @JSON
    public RestResponse topics(@PathParam int isuser, @PathParam int page, @QueryParam(value = "limit", defaultValue = "9") int limit) {
        RestResponse restResponse = new RestResponse();
        String cu = null != SessionUtils.getLoginUser() ? SessionUtils.getLoginUser().getUsername() : null;
        String user = isuser == 1 ? cu : null;
        List<HomeTopic> homeTopics = topicService.getTopics(cu, user, page, limit);
        restResponse.setPayload(homeTopics);
        restResponse.setSuccess(true);
        return restResponse;
    }

}