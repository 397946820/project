package com.it.ocs.task.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import com.google.common.collect.Lists;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.task.model.LightOrderModel;

public class DateToList{
	
	private static Method findMehtodByField(Field field,Method[] methods) {
		return CollectionUtil.search(methods, new IFunction<Method, Boolean>() {
			@Override
			public Boolean excute(Method obj) {
				return obj.getName().startsWith("set") 
						&& obj.getName().toLowerCase().contains(field.getName().toLowerCase())
						&& obj.getName().length() == ("set"+field.getName()).length();
			}
		});
	}
	
	public static <T> List<T> toListModel(ResultSet resultSet,Class<T> cls) throws InstantiationException, IllegalAccessException{

		List<T> ts = Lists.newArrayList();
		
		try {
			
			ResultSetMetaData md = resultSet.getMetaData();
			int columnCount = md.getColumnCount();
			
			Field[] fields = cls.getDeclaredFields();
			Method[] methods = cls.getDeclaredMethods();
			while(resultSet.next()){
				T t = cls.newInstance();
				
				Class iClass = t.getClass();
				
				
				for(Field field : fields) {
					
					try {
						
	 						Object object = new Object();
							//System.out.println(field.getName()+":"+field.getType());
							if(field.getType().toString().equalsIgnoreCase("class java.lang.Long")){
								
	//							Method method = iClass.getMethod(parSetName(field.getName()), field.getType());
								Method method = findMehtodByField(field, methods);
								object = resultSet.getLong(field.getName());
								method.invoke(t, object);
							}else if(field.getType().toString().equalsIgnoreCase("class java.lang.Double")){
	//							Method method = iClass.getMethod(parSetName(field.getName()), Double.class);
								Method method = findMehtodByField(field, methods);
								object = resultSet.getDouble(field.getName());
								method.invoke(t, object);
							}else{
	//							Method method = iClass.getMethod(parSetName(field.getName()), field.getType());
								Method method = findMehtodByField(field, methods);
								object = resultSet.getObject(field.getName());
								method.invoke(t, object);
							}
						
						
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						//OutInfo.Out(e.toString(), path);
						
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						//OutInfo.Out(e.toString(), path);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						//OutInfo.Out(e.toString(), path);
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						//OutInfo.Out(e.toString(), path);
					}
						
					
					
				}
				ts.add(t);	
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return ts;
		
	}
	 /** 
     * 拼接在某属性的 set方法 
     *  
     * @param fieldName 
     * @return String 
     */  
    public static String parSetName(String fieldName) {  
        if (null == fieldName || "".equals(fieldName)) {  
            return null;  
        }  
        int startIndex = 0;  
        if (fieldName.charAt(0) == '_')  {
            startIndex = 1;  
        }
        return "set"  
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()  
                + fieldName.substring(startIndex + 1);  
    }  
    /** 
     * 拼接某属性的 get方法 
     *  
     * @param fieldName 
     * @return String 
     */  
    public static String parGetName(String fieldName) {  
        if (null == fieldName || "".equals(fieldName)) {  
            return null;  
        }  
        int startIndex = 0;  
        if (fieldName.charAt(0) == '_')  {
            startIndex = 1;  
        }
        return "get"  
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()  
                + fieldName.substring(startIndex + 1);  
    }
    
}
