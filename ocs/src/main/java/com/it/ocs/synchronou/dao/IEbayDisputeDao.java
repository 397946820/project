package com.it.ocs.synchronou.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.synchronou.model.EbayDisputeModel;
import com.it.ocs.synchronou.model.EbayUserCaseModel;
import com.it.ocs.synchronou.model.SaleSubItemModel;

public interface IEbayDisputeDao {

	public int isExistForDispute(String disputeID);

	public void insertDispute(Map<String, Object> map);

	public int getDisputeId();

	public int isExistForDisputeInfo(String disputeID);

	public void insertDisputeInfo(Map<String, Object> map);

	public void updateDisputeInfo(Map<String, Object> map);

	public List<EbayDisputeModel> getDisputeUnClose(String account);

	public List<EbayUserCaseModel> queryByPage(@Param("param")Map<String, Object> map, @Param("startRow")int startRow, @Param("endRow")int endRow);

	public int count(@Param("param")Map<String, Object> map);

	public int remark(Map<String, Object> disputeModel);

	public int disputeMessageIsExist(String messageId);

	public void insetDisputeMessage(Map<String, Object> dm);

	public SaleSubItemModel getOrderTransInfo(String orderLineItemId);

	public List<EbayDisputeModel> getOldDisputeMessage(String id);

	public int countUserCase(Map<String, Object> map);

	public void addUserCase(Map<String, Object> map);

	public void updateUserCase(Map<String, Object> map);

	public int getUseCaseId();

	public Integer getUseCaseIdByAccountAndCaseId(Map<String, String> param);

	public int userCaseEBPExist(int id);

	public void addUserCaseEBP(Map<String, Object> map);

	public void addAppeal(Map<String, Object> appeal);

	public void addCaseDocumentInfo(Map<String, Object> caseDocumentInfo);

	public void addMoneyMovement(Map<String, Object> moneyMovement);

	public void addResponseHistory(Map<String, Object> responseHistory);

	public void updateUserCaseEBP(Map<String, Object> map);

	public int countAppeal(Map<String, Object> appeal);

	public void updateAppeal(Map<String, Object> appeal);

	public int countMoneyMovement(Map<String, Object> moneyMovement);

	public void updateMoneyMovement(Map<String, Object> moneyMovement);

	public int countCaseDocumentInfo(Map<String, Object> caseDocumentInfo);

	public int countResponseHistory(Map<String, Object> responseHistory);

	public EbayUserCaseModel getUseCaseById(String id);

	public List<Map<String, Object>> getAppealListByParentId(String parentId);

	public List<Map<String, Object>> getMoneyMovementListByParentId(String parentId);

	public List<Map<String, Object>> getResponseHistoryListByParentId(String parentId);

}
