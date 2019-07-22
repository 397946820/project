package com.it.ocs.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.api.model.DeInWmsOrderMainModel;
import com.it.ocs.api.vo.InOrderVO;

/**
 * 德国仓入库单Dao接口
 * @author zhouyancheng
 *
 */
public interface IDeInOrderDao {

	/**
	 * 查询数据条数
	 * @param param 过滤条件
	 * @return
	 */
	int countInOrder(@Param("param") Map<String, Object> param);

	/**
	 * 分页查询入库单数据
	 * @param param 过滤条件
	 * @param startRow 分页开始
	 * @param endRow 分页结束
	 * @return
	 */
	List<InOrderVO> queryInOrderByPage(@Param("param") Map<String, Object> param, @Param("start") int startRow, @Param("end") int endRow);

	/**
	 * 查找入库单数据（返回一条记录）
	 * @param param 过滤条件
	 * @return
	 */
	DeInWmsOrderMainModel queryInOrderOne(@Param("param") Map<String, Object> param);

	/**
	 * 更新入库单主表数据
	 * @param main 待更新的数据
	 */
	void updateMain(@Param("param") Map<String, Object> main);

	/**
	 * 获取入库单
	 * @param 
	 * param {@link java.util.Map}: <code>key</code>={@link java.lang.String}; <code>value</code>={@link java.lang.Object}
	 * <p>
	 * 必须包含<code>key</code>="<code>id</code>", <code>value</code>={@link java.lang.Long}（出库单主键Id）；</br>
	 * 必须包含<code>key</code>="<code>orderId</code>", <code>value</code>={@link java.lang.String}（出库单oderId）；</br>
	 * </p>
	 * @return {@link java.util.Map}（key={@link java.lang.String}; value={@link java.lang.Object}）
	 */
	Map<String, Object> getDeInOrder(@Param("param") Map<String, Object> param);

	List<Map<String, Object>> getDeInOrderExportData(@Param("param") Map<String, Object> param);

	/**
	 * 获取OMS入库单
	 * @param rma
	 * @return
	 */
	DeInWmsOrderMainModel getOmsInOrder(@Param("rma") String rma);

	/**
	 * 获取WMS退货认领单
	 * @param rma
	 * @param warehouseId
	 * @return
	 */
	DeInWmsOrderMainModel getWmsClaimOrder(@Param("rma") String rma, @Param("warehouseId") String warehouseId);

	int countWmsClaimOrder(@Param("param") Map<String, Object> param);

	List<InOrderVO> queryWmsClaimOrderByPage(@Param("param") Map<String, Object> param, @Param("start") int startRow, @Param("end") int endRow);

	/**
	 * 查找入库单数据（返回一条记录）
	 * @param param 过滤条件
	 * @return
	 */
	DeInWmsOrderMainModel queryClaimOrderOne(@Param("param") Map<String, Object> param);
}
