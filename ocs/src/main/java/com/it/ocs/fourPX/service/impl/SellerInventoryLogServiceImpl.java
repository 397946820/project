package com.it.ocs.fourPX.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.ex.BussinessException;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.fourPX.dao.SellerInventoryLogDao;
import com.it.ocs.fourPX.http.HttpRequest;
import com.it.ocs.fourPX.model.SellerInventoryLogModel;
import com.it.ocs.fourPX.model.UnqualifiedModel;
import com.it.ocs.fourPX.service.AccountManagerService;
import com.it.ocs.fourPX.service.FpxServiceUtils;
import com.it.ocs.fourPX.service.SellerInventoryLogService;
import com.it.ocs.fourPX.utils.ReflectUtils;
import com.it.ocs.fourPX.vo.FPXStockVO;
import com.it.ocs.fourPX.vo.FourPXRequestVO;
import com.it.ocs.fourPX.vo.InventoryRequestVO;
import com.it.ocs.fourPX.vo.SellerInventoryLogExportVO;
import com.it.ocs.fourPX.vo.SellerInventoryLogVO;
import com.it.ocs.fourPX.vo.SyncSilRequestVO;
import com.it.ocs.synchronou.util.JsonConvertUtil;
import com.it.ocs.sys.model.UserModel;
import com.it.ocs.sys.service.IUserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class SellerInventoryLogServiceImpl implements SellerInventoryLogService {
	
	private static final Logger logger = Logger.getLogger(SellerInventoryLogServiceImpl.class);

	@Autowired
	private SellerInventoryLogDao sellerInventoryLogDao;
	@Autowired
	private IUserService userService;
	
	@Autowired	
	private AccountManagerService accountManagerService; 

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public ResponseResult<SellerInventoryLogVO> selectByPage(RequestParam param) {
		Map<String, Object> filter = param.getParam();
		
		ResponseResult<SellerInventoryLogVO> result = new ResponseResult<SellerInventoryLogVO>();
		
		result.setTotal(this.sellerInventoryLogDao.count(filter));
		
		List<Map<String, Object>> data = this.sellerInventoryLogDao.selectByPage(filter, param.getStartRow(), param.getEndRow());
		if(null != data && !data.isEmpty()) {
			List<SellerInventoryLogVO> rows = new ArrayList<SellerInventoryLogVO>();
			SellerInventoryLogModel model;
			SellerInventoryLogVO vo;
			for (Map<String, Object> map : data) {
				String codeType = (String) map.get(SellerInventoryLogExportVO.CODETYPE);
				map.put(SellerInventoryLogExportVO.CODETYPE, FpxServiceUtils.formatCodeType(codeType));
				if (map.containsKey("CODETYPEDETAIL") && null != map.get("CODETYPEDETAIL")) {
					map.put("CODETYPEDETAIL", FpxServiceUtils.formatCodeType(map.get("CODETYPEDETAIL").toString()));
				}
				model = new SellerInventoryLogModel();
				ReflectUtils.fillingTarget(model, map, true, false);
				vo = new SellerInventoryLogVO(model);
				vo.setUnqualifieds(FpxServiceUtils.buildUnqualifieds((String) map.get(SellerInventoryLogModel.JSON_UNQUALIFIED)));
				rows.add(vo);
			}
			result.setRows(rows);
		}
		List<Map<String,Object>> list = this.sellerInventoryLogDao.countQtyByInventoryType(filter);
		List<Map<String,Object>> footers = new ArrayList<>();
		for(Map<String,Object> map : list){
			Map<String,Object> footer = new HashMap<>();
			footer.put("codeType", FpxServiceUtils.formatCodeType(map.get("CODETYPE").toString()));
			footer.put("warehouseQuantity", map.get("WAREHOUSEQUANTITY"));
			footers.add(footer);
		}
		result.setFooter(footers);
		return result;
	}

	@Override
	public Date selectLastSyncTime(String busarea) {
		String dateStr = this.sellerInventoryLogDao.selectMaxCreateDate(busarea);
		if(StringUtils.isBlank(dateStr)) {
			return null;
		} 
		
		try {
			return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public OperationResult save(List<Map<String, Object>> data, String busarea) {
		Assert.notEmpty(data, "The parameter 'data' is null or empty.");

		List<String> success = new ArrayList<String>(), failed = new ArrayList<String>();
		boolean flag;
		String message;
		
		for (Map<String, Object> vo : data) {
			try {
				flag = this.save(vo, busarea);
			} catch (Exception e) {
				flag = false;
				logger.error("Save failed: " + e.getMessage(), e);
			}
			
			message = "[swlId=" + vo.get("swlId") + "; sku=" + vo.get("sku") + "]";
			if(flag) {
				success.add(message);
			} else {
				failed.add(message);
			}
		}
		
		OperationResult result = new OperationResult();
		result.setErrorCode(!success.isEmpty() ? 0 : 1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("failed", failed);
		result.setData(map);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean save(Map<String, Object> nnew, String busarea) throws Exception {
		Assert.notNull(nnew, "The parameter 'vo' is null.");
		Assert.notNull(nnew, "The parameter 'busarea' is null.");
		
		SellerInventoryLogModel model = new SellerInventoryLogModel();
		Map<String, Object> old = this.sellerInventoryLogDao.selectBySwlId((String) nnew.get("swlId"));
		
		if(null == old) {
			nnew.put("busarea", busarea);
			ReflectUtils.fillingTarget(model, nnew, false, false);
			model.setId(this.sellerInventoryLogDao.generatePrimaryKey());
			model.init();
			this.sellerInventoryLogDao.insert(model);
		} else {
			ReflectUtils.fillingTarget(model, old, true, false); //先用旧数据填充
			ReflectUtils.fillingTarget(model, nnew, false, true); //再用更新数据填充，需要排除禁止客户端修改的字段
			model.refresh();
			this.sellerInventoryLogDao.update(model);
		}

		this.saveUnqualifieds((List<Map<String, Object>>) nnew.get("unqualifieds"), model.getId());
		return true;
	}
	
	private void saveUnqualifieds(List<Map<String, Object>> data, Long parentId) {
		this.sellerInventoryLogDao.deleteUnqualifieds(parentId);
		if(!CollectionUtil.isNullOrEmpty(data)) {
			UnqualifiedModel unqualified = null;
			for (Map<String, Object> o: data) {
				unqualified = new UnqualifiedModel();
				ReflectUtils.fillingTarget(unqualified, o, false, false);
				unqualified.setId(this.sellerInventoryLogDao.generatePrimaryKey());
				unqualified.setParentId(parentId);
				this.sellerInventoryLogDao.insertUnqualified(unqualified);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public OperationResult syncFrom4pxSil(boolean increment, String busarea) {
		OperationResult result = new OperationResult();
		
		SyncSilRequestVO vo = this.buildSyncSilRequestVO(increment, busarea);
		
		try {
			result = this.request4pxSil(vo, busarea);
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setData(null);
			if(e instanceof BussinessException) {
				result.setDescription("同步失败：" + e.getMessage());
				logger.error(e.getMessage());
			} else {
				result.setDescription("同步失败：发生未知异常，请联系管理员。");
				logger.error(e.getMessage(), e);
			}
			return result;
		}
		
		if(0 == result.getErrorCode()) {
			List<Map<String, Object>> data = (List<Map<String, Object>>) result.getData();
			if(CollectionUtil.isNullOrEmpty(data)) {
				result = new OperationResult(0, "没有需要同步的数据", null, null);
			} else {
				result = this.save(data, busarea);	
			}
		}
		
		return result;
	}
	
	private SyncSilRequestVO buildSyncSilRequestVO(boolean increment, String busarea) {
		Date begin = null, end = null;
		
		if(increment) {
			begin = this.selectLastSyncTime(busarea);
			end = null == begin ? null : new Date();
			if(null != begin && null != end && begin.getTime() > end.getTime()) {
				begin = end;
			}	
		}
		
		SyncSilRequestVO vo = new SyncSilRequestVO();
		vo.setCreateDateBegin(null == begin ? "" : dateFormat.format(begin));
		vo.setCreateDateEnd(null == end ? "" : dateFormat.format(end));
		vo.setPageNo(1);
		vo.setPageSize(100000); //设置一个较大数值，一页获取所有数据
		return vo;
	}
	
	/*
	public static void main(String[] args) {
		SyncSilRequestVO vo = new SyncSilRequestVO();
		vo.setCreateDateBegin("");
		vo.setCreateDateEnd("");
		vo.setPageNo(1);
		vo.setPageSize(100000); //设置一个较大数值，一页获取所有数据
		
		FourPXRequestVO request = new FourPXRequestVO("http://openapi.4px.com/api/service/woms/item/getSellerInventoryLog", vo.toJSONString());
		request.setCustomerId("738792");
		request.setToken("404e43bd5ad500bd96a69abe7ca007c4");
		request.setLanguage("en_US");
		
		String response = HttpRequest.post(request);
		System.out.println(response);
	}
	*/
	
	@SuppressWarnings("unchecked")
	private OperationResult request4pxSil(SyncSilRequestVO vo, String busarea) throws Exception {
		/*
		FourPXRequestVO request = new FourPXRequestVO("http://openapi.4px.com/api/service/woms/item/getSellerInventoryLog", vo.toJSONString());
		request.setCustomerId("731603");
		request.setToken("7ba776a06345ac7bed58345f5565f18a");
		request.setLanguage("en_US");
		*/
		
		FourPXRequestVO request = null;
		if("US".equals(busarea)) {
			request = this.accountManagerService.selectOneByCode(FpxServiceUtils.GET_SELLER_INVENTORY_LOG);
		} else if("DE".equals(busarea)) {
			request = this.accountManagerService.selectDeOneByCode(FpxServiceUtils.GET_SELLER_INVENTORY_LOG);
		} else {
			throw new RuntimeException("[request4pxSil(...)] - This value is not supported. (busarea=" + busarea + ")");
		}
		
		request.setContent(vo.toJSONString());

		String response = HttpRequest.post(request);
		Map<String, Object> map = (Map<String, Object>) JsonConvertUtil.json2Obj(response, Map.class);
		if(null == map) {
			throw new BussinessException("请求4PX的getSellerInventoryLog接口未得到有效的返回值。");
		}
		
		boolean success = "0".equals((String) map.get("errorCode"));
		Object data = null;
		if(success) {
			//key = data, value = [ { "sellerInventory": [ { "swlId": "106005358" }, { "swlId": "106005184" }, { "swlId": "106005175" } ], "pageTotal": 20, "pageSize": 10, "pageNo": 1 } ]
			Object value = map.get("data");
			if(null != value) {
				//目标数据的类型递进: List --> Map --> List
				data = ((Map<String, Object>) ((List<?>) value).get(0)).get("sellerInventory");
			}
		}
		String errorMessage = success ? null : (String) map.get("errorMsg");
		
		return new OperationResult(success ? 0 : 1, errorMessage, data, errorMessage);
	}
	
	/**
	 * 同步4PX库存信息
	 */
	public void sync4pxInventory() {
		this.sync4pxInventoryBybusarea("US");
	}

	/**
	 * DE - 同步4PX库存信息
	 */
	public void sync4pxDeInventory() {
		this.sync4pxInventoryBybusarea("DE");
	}
	
	private void sync4pxInventoryBybusarea(String busarea) {
		List<String> skus = this.sellerInventoryLogDao.getAllSkus(); //获取所有sku
		if(CollectionUtil.isNullOrEmpty(skus)) {
			logger.info("[sync4pxInventoryBybusarea(...)] - No inventory data needs to be synchronized. (busarea=" + busarea + ")");
		}
		
		String[] warehouses = null;
		if("US".equals(busarea)) {
			warehouses = new String[] { "USLA", "USNY" };
		} else if("DE".equals(busarea)) {
			warehouses = new String[] { "DEFR" };
		} else {
			throw new RuntimeException("This value is not supported. (busarea=" + busarea + ")");
		}

		List<String> batches;
		int number = (skus.size() / 100) + (skus.size() % 100 == 0 ? 0 : 1);
		for (int i = 1; i <= number; i++) {
			batches = skus.subList((i - 1) * 100, i == number ? skus.size() : (i * 100));
			for (String warehouse : warehouses) {
				get4pxInventoryBySku(batches, warehouse, busarea);
			}
		}
	}
	
	/**
	 * 根据sku查询库存信息
	 * @param sku
	 */
	private void get4pxInventoryBySku(List<String> skus,String houseCode, String busarea) {
		FourPXRequestVO request;
		if("US".equals(busarea)) {
			request =  this.accountManagerService.selectOneByCode(FpxServiceUtils.GET_INVENTORY);
		} else if("DE".equals(busarea)) {
			request =  this.accountManagerService.selectDeOneByCode(FpxServiceUtils.GET_INVENTORY);
		} else {
			throw new RuntimeException("This value is not supported. (busarea=" + busarea + ")");
		}
		
		InventoryRequestVO content = new InventoryRequestVO();
		content.setLstSku(skus.toArray(new String[]{}));
		content.setWarehouseCode(houseCode);
		request.setContent(content.toJSONString());
		String response = HttpRequest.post(request);
		JSONObject json = JSONObject.fromObject(response);
		if("null".equals(json.get("data").toString())){
			logger.info("4px无返回数据："+response);
			return;
		}
		JSONArray datas = json.getJSONArray("data");
		saveData(datas, busarea);
	}

	private void saveData(JSONArray datas, String busarea) {
		if(null == datas){
			return;
		}
		for(int i=0;i<datas.size();i++){
			JSONObject data = datas.getJSONObject(i);
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>)data;
			map.put("busarea", busarea);
			this.sellerInventoryLogDao.add4pxInventory(map);
		}
		
	}

	/**
	 * 字符串集合用逗号隔开想连
	 * @param list
	 * @return
	 */
	public String join(List<String> list){
		if(list.isEmpty()){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(list.get(0));
		for(int i=1;i<list.size();i++){
			sb.append(","+list.get(i));
		}
		return sb.toString();
	}

	@Override
	public ResponseResult<FPXStockVO> stockListHis(RequestParam param) {
		Map<String,Object> map = param.getParam();
		int count = sellerInventoryLogDao.countFpxStockInfoHis(map);
		ResponseResult<FPXStockVO> result = new ResponseResult<>();
		result.setRows(sellerInventoryLogDao.queryFpxStockInfoHisByPage(map,param.getStartRow(),param.getEndRow()));
		result.setTotal(count);
		return result;
	}

	@Override
	public OperationResult modifyCodeType(RequestParam param) {
		OperationResult result = new OperationResult();
		try {
			Map<String,Object> paramMap = param.getParam();
			UserModel user = userService.getConcurrentUser();
			paramMap.put("lastUpdateBy", user.getId());
			sellerInventoryLogDao.updateCodeTypeDetail(paramMap);
			result.setData(FpxServiceUtils.formatCodeType(param.getParam().get("codeTypeDetail").toString()));
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setError("更新失败！");
		}
		return result;
	}

	@Override
	public List<Map<String, String>> getCodeMap() {
		List<Map<String,String>> result = Lists.newArrayList();
		CollectionUtil.each(FpxServiceUtils.getCODE_TYPE_MAP().entrySet(),new IAction<Entry<String, String>>() {
			@Override
			public void excute(Entry<String, String> entry) {
				Map<String,String> map = new HashMap<>();
				map.put("codeTypeKey", entry.getKey());
				map.put("codeTypeValue", entry.getValue());
				result.add(map);
			}
		});
		return result;
	}
}
