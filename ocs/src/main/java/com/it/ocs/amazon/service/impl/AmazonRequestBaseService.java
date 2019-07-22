package com.it.ocs.amazon.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.it.ocs.amazon.dao.IAmazonReportDao;
import com.it.ocs.amazon.model.AmazonRequestConfig;
import com.it.ocs.amazon.model.ReportRequestListModel;
import com.it.ocs.amazon.utils.AmazonHttpUtil;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.salesStatistics.utils.TimeTools;
import com.it.ocs.synchronou.dao.ISyncAmazonOrderDao;
import com.it.ocs.synchronou.model.AmazonAccountModel;
import com.it.ocs.synchronou.model.ParseXMLModel2;
import com.it.ocs.synchronou.model.XMLNode;

public abstract class AmazonRequestBaseService {
	private final static Logger log = Logger.getLogger(AmazonRequestBaseService.class);
	@Autowired
	private ISyncAmazonOrderDao syncAmazonOrderDao;
	@Autowired
	private IAmazonReportDao iAmazonReportDao;
	protected static final Map<String, String> SITE_RELATE_MAP = new HashMap<String, String>();
	protected static Map<String, String> TIME_CONVERT_MAP = Maps.newConcurrentMap();
	protected static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static {
		// 根据报表数据反馈定的时间转义ID
		TIME_CONVERT_MAP.put("amazon.com", "America/Los_Angeles");
		TIME_CONVERT_MAP.put("amazon.ca", "America/Los_Angeles");
		TIME_CONVERT_MAP.put("amazon.jp", "Asia/Tokyo");
		TIME_CONVERT_MAP.put("amazon.de", "Europe/Berlin");
		TIME_CONVERT_MAP.put("amazon.fr", "Europe/Paris");
		TIME_CONVERT_MAP.put("amazon.co.uk", "Europe/London");
		TIME_CONVERT_MAP.put("amazon.es", "Europe/Madrid");
		TIME_CONVERT_MAP.put("amazon.it", "Europe/Rome");
		TIME_CONVERT_MAP.put("amazon.com.au", "Australia/ACT");
		SITE_RELATE_MAP.put("ATVPDKIKX0DER", "amazon.com");
		SITE_RELATE_MAP.put("A1VC38T7YXB528", "amazon.jp");
		SITE_RELATE_MAP.put("A2EUQ1WTGCTBG2", "amazon.ca");
		SITE_RELATE_MAP.put("A1RKKUPIHCS9HS", "amazon.es");
		SITE_RELATE_MAP.put("A1F83G8C2ARO7P", "amazon.co.uk");
		SITE_RELATE_MAP.put("A1PA6795UKMFR9", "amazon.de");
		SITE_RELATE_MAP.put("A1RKKUPIHCS9HS,A1F83G8C2ARO7P,A1PA6795UKMFR9,A13V1IB3VIYZZH,APJ6JRA9NG5V4", "amazon.de");
		SITE_RELATE_MAP.put("A13V1IB3VIYZZH", "amazon.fr");
		SITE_RELATE_MAP.put("APJ6JRA9NG5V4", "amazon.it");
		SITE_RELATE_MAP.put("A39IBJ37TRP1C6", "amazon.com.au");
	}

	protected abstract String getReportType();

	public boolean isRequest(AmazonAccountModel account) {
		boolean result = false;
		Map<String, String> queryParam = Maps.newConcurrentMap();
		queryParam.put("ReportType", getReportType());
		queryParam.put("PlateForm", account.getPlatform());
		ReportRequestListModel unaskRequestModel = iAmazonReportDao.getUnaskRequestByTime(queryParam);
		if (unaskRequestModel != null) {
			return result;
		}
		ReportRequestListModel requestModel = iAmazonReportDao.getLastDoneReportByParam(queryParam);
		if (null == requestModel) {
			result = true;
		} else {
			try {
				Date siteEndDate = convertDateBySite(requestModel.getEndDate(), "UTC",
						TIME_CONVERT_MAP.get(SITE_RELATE_MAP.get(account.getSiteId())));
				Date siteCurDate = convertDateBySite(sdf.format(new Date()), "Asia/Shanghai",
						TIME_CONVERT_MAP.get(SITE_RELATE_MAP.get(account.getSiteId())));
				return siteEndDate.compareTo(TimeTools.getChangeDay(siteCurDate, -1)) < 0;
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		return result;

	}

	protected Date convertDateBySite(String date, String srcSite, String tarSite) throws ParseException {
		String ssite = srcSite.equals("UTC") ? "UTC" : srcSite;
		String tsite = tarSite.equals("UTC") ? "UTC" : tarSite;
		String convertTime = TimeTools.timeConvert(date, ssite, tsite);
		if (!Strings.isNullOrEmpty(convertTime)) {
			return sdf.parse(convertTime);
		}
		return null;
	}

	/**
	 * Spring Schedule：生成亚马逊FBA库存报表请求清单</br>
	 * <p>
	 * 调用亚马逊商城网络服务（亚马逊MWS）下的亚马逊物流 (FBA) 报告相关API：</br>
	 * <tt>
	 * 1. 亚马逊物流管理库存（<code>ReportType</code> =
	 * <code>_GET_FBA_MYI_UNSUPPRESSED_INVENTORY_DATA_</code>）；</br>
	 * 2. 亚马逊物流预留库存（<code>ReportType</code> =
	 * <code>_GET_RESERVED_INVENTORY_DATA_</code>）</br>
	 * </tt>
	 * 并且将请求列表数据预插入到当前系统亚马逊报表请求数据表（<code>table: amazon_reports_request</code>）中
	 * </p>
	 */
	public void doRequests() {
		CollectionUtil.each(this.syncAmazonOrderDao.getAmazonAccounts(), new IAction<AmazonAccountModel>() {
			@SuppressWarnings("unchecked")
			public void excute(AmazonAccountModel account) {
				if (isRequest(account)) {
					String response = postAmazonReport(account, getReportType());
					Map<String, Object> data = parseAmazonFBAInventoryResponseXml(response);
					if (null != data) {
						saveRequests((List<Map<String, Object>>) data.get("data"), account,
								SITE_RELATE_MAP.get(account.getSiteId()), true);
					}
				}
			}
		});
	}

	public Map<String, Object> parseAmazonFBAInventoryResponseXml(String response) {
		Map<String, Object> map = null;
		try {
			ParseXMLModel2 parser = new ParseXMLModel2(response, "http://mws.amazonaws.com/doc/2009-01-01/") {
				@Override
				public List<Map<String, Object>> getResult() {
					XMLNode[] columns = { XMLNode.getInstance("ReportType"),
							XMLNode.getInstance("ReportProcessingStatus"),
							XMLNode.getInstance("EndDate", XMLNode.DATE_UTC), XMLNode.getInstance("Scheduled"),
							XMLNode.getInstance("ReportRequestId"),
							XMLNode.getInstance("StartedProcessingDate", XMLNode.DATE_UTC),
							XMLNode.getInstance("SubmittedDate", XMLNode.DATE_UTC),
							XMLNode.getInstance("CompletedDate", XMLNode.DATE_UTC),
							XMLNode.getInstance("StartDate", XMLNode.DATE_UTC),
							XMLNode.getInstance("GeneratedReportId") };

					List<Map<String, Object>> list = new ArrayList<>();
					List<Element> elements = this.getElementChild("RequestReportResult_ReportRequestInfo");
					for (Element element : elements) {
						element = this.formateElement(element);
						Map<String, Object> map = parseRecord(element, columns);
						list.add(map);
					}
					return list;
				}
			};
			map = new HashMap<>();
			map.put("data", parser.getResult());
		} catch (Exception e) {
			log.error("解析Amazon请求返回的Xml数据失败", e);
		}
		return map;
	}

	private String postAmazonReport(AmazonAccountModel account, String reportType) {
		AmazonRequestConfig config = new AmazonRequestConfig(account, "RequestReport", "2009-01-01");
		Map<String, String> param = queryParam(account);
		config.setQueryParameters(param);
		return AmazonHttpUtil.amazonPOST(config);
	}

	protected Map<String, String> queryParam(AmazonAccountModel account) {
		Map<String, String> result = Maps.newConcurrentMap();
		result.put("ReportOptions", "ShowSalesChannel=true");
		result.put("ReportType", getReportType());
		result.put("PlateForm", account.getPlatform());
		ReportRequestListModel requestModel = iAmazonReportDao.getLastDoneReportByParam(result);
		try {
			if (null != requestModel) {
				result.put("StartDate", timeConvertUTCFormat(requestModel.getEndDate()));
				result.put("EndDate",
						timeConvertUTCFormat(sdf.format(TimeTools.getChangeDay(sdf.parse(requestModel.getEndDate()),
								getIntervalDay(requestModel.getEndDate(),
										TIME_CONVERT_MAP.get(SITE_RELATE_MAP.get(account.getSiteId())),account)))));
			} else {
				Date startTime = convertDateBySite("2018-06-01 00:00:00", TIME_CONVERT_MAP.get(SITE_RELATE_MAP.get(account.getSiteId())),
						"UTC");
				result.put("StartDate", timeConvertUTCFormat(sdf.format(startTime)));
				result.put("EndDate",
						timeConvertUTCFormat(sdf.format(TimeTools.getChangeDay(startTime,
								getIntervalDay("2018-06-01 00:00:00",
										TIME_CONVERT_MAP.get(SITE_RELATE_MAP.get(account.getSiteId())),account)))));
				
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	private String timeConvertUTCFormat(String time) {
		if (Strings.isNullOrEmpty(time))
			return time;
		return time.replace(" ", "T") + "+00:00";
	}
	protected Integer getDefaultIntervalDay(AmazonAccountModel account) {
		if ("DE".equals(account.getPlatform()) || "US".equals(account.getPlatform())) {
			return 10;
		}
		return 15;
	};
	protected Integer getIntervalDay(String startUTCDate, String tarZoneId,AmazonAccountModel account) throws ParseException {
		Date stard = convertDateBySite(startUTCDate, "UTC", tarZoneId);
		Date currentd = convertDateBySite(sdf.format(new Date()), "Asia/Shanghai", tarZoneId);
		int intervalDay = (int) ((currentd.getTime()-stard.getTime())/(1000*3600*24));
		return intervalDay>getDefaultIntervalDay(account)?getDefaultIntervalDay(account):intervalDay;
	};

	/**
	 * 保存报表请求订单
	 * 
	 * @param requests
	 * @param account
	 * @param site
	 *            站点，例如: "amazon.com"
	 * @param sysAuto
	 *            是否属于当前平台自动保存
	 */
	public void saveRequests(List<Map<String, Object>> requests, AmazonAccountModel account, String site,
			boolean sysAuto) {
		CollectionUtil.each(requests, new IAction<Map<String, Object>>() {
			public void excute(Map<String, Object> request) {
				request.put("platform", account.getPlatform());
				request.put("account", account.getSellerId());
				request.put("site", site);
				if (sysAuto) {
					request.put("generateMode", "sys_auto");
				}
				iAmazonReportDao.saveRequest(request);
			}
		});
	}
}
