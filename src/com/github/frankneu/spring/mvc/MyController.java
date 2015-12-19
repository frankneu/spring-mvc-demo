package com.github.frankneu.spring.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.frankneu.spring.servlet.MyModelAndView;

public interface MyController {
	MyModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
