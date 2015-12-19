package com.github.frankneu.spring.servlet;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class MyHandlerAdapterImpl implements MyHandlerAdapter {

	@Override
	public MyModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler, Object obj)
			throws Exception {
		return (MyModelAndView)((Method) handler).invoke(obj, request, response);
	}

	@Override
	public boolean supports(Object handler) {
		return (handler instanceof MyHandlerExecutionChain);
	}

}
