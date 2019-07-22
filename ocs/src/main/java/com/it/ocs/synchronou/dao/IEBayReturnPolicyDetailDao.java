package com.it.ocs.synchronou.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.synchronou.model.EBayReturnPolicyDetailModel;

public interface IEBayReturnPolicyDetailDao extends IBaseDAO<EBayReturnPolicyDetailModel> {

	public void insertReturnPolicyDetail(EBayReturnPolicyDetailModel eBayReturnPolicyDetailModel);
	
	public void updateReturnPolicyDetail(EBayReturnPolicyDetailModel eBayReturnPolicyDetailModel);
	
	public List<EBayReturnPolicyDetailModel> selectReturnPolicySiteIds();
	
	public List<EBayReturnPolicyDetailModel> selectReturnPolicyDetails(@Param(value="starRow") Integer starRow,@Param(value="endRow") Integer endRow);
	
	public int getTotal();
	
	public List<EBayReturnPolicyDetailModel> selectReturnPolicysBySiteId(Long site_id);
	
}
