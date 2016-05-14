package com.cht.emm.dao;

import java.util.List;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.dao.UnionEntityGetPK;
import com.cht.emm.model.id.ResourceAuth;


public interface ResourceAuthDao extends IBaseDao<ResourceAuth, String>,
		UnionEntityGetPK<String, String, String> {

	List<String> getAuthUrlsByResourceId(String resId);
		
}
