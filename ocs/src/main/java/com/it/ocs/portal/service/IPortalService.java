package com.it.ocs.portal.service;

import java.util.List;
import java.util.Map;

import com.it.ocs.common.RequestParam;

public interface IPortalService {
	public List<Map<String,Object>> contructLinearDatas(RequestParam param);
}
