package com.moke.Demo.base;

import org.apache.ibatis.javassist.expr.NewArray;

public class CodeMsg {
	private int code;
	private String msg;
	
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
	public static CodeMsg BIND_ERROR = new CodeMsg(500209, "参数校验异常: %s");
	public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500210, "密码不能为空");
	public static CodeMsg MOBAILE_EMPTY = new CodeMsg(500211, "手机号不能为空");
	public static final CodeMsg MOBILE_ERROR = new CodeMsg(500211, "手机号格式错误");
	public static final CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500212, "手机号不存在");
	public static final CodeMsg PASSWORD_ERROR = new CodeMsg(500213, "密码错误");
	public static final CodeMsg SESSION_ERROR = new CodeMsg(500214, "未登录");

	
	public static final CodeMsg MIAO_SHA_OVER = new CodeMsg(500310, "商品不存在");
	public static final CodeMsg REPEATE_MIAOSHA = new CodeMsg(500311, "不能重复秒杀");
	
	public static final CodeMsg ORDER_NOT_EXITS = new CodeMsg(500311, "订单不存在");

	
	private CodeMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public CodeMsg fillArgs(Object...args) {
		int code = this.code;
		String message = String.format(this.msg, args);
		return new CodeMsg(code, message);
	}
}
