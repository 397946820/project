package com.it.ocs.synchronou.util;

import java.util.ArrayList;
import java.util.List;

import com.it.ocs.synchronou.model.EBayCategoryModel;
import com.it.ocs.synchronou.service.impl.BaseHttpsService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CategoryHttps extends BaseHttpsService{
	
	private static String CHILD="childCategoryTreeNodes";
	private static String CATEGORY="category";
	private static Long categoryTreeId;
	private static Long categoryTreeVersion;
	private List<EBayCategoryModel> ebayCategoryModels= new ArrayList<>();
	

	public List<EBayCategoryModel> jsonToModel(JSONObject jsonObject) {
		
		JSONObject rootJson  =  jsonObject.getJSONObject("rootCategoryNode");
		
		categoryTreeId = Long.parseLong(jsonObject.getString("categoryTreeId"));
		categoryTreeVersion = Long.parseLong(jsonObject.getString("categoryTreeVersion"));
		
		parsingJson(rootJson);
		return ebayCategoryModels;
	}
	

	public void parsingJson(JSONObject json){
		
		
		EBayCategoryModel categoryModel = new EBayCategoryModel();
		
		categoryModel.setMarketplace_id(categoryTreeId);
		
		categoryModel.setCategorytreeversion(categoryTreeVersion);
		
		if (json.has("leafCategoryTreeNode")) {
			
			categoryModel.setLeaf_category_tree_node(json.getString("leafCategoryTreeNode"));
		}
		if(json.has("parentCategoryTreeNodeHref")){
			String r = json.getString("parentCategoryTreeNodeHref");
			categoryModel.setParent_category_tree_node_href(r);
			categoryModel.setParent_category_id(Long.parseLong(r.substring(r.indexOf("=")+1, r.length())));
		}
		if(json.has("parentCategoryTreeNodeHref")){
			String r = json.getString("parentCategoryTreeNodeHref");
			categoryModel.setParent_category_tree_node_href(r);
			categoryModel.setParent_category_id(Long.parseLong(r.substring(r.indexOf("=")+1, r.length())));
		}
		if(json.has("categoryTreeNodeLevel")){
			categoryModel.setCategory_tree_node_level(json.getLong("categoryTreeNodeLevel"));
		}
		JSONObject categoryKey = json.getJSONObject(CATEGORY);
		categoryModel.setCategory_id(categoryKey.getLong("categoryId"));
		categoryModel.setName(categoryKey.getString("categoryName"));
		if(json.has(CHILD)){
			categoryModel.setChild_category_tree_nodes("true");
			ebayCategoryModels.add(categoryModel);
			JSONArray childArray = json.getJSONArray(CHILD);
			for(int i=0; i<childArray.size();i++){
				parsingJson(childArray.getJSONObject(i));
			}
		}else{
			ebayCategoryModels.add(categoryModel);
		}
	}


	public List<EBayCategoryModel> getEbayCategoryModels() {
		return ebayCategoryModels;
	}


	public void setEbayCategoryModels(List<EBayCategoryModel> ebayCategoryModels) {
		this.ebayCategoryModels = ebayCategoryModels;
	}
	
}
