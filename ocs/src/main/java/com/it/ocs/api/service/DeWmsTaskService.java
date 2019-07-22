package com.it.ocs.api.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.api.constant.WarehouseConstant;
import com.it.ocs.api.dao.IDeWarehouseDao;
import com.it.ocs.api.dao.IWmsProviderTokenDao;
import com.it.ocs.api.ex.Oms2WmsException;
import com.it.ocs.api.ex.SkuInventoryException;
import com.it.ocs.api.model.DeAbnormalReasonModel;
import com.it.ocs.api.utils.AbnormalReasonUtils;
import com.it.ocs.api.utils.CommonUtils;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.synchronou.util.JsonConvertUtil;

/**
 * @ClassName: DeWmsTaskService
 * @Description: 德国仓出入库定时任务
 * @author wgc
 * @date 2018年4月11日 下午5:53:51
 *
 */
@Service("deWmsTaskService")
public class DeWmsTaskService {

	private static final Logger logger = Logger.getLogger(DeWmsTaskService.class);

	@Autowired
	private IDeWarehouseDao deWarehouseDao;
	
	@Autowired
	private IDeWarehouseService deWarehouseService;

	@Autowired
	private IWmsProviderTokenDao wmsProviderTokenDao;
	
	@Autowired
	private CommonUtils commonUtils;

	/**
	 * Spring Schedule: 获取订单，生成待推送WMS平台的出库单
	 */
	public void selectOutWmsOrders() {
		this.saveDeOutWmsOrders(this.deWarehouseDao.getDeNoshipOrderInfoFromLight(), "1");  //获取官网平台订单数据
		this.saveDeOutWmsOrders(this.deWarehouseDao.getDENoShipOrderInfoFromEbay(), "2");   //获取ebay平台订单数据
		this.saveDeOutWmsOrders(this.deWarehouseDao.getDeNoshipOrderInfoFromAmazon(), "3"); //获取Amazon平台订单数据
		this.saveDeOutWmsOrders(this.deWarehouseDao.getDeNoshipOrderInfoFromVC(), "5"); //获取VC订单数据
	}

	/**
	 * Spring Schedule: 获取官网订单，生成待推送WMS平台的出库单
	 */
	public void selectLightOutWmsOrders() {
		this.saveDeOutWmsOrders(this.deWarehouseDao.getDeNoshipOrderInfoFromLight(), "1"); //获取官网平台
	}

	/**
	 * Spring Schedule: 获取ebay订单，生成待推送WMS平台的出库单
	 */
	public void selectEbayOutWmsOrders() {
		this.saveDeOutWmsOrders(this.deWarehouseDao.getDENoShipOrderInfoFromEbay(), "2"); //获取ebay平台
	}

	/**
	 * Spring Schedule: 获取亚马逊订单，生成待推送WMS平台的出库单
	 */
	public void selectAmazonOutWmsOrders() {
		this.saveDeOutWmsOrders(this.deWarehouseDao.getDeNoshipOrderInfoFromAmazon(), "3");
	}

	/**
	 * Spring Schedule: 获取VC订单，生成待推送WMS平台的出库单
	 */
	public void selectVcOutWmsOrders() {
		this.saveDeOutWmsOrders(this.deWarehouseDao.getDeNoshipOrderInfoFromVC(), "5");
	}
	
	/**
	 * 保存待推送出库单
	 * @param orders 订单数据集（来源于订单表）
	 * @param platform 平台：1-官网；2-ebay；3-亚马逊；4-沃尔玛；5-VC
	 */
	private void saveDeOutWmsOrders(List<Map<String, Object>> orders, String platform) {
		CollectionUtil.each(orders, new IAction<Map<String, Object>>() {
			public void excute(Map<String, Object> data) {	
				try {
					deWarehouseService.saveDeOutOrders(platform, data);
				} catch(Oms2WmsException e) {
					if(!"exists".equals(e.getCode())) {
						//已经存在的异常不记录日志（定时任务取数，会经常出现已经存的现象，记录日志毫无意义且会导致日志数据爆炸）
						logger.warn(">>> Out-Order >>> " + e.getMessage() + ": " + JsonConvertUtil.getJsonString(data));
						deWarehouseService.recordOperFailedLog("save-out-order", null, e.getMessage() + ": " + JsonConvertUtil.getJsonString(data));
					}
				} catch (Exception e) {
					logger.error(">>> Out-Order Error >>> platform=" + platform + "; message=" + e.getMessage() + ": " + JsonConvertUtil.getJsonString(data), e);
					deWarehouseService.recordOperFailedLog("save-out-order", null, e.getMessage() + ": " + JsonConvertUtil.getJsonString(data));
				}
			}
		});
	}

	/**
	 * Spring Schedule: 获取退货退款单，生成待推送WMS平台的入库单
	 */
	public void selectInWmsOrders() {
		CollectionUtil.each(this.deWarehouseDao.getDeInWmsOrderInfos(), new IAction<Map<String, Object>>() {
			public void excute(Map<String, Object> data) {
				try {
					deWarehouseService.saveDeInOrders(data);
				} catch (Exception e) {
					String message = "request=" + JsonConvertUtil.getJsonString(data) + "; response=" + e.getMessage();
					boolean isRecordLog = true;
					if(e instanceof Oms2WmsException) {
						//已经存在的异常不记录日志（定时任务取数，会经常出现已经存的现象，记录日志毫无意义且会导致日志数据爆炸）
						isRecordLog = !"exists".equals(((Oms2WmsException) e).getCode());
						if(isRecordLog) {
							logger.error(">>> In-Order Error >>> " + message);
						}
					} else {
						logger.error(">>> In-Order Error >>> " + message, e);
					}
					if(isRecordLog) {
						deWarehouseService.recordOperFailedLog("save-in-order", null, message);	
					}
				}
			}
		});
	}

	/**
	 * Spring Schedule: 推送德国仓官网（LE）出库单到WMS系统
	 * @return void
	 */
	public void sendDeLightOutOrderToWms() {
		this.sendDeOutToWms(this.deWarehouseDao.getSendDeOutWmsOrders(1));
	}

	/**
	 * Spring Schedule: 推送德国仓Ebay出库单到WMS系统
	 * @return void
	 */
	public void sendDeEbayOutOrderToWms() {
		this.sendDeOutToWms(this.deWarehouseDao.getSendDeOutWmsOrders(2));
	}
	
	/**
	 * Spring Schedule: 推送德国仓Amazon出库单到WMS系统
	 * @return void
	 */
	public void sendDeAmazonOutOrderToWms() {
		this.sendDeOutToWms(this.deWarehouseDao.getSendDeOutWmsOrders(3));
	}
	
	/**
	 * Spring Schedule: 推送VC出库单到WMS系统
	 * @return void
	 */
	public void sendDeVcOutOrderToWms() {
		this.sendDeOutToWms(this.deWarehouseDao.getSendDeOutWmsOrders(5));
	}

	private String wmsToken() {
		String tempToken = commonUtils.getDeWmsToken();
		Map<String,Object> identity = CommonUtils.newIdentity();
		identity.put("token", tempToken);
		wmsProviderTokenDao.updateProviderTokenByMap(identity);
		return tempToken;
	}
	
	/**
	 * 推送德国仓出库单到WMS系统
	 * @param outs 待推送的出库单集合
	 * @return void
	 */
	private void sendDeOutToWms(List<Map<String, Object>> outs) {
		//String token = wmsProviderTokenDao.getProviderToken(CommonUtils.newIdentity()).getToken();
		String token = wmsToken();
		CollectionUtil.each(outs, new IAction<Map<String, Object>>() {
			public void excute(Map<String, Object> data) {
				Long id = null;
				try {
					id = Long.parseLong(String.valueOf(data.get("ID")));
					deWarehouseService.sendDeOutOrders(data, token);
				} catch(SkuInventoryException e) {
					try {
						DeAbnormalReasonModel reason = AbnormalReasonUtils.newOut(id, AbnormalReasonUtils.ACTION_SOT_CSI, e.getMessage());
						deWarehouseService.afterOperateDeOutOrder(id, WarehouseConstant.IS_SEND_WMS_FAILED, WarehouseConstant.IS_ABNORMAL_1, reason);
					} catch (Exception e1) {
						logger.error(">>> Out-Order Error >>> changeDeOutOrderStatus >>> " + e.getMessage(), e);
						deWarehouseService.recordOperFailedLog("after-send-out-order", id, e1.getMessage() + ":" + JsonConvertUtil.getJsonString(data));
					}
					logger.error(">>> Out-Order Error >>> " + e.getMessage() + ": " + JsonConvertUtil.getJsonString(data));
					deWarehouseService.recordOperFailedLog("send-out-order", id, e.getMessage() + ":" + JsonConvertUtil.getJsonString(data));
				} catch (Exception e) {
					logger.error(">>> Out-Order Error >>> " + e.getMessage() + ": " + JsonConvertUtil.getJsonString(data), e);
					deWarehouseService.recordOperFailedLog("send-out-order", id, e.getMessage() + ":" + JsonConvertUtil.getJsonString(data));
				}
			}
		});
	}

	/**
	 * Spring Schedule: 推送德国仓入库单到WMS系统
	 * @return void
	 */
	public void sendDeInWmsOrder() {
		//String token = this.wmsProviderTokenDao.getProviderToken(CommonUtils.newIdentity()).getToken();
		String token = wmsToken();
		//将未推送的非认领单的入库单推送至WMS平台
		CollectionUtil.each(this.deWarehouseDao.getSendDeInWmsOrders(), new IAction<Map<String, Object>>() {
			public void excute(Map<String, Object> data) {	
				try {
					deWarehouseService.sendDeInOrders(data, token);
				} catch (Exception e) {
					logger.error(">>> In-Order Error >>> " + e.getMessage() + ": request=" + JsonConvertUtil.getJsonString(data), e);
				}
			}
		});
	}

	/**
	 * Spring Schedule: 扫描上传light（官网）订单跟踪号
	 */
	public void scanUploadLightTrackingNumber() {
		this.deWarehouseService.scanOutsUploadTrackingNumber(1, false);
	}
	
	/**
	 * Spring Schedule: 扫描上传ebay订单跟踪号
	 */
	public void scanUploadEbayTrackingNumber() {
		this.deWarehouseService.scanOutsUploadTrackingNumber(2, false);
	}
	
	/**
	 * Spring Schedule: 扫描上传amazon（亚马逊）订单跟踪号
	 */
	public void scanUploadAmazonTrackingNumber() {
		this.deWarehouseService.scanOutsUploadTrackingNumber(3, false);
	}

	/**
	 * Spring Schedule: 扫描上传VC订单跟踪号
	 */
	public void scanUploadVcTrackingNumber() {
		this.deWarehouseService.scanOutsUploadTrackingNumber(5, false);
	}

	/**
	 * Spring Schedule: 最后一次扫描上传light（官网）订单跟踪号
	 */
	public void lastScanUploadLightTrackingNumber() {
		this.deWarehouseService.scanOutsUploadTrackingNumber(1, true);
	}
	
	/**
	 * Spring Schedule: 最后一次扫描上传ebay订单跟踪号
	 */
	public void lastScanUploadEbayTrackingNumber() {
		this.deWarehouseService.scanOutsUploadTrackingNumber(2, true);
	}
	
	/**
	 * Spring Schedule: 最后一次扫描上传amazon（亚马逊）订单跟踪号
	 */
	public void lastScanUploadAmazonTrackingNumber() {
		this.deWarehouseService.scanOutsUploadTrackingNumber(3, true);
	}

	/**
	 * Spring Schedule: 最后一次扫描上传VC订单跟踪号
	 */
	public void lastScanUploadVcTrackingNumber() {
		this.deWarehouseService.scanOutsUploadTrackingNumber(5, true);
	}
	
	/**
	 * Spring Schedule: 同步WMS仓库系统的异常单（病单）
	 */
	public void syncWmsDiseaseOutOrders() {
		this.deWarehouseService.syncWmsDiseaseOutOrders();
	}
	
	/*public static void main(String[] args) {
		org.springframework.context.ApplicationContext ac = new org.springframework.context.support.ClassPathXmlApplicationContext("classpath:spring.xml");
		IDeWarehouseService s = (IDeWarehouseService) ac.getBean("deWarehouseServiceImpl");
		s.test();
	}*/
}
