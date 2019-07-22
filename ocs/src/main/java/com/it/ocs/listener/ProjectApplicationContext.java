package com.it.ocs.listener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
@Component
public class ProjectApplicationContext implements ApplicationContextAware {
	private static ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ProjectApplicationContext.applicationContext = applicationContext;
	}
	public static ApplicationContext getApplicationContext() {
		return ProjectApplicationContext.applicationContext;
	}
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name,Class<T> cls) {
		if (ProjectApplicationContext.applicationContext.containsBean(name)) {
			return (T)ProjectApplicationContext.applicationContext.getBean(name);
		}
		return null;
	}
	
	public static Object getBean(String name) {
		return ProjectApplicationContext.applicationContext.getBean(name);
	}

	
	

}
