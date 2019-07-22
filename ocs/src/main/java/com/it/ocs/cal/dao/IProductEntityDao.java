package com.it.ocs.cal.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.cal.model.PackageModel;
import com.it.ocs.cal.model.ProductEntityModel;
import com.it.ocs.cal.model.ProductOtherModel;
import com.it.ocs.cal.model.SKUDiscontinueModel;
import com.it.ocs.cal.model.SKUModel;

@Repository
public interface IProductEntityDao extends IBaseDao<ProductEntityModel> {

	Long findEntityIdBySku(String sku);

	List<String> findAllSku();
	
	List<ProductEntityModel> findAllActivated();

	List<ProductEntityModel> findAllByUserId(@Param(value = "userId")Long userId);

	ProductEntityModel queryEntityBySku(String sku);

	List<ProductEntityModel> findAllByUser(@Param(value = "userId")Long userId);

	List<Long> findUserIdBySku(@Param(value = "sku")String sku);

	List<ProductEntityModel> findAllByUserAndStatus(@Param(value = "userId")Long userId, @Param(value = "status")String status);

	public List<SKUDiscontinueModel> getSkuDisInfoBySKUAndPlatform(@Param("sku")String sku, @Param("platform")String platform);

	public int skuDisInfoIsExist(Map<String, Object> sd);

	public void addSkuDisInfo(Map<String, Object> sd);

	public void updateSkuDisInfo(Map<String, Object> sd);

	public SKUModel getSKUInfoBySKU(String skuName);

	public String getFBA(PackageModel pm);

	public String getFBAForAU(PackageModel pm);

	public int skuExist(String sku);
}
