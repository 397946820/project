package com.it.ocs.compare.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.compare.service.ICompareService;
import com.it.ocs.compare.service.inner.IAmazonCompareService;
import com.it.ocs.compare.service.inner.IEbayCompareService;
import com.it.ocs.compare.service.inner.ILightCompareService;
import com.it.ocs.compare.vo.DataCompareVO;
import com.it.ocs.excel.utils.ExcelUtils;

@Service
public class CompareService extends BaseService implements ICompareService {
	@Autowired
	private ILightCompareService lightCompareService;
	@Autowired
	private IEbayCompareService ebayCompareService;
	@Autowired
	private IAmazonCompareService amazonCompareService;
	public Map<String, SXSSFWorkbook> wbMap = new HashMap<>();

	@Override
	public void exportCompareResult(DataCompareVO param, HttpServletRequest request, HttpServletResponse response) {
		SXSSFWorkbook swb = wbMap.get("import_" + getCurentUser().getId());
		String fileName = null;
		if ("ebay".equals(param.getPlatForm())) {
			fileName = "EBAY-" + param.getAccount() + "-" + param.getSite() + "报表数据核对结果.xlsx";
		} else if ("light".equals(param.getPlatForm())) {
			fileName = "官网" + param.getSite() + "报表数据核对结果.xlsx";
		}else if("amazon".equals(param.getPlatForm())){
			fileName = "Amazon" + param.getAccount() + "报表数据核对结果.xlsx";
		}
		ExcelUtils.outputExcel(response, swb, ExcelUtils.formatDownloadName(fileName, request));
	}

	@Override
	public List<Map<String, String>> queryAccountByPF(String pf) {
		List<String> accounts = new ArrayList<>();
		List<Map<String, String>> result = new ArrayList<>();
		if ("amazon".equals(pf)) {
			accounts = ebayCompareDAO.queryAmazonAccount();
		} else if ("ebay".equals(pf)) {
			accounts = ebayCompareDAO.queryEbayAccount();
		} else if ("light".equals(pf)) {
			accounts = ebayCompareDAO.queryLightAccount();
		}
		CollectionUtil.each(accounts, new IAction<String>() {
			@Override
			public void excute(String obj) {
				Map<String, String> map = new HashMap<>();
				map.put("text", obj);
				result.add(map);
			}
		});
		return result;
	}

	@Override
	public OperationResult dataCompare(DataCompareVO param, HttpServletRequest request, HttpServletResponse response) {
		OperationResult result = new OperationResult();
		if (StringUtils.isBlank(param.getPlatForm())) {
			throw new RuntimeException("平台为空!");
		}
		String platForm =param.getPlatForm();
		SXSSFWorkbook swb = null;
		if ("ebay".equals(platForm)) {
			swb = ebayCompareService.ebayDataCompare(param, request, response);
		} else if ("light".equals(platForm)) {
			swb = lightCompareService.lightDataCompare(param, request, response);
		}else if("amazon".equals(platForm)){
			swb  = amazonCompareService.amazonDataCompare(param, request, response);
		}
		if (null != swb) {
			wbMap.put("import_" + getCurentUser().getId(), swb);
		}
		result.setDescription("数据对比成功!");
		return result;
	}

}
