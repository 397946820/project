package com.it.ocs.compare.service.inner.impl;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import com.it.ocs.common.enums.ExcelVersion;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.util.TimeConvertUtil;
import com.it.ocs.compare.excel.CompareDataExcelImport;
import com.it.ocs.compare.model.LightDBModel;
import com.it.ocs.compare.model.LightExcelModel;
import com.it.ocs.compare.service.impl.support.LightExcelCompareSupport;
import com.it.ocs.compare.service.inner.ILightCompareService;
import com.it.ocs.compare.vo.DataCompareVO;
@Service
public class LightCompareService extends BaseService implements ILightCompareService {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
	@Override
	public SXSSFWorkbook lightDataCompare(DataCompareVO param, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			InputStream inputStream = param.getMyfile().getInputStream();
			// 导入进来的数据
			List<LightExcelModel> list = CompareDataExcelImport.convertBean(inputStream, ExcelVersion.NEW,
					LightExcelModel.class);
			list.sort(new Comparator<LightExcelModel>() {
				@Override
				public int compare(LightExcelModel o1, LightExcelModel o2) {
					if (StringUtils.isBlank(o1.getShippedAt())
							|| StringUtils.isBlank(o2.getShippedAt())) {
						return -1;
					}
					return o1.getShippedAt().compareTo(o2.getShippedAt());
				}
			});
			List<LightDBModel> dbModels = lightDAO.queryByParam(getParam(list,param));
			return LightExcelCompareSupport.compareData(list, dbModels, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	private Map<String, Object> getParam(List<LightExcelModel> list,DataCompareVO compareParam) throws ParseException {
		Map<String, Object> param = new HashMap<>();
		String sourceTZ = getTimeZoneByAccount(compareParam.getAccount());
		String beginTime = TimeConvertUtil.timeConvert(getBeginTime(list), sourceTZ, "UTC");
		String endTime =TimeConvertUtil.timeConvert(getEndTime(list), sourceTZ, "UTC");
		param.put("startTime", beginTime);
		param.put("endTime", endTime);
		param.put("account", compareParam.getAccount());
		return param;
	}
	private String getTimeZoneByAccount(String account) {
		if ("DE".equals(account)) {
			return "Europe/Berlin";
		} else if ("UK".equals(account)) {
			return "Europe/London";
		} else {
			return "America/Los_Angeles";
		}
	}
	private String getBeginTime(List<LightExcelModel> excelModels) {
		for (LightExcelModel excelModel : excelModels) {
			if (!StringUtils.isBlank(excelModel.getShippedAt())) {
				String t = excelModel.getShippedAt().replace("T"," ").replace("+", "");
				return t.substring(0,t.indexOf(" "))+ " 00:00:00";
			}
		}
		return null;
	}

	private String getEndTime(List<LightExcelModel> excelModels) {
		for (int i = excelModels.size() - 1; i > 0; i--) {
			if (!StringUtils.isBlank(excelModels.get(i).getShippedAt())) {
				String t = excelModels.get(i).getShippedAt().replace("T"," ").replace("+", "");
				return t.substring(0,t.indexOf(" "))+ " 23:59:59";
			}
		}
		return null;
	}
	
}
