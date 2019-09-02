package com.moke.Demo.base.access;

import java.io.OutputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.alibaba.fastjson.JSON;
import com.moke.Demo.Domain.PUser;
import com.moke.Demo.Service.PUserService;
import com.moke.Demo.Service.RedisService;
import com.moke.Demo.base.CodeMsg;
import com.moke.Demo.base.Result;
import com.moke.Demo.base.redis.AccessKey;

@Service
public class AccessInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	private PUserService userService;
	@Autowired
	private RedisService redisService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(handler instanceof HandlerMethod) {
			PUser user = getUser(request, response);
			UserContext.setUser(user);
			HandlerMethod hm = (HandlerMethod)handler;
			AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
			if(accessLimit!=null) {
				int seconds = accessLimit.seconds();
				int maxCount = accessLimit.maxCount();
				boolean needLogin = accessLimit.needLogin();
				String key = request.getRequestURI();
				if(needLogin) {
					if(user == null) {
						render(response,CodeMsg.SESSION_ERROR);
						return false;
					}
					key += "_"+user.getId();
				}
				Integer count = redisService.get(AccessKey.withExpire(seconds),key, Integer.class);
				if(count==null)
					redisService.set(AccessKey.withExpire(seconds),key, 1);
				else if(count<5)
					redisService.incr(AccessKey.withExpire(seconds),key);
				else {
					render(response,CodeMsg.ACCESS_LIMIT);
					return false;
				}
			}
		}
		return super.preHandle(request, response, handler);
	}
	
	private void render(HttpServletResponse response, CodeMsg cm)throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		OutputStream out = response.getOutputStream();
		String str  = JSON.toJSONString(Result.error(cm));
		out.write(str.getBytes("UTF-8"));
		out.flush();
		out.close();
	}
	
	private PUser getUser(HttpServletRequest request, HttpServletResponse response) {
		String paramToken = request.getParameter(PUserService.COOKIE_NAME_TOKEN);
		String cookieToken = getCookieValue(request, PUserService.COOKIE_NAME_TOKEN);
		if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
			return null;
		}
		String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
		return userService.getByToken(response, token);
	}
	
	private String getCookieValue(HttpServletRequest request, String cookiName) {
		Cookie[]  cookies = request.getCookies();
		if(cookies == null || cookies.length <= 0){
			return null;
		}
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals(cookiName)) {
				return cookie.getValue();
			}
		}
		return null;
	}
}
