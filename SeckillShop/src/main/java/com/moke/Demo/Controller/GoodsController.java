package com.moke.Demo.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import com.moke.Demo.Domain.PUser;
import com.moke.Demo.Service.PGoodsService;
import com.moke.Demo.Service.PUserService;
import com.moke.Demo.Service.RedisService;
import com.moke.Demo.Vo.PGoodsVo;
import com.moke.Demo.base.redis.PGoodsKey;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	PUserService pUserService;
	@Autowired
	PGoodsService pGoodsService;
	@Autowired
	RedisService redisService;
	@Autowired
	private ThymeleafViewResolver thymeleafViewResolver;
	@Autowired
	private ApplicationContext applicationContext;
	
	
	/**
	 * QPS 1962
	 * 5000 * 10
	 * 加上页面缓存：QPS 3189
	 * 
	 * @param model
	 * @param user
	 * @return
	 */
    @RequestMapping(value="/to_list",produces="text/html")
    @ResponseBody
    public String list(HttpServletRequest request,HttpServletResponse response,
    				Model model,PUser user) {
    	//页面缓存
    	String html = redisService.get(PGoodsKey.getGoodsList, "", String.class);
        if(!StringUtils.isEmpty(html))
        	return html;
    	model.addAttribute("user", user);
    	List<PGoodsVo> goodsList = pGoodsService.getGoodsVo();
    	model.addAttribute("goodsList", goodsList);
        //手动渲染
        WebContext springWebContext = new WebContext(
        		request,response,request.getServletContext(),
        		request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", springWebContext);
    	if(!StringUtils.isEmpty(html))
    		redisService.set(PGoodsKey.getGoodsList, "", html);
        return html;
    }
    
    @RequestMapping(value="/to_detail/{id}",produces="text/html")
    @ResponseBody
    public String detail(HttpServletRequest request,HttpServletResponse response,
    					Model model,PUser user,@PathVariable("id")long goodsId) {
    	//页面缓存
    	String html = redisService.get(PGoodsKey.getGoodsDetail, ""+goodsId, String.class);
        if(!StringUtils.isEmpty(html))
        	return html;
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
        //手动渲染
        WebContext springWebContext = new WebContext(
        		request,response,request.getServletContext(),
        		request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", springWebContext);
    	if(!StringUtils.isEmpty(html))
    		redisService.set(PGoodsKey.getGoodsDetail, ""+goodsId, html);
        return html;
    }
}
