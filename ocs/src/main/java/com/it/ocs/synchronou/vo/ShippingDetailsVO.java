//package com.it.ocs.synchronou.vo;
//
//import java.util.List;
//
//import com.ebay.soap.eBLBaseComponents.AmountType;
//import com.ebay.soap.eBLBaseComponents.CalculatedShippingDiscountType;
//import com.ebay.soap.eBLBaseComponents.InsuranceOptionCodeType;
//import com.ebay.soap.eBLBaseComponents.ShippingRateTypeCodeType;
//import com.ebay.soap.eBLBaseComponents.ShippingTypeCodeType;
//
//public class ShippingDetailsVO {
//
//	private Boolean allowPaymentEdit;
//	private Boolean applyShippingDiscount;
//	private Boolean globalShipping;
//
//	private Boolean changePaymentInstructions;
//
//	private AmountType insuranceFee;
//
//	private InsuranceOptionCodeType insuranceOption;
//
//	private Boolean insuranceWanted;
//
//	private Boolean paymentEdited;
//
//	private String paymentInstructions;
//
//	private String shippingRateErrorMessage;
//
//
//	private ShippingRateTypeCodeType shippingRateType;
//
//	private String shippingType;
//
//	private Integer sellingManagerSalesRecordNumber;
//
//	private Boolean thirdPartyCheckout;
//
//	private Boolean getItFast;
//
//
//	private String shippingServiceUsed;
//
//	private AmountType defaultShippingCost;
//
//	private String shippingDiscountProfileID;
//
//	private CalculatedShippingDiscountType calculatedShippingDiscount;
//
//	private Boolean promotionalShippingDiscount;
//
//
//	private String internationalShippingDiscountProfileID;
//
//	private Boolean internationalPromotionalShippingDiscount;
//
//	private AmountType codCost;
//
//	private List<String> excludeShipToLocation;
//
//	private Boolean sellerExcludeShipToLocationsPreference;
//
//	public Boolean getAllowPaymentEdit() {
//		return allowPaymentEdit;
//	}
//
//	public void setAllowPaymentEdit(Boolean allowPaymentEdit) {
//		this.allowPaymentEdit = allowPaymentEdit;
//	}
//
//	public Boolean getApplyShippingDiscount() {
//		return applyShippingDiscount;
//	}
//
//	public void setApplyShippingDiscount(Boolean applyShippingDiscount) {
//		this.applyShippingDiscount = applyShippingDiscount;
//	}
//
//	public Boolean getGlobalShipping() {
//		return globalShipping;
//	}
//
//	public void setGlobalShipping(Boolean globalShipping) {
//		this.globalShipping = globalShipping;
//	}
//
//	public Boolean getChangePaymentInstructions() {
//		return changePaymentInstructions;
//	}
//
//	public void setChangePaymentInstructions(Boolean changePaymentInstructions) {
//		this.changePaymentInstructions = changePaymentInstructions;
//	}
//
//	public AmountType getInsuranceFee() {
//		return insuranceFee;
//	}
//
//	public void setInsuranceFee(AmountType insuranceFee) {
//		this.insuranceFee = insuranceFee;
//	}
//
//	public InsuranceOptionCodeType getInsuranceOption() {
//		return insuranceOption;
//	}
//
//	public void setInsuranceOption(InsuranceOptionCodeType insuranceOption) {
//		this.insuranceOption = insuranceOption;
//	}
//
//	public Boolean getInsuranceWanted() {
//		return insuranceWanted;
//	}
//
//	public void setInsuranceWanted(Boolean insuranceWanted) {
//		this.insuranceWanted = insuranceWanted;
//	}
//
//	public Boolean getPaymentEdited() {
//		return paymentEdited;
//	}
//
//	public void setPaymentEdited(Boolean paymentEdited) {
//		this.paymentEdited = paymentEdited;
//	}
//
//	public String getPaymentInstructions() {
//		return paymentInstructions;
//	}
//
//	public void setPaymentInstructions(String paymentInstructions) {
//		this.paymentInstructions = paymentInstructions;
//	}
//
//	public String getShippingRateErrorMessage() {
//		return shippingRateErrorMessage;
//	}
//
//	public void setShippingRateErrorMessage(String shippingRateErrorMessage) {
//		this.shippingRateErrorMessage = shippingRateErrorMessage;
//	}
//
//	public ShippingRateTypeCodeType getShippingRateType() {
//		return shippingRateType;
//	}
//
//	public void setShippingRateType(ShippingRateTypeCodeType shippingRateType) {
//		this.shippingRateType = shippingRateType;
//	}
//
//	
//	public String getShippingType() {
//		return shippingType;
//	}
//
//	public void setShippingType(String shippingType) {
//		this.shippingType = shippingType;
//	}
//
//	public Integer getSellingManagerSalesRecordNumber() {
//		return sellingManagerSalesRecordNumber;
//	}
//
//	public void setSellingManagerSalesRecordNumber(Integer sellingManagerSalesRecordNumber) {
//		this.sellingManagerSalesRecordNumber = sellingManagerSalesRecordNumber;
//	}
//
//	public Boolean getThirdPartyCheckout() {
//		return thirdPartyCheckout;
//	}
//
//	public void setThirdPartyCheckout(Boolean thirdPartyCheckout) {
//		this.thirdPartyCheckout = thirdPartyCheckout;
//	}
//
//	public Boolean getGetItFast() {
//		return getItFast;
//	}
//
//	public void setGetItFast(Boolean getItFast) {
//		this.getItFast = getItFast;
//	}
//
//	public String getShippingServiceUsed() {
//		return shippingServiceUsed;
//	}
//
//	public void setShippingServiceUsed(String shippingServiceUsed) {
//		this.shippingServiceUsed = shippingServiceUsed;
//	}
//
//	public AmountType getDefaultShippingCost() {
//		return defaultShippingCost;
//	}
//
//	public void setDefaultShippingCost(AmountType defaultShippingCost) {
//		this.defaultShippingCost = defaultShippingCost;
//	}
//
//	public String getShippingDiscountProfileID() {
//		return shippingDiscountProfileID;
//	}
//
//	public void setShippingDiscountProfileID(String shippingDiscountProfileID) {
//		this.shippingDiscountProfileID = shippingDiscountProfileID;
//	}
//
//	public CalculatedShippingDiscountType getCalculatedShippingDiscount() {
//		return calculatedShippingDiscount;
//	}
//
//	public void setCalculatedShippingDiscount(CalculatedShippingDiscountType calculatedShippingDiscount) {
//		this.calculatedShippingDiscount = calculatedShippingDiscount;
//	}
//
//	public Boolean getPromotionalShippingDiscount() {
//		return promotionalShippingDiscount;
//	}
//
//	public void setPromotionalShippingDiscount(Boolean promotionalShippingDiscount) {
//		this.promotionalShippingDiscount = promotionalShippingDiscount;
//	}
//
//	public String getInternationalShippingDiscountProfileID() {
//		return internationalShippingDiscountProfileID;
//	}
//
//	public void setInternationalShippingDiscountProfileID(String internationalShippingDiscountProfileID) {
//		this.internationalShippingDiscountProfileID = internationalShippingDiscountProfileID;
//	}
//
//	public Boolean getInternationalPromotionalShippingDiscount() {
//		return internationalPromotionalShippingDiscount;
//	}
//
//	public void setInternationalPromotionalShippingDiscount(Boolean internationalPromotionalShippingDiscount) {
//		this.internationalPromotionalShippingDiscount = internationalPromotionalShippingDiscount;
//	}
//
//	public AmountType getCodCost() {
//		return codCost;
//	}
//
//	public void setCodCost(AmountType codCost) {
//		this.codCost = codCost;
//	}
//
//	public List<String> getExcludeShipToLocation() {
//		return excludeShipToLocation;
//	}
//
//	public void setExcludeShipToLocation(List<String> excludeShipToLocation) {
//		this.excludeShipToLocation = excludeShipToLocation;
//	}
//
//	public Boolean getSellerExcludeShipToLocationsPreference() {
//		return sellerExcludeShipToLocationsPreference;
//	}
//
//	public void setSellerExcludeShipToLocationsPreference(Boolean sellerExcludeShipToLocationsPreference) {
//		this.sellerExcludeShipToLocationsPreference = sellerExcludeShipToLocationsPreference;
//	}
//
//
//
//}
