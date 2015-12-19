package com.github.frankneu.spring.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MyHandlerAdapter {

	MyModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler, Object obj) throws Exception;

	boolean supports(Object handler);
}
