package com.it.ocs.sys.model;

import java.util.Map;

import com.it.ocs.common.model.BaseModel;

public class SystemLogModel extends BaseModel{
	private String targetName;
	private String methodName;
	private String desc;
	private String param;
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getParam() {
		return param;
	}
	public void setParam(Map param) {
		this.param = param.toString();
	}
	
}
