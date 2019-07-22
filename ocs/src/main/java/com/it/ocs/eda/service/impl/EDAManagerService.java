package com.it.ocs.eda.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.eda.dao.IEDADao;
import com.it.ocs.eda.model.EDAOrderModel;
import com.it.ocs.eda.model.InventoryHistoryModel;
import com.it.ocs.eda.model.ShipOrderInfo;
import com.it.ocs.eda.model.WestShippingModel;
import com.it.ocs.eda.service.IEDAManagerService;
import com.it.ocs.eda.vo.EDAOrderVO;
import com.it.ocs.eda.vo.EDAStockVO;
import com.it.ocs.eda.vo.SKULinkVO;
import com.it.ocs.publication.vo.ComboBoxVO;
import com.it.ocs.sys.model.UserModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class EDAManagerService implements IEDAManagerService {
	@Autowired
	private IEDADao iEDADao;
	@Autowired
	private EDAService eDAService;
	
	@Override
	public ResponseResult<EDAOrderVO> findEDAOrderList(RequestParam param) {
		Map<String,Object> map = param.getParam();
		//转换时间为毫秒数
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeStart = map.get("timeStart").toString();
		String timeEnd = map.get("timeEnd").toString();
		try {
			// 00:00:00
			Date date = null;
			if(!"".equals(timeStart)){
				timeStart = timeStart + " 00:00:00";
				date = sdf.parse(timeStart);
				map.put("timeStart", date.getTime());
			}
			if(!"".equals(timeEnd)){
				timeEnd = timeEnd + " 00:00:00";
				date = sdf.parse(timeEnd);
				map.put("timeEnd", date.getTime()+24*60*60*1000L);
			}
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		int count = iEDADao.countEDAOrder(map);
		ResponseResult<EDAOrderVO> result = new ResponseResult<>();
		result.setRows(iEDADao.queryEDAOrderByPage(map,param.getStartRow(),param.getEndRow()));
		result.setTotal(count);
		return result;
	}
	
	@Override
	public ResponseResult<WestShippingModel> westOrderList(RequestParam param) {
		Map<String,Object> map = param.getParam();
		int count = iEDADao.countWestOrderList(map);
		ResponseResult<WestShippingModel> result = new ResponseResult<>();
		result.setRows(iEDADao.westOrderList(map,param.getStartRow(),param.getEndRow()));
		result.setTotal(count);
		return result;
	}

	@Override
	public OperationResult getEDAOrderInfoById(String edaId) {
		OperationResult re = new OperationResult();
		Map<String, Object> order = iEDADao.getEDAOrderByEDAId(edaId);
		if(null != order){
			eDAService.syncEDAOrderInfo(order);
		}
		re.setData("success");
		return re;
	}
	
	@Override
	public OperationResult cancelEDAOrderById(String id) {
		OperationResult re = new OperationResult();
		Map<String, Object> order = iEDADao.getEDAOrderById(id);
		if(null != order&&null != order.get("BUSINESSNUM")){
			boolean isSuccess = eDAService.cancelEDAOrder(order);
			if(isSuccess){
				re.setData("success");
			}
			eDAService.syncEDAOrderInfo(order);
		}else{
			iEDADao.updateEDAOrderNoCreate(id);
			re.setData("success");
		}
		return re;
	}

	@Override
	public ResponseResult<EDAStockVO> stockList(RequestParam param) {
		Map<String,Object> map = param.getParam();
		int count = iEDADao.countEDAStockInfo(map);
		ResponseResult<EDAStockVO> result = new ResponseResult<>();
		result.setRows(iEDADao.queryEDAStockInfoByPage(map,param.getStartRow(),param.getEndRow()));
		result.setTotal(count);
		return result;
	}
	
	@Override
	public ResponseResult<EDAStockVO> stockListHis(RequestParam param) {
		Map<String,Object> map = param.getParam();
		int count = iEDADao.countEDAStockInfoHis(map);
		ResponseResult<EDAStockVO> result = new ResponseResult<>();
		result.setRows(iEDADao.queryEDAStockInfoHisByPage(map,param.getStartRow(),param.getEndRow()));
		result.setTotal(count);
		return result;
	}

	
	

	@Override
	public ResponseResult<SKULinkVO> skuLinkList(RequestParam param) {
		ResponseResult<SKULinkVO> result = new ResponseResult<>();
		Map<String,Object> map = param.getParam();
		List<SKULinkVO> skus = iEDADao.skuLinkListByPage(map,param.getStartRow(),param.getEndRow());
		result.setRows(skus);
		result.setTotal(iEDADao.skuLinkListCount(map));
		return result;
	}

	@Override
	public ResponseResult<SKULinkVO> productList(RequestParam param) {
		ResponseResult<SKULinkVO> result = new ResponseResult<>();
		Map<String,Object> map = param.getParam();
		List<SKULinkVO> skus = iEDADao.productListByPage(map,param.getStartRow(),param.getEndRow());
		result.setRows(skus);
		result.setTotal(iEDADao.productListCount(map));
		return result;
	}

	@Override
	public OperationResult skuLinkSave(Map<String, Object> map) {
		String pSku = String.valueOf(map.get("pSku"));
		Object insertData = map.get("insertData");
		if(null != insertData){
			updateSkuLink(insertData,pSku);
		}
		Object updateData = map.get("updateData");
		if(null != updateData){
			updateSkuLink(updateData,pSku);
		}
		Object deleteData = map.get("deleteData");
		if(null != deleteData){
			List<Map<String,Object>> skus = (List<Map<String,Object>>)deleteData;
			for(Map<String,Object> sku : skus){
				sku.put("pSku", pSku);
				iEDADao.deletePSkuLinkInfo(sku);
			}
		}
		OperationResult or = new OperationResult();
		or.setData("success");
		return or;
	}
	
	private void updateSkuLink(Object data,String pSku){
		List<Map<String,Object>> skus = (List<Map<String,Object>>)data;
		for(Map<String,Object> sku : skus){
			sku.put("pSku", pSku);
			sku.put("userId", getUser().getId());
			iEDADao.updatePSkuLinkInfo(sku);
		}
	}
	
	public static UserModel getUser(){
		Subject currentUser = SecurityUtils.getSubject();
		UserModel userModel = (UserModel)currentUser.getSession().getAttribute("user");
		return userModel;
	}

	@Override
	public List<SKULinkVO> getSkusByPSku(String psku) {
		return iEDADao.getSkusByPSku(psku);
	}

	@Override
	public OperationResult pSkuIsLikeSku(String psku) {
		if(null != psku){
			psku = psku.trim();
		}
		OperationResult or = new OperationResult();
		or.setData(iEDADao.skuIsExist(psku));
		return or;
	}
	
	@Override
	public OperationResult cancelWestOrderById(String id) {
		Map<String,Object> map = new HashMap<>();
		map.put("id", id);
		map.put("userId", getUser().getId());
		iEDADao.cancelWestOrderById(map);
		OperationResult re = new OperationResult();
		re.setData("success");
		return re;
	}

	@Override
	public OperationResult toEastShipping(String id) {
		OperationResult or = new OperationResult();
		//判断补发单，特殊处理
		if(id.indexOf("W_") > -1 ){
			List<WestShippingModel> westShip = iEDADao.getWestShipByShipOrderId(id);
			if(null != westShip && westShip.size() > 0){
				//构建eda单
				EDAOrderModel edaOrder = WestShippingModel.toEastModel(westShip);
				iEDADao.addEDAOrder(edaOrder);
				iEDADao.updateWestWOrderToEast(edaOrder.getOrderId());
			}
		}else{
			List<ShipOrderInfo> orders =iEDADao.getEDAEastOrderById(id);
			Map<String, List<ShipOrderInfo>> map = new HashMap<>();
			//分组存入map中
			for (ShipOrderInfo shipOrder : orders) {
				String ocsId = shipOrder.getOrderOCSId();
				if (null == map.get(ocsId)) {
					List<ShipOrderInfo> list = new ArrayList<>();
					list.add(shipOrder);
					map.put(ocsId, list);
				} else {
					List<ShipOrderInfo> list = map.get(ocsId);
					list.add(shipOrder);
					map.put(ocsId, list);
				}
			}
			iEDADao.updateWestToEast(id);
			eDAService.formatAndSaveEdaOrder(map);
		}
		or.setData("success");
		return or;
	}

	@Override
	public OperationResult addressUpdate(Map<String,Object> west) {
		iEDADao.addressUpdate(west);
		OperationResult or = new OperationResult();
		or.setData("success");
		return or;
	}

	@Override
	public OperationResult edaAddressUpdate(Map<String, Object> eda) {
		iEDADao.edaAddressUpdate(eda);
		OperationResult or = new OperationResult();
		or.setData("success");
		return or;
	}

	@Override
	public List<Map<String, Object>> getOrder(Map<String, Object> eda) {
		//地址数据
		String address = "";
		List<Map<String, Object>> items = iEDADao.getOrder(eda);
		//遍历数据，判断每个sku的发货情况
		for(Map<String, Object> sku :items){
			sku.put("estatus", "");
			sku.put("wstatus", "");
			//先查询东仓
			sku.put("OCSID", sku.get("OCSID").toString());
			Map<String, Object> east = iEDADao.getOrderItemInfoInEast(sku);
			if(null != east){
				sku.put("estatus", "1");
				address = east.get("SHIPPING_ADDRESS").toString();
			}
			//查询西仓数据
			sku.put("OCSITEMID", sku.get("OCSITEMID").toString());
			List<Map<String, Object>> west = iEDADao.getOrderItemInfoInWest(sku);
			if(null != west && west.size() > 0){
				sku.put("wstatus", "1");
				if("".equals(address)){
					address = getEdaShippingAddressJsonStr(west.get(0));
				}
			}
			//默认使用自己的地址
			if("".equals(address)){
				address = getEdaShippingAddressJsonStr(sku);
			}
			sku.put("address", address);
		}
		
		return items;
	}

	private String getEdaShippingAddressJsonStr(Map<String, Object> map){
		JSONObject adJson = new JSONObject();
		adJson.put("addressLine1", nullStrObjectToString(map.get("ADDRESSLINE1")));
		adJson.put("addressLine2",  nullStrObjectToString(map.get("ADDRESSLINE2")));
		adJson.put("city",  nullStrObjectToString(map.get("CITY")));
		adJson.put("country",  nullStrObjectToString(map.get("COUNTRY")));
		adJson.put("name",  nullStrObjectToString(map.get("NAME")));
		adJson.put("phone",  nullStrObjectToString(map.get("PHONE")));
		adJson.put("postalCode",  nullStrObjectToString(map.get("POSTALCODE")));
		adJson.put("provState",  nullStrObjectToString(map.get("PROVSTATE")));
		return adJson.toString();
	}
	
	public static String nullStrObjectToString(Object obj){
		if(null == obj){
			return "";
		}else{
			return obj.toString();
		}
	}

	@Override
	public OperationResult addOrder(Map<String, Object> map) {
		OperationResult or = new OperationResult();
		List<Map<String,Object>> rows = (List<Map<String,Object>>)map.get("skus");
		List<Map<String,Object>> eastSku = new ArrayList<>();
		List<Map<String,Object>> westSku = new ArrayList<>();
		for(Map<String,Object> row : rows){
			Integer isEast = Integer.parseInt(row.get("isEast").toString());
			//需要东仓发货的
			if(isEast == 0){
				eastSku.add(row);
			}
			//需要西仓发货的
			else if(isEast == 1){
				westSku.add(row);
			}
		}
		
		String orderId = map.get("orderId").toString();
		//判断此单是否有手工单
		if(iEDADao.hasHand(orderId) > 0){
			or.setData("fail");
			or.setDescription("此订单已在西仓手工发货过，请查询处理！");
			return or;
		}
		//判断西仓此单是否发货，没有发货并且取消西仓数据
		if(iEDADao.hasWest(orderId) > 0){
			or.setData("fail");
			or.setDescription("此订单已在西仓自动发货过，请查询处理！");
			return or;
		}else{
			iEDADao.updateSysWestOrder(orderId);
		}
		String address = map.get("address").toString();
		addEastOrder(eastSku,orderId,address);
		addWestOrder(westSku,orderId,address);
		
		or.setData("success");
		return or;
	}
	
	/**
	 * 手工西仓新建发货单
	 * @param westSku
	 * @param orderId
	 * @param address
	 */
	private void addWestOrder(List<Map<String, Object>> westSku,String orderId,String address) {
		if(null == westSku ||westSku.size()==0){
			return;
		}
		JSONObject json = JSONObject.fromObject(address);
		List<ShipOrderInfo> orders = new ArrayList<>();
		for(Map<String, Object> skuInfo : westSku){
			ShipOrderInfo edaOrder = new ShipOrderInfo();
			edaOrder.setPlatform(skuInfo.get("PLATFORM").toString());
			edaOrder.setAccount(skuInfo.get("ACCOUNT").toString());
			edaOrder.setOrderItemOCSId(skuInfo.get("OCSITEMID").toString());
			edaOrder.setOrderId(orderId);
			edaOrder.setCreateTime(skuInfo.get("CREATETIME").toString());
			String skuName = skuInfo.get("SKU").toString();
			edaOrder.setWarehouseSku(skuName);
			edaOrder.setWeight(iEDADao.getSKUInfo(skuName));
			edaOrder.setQuantity(Integer.parseInt(skuInfo.get("QTY").toString()));
			edaOrder.setName(json.getString("name"));
			edaOrder.setAddressLine1(json.getString("addressLine1"));
			edaOrder.setAddressLine2(json.getString("addressLine2"));
			edaOrder.setCity(json.getString("city"));
			edaOrder.setCountry(json.getString("country"));
			edaOrder.setProvState(json.getString("provState"));
			edaOrder.setPostalCode(json.getString("postalCode"));
			edaOrder.setPhone(json.getString("phone"));
			orders.add(edaOrder);
		}
		List<Map<String, Object>> returnList = USWestShipPackageSelectionSupport.formatPackage(orders);
		for(Map<String, Object> west : returnList){
			west.put("isHand", 1);
			iEDADao.addWest(west);
		}
	}
	
	/**
	 * 手工东仓新建发货单
	 * @param eastSku
	 * @param orderId
	 * @param address
	 */
	private void addEastOrder(List<Map<String, Object>> eastSku,String orderId,String address) {
		if(null == eastSku ||eastSku.size()==0){
			return;
		}
		//查询数据
		ShipOrderInfo edaOrder = new ShipOrderInfo();
		edaOrder.setEdaAccount("API_LE");
		edaOrder.setOrderId(orderId);
		String ocsId = null;
		JSONArray itemArray = new JSONArray();
		for(Map<String, Object> skuInfo : eastSku){
			edaOrder.setPlatform(skuInfo.get("PLATFORM").toString());
			if(null == ocsId){
				ocsId = skuInfo.get("OCSID").toString();
			}
			
			JSONObject json = new JSONObject();
			json.put("warehouseSku", skuInfo.get("SKU").toString());
			json.put("quantity", Integer.parseInt(skuInfo.get("QTY").toString()));
			json.put("unitPrice", "");
			json.put("productId", "");
			json.put("description", "");
			itemArray.add(json);
		}
		edaOrder.setOrderOCSId(ocsId);
		edaOrder.setItems(itemArray);
		edaOrder.setShippingAddress(address);
		Map<String,String> param = new HashMap<>();
		param.put("edaAccount", edaOrder.getEdaAccount());
		param.put("ocsId", ocsId);
		if(iEDADao.edaOrderIsExist(param) == 0){
			iEDADao.addEdaOrders(edaOrder);
		}
		
	}

	@Override
	public ResponseResult<InventoryHistoryModel> inventoryHistoryList(RequestParam param) {
		Map<String,Object> map = param.getParam();
		int count = iEDADao.countInventoryHistory(map);
		ResponseResult<InventoryHistoryModel> result = new ResponseResult<>();
		List<InventoryHistoryModel> rows = iEDADao.queryInventoryHistoryByPage(map,param.getStartRow(),param.getEndRow());
		for(InventoryHistoryModel ih :rows){
			String billNum = ih.getBillNum();
			if(null != billNum && !"".equals(billNum)){
				Map<String,Object> eda = iEDADao.getEDAOrderInfoByORAId(billNum);
				if(null != eda){
					ih.setOrderId(eda.get("ORDERID").toString());
					ih.setEdaOrderCreateDate(eda.get("EDAORDERCREATEDATE").toString());
				}
			}
		}
		result.setRows(rows);
		result.setTotal(count);
		result.setFooter(this.getInventoryHistoryFooter(map));
		return result;
	}
	
	private List<Map<String, String>> getInventoryHistoryFooter(Map<String, Object> map) {
		return this.formatData(iEDADao.getTotalByRecordType(map));
	}

	private List<Map<String, String>> formatData(List<Map<String, Object>> data) {
		String[] title = {"recordType","qty","warehouseId"};
		List<Map<String, String>> list = new ArrayList<>();
		for(Map<String, Object> map : data){
			if("2".equals(map.get("WAREHOUSEID").toString())){
				map.put("WAREHOUSEID", "Los Angeles Warehouse");
			}else if("7".equals(map.get("WAREHOUSEID").toString())){
				map.put("WAREHOUSEID", "US New Jersey Warehouse");
			}
			Map<String, String> row = new HashMap<>();
			for(int i= 0;i<title.length;i++){
				Object obj = map.get(title[i].toUpperCase());
				if(null == obj){
					obj = 0;
				}
				row.put(title[i], String.valueOf(obj));
			}
			list.add(row);
		}
		return list;
	}
	
	@Override
	public List<ComboBoxVO> getRecordType() {
		return iEDADao.getRecordType();
	}

	@Override
	public OperationResult getEdaInfoForToEastOrder(String orderId) {
		OperationResult or = new OperationResult();
		or.setData(iEDADao.getEdaInfoForToEastOrder(orderId));		
		return or;
	}

}
