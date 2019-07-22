package com.it.ocs.customerCenter.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.customerCenter.model.ClaimProductModel;
import com.it.ocs.customerCenter.vo.ClaimProductVO;
@Repository
public interface IClaimProductDao extends IBaseDAO<ClaimProductModel> {

	public List<ClaimProductModel> selectClaimProducts(@Param("claimProduct")Map<String, Object> map,@Param("startRow") int startRow,@Param("endRow") int endRow,@Param("order")String order,@Param("sort")String sort);
	
	public int getTotal(@Param("claimProduct")Map<String, Object> map);
	
	public void insertClaimProduct(List<ClaimProductVO> claimProductModels);
	
	public void updateClaimProduct(List<ClaimProductVO> claimProductModels);
	
	public void remove(@Param("id")Long id,@Param("userId")Long userId);
	
	public List<Map<String, Object>> selectClaimProduct(@Param("claimProduct")Map<String, String>map);
	
	public List<ClaimProductModel> selectClaimProductByOID(@Param("orderId")String orderId,@Param("id")Long id);
}
