package com.it.ocs.cal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.cal.model.SmallTaximeterModel;
import com.it.ocs.cal.service.ISmallTaximeterService;
import com.it.ocs.cal.vo.SmallTaximeterVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.core.SystemLog;

/**
 * 
 * @author yecaiqing
 *
 */
@Controller
@RequestMapping("/smallTaximeter")
public class SmallTaximeterController {

	@Autowired
	private ISmallTaximeterService smallTaximeterService;

	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/taximeter/smallTaximeter";
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<SmallTaximeterVo> findAll(RequestParam param) {
		return smallTaximeterService.findAll(param);
	}

	@RequestMapping("/editSmallTaximeter")
	@SystemLog("小包计价器推算")
	@ResponseBody
	public OperationResult editSmallTaximeter(SmallTaximeterModel model) {
		return smallTaximeterService.editSmallTaximeter(model);
	}

	@RequestMapping("/refresh")
	@SystemLog("小包计价器刷新")
	@ResponseBody
	public OperationResult refresh() {
		return smallTaximeterService.refresh();
	}
}
