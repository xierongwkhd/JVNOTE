package com.moke.Demo.base.redis;

public class POrderKey extends BasePrefix {

	protected POrderKey(String prefix) {
		super(prefix);
	}

	public static POrderKey getPOrderByUidGid = new POrderKey("moug");
}
