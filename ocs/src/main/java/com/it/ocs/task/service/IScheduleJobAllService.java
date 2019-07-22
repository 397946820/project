package com.it.ocs.task.service;

import java.util.List;

import org.quartz.SchedulerException;

import com.it.ocs.task.model.ScheduleJob;

public interface IScheduleJobAllService {
	
	public void addTask(ScheduleJob job);
}
