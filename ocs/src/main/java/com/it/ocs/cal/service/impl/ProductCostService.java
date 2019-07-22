package com.it.ocs.cal.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.cache.CurrencyRateCache;
import com.it.ocs.cal.dao.IProductCostDao;
import com.it.ocs.cal.dao.IProductEntityDao;
import com.it.ocs.cal.model.ProductCostModel;
import com.it.ocs.cal.service.IProductCostService;
import com.it.ocs.cal.utils.ExportExcelUtil;
import com.it.ocs.cal.utils.ImportExcelUtil;
import com.it.ocs.cal.utils.Tools;
import com.it.ocs.cal.utils.Utils;
import com.it.ocs.cal.vo.ProductCostVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.UserUtils;

@Service
@Transactional
public class ProductCostService implements IProductCostService {

	@Autowired
	private IProductCostDao productCostDao;
	
	@Autowired
	private IProductEntityDao productEntityDao;
	
	private DecimalFormat df = new DecimalFormat("0.0000");

	@Override
	public ResponseResult<ProductCostVo> findAll(RequestParam param, Boolean flag) {
		ResponseResult<ProductCostVo> result = new ResponseResult<>();
		
		param = Tools.getRequestParam(param);
		
		ProductCostVo productCost = BeanConvertUtil.mapToObject(param.getParam(), ProductCostVo.class);
		
		if(!flag) {
			if(productCost == null) {
				productCost = new ProductCostVo();
			}
			productCost.setUserId(UserUtils.getUserId());
		}
		
		List<ProductCostModel> list = productCostDao.queryByPage(productCost, param.getStartRow(), param.getEndRow(), param.getSort(), param.getOrder());
		
		int total = productCostDao.count(productCost);
		
		List<ProductCostVo> rows = CollectionUtil.beansConvert(list, ProductCostVo.class);
		
		for (ProductCostVo model : rows) {
			/*String currency = model.getCurrency();
			model.setPurchasePrice(Tools.getCurrencySymbol(currency) + " " + model.getPrice());
			if(currency.equals("RMB")) {
				model.setPurchasePriceRMB("￥ " + model.getPrice());
			} else if(currency.endsWith("USD")) {
				model.setPurchasePriceRMB("￥ " + df.format((model.getPrice() * CurrencyRateCache.getCurrencyRate("RMB"))));
			}*/
		}
		
		result.setRows(rows);
		
		result.setTotal(total);
		
		return result;
	}
	
	@Override
	public OperationResult edit(ProductCostVo productCost) {
		OperationResult result = new OperationResult();
		
		try{
			productCost.setUpdatedAt(new Date());
			productCostDao.update(productCost);
		}catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("修改失败");
			throw new RuntimeException();
		}
		
		return result;
	}
	
	@Override
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, String template, Boolean flag, List<String> strings) {
		// 输出的文件名称
		String fileName = "";
		Long userId = UserUtils.getUserIdByFlag(flag);
		List<ProductCostModel> list = productCostDao.findByUserId(userId);
		// 要导出的
		List<ProductCostModel> result = new ArrayList<>();
		if (!CollectionUtil.isNullOrEmpty(list)) {
			if (StringUtils.isNotBlank(template)) {
				// 导出模板
				fileName = "产品成本模板" + Utils.getFileName() + ".xlsx";
				result.add(list.get(0));
			} else {
				// 导出全部
				fileName = "产品成本数据" + Utils.getFileName() + ".xlsx";
				result.addAll(list);
			}
			try {
				ExportExcelUtil.writeExcel(response, request, fileName, result, ProductCostModel.class, template, strings);
			} catch (IllegalArgumentException | IllegalAccessException | IOException e) {
				throw new RuntimeException();
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public OperationResult uploadExcel(MultipartFile file) {
		OperationResult result = new OperationResult();
		String flag = "";
		int f = 0;
		try {
			InputStream inputStream = file.getInputStream();
			List<ProductCostModel> list = ImportExcelUtil.importExcel(ProductCostModel.class, inputStream);
			for (ProductCostModel model : list) {
				String sku = model.getSku();
				//根据sku查找父id
				Long parentId = productEntityDao.findEntityIdBySku(sku);
				if(parentId != null) {
					//判断该sku在表中是否已经有数据
					int count = productCostDao.findCountByParent(parentId);
					if(count == 1) {
						flag = sku;
						f = 1;
						throw new RuntimeException();
					} else {
						model.setParentId(parentId);
						model.setCreatedAt(new Date());
						model.setUpdatedAt(new Date());
						productCostDao.add(model);
					}
				} else {
					flag = sku;
					f = 2;
					throw new Exception();
				}
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			if(f == 0) {
				result.setDescription("操作失败");
			} else if(f== 1) {
				result.setDescription("导入失败," + flag + "  此sku在表中已经存在数据，无法再导入");
			} else if(f== 2) {
				result.setDescription("导入失败," + flag + "  此sku在产品表中不存在");
			}
			throw new RuntimeException();
		}
		return result;
	}
}
