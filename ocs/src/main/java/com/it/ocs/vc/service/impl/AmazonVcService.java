package com.it.ocs.vc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.vc.dao.IAmazonVcDAO;
import com.it.ocs.vc.model.AmazonVcModel;
import com.it.ocs.vc.service.IAmazonVcService;
import com.it.ocs.vc.vo.AmazonVcVO;
@Service
public class AmazonVcService implements IAmazonVcService {
	@Autowired
	private IAmazonVcDAO amazonVcDAO;

	@Override
	public ResponseResult<AmazonVcVO> queryAmazonVcInfos(RequestParam param) {
		ResponseResult<AmazonVcVO> result = new ResponseResult<>();
		AmazonVcVO paramVO = BeanConvertUtil.mapToObject2(param.getParam(), AmazonVcVO.class);
		List<AmazonVcModel> models = amazonVcDAO.queryByPage(paramVO, param.getStartRow(), param.getEndRow());
		List<AmazonVcVO> ifvos = CollectionUtil.beansConvert(models, AmazonVcVO.class);
		int count = amazonVcDAO.count(paramVO);
		result.setRows(ifvos);
		result.setTotal(count);
		return result;
	}

}
