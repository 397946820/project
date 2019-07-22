package com.it.ocs.eda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.eda.dao.IEDADao;

@Service
public class EDACreateOrderService {
	
	@Autowired
	private IEDADao iEDADao;
	
	public void addOrderToEDA(){
		iEDADao.addOrderToEDA();
		//新增补发单
		this.addSupplyAgainOrder();
	}

	private void addSupplyAgainOrder() {
		// TODO Auto-generated method stub
		
	}
	
}
