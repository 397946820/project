package com.it.ocs.publication.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.publication.model.EBayTimingPlanModel;
import com.it.ocs.task.model.ScheduleJob;
@Repository
public interface IEBayTimingPlanDAO extends IBaseDAO<EBayTimingPlanModel> {
	
	public void updateTimingPlan(EBayTimingPlanModel eBayTimingPlanModel);
	
	public List<EBayTimingPlanModel> selectTimingPlans(@Param("planModel")Map<String, Object> params,@Param("starRow") int startRow,  @Param("endRow")int endRow);

	public Integer countList(@Param("planModel")Map<String, Object> map);
	
	public int countPub(@Param("planModel")Map<String, Object> map);
	public List<EBayTimingPlanModel> selectLineTimingPlans(@Param("planModel")Map<String, Object> params,@Param("starRow") int startRow,  @Param("endRow")int endRow);
	public int countLinePub(@Param("planModel")Map<String, Object> map);
	public Integer countLineList(@Param("planModel")Map<String, Object> map);
	
	public List<EBayTimingPlanModel> selectTimingPlanByFD(@Param("first_date")Date firstDate);

	public List<ScheduleJob> getScheduleJob();
	
	public List<ScheduleJob> getScheduleJobByPId(EBayTimingPlanModel eBayTimingPlanModel);
}
