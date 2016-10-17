package com.smart.comm.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ObjectUtils {
	
	/**
	 * 复制对象--复制copyFrom到copy
	 * @param copy
	 * @param copyFrom
	 * @throws Exception
	 */
	public static void copyObject(Object copy, Object copyFrom) throws Exception {
		Class<? extends Object> clazz = copy.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			try {
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
				Method getMethod = pd.getReadMethod();
				Method setMethod = pd.getWriteMethod();
				if (null == getMethod || null == setMethod) {
					continue;
				}
				Object obj = getMethod.invoke(copyFrom);
				if (null != obj) {
					setMethod.invoke(copy, obj);
				}
			} catch(Exception e) {
				continue;
			}
		}
	}
	
}
