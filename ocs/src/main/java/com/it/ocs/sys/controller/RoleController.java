package com.it.ocs.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.util.UserUtils;
import com.it.ocs.sys.model.RoleModel;
import com.it.ocs.sys.service.IRoleService;
import com.it.ocs.sys.vo.RoleVO;

@Controller
@RequestMapping(value = "/role")
public class RoleController {
	@Autowired
	private IRoleService roleService;
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
    public ResponseResult<RoleVO> list(RequestParam param) {
		ResponseResult<RoleVO> rolePageResult = roleService.queryRole(param);
        return rolePageResult;
    }
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult saveRole(RoleVO param) {
		return roleService.saveRole(param);
	}
	@RequestMapping(value = "/remove",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult removeRole(Long id) {
		return roleService.removeRole(id);
	}
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public String index() {
		return "admin/sys/role";
	}
	@RequestMapping(value = "/findByDepartmentId", method = RequestMethod.POST)
	@ResponseBody
    public List<RoleVO> find(Long departmentId,Long userId) {
        return roleService.findByDepartmentId(departmentId,userId);
    }
	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult authorize(@RequestBody RoleVO param) {
		return roleService.authorize(param);
	}
	@RequestMapping(value = "/exist", method = RequestMethod.POST)
	@ResponseBody
	public boolean valRoleExist(RoleVO param) {
		return roleService.existByParam(param);
	}
	@RequestMapping(value = "/queryByUserId", method = RequestMethod.POST)
	@ResponseBody
	public List<RoleModel> queryByUserId() {
		return roleService.queryByUserId(UserUtils.getUserId());
	}
}
