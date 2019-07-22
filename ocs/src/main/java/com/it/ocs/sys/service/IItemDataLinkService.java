package com.it.ocs.sys.service;

import java.util.List;
import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.sys.vo.EasyUITreeVO;

public interface IItemDataLinkService {

	public List<EasyUITreeVO> itemDataTree(long id);

	public EasyUITreeVO getItemNodeInfo(long id);

	public OperationResult save(Map<String, Object> map);

}
