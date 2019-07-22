package com.it.ocs.amazon.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.amazon.dao.IAmazonReportDao;
import com.it.ocs.amazon.model.ColumnData;
import com.it.ocs.amazon.model.MyiUnsuppressedVO;
import com.it.ocs.amazon.model.ReportRequestListModel;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

@Service
public class ReportDataSaveSupport {
	public final static String _GET_DATE_RANGE_FINANCIAL_TRANSACTION_DATA_ ="_GET_DATE_RANGE_FINANCIAL_TRANSACTION_DATA_";
	public final static String _GET_FBA_MYI_UNSUPPRESSED_INVENTORY_DATA_ ="_GET_FBA_MYI_UNSUPPRESSED_INVENTORY_DATA_";
	public final static String _GET_RESERVED_INVENTORY_DATA_ = "_GET_RESERVED_INVENTORY_DATA_";
	
	@Autowired
	private IAmazonReportDao iAmazonReportDao;
	
	public List<ColumnData> getColumnData(String site,String reportType){
		Map<String,Object> map = new HashMap<>();
		map.put("site", site);
		map.put("reportType", reportType);
		return iAmazonReportDao.getColumnData(map);
	}
   public String getPlatformBySite(String site){
	   return iAmazonReportDao.getPlatformBySite(site);
   }
	
	public void saveData(List<String[]> reportData, String[] title, List<ColumnData> columnData, ReportRequestListModel reportMode,String site) {
		for(String[] data : reportData){
			formatDataForSet(data,title,columnData,reportMode,site);
		}
	}
	
	private void formatDataForSet(String[] data, String[] title, List<ColumnData> columnData,ReportRequestListModel reportMode,String site) {
		Map<String,String> map = new HashMap<>();
		for(int i=0;i<title.length;i++){
			map.put(title[i], data[i]);
		}
		getSaveData(map,columnData,reportMode,site);
	}
    
	private void getSaveData(Map<String, String> map, List<ColumnData> columnData,ReportRequestListModel reportMode,String site) {
		Map<String,Object> data = new HashMap<>();
		for(ColumnData column : columnData){
			String value = map.get(column.getLinkName());
			String columnName = column.getColumnName();
			if(null != value&&!"".equals(value)){
				column.setValue(value);
				data.put(columnName, column.getValue());
				if("date_time".equals(columnName)){
					data.put("self_date", ColumnData.getSelfDateValue(value, column.getColumnType()));
				}
			}else{
				data.put(columnName, "");
			}
		}
		data.put("report_id", reportMode.getReportGetId());
		data.put("platform", reportMode.getPlatform());
		data.put("site", site);
		save(data);
	}

	private void save(Map<String, Object> data) {
		Object time = data.get("date_time");
		if(null == time || "".equals(time.toString())){
			return ;
		}
		iAmazonReportDao.addOrderReport(data);
	}
	
	public void saveInventoryData(List<String[]> reportData, String[] title, List<ColumnData> columnSet,
			ReportRequestListModel reportMode) {
		for(String[] data : reportData){
			Map<String,String> map = new HashMap<>();
			for(int i=0;i<title.length;i++){
				map.put(title[i], data[i]);
			}
			Map<String,Object> saveData = formatData(map,columnSet,reportMode);
			if(!saveData.isEmpty()){
				iAmazonReportDao.inventorySave(saveData);
			}
		}
	}
	
	/**
	 * 根据映射组装数据存储
	 * @param map 报表数据
	 * @param columnData 报表标题和数据字段映射关系
	 * @param reportMode 报表mode
	 * @return
	 */
	private Map<String,Object> formatData(Map<String, String> map, List<ColumnData> columnData, ReportRequestListModel reportMode) {
		Map<String,Object> data = new HashMap<>();
		for(ColumnData column : columnData){
			String value = map.get(column.getLinkName());
			String columnName = column.getColumnName();
			if(null != value&&!"".equals(value)){
				column.setValue(value);
				data.put(columnName, column.getValue());
			}else{
				data.put(columnName, "");
			}
		}
		data.put("REPORT_ID", reportMode.getReportGetId());
		data.put("SITE", reportMode.getSite());
		return data;
	}
	
	public void saveInventoryReservedData(List<String[]> reportData, String[] title, List<ColumnData> columnSet,
			ReportRequestListModel reportMode) {
		for(String[] data : reportData){
			Map<String,String> map = new HashMap<>();
			for(int i=0;i<title.length;i++){
				map.put(title[i], data[i]);
			}
			Map<String,Object> saveData = formatData(map,columnSet,reportMode);
			if(!saveData.isEmpty()){
				iAmazonReportDao.inventoryReservedSave(saveData);
			}
		}
		
	}
	
	public static void main(String[] args) {
		/*String c[] = {"date_time","settlement id","type","order id","sku","description","quantity","marketplace","fulfillment","order city","order state","order postal","product sales","shipping credits","gift wrap credits","promotional rebates","selling fees","fba fees","other transaction fees","other","total"};
		String t[] = {"Data/Ora:","Numero pagamento","Tipo","Numero ordine","SKU","Descrizione","Quantità","Marketplace","Gestione","Città di provenienza dell'ordine","Provincia di provenienza dell'ordine","CAP dell'ordine","Vendite","Accrediti per le spedizioni","Accrediti per confezioni regalo","Sconti promozionali","Commissioni di vendita","Costi del servizio Logistica di Amazon","Altri costi relativi alle transazioni","Altro","totale"};
		for(int i=0;i<c.length;i++){
			String str = c[i].trim();
			String str1 = str.replaceAll("\\s+", "_");
			String str2 = t[i].trim();
			//System.out.println(str+ "  VARCHAR2(256),");
			System.out.println("INSERT INTO AMAZON_REPORT_COLUMN_SET(ID,REPORT_TYPE,COLUMN_NAME,LINK_NAME,COLUMN_TYPE,PLATFORM,SITE_NAME,CREATE_DATE,UPDATE_DATE)VALUES(AMAZON_REPORT_COLUMN_SET_SEQ.NEXTVAL,'_GET_DATE_RANGE_FINANCIAL_TRANSACTION_DATA_','"+str1+"','"+str2+"',0,'DE','amazon.it',SYSDATE,SYSDATE);");
		}*/
		/*String str = "\",,,,,,,\"";
		System.out.println(str);
		while(str.contains("\",,") || str.contains(",,\"")){
			str = str.replace(",,", ",\"\",");
		}
		System.out.println(str);*/
		/*String str = "sku	fnsku	asin	product-name	condition	your-price	mfn-listing-exists	mfn-fulfillable-quantity	afn-listing-exists	afn-warehouse-quantity	afn-fulfillable-quantity	afn-unsellable-quantity	afn-reserved-quantity	afn-total-quantity	per-unit-volume	afn-inbound-working-quantity	afn-inbound-shipped-quantity	afn-inbound-receiving-quantity";
		String stra[] = str.split("\t");
		String strB = "sku	fnsku	asin	product-name	condition	your-price	mfn-listing-exists	mfn-fulfillable-quantity	afn-listing-exists	afn-warehouse-quantity	afn-fulfillable-quantity	afn-unsellable-quantity	afn-reserved-quantity	afn-total-quantity	per-unit-volume	afn-inbound-working-quantity	afn-inbound-shipped-quantity	afn-inbound-receiving-quantity";
		String strb[] = strB.split("\t");
		for(int i = 0;i<stra.length;i++){
			System.out.println("insert into AMAZON_REPORT_COLUMN_SET(ID,REPORT_TYPE,COLUMN_NAME,LINK_NAME,SITE_NAME,COLUMN_TYPE,CREATE_DATE,UPDATE_DATE,PLATFORM)values(AMAZON_REPORT_COLUMN_SET_SEQ.NEXTVAL,'_GET_FBA_MYI_UNSUPPRESSED_INVENTORY_DATA_','"+stra[i].toUpperCase().replace("-", "_")+"','"+strb[i]+"','amazon.ca',0,sysdate,sysdate,'CA');");
		}
		*/
		//sku	fnsku	asin	product-name	reserved_qty	reserved_customerorders	reserved_fc-transfers	reserved_fc-processing
		String str = "sku	fnsku	asin	product-name	reserved_qty	reserved_customerorders	reserved_fc-transfers	reserved_fc-processing";
		String stra[] = str.split("\t");
		String strB = "sku	fnsku	asin	product-name	reserved_qty	reserved_customerorders	reserved_fc-transfers	reserved_fc-processing";
		String strb[] = strB.split("\t");
		for(int i = 0;i<stra.length;i++){
			System.out.println("insert into AMAZON_REPORT_COLUMN_SET(ID,REPORT_TYPE,COLUMN_NAME,LINK_NAME,SITE_NAME,COLUMN_TYPE,CREATE_DATE,UPDATE_DATE,PLATFORM)values(AMAZON_REPORT_COLUMN_SET_SEQ.NEXTVAL,'_GET_RESERVED_INVENTORY_DATA_','"+stra[i].toUpperCase().replace("-", "_")+"','"+strb[i]+"','amazon.com',0,sysdate,sysdate,'US');");
			//System.out.println("#{"+stra[i].toUpperCase().replace("-", "_")+",jdbcType=VARCHAR},");
		}
	}
	
	public ResponseResult<MyiUnsuppressedVO> findMyiUnsuppressed(RequestParam param) {
		Map<String,Object> map = param.getParam();
		String marketplace = (String) map.get("marketplace");
		map.put("requestMarketplace", "amazon.co.uk".equals(marketplace) ? "amazon.de" : marketplace); //特殊逻辑：英国站点下的数据对应请求列表的站点为德国
		int count = iAmazonReportDao.countMyiUnsuppressed(map);
		ResponseResult<MyiUnsuppressedVO> result = new ResponseResult<MyiUnsuppressedVO>();
		result.setRows(iAmazonReportDao.findMyiUnsuppressed(map, param.getStartRow(), param.getEndRow()));
		result.setTotal(count);
		return result;
	}


}
