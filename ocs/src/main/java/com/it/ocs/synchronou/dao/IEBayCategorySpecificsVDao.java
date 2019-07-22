package com.it.ocs.synchronou.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.synchronou.model.EBayCategorySpecificsVModel;

@Repository
public interface IEBayCategorySpecificsVDao {
	
	public void insertCategorySpecificsVList(List<EBayCategorySpecificsVModel> eBayCategorySpecificsVModels);
	public void updateCategorySpecificsVList(List<EBayCategorySpecificsVModel> eCategorySpecificsVModels);

	public Long selectCategorySpecificsMaxCID(@Param("marketplaceId")Long marketplaceId);

	public List<EBayCategorySpecificsVModel> findCategorySpecificsVList(@Param("name")String name,@Param("category_id")Long category_Id,@Param("marketplace_id")Long marketplace_id);
}
