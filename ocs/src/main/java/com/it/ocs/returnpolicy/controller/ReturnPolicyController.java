package com.it.ocs.returnpolicy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.returnpolicy.service.IReturnPolicyService;
import com.it.ocs.returnpolicy.vo.ReturnPolicyVO;
import com.it.ocs.common.RequestParam;

@Controller
@RequestMapping(value = "/returnpolicy")
public class ReturnPolicyController {
	@Autowired
	private IReturnPolicyService returnPolicyService;
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
    public ResponseResult<ReturnPolicyVO> list(RequestParam param) {
		ResponseResult<ReturnPolicyVO> ReturnPolicysPageResult = returnPolicyService.queryReturnPolicy(param);
        return ReturnPolicysPageResult;
    }
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult saveReturnPolicy(ReturnPolicyVO param) {
		return returnPolicyService.saveReturnPolicy(param);
	}
	@RequestMapping(value = "/remove",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult removeReturnPolicy(String ids) {
		return returnPolicyService.removeReturnPolicy(ids);
	}
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public String index() {
		return "admin/returnpolicy/list";
	}
	
}
