package com.it.ocs.task.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import com.it.ocs.task.model.ScheduleJob;
import com.it.ocs.task.support.spring.SpringUtils;

public class TaskUtils {
	public final static Logger log = Logger.getLogger(TaskUtils.class);

	/**
	 * 通过反射调用scheduleJob中定义的方法
	 * @param scheduleJob
	 */
	public static void invokMethod(ScheduleJob scheduleJob) {
		Object object = null;
		Class clazz = null;
		if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
			object = SpringUtils.getBean(scheduleJob.getSpringId());
		}
		if (object == null) {
			log.error("任务名称 = [" + scheduleJob.getJobName() + "] - 未启动成功，请检查是否配置正确！！！");
			return;
		}
		try {
			object = getTarget(object);
		} catch (Exception e1) {
			log.error("任务名称 = [" + scheduleJob.getJobName() + "] - 未启动成功，请检查bean名称是否配置正确！！！",e1);
			return;
		}
		clazz = object.getClass();
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
			method.invoke(object);
		} catch (Exception e) {
			log.error("任务名称 = [" + scheduleJob.getJobName() + "] - 未启动成功，方法名设置错误！！！",e);
			return;
		}
		log.info("任务名称 = [" + scheduleJob.getJobName() + "] - 启动成功");
	}
	
	/**
	 * spring 代理对象目标对象
	 * @param proxy
	 * @return
	 * @throws Exception
	 */
	public static Object getTarget(Object proxy) throws Exception {
		if (!AopUtils.isAopProxy(proxy)) {
			return proxy;// 不是代理对象
		}
		if (AopUtils.isJdkDynamicProxy(proxy)) {
			return getJdkDynamicProxyTargetObject(proxy);
		} else { // cglib
			return getCglibProxyTargetObject(proxy);
		}

	}
	
	/**
	 * cglib 获取代理目标对象
	 * @param proxy
	 * @return
	 * @throws Exception
	 */
	private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
		Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
		h.setAccessible(true);
		Object dynamicAdvisedInterceptor = h.get(proxy);
		Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
		advised.setAccessible(true);
		Object target = ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
		return target;
	}
	
	/**
	 * proxy 获取代理目标对象
	 * @param proxy
	 * @return
	 * @throws Exception
	 */
	private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
		Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
		h.setAccessible(true);
		AopProxy aopProxy = (AopProxy) h.get(proxy);
		Field advised = aopProxy.getClass().getDeclaredField("advised");
		advised.setAccessible(true);
		Object target = ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
		return target;
	}
}
