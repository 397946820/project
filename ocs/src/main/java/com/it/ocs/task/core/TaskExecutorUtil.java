package com.it.ocs.task.core;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.it.ocs.task.support.spring.SpringUtils;

public class TaskExecutorUtil {
	public static ThreadPoolTaskExecutor getTaskExecutor() {
	    return SpringUtils.getBean("taskExecutor");
    }
	
	public static void threadRun(Runnable task){
		TaskExecutorUtil.getTaskExecutor().execute(task);
	}
}
