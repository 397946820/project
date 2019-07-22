package com.it.ocs.sys.core;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.it.ocs.sys.model.SystemLogModel;

public class SystemLogInterceptor implements HandlerInterceptor{
	private final static Logger log = Logger.getLogger(SystemLogInterceptor.class);

	@Autowired
	private SystemLogService systemLogService;
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 记录日志
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		String targetName = handlerMethod.getBeanType().getName();
		Method method = handlerMethod.getMethod();
		if (method.isAnnotationPresent(SystemLog.class)) {
			SystemLog systemLog = method.getAnnotation(SystemLog.class);
			SystemLogModel logModel = new SystemLogModel();
			logModel.setTargetName(targetName);
			logModel.setMethodName(method.getName());
			logModel.setDesc(systemLog.value());
			Enumeration em = request.getParameterNames();
			Map<String,String> map = new HashMap<>();
			while (em.hasMoreElements()) {
			    String name = (String) em.nextElement();
			    String value = request.getParameter(name);
			    map.put(name, value);
			}
			logModel.setParam(map);
			systemLogService.addLog(logModel);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		
	}
}
