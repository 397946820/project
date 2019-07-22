package com.it.ocs.task.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.task.core.QuartzJobFactory;
import com.it.ocs.task.core.QuartzJobFactoryDisallowConcurrentExecution;
import com.it.ocs.task.dao.IScheduleJobDao;
import com.it.ocs.task.model.ScheduleJob;
import com.it.ocs.task.service.IScheduleJobService;
@Service
public class ScheduleJobService implements IScheduleJobService {
	private final static Logger log = Logger.getLogger(ScheduleJobService.class);
	
	@Autowired
	private IScheduleJobDao iScheduleJobDao;
	
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Override
	public OperationResult startJob(String jobId) {
		OperationResult or = new OperationResult();
		ScheduleJob job = iScheduleJobDao.getScheduleJobById(jobId);
		try {
			addJob(job);
			job.setJobStatus("1");
			iScheduleJobDao.updateScheduleJobStatus(job);
			or.setData("success");
		} catch (SchedulerException e) {
			log.info("启动任务失败",e);
			or.setData("fail");
		}
		return or;
	}

	@Override
	public OperationResult stopJob(String jobId) {
		OperationResult or = new OperationResult();
		ScheduleJob job = iScheduleJobDao.getScheduleJobById(jobId);
		try {
			deleteJob(job);
			job.setJobStatus("0");
			iScheduleJobDao.updateScheduleJobStatus(job);
			or.setData("success");
		} catch (SchedulerException e) {
			log.info("停止任务失败",e);
			or.setData("fail");
		}
		return or;
	}
	
	@Override
	public OperationResult runImmediately(String jobId) {
		OperationResult or = new OperationResult();
		ScheduleJob job = iScheduleJobDao.getScheduleJobById(jobId);
		try {
			List<ScheduleJob>  jobs = this.getRunningJob();
			for(ScheduleJob sjob : jobs){
				if(sjob.getJobName().equals(job.getJobName())&&sjob.getJobGroup().equals(job.getJobGroup())){
					or.setData("job is running");
					return or;
				}
			}
			if(ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())){
				runAJobNow(job);
			}else{
				//设置一个较长时间
				job.setCronExpression("0 11 11 11 11 ? 2099");
				addTask(job);
				runAJobNow(job);
			}
			or.setData("success");
		} catch (SchedulerException  e) {
			log.info("立即执行失败",e);
			or.setData("fail");
		}
		return or;
	}

	@Override
	public ResponseResult<ScheduleJob> taskList(RequestParam param) {
		ResponseResult<ScheduleJob> result = new ResponseResult<>();
		Map<String,Object> map = param.getParam();
		List<ScheduleJob> scheduleJobs = iScheduleJobDao.queryByPage(map, param.getStartRow(), param.getEndRow());
		int count = iScheduleJobDao.count(map);
		result.setRows(scheduleJobs);
		result.setTotal(count);
		return result;
	}  
	
	private void addJob(ScheduleJob job) throws SchedulerException {
		if (job == null || ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {  
            return;  
        }  
        addTask(job);  
	}

	private void addTask(ScheduleJob job) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();  
        log.info(job.getJobName()+ "is add");  
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());  
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);  
        // 不存在，创建一个  
        if (null == trigger) {  
            Class clazz = ScheduleJob.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;  
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();  
            jobDetail.getJobDataMap().put("scheduleJob", job);  
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());  
            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();  
            scheduler.scheduleJob(jobDetail, trigger);  
        } else {  
            // Trigger已存在，那么更新相应的定时设置  
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());  
            // 按新的cronExpression表达式重新构建trigger  
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();  
            // 按新的trigger重新设置job执行  
            scheduler.rescheduleJob(triggerKey, trigger);  
        }
	}
	
	/*
	 * 获取所有计划中的任务列表  
     *   
     * @return  
     * @throws SchedulerException  
     */  
    public List<ScheduleJob> getAllJob() throws SchedulerException {  
        Scheduler scheduler = schedulerFactoryBean.getScheduler();  
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();  
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);  
        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();  
        for (JobKey jobKey : jobKeys) {  
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);  
            for (Trigger trigger : triggers) {  
                ScheduleJob job = new ScheduleJob();  
                job.setJobName(jobKey.getName());  
                job.setJobGroup(jobKey.getGroup());  
                job.setDescription("触发器:" + trigger.getKey());  
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());  
                job.setJobStatus(triggerState.name());  
                if (trigger instanceof CronTrigger) {  
                    CronTrigger cronTrigger = (CronTrigger) trigger;  
                    String cronExpression = cronTrigger.getCronExpression();  
                    job.setCronExpression(cronExpression);  
                }  
                jobList.add(job);  
            }  
        }  
        return jobList;  
    }  
  
    /** 
     * 所有正在运行的job 
     *  
     * @return 
     * @throws SchedulerException 
     */  
    public List<ScheduleJob> getRunningJob() throws SchedulerException {  
        Scheduler scheduler = schedulerFactoryBean.getScheduler();  
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();  
        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());  
        for (JobExecutionContext executingJob : executingJobs) {  
            ScheduleJob job = new ScheduleJob();  
            JobDetail jobDetail = executingJob.getJobDetail();  
            JobKey jobKey = jobDetail.getKey();  
            Trigger trigger = executingJob.getTrigger();  
            job.setJobName(jobKey.getName());  
            job.setJobGroup(jobKey.getGroup());  
            job.setDescription("触发器:" + trigger.getKey());  
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());  
            job.setJobStatus(triggerState.name());  
            if (trigger instanceof CronTrigger) {  
                CronTrigger cronTrigger = (CronTrigger) trigger;  
                String cronExpression = cronTrigger.getCronExpression();  
                job.setCronExpression(cronExpression);  
            }  
            jobList.add(job);  
        }  
        return jobList;  
    }  
  
    /** 
     * 暂停一个job 
     *  
     * @param scheduleJob 
     * @throws SchedulerException 
     */  
    public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {  
        Scheduler scheduler = schedulerFactoryBean.getScheduler();  
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
        scheduler.pauseJob(jobKey);  
    }  
  
    /** 
     * 恢复一个job 
     *  
     * @param scheduleJob 
     * @throws SchedulerException 
     */  
    public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {  
        Scheduler scheduler = schedulerFactoryBean.getScheduler();  
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
        scheduler.resumeJob(jobKey);  
    }  
  
    /** 
     * 删除一个job 
     *  
     * @param scheduleJob 
     * @throws SchedulerException 
     */  
    public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {  
        Scheduler scheduler = schedulerFactoryBean.getScheduler();  
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
        scheduler.deleteJob(jobKey);  
  
    }  
  
    /** 
     * 立即执行job 
     *  
     * @param scheduleJob 
     * @throws SchedulerException 
     */  
    public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {  
        Scheduler scheduler = schedulerFactoryBean.getScheduler();  
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
        scheduler.triggerJob(jobKey);  
    }  
  
    /** 
     * 更新job时间表达式 
     *  
     * @param scheduleJob 
     * @throws SchedulerException 
     */  
    public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {  
        Scheduler scheduler = schedulerFactoryBean.getScheduler();  
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);  
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());  
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();  
        scheduler.rescheduleJob(triggerKey, trigger);  
    }
    
    public void initSchedule(){
    	log.info("init task is start.");
    	List<ScheduleJob> jobs = iScheduleJobDao.getScheduleJobByStatus(ScheduleJob.STATUS_RUNNING);
    	int count = 0;
    	for(ScheduleJob job : jobs){
    		try {
    			addTask(job);
			} catch (SchedulerException e) {
				log.info("add task failed,job name is "+ job.getJobName(),e);
			}
    		count++;
    	}
    	log.info("init task is end,has add "+count);
    }
    
}
