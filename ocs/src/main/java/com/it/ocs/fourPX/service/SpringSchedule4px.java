package com.it.ocs.fourPX.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.OperationResult;

@Service("springSchedule4px")
public class SpringSchedule4px {
	
	private static final Logger logger = Logger.getLogger(SpringSchedule4px.class);

	@Autowired
	private FpxOutWarehouseService fpxOutWarehouseService;
	
	@Autowired
	private SellerInventoryLogService sellerInventoryLogService;
	
	/**
	 * Spring schedule: 全量同步4PX的库存流水
	 */
	public void fullSyncFrom4pxSil() {
		this.syncFrom4pxSil(false, "US");
	}

	/**
	 * Spring Schedule: 基于同步得到的最后时间为起始时间条件，增量同步4PX的库存流水
	 */
	public void incrementSyncFrom4pxSil() {
		this.syncFrom4pxSil(true, "US");
	}

	/**
	 * Spring schedule: DE - 全量同步4PX的库存流水
	 */
	public void fullSyncDeFrom4pxSil() {
		this.syncFrom4pxSil(false, "DE");
	}

	/**
	 * Spring Schedule: DE - 基于同步得到的最后时间为起始时间条件，增量同步4PX的库存流水
	 */
	public void incrementSyncDeFrom4pxSil() {
		this.syncFrom4pxSil(true, "DE");
	}

	/**
	 * Spring Schedule: 将4PX出库单的状态等相关信息同步到OMS（即更新OMS对应4PX出库单、源订单状态相关信息）
	 */
	public void syncOutFrom4px() {
		this.fpxOutWarehouseService.syncOutFrom4px();
	}
	
	/**
	 * Spring Schedule: 将OMS订单表中的未发货订单同步到OMS的4PX出库单管理平台，形成待推送4PX的出库单数据
	 */
	public void syncOutFormOrder() {
		this.fpxOutWarehouseService.syncOutFromUnshippedOrder(null);
	}

	/**
	 * Spring Schedule: 批量推送出库单至4px平台
	 */
	public void batchPush4px() {
		this.fpxOutWarehouseService.batchPush4px();
	}
	
	private void syncFrom4pxSil(boolean increment, String busarea) {
		OperationResult or = this.sellerInventoryLogService.syncFrom4pxSil(increment, busarea);
		if(0 != or.getErrorCode()) {
			logger.error("[SpringSchedule4px.syncFrom4pxSil(...)] - " + busarea + " - " + (null == or.getDescription() ? "同步4PX的库存流水异常。" : or.getDescription()));
		}
	}
}
