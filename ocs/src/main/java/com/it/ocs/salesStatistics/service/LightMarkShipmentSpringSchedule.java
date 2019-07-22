package com.it.ocs.salesStatistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("lightMarkShipmentSpringSchedule")
public class LightMarkShipmentSpringSchedule {
	
	@Autowired
	private ILightMarkShipmentService lightMarkShipmentService;
	
	/**
	 * Spring Schedule: 上传跟踪号 
	 */
	public void uploadTrackingNumbers() {
		String light = "light", amazon = "amazon";
		this.lightMarkShipmentService.uploadTrackingNumbers(light, this.lightMarkShipmentService.scanWaitingUploads(light));
		this.lightMarkShipmentService.uploadTrackingNumbers(amazon, this.lightMarkShipmentService.scanWaitingUploads(amazon));
	}
	
	/*public static void main(String[] args) {
		org.springframework.context.ApplicationContext ac = new org.springframework.context.support.ClassPathXmlApplicationContext("classpath:spring.xml");
		LightMarkShipmentSpringSchedule ss = (LightMarkShipmentSpringSchedule) ac.getBean("lightMarkShipmentSpringSchedule");
		ss.uploadTrackingNumbers();
	}*/
}
