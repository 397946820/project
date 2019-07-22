package com.it.ocs.sys.service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.sys.vo.RoleUserVO;

public interface IRoleUserService {
	public OperationResult batchAdd(RoleUserVO roleUserVO);
}
