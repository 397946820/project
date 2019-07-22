package com.it.ocs.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;

import com.it.ocs.api.dao.IDeWarehouseDao;
import com.it.ocs.api.model.DeOutWmsOrderDetailModel;
import com.it.ocs.api.model.DeOutWmsOrderMainModel;
import com.it.ocs.api.service.IDeWarehouseService;
import com.it.ocs.common.util.StringUtil;

public class DeTnUploadThreadListener<T> implements Runnable {
	private static final Logger logger = Logger.getLogger(DeTnUploadThreadListener.class);
	
	private static final ExecutorService timmerPool = Executors.newFixedThreadPool(10);
	
	//超时值（秒）：[ >= timeout ] 代表超时。默认超时值=300秒，即5分钟
	static final int timeout = 300;
	
	private DeTnUploadThreadTimer timer;
	private FutureTask<T> task;
	
	public DeTnUploadThreadListener(DeTnUploadThreadTimer timer, FutureTask<T> task) {
		this.timer = timer;
		this.task = task;
	}
	
	public void run() {
		timmerPool.execute(this.task);
		while(!task.isDone()) {
			if(timer.getSeconds() < timeout && !timer.isExecuted()) {
				
				try {
					Thread.sleep(1000); //让线程sleep一秒：模仿秒表
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				
				timer.setSeconds(timer.getSeconds() + 1); //累计时间：秒 
				
				//超时判断：如果超时，取消该线程执行监听器（this）所监听的线程任务（task）
				if(timer.getSeconds() >= timeout) {
					task.cancel(true);
					logger.warn("de-tn-upload-thread: cancel 'task" + this.timer.getThreadIndex() + "'.");
				}
			}
		}
	}
}

class DeTnUploadThread implements Callable<Boolean> {
	
	private static final Logger logger = Logger.getLogger(DeTnUploadThread.class);
	
	private static final String FORMAT_MSG_UPLOAD_TN_FAILED = "de-tn-upload-thread: 出库单（OrderId=%s）上传跟踪号（Tracking Number=%s）失败";
	
	private DeTnUploadThreadTimer timer;
	private DeTnUploadThreadDTO dto;
	
	public DeTnUploadThread(DeTnUploadThreadTimer timer, DeTnUploadThreadDTO dto) {
		this.timer = timer;
		this.dto = dto;
	}
	
	public Boolean call() throws Exception {
		int positionFlag = 0;
		boolean success = false;
		logger.info("de-tn-upload-thread: exec 'task" + this.timer.getThreadIndex() + "' ...");
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("parentId", this.dto.getMain().getId());
			List<DeOutWmsOrderDetailModel> details = this.dto.getDeWarehouseDao().getOutWmsOrderDetailByMap(param);
			positionFlag = 1;
			success = this.dto.getDeWarehouseService().uploadTrackingNumber(this.dto.getMain(), details);
			positionFlag = 2;
			this.dto.getDeWarehouseService().updateOutUploadStatus(this.dto.getMain().getId(), success ? 1 : (this.dto.isEndUpload() ? 3 : 2));
			positionFlag = 3;
			if(!success) {
				logger.warn(String.format(FORMAT_MSG_UPLOAD_TN_FAILED, this.dto.getMain().getOrderId(), this.dto.getMain().getTrackingNumber()));
			}
		} catch(Exception e) {
			logger.error("de-tn-upload-thread: throw '" + e.getMessage() + "' exception.  (positionFlag=" + positionFlag + ")");
			if(positionFlag == 1) {
				if(!success) {
					this.dto.getDeWarehouseService().updateOutUploadStatus(this.dto.getMain().getId(), 2);	
				}
				this.dto.getDeWarehouseService().recordOperFailedLog(e.getClass().getName(), Long.valueOf(this.dto.getMain().getId()), e.getMessage() + ": " + StringUtil.instanceDetail(this.dto.getMain()));
			}
			throw e;
		} finally {
			this.timer.setExecuted(true); //遇到正常执行完毕或抛出异常，都表示业务线程执行完毕，需要将执行完毕进行标记
		}
		logger.info("de-tn-upload-thread: exec 'task" + this.timer.getThreadIndex() + "' end.");
		return success;
	}
}

class DeTnUploadThreadDTO {
	private IDeWarehouseDao deWarehouseDao;
	private IDeWarehouseService deWarehouseService;
	private DeOutWmsOrderMainModel main;
	private boolean endUpload;
	
	public DeTnUploadThreadDTO(IDeWarehouseDao deWarehouseDao, IDeWarehouseService deWarehouseService, DeOutWmsOrderMainModel main, boolean endUpload) {
		this.deWarehouseDao = deWarehouseDao;
		this.deWarehouseService = deWarehouseService;
		this.main = main;
		this.endUpload = endUpload;
	}

	public IDeWarehouseDao getDeWarehouseDao() {
		return deWarehouseDao;
	}

	public void setDeWarehouseDao(IDeWarehouseDao deWarehouseDao) {
		this.deWarehouseDao = deWarehouseDao;
	}

	public IDeWarehouseService getDeWarehouseService() {
		return deWarehouseService;
	}

	public void setDeWarehouseService(IDeWarehouseService deWarehouseService) {
		this.deWarehouseService = deWarehouseService;
	}
	
	public DeOutWmsOrderMainModel getMain() {
		return main;
	}

	public void setMain(DeOutWmsOrderMainModel main) {
		this.main = main;
	}

	public boolean isEndUpload() {
		return endUpload;
	}

	public void setEndUpload(boolean endUpload) {
		this.endUpload = endUpload;
	}
}

class DeTnUploadThreadTimer {
	private int seconds = 0;
	private boolean executed = false;
	private int threadIndex;
	
	public DeTnUploadThreadTimer(int threadIndex) {
		this.threadIndex = threadIndex;
	}
	
	public int getThreadIndex() {
		return threadIndex;
	}

	public void setThreadIndex(int threadIndex) {
		this.threadIndex = threadIndex;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public boolean isExecuted() {
		return executed;
	}

	public void setExecuted(boolean executed) {
		this.executed = executed;
	}
}