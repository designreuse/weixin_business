package com.cht.emm.service;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.News;
import com.cht.emm.vo.NewsEntity;
import com.cht.emm.vo.NewsVO;


public interface NewsService extends IBaseService<News, String> {

	public List<NewsVO> queryForPage(int count, String whereClause,
			String orderby, int pn, Integer length);

	public void createNews(NewsVO newsVo, HttpServletRequest request);

	public void deleteNews(String id, HttpServletRequest request);

	public NewsVO getNews(String id, HttpServletRequest request);

	public String loadNewsByTime(Timestamp timestamp, HttpServletRequest request)
			throws Exception;

	public Object loadMoreNewsByTime(Timestamp timestamp,
			HttpServletRequest request) throws Exception;

	public List<NewsEntity> listNewsByTime(Timestamp timestamp,
			HttpServletRequest request) throws Exception;

	public List<NewsEntity> listMoreNewsByTime(Timestamp timestamp,
			HttpServletRequest request) throws Exception;
}
