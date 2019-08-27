package com.moke.Demo.base.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moke.Demo.base.CodeMsg;
import com.moke.Demo.base.Result;

@ControllerAdvice
@ResponseBody
public class GlobleExceptionHandler {
	
	@ExceptionHandler(value=Exception.class)
	public Result<String> exceptionHandler(HttpServletRequest request,Exception e){
		if(e instanceof GlobleException) {
			GlobleException ex = (GlobleException)e;
			return Result.error(ex.getCm());
		}else if(e instanceof BindException) {
			BindException ex = (BindException)e;
			String msg = ex.getMessage();
			return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
		}else {
			return Result.error(CodeMsg.SERVER_ERROR);
		}
	}
}
