package com.moke.Demo.Controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moke.Demo.Service.PUserService;
import com.moke.Demo.Vo.LoginVo;
import com.moke.Demo.base.CodeMsg;
import com.moke.Demo.base.Result;
import com.moke.Demo.base.util.ValidatorUtil;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	private static Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private PUserService pUserService;
	
	
	@RequestMapping("/to_login")
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping("/do_login")
	@ResponseBody
	public Result<String> doLogin(HttpServletResponse response,@Valid LoginVo loginVo){
		log.info(loginVo.toString());
		//参数校验，使用@Valid校验
		String token = pUserService.login(response,loginVo);
		return Result.success(token);

	}
}
