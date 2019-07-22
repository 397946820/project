package com.it.ocs.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IActionExt;
import com.it.ocs.common.support.IExcutor;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.salesStatistics.utils.ReflectUtils;

public class CollectionUtil {
	public static <T> void each(Collection<T> list, IAction<T> action) {
		if (!isNullOrEmpty(list)) {
			for (T t : list) {
				action.excute(t);
			}
		}
	}
	
	public static <T> void each(Collection<T> list, IActionExt<T> action, boolean enabledBreak) {
		if (!isNullOrEmpty(list)) {
			for (T t : list) {
				if(action.excute(t) && enabledBreak) {
					break;
				}
			}
		}
	}
	
	public static <T> void each(T[] array, IActionExt<T> action, boolean enabledBreak) {
		if (null != array) {
			for (T t : array) {
				if(action.excute(t) && enabledBreak) {
					break;
				}
			}
		}
	}
	
	public static <T> void each(Collection<T> list, IExcutor<T> excutor, Object other) {
		if (!isNullOrEmpty(list)) {
			for (T t : list) {
				excutor.excute(t, other);
			}
		}
	}
	
	public static <T> void each(T[] array, IExcutor<T> excutor, Object other) {
		if (null != array) {
			for (T t : array) {
				excutor.excute(t, other);
			}
		}
	}

	public static <T> void each(T[] array, IAction<T> action) {
		if (null != array) {
			for (int i = 0; i < array.length; i++) {
				action.excute(array[i]);
			}
		}
	}

	public static <T> boolean isNullOrEmpty(Collection<T> list) {
		return null == list || list.size() == 0;
	}
	
	public static <T> boolean isNullOrEmpty(Object[] objs) {
		return null == objs || objs.length == 0;
	}

	public static <T> List<T> searchList(List<T> list, IFunction<T, Boolean> func) {
		List<T> result = new ArrayList<>();
		if (!isNullOrEmpty(list)) {
			for (T obj : list) {
				if (func.excute(obj)) {
					result.add(obj);
				}
			}
		}
		return result;
	}

	public static <T> Map<String, List<T>> searchListReturnMap(List<T> list, IFunction<T, Boolean> func) {
		List<T> datas = new ArrayList<>();
		List<T> surplus = new ArrayList<>();
		Map<String, List<T>> result = new HashMap<>();
		if (!isNullOrEmpty(list)) {
			for (T obj : list) {
				if (func.excute(obj)) {
					datas.add(obj);
				} else {
					surplus.add(obj);
				}
			}
		}
		result.put("datas", datas);
		result.put("surplusList", surplus);
		return result;
	}

	public static <T> T search(Collection<T> list, IFunction<T, Boolean> func) {
		if (!isNullOrEmpty(list)) {
			for (T obj : list) {
				if (func.excute(obj)) {
					return obj;
				}
			}
		}
		return null;
	}
	public static <T> T search(T[] list, IFunction<T, Boolean> func) {
		if (null != list && list.length > 0) {
			for (T obj : list) {
				if (func.excute(obj)) {
					return obj;
				}
			}
		}
		return null;
	}

	public static <T, F> List<F> beansConvert(List<T> sources, Class<F> cls) {
		List<F> result = new ArrayList<F>();
		each(sources, new IAction<T>() {
			@Override
			public void excute(T obj) {
				try {
					F target = cls.newInstance();
					BeanUtils.copyProperties(obj, target);
					result.add(target);
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		});
		return result;
	}
	
	/**
	 * 对list中的元素进行排序.
	 * @param list  排序集合
	 * @param field 排序字段
	 * @param sort  排序方式: DESC(降序) ASC(升序).
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<?> sort(List<?> list, final String field, final String sort) throws Exception {
		if(CollectionUtil.isNullOrEmpty(list)) {
			return list;
		}
		Field f = list.get(0).getClass().getDeclaredField(field);
		f.setAccessible(true);
		Class<?> type = f.getType();
		
		Collections.sort(list, new Comparator() {
			@Override
			public int compare(Object a, Object b) {
				int ret = 0;
				try {
					Object o1 = f.get(a);
					Object o2 = f.get(b);
					if(o1 == null && o2 == null) {
						
					} else if(o1 == null) {
						ret = -1;
					} else if (o2 == null) {
						ret = 1;
					} else {
						if (type == int.class) {
							ret = ((Integer) o1).compareTo((Integer) o2);
						} else if (type == double.class) {
							ret = ((Double) o1).compareTo((Double) o2);
						} else if (type == long.class) {
							ret = ((Long) o1).compareTo((Long) o2);
						} else if (type == float.class) {
							ret = ((Float) o1).compareTo((Float) o2);
						} else if (type == Date.class) {
							ret = ((Date) o1).compareTo((Date)o2);
						} else if (isImplementsOf(type, Comparable.class)) {
							ret = ((Comparable) o1).compareTo((Comparable) o2);
						} else {
							ret = String.valueOf(o1).compareTo(String.valueOf(o2));
						}
					}

				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				if (sort != null && sort.toLowerCase().equals("desc")) {
					return -ret;
				} else {
					return ret;
				}

			}
		});
		return list;
	}

	 /** 
     * 判断对象实现的所有接口中是否包含szInterface 
     *  
     * @param clazz 
     * @param szInterface 
     * @return 
     */  
    public static boolean isImplementsOf(Class<?> clazz, Class<?> szInterface) {  
        boolean flag = false;  
  
        Class<?>[] face = clazz.getInterfaces();  
        for (Class<?> c : face) {  
            if (c == szInterface) {  
                flag = true;  
            } else {  
                flag = isImplementsOf(c, szInterface);  
            }  
        }  
  
        if (!flag && null != clazz.getSuperclass()) {  
            return isImplementsOf(clazz.getSuperclass(), szInterface);  
        }  
  
        return flag;  
    }  
    
    /**
     * 搜索出符合条件的数据
     * @param list 要搜索的集合
     * @param t    对象
     * @param strings  搜素的字段
     * @param flag  模糊匹配还是equal
     * @return
     * @throws Exception
     */
    private static <T> boolean metch(String[] strings,T t,String flag,T model) throws Exception {
    	boolean result = true;
    	for (String string : strings) {
    		Method method = ReflectUtils.getMethod(string, t.getClass());
			String invoke = ReflectUtils.getValue(t, method);
			if (StringUtils.isNotBlank(invoke)) {
				String value = ReflectUtils.getValue(model, method);
				if(StringUtils.isNotBlank(value)) {
					if(flag.equals("like")) {
						Pattern pattern = Pattern.compile(invoke);
						Matcher matcher = pattern.matcher(value);
						if (!matcher.find()){
							result = false;
							break;
						}
					} else if(flag.equals("eq")) {
						if (!invoke.equals(value)) {
							result = false;
							break;
						}
					}
				} else {
					result = false;
					break;
				}
			}
    	}
    	return result;
    }
    public static <T> List<T> queryByParam(List<T> list, T t, String[] strings,String flag) throws Exception {
		List<T> result = new ArrayList<>();
		for (T model : list) {
			if (metch(strings, t, flag,model)) {
				result.add(model);
			}
		}
		return result;
		/*for (String string : strings) {
			Method method = ReflectUtils.getMethod(string, t.getClass());
			String invoke = ReflectUtils.getValue(t, method);
			if (StringUtils.isNotBlank(invoke)) {
				Pattern pattern = Pattern.compile(invoke);
				for (T model : list) {
					String value = ReflectUtils.getValue(model, method);
					if(StringUtils.isNotBlank(value)) {
						if(flag.equals("like")) {
							Matcher matcher = pattern.matcher(value);
							if (matcher.find()) {
								result.add(model);
							}
						} else if(flag.equals("eq")) {
							if (invoke.equals(value)) {
								result.add(model);
							}
						}
					}
				}
				list.clear();
				list.addAll(result);
				result.clear();
			}
		}*/
	}
    
    /**
     * 对集合分页
     * @param list 要分页的集合
     * @param startRow 开始行
     * @param endRow   结束行
     * @return
     */
    public  static <T> List<T> pageList(List<T> list, int startRow, int endRow) {
    	List<T> result = new ArrayList<>();
    	if(!isNullOrEmpty(list)) {
    		for (int i = startRow - 1; i < list.size(); i++) {
    			if (i >= (startRow - 1) && i < endRow) {
    				result.add(list.get(i));
    			}
    			if (i == endRow) {
    				break;
    			}
    		}
    	}
		return result;
    }
    
    /**
     * 找出两个集合不同的对象
     * @param list1
     * @param list2
     * @param clazz
     * @param strings	哪些字段区分
     * @return
     * @throws Exception
     */
    public static <T> List<T> getDiffrent(List<T> list1, List<T> list2,Class<T> clazz, String[] strings) throws Exception {
		List<T> list = new ArrayList<>();
		Map<String, T> map = new HashMap<>();
		for (T object : list2) {
			String key = getKey(object,strings,clazz);
			map.put(key, object);
		}
		for (T object : list1) {
			String key = getKey(object,strings,clazz);
			if(!map.containsKey(key)) {
				list.add(object);
			}
		}
		return list;
    }

	private static <T> String getKey(T object, String[] strings,Class<T> clazz) throws Exception {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strings.length; i++) {
			Method method = ReflectUtils.getMethod(strings[i], clazz);
			String invoke = ReflectUtils.getValue(object, method);
			sb.append(invoke);
		}
		return sb.toString();
	}
}
