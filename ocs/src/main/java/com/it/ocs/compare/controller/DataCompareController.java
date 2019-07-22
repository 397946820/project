package com.it.ocs.compare.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.compare.service.ICompareService;
import com.it.ocs.compare.vo.DataCompareVO;

@Controller
@RequestMapping(value = "/dataCompareController")
public class DataCompareController {
	@Autowired
	private ICompareService compareService;

	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/compare/compare";
	}

	@RequestMapping("/data_uploadExcel")
	@ResponseBody
	public OperationResult lightUploadExcel(DataCompareVO param, HttpServletRequest request,
			HttpServletResponse response) {
		return compareService.dataCompare(param, request, response);
	}

	@RequestMapping("/ebay_exportExcel")
	@ResponseBody
	public void ebayExportExcel(DataCompareVO param, HttpServletRequest request,
			HttpServletResponse response) {
		compareService.exportCompareResult(param,request, response);
	}

	@RequestMapping("/query_account")
	@ResponseBody
	public List<Map<String, String>> queryAccountByPF(String pf) {
		return compareService.queryAccountByPF(pf);
	}
}
