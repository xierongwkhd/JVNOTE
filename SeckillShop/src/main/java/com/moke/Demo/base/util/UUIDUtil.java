package com.moke.Demo.base.util;

import java.util.UUID;

public class UUIDUtil {

	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
