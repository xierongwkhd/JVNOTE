package com.moke.Demo.Controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.resource.HttpResource;

import com.moke.Demo.Domain.POrder;
import com.moke.Demo.Domain.PUser;
import com.moke.Demo.Service.MiaoshService;
import com.moke.Demo.Service.PGoodsService;
import com.moke.Demo.Service.POrderService;
import com.moke.Demo.Service.RedisService;
import com.moke.Demo.Vo.PGoodsVo;
import com.moke.Demo.base.CodeMsg;
import com.moke.Demo.base.Result;
import com.moke.Demo.base.access.AccessLimit;
import com.moke.Demo.base.rabbitmq.MQSender;
import com.moke.Demo.base.rabbitmq.MiaoshaMessage;
import com.moke.Demo.base.redis.AccessKey;
import com.moke.Demo.base.redis.PGoodsKey;
import com.moke.Demo.base.util.MD5Util;
import com.moke.Demo.base.util.UUIDUtil;

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

	@RequestMapping(value = "/{path}/do_miaosha",method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> miaosha(Model model,PUser user,
			@RequestParam long goodsId,@PathVariable("path") String path) {
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
		if(path==null || !redisService.get(PGoodsKey.getMiaoshaPath, user.getId()+","+goodsId,String.class).equals(path))
			return Result.error(CodeMsg.REQUEST_ILLEGAL);
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
	/**
	 * 获取秒杀地址
	 * @param model
	 * @param user
	 * @param goodsId
	 * @return
	 */
	@AccessLimit(seconds=5,maxCount=5,needLogin=true)
	@RequestMapping(value="/path",method = RequestMethod.GET)
	@ResponseBody
	public Result<String> getMiaoshaPath(PUser user,@RequestParam("goodsId") long goodsId,
			@RequestParam(value="verifyCode",defaultValue="0") int verifyCode,HttpServletRequest request){
		if(user == null)
			return Result.error(CodeMsg.SESSION_ERROR);
		//限流防刷
//		String uri = request.getRequestURI();
//		String key = uri+"_"+user.getId();
//		Integer count = redisService.get(AccessKey.getAccessTimes,key, Integer.class);
//		if(count==null)
//			redisService.set(AccessKey.getAccessTimes,key, 1);
//		else if(count<5)
//			redisService.incr(AccessKey.getAccessTimes,key);
//		else
//			return Result.error(CodeMsg.ACCESS_LIMIT);
		//检查验证码
		if(!miaoshService.checkVerifyCode(user, goodsId, verifyCode))
			return Result.error(CodeMsg.REQUEST_ILLEGAL);
		String str = MD5Util.md5(UUIDUtil.uuid());
		redisService.set(PGoodsKey.getMiaoshaPath, user.getId()+","+goodsId, str);
		return Result.success(str);
	}
	
	/**
	 * 获取验证码
	 * @param model
	 * @param user
	 * @param goodsId
	 * @return
	 */
	@RequestMapping(value="/verifyCode",method = RequestMethod.GET)
	@ResponseBody
	public Result<String> getVerifyCode(HttpServletResponse response,Model model,PUser user,@RequestParam("goodsId") long goodsId){
		if(user == null)
			return Result.error(CodeMsg.SESSION_ERROR);
		BufferedImage image = miaoshService.createVerifyCode(user,goodsId);
		try {
			OutputStream out = response.getOutputStream();
			ImageIO.write(image, "JPEG", out);
			out.flush();
			out.close();
			return null;
		} catch (IOException e) {
			return Result.error(CodeMsg.MIAOSHA_FAIL);

		}
	}
	
}
