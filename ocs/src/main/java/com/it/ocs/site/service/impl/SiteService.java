package com.it.ocs.site.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.site.dao.ISiteDAO;
import com.it.ocs.site.model.SiteModel;
import com.it.ocs.site.service.ISiteService;
import com.it.ocs.site.vo.SiteVO;

@Service
public class SiteService extends BaseService implements ISiteService {
	@Autowired
	private ISiteDAO siteDAO;

	@Override
	public ResponseResult<SiteVO> querySite(RequestParam param) {
		ResponseResult<SiteVO> result = new ResponseResult<>();
		SiteVO siteVO = BeanConvertUtil.mapToObject(param.getParam(), SiteVO.class);
		List<SiteModel> sites = siteDAO.queryByPage(siteVO, param.getStartRow(), param.getEndRow());
		List<SiteVO> siteVOs = CollectionUtil.beansConvert(sites, SiteVO.class);
		int count = siteDAO.count(siteVO);
		result.setRows(siteVOs);
		result.setTotal(count);
		return result;
	}

	@Override
	public OperationResult saveSite(SiteVO siteVO) {
		OperationResult result = new OperationResult();
		try {
			if (null != siteVO.getId()) {
				SiteModel siteModel = siteDAO.getById(siteVO.getId());
				BeanUtils.copyProperties(siteModel, siteVO,new String[]{"name","ebaySiteRelation","ico"});
				updateInit(siteVO);
				siteDAO.update(siteVO);
			} else {
				insertInit(siteVO);
				siteDAO.add(siteVO);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("save error");
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public OperationResult removeSite(Long siteId) {
		OperationResult result = new OperationResult();
		try {
			siteDAO.delete(siteId);
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("remove error");
			e.printStackTrace();
		}
		return result;
	}

}
