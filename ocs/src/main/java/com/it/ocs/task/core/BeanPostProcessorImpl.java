package com.it.ocs.task.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.it.ocs.task.service.impl.ScheduleJobAllService;
import com.it.ocs.task.service.impl.ScheduleJobService;

public class BeanPostProcessorImpl implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if("scheduleJobService".equals(beanName)){
			ScheduleJobService schedule = (ScheduleJobService)bean;
//			schedule.initSchedule();
		}else if("scheduleJobAllService".equals(beanName)){
			ScheduleJobAllService schedule = (ScheduleJobAllService)bean;
//			schedule.initSchedule();
		}
		
		return bean;
	}

}
