package com.it.ocs.cal.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.cal.service.IProductSaleReportService;
import com.it.ocs.cal.vo.SaleCatCountReportVO;
import com.it.ocs.cal.vo.SaleCountReportVO;
import com.it.ocs.cal.vo.SaleDisCountReportVO;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

@Controller
@RequestMapping("/productSaleReport")
public class ProductSaleReportController {

	@Autowired
	private IProductSaleReportService productSaleReportService;

	@RequestMapping("/show")
	public String show() {
		return "admin/salesStatistics/skuSaleCountReport";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public List<SaleCountReportVO> findSaleCountReportList(RequestParam param) {
		return productSaleReportService.findSaleCountReportList(param);
	}

	@RequestMapping("/show_dis")
	public String show_dis() {
		return "admin/salesStatistics/skuDisCountReport";
	}

	@RequestMapping(value = "/list_dis")
	@ResponseBody
	public ResponseResult<SaleDisCountReportVO> findDisSaleCountReportList(RequestParam param) throws ParseException {
		return productSaleReportService.findSaleDisCountReportList(param);
	}

	@RequestMapping("/show_cat")
	public String show_cat() {
		return "admin/salesStatistics/skuCatCountReport";
	}

	@RequestMapping(value = "/list_cat")
	@ResponseBody
	public ResponseResult<SaleCatCountReportVO> findCatSaleCountReportList(RequestParam param) throws ParseException {
		return productSaleReportService.findSaleCatCountReportList(param);
	}

	@RequestMapping(value = "/findProductTypes")
	@ResponseBody
	public List<SaleCatCountReportVO> findProductTypes() {
		return productSaleReportService.findProductTypes();
	}

	@RequestMapping(value = "/querySiteByAccount")
	@ResponseBody
	public List<SaleCatCountReportVO> querySiteByAccount(String account) {
		return productSaleReportService.querySiteByAccount(account);
	}

}
