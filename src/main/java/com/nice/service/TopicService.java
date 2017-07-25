package com.nice.service;

import com.nice.model.dto.HomeTopic;
import com.nice.ext.ActionType;
import com.nice.model.entity.Topic;

import java.util.List;

public interface TopicService {

	void delete(String id) throws Exception;

	/**
	 * 发布主题
	 */
    void publish(Topic topic);

	/**
	 * 分页获取首页主题
	 *
	 * @param me
	 * @param user
	 * @param page
	 * @param limit
	 * @return
	 */
	List<HomeTopic> getTopics(String me, String user, int page, int limit);

	/**
	 * 更新次数
	 *
	 * @param actionType
	 * @param tid
	 * @param hits
	 */
    void upCount(ActionType actionType, String tid, int hits);

}
