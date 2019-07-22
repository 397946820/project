package com.it.ocs.task.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.it.ocs.publication.dao.IEBayTimingPlanDAO;
import com.it.ocs.task.core.QuartzJobFactory;
import com.it.ocs.task.core.QuartzJobFactoryDisallowConcurrentExecution;
import com.it.ocs.task.model.ScheduleJob;
import com.it.ocs.task.service.IScheduleJobAllService;

@Service
public class ScheduleJobAllService implements IScheduleJobAllService {
	
	@Autowired
	private IEBayTimingPlanDAO timingPlanDao;
	
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	private final static Logger log = Logger.getLogger(ScheduleJobService.class);
	public void initSchedule(){
		log.info("init task is start.");
		List<ScheduleJob> jobs = timingPlanDao.getScheduleJob();
		int count = 0;
    	for(ScheduleJob job : jobs){
    		addTask(job);
    		count++;
    	}
    	
		log.info("init task is end,has add "+count);
    }
	public void addTask(ScheduleJob job)  {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
        log.info(job.getJobName()+ "is add");
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());  
        CronTrigger trigger=null;
		try {
			trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        // 不存在，创建一个  
        if (null == trigger) {  
            Class clazz = ScheduleJob.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;  
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();  
            jobDetail.getJobDataMap().put("scheduleJob", job);  
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());  
            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();  
            try {
				scheduler.scheduleJob(jobDetail, trigger);
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        } else {  
            // Trigger已存在，那么更新相应的定时设置  
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());  
            // 按新的cronExpression表达式重新构建trigger  
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();  
            // 按新的trigger重新设置job执行  
            try {
				scheduler.rescheduleJob(triggerKey, trigger);
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        }
	}
}
