package com.it.ocs.compare.service.inner.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.it.ocs.amazon.model.ColumnData;
import com.it.ocs.amazon.service.ReportDataSaveSupport;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.ExcelImportUtil;
import com.it.ocs.compare.dao.IAmazonOrderCompareDAO;
import com.it.ocs.compare.model.AmazonDBModel;
import com.it.ocs.compare.model.AmazonInputEXModel;
import com.it.ocs.compare.service.impl.support.AmazonExcelCompareSupport;
import com.it.ocs.compare.service.inner.IAmazonCompareService;
import com.it.ocs.compare.utils.DateUtil;
import com.it.ocs.compare.vo.DataCompareVO;
import com.it.ocs.excel.annotation.ExcelLink;
import com.it.ocs.excel.utils.ExcelUtils;
import com.it.ocs.synchronou.mapping.AmazonMapping;
import com.it.ocs.synchronou.util.ValidationUtil;
@Service
public class AmazonCompareService implements IAmazonCompareService {

	@Autowired
	private ReportDataSaveSupport reportDataSaveSupport;
	@Autowired
	private IAmazonOrderCompareDAO amazonDao;
	@Override
	public SXSSFWorkbook amazonDataCompare(DataCompareVO param, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			InputStream inputStream = param.getMyfile().getInputStream();
			//导入进来的数据
			List<AmazonInputEXModel> list = convertBean(inputStream,param.getAccount());
			
			list.sort(new Comparator<AmazonInputEXModel>() {
				@Override
				public int compare(AmazonInputEXModel o1, AmazonInputEXModel o2) {
					// TODO Auto-generated method stub
					
					if(ValidationUtil.isNullOrEmpty(o1.getDate_time())||ValidationUtil.isNullOrEmpty(o2.getDate_time())){
						return -1;
					}
					return DateUtil.getSelfDateValue(o1.getDate_time(), param.getAccount()).compareTo(DateUtil.getSelfDateValue(o2.getDate_time(), param.getAccount()));
				}
			});
			
			List<AmazonDBModel> dbModels = amazonDao.queryByInuptParam(getParam(list,param));
			List<String> type = amazonDao.queryTypeByPlatform(getParam(list,param));
			return AmazonExcelCompareSupport.compareDate(list, dbModels, request, response,type);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private Map<String,Object> getParam(List<AmazonInputEXModel> list,DataCompareVO compareParam){
		Map<String,Object> param = new HashMap<>();
		String site = compareParam.getAccount();
		String beginTime = getBeginTime(list);
		String endTime = getEndTime(list);
		
		param.put("startTime", beginTime);
		param.put("endTime", endTime);
		param.put("platform", site);
		return param;
	}
	public void inputstreamtofile(InputStream ins,File file){
		OutputStream os;
		try {
			os = new FileOutputStream(file);
		
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	private String getBeginTime(List<AmazonInputEXModel> list){
		for (AmazonInputEXModel amazonInputEXModel : list) {
			String beginTime = amazonInputEXModel.getDate_time();
			if (!StringUtils.isBlank(amazonInputEXModel.getDate_time())) {
				
				return beginTime;
			}
		}
		return null;
	}
	private <T> List<AmazonInputEXModel> convertBean(InputStream in ,String site) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		List<AmazonInputEXModel> reslut = Lists.newArrayList();
		String platform = AmazonMapping.searchPlatformBySite(site);
		BufferedReader bufferedReader = null;
		try{
			
			/*File file = new File("E:\\werew\\file.txt");
			inputstreamtofile(in,file);
			
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GB2312"));
			*/
			bufferedReader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			String line="";
			String title[] = null;
		    String data[] = null;
		    List<String[]> reportData =Lists.newArrayList();
		   
		    boolean isTitle = true;
			while((line=bufferedReader.readLine())!=null){
				data = line.split("\t");
				if(data.length > 10){
					if(isTitle){
	        			title = data;
	        			isTitle = false;
	        		}else{
	        			reportData.add(data);
	        		}
        		}
			}
			List<ColumnData> columnDatas = reportDataSaveSupport.getColumnData(site,reportDataSaveSupport._GET_DATE_RANGE_FINANCIAL_TRANSACTION_DATA_);
			
			Map<String, String> columnMapping = new HashMap<>();
			for (ColumnData columnData : columnDatas) {
				String linkName = columnData.getLinkName();
				columnMapping.put(linkName, columnData.getColumnName());
			}
			 for (String[] strings : reportData) {
					AmazonInputEXModel amazonInputEXModel = new AmazonInputEXModel();
					if(strings.length==20){
						System.out.println(strings);
					}
				    int i=0;
				    for (String t : title) {
				    	String key = CollectionUtil.search(columnMapping.keySet(), new IFunction<String, Boolean>() {
				    	       @Override
				    	       public Boolean excute(String obj) {
				    	    	   if(t.length()-obj.length()<=1){
				    	    		   return t.contains(obj);
				    	    	   }else{
				    	    		   return false;
				    	    	   }
				    	        
				    	       }
				    	      });
				    	String name = columnMapping.get(key);
				    	String fieldName = name.substring(0, 1).toUpperCase() + name.substring(1);
				    	
				    	Method m = amazonInputEXModel.getClass().getMethod("set"+fieldName, String.class);
				    	String value = strings[i].replace("\"", "");
				    	if(fieldName.equalsIgnoreCase("date_time")){
				    		m.invoke(amazonInputEXModel, DateUtil.getSelfDateValue(value, platform));
				    	}else{
				    		if(ValidationUtil.isEquals(platform, "US","CA","UK","JP")){
				    			m.invoke(amazonInputEXModel, value.replace(",", ""));
				    		}else{
				    			m.invoke(amazonInputEXModel, value.replace(".", "").replace(",", "."));
				    		}
				    		
				    	}
				    
				    	
				    	i++;
				    }
				    
				    reslut.add(amazonInputEXModel);
			 }
			
			
		}finally {
			if(bufferedReader!=null){
				bufferedReader.close();
			}
		}
		
		return reslut;
	}

	private String getEndTime(List<AmazonInputEXModel> list) {
		for (int i = list.size() - 1; i > 0; i--) {
			String endTime = list.get(i).getDate_time();
			if (!StringUtils.isBlank(endTime)) {
				return endTime;
			}
		}
		return null;
	}
}
