package com.it.ocs.cal.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.cal.vo.ProductEntityVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface IProductEntityService {

	/**
	 * 查找
	 * @param param
	 * @param flag 
	 * @param string 
	 * @return
	 */
	ResponseResult<ProductEntityVo> findAll(RequestParam param, Boolean flag, String string);
	
	/**
	 * 保存和修改
	 * @param entity
	 * @return
	 */
	OperationResult saveEdit(ProductEntityVo entity);

	/**
	 * 导出
	 * @param request
	 * @param response
	 * @param template 导出模板还是全部
	 * @param flag 
	 * @param list 
	 */
	void exportExcel(HttpServletRequest request, HttpServletResponse response, String template, Boolean flag, List<String> list);
	
	/**
	 * 导入
	 * @param file 导入的文件
	 * @param flag 
	 * @return
	 */
	OperationResult uploadExcel (MultipartFile file, Boolean flag);

	/**
	 * 导入产品价格
	 * @param file
	 * @param sySorCWRY
	 * @return
	 */
	OperationResult importProductPrice(MultipartFile file, Boolean sySorCWRY);
	
	/**
	 * sku DIS状态修改
	 * @param sku
	 * @param platform
	 * @return
	 */
	public OperationResult getSkuDisInfo(String sku, String platform);
	
	/**
	 * 保存sku dis状态信息
	 * @param map
	 * @return
	 */
	public OperationResult saveSkuDisInfo(Map<String, Object> map);

	public OperationResult skuExist(String sku);
}
