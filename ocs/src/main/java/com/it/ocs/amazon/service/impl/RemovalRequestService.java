package com.it.ocs.amazon.service.impl;

import org.springframework.stereotype.Service;

import com.it.ocs.amazon.service.IAmazonRequestReportService;
import com.it.ocs.synchronou.model.AmazonAccountModel;
@Service
public class RemovalRequestService extends AmazonRequestBaseService implements IAmazonRequestReportService {
	public final String _GET_FBA_FULFILLMENT_REMOVAL_SHIPMENT_DETAIL_DATA_ = "_GET_FBA_FULFILLMENT_REMOVAL_SHIPMENT_DETAIL_DATA_";

	@Override
	protected String getReportType() {
		return _GET_FBA_FULFILLMENT_REMOVAL_SHIPMENT_DETAIL_DATA_;
	}

	@Override
	public void genRequests() {
		super.doRequests();
	}

}
