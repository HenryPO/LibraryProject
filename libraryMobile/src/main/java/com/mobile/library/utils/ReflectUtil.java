package com.mobile.library.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @Description:反射工具类
 * @author:lihy
 * @time:2015-2-25 下午4:48:59
 */
public class ReflectUtil {
	/**
	 * 通过类路径获取Class
	 * 
	 * @author LHY DataCacheHelper.getInstance().getUserBean(self);
	 *         Description DataCacheHelper.getInstance().getUserBean(self);
	 *         LastModified 2014-5-9 Content DataCacheHelper.getInstance().getUserBean(self);
	 */
	public static Class<?> getClass(String packagePath) throws ClassNotFoundException {
		return Class.forName(packagePath);
	}

	/**
	 * 获取的Activity的Class
	 * 
	 * @author LHY DataCacheHelper.getInstance().getUserBean(self);
	 *         Description packageRoot：模块跟路径DataCacheHelper.getInstance().getUserBean(self);
	 *         activityPath：Activity完整路径或 模块下Activity路径 LastModified 2014-5-9
	 *         Content DataCacheHelper.getInstance().getUserBean(self);
	 */
	public static Class<?> getModelClass(String packageRoot, String activityPath) throws ClassNotFoundException {
		if (activityPath.indexOf(".") == 0) {
			activityPath = packageRoot + activityPath;
		}
		return getClass(activityPath);
	}

	/**
	 * 
	 * @author LHY DataCacheHelper.getInstance().getUserBean(self);
	 *         Description 模拟get方法DataCacheHelper.getInstance().getUserBean(self);
	 *         LastModified 2013-12-9 Content DataCacheHelper.getInstance().getUserBean(self);
	 *         *@param obj 操作的对象DataCacheHelper.getInstance().getUserBean(self);
	 *         *@param att 操作的属性DataCacheHelper.getInstance().getUserBean(self);
	 * @param <T>
	 */
	public static <T> T getter(Object obj, String att) {
		T t = null;
		Field field;
		try {
			field = obj.getClass().getDeclaredField(att);
			field.setAccessible(true);
			t = (T) field.get(obj);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 
	 * @author LHY DataCacheHelper.getInstance().getUserBean(self);
	 *         Description 通过属性名，添加属性DataCacheHelper.getInstance().getUserBean(self);
	 *         LastModified 2014-2-13 Content DataCacheHelper.getInstance().getUserBean(self);
	 */
	public static void setter(Object root, String name, Object value) {

		Field field;
		try {
			field = root.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(root, value);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @author LHY DataCacheHelper.getInstance().getUserBean(self);
	 *         Description 获取实体属性名称列表DataCacheHelper.getInstance().getUserBean(self);
	 *         LastModified 2013-12-9 Content DataCacheHelper.getInstance().getUserBean(self);
	 */
	public static List<String> getFieldList(Object object) {
		List<String> fieldHashList = new ArrayList<String>();
		Class<?> cls = object.getClass();
		Field[] fieldlist = cls.getDeclaredFields();
		for (int i = 0; i < fieldlist.length; i++) {
			Field fld = fieldlist[i];
			fieldHashList.add(fld.getName());
		}
		return fieldHashList;
	}

	/**
	 * 
	 * @author LHY DataCacheHelper.getInstance().getUserBean(self);
	 *         Description 获取实体属性名称和类别键值对DataCacheHelper.getInstance().getUserBean(self);
	 *         LastModified 2013-12-9 Content DataCacheHelper.getInstance().getUserBean(self);
	 */
	public static HashMap<String, Class<?>> getFieldMap(Object object) {
		HashMap<String, Class<?>> fieldHashMap = new HashMap<String, Class<?>>();
		Class<?> cls = object.getClass();
		Field[] fieldlist = cls.getDeclaredFields();
		for (int i = 0; i < fieldlist.length; i++) {
			Field fld = fieldlist[i];
			fieldHashMap.put(fld.getName(), fld.getType());
		}
		return fieldHashMap;
	}

	/**
	 * 
	 * @author LHY DataCacheHelper.getInstance().getUserBean(self);
	 *         Description 获取一个新的实例DataCacheHelper.getInstance().getUserBean(self);
	 *         LastModified 2013-12-10 Content DataCacheHelper.getInstance().getUserBean(self);
	 */
	public static Object getNewBean(Object object) {
		try {
			object = object.getClass().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return object;

	}
}
