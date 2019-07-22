package com.it.ocs.salesStatistics.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.ex.BussinessException;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.salesStatistics.dao.ILightMarkShipmentDao;
import com.it.ocs.salesStatistics.model.LightMarkShipmentModel;
import com.it.ocs.salesStatistics.service.ILightMarkShipmentService;
import com.it.ocs.salesStatistics.service.ILightSaleOrderService;
import com.it.ocs.salesStatistics.vo.LightMarkShipmentVO;
import com.it.ocs.sys.dao.IUserDAO;
import com.it.ocs.sys.model.UserModel;
import com.it.ocs.task.service.IAmazonOrderService;

@Service
public class LightMarkShipmentServiceImpl implements ILightMarkShipmentService {
	
	private final static Logger logger = Logger.getLogger(LightMarkShipmentServiceImpl.class);

	@Autowired
	private ILightMarkShipmentDao lightMarkShipmentDao;
	
	@Autowired
	private ILightSaleOrderService iLightSaleOrderService;
	
	@Autowired
	private IAmazonOrderService amazonOrderService;
	
	@Autowired
	private IUserDAO userDao;
	
	@Override
	public ResponseResult<LightMarkShipmentVO> queryByPage(RequestParam param, String platform) {
		Map<String, Object> filter = param.getParam() == null ? new HashMap<String, Object>() : param.getParam();
		filter.put("platform", platform);
		int count = this.lightMarkShipmentDao.count(filter);
		ResponseResult<LightMarkShipmentVO> result = new ResponseResult<LightMarkShipmentVO>();
		List<LightMarkShipmentVO> rows = new ArrayList<LightMarkShipmentVO>();
		Map<Long, String> userCache = new HashMap<Long, String>();
		CollectionUtil.each(this.lightMarkShipmentDao.queryByPage(filter, param.getStartRow(), param.getEndRow()), new IAction<LightMarkShipmentModel>() {
			private String getMarkerText(Long marker) {
				if(null == marker) {
					return null;
				}
				if(!userCache.containsKey(marker)) {
					UserModel user = userDao.getById(marker);
					if(null != user) {
						userCache.put(marker, user.getUserName());
					}
				}
				return userCache.get(marker);	
			}
			
			@Override
			public void excute(LightMarkShipmentModel model) {
				try {
					LightMarkShipmentVO vo = new LightMarkShipmentVO(model);
					vo.setMarkerText(this.getMarkerText(vo.getMarker()));
					rows.add(vo);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		});
		result.setRows(rows);
		result.setTotal(count);
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public OperationResult cancel(String platform, Long id) throws Exception {
		Assert.notNull(id, "This parameter 'id' cannot be null");
		LightMarkShipmentModel model = this.lightMarkShipmentDao.get(id);
		if(null == model || !LightMarkShipmentModel.TNUPLOADSTATUS_WAITING.equals(model.getTnUploadStatus())) {
			throw new BussinessException("找不到当前（id=" + id + "）要取消的且状态属于待上传的数据！");
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("platform", platform);
		param.put("orderId", model.getOrderId());
		param.put("itemId", model.getItemId());
		param.put("account", model.getAccount());
		param.put("tnUploadStatus", LightMarkShipmentModel.TNUPLOADSTATUS_CANCELED);
		this.lightMarkShipmentDao.update(param);
		return new OperationResult(0, "当前待上传数据（orderId=" + model.getOrderId() + ";itemId=" + model.getItemId() + "）已成功取消！", null, null);
	}

	@Override
	public List<LightMarkShipmentModel> scanWaitingUploads(String platform) {
		Assert.hasText(platform, "The parameter 'platform' cannot be null.");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("platform", platform);
		param.put("tnUploadStatus", LightMarkShipmentModel.TNUPLOADSTATUS_WAITING);
		param.put("tn01_isnotnull", null);
		param.put("carrier01_isnotnull", null);
		return this.lightMarkShipmentDao.query(param);
	}

	@Override
	public Map<String, Object> queryProduct(String platform, String orderId, Long detailId) {
		Assert.hasText(platform, "The parameter 'platform' cannot be null.");
		Assert.hasText(orderId, "The parameter 'orderId' cannot be empty.");
		Assert.notNull(detailId, "The parameter 'detailId' cannot be null.");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("platform", platform);
		param.put("orderId", orderId);
		param.put("detailId", detailId);
		return this.lightMarkShipmentDao.queryLightProduct(param);
	}

	private static final String REISSUE_ORDER_PREFIX = "W_";
	
	@Override
	public OperationResult uploadTrackingNumbers(String platform, List<LightMarkShipmentModel> models) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, String> failed = new HashMap<String, String>();
		data.put("failed", failed);
		
		//基于同一的订单进行明细分组......
		Map<String, List<LightMarkShipmentModel>> order2detail = new HashMap<String, List<LightMarkShipmentModel>>();
		CollectionUtil.each(models, new IAction<LightMarkShipmentModel>() {
			@Override
			public void excute(LightMarkShipmentModel model) {
				if(!order2detail.containsKey(model.getOrderId())) {
					order2detail.put(model.getOrderId(), new ArrayList<LightMarkShipmentModel>());
				}
				order2detail.get(model.getOrderId()).add(model);
			}
		});
		
		for (Entry<String, List<LightMarkShipmentModel>> entry : order2detail.entrySet()) {
			OperationResult result = null;
			String exmsg = null;
			try {
				result = this.uploadTrackingNumber(platform, entry.getValue());
			} catch (Exception e) {
				exmsg = e.getMessage();
				logger.error(exmsg, e);
			}
			boolean success = null != result && 0 == result.getErrorCode();
			String cause = success ? null : ((null == exmsg ? "" : exmsg) +  (null == result || null == result.getDescription() ? "" : result.getDescription()));
			if(!success) {
				cause = "创建跟踪号失败" + (StringUtils.isBlank(cause) ? "。" : ":");
				failed.put(entry.getKey(), cause);
			}
			
			for (LightMarkShipmentModel model : entry.getValue()) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("platform", platform);
				param.put("orderId", model.getOrderId());
				param.put("itemId", model.getItemId());
				param.put("account", model.getAccount());
				param.put("tnUploadStatus", success ? LightMarkShipmentModel.TNUPLOADSTATUS_UPLOADED : LightMarkShipmentModel.TNUPLOADSTATUS_FAILED);
				param.put("shippedAt", success ? new Date() : null);
				param.put("cause", cause);
				this.lightMarkShipmentDao.update(param);
			}
		}
		//关于errorCode: 0-全部上传成功; 1-全部上传失败; 2-一部分上传成功，一部分上传失败
		return new OperationResult(failed.isEmpty() ? 0 : (failed.size() == order2detail.size() ? 1 : 2), null, data, null);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private OperationResult uploadTrackingNumber(String platform, List<LightMarkShipmentModel> sameOrderModels) throws Exception {
		OperationResult or = null;
		String orderId = sameOrderModels.get(0).getOrderId();
		if("light".equals(platform)) {
			if(orderId.startsWith(REISSUE_ORDER_PREFIX)) {
				String[] reissue = orderId.split("_"); //补发单格式："W_" + OrderId + "_" + times
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("tarckingNum", this.mergeTrackingNumbers(sameOrderModels));
				param.put("orderId", reissue[1]);
				param.put("tarckingService", sameOrderModels.get(0).getCarrier01());
				param.put("times", reissue[2]);
				param.put("platform", "light");
				or = this.iLightSaleOrderService.handleReissueTranNumber(param);
			} else {
				List<Map<String, Object>> ships = new ArrayList<Map<String, Object>>();
				List<Map<String,Object>> items = new ArrayList<>();
				Map<String, Object> param = null;
				for (LightMarkShipmentModel model : sameOrderModels) {
					param = new HashMap<String, Object>();
					param.put("tranNumber", model.getTn01());
					param.put("carrier", model.getCarrier01());
					ships.add(param);
					if(StringUtils.isNotBlank(model.getTn02()) && StringUtils.isNotBlank(model.getCarrier02())) {
						param = new HashMap<String, Object>();
						param.put("tranNumber", model.getTn02());
						param.put("carrier", model.getCarrier02());
						ships.add(param);
					}
					if(StringUtils.isNotBlank(model.getTn03()) && StringUtils.isNotBlank(model.getCarrier03())) {
						param = new HashMap<String, Object>();
						param.put("tranNumber", model.getTn03());
						param.put("carrier", model.getCarrier03());
						ships.add(param);
					}
					if(StringUtils.isNotBlank(model.getTn04()) && StringUtils.isNotBlank(model.getCarrier04())) {
						param = new HashMap<String, Object>();
						param.put("tranNumber", model.getTn04());
						param.put("carrier", model.getCarrier04());
						ships.add(param);
					}
					
					String[] array = model.getItemId().split("-");
					Map<String, Object> product = this.queryProduct(platform, orderId, Long.valueOf(array[0]));
					param = new HashMap<String, Object>();
					param.put("qty", product.get("qty"));
					param.put("orderId", orderId);
					param.put("sku", product.get("sku"));
					param.put("order_item_id", array[1]);
					items.add(param);
				}
				or = this.iLightSaleOrderService.uploadTranNumber(orderId, ships, items);
			}
		} else if("amazon".equals(platform)) {
			if(orderId.startsWith(REISSUE_ORDER_PREFIX)) {
				String[] reissue = orderId.split("_"); //补发单格式："W_" + OrderId + "_" + times
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("tarckingNum", this.mergeTrackingNumbers(sameOrderModels));
				param.put("orderId", reissue[1]);
				param.put("tarckingService", sameOrderModels.get(0).getCarrier01());
				param.put("times", reissue[2]);
				param.put("platform", "amazon");
				param.put("account", "DE");
				or = this.amazonOrderService.handleReissueTranNumber(param);
			} else {
				throw new RuntimeException("不支持亚马逊平台非补发单（orderId=" + orderId + "）上传跟踪号！");
			}
		} else {
			throw new RuntimeException("不支持该平台（platform=" + platform + "）进行跟踪号上传！");
		}
		return or;
	}
	
	private String mergeTrackingNumbers(List<LightMarkShipmentModel> sameOrderModels) {
		StringBuilder builder = new StringBuilder();
		for (LightMarkShipmentModel model : sameOrderModels) {
			builder.append(builder.length() > 0 ? "," : "").append(model.getTn01())
				   .append(model.getTn02() == null ? "" : ("," + model.getTn02()))
				   .append(model.getTn03() == null ? "" : ("," + model.getTn03()))
				   .append(model.getTn04() == null ? "" : ("," + model.getTn04()));
		}
		return builder.toString();
	}
}
