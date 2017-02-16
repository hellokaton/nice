package com.nice.service;

import com.nice.ext.ActionType;
import com.nice.model.User;

import java.util.List;

public interface UserService {
	
	User getUserById(String username);

	User update(User user) throws Exception;

	void delete(String username) throws Exception;

	/**
	 * 用户注册
	 *
	 * @param username
	 * @param password
	 * @param email
	 * @param avatar
	 */
    void signup(String username, String password, String email, String avatar);

	/**
	 * 激活用户
	 *
	 * @param code
	 */
	void active(String code);

	/**
	 * 用户登录
	 *
	 * @param loginname
	 * @param password
	 * @return
	 */
    User signin(String loginname, String password);

	/**
	 * 更新次数
	 *
	 * @param actionType
	 * @param username
	 * @param hits
	 */
	void upCount(ActionType actionType, String username, int hits);

	/**
	 * 根据用户名查询用户
	 *
	 * @param username
	 * @param page
	 * @param limit
	 * @return
	 */
    List<User> getUsers(String username, int page, int limit);
}