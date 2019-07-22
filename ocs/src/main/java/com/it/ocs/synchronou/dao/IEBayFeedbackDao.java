package com.it.ocs.synchronou.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.synchronou.model.EBayFeedbackModel;

@Repository
public interface IEBayFeedbackDao extends IBaseDAO<EBayFeedbackModel> {
	public void insertFeedbacks(List<EBayFeedbackModel> eBayFeedbackModels);
	public Date selectMaxLeftCommentTime(String sellerId);
	public Date selectMaxReceivedCommentTime(String sellerId);
	public EBayFeedbackModel selectEBayFeedbackModel(EBayFeedbackModel eBayFeedbackModel);
	public void updateEBayFeedbackModels(List<EBayFeedbackModel> eBayFeedbackModels);
	public List<EBayFeedbackModel> selectFeedbackModelsByPage(@Param("feedback")Map<String, Object> params,@Param("startRow") int startRow, @Param("endRow") int endRow);
	public Integer getTotal(@Param("feedback")Map<String, Object> params);
	public void updateFeedbackById(EBayFeedbackModel eBayFeedbackModel);
}
