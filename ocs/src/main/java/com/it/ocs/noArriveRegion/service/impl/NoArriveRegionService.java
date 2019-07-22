package com.it.ocs.noArriveRegion.service.impl;

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
import com.it.ocs.noArriveRegion.dao.INoArriveRegionDao;
import com.it.ocs.noArriveRegion.model.NoArriveRegionModel;
import com.it.ocs.noArriveRegion.service.INoArriveRegionService;
import com.it.ocs.noArriveRegion.vo.NoArriveRegionVo;

@Service
@Transactional
public class NoArriveRegionService extends BaseService implements INoArriveRegionService {

	@Autowired
	private INoArriveRegionDao noArriveRegionDao;

	@Override
	public ResponseResult<NoArriveRegionVo> findAll(RequestParam param) {
		ResponseResult<NoArriveRegionVo> result = new ResponseResult<>();
		
		NoArriveRegionVo noArriveRegion = BeanConvertUtil.mapToObject(param.getParam(), NoArriveRegionVo.class);
		
		List<NoArriveRegionModel> models = noArriveRegionDao.queryByPage(noArriveRegion, param.getStartRow(), param.getEndRow());
		
		List<NoArriveRegionVo> vos = CollectionUtil.beansConvert(models, NoArriveRegionVo.class);
		
		int total = noArriveRegionDao.count(noArriveRegion);
		
		result.setRows(vos);
		
		result.setTotal(total);
		
		return result;
	}
	
	@Override
	public OperationResult saveEdit(NoArriveRegionVo region) {
		OperationResult result = new OperationResult();
		
		try {
			
			if (region.getId() == null) {
				insertInit(region);
				noArriveRegionDao.add(region);
			} else {
				updateInit(region);
				noArriveRegionDao.update(region);
			}
			
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("操作失败");
			throw new RuntimeException();
		}
		return result;
	}
	
	@Override
	public OperationResult removeNoArriveRegion(String ids) {
		OperationResult result = new OperationResult();
		
		try {
			String[] split = ids.split(",");
			for (String id : split) {
				NoArriveRegionModel model = noArriveRegionDao.getById(new Long(id));
				updateInit(model);
				model.setEnabledFlag("N");
				noArriveRegionDao.update(model);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("删除失败");
			throw new RuntimeException();
		}
		return result;
	}
}
