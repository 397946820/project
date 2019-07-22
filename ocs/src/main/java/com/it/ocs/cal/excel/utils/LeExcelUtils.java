package com.it.ocs.cal.excel.utils;

import java.util.HashMap;
import java.util.Map;

public class LeExcelUtils {

	private static Map<String, Object> PRICE_CALCULATE = null;
	private static Map<String, Object> PRICE_BIND_CALCULATE = null;
	private static Map<String, Object> COST_CALCULATE = null;

	public static Map<String, Object> constructTemplateObj(String templateInfo) {
		if (templateInfo.equals("template")) {
			return getPRICE_CALCULATE();
		} else if (templateInfo.equals("template_variant")) {
			return getPRICE_BIND_CALCULATE();
		} else if (templateInfo.equals("count")) {
			return getCOST_CALCULATE();
		} else {
			return null;
		}
	}

	private static Map<String, Object> getPRICE_CALCULATE() {
		if (null == PRICE_CALCULATE) {
			PRICE_CALCULATE = new HashMap<>();
			PRICE_CALCULATE.put("PLATFORM", "EBAY");
			PRICE_CALCULATE.put("COUNTRYID", "DE");
			PRICE_CALCULATE.put("SKU", "400019-WW-US");
			PRICE_CALCULATE.put("SHIPPINGTYPE", "af");
			PRICE_CALCULATE.put("TRANSACTIONMODE", "0");
			PRICE_CALCULATE.put("ISCOSTOF", "0");
			PRICE_CALCULATE.put("ISSTORAGECHARGES", "0");
			PRICE_CALCULATE.put("QTY", "1");
			PRICE_CALCULATE.put("PROFITRATEACTION1", "1");
			PRICE_CALCULATE.put("PROFITRATEACTION2", "1");
			PRICE_CALCULATE.put("PROFITRATEACTION3", "1");
			PRICE_CALCULATE.put("FINALPRICE1", " ");
			PRICE_CALCULATE.put("FINALPRICE2", " ");
			PRICE_CALCULATE.put("FINALPRICE3", " ");
		}
		return PRICE_CALCULATE;
	}

	private static Map<String, Object> getPRICE_BIND_CALCULATE() {
		if (null == PRICE_BIND_CALCULATE) {
			PRICE_BIND_CALCULATE = new HashMap<>();
			PRICE_BIND_CALCULATE.put("PLATFORM", "EBAY");
			PRICE_BIND_CALCULATE.put("COUNTRYID", "DE");
			PRICE_BIND_CALCULATE.put("SKU", "400019-WW-US,400019-WW-US");
			PRICE_BIND_CALCULATE.put("SHIPPINGTYPE", "af,sf");
			PRICE_BIND_CALCULATE.put("TRANSACTIONMODE", "0");
			PRICE_BIND_CALCULATE.put("ISCOSTOF", "0");
			PRICE_BIND_CALCULATE.put("ISSTORAGECHARGES", "0");
			PRICE_BIND_CALCULATE.put("QTY", "1,1");
			PRICE_BIND_CALCULATE.put("PROFITRATEACTION1", "1");
			PRICE_BIND_CALCULATE.put("PROFITRATEACTION2", "1");
			PRICE_BIND_CALCULATE.put("PROFITRATEACTION3", "1");
			PRICE_BIND_CALCULATE.put("FINALPRICE1", " ");
			PRICE_BIND_CALCULATE.put("FINALPRICE2", " ");
			PRICE_BIND_CALCULATE.put("FINALPRICE3", " ");
		}
		return PRICE_BIND_CALCULATE;
	}

	private static Map<String, Object> getCOST_CALCULATE() {
		if (null == COST_CALCULATE) {
			COST_CALCULATE = new HashMap<>();
			COST_CALCULATE.put("COUNTRY", "DE");
			COST_CALCULATE.put("REGION", "欧洲");
			COST_CALCULATE.put("SKU", "400019-WW-US");
			COST_CALCULATE.put("SHIPPINGTYPE", "af");
			COST_CALCULATE.put("QTY", "1");
			COST_CALCULATE.put("PROFITRATEACTION", "1");
			COST_CALCULATE.put("FINALPRICE", null);
			COST_CALCULATE.put("PACKINGQTY", null);
			COST_CALCULATE.put("COUNT", null);
			COST_CALCULATE.put("UNITPRICE", null);
			COST_CALCULATE.put("PRICE", null);
		}
		return COST_CALCULATE;
	}

}
