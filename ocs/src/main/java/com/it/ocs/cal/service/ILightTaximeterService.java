package com.it.ocs.cal.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.cal.excel.vo.LECalculateExcelVO;
import com.it.ocs.cal.model.LightTaximeterModel;
import com.it.ocs.cal.vo.LightTaximeterVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface ILightTaximeterService {

	ResponseResult<LightTaximeterVo> findAll(RequestParam param);

	OperationResult refresh();

	LightTaximeterVo editLightTaximeter(LightTaximeterVo lightTaximeter);

	List<String> getCountrys(LightTaximeterModel model);

	OperationResult recover();

	public void reckonExport(HttpServletRequest request, HttpServletResponse response);

	public void exportExcel(HttpServletRequest request, HttpServletResponse response, String template);

	public OperationResult priceCalculate(MultipartFile file);

	public OperationResult profitRatCalculate(MultipartFile file);

	public OperationResult priceVariantCalculate(MultipartFile file);

	List<Map<String, String>> findAllSku();

	Double calculateFinalPrice(LECalculateExcelVO vo);

	Double calculateProfitRateAction(LECalculateExcelVO vo);

	Double changeFinalPrice(LECalculateExcelVO vo);

	List<Double> calculateAllFinalPrice(LECalculateExcelVO vo);

	OperationResult customerPrice(MultipartFile file); 

	OperationResult customerProfitRate(MultipartFile file);

	List<String> lePricePlanTest(LECalculateExcelVO vo);

	List<Map<String, Object>> fobexwReverse(Map<String, Object> map);

}
