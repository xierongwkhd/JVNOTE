package com.moke.Demo.Vo;

import com.moke.Demo.Domain.PUser;

public class PGoodsDetailVo {
	private PGoodsVo goods;
	private int miaoshastatus = 0;//状态
	private int remainSeconds = 0;
	private PUser user;
	
	public PGoodsVo getGoods() {
		return goods;
	}
	public void setGoods(PGoodsVo goods) {
		this.goods = goods;
	}
	public int getMiaoshastatus() {
		return miaoshastatus;
	}
	public void setMiaoshastatus(int miaoshastatus) {
		this.miaoshastatus = miaoshastatus;
	}
	public int getRemainSeconds() {
		return remainSeconds;
	}
	public void setRemainSeconds(int remainSeconds) {
		this.remainSeconds = remainSeconds;
	}
	public PUser getUser() {
		return user;
	}
	public void setUser(PUser user) {
		this.user = user;
	}
}
