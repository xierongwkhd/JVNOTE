package com.moke.Demo.base.rabbitmq;

import com.moke.Demo.Domain.PUser;

public class MiaoshaMessage {
	private PUser user;
	private long goodsId;
	
	public PUser getUser() {
		return user;
	}
	public void setUser(PUser user) {
		this.user = user;
	}
	public long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}
}
