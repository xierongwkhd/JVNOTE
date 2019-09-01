package com.moke.Demo.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moke.Demo.Dao.POrderDao;
import com.moke.Demo.Domain.POrder;
import com.moke.Demo.Domain.POrderInfo;
import com.moke.Demo.Domain.PUser;
import com.moke.Demo.Vo.PGoodsVo;

@Service
public class POrderService {
	
	@Autowired
	private POrderDao pOrderDao;
	@Autowired
	private RedisService redisService;

	public POrder getMiaoShaOrderByUserIdGoodsId(Long userId, long goodsId) {
		//return redisService.get(POrderKey.getPOrderByUidGid, userId+","+goodsId, POrder.class);
		return pOrderDao.getMiaoShaOrderByUserIdGoodsId(userId, goodsId);
	}

	@Transactional
	public POrderInfo createOrder(PUser user, PGoodsVo goods) {
		POrderInfo orderInfo = new POrderInfo();
		orderInfo.setCreateDate(new Date());
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getGoodsPrice());
		orderInfo.setOrderChannel((byte) 1);
		orderInfo.setStatus((byte) 0);//未支付
		orderInfo.setUserId(user.getId());
		long orderId = pOrderDao.insertOrderInfo(orderInfo);
		POrder order = new POrder();
		order.setGoodsId(goods.getId());
		order.setOrderId(orderId);
		order.setUserId(user.getId());
		pOrderDao.insertOrder(order);
		//redisService.set(POrderKey.getPOrderByUidGid, user.getId()+","+goods.getId(),POrder.class);
		return orderInfo;
	}

	public POrderInfo getOrderById(long orderId) {
		return pOrderDao.getOrderById(orderId);
	}
	
	

}
