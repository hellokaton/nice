package com.nice.service;

public interface CommentService {

	void delete(Integer id) throws Exception;

	/**
	 * 评论主题
	 *
	 * @param username
	 * @param id
	 * @param comment
	 */
    void comment(String username, String id, String comment);
}
