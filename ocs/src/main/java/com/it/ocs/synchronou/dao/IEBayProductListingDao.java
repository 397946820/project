package com.it.ocs.synchronou.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.synchronou.model.EBayProductListingModel;

@Repository
public interface IEBayProductListingDao extends IBaseDAO<EBayProductListingModel> {

	public void insertProductListing(EBayProductListingModel eBayProductListingModel);
	
	public void updateProductListing(EBayProductListingModel eBayProductListingModels);
	
	public EBayProductListingModel internalSelectProductListingsByMIDAndCID(@Param("category_id")Long category_id,@Param("marketplace_id")Long marketplace_id);
	
	public List<EBayProductListingModel> selectProductListingsByMIDOrCID(@Param(value="starRow") Integer starRow,@Param(value="endRow") Integer endRow,@Param("marketplace_id")Long marketplace_id,@Param("category_id")Long category_id);

	public int getTotal(@Param("marketplace_id")Long marketplace_id,@Param("category_id")Long category_id);
	
	public EBayProductListingModel internalSelectProductListingByMIDOrCID(@Param("category_id")Long category_Id,@Param("marketplace_id")Long marketplace_id);
}
