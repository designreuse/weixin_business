/**
 * @Title: NodeInst.java
 * @Package: nari.mip.backstage.vo
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2014-12-26 下午3:36:49
 * @Version: 1.0
 */
package com.cht.emm.vo;

import java.util.List;

/**
 * @Class: NodeInst
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
public class NodeInst extends NodeCommon {

 
	private static final long serialVersionUID = -5809111282711537887L;
	private List<NodeInst> children;
	public List<NodeInst> getChildren() {
		return children;
	}
	public void setChildren(List<NodeInst> children) {
		this.children = children;
	}
}
