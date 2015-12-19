package com.github.frankneu.spring.core;

import java.util.LinkedList;
import java.util.List;

import com.github.frankneu.spring.servlet.MyHandlerAdapterImpl;
import com.github.frankneu.spring.servlet.MyHandlerMapping;
import com.github.frankneu.spring.servlet.MyHandlerMappingImpl;

public class MyApplicationContextImpl implements MyApplicationContext {

	@Override
	public Object getBean(String string, Class class1) {
		List<Object> list = new LinkedList<Object>();
		Object obj = null;
		if(class1.getName().indexOf("MyHandlerMapping")>=0)
			obj = new MyHandlerMappingImpl();
		else if(class1.getName().indexOf("MyHandlerAdapter")>=0)
			obj = new MyHandlerAdapterImpl();
		list.add(obj);
		return list;
	}

}
