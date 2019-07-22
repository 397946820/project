package com.it.ocs.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.service.IOperatingProfitViewService;
import com.it.ocs.sys.vo.OperatingProfitViewVo;

@Controller
@RequestMapping("/operatingProfitView")
public class OperatingProfitViewController {

	@Autowired
	private IOperatingProfitViewService operatingProfitViewService;

	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/sys/operatingProfitView";
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<OperatingProfitViewVo> findAll(RequestParam param) {
		return operatingProfitViewService.findAll(param);
	}

	@RequestMapping("/refreshBefore")
	@ResponseBody
	public Integer refreshBefore() {
		return operatingProfitViewService.refreshBefore();
	}

	@RequestMapping("/refresh")
	@ResponseBody
	public OperationResult refresh() {
		return operatingProfitViewService.refresh();
	}

	@RequestMapping("/sure")
	@ResponseBody
	public OperationResult sure() {
		return operatingProfitViewService.sure();
	}

	@RequestMapping("/exportBefore")
	@ResponseBody
	public OperationResult exportBefore(@RequestBody Map<String, Object> map) {
		return operatingProfitViewService.exportBefore(map);
	}

	@RequestMapping("/generateDataBefore")
	@ResponseBody
	public OperationResult generateDataBefore() {
		return operatingProfitViewService.generateDataBefore();
	}

	@RequestMapping("/generateData")
	@ResponseBody
	public OperationResult generateData() {
		return operatingProfitViewService.generateData();
	}

	@RequestMapping("/mappingSku")
	@ResponseBody
	public OperationResult mappingSku() {
		return operatingProfitViewService.mappingSku();
	}

	@RequestMapping("/categorySku")
	@ResponseBody
	public OperationResult categorySku() {
		return operatingProfitViewService.categorySku();
	}
	
	@RequestMapping("/syncCategory")
	@ResponseBody
	public OperationResult syncCategory() {
		return operatingProfitViewService.syncCategory();
	}
	
	@RequestMapping("/findAmazonFee")
	@ResponseBody
	public Map<String, Object> findAmazonFee(RequestParam param) {
		return operatingProfitViewService.findAmazonFee(param);
	}

	@RequestMapping("/getCategory")
	@ResponseBody
	public List<Map<String, Object>> getCategory() {
		return operatingProfitViewService.getCategory();
	}

	@RequestMapping("/getSite")
	@ResponseBody
	public List<Map<String, Object>> getSite() {
		return operatingProfitViewService.getSite();
	}

	@RequestMapping("/getSkuByCategory")
	@ResponseBody
	public List<Map<String, Object>> getSkuByCategory(@RequestBody Map<String, Object> map) {
		return operatingProfitViewService.getSkuByCategory(map);
	}

	@RequestMapping("/getPersonnelByDepartment")
	@ResponseBody
	public List<Map<String, Object>> getPersonnelByDepartment(@RequestBody Map<String, Object> map) {
		return operatingProfitViewService.getPersonnelByDepartment(map);
	}
}
