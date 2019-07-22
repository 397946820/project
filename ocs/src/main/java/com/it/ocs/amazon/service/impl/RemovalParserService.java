package com.it.ocs.amazon.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.amazon.dao.IAmazonReportBaseDAO;
import com.it.ocs.amazon.dao.IRemovalShipmentDAO;
import com.it.ocs.amazon.service.IAmazonReportParserService;

@Service
public class RemovalParserService extends AmazonReportParserBaseService implements IAmazonReportParserService {
	public final String _GET_FBA_FULFILLMENT_REMOVAL_SHIPMENT_DETAIL_DATA_ = "_GET_FBA_FULFILLMENT_REMOVAL_SHIPMENT_DETAIL_DATA_";
	@Autowired
	private IRemovalShipmentDAO removalShipmentDAO;

	@Override
	public void parserReportData() {
		super.parserDate();
	}

	@Override
	protected String getReportType() {
		return _GET_FBA_FULFILLMENT_REMOVAL_SHIPMENT_DETAIL_DATA_;
	}

	@Override
	protected IAmazonReportBaseDAO getDao() {
		return removalShipmentDAO;
	}

	@Override
	protected void saveDate(Map<String, Object> saveData) {
		if (!saveData.isEmpty()) {
			if (saveData.containsKey("REQUEST_DATE")) {
				String shipDate = saveData.get("REQUEST_DATE").toString();
				saveData.put("REQUEST_DATE", shipDate.replace("T", " ").substring(0, 19));
			}
			if (saveData.containsKey("SHIPMENT_DATE")) {
				String shipDate = saveData.get("SHIPMENT_DATE").toString();
				saveData.put("SHIPMENT_DATE", shipDate.replace("T", " ").substring(0, 19));
			}
			removalShipmentDAO.save(saveData);
		}

	}

}
