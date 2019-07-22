package com.it.ocs.publication.service.external.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.pic.service.IPicService;
import com.it.ocs.pic.util.ImageUtil;
import com.it.ocs.pic.vo.PicVO;
import com.it.ocs.publication.dao.IEBayAdvertTemplatesDAO;
import com.it.ocs.publication.model.EBayAdvertTemplatesModel;
import com.it.ocs.publication.service.external.IEBayAdvertTemplatesService;
import com.it.ocs.publication.vo.AdvertTemplatesVO;
import com.it.ocs.synchronou.model.EBayFeedbackModel;
import com.it.ocs.synchronou.util.ValidationUtil;
import com.it.ocs.synchronou.vo.FeedbackVO;

@Service
public class EBayAdvertTemplatesService implements IEBayAdvertTemplatesService {
	
//	@Autowired
	private IPicService picService;
	@Autowired
	private IEBayAdvertTemplatesDAO advertDao;
	@Override
	public OperationResult insertAdvert(AdvertTemplatesVO advertTemplatesVO) {
		OperationResult result2 = new OperationResult();
		PicVO picVO = new PicVO();
		MultipartFile file = advertTemplatesVO.getFile();
		picVO.setFile(file);
		picVO.setCategoryId(3146L);
		picVO.setName(advertTemplatesVO.getName());
		picVO.setUrl("");
		OperationResult result = picService.savePic(picVO);
		advertTemplatesVO.setPid_id(result.getId());
		advertTemplatesVO.setImg_name(file.getOriginalFilename());
		advertTemplatesVO.setUrl(result.getData().toString());
	
		advertDao.insertAdvert(advertTemplatesVO);
		result2.setDescription("添加成功！");
		return result2;
	}

	@Override
	public OperationResult updateAdvert(AdvertTemplatesVO advertTemplatesVO) {
		OperationResult result2 = new OperationResult();
		Long picId = advertTemplatesVO.getPid_id();
		OperationResult result = new OperationResult();
		MultipartFile file = advertTemplatesVO.getFile();
		String fileName = file.getOriginalFilename();
		advertTemplatesVO.setUrl(null);
		if(!ValidationUtil.isNullOrEmpty(fileName)){
			PicVO picVO = new PicVO();
			picVO.setId(picId);
			picVO.setFile(file);
			picVO.setCategoryId(3146L);
			picVO.setName(advertTemplatesVO.getName());
			picVO.setUrl("");
			result = picService.savePic(picVO);
			advertTemplatesVO.setPid_id(result.getId());
			advertTemplatesVO.setUrl(result.getData().toString());
		}
		
		advertTemplatesVO.setImg_name(fileName);
		advertDao.updateAdvert(advertTemplatesVO);
		result2.setDescription("更改成功！");
		return result2;
	}

	@Override
	public OperationResult addSeve(AdvertTemplatesVO advertTemplatesVO) {
		OperationResult result = new OperationResult();
		if(ValidationUtil.isNull(advertTemplatesVO.getId())){
			result = insertAdvert(advertTemplatesVO);
		}else{
			result = updateAdvert(advertTemplatesVO);
		}
		return result;
	}

	@Override
	public ResponseResult<AdvertTemplatesVO> selectAdvertTemplates(RequestParam param) {
		ResponseResult<AdvertTemplatesVO> result = new ResponseResult<>();
		List<EBayAdvertTemplatesModel> eBayAdvertTemplatesModels = advertDao.selectAdvertTemplates(param.getParam(),param.getStartRow(), param.getEndRow());
		List<AdvertTemplatesVO> advertTemplatesVOs = Lists.newArrayList();
		convertList(eBayAdvertTemplatesModels, advertTemplatesVOs);
		for (AdvertTemplatesVO advertTemplatesVO : advertTemplatesVOs) {
			advertTemplatesVO.setUrl("data:image/jpg;base64,"+ImageUtil.getImageBinary(advertTemplatesVO.getUrl()));
		}
		int total = advertDao.getTotal();
		result.setRows(advertTemplatesVOs);
		result.setTotal(total);
		return result;
	}

	private void convertList(List<EBayAdvertTemplatesModel> source, final List<AdvertTemplatesVO> target) {
		CollectionUtil.each(source, new IAction<EBayAdvertTemplatesModel>() {
			@Override
			public void excute(EBayAdvertTemplatesModel obj) {
				AdvertTemplatesVO advertTemplatesVO = new AdvertTemplatesVO();
				BeanUtils.copyProperties(obj, advertTemplatesVO);
				target.add(advertTemplatesVO);
			}
		});
	}

	@Override
	public OperationResult delete(Long id) {
		OperationResult result = new OperationResult();
		advertDao.updateById(id);
		EBayAdvertTemplatesModel eBayAdvertTemplatesModel = advertDao.selectAdvertById(id);
		picService.removePic(eBayAdvertTemplatesModel.getPid_id());
		result.setDescription("删除成功！");
		return result;
	}

	@Override
	public ResponseResult<AdvertTemplatesVO> selectAdvertList() {
		ResponseResult<AdvertTemplatesVO> result = new ResponseResult<>();
		List<EBayAdvertTemplatesModel> eBayAdvertTemplatesModels = advertDao.selectAdvertList();
		List<AdvertTemplatesVO> advertTemplatesVOs = Lists.newArrayList();
		convertList(eBayAdvertTemplatesModels, advertTemplatesVOs);
		result.setRows(advertTemplatesVOs);
		return result;
	}

	@Override
	public EBayAdvertTemplatesModel selectAdvertById(Long id) {
		return advertDao.selectAdvertById(id);
	}
	

}
