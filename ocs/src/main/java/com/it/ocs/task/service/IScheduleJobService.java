package com.it.ocs.task.service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.task.model.ScheduleJob;

public interface IScheduleJobService {

	public OperationResult startJob(String jobId);

	public ResponseResult<ScheduleJob> taskList(RequestParam param);

	public OperationResult stopJob(String jobId);

	public OperationResult runImmediately(String jobId);

}
