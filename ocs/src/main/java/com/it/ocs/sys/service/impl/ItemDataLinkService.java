package com.it.ocs.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.publication.model.BuyerRequireModel;
import com.it.ocs.publication.model.PaymentModel;
import com.it.ocs.publication.vo.BuyerRequireVO;
import com.it.ocs.sys.model.ItemDataModel;
import com.it.ocs.sys.service.IItemDataLinkService;
import com.it.ocs.sys.vo.EasyUITreeVO;

@Service
public class ItemDataLinkService extends BaseService implements IItemDataLinkService {

	@Override
	public List<EasyUITreeVO> itemDataTree(long id) {
		List<ItemDataModel> itemDataModels = pubInfoDAO.getItemDataModels(id);
		List<EasyUITreeVO> target = new ArrayList<>();
		CollectionUtil.each(itemDataModels, new IAction<ItemDataModel>() {
			@Override
			public void excute(ItemDataModel obj) {
				EasyUITreeVO easyUITreeVO = new EasyUITreeVO();
				easyUITreeVO.setId(obj.getId());
				easyUITreeVO.setText(obj.getDisplayName());
				easyUITreeVO.setName(obj.getName());
				easyUITreeVO.setLinkView(obj.getLinkView());
				easyUITreeVO.setState("closed");
				easyUITreeVO.setChildren(new ArrayList<EasyUITreeVO>());
				target.add(easyUITreeVO);
			}
		});
		return target;
	}

	private List<EasyUITreeVO> getChildren(Long id, List<ItemDataModel> itemDataModels) {
		List<EasyUITreeVO> trees = new ArrayList<EasyUITreeVO>();
		for(ItemDataModel itemModel:itemDataModels){
			if(itemModel.getParentId() == id){
				EasyUITreeVO retrunTree = new EasyUITreeVO();
				retrunTree.setId(itemModel.getId());
				retrunTree.setText(itemModel.getName());
				retrunTree.setState("closed");
				retrunTree.setChildren(this.getChildren(itemModel.getId(),itemDataModels));
				trees.add(retrunTree);
			}
		}
		return trees;
	}

	@Override
	public EasyUITreeVO getItemNodeInfo(long id) {
		ItemDataModel itemDataModel = pubInfoDAO.getItemNodeInfo(id);
		EasyUITreeVO itemNodeVO = new EasyUITreeVO();
		BeanUtils.copyProperties(itemDataModel, itemNodeVO);
		return itemNodeVO;
	}

	@Override
	public OperationResult save(Map<String, Object> map) {
		OperationResult operationResult = new OperationResult();
		ItemDataModel itemDataModel = BeanConvertUtil.mapToObject2(map, ItemDataModel.class);
		String idStr = (String) map.get("id");
		if(StringUtils.isEmpty(idStr)){
			String parentId = (String) map.get("parentId");
			itemDataModel.setParentId(Long.parseLong(parentId));
			super.insertInit(itemDataModel);
			pubInfoDAO.addItemData(itemDataModel);
		}else{
			itemDataModel.setId(Long.parseLong(idStr));
			super.updateInit(itemDataModel);
			pubInfoDAO.updateItemData(itemDataModel);
		}
		
		operationResult.setData(itemDataModel);
		return operationResult;
	}

}
