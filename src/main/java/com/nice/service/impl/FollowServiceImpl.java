package com.nice.service.impl;

import java.util.List;

import com.blade.ioc.annotation.Inject;
import com.blade.ioc.annotation.Service;
import com.blade.jdbc.ActiveRecord;
import com.blade.jdbc.core.Take;
import com.blade.jdbc.model.Paginator;

import com.nice.model.Follow;
import com.nice.exception.TipException;
import com.nice.service.FollowService;

@Service
public class FollowServiceImpl implements FollowService {

	@Inject
	private ActiveRecord activeRecord;

	@Override
	public Follow getFollowById(Integer id) {
		if(null == id){
			return null;
		}
		return activeRecord.byId(Follow.class, id);
	}

	@Override
	public List<Follow> getFollowList(Take take) {
		if(null != take){
			if(null != take.getPageRow()){
				return this.getFollowPage(take).getList();
			}
			return activeRecord.list(take);
		}
		return null;
	}
	
	@Override
	public Paginator<Follow> getFollowPage(Take take) {
		if(null != take){
			return activeRecord.page(take);
		}
		return null;
	}
	
	@Override
	public void save(Follow follow) throws Exception {
		if(null == follow){
			throw new TipException("对象为空");
		}
		try {
			activeRecord.insert(follow);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void update(Follow follow) throws Exception {
		if(null == follow){
			throw new TipException("对象为空");
		}
		try {
			activeRecord.update(follow);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void delete(Integer id) throws Exception {
		if(null == id){
			throw new TipException("主键为空");
		}
		try {
			activeRecord.delete(Follow.class, id);
		} catch (Exception e){
			throw e;
		}
	}
		
}
