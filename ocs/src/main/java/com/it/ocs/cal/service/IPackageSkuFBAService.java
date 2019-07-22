package com.it.ocs.cal.service;

import java.util.List;
import java.util.Map;

import com.it.ocs.cal.model.PackageModel;

public interface IPackageSkuFBAService {
	
	public Map<String,List<PackageModel>> getGreatFBABySKU(String countryId,String sku,int qty);
}
