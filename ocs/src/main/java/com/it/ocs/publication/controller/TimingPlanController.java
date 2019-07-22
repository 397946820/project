package com.it.ocs.publication.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.publication.service.external.IEBayTimingPlanService;
import com.it.ocs.publication.vo.TimingPlanVO;

@Controller
@RequestMapping(value="/TimingPlan")
public class TimingPlanController {
	
	@Autowired
	private IEBayTimingPlanService timingPlanService;
	
	@RequestMapping(value="/show/{day}",method = RequestMethod.GET)
	public String show(){
		return "admin/publication/preTimingPlan";
	}
	@RequestMapping(value="/lineShow/{day}",method = RequestMethod.GET)
	public String lineShow(){
		return "admin/publication/checkTimingPlan";
	}
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<TimingPlanVO> list(RequestParam param){
		return timingPlanService.selectTimingPlans(param);
	}
	@RequestMapping(value="/checkList")
	@ResponseBody
	public ResponseResult<TimingPlanVO> checkList(RequestParam param){
		return timingPlanService.selectLineTimingPlans(param);
	}
	@RequestMapping(value="/countPub/site" ,method=RequestMethod.POST)
	@ResponseBody
	public OperationResult countPub(@RequestBody Map<String, Object> map){
		return timingPlanService.countPub(map);
	}
	@RequestMapping(value="/countLinePub/site" ,method=RequestMethod.POST)
	@ResponseBody
	public OperationResult countLinePub(@RequestBody Map<String, Object> map){
		return timingPlanService.countLinePub(map);
	}
}
