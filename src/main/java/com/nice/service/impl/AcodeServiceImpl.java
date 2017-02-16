package com.nice.service.impl;

import com.nice.model.Acode;
import com.nice.service.AcodeService;
import com.nice.utils.UUID;
import com.blade.ioc.annotation.Inject;
import com.blade.ioc.annotation.Service;
import com.blade.jdbc.ActiveRecord;
import com.blade.kit.DateKit;

@Service
public class AcodeServiceImpl implements AcodeService {

	@Inject
	private ActiveRecord activeRecord;

	@Override
	public String newAcode(String username, String type) {
		Acode acode = new Acode();
		acode.setUsername(username);
		acode.setType(type);
		acode.setCreated(DateKit.getCurrentUnixTime());
		acode.setExpired(0);
		acode.setUsed(false);
		String code = UUID.UU32();
		acode.setCode(code);
		activeRecord.insert(acode);
		return code;
	}

	@Override
	public Acode getByCode(String code) {
		Acode acode = new Acode();
		acode.setCode(code);
		return activeRecord.one(acode);
	}

	@Override
	public void useCode(Integer id) {
		Acode acode = new Acode();
		acode.setId(id);
		acode.setUsed(true);
		activeRecord.update(acode);
	}
}
