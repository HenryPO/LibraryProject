//package com.mobile.library.utils;
//
//import android.content.Context;
//
///**
// * 通用工具类
// *
// * @author lihy
// */
//@Deprecated
//public class Utils {
//	private static long lastClickTime;
//	private static Context context;
//
//	/**
//	 * 是否是第一次点击判断 （防止按钮短时间内多次点击）
//	 *
//	 * @return 是第一次点击
//	 */
//	public synchronized static boolean isFastClick() {
//		long time = System.currentTimeMillis();
//		if (time - lastClickTime < 500) {
//			return true;
//		}
//		lastClickTime = time;
//		return false;
//	}
//
//	private Utils() {
//		throw new UnsupportedOperationException("u can't instantiate me...");
//	}
//
//	/**
//	 * 初始化工具类
//	 *
//	 * @param context 上下文
//	 */
//	public static void init(Context context) {
//		Utils.context = context.getApplicationContext();
//	}
//
//	/**
//	 * 获取ApplicationContext
//	 *
//	 * @return ApplicationContext
//	 */
//	public static Context getContext() {
//		if (context != null)
//			return context;
//		throw new NullPointerException("u should init first");
//	}
//}
