package com.it.ocs.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.task.model.ScheduleJob;
import com.it.ocs.task.service.IScheduleJobService;

@RequestMapping("schedule")
@Controller
public class ScheduleJobController {
	
	@Autowired
	private IScheduleJobService scheduleJobService;
	
	@RequestMapping(value="/show")
	public String show(){
		return "admin/task/taskManager";
	}
	
	@RequestMapping(value="/list")
	@ResponseBody 
	public ResponseResult<ScheduleJob> taskList(RequestParam param){  
		return scheduleJobService.taskList(param);
	}
	
	@RequestMapping("/start/{jonId}")
	@ResponseBody
	public OperationResult startJob(@PathVariable("jonId")String jobId){
		return scheduleJobService.startJob(jobId);
	}
	
	@RequestMapping("/stop/{jonId}")
	@ResponseBody
	public OperationResult stopJob(@PathVariable("jonId")String jobId){
		return scheduleJobService.stopJob(jobId);
	}
	
	@RequestMapping("/runImmediately/{jonId}")
	@ResponseBody
	public OperationResult runImmediately(@PathVariable("jonId")String jobId){
		return scheduleJobService.runImmediately(jobId);
	}
}
