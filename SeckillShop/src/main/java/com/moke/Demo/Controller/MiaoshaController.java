package com.moke.Demo.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moke.Demo.Domain.POrder;
import com.moke.Demo.Domain.PUser;
import com.moke.Demo.Service.MiaoshService;
import com.moke.Demo.Service.PGoodsService;
import com.moke.Demo.Service.POrderService;
import com.moke.Demo.Service.RedisService;
import com.moke.Demo.Vo.PGoodsVo;
import com.moke.Demo.base.CodeMsg;
import com.moke.Demo.base.Result;
import com.moke.Demo.base.rabbitmq.MQSender;
import com.moke.Demo.base.rabbitmq.MiaoshaMessage;
import com.moke.Demo.base.redis.PGoodsKey;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean{
	
	@Autowired
	private PGoodsService pGoodsService;
	@Autowired
	private POrderService pOrderService;
	@Autowired
	private MiaoshService miaoshService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private MQSender sender;
	
	private Map<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();
	
	/**
	 * QPS:1572
	 * 5000 * 10
	 * 
	  *  优化：redis、rabbitmq
	 * QPS:2470
	 * 5000 * 10
	 * @param model
	 * @param user
	 * @param goodsId
	 * @return
	 */
//	@RequestMapping(value = "/do_miaosha")
//	public String miaosha(Model model,PUser user,
//			@RequestParam long goodsId) {
//		model.addAttribute("user", user);
//		if(user == null)
//			return "login";
//		PGoodsVo goods = this.pGoodsService.getGoodsVoByGoodsId(goodsId);
//		if(goods.getStockCount() <=0) {
//			model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
//			return "miaosha_fail";
//		}
//		//判断是否秒杀到
//		POrder order = pOrderService.getMiaoShaOrderByUserIdGoodsId(user.getId(),goodsId);
//		if(order!=null) {
//			model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
//			return "miaosha_fail";
//		}
//		//减库存、下订单、写入秒杀订单，事务
//		POrderInfo orderInfo = miaoshService.miaosha(user,goods);
//		model.addAttribute("orderInfo", orderInfo);
//		model.addAttribute("goods", goods);
//		return "order_detail";
//	}

	@RequestMapping(value = "/do_miaosha",method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> miaosha(Model model,PUser user,
			@RequestParam long goodsId) {
		if(user == null)
			return Result.error(CodeMsg.SESSION_ERROR);
		
		/* 旧版
		PGoodsVo goods = this.pGoodsService.getGoodsVoByGoodsId(goodsId);
		if(goods.getStockCount() <=0) {
			return Result.error(CodeMsg.MIAO_SHA_OVER);
		}
		//判断是否秒杀到
		POrder order = pOrderService.getMiaoShaOrderByUserIdGoodsId(user.getId(),goodsId);
		if(order!=null) {
			return Result.error(CodeMsg.REPEATE_MIAOSHA);
		}
		//注：避免一个用户连续请求，都可以秒杀，表t_order需要给userId和orderId添加唯一索引
		//减库存、下订单、写入秒杀订单，事务
		POrderInfo orderInfo = miaoshService.miaosha(user,goods);
		*/
		
		//优化
		//内存标记，减少redis访问
		boolean over = localOverMap.get(goodsId);
		if(over)
			return Result.error(CodeMsg.MIAO_SHA_OVER);
		
		
		//秒杀商品库存数保存在redis
		long stock = redisService.decr(PGoodsKey.getMiaoshaGoodsStock, ""+goodsId);
		if(stock < 0) {
			localOverMap.put(goodsId,true);
			return Result.error(CodeMsg.MIAO_SHA_OVER);
		}
		//判断是否秒杀到
		POrder order = pOrderService.getMiaoShaOrderByUserIdGoodsId(user.getId(),goodsId);
		if(order!=null) {
			return Result.error(CodeMsg.REPEATE_MIAOSHA);
		}
		//入队rabbitmq
		MiaoshaMessage msgMessage = new MiaoshaMessage();
		msgMessage.setUser(user);
		msgMessage.setGoodsId(goodsId);
		sender.sendMiaoshaMessage(msgMessage);
		
		return Result.success(true);
	}

	/*
	 * 实现InitializingBean接口后，在系统初始化时会调用afterPropertiesSet方法
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		List<PGoodsVo> goodsList = this.pGoodsService.getGoodsVo(); 
		if(goodsList==null)
			return;
		for(PGoodsVo goods:goodsList) {
			redisService.set(PGoodsKey.getMiaoshaGoodsStock, ""+goods.getId(), goods.getStockCount());
			localOverMap.put(goods.getId(),false);
		}
	}
	/**
	 * 前端轮询，成功返回 orderId
	 * 失败 -1
	 * 排队 0
	 * @param model
	 * @param user
	 * @param goodsId
	 * @return
	 */
	@RequestMapping(value="/result",method = RequestMethod.GET)
	@ResponseBody
	public Result<Long> miaoshaResult(Model model,PUser user,@RequestParam("goodsId") long goodsId){
		if(user == null)
			return Result.error(CodeMsg.SESSION_ERROR);
		long result = miaoshService.getMiaoshaResult(user.getId(),goodsId);
		return Result.success(result);
	}
	
}
