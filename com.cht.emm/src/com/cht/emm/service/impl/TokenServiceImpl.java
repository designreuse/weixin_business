package com.cht.emm.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.dao.TokenDao;
import com.cht.emm.model.Token;
import com.cht.emm.service.TokenService;
import com.cht.emm.util.UUIDGen;

@Service("tokenService")
public class TokenServiceImpl extends BaseService<Token, String> implements
		TokenService {

	@Resource(name = "tokenDaoImpl")
	private TokenDao tokenDao;

	@Override
	public Token getToken(String id) {
		// TODO Auto-generated method stub
		return tokenDao.get(id);
	}

	@Override
	public Token createToken(String uname, String devid) {
		// TODO Auto-generated method stub

		String id = UUIDGen.getUUID();

		Token token = new Token();

		token.setTokenId(id);
		token.setDeviceId(devid);
		token.setUserName(uname);
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		token.setCreateTime(curTime);
		token.setUpdateTime(curTime);

		tokenDao.save(token);

		return token;
	}

	@Override
	public void updateToken(Token token) {
		// TODO Auto-generated method stub

		tokenDao.update(token);
	}

	@Override
	public void deleteToken(Token token) {

		// TODO Auto-generated method stub

		tokenDao.deleteObject(token);

	}

	@Override
	public void deleteTokenById(String tokenId) {
		// TODO Auto-generated method stub

		tokenDao.delete(tokenId);
	}

	@Override
	public Token getTokenByUserAndDevice(String uname, String devid) {
		// TODO Auto-generated method stub
		return tokenDao.getTokenByUserAndDevice(uname, devid);
	}

	@Resource(name = "tokenDaoImpl")
	@Override
	public void setBaseDao(IBaseDao<Token, String> baseDao) {
		// TODO Auto-generated method stub
		this.baseDao = baseDao;
	}

	@Override
	public void deleteTokenByUserAndDevice(String username, String deviceID) {
		// TODO Auto-generated method stub
		tokenDao.getSession()
				.createQuery(
						"delete from Token where (userName=:username and device_id is not null and device_id!=:deviceID) or (userName!=:username and userName is not null and device_id=:deviceID)")
				.setParameter("username", username)
				.setParameter("deviceID", deviceID).executeUpdate();

	}

	@Override
	public Token getTokenByDevice(String deviceID) {
		List<Token> tokens = tokenDao.listAll(" where device_id='" + deviceID
				+ "' ", " order by updateTime desc ", 1, 1);
		if (tokens.size() > 0) {
			return tokens.get(0);
		}
		return null;
	}
}
