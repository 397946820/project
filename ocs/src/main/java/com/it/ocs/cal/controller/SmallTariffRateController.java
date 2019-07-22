package com.it.ocs.cal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.cal.service.ISmallTariffRateService;
import com.it.ocs.cal.vo.SmallTariffRateVo;
import com.it.ocs.cal.vo.SmallTypeVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

/**
 * 
 * @author yecaiqing
 *
 */
@Controller
@RequestMapping("/smallTariffRate")
public class SmallTariffRateController {

	@Autowired
	private ISmallTariffRateService smallTariffRateService;

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<SmallTariffRateVo> findAll(RequestParam param) {
		return smallTariffRateService.findAll(param);
	}

	@RequestMapping("/saveEdit")
	@ResponseBody
	public OperationResult saveEdit(SmallTariffRateVo smallTariffRate) {
		return smallTariffRateService.saveEdit(smallTariffRate);
	}
}
