package com.github.frankneu.spring.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class MyHandlerExecutionChain {

	private final Object handler;
	
	private final Object obj;
	
	public Object getObj() {
		return obj;
	}


	public MyHandlerExecutionChain(Object obj, Object handler){
		if (handler instanceof MyHandlerExecutionChain) {
			MyHandlerExecutionChain originalChain = (MyHandlerExecutionChain) handler;
			this.handler = originalChain.getHandler();
		}
		else {
			this.handler = handler;
		}
		this.obj = obj;
	}
	
	
	public Object getHandler() {
		return handler;
	}

	
}
