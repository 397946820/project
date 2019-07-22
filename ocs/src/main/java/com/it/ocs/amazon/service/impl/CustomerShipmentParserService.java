package com.it.ocs.amazon.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.amazon.dao.IAmazonReportBaseDAO;
import com.it.ocs.amazon.dao.ICustomerShipmentDAO;
import com.it.ocs.amazon.service.IAmazonReportParserService;

@Service
public class CustomerShipmentParserService extends AmazonReportParserBaseService implements IAmazonReportParserService {
	@Autowired
	private ICustomerShipmentDAO customerShipmentDAO;
	public final String _GET_FBA_FULFILLMENT_CUSTOMER_SHIPMENT_SALES_DATA_ = "_GET_FBA_FULFILLMENT_CUSTOMER_SHIPMENT_SALES_DATA_";

	@Override
	protected String getReportType() {
		return _GET_FBA_FULFILLMENT_CUSTOMER_SHIPMENT_SALES_DATA_;
	}

	@Override
	public void parserReportData() {
		super.parserDate();
	}

	@Override
	protected void saveDate(Map<String, Object> saveData) {
		if (!saveData.isEmpty()) {
			if (saveData.containsKey("SHIPMENT_DATE")) {
				String shipDate = saveData.get("SHIPMENT_DATE").toString();
				saveData.put("SHIPMENT_DATE", shipDate.replace("T", " ").substring(0,19));
				customerShipmentDAO.save(saveData);
			}
		}
		
	}

	@Override
	protected IAmazonReportBaseDAO getDao() {
		return customerShipmentDAO;
	}
}
