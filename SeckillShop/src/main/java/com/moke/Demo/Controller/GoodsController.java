package com.moke.Demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moke.Demo.Domain.PUser;
import com.moke.Demo.Service.PUserService;
import com.moke.Demo.Service.RedisService;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	PUserService pUserService;
	
	@Autowired
	RedisService redisService;
	
    @RequestMapping("/to_list")
    public String list(Model model,PUser user) {
    	model.addAttribute("user", user);
        return "goods_list";
    }
}
