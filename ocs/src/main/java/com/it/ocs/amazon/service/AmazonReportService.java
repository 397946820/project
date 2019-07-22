package com.it.ocs.amazon.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.mws.MarketplaceWebServiceClient;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.GetReportRequest;
import com.amazonaws.mws.model.GetReportRequestListByNextTokenRequest;
import com.amazonaws.mws.model.GetReportRequestListByNextTokenResponse;
import com.amazonaws.mws.model.GetReportRequestListRequest;
import com.amazonaws.mws.model.GetReportRequestListResponse;
import com.it.ocs.amazon.common.AmazonReportServiceClient;
import com.it.ocs.amazon.dao.IAmazonReportDao;
import com.it.ocs.amazon.model.AmazonRequestConfig;
import com.it.ocs.amazon.model.AmazonRequestMode;
import com.it.ocs.amazon.model.ColumnData;
import com.it.ocs.amazon.model.ParseAmazonReportRequstListXMLModel;
import com.it.ocs.amazon.model.ReportRequestListModel;
import com.it.ocs.amazon.order.AmazonRequest;
import com.it.ocs.amazon.order.GetAmazonClient;
import com.it.ocs.amazon.utils.AmazonHttpUtil;
import com.it.ocs.amazon.utils.AmazonUtils;
import com.it.ocs.amazon.utils.FindTimeLine;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.Counter;
import com.it.ocs.common.util.StringUtil;
import com.it.ocs.synchronou.dao.ISyncAmazonOrderDao;
import com.it.ocs.synchronou.model.AmazonAccountModel;
import com.it.ocs.synchronou.model.ParseXMLModel2;
import com.it.ocs.synchronou.model.XMLNode;
import com.it.ocs.synchronou.util.UTCTimeUtils;
import com.it.ocs.task.core.TaskExecutorUtil;
import com.it.ocs.task.core.TaskRunnable;
/**
 * amazon 报表同步
 * @author chenyong
 *
 */
@Service
public class AmazonReportService {
	private final static Logger log = Logger.getLogger(AmazonReportService.class);
	
	private final static String BASE_PATH = "/ocs/report/";
	private final static String AMAZON_COM = "amazon.com";
	private final static String AMAZON_CA = "amazon.ca";
	private final static String AMAZON_JP = "amazon.jp";
	private final static String AMAZON_ES = "amazon.es";
	private final static String AMAZON_CO_UK = "amazon.co.uk";
	private final static String AMAZON_FR = "amazon.fr";
	private final static String AMAZON_DE = "amazon.de";
	private final static String AMAZON_IT = "amazon.it";
	private final static String AMAZON_AU = "amazon.com.au";
	
	@Autowired
	private IAmazonReportDao iAmazonReportDao;
	
	@Autowired
	private ISyncAmazonOrderDao syncAmazonOrderDao;
	
	@Autowired
	private ReportDataSaveSupport reportDataSaveSupport;

	private static final String _GET_FBA_MYI_UNSUPPRESSED_INVENTORY_DATA_ = "_GET_FBA_MYI_UNSUPPRESSED_INVENTORY_DATA_";
	
	private static final String _GET_RESERVED_INVENTORY_DATA_ = "_GET_RESERVED_INVENTORY_DATA_";
	
	private static final String[] FBA_INVENTORIES = new String[] { _GET_FBA_MYI_UNSUPPRESSED_INVENTORY_DATA_, _GET_RESERVED_INVENTORY_DATA_ };
	
	/**
	 * Spring Schedule：生成亚马逊FBA库存报表请求清单</br>
	 * <p>
	 * 调用亚马逊商城网络服务（亚马逊MWS）下的亚马逊物流 (FBA) 报告相关API：</br>
	 * <tt>
	 * 1. 亚马逊物流管理库存（<code>ReportType</code> = <code>_GET_FBA_MYI_UNSUPPRESSED_INVENTORY_DATA_</code>）；</br>
	 * 2. 亚马逊物流预留库存（<code>ReportType</code> = <code>_GET_RESERVED_INVENTORY_DATA_</code>）</br>
	 * </tt>
	 * 并且将请求列表数据预插入到当前系统亚马逊报表请求数据表（<code>table: amazon_reports_request</code>）中
	 * </p>
	 */
	public void genFBAInventoryRequests() {
		CollectionUtil.each(this.syncAmazonOrderDao.getAmazonAccounts(), new IAction<AmazonAccountModel>() {
			@SuppressWarnings("unchecked")
			public void excute(AmazonAccountModel account) {
				for (String type : FBA_INVENTORIES) {
					String response = postAmazonReport(account, type);
					Map<String, Object> data = parseAmazonFBAInventoryResponseXml(response);
					if(null != data) {
						String site = siteid2site.get(account.getSiteId());
						if(StringUtils.isNotBlank(site)) {
							saveRequests((List<Map<String,Object>>) data.get("data"),  account, site, true);	
						}
					} else {
						try {
							log.warn(String.format(">>> 获取或解析请求列表失败：Account=%s; ReportType=%s; Response=%s", StringUtil.instanceDetail(account), type, response));
						} catch (Exception e) {
							log.error(">>> exec 'log.warn' error >>>" + e.getMessage(), e);
						}
					}

					//扩大每次请求的时间间隔，避免触发亚马逊的请求保护机制。
					try {
						log.info("获取reportRequest清单，下一次请求等待中（45s）.......");
						Thread.sleep(45 * 1000);
					} catch (InterruptedException e) {
						
					}
				}
			}
		});
	}
	
	private static final Map<String, String> siteid2site = new HashMap<String, String>();

	private static final String SITE_CO_UK = "amazon.co.uk";
	
	private static final String SITE_DE = "amazon.de";
	
	static {
		siteid2site.put("ATVPDKIKX0DER", "amazon.com");
		siteid2site.put("A1VC38T7YXB528", "amazon.jp");
		siteid2site.put("A2EUQ1WTGCTBG2", "amazon.ca");
		siteid2site.put("A1RKKUPIHCS9HS", "amazon.es");
		siteid2site.put("A1F83G8C2ARO7P", SITE_CO_UK);
		siteid2site.put("A1PA6795UKMFR9", SITE_DE);
		siteid2site.put("A1RKKUPIHCS9HS,A1F83G8C2ARO7P,A1PA6795UKMFR9,A13V1IB3VIYZZH,APJ6JRA9NG5V4", SITE_DE);
		siteid2site.put("A13V1IB3VIYZZH", "amazon.fr");
		siteid2site.put("APJ6JRA9NG5V4", "amazon.it");
		siteid2site.put("A39IBJ37TRP1C6", "amazon.com.au");
	}
	
	/**
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private Map<String, String> amazonPOST(AmazonAccountModel account, String reportType) {
		Map<String, String> result = new HashMap<String, String>();
		AmazonRequestConfig config = new AmazonRequestConfig(account, "RequestReport", "2009-01-01");
		if("DE".equals(account.getPlatform().toUpperCase())) {
			Map<String, String> de = new HashMap<>(), uk = new HashMap<>();
			de.put("ReportType", reportType);
			uk.put("ReportType", reportType);
			Counter counter = new Counter();
			CollectionUtil.each(account.getSiteId().split(","), new IAction<String>() {
				public void excute(String siteid) {
					if(SITE_CO_UK.equals(siteid2site.get(siteid))) {
						uk.put("MarketplaceIdList.Id.1", siteid);
					} else {
						de.put("MarketplaceIdList.Id." + counter.beforePlus(), siteid);
					}
				}
			});
			config.setQueryParameters(de);
			result.put(SITE_DE, AmazonHttpUtil.amazonPOST(config));
			config.setQueryParameters(uk);
			result.put(SITE_CO_UK, AmazonHttpUtil.amazonPOST(config));
		} else {
			Map<String, String> param = new HashMap<>();
			param.put("ReportType", reportType);
			param.put("MarketplaceIdList.Id.1", account.getSiteId());	
			config.setQueryParameters(param);
			result.put(siteid2site.get(account.getSiteId()), AmazonHttpUtil.amazonPOST(config));
		}
		return result;
	}
	
	private String postAmazonReport(AmazonAccountModel account, String reportType) {
		AmazonRequestConfig config = new AmazonRequestConfig(account, "RequestReport", "2009-01-01");
		Map<String, String> param = new HashMap<>();
		param.put("ReportType", reportType);
		Counter counter = new Counter();
		CollectionUtil.each(account.getSiteId().split(","), new IAction<String>() {
			public void excute(String siteId) {
				param.put("MarketplaceIdList.Id." + counter.beforePlus(), siteId);
			}
		});
		config.setQueryParameters(param);
		return AmazonHttpUtil.amazonPOST(config);
	}
	
	/**
	 * 同步报表清单
	 */
	public void syncReportRequestList(){
		List<AmazonAccountModel> accounts = syncAmazonOrderDao.getAmazonAccounts();
		for(AmazonAccountModel account : accounts){
			this.getReportRequestListByAccount(account);
		}
	}
	
	/**
	 * 根据亚马逊账号获取报表清单
	 * @param account
	 */
	public void getReportRequestListByAccount(AmazonAccountModel account){
		AmazonRequestMode requestMode = AmazonUtils.getRequestModel(account);
		requestMode.setCreatedAfter(UTCTimeUtils.getUTCTimeStr(-24*3));
		requestMode.setCreatedBefore(UTCTimeUtils.getUTCTimeStr(0));
		MarketplaceWebServiceClient client = GetAmazonClient.createReportClient(requestMode);
		GetReportRequestListRequest request = AmazonRequest.createReportRequestListRequest(requestMode);
		try {
			GetReportRequestListResponse response = client.getReportRequestList(request);
			Map<String,Object> data = paseReportRequestList(response.toXML());
			if(null == data){
				return;
			}
			List<Map<String,Object>> requestList = (List<Map<String,Object>>)data.get("data");
			saveData(requestList,account);
			String nextToken = (String)data.get("nextToken");
			boolean hasNext = (boolean)data.get("hasNext");
			while(hasNext){
				requestMode.setNextToken(nextToken);
				GetReportRequestListByNextTokenRequest requestByNextToken = AmazonRequest.createReportRequestListByNextTokenRequest(requestMode);
				GetReportRequestListByNextTokenResponse responseByNextToken = client.getReportRequestListByNextToken(requestByNextToken);
				data = paseReportRequestList(responseByNextToken.toXML());
				if(null == data){
					return;
				}
				requestList = (List<Map<String,Object>>)data.get("data");
				saveData(requestList,account);
				nextToken = (String)data.get("nextToken");
				hasNext = (boolean)data.get("hasNext");
				try {
					log.info("获取reportRequest清单，下一次请求等待中（45s）.......");
					Thread.sleep(45*1000);
				} catch (InterruptedException e) {
					
				}
			}
			
		} catch (MarketplaceWebServiceException e) {
			log.info("获取亚马逊报表请求清单失败", e);
		}
	}
	
	/**
	 * 保存报表清单数据
	 * @param requestList
	 * @param account
	 */
	private void saveData(List<Map<String, Object>> requestList, AmazonAccountModel account) {
		for(Map<String, Object> request : requestList){
			request.put("platform", account.getPlatform());
			request.put("account", account.getSellerId());
			iAmazonReportDao.saveData(request);
		}
		
	}
	
	/**
	 * 保存报表请求订单
	 * @param requests
	 * @param account
	 * @param site 站点，例如: "amazon.com"
	 * @param sysAuto 是否属于当前平台自动保存	
	 */
	public void saveRequests(List<Map<String, Object>> requests, AmazonAccountModel account, String site, boolean sysAuto) {
		CollectionUtil.each(requests, new IAction<Map<String, Object>>() {
			public void excute(Map<String, Object> request) {	
				request.put("platform", account.getPlatform());
				request.put("account", account.getSellerId());
				request.put("site", site);
				if(sysAuto) {
					request.put("generateMode", "sys_auto");
				}
				iAmazonReportDao.saveRequest(request);
			}
		});
	}

	private static final String MWS_URL_PREFIX = "https://mws.amazonservices.";
	
	private static final String SITE_PREFIX = "amazon.";
	
	/**
	 * @deprecated
	 * @param account
	 * @return
	 */
	private String genSite(AmazonAccountModel account) {
		String url = account.getUrl();
		String temp = url.replace(MWS_URL_PREFIX, "");
		int index = temp.indexOf("/");
		String country = index >= 0 ? temp.substring(0, index) : temp;
		return SITE_PREFIX + country;
	}
	
	/**
	 * 解析报表清单xml数据
	 * @param xml
	 * @return
	 */
	public Map<String,Object> paseReportRequestList(String xml){
		Map<String,Object> map = null;
		try {
			Document doc = DocumentHelper.parseText(xml);
			ParseAmazonReportRequstListXMLModel parse = new ParseAmazonReportRequstListXMLModel(doc,"http://mws.amazonaws.com/doc/2009-01-01/");
			map = new HashMap<>();
			map.put("data", parse.getResult());
			map.put("nextToken",  parse.getNextToken());
			map.put("hasNext",  parse.hasNext());
		} catch (DocumentException e) {
			log.info("获取亚马逊报表请求清单解析失败", e);
		}
		return map;
	}

	public Map<String, Object> parseAmazonFBAInventoryResponseXml(String response) {
		Map<String,Object> map = null;
		try {
			ParseXMLModel2 parser = new ParseXMLModel2(response, "http://mws.amazonaws.com/doc/2009-01-01/") {
				@Override
				public List<Map<String, Object>> getResult() {
					XMLNode [] columns = {
						XMLNode.getInstance("ReportType"),
						XMLNode.getInstance("ReportProcessingStatus"),
						XMLNode.getInstance("EndDate", XMLNode.DATE_UTC),
						XMLNode.getInstance("Scheduled"),
						XMLNode.getInstance("ReportRequestId"),
						XMLNode.getInstance("StartedProcessingDate", XMLNode.DATE_UTC),
						XMLNode.getInstance("SubmittedDate", XMLNode.DATE_UTC),
						XMLNode.getInstance("CompletedDate", XMLNode.DATE_UTC),
						XMLNode.getInstance("StartDate", XMLNode.DATE_UTC),
						XMLNode.getInstance("GeneratedReportId")
					};
					
					List<Map<String,Object>> list = new ArrayList<>();
					List<Element> elements = this.getElementChild("RequestReportResult_ReportRequestInfo");
					for(Element element : elements) {
						element = this.formateElement(element);
						Map<String,Object> map = parseRecord(element,columns);
						list.add(map);
					}
					return list;
				}
			};
			map = new HashMap<>();
			map.put("data", parser.getResult());
		} catch(Exception e) {
			log.error("解析Amazon请求返回的Xml数据失败", e);
		}
		return map;
	}
	
	/**
	 * task调用方法，获取所有的亚马逊账号，并下载所属订单报告
	 */
	public void downloadReportFile(){
		List<AmazonAccountModel> accounts = syncAmazonOrderDao.getAmazonAccounts();
		for(AmazonAccountModel account : accounts){
			List<ReportRequestListModel> requests = iAmazonReportDao.getDownloadReportByAccount(account.getPlatform());
			int size = requests == null ? 0 : requests.size();
			for (int i = size - 1; i >= 0 ; i--) {
				if(isNotNeedDownload(requests.get(i))) {
					requests.remove(i);
				}
			}
			downloadReportByAccount(account, requests);
		}
	}
	
	/**
	 * 判断之情的请求是否属于不需要下载的请求
	 * @param request
	 * @return true-不需要下载; false-需要下载
	 */
	private boolean isNotNeedDownload(ReportRequestListModel request) {
		//"sys_auto"表示由当前系统平台自动生成，而非人工在Amazon FBA Inventory平台上操作生成的请求列表
		return (_GET_FBA_MYI_UNSUPPRESSED_INVENTORY_DATA_.equals(request.getReportType()) 
				|| _GET_RESERVED_INVENTORY_DATA_.equals(request.getReportType())) && !"sys_auto".equals(request.getGenerateMode());
	}
	
	@SuppressWarnings("static-access")
	private boolean canUpdateStite(String reportType) {
		return reportDataSaveSupport._GET_DATE_RANGE_FINANCIAL_TRANSACTION_DATA_.equals(reportType);
	}
	
	/**
	 * 下载报告根据亚马逊账号
	 * @param account
	 */
	public void downloadReportByAccount(AmazonAccountModel account, List<ReportRequestListModel> requests) {
		TaskExecutorUtil.threadRun(new TaskRunnable(){

			@Override 
			public void runTask() {
				AmazonRequestMode requestMode = AmazonUtils.getRequestModel(account);
				Map<String,Object> param = new HashMap<>();
				for(ReportRequestListModel request : requests){
					param.put("reportId", request.getReportGetId());
					requestMode.setParam(param);
					download(requestMode,request);
					try {
						log.info(account.getPlatform()+" 下载report,下一次请求等待中（60）.......");
						Thread.sleep(60*1000);
					} catch (InterruptedException e) {
						
					}
				}
			}
			private void download(AmazonRequestMode requestMode,ReportRequestListModel request){
				AmazonReportServiceClient client = GetAmazonClient.createReportDownloadClient(requestMode);
				GetReportRequest getReportRequest = AmazonRequest.createReportRequest(requestMode);
				FileWriter writer = null;
				String fileName = request.getPlatform()+"-"+request.getReportGetId()+"-"+System.currentTimeMillis()+".txt";
				String filePathName = createFilePath()+File.separator+fileName;
				try {
					String data = client.downloadReport(getReportRequest);
					if(null !=data && !"".equals(data)){
						
						File file = new File(filePathName);
		        		if(!file.exists()){
		        			file.createNewFile();
		        		}
		        		writer = new FileWriter(file);
		        		writer.write(data);
		                writer.flush();
		                request.setFilePathName(filePathName);
		                iAmazonReportDao.updateReportRequest(request);
		                writer.close();
		                if(canUpdateStite(request.getReportType())) {
			                updateRequestSite(request,filePathName);
		                }
					}
	        		
				} catch (Exception e) {
					log.info("获取亚马逊报表数据失败", e);
				}finally{
					if(null != writer){
						try {
							writer.close();
						} catch (IOException e) {
						}
					}
				}
				
				
			}
			
			private void updateRequestSite(ReportRequestListModel request, String filePathName) {
				FileInputStream fis = null;
				InputStreamReader isr = null;
				BufferedReader br = null;
				try {
					fis = new FileInputStream(filePathName);
					isr= new InputStreamReader(fis, "UTF-8");
			        br = new BufferedReader(isr);
			        String line="";
			        String data[] = null;
			        String site = "";
			        boolean isTitle = true;
			        List<String> getSiteData = new ArrayList<>();
			        
			        while ((line=br.readLine())!=null) {
			        	if(line.length()<10){
			        		continue;
			        	}
			        	line = line.substring(1, line.length()-1);
			        	data = line.split("\",\"");
			        	if(data.length > 10){
			        		if(isTitle){
			        			isTitle = false;
			        		}else{
			        			if(getSiteData.size()<100){
			        				getSiteData.add(line);
			        			}else{
			        				break;
			        			}
			        		}
			        	}
			        }
			        if(getSiteData.size()>0){
			        	site = getSite(getSiteData,filePathName);
			        	request.setSite(site);
			        	iAmazonReportDao.updateRequestSite(request);
			        }
			        
				} catch (Exception e) {
					log.info("解析订单报表数据失败",e);
				}finally {
					if(null !=br){
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if(null != isr){
						try {
							isr.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
			        if(null != fis){
			        	try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
			        }
				}
			}
		});
	}
	
	

	/**
	 * 根据日期创建目录
	 * @return
	 */
	public static String createFilePath(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String day = sdf.format(new Date());
		String path = BASE_PATH + day;
		File fileDir = new File(path);
		if(!fileDir.exists()){
			fileDir.mkdirs();
		}
		return path;
	}
	
	/**
	 * 获取带解析文件清单
	 */
	public void paseOrderReport(){
		String sites[] = {"amazon.ca","amazon.jp","amazon.es","amazon.it","amazon.fr","amazon.co.uk","amazon.com","amazon.de","amazon.com.au"};
		for(String site : sites){
			//先处理异常数据
			//paseExceptionData(site);
			paseSysClickData(site);
		}
		
	}
	
	private void paseExceptionData(String site){
		List<ReportRequestListModel> reports = iAmazonReportDao.getExceptionTimeData(site);
		for(ReportRequestListModel reportMode: reports){
			List<ReportRequestListModel> startData = iAmazonReportDao.getAllBetweenData(reportMode);
			if(null == startData || startData.size() == 0){
				continue;
			}
			FindTimeLine line = new FindTimeLine(startData, reportMode.getStartDate(), reportMode.getEndDate());
			//解析
			List<ReportRequestListModel> re = line.getReturn();
			if(null != re){
				for(ReportRequestListModel data : re){
					paseData(data);
				}
				//更新此条异常数据
				iAmazonReportDao.updateReportExceptionStatus(reportMode);
			}
			
		}
	}
	


	private void paseSysClickData(String site){
		ReportRequestListModel reportMode = iAmazonReportDao.getParseStart(site);
		if(null == reportMode){
			return;
		}
		String startTime = "";
		String endTime = "";
		while(null != reportMode){
			if(reportMode.getIsParse() == 0&&reportMode.getIsGetData()==1){
				paseData(reportMode);
			}
			startTime = reportMode.getEndDate();
			reportMode.setStartDate(startTime);
			//获取中断的结束时间
			endTime = iAmazonReportDao.getEndTime(reportMode);
			reportMode.setEndDate(endTime);
			reportMode = iAmazonReportDao.getNextParseReport(reportMode);
		}
		if(null == reportMode&&isBreakOff(site,startTime)){
			log.info("--------------------------------------------------------------------------");
			log.info("----站点"+site+"订单报表数据中断 time:"+startTime+"------------------");
			log.info("--------------------------------------------------------------------------");
			reportMode = new ReportRequestListModel();
			reportMode.setSite(site);
			reportMode.setStartDate(startTime);
			reportMode.setEndDate(endTime);
			//选择所有的请求清单（包含白天点击的报表数据）中此时间段的数据
			//根据起始时间和结束时间判断
			String platform = site.substring(site.lastIndexOf(".")).toUpperCase();
			if("COM".equals(platform)){
				platform = "US";
			}else if("CA".equals(platform)||"JP".equals(platform)){
				platform = platform;
			}else{
				platform = "DE";
			}
			reportMode.setPlatform(platform);
			iAmazonReportDao.chooseByRequestList(reportMode);
			iAmazonReportDao.addOrderReportException(reportMode);
		}
	}
	
	
	private void paseData(ReportRequestListModel reportMode) {
		iAmazonReportDao.deleteReportDataById(reportMode.getReportGetId());
		paseOrderReportData(reportMode);
		
	}
	/**
	 * 判断数据是否中断
	 * 依据：此时间之后，存在系统点的报表
	 * @param site
	 * @param startTime
	 * @return
	 */
	private boolean isBreakOff(String site, String startTime) {
		Map map = new HashMap<>();
		map.put("site", site);
		map.put("startTime", startTime);
		//return iAmazonReportDao.isBreakOff(map)>0?true:false;
		return false;
	}
	
	/**
	 * 读取文件解析数据
	 * @param reportMode
	 */
	private void paseOrderReportData(ReportRequestListModel reportMode) {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(reportMode.getFilePathName());
			isr= new InputStreamReader(fis, "UTF-8");
	        br = new BufferedReader(isr);
	        String line="";
	        String title[] = null;
	        String data[] = null;
	        String site = "";
	        boolean isTitle = true;
	        boolean getsiteDataFlag = true;
	        List<String> getSiteData = new ArrayList<>();
	        List<String[]> reportData = new ArrayList<>();
	        List<ColumnData> columnSet = new ArrayList<>();
	        //计数，避免同一个report被多次解析
	        int count = 0;
	        while ((line=br.readLine())!=null) {
	        	if(line.length()<10){
	        		continue;
	        	}
	        	while(line.contains("\",,") || line.contains(",,\"")){
	        		line = line.replace(",,", ",\"\",");
	    		}
	        	line = line.substring(1, line.length()-1);
	        	data = line.split("\",\"");
	        	if(data.length > 10){
	        		if(isTitle){
	        			title = data;
	        			isTitle = false;
	        		}else{
	        			count++;
	        			if(getSiteData.size()<100){
	        				getSiteData.add(line);
	        			}
	        			if(getSiteData.size()>=100 && getsiteDataFlag){
	        				site = getSite(getSiteData,reportMode.getFilePathName());
	        				columnSet = reportDataSaveSupport.getColumnData(site,reportDataSaveSupport._GET_DATE_RANGE_FINANCIAL_TRANSACTION_DATA_);
	        				getsiteDataFlag = false;
	        			}
	        			reportData.add(data);
	        			if(reportData.size()>0&&reportData.size()%100==0){
	        				reportDataSaveSupport.saveData(reportData,title,columnSet,reportMode,site);
	        				reportData.clear();
	        			}
	        			
	        			
	        		}
	        	}
	        }
	        if(getSiteData.size()<100){
	        	site = getSite(getSiteData,reportMode.getFilePathName());
				columnSet = reportDataSaveSupport.getColumnData(site,reportDataSaveSupport._GET_DATE_RANGE_FINANCIAL_TRANSACTION_DATA_);
	        }

	        if(reportData.size()> 0){
	        	//最后数据不足100条，数据处理
		        reportDataSaveSupport.saveData(reportData,title,columnSet,reportMode,site);
	        }
	        //验证计数与数据库数量是否匹配
	        if(iAmazonReportDao.countThisReportData(reportMode.getReportGetId()) > count){
	        	iAmazonReportDao.deleteReportDataById(reportMode.getReportGetId());
	        }else{
	        	iAmazonReportDao.updateParseStatus(reportMode);
	        }
	        
		} catch (Exception e) {
			iAmazonReportDao.deleteReportDataById(reportMode.getReportGetId());
			log.info("解析订单报表数据失败",e);
		}finally {
			if(null !=br){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null != isr){
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	        if(null != fis){
	        	try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
		}
	}
	/**
	 * 根据数据判断站点信息，
	 * <取100条数据，判断站点名称出现次数>
	 * @param getSiteData
	 * @param fileName 
	 * @return
	 */
	private String getSite(List<String> getSiteData, String fileName) {
		String dataDemo = null;
		Map<String,Integer> map = new HashMap();
		for(String str : getSiteData){
			if(null == dataDemo){
				dataDemo = str;
			}
			if(str.indexOf(this.AMAZON_AU)>-1){
				Integer count = map.get(AMAZON_AU);
				if(null == count){
					map.put(AMAZON_AU, 1);
				}else{
					count = count + 1;
					map.put(AMAZON_AU, count);
				}
				continue;
			}else if(str.indexOf(this.AMAZON_COM)>-1){
				Integer count = map.get(AMAZON_COM);
				if(null == count){
					map.put(AMAZON_COM, 1);
				}else{
					count = count + 1;
					map.put(AMAZON_COM, count);
				}
				continue;
			}else if(str.indexOf(this.AMAZON_CA)>-1){
				Integer count = map.get(AMAZON_CA);
				if(null == count){
					map.put(AMAZON_CA, 1);
				}else{
					count = count + 1;
					map.put(AMAZON_CA, count);
				}
				continue;
			}else if(str.indexOf(this.AMAZON_CO_UK)>-1){
				Integer count = map.get(AMAZON_CO_UK);
				if(null == count){
					map.put(AMAZON_CO_UK, 1);
				}else{
					count = count + 1;
					map.put(AMAZON_CO_UK, count);
				}
				continue;
			}else if(str.indexOf(this.AMAZON_DE)>-1){
				Integer count = map.get(AMAZON_DE);
				if(null == count){
					map.put(AMAZON_DE, 1);
				}else{
					count = count + 1;
					map.put(AMAZON_DE, count);
				}
				continue;
			}else if(str.indexOf(this.AMAZON_ES)>-1){
				Integer count = map.get(AMAZON_ES);
				if(null == count){
					map.put(AMAZON_ES, 1);
				}else{
					count = count + 1;
					map.put(AMAZON_ES, count);
				}
				continue;
			}else if(str.indexOf(this.AMAZON_FR)>-1){
				Integer count = map.get(AMAZON_FR);
				if(null == count){
					map.put(AMAZON_FR, 1);
				}else{
					count = count + 1;
					map.put(AMAZON_FR, count);
				}
				continue;
			}else if(str.indexOf(this.AMAZON_JP)>-1){
				Integer count = map.get(AMAZON_JP);
				if(null == count){
					map.put(AMAZON_JP, 1);
				}else{
					count = count + 1;
					map.put(AMAZON_JP, count);
				}
				continue;
			}else if(str.indexOf(this.AMAZON_IT)>-1){
				Integer count = map.get(AMAZON_IT);
				if(null == count){
					map.put(AMAZON_IT, 1);
				}else{
					count = count + 1;
					map.put(AMAZON_IT, count);
				}
				continue;
			}
		}
		
		//不含站点信息
		if(map.isEmpty()){
			if(fileName.contains("US-")){
				return AMAZON_COM;
			}else if(fileName.contains("CA-")){
				return AMAZON_CA;
			}else if(fileName.contains("JP-")){
				return AMAZON_JP;
			}else if(fileName.contains("AU-")){
				return AMAZON_AU;
			}else if(fileName.contains("DE-")){
				String dateTimeStr = dataDemo.split("\",\"")[0];
				if(dateTimeStr.contains("/")){
					String month = dateTimeStr.split("/")[1];
					if(isNumeric(month)){
						return AMAZON_ES;
					}else{
						return AMAZON_IT;
					}
				}
				if(dateTimeStr.contains(".")){
					String month = dateTimeStr.split("\\.")[1];
					if(isNumeric(month)){
						return AMAZON_DE;
					}else{
						return AMAZON_FR;
					}
				}
				
				return AMAZON_CO_UK;
			}
		}
		String site = "";
		int max = 0;
		for(Map.Entry<String, Integer> entry:map.entrySet()){
			if(entry.getValue() > max){
				max = entry.getValue();
				site = entry.getKey();
			}
		}
		return site;
	}

	
	public final static boolean isNumeric(String s) {  
        if (s != null && !"".equals(s.trim()))  
            return s.matches("^[0-9]*$");  
        else {
            return false;
        }
    }
	public void amazonDataOneDay(){
		List<Map<String,Object>> list = iAmazonReportDao.getAmazonChangeData();
		for(Map<String,Object> map : list){
			int dateType = Integer.parseInt(map.get("DATETYPE").toString());
			if(dateType == 1){
				iAmazonReportDao.countAmazonDataByCreateTime(map);
			}
			if(dateType == 2){
				iAmazonReportDao.countAmazonDataByUpdateTime(map);
			}
			if(dateType == 3){
				iAmazonReportDao.countAmazonDataByShipTime(map);
			}
			
		}
	}
}
