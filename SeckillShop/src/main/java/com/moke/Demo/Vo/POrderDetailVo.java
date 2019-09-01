package com.moke.Demo.Vo;

import com.moke.Demo.Domain.POrderInfo;

public class POrderDetailVo {
	private PGoodsVo goods;
	private POrderInfo order;
	
	public PGoodsVo getGoods() {
		return goods;
	}
	public void setGoods(PGoodsVo goods) {
		this.goods = goods;
	}
	public POrderInfo getOrder() {
		return order;
	}
	public void setOrder(POrderInfo order) {
		this.order = order;
	}
	
	

}
