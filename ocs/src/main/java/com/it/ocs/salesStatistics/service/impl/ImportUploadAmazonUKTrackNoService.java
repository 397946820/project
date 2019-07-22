package com.it.ocs.salesStatistics.service.impl;

import org.springframework.stereotype.Service;

@Service("uploadAmazonUKTrackNo")
public class ImportUploadAmazonUKTrackNoService extends ImportUploadBaseUKTrackNoService {
	@Override
	protected String getPlatform() {
		return "amazon";
	}
}
