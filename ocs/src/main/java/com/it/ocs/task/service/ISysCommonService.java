package com.it.ocs.task.service;

import java.io.Serializable;

public interface ISysCommonService<T extends Serializable> {
	public boolean existByParam(T param);

}
