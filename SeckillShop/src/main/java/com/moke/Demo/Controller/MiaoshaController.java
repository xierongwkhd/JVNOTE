package com.moke.Demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.moke.Demo.Domain.POrder;
import com.moke.Demo.Domain.POrderInfo;
import com.moke.Demo.Domain.PUser;
import com.moke.Demo.Service.MiaoshService;
import com.moke.Demo.Service.PGoodsService;
import com.moke.Demo.Service.POrderService;
import com.moke.Demo.Vo.PGoodsVo;
import com.moke.Demo.base.CodeMsg;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {
	
	@Autowired
	private PGoodsService pGoodsService;
	@Autowired
	private POrderService pOrderService;
	@Autowired
	private MiaoshService miaoshService;
	
	@RequestMapping("/do_miaosha")
	public String miaosha(Model model,PUser user,
			@RequestParam long goodsId) {
		model.addAttribute("user", user);
		if(user == null)
			return "login";
		PGoodsVo goods = this.pGoodsService.getGoodsVoByGoodsId(goodsId);
		if(goods.getStockCount() <=0) {
			model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
			return "miaosha_fail";
		}
		//判断是否秒杀到
		POrder order = pOrderService.getMiaoShaOrderByUserIdGoodsId(user.getId(),goodsId);
		if(order!=null) {
			model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
			return "miaosha_fail";
		}
		//减库存、下订单、写入秒杀订单，事务
		POrderInfo orderInfo = miaoshService.miaosha(user,goods);
		model.addAttribute("orderInfo", orderInfo);
		model.addAttribute("goods", goods);
		return "order_detail";
	}

}
