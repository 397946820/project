package com.it.ocs.cal.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.cal.excel.vo.LECalculateExcelVO;
import com.it.ocs.cal.model.LightTaximeterModel;
import com.it.ocs.cal.service.ILightTaximeterService;
import com.it.ocs.cal.vo.LightTaximeterVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.core.SystemLog;

@Controller
@RequestMapping("/lightTaximeter")
public class LightTaximeterController {

	@Autowired
	private ILightTaximeterService lightTaximeterService;

	// 跳转到折扣主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/taximeter/lightTaximeter";
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<LightTaximeterVo> findAll(RequestParam param) {
		return lightTaximeterService.findAll(param);
	}

	@RequestMapping("/refresh")
	@ResponseBody
	@SystemLog("刷新官网计价器")
	public OperationResult refresh() {
		return lightTaximeterService.refresh();
	}

	@RequestMapping("/recover")
	@ResponseBody
	@SystemLog("恢复利润系数")
	public OperationResult recover() {
		return lightTaximeterService.recover();
	}

	@RequestMapping("/editLightTaximeter")
	@ResponseBody
	@SystemLog("修改官网计价器行数据")
	public LightTaximeterVo editLightTaximeter(LightTaximeterVo lightTaximeter) {
		return lightTaximeterService.editLightTaximeter(lightTaximeter);
	}

	@RequestMapping("/getCountrys")
	@ResponseBody
	public List<String> getCountrys(LightTaximeterModel model) {
		return lightTaximeterService.getCountrys(model);
	}

	@RequestMapping("/findAllSku")
	@ResponseBody
	public List<Map<String, String>> findAllSku() {
		return lightTaximeterService.findAllSku();
	}

	@RequestMapping("/calculateFinalPrice")
	@ResponseBody
	public Double calculateFinalPrice(LECalculateExcelVO vo) {
		return lightTaximeterService.calculateFinalPrice(vo);

	}

	@RequestMapping("/calculateProfitRateAction")
	@ResponseBody
	public Double calculateProfitRateAction(LECalculateExcelVO vo) {
		return lightTaximeterService.calculateProfitRateAction(vo);

	}

	@RequestMapping("/changeFinalPrice")
	@ResponseBody
	public Double changeFinalPrice(LECalculateExcelVO vo) {
		return lightTaximeterService.changeFinalPrice(vo);

	}

	@RequestMapping("/changeAllFinalPrice")
	@ResponseBody
	public List<Double> changeAllFinalPrice(LECalculateExcelVO vo) {
		return lightTaximeterService.calculateAllFinalPrice(vo);

	}

	@RequestMapping("/lePricePlanTest")
	@ResponseBody
	public List<String> lePricePlanTest(LECalculateExcelVO vo) {
		return lightTaximeterService.lePricePlanTest(vo);

	}

	// 导出
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@org.springframework.web.bind.annotation.RequestParam("template") String template) {
		lightTaximeterService.exportExcel(request, response, template);
	}

	// 导入价格推算
	@RequestMapping("/priceCal")
	@ResponseBody
	@SystemLog("价格推算")
	public OperationResult priceCal(@org.springframework.web.bind.annotation.RequestParam("myfile") MultipartFile file)
			throws Exception {
		return lightTaximeterService.priceCalculate(file);
	}

	// 导入价格推算
	@RequestMapping("/priceVariantCal")
	@ResponseBody
	@SystemLog("捆绑价格推算")
	public OperationResult priceVariantCal(
			@org.springframework.web.bind.annotation.RequestParam("myfile") MultipartFile file) throws Exception {
		return lightTaximeterService.priceVariantCalculate(file);
	}

	// 导入系数推算
	@RequestMapping("/profitRate")
	@ResponseBody
	@SystemLog("利润系数推算")
	public OperationResult profitRateCal(
			@org.springframework.web.bind.annotation.RequestParam("myfile") MultipartFile file) throws Exception {
		return lightTaximeterService.profitRatCalculate(file);
	}

	@RequestMapping("/customerPrice")
	@ResponseBody
	public OperationResult customerPrice(
			@org.springframework.web.bind.annotation.RequestParam("myfile") MultipartFile file) throws Exception {
		return lightTaximeterService.customerPrice(file);
	}

	@RequestMapping("/customerProfitRate")
	@ResponseBody
	public OperationResult customerProfitRate(
			@org.springframework.web.bind.annotation.RequestParam("myfile") MultipartFile file) throws Exception {
		return lightTaximeterService.customerProfitRate(file);
	}

	// 导出
	@RequestMapping("/reckonExport")
	public void reckonExport(HttpServletRequest request, HttpServletResponse response) {
		lightTaximeterService.reckonExport(request, response);
	}

	@RequestMapping("/fobexwReverse")
	@ResponseBody
	public List<Map<String, Object>> fobexwReverse(@RequestBody Map<String, Object> map) {
		return lightTaximeterService.fobexwReverse(map);
	}

}
