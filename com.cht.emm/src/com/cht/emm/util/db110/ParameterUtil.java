/**   
 * @Title: ParameterUtil.java 
 * @Package com.nari 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 张凯  zhangkai3@sgepri.sgcc.com.cn   
 * @date 2014-9-12 下午4:32:14 
 * @version V1.0   
 */
package com.cht.emm.util.db110;

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
				.parseInt(request.getParameter("colsize"));
		if (columnLength != null && columnLength > 0) {
			for (int i = 0; i < columnLength; i++) {
				// 读取所有关于column的所有信息

				ColumnProperty property = new ColumnProperty();
				property.setColName(request.getParameter("columns[" + i
						+ "][data]"));
				property.setSearchable(request.getParameter(
						"columns[" + i + "][searchable]").equals("true"));
				property.setSearchValue(request.getParameter("columns[" + i
						+ "][search][value]"));
				property.setUseRegex(request.getParameter(
						"columns[" + i + "][search][regex]").equals("true"));
				property.setSortable(request.getParameter(
						"columns[" + i + "][orderable]").equals("true"));
				columnProperties.add(property);

			}

		}

		return columnProperties;
	}

	public static PageInfo getPageInfo(HttpServletRequest request) {
		PageInfo pageInfo = new PageInfo();
		pageInfo.setStart(Integer.parseInt(request.getParameter("start")));
		pageInfo.setLength(Integer.parseInt(request.getParameter("length")));
		pageInfo.setColumnLength(Integer.parseInt(request
				.getParameter("colsize")));
		pageInfo.setSearch(request.getParameter("search[value]"));
		List<KeyValue<Integer, String>> orders = new ArrayList<KeyValue<Integer,String>>();
		KeyValue<Integer, String > order = new KeyValue<Integer, String>();
		order.setKey(Integer.parseInt(request.getParameter("order[0][column]")));
		order.setValue(request.getParameter("order[0][dir]"));
		orders.add(order);
		pageInfo.setOrders(orders);
		return pageInfo;
	}

}
