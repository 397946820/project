package com.it.ocs.transportSetting.service.impl;

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
import com.it.ocs.transportSetting.dao.ITransportSettingDao;
import com.it.ocs.transportSetting.model.TransportSettingModel;
import com.it.ocs.transportSetting.service.ITransportSettingService;
import com.it.ocs.transportSetting.utils.Tools;
import com.it.ocs.transportSetting.vo.TransportSettingVo;

@Service
@Transactional
public class TransportSettingService extends BaseService implements ITransportSettingService {

	@Autowired
	private ITransportSettingDao transportSettingDao;

	@Override
	public ResponseResult<TransportSettingVo> findAll(RequestParam param) {
		ResponseResult<TransportSettingVo> result = new ResponseResult<>();

		TransportSettingVo transportSetting = BeanConvertUtil.mapToObject(param.getParam(), TransportSettingVo.class);

		List<TransportSettingModel> models = transportSettingDao.queryByPage(transportSetting, param.getStartRow(),
				param.getEndRow());

		List<TransportSettingVo> vos = CollectionUtil.beansConvert(models, TransportSettingVo.class);

		int total = transportSettingDao.count(transportSetting);

		result.setRows(vos);

		result.setTotal(total);

		return result;
	}

	@Override
	public OperationResult saveEdit(TransportSettingVo transportSetting) {
		OperationResult result = new OperationResult();
		try {
			transportSetting.setDomisticRule(Tools.getDomisticRule(transportSetting.getDomisticRule()));
			transportSetting.setInternationalRule(Tools.getInternationalRule(transportSetting.getInternationalRule()));
			if (transportSetting.getPublished() == null || transportSetting.getPublished() == "") {
				transportSetting.setPublished("N");
			}
			if (transportSetting.getId() == null) {
				// 保存
				insertInit(transportSetting);
				transportSettingDao.add(transportSetting);
			} else {
				// 修改
				updateInit(transportSetting);
				transportSettingDao.update(transportSetting);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("操作失败");
			throw new RuntimeException();
		}
		return result;
	}

	@Override
	public OperationResult removeTransportSetting(String ids) {
		OperationResult result = new OperationResult();
		try {
			String[] split = ids.split(",");
			for (String id : split) {
				TransportSettingModel model = transportSettingDao.getById(new Long(id));
				updateInit(model);
				model.setEnabledFlag("N");
				transportSettingDao.update(model);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("删除失败");
			throw new RuntimeException();
		}
		return result;
	}
}
