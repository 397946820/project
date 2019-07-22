package com.it.ocs.salesStatistics.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.salesStatistics.model.SalesStatisticsExcelModel;
import com.it.ocs.salesStatistics.model.SalesStatisticsModel;
import com.it.ocs.salesStatistics.vo.SalesStatisticsVo;

@Repository
public interface ISalesStatisticsDao {

	public List<SalesStatisticsModel> amazonQuery();

	public List<SalesStatisticsModel> amazonQueryByUpdateAt(@Param(value = "start") Date startTime,
			@Param(value = "end") Date endTime);

	public List<SalesStatisticsModel> ebayQuery();

	public List<SalesStatisticsModel> lightQuery();

	public Date getMaxUpdateAt();

	// 查找mysql时间段的数据
	public List<SalesStatisticsModel> findByTime(@Param(value = "station") String station,
			@Param(value = "start") Date startDate, @Param(value = "end") Date endDate);

	public List<SalesStatisticsModel> findModelsBySatationAndTime(@Param(value = "station") String station,
			@Param(value = "start") Date startTime, @Param(value = "end") Date endTime);

	public Map<String, String> findImgAndUrl(@Param(value = "asin") String asin,
			@Param(value = "platform") String platform);

	public List<SalesStatisticsModel> query(@Param(value = "param") SalesStatisticsVo salesStatistics,
			@Param(value = "platforms") List<String> platforms, @Param(value = "stations") List<String> stations,
			@Param(value = "sort") String sort, @Param(value = "order") String order);

	public List<SalesStatisticsModel> queryFooter(@Param(value = "param") SalesStatisticsVo salesStatistics,
			@Param(value = "platforms") List<String> platforms, @Param(value = "stations") List<String> stations);

	public List<SalesStatisticsModel> queryOrderDetails(@Param(value = "param") SalesStatisticsVo salesStatistics);

	public List<SalesStatisticsModel> queryDeatals(@Param(value = "param") SalesStatisticsVo salesStatistics,
			@Param(value = "platforms") List<String> platforms, @Param(value = "stations") List<String> stations,
			@Param(value = "sort") String sort, @Param(value = "order") String order);

	public List<SalesStatisticsModel> findOrder(@Param(value = "param") SalesStatisticsVo model);

	public Long getCountBySkuAndCountry(@Param(value = "param") SalesStatisticsVo statisticsvo);

	public List<SalesStatisticsModel> queryRate(@Param(value = "param") SalesStatisticsVo salesStatistics,
			@Param(value = "platforms") List<String> platforms, @Param(value = "stations") List<String> stations);

	public List<String> getEbayStation();
	
	public List<SalesStatisticsExcelModel> getData2(@Param(value = "fromDate") String fromDate,
			@Param(value = "toDate") String toDate, @Param(value = "platform") String platform,
			@Param(value = "station") String station, @Param(value = "whichTime") String whichTime);

}
