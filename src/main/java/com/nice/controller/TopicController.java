package com.nice.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.Request;
import com.blade.mvc.ui.RestResponse;
import com.nice.exception.TipException;
import com.nice.ext.HomeTopic;
import com.nice.model.User;
import com.nice.service.CommentService;
import com.nice.service.StarsService;
import com.nice.service.TopicService;
import com.nice.service.UserService;
import com.nice.utils.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

@Path
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
    @GetRoute("/publish")
    public String publishPage(Request request) {
        int s = r.nextInt(8) % 8 + 1;
        request.attribute("rand", s);
        return "publish";
    }

    /**
     * 发布操作
     *
     * @return
     */
    @PostRoute("/publish")
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
    @PostRoute("/comment/:id")
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
    @PostRoute("/star/:id")
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
    @PostRoute("/unstar/:id")
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
    @GetRoute("/u/:username")
    public String homepage(@PathParam String username, Request request) {
        request.attribute("title", username + "的主页");
        User user = userService.getUserById(username);
        request.attribute("user", user);

        String cu = null != SessionUtils.getLoginUser() ? SessionUtils.getLoginUser().getUsername() : null;

        List<HomeTopic> homeTopics = topicService.getTopics(cu, username, 1, 9);
        request.attribute("topics", homeTopics);

        return "homepage";
    }

    @GetRoute("/topics/:isuser/:page")
    @JSON
    public RestResponse topics(@PathParam int isuser, @PathParam int page,
                               @QueryParam(defaultValue = "9") int limit) {

        RestResponse restResponse = new RestResponse();
        String cu = null != SessionUtils.getLoginUser() ? SessionUtils.getLoginUser().getUsername() : null;
        String user = isuser == 1 ? cu : null;
        List<HomeTopic> homeTopics = topicService.getTopics(cu, user, page, limit);
        restResponse.setPayload(homeTopics);
        restResponse.setSuccess(true);
        return restResponse;
    }

}