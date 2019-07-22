package com.it.ocs.cal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.cal.service.ITaximeterService;
import com.it.ocs.cal.vo.TaximeterVo;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.controller.IBaseController;
import com.it.ocs.common.util.UserUtils;
import com.it.ocs.sys.model.PermissionModel;
import com.it.ocs.sys.model.RoleModel;
import com.it.ocs.sys.service.IPermissionService;
import com.it.ocs.sys.service.IRoleService;

@Controller
@RequestMapping("/taximeter")
public class TaximeterController extends IBaseController {

	@Autowired
	private ITaximeterService taximeterService;
	
	// 跳转到折扣主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/taximeter/taximeter";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<TaximeterVo> findAll(RequestParam param)  throws Exception {
		
		return taximeterService.findAll(param,isSYSorCWRY(UserUtils.getUserId()),"");
	}
	
	// 导出
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
						@org.springframework.web.bind.annotation.RequestParam("template") String template) throws Exception {
		taximeterService.exportExcel(request, response, template, getColumns(UserUtils.getUserId(),"DTJJQ_"),isSYSorCWRY(UserUtils.getUserId()));
	}
}
