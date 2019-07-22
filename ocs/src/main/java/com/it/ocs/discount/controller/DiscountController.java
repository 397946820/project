package com.it.ocs.discount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.discount.service.IDiscountService;
import com.it.ocs.discount.vo.DiscountVo;

/**
 * 折扣控制层
 * 
 * @author yecaiqing
 *
 */
@Controller
@RequestMapping("/discount")
public class DiscountController {

	@Autowired
	private IDiscountService discountService;

	// 跳转到折扣主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/discount/list";
	}

	// 查询所有
	@RequestMapping("/findAll")
	public @ResponseBody ResponseResult<DiscountVo> findAll(RequestParam param) {
		ResponseResult<DiscountVo> result = discountService.findAll(param);

		return result;

	}

	// 保存修改
	@RequestMapping("/saveEdit")
	public @ResponseBody OperationResult saveEdit(DiscountVo discount) {

		return discountService.saveEditDiscount(discount);
	}

	// 作废
	@RequestMapping("/remove")
	public @ResponseBody OperationResult removeDiscount(String ids) {
		return discountService.removeDiscount(ids);
	}

}
