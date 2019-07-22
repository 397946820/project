package com.it.ocs.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.sys.model.OrderApproveModel;
import com.it.ocs.sys.model.ReturnOrderItemModel;

public interface IOrderApproveDao {

	public List<OrderApproveModel> queryDataByPage(@Param("param")Map<String, Object> param, @Param("start")int startRow, @Param("end")int endRow);

	public int queryDataCount(@Param("param")Map<String, Object> param);

	public List<ReturnOrderItemModel> getInfoById(Map<String, Object> map);
	
	public int saveApproveData(Map<String, Object> data);
	
	public int cancelOrder(@Param("param") Map<String, Object> param);

	public void addDataToFinal(Map<String, Object> data);

	public void addReplaceOrRefound(Map<String, Object> data);

	public List<Map<String, Object>> queryData(@Param("param")Map<String, Object> params);

	public Map<String, Object> getOrderById(Object id);

	public List<Map<String, Object>> getItemsByParentId(Object id);

	/**
	 * 查询sys_return_order表指定id对应已审批通过的单的被引用数量
	 * @param id sys_return_order表的主键id
	 * @return 被引用数量
	 */
	public int refcount(Long id);

}
