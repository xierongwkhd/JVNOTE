package com.moke.Demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moke.Demo.Domain.PUser;
import com.moke.Demo.Service.PGoodsService;
import com.moke.Demo.Service.PUserService;
import com.moke.Demo.Service.RedisService;
import com.moke.Demo.Vo.PGoodsVo;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	PUserService pUserService;
	@Autowired
	PGoodsService pGoodsService;
	@Autowired
	RedisService redisService;
	
    @RequestMapping("/to_list")
    public String list(Model model,PUser user) {
    	model.addAttribute("user", user);
    	List<PGoodsVo> goodsList = pGoodsService.getGoodsVo();
    	model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }
    
    @RequestMapping("/to_detail/{id}")
    public String detail(Model model,PUser user,@PathVariable("id")long goodsId) {
    	model.addAttribute("user", user);
    	PGoodsVo goods = pGoodsService.getGoodsVoByGoodsId(goodsId);
    	model.addAttribute("goods", goods);
    	long start = goods.getStartDate().getTime();
    	long end = goods.getEndDate().getTime();
    	long now = System.currentTimeMillis();
    	
    	int status = 0;//状态
    	int remain = 0;
    	if(now<start) {
    		status = 0;
    		remain = (int)(start-now)/1000;
    	}else if(now >end) {
    		status = 2;
    		remain = -1;
    	}else {
    		status = 1;
    		remain = 0;
    	}
    	model.addAttribute("miaoshaStatus", status);
    	model.addAttribute("remainSeconds", remain);
    	
        return "goods_detail";
    }
}
