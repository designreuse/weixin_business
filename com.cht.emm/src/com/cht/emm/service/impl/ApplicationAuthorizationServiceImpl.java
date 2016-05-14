package com.cht.emm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.dao.impl.GroupDaoImpl;
import com.cht.emm.model.Group;
import com.cht.emm.model.id.ApplicationAuthorization;
import com.cht.emm.service.ApplicationAuthorizationService;
import com.cht.emm.vo.ApplicationAuthorizationVO;
import com.cht.emm.vo.GroupTreeNode;


@Service
public class ApplicationAuthorizationServiceImpl extends
		BaseService<ApplicationAuthorization, String> implements
		ApplicationAuthorizationService {

	@Resource(name = "groupDao")
	private GroupDaoImpl groupDaoImpl;

	@Resource(name = "applicationAuthorizationDaoImpl")
	@Override
	public void setBaseDao(IBaseDao<ApplicationAuthorization, String> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<ApplicationAuthorizationVO> listAppAuths(String app_id, int pn,
			Integer length) {
		List<ApplicationAuthorizationVO> vos = new ArrayList<ApplicationAuthorizationVO>();
		ConditionQuery query = new ConditionQuery();
		query.add(Restrictions.eq("app.id", app_id));
		List<ApplicationAuthorization> appAuths = this.baseDao.listAll(query,
				null, pn, length);
		for (ApplicationAuthorization appAuth : appAuths) {
			ApplicationAuthorizationVO vo = new ApplicationAuthorizationVO();
			vo.fromAppAuth(appAuth);
			vos.add(vo);
		}
		return vos;
	}

	@Override
	public List<GroupTreeNode> getGroupTree(String app_id) {
		List<GroupTreeNode> nodes = new ArrayList<GroupTreeNode>();
		// 查询已经配置应用权限的组织机构
		ConditionQuery query = new ConditionQuery();
		query.add(Restrictions.eq("app.id", app_id));
		List<ApplicationAuthorization> appAuths = this.baseDao.listAll(query,
				null, -1, -1);
		List<String> groups = new ArrayList<String>();
		for (ApplicationAuthorization appAuth : appAuths) {
			groups.add(appAuth.getGroup().getId());
		}
		// 查询组织机构树，去除已配置的节点
		query = new ConditionQuery();
		query.add(Restrictions.isNull("parentGroup"));
		List<Group> roots = groupDaoImpl.listAll(query, null, -1, -1);
		for (Group root : roots) {
			GroupTreeNode node = this.getNode(root, groups);
			if (node != null) {
				nodes.add(node);
			}
		}
		return nodes;
	}

	private GroupTreeNode getNode(Group group, List<String> ids) {
		if (ids.contains(group.getId())) {
			return null;
		}
		GroupTreeNode node = new GroupTreeNode();
		node.setId(group.getId());
		node.setText(group.getGroupName());
		for (Group sub : group.getSubGroups()) {
			GroupTreeNode subnode = this.getNode(sub, ids);
			if (subnode != null) {
				node.getChildren().add(subnode);
			}
		}
		return node;
	}

}
