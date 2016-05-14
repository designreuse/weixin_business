package com.cht.emm.util.objectcopier;

import java.util.ArrayList;
import java.util.List;

import com.cht.emm.model.PlatFormAgent;
import com.cht.emm.util.TimestampUtil;
import com.cht.emm.vo.PlatFormAgentVO;


public class PlatFormCopier {
	public static PlatFormAgentVO singleCopy(PlatFormAgent o) {
		PlatFormAgentVO copy = new PlatFormAgentVO();
		copy.setId(o.getId());
		copy.setIconUrl(o.getIconUrl());
		copy.setUrl(o.getUrl());
		copy.setVersionName(o.getVersionName());
		switch (o.getOs()) {
		case 0:
			copy.setOs("Android");
			break;
		case 1:
			copy.setOs("IOS");
			break;
		case 2:
			copy.setOs("windows");
			break;
		default:
			copy.setOs("其他");
			break;
		}
		copy.setCreateTime(TimestampUtil.toString(o.getCreateTime()));
		return copy;
	}
	
	public static PlatFormAgentVO copy(PlatFormAgent o){
		PlatFormAgentVO copy =singleCopy(o);
		copy.setCreateUser(o.getCreator().getUsername());
		return copy;
	}
	
	
	public static List<PlatFormAgentVO> copy(List<PlatFormAgent> objs){
		if(objs==null){
			return null;
		}
		List<PlatFormAgentVO> copies = new ArrayList<PlatFormAgentVO>();
		for (PlatFormAgent obj : objs) {
			PlatFormAgentVO copy = copy(obj);
			copies.add(copy);
		}
		
		return copies;
	}
	
}
