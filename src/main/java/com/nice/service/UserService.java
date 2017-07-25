package com.nice.service;

import com.nice.ext.ActionType;
import com.nice.model.entity.User;
import com.nice.model.param.LoginParam;
import com.nice.model.param.SignupParam;

import java.util.List;

public interface UserService {
	
	User getUserById(String username);

	User update(User user) throws Exception;

	void delete(String username) throws Exception;

	/**
	 * 用户注册
	 *
	 */
    void signup(SignupParam signupParam);

	/**
	 * 激活用户
	 *
	 * @param code
	 */
	void active(String code);

	/**
	 * 用户登录
	 * @return
	 */
    User signin(LoginParam loginParam);

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