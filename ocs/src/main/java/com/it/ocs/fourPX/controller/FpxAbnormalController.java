package com.it.ocs.fourPX.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.ResponseResult;
import com.it.ocs.fourPX.service.FpxAbnormalService;
import com.it.ocs.fourPX.vo.FpxAbnormalVO;

@Controller
public class FpxAbnormalController {

	@Autowired
	private FpxAbnormalService fpxAbnormalService;

	@RequestMapping(value="/fourpx/abnormal/list")
	@ResponseBody 
	public ResponseResult<FpxAbnormalVO> list(com.it.ocs.common.RequestParam param) {
		return this.fpxAbnormalService.selectByPage(param);
	}
}
