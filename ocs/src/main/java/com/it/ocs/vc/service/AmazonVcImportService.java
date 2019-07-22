package com.it.ocs.vc.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.sys.model.UserModel;
import com.it.ocs.sys.service.IUserService;
import com.it.ocs.vc.dao.IAmazonVcDAO;
import com.it.ocs.vc.model.AmazonVcModel;

@Service("amazonVcImport")
public class AmazonVcImportService extends AExcelImport {
	
	private static final Logger logger = Logger.getLogger(AmazonVcImportService.class);

	@Autowired
	private IAmazonVcDAO amazonVcDAO;
	
	@Autowired
	private IUserService userService;

	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {

		return null != row.get("shippingDate") && null != row.get("po") && null != row.get("modelNumber");
	}

	@Override
	protected boolean isExist(Map<String, Object> row, List<String> errorsMsg) {
		return amazonVcDAO.getCount(row) > 0;
	}

	@Override
	protected int insert(List<Map<String, Object>> rows, List<String> errorsMsg) {
		UserModel concurrentUser = userService.getConcurrentUser();
		CollectionUtil.each(rows, new IAction<Map<String, Object>>() {
			@Override
			public void excute(Map<String, Object> data) {
				data.put("lastUpdateBy", concurrentUser.getId());
				data.put("createBy", concurrentUser.getId());
				List<AmazonVcModel> models = amazonVcDAO.queryByParam(data);
				if (!CollectionUtil.isNullOrEmpty(models)) {
					AmazonVcModel m = models.get(0);
					data.put("ocsOrderId", m.getOcsOrderId());
				} else {
					Long ocsOrderId = amazonVcDAO.getOcsOrderId();
					data.put("ocsOrderId", ocsOrderId);
				}
				splitAddress(data);
				amazonVcDAO.add(data);
			}
		});
		return rows.size();
	}

	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {
		return 0;
	}

	@Override
	protected int refresh(List<Map<String, Object>> rows) {
		return 0;
	}

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		return AmazonVcModel.class;
	}
	
	private void splitAddress(Map<String,Object> data) {
		String platform = data.get("platform").toString();
		String[] values = data.get("shipToAddr1").toString().split("\n");
		if ("DE".equals(platform)) {
			/** 
			 *[0]  	Amazon Logistik Winsen GmbH
			 *[1]	Borgwardstrasse 10
			 *[2]	21423 Winsen an der Luhe
			 *[3]	Germany
			 *
			 *		第一种情况，四行数据：
			 *  	公司名称
			 *	  	收件地址1
			 *	  	邮编 城市 
			 *  	国家
			 *  
			 *		第二种情况，五行数据：
			 *  	公司名称
			 *	  	收件地址1
			 *	  	收件地址2
			 *	  	邮编 城市
			 *  	国家
			 */
			data.put("shipToName", values[0]); //公司名称。
			data.put("shipToCountry", values[values.length - 1]); //倒数第一行：国家。
			String str = values[values.length - 2].trim(); //倒数第二行：邮编 城市。
			int index = str.indexOf(" ");
			//如果不包含空格，视为只有一个值；否则，才视为[城市 邮编]。
			if(index < 1) {
				//如果是数字，视为邮编；否则，视为城市。而另一个字段，给空字符串即可。
				data.put(StringUtils.isNumeric(str) ? "shipPC" : "shipToAddr3", str);
				data.put(StringUtils.isNumeric(str) ? "shipToAddr3" : "shipPC", "");
			} else {
				data.put("shipPC", str.substring(0, index)); //邮编。
				data.put("shipToAddr3", str.substring(index + 1)); //城市。
			}
			data.put("shipToAddr1", values[1]); //第二行：地址1。
			if(values.length > 4) {
				data.put("shipToAddr2", values[2]); //大于4行才有地址2，否则第三行（索引为2）为[邮编 城市]（即倒数第二行）。
			}
		} else if("ES".equals(platform)) {
			/**
			 *[0] 	BCN1 FULFILLMENT SL
			 *[1]	Av. de les Garrigues num. 6-8
			 *[2]	El Prat de Llobregat
			 *[3]	El Prat de Llobregat, Barcelona 08820
			 *
			 * 		第一种情况，三行数据：
			 *  	公司名称
			 *	  	收件地址1
			 *	 	[收件地址2, ]城市 邮编
			 *
			 * 		第二种情况，四行数据：
			 *  	公司名称
			 *	  	收件地址1
			 *	  	收件地址2
			 *	 	[收件地址2, ]城市 邮编
			 */
			data.put("shipToName", values[0]); //第一行：公司名称。
			String str = values[values.length - 1]; //倒数第一行：[地址, ]城市 邮编。
			int index = str.lastIndexOf(", ");
			String left = index >= 0 ? str.substring(0, index) : ""; //取倒数第一行中包含的[地址]部分值。
			data.put("shipToAddr1", values[1]); //第二行：地址1。
			data.put("shipToAddr2", values.length > 3 ? (values[2] + " " + left) : left); //大于3行，地址2=第2行+第4行地址部分；否则，地址2=第4行地址部分。
			String right = index >= 0 ? str.substring(index + 2).trim() : str.trim(); //取倒数第一行中包含的[城市 邮编]。
			int index2 = right.lastIndexOf(" ");
			//如果不包含空格，视为只有一个值；否则，才视为[城市 邮编]。
			if(index2 < 1) {
				//如果是数字，视为邮编；否则，视为城市。而另一个字段，给空字符串即可。
				data.put(StringUtils.isNumeric(right) ? "shipPC" : "shipToAddr3", right);
				data.put(StringUtils.isNumeric(right) ? "shipToAddr3" : "shipPC", "");
			} else {
				data.put("shipPC", right.substring(index2 + 1)); //邮编。
				data.put("shipToAddr3", right.substring(0, index2)); //城市。
			}
		} else if("IT".equals(platform)) {
			/**
			 *[0] 	Amazon Italia Logistica S.r.l		--- 公司名
			 *[1] 	Strada Dogana Po, 2U				--- 地址1
			 *[2] 	Castel San Giovanni (PC), 29015		--- 城市名 (省份), 邮编
			 */
			data.put("shipToName", values[0]); //公司名称。
			data.put("shipToAddr1", values[1]); //第二行：地址1。
			int index = values[2].lastIndexOf(",");
			data.put("shipPC", values[2].substring(index + 1).trim()); //邮编。
			String str = values[2].substring(0, index).trim();
			int index2 = str.lastIndexOf(" (");
			data.put("shipToAddr3", str.substring(0, index2)); //城市。
			data.put("shipToCountry", str.substring(index2 + 2, str.length() - 1)); //省份。
		} else if("FR".equals(platform)) {
			/**
			 *[0]	Amazon.fr logistique SAS	---	公司名
			 *[1]	1, Rue Amazon				--- 地址1
			 *[2]	59353 LAUWIN PLANQUE CEDEX	--- 邮编 地址2 地址3   // 地址2=LAUWIN PLANQUE CEDEX 	地址3=CEDEX
			 *[3]	France						--- 国家
			 */
			data.put("shipToName", values[0]); //公司名称。
			data.put("shipToAddr1", values[1]); //第二行：地址1。
			int index = values[2].indexOf(" ");
			data.put("shipPC", values[2].substring(0, index)); //邮编。
			String addr2 = values[2].substring(index + 1).trim();
			data.put("shipToAddr2", addr2);
			data.put("shipToAddr3", addr2.substring(addr2.lastIndexOf(" ") + 1)); //城市。
			data.put("shipToCountry", values[values.length - 1]); //最后一行：省/州
		} else if("ignore".equalsIgnoreCase(platform)) {
			logger.info("[splitAddress(...)] - 地址已经过手动拆分。（po=" + data.get("po") + "）" );
		} else {
			throw new RuntimeException("不支持基于当前平台[platform=" + platform + "]的地址解析。");
		}
	}
	
	/*public static void main(String[] args) {
		AmazonVcImportService service = new AmazonVcImportService();
		Map<String, Object> data = new java.util.HashMap<String, Object>();
		StringBuilder value = new StringBuilder();
		value.append("Amazon.fr logistique SAS" + "\n");
		value.append("1, Rue Amazon" + "\n");
		value.append("59353 LAUWIN PLANQUE CEDEX" + "\n");
		value.append("France");
		data.put("shipToAddr1", value.toString());
		data.put("platform", "FR");
		service.splitAddress(data);
		for (java.util.Map.Entry<String, Object> entry : data.entrySet()) {
			System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue() + ";");
		}
	}*/
}
