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
import com.it.ocs.seller.dao.IEBayBuyerRequiredDao;
import com.it.ocs.seller.model.EBayBuyerRequiredModel;
import com.it.ocs.seller.service.IEBayBuyerRequireService;
import com.it.ocs.seller.util.BuyerRequireUtil;
import com.it.ocs.seller.vo.BuyerRequiredVO;

import net.sf.json.JSONObject;
@Service
public class EBayBuyerRequireService extends BaseService implements IEBayBuyerRequireService {
	

	@Autowired
	private IEBayBuyerRequiredDao buyerRequiredDao;
	

	@Override
	public BuyerRequiredVO findBuyerRequireById(Long id) {
		// TODO Auto-generated method stub
		EBayBuyerRequiredModel eBayBuyerRequiredModel = buyerRequiredDao.getById(id);
		BuyerRequiredVO buyerRequiredVO2 = new BuyerRequiredVO();
		JSONObject jsonObject = JSONObject.fromObject(eBayBuyerRequiredModel.getBuyerRule());
		buyerRequiredVO2.setName(eBayBuyerRequiredModel.getName());
		buyerRequiredVO2.setSite(eBayBuyerRequiredModel.getSite());
		buyerRequiredVO2.setId(eBayBuyerRequiredModel.getId());
		buyerRequiredVO2.setAllowAllBuyer(eBayBuyerRequiredModel.getAllowAllBuyer());
		buyerRequiredVO2.setJsonObject(jsonObject);
		return buyerRequiredVO2;
		
	}

	@Override
	public ResponseResult<BuyerRequiredVO> findBuyerRequireList(RequestParam param) {
		// TODO Auto-generated method stub
		
		BuyerRequireUtil allUtil = new BuyerRequireUtil();
		
		BuyerRequiredVO buyerRequiredVO = BeanConvertUtil.mapToObject(param.getParam(), BuyerRequiredVO.class);
		ResponseResult<BuyerRequiredVO> result = new ResponseResult<>();
		
		List<EBayBuyerRequiredModel> bayBuyerRequiredModels = buyerRequiredDao.queryByPage(buyerRequiredVO, param.getStartRow(), param.getEndRow());
		
		List<BuyerRequiredVO> buyerRequiredVOs = new ArrayList<>() ;
		
		for (EBayBuyerRequiredModel bayBuyerRequiredModel : bayBuyerRequiredModels) {
			
			String buyerRule = allUtil.getTextByJsonString(bayBuyerRequiredModel.getBuyerRule());
			
			bayBuyerRequiredModel.setBuyerRule(buyerRule);
			
		}
		convertList(bayBuyerRequiredModels,buyerRequiredVOs);
		int count = buyerRequiredDao.count(buyerRequiredVO);
		result.setRows(buyerRequiredVOs);
		result.setTotal(count);
		return result;
	}

	@Override
	public OperationResult insertBuyerRequire(BuyerRequiredVO buyerRequiredVO) {
		
		JSONObject json = buyerRequiredVO.getJsonObject();
		OperationResult result = new OperationResult();
		try{
			if (!json.has("id")||json.getString("id")==null||json.getString("id")=="") {
				
				buyerRequiredVO.setName(buyerRequiredVO.getJsonObject().getString("name"));
				buyerRequiredVO.setSite(buyerRequiredVO.getJsonObject().getString("site"));
				buyerRequiredVO.setAllowAllBuyer(buyerRequiredVO.getJsonObject().getString("allowAllBuyer"));
				buyerRequiredVO.setJsonObject(buyerRequiredVO.getJsonObject().discard("name"));
				buyerRequiredVO.setJsonObject(buyerRequiredVO.getJsonObject().discard("site"));
				buyerRequiredVO.setBuyerRule(buyerRequiredVO.getJsonObject().toString());
				
				if(buyerRequiredVO.getJsonObject().getString("allowAllBuyer").equals("N")){
					
					buyerRequiredVO.setBuyerRule(buyerRequiredVO.getJsonObject().toString());
				
				}else{
					
					buyerRequiredVO.setBuyerRule("{\"allowAllBuyer\":\"Y\"}");
					
				}
				
				EBayBuyerRequiredModel eBayBuyerRequiredModel = buyerRequiredVO;
				buyerRequiredDao.add(eBayBuyerRequiredModel);
				
				
			}else{
				
				result = updateBuyerRequireById(buyerRequiredVO);
				
				
			}
		}catch (Exception e) {
			// TODO: handle exception
			
			result.setErrorCode(1);
			
			result.setDescription("update error");
			
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public OperationResult updateBuyerRequireById(BuyerRequiredVO buyerRequiredVO) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		
		try{
			JSONObject jsonObject = buyerRequiredVO.getJsonObject();
			buyerRequiredVO.setName(jsonObject.getString("name"));
			buyerRequiredVO.setSite(jsonObject.getString("site"));
			buyerRequiredVO.setId(jsonObject.getLong("id"));
			buyerRequiredVO.setAllowAllBuyer(jsonObject.getString("allowAllBuyer"));
			buyerRequiredVO.setJsonObject(buyerRequiredVO.getJsonObject().discard("name"));
			buyerRequiredVO.setJsonObject(buyerRequiredVO.getJsonObject().discard("site"));
			buyerRequiredVO.setJsonObject(buyerRequiredVO.getJsonObject().discard("id"));
			
			if(jsonObject.getString("allowAllBuyer").equals("N")){
				
				buyerRequiredVO.setBuyerRule(buyerRequiredVO.getJsonObject().toString());
			
			}else{
				
				buyerRequiredVO.setBuyerRule("{\"allowAllBuyer\":\"Y\"}");
				
			}
			
			EBayBuyerRequiredModel eBayBuyerRequiredModel = buyerRequiredVO;
			
			
			buyerRequiredDao.update(eBayBuyerRequiredModel);
			
		}catch (Exception e) {
			
			result.setErrorCode(1);
			
			result.setDescription("update error");
			
			e.printStackTrace();
			
		}
		return result;
	}

	@Override
	public OperationResult deleteBuyerRequireById(String ids) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		
		List<Long> idList = new ArrayList<Long>();
		
		try {
			String[] idArray = ids.split(",");
			
			for (String id : idArray) {
				
				idList.add(Long.parseLong(id));
				
			}
			
			buyerRequiredDao.deleteBuyerRequireById(idList);
			
		}catch (Exception e) {
			// TODO: handle exception
			
			result.setErrorCode(1);
			
			result.setDescription("delete error");
			
			e.printStackTrace();
		}
		
		
		return result;
	}
	private void convertList(List<EBayBuyerRequiredModel> source, final List<BuyerRequiredVO> target) {
		CollectionUtil.each(source, new IAction<EBayBuyerRequiredModel>() {
			@Override
			public void excute(EBayBuyerRequiredModel obj) {
				BuyerRequiredVO buyerRequiredVO = new BuyerRequiredVO();
				BeanUtils.copyProperties(obj, buyerRequiredVO);
				target.add(buyerRequiredVO);
			}
		});
	}
}
