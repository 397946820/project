package com.it.ocs.task.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.task.model.LightOrderItemModel;

@Repository
public interface ILightOrderItemDao{

		public void insertLightOrderItems(List<LightOrderItemModel> lightOrderItemModels);
		
		public void updateLightOrderItems(List<LightOrderItemModel> lightOrderItemModels);
		
		public List<LightOrderItemModel> findLightOrderItems(List<LightOrderItemModel> lightOrderItemModels);
		
}
