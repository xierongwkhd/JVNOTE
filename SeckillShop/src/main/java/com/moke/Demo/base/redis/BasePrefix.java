package com.moke.Demo.base.redis;

public abstract class BasePrefix implements KeyPrefix{
	
	private int expireSeconds;
	private String prefix;	
			
	protected BasePrefix(int expireSeconds, String prefix) {
		this.expireSeconds = expireSeconds;
		this.prefix = prefix;
	}
	
	protected BasePrefix(String prefix) {
		this.expireSeconds = 0;
		this.prefix = prefix;
	}

	@Override
	public int expierSeconds() {
		return expireSeconds;
	}
	
	@Override
	public String getPrefix() {
		String className = getClass().getSimpleName();
		return className + ":" + prefix;
	}

}
