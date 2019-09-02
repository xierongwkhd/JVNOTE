package com.moke.Demo.base.access;

import com.moke.Demo.Domain.PUser;

public class UserContext {
	
	private static ThreadLocal<PUser> userHolder = new ThreadLocal<PUser>();
	
	public static void setUser(PUser user){
		userHolder.set(user);
	}

	public static PUser get() {
		return userHolder.get();
	}
}
