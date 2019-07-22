package com.it.ocs.returnpolicy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.returnpolicy.dao.IReturnPolicyDAO;
import com.it.ocs.returnpolicy.model.ReturnPolicyModel;
import com.it.ocs.returnpolicy.service.IReturnPolicyService;
import com.it.ocs.returnpolicy.vo.ReturnPolicyVO;

@Service
public class ReturnPolicyServiceImpl extends BaseService implements IReturnPolicyService {
	@Autowired
	private IReturnPolicyDAO returnPolicyDAO;

	@Override
	public ResponseResult<ReturnPolicyVO> queryReturnPolicy(RequestParam param) {
		ResponseResult<ReturnPolicyVO> result = new ResponseResult<>();
		ReturnPolicyVO returnPolicyVO = BeanConvertUtil.mapToObject(param.getParam(), ReturnPolicyVO.class);
		List<ReturnPolicyModel> returnPolicys = returnPolicyDAO.queryByPage(returnPolicyVO, param.getStartRow(), param.getEndRow());
		List<ReturnPolicyVO> returnPolicyVOs = new ArrayList<>();
		convertList(returnPolicys, returnPolicyVOs);
		int count = returnPolicyDAO.count(returnPolicyVO);
		result.setRows(returnPolicyVOs);
		result.setTotal(count);
		return result;
	}

	@Override
	public OperationResult saveReturnPolicy(ReturnPolicyVO returnPolicyVO) {
		OperationResult result = new OperationResult();
		try {
			if (null != returnPolicyVO.getReturnPolicyId()) {
				updateInit(returnPolicyVO);
				returnPolicyDAO.update(returnPolicyVO);
			} else {
				insertInit(returnPolicyVO);
				returnPolicyDAO.add(returnPolicyVO);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("save error");
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public OperationResult removeReturnPolicy(String ids) {
		OperationResult result = new OperationResult();
		try {
			String[] idarr=ids.split(",");
			for (String id : idarr) {
				ReturnPolicyVO returnPolicyVO  = new ReturnPolicyVO();
				returnPolicyVO.setEnabledFlag("N");
				returnPolicyVO.setReturnPolicyId(Long.valueOf(id));
				updateInit(returnPolicyVO);
				returnPolicyDAO.update(returnPolicyVO);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("remove error");
		}
		return result;
	}

	private void convertList(List<ReturnPolicyModel> source, final List<ReturnPolicyVO> target) {
		CollectionUtil.each(source, new IAction<ReturnPolicyModel>() {
			@Override
			public void excute(ReturnPolicyModel obj) {
				ReturnPolicyVO returnPolicyVO = new ReturnPolicyVO();
				BeanUtils.copyProperties(obj, returnPolicyVO);
				target.add(returnPolicyVO);
			}
		});
	}

}
