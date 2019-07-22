package com.it.ocs.fourPX.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

	
public class SellerInventoryLogModel extends BaseModel {
	public static final String JSON_UNQUALIFIED = "JSON_UNQUALIFIED";
	
	public static final String CODETYPE = "CODETYPE";

	@ClientUneditable
	@ExcelLink(title = "swlId", index = 2)
	private String swlId;

	@ClientUneditable
	@ExcelLink(title = "仓库代码", index = 3)
	private String warehouseCode;

	@ClientUneditable
	@ExcelLink(title = "sku", index = 4)
	private String sku;

	@ClientUneditable
	@ExcelLink(title = "skuId", index = 5)
	private String skuId;

	@ExcelLink(title = "产品名称", index = 6)
	private String skuName;

	@ExcelLink(title = "产品条码", index = 7)
	private String skuBarCode;

	@ExcelLink(title = "产品数字条码", index = 8)
	private String skuBarno;

	@ExcelLink(title = "单据号", index = 9)
	private String refCode;

	@ExcelLink(title = "业务类型", index = 10)
	private String codeType;

	@ExcelLink(title = "待入库库存", index = 11)
	private Integer inQuantity;

	@ExcelLink(title = "库内库存", index = 12)
	private Integer actualQuantity;

	@ExcelLink(title = "可销售库存", index = 13)
	private Integer availableQuantity;

	@ExcelLink(title = "待出库库存", index = 14)
	private Integer shippingQuantity;

	@ExcelLink(title = "数量", index = 15)
	private Integer warehouseQuantity;

	@ExcelLink(title = "4PX创建时间", index = 16)
	private Date createDate;

	@ExcelLink(title = "不合格库存", index = 17)
	private Integer unqualifiedGross;
	@ExcelLink(title = "细分业务类型", index = 18)
	private String codeTypeDetail;

	@ExcelLink(title = "业务区域", index = 19)
	private String busarea;
	
	public SellerInventoryLogModel() {
		
	}
	
	public SellerInventoryLogModel(SellerInventoryLogModel model) {
		super(model.getId(), model.getCreatedat(), model.getUpdatedat());
		this.swlId = model.swlId;
		this.warehouseCode = model.warehouseCode;
		this.sku = model.sku;
		this.skuId = model.skuId;
		this.skuName = model.skuName;
		this.skuBarCode = model.skuBarCode;
		this.skuBarno = model.skuBarno;
		this.refCode = model.refCode;
		this.codeType = model.codeType;
		this.inQuantity = model.inQuantity;
		this.actualQuantity = model.actualQuantity;
		this.availableQuantity = model.availableQuantity;
		this.shippingQuantity = model.shippingQuantity;
		this.warehouseQuantity = model.warehouseQuantity;
		this.createDate = model.createDate;
		this.unqualifiedGross = model.unqualifiedGross;
		this.codeTypeDetail = model.codeTypeDetail;
		this.busarea = model.busarea;
	}
	
	public String getBusarea() {
		return busarea;
	}

	public void setBusarea(String busarea) {
		this.busarea = busarea;
	}

	public String getCodeTypeDetail() {
		return codeTypeDetail;
	}

	public void setCodeTypeDetail(String codeTypeDetail) {
		this.codeTypeDetail = codeTypeDetail;
	}

	public String getSwlId() {
		return swlId;
	}
	public void setSwlId(String swlId) {
		this.swlId = swlId;
	}
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public String getSkuBarCode() {
		return skuBarCode;
	}
	public void setSkuBarCode(String skuBarCode) {
		this.skuBarCode = skuBarCode;
	}
	public String getSkuBarno() {
		return skuBarno;
	}
	public void setSkuBarno(String skuBarno) {
		this.skuBarno = skuBarno;
	}
	public String getRefCode() {
		return refCode;
	}
	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public Integer getInQuantity() {
		return inQuantity;
	}
	public void setInQuantity(Integer inQuantity) {
		this.inQuantity = inQuantity;
	}
	public Integer getActualQuantity() {
		return actualQuantity;
	}
	public void setActualQuantity(Integer actualQuantity) {
		this.actualQuantity = actualQuantity;
	}
	public Integer getAvailableQuantity() {
		return availableQuantity;
	}
	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	public Integer getShippingQuantity() {
		return shippingQuantity;
	}
	public void setShippingQuantity(Integer shippingQuantity) {
		this.shippingQuantity = shippingQuantity;
	}
	public Integer getWarehouseQuantity() {
		return warehouseQuantity;
	}
	public void setWarehouseQuantity(Integer warehouseQuantity) {
		this.warehouseQuantity = warehouseQuantity;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getUnqualifiedGross() {
		return unqualifiedGross;
	}
	public void setUnqualifiedGross(Integer unqualifiedGross) {
		this.unqualifiedGross = unqualifiedGross;
	}
}
