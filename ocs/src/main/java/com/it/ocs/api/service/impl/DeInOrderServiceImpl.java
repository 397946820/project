package com.it.ocs.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.it.ocs.api.constant.WarehouseConstant;
import com.it.ocs.api.dao.IDeInOrderDao;
import com.it.ocs.api.dao.IWmsProviderTokenDao;
import com.it.ocs.api.ex.Oms2WmsException;
import com.it.ocs.api.model.DeInWmsOrderMainModel;
import com.it.ocs.api.service.IDeInOrderService;
import com.it.ocs.api.service.IDeOperLogService;
import com.it.ocs.api.service.IDeWarehouseService;
import com.it.ocs.api.utils.CommonUtils;
import com.it.ocs.api.vo.InOrderVO;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;

@Service
public class DeInOrderServiceImpl extends BaseService implements IDeInOrderService {

	@Autowired
	private IDeInOrderDao deInOrderDao;
	
	@Autowired
	private IDeOperLogService deOperLogService;
	
	@Autowired
	private IWmsProviderTokenDao wmsProviderTokenDao;
	
	@Autowired
	private IDeWarehouseService deWarehouseService;
	
	@Override
	public ResponseResult<InOrderVO> queryByPage(RequestParam param) {
		Map<String, Object> filter = param.getParam();
		int count = this.deInOrderDao.countInOrder(filter);
		ResponseResult<InOrderVO> result = new ResponseResult<InOrderVO>();
		result.setRows(this.deInOrderDao.queryInOrderByPage(filter, param.getStartRow(), param.getEndRow()));
		result.setTotal(count);
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public OperationResult cancel(Long id, String orderId, Long orderOcsId) throws Exception {
		if(null == id || StringUtils.isBlank(orderId) || null == orderOcsId) {
			throw new IllegalArgumentException(">>> cancel >>> Illegal parameters: id=" + id + "; orderId=" + orderId + "; orderOcsId=" + orderOcsId);
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("orderId", orderId);
		DeInWmsOrderMainModel main = this.deInOrderDao.queryInOrderOne(param);
		if(null == main) {
			throw new Oms2WmsException("找不到当前入库单信息（orderId=" + orderId + "）");
		} else if(!"0".equals(main.getIsSendWms()) && !"3".equals(main.getIsSendWms())) {
			//只有当入库单处于未推送、推送失败的状态时，才可以取消；否则，不允许取消
			throw new Oms2WmsException("当前入库单处于" + CommonUtils.status2text(main.getIsSendWms()) + "状态，不可取消（orderId=" + orderId + "）");
		}
		
		Map<String, Object> mainmap = new HashMap<String, Object>();
		mainmap.put("id", id);
		mainmap.put("orderId", orderId);
		mainmap.put("isSendWms", WarehouseConstant.IS_SEND_WMS_CANCELLED); //将订单置为取消状态
		this.deInOrderDao.updateMain(mainmap);
		
		String description = "入库单（orderId=" + orderId + "）已成功被取消";
		this.deOperLogService.recordOperSuccessLog("cancel-in-order", id, description);
		return new OperationResult(0, description, null, null);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public OperationResult resend(Long id, String orderId, Long orderOcsId) throws Exception {
		Assert.notNull(id, ">>> resend in order >>> The 'id' must not be null.");
		Assert.hasText(orderId, ">>> resend in order >>> The 'orderId' must has text.");
		Assert.notNull(orderOcsId, ">>> resend in order >>> The 'orderOcsId' must not be null.");
		
		String token = wmsProviderTokenDao.getProviderToken(CommonUtils.newIdentity()).getToken();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("orderId", orderId);
		Map<String, Object> data = this.deInOrderDao.getDeInOrder(param);
		if(null == data) {
			throw new Oms2WmsException("找不到当前入库单信息（orderId=" + orderId + "; orderOcsId=" + orderOcsId + "）");
		} else if(!"3".equals(data.get("IS_SEND_WMS").toString())) {
			//只有当入库单处于推送失败的状态时，才人工干预进行推送；否则，不允许
			throw new Oms2WmsException("当前入库单处于" + CommonUtils.status2text(data.get("IS_SEND_WMS").toString()) + "状态，不可推送（orderId=" + data.get("ORDER_ID").toString() + "）");
		}
		
		boolean success = this.deWarehouseService.sendDeInOrders(data, token);
		String description = "入库单（orderId=" + orderId + "）推送" + (success ? "成功" : "失败");
		this.deOperLogService.recordOperLog("send-in-order", id, success ? "success" : "failed", description);
		return new OperationResult(success ? 0 : 1, description, null, null);
	}

	@Override
	public ResponseResult<InOrderVO> queryWmsClaimOrderByPage(RequestParam param) {
		Map<String, Object> filter = param.getParam();
		int count = this.deInOrderDao.countWmsClaimOrder(filter);
		ResponseResult<InOrderVO> result = new ResponseResult<InOrderVO>();
		result.setRows(this.deInOrderDao.queryWmsClaimOrderByPage(filter, param.getStartRow(), param.getEndRow()));
		result.setTotal(count);
		return result;
	}

	@Override
	public ResponseResult<InOrderVO> queryWmsClaimOwnerOrderByPage(RequestParam param) {
		param.getParam().put("waitClaim", "oms_nonclaim"); //只有未关联认领单的入库单才可以作为与认领单的所有者（认领单对应的入库单）
		return this.queryByPage(param);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public OperationResult claiming(Long claim, Long owner) throws Exception {
		Assert.notNull(claim, ">>> claim in order >>> The 'claim' must not be null.");
		Assert.notNull(owner, ">>> claim in order >>> The 'owner' must not be null.");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", owner);
		DeInWmsOrderMainModel mainOwner = this.deInOrderDao.queryInOrderOne(param);
		if(null == mainOwner) {
			throw new Oms2WmsException("找不到可关联当前认领单的入库单信息（id=" + owner + "）！");
		} else if(!"oms_nonclaim".equals(mainOwner.getWaitClaim()) || null != mainOwner.getClaimId()) {
			throw new Oms2WmsException("入库单（id=" + owner + "）已经关联了认领单，不可再次关联认领单！");
		}
		
		param.clear(); 
		
		param.put("id", claim);
		DeInWmsOrderMainModel mainClaim = this.deInOrderDao.queryClaimOrderOne(param);
		if(null == mainClaim) {
			throw new Oms2WmsException("找不到当前认领单（id=" + owner + "）！");
		} else if(!"wms_unclaimed".equals(mainClaim.getWaitClaim()) || null != mainClaim.getClaimId()) {
			throw new Oms2WmsException("认领单（id=" + claim + "）已经被认领，不可再次被认领！"); 
		}
		
		if(StringUtils.isNotBlank(mainClaim.getRma()) && !mainClaim.getRma().equals(mainOwner.getRma())) {
			throw new Oms2WmsException("认领单RMA（rma=" + mainClaim.getRma() + "）与入库单RMA（rma=" + mainOwner.getRma() + "）不相等，无法建立认领关系！"); 
		}
		
		//更新入库单：入库单（owner）认领了认领单（claim），入库单更新为已关联认领单
		Map<String, Object> ownerUpdate = new HashMap<String, Object>();
		ownerUpdate.put("id", owner);
		ownerUpdate.put("waitClaim", "oms_bindclaim");
		ownerUpdate.put("claimId", claim);
		this.deInOrderDao.updateMain(ownerUpdate);
		
		//更新认领单：认领单（claim）被入库单（owner）认领，认领单更新为已认领且关联入库单
		Map<String, Object> claimUpdate = new HashMap<String, Object>();
		claimUpdate.put("id", claim);
		claimUpdate.put("waitClaim", "wms_claimed");
		claimUpdate.put("claimId", owner);
		this.deInOrderDao.updateMain(claimUpdate);
		
		String description = "认领单（id=" + claim + "）被入库单（id=" + owner + "）成功认领！";
		this.deOperLogService.recordOperLog("claim-in-order", claim, "success", description);
		return new OperationResult(0, description, null, null);
	}

	@Override
	public ResponseResult<InOrderVO> queryMainById(Long id) {
		Assert.notNull(id, ">>> queryMainById in order >>> The 'id' must not be null.");
		RequestParam param = new RequestParam();
		param.setParam(new HashMap<String, Object>());
		param.getParam().put("id", id);
		return this.queryByPage(param);
	}

}
