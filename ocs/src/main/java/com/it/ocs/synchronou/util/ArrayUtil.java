package com.it.ocs.synchronou.util;

import java.lang.reflect.Array;
import java.util.List;

public class ArrayUtil {
	public static <T> T[] listToArray(List<T> source,Class<T> cla){
		T[] target=null;
		if (!ValidationUtil.isNull(source)) {
			T[] array = (T[]) Array.newInstance(cla, source.size());
			 target = source.toArray(array);
		}
		return target;
	}
	public static <T> T[] addObjectToArray(T[] target, T source,Class<T> cla){
		T[] result =null;
		if(ValidationUtil.isNull(target)||target.length<1){
			result = (T[]) Array.newInstance(cla, 1);
			result[0] = source;
			return result;
		}else{
			int length = target.length;
			result = (T[]) Array.newInstance(cla, length+1);
			for (int i = 0; i < length; i++) {
				result[i]=target[i];
			}
			result[length]=source;

			return result;
		}
	};
}
