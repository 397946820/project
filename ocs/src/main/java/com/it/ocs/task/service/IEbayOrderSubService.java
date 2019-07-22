
package com.it.ocs.task.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.task.model.EbayOrderSubModel;

public interface IEbayOrderSubService {
	
	public OperationResult insertEbayOrderSubs(List<EbayOrderSubModel> ebayOrderSubModels);
	
	public OperationResult updateEbayOrderSubs(List<EbayOrderSubModel> ebayOrderSubModels);
	
	public List<EbayOrderSubModel> findEbayOrderSubs();
	
	public List<Long> getOrderSubSeqs(Long size);
}
