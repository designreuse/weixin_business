/**
 * @Title: ThirdPartConfigCopier.java
 * @Package: nari.mip.backstage.util.objectcopier
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-3-23 下午4:53:47
 * @Version: 1.0
 */
package com.cht.emm.util.objectcopier;

import java.util.ArrayList;
import java.util.List;

import com.cht.emm.model.ThirdPartConfig;
import com.cht.emm.vo.ThirdPartConfigVO;

import nariis.pi3000.framework.utility.StringUtil;

/**
 * @Class: ThirdPartConfigCopier
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
public class ThirdPartConfigCopier {

	public static List<ThirdPartConfigVO> copy(List<ThirdPartConfig> configs) {
		// TODO Auto-generated method stub
		List<ThirdPartConfigVO> list = new ArrayList<ThirdPartConfigVO>();
		for (ThirdPartConfig obj : configs) {
			list.add(copy(obj));
		}
		return list;
	}
	
	public static ThirdPartConfigVO copy(ThirdPartConfig obj){
		if(obj == null){
			return null;
		}
		ThirdPartConfigVO copy =  new ThirdPartConfigVO();
		if(StringUtil.isNotBlank(obj.getId())){
			copy.setId(obj.getId());
		}
		
		if(StringUtil.isNotBlank(obj.getClassName())){
			copy.setClassName(obj.getClassName());
		}
		
		if(StringUtil.isNotBlank(obj.getRemoteUrl())){
			copy.setRemoteUrl(obj.getRemoteUrl());
		}
		
		if(StringUtil.isNotBlank(obj.getOthers())){
			copy.setOthers(obj.getOthers());
		}
		
		if(StringUtil.isNotBlank(obj.getName())){
			copy.setName(obj.getName());
		}
		
		if(obj.getGroup()!=null){
			copy.setGroupId(obj.getGroup().getId());
			copy.setGroupName(obj.getGroup().getGroupName());
		}
		
		return copy;
	}

}
