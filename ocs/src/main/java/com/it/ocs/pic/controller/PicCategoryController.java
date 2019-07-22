package com.it.ocs.pic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.pic.service.IPicCategoryService;
import com.it.ocs.pic.vo.PicCategoryVO;

@Controller
@RequestMapping(value = "/picCategory")
public class PicCategoryController {
	@Autowired
	private IPicCategoryService picCategoryService;
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
    public List<PicCategoryVO> list() {
        return picCategoryService.query();
    }
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult savePicCategory(@RequestBody PicCategoryVO param) {
		return picCategoryService.savePicCategory(param);
	}
	@RequestMapping(value = "/remove",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult removePicCategory(@RequestBody PicCategoryVO param) {
		return picCategoryService.removePicCategory(param.getId());
	}
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public String index() {
		return "admin/pic/category";
	}
	@RequestMapping(value = "/combotree", method = RequestMethod.POST)
	@ResponseBody
    public List<PicCategoryVO> combotree() {
        return picCategoryService.combotree();
    }
	@RequestMapping(value = "/conditionCombotree", method = RequestMethod.POST)
	@ResponseBody
    public List<PicCategoryVO> conditionCombotree() {
        return picCategoryService.conditionCombotree();
    }
}
