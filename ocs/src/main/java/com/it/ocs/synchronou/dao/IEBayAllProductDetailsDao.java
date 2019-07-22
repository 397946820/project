package com.it.ocs.synchronou.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.synchronou.model.EBayAllProductDetailsModel;
import com.it.ocs.synchronou.vo.AllProductDetailsVO;

public interface IEBayAllProductDetailsDao extends IBaseDAO<EBayAllProductDetailsModel> {
	
	public void insertAllProductDetail(EBayAllProductDetailsModel allProductDetailsModel);
	
	public void updateAllProductDetail(EBayAllProductDetailsModel allProductDetailsModel);
	
	public List<EBayAllProductDetailsModel> selectAllProductDetails(@Param(value="starRow") Integer starRow,@Param(value="endRow") Integer endRow);
	
	public int getTotal();
	
	public List<EBayAllProductDetailsModel> internalSelectAllProductDetails();
}
