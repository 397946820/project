package com.it.ocs.pic.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.pic.model.PicModel;
import com.it.ocs.pic.vo.PicVO;

public interface IPicService {
	public ResponseResult<PicVO> queryPic(RequestParam param);
	
	public ResponseResult<PicVO> getCategoryPicList(RequestParam param);
	
	public OperationResult savePic(PicVO picVO);
	
	public OperationResult removePic(Long picId);
	
	public List<PicVO> getPicRealUrls(String ids,String ebayAccount);
	public PicVO getPicRealUrl(PicModel picModel,String ebayAccount);
	
	public PicModel queryById(Long id);
	
	public List<PicModel> queryByIds(String ids);
	

	
}
