package com.it.ocs.eda.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.it.ocs.eda.model.OrderExportOfUSWestModel;
import com.it.ocs.eda.model.ShipOrderInfo;
import com.it.ocs.eda.model.WestPackageModel;
import com.it.ocs.excel.annotation.ExcelLink;
/**
 * 美国西部仓库订单分包和物流方式选择支持类
 * @author chenyong
 *
 */
public class USWestShipPackageSelectionSupport {
	private final static String proveExcept = "HI,AK,PR,AA,AP,AE,GU,AS,MP,VI,FM,MH,PW,";
	private static WestPackageModel westPackages[] ={
			WestPackageModel.getInstance("USPS01", -0.1d, 0.142, 17.78, 25.4, 12.7),
			WestPackageModel.getInstance("USPS01", 0.142, 0.285, 20.32, 30.48, 15.24),
			WestPackageModel.getInstance("USPS01", 0.285, 0.453, 22.86, 35.56, 20.32),
			WestPackageModel.getInstance("FDX01", 0.453, 0.907, 20.32, 15.24, 10.16),
			WestPackageModel.getInstance("FDX01", 0.907, 1.361, 22.86, 17.78, 12.7),
			WestPackageModel.getInstance("FDX01", 1.361, 1.814, 30.48, 20.32, 15.24),
			WestPackageModel.getInstance("FDX01", 1.814, 2.722, 35.56, 25.4, 15.24),
			WestPackageModel.getInstance("FDX01", 2.722, 4.082, 35.56, 30.48, 20.32),
			WestPackageModel.getInstance("FDX02", 4.082, 5.897, 40.64, 35.56, 25.4),
			WestPackageModel.getInstance("FDX02", 5.897, 8.165, 45.72, 30.48, 38.1),
			WestPackageModel.getInstance("FDX02", 8.165, 11.34, 50.8, 30.48, 45.72),
			WestPackageModel.getInstance("FDX02", 11.34, 13.608, 71.12, 45.72, 30.48),
			WestPackageModel.getInstance("FDX02", 13.608, 18.144, 76.2, 50.8, 30.48),
			WestPackageModel.getInstance("FDX02", 18.144, 22.68, 81.28, 50.8, 38.1),
			WestPackageModel.getInstance("FDX02", 22.68, 27.216, 96.52, 50.8, 40.64),
			WestPackageModel.getInstance("FDX02", 27.216, 30.0, 101.6, 60.96, 40.64),
	};
	
	public static Map<String,String> provCode = new HashMap<>();
	
	static{
		provCode.put("CALIFORNIA","CA");
		provCode.put("OREGON","OR");
		provCode.put("WASHINGTON","WA");
		provCode.put("IDAHO","ID");
		provCode.put("NEVADA","NV");
		provCode.put("ARIZONA","AZ");
		provCode.put("NEWMEXICO","NM");
		provCode.put("UTAH","UT");
		provCode.put("COLORADO","CO");
		provCode.put("WYOMING","WY");
		provCode.put("MONTANA","MT");
		provCode.put("NORTHDAKOTA","ND");
		provCode.put("SOUTHDAKOTA","SD");
		provCode.put("NEBRASKA","NE");
		provCode.put("KANSAS","KS");
		provCode.put("OKLAHOMA","OK");
		provCode.put("TEXAS","TX");
		//provCode.put("HAWAII","HI");
		//provCode.put("ALASKA","AK");
		provCode.put("MAINE","ME");
		provCode.put("NEWHAMPSHIRE","NH");
		provCode.put("VERMONT","VT");
		provCode.put("MASSACHUSETTS","MA");
		provCode.put("RHODEISLAND","RI");
		provCode.put("CONNECTICUT","CT");
		provCode.put("NEWYORK","NY");
		provCode.put("PENNSYLVANIA","PA");
		provCode.put("NEWJERSEY","NJ");
		provCode.put("DELAWARE","DE");
		provCode.put("MARYLAND","MD");
		provCode.put("COLUMBIA","DC");
		provCode.put("WESTVIRGINIA","WV");
		provCode.put("VIRGINIA","VA");
		provCode.put("NORTHCAROLINA","NC");
		provCode.put("SOUTHCAROLINA","SC");
		provCode.put("GEORGIA","GA");
		provCode.put("FLORIDA","FL");
		provCode.put("ALABAMA","AL");
		provCode.put("TENNESSEE","TN");
		provCode.put("KENTUCKY","KY");
		provCode.put("OHIO","OH");
		provCode.put("MICHIGAN","MI");
		provCode.put("INDIANA","IN");
		provCode.put("LOUISIANA","LA");
		provCode.put("ARKANSAS","AR");
		provCode.put("MISSOURI","MO");
		provCode.put("IOWA","IA");
		provCode.put("MINNESOTA","MN");
		provCode.put("WISCONSIN","WI");
		provCode.put("ILLINOIS","IL");
		provCode.put("MISSISSIPPI","MS");

		provCode.put("HAWAII","HI");
		provCode.put("ALASKA","AK");
		provCode.put("PUERTORICO","PR");
		provCode.put("ARMEDFORCESAMERICAS","AA");
		provCode.put("ARMEDFORCESPACIFIC","AP");
		provCode.put("ARMEDFORCESEUROPE","AE");
		provCode.put("GUAM","GU");
		provCode.put("AMERICANSAMOA","AS");
		provCode.put("NORTHERNMARIANAISLANDS","MP");
		provCode.put("VIRGINISLANDS","VI");
		provCode.put("FEDERALSTATESOFMICRONESIA","FM");
		provCode.put("MARSHALLISLANDS","MH");
		provCode.put("PALAU","PW");

	}
	
	public static List<Map<String,Object>> formatPackage(List<ShipOrderInfo> shipOrderInfos){
		double totalWeight = 0d;
		for(ShipOrderInfo shipOrderInfo : shipOrderInfos){
			totalWeight = totalWeight + shipOrderInfo.getWeight()*shipOrderInfo.getQuantity();
		}
		List<Map<String,Object>> rows = new ArrayList<>();
		if(totalWeight <= 30d){
			WestPackageModel wp = choosePackage(totalWeight);
			for(ShipOrderInfo shipOrderInfo : shipOrderInfos){
				rows.add(getRow(shipOrderInfo,wp,null,totalWeight));
			}
		//拆包
		}else{
			getPackage(rows,shipOrderInfos,1);
		}
		for(Map<String,Object> row : rows){
			
		}
		return rows;
	}
	
	public static void getPackage(List<Map<String,Object>> rows,List<ShipOrderInfo> shipOrderInfos,int packageNumber){
		if(null == shipOrderInfos || shipOrderInfos.size() == 0){
			return ;
		}
		Double sweight = 30d;
		Double  total = 0d;
		List<ShipOrderInfo> spare = new ArrayList<>();
		List<ShipOrderInfo> curPage = new ArrayList<>();
		//按照从大到小排序
		Collections.sort(shipOrderInfos,new Comparator<ShipOrderInfo>() {
            @Override
            public int compare(ShipOrderInfo o1, ShipOrderInfo o2) {
                return (int)(o1.getWeight() - o2.getWeight()) ;
            }
        });
		for(ShipOrderInfo shipOrderInfo : shipOrderInfos){
			int qty = shipOrderInfo.getQuantity();
			if(total + shipOrderInfo.getWeight() < sweight){
				Double dw = sweight-total;
				int cqty = (int) Math.floor(dw/shipOrderInfo.getWeight());
				if(cqty >= qty){
					total = total + shipOrderInfo.getWeight()*qty;
					curPage.add(shipOrderInfo);
				}else{
					//数量小于总数，拆分订单
					total = total + shipOrderInfo.getWeight()*cqty;
					shipOrderInfo.setQuantity(cqty);
					curPage.add(shipOrderInfo);
					
					ShipOrderInfo nShipOrderInfo = (ShipOrderInfo)shipOrderInfo.clone();
					nShipOrderInfo.setQuantity(qty -cqty);
					spare.add(nShipOrderInfo);
				}
			}else{
				spare.add(shipOrderInfo);
			}
		}
		WestPackageModel wp = choosePackage(total);
		for(ShipOrderInfo shipOrderInfo : curPage){
			rows.add(getRow(shipOrderInfo,wp,packageNumber,total));
		}
		getPackage(rows,spare,packageNumber+1);
	}
	
	public static Map<String,Object> getRow(ShipOrderInfo shipOrderInfo,WestPackageModel wp,Integer packageNumber,Double totalWeight){
		//判断是否有新地址
		if(null != shipOrderInfo.getNewAddress()&& !"".equals(shipOrderInfo.getNewAddress())){
			shipOrderInfo.setShippingAddress(shipOrderInfo.getNewAddress());
		}
		Map<String,Object> map = getExcelTemp(OrderExportOfUSWestModel.class);
		map.put("PLATFORM",shipOrderInfo.getPlatform());
		map.put("ACCOUNT",shipOrderInfo.getAccount());
		map.put("ORDERID",shipOrderInfo.getOrderItemOCSId());
		map.put("MERCHANTFULFILLMENTORDERID",shipOrderInfo.getOrderId());
		if(null == packageNumber){
			map.put("ISMPS","");
		}else{
			map.put("ISMPS",packageNumber);
		}
		map.put("DISPLAYABLEORDERID",shipOrderInfo.getOrderId());
		map.put("DISPLAYABLEORDERDATE",shipOrderInfo.getCreateTime());
		map.put("MERCHANTSKU",shipOrderInfo.getWarehouseSku());
		map.put("QUANTITY",shipOrderInfo.getQuantity());
		map.put("MERCHANTFULFILLMENTORDERITEMID",shipOrderInfo.getWarehouseSku());
		map.put("GIFTMESSAGE","");
		map.put("DISPLAYABLECOMMENT","");
		map.put("PERUNITDECLAREDVALUE","");
		map.put("DISPLAYABLEORDERCOMMENT","Thank you for order");
		map.put("DELIVERYSLA","Standard");
		map.put("ADDRESSNAME",shipOrderInfo.getName());
		map.put("ADDRESSFIELDONE",shipOrderInfo.getAddressLine1());
		map.put("ADDRESSFIELDTWO",shipOrderInfo.getAddressLine2());
		map.put("ADDRESSFIELDTHREE","");
		map.put("ADDRESSCITY",shipOrderInfo.getCity());
		map.put("ADDRESSCOUNTRYCODE",formatCountry(shipOrderInfo.getCountry()));
		map.put("ADDRESSSTATEORREGION",formatProve(shipOrderInfo.getProvState()));
		map.put("ADDRESSPOSTALCODE",shipOrderInfo.getPostalCode());
		map.put("ADDRESSPHONENUMBER",shipOrderInfo.getPhone());
		map.put("NOTIFICATIONEMAIL","");
		map.put("CARRIERSERVICETYPECODE",wp.getCode());
		map.put("PACKAGELENGTH",wp.getLength().toString());
		map.put("PACKAGEWIDTH",wp.getWidth().toString());
		map.put("PACKAGEHEIGHT",wp.getHeight().toString());
		map.put("PACKAGEWEIGHT",totalWeight.toString());
		map.put("TRANCKINGNUMBER","");
		
		/**
		 * 特殊情况
		 *1、 大于0.43KG的订单，如果地址中包含了POBOX，运输渠道就选择USPS02（USPS Priority Mail），因为这种POBOX的邮政信箱地址只有USPS能派送
		 *2、HI,AK,PR,AA,AP,AE,GU,AS,MP,VI,FM,MH,PW
		 *
		 */
		String addressStr = shipOrderInfo.getAddressLine1()+shipOrderInfo.getAddressLine2();
		addressStr = addressStr.replaceAll("\\s*", "").toUpperCase();
		String provStr = shipOrderInfo.getProvState();
		if((addressStr.indexOf("POBOX")> -1||proveExcept.indexOf(provStr+",") > -1)&& totalWeight > 0.453){
			map.put("CARRIERSERVICETYPECODE","USPS02");
		}
		return map;
	}
	
	public static void main(String[] args) {
		ShipOrderInfo s = new ShipOrderInfo();
		s.setAddressLine1("PO box 456565");
		s.setAddressLine2("");
		s.setCountry("US");
		s.setProvState("AL");
		WestPackageModel wa = choosePackage(0.5);
		Map<String,Object> map = getRow(s,wa,1,1.5);
		System.out.println(map.get("CARRIERSERVICETYPECODE"));
	}
	
	/**
	 * 获取导出模板数据
	 * @param clazz
	 * @return
	 */
	public static Map<String,Object> getExcelTemp(Class clazz){
		Map<String,Object> map = new HashMap<>();
		Field fields[] = clazz.getDeclaredFields();
		for(Field field : fields){
			ExcelLink excel = field.getAnnotation(ExcelLink.class);
			String value = excel.value();
			if(null == value){
				value = "";
			}
			map.put(field.getName().toUpperCase(), value);
		}
		return map;
	}
	
	/**
	 * 选择包装信息
	 * @param weight
	 * @return
	 */
	public static WestPackageModel choosePackage(double weight){
		for(WestPackageModel westPackage : westPackages){
			if(weight > westPackage.getMin() && weight <= westPackage.getMax()){
				return westPackage;
			}
		}
		return null;
	}
	
	/**
	 * 国家简码USA 切换成 US
	 * @param country
	 * @return
	 */
	public static String formatCountry(String country){
		if("USA".endsWith(country.toUpperCase())){
			return "US";
		}
		return country;
	}
	
	/**
	 * 州转换成简码
	 * @param prove
	 * @return
	 */
	public static String formatProve(String prove){
		prove = prove.trim();
		prove = prove.replaceAll("\\s*", "").toUpperCase();
		String code = provCode.get(prove);
		if(null != code){
			return code;
		}
		return prove;
	}
	

}
