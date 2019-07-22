package com.it.ocs.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.sys.service.IPermissionService;
import com.it.ocs.sys.vo.PermissionVO;
@Controller
@RequestMapping(value = "/permission")
public class PermissionController {
	@Autowired
	private IPermissionService permissionService;
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
    public List<PermissionVO> list() {
        return permissionService.query();
    }
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult savePermission(PermissionVO param) {
		return permissionService.savePermission(param);
	}
	@RequestMapping(value = "/remove",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult removePermission(Long id) {
		return permissionService.removePermission(id);
	}
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public String index() {
		return "admin/sys/permission";
	}
	@RequestMapping(value = "/menu",method = RequestMethod.POST)
	@ResponseBody
	public List<PermissionVO> menu(Long projectId){
		return permissionService.menu(projectId);
	}
	@RequestMapping(value = "/queryAuthorize")
	@ResponseBody
	public List<PermissionVO> queryAuthorize(Long roleId) {
		return permissionService.queryAuthorize(roleId);
	}
	@RequestMapping(value = "/exist", method = RequestMethod.POST)
	@ResponseBody
	public boolean valPermissionExist(PermissionVO param) {
		return permissionService.existByParam(param);
	}
	
}
