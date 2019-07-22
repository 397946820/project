package com.it.ocs.cal.model;

import com.it.ocs.cal.utils.ExcelRead;
import com.it.ocs.cal.utils.ModelProp;

//各国FBA规则 实体类
public class FbaRuleModel extends ImportModel {

	@ExcelRead(title = "国家")
	@ModelProp(name = "国家", colIndex = 0, nullable = false)
	private String countryId;

	@ExcelRead(title = "FBA标题")
	@ModelProp(name = "FBA标题", colIndex = 1, nullable = false)
	private String title;

	@ExcelRead(title = "处理费")
	@ModelProp(name = "处理费", colIndex = 2, nullable = false)
	private Double handlingFee;

	@ExcelRead(title = "包装费")
	@ModelProp(name = "包装费", colIndex = 3, nullable = false)
	private Double pickpackFee;

	@ExcelRead(title = "长")
	@ModelProp(name = "长", colIndex = 4, nullable = false)
	private Double length;

	@ExcelRead(title = "宽")
	@ModelProp(name = "宽", colIndex = 5, nullable = false)
	private Double width;

	@ExcelRead(title = "高")
	@ModelProp(name = "高", colIndex = 6, nullable = false)
	private Double height;

	@ExcelRead(title = "标志")
	@ModelProp(name = "标志", colIndex = 7, nullable = false)
	private String flag;

	@ExcelRead(title = "起始重量")
	@ModelProp(name = "起始重量", colIndex = 8, nullable = false)
	private Double fromWeight;

	@ExcelRead(title = "结束重量")
	@ModelProp(name = "结束重量", colIndex = 9, nullable = false)
	private Double toWeight;

	@ExcelRead(title = "最大重量")
	@ModelProp(name = "最大重量", colIndex = 10, nullable = false)
	private Double maxWeight;

	@ExcelRead(title = "价格")
	@ModelProp(name = "价格", colIndex = 11, nullable = false)
	private String price;

	@ExcelRead(title = "EFN费用")
	@ModelProp(name = "EFN费用", colIndex = 12, nullable = false)
	private Double efnFee;

	@ExcelRead(title = "FBA额外费用")
	@ModelProp(name = "FBA额外费用", colIndex = 13, nullable = false)
	private Double extraFee;

	@ExcelRead(title = "适用月份")
	@ModelProp(name = "适用月份", colIndex = 14, nullable = false)
	private String isMonth;

	@ExcelRead(title = "排序")
	@ModelProp(name = "排序", colIndex = 15, nullable = false)
	private Integer sortOrder;

	public FbaRuleModel() {

	}

	public FbaRuleModel(String countryId,Integer sortOrder) {
		this.countryId = countryId;
		this.sortOrder = sortOrder;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId == null ? null : countryId.trim();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag == null ? null : flag.trim();
	}

	public Double getHandlingFee() {
		return handlingFee;
	}

	public void setHandlingFee(Double handlingFee) {
		this.handlingFee = handlingFee;
	}

	public Double getPickpackFee() {
		return pickpackFee;
	}

	public void setPickpackFee(Double pickpackFee) {
		this.pickpackFee = pickpackFee;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getFromWeight() {
		return fromWeight;
	}

	public void setFromWeight(Double fromWeight) {
		this.fromWeight = fromWeight;
	}

	public Double getToWeight() {
		return toWeight;
	}

	public void setToWeight(Double toWeight) {
		this.toWeight = toWeight;
	}

	public Double getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(Double maxWeight) {
		this.maxWeight = maxWeight;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price == null ? null : price.trim();
	}

	public Double getEfnFee() {
		return efnFee;
	}

	public void setEfnFee(Double efnFee) {
		this.efnFee = efnFee;
	}

	public Double getExtraFee() {
		return extraFee;
	}

	public void setExtraFee(Double extraFee) {
		this.extraFee = extraFee;
	}

	public String getIsMonth() {
		return isMonth;
	}

	public void setIsMonth(String isMonth) {
		this.isMonth = isMonth == null ? null : isMonth.trim();
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

}
