package com.it.ocs.seller.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.collect.Maps;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.publication.model.PaymentModel;
import com.it.ocs.publication.model.PublicationModel;
import com.it.ocs.publication.vo.PaymentVO;
import com.it.ocs.publication.vo.PublicationPageVO;
import com.it.ocs.publication.vo.PublicationVO;
import com.it.ocs.seller.dao.IEBayItemPromoteDao;
import com.it.ocs.seller.model.ComboTreeModel;
import com.it.ocs.seller.model.EBayItemPromoteModel;
import com.it.ocs.seller.service.IEBayItemPromoteService;
import com.it.ocs.seller.vo.ItemPromoteVO;

@Service
public class EBayItemPromoteService extends BaseService implements IEBayItemPromoteService {
	
	@Autowired
	private IEBayItemPromoteDao itemPromoteDao; 
	
	@Override
	public ResponseResult<ItemPromoteVO> findItemPromoteList(RequestParam param) {
		ResponseResult<ItemPromoteVO> result = new ResponseResult<>();
		ItemPromoteVO itemPromoteVO = BeanConvertUtil.mapToObject(param.getParam(), ItemPromoteVO.class);
		List<EBayItemPromoteModel> eBayItemPromoteModels = itemPromoteDao.queryByPage(itemPromoteVO,param.getStartRow(),param.getEndRow());
		List<ItemPromoteVO> itemPromoteVOs = new ArrayList<>();
		convertList(eBayItemPromoteModels, itemPromoteVOs);
		int count = itemPromoteDao.count(itemPromoteVO);
		result.setRows(itemPromoteVOs);
		result.setTotal(count);
		return result;
	}
	private void convertList(List<EBayItemPromoteModel> source, final List<ItemPromoteVO> target) {
		CollectionUtil.each(source, new IAction<EBayItemPromoteModel>() {
			@Override
			public void excute(EBayItemPromoteModel obj) {
				ItemPromoteVO itemPromoteVO = new ItemPromoteVO();
				BeanUtils.copyProperties(obj, itemPromoteVO);
				target.add(itemPromoteVO);
			}
		});
	}
	@Override
	public OperationResult addOrEdit(Map<String, Object> map) {
		OperationResult result = new OperationResult();
		EBayItemPromoteModel promoteModel = new EBayItemPromoteModel();
		promoteModel = BeanConvertUtil.mapToObject2(map, EBayItemPromoteModel.class);
		String tempLateId = (String) map.get("id");
		if(null != tempLateId && !StringUtils.isEmpty(tempLateId)){
			promoteModel.setId(Long.parseLong(tempLateId));
			super.updateInit(promoteModel);
			itemPromoteDao.update(promoteModel);
		}else{
			super.insertInit(promoteModel);
			itemPromoteDao.add(promoteModel);
		}
		result.setData(promoteModel);
		return result;
	}
	@Override
	public ResponseResult<PublicationVO> searchProduct(RequestParam param) {
		ResponseResult<PublicationVO> result = new ResponseResult<>();
		
		List<PublicationModel> publicationModels = itemPromoteDao.searchProduct(param.getParam(),param.getStartRow(),param.getEndRow());
		int count = itemPromoteDao.searchCount(param.getParam());
		result.setRows(formateResult(publicationModels));
		result.setTotal(count);
		return result;
	}
	@Override
	public List<PublicationVO> getTemplateItems(String temId) {
		List<PublicationVO> publicationVOs = new ArrayList<>();
		String itemIds = itemPromoteDao.getPromoteItemIds(temId);
		if(null != itemIds && !StringUtils.isEmpty(itemIds)){
			String[] ids = itemIds.split(",");
			List idList = Arrays.asList(ids);
			List<PublicationModel> publicationModels = itemPromoteDao.getProductInfo(idList);
			publicationVOs = formateResult(publicationModels);
		}
		return publicationVOs;
	}

	private List<PublicationVO> formateResult(List<PublicationModel> publicationModels){
		List<PublicationVO> publicationVOs = new ArrayList<>();
		for(PublicationModel pub:publicationModels){
			PublicationVO pv = new PublicationVO();
			BeanUtils.copyProperties(pub, pv);
			publicationVOs.add(pv);
		}
		return publicationVOs;
	}
	@Override
	public void saveItems(Map<String, Object> map) {
		itemPromoteDao.saveItems(map);
	}
	@Override
	public OperationResult deleteItemPromoteByIds(String ids) {
		OperationResult result = new OperationResult();
		List<Long> idList = new ArrayList<>();
		String[] idArray = ids.split(",");
		for(String id : idArray){
			idList.add(Long.parseLong(id));
		}
		int count = itemPromoteDao.deleteItemPromoteByIds(idList);
		result.setData(count);
		return result;
	}
	@Override
	public List<PublicationVO> getItemsByRule(Map<String, Object> map) {
		List<PublicationVO> publicationVOs = new ArrayList<>();
		String keyword = String.valueOf(map.get("keyword"));
		if(!"".equals(keyword)){
			keyword =keyword.trim();
			keyword = keyword.replace(",", "|");
			keyword = keyword.replaceAll("[\\s]+", "|");
			map.put("keyword", keyword);
		}
		List<PublicationModel> publicationModels = null;
		String orderKey = map.get("orderKey").toString();
		if("0".equals(orderKey)){
			publicationModels = itemPromoteDao.getItemsByHot(map);
		}else{
			publicationModels = itemPromoteDao.getItemsByRule(map);
		}
		publicationVOs = formateResult(publicationModels);
		return publicationVOs;
	}
	@Override
	public List<ComboTreeModel> getItemType(String siteId) {
		List<ComboTreeModel> trees = itemPromoteDao.getItemType(siteId);
		return ComboTreeModel.formatData(trees, 0);
	}
	@Override
	public List<ComboTreeModel> getItemStoreType(String siteId) {
		List<ComboTreeModel> trees = itemPromoteDao.getItemStoreType(siteId);
		return ComboTreeModel.formatData(trees, 0);
	}
	
	
}
