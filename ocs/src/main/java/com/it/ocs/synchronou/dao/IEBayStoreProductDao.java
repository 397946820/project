package com.it.ocs.synchronou.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.synchronou.model.EBayStoreProductModel;
import com.it.ocs.synchronou.vo.StoreProductVO;

@Repository
public interface IEBayStoreProductDao extends IBaseDAO<EBayStoreProductModel> {

	public void insertStoreProducts(List<EBayStoreProductModel> eBayStoreProductModels);
	
	public void updateStoreProducts(List<EBayStoreProductModel> eBayStoreProductModels);
	
	public List<EBayStoreProductModel> selectStoreProductsByGlobalIdAndStoreName(@Param(value="starRow") Integer starRow,@Param(value="endRow") Integer endRow,@Param("globalID")String globalID,@Param("storeName")String storeName);
	
	public List<EBayStoreProductModel> interiorSelectStoreProductsByGlobalId(@Param("globalID")String globalID);

	public int getTotal(@Param("globalID")String globalID,@Param("storeName")String storeName);
}
