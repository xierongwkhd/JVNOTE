package com.moke.Demo.base.redis;

public class PUserKey extends BasePrefix {
	
	public static final int TOKEN_EXPIRE = 3600*24*2;
	
	private PUserKey(int expireSeconds,String prefix) {
		super(expireSeconds,prefix);
	}

	public static PUserKey token = new PUserKey(TOKEN_EXPIRE,"token");
	
}
