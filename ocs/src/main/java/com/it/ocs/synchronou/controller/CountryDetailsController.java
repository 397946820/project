package com.it.ocs.synchronou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.service.IEBayCountryDetailsService;
import com.it.ocs.synchronou.vo.CountryDetailsVO;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value="/CountryDetails")
public class CountryDetailsController {

		@Autowired
		private IEBayCountryDetailsService countryDetailsService;
		
		@RequestMapping(value="show")
		public String show(){
			//itemService.publishedItem(1l);
			return "admin/ebaySynchronous/countryDetails";
		}
		
		@RequestMapping(value="findCountryDetailsList")
		@ResponseBody
		public ResponseResult<CountryDetailsVO> findCountryDetailsList(RequestParam param){
			return countryDetailsService.findCountryDetailsList(param);
		}
		
		@RequestMapping(value="synchronouCountryDetails")
		@ResponseBody
		public OperationResult synchronouCountryDetails(){
			
			return countryDetailsService.synchronouCountryDetails();
		}
		
	@RequestMapping(value="/selectCountryDetailsListJson")
	@ResponseBody
	public JSONArray selectCountryDetailsListJson(){
		
		return countryDetailsService.selectCountryDetailsListJson();
	}
}
