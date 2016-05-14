package com.cht.emm.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import nariis.pi3000.framework.json.JSONArray;
import nariis.pi3000.framework.json.JSONObject;
import nariis.pi3000.framework.utility.StringUtil;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.dao.util.OrderBy;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.dao.impl.NewsDaoImpl;
import com.cht.emm.dao.impl.NewsDetailDaoImpl;
import com.cht.emm.model.News;
import com.cht.emm.model.NewsDetail;
import com.cht.emm.service.NewsService;
import com.cht.emm.util.TimestampUtil;
import com.cht.emm.util.UUIDGen;
import com.cht.emm.vo.NewsEntity;
import com.cht.emm.vo.NewsVO;

@Service
@Transactional(readOnly = true, timeout = 2, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class NewsServiceImpl extends BaseService<News, String> implements
		NewsService {

	@Resource(name = "newsDaoImpl")
	private NewsDaoImpl newsDaoImpl;

	@Resource(name = "newsDetailDaoImpl")
	private NewsDetailDaoImpl newsDetailDaoImpl;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void createNews(NewsVO newsVo, HttpServletRequest request) {
		if (newsVo.getId() == null) {
			newsVo.setId(UUIDGen.getUUID());
			newsVo.setUrl(StringUtil.substringBefore(request.getRequestURL()
					.toString(), "rest/news/add")
					+ "uploads/news/" + newsVo.getId() + ".html");
		}
		// 修改html
		File NEWS_DIR = new File(request.getSession().getServletContext()
				.getRealPath("/"), "uploads/news");
		File output = new File(NEWS_DIR, newsVo.getId() + ".html");
		BufferedWriter writer = null;
		try {
			OutputStreamWriter write = new OutputStreamWriter(
					new FileOutputStream(output), "UTF-8");
			writer = new BufferedWriter(write);
			writer.write(StringUtil.replace(News.NEWS_TEMPLATE, "{body}",
					newsVo.getBody()));
		} catch (Exception e) {

		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		News news = newsDaoImpl.get(newsVo.getId());
		news = newsVo.toNews(news);
		newsDaoImpl.saveOrUpdate(news);
		NewsDetail newsDetail = newsDetailDaoImpl.get(newsVo.getId());
		if (newsDetail == null) {
			newsDetail = new NewsDetail();
			newsDetail.setId(newsVo.getId());
		}
		String pics = newsDetail.getPics();
		newsDetail.setLocal(newsVo.getLocal());
		newsDetail.setMark(newsVo.getMark());
		newsDetail.setPics(newsVo.getPics());
		if (StringUtil.isNotEmpty(pics)
				&& !StringUtil.equals(pics, newsVo.getPics())) {
			String prePath = NEWS_DIR.getParent();
			String[] screenshots = pics.split(",");
			List<String> newScreenshots = new ArrayList<String>();
			if (StringUtil.isNotEmpty(newsVo.getPics())) {
				String[] screenshotsArray = newsVo.getPics().split(",");
				for (String s : screenshotsArray) {
					newScreenshots.add(s);
				}
			}
			for (String screenshotUrl : screenshots) {
				if (!newScreenshots.contains(screenshotUrl)) {
					String suffixPath = StringUtil.substringAfterLast(
							screenshotUrl, "uploads");
					File file = new File(prePath + suffixPath);
					if (file.exists()) {
						if (file.delete()) {
							file = file.getParentFile();
							file.delete();
						}
					}
				}
			}
		}
		newsDetailDaoImpl.saveOrUpdate(newsDetail);
	}

	public void deleteNews(String id, HttpServletRequest request) {
		// 删除资讯文件
		File NEWS_DIR = new File(request.getSession().getServletContext()
				.getRealPath("/"), "uploads/news");
		File output = new File(NEWS_DIR, id + ".html");
		if (output.exists()) {
			output.delete();
		}

		News news = newsDaoImpl.get(id);
		// 删除资讯图片
		if (StringUtil.isNotEmpty(news.getPhoto())) {
			String suffixPath = StringUtil.substringAfterLast(news.getPhoto(),
					"uploads");
			if (StringUtil.isNotEmpty(suffixPath)) {
				File photo = new File(NEWS_DIR.getParent(), suffixPath);
				if (photo.exists()) {
					if (photo.delete()) {
						photo = photo.getParentFile();
						photo.delete();
					}
				}
			}
		}
		String pics = news.getDetail().getPics();
		if (StringUtil.isNotEmpty(pics)) {
			String[] picsUrls = pics.split(",");
			for (int i = 0; i < picsUrls.length; i++) {
				String path = picsUrls[i];
				String suffixPath = StringUtil.substringAfterLast(path,
						"uploads");
				if (StringUtil.isNotEmpty(suffixPath)) {
					File photo = new File(NEWS_DIR.getParent(), suffixPath);
					if (photo.exists()) {
						if (photo.delete()) {
							photo = photo.getParentFile();
							photo.delete();
						}
					}
				}
			}
		}
		// 删除资讯中嵌入的图片
		String body = news.getBody();
		while (StringUtil.isNotEmpty(body)) {
			body = StringUtil.substringAfter(body, "<img ");
			if (StringUtil.isNotEmpty(body)) {
				String img = StringUtil.substringBetween(body, "src='", "'");
				String suffix = StringUtil.substringAfterLast(img, "uploads");
				if (StringUtil.isNotEmpty(suffix)) {
					File file = new File(NEWS_DIR.getParent(), suffix);
					if (file.exists()) {
						if (file.delete()) {
							file = file.getParentFile();
							file.delete();
						}
					}
				}
			}
		}
		// 删除资讯记录
		newsDetailDaoImpl.delete(id);
		newsDaoImpl.delete(id);
	}

	public NewsVO getNews(String id, HttpServletRequest request) {
		NewsVO vo = new NewsVO();
		vo.fromNews(newsDaoImpl.get(id));
		String url = request.getRequestURL().toString();
		String pre = url.substring(0, url.indexOf("rest/news"));
		String path = null;
		String suffix = null;
		if (StringUtil.isNotEmpty(vo.getPhoto())) {
			path = vo.getPhoto();
			suffix = path.substring(path.indexOf("uploads"));
			vo.setPhoto(pre + suffix);
		}
		String pics = vo.getPics();
		StringBuilder sb = new StringBuilder();
		if (StringUtil.isNotEmpty(pics)) {
			String[] picsUrls = pics.split(",");
			for (int i = 0; i < picsUrls.length; i++) {
				if (sb.length() > 0) {
					sb.append(",");
				}
				path = picsUrls[i];
				suffix = path.substring(path.indexOf("uploads"));
				sb.append(pre + suffix);
			}
			vo.setPics(sb.toString());
		}
		return vo;
	}

	public String loadNewsByTime(Timestamp p_time, HttpServletRequest request)
			throws Exception {
		JSONObject result = new JSONObject();
		result.put("time", System.currentTimeMillis());
		JSONArray newsList = new JSONArray();
		List<News> newses = null;
		// 检查新闻是否有更新
		if (p_time != null) {
			ConditionQuery query = new ConditionQuery();
			query.add(Restrictions.gt("time", p_time));
			newses = this.baseDao.listAll(query, null, 1, 1);
			if (newses.size() == 0) {
				result.put("news", newsList);
				return newsList.toString();
			}
		}
		// 返回最新的新闻列表
		newses = this.baseDao.listAll(" where type=1 ", " order by time desc ",
				1, 10);
		newses.addAll(this.baseDao.listAll(" where type=0 ",
				" order by time desc ", 1, 15));
		String url = request.getRequestURL().toString();
		String pre = url.substring(0, url.indexOf("rest/mobile"));
		for (News news : newses) {
			JSONObject object = new JSONObject();
			object.put("id", news.getId());
			object.put("title", news.getTitle());
			object.put("time", TimestampUtil.toString(news.getTime()));
			String path = news.getUrl();
			String suffix = path.substring(path.indexOf("uploads"));
			object.put("page", pre + suffix);
			if (StringUtil.isNotEmpty(news.getLead())) {
				object.put("lead", news.getLead());
			}
			object.put("type", news.getType());
			if (StringUtil.isNotEmpty(news.getPhoto())) {
				path = news.getPhoto();
				suffix = path.substring(path.indexOf("uploads"));
				object.put("photo", pre + suffix);
			}
			newsList.put(object);
		}

		result.put("news", newsList);
		return result.toString();

	}

	public JSONArray loadMoreNewsByTime(Timestamp time,
			HttpServletRequest request) throws Exception {
		JSONArray newsList = new JSONArray();
		ConditionQuery query = new ConditionQuery();
		query.add(Restrictions.lt("time", time));
		query.add(Restrictions.eq("type", 0));
		OrderBy orderby = new OrderBy();
		orderby.add(Order.desc("time"));
		List<News> newses = this.baseDao.listAll(query, orderby, 1, 15);
		String url = request.getRequestURL().toString();
		String pre = url.substring(0, url.indexOf("rest/mobile"));
		for (News news : newses) {
			JSONObject object = new JSONObject();
			object.put("id", news.getId());
			object.put("title", news.getTitle());
			object.put("time", TimestampUtil.toString(news.getTime()));
			String path = news.getUrl();
			String suffix = path.substring(path.indexOf("uploads"));
			object.put("page", pre + suffix);
			if (StringUtil.isNotEmpty(news.getLead())) {
				object.put("lead", news.getLead());
			}
			object.put("type", news.getType());
			if (StringUtil.isNotEmpty(news.getPhoto())) {
				path = news.getPhoto();
				suffix = path.substring(path.indexOf("uploads"));
				object.put("photo", pre + suffix);
			}
			newsList.put(object);
		}
		return newsList;
	}

	public List<NewsEntity> listNewsByTime(Timestamp p_time,
			HttpServletRequest request) throws Exception {
		List<NewsEntity> newsList = new ArrayList<NewsEntity>();
		List<News> newses = null;
		// 检查新闻是否有更新
		if (p_time != null) {
			ConditionQuery query = new ConditionQuery();
			query.add(Restrictions.gt("time", p_time));
			OrderBy orderby = new OrderBy();
			orderby.add(Order.desc("time"));
			newses = this.baseDao.listAll(query, orderby, 1, 15);
			if (newses.size() == 0) {
				return newsList;
			}
		} else {
			// 返回最新的新闻列表
			newses = this.baseDao.listAll(" ", " order by time desc ", 1, 15);
		}
		String url = request.getRequestURL().toString();
		String pre = url.substring(0, url.indexOf("rest/mobile"));
		for (News news : newses) {
			NewsEntity entity = new NewsEntity();
			entity.setNewsId(news.getId());
			entity.setMark(news.getDetail().getMark());
			entity.setTitle(news.getTitle());
			entity.setSource(news.getEditor());

			String path = news.getUrl();
			String suffix = path.substring(path.indexOf("uploads"));
			entity.setSource_url(pre + suffix);

			entity.setPublishTime(news.getTime().getTime());
			entity.setLocal(news.getDetail().getLocal());

			String pics = news.getDetail().getPics();
			if (StringUtil.isNotEmpty(pics)) {
				String[] picsUrls = pics.split(",");
				for (int i = 0; i < picsUrls.length; i++) {
					path = picsUrls[i];
					suffix = path.substring(path.indexOf("uploads"));
					if (i == 0) {
						entity.setPicOne(pre + suffix);
					} else if (i == 1) {
						entity.setPicTwo(pre + suffix);
					} else if (i == 2) {
						entity.setPicThr(pre + suffix);
						break;
					}
				}
			}

			entity.setIsLarge(news.getType() == 1);
			newsList.add(entity);
		}
		return newsList;

	}

	public List<NewsEntity> listMoreNewsByTime(Timestamp time,
			HttpServletRequest request) throws Exception {
		List<NewsEntity> newsList = new ArrayList<NewsEntity>();
		ConditionQuery query = new ConditionQuery();
		query.add(Restrictions.lt("time", time));
		OrderBy orderby = new OrderBy();
		orderby.add(Order.desc("time"));
		List<News> newses = this.baseDao.listAll(query, orderby, 1, 15);
		String url = request.getRequestURL().toString();
		String pre = url.substring(0, url.indexOf("rest/mobile"));
		for (News news : newses) {
			NewsEntity entity = new NewsEntity();
			entity.setNewsId(news.getId());
			entity.setMark(news.getDetail().getMark());
			entity.setTitle(news.getTitle());
			entity.setSource(news.getEditor());

			String path = news.getUrl();
			String suffix = path.substring(path.indexOf("uploads"));
			entity.setSource_url(pre + suffix);

			entity.setPublishTime(news.getTime().getTime());
			entity.setLocal(news.getDetail().getLocal());

			String pics = news.getDetail().getPics();
			if (StringUtil.isNotEmpty(pics)) {
				String[] picsUrls = pics.split(",");
				for (int i = 0; i < picsUrls.length; i++) {
					path = picsUrls[i];
					suffix = path.substring(path.indexOf("uploads"));
					if (i == 0) {
						entity.setPicOne(pre + suffix);
					} else if (i == 1) {
						entity.setPicTwo(pre + suffix);
					} else if (i == 2) {
						entity.setPicThr(pre + suffix);
						break;
					}
				}
			}

			entity.setIsLarge(news.getType() == 1);
			newsList.add(entity);
		}
		return newsList;
	}

	@Override
	public List<NewsVO> queryForPage(int count, String whereClause,
			String orderby, int pn, Integer length) {
		List<News> newses = this.baseDao.listAll(whereClause, orderby, pn,
				length);
		List<NewsVO> vos = new ArrayList<NewsVO>();
		for (News news : newses) {
			NewsVO vo = new NewsVO();
			vo.fromNews(news);
			vos.add(vo);
		}
		return vos;
	}

	@Resource(name = "newsDaoImpl")
	@Override
	public void setBaseDao(IBaseDao<News, String> baseDao) {
		this.baseDao = baseDao;
	}

}
