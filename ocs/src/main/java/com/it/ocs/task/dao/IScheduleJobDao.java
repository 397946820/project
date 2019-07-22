package com.it.ocs.task.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.task.model.ScheduleJob;

public interface IScheduleJobDao {

	public ScheduleJob getScheduleJobById(String jobId);

	public List<ScheduleJob> queryByPage(@Param("param")Map<String, Object> map,@Param("startRow")int startRow,@Param("endRow")int endRow);

	public int count(@Param("param")Map<String, Object> map);

	public void updateScheduleJobStatus(ScheduleJob job);

	public List<ScheduleJob> getScheduleJobByStatus(String status);

}
