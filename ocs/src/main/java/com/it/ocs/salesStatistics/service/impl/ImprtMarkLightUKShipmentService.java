package com.it.ocs.salesStatistics.service.impl;

import org.springframework.stereotype.Service;

@Service("markLightUKShipment")
public class ImprtMarkLightUKShipmentService extends ImprtMarkBaseUKShipmentService {
	@Override
	protected String getPlatform() {
		return "light";
	}
}
