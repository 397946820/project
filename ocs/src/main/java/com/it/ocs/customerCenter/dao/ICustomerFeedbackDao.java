package com.it.ocs.customerCenter.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.customerCenter.model.CustomerFeedbackModel;
import com.it.ocs.customerCenter.vo.CustomerFeedbackVO;

@Repository
public interface ICustomerFeedbackDao extends IBaseDAO<CustomerFeedbackModel> {

	
	public List<CustomerFeedbackModel> selectCustomerFeecBacks(@Param("feedback")Map<String , Object> map,@Param("startRow") int startRow,@Param("endRow") int endRow,@Param("order")String order,@Param("sort")String sort);

	public void remove(@Param("feedbacks_id")Long feedbacks_id);
	
	public Integer getTotal(@Param("feedback")Map<String,Object> map);
	
	public List<Map<String, Object>> selectCustomerF(@Param("feedback")Map<String, String> map);
	
	public void updateFeedbacks(List<CustomerFeedbackVO> customerFeedbackVOs);
	
	public void insertFeedback(List<CustomerFeedbackVO> customerFeedbackVOs);
}
