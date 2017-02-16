package com.nice.service;

import java.util.List;

import com.blade.jdbc.core.Take;
import com.blade.jdbc.model.Paginator;

import com.nice.model.Follow;

public interface FollowService {
	
	Follow getFollowById(Integer id);

	List<Follow> getFollowList(Take take);
	
	Paginator<Follow> getFollowPage(Take take);

	void update(Follow follow) throws Exception;

	void save(Follow follow) throws Exception;
	
	void delete(Integer id) throws Exception;
		
}
