package com.it.ocs.cal.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.cal.model.PricePlanModel;
import com.it.ocs.cal.vo.PricePlanVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.publication.vo.ComboBoxVO;
import com.it.ocs.sys.vo.PermissionVO;

public interface IPricePlanService {

	/**
	 * 搜索
	 * @param param
	 * @param permissionVO 
	 * @param string 
	 * @param status 
	 * @return
	 */
	ResponseResult<PricePlanVo> findAll(RequestParam param, PermissionVO permissionVO, String string, String status);

	/**
	 * 刷新价格计划
	 * @param flag 
	 * @param status 
	 * @return
	 */
	OperationResult refresh(Boolean flag, String status);

	/**
	 * 同步数据
	 * @return
	 */
	OperationResult allRefresh();
	
	/**
	 * 修改
	 * @param pricePlan
	 * @return
	 */
	PricePlanModel editPricePlan(PricePlanVo pricePlan);

	/**
	 * 恢复可用利润率
	 * @param flag 
	 * @param status 
	 * @return
	 */
	OperationResult recover(Boolean flag, String status);

	/**
	 * 导出
	 * @param request
	 * @param response
	 * @param template
	 * @param list 
	 * @param permissionVO 
	 * @param status 
	 */
	void exportExcel(HttpServletRequest request, HttpServletResponse response, String template, List<String> list, PermissionVO permissionVO, String status);

	/**
	 * 导入
	 * @param file
	 * @param permissionVO 
	 * @param status 
	 * @return
	 */
	OperationResult uploadExcel(MultipartFile file,String string, PermissionVO permissionVO, String status);

	void reckonExport(HttpServletRequest request, HttpServletResponse response, String string, String status);

	OperationResult uploadGetCount(MultipartFile file, PermissionVO permissionVO, String status);

	Boolean isCPB();

	List<Map<String, String>> findAllSku(String status);

	String lePricePlanTest(PricePlanModel model);

	public List<ComboBoxVO> getProductField();


}
