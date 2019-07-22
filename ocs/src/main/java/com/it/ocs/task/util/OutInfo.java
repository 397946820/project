package com.it.ocs.task.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OutInfo {
	
	/**
	 * 
	 * @param info
	 * @param path
	 * 描述：输出信息到指定的路径文件
	 */
	public static void  Out(String info,String path){

		 FileOutputStream fileOutputStream = null;
	      	
		 File file;
			 
		 

			try {
       		///home/oracle/Public/订单表数据对接日记.txt
				file=new File(path);
           	
           	fileOutputStream = new FileOutputStream(file,true);
           	
       		if(!file.exists()){
       			
           		file.createNewFile();
           	}
       		
				byte[] xin = info.getBytes();
				
				fileOutputStream.write(xin);
				
				fileOutputStream.flush();
				
				fileOutputStream.close();
				
				
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/**
	 * 
	 * @param path
	 * 描述：程序开始执行时间
	 */
	public static void outStartTime(String path){
			
			 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 Date date = new Date();
			 String timeString = simpleDateFormat.format(date);
			 String statrTime = "开始执行时间： "+ timeString +"\t\n";
			 FileOutputStream fileOutputStream = null;
	      	
			 File file;
				 
			 

				try {
	        		///home/oracle/Public/订单表数据对接日记.txt
					file=new File(path);
	            	
	            	fileOutputStream = new FileOutputStream(file,true);
	            	
	        		if(!file.exists()){
	        			
	            		file.createNewFile();
	            	}
	        		
					byte[] xin = statrTime.getBytes();
					
					fileOutputStream.write(xin);
					
					fileOutputStream.flush();
					
					fileOutputStream.close();
					
					
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        	
			
		}
	/**
	 * @param path
	 * 描述：结束时间
	 */
	public static void outEndTime(String path){
		
		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date date = new Date();
		 String timeString = simpleDateFormat.format(date);
		 String statrTime = "执行的结束时间： "+ timeString +"\t\n";
		 FileOutputStream fileOutputStream = null;
     	
		 File file;
			 
		 

			try {
       		///home/oracle/Public/订单表数据对接日记.txt
				file=new File(path);
           	
           	fileOutputStream = new FileOutputStream(file,true);
           	
       		if(!file.exists()){
       			
           		file.createNewFile();
           	}
       		
				byte[] xin = statrTime.getBytes();
				
				fileOutputStream.write(xin);
				
				fileOutputStream.flush();
				
				fileOutputStream.close();
				
				
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       	
			
	}
}
