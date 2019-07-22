package com.it.ocs.customerCenter.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.customerCenter.vo.ClaimProductVO;

public interface IClaimProductService {

	/**
	 * 
	 * @param param
	 * @return
	 * 描述：分页查找
	 */
	public ResponseResult<ClaimProductVO> selectClaimProducts(RequestParam param);
  
	/**
	 * 
	 * @param claimProductVOs
	 * @return
	 * 描述：保存
	 */
	public OperationResult batchSaveClaimProduct(ClaimProductVO[] claimProductVOs);
	
	/**
	 * 
	 * @param claimProductVOs
	 * @return
	 * 描述：批量添加
	 */
	public OperationResult insertClaimProduct(List<ClaimProductVO> claimProductVOs);
	/**
	 * 
	 * @param claimProductVOs
	 * @return
	 * 描述：批量修改
	 */
	public OperationResult updateClaimProduct(List<ClaimProductVO> claimProductVOs);

	/**
	 * 
	 * @param id
	 * @return 删除
	 */
	public OperationResult remove(Long id);
	
	public OperationResult selectClaimProductByOID(String orderId,Long id);
}
