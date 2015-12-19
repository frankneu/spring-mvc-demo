package com.github.frankneu.spring.servlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.frankneu.spring.core.MyApplicationContext;
import com.github.frankneu.spring.utils.MyBeanFactoryUtils;

@SuppressWarnings("serial")
public class MyDispatcherServlet extends MyFrameworkServlet {

	private boolean detectAllHandlerMappings = false;

	private boolean detectAllHandlerAdapters = false;

	private List<MyHandlerMapping> handlerMappings;

	private List<MyHandlerAdapter> handlerAdapters;

	@Override
	protected void refreash(MyApplicationContext context) {
		System.out.println("initing");
		initStrategies(context);
	}

	protected void initStrategies(MyApplicationContext context) {
		initHandlerMappings(context);
		initHandlerAdapters(context);
	}

	private void initHandlerMappings(MyApplicationContext context) {
		this.handlerMappings = null;
		if (this.detectAllHandlerMappings) {
			Map matchingBeans = MyBeanFactoryUtils.beansOfTypeIncludingAncestors(context, MyHandlerMapping.class);
			if (!matchingBeans.isEmpty()) {
				this.handlerMappings = new ArrayList(matchingBeans.values());
				Collections.sort(this.handlerMappings, new Comparator<MyHandlerMapping>() {
					@Override
					public int compare(MyHandlerMapping o1, MyHandlerMapping o2) {
						return 0;
					}
				});
			}
		} else {
			try {
				MyHandlerMapping hm = (MyHandlerMapping) context.getBean("handlerMapping", MyHandlerMapping.class);
				this.handlerMappings = Collections.singletonList(hm);
			} catch (Exception ex) {
			}
		}
		if (this.handlerMappings == null) {
			this.handlerMappings = getDefaultStrategies(context, MyHandlerMapping.class);
		}
	}

	private List getDefaultStrategies(MyApplicationContext context, Class class1) {
		List<Object> list = new LinkedList<Object>();
		Object obj = null;
		if(class1.getName().indexOf("MyHandlerMapping")>=0)
			obj = new MyHandlerMappingImpl();
		else if(class1.getName().indexOf("MyHandlerAdapter")>=0)
			obj = new MyHandlerAdapterImpl();
		list.add(obj);
		return list;
	}

	private void initHandlerAdapters(MyApplicationContext context) {
		this.handlerAdapters = null;
		if (this.detectAllHandlerAdapters) {
			Map matchingBeans = MyBeanFactoryUtils.beansOfTypeIncludingAncestors(context, MyHandlerAdapter.class);
			if (!matchingBeans.isEmpty()) {
				this.handlerAdapters = new ArrayList(matchingBeans.values());
				Collections.sort(this.handlerAdapters, new Comparator<MyHandlerAdapter>() {
					@Override
					public int compare(MyHandlerAdapter o1, MyHandlerAdapter o2) {
						return 0;
					}
				});
			}
		} else {
			try {
				MyHandlerAdapter ha = (MyHandlerAdapter) context.getBean("handlerAdapter", MyHandlerAdapter.class);
				this.handlerAdapters = Collections.singletonList(ha);
			} catch (Exception ex) {
			}
		}

		if (this.handlerAdapters == null) {
			this.handlerAdapters = getDefaultStrategies(context, MyHandlerAdapter.class);
		}
	}

	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		doDispatch(request, response);
	}

	/**
	 * 实际的路由请求处理函数，处理所有的http请求，
	 */
	/**
	 * Process the actual dispatching to the handler.
	 * <p>The handler will be obtained by applying the servlet's HandlerMappings in order.
	 * The HandlerAdapter will be obtained by querying the servlet's installed HandlerAdapters
	 * to find the first that supports the handler class.
	 * <p>All HTTP methods are handled by this method. It's up to HandlerAdapters or handlers
	 * themselves to decide which methods are acceptable.
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @throws Exception in case of any kind of processing failure
	 */
	private void doDispatch(HttpServletRequest request, HttpServletResponse response) {

		try {
			MyHandlerExecutionChain mappedHandler = getHandler(request);
			MyHandlerAdapter ha = getHandlerAdapter(mappedHandler);
			MyModelAndView mv = ha.handle(request, response, mappedHandler.getHandler(), mappedHandler.getObj());
			processDispatchResult(request, response, mappedHandler, mv);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private MyHandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
		for (MyHandlerMapping hm : this.handlerMappings) {
			MyHandlerExecutionChain handler = hm.getHandler(request);
			if (null != handler) {
				return handler;
			}
		}
		throw new Exception("getHandler");
	}

	private MyHandlerAdapter getHandlerAdapter(Object handler) throws Exception {
		for (MyHandlerAdapter ha : this.handlerAdapters) {
			if (ha.supports(handler)) {
				return ha;
			}
		}
		throw new Exception("getHandlerAdapter");
	}

	private void processDispatchResult(HttpServletRequest request, HttpServletResponse response,
			MyHandlerExecutionChain mappedHandler, MyModelAndView mv) {
		render(mv, request, response);
	}

	private void render(MyModelAndView mv, HttpServletRequest request, HttpServletResponse response) {
		mv.render(request, response, mv.getModel());
	}
}
