package com.it.ocs.cal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.it.ocs.cal.model.ProductEntityModel;
import com.it.ocs.cal.model.ProductUserModel;
import com.it.ocs.cal.service.IProductRelationService;
import com.it.ocs.cal.vo.ProductUserVO;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;

@Service
public class ProductRelationService extends BaseService implements IProductRelationService {

	@Override
	public ResponseResult<ProductUserVO> queryParam(RequestParam param) {
		ResponseResult<ProductUserVO> result = new ResponseResult<ProductUserVO>();
		ProductEntityModel paramModel = null;
		if (!isAdministrator()) {
			paramModel = new ProductEntityModel();
			paramModel.setUserId(getCurentUser().getId());
		}
		List<ProductEntityModel> models = relationDAO.queryByPage(paramModel, param.getStartRow(), param.getEndRow(),null,null);
		List<ProductUserVO> vos = CollectionUtil.beansConvert(models, ProductUserVO.class);
		int count = relationDAO.count(paramModel);
		if (param.getParam() != null && param.getParam().containsKey("userId")) {
			Long userId = Long.parseLong(param.getParam().get("userId").toString());
			List<ProductUserModel> userModels = relationDAO.queryByUserId(userId);
			CollectionUtil.each(vos, new IAction<ProductUserVO>() {
				@Override
				public void excute(ProductUserVO productUser) {
					ProductUserModel searchModel = CollectionUtil.search(userModels,
							new IFunction<ProductUserModel, Boolean>() {
								@Override
								public Boolean excute(ProductUserModel userModel) {
									return userModel.getProductId() == productUser.getEntityId()
											|| userModel.getProductId().equals(productUser.getEntityId());
								}
							});
					if (null != searchModel) {
						productUser.setChecked(true);
					}
				}
			});
		}
		result.setRows(vos);
		result.setTotal(count);
		return result;
	}

	@Override
	public OperationResult castSku(ProductUserVO param) {
		OperationResult result = new OperationResult();
		String[] skuIds = param.getProductIds().split(",");
		List<ProductUserModel> puExistModels = relationDAO.queryByUserId(param.getUserId());
		List<ProductUserModel> puModels = Lists.newArrayList();
		CollectionUtil.each(skuIds, new IAction<String>() {
			@Override
			public void excute(String skuId) {
				ProductUserModel puSearchModel = CollectionUtil.search(puExistModels,
						new IFunction<ProductUserModel, Boolean>() {
							@Override
							public Boolean excute(ProductUserModel pum) {
								return pum.getProductId() == Long.parseLong(skuId)
										|| pum.getProductId().equals(Long.parseLong(skuId));
							}
						});
				if (null == puSearchModel) {
					ProductUserModel model = new ProductUserModel(param.getUserId(), Long.parseLong(skuId));
					puModels.add(model);
				}
			}
		});
		if (!CollectionUtil.isNullOrEmpty(puModels)) {
			relationDAO.batchAdd(puModels);
			result.setDescription("SKU分配成功");
		} else {
			result.setDescription("SKU分配失败");
		}
		return result;
	}

	@Override
	public ResponseResult<ProductUserVO> queryExistByParam(RequestParam param) {
		ResponseResult<ProductUserVO> result = new ResponseResult<ProductUserVO>();
		if (param.getParam() != null && param.getParam().containsKey("userId")) {
			ProductEntityModel paramModel = new ProductEntityModel();
			Long userId = Long.parseLong(param.getParam().get("userId").toString());
			paramModel.setUserId(userId);
			List<ProductEntityModel> models = relationDAO.queryByPage(paramModel, param.getStartRow(), param.getEndRow(), null, null);
			List<ProductUserVO> vos = CollectionUtil.beansConvert(models, ProductUserVO.class);
			int count = relationDAO.count(paramModel);
			result.setRows(vos);
			result.setTotal(count);
		}
		return result;
	}

	@Override
	public OperationResult cancelSku(ProductUserVO param) {
		String[] skuIds = param.getProductIds().split(",");
		List<Long> productIds = new ArrayList<Long>();
		CollectionUtil.each(skuIds, new IAction<String>() {
			@Override
			public void excute(String skuId) {
				productIds.add(Long.parseLong(skuId));
			}
		});
		return null;
	}

}
