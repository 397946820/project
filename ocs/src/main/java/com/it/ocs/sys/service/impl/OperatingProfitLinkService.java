package com.it.ocs.sys.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.enums.Style;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;
import com.it.ocs.sys.dao.IOperatingProfitLinkDao;
import com.it.ocs.sys.model.OperatingProfitLinkModel;
import com.it.ocs.sys.service.IOperatingProfitLinkService;
import com.it.ocs.sys.vo.OperatingProfitLinkVo;

@Service
public class OperatingProfitLinkService implements IOperatingProfitLinkService {
	
Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private IOperatingProfitLinkDao operatingProfitLinkDao;

	@Override
	public ResponseResult<OperatingProfitLinkVo> findAll(RequestParam param) {
		ResponseResult<OperatingProfitLinkVo> result = new ResponseResult<>();
		OperatingProfitLinkVo operatingProfitLink = BeanConvertUtil.mapToObject(param.getParam(),
				OperatingProfitLinkVo.class);
		PageHelper.startPage(param.getPage(), param.getRows());
		List<OperatingProfitLinkModel> list = operatingProfitLinkDao.query(operatingProfitLink,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());
		PageInfo<OperatingProfitLinkModel> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), OperatingProfitLinkVo.class));
		result.setTotal((int)pageInfo.getTotal());
		return result;
	}

}
