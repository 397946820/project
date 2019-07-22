package com.it.ocs.compare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.compare.service.IImportCompareDataService;

@Controller
@RequestMapping(value = "/importCompareController")
public class ImportCompareController {
	@Autowired
	private IImportCompareDataService importService;
	@RequestMapping("/uploadAmazonExcel")
	@ResponseBody
	public void uploadAmazonExcel(@org.springframework.web.bind.annotation.RequestParam("myfile") MultipartFile file) {
		importService.importAmazonCompareData(file);
	}
}
