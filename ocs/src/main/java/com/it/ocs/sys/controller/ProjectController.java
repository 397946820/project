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
import com.it.ocs.sys.service.IProjectInfoService;
import com.it.ocs.sys.vo.ProjectInfoVO;
@Controller
@RequestMapping(value = "/project")
public class ProjectController {
	@Autowired
	private IProjectInfoService projectService;
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
    public ResponseResult<ProjectInfoVO> list(RequestParam param) {
		ResponseResult<ProjectInfoVO> projectPageResult = projectService.queryProject(param);
        return projectPageResult;
    }
	@RequestMapping(value = "/findAll", method = RequestMethod.POST)
	@ResponseBody
    public List<ProjectInfoVO> findAll() {
		List<ProjectInfoVO> result = projectService.findAll();
        return result;
    }
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult saveProject(ProjectInfoVO param) {
		return projectService.saveProject(param);
	}
	@RequestMapping(value = "/remove",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult removeProject(Long id) {
		return projectService.removeProject(id);
	}
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public String index() {
		return "admin/sys/project";
	}
	@RequestMapping(value = "/exist", method = RequestMethod.POST)
	@ResponseBody
	public boolean valProjectExist(ProjectInfoVO param) {
		return projectService.existByParam(param);
	}
	
}
