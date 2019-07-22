package com.it.ocs.synchronou.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.synchronou.model.EBayCategorySpecificsModel;

@Repository
public interface IEBayCategorySpecificsDao {
	
	public void insertCategorySpecificsList(List<EBayCategorySpecificsModel> eBayCategorySpecificsModels);
	
	public void updateCategorySpecificsList(List<EBayCategorySpecificsModel> eBayCategorySpecificsModels);
	
	public List<EBayCategorySpecificsModel> findCategorySpecificsByCIDAndMID(@Param("category_Id")Long category_Id,@Param("marketplace_id")Long marketplace_id);
	
	public List<EBayCategorySpecificsModel> findCategorySpecificsByMID(@Param(value="starRow") Integer starRow,@Param(value="endRow") Integer endRow,@Param("marketplace_id")Long marketplace_id,@Param("category_Id")Long category_Id);

	public int getTotal(@Param("marketplace_id")Long marketplace_id,@Param("category_Id")Long category_Id);

	public List<EBayCategorySpecificsModel> internalFindCategorySpecificsList(@Param("category_id")Long category_Id,@Param("marketplace_id")Long marketplace_id);
	
	public EBayCategorySpecificsModel internalFindSpecificsByNameAanCIDAanMID(@Param("name")String name,@Param("category_id")Long category_Id,@Param("marketplace_id")Long marketplace_id);
}
