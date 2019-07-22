package com.it.ocs.ukinventory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.ukinventory.dao.IInventoryUKDAO;
import com.it.ocs.ukinventory.model.InventoryUKModel;
import com.it.ocs.ukinventory.service.IInventoryService;
import com.it.ocs.ukinventory.vo.InventoryUKVO;
@Service
public class InventoryService implements IInventoryService {
	@Autowired
	private IInventoryUKDAO inventoryUKDAO;
	@Override
	public ResponseResult<InventoryUKVO> queryInventory(RequestParam param) {
		ResponseResult<InventoryUKVO> result = new ResponseResult<>();
		InventoryUKVO paramVO = BeanConvertUtil.mapToObject2(param.getParam(), InventoryUKVO.class);
		List<InventoryUKModel> models = inventoryUKDAO.queryByPage(paramVO, param.getStartRow(), param.getEndRow());
		List<InventoryUKVO> ifvos = CollectionUtil.beansConvert(models, InventoryUKVO.class);
		int count = inventoryUKDAO.count(paramVO);
		result.setRows(ifvos);
		result.setTotal(count);
		return result;
	}

}
