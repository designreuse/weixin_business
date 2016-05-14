package com.cht.emm.service;

import java.util.List;

import com.cht.emm.common.service.IBaseService;
import com.cht.emm.common.service.UnionEntityDeleteTrait;
import com.cht.emm.model.id.ResourceAuth;


public interface ResourceAuthService extends
		IBaseService<ResourceAuth, String>,
		UnionEntityDeleteTrait<String, String> {

	 List<String> getAuthUrlsByResourceId(String resId);

	String getPK(String id, String id2);
}