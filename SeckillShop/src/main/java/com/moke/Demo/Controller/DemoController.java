package com.moke.Demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moke.Demo.Domain.User;
import com.moke.Demo.Service.RedisService;
import com.moke.Demo.Service.UserService;
import com.moke.Demo.base.CodeMsg;
import com.moke.Demo.base.Result;
import com.moke.Demo.base.rabbitmq.MQSender;
import com.moke.Demo.base.redis.UserKey;

@Controller
@RequestMapping("/demo")
public class DemoController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private MQSender mQSender;
	
//	@RequestMapping("/mq/header")
//	@ResponseBody
//	public Result<String> header() {
//		mQSender.sendHeader("hello,imooc");
//		return Result.success("Hello，world");
//	}
//	
//	@RequestMapping("/mq/fanout")
//	@ResponseBody
//	public Result<String> fanout() {
//		mQSender.sendFanout("hello,imooc");
//		return Result.success("Hello，world");
//	}
//	
//	@RequestMapping("/mq/topic")
//  	@ResponseBody
//  	public Result<String> topic() {
//		mQSender.sendTopic("hello,imooc");
//      	return Result.success("Hello，world");
//  	}
//	
//	@RequestMapping("/mq")
//	@ResponseBody
//	public String mq() {
//		mQSender.send("hello,imooc");
//		return "hello world";
//	}
	
	@RequestMapping("/")
	@ResponseBody
	public String home() {
		return "hello world";
	}
	
	@RequestMapping("/success")
	@ResponseBody
	public Result<String> success() {
		return Result.success("hello");
	}

	@RequestMapping("/error")
	@ResponseBody
	public Result<String> error() {
		return Result.error(CodeMsg.SERVER_ERROR);
	}
	
	@RequestMapping("/thymeleaf")
	public String thymeleaf(Model model) {
		model.addAttribute("name","moke");
		return "hello";
	}
	
	@RequestMapping("/dbGet")
	@ResponseBody
	public Result<User> dbGet() {
		User user = this.userService.getById(1);
		return Result.success(user);
	}
	
	@RequestMapping("/dbTx")
	@ResponseBody
	public Result<Boolean> dbTx() {
		userService.tx();
		return Result.success(true);
	}
	
	@RequestMapping("/redisGet")
	@ResponseBody
	public Result<User> redisGet() {
		User user = redisService.get(UserKey.getById,"1",User.class);
		return Result.success(user);
	}
	
	@RequestMapping("/redisSet")
	@ResponseBody
	public Result<Boolean> redisSet() {
		User user = new User();
		user.setId(1);
		user.setName("testRedis");
		boolean v1 = redisService.set(UserKey.getById,"1",user);
		return Result.success(v1);
	}
}
