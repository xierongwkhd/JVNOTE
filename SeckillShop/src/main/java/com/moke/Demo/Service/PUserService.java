package com.moke.Demo.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moke.Demo.Dao.PUserDao;
import com.moke.Demo.Dao.UserDao;
import com.moke.Demo.Domain.PUser;
import com.moke.Demo.Vo.LoginVo;
import com.moke.Demo.base.CodeMsg;
import com.moke.Demo.base.exception.GlobleException;
import com.moke.Demo.base.redis.PUserKey;
import com.moke.Demo.base.util.MD5Util;
import com.moke.Demo.base.util.UUIDUtil;

@Service
public class PUserService {
	
	public static final String COOKIE_NAME_TOKEN = "token";

	@Autowired
	private PUserDao pUserDao;
	@Autowired
	RedisService redisService;
	
	public PUser getById(long id) {
		PUser user = redisService.get(PUserKey.getById, ""+id, PUser.class);
		if(user!=null)
			return user;
		user = pUserDao.getById(id);
		if(user!=null)
			redisService.set(PUserKey.getById, ""+id, user);
		return user;
	}
	
	public boolean updatePassword(String token,long id,String passwordNew) {
		PUser user = getById(id);
		if(user==null)
			throw new GlobleException(CodeMsg.MOBILE_NOT_EXIST);
		PUser upUser = new PUser();
		upUser.setId(id);
		upUser.setPassword(MD5Util.formPassToDBPass(passwordNew,user.getSalt()));
		pUserDao.update(upUser);
		//处理缓存
		redisService.delete(PUserKey.getById, ""+id);
		user.setPassword(upUser.getPassword());
		redisService.set(PUserKey.token, token, user);
		return true;
	}
	
	public PUser getByToken(HttpServletResponse response, String token) {
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		PUser user = redisService.get(PUserKey.token, token, PUser.class);
		//延长有效期
		if(user != null) {
			addCookie(response, token, user);
		}
		return user;
	}

	public String login(HttpServletResponse response, LoginVo loginVo) {
		String mobile = loginVo.getMobile();
		String formPass = loginVo.getPassword();
				
		PUser user = pUserDao.getById(Long.parseLong(mobile));
		if(user==null)
			throw new GlobleException(CodeMsg.MOBILE_NOT_EXIST);
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
		if(!calcPass.equals(dbPass))
			throw new GlobleException(CodeMsg.PASSWORD_ERROR);
		//生成cookie
		String token = UUIDUtil.uuid();
		addCookie(response,token,user);
		return token;
	}
	
	private void addCookie(HttpServletResponse response, String token, PUser user) {
		redisService.set(PUserKey.token, token, user);
		Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
		cookie.setMaxAge(PUserKey.token.expierSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}
