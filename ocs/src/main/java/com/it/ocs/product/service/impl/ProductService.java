package com.it.ocs.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.product.dao.IProductDao;
import com.it.ocs.product.model.ProductModel;
import com.it.ocs.product.service.IProductService;
import com.it.ocs.product.vo.ProductVo;

@Service
@Transactional
public class ProductService  extends BaseService implements IProductService{

	@Autowired
	private IProductDao productDao;

	@Override
	public ResponseResult<ProductVo> findAll(RequestParam param) {
		ResponseResult<ProductVo> result = new ResponseResult<>();
		ProductVo product = BeanConvertUtil.mapToObject(param.getParam(),ProductVo.class);
		List<ProductModel> products = productDao.queryByPage(product, param.getStartRow(), param.getEndRow());
		List<ProductVo> vos = CollectionUtil.beansConvert(products, ProductVo.class);
		int total = productDao.count(product);
		result.setRows(vos);
		result.setTotal(total);
		return result;
	}

	@Override
	public OperationResult saveEdit(ProductVo product) {
		OperationResult result = new OperationResult();
		try {
			if(product.getId() == null) {
				insertInit(product);
				productDao.add(product);
			} else {
				updateInit(product);
				productDao.update(product);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("操作失败");
			throw new RuntimeException();
		}
		return result;
	}
	
	@Override
	public OperationResult remove(String ids) {
		OperationResult result = new OperationResult();
		try {
			String[] split = ids.split(",");
			for (String id : split) {
				ProductModel product = productDao.getById(new Long(id));
				product.setEnabledFlag("N");
				updateInit(product);
				productDao.update(product);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("删除失败");
			throw new RuntimeException();
		}
		return result;
	}
}
