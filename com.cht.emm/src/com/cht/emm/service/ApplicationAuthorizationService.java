package com.cht.emm.service;

import java.util.List;

import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.id.ApplicationAuthorization;
import com.cht.emm.vo.ApplicationAuthorizationVO;
import com.cht.emm.vo.GroupTreeNode;


public interface ApplicationAuthorizationService extends
		IBaseService<ApplicationAuthorization, String> {

	public List<ApplicationAuthorizationVO> listAppAuths(String app_id, int i, Integer integer);

	public List<GroupTreeNode> getGroupTree(String id);

}
