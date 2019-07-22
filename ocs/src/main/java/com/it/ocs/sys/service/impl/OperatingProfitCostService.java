package com.it.ocs.sys.service.impl;

import java.text.SimpleDateFormat;
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
import com.it.ocs.salesStatistics.utils.TimeTools;
import com.it.ocs.sys.dao.IOperatingProfitCostDao;
import com.it.ocs.sys.model.OperatingProfitCostModel;
import com.it.ocs.sys.service.IOperatingProfitCostService;
import com.it.ocs.sys.vo.OperatingProfitCostVo;

@Service
@Transactional
public class OperatingProfitCostService implements IOperatingProfitCostService {

	@Autowired
	private IOperatingProfitCostDao operatingProfitCostDao;

	@Override
	public ResponseResult<OperatingProfitCostVo> findAll(RequestParam param) {
		ResponseResult<OperatingProfitCostVo> result = new ResponseResult<>();
		OperatingProfitCostVo operatingProfitCost = BeanConvertUtil.mapToObject(param.getParam(),
				OperatingProfitCostVo.class);
		PageHelper.startPage(param.getPage(), param.getRows());
		List<OperatingProfitCostModel> list = operatingProfitCostDao.query(operatingProfitCost,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());
		PageInfo<OperatingProfitCostModel> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), OperatingProfitCostVo.class));
		result.setTotal((int) pageInfo.getTotal());
		return result;
	}

	@Override
	public OperationResult saveEdit(OperatingProfitCostVo operatingProfitCost) {
		OperationResult result = new OperationResult();
		try {
			String monthOfYear = new SimpleDateFormat("yyyy-MM").format(TimeTools.getChangeMonth(new Date(), -1));
			OperatingProfitCostModel param = operatingProfitCostDao
					.queryByParam(new OperatingProfitCostModel(monthOfYear));
			if (param == null) {
				operatingProfitCost.setMonthOfYear(monthOfYear);
				operatingProfitCostDao.add(operatingProfitCost);
			} else {
				operatingProfitCost.setEntityId(param.getEntityId());
				operatingProfitCost.setUpdatedAt(new Date());
				operatingProfitCostDao.update(operatingProfitCost);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

}
