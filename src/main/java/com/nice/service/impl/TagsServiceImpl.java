package com.nice.service.impl;

import java.util.List;

import com.blade.ioc.annotation.Bean;
import com.blade.ioc.annotation.Inject;
import com.blade.jdbc.ActiveRecord;
import com.blade.jdbc.core.Take;
import com.blade.jdbc.model.Paginator;

import com.nice.model.Tags;
import com.nice.exception.TipException;
import com.nice.service.TagsService;

@Bean
public class TagsServiceImpl implements TagsService {

	@Inject
	private ActiveRecord activeRecord;

	@Override
	public Tags getTagsById(String id) {
		if(null == id){
			return null;
		}
		return activeRecord.byId(Tags.class, id);
	}

	@Override
	public List<Tags> getTagsList(Take take) {
		if(null != take){
			if(null != take.getPageRow()){
				return this.getTagsPage(take).getList();
			}
			return activeRecord.list(take);
		}
		return null;
	}
	
	@Override
	public Paginator<Tags> getTagsPage(Take take) {
		if(null != take){
			return activeRecord.page(take);
		}
		return null;
	}
	
	@Override
	public void save(Tags tags) throws Exception {
		if(null == tags){
			throw new TipException("对象为空");
		}
		try {
			activeRecord.insert(tags);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void update(Tags tags) throws Exception {
		if(null == tags){
			throw new TipException("对象为空");
		}
		try {
			activeRecord.update(tags);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void delete(String id) throws Exception {
		if(null == id){
			throw new TipException("主键为空");
		}
		try {
			activeRecord.delete(Tags.class, id);
		} catch (Exception e){
			throw e;
		}
	}
		
}
