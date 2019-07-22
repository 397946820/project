//package com.it.ocs.synchronou.mapping;
//
//import java.util.HashMap;
//
//import com.ebay.soap.eBLBaseComponents.BuyerPaymentMethodCodeType;
//
//public class BuyerPaymentMethodCodeTypeEnum {
//
//	public static BuyerPaymentMethodCodeType searchPaymentMethod(String param){
//		
//		HashMap<String, BuyerPaymentMethodCodeType> map = new HashMap<>();
//		map.put("AmEx", BuyerPaymentMethodCodeType.AM_EX);
//		map.put("CashInPerson", BuyerPaymentMethodCodeType.CASH_IN_PERSON);
//		map.put("CashOnPickup", BuyerPaymentMethodCodeType.CASH_ON_PICKUP);
//		map.put("CCAccepted", BuyerPaymentMethodCodeType.CC_ACCEPTED);
//		map.put("COD", BuyerPaymentMethodCodeType.COD);
//		map.put("CODPrePayDelivery", BuyerPaymentMethodCodeType.COD_PRE_PAY_DELIVERY);
//		map.put("CreditCard", BuyerPaymentMethodCodeType.CREDIT_CARD);
//		map.put("CustomCode", BuyerPaymentMethodCodeType.CUSTOM_CODE);
//		map.put("Diners",BuyerPaymentMethodCodeType.DINERS);
//		map.put("DirectDebit", BuyerPaymentMethodCodeType.DIRECT_DEBIT);
//		map.put("Discover", BuyerPaymentMethodCodeType.DISCOVER);
//		map.put("ELV", BuyerPaymentMethodCodeType.ELV);
//		map.put("Escrow", BuyerPaymentMethodCodeType.ESCROW);
//		map.put("IntegratedMerchantCreditCard", BuyerPaymentMethodCodeType.INTEGRATED_MERCHANT_CREDIT_CARD);
//		map.put("LoanCheck", BuyerPaymentMethodCodeType.LOAN_CHECK);
//		map.put("MOCC", BuyerPaymentMethodCodeType.MOCC);
//		map.put("MoneyXferAccepted", BuyerPaymentMethodCodeType.MONEY_XFER_ACCEPTED);
//		map.put("MoneyXferAcceptedInCheckout", BuyerPaymentMethodCodeType.MONEY_XFER_ACCEPTED_IN_CHECKOUT);
//		map.put("None", BuyerPaymentMethodCodeType.NONE);
//		map.put("OtherOnlinePayments", BuyerPaymentMethodCodeType.OTHER_ONLINE_PAYMENTS);
//		map.put("PaisaPayAccepted", BuyerPaymentMethodCodeType.PAISA_PAY_ACCEPTED);
//		map.put("PaisaPayEscrow", BuyerPaymentMethodCodeType.PAISA_PAY_ESCROW);
//		map.put("PaisaPayEscrowEMI", BuyerPaymentMethodCodeType.PAISA_PAY_ESCROW_EMI);
//		map.put("PaymentSeeDescription", BuyerPaymentMethodCodeType.PAYMENT_SEE_DESCRIPTION);
//		map.put("PayOnPickup", BuyerPaymentMethodCodeType.PAY_ON_PICKUP);
//		map.put("PayPal", BuyerPaymentMethodCodeType.PAY_PAL);
//		map.put("PayPalCredit", BuyerPaymentMethodCodeType.PAY_PAL_CREDIT);
//		map.put("PayUponInvoice", BuyerPaymentMethodCodeType.PAY_UPON_INVOICE);
//		map.put("PersonalCheck", BuyerPaymentMethodCodeType.PERSONAL_CHECK);
//		map.put("PostalTransfer", BuyerPaymentMethodCodeType.POSTAL_TRANSFER);
//		map.put("PrePayDelivery", BuyerPaymentMethodCodeType.PRE_PAY_DELIVERY);
//		map.put("VisaMC", BuyerPaymentMethodCodeType.VISA_MC);
//		if (map.get(param)!=null) {
//			return map.get(param);
//		}else{
//			return null;
//		}
//		
//	}
//}
