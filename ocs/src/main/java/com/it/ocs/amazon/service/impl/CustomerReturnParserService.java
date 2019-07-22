package com.it.ocs.amazon.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.amazon.dao.IAmazonReportBaseDAO;
import com.it.ocs.amazon.dao.ICustomerReturnDAO;
import com.it.ocs.amazon.service.IAmazonReportParserService;

@Service
public class CustomerReturnParserService extends AmazonReportParserBaseService implements IAmazonReportParserService {
	@Autowired
	private ICustomerReturnDAO customerReturnDAO;
	public final String _GET_FBA_FULFILLMENT_CUSTOMER_RETURNS_DATA_ = "_GET_FBA_FULFILLMENT_CUSTOMER_RETURNS_DATA_";

	@Override
	protected String getReportType() {
		return _GET_FBA_FULFILLMENT_CUSTOMER_RETURNS_DATA_;
	}

	@Override
	public void parserReportData() {
		super.parserDate();
	}

	@Override
	protected void saveDate(Map<String, Object> saveData) {
		if (!saveData.isEmpty()) {
			if (saveData.containsKey("RETURN_DATE")) {
				String shipDate = saveData.get("RETURN_DATE").toString();
				saveData.put("RETURN_DATE", shipDate.replace("T", " ").substring(0, 19));
				customerReturnDAO.save(saveData);
			}
		}
	}

	@Override
	protected IAmazonReportBaseDAO getDao() {
		return customerReturnDAO;
	}

}
