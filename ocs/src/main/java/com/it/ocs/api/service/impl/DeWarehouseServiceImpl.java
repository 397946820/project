package com.it.ocs.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.it.ocs.api.constant.WarehouseConstant;
import com.it.ocs.api.dao.IDeInOrderDao;
import com.it.ocs.api.dao.IDeWarehouseDao;
import com.it.ocs.api.dao.IWmsProviderTokenDao;
import com.it.ocs.api.ex.Oms2WmsException;
import com.it.ocs.api.ex.SkuInventoryException;
import com.it.ocs.api.model.DeAbnormalReasonModel;
import com.it.ocs.api.model.DeInWmsOrderDetailModel;
import com.it.ocs.api.model.DeInWmsOrderMainModel;
import com.it.ocs.api.model.DeOutWmsOrderDetailModel;
import com.it.ocs.api.model.DeOutWmsOrderMainModel;
import com.it.ocs.api.model.DeWmsOperateLogModel;
import com.it.ocs.api.service.IDeWarehouseService;
import com.it.ocs.api.utils.AbnormalReasonUtils;
import com.it.ocs.api.utils.CommonUtils;
import com.it.ocs.api.utils.DateTimeHelper;
import com.it.ocs.api.utils.SpringPropertyUtil;
import com.it.ocs.api.vo.AbnormalReasonVO;
import com.it.ocs.api.vo.OutOrderChangeVO;
import com.it.ocs.api.vo.OutOrderVO;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IActionExt;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.Counter;
import com.it.ocs.common.util.StringUtil;
import com.it.ocs.eda.dao.IEDADao;
import com.it.ocs.eda.model.EBayOrderShippingModel;
import com.it.ocs.eda.service.impl.EDAUploadTrackingNumberService;
import com.it.ocs.salesStatistics.service.ILightSaleOrderService;
import com.it.ocs.salesStatistics.service.IWalmartSaleOrderService;
import com.it.ocs.synchronou.service.impl.EbayOrderShipNumberUploadService;
import com.it.ocs.synchronou.util.JsonConvertUtil;
import com.it.ocs.sys.model.UserModel;
import com.it.ocs.task.service.IAmazonOrderService;

import net.sf.json.JSONObject;

/**
* @ClassName: DeWarehouseServiceImpl 
* @Description: 
* @author wgc 
* @date 2018年4月9日 上午10:37:14 
*
 */
@Service
public class DeWarehouseServiceImpl extends BaseService implements IDeWarehouseService {
	
	private static final Logger logger = Logger.getLogger(DeWarehouseServiceImpl.class);
	
	@Autowired
	private IDeWarehouseDao deWarehouseDao;
	
	@Autowired
	private CommonUtils commonUtils;

	@Autowired
	private ILightSaleOrderService iLightSaleOrderService;

	@Autowired
	private IAmazonOrderService amazonOrderService;
	
	@Autowired
	private IWmsProviderTokenDao wmsProviderTokenDao;
	
	@SuppressWarnings("unused")
	@Autowired
	private IWalmartSaleOrderService iWalmartSaleOrderService;

	@Autowired
	private EDAUploadTrackingNumberService eDAUploadTrackingNumberService;
	
	@Autowired
	private IEDADao iEDADao;
	
	@Autowired
	private EbayOrderShipNumberUploadService ebayOrderShipNumberUploadService;

	@Autowired
	private IDeInOrderDao deInOrderDao;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> receiveOutOrderFeedback(Map<String, Object> data) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		
		Map<String,Object> mainParam = new HashMap<String,Object>();
		mainParam.put("orderId", data.get("orderId")); //WMS外部订单号，对应OMS的order_id
		String orderOcsId = String.valueOf(data.get("warehouseId"));
		//需要处理补发单的问题：补发单带了非数值的前缀与后缀，需要去掉
		if(orderOcsId.startsWith("W_")) {
			orderOcsId = orderOcsId.split("_")[1];
		}
		mainParam.put("orderOcsId", orderOcsId); //WMS出库单号，对应OMS的order_ocs_id
		DeOutWmsOrderMainModel main = deWarehouseDao.getOutWmsOrderMailByMap(mainParam);

		if(null == main) {
			result.put("resultFlag", "false");
			result.put("error", "反馈失败,该订单号" + data.get("orderId") + "或者出库单号" + data.get("warehouseId") + "不存在OMS");
			result.put("resultCode", "1001");
			return result;
		}
		
		String trackingNumber = (String) data.get("trackingNumber");
		//忽略对VC（platform=5）单跟踪号的校验。
		if(StringUtils.isBlank(trackingNumber) && !"5".equals(main.getPlatform())) {
			result.put("resultFlag", "false");
			result.put("error", "反馈失败，该订单" + data.get("orderId") + "或者出库单" + data.get("warehouseId") + "对应的跟踪号没有值。（跟踪号是必填字段）");
			result.put("resultCode", "1003");
			return result;
		}
		
		
		if(WarehouseConstant.IS_SEND_WMS_FEEDBACK.equals(main.getIsSendWms())) {
			result.put("resultFlag", "false");
			result.put("error", "反馈失败,该订单号" + data.get("orderId") + "或者出库单号" + data.get("warehouseId") + "已经反馈过OMS一次");
			result.put("resultCode", "1002");
			return result;
		}

		//处理主表信息，反馈之后需要更新相关字段...
		List<DeOutWmsOrderMainModel> mains = new ArrayList<DeOutWmsOrderMainModel>();
		main.setWarehouseId((String) data.get("warehouseId"));
		main.setShipBy((String) data.get("shipBy"));
		main.setShipDate(DateTimeHelper.strTimeToDate((String) data.get("shipDate"), "yyyy-MM-dd HH:mm:ss"));
		main.setTrackingNumber(trackingNumber);
		main.setIsSendWms(WarehouseConstant.IS_SEND_WMS_FEEDBACK);
		main.setFeedbackDate(new Date());
		mains.add(main);
		
		//处理明细信息，反馈之后需要更新相关字段...
		Map<String,Object> detailParam = new HashMap<String,Object>();
		detailParam.put("parentId", main.getId());
		List<DeOutWmsOrderDetailModel> details = new ArrayList<DeOutWmsOrderDetailModel>();
		List<Map<String,Object>> skus = this.transferWmsSku((List<Map<String, Object>>) data.get("skuDetail"), "1"); //先转换WMS的sku为oms可认识的sku
		CollectionUtil.each(skus, new IAction<Map<String,Object>>() {
			public void excute(Map<String,Object> sku) {
				DeOutWmsOrderDetailModel detail = CollectionUtil.search(deWarehouseDao.getOutWmsOrderDetailByMap(detailParam), new IFunction<DeOutWmsOrderDetailModel, Boolean>() {
					public Boolean excute(DeOutWmsOrderDetailModel d) {
						return d.getId() == Long.parseLong((String) sku.get("itemNumber")); //反馈接口中的itemNumber表示出库单明细行号（主键ID）, 不是出库单明细的itemNumber字段
					}
				});
				//如果当前sku信息对应的原始数据存在，则更新，否则，不更新
				if(detail != null) {
					detail.setActualQty((String) sku.get("itemQty"));
					//detail.setSku((String) sku.get("sku"));
					detail.setItem((String) sku.get("item"));
					details.add(detail);
				}
			}
		});
		
		if(!mains.isEmpty()) { 
			deWarehouseDao.batchUpdateDeOutWmsOrderMain(mains);
			logger.info(">>> exec 'deWarehouseDao.batchUpdateDeOutWmsOrderMain' end.");
		}
		
		if(!details.isEmpty()) {
			deWarehouseDao.batchUpdateDeOutWmsOrderDetail(details);
			logger.info(">>> exec 'deWarehouseDao.batchUpdateDeOutWmsOrderDetail' end.");
		}
		
		//如果反馈有传入跟踪号，需要上传跟踪号
		if(StringUtils.isNotBlank(main.getTrackingNumber())) {
			//注释以下代码：将wms反馈跟踪号与oms上传跟踪号拆开，以免影响wms反馈
			//this.updateOutUploadStatus(main.getId(), this.uploadTrackingNumber(main, details) ? 1 : 2);
		}

		this.recordOperSuccessLog("receive-out-order-feedback", main.getId(), "parameter data: " + JsonConvertUtil.getJsonString(data));
		
		result.put("resultFlag", "true");
		result.put("success", "true");
		result.put("resultCode", "10");
		return result;
	}

	static ExecutorService uploadTnThreadPool = Executors.newFixedThreadPool(10);
	
	/**
	 * {@inheritDoc}
	 */
	public void scanOutsUploadTrackingNumber(int platform, boolean endUpload) {
		List<DeOutWmsOrderMainModel> outs = this.deWarehouseDao.queryNoUploadTrackingNumberOuts(String.valueOf(platform));
//		int size = outs == null ? 0 : outs.size();
//		DeOutWmsOrderMainModel main;
		CollectionUtil.each(outs, new IAction<DeOutWmsOrderMainModel>() {
			@Override
			public void excute(DeOutWmsOrderMainModel main) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("parentId", main.getId());
				List<DeOutWmsOrderDetailModel> details = deWarehouseDao.getOutWmsOrderDetailByMap(param);
				boolean success = uploadTrackingNumber(main, details);
				updateOutUploadStatus(main.getId(), success ? 1 : (endUpload ? 3 : 2));
				if(!success) {
					logger.warn(String.format(">>> 出库单（OrderId=%s）上传跟踪号（Tracking Number=%s）失败", main.getOrderId(), main.getTrackingNumber()));
				}
			}
		});
//		if(1 == platform) {
//			DeTnUploadThreadTimer timer;
//			DeTnUploadThreadDTO dto;
//			DeTnUploadThread uploadThread;
//			FutureTask<Boolean> task;
//			for (int i = 0; i < size; i++) {
//				//官网采用多线程 ...
//				timer = new DeTnUploadThreadTimer(i);
//				dto = new DeTnUploadThreadDTO(deWarehouseDao, this, outs.get(i), endUpload);
//				uploadThread = new DeTnUploadThread(timer, dto);
//				task = new FutureTask<Boolean>(uploadThread);
//				uploadTnThreadPool.execute(new DeTnUploadThreadListener<Boolean>(timer, task));
//			}
//		} else {
//			for (int i = 0; i < size; i++) {
//				main = outs.get(i);
//				Map<String, Object> param = new HashMap<String, Object>();
//				param.put("parentId", main.getId());
//				List<DeOutWmsOrderDetailModel> details = deWarehouseDao.getOutWmsOrderDetailByMap(param);
//				boolean success = uploadTrackingNumber(main, details);
//				updateOutUploadStatus(main.getId(), success ? 1 : (endUpload ? 3 : 2));
//				if(!success) {
//					logger.warn(String.format(">>> 出库单（OrderId=%s）上传跟踪号（Tracking Number=%s）失败", main.getOrderId(), main.getTrackingNumber()));
//				}
//			}
//		}
	}
	
	/*
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
		//IDeWarehouseService ars = (IDeWarehouseService) context.getBean("deWarehouseServiceImpl");
		ILightSaleOrderService sss = (ILightSaleOrderService) context.getBean("lightSaleOrderService");
		String[] reissue = "W_DE100067757_1".split("_"); //补发单格式："W_" + OrderId + "_" + times
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tarckingNum", "333");
		param.put("orderId", reissue[1]);
		param.put("tarckingService", WarehouseConstant.CARRIER_ID_DHL);
		param.put("times", reissue[2]);
		param.put("platform", "light");
		OperationResult or = sss.handleReissueTranNumber(param);
	}
	*/

	static final ExecutorService lightTnUploadPool = Executors.newFixedThreadPool(10);
	
	/**
	 * {@inheritDoc}
	 */
	public boolean uploadTrackingNumber(DeOutWmsOrderMainModel main, List<DeOutWmsOrderDetailModel> details) {
		boolean success = false;
		String[] tns = main.getTrackingNumber().split(",");
		try {
			if("1".equals(main.getPlatform())) { //官网上传...
				OperationResult or = null;
				//补发单不用上传跟踪号，更新补发单的相关信息即可；非补发单需要上传跟踪号
				if(main.getOrderId().startsWith(REISSUE_ORDER_PREFIX)) {
					String[] reissue = main.getOrderId().split("_"); //补发单格式："W_" + OrderId + "_" + times
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("tarckingNum", main.getTrackingNumber());
					param.put("orderId", reissue[1]);
					param.put("tarckingService", WarehouseConstant.CARRIER_ID_DHL);
					param.put("times", reissue[2]);
					param.put("platform", "light");
					or = this.iLightSaleOrderService.handleReissueTranNumber(param);
				} else {
					List<Map<String, Object>> ships = new ArrayList<Map<String, Object>>();
					Map<String, Object> param = null;
					for (String tn : tns) {
						param = new HashMap<String, Object>();
						param.put("tranNumber", tn);
						param.put("carrier", WarehouseConstant.CARRIER_ID_DHL); //目前德国仓默认物流商都是DHL
						ships.add(param);
					}
					List<Map<String,Object>> items = new ArrayList<>();
					Map<String,Object> item = null;
					for (DeOutWmsOrderDetailModel detail : details) {
						item = new HashMap<>();
						item.put("qty", detail.getActualQty());
						item.put("orderId", main.getOrderId());
						item.put("sku", detail.getSku());
						item.put("order_item_id", this.iEDADao.getLightOrderItemIdBySku(item));
						items.add(item);
					}
					or = this.iLightSaleOrderService.uploadTranNumber(main.getOrderId(), ships, items);
				}
				success = null != or && 0 == or.getErrorCode();
			} else if("2".equals(main.getPlatform())) { //ebay上传...
				List<JSONObject> ships = new ArrayList<JSONObject>();
				JSONObject ship = null;
				for (String tn : tns) {
					ship = new JSONObject();
					ship.put("courierName", WarehouseConstant.CARRIER_ID_DHL); //目前德国仓默认物流商都是DHL
					ship.put("trackingNum", tn);
					ships.add(ship);
				}
				List<EBayOrderShippingModel> osms = this.iEDADao.getEbayOrderItemByParentId(main.getOrderOcsId());
				int forecast = null == osms ? 0 : osms.size();
				Counter successCounter = new Counter();
				CollectionUtil.each(osms, new IAction<EBayOrderShippingModel>() {
					public void excute(EBayOrderShippingModel osm) {
						if(main.getOrderId().startsWith(REISSUE_ORDER_PREFIX)) {
							//ebay补发单不用上传跟踪号，只需要向用户发送信息，并跟新补发申请表的跟踪号相关字段
							String[] reissue = main.getOrderId().split("_"); //补发单格式："W_" + OrderId + "_" + times
							Map<String, String> orderParam = new HashMap<String, String>();
							orderParam.put("account", osm.getAccount());
							orderParam.put("orderId", reissue[1]);
							orderParam.put("trackingNo", main.getTrackingNumber());
							orderParam.put("carrier", WarehouseConstant.CARRIER_ID_DHL);
							orderParam.put("times", reissue[2]);
							orderParam.put("platform", "ebay");
							orderParam.put("itemId", osm.getItemId());
							OperationResult or = ebayOrderShipNumberUploadService.updateWOrderInfo2(orderParam);
							if(null != or && "success".equals(String.valueOf(or.getData()))) {
								successCounter.beforePlus();
							}
						} else {
							//ebay非补发单需要上传跟踪号
							if(eDAUploadTrackingNumberService.uploadEbayShippingNumber(osm, ships)) {
								successCounter.beforePlus();
							}
						}
					};
				});
				success = forecast == successCounter.get();
			} else if("3".equals(main.getPlatform())) { //亚马逊上传...
				OperationResult or = null;
				if(main.getOrderId().startsWith(REISSUE_ORDER_PREFIX)) {
					String[] reissue = main.getOrderId().split("_"); //补发单格式："W_" + OrderId + "_" + times
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("tarckingNum", main.getTrackingNumber());
					param.put("orderId", reissue[1]);
					param.put("tarckingService", WarehouseConstant.CARRIER_ID_DHL);
					param.put("times", reissue[2]);
					param.put("platform", "amazon");
					param.put("account", "DE");
					or = this.amazonOrderService.handleReissueTranNumber(param);
				} else {
					throw new RuntimeException("暂时不支持亚马逊平台非补发单上传跟踪号");
				}
				success = null != or && 0 == or.getErrorCode();
			} else if("4".equals(main.getPlatform())) { //沃尔玛上传...
				throw new RuntimeException("暂时不支持沃尔玛平台上传跟踪号");
				/*
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("OCSID", main.getOrderOcsId());
				this.iWalmartSaleOrderService.uploadTranNumber(main.getOrderId(), iEDADao.getWalmartOrderInfoByShip(param));
				*/
			} else if("5".equals(main.getPlatform())) { //VC上传...
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("orderId", main.getOrderId());
				param.put("orderOcsId", main.getOrderOcsId());
				param.put("tarckingService", WarehouseConstant.CARRIER_ID_DHL);
				param.put("tarckingNumber", main.getTrackingNumber());
				this.deWarehouseDao.uploadVcTranNumber(param);
				success = true;
			} else {
				throw new RuntimeException("不支持当前平台代号（" + main.getPlatform() + "）");
			}
			if(!success) {
				this.recordOperFailedLog("upload-tarcking-number", Long.valueOf(main.getId()), "上传失败");
			}
		} catch (Exception e) {
			success = false;
			logger.error("上传跟踪号或者更新状态失败：" + e.getMessage(), e);
			this.recordOperFailedLog("upload-tarcking-number", Long.valueOf(main.getId()), e.getMessage() + ": " + StringUtil.instanceDetail(main));
		}
		return success;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void updateOutUploadStatus(Long id, int status) {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("status", status);
			this.deWarehouseDao.updateOutUploadStatus(map);
		} catch (Exception e) {
			logger.error(">>> OMS更新上传跟踪号字段状态失败（Parameter" + map + "） >>> " + e.getMessage(), e);
			try {
				this.deWarehouseDao.updateOutUploadStatus(map); //如果没有更新成功，再更新一次
			} catch (Exception e2) {
				logger.error(">>> OMS再次更新上传跟踪号字段状态失败（Parameter" + map + "） >>> " + e2.getMessage(), e2);
			}
		}
	}
	
	/**
	* @Title: transferWmsSku 
	* @Description: 根据WMS反馈的sku转换为我方的sku
	* @param @param wmsList=> WMS反馈的sku明细list
	* @param @param inOutType=> 1出库反馈 2入库反馈
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String,Object>> transferWmsSku(List<Map<String,Object>> wmsList,String inOutType){
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		Map<String,Object> skuMap = new HashMap<String,Object>();//最终拼出的sku的map集合
		for(Map<String,Object> li:wmsList) {
			//SKU不存在就把其放进map
			Map<String,Object> omsMap = new HashMap<String,Object>();
			if("1".equals(inOutType)){
				omsMap.put("sku", li.get("sku"));//换为oms的sku
				omsMap.put("itemNumber", li.get("itemNumber"));//sku行号平移过来
				omsMap.put("itemQty", li.get("itemQty"));//sku数量平移过来
				omsMap.put("item", li.get("item"));
				skuMap.put((String) li.get("sku"),omsMap);
			}else{
				omsMap.put("itemNumber", li.get("itemNumber"));
				omsMap.put("unit", li.get("unit"));
				omsMap.put("address", li.get("address"));
				omsMap.put("packageCode", li.get("packageCode"));
				omsMap.put("returnReason", li.get("returnReason"));
				omsMap.put("qty", li.get("qty"));
				omsMap.put("badReason", li.get("badReason"));
				omsMap.put("mobile", li.get("mobile"));
				omsMap.put("sku", li.get("sku"));//换为oms的sku
				omsMap.put("customerName", li.get("customerName"));
				omsMap.put("skuProperty", li.get("skuProperty"));
				omsMap.put("item", li.get("item"));
				skuMap.put((String) li.get("sku"),omsMap);
			}
			logger.debug(">>>>>>>>>>>>>>>>>>>>>> " + li);
		}
		for (Entry<String, Object> entry : skuMap.entrySet()) {
		      resultList.add((Map<String, Object>) entry.getValue());
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public OperationResult receiveInOrderFeedback(Map<String, Object> data) throws Exception {
		/**
		 * key=RMA: 正常反馈，其值取OMS推送WMS时RMA字段的值（RMA）；反馈认领单，其值取客户退货填写的RMA，如果客户没有填写RMA，该值为空（即反馈无单的退货）
		 * key=storeInOrder: 正常反馈，其值取OMS推送WMS时RECEIPTID字段的值（RMA）；反馈认领单，其值取WMS自己生成的流水号。
		 */
		String rma =  (String) data.get("RMA"), warehouseId = (String) data.get("storeInOrder");
		if(StringUtils.isBlank(rma) && StringUtils.isBlank(warehouseId)) {
			//当反馈无单退货（即RMA为空值）时，需要防重标记，storeInOrder（WMS流水号）最合适，基于此，这两个参数不能同时为空值。
			throw new Oms2WmsException("参数RMA与storeInOrder不能同时为空值！", "1209");
		}
		
		if(null != this.deInOrderDao.getWmsClaimOrder(rma, warehouseId)) {
			throw new Oms2WmsException("该认领单已经反馈，请不要重复反馈认领单！", "1201");
		}
		
		List<DeInWmsOrderMainModel> mains = new ArrayList<DeInWmsOrderMainModel>();
		List<DeInWmsOrderDetailModel> details = new ArrayList<DeInWmsOrderDetailModel>();
		DeInWmsOrderMainModel main = StringUtils.isBlank(rma) ? null : this.deInOrderDao.getOmsInOrder(rma);
		DeInWmsOrderDetailModel detail = null;
		if(null == main) {
			//没有rma即反馈无单的退货或者有rma但是没有入库单，这种情况需要创建一条待认领数据，由OMS客服进行认领（将当前创建的待认领数据与入库单对应起来）。
			mains.add(this.createClaimInOrder(data));
			for (Map<String, Object> product : this.transferWmsSku((List<Map<String, Object>>) data.get("skuDetail"), "2")) {
				detail = new DeInWmsOrderDetailModel();
				detail.setParentId(mains.get(0).getId());
				mappingDetail(product, detail);
				details.add(detail);
			}
			if(!mains.isEmpty()) this.deWarehouseDao.batchInsertDeInWmsOrderMain(mains);
			if(!details.isEmpty()) this.deWarehouseDao.batchInsertDeInWmsOrderDetail(details);
		} else {
			//正常反馈或者反馈OMS存在入库单的认领单都视为正常反馈
			String id = (String) data.get("storeInOrderId"); //key=storeInOrderId对应OMS入库单主键ID
			boolean isNormalFeedback = StringUtils.isNotBlank(id);
			if(isNormalFeedback && !id.equals(String.valueOf(main.getId()))) {
				//如果存在OMS入库单主键ID且该ID与通过RMA取到的OMS入库单的主键ID不一致（这种情况数据是错乱的），则抛出异常。
				throw new Oms2WmsException("OMS找不到该入库单（id=" + id + "），反馈失败！", "1202");
			}
			main.setWarehouseId(warehouseId);
			main.setNewTrackingNumber((String) data.get("newTrackingNumber"));
			main.setRemark((String) data.get("remark"));
			main.setIsSendWms(WarehouseConstant.IS_SEND_WMS_FEEDBACK); //设置反馈状态为"已反馈"
			main.setFeedbackDate(new Date());
			mains.add(main);
			List<DeInWmsOrderDetailModel> insertDetails = new ArrayList<DeInWmsOrderDetailModel>();
			for (Map<String, Object> product : this.transferWmsSku((List<Map<String, Object>>) data.get("skuDetail"), "2")) {
				String itemNumber = (String) product.get("itemNumber");
				boolean hasItemNumber = StringUtils.isNotBlank(itemNumber) && !"0".equals(itemNumber);
				detail = new DeInWmsOrderDetailModel();
				detail.setId(hasItemNumber ? Integer.parseInt(itemNumber) : null);//主键ID
				detail.setParentId(main.getId());
				mappingDetail(product, detail);
				//入库反馈的时候有意外多货物的情况，如果多货则直接保存到入库单明细。
				if(hasItemNumber) {
					details.add(detail);
				} else {
					insertDetails.add(detail);
				}
			}
			if(!mains.isEmpty()) deWarehouseDao.batchUpdateDeInWmsOrderMain(mains);
			if(!details.isEmpty()) deWarehouseDao.batchUpdateDeInWmsOrderDetail(details);
			if(!insertDetails.isEmpty()) deWarehouseDao.batchInsertDeInWmsOrderDetail(insertDetails);
		}
		
		OperationResult or = new OperationResult(0, "10", null, null);
		String description = "request=" + JsonConvertUtil.getJsonString(data) + "; response=" + JsonConvertUtil.getJsonString(or);
		this.recordOperSuccessLog("receive-in-order-feedback", mains.get(0).getId(), description);
		return or;
	}

	private void mappingDetail(Map<String, Object> source, DeInWmsOrderDetailModel target) {
		target.setSku((String) source.get("sku"));
		target.setActualQty((String) source.get("qty"));
		target.setPackageCode((String) source.get("packageCode"));
		target.setUnit((String) source.get("unit"));
		target.setSkuProperty((String) source.get("skuProperty"));
		target.setReturnReason((String) source.get("returnReason"));
		target.setCustomerName((String) source.get("customerName"));
		target.setMobile((String) source.get("mobile"));
		target.setAddress((String) source.get("address"));
		target.setPicUrl((String) source.get("picUrl"));
		target.setBadReason((String) source.get("badReason"));
		target.setItem((String) source.get("item"));
	}

	private DeInWmsOrderMainModel createClaimInOrder(Map<String, Object> data) {
		DeInWmsOrderMainModel main = new DeInWmsOrderMainModel();
		main.setId(this.deWarehouseDao.getInWmsOrderSequences());
		main.setOwnerCode((String) SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_DEOWNERCODE));
		main.setStoreCode((String) SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_DESTORECODE));
		main.setWarehouseId((String) data.get("storeInOrder"));
		main.setRma((String) data.get("RMA"));
		main.setOrderType(WarehouseConstant.DE_IN_ORDER_TYPE_RETURNGOODS);//默认都是退库入库
		main.setPlatform(this.platformSwitch(String.valueOf(data.get("source"))));
		main.setCreateBy((String) data.get("createBy"));
		main.setOrderId((String) data.get("orderId"));
		main.setTrackingNumber((String) data.get("trackingNumber"));
		main.setNewTrackingNumber((String) data.get("newTrackingNumber"));
		main.setRemark((String) data.get("remark"));
		main.setWaitClaim(WarehouseConstant.DEIN_WMS_UNCLAIMED);//WMS退货待认领单
		return main;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveDeOutOrders(String platform, Map<String, Object> data) throws Exception {
		String orderOcsId = String.valueOf(data.get("ORDER_OCS_ID"));
		String orderId = (String) data.get("ORDER_ID");
		boolean reissueFlag = orderId.contains(REISSUE_ORDER_PREFIX);
		String address1 = StringUtil.cancelNewline((String) data.get("ADDRESS_ONE"));
		String address2 = StringUtil.cancelNewline((String) data.get("ADDRESS_TWO")); 
		
		//首先校验该出库单是否存在,存在则跳过
		Map<String,Object> exists = new HashMap<String,Object>();
		exists.put("orderOcsId", orderOcsId);
		//需要加入orderId进行联合查询，因为补发单与原单的orderOcsId是一致的，数据源的orderOcsId并未区分补发单与原单
		exists.put("orderId", orderId); 
		exists.put("platform", platform);
		DeOutWmsOrderMainModel om = deWarehouseDao.getOutWmsOrderMailByMap(exists);
		if(null != om && !(orderId.startsWith("W_OCS") && "4".equals(om.getIsSendWms()))) {
			throw new Oms2WmsException("当前出库单（orderId=" + orderId + "）已经存在", "exists"); //如果存在该出库单了就抛出异常，终止当前操作并触发回滚事务
		}
		
		//处理主表信息...
		List<DeOutWmsOrderMainModel> mains = new ArrayList<DeOutWmsOrderMainModel>();
		DeOutWmsOrderMainModel main = new DeOutWmsOrderMainModel();
		main.setId(this.deWarehouseDao.getOutWmsOrderSequences());
		main.setPlatform(platform);
		main.setOrderOcsId(Long.parseLong(orderOcsId));
		if("5".equals(platform)) {
			main.setOrderOutType(WarehouseConstant.DE_OUT_HME_VC_SHIPMENT);
		} else {
			main.setOrderOutType(reissueFlag ? WarehouseConstant.DE_OUT_REISSUE_NORMAL : WarehouseConstant.DE_OUT_HME_INV_SHIPMENT); //需要区分是补发出库还是销售出库
		}
		main.setOrderId(orderId); //取原始数据，补发单不需要去掉前缀"W_"
		main.setIsUpload("0");
		main.setOcsOrderCreateDate(DateTimeHelper.strTimeToDate(String.valueOf(data.get("OCS_ORDER_CREATE_DATE")), "yyyy-MM-dd HH:mm:ss"));
		main.setStoreCode((String) SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_DESTORECODE));
		main.setCarrierId(WarehouseConstant.CARRIER_ID_DHL);
		main.setCustomerName((String) data.get("CUSTOMER_NAME"));
		main.setCustomerCountry((String) data.get("CUSTOMER_COUNTRY"));
		main.setCustomerProvince((String) data.get("CUSTOMER_PROVINCE"));
		main.setCustomerCity((String) data.get("CUSTOMER_CITY"));
		//地址1取原始地址1的值存储，不再进行将地址1与地址2合并存储 	-- Modified by Louis with 2018-05-31
		//main.setCustomerAddress(this.genAddress(address1, address2));
		main.setCustomerAddress(address1);
		main.setCustomerAddress2(address2);
		main.setCustomerZip((String) data.get("CUSTOMER_ZIP"));
		main.setCustomerEmail((String) data.get("CUSTOMER_EMAIL"));
		main.setCustomerPhone((String) data.get("CUSTOMER_PHONE"));
		mains.add(main);
		
		String skus[] = String.valueOf(data.get("SKU")).split(",");
		String qtys[] = String.valueOf(data.get("ITEM_QTY")).split(",");
		String itemIds[] = String.valueOf(data.get("ITEM_ID")).split(",");
		String[] prices =  String.valueOf(data.get("PRICE")).split(",");
		
		//处理明细表信息...
		List<DeOutWmsOrderDetailModel> details = new ArrayList<DeOutWmsOrderDetailModel>();
		DeOutWmsOrderDetailModel detail = null;
		Map<String,Object> skuParam = new HashMap<String,Object>();
		for (int i = 0; i < skus.length; i++) {
			detail = new DeOutWmsOrderDetailModel();
			detail.setItemNumber(itemIds[i]);
			detail.setParentId(main.getId());
			String price = prices[i];
			String psku = skus[i];
			if (psku.contains("^")) {//含有逗号的sku名称被特殊处理过,此处还原为原来sku名称
				psku = psku.replace("^", ",");
			}
			skuParam.put("psku", psku);
			Map<String,Object> sku = deWarehouseDao.getSkusByPSku(skuParam);
			//能通过映射关系找到sku，则取映射关系的sku信息，找不到则直接去当前sku信息
			if(null != sku && sku.size() > 0) {
				detail.setSku((String) sku.get("SKU"));
				int qty = Integer.parseInt(String.valueOf(sku.get("QTY")));
				detail.setItemQty(qty > 0 ? String.valueOf(Integer.parseInt(qtys[i]) * qty) : qtys[i]);
				detail.setPrice(StringUtils.isBlank(price) ? null : (qty > 0 ? new BigDecimal(price).divide(new BigDecimal(qty), 2, java.math.RoundingMode.HALF_UP).doubleValue() : Double.valueOf(price)));
			} else {
				detail.setSku(psku);
				detail.setItemQty(qtys[i]);
				detail.setPrice(StringUtils.isBlank(price) ? null : Double.valueOf(price));
			}
			detail.setActualQty(detail.getItemQty());
			details.add(detail);
		}

		if(mains.size() > 0) {
			deWarehouseDao.batchInsertDeOutWmsOrderMain(mains);
		}
		
		if(details.size() > 0) {
			deWarehouseDao.batchInsertDeOutWmsOrderDetail(details);
		}
		
		this.recordOperSuccessLog("save-out-order", main.getId(), "出库单（orderId=" + orderId + "）保存成功: " + JsonConvertUtil.getJsonString(data));
	}
	
	/**
	 * 生成地址
	 * @param street 街道
	 * @param houseNumber 门牌
	 * @return
	 */
	private String genAddress(String street, String houseNumber) {
		street = StringUtils.isBlank(street) ? "" : street.trim();
		houseNumber = StringUtils.isBlank(houseNumber) ? "" : houseNumber.trim();
		
		if(street.length() > 0 && houseNumber.length() > 0) {
			return street + " " + houseNumber;
		} 
		
		if(street.length() > 0) {
			return street;
		}
		
		if(houseNumber.length() > 0) {
			return houseNumber;
		}
		
		return "";
	}
	
	/*public static void main(String[] args) {
		DeWarehouseServiceImpl impl = new DeWarehouseServiceImpl();
		System.out.println(impl.genAddress("a", "b"));
		System.out.println(impl.genAddress("a", null));
		System.out.println(impl.genAddress(null, null));
		System.out.println(impl.genAddress(null, ""));
		System.out.println(impl.genAddress("", ""));
		System.out.println(impl.genAddress(null, "b"));
		System.out.println(impl.genAddress("", "b"));
		System.out.println();
		
		String street = "abcd", houseNumber = "null";
		System.out.println("Street=" + street);
		System.out.println("Street.length=" + (street == null ? 0 : street.length()));
		System.out.println();
		System.out.println("House Number=" + houseNumber);
		System.out.println("House Number.length=" + (houseNumber == null ? 0 :houseNumber.length()));
		
		String address = impl.getJionStr(street, houseNumber);
		System.out.println();
		System.out.println("Address=" + address);
		System.out.println("Address.length=" + address.length());
	}*/
	
	/*public static void main(String[] args) {
		org.springframework.context.ApplicationContext context = new org.springframework.context.support.ClassPathXmlApplicationContext("classpath:spring.xml");
		IDeWarehouseService impl = (IDeWarehouseService) context.getBean("deWarehouseServiceImpl");
		try {
			impl.throwExIfLackInventory("C-700015-WW-EU-5", 1, "jrtd2zg020gcm50ah3sb023e");
		} catch (SkuInventoryException e) {
			e.printStackTrace();
		}
		System.out.println("End.");
	}*/
	
	
	/**
	 * 检查指定的sku库存：如果缺少库存则跑出异常
	 * @param sku 待检查库存的SKU
	 * @param minQty 库存至少需要满足的数量
	 * @param token 用于身份认证的加密字符串
	 * @throws SkuInventoryException 获取SKU库存失败、返回值不符合要求、SKU库存不足等等会抛出该异常
	 */
	@SuppressWarnings("unchecked")
	public void throwExIfLackInventory(String sku, int minQty, String token) throws SkuInventoryException {
		Map<String, Object> inventory = null; 
		try {
			inventory = commonUtils.getDeWmsSkuQty(sku, token);
		} catch (Exception e) {
			logger.warn(">>> 第一次调用WMS接口获取库存出现异常（sku=" + sku + "; token=" + token + "）", e);
			try {
				inventory = commonUtils.getDeWmsSkuQty(sku, token); //如果一次请求失败，尝试再请求一次
			} catch (Exception e2) {
				logger.error(">>> 第二次调用WMS接口获取库存出现异常（sku=" + sku + "; token=" + token + "）", e);
				throw new SkuInventoryException("WMS Inventory API: Server Error（sku=" + sku + "; token=" + token + "）", e, SkuInventoryException.API_CALLFAILED);
			}
		}
		
		if(null == inventory) {
			throw new SkuInventoryException("WMS Inventory API: Server Error（sku=" + sku + "; token=" + token + "）", SkuInventoryException.API_CALLFAILED);
		}
		
		try {
			String code = String.valueOf(inventory.get("resultCode"));
			String msg = (String) inventory.get("msg");
			if("0".equals(code)) {
				List<Map<String, Object>> data = (List<Map<String, Object>>) inventory.get("data");
				if(CollectionUtil.isNullOrEmpty(data)) {
					throw new SkuInventoryException("WMS Inventory API: 获取当前SKU（" + sku + "）报错。（msg=" + msg + "）", SkuInventoryException.INVENTORY_SHORTAGE);
				}
				int available = 0;
				for (Map<String, Object> map : data) {
					available += map.get("AVAILABLE_QTY") == null ? 0 : Double.valueOf(map.get("AVAILABLE_QTY").toString()).intValue();
				}
				if(available < minQty) {
					throw new SkuInventoryException("WMS Inventory API: 当前SKU（" + sku + "）的库存不足（inventory=" + available + "; min=" + minQty + "）。（msg=" + msg + "）", SkuInventoryException.INVENTORY_SHORTAGE);
				}
			} else {
				throw new SkuInventoryException("WMS Inventory API: Error Code（resultCode=" + code + "; sku=" + sku + "）。（msg=" + msg + "）", SkuInventoryException.INVENTORY_GETFAILED);
			}
		} catch (SkuInventoryException e) {
			throw e;
		} catch (Exception e) {
			throw new SkuInventoryException("WMS Inventory API: Invalid Data（sku=" + sku + "; token=" + token + "; data=" + JsonConvertUtil.getJsonString(inventory) + "）", SkuInventoryException.API_RETINVALID);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean sendDeOutOrders(Map<String, Object> data, String token) throws Exception {
		String platform = (String) data.get("PLATFORM");
		String orderId = (String) data.get("ORDER_ID");
		String address1 = (String) data.get("CUSTOMER_ADDRESS");
		String address2 = (String) data.get("CUSTOMER_ADDRESS_2"); //SQL取数层面，地址二数据来源：ebay对应ebay_order_sub表的street2；light没有地址二，取空字符串
		String orderOcsId = String.valueOf(data.get("ORDER_OCS_ID"));
		
		Map<String,Object> out = new HashMap<String,Object>(); //出库单数据
		out.put("OWNER_ID", (String) SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_DEOWNERCODE));
		out.put("WH_ID", data.get("STORE_CODE"));
		out.put("EXTERNAL_ORDER_ID", orderId); //外部订单号：由主键Id改为传orderId
		out.put("ORDER_TYPE_SC", data.get("ORDER_OUT_TYPE"));//订单类型
		out.put("ORDER_FROM_SC", "FromOms");//订单来源
		out.put("SO_UDF5", this.platformSwitch(platform));//订单来源
		out.put("CARRIER_ID", data.get("CARRIER_ID"));
		out.put("SO_UDF7", "2".equals(platform) ? "" : address2); //基于公司地址需求：ebay平台不传，官网平台传地址
		out.put("SO_UDF8", data.get("CUSTOMER_ADDRESS_3")); //地址三数据来源：ebay与light都没有，忽略
		out.put("CUSTOMER_NAME", data.get("CUSTOMER_NAME"));
		out.put("CUSTOMER_COUNTRY", data.get("CUSTOMER_COUNTRY")); //国家简码
		out.put("CUSTOMER_CONTACT", data.get("CUSTOMER_PROVINCE")); //省份
		out.put("CUSTOMER_FAX", data.get("CUSTOMER_CITY")); //城市
		out.put("SO_UDF6", address1 ); //客户地址
		//地址已经没有合并存储了，所以需要在此处合并之后取各自街道、门牌   -- Modified by Louis with 2018-05-31
		String address = this.genAddress(address1, address2);
		out.put("CUSTOMER_ADDRESS", this.getAdressStr(address,"2")); //客户街道名称
		out.put("CUSTOMER_REF", this.getAdressStr(address,"3")); //客户门牌号
		out.put("CUSTOMER_ZIP", data.get("CUSTOMER_ZIP")); //客户收件地址邮编号码
		out.put("CUSTOMER_PHONE", data.get("CUSTOMER_PHONE")); //客户手机号
		out.put("CUSTOMER_EMAIL", data.get("CUSTOMER_EMAIL")); //客户邮箱
		//出库单的一个字段要修改下传值的地方，你们的下单时间，现在是传在W的CREATED_DATE，这个修改下，这个字段不传值，把你们的下单时间传到SO_UDF9  --- by wms@nickzeng
		out.put("SO_UDF9", data.get("OCS_ORDER_CREATE_DATE")); //订单的下单时间
		out.put("SHIPPING_ORDER_ID", this.genReissueOcsId(orderId, orderOcsId)); //出库订单号：传ORDER_OCS_ID
		out.put(DISEASE_TYPE_SC, ""); //可能是病单重推，该值传空
		List<Map<String,Object>> outDetails = new ArrayList<Map<String,Object>>(); //出库单明细
		out.put("ShippingOrderDetailList", outDetails);

		Map<String,Object> dparam = new HashMap<String,Object>();
		dparam.put("parentId", data.get("ID"));
		List<DeOutWmsOrderDetailModel> details = deWarehouseDao.getOutWmsOrderDetailByMap(dparam);
		Map<String, Object> outDetail = null;
		String sku = null;
		int qty = 0; 
		for(DeOutWmsOrderDetailModel detail : details) {
			sku = this.fixSku(detail.getSku());
			Map<String, Object> product = deWarehouseDao.getProduct(sku, Integer.parseInt(detail.getItemQty()));
			if(null == product) {
				throw new Oms2WmsException("找不到映射SKU产品信息(orderId=" + orderId + "; sku=" + sku + ")");
			}
			sku = (String) product.get("SKU");
			qty = Integer.parseInt(product.get("QTY").toString());
			this.throwExIfLackInventory(sku, qty, token);
			outDetail = new HashMap<String,Object>(); //传给wms的sku明细
			outDetail.put("SKU_ID", sku);
			outDetail.put("ORDER_QTY", qty);
			outDetail.put("EXTERNAL_LINE_ID", detail.getId());//对方外部sku行号
			//价格取原单的sku售价，而不是取sku的产品售价，此处改动需要考虑历史数据，所以当原单的sku售价没有的时候，依然按以前的取sku的产品售价
			outDetail.put("UNIT_PRICE", null == detail.getPrice() ? product.get("PRICE") : detail.getPrice());
			if("5".equals(platform) || "VC".equals(platform)) {
				outDetail.put("SOD_UDF7", "DE02"); //来自@WMS-nickzeng在2018/11/21 17:40:44提出的需求：VC出库单的明细自定义字段SOD_UDF7上默认传一个DE02值 
			}
			outDetails.add(outDetail);
		}

		Map<String,Object> request = new HashMap<String,Object>();
		request.put("shippingorderEditDTO", out);
		request.put("token", token);
		request.put("whgid", (String) SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_DESTORECODE));
		String url = SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_WMS_URL) + WarehouseConstant.SAVESHIPPINGORDER;
		Map<String,Object> response = commonUtils.httpPostJson2DeWms(url, request);
		String reqresp = "request=" + JsonConvertUtil.getJsonString(request) + "; response=" + JsonConvertUtil.getJsonString(response);
		logger.info(">>> Http post out-order to wms: " + reqresp);

		boolean success = null != response && 0 == Integer.parseInt(String.valueOf(response.get("resultCode"))); //是否推送成功
		Long id = Long.parseLong(String.valueOf(data.get("ID")));
		String isSendWms = success ? WarehouseConstant.IS_SEND_WMS_YES : WarehouseConstant.IS_SEND_WMS_FAILED;
		String isAbnormal = success ? WarehouseConstant.IS_ABNORMAL_0 : WarehouseConstant.IS_ABNORMAL_1;
		String description = null != response ? (String) response.get("msg") : "response is null. (Network error ?)";
		this.afterOperateDeOutOrder(id, isSendWms, isAbnormal, success ? null : AbnormalReasonUtils.newOut(id, AbnormalReasonUtils.ACTION_SOR_HWR, description));
		this.recordOperLog("send-out-order", id, success ? "success" : "failed", "出库单（orderId=" + orderId + "）推送" + (success ? "成功" : "失败") + ": " + reqresp);
		
		return success;		
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void afterOperateDeOutOrder(Long id, String isSendWms, String isAbnormal, DeAbnormalReasonModel reason) throws Exception {
		if(null == id) {
			return;
		}
		
		Map<String, Object> update = new HashMap<String, Object>();
		update.put("id", id);
		if(this.validIsSendWms(isSendWms)) {
			update.put("isSendWms", isSendWms);	
		}
		if(this.validIsAbnormal(isAbnormal)) {
			update.put("isAbnormal", isAbnormal);
		}
		if(WarehouseConstant.IS_SEND_WMS_YES.equals(isSendWms)) {
			update.put("sendDate", new Date());
		}
		if(WarehouseConstant.IS_SEND_WMS_FEEDBACK.equals(isSendWms)) {
			update.put("feedbackDate", new Date());
		}
		this.deWarehouseDao.updateDeOutWmsOrderMainByMap(update);
		if(reason != null) {
			this.deWarehouseDao.addAbnormalReason(reason);
		}
	}
	
	private boolean validIsSendWms(String isSendWms) {
		return WarehouseConstant.IS_SEND_WMS_NO.equals(isSendWms) || WarehouseConstant.IS_SEND_WMS_YES.equals(isSendWms)
				|| WarehouseConstant.IS_SEND_WMS_FEEDBACK.equals(isSendWms) || WarehouseConstant.IS_SEND_WMS_FAILED.equals(isSendWms);
	}
	
	private boolean validIsAbnormal(String isAbnormal) {
		return WarehouseConstant.IS_ABNORMAL_0.equals(isAbnormal) || WarehouseConstant.IS_ABNORMAL_1.equals(isAbnormal);
	}
	
	/**
	 * "W_"代表是补发单
	 */
	private static final String REISSUE_ORDER_PREFIX = "W_"; 
	
	/**
	 * 将OrderOcsId生成补发单号
	 * @param orderId
	 * @param orderOcsId
	 * @return
	 */
	private String genReissueOcsId(String orderId, String orderOcsId) {
		if(StringUtils.isNotBlank(orderId) && orderId.startsWith(REISSUE_ORDER_PREFIX)) {
			String[] array = orderId.split("_");
			orderOcsId = REISSUE_ORDER_PREFIX + orderOcsId;
			if(array.length > 2) {
				orderOcsId += "_" + array[2]; 
			}
		}
		return orderOcsId;
	}
	
	/*public static void main(String[] args) {
		DeWarehouseServiceImpl impl = new DeWarehouseServiceImpl();
		System.out.println(impl.genReissueOcsId("W_123", "123"));
		System.out.println(impl.genReissueOcsId("W_123_3", "123"));
		System.out.println(impl.genReissueOcsId("_123_", "123"));
		System.out.println(impl.genReissueOcsId("123", "123"));
		System.out.println(impl.genReissueOcsId("123_1", "123"));
	}*/
	
	private static final String _BLUE = "-BLUE";
	
	/**
	 * 修理SKU（个别SKU因为历史原因OMS-WMS会存在大小写差别）
	 * @param sku
	 * @return
	 */
	private String fixSku(String sku) {
		if(StringUtils.isBlank(sku)) {
			return sku;
		}
		String lc = sku.toLowerCase();
		int blueIndex = lc.indexOf(_BLUE.toLowerCase());
		return blueIndex == -1 ? sku : (sku.substring(0, blueIndex) + _BLUE + sku.substring(blueIndex + _BLUE.length()));
	}
	
	/*public static void main(String[] args) {
		String[] skus = new String[] { "2*4100057-blue-2", "F-4500001-BLUE-US", "L-4300004-BLUE-a", "L4300004BLUEa", "BLUEa", null, "" };
		DeWarehouseServiceImpl impl = new DeWarehouseServiceImpl();
		CollectionUtil.each(skus, new IAction<String>() {
			public void excute(String sku) {	
				System.out.println("before fixed: " + sku);
				System.out.println(" after fixed: " + impl.fixSku(sku));
				System.out.println("-----------------------------");
			}
		});
	}*/
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean sendDeInOrders(Map<String, Object> data, String token) throws Exception {
		String orderId = (String) data.get("ORDER_ID");
		Map<String,Object> in = new HashMap<String,Object>();
		in.put("RECEIPT_ID", data.get("RMA"));
		in.put("OWNER_ID", (String) SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_DEOWNERCODE));//货主代码
		in.put("WH_ID",data.get("STORE_CODE"));//仓库代码
		in.put("INBOUND_ID", data.get("ID"));//OMS入库单号
		in.put("RECEIPT_TYPE_SC", WarehouseConstant.DE_IN_ORDER_TYPE_RETURNGOODS);//入库订单类型 
		in.put("R_UDF5", this.platformSwitch(data.get("PLATFORM")+""));//平台来源
		in.put("CREATED_DATE", data.get("CREATED_DATE"));//创建日期
		in.put("R_UDF6", "");//操作人
		in.put("RMA", data.get("RMA"));//退货标识
		in.put("PACKING_ORDER_ID", orderId);//原始销售单号
		in.put("EXTERNAL_RECEIPT_ID", data.get("TRACKING_NUMBER"));//原始运单号
		in.put("EXTERNAL_RECEIPT_ID2", data.get("NEW_TRACKING_NUMBER"));//新运单号
		in.put("R_REMARK", data.get("REMARK"));//备注
		in.put("RECEIPT_FROM_SC", "FromOms"); //订单来源

		List<Map<String,Object>> details = new ArrayList<Map<String,Object>>();
		Map<String,Object> detail = null;
		String sku;
		int qty;
		in.put("ReceiptDetailList", details);//出库订单详情list
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("parentId", data.get("ID"));
		for (DeInWmsOrderDetailModel item : this.deWarehouseDao.getInWmsOrderDetailByMap(param)) {
			detail = new HashMap<String,Object>();//传给wms的sku明细
			sku = fixSku(item.getSku());
			Map<String, Object> product = deWarehouseDao.getProduct(sku, Integer.parseInt(item.getQty()));
			if(null == product) {
				throw new Oms2WmsException("找不到映射SKU产品信息(orderId=" + orderId + "; sku=" + sku + ")");
			}
			sku = (String) product.get("SKU");
			qty = Integer.parseInt(product.get("QTY").toString());
			detail.put("SKU_ID", sku);//转换为wms的sku
			detail.put("EXPECTED_QTY", qty);//入库数量
			detail.put("PACK_ID", item.getPackageCode());//WMS产品包装代码.如果没有,WMS默认产品包装
			detail.put("UOM_ID", item.getUnit());//WMS产品包装单位.如果没有,WMS默认产品最小单位
			//detail.put("SKU_PROPERTY", StringUtils.isBlank(item.getSkuProperty()) ? "QC" : item.getSkuProperty());//货品属性 默认:待检查
			detail.put("SKU_PROPERTY", StringUtils.isBlank(item.getSkuProperty()) ? "OK" : item.getSkuProperty());//货品属性改为传"OK"
			detail.put("RD_UDF9", item.getReturnReason());//退货原因
			detail.put("EXTERNAL_LINE_ID", item.getId());//入库单行号
			detail.put("RD_UDF7", item.getCustomerName());//退货客户名称
			detail.put("RD_UDF8", item.getMobile());//联系电话
			detail.put("RD_REMARK", item.getAddress());//联系地址
			details.add(detail);
		}

		String url = SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_WMS_URL) + WarehouseConstant.SAVERECEIPT;
		Map<String,Object> request = new HashMap<String,Object>();
		request.put("receiptEditDTO", in);
		request.put("token", token);
		request.put("whgid", (String) SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_DESTORECODE));
		Map<String, Object> response = commonUtils.httpPostJson2DeWms(url, request);
		String reqresp = "request=" + JsonConvertUtil.getJsonString(request) + "; response=" + JsonConvertUtil.getJsonString(response);
		logger.info(">>> Http post in-order to wms: " + reqresp);

		boolean success = 0 == Integer.parseInt(String.valueOf(response.get("resultCode"))); //是否推送成功
		Long id = Long.parseLong(data.get("ID").toString());
		String isSendWms = success ? WarehouseConstant.IS_SEND_WMS_YES : WarehouseConstant.IS_SEND_WMS_FAILED;
		String description = null != response ? (String) response.get("msg") : "response is null. (Network error ?)";
		this.afterOperateDeInOrder(id, isSendWms, success ? null : AbnormalReasonUtils.newIn(id, AbnormalReasonUtils.ACTION_SIR_HWR, description));
		this.recordOperLog("send-in-order", id, success ? "success" : "failed", reqresp);
		return success;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void afterOperateDeInOrder(Long id, String isSendWms, DeAbnormalReasonModel reason) throws Exception {
		Assert.notNull(id, "The parameter 'id' Can not be null.");
		Map<String, Object> mainmap = new HashMap<String, Object>();
		mainmap.put("id", id);
		if(CommonUtils.validIsSendWms(isSendWms)) {
			mainmap.put("isSendWms", isSendWms);	
		}
		if(WarehouseConstant.IS_SEND_WMS_YES.equals(isSendWms)) {
			mainmap.put("sendDate", new Date());
		}
		if(WarehouseConstant.IS_SEND_WMS_FEEDBACK.equals(isSendWms)) {
			mainmap.put("feedbackDate", new Date());
		}
		this.deInOrderDao.updateMain(mainmap);
		if(reason != null) {
			this.deWarehouseDao.addAbnormalReason(reason);
		}
	}
	
	/**
	* @Title: platformSwitch 
	* @Description: 官网平台推送wms时转换
	* @param @param platform
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	private String platformSwitch(String platform){
		if("1".equals(platform)){
			return "官网";
		}else if("2".equals(platform)){
			return "eBay";
		}else if("3".equals(platform)){
			return "";
		}else if("4".equals(platform)){
			return "沃尔玛";
		}else if("5".equals(platform)){
			return "VC";
		}
		
		if("官网".equals(platform)||"light".equals(platform)){
			return "1";
		}else if("eBay".equals(platform)||"ebay".equals(platform)){//"ebay"主要是为了容错从退款退件表取的平台
			return "2";
		}else if("3".equals(platform)){
			return "";
		}else if("沃尔玛".equals(platform)||"walmart".equals(platform)){
			return "4";
		}else if("VC".equals(platform)){
			return "5";
		}
		return null;
	}
	
	/*public static void main(String[] args) {
		String address = "Neue Schönhauser Str. 8 ";
		DeWarehouseServiceImpl impl = new DeWarehouseServiceImpl();
		String street = impl.getAdressStr(address, "2");
		System.out.println("Street: " + street);
		String houseNumber = impl.getAdressStr(address, "3");
		System.out.println("House Number: " + houseNumber);
	}*/
	
	/**
	* @Title: getAdressStr 
	* @Description: 根据地址获取对应的街道门牌号信息 
	* @param @param addStr
	* @param @param type 1获取完整街道信息 2获取街道 3获取门牌号
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	private String getAdressStr(String addStr,String type){
		String strAddTemp[] = addStr.split("\\^");
		StringBuffer strBuff = new StringBuffer();
		String temp ="";
		for(int i=0;i<strAddTemp.length;i++){
			if(!"null".equals(strAddTemp[i])){
				strBuff.append(strAddTemp[i]);
			}
		}
		temp = strBuff.toString();
		
		if("1".equals(type)){//获取完整地址
			return strBuff.toString();
		}else if("2".equals(type)){//获取街道
			if(-1 == temp.lastIndexOf(" ")){
				//没有空格就直接返回原地址
				return temp;
			}else{
				return temp.substring(0,temp.lastIndexOf(" ")+1);
			}
		}else{//获取门牌号
			if(-1 == temp.lastIndexOf(" ")){
				//如果没有空格就返回-
				return "-";
			}else{
				return temp.substring(temp.lastIndexOf(" ")+1,temp.length());
			}
		}
	}
	
	/**
	* @Title: getSkuRmbPrice 
	* @Description: 获取sku的人民币价格
	* @param @param plarform 平台来源 1官网 2eBay
	* @param @param sku 
	* @param @param order_ocs_id 原始订单主键ID
	* @param @param countryCode 国家简码(为了方便查找汇率)
	* @param @param rateMap 汇率map
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@SuppressWarnings("unused")
	private String getSkuRmbPrice(String plarform,String sku,String order_ocs_id,String countryCode, Map<String,Object> rateMap) {
		//获取sku总价和数量 由于补发单里面只记录数量没有记录总价,所以此处通过明细表去计算
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String skuPrice = "";
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		paramMap.put("sku", sku);
		paramMap.put("parentId", order_ocs_id);
		if("1".equals(plarform)){
			resultList = deWarehouseDao.getSkuInfoFromItemLight(paramMap);
		}else if("2".equals(plarform)){
			resultList = deWarehouseDao.getSkuInfoFromItemEbay(paramMap);
		}else if("3".equals(plarform)){
			resultList = deWarehouseDao.getSkuInfoFromItemAmazon(paramMap);
		}
		
		if(resultList == null || resultList.isEmpty()) {
			logger.error(String.format(">>> Get Price Error >>> Args: { plarform=%s, sku=%s, order_ocs_id=%s }", plarform, sku, order_ocs_id));
		}
		
		paramMap.clear();
		paramMap = resultList.get(0);
		if(0 == Integer.parseInt(paramMap.get("QTY")+"")){
			skuPrice = "0.00";
		}else{
			//不用通过汇率算价格，直接取价格返回
			Object val = paramMap.get("PRICE");
			skuPrice = val == null ? "0.00" : val.toString();
			
			/*BigDecimal d1 = new BigDecimal(paramMap.get("PRICE")+"");
			//根据币种换成人民币
			@SuppressWarnings("unchecked")
			Map<String,Object> tempMap = (Map<String, Object>) rateMap.get(countryCode);
			@SuppressWarnings("unchecked")
			Map<String,Object> tempMapCN = (Map<String, Object>) rateMap.get("CN");//人民币
			BigDecimal rateTemp = new BigDecimal(tempMap.get("CURRENCY_RATE")+"");//得到当前币种兑换美元的汇率
			BigDecimal rateTempCN = new BigDecimal(tempMapCN.get("CURRENCY_RATE")+"");//人民币兑换美元的汇率
			BigDecimal d2 = new BigDecimal(paramMap.get("QTY")+"");
			skuPrice = d1.multiply(rateTemp).multiply(rateTempCN).divide(d2,2,BigDecimal.ROUND_HALF_UP).doubleValue()+""; */
		}
		
		return skuPrice;
	}
	
	@SuppressWarnings("unused")
	private Map<String,Object> getCalcurrencyRate(){
		List<Map<String,Object>> list = deWarehouseDao.getCalCurrencyCodeList();
		Map<String,Object> map = new HashMap<String,Object>();
		for(Map<String,Object> li:list){
			map.put(li.get("COUNTRY_ID")+"", li);
		}
		return map;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveDeInOrders(Map<String, Object> data) throws Exception {
		//首先去检查是否已经该入库单存在,存在的则抛出含有具体提示信息的异常
		String retOrderId = String.valueOf(data.get("RETURN_ORDER_ID"));
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("returnOrderId", retOrderId);
		DeInWmsOrderMainModel inmain = this.deWarehouseDao.getInWmsOrderMailByMap(param);
		if(null != inmain) {
			throw new Oms2WmsException("当前退件（return_order_id=" + retOrderId + "）已经在待处理列表或者已经被处理", "exists");
		}

		String platform = String.valueOf(data.get("PLATFORM"));
		String orderId = String.valueOf(data.get("ORDER_ID"));
		String account = String.valueOf(data.get("ACCOUNT"));
		String trackingNumber = null;
		DeInWmsOrderMainModel main = new DeInWmsOrderMainModel();
		
		Map<String, Object> od = null;
		Map<String,Object> odparam = new HashMap<String,Object>();
		odparam.put("orderId", orderId);
		//首先根据根据平台去获取收货地址跟踪号等信息 light, ebay, walmart
		if("light".equals(platform)) {
			//官网没有跟踪号，不需要处理跟踪号
			od = deWarehouseDao.getLightOrderInfosByMap(odparam);
			if(null == od) {
				throw new Oms2WmsException("官网平台找不到当前退件对应的订单相关信息（orderId=" + orderId + "）");
			}
		} else if("ebay".equals(platform)) {
			odparam.put("account", account);  //ebay的需要order_id和account才可以定位到具体订单
			od = deWarehouseDao.getEbayOrderInfosByMap(odparam);
			if(null == od) {
				throw new Oms2WmsException("eBay平台找不到当前退件对应的订单相关信息（orderId=" + orderId + ", account=" + account + "）");
			}
			trackingNumber = String.valueOf(od.get("TRACKINGNUMBER"));
		} else if("walmart".equals(platform)) {
			throw new Oms2WmsException("暂时不支持处理walmart平台的退件");
		}
		
		//先判断，如果是退件是德国的才往下走流程，否则抛出含有具体提示信息的异常
		String country = (String) od.get("CUSTOMER_COUNTRY");
		if(!"DE".equals(country)) {
			throw new Oms2WmsException("当前退件（Country=" + country + "）不属于德国，无法处理");
		}

		List<DeInWmsOrderMainModel> mains = new ArrayList<DeInWmsOrderMainModel>();
		main.setOwnerCode((String) SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_DEOWNERCODE));
		main.setStoreCode((String) SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_DESTORECODE));
		main.setId(deWarehouseDao.getInWmsOrderSequences());//主键ID
		main.setReturnOrderId(Long.parseLong(retOrderId));//退款退货表主键
		main.setIsSendWms(WarehouseConstant.IS_SEND_WMS_NO);
		main.setOrderType(WarehouseConstant.DE_IN_ORDER_TYPE_RETURNGOODS);//默认都是退库入库
		main.setPlatform(this.platformSwitch(platform));
		main.setAccount(account);//平台的国家账号标识
		main.setOrderId(orderId);//原始销售单号
		main.setTrackingNumber(trackingNumber);
		main.setNewTrackingNumber(null);
		main.setRemark(null);
		main.setWaitClaim(WarehouseConstant.DEIN_OMS_NONCLAIM);//暂时与wms退货认领单无关
		main.setRma(String.valueOf(data.get("RMA")));
		mains.add(main);
		
		//处理明细表
		List<DeInWmsOrderDetailModel> details = new ArrayList<DeInWmsOrderDetailModel>();
		String skus[] = String.valueOf(data.get("SKU")).split(",");
		String qtys[] = String.valueOf(data.get("QTY")).split(",");
		String productCaseTypes[] = String.valueOf(data.get("PRODUCT_CASE_TYPE")).split(",");
		
		Map<String,Object> skuParam = null;
		DeInWmsOrderDetailModel detail = null;
		for (int i = 0; i < skus.length; i++) {
			String psku = skus[i];
			if (psku.contains("^")) {//含有逗号的sku名称被特殊处理过,此处还原为原来sku名称
				psku = psku.replace("^", ",");
			}
			
			detail = new DeInWmsOrderDetailModel();
			detail.setId(deWarehouseDao.getInWmsOrderSequences());
			detail.setParentId(main.getId());//设置父ID
			
			skuParam = new HashMap<String, Object>();
			skuParam.put("psku", psku);
			Map<String,Object> skuMap = deWarehouseDao.getSkusByPSku(skuParam);
			if(null != skuMap && skuMap.size() > 0) {
				//如果去sku映射表能找到
				detail.setSku((String) skuMap.get("SKU"));
				int qty = Integer.parseInt(String.valueOf(skuMap.get("QTY")));
				detail.setQty(qty > 0 ? String.valueOf(Integer.parseInt(qtys[i]) * qty) : qtys[i]); //根据映射表的数量关系去计算出sku个数
			} else {
				//如果去sku映射表不能找到,直接设置好sku和数量即可
				detail.setSku(psku);
				detail.setQty(qtys[i]);
			}
			detail.setActualQty(detail.getQty());
			//detail.setSkuProperty("QC"); //产品属性：待检
			detail.setSkuProperty("OK"); //改为传"OK"
			detail.setBadReason(productCaseTypes[i]);
			detail.setCustomerName((String) od.get("CUSTOMER_NAME"));
			detail.setMobile((String) od.get("CUSTOMER_PHONE"));
			detail.setAddress(this.getInWmsAdress(od));
			details.add(detail);
		}
		
		if(mains.size() > 0) {
			deWarehouseDao.batchInsertDeInWmsOrderMain(mains);
		}
		
		if(details.size() > 0) {
			deWarehouseDao.batchInsertDeInWmsOrderDetail(details);
		}
	}
	
	/**
	* @Title: getInWmsAdress 
	* @Description: 组装入库单的地址
	* @param @param map
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	private String getInWmsAdress(Map<String,Object> map){
		StringBuffer strBuff = new StringBuffer();
		if(null !=map.get("CUSTOMER_COUNTRY")
				&& !"null".equals(String.valueOf(map.get("CUSTOMER_COUNTRY")))){
			strBuff.append(map.get("CUSTOMER_COUNTRY")).append(",");
		}
		if(null != map.get("CUSTOMER_PROVINCE")
				&&!"null".equals(String.valueOf(map.get("CUSTOMER_PROVINCE")))){
			strBuff.append(map.get("CUSTOMER_PROVINCE")).append(",");
		}
		if(null != map.get("CUSTOMER_CITY")
				&&!"null".equals(String.valueOf(map.get("CUSTOMER_CITY")))){
			strBuff.append(map.get("CUSTOMER_CITY")).append(",");
		}
		String address1 = StringUtil.cancelNewline((String) map.get("ADDRESS_ONE")) ;
		if(org.apache.commons.lang.StringUtils.isNotBlank(address1)) {
			strBuff.append(address1).append(",");
		}
		String address2 = StringUtil.cancelNewline((String) map.get("ADDRESS_TWO")) ;
		if(org.apache.commons.lang.StringUtils.isNotBlank(address2)) {
			strBuff.append(address2).append(",");
		}
		strBuff.deleteCharAt(strBuff.length() - 1);
		
		return strBuff.toString().replace(",null", "");
	}

	@Override
	public ResponseResult<OutOrderVO> findDeOutOrders(RequestParam param) {
		Map<String, Object> filter = param.getParam();
		int count = this.deWarehouseDao.countDeOutOrder(filter);
		ResponseResult<OutOrderVO> result = new ResponseResult<OutOrderVO>();
		result.setRows(this.deWarehouseDao.findDeOutOrders(filter, param.getStartRow(), param.getEndRow()));
		result.setTotal(count);
		return result;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public OperationResult changeBeforeSendWms(OutOrderChangeVO vo) throws Exception {
		Map<String, Object> param = null;
		try {
			param = new HashMap<String, Object>();
			param.put("id", vo.getId());
			param.put("orderId", vo.getOrderId());
			param.put("orderOcsId", vo.getOrderOcsId());
		} catch (Exception e) {
			throw new IllegalArgumentException(">>> changeBeforeSendWms >>> Illegal parameters: " + StringUtil.instanceDetail(vo), e);
		}
		
		DeOutWmsOrderMainModel main = this.deWarehouseDao.getOutWmsOrderMailByMap(param);
		if(null == main) {
			throw new Oms2WmsException("找不到当前出库单信息（orderId=" + vo.getOrderId() + "; orderOcsId=" + vo.getOrderOcsId() + "）");
		} else if(!"0".equals(main.getIsSendWms()) && !"3".equals(main.getIsSendWms())) {
			//只有当出库单处于未推送、推送失败的状态时，才可以修改；否则，不允许修改
			throw new Oms2WmsException("当前出库单处于" + CommonUtils.status2text(main.getIsSendWms()) + "状态，不可修改（orderId=" + vo.getOrderId() + "）");
		}
		
		String old = StringUtil.instanceDetail(main);
		//允许修改的字段：客户名称、客户国家、客户省份、客户城市、客户邮编、客户邮箱、客户电话、客户地址1、客户地址2
		main.setCustomerName(vo.getCustomerName());
		main.setCustomerCountry(vo.getCustomerCountry());
		main.setCustomerProvince(vo.getCustomerProvince());
		main.setCustomerCity(vo.getCustomerCity());
		main.setCustomerZip(vo.getCustomerZip());
		main.setCustomerEmail(vo.getCustomerEmail());
		main.setCustomerPhone(vo.getCustomerPhone());
		main.setCustomerAddress(vo.getCustomerAddress());
		main.setCustomerAddress2(vo.getCustomerAddress2());
		this.deWarehouseDao.changeBeforeSendWms(main);
		String _new = StringUtil.instanceDetail(main);
		
		String description = "出库单（orderId=" + vo.getOrderId() + "）修改成功";
		this.recordOperSuccessLog("change-out-order", vo.getId(), description + ": old=" + old + "; new=" + _new);
		return new OperationResult(0, description, null, null);
	}


	private String wmsToken() {
		String tempToken = commonUtils.getDeWmsToken();
		Map<String,Object> identity = CommonUtils.newIdentity();
		identity.put("token", tempToken);
		wmsProviderTokenDao.updateProviderTokenByMap(identity);
		return tempToken;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public OperationResult sendOutOrder(Long id, String orderId, Long orderOcsId) throws Exception {
		if(null == id || StringUtils.isBlank(orderId) || null == orderOcsId) {
			throw new IllegalArgumentException(">>> sendOutOrder >>> Illegal parameters: id=" + id + "; orderId=" + orderId + "; orderOcsId=" + orderOcsId);
		}
		
		//String token = wmsProviderTokenDao.getProviderToken(CommonUtils.newIdentity()).getToken();
		String token = this.wmsToken();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("orderId", orderId);
		param.put("orderOcsId", orderOcsId);
		Map<String, Object> data = this.deWarehouseDao.getDeOutOrder(param);
		if(null == data) {
			throw new Oms2WmsException("找不到当前出库单信息（orderId=" + orderId + "; orderOcsId=" + orderOcsId + "）");
		} else if(!"0".equals(data.get("IS_SEND_WMS").toString()) && !"3".equals(data.get("IS_SEND_WMS").toString())) {
			//只有当出库单处于推送失败的状态时，才人工干预进行推送；否则，不允许
			throw new Oms2WmsException("当前出库单处于" + CommonUtils.status2text(data.get("IS_SEND_WMS").toString()) + "状态，不可推送（orderId=" + data.get("ORDER_ID").toString() + "）");
		}
		
		boolean success = this.sendDeOutOrders(data, token);
		return new OperationResult(success ? 0 : 1, "出库单（orderId=" + orderId + "）推送" + (success ? "成功" : "失败"), null, null);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public OperationResult cancelOutOrder(Long id, String orderId, Long orderOcsId) throws Exception {
		if(null == id || StringUtils.isBlank(orderId) || null == orderOcsId) {
			throw new IllegalArgumentException(">>> cancelOutOrder >>> Illegal parameters: id=" + id + "; orderId=" + orderId + "; orderOcsId=" + orderOcsId);
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("orderId", orderId);
		param.put("orderOcsId", orderOcsId);
		DeOutWmsOrderMainModel main = this.deWarehouseDao.getOutWmsOrderMailByMap(param);
		if(null == main) {
			throw new Oms2WmsException("找不到当前出库单信息（orderId=" + orderId + "; orderOcsId=" + orderOcsId + "）");
		} else if(!"0".equals(main.getIsSendWms()) && !"3".equals(main.getIsSendWms())) {
			//只有当出库单处于未推送、推送失败的状态时，才可以取消；否则，不允许取消
			throw new Oms2WmsException("当前出库单处于" + CommonUtils.status2text(main.getIsSendWms()) + "状态，不可取消（orderId=" + orderId + "）");
		}
		
		param.clear();

		param.put("id", id);
		param.put("isSendWms", WarehouseConstant.IS_SEND_WMS_CANCELLED); //将订单置为取消状态
		this.deWarehouseDao.updateDeOutWmsOrderMainByMap(param);
		
		String description = "出库单（orderId=" + orderId + "）已成功被取消";
		this.recordOperSuccessLog("cancel-out-order", id, description);
		return new OperationResult(0, description, null, null);
	}

	@Override
	public ResponseResult<AbnormalReasonVO> findAbnormalReasons(RequestParam param) {
		Map<String, Object> filter = param.getParam();
		int count = this.deWarehouseDao.countAbnormalReason(filter);
		ResponseResult<AbnormalReasonVO> result = new ResponseResult<AbnormalReasonVO>();
		List<AbnormalReasonVO> rows = this.deWarehouseDao.findAbnormalReasons(filter, param.getStartRow(), param.getEndRow());
		CollectionUtil.each(rows, new IAction<AbnormalReasonVO>() {
			public void excute(AbnormalReasonVO vo) {
				vo.setParentTypeText(AbnormalReasonUtils.parentTypeText(vo.getParentType()));
				vo.setActionText(AbnormalReasonUtils.actionText(vo.getAction()));
			}
		});
		result.setRows(rows);
		result.setTotal(count);
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OperationResult syncWmsDiseaseOutOrders() {
		Map<String, Object> response = this.httpPostDiseaseOrders();
		String resultCode = null == response ? null : String.valueOf(response.get("resultCode"));
		if(!"0".equals(resultCode)) {
			logger.info(">>> syncWmsDiseaseOutOrders >>> failed");
			List<String> failed = new ArrayList<String>();
			failed.add("调用WMS获取异常单失败（resultCode=" + resultCode + "）");
			Map<String, List<String>> result = new HashMap<String, List<String>>();
			result.put("success", null);
			result.put("failed", failed);
			return new OperationResult(failed.isEmpty() ? 0 : 1, null, result, null);
		}
		return this.handleDiseaseOrders((List<Map<String, Object>>) response.get("data"));
	}
	
	/*public static void main(String[] args) {
		org.springframework.context.ApplicationContext context = new org.springframework.context.support.ClassPathXmlApplicationContext("classpath:spring.xml");
		IDeWarehouseService impl = (IDeWarehouseService) context.getBean("deWarehouseServiceImpl");
		//CommonUtils impl = (CommonUtils) context.getBean("commonUtils");
		impl.syncWmsDiseaseOutOrders();
	}*/

	private Map<String, Object> httpPostDiseaseOrders() {
		//String token = this.wmsProviderTokenDao.getProviderToken(CommonUtils.newIdentity()).getToken();
		String token = this.wmsToken();
		//String token = "doz4sujmrmzw0ka3ryoh4tvt"; //test...
		
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> item = new HashMap<String, Object>();
		item.put("Field", "STOCKUP_STATUS_SC");
		item.put("Method", "Equal");
		item.put("Value", "10");
		items.add(item);

		Map<String, Object> item2 = new HashMap<String, Object>();
		item2.put("Field", DISEASE_TYPE_SC);
		item2.put("Method", "NotEqual");
		items.add(item2);
		
		Map<String, Object> queryModel = new HashMap<String, Object>();
		queryModel.put("Items", items);
		
		Map<String, Object> pageInfo = new HashMap<String, Object>();
		pageInfo.put("IsPaging", false);
		
		Map<String, Object> search = new HashMap<String, Object>();
		search.put("QueryModel", queryModel);
		search.put("PageInfo", pageInfo);
		
		Map<String, Object> queryparamsters = new HashMap<String, Object>();
		queryparamsters.put("Search", search);
		//queryparamsters.put("token", token);
		
		Map<String, Object> request = new HashMap<String, Object>();
		request.put("queryparamsters", queryparamsters);
		//request.put("sumcols", null);
		request.put("whgid", (String) SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_DESTORECODE));
		request.put("token", token);

		String url = SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_WMS_URL) + WarehouseConstant.GETSHIPPINGORDERLISTBYQUERY;
		Map<String, Object> response = commonUtils.httpPostJson2DeWms(url, request);
		logger.info(">>> Http post query disease orders: request=" + JsonConvertUtil.getJsonString(request) + "; response=" + JsonConvertUtil.getJsonString(response));
		return response;
	}

	private static final String DISEASE_TYPE_SC = "DISEASE_TYPE_SC";
	//private static final String IS_CANCEL = "IS_CANCEL";
	
	private OperationResult handleDiseaseOrders(List<Map<String, Object>> data) {
		List<String> success = new ArrayList<String>(), failed = new ArrayList<String>();
		CollectionUtil.each(data, new IAction<Map<String, Object>>() {
			public void excute(Map<String, Object> map) {
				String diseaseType = (String) map.get(DISEASE_TYPE_SC);
				Object orderOcsId = map.get("SHIPPING_ORDER_ID"), orderId = map.get("EXTERNAL_ORDER_ID");
				/** 
				 * 1、关于diseaseType：
				 * 	  WMS返回的数据中，当前出库单的DISEASE_TYPE_SC不为null或empty的话，则说明当前出库单为病单。
				 *    针对这些病单，相对应的OMS的出库单也要做一些处理，例如更新状态、记录异常原因等等。
				 * 2、忽略条件：
				 *    如果当前出库单不是病单（即是正常单）的话，则忽略;
				 *    如果当前病单为以前已经同步的重复数据，则忽略。
				 * 3、关于orderOcsId，orderId：在查询出库单时，这两个条件是必须的。
				 */
				if(StringUtils.isNotBlank(diseaseType) && null != orderOcsId && StringUtils.isNotBlank(orderOcsId.toString()) 
						&& null != orderId && StringUtils.isNotBlank(orderId.toString())) {
					DeOutWmsOrderMainModel out = deWarehouseDao.getOut(Long.parseLong(orderOcsId.toString()), (String) orderId);
					if(out != null && !isRepeatDisease(out.getId(), diseaseType)) {
						DeAbnormalReasonModel reason = AbnormalReasonUtils.newOut(out.getId(), AbnormalReasonUtils.ACTION_SOD_GWD, diseaseType);
						String message = "[orderId=" + String.valueOf(orderId) + ", orderOcsId=" + String.valueOf(orderOcsId) + "]";
						try {
							afterOperateDeOutOrder(out.getId(), WarehouseConstant.IS_SEND_WMS_FAILED, WarehouseConstant.IS_ABNORMAL_1, reason);
							success.add(message);
						} catch (Exception e) {
							logger.error(">>> handleDiseaseOrders >>> " + e.getMessage(), e);
							failed.add(message);
						}
					}
				}
			}
		});
		
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		result.put("success", success);
		result.put("failed", failed);
		return new OperationResult(failed.isEmpty() ? 0 : 1, null, result, null);
	}
	
	/**
	 * 判断指定的病单类型是否重复
	 * @see 同步机制是先获取所有数据再筛选，所以，每次同步都可能同步到上次的重复数据，需要去掉重复项。
	 * @param parentId
	 * @param compareDisease
	 * @return
	 */
	private boolean isRepeatDisease(Long parentId, String compareDisease) {
		boolean[] repeats = { false };
		CollectionUtil.each(this.queryAbnormalReasons(parentId), new IActionExt<Map<String, Object>>() {
			@Override
			public boolean excute(Map<String, Object> map) {
				String reason = (String) map.get("REASON");
				boolean repeat = StringUtils.isNotBlank(reason) && reason.equals(compareDisease);
				if(repeat) {
					repeats[0] = true;
				}
				return repeat;
			}
		}, true);
		return repeats[0];
	}
	
	private List<Map<String, Object>> queryAbnormalReasons(Long parentId) {
		Assert.notNull(parentId, "The parameter 'parentId' canot be null.");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentId", parentId);
		param.put("parentType", AbnormalReasonUtils.TYPE_OUT);
		param.put("action", AbnormalReasonUtils.ACTION_SOD_GWD);
		return deWarehouseDao.queryAbnormalReasons(param);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void recordOperFailedLog(String target, Long objectId, String description) {
		this.recordOperLog(target, objectId, "failed", description);
	}

	private void recordOperSuccessLog(String target, Long objectId, String description) {
		this.recordOperLog(target, objectId, "success", description);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private void recordOperLog(String target, Long objectId, String result, String description) {
		/**
		 * 日志添加不成功发生异常也不应该影响调用者的事务，所以在该方法内部将异常处理掉，不往外抛了，且该方法的事务独立于其他事务。
		 */
		
		Long operator = null;
		try {
			UserModel user = getCurentUser();
			operator = null == user ? null : user.getId();
		} catch (Exception e) {
			logger.warn(">>> get current user >>> " + e.getMessage());
		}
		
		try {
			if(StringUtils.isNotBlank(description) && description.length() > 3000) {
				description = description.substring(0, 3000) + " ......";
			}
			this.deWarehouseDao.addOperLog(new DeWmsOperateLogModel(target, objectId, operator, result, description));
		} catch (Exception e) {
			logger.error(">>> recordOperFailedLog >>> " + e.getMessage(), e);
		}
	}

	@Override
	public void test() {
		/*Map<String, Object> map = new HashMap<String, Object>();
		map.put("SHIPPING_ORDER_ID", new Long("5267826"));
		map.put("EXTERNAL_ORDER_ID", "172861253219-1877289528007");
		Object orderOcsId = map.get("SHIPPING_ORDER_ID"), orderId = map.get("EXTERNAL_ORDER_ID");
		DeOutWmsOrderMainModel out = deWarehouseDao.getOut(Long.parseLong(orderOcsId.toString()), (String) orderId);
		System.out.println(out.toString());*/
		
		/*String sku = "3400026-DW-a";
		Map<String, Object> product = deWarehouseDao.getProduct(sku, Integer.parseInt("20"));
		System.out.println(product);*/
		
		/*List<DeOutWmsOrderMainModel> outs = this.deWarehouseDao.queryNoUploadTrackingNumberOuts("1");
		System.out.println(outs);
		outs = this.deWarehouseDao.queryNoUploadTrackingNumberOuts("2");
		System.out.println(outs);
		outs = this.deWarehouseDao.queryNoUploadTrackingNumberOuts("3");
		System.out.println(outs);*/
		
		this.scanOutsUploadTrackingNumber(1, false);
	}
}