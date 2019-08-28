package com.moke.Demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moke.Demo.Domain.POrderInfo;
import com.moke.Demo.Domain.PUser;
import com.moke.Demo.Vo.PGoodsVo;

@Service
public class MiaoshService {
	
	@Autowired
	private PGoodsService pGoodsService;
	
	@Autowired
	private POrderService pOrderService;
	
	@Transactional
	public POrderInfo miaosha(PUser user, PGoodsVo goods) {
		//减少库存
		pGoodsService.reduceStock(goods);
		
		//写入订单
		return pOrderService.createOrder(user,goods);
	}

}
