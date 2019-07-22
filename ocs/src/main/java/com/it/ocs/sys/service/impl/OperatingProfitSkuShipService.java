package com.it.ocs.sys.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.enums.Style;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;
import com.it.ocs.sys.dao.IOperatingProfitSkuShipDao;
import com.it.ocs.sys.model.OperatingProfitSkuShipModel;
import com.it.ocs.sys.service.IOperatingProfitSkuShipService;
import com.it.ocs.sys.vo.OperatingProfitSkuShipVo;

@Service
@Transactional
public class OperatingProfitSkuShipService implements IOperatingProfitSkuShipService {

	@Autowired
	private IOperatingProfitSkuShipDao operatingProfitSkuShipDao;

	@Override
	public ResponseResult<OperatingProfitSkuShipVo> findAll(RequestParam param) {
		ResponseResult<OperatingProfitSkuShipVo> result = new ResponseResult<>();
		OperatingProfitSkuShipVo operatingProfitSkuShip = BeanConvertUtil.mapToObject(param.getParam(),
				OperatingProfitSkuShipVo.class);
		PageHelper.startPage(param.getPage(), param.getRows());
		List<OperatingProfitSkuShipModel> list = operatingProfitSkuShipDao.query(operatingProfitSkuShip,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());
		PageInfo<OperatingProfitSkuShipModel> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), OperatingProfitSkuShipVo.class));
		result.setTotal((int) pageInfo.getTotal());
		return result;
	}

	@Override
	public OperationResult saveEdit(OperatingProfitSkuShipVo operatingProfitSkuShip) {
		OperationResult result = new OperationResult();
		try {
			operatingProfitSkuShip.setUpdatedAt(new Date());
			operatingProfitSkuShipDao.update(operatingProfitSkuShip);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

}
