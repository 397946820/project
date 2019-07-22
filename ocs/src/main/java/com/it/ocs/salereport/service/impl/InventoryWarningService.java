package com.it.ocs.salereport.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.amazon.model.MyiUnsuppressedVO;
import com.it.ocs.amazon.service.ReportDataSaveSupport;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.salereport.model.InventoryWarningModel;
import com.it.ocs.salereport.model.PlanBaseModel;
import com.it.ocs.salereport.service.IInventoryWarningService;
import com.it.ocs.salereport.service.support.MapCache;
import com.it.ocs.salereport.utils.StockCodeUtil;
import com.it.ocs.sys.dao.IInventoryWarningDao;
import com.it.ocs.task.core.TaskExecutorUtil;

@Service
public class InventoryWarningService implements IInventoryWarningService {
	
	@Autowired
	private IInventoryWarningDao iInventoryWarningDao;
	
	@Autowired
	private ReportDataSaveSupport ReportDataSaveSupport;
	
	@Override
	public ResponseResult<InventoryWarningModel> findInventoryWarningList(RequestParam param) {
		Map<String,Object> map = param.getParam();
		int count = iInventoryWarningDao.countInventoryWarning(map);
		ResponseResult<InventoryWarningModel> result = new ResponseResult<>();
		result.setRows(iInventoryWarningDao.queryInventoryWarningByPage(map,param.getStartRow(),param.getEndRow()));
		result.setTotal(count);
		return result;
	}
	
	@Override
	public OperationResult updateInventoryWarning() {
		calculateSkuInventory();
		OperationResult or = new OperationResult();
		or.setData("success");
		return or;
	}
	public void calculateSkuInventory(){
		//新建临时缓存区域，避免多次重复操作
		MapCache map = MapCache.getInstance();
		//清除当天数据
		this.iInventoryWarningDao.clearInventoryWarnDataThisDay();
		//清除erp同步数据,并重新拉取
		this.iInventoryWarningDao.updateErpData();
		//分页计算sku信息
		int total = this.iInventoryWarningDao.countInventoryWarningSku();
		//一次获取100条数据,计算循环多少
		int c = total%200 == 0?total%200:total%200+1;
		
		final CountDownLatch latch = new CountDownLatch(c);
		for(int i=1;i<=c;i++){
			List<InventoryWarningModel> iwms= this.iInventoryWarningDao.queryInventoryWarningSKUByPage((i-1)*200, i*200-1);
			//多线程执行任务
			TaskExecutorUtil.threadRun(new Runnable() {
				@Override
				public void run() {
					for(InventoryWarningModel iwm : iwms){
						calculateSkuInventoryInfo(iwm,map);
						//插入数据
						iInventoryWarningDao.addInventoryWarning(iwm);
					}
					latch.countDown();
				}
			});
		}
		//主线程等待
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 计算sku库存预警主入口
	 * @param iwm
	 * @param map
	 */
	public void calculateSkuInventoryInfo(InventoryWarningModel iwm,MapCache map){
		//oversize
		this.setOverSize(iwm,map);
		//时长周期等信息处理
		this.setCycleTime(iwm,map);
		//最终预警系数
		this.setWarnCoeff(iwm,map);
		//查询是否dis
		this.setDiscontinue(iwm,map);
		//统计库存
		this.setInventoryInfo(iwm);
		//统计销量
		this.setSaleQty(iwm);
		//计算销量和库存相关逻辑，
		this.calculateSaleAndInventory(iwm);
		//计算是否断货
		this.setIsBad(iwm);
		//查询上月断货天数
		this.setBadDayOfLastMonth(iwm);
	}
	/**
	 * 设置上月断货天数
	 * @param iwm
	 */
	private void setBadDayOfLastMonth(InventoryWarningModel iwm) {
		Map<String,Object> param = new HashMap<>();
		param.put("sku", iwm.getSku());
		param.put("scode",iwm.getScode());
		iwm.setLast_month_bad(this.iInventoryWarningDao.getBadDayOfLastMonth(param));
	}

	/**
	 * 计算是否断货
	 * @param iwm
	 */
	private void setIsBad(InventoryWarningModel iwm) {
		String dis = iwm.getIs_dis();
		String hasSale = iwm.getHas_sale();
		if("Y".equals(hasSale) && ("CONTINUE".equals(dis))){
			int s = iwm.getQuantity() + iwm.getReserved_sale();
			int d = iwm.getAvg_3_day()/30;
			if(s <= d){
				iwm.setIs_bad("断货");
			}
		}
		
	}

	/**
	 * 计算关于销量和库存的相关逻辑数据
	 * @param iwm
	 */
	private void calculateSaleAndInventory(InventoryWarningModel iwm) {
		//近多少天日均最大销量
		Double ad3 = Math.round(iwm.getAvg_3_day()/3.0*10)/10.0;
		Double ad7 = Math.round(iwm.getAvg_7_day()/7.0*10)/10.0;
		Double ad30 = Math.round(iwm.getAvg_30_day()/30.0*10)/10.0;
		//计算本月日均
		//本月断货天数
		Map<String,Object> map = new HashMap<>();
		map.put("sku", iwm.getSku());
		map.put("scode",iwm.getScode());
		map.put("ship_type",iwm.getShip_type());
		int cd = getCurrDayOfMonth()-1;
		map.put("day",cd);
		int c = this.iInventoryWarningDao.countBadDay(map);
		iwm.setBad_day(c);
		Double am = Math.round(iwm.getMonth_days_sales()/(cd - c)*10.0)/10.0;
		Double max = ad3;
		if(max < ad7){
			max = ad7;
		}
		if(max < ad30){
			max = ad30;
		}
		if(max < am){
			max = am;
		}
		iwm.setQty_one_day(max);
		if(am > 0){
			iwm.setAvg_days_sales(Integer.parseInt(String.valueOf(Math.round(am))));
		}else{
			iwm.setAvg_days_sales(0);
		}
		//安全库存量-国外库存
		int safe = (int) (max * iwm.getCoeff()*iwm.getCoeff1()*iwm.getCycle_time() - iwm.getOuter_stock());
		if(safe > 0){
			iwm.setSave_ity(safe);
		}
		//可售天数
		if(iwm.getOuter_stock() > 0){
			if(max > 0){
				iwm.setSale_day((int)(Math.round(iwm.getOuter_stock()/max)));
			}else{
				iwm.setSale_day(999);
			}
		}else{
			iwm.setSale_day(0);
		}
		
	}

	/**
	 * 获取库存信息
	 * @param iwm
	 */
	private void setInventoryInfo(InventoryWarningModel iwm) {
		MyiUnsuppressedVO sm = null;
		Map<String,Object> param = new HashMap<>();
		param.put("sku", iwm.getSku());
		
		
		//1 US01 美国亚马逊
		//2 US02 ebay 官网美国站
		//3 US03 销量0
		//4 DE01 亚马逊 德国，法国，意大利IT，西班牙ES，英国 SKU非UK
		//5 DE02 ebay 官网德国站
		//6 UK01 亚马逊 英国SKU是UK的
		//7 UK02 ebay 官网 英国站
		//8 JP01 日本站 亚马逊
		//9 CA01 加拿大站亚马逊
		switch(StockCodeUtil.format(iwm.getScode())){
		case 1:
			param.put("marketplace", "amazon.com");
			sm = getAmazonInventory(param);
			this.setAmazonInventoryInfo(iwm,sm);
			break;
		case 2:
			sm = this.iInventoryWarningDao.getUSInventory(param);
			break;
		case 3:
			break;
		case 4:
			param.put("marketplace", "amazon.de");
			sm = getAmazonInventory(param);
			this.setAmazonInventoryInfo(iwm,sm);
			break;
		case 5:
			//查询wms库存数据
			sm = this.iInventoryWarningDao.getWMSInventory(param);
			break;
		case 6:
			param.put("marketplace", "amazon.co.uk");
			sm = getAmazonInventory(param);
			this.setAmazonInventoryInfo(iwm,sm);
			break;
		case 7:
			sm = this.iInventoryWarningDao.getUKInventory(param);
			break;
		case 8:
			param.put("marketplace", "amazon.jp");
			sm = getAmazonInventory(param);
			this.setAmazonInventoryInfo(iwm,sm);
			break;
		case 9:
			param.put("marketplace", "amazon.ca");
			sm = getAmazonInventory(param);
			this.setAmazonInventoryInfo(iwm,sm);
			break;
		default :
		}
		if(null != sm){
			iwm.setQuantity(Integer.parseInt(sm.getAfn_fulfillable_quantity()));
		}
		int total = iwm.getQuantity() + iwm.getInbound()+iwm.getReceiving()+iwm.getReserved_sale();
		iwm.setOuter_stock(total);
	}



	private void setAmazonInventoryInfo(InventoryWarningModel iwm, MyiUnsuppressedVO sm) {
		if(null != sm){
			iwm.setInbound(sm.getAfn_inbound_shipped_quantity()==null?0:Integer.parseInt(sm.getAfn_inbound_shipped_quantity()));
			iwm.setReceiving(sm.getAfn_inbound_receiving_quantity()==null?0:Integer.parseInt(sm.getAfn_inbound_receiving_quantity()));
			iwm.setReserved(sm.getAfn_reserved_quantity()==null?0:Integer.parseInt(sm.getAfn_reserved_quantity()));
			iwm.setReserved_sale(sm.getReserved_fc_transfers()==null?0:Integer.parseInt(sm.getReserved_fc_transfers()));
		}
		
	}

	private MyiUnsuppressedVO getAmazonInventory(Map<String, Object> param) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		param.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
		RequestParam params = new RequestParam();
		//params.setRows(10);
		params.setParam(param);
		ResponseResult<MyiUnsuppressedVO> rs = ReportDataSaveSupport.findMyiUnsuppressed(params);
		List<MyiUnsuppressedVO> lst = rs.getRows();
		MyiUnsuppressedVO returnVo = null;
		if(null != lst && lst.size() > 0){
			returnVo = lst.get(0);
			int inbound = returnVo.getAfn_inbound_shipped_quantity()==null?0:Integer.parseInt(returnVo.getAfn_inbound_shipped_quantity());
			int receiving  = returnVo.getAfn_inbound_receiving_quantity()==null?0:Integer.parseInt(returnVo.getAfn_inbound_receiving_quantity());
			int reserved = returnVo.getAfn_reserved_quantity()==null?0:Integer.parseInt(returnVo.getAfn_reserved_quantity());
			int reserved_sale = returnVo.getReserved_fc_transfers()==null?0:Integer.parseInt(returnVo.getReserved_fc_transfers());
			int allQty = returnVo.getAfn_fulfillable_quantity()== null?0:Integer.parseInt(returnVo.getAfn_fulfillable_quantity());
			for(int i = 1 ;i<lst.size();i++){
				MyiUnsuppressedVO my = lst.get(i);
				int inbound1 = my.getAfn_inbound_shipped_quantity()==null?0:Integer.parseInt(my.getAfn_inbound_shipped_quantity());
				int receiving1  = my.getAfn_inbound_receiving_quantity()==null?0:Integer.parseInt(my.getAfn_inbound_receiving_quantity());
				int reserved1 = my.getAfn_reserved_quantity()==null?0:Integer.parseInt(my.getAfn_reserved_quantity());
				int reserved_sale1 = my.getReserved_fc_transfers()==null?0:Integer.parseInt(my.getReserved_fc_transfers());
				int allQty1 = my.getAfn_fulfillable_quantity()== null?0:Integer.parseInt(my.getAfn_fulfillable_quantity());
				inbound = inbound + inbound1;
				receiving = receiving + receiving1;
				reserved = reserved + reserved1;
				reserved_sale = reserved_sale + reserved_sale1;
				allQty = allQty + allQty1;
			}
			returnVo.setAfn_inbound_shipped_quantity(String.valueOf(inbound));
			returnVo.setAfn_inbound_receiving_quantity(String.valueOf(receiving));
			returnVo.setAfn_reserved_quantity(String.valueOf(reserved));
			returnVo.setReserved_fc_transfers(String.valueOf(reserved_sale));
			returnVo.setAfn_fulfillable_quantity(String.valueOf(allQty));
			returnVo.setSku(param.get("sku").toString());
			return returnVo;
		}
		return null;
	}

	/**
	 * 查询sku对应的销量
	 * @param iwm
	 */
	private void setSaleQty(InventoryWarningModel iwm) {
		Map<String,Object> param = new HashMap<>();
		param.put("sku", iwm.getSku());
		String scode = iwm.getScode();
		//US01 美国亚马逊
		//US02 ebay 官网美国站
		//US03 销量0
		//DE01 亚马逊 德国，法国，意大利IT，西班牙ES，英国 SKU非UK
		//DE02 ebay 官网德国站
		//UK01 亚马逊 英国SKU是UK的
		//UK02 ebay 官网 英国站
		//JP01 日本站 亚马逊
		//CA01 加拿大站亚马逊
		int day3 = 0;
		int day7 = 0;
		int day30 = 0;
		int month = 0;
		int day180 = 0;
		switch(StockCodeUtil.format(scode)){
		case 1:
			param.put("day", 3);
			day3 = this.iInventoryWarningDao.getUS01SaleQtyBySku(param);
			param.put("day", 7);
			day7 = this.iInventoryWarningDao.getUS01SaleQtyBySku(param);
			param.put("day", 30);
			day30 = this.iInventoryWarningDao.getUS01SaleQtyBySku(param);
			param.put("day", getCurrDayOfMonth()-1);
			month = this.iInventoryWarningDao.getUS01SaleQtyBySku(param);
			param.put("day", 180);
			day180 = this.iInventoryWarningDao.getUS01SaleQtyBySku(param);
			break;
		case 2:
			param.put("day", 3);
			day3 = this.iInventoryWarningDao.getUS02SaleQtyBySku(param);
			param.put("day", 7);
			day7 = this.iInventoryWarningDao.getUS02SaleQtyBySku(param);
			param.put("day", 30);
			day30 = this.iInventoryWarningDao.getUS02SaleQtyBySku(param);
			param.put("day", getCurrDayOfMonth()-1);
			month = this.iInventoryWarningDao.getUS02SaleQtyBySku(param);
			param.put("day", 180);
			day180 = this.iInventoryWarningDao.getUS02SaleQtyBySku(param);
			break;
		case 3:
			break;
		case 4:
			param.put("day", 3);
			day3 = this.iInventoryWarningDao.getDE01SaleQtyBySku(param);
			param.put("day", 7);
			day7 = this.iInventoryWarningDao.getDE01SaleQtyBySku(param);
			param.put("day", 30);
			day30 = this.iInventoryWarningDao.getDE01SaleQtyBySku(param);
			param.put("day", getCurrDayOfMonth()-1);
			month = this.iInventoryWarningDao.getDE01SaleQtyBySku(param);
			param.put("day", 180);
			day180 = this.iInventoryWarningDao.getDE01SaleQtyBySku(param);
			break;
		case 5:
			param.put("day", 3);
			day3 = this.iInventoryWarningDao.getDE02SaleQtyBySku(param);
			param.put("day", 7);
			day7 = this.iInventoryWarningDao.getDE02SaleQtyBySku(param);
			param.put("day", 30);
			day30 = this.iInventoryWarningDao.getDE02SaleQtyBySku(param);
			param.put("day", getCurrDayOfMonth()-1);
			month = this.iInventoryWarningDao.getDE02SaleQtyBySku(param);
			param.put("day", 180);
			day180 = this.iInventoryWarningDao.getDE02SaleQtyBySku(param);
			break;
		case 6:
			param.put("day", 3);
			day3 = this.iInventoryWarningDao.getUK01SaleQtyBySku(param);
			param.put("day", 7);
			day7 = this.iInventoryWarningDao.getUK01SaleQtyBySku(param);
			param.put("day", 30);
			day30 = this.iInventoryWarningDao.getUK01SaleQtyBySku(param);
			param.put("day", getCurrDayOfMonth()-1);
			month = this.iInventoryWarningDao.getUK01SaleQtyBySku(param);
			param.put("day", 180);
			day180 = this.iInventoryWarningDao.getUK01SaleQtyBySku(param);
			break;
		case 7:
			param.put("day", 3);
			day3 = this.iInventoryWarningDao.getUK02SaleQtyBySku(param);
			param.put("day", 7);
			day7 = this.iInventoryWarningDao.getUK02SaleQtyBySku(param);
			param.put("day", 30);
			day30 = this.iInventoryWarningDao.getUK02SaleQtyBySku(param);
			param.put("day", getCurrDayOfMonth()-1);
			month = this.iInventoryWarningDao.getUK02SaleQtyBySku(param);
			param.put("day", 180);
			day180 = this.iInventoryWarningDao.getUK02SaleQtyBySku(param);
			break;
		case 8:
			param.put("day", 3);
			day3 = this.iInventoryWarningDao.getJP01SaleQtyBySku(param);
			param.put("day", 7);
			day7 = this.iInventoryWarningDao.getJP01SaleQtyBySku(param);
			param.put("day", 30);
			day30 = this.iInventoryWarningDao.getJP01SaleQtyBySku(param);
			param.put("day", getCurrDayOfMonth()-1);
			month = this.iInventoryWarningDao.getJP01SaleQtyBySku(param);
			param.put("day", 180);
			day180 = this.iInventoryWarningDao.getJP01SaleQtyBySku(param);
			break;
		case 9:
			param.put("day", 3);
			day3 = this.iInventoryWarningDao.getCA01SaleQtyBySku(param);
			param.put("day", 7);
			day7 = this.iInventoryWarningDao.getCA01SaleQtyBySku(param);
			param.put("day", 30);
			day30 = this.iInventoryWarningDao.getCA01SaleQtyBySku(param);
			param.put("day", getCurrDayOfMonth()-1);
			month = this.iInventoryWarningDao.getCA01SaleQtyBySku(param);
			param.put("day", 180);
			day180 = this.iInventoryWarningDao.getCA01SaleQtyBySku(param);
			break;
		default :
		}
		iwm.setAvg_3_day(day3);
		iwm.setAvg_7_day(day7);
		iwm.setAvg_30_day(day30);
		iwm.setMonth_days_sales(month);
		iwm.setTotal_sales(day180);
	}
	
	public static int getCurrDayOfMonth(){
		Calendar ca = Calendar.getInstance();
		return ca.get(Calendar.DAY_OF_MONTH);
	}
	
	public static void main(String[] args) {
		System.out.println(getCurrDayOfMonth());
	}
	
	/**
	 * 设置预警系数
	 * @param iwm
	 * @param cache
	 */
	private void setWarnCoeff(InventoryWarningModel iwm, MapCache cache) {
		String key = iwm.getField()+iwm.getPlatform()+iwm.getShip_type()+"COEFF";
		Object obj = cache.get(key);
		Double coeff = 1.0;
		if(null != obj){
			coeff = (Double)obj;
		}else{
			Map<String,String> map = new HashMap<String, String>();
			map.put("field", iwm.getField());
			map.put("platform", iwm.getPlatform());
			map.put("ship_type", iwm.getShip_type());
			coeff = this.iInventoryWarningDao.getWarnCoeff(map);
			if(null == coeff ){
				coeff = 1.0;
			}
			//放入缓存区,避免重复查询
			cache.put(key, coeff);
		}
		iwm.setCoeff(coeff);
	}
	
	/**
	 * 设置时长相关信息
	 * @param iwm
	 * @param cache
	 */
	private void setCycleTime(InventoryWarningModel iwm, MapCache cache) {
		String scode_ship_oversize = iwm.getScode()+iwm.getShip_type()+(null ==iwm.getOversize()?"":iwm.getOversize());
		scode_ship_oversize  = scode_ship_oversize.toUpperCase();
		iwm.setScode_ship_oversize(scode_ship_oversize);
		Object object = cache.get(scode_ship_oversize);
		PlanBaseModel pbm = null;
		if(null != object){
			pbm = (PlanBaseModel)object;
		}else{
			pbm = this.iInventoryWarningDao.getPlanTimeBaseByCode(scode_ship_oversize);
			//放入缓存区,避免重复查询
			cache.put(scode_ship_oversize, pbm);
		}
		if(null != pbm){
			pbm.setShip_type(iwm.getShip_type());
			pbm.setPurchase_cycle_time(iwm.getPurchase_cycle_time());
			iwm.setOpt_time(pbm.getOpt_time());
			iwm.setShip_time(pbm.getShip_time());
			iwm.setListing_time(pbm.getListing_time());
			iwm.setShip_cycle_time(pbm.getShip_cycle_time());
			
			iwm.setCycle_time(pbm.getCycle_time());
		}
		
	}
	
	/**
	 * 设置oversize
	 * @param iwm
	 * @param cache
	 */
	private void setOverSize(InventoryWarningModel iwm, MapCache cache) {
		String sku = iwm.getSku();
		String platform = iwm.getPlatform();
		platform = "US".equals(platform)?"US":"DE";
		String oversize = null;
		if(cache.containsKey(sku+platform+"oversize")){
			Object o = cache.get(sku+platform+"oversize");
			if(null != o){
				oversize = cache.get(sku+platform+"oversize").toString();
			}
			
		}else{
			Map<String,String> map = new HashMap<String, String>();
			map.put("sku", sku);
			map.put("platform", platform);
			oversize = this.iInventoryWarningDao.isOversize(map);
			//放入缓存
			cache.put(sku+platform+"oversize", oversize);
		}
		iwm.setOversize(oversize);
	}
	/**
	 * 查询是否dis
	 * @param iwm
	 * @param cache
	 */
	private void setDiscontinue(InventoryWarningModel iwm, MapCache cache) {
		String sku = iwm.getSku();
		String scode = iwm.getScode();
		String key = sku+scode+"discontinue";
		String dis = null;
		if(cache.containsKey(key)){
			Object ot = cache.get(key);
			if(null != ot){
				dis = cache.get(key).toString();
			}
			
		}else{
			Map<String,String> map = new HashMap<String, String>();
			map.put("sku", sku);
			String platform = "";
			String country = "";
			switch(StockCodeUtil.format(scode)){
			case 1:
				platform = "amazon";
				country = "US";
				break;
			case 2:
				platform = "light";
				country = "US";
				break;
			case 3:
				break;
			case 4:
				platform = "amazon";
				country = "DE";
				break;
			case 5:
				platform = "light";
				country = "DE";
				break;
			case 6:
				platform = "amazon";
				country = "UK";
				break;
			case 7:
				platform = "light";
				country = "UK";
				break;
			case 8:
				platform = "amazon";
				country = "JP";
				break;
			case 9:
				platform = "amazon";
				country = "CA";
				break;
			default :
			}
			map.put("platform", platform);
			map.put("country", country);
			
			Integer disCode = this.iInventoryWarningDao.getSKUDis(map);
			if(null != disCode){
				if("light".equals(platform)&&disCode != 1){
					map.put("platform", "ebay");
					disCode = this.iInventoryWarningDao.getSKUDis(map);
				}
				if(disCode == 1){
					dis = "DISCONTINUE";
				}else{
					dis = "CONTINUE";
				}
				
			}else{
				dis = "CONTINUE";
			}
			//放入缓存区
			cache.put(key, dis);
		}
		iwm.setIs_dis(dis);
	}


}
