package com.it.ocs.mongo;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.ocs.salesStatistics.model.SalesStatisticsModel;
import com.it.ocs.salesStatistics.utils.TimeTools;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryOperators;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoTest {
/*
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		MongoClient mc = new MongoClient("192.168.174.129", 27017);
		MongoDatabase db = mc.getDatabase("mongoTest");
		MongoCollection<Document> collection = db.getCollection("AMAZON_SALE_ORDER");
		System.out.println(collection.count());
		long time = System.currentTimeMillis();
		Date beginTime = TimeTools.getChangeMonth(new Date(), -1);
		Date endTime = new Date();
		BasicDBObject bo = new BasicDBObject();
		bo.append("purchaseat",new BasicDBObject().append(QueryOperators.GT, beginTime));
		bo.append("purchaseat",new BasicDBObject().append(QueryOperators.LT, endTime));
		bo.append("platform","JP");
		@SuppressWarnings("rawtypes")
		FindIterable iterable = collection.find(bo);
		@SuppressWarnings("unchecked")
		MongoCursor<Document> mongoCursor = iterable.iterator();
		System.out.println(System.currentTimeMillis()-time);
		SalesStatisticsModel.class.getDeclaredFields();
		List<SalesStatisticsModel> models = Lists.newArrayList();
		long start = System.currentTimeMillis();
		int i = 0;
		while(mongoCursor.hasNext()) {
			Map<String, Object> map = mongoCursor.next();
			Date purchaseat = (Date) map.get("purchaseat");
			if (purchaseat.getTime() > endTime.getTime() || purchaseat.getTime() < beginTime.getTime()) {
				System.out.println("purchaseat111111111");
			}
//				SalesStatisticsModel model = BeanConvertUtil.mapToObject(map, SalesStatisticsModel.class);
			i++;
			if (i % 1000000==0) {
				System.out.println(System.currentTimeMillis()-start);
				start = System.currentTimeMillis();
			}
		}
		System.out.println(i);
		
		

	}*/
	private static Map<String, Object> getAllField() {
		Field[] fields = SalesStatisticsModel.class.getDeclaredFields();
		Map<String, Object> map = Maps.newConcurrentMap();
		for (Field field : fields) {
			map.put(field.getName(), null);
		}
		return map;
	}
	

}
