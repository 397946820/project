package com.it.ocs.fourPX.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.fourPX.model.SellerInventoryLogModel;
import com.it.ocs.fourPX.model.UnqualifiedModel;
import com.it.ocs.fourPX.vo.FPXStockVO;

public interface SellerInventoryLogDao extends Base4pxDao<SellerInventoryLogModel> {
	
	int batchUpdate(@Param("param")Map<String,Object> map);
	
	Map<String, Object> selectBySwlId(@Param("swlId") String swlId);
	
	void deleteUnqualifieds(@Param("parentId") Long parentId);

	void insertUnqualified(@Param("item") UnqualifiedModel unqualified);

	String selectMaxCreateDate(@Param("busarea") String busarea);

	public List<String> getAllSkus();
	
	public int updateCodeTypeDetail(@Param("param")Map<String,Object> map);

	public void add4pxInventory(Map<String, Object> map);

	public int countFpxStockInfoHis(@Param("param")Map<String, Object> map);

	public List<FPXStockVO> queryFpxStockInfoHisByPage(@Param("param")Map<String, Object> map, @Param("start")int startRow, @Param("end")int endRow);

	public List<Map<String, Object>> countQtyByInventoryType(@Param("item")Map<String, Object> filter);

}
