package com.it.ocs.cal.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.dao.IProductEntityDao;
import com.it.ocs.cal.model.PackageModel;
import com.it.ocs.cal.model.SKUModel;
import com.it.ocs.cal.service.IPackageSkuFBAService;

@Service
public class PackageSkuFBAService implements IPackageSkuFBAService {
	
	@Autowired
	protected IProductEntityDao productEntityDao;
	
	@Override
	public Map<String,List<PackageModel>> getGreatFBABySKU(String countryId,String skuName, int qty) {
		List<PackageModel> pmList = new ArrayList<>();
		List<PackageModel> greatList = new ArrayList<>();
		// 获取sku信息
		SKUModel sku = productEntityDao.getSKUInfoBySKU(skuName);
		//根据多种包装数量计算所有数据的FBA费用
		for(int i = 1;i<=qty;i++){
			sku.setQty(i);
			//获取组合信息
			List<PackageModel> list = sku.findAllConditions();
			PackageModel greatPm = null;
			for(PackageModel pm : list){
				pm.setCountryId(countryId);
				String fbaStr = "";
				if("AU".equals(countryId)){
					fbaStr = productEntityDao.getFBAForAU(pm);
				}else{
					fbaStr = productEntityDao.getFBA(pm);
				}
				String fba = "0";
				if(null != fbaStr && !"".equals(fbaStr)){
					fba = fbaStr.substring(0,fbaStr.length()-2);
				}
				Double fbaNum = new Double(fba);
				//获取最小FBA费用的组合方式
				if(null == greatPm || greatPm.getFba() > fbaNum){
					greatPm = pm;
				}
				pm.setFba(fbaNum);
				pmList.add(pm);
			}
			greatList.add(greatPm);
		}
		//按照包装数量排序最优FBA费用组合
		Collections.sort(greatList, new Comparator<PackageModel>() {
			@Override
			public int compare(PackageModel o1, PackageModel o2) {
				return  o1.getFba() - o2.getFba() >= 0 ? 1:-1;
			}
		});
		Map<String,List<PackageModel>> map = new HashMap<>();
		map.put("list", pmList);
		map.put("great", greatList);
		return map;
	}
	


}
