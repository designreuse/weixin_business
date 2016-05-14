package com.cht.emm.security.token;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;


import org.springframework.stereotype.Component;

import com.cht.emm.model.Token;
import com.cht.emm.service.TokenService;

@Component
public class TokenChecker {

	// 令牌失效时间，单位为毫秒
	public static final long TOKEN_INVALIDATE_TIME = 10 * 60 * 1000;

	@Resource(name = "tokenService")
	private TokenService tokenService;

	/**
	 * 检查令牌是否有效
	 * 
	 * @param tokenId
	 *            令牌ID
	 * @return
	 */
	public boolean check(String tokenId) {

		Token token = tokenService.getToken(tokenId);

		if (null == token)
			return false;

		long curTime = new Date().getTime();

		long updateTime = token.getUpdateTime().getTime();

		// 令牌超时,返回
		if (curTime - updateTime > TOKEN_INVALIDATE_TIME) {

			// tokenService.deleteTokenById(tokenId);

			return false;

		} else { // 令牌没有超时，则更新令牌访问时间

			token.setUpdateTime(new Timestamp(curTime));

			tokenService.updateToken(token);
		}

		return true;
	}

	// 检查令牌是否存在，若存在判断是否超时，如果超时更新令牌，否则直接返回令牌；若不存在则创建令牌
	public Token checkAndCreate(String uname, String devid) {

		Token token = tokenService.getTokenByUserAndDevice(uname, devid);

		if (null == token)
			return tokenService.createToken(uname, devid);

		long curTime = System.currentTimeMillis();

		long updateTime = token.getUpdateTime().getTime();

		// 令牌超时，更新令牌创建时间和访问时间
		if (curTime - updateTime > TOKEN_INVALIDATE_TIME) {

			token.setCreateTime(new Timestamp(curTime));
			token.setUpdateTime(new Timestamp(curTime));
			tokenService.updateToken(token);

		} else {// 更新访问时间

			token.setUpdateTime(new Timestamp(curTime));
			tokenService.updateToken(token);

		}

		return token;
	}

	public Token checkAndUpdate(String username, String deviceID) {
		// TODO Auto-generated method stub
		
		//限制一台设备在同一时刻只能一个人用，同时一个人在同一时刻只能用一台设备
		tokenService.deleteTokenByUserAndDevice(username, deviceID);

		Token token = tokenService.getTokenByUserAndDevice(username, deviceID);

		if (null == token)
			return tokenService.createToken(username, deviceID);

		long curTime = System.currentTimeMillis();

		long updateTime = token.getUpdateTime().getTime();

		// 令牌超时，更新令牌创建时间和访问时间
		if (curTime - updateTime > TOKEN_INVALIDATE_TIME) {

			token.setCreateTime(new Timestamp(curTime));
			token.setUpdateTime(new Timestamp(curTime));
			tokenService.updateToken(token);

		} else {// 更新访问时间

			token.setUpdateTime(new Timestamp(curTime));
			tokenService.updateToken(token);

		}

		return token;
	}
}
