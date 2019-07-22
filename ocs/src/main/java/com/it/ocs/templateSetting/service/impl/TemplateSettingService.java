package com.it.ocs.templateSetting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.templateSetting.dao.ITemplateSettingDao;
import com.it.ocs.templateSetting.model.TemplateSettingModel;
import com.it.ocs.templateSetting.service.ITemplateSettingService;
import com.it.ocs.templateSetting.vo.TemplateSettingVo;

@Service
@Transactional
public class TemplateSettingService extends BaseService implements ITemplateSettingService {

	@Autowired
	private ITemplateSettingDao templateSettingDao;

	@Override
	public ResponseResult<TemplateSettingVo> findAll(RequestParam param) {
		ResponseResult<TemplateSettingVo> result = new ResponseResult<>();
		
		TemplateSettingVo templateSetting = BeanConvertUtil.mapToObject(param.getParam(), TemplateSettingVo.class);
		
		List<TemplateSettingModel> models = templateSettingDao.queryByPage(templateSetting, param.getStartRow(), param.getEndRow());
		
		List<TemplateSettingVo> vos = CollectionUtil.beansConvert(models, TemplateSettingVo.class);
		
		int total = templateSettingDao.count(templateSetting);
		
		result.setRows(vos);
		
		result.setTotal(total);
		
		return result;
	}

	@Override
	public OperationResult saveEdit(TemplateSettingVo templateSetting) {
		OperationResult result = new OperationResult();
		try {
			if (templateSetting.getId() == null) {
				insertInit(templateSetting);
				templateSettingDao.add(templateSetting);
			} else {
				updateInit(templateSetting);
				templateSettingDao.update(templateSetting);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("操作失败");
			throw new RuntimeException();
		}
		
		return result;
	}
	
	@Override
	public OperationResult removeTemplateSetting(String ids) {
		OperationResult result = new OperationResult();
		try {
			String[] split = ids.split(",");
			for (String id : split) {
				//查找
				TemplateSettingModel model = templateSettingDao.getById(new Long(id));
				model.setEnabledFlag("N");
				updateInit(model);
				templateSettingDao.update(model);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("删除失败");
			throw new RuntimeException();
		}
		return result;
	}
}
