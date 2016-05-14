package com.cht.emm.controller.authorities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.controller.BaseController;
import com.cht.emm.model.User;
import com.cht.emm.security.MyUserDetails;
import com.cht.emm.service.AuthorityService;
import com.cht.emm.service.GroupService;
import com.cht.emm.service.ResourceService;
import com.cht.emm.service.RoleService;
import com.cht.emm.service.UserService;
import com.cht.emm.util.ConstantValue;
import com.cht.emm.vo.AuthVO;
import com.cht.emm.vo.ResourceVO;
import com.cht.emm.vo.RoleVO;
import com.cht.emm.vo.UserVO;

@Controller
public class AuthoritiesPageController extends BaseController {

	@Resource
	UserService userService;

	@Resource
	RoleService roleService;

	@Resource
	GroupService groupService;
	@Resource
	ResourceService resourceService;

	@Resource
	AuthorityService authorityService;

	@RequestMapping(value = "/console/user/list", method = RequestMethod.GET)
	public ModelAndView user_list() {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		UserVO user = userService.getUserById(userDetails.getUserId());
		String groupId = null;
		if (user.getUserType() == User.UserType.ORG_MASTER.getType()) {
			groupId = groupService.getTopGroup().getId();
		} else if (user.getUserType() == User.UserType.DEPART_MASTER.getType()
				|| user.getUserType() == User.UserType.COMMON.getType()) {
			if (user.getGroupIds() != null) {
				groupId = user.getGroupIds().get(0);

			}
		}
		return new ModelAndView("authorities/user_list", "groupId", groupId);
	}

	@RequestMapping(value = "/console/auth/list", method = RequestMethod.GET)
	public String auth_list() {

		return "authorities/auth_list";
	}

	@RequestMapping(value = "/console/auth/add", method = RequestMethod.GET)
	public ModelAndView create_auth() {
		Map<String, Object> result = new HashMap<String, Object>();
		Integer max = authorityService.getMaxIndex();
		result.put("maxLength", max + 1);
		return new ModelAndView("authorities/create_auth", result);
	}

	@RequestMapping(value = "/console/auth/edit", method = RequestMethod.GET)
	public ModelAndView edit_auth(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		AuthVO auth = authorityService.getById(id);
		Integer max = authorityService.getMaxIndex();
		result.put("maxLength", max);
		result.put("auth", auth);
		return new ModelAndView("authorities/edit_auth", result);
	}

	@RequestMapping(value = "/console/user/info", method = RequestMethod.GET)
	public ModelAndView user_info(String id) {
		UserVO user = userService.getUserById(id);

		return new ModelAndView("authorities/user_info", "user", user);
	}

	@RequestMapping(value = "/console/user/edit", method = RequestMethod.GET)
	public ModelAndView edit_user(String id) {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		UserVO admin = userService.getUserById(userDetails.getUserId());
		String topGroupId = null;
		String orgGroupId = null;
		UserVO user = userService.getUserById(id);
		orgGroupId = groupService.getTopGroup().getId();
		if (admin.getUserType() == User.UserType.ORG_MASTER.getType()) {
			topGroupId = orgGroupId;
		} else {
			topGroupId = groupService.get(admin.getGroups().get(0).getId())
					.getId();
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("roles", roleService.selectRole(user.getUserType()));
		result.put("topGroupId", topGroupId);
		result.put("orgGroupId", orgGroupId);
		result.put("groupId", user.getGroups() != null ? user.getGroups()
				.get(0).getId() : "");
		result.put("user", user);
		return new ModelAndView("authorities/edit_user", result);
	}

	/**
	 * @Name: create_user
	 * @Decription:
	 * @Time: 2015-1-26 下午1:45:43
	 * @param userId
	 *            创建者的id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/console/user/add", method = RequestMethod.GET)
	public ModelAndView create_user(String groupId) {
		Map<String, Object> result = new HashMap<String, Object>();
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		UserVO user = userService.getUserById(userDetails.getUserId());
		String topGroupId = null;
		int isTopGroup = 0;
		if (user.getUserType() == User.UserType.ORG_MASTER.getType()) {
			topGroupId = groupService.getTopGroup().getId();
			isTopGroup = 1;
		} else {
			topGroupId = user.getGroups().get(0).getId();
		}
		ConditionQuery query = new ConditionQuery();
		query.add(Restrictions.ne("isSystem", 1));

		result.put("roles", roleService.listAll(query, null, -1, -1));
		result.put("topGroupId", topGroupId);
		result.put("groupId", groupId);
		result.put("isTopGroup", isTopGroup);
		return new ModelAndView("authorities/create_user", result);
	}

	@RequestMapping(value = "/console/user/select", method = RequestMethod.GET)
	public ModelAndView user_select(String type, String id) {
		// List<UserVO> users = userService.selectUser(type,id);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", type);
		paramMap.put("id", id);
		return new ModelAndView("authorities/user_select", paramMap);
	}

	@RequestMapping(value = "/console/group/edit", method = RequestMethod.GET)
	public ModelAndView edit_group(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("group", groupService.getById(id));
		return new ModelAndView("authorities/edit_group", result);
	}

	@RequestMapping(value = "/console/group/add", method = RequestMethod.GET)
	public ModelAndView create_group() {
		Map<String, Object> result = new HashMap<String, Object>();
		return new ModelAndView("authorities/create_group", result);
	}

	@RequestMapping(value = "/console/create_device", method = RequestMethod.GET)
	public ModelAndView create_device(String userId) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("user", userService.getUserById(userId));
		res.put("ops", ConstantValue.getOps());
		return new ModelAndView("authorities/create_device", res);
	}

	@RequestMapping(value = "/console/role/add", method = RequestMethod.GET)
	public ModelAndView createRole() {
		Map<String, Object> result = new HashMap<String, Object>();
		// roleService.addRole(roleName,desc);
		return new ModelAndView("authorities/create_role", result);
	}

	@RequestMapping(value = "/console/role/list", method = RequestMethod.GET)
	public ModelAndView user_role_list() {
		Map<String, Object> result = new HashMap<String, Object>();
		// result.put("roles", roleService.getAllRoles());
		return new ModelAndView("authorities/user_role_list", result);
	}

	@RequestMapping(value = "/console/role/edit", method = RequestMethod.GET)
	public ModelAndView edit_role(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		RoleVO role = roleService.getById(id);
		result.put("role", role);
		result.put("selected1", (1&role.getUserType())>0);
		result.put("selected2", (2&role.getUserType())>0);
		result.put("selected3", (4&role.getUserType())>0);
		result.put("selected4", (8&role.getUserType())>0);
		return new ModelAndView("authorities/edit_role", result);
	}

	@RequestMapping(value = "/console/role/info", method = RequestMethod.GET)
	public ModelAndView role_info(String id) {
		RoleVO role = roleService.getById(id);
		return new ModelAndView("authorities/role_info", "role", role);
	}

	@RequestMapping(value = "/console/role/select", method = RequestMethod.GET)
	public ModelAndView role_select(String type, String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id", id);
		result.put("type", type);
		return new ModelAndView("authorities/role_select", result);
	}

	@RequestMapping(value = "/console/group/list", method = RequestMethod.GET)
	public ModelAndView user_group_list() {
		// Map<String, Object> res = new HashMap<String, Object>();
		// res.put("groups", groupService.getAllGroups());
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		UserVO user = userService.getUserById(userDetails.getUserId());
		String groupId = null;
		if (user.getUserType() == User.UserType.ORG_MASTER.getType()) {
			groupId = groupService.getTopGroup().getId();
		} else if (user.getUserType() == User.UserType.DEPART_MASTER.getType()) {
			if (user.getGroupIds() != null) {
				groupId = user.getGroupIds().get(0);

			}
		}
		return new ModelAndView("authorities/user_group_list", "groupId",
				groupId);
	}

	@RequestMapping(value = "/console/group/info", method = RequestMethod.GET)
	public ModelAndView group_info(String groupId) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("group", groupService.getById(groupId));
		return new ModelAndView("authorities/group_info", res);
	}

	@RequestMapping(value = "/console/group/select", method = RequestMethod.GET)
	public ModelAndView group_select(String type, String id) {

		// List<GroupVO> groups =null;
		// if(type!=null && !"".equals(type) ){
		// groups = groupService.selectGroups(type,id);
		// }else{
		// groups = groupService.getAllGroups();
		// }
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id", id);
		result.put("type", type);
		return new ModelAndView("authorities/group_select", result);
	}

	@RequestMapping(value = "/console/resource/list", method = RequestMethod.GET)
	public ModelAndView resource_list() {

		List<ResourceVO> resources = resourceService.getResources();
		return new ModelAndView("authorities/resource_list", "resources",
				resources);
	}

	@RequestMapping(value = "/console/menu/list", method = RequestMethod.GET)
	public ModelAndView menu_list() {

		return new ModelAndView("authorities/menu_list");
	}

	// console/menu/edit
	@RequestMapping(value = "/console/menu/edit", method = RequestMethod.GET)
	public ModelAndView edit_menu(String id) {

		return new ModelAndView("authorities/edit_menu", "id", id);
	}

	@RequestMapping(value = "/console/resource/select", method = RequestMethod.GET)
	public ModelAndView resource_select(String type, String id) {
		// List<ResourceVO> resources =resourceService.selectResource(type,id);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("type", type);
		result.put("id", id);
		return new ModelAndView("authorities/resource_select", result);
	}

	@RequestMapping(value = "/console/resource/edit", method = RequestMethod.GET)
	public ModelAndView edit_resource(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("resource", resourceService.getVMById(id));
		result.put("types", ConstantValue.getTypes());
		result.put("auths", resourceService.getAllAuths());
		return new ModelAndView("authorities/edit_resource", result);
	}

	@RequestMapping(value = "/console/resource/info", method = RequestMethod.GET)
	public ModelAndView resource_info(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("resource", resourceService.getVMById(id));
		return new ModelAndView("authorities/resource_info", result);
	}

	/**
	 * 资源相关的操作
	 */

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/resource/add", method = RequestMethod.GET)
	public ModelAndView createResource() {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("types", ConstantValue.getTypes());
		res.put("auths", resourceService.getAllAuths());
		return new ModelAndView("authorities/create_resource", res);
	}

	@RequestMapping(value = "/console/depart/manage", method = RequestMethod.GET)
	public ModelAndView departManage() {

		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		UserVO user = userService.getUserById(userDetails.getUserId());
		String groupId = null;
		if (user.getGroupIds() != null) {
			groupId = user.getGroupIds().get(0);

		}
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("groupId", groupId);
		return new ModelAndView("authorities/depart_manage", res);
	}

	@RequestMapping(value = "/console/depart/user", method = RequestMethod.GET)
	public ModelAndView departUser() {

		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		UserVO user = userService.getUserById(userDetails.getUserId());
		String groupId = null;
		if (user.getGroupIds() != null) {
			groupId = user.getGroupIds().get(0);

		}
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("groupId", groupId);
		return new ModelAndView("authorities/depart_user", res);
	}

}
