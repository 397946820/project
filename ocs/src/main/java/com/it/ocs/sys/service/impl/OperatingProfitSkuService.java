package com.it.ocs.sys.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
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
import com.it.ocs.sys.dao.IOperatingProfitSkuDao;
import com.it.ocs.sys.model.OperatingProfitSkuModel;
import com.it.ocs.sys.service.IOperatingProfitSkuService;
import com.it.ocs.sys.vo.OperatingProfitSkuVo;

@Service
@Transactional
public class OperatingProfitSkuService implements IOperatingProfitSkuService {

	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private IOperatingProfitSkuDao operatingProfitSkuDao;

	@Override
	public ResponseResult<OperatingProfitSkuVo> findAll(RequestParam param) {
		ResponseResult<OperatingProfitSkuVo> result = new ResponseResult<>();
		OperatingProfitSkuVo operatingProfitSku = BeanConvertUtil.mapToObject(param.getParam(),
				OperatingProfitSkuVo.class);
		PageHelper.startPage(param.getPage(), param.getRows());
		List<OperatingProfitSkuModel> list = operatingProfitSkuDao.query(operatingProfitSku,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());
		PageInfo<OperatingProfitSkuModel> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), OperatingProfitSkuVo.class));
		result.setTotal((int)pageInfo.getTotal());
		return result;
	}

	@Override
	public OperationResult saveEdit(OperatingProfitSkuVo operatingProfitSku) {
		OperationResult result = new OperationResult();
		try {
			operatingProfitSku.setUpdatedAt(new Date());
			operatingProfitSkuDao.update(operatingProfitSku);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

}
