package com.it.ocs.publication.service.external.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.publication.dao.IEBayTimingPlanDAO;
import com.it.ocs.publication.model.EBayTimingPlanModel;
import com.it.ocs.publication.model.PublicationModel;
import com.it.ocs.publication.service.external.IEBayTimingPlanService;
import com.it.ocs.publication.vo.PublicationPageVO;
import com.it.ocs.publication.vo.TimingPlanVO;
import com.it.ocs.synchronou.util.ConversionDateUtil;

@Service
public class EBayTimingPlanService extends BaseService implements IEBayTimingPlanService {

	@Autowired
	private IEBayTimingPlanDAO timingPlanDao;
	@Override
	public ResponseResult<TimingPlanVO> selectTimingPlans(RequestParam param) {
		// TODO Auto-generated method stub
		ResponseResult<TimingPlanVO> result = new ResponseResult<>();
		
		Map<String, Object> map = param.getParam();
		
		List<EBayTimingPlanModel> eBayTimingPlanModels = timingPlanDao.selectTimingPlans(map, param.getStartRow(), param.getEndRow());
		
		List<TimingPlanVO> timingPlanVOs = Lists.newArrayList();
		
		convertList(eBayTimingPlanModels, timingPlanVOs);
		if(timingPlanVOs.size()>0){
			for (TimingPlanVO timingPlanVO : timingPlanVOs) {
				timingPlanVO.setStartDate(ConversionDateUtil.dateToCharFormat(timingPlanVO.getFirst_date(), "yyyy-MM-dd HH:mm:ss"));
				timingPlanVO.setEndDate(ConversionDateUtil.dateToCharFormat(timingPlanVO.getFirst_site_date(),  "yyyy-MM-dd HH:mm:ss"));
				timingPlanVO.setCreateDate(ConversionDateUtil.dateToCharFormat(timingPlanVO.getCreate_date(), "yyyy-MM-dd HH:mm:ss"));
			}
		}
		int count = timingPlanDao.countList(map);
		
		result.setRows(timingPlanVOs);
		
		result.setTotal(count);
		
		return result;
	}
	private void convertList(List<EBayTimingPlanModel> source, final List<TimingPlanVO> target) {
		CollectionUtil.each(source, new IAction<EBayTimingPlanModel>() {
			@Override
			public void excute(EBayTimingPlanModel obj) {
				TimingPlanVO timingPlanVO = new TimingPlanVO();
				BeanUtils.copyProperties(obj, timingPlanVO);
				target.add(timingPlanVO);
			}
		});
	}
	@Override
	public OperationResult countPub(Map<String, Object> map) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		Object siteId = map.get("siteId");
		if("all".equals(siteId)) {map.put("siteId", "");}
		result.setData(timingPlanDao.countList(map));
		return result;
	}
	@Override
	public OperationResult countLinePub(Map<String, Object> map) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		Object siteId = map.get("siteId");
		if("all".equals(siteId)) {map.put("siteId", "");}
		result.setData(timingPlanDao.countLineList(map));
		return result;
	}
	@Override
	public ResponseResult<TimingPlanVO> selectLineTimingPlans(RequestParam param) {
		// TODO Auto-generated method stub
    ResponseResult<TimingPlanVO> result = new ResponseResult<>();
		
		Map<String, Object> map = param.getParam();
		
		List<EBayTimingPlanModel> eBayTimingPlanModels = timingPlanDao.selectLineTimingPlans(map, param.getStartRow(), param.getEndRow());
		
		List<TimingPlanVO> timingPlanVOs = Lists.newArrayList();
		
		convertList(eBayTimingPlanModels, timingPlanVOs);
		if(timingPlanVOs.size()>0){
			for (TimingPlanVO timingPlanVO : timingPlanVOs) {
				timingPlanVO.setStartDate(ConversionDateUtil.dateToCharFormat(timingPlanVO.getFirst_date(), "yyyy-MM-dd HH:mm:ss"));
				timingPlanVO.setEndDate(ConversionDateUtil.dateToCharFormat(timingPlanVO.getFirst_site_date(),  "yyyy-MM-dd HH:mm:ss"));
				timingPlanVO.setCreateDate(ConversionDateUtil.dateToCharFormat(timingPlanVO.getCreate_date(), "yyyy-MM-dd HH:mm:ss"));
			}
		}
		int count = timingPlanDao.countLineList(map);
		
		result.setRows(timingPlanVOs);
		
		result.setTotal(count);
		
		return result;
	}
	
}
