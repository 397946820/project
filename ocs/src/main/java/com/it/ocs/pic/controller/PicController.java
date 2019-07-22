package com.it.ocs.pic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.pic.model.PicModel;
import com.it.ocs.pic.service.IPicService;
import com.it.ocs.pic.vo.PicVO;

@Controller
@RequestMapping(value = "/pic")
public class PicController {
//	@Autowired
	private IPicService picService;
	@RequestMapping(value="/categoryPicList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult<PicVO> getCategoryPicList(@RequestBody RequestParam param){
		return picService.getCategoryPicList(param);
	}
	@RequestMapping("/getPicRealUrl")
	@ResponseBody
	public List<PicVO> getPicRealUrls(String ids,String ebayAccount){
		return picService.getPicRealUrls(ids,ebayAccount);
	}
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
    public ResponseResult<PicVO> list(RequestParam param) {
		ResponseResult<PicVO> picPageResult = picService.queryPic(param);
        return picPageResult;
    }
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult savePic(PicVO picVO) {
		return picService.savePic(picVO);
	}
	@RequestMapping(value = "/remove")
	@ResponseBody
	public OperationResult removePic(Long id) {
		return picService.removePic(id);
	}
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public String index() {
		return "admin/pic/pic";
	}
	@RequestMapping(value="/selectPicById")
	@ResponseBody
	public PicModel selectPicById(Long id){
		return picService.queryById(id);
	}
}
