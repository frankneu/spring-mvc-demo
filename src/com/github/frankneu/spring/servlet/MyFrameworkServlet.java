package com.github.frankneu.spring.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.frankneu.spring.core.MyApplicationContext;
import com.github.frankneu.spring.core.MyApplicationContextImpl;

@SuppressWarnings("serial")
public abstract class MyFrameworkServlet extends HttpServlet {

	private static final String RequestMethod = null;

	protected abstract void doService(HttpServletRequest request, HttpServletResponse response) throws Exception;

	
	@Override
	public final void init() throws ServletException {
		MyApplicationContext mac = new MyApplicationContextImpl();
		initBean(mac);
	}

	private void initBean(MyApplicationContext context) {
		refreash(context);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) {
		try {
			doService(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void refreash(MyApplicationContext context) {}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String method = request.getMethod();
		if (method.equalsIgnoreCase(RequestMethod)) {
			processRequest(request, response);
		} else {
			super.service(request, response);
		}
	}

	@Override
	protected final void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected final void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
}
