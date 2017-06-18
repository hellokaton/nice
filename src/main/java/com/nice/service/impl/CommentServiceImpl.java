package com.nice.service.impl;

import com.blade.ioc.annotation.Bean;
import com.nice.exception.TipException;
import com.nice.ext.ActionType;
import com.nice.model.Comment;
import com.nice.service.CommentService;
import com.nice.service.TopicService;
import com.blade.ioc.annotation.Inject;
import com.blade.jdbc.ActiveRecord;
import com.blade.kit.DateKit;
import com.blade.kit.StringKit;

@Bean
public class CommentServiceImpl implements CommentService {

	@Inject
	private ActiveRecord activeRecord;

	@Inject
	private TopicService topicService;

	@Override
	public void delete(Integer id) throws Exception {
		if(null == id){
			throw new TipException("主键为空");
		}
		try {
			activeRecord.delete(Comment.class, id);
		} catch (Exception e){
			throw e;
		}
	}

	@Override
	public void comment(String username, String tid, String comment) {
		if(StringKit.isBlank(username) || StringKit.isBlank(tid) || StringKit.isBlank(comment)){
			throw new TipException("参数不能为空");
		}

		Comment commen = new Comment();
		commen.setUsername(username);
		commen.setContent(comment);
		commen.setTid(tid);
		commen.setCreated(DateKit.nowUnix());

		activeRecord.insert(commen);
		topicService.upCount(ActionType.comments, tid, 1);
	}
}
