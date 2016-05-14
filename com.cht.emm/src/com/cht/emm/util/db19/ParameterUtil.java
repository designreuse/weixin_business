/**   
 * @Title: ParameterUtil.java 
 * @Package com.nari 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 张凯  zhangkai3@sgepri.sgcc.com.cn   
 * @date 2014-9-12 下午4:32:14 
 * @version V1.0   
 */
package com.cht.emm.util.db19;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cht.emm.util.ColumnProperty;
import com.cht.emm.util.KeyValue;
import com.cht.emm.util.PageInfo;


/**
 * @ClassName: ParameterUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张凯 zhangkai3@sgepri.sgcc.com.cn
 * @date 2014-9-12 下午4:32:14
 * 
 */
public class ParameterUtil {
	public static List<ColumnProperty> getColumnProperties(
			HttpServletRequest request) {
		List<ColumnProperty> columnProperties = new ArrayList<ColumnProperty>();

		Integer columnLength = Integer
				.parseInt(request.getParameter("iColumns"));
		if (columnLength != null && columnLength > 0) {
			for (int i = 0; i < columnLength; i++) {
				// 读取所有关于column的所有信息

				ColumnProperty property = new ColumnProperty();
				property.setColName(request.getParameter("mDataProp_" + i));
				property.setSearchable(request.getParameter("bSearchable_"+i).equals("true"));
				property.setSearchValue(request.getParameter("sSearch_"+i));
				property.setUseRegex(request.getParameter(
						"bRegex_"+i).equals("true"));
				property.setSortable(request.getParameter(
						"bSortable_"+i).equals("true"));
				columnProperties.add(property);

			}

		}

		return columnProperties;
	}

	public static PageInfo getPageInfo(HttpServletRequest request) {
		PageInfo pageInfo = new PageInfo();
		pageInfo.setStart(Integer.parseInt(request.getParameter("iDisplayStart")));
		pageInfo.setLength(Integer.parseInt(request.getParameter("iDisplayLength")));
		pageInfo.setColumnLength(Integer.parseInt(request
				.getParameter("iColumns")));
		pageInfo.setSearch(request.getParameter("sSearch"));
		List<KeyValue<Integer, String>> orders = new ArrayList<KeyValue<Integer,String>>();
		KeyValue<Integer, String > order = new KeyValue<Integer, String>();
		Integer sortColumns = Integer.parseInt(request.getParameter("iSortingCols"));
		for(int i =0;i<sortColumns;i++){
			order.setKey(Integer.parseInt(request.getParameter("iSortCol_"+i)));
			order.setValue(request.getParameter("sSortDir_"+i));
			orders.add(order);
		}
		pageInfo.setOrders(orders);
		return pageInfo;
	}

}
