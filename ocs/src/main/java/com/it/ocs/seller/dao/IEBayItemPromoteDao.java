package com.it.ocs.seller.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.publication.model.PublicationModel;
import com.it.ocs.seller.model.ComboTreeModel;
import com.it.ocs.seller.model.EBayItemPromoteModel;

public interface IEBayItemPromoteDao extends IBaseDAO<EBayItemPromoteModel> {

	public List<PublicationModel> searchProduct(@Param("pubModel") Map<String, Object> map, @Param("startRow")int startRow, @Param("endRow")int endRow);

	public int searchCount(@Param("pubModel") Map<String, Object> map);

	public String getPromoteItemIds(String temId);

	public List<PublicationModel> getProductInfo(List idList);

	public void saveItems(Map<String, Object> map);

	public int deleteItemPromoteByIds(List<Long> idList);

	public List<PublicationModel> getItemsByRule(@Param("param")Map<String, Object> map);

	public List<ComboTreeModel> getItemType(@Param("siteId")String siteId);

	public List<ComboTreeModel> getItemStoreType(@Param("siteId")String siteId);

	public List<PublicationModel> getItemsByHot(@Param("param")Map<String, Object> map);

}
