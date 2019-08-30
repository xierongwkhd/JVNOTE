package com.moke.Demo.base.redis;

public class PUserKey extends BasePrefix {
	
	public static final int TOKEN_EXPIRE = 3600*24;

	private PUserKey(int expireSeconds,String prefix) {
		super(expireSeconds,prefix);
	}

	public static PUserKey token = new PUserKey(TOKEN_EXPIRE,"token");
	public static PUserKey getById = new PUserKey(0,"id");

}
