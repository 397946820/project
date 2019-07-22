package com.it.ocs.cal.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.cal.dao.IPackagingRuleDao;
import com.it.ocs.cal.model.PackagingRuleModel;
import com.it.ocs.cal.service.IPackagingRuleService;
import com.it.ocs.cal.vo.PackagingRuleVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.enums.Style;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;

@Service
@Transactional
public class PackagingRuleService implements IPackagingRuleService {

	@Autowired
	private IPackagingRuleDao packagingRuleDao;

	@Override
	public ResponseResult<PackagingRuleVo> findAll(RequestParam param) {

		ResponseResult<PackagingRuleVo> result = new ResponseResult<>();

		PackagingRuleModel rule = BeanConvertUtil.mapToObject(param.getParam(), PackagingRuleModel.class);
		
		if(rule == null) {
			rule = new PackagingRuleModel();
		}

		PageHelper.startPage(param.getPage(), param.getRows());

		List<PackagingRuleModel> list = packagingRuleDao.query(rule,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());

		PageInfo<PackagingRuleModel> pageInfo = new PageInfo<>(list);

		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), PackagingRuleVo.class));

		result.setTotal((int) pageInfo.getTotal());

		return result;
	}

	@Override
	public OperationResult saveEdit(PackagingRuleVo packagingRule) {
		OperationResult result = new OperationResult();
		try {
			if (packagingRule.getEntityId() == null) {
				packagingRuleDao.add(packagingRule);
			} else {
				packagingRule.setUpdatedAt(new Date());
				packagingRuleDao.update(packagingRule);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

}
