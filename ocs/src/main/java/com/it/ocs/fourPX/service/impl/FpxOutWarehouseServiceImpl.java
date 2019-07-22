package com.it.ocs.fourPX.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.ex.BussinessException;
import com.it.ocs.common.util.StringUtil;
import com.it.ocs.fourPX.dao.FpxOutWarehouseDao;
import com.it.ocs.fourPX.ex.AbnormalOrderException;
import com.it.ocs.fourPX.ex.OverweightOrderException;
import com.it.ocs.fourPX.http.HttpRequest;
import com.it.ocs.fourPX.model.FpxOutWarehouseConsigneeModel;
import com.it.ocs.fourPX.model.FpxOutWarehouseDetailModel;
import com.it.ocs.fourPX.model.FpxOutWarehouseModel;
import com.it.ocs.fourPX.model.FpxStatus;
import com.it.ocs.fourPX.model.OutWarehouseStatus;
import com.it.ocs.fourPX.service.AccountManagerService;
import com.it.ocs.fourPX.service.FpxAbnormalService;
import com.it.ocs.fourPX.service.FpxOperLogService;
import com.it.ocs.fourPX.service.FpxOutWarehouseService;
import com.it.ocs.fourPX.service.FpxServiceUtils;
import com.it.ocs.fourPX.utils.ReflectUtils;
import com.it.ocs.fourPX.vo.Base4PXVO;
import com.it.ocs.fourPX.vo.CalculateFeeRequestVO;
import com.it.ocs.fourPX.vo.ConsigneeVO;
import com.it.ocs.fourPX.vo.DeliveryOrderItemVO;
import com.it.ocs.fourPX.vo.DeliveryOrderRequestVO;
import com.it.ocs.fourPX.vo.FourPXRequestVO;
import com.it.ocs.fourPX.vo.FpxOrderPlatform;
import com.it.ocs.fourPX.vo.FpxOutWarehouseVO;
import com.it.ocs.fourPX.vo.GetDeliveryOrderRequestVO;
import com.it.ocs.fourPX.vo.InventoryRequestVO;
import com.it.ocs.fourPX.vo.ItemListRequestVO;
import com.it.ocs.synchronou.util.JsonConvertUtil;

@Service
public class FpxOutWarehouseServiceImpl implements FpxOutWarehouseService {
	
	private static final Logger logger = Logger.getLogger(FpxOutWarehouseServiceImpl.class); 
	
	/**
	 * 4PX所有订单状态中不可逆、不可变化的状态
	 */
	private static final FpxStatus[] UNCHANGEABLE_FPXSTATUS = new FpxStatus[] { FpxStatus.S, FpxStatus.X, FpxStatus.D, FpxStatus.Q };
	
	@Autowired
	private FpxOutWarehouseDao fpxOutWarehouseDao;
	
	@Autowired
	private AccountManagerService accountManagerService;
	
	@Autowired
	private FpxOperLogService fpxOperLogService;

	@Autowired
	private FpxAbnormalService fpxAbnormalService;
	
	@Override
	public ResponseResult<FpxOutWarehouseVO> selectByPage(RequestParam param) {
		Map<String, Object> filter = param.getParam();
		
		ResponseResult<FpxOutWarehouseVO> result = new ResponseResult<FpxOutWarehouseVO>();
		
		result.setTotal(this.fpxOutWarehouseDao.count(filter));
		
		List<Map<String, Object>> data = this.fpxOutWarehouseDao.selectByPage(filter, param.getStartRow(), param.getEndRow());
		if(null != data && !data.isEmpty()) {
			List<FpxOutWarehouseVO> rows = new ArrayList<FpxOutWarehouseVO>();
			FpxOutWarehouseVO vo;
			for (Map<String, Object> map : data) {
				ReflectUtils.fillingTarget(vo = new FpxOutWarehouseVO(), map, true, false);
				rows.add(vo);
			}
			result.setRows(rows);
		}
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OperationResult syncOutFromUnshippedOrder(FpxOrderPlatform platform) {
		List<Map<String, Object>> data = this.fpxOutWarehouseDao.selectUnshippedOrders(null == platform ? null : platform.getName());
		OperationResult result = this.createPendingPushOut(data);
		if(0 != result.getErrorCode()) {
			this.fpxOperLogService.recordFailed("sync-outs-from-order", null, null, "同步出库单未发货订单到OMS的4PX出库单管理平台出错");	
		}
		return result;
	}

	public OperationResult createPendingPushOut(List<Map<String, Object>> unshippeds) {
		if(CollectionUtils.isEmpty(unshippeds)) {
			return new OperationResult(0, "没有使用4px服务的未发货源订单数据，不需要创建待推送4px的出库单数据。", null, null);
		}
		
		List<String> success = new ArrayList<String>(), failed = new ArrayList<String>();
		String orderId = null;
		
		for (Map<String, Object> unshipped : unshippeds) {
			try {
				orderId = (String) unshipped.get("ORDERID");
				Long key = this.createPendingPushOut(unshipped);
				if(null != key && key.longValue() > 0) {
					success.add(orderId);
				} else {
					failed.add(orderId);
					this.fpxOperLogService.recordFailed("create-pending-push-out", null, JsonConvertUtil.getJsonString(unshipped), null);
				}
			} catch (Exception e) {
				failed.add(orderId);
				this.fpxOperLogService.recordFailed("create-pending-push-out", null, JsonConvertUtil.getJsonString(unshipped), e.getMessage());
				logger.error("[FpxOutWarehouseServiceImpl.createPendingPushOut(...)] - Failed: " + e.getMessage(), e);
			}
		}
		
		OperationResult result = new OperationResult(success.size() > 0 ? 0 : 1, null, null, null);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", success);
		data.put("failed", failed);
		result.setData(data);
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Long createPendingPushOut(Map<String, Object> unshipped) throws Exception {
		Assert.notNull(unshipped, "The parameter 'unshipped' is null.");
		
		String postalCode = (String) unshipped.get("POSTALCODE");
		if(StringUtils.isBlank(postalCode)) {
			throw new BussinessException("创建4PX待推送出库单数据出错：邮编代码没有值。");
		}
		
		// init main ...
		FpxOutWarehouseModel main = new FpxOutWarehouseModel();
		ReflectUtils.fillingTarget(main, unshipped, true, false);
		main.setId(this.fpxOutWarehouseDao.generatePrimaryKey());
		main.init();
		main.setReferenceCode(main.getReferenceCode().replace("_", "-"));
		main.setStatus(OutWarehouseStatus.PENDING.getValue());

		// init consignee ...
		FpxOutWarehouseConsigneeModel consignee = new FpxOutWarehouseConsigneeModel();
		ReflectUtils.fillingTarget(consignee, unshipped, true, false);
		consignee.setId(this.fpxOutWarehouseDao.generatePrimaryKey());
		consignee.setParentId(main.getId());
		consignee.setStreet(StringUtil.cancelNewline(consignee.getStreet()));
		consignee.compatibleWith4px(unshipped);
		
		if(null == main.getWeight() || main.getWeight().doubleValue() <= 0.00) {
			throw new BussinessException("创建4PX待推送出库单数据出错：包裹重量没有值。");
		}
		
		// save main ...
		this.fpxOutWarehouseDao.insert(main);
		
		// handle items ...
		FpxOutWarehouseDetailModel detail = null;
		String[] skus = ((String) unshipped.get("SKU")).split(",");
		String[] quantities = ((String) unshipped.get("QUANTITY")).split(",");
		for (int i = 0, l = skus.length; i < l; i++) {
			detail = new FpxOutWarehouseDetailModel();
			detail.setId(this.fpxOutWarehouseDao.generatePrimaryKey());
			detail.setParentId(main.getId());
			detail.setSku(skus[i]);
			detail.setQuantity(Integer.parseInt(quantities[i]));
			this.fpxOutWarehouseDao.insertOutDetail(detail);
		}

		// save consignee ...
		this.fpxOutWarehouseDao.insertConsignee(consignee);
		
		return main.getId();
	}
	
	/**
	 * 根据包裹重量映射出渠道代码
	 * @param weight 包裹重量
	 * @return	String - 渠道代码
	 * @throws Exception
	 */
	private String mappingCarrierCode(FpxOutWarehouseModel main) throws Exception {
		double weight = main.getWeight(), first = 0.446, second = 1.75, third = 4.001, fourth = 30;
		/*	根据包裹重量选择渠道代码的逻辑：
		 *	[0 ~ 0.446)		-->  USEXPSM  	美国优先小包
		 *	[0.446 ~ 1.75)  -->  USEXPBIG  	美国优先大包
		 *	[1.75 ~ 4.001)  -->  Surepost  	surepost包裹
		 *	[4.001~30]		-->  USLAFEDEX  美国FEDEX本地派送
		 *	> 30KG			-->  USLAFEDEX	
		 */
		if(weight >= 0 && weight <= first) {
			return "USEXPSM"; //美国优先小包
		} else if(weight > first && weight <= second) {
			return "USEXPBIG"; //美国优先大包
		} else if(weight > second && weight <= third) {
			return "Surepost"; //surepost包裹
		} else if(weight > third && weight <= fourth) {
			return "USLAFEDEX"; //美国优先大包
		} else if(weight > fourth) {
			//throw new OverweightOrderException("出库单[" + main.getOrderid() + "]打包重量超过" + weight + "KG");
			//4px已经取消重量限制，没有超重单了
			return "USLAFEDEX"; //美国优先大包
		} 
		
		throw new BussinessException("出库单[" + main.getOrderid() + "]无法得到有效的打包重量");
	}
	
	/**
	 * 计算得到最优惠的渠道代号
	 * @param warehouseCode 仓库代码
	 * @param countryCode 收件人的国家二字码
	 * @param weight 重量
	 * @param cargoType 货物类型
	 * @param postalCode 邮编
	 * @return
	 */
	@Deprecated
	@SuppressWarnings({ "unchecked", "unused" })
	private String calcPreferentialCarrierCode(String warehouseCode, String countryCode, String weight, String cargoType, String postalCode) throws Exception {
		Assert.hasText(warehouseCode, "The parameter 'warehouseCode' is null.");
		Assert.hasText(countryCode, "The parameter 'countryCode' is null.");
		Assert.hasText(weight, "The parameter 'weight' is null.");
		Assert.hasText(cargoType, "The parameter 'cargoType' is null.");
		
		CalculateFeeRequestVO vo = new CalculateFeeRequestVO(warehouseCode, countryCode, weight, null, postalCode, cargoType);
		Map<String, Object> response = null;
		try {
			response = this.post4px(vo, FpxServiceUtils.GET_CALCULATE_FEE, false);
		} catch (Exception e) {
			throw new AbnormalOrderException(e.getMessage(), e);
		}
		
		Object tempPrice, prevfloorPrice = null, carrierCode = null;
		for (Map<String, Object> item : (List<Map<String, Object>>) response.get("data")) {
			tempPrice = item.get("totalFee");
			if(null != tempPrice) {
				if(null == prevfloorPrice || Double.parseDouble(prevfloorPrice.toString()) > Double.parseDouble(tempPrice.toString())) {
					prevfloorPrice = tempPrice;
					carrierCode = item.get("carrierCode");
				}
			}
		}
		
		if(null == carrierCode) {
			throw new AbnormalOrderException("请求4PX的getCalculateFee接口异常：没有取到符合要求的渠道代号");
		}
		
		return (String) carrierCode;
	}
	
	/**
	 * 获取4px可变化的订单状态列表
	 * @return
	 */
	private String[] getChangeableFpxStatus() {
		List<String> changeable = new ArrayList<String>();
		for (FpxStatus fs : FpxStatus.values()) {
			boolean flag = true;
			for (FpxStatus unchangeable : UNCHANGEABLE_FPXSTATUS) {
				if(fs.equals(unchangeable)) {
					flag = false;
					break;
				}
			}
			
			if(flag) {
				changeable.add(fs.toString());
			}			
		}
		return changeable.toArray(new String[changeable.size()]);
	}

	@Override
	public OperationResult syncOutFrom4px() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fpxstatus", this.getChangeableFpxStatus());
		List<Map<String, Object>> rows = this.fpxOutWarehouseDao.select(param);
		if(CollectionUtils.isEmpty(rows)) {
			return new OperationResult(0, "没有需要同步的状态的订单数据！", null, null);
		}
		
		List<String> success = new ArrayList<String>(), failed = new ArrayList<String>();

		OperationResult temp = null;
		for (Map<String, Object> main : rows) {
			Long id = null;
			try {
				id = Long.parseLong(main.get("ID").toString());
				temp = this.syncOneOutFrom4px(main);
			} catch (Exception e) {
				String action = "sync-out-from-4px";
				this.fpxOperLogService.recordFailed(action, id, String.valueOf(id), e.getMessage());
				logger.error(e.getMessage(), e);
				//如果是业务异常，则将异常消息抛向前端；否则，采用统一的异常信息，隐藏异常具体信息
				String msg = e instanceof BussinessException ? e.getMessage() : "当前订单（referenceCode=" + ((String) main.get("REFERENCECODE")) + "）触发同步4px失败！";
				temp = new OperationResult(1, msg, null, null);
			}
			
			if(0 == temp.getErrorCode()) {
				success.add(temp.getDescription());
			} else {
				failed.add(temp.getDescription());
			}
		}

		OperationResult result = new OperationResult(success.size() > 0 ? 0 : 1, null, null, null);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", success);
		data.put("failed", failed);
		result.setData(data);
		return result;
	}
	
	private String mappingOmsCarrierCode(String fpxCarrierCode, String platform) {
		//walmart不认DHL eCommerce
		boolean flag = !"walmart".equals(platform); //"walmart".equals(platform) || "ebay".equalsIgnoreCase(platform);
		if("USEXPBIG".equalsIgnoreCase(fpxCarrierCode)) {
			return flag ? "DHL eCommerce" : "USPS";
		} else if("USEXPSM".equalsIgnoreCase(fpxCarrierCode)) {
			return flag ? "DHL eCommerce" : "USPS";
		} else if("Surepost".equalsIgnoreCase(fpxCarrierCode)) {
			return "UPS";
		} else if("USLAFEDEX".equalsIgnoreCase(fpxCarrierCode)) {
			return "FedEx";
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public OperationResult syncOneOutFrom4px(Map<String, Object> mainMap) throws Exception {
		Assert.notNull(mainMap, "The parameter 'mainMap' is null.");
		
		Long id = Long.valueOf(mainMap.get("ID").toString());
		String referenceCode = (String) mainMap.get("REFERENCECODE");
		String documentCode = (String) mainMap.get("DOCUMENTCODE");
		String platform = (String) mainMap.get("PLATFORM");
		
		Assert.hasText(referenceCode, "The parameter 'REFERENCECODE' is null or empty.");
		Assert.hasText(documentCode, "The parameter 'DOCUMENTCODE' is null or empty.");
		
		GetDeliveryOrderRequestVO content = new GetDeliveryOrderRequestVO(documentCode, referenceCode);
		Map<String, Object>	response = this.post4px(content, FpxServiceUtils.GET_DELIVERY_ORDER, false);
		Map<String, Object> data = (Map<String, Object>) response.get("data");
		String fpxStatus = (String) data.get("status"), shippingNumber = (String) data.get("shippingNumber");
		String carrierCode = this.mappingOmsCarrierCode((String) mainMap.get("CARRIERCODE"), platform);
		
		if(!FpxStatus.validName(fpxStatus)) {
			throw new BussinessException("同步4PX出库单到OMS出错：4PX接口返回的订单状态（status=" + fpxStatus + "）无法被OMS识别。");
		}

		FpxStatus fs = Enum.valueOf(FpxStatus.class, fpxStatus);
		OutWarehouseStatus os = FpxServiceUtils.mappingOmsStatus(fs);
		int errorCode = this.updateStatus(mainMap, os, fs).getErrorCode();
		
		//如果有跟踪号，需要将跟踪号更新oms相关数据
		if(StringUtils.isNotBlank(shippingNumber)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("orderId", mainMap.get("ORDERID"));
			param.put("shippingNumber", shippingNumber);
			param.put("carrierCode", carrierCode);
			param.put("status", 1);
			this.fpxOutWarehouseDao.updateShippingNumber(param);
		}

		this.fpxOperLogService.recordSuccess("sync-out-from-4px", id, content.toJSONString(), JsonConvertUtil.obj2Json(response));
		
		return new OperationResult(errorCode, referenceCode, referenceCode, null);
	}
	
	@Override
	public OperationResult batchPush4px() {
		List<Long> ids = this.fpxOutWarehouseDao.selectPendingIds();
		if(CollectionUtils.isNotEmpty(ids)) {
			List<String> success = new ArrayList<String>(), failed = new ArrayList<String>();
			OperationResult temp = null;
			for (Long id : ids) {
				temp = this.push4px(id);
			}
			
			if(0 == temp.getErrorCode()) {
				success.add(temp.getDescription());
			} else {
				failed.add(temp.getDescription());
			}
			
			OperationResult result = new OperationResult(success.size() > 0 ? 0 : 1, null, null, null);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("success", success);
			data.put("failed", failed);
			result.setData(data);
			return result;
		} else {
			return new OperationResult(0, "没有需要推送4PX的数据。", null, null);
		}
	}
	
	@Override
	public OperationResult push4px(Long id) {
		try {
			return this.handlePush4px(id);
		} catch(Exception e) {
			String action = "push-out-to-4px";
			this.fpxOperLogService.recordFailed(action, id, String.valueOf(id), e.getMessage());
			try {
				if(e instanceof AbnormalOrderException) {
					this.fpxAbnormalService.recordOutAbnormal(id, action, e.getMessage());
					this.updateStatus(id, OutWarehouseStatus.ABNORMAL, null);
				} else if(e instanceof OverweightOrderException) {
					this.updateStatus(id, OutWarehouseStatus.OVERWEIGHT, null);
				}
			} catch (Exception e1) {
				logger.error(e1.getMessage(), e1);
			}
			logger.error(e.getMessage(), e);
			//如果是业务异常，则将异常消息抛向前端；否则，采用统一的异常信息，隐藏异常具体信息
			return new OperationResult(1, e instanceof BussinessException ? e.getMessage() : "推送失败，请稍后再试！", null, null);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public OperationResult handlePush4px(Long id) throws Exception {
		Assert.notNull(id, "The parameter 'id' is null.");
		
		Map<String, Object> mainMap = this.fpxOutWarehouseDao.load(id);
		if(null == mainMap) {
			throw new BussinessException("推送出库单到4PX出现错误：找不到指定的出库单。（id=" + id + "）");
		}
		
		String status = (String) mainMap.get("STATUS");
		if(!OutWarehouseStatus.PENDING.getValue().equals(status) && !OutWarehouseStatus.ABNORMAL.getValue().equals(status)) {
			throw new BussinessException("推送出库单到4PX出现错误：当前出库单处于" + status + "状态，不可推送。（id=" + id + "）");
		}
		
		Long parentId = Long.parseLong(mainMap.get("ID").toString());
		
		List<Map<String, Object>> details = this.fpxOutWarehouseDao.loadDetails(parentId);
		if(CollectionUtils.isEmpty(details)) {
			throw new BussinessException("推送出库单到4PX出现错误：找不到指定的出库单的产品明细信息。（id=" + id + "）");
		}
		
		Map<String, Object> consigneeMap = this.fpxOutWarehouseDao.loadConsignee(parentId);
		if(null == consigneeMap) {
			throw new BussinessException("推送出库单到4PX出现错误：找不到指定的出库单的收货人信息。（id=" + id + "）");
		}

		String postalCode = (String) consigneeMap.get("POSTALCODE");
		FpxOutWarehouseModel main = new FpxOutWarehouseModel();
		ReflectUtils.fillingTarget(main, mainMap, true, false);
		main.refresh();
		
		List<DeliveryOrderItemVO> items = new ArrayList<DeliveryOrderItemVO>();
		DeliveryOrderItemVO item = null;
		Map<String, Integer> sku2quantity = new HashMap<String, Integer>();
		for (int i = 0; i < details.size(); i++) {
			item = new DeliveryOrderItemVO();
			ReflectUtils.fillingTarget(item, details.get(i), true, false);
			items.add(item);
			sku2quantity.put(item.getSku().toUpperCase(), Integer.parseInt(item.getQuantity()));
		}
		
		main.setWeight(this.sumWeightFrom4px(sku2quantity));
		
		//通过业务规则来映射渠道代号
		main.setCarrierCode(this.mappingCarrierCode(main));
				
		//仓库代码是动态的，事先并不确定，需要根据库存来计算
		main.setWarehouseCode(this.calcApplicableWarehouseCode(postalCode, main, sku2quantity));
		//物流渠道代码也是动态的，事先并不确定，需要根据仓库代码、国家代码、包裹重量等信息来计算
		//计算运送的渠道代号：参数cargoType的值限定为P（可选值有两个：L - 信封或Letter；P - 包裹）
		//放弃采用4PX接口取数（接口数据不准确）
		//main.setCarrierCode(this.calcPreferentialCarrierCode(main.getWarehouseCode(), (String) consigneeMap.get("COUNTRYCODE"), main.getWeight().toString(), "P", postalCode));
		
		this.fpxOutWarehouseDao.update(main); //数据发生变化，需要更新
		mainMap = this.fpxOutWarehouseDao.load(id); //数据有更新，需要重新加载
		
		//组装用于推送4px出库单生成4px发货单的http请求内容
		DeliveryOrderRequestVO content = new DeliveryOrderRequestVO();
		ReflectUtils.fillingTarget(content, mainMap, true, false);
		content.setItems(items);
		content.setConsignee(new ConsigneeVO());
		ReflectUtils.fillingTarget(content.getConsignee(), consigneeMap, true, false);
		
		Map<String, Object> response = this.post4px(content, FpxServiceUtils.CREATE_DELIVERY_ORDER, false);
		Map<String, Object> data = (Map<String, Object>) response.get("data");
		if(!"Y".equals((String) data.get("ack"))) {
			List<Map<String, Object>> errors = (List<Map<String, Object>>) data.get("errors");
			StringBuilder message = new StringBuilder("");
			for (Map<String, Object> error : errors) {
				message.append("{code=").append(error.get("code")).append(",codeNote=").append(error.get("codeNote")).append("}");
			}
			throw new AbnormalOrderException("推送出库单到4PX出现错误：4PX API Message = " + message.toString());
		}
		
		//为更新状态而重新实例化与对象赋值（更新是orm方式的全字段更新，需要考虑其他字段值不受到影响）
		main = new FpxOutWarehouseModel();
		ReflectUtils.fillingTarget(main, mainMap, true, false);
		main.refresh();
		main.setStatus(OutWarehouseStatus.PUSHED.getValue());
		main.setPushedat(new Date());
		main.setDocumentCode((String) data.get("documentCode"));
		this.fpxOutWarehouseDao.update(main);
		
		this.fpxOperLogService.recordSuccess("push-out-to-4px", main.getId(), content.toJSONString(), JsonConvertUtil.obj2Json(response));
		
		return new OperationResult(0, "出库单[" + main.getReferenceCode() + "]已成功推送至4PX系统。", main.getDocumentCode(), null);
	}

	@SuppressWarnings("unchecked")
	private Double sumWeightFrom4px(Map<String, Integer> sku2quantity) throws Exception {
		ItemListRequestVO content = new ItemListRequestVO();
		List<String> skus = new ArrayList<String>();
		for (String sku : sku2quantity.keySet()) {
			skus.add(sku);
		}
		content.setLstSku(skus.toArray(new String[skus.size()]));
		
		Map<String, Object> response = this.post4px(content, FpxServiceUtils.GET_ITEM_LIST, true);
		List<Map<String, Object>> data = (List<Map<String, Object>>) response.get("data");
		if(null == data) {
			throw new AbnormalOrderException("调用4PX接口" + FpxServiceUtils.GET_ITEM_LIST + "返回空数据。(data=null; skus=" + Arrays.toString(content.getLstSku()) + ")");
		}
		
		Map<String, Double> sku2weight = new HashMap<String, Double>();
		for (Map<String, Object> map : data) {
			sku2weight.put(map.get("sku").toString().toUpperCase(), Double.valueOf(map.get("weight").toString()));
		}
		
		for (String sku : skus) {
			if(!sku2weight.containsKey(sku)) {
				throw new AbnormalOrderException("调用4PX接口" + FpxServiceUtils.GET_ITEM_LIST + "返回数据不完整。(sku=" + sku + "在4px系统不存在)");
			}
		}
		
		double weight = 0;
		for (Map.Entry<String, Integer> kv : sku2quantity.entrySet()) {
			weight += sku2weight.get(kv.getKey()) * kv.getValue();
		}
		return weight;
	}

	/**
	 * 计算得到适用的发货仓库代码</br>
	 * <p>
	 * 默认以邮编就近的仓库进行发货，但是需要考虑仓库的库存，如果库存不够的话，需要转仓发货。</br>
	 * 所以，需要对仓库的库存进行校验，校验逻辑是：</br>
	 * 最先检查第一仓库库存，库存不足则检查第二仓库库存，库存不足则检查第一、第二仓库的总库存（总库存用于提示，不作为发货的库存依据）。
	 * </p>
	 * @param postalCode
	 * @param main
	 * @param sku2quantity
	 * @return
	 * @throws Exception
	 */
	private String calcApplicableWarehouseCode(String postalCode, FpxOutWarehouseModel main, Map<String, Integer> sku2quantity) throws Exception {
		DeliveryCheckResult dcr = this.checkDeliveryInfo(sku2quantity, FpxServiceUtils.mappingWarehouseCode(postalCode)); 

		/* **************
		 * 关于库存与发货的多种情况：
		 * 1) 条件：第一、第二仓库的总库存不够。
		 *	    结果：无法发货，给与包括第一、第二仓库与总库存不足的提示。
		 * 2) 第一、第二仓库的总库存足够，但是其中任何单个的仓库库存都不够。
		 *	    结果：无法发货，给与第一、第二仓库库存不足的提示。
		 * 3) 第一仓库库存不够，第二仓库库存足够。
		 *	    结果：转到第二仓库发货，给与第一仓库库存不够的提示。
		 * 4) 第一仓库库存足够。
		 *	    结果：使用第一仓库发货，不需要给与其他提示。
		 **************/
		
		//第一、第二仓库都没有满足发货需要的库存，无法发货，直接抛异常。
		if(CollectionUtils.isNotEmpty(dcr.getMergeMessage()) || CollectionUtils.isNotEmpty(dcr.getSecondMessage())) {
			throw new AbnormalOrderException("推送4PX出现库存问题：" + dcr.listToString(dcr.outputAllMessage()));
		}
		
		//如果：第一仓库库存不够，第二仓库库存足够，转到第二仓库发货，因满足发货条件，所以不能抛异常再后续处理，需要在此记录第一仓库库存不足的提示信息
		//否则：第一仓库库存足够，适用第一仓库发货，没有库存不足的提示信息
		if(CollectionUtils.isNotEmpty(dcr.getFirstMessage())) {
			this.fpxAbnormalService.recordOutAbnormal(main.getId(), "ou-warehouse-changes", "推送4PX尝试转仓发货：" + dcr.listToString(dcr.outputAllMessage()));
		}
		
		return dcr.getApplicableWarehouseCode(); //返回适用的发货仓库代码
	}

	/**
	 * 检查发货信息
	 * @param sku2qty {@link java.util.Map}: key=sku; value=数量
	 * @param firstWarehouseCode 优先使用的仓库代码
	 * @return
	 * @throws Exception
	 */
	private DeliveryCheckResult checkDeliveryInfo(Map<String, Integer> sku2qty, String firstWarehouseCode) throws Exception {
		DeliveryCheckResult dcr = new DeliveryCheckResult();
		dcr.setFirstWarehouseCode(firstWarehouseCode);
		dcr.setApplicableWarehouseCode(firstWarehouseCode);
		
		Map<String, Integer> firstAvailable = this.getAvailableInventory(sku2qty, firstWarehouseCode);
		Map<String, String> firstMessage = this.checkInventory(sku2qty, firstAvailable, firstWarehouseCode);
		if(null != firstMessage) {
			dcr.setFirstMessage(new ArrayList<String>(firstMessage.values()));
		}
		
		//如果第一仓库库存是足够的，则库存足够，不需要再进行后续第二仓库、第一与第二仓库合并库存检查
		if(CollectionUtils.isEmpty(dcr.getFirstMessage())) {
			return dcr;
		}
		
		String secondWarehouseCode = FpxServiceUtils.WAREHOUSECODE_USNY.equals(firstWarehouseCode) ? FpxServiceUtils.WAREHOUSECODE_USLA : FpxServiceUtils.WAREHOUSECODE_USNY;
		dcr.setSecondWarehouseCode(secondWarehouseCode);
		dcr.setApplicableWarehouseCode(secondWarehouseCode);

		String skus = Arrays.toString(firstMessage.keySet().toArray(new String[firstMessage.size()]));
		dcr.setWarehouseChanges(String.format("转仓：%s仓库%S的库存不足，系统尝试通过%s仓库进行发货。", firstWarehouseCode, skus, secondWarehouseCode, secondWarehouseCode));
		
		Map<String, Integer> secondAvailable = this.getAvailableInventory(sku2qty, secondWarehouseCode);
		Map<String, String> secondMessage = this.checkInventory(sku2qty, secondAvailable, secondWarehouseCode);
		if(null != secondMessage) {
			dcr.setSecondMessage(new ArrayList<String>(secondMessage.values()));
		}
		
		//如果第二仓库库存是足够的，则使用第二仓库发货，不需要再进行第一与第二仓库合并库存检查
		if(CollectionUtils.isEmpty(dcr.getSecondMessage())) {
			return dcr;
		}
		
		//合并第一、第二仓库库存，再看看合并后库存够不够（仅仅是为了提示，而不做发货依据）
		Map<String, Integer> merge = new HashMap<String, Integer>();
		Integer first, second;
		for (String key  : firstAvailable.keySet()) {
			first = firstAvailable.get(key);
			second = secondAvailable.get(key);
			merge.put(key, (null == first ? 0 : first) + (null == second ? 0 : second));
		}
		Map<String, String> mergeMessage = this.checkInventory(sku2qty, merge, "Total: " + firstWarehouseCode + " + " + secondWarehouseCode);
		if(null != mergeMessage) {
			dcr.setMergeMessage(new ArrayList<String>(mergeMessage.values()));	
		}
		
		//如果第二仓库库存也不够的话，则将适用仓库恢复到第一仓库
		if(!CollectionUtils.isEmpty(dcr.getMergeMessage())) {
			dcr.setApplicableWarehouseCode(firstWarehouseCode);
		}
		
		return dcr;
	}
	
	private Map<String, String> checkInventory(Map<String, Integer> need, Map<String, Integer> available, String warehouseCode) {
		Map<String, String> message = null;
		for (Map.Entry<String, Integer> kv : available.entrySet()) {
			if(kv.getValue() < need.get(kv.getKey())) {
				if(null == message) {
					message = new HashMap<String, String>();
				}
				message.put(kv.getKey(), String.format("%s仓库的%s库存不足。（need=%s; available=%s）", warehouseCode, kv.getKey(), need.get(kv.getKey()), kv.getValue()));
			} 
		}
		return message;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Integer> getAvailableInventory(Map<String, Integer> need, String warehouseCode) throws Exception {
		InventoryRequestVO content = new InventoryRequestVO();
		content.setLstSku(new String[need.size()]);
		int i = 0;
		for (String key : need.keySet()) {
			content.getLstSku()[i++] = key; 
		}
		content.setWarehouseCode(warehouseCode);
		Map<String, Object> response = this.post4px(content, FpxServiceUtils.GET_INVENTORY, true);

		Map<String, Integer> available = new HashMap<String, Integer>();
		Object data = response.get("data");
		
		if(null == data) {
			for (String	sku: need.keySet()) {
				available.put(sku.toUpperCase(), 0);
			}
		} else {
			String tsku;
			for (Map<String, Object> map : (List<Map<String, Object>>) data) {
				tsku = map.get("sku").toString().toUpperCase();
				for (String	sku: need.keySet()) {
					if(tsku.equalsIgnoreCase(sku)) {
						if(!available.containsKey(tsku)) {
							available.put(tsku, 0);
						}
						available.put(tsku, available.get(sku) + Integer.parseInt(map.get("availableQuantity").toString()));
					}
				}
			}
		}
		
		return available;
	}
	
	@Override
	public OperationResult cancel(Long id) {
		try {
			return this.updateStatus(id, OutWarehouseStatus.CANCELLED, null);
		} catch(Exception e) {
			this.fpxOperLogService.recordFailed("cancel-out", id, String.valueOf(id), e.getMessage());
			logger.error(e.getMessage(), e);
			//如果是业务异常，则将异常消息抛向前端；否则，采用统一的异常信息，隐藏异常具体信息
			return new OperationResult(1, e instanceof BussinessException ? e.getMessage() : "取消失败，请稍后再试！", null, null);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public OperationResult updateStatus(Map<String, Object> main, OutWarehouseStatus omsStatus, FpxStatus fpxStatus)  throws Exception {
		String status = (String) main.get("STATUS");
		if(null != omsStatus) {
			if(OutWarehouseStatus.CANCELLED.getValue().equals(omsStatus.getValue())) {
				if(!OutWarehouseStatus.PENDING.getValue().equals(status) && !OutWarehouseStatus.ABNORMAL.getValue().equals(status)) {
					throw new BussinessException("取消出库单出现错误：当前出库单处于" + status + "状态，不可取消。（referenceCode=" + (String) main.get("REFERENCECODE") + "）");
				}	
			}	
		}

		String description = "出库单[" + ((String) main.get("REFERENCECODE")) + "]状态无变化，不需要更新。";
		if(null != omsStatus || null != fpxStatus) {
			FpxOutWarehouseModel model = new FpxOutWarehouseModel();
			ReflectUtils.fillingTarget(model, main, true, false);
			String request = JsonConvertUtil.obj2Json(model);
			model.refresh();
			
			//oms业务状态
			if(null != omsStatus) {
				model.setStatus(omsStatus.getValue());
				if(OutWarehouseStatus.FEEDBACK.getValue().equals(model.getStatus())) {
					model.setFeedbackat(model.getUpdatedat());
				}
			}
			
			//4px业务状态
			if(null != fpxStatus) {
				model.setFpxstatus(fpxStatus.toString());	
			}
			
			this.fpxOutWarehouseDao.update(model);
			this.fpxOperLogService.recordSuccess("update-out-status[" + (null == omsStatus ? "" : omsStatus.getValue()) + "]", model.getId(), request, JsonConvertUtil.obj2Json(model));
			description = "出库单[" + model.getReferenceCode() + "]状态已成功被更新。";
		}
		
		return new OperationResult(0, description, null, null);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public OperationResult updateStatus(Long id, OutWarehouseStatus omsStatus, FpxStatus fpxStatus)  throws Exception {
		Assert.notNull(id, "The parameter 'id' is null.");
		
		Map<String, Object> main = this.fpxOutWarehouseDao.load(id);
		if(null == main) {
			throw new BussinessException("更新出库单状态出现错误：找不到指定的出库单。（id=" + id + "）");
		}
		
		return this.updateStatus(main, omsStatus, fpxStatus);
	}

	@Override
	public OperationResult saveChanges(FpxOutWarehouseVO vo) {
		try {
			return this.update(vo);
		} catch (Exception e) {
			this.fpxOperLogService.recordFailed("modify-out", vo.getId(), String.valueOf(vo.getId()), e.getMessage());
			logger.error(e.getMessage(), e);
			//如果是业务异常，则将异常消息抛向前端；否则，采用统一的异常信息，隐藏异常具体信息
			return new OperationResult(1, e instanceof BussinessException ? e.getMessage() : "推送失败，请稍后再试！", null, null);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public OperationResult update(FpxOutWarehouseVO vo) throws Exception {
		Assert.notNull(vo, "The parameter 'vo' is null.");
		
		Map<String, Object> mainMap = this.fpxOutWarehouseDao.load(vo.getId());
		if(null == mainMap) {
			throw new BussinessException("修改出库单出现错误：找不到指定的出库单。（id=" + vo.getId() + "）");
		}
		
		Map<String, Object> consigneeMap = this.fpxOutWarehouseDao.loadConsignee(vo.getId());
		if(null == consigneeMap) {
			throw new BussinessException("修改出库单出现错误：找不到指定的出库单的收货人信息。（id=" + vo.getId() + "）");
		}
		
		FpxOutWarehouseModel main = new FpxOutWarehouseModel(); 
		ReflectUtils.fillingTarget(main, mainMap, true, false);
		String request = JsonConvertUtil.obj2Json(main);
		BeanUtils.copyProperties(vo, main, ReflectUtils.clientUneditableFields(FpxOutWarehouseModel.class));
		
		FpxOutWarehouseConsigneeModel consignee = new FpxOutWarehouseConsigneeModel();
		ReflectUtils.fillingTarget(consignee, consigneeMap, true, false);
		request = JsonConvertUtil.obj2Json(consignee);
		BeanUtils.copyProperties(vo, consignee, ReflectUtils.clientUneditableFields(FpxOutWarehouseConsigneeModel.class));
		
		this.fpxOutWarehouseDao.update(main);
		this.fpxOutWarehouseDao.updateConsignee(consignee);

		this.fpxOperLogService.recordSuccess("modify-out-main", main.getId(), request, JsonConvertUtil.obj2Json(main));
		this.fpxOperLogService.recordSuccess("modify-out-consignee", consignee.getId(), request, JsonConvertUtil.obj2Json(consignee));

		return new OperationResult(0, "出库单[" + main.getReferenceCode() + "]已成功被修改。", null, null);
	}
	
	@SuppressWarnings("unchecked")
	private <T extends Base4PXVO> Map<String, Object> post4px(T content, String interfaceMethod, boolean allowDataNull) throws Exception {
		FourPXRequestVO request = this.accountManagerService.selectOneByCode(interfaceMethod);
		request.setContent(content.toJSONString());

		String response = HttpRequest.post(request);
		Map<String, Object> map = (Map<String, Object>) JsonConvertUtil.json2Obj(response, Map.class);
		
		this.fpxOperLogService.record("oms-4px-http-connect", null, null != map ? "success" : "failed", JsonConvertUtil.obj2Json(request), response);
		
		if(null == map) {
			throw new BussinessException("请求4PX的" + interfaceMethod + "接口未得到有效的返回值。");
		} else if(!"0".equals((String) map.get("errorCode"))) {
			throw new BussinessException("请求4PX的" + interfaceMethod + "接口异常：errorMsg=" + (String) map.get("errorMsg"));
		}

		if(!allowDataNull && null == map.get("data")) {
			throw new BussinessException("请求4PX的" + interfaceMethod + "接口返回的数据为null。（errorMsg=" + ((String) map.get("errorMsg")) + "）");
		}
		
		return map;
	}
}

/**
 * 发货信息检查结果
 * @author zhouyancheng
 *
 */
class DeliveryCheckResult {
	private List<String> firstMessage;
	private String firstWarehouseCode;
	private String warehouseChanges;
	private List<String> secondMessage;
	private String secondWarehouseCode;
	private List<String> mergeMessage;
	private String applicableWarehouseCode;
	
	public String getFirstWarehouseCode() {
		return firstWarehouseCode;
	}
	public void setFirstWarehouseCode(String firstWarehouseCode) {
		this.firstWarehouseCode = firstWarehouseCode;
	}
	public String getSecondWarehouseCode() {
		return secondWarehouseCode;
	}
	public void setSecondWarehouseCode(String secondWarehouseCode) {
		this.secondWarehouseCode = secondWarehouseCode;
	}
	public List<String> getFirstMessage() {
		return firstMessage;
	}
	public void setFirstMessage(List<String> firstMessage) {
		this.firstMessage = firstMessage;
	}
	public String getWarehouseChanges() {
		return warehouseChanges;
	}
	public void setWarehouseChanges(String warehouseChanges) {
		this.warehouseChanges = warehouseChanges;
	}
	public List<String> getSecondMessage() {
		return secondMessage;
	}
	public void setSecondMessage(List<String> secondMessage) {
		this.secondMessage = secondMessage;
	}
	public List<String> getMergeMessage() {
		return mergeMessage;
	}
	public void setMergeMessage(List<String> mergeMessage) {
		this.mergeMessage = mergeMessage;
	}
	public String getApplicableWarehouseCode() {
		return applicableWarehouseCode;
	}
	public void setApplicableWarehouseCode(String applicableWarehouseCode) {
		this.applicableWarehouseCode = applicableWarehouseCode;
	}
	
	public String listToString(List<String> list) {
		return Arrays.toString(list.toArray(new String[list.size()]));
	}
	
	public List<String> outputAllMessage() {
		List<String> all = new ArrayList<String>();
		
		if(CollectionUtils.isNotEmpty(this.getMergeMessage())) {
			all.add("总库存不足：" + this.listToString(this.getMergeMessage()));
		}
		
		if(CollectionUtils.isNotEmpty(this.getSecondMessage())) {
			all.add( this.getSecondWarehouseCode() + "库存不足：" + this.listToString(this.getSecondMessage()));
		}
		
		if(StringUtils.isNotBlank(this.getWarehouseChanges())) {
			all.add(this.getWarehouseChanges());
		}
		
		if(CollectionUtils.isNotEmpty(this.getFirstMessage())) {
			all.add( this.getFirstWarehouseCode() + "库存不足：" + this.listToString(this.getFirstMessage()));
		}
		
		return all;
	}
}
