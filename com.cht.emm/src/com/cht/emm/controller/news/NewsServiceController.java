package com.cht.emm.controller.news;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import nariis.pi3000.framework.utility.StringUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cht.emm.controller.BaseController;
import com.cht.emm.service.NewsService;
import com.cht.emm.util.ColumnProperty;
import com.cht.emm.util.KeyValue;
import com.cht.emm.util.PageInfo;
import com.cht.emm.util.Response;
import com.cht.emm.util.db19.ParameterUtil;
import com.cht.emm.vo.NewsVO;

@Controller
public class NewsServiceController extends BaseController {

	@Resource(name = "newsServiceImpl")
	private NewsService newsServiceImpl;

	@RequestMapping(value = "rest/news/add", method = RequestMethod.POST)
	@ResponseBody
	public Response createNews(HttpServletRequest request,
			@RequestBody NewsVO newsVo) {
		Response res = new Response();
		newsServiceImpl.createNews(newsVo, request);
		res.setSuccessful(true);
		return res;
	}

	@RequestMapping(value = "rest/news/delete", method = RequestMethod.POST)
	@ResponseBody
	public Response deleteNews(HttpServletRequest request,
			@RequestParam String ids) {
		Response res = new Response();
		// 逐个删除应用
		for (String id : ids.split(",")) {
			newsServiceImpl.deleteNews(id, request);
		}
		res.setSuccessful(true);
		return res;
	}

	@RequestMapping(value = "rest/news/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response getNewsDetail(@PathVariable String id,
			HttpServletRequest request) {
		Response res = new Response();
		res.setSuccessful(true);
		res.setResultValue(newsServiceImpl.getNews(id, request));
		return res;
	}

	/**
	 * 分页加载资讯列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "rest/news/all/pages", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getNewsPages(HttpServletRequest request) {
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		if (StringUtil.isNotEmpty(search)) {

			for (ColumnProperty columnProperty : columns) {

				if (columnProperty.isSearchable()) {
					String colnName = columnProperty.getColName();
					if (condition.length() > 0) {
						condition.append(" or ");
					}
					if ("title".equals(colnName)) {
						condition.append(" title like '%" + search + "%' ");
					}
				}
			}

		}

		String conditionQuery = "";
		String orderList = "";
		if (condition.length() > 0) {
			conditionQuery = " where " + condition.toString();
		}
		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (orderBy.length() > 0) {
				orderBy.append(",");
			}

			String colName = columns.get(keyValue.getKey()).getColName();
			if ("news_time".equals(colName)) {
				orderBy.append(" time " + keyValue.getValue().toLowerCase());
			}

		}
		if (orderBy.length() > 0) {
			orderList = " order by " + orderBy.toString();
		} else {
			orderList = " order by time desc ";
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = newsServiceImpl.countAll(conditionQuery);
		int countAll = newsServiceImpl.countAll();
		List<NewsVO> news = newsServiceImpl.queryForPage(countFilter,
				conditionQuery, orderList,
				pageInfo.getStart() / pageInfo.getLength() + 1,
				pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", news);
		return resultMap;
	}

}
