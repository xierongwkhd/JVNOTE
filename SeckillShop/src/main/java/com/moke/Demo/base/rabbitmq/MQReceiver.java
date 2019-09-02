package com.moke.Demo.base.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moke.Demo.Domain.POrder;
import com.moke.Demo.Domain.PUser;
import com.moke.Demo.Service.MiaoshService;
import com.moke.Demo.Service.PGoodsService;
import com.moke.Demo.Service.POrderService;
import com.moke.Demo.Service.RedisService;
import com.moke.Demo.Vo.PGoodsVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class MQReceiver {

	private static Logger log = LoggerFactory.getLogger(MQReceiver.class);
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	PGoodsService goodsService;
	
	@Autowired
	POrderService orderService;
	
	@Autowired
	MiaoshService miaoshaService;
	
	@RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
	public void receive(String message) {
		log.info("receive message:"+message);
		MiaoshaMessage mm  = RedisService.stringToBean(message, MiaoshaMessage.class);
		PUser user = mm.getUser();
		long goodsId = mm.getGoodsId();
		
		PGoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	int stock = goods.getStockCount();
    	if(stock <= 0) {
    		return;
    	}
    	//判断是否已经秒杀到了
    	POrder order = orderService.getMiaoShaOrderByUserIdGoodsId(user.getId(), goodsId);
    	if(order != null) {
    		return;
    	}
    	//减库存 下订单 写入秒杀订单
    	miaoshaService.miaosha(user, goods);
	}

//	@RabbitListener(queues=MQConfig.QUEUE)
//	public void receive(String message) {
//		log.info("receive message:"+message);
//	}
//	
//	@RabbitListener(queues=MQConfig.TOPIC_QUEUE1)
//	public void receiveTopic1(String message) {
//		log.info(" topic  queue1 message:"+message);
//	}
//	
//	@RabbitListener(queues=MQConfig.TOPIC_QUEUE2)
//	public void receiveTopic2(String message) {
//		log.info(" topic  queue2 message:"+message);
//	}
//	
//	@RabbitListener(queues=MQConfig.HEADER_QUEUE)
//	public void receiveHeaderQueue(byte[] message) {
//		log.info(" header  queue message:"+new String(message));
//	}
}

