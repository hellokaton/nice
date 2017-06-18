package com.nice.service.impl;

import com.blade.ioc.annotation.Bean;
import com.blade.jdbc.ActiveRecord;
import com.blade.jdbc.core.Take;
import com.nice.ext.ActionType;
import com.nice.model.Stars;
import com.nice.service.StarsService;
import com.nice.service.TopicService;
import com.blade.ioc.annotation.Inject;

@Bean
public class StarsServiceImpl implements StarsService {

    @Inject
    private ActiveRecord activeRecord;

    @Inject
    private TopicService topicService;

    @Override
    public void star(String username, String tid) {
        Stars stars = new Stars();
        stars.setTid(tid);
        stars.setUsername(username);
        activeRecord.insert(stars);
        topicService.upCount(ActionType.stars, tid, 1);
    }

    @Override
    public void unstar(String username, String tid) {
        Take take = new Take(Stars.class);
        take.eq("username", username).eq("tid", tid);
        activeRecord.delete(take);
        topicService.upCount(ActionType.stars, tid, -1);
    }

}