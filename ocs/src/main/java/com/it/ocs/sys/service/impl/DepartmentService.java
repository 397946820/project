package com.it.ocs.sys.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.sys.model.DepartmentModel;
import com.it.ocs.sys.service.IDepartmentService;
import com.it.ocs.sys.vo.DepartmentVO;
@Service
public class DepartmentService extends BaseService implements IDepartmentService {
	@Override
	public ResponseResult<DepartmentVO> queryDepartment(RequestParam param) {
		ResponseResult<DepartmentVO> result = new ResponseResult<>();
		List<DepartmentModel> departmentModels = departmentDAO.queryByPage(null,param.getStartRow(), param.getEndRow());
		List<DepartmentVO> departmentVOs = CollectionUtil.beansConvert(departmentModels, DepartmentVO.class);
		int count = departmentDAO.count(null);
		result.setRows(departmentVOs);
		result.setTotal(count);
		return result;
	}

	@Override
	public OperationResult saveDepartment(DepartmentVO departmentVO) {
		OperationResult result = new OperationResult();
		try {
			if (null != departmentVO.getId()) {
				DepartmentModel departmentModel = departmentDAO.getById(departmentVO.getId());
				BeanUtils.copyProperties(departmentModel, departmentVO, new String[] {"name", "code"});
				updateInit(departmentVO);
				departmentDAO.update(departmentVO);
			} else {
				insertInit(departmentVO);
				departmentDAO.add(departmentVO);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("save error");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public OperationResult removeDepartment(Long departmentId) {
		OperationResult result = new OperationResult();
		try {
			departmentDAO.delete(departmentId);
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("remove error");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<DepartmentVO> findAll() {
		List<DepartmentModel> models = departmentDAO.query(null);
		List<DepartmentVO> departmentVOs = CollectionUtil.beansConvert(models, DepartmentVO.class);
		return departmentVOs;
	}

	@Override
	public boolean existByParam(DepartmentVO param) {
		DepartmentModel departmentModel = departmentDAO.getByParam(param);
		if (null == param.getId()) {
			return null != departmentModel;
		} else {
			return null != departmentModel && departmentModel.getId().longValue() != param.getId().longValue();
		}
		
	}

	public DepartmentModel getById(Long departmentId) {
		return departmentDAO.getById(departmentId);
	}

}
