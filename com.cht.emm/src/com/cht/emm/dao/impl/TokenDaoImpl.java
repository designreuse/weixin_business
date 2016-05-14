package com.cht.emm.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDao;
import com.cht.emm.dao.TokenDao;
import com.cht.emm.model.Token;


@Repository
public class TokenDaoImpl extends BaseHibernateDao<Token, String> implements TokenDao {

	public static final String USERNAME = "userName";
	public static final String DEVICEID = "deviceId";
	
	@Override
	public Token getTokenByUserAndDevice(String uname, String devid) {
		// TODO Auto-generated method stub
		
		Criteria criteria = getSession().createCriteria(Token.class);
		criteria.add(Restrictions.eq(USERNAME, uname));
		criteria.add(Restrictions.eq(DEVICEID, devid));
		
		List<Token> tokens = list(criteria);
		if( null == tokens || tokens.size()==0)
			return null;
		
		return tokens.get(0);
	}
	
}
