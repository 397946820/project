package com.it.ocs.seller.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.seller.dao.IEBaySellerDescriptionDao;
import com.it.ocs.seller.model.EBaySellerDescriptionModel;
import com.it.ocs.seller.service.IEBaySellerDescriptionService;
import com.it.ocs.seller.vo.ItemPromoteVO;
import com.it.ocs.seller.vo.SellerDescriptionVO;
@Service
public class EBaySellerDescriptionService extends BaseService implements IEBaySellerDescriptionService {
	
	@Autowired
	private IEBaySellerDescriptionDao sellerDescriptionDao;
	
	@Override
	public ResponseResult<SellerDescriptionVO> findSellerDescriptionList(RequestParam param) {
		
		ResponseResult<SellerDescriptionVO> result = new ResponseResult<>();
		
		SellerDescriptionVO sellerDescriptionVO= BeanConvertUtil.mapToObject(param.getParam(), SellerDescriptionVO.class);
		List<EBaySellerDescriptionModel> ebaySellerDescriptionModels = sellerDescriptionDao.queryByPage(sellerDescriptionVO, param.getStartRow(), param.getEndRow());
		
		List<SellerDescriptionVO> sellerDescriptionVOs = new ArrayList<>();
		
		convertList(ebaySellerDescriptionModels, sellerDescriptionVOs);
		
		int count = sellerDescriptionDao.count(sellerDescriptionVO);		
		
		result.setRows(sellerDescriptionVOs);
		
		result.setTotal(count);
		return result;
	}
	
	
	private void convertList(List<EBaySellerDescriptionModel> source, final List<SellerDescriptionVO> target) {
		CollectionUtil.each(source, new IAction<EBaySellerDescriptionModel>() {
			@Override
			public void excute(EBaySellerDescriptionModel obj) {
				SellerDescriptionVO sellerDescriptionVO = new SellerDescriptionVO();
				BeanUtils.copyProperties(obj, sellerDescriptionVO);
				target.add(sellerDescriptionVO);
			}
		});
	}


	@Override
	public OperationResult insertSellerDescription(Map<String, Object> map) {
		OperationResult result = new OperationResult();
		EBaySellerDescriptionModel sdModel = BeanConvertUtil.mapToObject(map, EBaySellerDescriptionModel.class);
		super.insertInit(sdModel);
		Integer opt = sellerDescriptionDao.addSellerDescription(sdModel);
		result.setData(opt);
		return result;
	}


	@Override
	public OperationResult updateSellerDescription(Map<String, Object> map) {
		OperationResult result = new OperationResult();
		EBaySellerDescriptionModel sdModel = BeanConvertUtil.mapToObject(map, EBaySellerDescriptionModel.class);
		super.updateInit(sdModel);
		Integer opt = sellerDescriptionDao.updateSellerDescription(sdModel);
		result.setData(opt);
		return result;
	}


	@Override
	public OperationResult addOrEdit(Map<String, Object> map) {
		String id = (String)map.get("id");
		if(null != id && !StringUtils.isEmpty(id)){
			return updateSellerDescription(map);
		}else{
			return insertSellerDescription(map);
		}
	}


	@Override
	public OperationResult deleteSellerDescriptionByIds(String ids) {
		// TODO Auto-generated method stub
		
		OperationResult result = new OperationResult();
		
		List<Long> idList = new ArrayList<>();
		
		try{
			
			String[] idArray = ids.split(",");
			
			for(String id : idArray){
				
				idList.add(Long.parseLong(id));
			}
			
			sellerDescriptionDao.deleteSellerDescriptionByIds(idList);
		}catch (Exception e) {
			// TODO: handle exception
			result.setErrorCode(1);
			
			result.setDescription("delete error");
					
			e.printStackTrace();
		}
		return result;
	}

	

}
