package com.it.ocs.task.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import org.apache.log4j.Logger;

import com.it.ocs.synchronou.util.ValidationUtil;
import com.it.ocs.task.model.AmazonProductModel;
import com.it.ocs.task.model.EbayOrderSubModel;

public class OracleData {
	private static Logger logger = Logger.getLogger(OracleData.class);
	public static String user = "ocs_ebay";
	public static String pwd = "ebay_line";
	public static String url = "jdbc:oracle:thin:@192.168.2.99:1521:srm";
	
	
	/*public static String user = "ebay_produce";
	public static String pwd = "produce123";
	public static String url = "jdbc:oracle:thin:@192.168.2.99:1521:srm";*/
	/*public static String user = "ebay_test";
	public static String pwd = "test123";
	public static String url = "jdbc:oracle:thin:@192.168.2.99:1521:srm";*/
	
	public ResultSet selectOracleData(String url, String user, String password,String sql){
		Connection conn;
		PreparedStatement stmt;
		ResultSet res = null ;
		try {	
			Class.forName("oracle.jdbc.OracleDriver").newInstance();
			conn = (Connection) DriverManager.getConnection(url,user,password);
			stmt = conn.prepareStatement(sql);
			res = stmt.executeQuery();
			return res;
		
		} catch (SQLException e) {
			logger.error(e);
		} catch (InstantiationException e) {
			logger.error(e);
		} catch (IllegalAccessException e) {
			logger.error(e);
		} catch (ClassNotFoundException e) {
			logger.error(e);
		}
		return res;	
	}
	public static void insertAmazonProduct(List<AmazonProductModel> amazonProductModels){
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url,user,pwd);
		} catch (Exception e) {
			logger.error(e);
		}
		try{
			
			String sql = "INSERT INTO AMAZON_PRODUCT (ENTITY_ID,"
					+ "NAME,CATEGORY,REVIEWS_TOTAL,ASIN,AMAZON_STOCK,AVERAGE_RATE,"
					+ "CREATED_AT,UPDATED_AT,PRODUCT_URL,SKU,HAS_NEW,STATUS,COUNTRY,PRICE,"
					+ "SPECIAL_PRICE,IMAGE,OFFER_TOTAL,STAR_ALL,BULLET_POINTS,SHORT_DESCRIPTION,DESCRIPTION,"
					+ "DETAILS,HAS_UPDATE,IMPORTANT_LEVEL,IF_CONTINUE,TYPE,"
					+ "DEMAND_SCORE,IF_INVITE,INVITE_COUNT,SOURCE_ID,CREATION_DATE)   "
					+ " values(amazon_product_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
			
			PreparedStatement stmt = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			for (AmazonProductModel amazonProductModel : amazonProductModels) {
				String name = amazonProductModel.getName();
				
				if(ValidationUtil.isNull(name)){
					stmt.setNull(1, Types.VARCHAR);
				}else{
					stmt.setString(1, name);
				}
				String category = amazonProductModel.getCategory();
				if (ValidationUtil.isNull(category)) {
					stmt.setNull(2,Types.VARCHAR );
				}else{
					stmt.setString(2,category );
				}
				Long reviewsTotal = amazonProductModel.getReviews_total();
				if (ValidationUtil.isNull(reviewsTotal)) {
					stmt.setNull(3,Types.BIGINT);
				}else{
					stmt.setLong(3,reviewsTotal );
				}
				String asin = amazonProductModel.getAsin();
				if (ValidationUtil.isNull(reviewsTotal)) {
					stmt.setNull(4,Types.VARCHAR);
				}else{
					stmt.setString(4, asin);
				}
				Long amazonStock = amazonProductModel.getAmazon_stock();
				if (ValidationUtil.isNull(amazonStock)) {
					stmt.setNull(5,Types.BIGINT);
				}else{
					stmt.setLong(5, amazonStock);
				}
				String averageRate = amazonProductModel.getAverage_rate();
				if (ValidationUtil.isNull(averageRate)) {
					stmt.setNull(6,Types.VARCHAR);
				}else{
					stmt.setString(6, averageRate);
				}
				Timestamp createdAt=amazonProductModel.getCreated_at();
				if (ValidationUtil.isNull(createdAt)) {
					stmt.setNull(7, Types.TIMESTAMP);
				}else{

					stmt.setTimestamp(7,createdAt );
				}
				Timestamp updatedAt= amazonProductModel.getUpdated_at();
				if (ValidationUtil.isNull(updatedAt)) {
					stmt.setNull(8, Types.TIMESTAMP);
				}else{

					stmt.setTimestamp(8,createdAt );
				}
				String productUrl = amazonProductModel.getProduct_url();
				if (ValidationUtil.isNull(productUrl)) {
					stmt.setNull(9,Types.VARCHAR);
				}else{
					stmt.setString(9, productUrl);
				}
				String sku = amazonProductModel.getSku();
				if (ValidationUtil.isNull(sku)) {
					stmt.setNull(10,Types.VARCHAR);
				}else{
					stmt.setString(10, sku);
				}
				Long hasNew = amazonProductModel.getHas_new();
				if (ValidationUtil.isNull(hasNew)) {
					stmt.setNull(11,Types.BIGINT);
				}else{
					stmt.setLong(11, hasNew);
				}
				Long status = amazonProductModel.getStatus();
				if (ValidationUtil.isNull(status)) {
					stmt.setNull(12,Types.BIGINT);
				}else{
					stmt.setLong(12, status);
				}
				String country = amazonProductModel.getCountry();
				if (ValidationUtil.isNull(country)) {
					stmt.setNull(13,Types.VARCHAR);
				}else{
					stmt.setString(13, country);
				}
				String price = amazonProductModel.getPrice();
				if (ValidationUtil.isNull(price)) {
					stmt.setNull(14,Types.VARCHAR);
				}else{
					stmt.setString(14, price);
				}
				String specialPrice = amazonProductModel.getSpecial_price();
				if (ValidationUtil.isNull(specialPrice)) {
					stmt.setNull(15,Types.VARCHAR);
				}else{
					stmt.setString(15, specialPrice);
				}
				String image = amazonProductModel.getImage();
				if (ValidationUtil.isNull(image)) {
					stmt.setNull(16,Types.VARCHAR);
				}else{
					stmt.setString(16, image);
				}
				Long offerTotal = amazonProductModel.getOffer_total();
				if (ValidationUtil.isNull(offerTotal)) {
					stmt.setNull(17,Types.BIGINT);
				}else{
					stmt.setLong(17, offerTotal);
				}
				String starAll = amazonProductModel.getStar_all();
				if (ValidationUtil.isNull(starAll)) {
					stmt.setNull(18,Types.VARCHAR);
				}else{
					stmt.setString(18, starAll);
				}
				String bulletPoints = amazonProductModel.getBullet_points();
				if (ValidationUtil.isNull(bulletPoints)) {
					stmt.setNull(19,Types.VARCHAR);
				}else{
					stmt.setString(19, bulletPoints);
				}
				String shortDescription = amazonProductModel.getShort_description();
				if (ValidationUtil.isNull(shortDescription)) {
					stmt.setNull(20,Types.VARCHAR);
				}else{
					stmt.setString(20, shortDescription);
				}
				String description = amazonProductModel.getDescription();
				if (ValidationUtil.isNull(description)) {
					stmt.setNull(21,Types.VARCHAR);
				}else{
					stmt.setString(21, description);
				}
				String details = amazonProductModel.getDetails();
				if (ValidationUtil.isNull(details)) {
					stmt.setNull(22,Types.VARCHAR);
				}else{
					stmt.setString(22, details);
				}
				Long hasUpdate = amazonProductModel.getHas_update();
				if (ValidationUtil.isNull(hasUpdate)) {
					stmt.setNull(23,Types.BIGINT);
				}else{
					stmt.setLong(23, hasUpdate);
				}
				Long importantLevel = amazonProductModel.getImportant_level();
				if (ValidationUtil.isNull(importantLevel)) {
					stmt.setNull(24,Types.BIGINT);
				}else{
					stmt.setLong(24, importantLevel);
				}
				Long ifContinue = amazonProductModel.getIf_continue();
				if (ValidationUtil.isNull(ifContinue)) {
					stmt.setNull(25,Types.BIGINT);
				}else{
					stmt.setLong(25, ifContinue);
				}
				Long type = amazonProductModel.getType();
				if (ValidationUtil.isNull(type)) {
					stmt.setNull(26,Types.BIGINT);
				}else{
					stmt.setLong(26, type);
				}
				String demandScore = amazonProductModel.getDemand_score();
				if (ValidationUtil.isNull(demandScore)) {
					stmt.setNull(27,Types.VARCHAR);
				}else{
					stmt.setString(27, demandScore);
				}
				Long ifInvite = amazonProductModel.getIf_invite();
				if (ValidationUtil.isNull(ifInvite)) {
					stmt.setNull(28,Types.BIGINT);
				}else{
					stmt.setLong(28, ifInvite);
				}
				Long inviteCount = amazonProductModel.getInvite_count();
				if (ValidationUtil.isNull(inviteCount)) {
					stmt.setNull(29,Types.BIGINT);
				}else{
					stmt.setLong(29, inviteCount);
				}
				Long entityId = amazonProductModel.getEntity_id();
				if (ValidationUtil.isNull(entityId)) {
					stmt.setNull(30,Types.BIGINT);
				}else{
					stmt.setLong(30, entityId);
				}
				System.out.println(amazonProductModel.getEntity_id());
			stmt.addBatch();
			}
			stmt.executeBatch();
					
			}catch (Exception e) {
				// TODO: handle exception
				
				e.printStackTrace();
				
			}finally{
				try {
					if(con!=null){
						con.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					
					e1.printStackTrace();
				}
		}
	}
	public static void insertEbaySub(List<EbayOrderSubModel> ebayOrderSubModels){
		Connection con = null;
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//String url1 = "jdbc:oracle:thin:@192.168.2.99:1521:srm";
			con = DriverManager.getConnection(url,user,pwd);
		}catch (Exception e) {
			logger.error(e);
		}finally{
			try {
				con.close();
			} catch (SQLException e1) {
				logger.error(e1);
			}
	}
		
		try{
			
				String sql ="insert into ebay_order_sub(ID,PARENT_ID,CHECKOUT_STATUS,SHIPPING_DETAILS,SHIPPING_ADDRESS,SHIPPING_SERVICE_SELECTED,EXTERNAL_TRANSACTION,TRANSACTION_ARRAY,MONETARY_DETAILS,ORDER_ID) values(TOTAL_ORDER_SUB_SEQ.Nextval,?,?,?,?,?,?,?,?,?)";
				
				PreparedStatement stmt = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
				for (EbayOrderSubModel ebayOrderSubModel : ebayOrderSubModels) {
					
				
				stmt.setLong(1,ebayOrderSubModel.getParent_id());
				stmt.setString(2, ebayOrderSubModel.getCheckout_status());
				stmt.setString(3, ebayOrderSubModel.getShipping_details());
				stmt.setString(4, ebayOrderSubModel.getShipping_address());
				stmt.setString(5, ebayOrderSubModel.getShipping_service_selected());
				stmt.setString(6, ebayOrderSubModel.getExternal_transaction());
				stmt.setString(7, ebayOrderSubModel.getTransaction_array());
				stmt.setString(8, ebayOrderSubModel.getMonetary_details());
				stmt.setString(9, ebayOrderSubModel.getOrder_id());
				stmt.addBatch();
				}
				stmt.executeBatch();
		
		}catch (Exception e) {
			e.printStackTrace();
			
		}finally{
			try {
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				
				e1.printStackTrace();
			}
	}
		
	}
	
	public static void updateEbaySub(List<EbayOrderSubModel> ebayOrderSubModels){
		Connection con = null;
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//xin
			//String url1 = "jdbc:oracle:thin:@192.168.2.99:1521:srm";
			con = DriverManager.getConnection(url,user,pwd);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		
		try{
			
				String sql ="update ebay_order_sub set CHECKOUT_STATUS=?,SHIPPING_DETAILS=?,SHIPPING_ADDRESS=?,SHIPPING_SERVICE_SELECTED=?,EXTERNAL_TRANSACTION=?,TRANSACTION_ARRAY=?,MONETARY_DETAILS=? where ORDER_ID=?";
				
				PreparedStatement stmt = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
				for (EbayOrderSubModel ebayOrderSubModel : ebayOrderSubModels) {
					
					stmt.setString(1, ebayOrderSubModel.getCheckout_status());
					stmt.setString(2, ebayOrderSubModel.getShipping_details());
					stmt.setString(3, ebayOrderSubModel.getShipping_address());
					stmt.setString(4, ebayOrderSubModel.getShipping_service_selected());
					stmt.setString(5, ebayOrderSubModel.getExternal_transaction());
					stmt.setString(6, ebayOrderSubModel.getTransaction_array());
					stmt.setString(7, ebayOrderSubModel.getMonetary_details());
					stmt.setString(8, ebayOrderSubModel.getOrder_id());
					stmt.addBatch();
				}
				stmt.executeBatch();
		
		}catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
			
		}finally{
			try {
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				
				e1.printStackTrace();
			}
	}
		
	}
	
	public static void insertSql(String sql, String value){
		Connection con = null;
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			/*String user = "ocs_ebay";
			String pwd = "ebay123";
			String url = "jdbc:oracle:thin:@192.168.2.99:1521:srm";*/
			con = DriverManager.getConnection(url,user,pwd);
			PreparedStatement pstmt = con.prepareStatement(sql);  
			if(value != null){
				pstmt.setObject(1,value);
			}
		    pstmt.executeUpdate();  
		    pstmt.close(); 
		}catch (Exception e) {
			e.printStackTrace();
			
		}finally {
			if(null!=con){
				try {
					con.close();
				} catch (SQLException e) {

				}
			}
		}
	}
}
