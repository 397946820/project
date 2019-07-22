package com.it.ocs.fourPX.vo;

import com.it.ocs.excel.annotation.ExcelLink;

public class FpxOutWarehouseOfflineVO implements java.io.Serializable {

	private static final long serialVersionUID = -6585144576014887418L;
	
	@ExcelLink(title = "Abnormal", index = 1)
	private String abnormal;

	@ExcelLink(title = "MerchantFulfillmentOrderID", index = 2)
	private String merchantfulfillmentorderid;

	@ExcelLink(title = "DisplayableOrderID", index = 3)
	private String displayableorderid;

	@ExcelLink(title = "DisplayableOrderDate", index = 4)
	private String displayableorderdate;

	@ExcelLink(title = "MerchantSKU", index = 5)
	private String merchantsku;

	@ExcelLink(title = "Quantity", index = 6)
	private String quantity;

	@ExcelLink(title = "MerchantFulfillmentOrderItemID", index = 7)
	private String merchantfulfillmentorderitemid;

	@ExcelLink(title = "GiftMessage", index = 8)
	private String giftmessage;

	@ExcelLink(title = "DisplayableComment", index = 9)
	private String displayablecomment;

	@ExcelLink(title = "PerUnitDeclaredValue", index = 10)
	private String perunitdeclaredvalue;

	@ExcelLink(title = "DisplayableOrderComment", index = 11)
	private String displayableordercomment;

	@ExcelLink(title = "DeliverySLA", index = 12)
	private String deliverysla;

	@ExcelLink(title = "AddressName", index = 13)
	private String addressname;

	@ExcelLink(title = "AddressFieldOne", index = 14)
	private String addressfieldone;

	@ExcelLink(title = "AddressFieldTwo", index = 15)
	private String addressfieldtwo;

	@ExcelLink(title = "AddressFieldThree", index = 16)
	private String addressfieldthree;

	@ExcelLink(title = "AddressCity", index = 17)
	private String addresscity;

	@ExcelLink(title = "AddressCountryCode", index = 18)
	private String addresscountrycode;

	@ExcelLink(title = "AddressStateOrRegion", index = 19)
	private String addressstateorregion;

	@ExcelLink(title = "AddressPostalCode", index = 20)
	private String addresspostalcode;

	@ExcelLink(title = "AddressPhoneNumber", index = 21)
	private String addressphonenumber;

	@ExcelLink(title = "NotificationEmail", index = 22)
	private String notificationemail;
}
