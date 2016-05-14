package com.cht.emm.util.objectcopier;

import java.util.ArrayList;
import java.util.List;

import com.cht.emm.model.Authority;
import com.cht.emm.vo.AuthVO;


public class AuthorityCopier {
	public static List<AuthVO> copy(List<Authority> auths) {
		List<AuthVO> copies =  new ArrayList<AuthVO>();
		
		for (Authority auth : auths) {
			AuthVO copy = copy(auth);
			copies.add(copy);
		}
		return copies;
	}

	public static AuthVO copy(Authority auth) {
		// TODO Auto-generated method stub
		if(auth == null ){
			return null;
		}
		AuthVO copy = new AuthVO();
		copy.setId(auth.getId());
		copy.setName(auth.getName());
		copy.setLocIndex(auth.getLocIndex());
		copy.setShowIndex(auth.getShowIndex());
		copy.setDescp(auth.getDescp());
		copy.setIsSystem(auth.getIsSystem());
		return copy;
	}
}
