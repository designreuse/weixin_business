package com.cht.emm.service;

import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.Token;

public interface TokenService extends IBaseService<Token, String> {

	public abstract Token getToken(String id);

	public abstract Token getTokenByUserAndDevice(String uname, String devid);

	public abstract Token createToken(String uname, String devid);

	public abstract void updateToken(Token token);

	public abstract void deleteToken(Token token);

	public abstract void deleteTokenById(String tokenId);

	public abstract void deleteTokenByUserAndDevice(String username,
			String deviceID);
	
	public abstract Token getTokenByDevice(String deviceID);

}
