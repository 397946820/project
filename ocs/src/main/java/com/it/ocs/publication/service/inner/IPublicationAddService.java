package com.it.ocs.publication.service.inner;

import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.publication.model.PaymentModel;
import com.it.ocs.publication.vo.PublicationSaveVO;

public interface IPublicationAddService {
	public void addPublication(PublicationSaveVO pubInfo);
}
