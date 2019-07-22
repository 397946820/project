package com.it.ocs.common;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.it.ocs.sys.model.ProjectInfoModel;
import com.it.ocs.sys.service.IProjectInfoService;
import com.it.ocs.sys.vo.ProjectInfoVO;
@Controller
public class MainController {
	@Autowired
	private IProjectInfoService projectService;
	@RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
		ModelAndView result = null;
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser.getSession().getAttribute("user")) {
			List<ProjectInfoVO> projectInfos = projectService.findUserProject();
			result = new ModelAndView("admin/main/index");
			result.addObject("projects", projectInfos);
		} else {
			result = new ModelAndView("redirect:/login.jsp");
		}
		return result;
		
    }
}
