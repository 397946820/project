package com.it.ocs.salesStatistics.service.impl;

import org.springframework.stereotype.Service;

@Service("markAmazonUKShipment")
public class ImprtMarkAmazonUKShipmentService extends ImprtMarkBaseUKShipmentService {
	@Override
	protected String getPlatform() {
		return "amazon";
	}
}
