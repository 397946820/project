package com.it.ocs.transportSetting.service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.transportSetting.vo.TransportSettingVo;

public interface ITransportSettingService {

	/**
	 * 分页查询
	 * @param param 查询条件，分页信息的对象
	 * @return
	 */
	ResponseResult<TransportSettingVo> findAll(RequestParam param);

	/**
	 * 保存和修改
	 * @param transportSetting 
	 * @return
	 */
	OperationResult saveEdit(TransportSettingVo transportSetting);

	/**
	 * 逻辑删除
	 * @param ids 需要删除的id
	 * @return
	 */
	OperationResult removeTransportSetting(String ids);

}
