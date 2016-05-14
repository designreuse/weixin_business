/**
 * @Title: NodeTools.java
 * @Package: nari.mip.backstage.util
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2014-12-19 下午1:26:40
 * @Version: 1.0
 */
package com.cht.emm.util;

import java.util.ArrayList;
import java.util.List;

import com.cht.emm.vo.GroupVO;
import com.cht.emm.vo.MenuItemVO;
import com.cht.emm.vo.NodeCommon;
import com.cht.emm.vo.NodeInst;
import com.cht.emm.vo.NodeLazy;


/**
 * @Class: NodeTools
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description:
 */
public class NodeTools {

	public static void WrapNode(NodeCommon node, MenuItemVO vo) {
		node.setId(vo.getId());
		node.setType(vo.isLeaf() ? "leaf" : "node");

		if (StringUtil.filterId(vo.getId()) == null) {
			node.getState().put("disabled", true);
			node.getState().put("opened", true);
		}
		node.setText(vo.getTitle());
		node.setIcon(vo.isLeaf() ? "jstree-file" : "jstree-folder");
		node.setRoot(vo.isRoot());
		node.setAssystem(vo.getIsSystem()!=0);
	}

	public static NodeLazy toNode(MenuItemVO vo) {
		if (vo == null) {
			return null;
		}
		NodeLazy node = new NodeLazy();
		WrapNode(node, vo);
		node.setChildren(vo.getSubItems() != null && !vo.isLeaf());
		return node;
	}

	public static NodeInst toNodeInst(MenuItemVO vo) {
		if (vo == null) {
			return null;
		}
		NodeInst node = new NodeInst();
		WrapNode(node, vo);
		
		List<NodeInst> nodes = new ArrayList<NodeInst>();
		if (vo.getSubItems() != null) {
			node.getState().put("opened", true);
			List<MenuItemVO> subs = vo.getSubItems();
			for (MenuItemVO menuItemVO : subs) {
				nodes.add(toNodeInst(menuItemVO));
			}

		}
		node.setChildren(nodes);
		return node;
	}
	
	public static void wrap(NodeCommon node,GroupVO vo){
		if(vo == null){
			node.getState().put("disabled", true);
			node.getState().put("opened", true);
			node.setText("创建顶级组");
		}else {
			node.setId(vo.getId());
			node.setText(vo.getGroupName());
			if(vo.getId()==null){
				node.getState().put("disabled", true);
				node.getState().put("opened", true);
			}
			if(vo.getThirdPartType()!=0){
				node.setOther("third");
			}
		}
		node.setIcon("node");
		 
	}
	public static NodeInst toNodeInst(GroupVO vo){
		NodeInst node = new NodeInst();
        wrap(node, vo);
        if(vo !=null){
        	 if(vo.getSubGroups() != null && vo.getSubGroups().size() > 0){
             	node.setChildren(new ArrayList<NodeInst>());
             	for (GroupVO item : vo.getSubGroups()) {
             		node.getChildren().add(toNodeInst(item));
     			}
             	node.setIcon("jstree-folder");
             }else {
     			node.setIcon("jstree-file");
     		}
		}
       
		return node;
	}
	
	public static NodeInst toNodeInstOrg(GroupVO vo){
		NodeInst node = new NodeInst();
        wrap(node, vo);
        if(vo !=null){
        	 node.setChildren(new ArrayList<NodeInst>());
        	 if(vo.getSubGroups() != null && vo.getSubGroups().size() > 0){
             	for (GroupVO item : vo.getSubGroups()) {
             		node.getChildren().add(toNodeInstOrg(item));
     			}
             	node.setIcon("jstree-folder");
             	node.setType("node");
             	
             }else {
            	 node.setIcon("jstree-file");
			}
		}
       
		return node;
	}

	public static List<NodeLazy> toNodes(List<MenuItemVO> vos) {
		if (vos == null) {
			return null;
		}
		List<NodeLazy> nodes = new ArrayList<NodeLazy>();
		for (MenuItemVO vo : vos) {
			nodes.add(toNode(vo));
		}

		return nodes;
	}
	public static List<NodeInst> toNodeInsts(List<MenuItemVO> vos) {
		if (vos == null) {
			return null;
		}
		List<NodeInst> nodes = new ArrayList<NodeInst>();
		for (MenuItemVO vo : vos) {
			nodes.add(toNodeInst(vo));
		}

		return nodes;
	}
	
}
