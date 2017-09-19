package com.nice.service.impl;

import com.blade.ioc.annotation.Bean;
import com.blade.ioc.annotation.Inject;
import com.blade.jdbc.core.ActiveRecord;
import com.blade.jdbc.page.Page;
import com.blade.jdbc.page.PageRow;
import com.blade.kit.DateKit;
import com.blade.kit.StringKit;
import com.nice.exception.TipException;
import com.nice.ext.ActionType;
import com.nice.model.dto.HomeTopic;
import com.nice.model.entity.Topic;
import com.nice.service.TopicService;
import com.nice.service.UserService;
import com.nice.utils.UUID;

import java.util.List;

@Bean
public class TopicServiceImpl implements TopicService {

    @Inject
    private UserService userService;

    @Override
    public void delete(String id) throws Exception {
        if (null == id) {
            throw new TipException("主键为空");
        }
        try {
            new Topic().delete(id);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void publish(Topic topic) {
        String id    = UUID.UU16();
        topic.setId(id);
        topic.setCreated(DateKit.nowUnix());
        topic.setComments(0);
        topic.setStars(0);

        topic.save();

        userService.upCount(ActionType.topics, topic.getUsername(), 1);
    }

    @Override
    public List<HomeTopic> getTopics(String me, String user, int page, int limit) {

        if (page <= 0) {
            page = 1;
        }
        if (limit <= 0 || limit >= 50) {
            limit = 20;
        }

        String sql = "select a.id, a.username, b.nickname, b.avatar, a.title, a.content, a.stars, a.comments, a.created from t_topic a left join t_user b on a.username = b.username order by a.created desc";
        if (StringKit.isNotBlank(me)) {
            String where = null != user ? " and a.username = '" + user + "' " : "";
            sql = "select a.id, a.username, b.nickname, b.avatar, a.title, a.content, a.stars, a.comments, a.created, c.id as starid from t_topic a left join t_user b on a.username = b.username " +
                    "left join t_stars c on a.id = c.tid and c.username = '" + me + "' where 1=1 " + where + " order by a.created desc";
        }

        HomeTopic       homeTopic     = new HomeTopic();
        Page<HomeTopic> homeTopicPage = homeTopic.page(new PageRow(page, limit), sql, null);
        if (null != homeTopicPage) {
            return homeTopicPage.getRows();
        }
        return null;
    }

    @Override
    public void upCount(ActionType actionType, String tid, int hits) {
        String m    = hits > 0 ? "+" : "";
        String type = actionType.toString();
        String sql  = "update t_topic set " + type + " = (" + type + m + hits + ") where id = '" + tid + "'";

        ActiveRecord activeRecord = new ActiveRecord();
        activeRecord.execute(sql);
    }
}
