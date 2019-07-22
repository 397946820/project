package com.it.ocs.salesStatistics.service.impl;

import org.springframework.stereotype.Service;

@Service("uploadLightUKTrackNo")
public class ImportUploadLightUKTrackNoService extends ImportUploadBaseUKTrackNoService {
	@Override
	protected String getPlatform() {
		return "light";
	}
}
