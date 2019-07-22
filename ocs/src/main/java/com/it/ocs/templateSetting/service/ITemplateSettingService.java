package com.it.ocs.templateSetting.service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.templateSetting.vo.TemplateSettingVo;

public interface ITemplateSettingService {

	/**
	 * 分页查询
	 * @param param
	 * @return
	 */
	ResponseResult<TemplateSettingVo> findAll(RequestParam param);

	/**
	 * 保存、修改模板
	 * @param templateSetting
	 * @return
	 */
	OperationResult saveEdit(TemplateSettingVo templateSetting);

	/**
	 * 逻辑删除模板
	 * @param ids 要删除的id
	 * @return
	 */
	OperationResult removeTemplateSetting(String ids);

}
