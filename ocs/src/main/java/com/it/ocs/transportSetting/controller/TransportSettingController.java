package com.it.ocs.transportSetting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.transportSetting.model.TransportSettingModel;
import com.it.ocs.transportSetting.service.ITransportSettingService;
import com.it.ocs.transportSetting.vo.TransportSettingVo;

/**
 * 运送选项控制层
 * @author yecaiqing
 *
 */
@Controller
@RequestMapping("/transportSetting")
public class TransportSettingController {

	@Autowired
	private ITransportSettingService transportSettingService;
	
	//跳转到主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/transportSetting/list";
	}
	
	//查找所有的
	@RequestMapping("/findAll")
	public @ResponseBody ResponseResult<TransportSettingVo> findAll(RequestParam param) {
		return transportSettingService.findAll(param);
	}
	
	//保存和修改
	@RequestMapping("/saveEdit")
	public @ResponseBody OperationResult saveEdit(TransportSettingVo transportSetting) {
		return transportSettingService.saveEdit(transportSetting);
	}
	
	//逻辑删除
	@RequestMapping("/remove")
	public @ResponseBody OperationResult removeTransportSetting(String ids) {
		return transportSettingService.removeTransportSetting(ids);
	}
}
