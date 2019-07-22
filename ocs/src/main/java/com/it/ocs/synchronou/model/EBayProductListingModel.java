package com.it.ocs.synchronou.model;

import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.model.BaseModel;
import com.it.ocs.synchronou.vo.CategorySpecificsVO;
/**
 * 
 * @author yangguanbao
 * 描述：产品标识
 */
public class EBayProductListingModel extends BaseModel {


	private Long category_id;
	private Long marketplace_id;
	private String ean;
	private String isbn;
	private String upc;
	private String conditions;
	private String condition_help_url;
	private ResponseResult<CategorySpecificsVO> rows;
	
	
	public ResponseResult<CategorySpecificsVO> getRows() {
		return rows;
	}
	public void setRows(ResponseResult<CategorySpecificsVO> rows) {
		this.rows = rows;
	}
	public String getCondition_help_url() {
		return condition_help_url;
	}
	public void setCondition_help_url(String condition_help_url) {
		this.condition_help_url = condition_help_url;
	}
	public Long getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}
	public Long getMarketplace_id() {
		return marketplace_id;
	}
	public void setMarketplace_id(Long marketplace_id) {
		this.marketplace_id = marketplace_id;
	}
	public String getEan() {
		return ean;
	}
	public void setEan(String ean) {
		this.ean = ean;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getUpc() {
		return upc;
	}
	public void setUpc(String upc) {
		this.upc = upc;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	
	
}
