package com.it.ocs.synchronou.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class EbaySmallPackageNewExportModel {
	@ExcelLink(title="Ebay account",index=0)
	private String account;
	@ExcelLink(title="Transaction Id",index=1)
	private String transactionId;
	@ExcelLink(title="Item Id",index=2)
	private String itemId;
	//Customer Number	  Customer Number   =Sales Record Number=order ID =订单号
	@ExcelLink(title="Customer Number",index=3)
	private String customerNumber;
	
	//Product
	@ExcelLink(title="Product",index=4)
	private String product;
	
	//Sales Record Number	
	@ExcelLink(title="Sales Record Number",index=5)
	private String salesRecordNumber;
	
	//User Id
	@ExcelLink(title="User Id",index=6)
	private String userId;
	
	//Buyer Fullname
	@ExcelLink(title="Buyer Fullname",index=7)
	private String name;
	//Buyer Phone Number
	@ExcelLink(title="Buyer Phone Number",index=8)
	private String phone;
	//Buyer Email	
	@ExcelLink(title="Buyer Email",index=9)
	private String email;
	//Buyer Address 1	
	@ExcelLink(title="Buyer Address 1",index=10)
	private String address1;
	//Buyer Address 2	
	@ExcelLink(title="Buyer Address 2",index=11)
	private String address2;
	//Buyer City	
	@ExcelLink(title="Buyer City",index=12)
	private String city;
	//Buyer State	
	@ExcelLink(title="Buyer State",index=13)
	private String state;
	//Buyer Zip	
	@ExcelLink(title="Buyer Zip",index=14)
	private String zip;
	//Buyer Country	
	@ExcelLink(title="Buyer Country",index=15)
	private String country;
	//Item Number	sku
	@ExcelLink(title="Item Number",index=16)
	private String sku;
	//Item Title	不填
	@ExcelLink(title="Item Title",index=17)
	private String itemTitle;
	
	//Custom Label	
	@ExcelLink(title="Custom Label",index=18)
	private String customLabel;
	//isreturn	
	@ExcelLink(title="isreturn",index=19)
	private String isReturn;
	//category
	@ExcelLink(title="category",index=20)
	private String category;

	
	//Quantity	
	@ExcelLink(title="Quantity",index=21)
	private String qty;
	//Sale Price	
	@ExcelLink(title="Sale Price",index=22)
	private String salePrice;
	
	//Shipping and Handling
	@ExcelLink(title="Shipping and Handling",index=23)
	private String shippingAndHanding;
	//US Tax
	@ExcelLink(title="US Tax",index=24)
	private String usTax;
	
	//Insurance	不填
	@ExcelLink(title="Insurance",index=25)
	private String insurance;
	//Total Price	
	@ExcelLink(title="Total Price",index=26)
	private String totalPrice;
	
	//Payment Method
	@ExcelLink(title="Payment Method",index=27)
	private String paymentMethod;
	
	//Sale Date	
	@ExcelLink(title="Sale Date",index=28)
	private String saleDate;
	//Checkout Date	
	@ExcelLink(title="Checkout Date",index=29)
	private String checkoutDate;
	//Paid on Date	
	@ExcelLink(title="Paid on Date",index=30)
	private String paidOnDate;
	//Shipped on Date
	@ExcelLink(title="Shipped on Date",index=31)
	private String shippedOnDate;
	
	//Feedback left
	@ExcelLink(title="Feedback left",index=32)
	private String feedbackLeft;
	//Feedback received	
	@ExcelLink(title="Feedback received",index=33)
	private String feedbackReceived;
	//Notes to yourself	
	@ExcelLink(title="Notes to yourself",index=34)
	private String notesToYourSelf;
	//Listed On	
	@ExcelLink(title="Listed On",index=35)
	private String listedOn;
	//Sold On
	@ExcelLink(title="Sold On",index=36)
	private String soldOn;
	//PayPal Transaction ID
	@ExcelLink(title="PayPal Transaction ID",index=37)
	private String paypalTransactionId;

	
	//Shipping Service	不填
	@ExcelLink(title="Shipping Service",index=38)
	private String shippingService;
	
	//Transaction ID
	@ExcelLink(title="Transaction ID",index=39)
	private String dTransactionID;
	
	//Order ID	
	@ExcelLink(title="Order ID",index=40)
	private String orderId;
	//declared value	不填
	@ExcelLink(title="declared value",index=41)
	private String declaredValue;
	//weight
	@ExcelLink(title="weight",index=42)
	private String weight;
	
	@ExcelLink(title="carrier",index=43)
	private String carrier;
	
	@ExcelLink(title="tranckingNumber",index=44)
	private String tranckingNumber;

}
