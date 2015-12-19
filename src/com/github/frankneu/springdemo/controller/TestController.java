package com.github.frankneu.springdemo.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.frankneu.spring.mvc.MyController;
import com.github.frankneu.spring.servlet.MyModelAndViewImpl;
import com.github.frankneu.spring.servlet.MyModelAndView;

public class TestController implements MyController {

	@Override
	public MyModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MyModelAndView mv = new MyModelAndViewImpl("hello");
		mv.addAttribute("time", new Date());
		mv.addAttribute("title", "Copy by FrankNEU");;
		return mv;
	}

}
