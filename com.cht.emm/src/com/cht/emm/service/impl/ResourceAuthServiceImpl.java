package com.cht.emm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.dao.ResourceAuthDao;
import com.cht.emm.model.id.ResourceAuth;
import com.cht.emm.service.ResourceAuthService;

@Service("resourceAuthService")
public class ResourceAuthServiceImpl extends BaseService<ResourceAuth, String> implements ResourceAuthService {

	@Override
	@Resource(name="resourceAuthDao")
	public void setBaseDao(IBaseDao<ResourceAuth, String> baseDao) {
		// TODO Auto-generated method stub
		this.baseDao = baseDao;
		
	}

	@Override
	public void deleteUnionEntityA2B(String pk1, String[] pk2s) {
		// TODO Auto-generated method stub
		for (String spk : pk2s) {
			String pk = ((ResourceAuthDao) baseDao).getPK(pk1,spk);
			if(pk!=null){
				delete(pk);
			}
		}
	}

	@Override
	public void deleteUnionEntityB2A(String pk1, String[] pk2s) {
		// TODO Auto-generated method stub
		for (String spk : pk2s) {
			String pk = ((ResourceAuthDao) baseDao).getPK(spk,pk1);
			if(pk!=null){
				delete(pk);
			}
		}
	}

	public List<String> getAuthUrlsByResourceId(String resId){
		
		return ((ResourceAuthDao) baseDao).getAuthUrlsByResourceId(resId);
	}

	@Override
	public String getPK(String resourceId, String authId) {
		// TODO Auto-generated method stub
		return ((ResourceAuthDao)baseDao).getPK(resourceId, authId);
	}
}
