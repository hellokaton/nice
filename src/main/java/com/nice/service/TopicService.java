package com.nice.service;

import com.nice.ext.HomeTopic;
import com.nice.ext.ActionType;

import java.util.List;

public interface TopicService {

	void delete(String id) throws Exception;

	/**
	 * 发布主题
	 *
	 * @param username
	 * @param title
	 * @param content
	 */
    void publish(String username, String title, String content);

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
