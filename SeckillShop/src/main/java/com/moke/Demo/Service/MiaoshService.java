package com.moke.Demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moke.Demo.Domain.POrder;
import com.moke.Demo.Domain.POrderInfo;
import com.moke.Demo.Domain.PUser;
import com.moke.Demo.Vo.PGoodsVo;
import com.moke.Demo.base.redis.PGoodsKey;

@Service
public class MiaoshService {
	
	@Autowired
	private PGoodsService pGoodsService;
	
	@Autowired
	private POrderService pOrderService;
	@Autowired
	private RedisService redisService;
	
	@Transactional
	public POrderInfo miaosha(PUser user, PGoodsVo goods) {
		//减少库存
		pGoodsService.reduceStock(goods);
		
		//写入订单
		return pOrderService.createOrder(user,goods);
	}

	public long getMiaoshaResult(Long userId, long goodsId) {
		POrder order = pOrderService.getMiaoShaOrderByUserIdGoodsId(userId, goodsId);
		if(order!=null)
			return order.getOrderId();
		else {
			if(redisService.get(PGoodsKey.getMiaoshaGoodsStock, ""+goodsId, Long.class)<0)
				return -1;
			else 
				return 0;
		}
	}

}
