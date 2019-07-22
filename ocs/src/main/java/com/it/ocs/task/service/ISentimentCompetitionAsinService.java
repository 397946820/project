package com.it.ocs.task.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.task.model.SentimentCompetitionAsinModel;

public interface ISentimentCompetitionAsinService {

	public OperationResult synchronouSentimentCompetitionAsin(List<SentimentCompetitionAsinModel> sentimentCompetitionAsinModels);
	
	public OperationResult insertSentimentCompetitionAsins(List<SentimentCompetitionAsinModel> sentimentCompetitionAsinModels);
	
	public OperationResult updateSentimentCompetitionAsins(List<SentimentCompetitionAsinModel> sentimentCompetitionAsinModels);
	
	public String selectMaxDate();
	
	public List<SentimentCompetitionAsinModel> selectMySqlDate(String date);
	
	public List<SentimentCompetitionAsinModel> selectSentimentCompetitionAsinModels(List<SentimentCompetitionAsinModel> sentimentCompetitionAsinModels);
	
	public OperationResult updateInsertData(List<SentimentCompetitionAsinModel> sentimentCompetitionAsinModels);
}
