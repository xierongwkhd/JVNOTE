package com.moke.Demo.Service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

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

	public BufferedImage createVerifyCode(PUser user, long goodsId) {
		if(user == null || goodsId <=0) {
			return null;
		}
		int width = 80;
		int height = 32;
		//create the image
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		// set the background color
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, width, height);
		// draw the border
		g.setColor(Color.black);
		g.drawRect(0, 0, width - 1, height - 1);
		// create a random instance to generate the codes
		Random rdm = new Random();
		// make some confusion
		for (int i = 0; i < 50; i++) {
			int x = rdm.nextInt(width);
			int y = rdm.nextInt(height);
			g.drawOval(x, y, 0, 0);
		}
		// generate a random code
		String verifyCode = generateVerifyCode(rdm);
		g.setColor(new Color(0, 100, 0));
		g.setFont(new Font("Candara", Font.BOLD, 24));
		g.drawString(verifyCode, 8, 24);
		g.dispose();
		//把验证码存到redis中
		int rnd = calc(verifyCode);
		redisService.set(PGoodsKey.getMiaoshaVerifyCode, user.getId()+","+goodsId, rnd);
		//输出图片	
		return image;
	}

	public boolean checkVerifyCode(PUser user, long goodsId, int verifyCode) {
		if(user == null || goodsId <=0) {
			return false;
		}
		Integer codeOld = redisService.get(PGoodsKey.getMiaoshaVerifyCode, user.getId()+","+goodsId, Integer.class);
		if(codeOld == null || codeOld - verifyCode != 0 ) {
			return false;
		}
		redisService.delete(PGoodsKey.getMiaoshaVerifyCode, user.getId()+","+goodsId);
		return true;
	}
	
	private static char[] ops = new char[] {'+', '-', '*'};
	private String generateVerifyCode(Random rdm) {
		int num1 = rdm.nextInt(10);
	    int num2 = rdm.nextInt(10);
		int num3 = rdm.nextInt(10);
		char op1 = ops[rdm.nextInt(3)];
		char op2 = ops[rdm.nextInt(3)];
		String exp = ""+ num1 + op1 + num2 + op2 + num3;
		return exp;
	}
	
	private static int calc(String exp) {
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("JavaScript");
			return (Integer)engine.eval(exp);
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
