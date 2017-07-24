package com.nice.service.impl;

import com.blade.ioc.annotation.Bean;
import com.blade.ioc.annotation.Inject;
import com.nice.ext.ActionType;
import com.nice.model.Stars;
import com.nice.service.StarsService;
import com.nice.service.TopicService;

@Bean
public class StarsServiceImpl implements StarsService {

    @Inject
    private TopicService topicService;

    @Override
    public void star(String username, String tid) {
        Stars stars = new Stars();
        stars.setTid(tid);
        stars.setUsername(username);
        stars.save();
        topicService.upCount(ActionType.stars, tid, 1);
    }

    @Override
    public void unStar(String username, String tid) {
        Stars stars = new Stars();
        stars.where("username", username).and("tid", tid).delete();

        topicService.upCount(ActionType.stars, tid, -1);
    }

}