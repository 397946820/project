package com.it.ocs.amazon.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.amazon.model.ColumnData;
import com.it.ocs.amazon.model.MyiUnsuppressedVO;
import com.it.ocs.amazon.model.OrderReportVO;
import com.it.ocs.amazon.model.ReportRequestListModel;
import com.it.ocs.publication.vo.ComboBoxVO;

public interface IAmazonReportDao {

	public void saveData(Map<String, Object> request);
	public ReportRequestListModel getUnaskRequestByTime(Map<String,String> map);
	public ReportRequestListModel getLastDoneReportByParam(Map<String,String> map);

	public List<ReportRequestListModel> getDownloadReportByAccount(String platform);

	public void updateReportRequest(ReportRequestListModel request);

	public int dataRangeReportMonthU(@Param(value="reportIds") List<String> reportIds,
			@Param(value="site") String site,@Param(value="month") String month);
	
	public List<ReportRequestListModel> getParseReport(Map<String, Object> map);
	
	public List<ReportRequestListModel> getParsedReport(Map<String, String> map);

	public List<ColumnData> getColumnData(Map<String,Object> map);

	public String getPlatformBySite(String site);
	
	public int isExist(String orderId);

	public void addOrderReport(Map<String, Object> data);

	public void deleteReportDataById(String report_id);

	public void updateParseStatus(ReportRequestListModel reportMode);

	public int countOrderReport(@Param("param")Map<String, Object> map);

	public List<OrderReportVO> queryOrderReportByPage(@Param("param")Map<String, Object> map, @Param("start")int startRow, @Param("end")int endRow);

	public void updateRequestSite(ReportRequestListModel reportMode);

	public ReportRequestListModel getParseStart(String site);

	public ReportRequestListModel getNextParseReport(ReportRequestListModel reportMode);

	public void addOrderReportException(ReportRequestListModel reportMode);

	public int isBreakOff(Map map);

	public String getEndTime(ReportRequestListModel reportMode);

	public void chooseByRequestList(ReportRequestListModel reportMode);

	public List<ReportRequestListModel> getExceptionTimeData(String site);

	public List<ReportRequestListModel> getAllBetweenData(ReportRequestListModel reportMode);

	public void updateReportExceptionStatus(ReportRequestListModel reportMode);

	public List<Map<String, Object>> getExportDataByParam(@Param("param")Map<String, Object> map);

	public List<Map<String, Object>> getAmazonChangeData();

	public void countAmazonDataByCreateTime(Map<String, Object> map);
	
	public void countAmazonDataByUpdateTime(Map<String, Object> map);
	
	public void countAmazonDataByShipTime(Map<String, Object> map);

	public List<ComboBoxVO> getOrderTypeBySite(String site);

	public List<Map<String, Object>> getTotalByContry(@Param("param")Map<String, Object> map);

	public List<Map<String, Object>> getTotalByType(@Param("param")Map<String, Object> map);

	public int countThisReportData(String reportGetId);

	public void saveRequest(@Param("request") Map<String, Object> request);
	
	public void deleteInventoryReportDataById(String reportGetId);

	public void inventorySave(Map<String, Object> data);

	public int countThisInventoryReportData(String reportGetId);

	public int countMyiUnsuppressed(@Param("param") Map<String, Object> map);

	public void deleteInventoryReservedReportDataById(String reportGetId);

	public int countThisInventoryReservedReportData(String reportGetId);

	public void inventoryReservedSave(Map<String, Object> saveData);

	public List<MyiUnsuppressedVO> findMyiUnsuppressed(@Param("param")Map<String, Object> map, @Param("start")int startRow, @Param("end")int endRow);
	
	public List<Map<String, Object>> getMyiUnsuppressedExportData(@Param("param")Map<String, Object> map);

	public void refreshReportUKSiste();
	
	public void refreshReservedUKSiste();
}
