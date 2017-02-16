package com.nice.service;

public interface StarsService {

	/**
	 * 点赞
	 *
	 * @param username
	 * @param tid
	 */
    void star(String username, String tid);

	/**
	 * 取消点赞
	 *
	 * @param username
	 * @param tid
	 */
	void unstar(String username, String tid);

}
