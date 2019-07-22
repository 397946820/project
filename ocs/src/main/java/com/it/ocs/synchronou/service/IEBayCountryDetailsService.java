package com.it.ocs.synchronou.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.model.EBayCountryDetailsModel;
import com.it.ocs.synchronou.vo.CountryDetailsVO;

import net.sf.json.JSONArray;

public interface IEBayCountryDetailsService {

		public ResponseResult<CountryDetailsVO> findCountryDetailsList(RequestParam param);
		
        public OperationResult synchronouCountryDetails();
        
		public OperationResult insertCountryDetails(List<EBayCountryDetailsModel> eBayCountryDetailsModels);
		
		public OperationResult updateCountryDetails(List<EBayCountryDetailsModel> eBayCountryDetailsModels);

		public List<CountryDetailsVO> selectCountryDetails();
		
		public EBayCountryDetailsModel selectCountryDetailById(Long id);
		
		public List<EBayCountryDetailsModel> internalSelectCountryDetailsList();
		
		public JSONArray selectCountryDetailsListJson();
}
