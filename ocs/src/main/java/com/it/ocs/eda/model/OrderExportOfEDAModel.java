package com.it.ocs.eda.model;

import java.lang.reflect.Field;
import java.util.List;
import java.util.ListIterator;

import com.it.ocs.excel.annotation.ExcelLink;
import com.it.ocs.excel.utils.ExcelUtils;

public class OrderExportOfEDAModel {
	@ExcelLink(title="仓库代码/Warehouse Code",index=0)
	private String warehouseCode;
	
	@ExcelLink(title="参考编号/Reference Code",index=1)
	private String referenceCode;
	
	@ExcelLink(title="派送方式/Delivery Style",index=2)
	private String deliveryStyle;
	
	@ExcelLink(title="保险类型/Insurance Type",index=3)
	private String insuranceType;
	
	@ExcelLink(title="销售平台/Sales Platform",index=4)
	private String salesplatform;
	
	@ExcelLink(title="销售交易号/Sales Transaction Numbers",index=5)
	private String salesTransaction;
	
	@ExcelLink(title="收件人姓名/Consignee Name",index=6)
	private String name;
	
	@ExcelLink(title="收件人公司/Consignee Company",index=7)
	private String company;
	
	@ExcelLink(title="收件人国家/Consignee Country",index=8)
	private String country;
	
	@ExcelLink(title="州/Province",index=9)
	private String province;
	
	@ExcelLink(title="城市/City",index=10)
	private String city;
	
	@ExcelLink(title="街道/Street",index=11)
	private String street;
	
	@ExcelLink(title="门牌号/Doorplate",index=12)
	private String doorplate;
	
	@ExcelLink(title="邮编/Zip Code",index=13)
	private String zipCode;
	
	@ExcelLink(title="收件人Email/Consignee Email",index=14)
	private String email;
	
	@ExcelLink(title="收件人电话/Consignee Phone",index=15)
	private String phone;
	
	@ExcelLink(title="备注/Remark",index=16)
	private String remark;
	
	@ExcelLink(title="SKU1",index=17)
	private String sku1;
	@ExcelLink(title="数量1/Quantity 1",index=18)
	private String qty1;
	
	@ExcelLink(title="SKU2",index=19)
	private String sku2;
	@ExcelLink(title="数量2/Quantity 2",index=20)
	private String qty2;
	
	@ExcelLink(title="SKU3",index=21)
	private String sku3;
	@ExcelLink(title="数量3/Quantity 3",index=22)
	private String qty3;
	
	@ExcelLink(title="SKU4",index=23)
	private String sku4;
	@ExcelLink(title="数量4/Quantity 4",index=24)
	private String qty4;
	
	@ExcelLink(title="SKU5",index=25)
	private String sku5;
	@ExcelLink(title="数量5/Quantity 5",index=26)
	private String qty5;
	
	@ExcelLink(title="SKU6",index=27)
	private String sku6;
	@ExcelLink(title="数量6/Quantity 6",index=28)
	private String qty6;
	
	@ExcelLink(title="SKU7",index=29)
	private String sku7;
	@ExcelLink(title="数量7/Quantity 7",index=30)
	private String qty7;
	
	@ExcelLink(title="SKU8",index=31)
	private String sku8;
	@ExcelLink(title="数量8/Quantity 8",index=32)
	private String qty8;
	
	@ExcelLink(title="SKU9",index=33)
	private String sku9;
	@ExcelLink(title="数量9/Quantity 9",index=34)
	private String qty9;
	
	@ExcelLink(title="SKU10",index=35)
	private String sku10;
	@ExcelLink(title="数量10/Quantity 10",index=36)
	private String qty10;
	
	@ExcelLink(title="SKU11",index=37)
	private String sku11;
	@ExcelLink(title="数量11/Quantity 11",index=38)
	private String qty11;
	
	@ExcelLink(title="SKU12",index=39)
	private String sku12;
	@ExcelLink(title="数量12/Quantity 12",index=40)
	private String qty12;
	
	
	@ExcelLink(title="SKU13",index=41)
	private String sku13;
	@ExcelLink(title="数量13/Quantity 13",index=42)
	private String qty13;
	
	@ExcelLink(title="SKU14",index=43)
	private String sku14;
	@ExcelLink(title="数量14/Quantity 14",index=44)
	private String qty14;
	
	@ExcelLink(title="SKU15",index=45)
	private String sku15;
	@ExcelLink(title="数量15/Quantity 15",index=46)
	private String qty15;
	
	
	@ExcelLink(title="SKU16",index=47)
	private String sku16;
	@ExcelLink(title="数量16/Quantity 16",index=48)
	private String qty16;
	
	
	@ExcelLink(title="SKU17",index=49)
	private String sku17;
	@ExcelLink(title="数量17/Quantity 17",index=50)
	private String qty17;
	
	
	@ExcelLink(title="SKU18",index=51)
	private String sku18;
	@ExcelLink(title="数量18/Quantity 18",index=52)
	private String qty18;
	
	
	@ExcelLink(title="SKU19",index=53)
	private String sku19;
	@ExcelLink(title="数量19/Quantity 19",index=54)
	private String qty19;
	
	public static void main(String[] args) {
		/*List<Field> fields = ExcelUtils.getField(OrderExportOfEDAModel.class);
		ListIterator<Field> iterator = fields.listIterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			System.out.println("row.put(\""+field.getName().toUpperCase()+"\",\"\");");
		}*/
		Class clazz = OrderExportOfEDAModel.class;
		Field[] dFields = clazz.getDeclaredFields();
		for(Field field : dFields){
			System.out.println("row.put(\""+field.getName().toUpperCase()+"\",\"\");");
		}
	}
	
	
}
