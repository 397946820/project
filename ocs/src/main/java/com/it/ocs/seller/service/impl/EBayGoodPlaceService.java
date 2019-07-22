package com.it.ocs.seller.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.seller.dao.IEBayGoodPlaceDao;
import com.it.ocs.seller.model.EBayGoodPlaceModel;
import com.it.ocs.seller.service.IEBayGoodPlaceService;
import com.it.ocs.seller.vo.GoodPlaceVO;
@Service
public class EBayGoodPlaceService extends BaseService implements IEBayGoodPlaceService {

	@Autowired
	private IEBayGoodPlaceDao goodPlaceDao;
	

	@Override
	public OperationResult deleteGoodPlaceByIds(String ids) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		
		List<Long> idList = new ArrayList<>();
		try{
			
			String[] idArray = ids.split(",");
			
			for (String id : idArray) {
				
				idList.add(Long.parseLong(id));
				
			}
			
			goodPlaceDao.deleteGoodPlaceByIds(idList);
			
		}catch (Exception e) {
			// TODO: handle exception
			
			result.setErrorCode(1);
			
			result.setDescription("delete error");
					
			e.printStackTrace();
		}
		
		return result;
	}
	@Override
	public OperationResult insertGoodPlace(GoodPlaceVO goodPlaceVO) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		
		try{
			
			EBayGoodPlaceModel bayGoodPlaceModel = goodPlaceVO;
			
			goodPlaceDao.add(bayGoodPlaceModel);
		
		}catch (Exception e) {
			// TODO: handle exception
			
				result.setErrorCode(1);
						
				result.setDescription("insert error");
						
				e.printStackTrace();
		}
		return result;
	}

	@Override
	public OperationResult updateGoodPlace(GoodPlaceVO goodPlaceVO) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		
		try{
			
			EBayGoodPlaceModel bayGoodPlaceModel = goodPlaceVO;
			
			goodPlaceDao.update(bayGoodPlaceModel);
			
		}catch (Exception e) {
			// TODO: handle exception
			result.setErrorCode(1);
			
			result.setDescription("update error");
					
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public OperationResult addOrEdit(GoodPlaceVO goodPlaceVO) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		
		
			if(goodPlaceVO.getId()==null||goodPlaceVO.getId().equals("")){
				
				result = insertGoodPlace(goodPlaceVO);
				
			}else{
				
				result = updateGoodPlace(goodPlaceVO);
				
			}
		return result;
	}
	
	@Override
	public ResponseResult<GoodPlaceVO> findGoodPlaceList(RequestParam param) {
		
		ResponseResult<GoodPlaceVO> result = new ResponseResult<>();
		
		GoodPlaceVO goodPlaceVO = BeanConvertUtil.mapToObject(param.getParam(), GoodPlaceVO.class);
		List<EBayGoodPlaceModel> ebayGoodPlaceModels = goodPlaceDao.queryByPage(goodPlaceVO, param.getStartRow(), param.getEndRow());
		
		List<GoodPlaceVO> goodPlaceVOs = new ArrayList<>();
		
		convertList(ebayGoodPlaceModels, goodPlaceVOs);
		
		int count = goodPlaceDao.count(goodPlaceVO); 
		
		result.setRows(goodPlaceVOs);
		
		result.setTotal(count);
		
		return result;
	}
	
	private void convertList(List<EBayGoodPlaceModel> source, final List<GoodPlaceVO> target) {
		CollectionUtil.each(source, new IAction<EBayGoodPlaceModel>() {
			@Override
			public void excute(EBayGoodPlaceModel obj) {
				GoodPlaceVO buyerRequiredVO = new GoodPlaceVO();
				BeanUtils.copyProperties(obj, buyerRequiredVO);
				target.add(buyerRequiredVO);
			}
		});
	}

	


	
}
