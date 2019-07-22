package com.it.ocs.customerCenter.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.customerCenter.dao.IBaseComboboxDao;
import com.it.ocs.customerCenter.model.BaseComboboxModel;
import com.it.ocs.customerCenter.model.MetadataModel;
import com.it.ocs.customerCenter.service.IBaseComboboxService;
import com.it.ocs.customerCenter.vo.MetadataVO;
import com.it.ocs.synchronou.util.ValidationUtil;

@Service
public class BaseComboboxService extends BaseService implements IBaseComboboxService {
	private static Logger logger = Logger.getLogger(BaseComboboxService.class);
	@Autowired
	private IBaseComboboxDao baseComboboxDao;
	@Override
	public String getValueByField(String field) {
		field = "select "+field +" as result from  BASE_COMBOBOX_INFO where ID=1";
		BaseComboboxModel baseComboboxModel = baseComboboxDao.getValueByField(field);
		return baseComboboxModel.getResult();
	}
	@Override
	public ResponseResult<MetadataVO> selectMappingSkus(RequestParam param) {
		ResponseResult<MetadataVO> result = new ResponseResult<>();
		Map<String, Object> map =param.getParam();
		List<MetadataVO> metadataVOs = Lists.newArrayList();
		List<MetadataModel> metadataModels = baseComboboxDao.selectMappingSkus(map, param.getStartRow(),
				param.getEndRow());
		CollectionUtil.each(metadataModels, new IAction<MetadataModel>() {
			@Override
			public void excute(MetadataModel obj) {
				MetadataVO metadataVO = new MetadataVO();
				BeanUtils.copyProperties(obj, metadataVO);
				metadataVOs.add(metadataVO);
			}
		});
		Integer total = baseComboboxDao.getSkuTotal(map);
		result.setRows(metadataVOs);
		result.setTotal(total);
		return result;
	}
	/*@Override
	public ResponseResult<MetadataVO> selectMappingCatagories(RequestParam param) {
		ResponseResult<MetadataVO> result = new ResponseResult<>();
		Map<String, Object> map =param.getParam();
		List<MetadataVO> metadataVOs = Lists.newArrayList();
		List<MetadataModel> metadataModels = baseComboboxDao.selectMappingCatagories(map, param.getStartRow(),
				param.getEndRow());
		CollectionUtil.each(metadataModels, new IAction<MetadataModel>() {
			@Override
			public void excute(MetadataModel obj) {
				MetadataVO metadataVO = new MetadataVO();
				BeanUtils.copyProperties(obj, metadataVO);
				metadataVOs.add(metadataVO);
			}
		});
		Integer total = baseComboboxDao.getCatagoriesTotal(map);
		result.setRows(metadataVOs);
		result.setTotal(total);
		return result;
	}
	@Override
	public ResponseResult<MetadataVO> selectMappingParents(RequestParam param) {
		ResponseResult<MetadataVO> result = new ResponseResult<>();
		Map<String, Object> map =param.getParam();
		List<MetadataVO> metadataVOs = Lists.newArrayList();
		List<MetadataModel> metadataModels = baseComboboxDao.selectMappingParents(map, param.getStartRow(),
				param.getEndRow());
		CollectionUtil.each(metadataModels, new IAction<MetadataModel>() {
			@Override
			public void excute(MetadataModel obj) {
				MetadataVO metadataVO = new MetadataVO();
				BeanUtils.copyProperties(obj, metadataVO);
				metadataVOs.add(metadataVO);
			}
		});
		Integer total = baseComboboxDao.getParentTotal(map);
		result.setRows(metadataVOs);
		result.setTotal(total);
		return result;
	}*/
	@Override
	public OperationResult skuSave(MetadataVO metadataVO) {
		OperationResult result = new OperationResult();
		
		MetadataModel isMetadas = baseComboboxDao.selectMetadatesBySku(metadataVO.getSku());
		
		List<MetadataModel> metadataModels = Lists.newArrayList();
		MetadataModel metadataModel = new MetadataVO();
		metadataModel = metadataVO;
		metadataModels.add(metadataModel);
		if(ValidationUtil.isNull(metadataVO.getId())){
			if(!ValidationUtil.isNull(isMetadas)){
				result.setDescription("关联的SKU已经存在");
				return result;
			}
			baseComboboxDao.insertMappingSkus(metadataModels);
		}else{
			baseComboboxDao.updateMappingSkus(metadataModels);
		}
		result.setData(1);
		result.setDescription("操作成功！");
		return result;
	}
	@Override
	public OperationResult insertMappingSkus(List<MetadataVO> metadataVOs) {
		OperationResult result = new OperationResult();
		List<MetadataModel> metadataModels = Lists.newArrayList();
		CollectionUtil.each(metadataVOs, new IAction<MetadataVO>() {
			@Override
			public void excute(MetadataVO obj) {
				MetadataModel metadataModel = new MetadataVO();
				BeanUtils.copyProperties(obj, metadataModel);
				metadataModels.add(metadataModel);
			}
		});
		baseComboboxDao.insertMappingSkus(metadataModels);
		result.setData("1");
		return result;
	}
	@Override
	public OperationResult updateMappingSkus(List<MetadataVO> metadataVOs) {
		OperationResult result = new OperationResult();
		List<MetadataModel> metadataModels = Lists.newArrayList();
		CollectionUtil.each(metadataVOs, new IAction<MetadataVO>() {
			@Override
			public void excute(MetadataVO obj) {
				MetadataModel metadataModel = new MetadataVO();
				BeanUtils.copyProperties(obj, metadataModel);
				metadataModels.add(metadataModel);
			}
		});
		baseComboboxDao.updateMappingSkus(metadataModels);
		result.setData("1");
		return result;
	}
	@Override
	public OperationResult catagoriesSave(MetadataVO metadataVO) {
		OperationResult result = new OperationResult();
		List<MetadataModel> metadataModels = Lists.newArrayList();
		MetadataModel metadataModel = new MetadataVO();
		metadataModel = metadataVO;
		metadataModels.add(metadataModel);
		if(ValidationUtil.isNull(metadataVO.getId())){
			baseComboboxDao.insertMappingCatagories(metadataModels);
		}else{
			baseComboboxDao.updateMappingCatagories(metadataModels);
		}
		result.setData(1);
		result.setDescription("操作成功！");
		return result;
	}
	@Override
	public OperationResult parentsSave(MetadataVO metadataVO) {
		OperationResult result = new OperationResult();
		List<MetadataModel> metadataModels = Lists.newArrayList();
		MetadataModel metadataModel = new MetadataVO();
		metadataModel = metadataVO;
		metadataModels.add(metadataModel);
		if(ValidationUtil.isNull(metadataVO.getId())){
			baseComboboxDao.insertMappingParents(metadataModels);
		}else{
			baseComboboxDao.updateMappingParents(metadataModels);
		}
		result.setData(1);
		result.setDescription("操作成功！");
		return result;
	}
	@Override
	public OperationResult skuRemove(Long id) {
		OperationResult result  = new OperationResult();
		baseComboboxDao.skuRemoveById(id);
		result.setDescription("操作成功！");
		return result;
	}
	/*@Override
	public OperationResult catagoriesRemove(Long id) {
		OperationResult result  = new OperationResult();
		baseComboboxDao.catagoriesRemoveById(id);
		result.setDescription("操作成功！");
		return result;
	}*/
	/*@Override
	public OperationResult parentsRemove(Long id) {
		OperationResult result  = new OperationResult();
		baseComboboxDao.parentRemoveById(id);
		result.setDescription("操作成功！");
		return result;
	}*/
	/*@Override
	public OperationResult insertMappingCatagories(List<MetadataVO> metadataVOs) {
		OperationResult result = new OperationResult();
		List<MetadataModel> metadataModels = Lists.newArrayList();
		CollectionUtil.each(metadataVOs, new IAction<MetadataVO>() {
			@Override
			public void excute(MetadataVO obj) {
				MetadataModel metadataModel = new MetadataVO();
				BeanUtils.copyProperties(obj, metadataModel);
				metadataModels.add(metadataModel);
			}
		});
		baseComboboxDao.insertMappingCatagories(metadataModels);
		result.setData("1");
		return result;
	}
	@Override
	public OperationResult updateMappingCatagories(List<MetadataVO> metadataVOs) {
		return null;
	}
	@Override
	public OperationResult insertParents(List<MetadataVO> metadataVOs) {
		return null;
	}
	@Override
	public OperationResult updateParents(List<MetadataVO> metadataVOs) {
		return null;
	}*/
	@Override
	public List<MetadataVO> selectParents() {
		List<MetadataVO> metadataVOs = Lists.newArrayList();
		List<MetadataModel> metadataModels = baseComboboxDao.selectParents();
		CollectionUtil.each(metadataModels, new IAction<MetadataModel>() {

			@Override
			public void excute(MetadataModel obj) {
				MetadataVO metadataVO = new MetadataVO();
				BeanUtils.copyProperties(obj, metadataVO);
				metadataVOs.add(metadataVO);
			}
		});
		
		return metadataVOs;
	}
	@Override
	public List<MetadataVO> selectCatagories() {
		List<MetadataVO> metadataVOs = Lists.newArrayList();
		List<MetadataModel> metadataModels = baseComboboxDao.selectCatagories();
		CollectionUtil.each(metadataModels, new IAction<MetadataModel>() {

			@Override
			public void excute(MetadataModel obj) {
				MetadataVO metadataVO = new MetadataVO();
				BeanUtils.copyProperties(obj, metadataVO);
				metadataVOs.add(metadataVO);
			}
		});
		
		return metadataVOs;
	}
	@Override
	public MetadataVO selectMetadatesBySku(String sku) {
		MetadataVO metadataVO = new MetadataVO();
		MetadataModel metadataModel = baseComboboxDao.selectMetadatesBySku(sku.trim());
		if(!ValidationUtil.isNull(metadataModel)){
			BeanUtils.copyProperties(metadataModel, metadataVO);
		}
		return metadataVO;
	}

}
