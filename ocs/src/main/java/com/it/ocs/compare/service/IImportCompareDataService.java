package com.it.ocs.compare.service;

import org.springframework.web.multipart.MultipartFile;

public interface IImportCompareDataService {
	public void importAmazonCompareData(MultipartFile file);
}
