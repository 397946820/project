package com.it.ocs.compare.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.it.ocs.common.OperationResult;
import com.it.ocs.compare.vo.DataCompareVO;

public interface ICompareService {
	public void exportCompareResult(DataCompareVO param,HttpServletRequest request,HttpServletResponse response);
	
	public List<Map<String,String>> queryAccountByPF(String pf);
	
	public OperationResult dataCompare(DataCompareVO param,HttpServletRequest request,HttpServletResponse response);
}
