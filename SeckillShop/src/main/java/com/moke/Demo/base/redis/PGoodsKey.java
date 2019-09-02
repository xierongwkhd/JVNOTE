package com.moke.Demo.base.redis;

public class PGoodsKey extends BasePrefix {

	protected PGoodsKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

	public static PGoodsKey getGoodsList = new PGoodsKey(60,"gl");
	public static PGoodsKey getGoodsDetail = new PGoodsKey(60,"gd");
	public static PGoodsKey getMiaoshaGoodsStock = new PGoodsKey(0,"gs");

	public static PGoodsKey getMiaoshaPath = new PGoodsKey(60,"gk");
	public static PGoodsKey getMiaoshaVerifyCode = new PGoodsKey(300,"vc");;

}
