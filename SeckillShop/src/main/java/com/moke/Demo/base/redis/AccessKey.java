package com.moke.Demo.base.redis;

public class AccessKey extends BasePrefix{

	protected AccessKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
		// TODO Auto-generated constructor stub
	}

//	public static AccessKey getAccessTimes = new AccessKey(60, "at");
	public static AccessKey withExpire(int expireSeconds) {
		return new AccessKey(expireSeconds, "access");
	}
}
