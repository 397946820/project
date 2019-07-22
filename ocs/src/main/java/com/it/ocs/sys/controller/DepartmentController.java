package com.it.ocs.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.service.IDepartmentService;
import com.it.ocs.sys.vo.DepartmentVO;

@Controller
@RequestMapping(value = "/department")
public class DepartmentController {
	@Autowired
	private IDepartmentService departmentService;
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
    public ResponseResult<DepartmentVO> list(RequestParam param) {
		ResponseResult<DepartmentVO> departmentPageResult = departmentService.queryDepartment(param);
        return departmentPageResult;
    }
	@RequestMapping(value = "/findAll", method = RequestMethod.POST)
	@ResponseBody
    public List<DepartmentVO> findAll() {
		List<DepartmentVO> result = departmentService.findAll();
        return result;
    }
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult saveDepartment(DepartmentVO param) {
		return departmentService.saveDepartment(param);
	}
	@RequestMapping(value = "/remove",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult removeDepartment(Long id) {
		return departmentService.removeDepartment(id);
	}
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public String index() {
		return "admin/sys/department";
	}
	@RequestMapping(value = "/exist", method = RequestMethod.POST)
	@ResponseBody
	public boolean valDepartExist(DepartmentVO param) {
		return departmentService.existByParam(param);
	}
}
