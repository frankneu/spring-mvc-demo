package com.github.frankneu.spring.servlet;

import javax.servlet.http.HttpServletRequest;

public interface MyHandlerMapping {

	MyHandlerExecutionChain getHandler(HttpServletRequest request);

}
