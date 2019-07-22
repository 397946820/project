package com.it.ocs.compare.service.inner.impl;

import java.io.InputStream;
import java.text.ParseException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.enums.ExcelVersion;
import com.it.ocs.common.util.TimeConvertUtil;
import com.it.ocs.compare.dao.IEbayOrderCompareDAO;
import com.it.ocs.compare.excel.CompareDataExcelImport;
import com.it.ocs.compare.model.EbayDBModel;
import com.it.ocs.compare.model.EbayExcelModel;
import com.it.ocs.compare.service.impl.support.EbayExcelCompareSupport;
import com.it.ocs.compare.service.inner.IEbayCompareService;
import com.it.ocs.compare.vo.DataCompareVO;

@Service
public class EbayCompareService implements IEbayCompareService {
	@Autowired
	private IEbayOrderCompareDAO ebayCompareDAO;
	@Override
	public SXSSFWorkbook ebayDataCompare(DataCompareVO param, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			InputStream inputStream = param.getMyfile().getInputStream();
			// 导入进来的数据
			List<EbayExcelModel> list = CompareDataExcelImport.convertBean(inputStream, ExcelVersion.OLD,
					EbayExcelModel.class);
			list.sort(new Comparator<EbayExcelModel>() {
				@Override
				public int compare(EbayExcelModel o1, EbayExcelModel o2) {
					if (StringUtils.isBlank(o1.getShippedOnDate()) || StringUtils.isBlank(o2.getShippedOnDate())) {
						return -1;
					}
					return o1.getShippedOnDate().compareTo(o2.getShippedOnDate());
				}
			});
			List<EbayDBModel> dbModels = ebayCompareDAO.queryByParam(getParam(param, list));
			return EbayExcelCompareSupport.compareData(param,list, dbModels, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private Map<String, Object> getParam(DataCompareVO compareParam, List<EbayExcelModel> list) throws ParseException {
		Map<String, Object> param = new HashMap<>();
		String beginTime = getBeginTime(compareParam);
		String endTime = getEndTime(compareParam);
		param.put("startTime", beginTime);
		param.put("endTime", endTime);
		if (StringUtils.isNotBlank(compareParam.getAccount())) {
			param.put("account", compareParam.getAccount());
		}
		if (StringUtils.isNotBlank(compareParam.getSite())) {
			param.put("site", compareParam.getSite());
		}
		return param;
	}

	private String getBeginTime(DataCompareVO compareParam) throws ParseException {
		return TimeConvertUtil.timeConvert("2017-11-01 00:00:00", "Asia/Shanghai", "UTC");
	}
//	private String getTimeZone(String country) {
//		if ("UK".equals(country)) {
//			return "Europe/London";
//		} else if ("US".equals(country)) {
//			return "America/Los_Angeles";
//		} else {
//			return "Europe/Berlin";
//		}
//	}

	private String getEndTime(DataCompareVO compareParam) throws ParseException {
		return TimeConvertUtil.timeConvert("2017-11-30 23:59:59", "Asia/Shanghai", "UTC");
	}

}
