package com.it.ocs.publication.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.publication.service.inner.IBuyerRequireSelectService;
import com.it.ocs.publication.vo.BuyerRequireVO;

/**
 * 买家要求
 * @author chenyong
 *
 */
@Controller
@RequestMapping("/buyerRequireSelect")
public class BuyerRequireSelectController {
	
	@Autowired
	private IBuyerRequireSelectService buyerRequireSelectService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
    public ResponseResult<BuyerRequireVO> list(RequestParam param) {
		return buyerRequireSelectService.queryDataList(param);
    }
	
	@RequestMapping(value = "/saveAs",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult saveAs(@RequestBody Map<String, Object> map) {
		return buyerRequireSelectService.saveAs(map);
	}
	
	@RequestMapping(value = "/payNameIsExist",method = RequestMethod.GET)
	@ResponseBody
	public OperationResult payNameIsExist(String payName){
		return buyerRequireSelectService.isExist(payName);
	}
}
