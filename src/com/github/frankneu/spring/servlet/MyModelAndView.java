package com.github.frankneu.spring.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class MyModelAndView {

	private String view;

	private Map<String, Object> model;

	public Map<String, Object> getModel() {
		return model;
	}

	public void addAttribute(String key, Object obj) {
		this.model.put(key, obj);
	}

	public MyModelAndView(String viewName) {
		this.view = viewName;
		this.model = new HashMap<String ,Object>();
	}

	public String getView() {
		return this.view;
	}

	abstract public void render(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> model);


}
