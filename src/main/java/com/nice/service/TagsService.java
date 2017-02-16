package com.nice.service;

import java.util.List;

import com.blade.jdbc.core.Take;
import com.blade.jdbc.model.Paginator;

import com.nice.model.Tags;

public interface TagsService {
	
	Tags getTagsById(String id);

	List<Tags> getTagsList(Take take);
	
	Paginator<Tags> getTagsPage(Take take);

	void update(Tags tags) throws Exception;

	void save(Tags tags) throws Exception;
	
	void delete(String id) throws Exception;
		
}
