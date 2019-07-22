package com.it.ocs.ukinventory.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.sys.model.UserModel;
import com.it.ocs.sys.service.IUserService;
import com.it.ocs.ukinventory.dao.IInventoryFlowUKDAO;
import com.it.ocs.ukinventory.model.InventoryFlowUKModel;
import com.it.ocs.ukinventory.service.IInventoryFlowService;
import com.it.ocs.ukinventory.vo.InventoryFlowUKVO;
import com.mysql.jdbc.StringUtils;
@Service
public class InventoryFlowService implements IInventoryFlowService {
	@Autowired
	private IUserService userService;
	@Autowired
	private IInventoryFlowUKDAO inventoryFlowDAO;
	private static Map<String,String> BTYPE_MAP = Maps.newConcurrentMap();
	static {
		BTYPE_MAP.put("R", "R--良品转入");
		BTYPE_MAP.put("C", "C--客户良品退货");
		BTYPE_MAP.put("A", "A--亚马逊转入");
		BTYPE_MAP.put("S", "S--盘点");
		BTYPE_MAP.put("O", "O--订单出库");
		BTYPE_MAP.put("T", "T--转出给亚马逊");
	}
	
	@Override
	public ResponseResult<InventoryFlowUKVO> queryInventoryFlow(RequestParam param) {
		ResponseResult<InventoryFlowUKVO> result = new ResponseResult<>();
		InventoryFlowUKVO paramVO = BeanConvertUtil.mapToObject2(param.getParam(), InventoryFlowUKVO.class);
		doTime(paramVO);
		List<InventoryFlowUKModel> models = inventoryFlowDAO.queryByPage(paramVO, param.getStartRow(), param.getEndRow());
		List<InventoryFlowUKVO> ifvos = CollectionUtil.beansConvert(models, InventoryFlowUKVO.class);
		int count = inventoryFlowDAO.count(paramVO);
		List<Map<String,Object>> forSummaryModels = inventoryFlowDAO.queryForSummary();
		result.setFooter(getFooters(forSummaryModels));
		result.setRows(ifvos);
		result.setTotal(count);
		return result;
	}
	private void doTime(InventoryFlowUKVO paramVO) {
		if (null == paramVO)
			return;
		if (!StringUtils.isNullOrEmpty(paramVO.getFlowStarTime())) {
			paramVO.setFlowStarTime(paramVO.getFlowStarTime()+" 00:00:00");
		}
		if (!StringUtils.isNullOrEmpty(paramVO.getFlowEndTime())) {
			paramVO.setFlowEndTime(paramVO.getFlowEndTime()+" 23:59:59");
		}
		if (!StringUtils.isNullOrEmpty(paramVO.getUploadStarTime())) {
			paramVO.setUploadStarTime(paramVO.getUploadStarTime()+" 00:00:00");
		}
		if (!StringUtils.isNullOrEmpty(paramVO.getUploadEndTime())) {
			paramVO.setUploadEndTime(paramVO.getUploadEndTime()+" 23:59:59");
		}
	}
	private List<Map<String,Object>> getFooters(List<Map<String,Object>> summaryDatas) {
		List<Map<String,Object>> footers = Lists.newArrayList();
		CollectionUtil.each(summaryDatas, new IAction<Map<String,Object>>() {
			@Override
			public void excute(Map<String, Object> obj) {
				Map<String,Object> footer = new HashMap<>();
				if (null != obj.get("B_TYPE")) {
					if (BTYPE_MAP.containsKey(obj.get("B_TYPE"))) {
						footer.put("bType", BTYPE_MAP.get(obj.get("B_TYPE").toString()));
					} else {
						footer.put("bType", obj.get("B_TYPE").toString());
					}
					
				}
				footer.put("qty", obj.get("QTY"));
				footers.add(footer);
			}
		});
		return footers;
	}
	@Override
	public void syncCustomerRefoud() {
		List<Map<String,Object>> list = inventoryFlowDAO.getUnSyncRefound();
		CollectionUtil.each(list, new IAction<Map<String,Object>>() {
			@Override
			public void excute(Map<String, Object> map) {
				map.put("createBy", 145);
				map.put("lastUpdateBy", 145);
				int valcount = inventoryFlowDAO.valicount(map);
				if (valcount<=0) {
					inventoryFlowDAO.add(map);
				}
			}
		});
		inventoryFlowDAO.updateUKRefound();
	}
	
	
}
