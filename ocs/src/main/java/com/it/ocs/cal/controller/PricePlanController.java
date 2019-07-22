package com.it.ocs.cal.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.cal.model.PricePlanModel;
import com.it.ocs.cal.service.IPricePlanService;
import com.it.ocs.cal.vo.PricePlanVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.controller.IBaseController;
import com.it.ocs.common.util.UserUtils;
import com.it.ocs.publication.vo.ComboBoxVO;
import com.it.ocs.sys.core.SystemLog;
import com.it.ocs.sys.vo.PermissionVO;

@Controller
@RequestMapping("/pricePlan")
public class PricePlanController extends IBaseController {

	@Autowired
	private IPricePlanService pricePlanSercie;

	// 跳转到折扣主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/taximeter/pricePlan";
	}

	@RequestMapping("/toTestList")
	public String skipTestList() {
		return "admin/taximeter/pricePlanTest";
	}

	@RequestMapping("/findAll/{status}")
	@ResponseBody
	public ResponseResult<PricePlanVo> findAll(RequestParam param, @PathVariable("status") String status) {
		return pricePlanSercie.findAll(param, getPermissionVO("JTJJQ_SJGLX_GJ"), "", status);
	}

	@RequestMapping("/getPermission")
	@ResponseBody
	public PermissionVO getPermission() {
		return getPermissionVO("JTJJQ_SJGLX_GJ");
	}

	@RequestMapping("/refresh")
	@ResponseBody
	@SystemLog("价格计划刷新")
	public OperationResult refresh(String status) {
		return pricePlanSercie.refresh(isSYSorCWRY(UserUtils.getUserId()),status);
	}

	@RequestMapping("/editPricePlan")
	@ResponseBody
	@SystemLog("修改价格计划的行数据")
	public PricePlanModel editPricePlan(PricePlanVo pricePlan) {
		return pricePlanSercie.editPricePlan(pricePlan);
	}

	@RequestMapping("/recover")
	@ResponseBody
	@SystemLog("恢复可用利润率")
	public OperationResult recover(String status) {
		return pricePlanSercie.recover(isSYSorCWRY(UserUtils.getUserId()),status);
	}

	// 导出
	@RequestMapping(value = "/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@org.springframework.web.bind.annotation.RequestParam("template") String template,
			@org.springframework.web.bind.annotation.RequestParam("status") String status) {
		pricePlanSercie.exportExcel(request, response, template, getColumns(UserUtils.getUserId(), "JGJHGL_"),
				getPermission(), status);
	}

	// 导出
	@RequestMapping("/reckonExport")
	public void reckonExport(HttpServletRequest request, HttpServletResponse response, String string,String status) {
		pricePlanSercie.reckonExport(request, response, string,status);
	}

	// 导入
	@RequestMapping("/uploadExcel")
	@ResponseBody
	@SystemLog("价格推算")
	public OperationResult reckonExcel(
			@org.springframework.web.bind.annotation.RequestParam("myfile") MultipartFile file, String string,
			String status) throws Exception {
		return pricePlanSercie.uploadExcel(file, string, getPermissionVO("JTJJQ_SJGLX_GJ"),status);
	}

	// 导入
	@RequestMapping("/uploadGetCount")
	@ResponseBody
	@SystemLog("获取回本个数")
	public OperationResult uploadGetCount(
			@org.springframework.web.bind.annotation.RequestParam("myfile") MultipartFile file, String status)
			throws Exception {
		return pricePlanSercie.uploadGetCount(file, getPermissionVO("JTJJQ_SJGLX_GJ"),status);
	}

	@RequestMapping("/isCPB")
	@ResponseBody
	public Boolean isCPB() {
		return pricePlanSercie.isCPB();
	}

	@RequestMapping("/findAllSku")
	@ResponseBody
	public List<Map<String, String>> findAllSku(String status) {
		return pricePlanSercie.findAllSku(status);
	}

	@RequestMapping("/lePricePlanTest")
	@ResponseBody
	public String lePricePlanTest(PricePlanModel model) {
		return pricePlanSercie.lePricePlanTest(model);
	}
	
	@RequestMapping("/getProductField")
	@ResponseBody
	public List<ComboBoxVO> getProductField() {
		return pricePlanSercie.getProductField();
	}
}
