//package com.it.ocs.sys.service.impl;
//
//import java.io.BufferedWriter;
//import java.io.FileInputStream;
//import java.io.FileWriter;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import com.it.ocs.publication.dao.IPublicationInfoDAO;
//import com.it.ocs.synchronou.dao.IEBayAccountDao;
//import com.it.ocs.synchronou.mapping.UserIDToken;
//import com.it.ocs.synchronou.model.EbayAccountModel;
//import com.it.ocs.synchronou.service.impl.BaseHttpsService;
//import com.it.ocs.sys.model.EbayOnlineModel;
//import com.it.ocs.sys.model.ItemDataModel;
//import com.it.ocs.sys.model.ParseEbayItemXMLModel;
//import com.it.ocs.sys.service.IEbayGetDataService;
//import com.it.ocs.sys.service.support.EbayHttpSupport;
//import com.it.ocs.task.dao.impl.OracleData;
//
//@Service
//public class EbayGetDataService extends BaseHttpsService implements IEbayGetDataService {
//	// private final static String token =
//	// "AgAAAA**AQAAAA**aAAAAA**u1RjWQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AEmYSiC5SDpQ2dj6x9nY+seQ**Xq8DAA**AAMAAA**nzc7lCXNjRxjfZiTVjNJ6hYFYlZxK+5R6x7rr5WSGrTGJTjr3ykfT4YvBuhdDheZSpftIpfgfQMA//5Y1AhRHhckFpPnVU+7t2R7pViACV9wyiDGh2UjZ/XRuh1v5dr6zqUQN8AIo47gJE+AXeeehIC7G77E0DY2/0p+IxIWCtqP9MNjAfMIPWYsOhX4OnqMQgBIQdcMZZ5/3PoQRj/0ELiTn3RqltZq/T9BnyNv14qXBfXcUiijizx2a0OZBv1dfIrog7/ohP/sqBgvgyl31Ebi78Qp/TgIZtjP9r44tbw7kmsk/X1jtjeeXYfKZ+JelJlYZLZX4DTCCobYAZ7E5sbPI7frJFyRBsm7gxV5sZeBPdwDC5hB9pOTQpM27CdOsz2e089ughAEQw8H9ZV+DXUR4Aw3BNr40QwAkh34dBDF7UFo6JU8bWYTeweFT2vAdxUhvEfFjb5FRMcU5A9LK6SUkcoXP5f2DOD9nloZQOw6X6rczVsDeXd2dCPGTK+vpf82gjY+NWbVanPjICAEwFQZqPQe2OesAcbbK5ztYmeftzAvIEoWR71fHMrKDNrVeEFzzP9a9Pdl/C1UZ5Ys/xlLU07BnqL/idcx0+2WdcW3ZKpwMD7M6NobbqAuHBE7pvezSZ1DAVl+h5mrEURUQdOUMCRV5nKNkOYooeORqtKVclyNAOMPnvJwgn1sr/gdLuzoYtgsREiKBv1wJmni2YwcU80d+uLmCk41nqG3n8unVN0CURR+8g5BGSNFVvl1";
//
//	@Autowired
//	protected IPublicationInfoDAO pubInfoDAO;
//	@Autowired
//	private IEBayAccountDao accountDao;
//	
//	@Override
//	public Document getItemById(String itemId, String siteId, String account) {
//		return httpsItemById(itemId, siteId, account);
//	}
// 
//	private Document httpsItemById(String itemId, String siteId, String account) {
//		EbayAccountModel accountModel = accountDao.getAccountByAccount(account);
//		Map map = new HashMap();
//		map.put("X-EBAY-API-SITEID", siteId);
//		map.put("X-EBAY-API-COMPATIBILITY-LEVEL", "967");
//		map.put("X-EBAY-API-CALL-NAME", "GetItem");
//		StringBuffer requestXml = new StringBuffer();
//		requestXml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
//		requestXml.append("<GetItemRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">");
//		requestXml.append("<RequesterCredentials>");
//		requestXml.append("<eBayAuthToken>" +UserIDToken.searchTokenByUserID(account) + "</eBayAuthToken>");
//		requestXml.append("</RequesterCredentials>");
//		//  < IncludeItemSpecifics > boolean </ IncludeItemSpecifics >
//		requestXml.append("<IncludeItemSpecifics >true </IncludeItemSpecifics>");
//		requestXml.append("<ErrorLanguage>en_US</ErrorLanguage>");
//		requestXml.append("<WarningLevel>High</WarningLevel>");
//		requestXml.append("<DetailLevel>ReturnAll</DetailLevel>");
//		requestXml.append("<ItemID>" + itemId + "</ItemID>");
//		requestXml.append("</GetItemRequest>");
//		return super.getPesponseXml("https://api.ebay.com/ws/api.dll", map, requestXml.toString());
//	}
//
//	@Override
//	public void parseItemData(String itemId, String siteId, String account) {
//		Document document = this.getItemById(itemId, siteId, account);
//
//	}
//
//	@Override
//	public void synchronizeEBayOnlineData() {
//		List<EbayOnlineModel> onlineDatas = pubInfoDAO.getEBayItemList();
//		for (EbayOnlineModel onlineMode : onlineDatas) {
//			Document document = getItemById(onlineMode.getItemId(), onlineMode.getSiteId(),
//					onlineMode.getAccountName());
//			String xml = document.asXML();
//			onlineMode.setEbayXML(xml);
//			pubInfoDAO.updateEbayXML(onlineMode);
//			parseDocument(document, onlineMode);
//		}
//	}
//
//	private void parseDocument(Document document, EbayOnlineModel onlineMode) {
//		// 获取设置
//		List<ItemDataModel> itemDataModels = pubInfoDAO.getItemDataModels(524);
//		StringBuffer sql = new StringBuffer();
//		sql.append("insert into ebay_publication_info_1(template_id,EBAY_ACCOUNT,CREATION_DATE,LAST_UPDATE_DATE,item_id");
//		StringBuffer valuesSql = new StringBuffer();
//		valuesSql.append(")values(EBAY_SITE_INFO_S.Nextval,'" + onlineMode.getAccountName() + "',sysdate,sysdate,"
//				+ onlineMode.getItemId());
//		valuesSql.append(")");
//		sql.append(valuesSql.toString());
//		pubInfoDAO.excute(sql.toString());
//
//		for (ItemDataModel mode : itemDataModels) {
//			StringBuffer update = new StringBuffer("update ebay_publication_info_1 set  ");
//			String value = this.getValue(mode.getLinkView(), document);
//			if (StringUtils.isEmpty(value)) {
//				continue;
//			}
//			if (value.contains("'")) {
//				value = value.replace("'", "''");
//			}
//			if (!StringUtils.isEmpty(mode.getFormatSql())) {
//				String format = mode.getFormatSql();
//				format = format.replace("?", "'" + value + "'");
//				value = pubInfoDAO.queryData(format);
//			}
//			boolean isTooLong = false;
//			update.append(mode.getName() + "=");
//			if (isNumeric(value)) {
//				update.append(value);
//			} else {
//				if (value.length() > 4000) {
//					update.append("?");
//					isTooLong = true;
//				} else {
//					update.append("'" + value + "'");
//				}
//			}
//			update.append(" where item_id=" + onlineMode.getItemId());
//			if (isTooLong) {
//				OracleData.insertSql(update.toString(), value);
//			} else {
//				pubInfoDAO.excute(update.toString());
//			}
//		}
//		/*
//		 * valuesSql.append(")"); sql.append(valuesSql.toString());
//		 * //pubInfoDAO.excute(sql.toString());
//		 * OracleData.insertSql(sql.toString());
//		 */
//	}
//
//	private String getValue(String linkView, Document document) {
//		if (StringUtils.isEmpty(linkView)) {
//			return "";
//		}
//		String[] nodeNames = linkView.split("\\.");
//
//		Element element = document.getRootElement();
//		
//		String curNodeName = nodeNames[0];
//		int i = 0;
//		while (i < nodeNames.length) {
//			curNodeName = nodeNames[i];
//			//判断是否有多个一样的节点
//			if(i==nodeNames.length-1){
//				List<Element> elements =  element.elements(curNodeName);
//				if(null != elements && elements.size() > 1){
//					return getValuesByList(elements);
//				}
//			}
//			if (null == element.element(curNodeName)) {
//				return element.getText();
//			} else {
//				element = element.element(curNodeName);
//			}
//			i++;
//		}
//		return EbayHttpSupport.get(curNodeName, element);
//	}
//	
//	private String getValuesByList(List<Element> elements){
//		String values = "";
//		for(Element element :elements){
//			if("".equals(values)){
//				values = element.getText();
//			}else{
//				values = values +","+ element.getText();
//			}
//			
//		}
//		return values;
//	}
//
//	public boolean isNumeric(String str) {
//		String string = "[0-9]+.?[0-9]+";
//		Pattern pattern = Pattern.compile(string);
//		Matcher isNum = pattern.matcher(str);
//		if (!isNum.matches()) {
//			return false;
//		}
//		return true;
//	}
//
//	@Override
//	public void loadPAExcel() {
//		try {
//			InputStream is = new FileInputStream("D:/Listing.xls");
//			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
//			// Read the Sheet
//			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
//			String insertSql = "insert into ebay_pa_data(";
//			// Read the Row
//			for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
//				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
//				String sql = insertSql;
//				// 遍历列Cell
//				for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
//					HSSFCell hssfCell = hssfRow.getCell(cellNum);
//					if (hssfCell == null) {
//						continue;
//					}
//					if(rowNum == 0){
//						if(cellNum ==0){
//							insertSql = insertSql + getValue(hssfCell);
//						}else{
//							insertSql = insertSql +","+ getValue(hssfCell);
//							if(cellNum == hssfRow.getLastCellNum()-1){
//								insertSql = insertSql+")values(";
//							}
//						}	
//					}else{
//						String value = getValue(hssfCell);
//						if(value.contains("'")){
//							value = value.replace("'", "''");
//						}
//						if(cellNum ==0){
//							
//							sql = sql + "'"+value+"'";
//						}else{
//							sql = sql +",'"+value+"'";
//						}
//					}
//					//System.out.println("insert into ebay_pa_data(" + getValue(hssfCell)+")values(");
//				}
//				sql = sql+")";
//				System.out.println(sql);
//				//WriteStringToFile2(sql);
//				OracleData.insertSql(sql, null);
//			}
//
//		} catch (Exception e) {
//
//		}
//
//	}
//	 public void WriteStringToFile2(String sql) {
//	        try {
//	            FileWriter fw = new FileWriter("D:/items.txt", true);
//	            BufferedWriter bw = new BufferedWriter(fw);
//	            //bw.append("在已有的基础上添加字符串");
//	            bw.write(sql+"\r\n ");// 往已有的文件上添加字符串
//	            bw.close();
//	            fw.close();
//	        } catch (Exception e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	        }
//	    }
//	private String getValue(HSSFCell hssfCell) {
//		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
//			return String.valueOf(hssfCell.getBooleanCellValue());
//		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
//			return String.valueOf(hssfCell.getNumericCellValue());
//		} else {
//			return String.valueOf(hssfCell.getStringCellValue());
//		}
//	}
//	public static void main(String[] args) {
//		EbayGetDataService e = new EbayGetDataService();
//		e.loadPAExcel();
//	}
//
//	@Override
//	public void uopdateEBayOnlineData() {
//		List<String> items = pubInfoDAO.getItemList();
//		for(String item : items){
//			String xml = pubInfoDAO.getItemInfoXML(item);
//			Document doc = null;
//			try {
//				doc = DocumentHelper.parseText(xml);
//				ParseEbayItemXMLModel parse = new ParseEbayItemXMLModel(doc, "urn:ebay:apis:eBLBaseComponents");
//				List<Map<String, Object>> data = parse.getResult();
//				for(Map<String, Object> map : data){
//					pubInfoDAO.updateItemInfo(map);
//				}
//			} catch (DocumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
//	}
//}
