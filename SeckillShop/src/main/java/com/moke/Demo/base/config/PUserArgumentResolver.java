package com.moke.Demo.base.config;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.moke.Demo.Domain.PUser;
import com.moke.Demo.base.access.UserContext;

@Component
public class PUserArgumentResolver implements HandlerMethodArgumentResolver{

	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> clazz = parameter.getParameterType();
		return clazz==PUser.class;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
//		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
//		String paramToken = request.getParameter(PUserService.COOKIE_NAME_TOKEN);
//		String cookieToken = getCookieValue(request,PUserService.COOKIE_NAME_TOKEN);
//		if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
//			return null;
//		}
//		String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
//		return pUserService.getByToken(response, token);
		return UserContext.get();
	}

//	private String getCookieValue(HttpServletRequest request, String cookieNameToken) {
//		Cookie[] cookies = request.getCookies();
//		if(cookies==null || cookies.length<=0)
//			return null;
//		for(Cookie cookie:cookies) {
//			if(cookie.getName().equals(cookieNameToken))
//				return cookie.getValue();
//		}
//		return null;
//	}

}
