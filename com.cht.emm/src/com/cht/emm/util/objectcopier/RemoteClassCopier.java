/**
 * @Title: RemoteClassCopier.java
 * @Package: nari.mip.backstage.util.objectcopier
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-4-2 下午4:23:39
 * @Version: 1.0
 */
package com.cht.emm.util.objectcopier;

import java.util.ArrayList;
import java.util.List;

import com.cht.emm.model.RemoteClass;
import com.cht.emm.vo.RemoteClassVO;


/**
 * @Class: RemoteClassCopier
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description:
 */
public class RemoteClassCopier {
	public static RemoteClassVO copy(RemoteClass obj) {
		RemoteClassVO copy = new RemoteClassVO();
		copy.setId(obj.getId());
		copy.setClassDesc(obj.getDesc());
		copy.setClassName(obj.getClassName());
		copy.setPackageName(obj.getPackageName());
		copy.setContent(obj.getContent());
		copy.setEnabled(obj.getEnabled());
		return copy;
	}

	public static List<RemoteClassVO> copy(List<RemoteClass> objs) {
		if (objs == null || objs.size() == 0) {
			return null;
		}
		List<RemoteClassVO> list = new ArrayList<RemoteClassVO>();
		for (RemoteClass obj : objs) {
			list.add(RemoteClassCopier.copy(obj));
		}
		return list;
	}
}
