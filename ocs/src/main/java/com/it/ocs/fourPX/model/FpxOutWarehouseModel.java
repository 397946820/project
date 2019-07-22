package com.it.ocs.fourPX.model;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.it.ocs.excel.annotation.ExcelLink;

/**
 * ORM数据实体： 4PX出库单信息
 * @author zhouyancheng
 *
 */
public class FpxOutWarehouseModel extends BaseModel {

	@ClientUneditable
	private String keys;

	@ClientUneditable
	@ExcelLink(title = "平台", index = 2)
	private String platform;

	@ClientUneditable
	@ExcelLink(title = "Order ID", index = 3)
	private String orderid;

	@ExcelLink(title = "业务状态", index = 4)
	private String status;
	
	/**
	 * 产品所在的仓库代码
	 */
	@ClientUneditable
	@ExcelLink(title = "仓库代码", index = 5)
	private String warehouseCode;
	
	/**
	 * OMS订单参考号
	 */
	@ClientUneditable
	@ExcelLink(title = "OMS订单参考号", index = 6)
	private String referenceCode;
	
	/**
	 * 运送的渠道代号
	 */
	@ExcelLink(title = "渠道代码", index = 7)
	private String carrierCode;
	
	/**
	 * 保险类型代码
	 */
	@ClientUneditable
	@ExcelLink(title = "保险类型", index = 8)
	private String insureType;
	
	/**
	 * 保险金额（如果保险类型是6P，保险金额必须填。 其他是非必须）
	 */
	@ClientUneditable
	private Double insureMoney;
	
	/**
	 * 销售交易号
	 */
	@ClientUneditable
	private String sellCode;
	
	/**
	 * 	如果是ODA(偏远地区附加费)，是否走货(Y：下单时，即使订单是ODA 单也会提示成功，走货后扣除ODA 费用；N：下单时，如果是ODA 单，则提示下单失败)
	 */
	@ClientUneditable
	@ExcelLink(title = "ODA", index = 9)
	private String remoteArea;
	
	/**
	 * 订单描述
	 */
	@ExcelLink(title = "订单描述", index = 10)
	private String description;
	
	/**
	 * 推送时间
	 */
	@ClientUneditable
	@ExcelLink(title = "推送时间", index = 11)
	private Date pushedat;

	/**
	 * 反馈时间
	 */
	@ClientUneditable
	@ExcelLink(title = "反馈时间", index = 12)
	private Date feedbackat;
	
	/**
	 * 重量
	 */
	@ClientUneditable
	@ExcelLink(title = "重量", index = 13)
	private Double weight;
	
	/**
	 * 4PX订单参考号
	 */
	@ClientUneditable
	@ExcelLink(title = "4PX订单参考号", index = 14)
	private String documentCode;

	/**
	 * 4PX订单状态
	 */
	@ClientUneditable
	@ExcelLink(title = "4PX订单状态", index = 15)
	private String fpxstatus;
	
	public FpxOutWarehouseModel() {
		
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public FpxOutWarehouseModel(FpxOutWarehouseModel model) {
		BeanUtils.copyProperties(model, this);
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getReferenceCode() {
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getInsureType() {
		return insureType;
	}

	public void setInsureType(String insureType) {
		this.insureType = insureType;
	}

	public Double getInsureMoney() {
		return insureMoney;
	}

	public void setInsureMoney(Double insureMoney) {
		this.insureMoney = insureMoney;
	}

	public String getSellCode() {
		return sellCode;
	}

	public void setSellCode(String sellCode) {
		this.sellCode = sellCode;
	}

	public String getRemoteArea() {
		return remoteArea;
	}

	public void setRemoteArea(String remoteArea) {
		this.remoteArea = remoteArea;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getPushedat() {
		return pushedat;
	}

	public void setPushedat(Date pushedat) {
		this.pushedat = pushedat;
	}

	public Date getFeedbackat() {
		return feedbackat;
	}

	public void setFeedbackat(Date feedbackat) {
		this.feedbackat = feedbackat;
	}

	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}

	public String getFpxstatus() {
		return fpxstatus;
	}

	public void setFpxstatus(String fpxstatus) {
		this.fpxstatus = fpxstatus;
	}

}
