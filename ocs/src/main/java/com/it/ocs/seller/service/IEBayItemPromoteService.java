package com.it.ocs.seller.service;

import java.util.List;
import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.publication.model.PublicationModel;
import com.it.ocs.publication.vo.PublicationVO;
import com.it.ocs.seller.model.ComboTreeModel;
import com.it.ocs.seller.vo.ItemPromoteVO;

public interface IEBayItemPromoteService {

	public ResponseResult<ItemPromoteVO> findItemPromoteList(RequestParam param);

	public OperationResult addOrEdit(Map<String, Object> map);

	public ResponseResult<PublicationVO> searchProduct(RequestParam param);

	public List<PublicationVO> getTemplateItems(String temId);

	public void saveItems(Map<String, Object> map);

	public OperationResult deleteItemPromoteByIds(String ids);

	public List<PublicationVO> getItemsByRule(Map<String, Object> map);

	public List<ComboTreeModel> getItemType(String siteId);

	public List<ComboTreeModel> getItemStoreType(String siteId);

}
