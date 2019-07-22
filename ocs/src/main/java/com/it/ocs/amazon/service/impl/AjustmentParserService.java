package com.it.ocs.amazon.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.amazon.dao.IAdjustmentInventoryDAO;
import com.it.ocs.amazon.dao.IAmazonReportBaseDAO;
import com.it.ocs.amazon.service.IAmazonReportParserService;

@Service
public class AjustmentParserService extends AmazonReportParserBaseService implements IAmazonReportParserService {
	public final String _GET_FBA_FULFILLMENT_INVENTORY_ADJUSTMENTS_DATA_ = "_GET_FBA_FULFILLMENT_INVENTORY_ADJUSTMENTS_DATA_";
	@Autowired
	private IAdjustmentInventoryDAO adjustmentInventoryDAO;

	@Override
	protected String getReportType() {
		return _GET_FBA_FULFILLMENT_INVENTORY_ADJUSTMENTS_DATA_;
	}

	@Override
	public void parserReportData() {
		super.parserDate();
	}

	@Override
	protected IAmazonReportBaseDAO getDao() {
		return adjustmentInventoryDAO;
	}

	@Override
	protected void saveDate(Map<String, Object> saveData) {
		if (!saveData.isEmpty()) {
			if (saveData.containsKey("ADJUSTED_DATE")) {
				String shipDate = saveData.get("ADJUSTED_DATE").toString();
				saveData.put("ADJUSTED_DATE", shipDate.replace("T", " ").substring(0, 19));
				adjustmentInventoryDAO.save(saveData);
			}
		}
	}

}
