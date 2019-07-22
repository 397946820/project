package com.it.ocs.common;

import java.io.Serializable;
import java.util.List;

import com.it.ocs.common.model.BaseModel;

public class BatchVO<T extends BaseModel> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7097252632870192292L;
	public List<T> createModels;
	public List<T> updateModels;
	public List<T> delModels;

	public List<T> getCreateModels() {
		return createModels;
	}

	public void setCreateModels(List<T> createModels) {
		this.createModels = createModels;
	}

	public List<T> getUpdateModels() {
		return updateModels;
	}

	public void setUpdateModels(List<T> updateModels) {
		this.updateModels = updateModels;
	}

	public List<T> getDelModels() {
		return delModels;
	}

	public void setDelModels(List<T> delModels) {
		this.delModels = delModels;
	}

}
