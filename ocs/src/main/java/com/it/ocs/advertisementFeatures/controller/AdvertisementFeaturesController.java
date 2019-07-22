package com.it.ocs.advertisementFeatures.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.advertisementFeatures.service.IAdvertisementFeaturesService;
import com.it.ocs.advertisementFeatures.vo.AdvertisingFeaturesVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

/**
 * 广告特色控制层
 * @author yecaiqing
 *
 */
@Controller
@RequestMapping("/advertisementFeatures")
public class AdvertisementFeaturesController {

	@Autowired
	private IAdvertisementFeaturesService advertisementFeaturesService;
	
	//跳转到广告特色主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/advertisementFeatures/list";
	}
	
	//查找所有的
	@RequestMapping("/findAll")
	public @ResponseBody ResponseResult<AdvertisingFeaturesVo> findAll(RequestParam param) {
		ResponseResult<AdvertisingFeaturesVo> pageResult = advertisementFeaturesService.findAll(param);
		
		return pageResult;
	}
	
	//添加和修改
	@RequestMapping("/saveEdit")
	public @ResponseBody OperationResult saveEdit(AdvertisingFeaturesVo featuresVo) {
		
		return advertisementFeaturesService.saveEditAdvertisingFeatures(featuresVo);
	}
	
	@RequestMapping("/remove")
	public @ResponseBody OperationResult removeAdvertisingFeatures(String ids) {
		return advertisementFeaturesService.removeAdvertisingFeatures(ids);
	}
	
}
