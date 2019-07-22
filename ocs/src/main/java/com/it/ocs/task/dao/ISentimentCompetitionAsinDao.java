package com.it.ocs.task.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.task.model.SentimentCompetitionAsinModel;
@Repository
public interface ISentimentCompetitionAsinDao extends IBaseDAO<SentimentCompetitionAsinModel> {
	
	public void insertSentimentCompetitionAsins(List<SentimentCompetitionAsinModel> sentimentCompetitionAsinModels);
	
	public void updateSentimentCompetitionAsins(List<SentimentCompetitionAsinModel> sentimentCompetitionAsinModels);
	
	public String selectMaxDate();
	
	public List<SentimentCompetitionAsinModel> selectMySqlDate(@Param("date") String date);
	
	public List<SentimentCompetitionAsinModel> selectSentimentCompetitionAsinModels(List<SentimentCompetitionAsinModel> sentimentCompetitionAsinModels);
	
	public void updateInsertData(List<SentimentCompetitionAsinModel> sentimentCompetitionAsinModels);
}
