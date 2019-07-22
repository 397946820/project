package com.it.ocs.cal.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.cal.model.PackageModel;
import com.it.ocs.cal.service.IPackageSkuFBAService;
import com.it.ocs.cal.service.IProductEntityService;
import com.it.ocs.cal.vo.ProductEntityVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.controller.IBaseController;
import com.it.ocs.common.util.UserUtils;
import com.it.ocs.sys.core.SystemLog;

@Controller
@RequestMapping("/productEntity")
public class ProductEntityController extends IBaseController {

	@Autowired
	private IProductEntityService productEntityService;
	
	@Autowired
	private IPackageSkuFBAService packageSkuFBAService;

	// 跳转到折扣主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/taximeter/productEntity";
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<ProductEntityVo> findAll(RequestParam param) {
		return productEntityService.findAll(param, isSYSorCWRY(UserUtils.getUserId()),"");
	}

	@RequestMapping("/saveEdit")
	@ResponseBody
	@SystemLog("产品添加或者修改")
	public OperationResult saveEdit(ProductEntityVo entity) {
		return productEntityService.saveEdit(entity);
	}

	// 导出
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@org.springframework.web.bind.annotation.RequestParam("template") String template) {
		
		productEntityService.exportExcel(request, response, template, isSYSorCWRY(UserUtils.getUserId()),
				getColumns(UserUtils.getUserId(), "CPGL_"));
	}

	// 导入
	@RequestMapping("/uploadExcel")
	@ResponseBody
	@SystemLog("产品导入")
	public OperationResult uploadExcel(@org.springframework.web.bind.annotation.RequestParam("myfile") MultipartFile file) {
		return productEntityService.uploadExcel(file, isSYSorCWRY(UserUtils.getUserId()));
	}

	// 导入
	@RequestMapping("/importProductPrice")
	@ResponseBody
	@SystemLog("产品价格导入")
	public OperationResult importProductPrice(@org.springframework.web.bind.annotation.RequestParam("myfile") MultipartFile file) {
		return productEntityService.importProductPrice(file, isSYSorCWRY(UserUtils.getUserId()));
	}
	
	
	@RequestMapping("/getSkuDisInfo/{sku}/{platform}")
	@ResponseBody
	public OperationResult getSkuDisInfo(@PathVariable("sku")String sku,@PathVariable("platform")String platform) {
		return productEntityService.getSkuDisInfo(sku,platform);
	}
	
	
	@RequestMapping("/saveSkuDisInfo")
	@ResponseBody
	@SystemLog("产品DIS状态修改")
	public OperationResult saveSkuDisInfo(@RequestBody Map<String,Object> map) {
		return productEntityService.saveSkuDisInfo(map);
	}
	
	@RequestMapping("/packageSkuFBAlist")
	public String packageSkuFBAlist() {
		return "admin/taximeter/packageSkuFBAlist";
	}
	
	@RequestMapping("/calculate")
	@ResponseBody
	public OperationResult calculate(@RequestBody Map<String,Object> map) {
		String countryId = map.get("countryId").toString();
		String sku  = map.get("sku").toString();
		int qty = Integer.parseInt(map.get("qty").toString());
		Map<String,List<PackageModel>> re = packageSkuFBAService.getGreatFBABySKU(countryId, sku, qty);
		OperationResult or = new OperationResult();
		or.setData(re);
		return or;
	}
	
	
	@RequestMapping("/skuExist/{sku}")
	@ResponseBody
	public OperationResult skuExist(@PathVariable("sku")String sku) {
		return productEntityService.skuExist(sku);
	}
}
