package com.it.ocs.compare.service.inner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.it.ocs.compare.vo.DataCompareVO;

public interface IAmazonCompareService {
	public SXSSFWorkbook amazonDataCompare(DataCompareVO param,HttpServletRequest request,
			HttpServletResponse response);
}
