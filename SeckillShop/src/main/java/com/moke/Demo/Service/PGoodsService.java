package com.moke.Demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moke.Demo.Dao.PGoodsDao;
import com.moke.Demo.Domain.PGoods;
import com.moke.Demo.Domain.PGoodsSeckill;
import com.moke.Demo.Vo.PGoodsVo;

@Service 
public class PGoodsService {
	
	@Autowired
	private PGoodsDao pGoodsDao;
	
	public List<PGoodsVo> getGoodsVo(){
		return pGoodsDao.getGoodsVo();
	}

	public PGoodsVo getGoodsVoByGoodsId(long goodsId) {
		return pGoodsDao.getGoodsVoByGoodsId(goodsId);
	}

	public void reduceStock(PGoodsVo goods) {
		PGoodsSeckill g = new PGoodsSeckill();
		g.setGoodsId(goods.getId());
		pGoodsDao.reduceStock(g);
	}

}
