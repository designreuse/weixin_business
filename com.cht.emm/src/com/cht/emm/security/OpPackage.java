/**
 * @Title: OpPackage.java
 * @Package: nari.mip.backstage.security
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-2-4 下午3:04:42
 * @Version: 1.0
 */
package com.cht.emm.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.cht.emm.service.SecurityOpService;



/**
 * @Class: OpPackage
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description:
 */
public class OpPackage {
	public static enum OP {
		REMOVE(1), ADD(2),REPLACE(3);
		private int type;

		private OP(int type) {
			this.type = type;
		}

		public int getType() {
			return type;
		}
	}

	private int type;
	private Map<String, List<String>> values = new HashMap<String, List<String>>();

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the values
	 */
	public Map<String, List<String>> getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(Map<String, List<String>> values) {
		this.values = values;
	}

	public void Wrap(OpItem item) {
		// 一个角色对应多个路径
		if (item.getType() == SecurityOpService.ITEM_TYPE.byRole.getType()) {
			String role = item.getKey();
			for (String url : item.getValues()) {
				if (values.get(url) == null) {
					values.put(url, new ArrayList<String>());
				}

				if (!values.get(url).contains(role)) {
					values.get(url).add(role);
				}

			}

		} else if (item.getType() == SecurityOpService.ITEM_TYPE.byUrl
				.getType()) {
			if (values.get(item.getKey()) == null) {
				values.put(item.getKey(), item.getValues());
			} else {
				List<String> roles = values.get(item.getKey());
				for (String role : item.getValues()) {
					if (!roles.contains(role)) {
						roles.add(role);
					}

				}
			}

		}

	}
	
	public List<OpItem> toItems(){
		List<OpItem> items = new ArrayList<OpItem>();
		Iterator<Entry<String, List<String>>> it = values.entrySet().iterator();
		while (it.hasNext()) {
			 Entry<String, List<String>> entry = it.next();
			 OpItem item = new OpItem();
			 item.setKey(entry.getKey());
			 item.setValues(entry.getValue());
			 item.setType(SecurityOpService.ITEM_TYPE.byUrl.getType());
			 items.add(item);
		}
		return items;
	}

}
