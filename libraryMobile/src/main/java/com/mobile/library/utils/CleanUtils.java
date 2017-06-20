package com.mobile.library.utils;

import com.mobile.library.Utils;
import com.mobile.library.utils.sd.SDDataUtil;

import java.io.File;

/**
 * 清除相关工具类
 * 
 * @author lihy
 *
 */
public class CleanUtils {

	private CleanUtils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}

	/**
	 * 清除内部缓存
	 *
	 * /data/data/com.xxx.xxx/cache
	 * DataCacheHelper.getInstance().getUserBean(self);
	 *
	 * @return {@code true}: 清除成功DataCacheHelper.getInstance().getUserBean(self);
	 * 		{@code false}: 清除失败
	 */
	public static boolean cleanInternalCache() {
		return FileUtils.deleteFilesInDir(Utils.getInstance().getContext().getCacheDir());
	}

	/**
	 * 清除内部文件
	 *
	 * /data/data/com.xxx.xxx/files
	 * DataCacheHelper.getInstance().getUserBean(self);
	 *
	 * @return {@code true}: 清除成功DataCacheHelper.getInstance().getUserBean(self);
	 * 		{@code false}: 清除失败
	 */
	public static boolean cleanInternalFiles() {
		return FileUtils.deleteFilesInDir(Utils.getInstance().getContext().getFilesDir());
	}

	/**
	 * 清除内部数据库
	 *
	 * /data/data/com.xxx.xxx/databases
	 * DataCacheHelper.getInstance().getUserBean(self);
	 *
	 * @return {@code true}: 清除成功DataCacheHelper.getInstance().getUserBean(self);
	 * 		{@code false}: 清除失败
	 */
	public static boolean cleanInternalDbs() {
		return FileUtils.deleteFilesInDir(Utils.getInstance().getContext().getFilesDir().getParent() + File.separator + "databases");
	}

	/**
	 * 根据名称清除数据库
	 *
	 * /data/data/com.xxx.xxx/databases/dbName
	 * DataCacheHelper.getInstance().getUserBean(self);
	 *
	 * @param dbName
	 *            数据库名称
	 * @return {@code true}: 清除成功DataCacheHelper.getInstance().getUserBean(self);
	 * 		{@code false}: 清除失败
	 */
	public static boolean cleanInternalDbByName(String dbName) {
		return Utils.getInstance().getContext().deleteDatabase(dbName);
	}

	/**
	 * 清除内部SP
	 *
	 * /data/data/com.xxx.xxx/shared_prefs
	 * DataCacheHelper.getInstance().getUserBean(self);
	 *
	 * @return {@code true}: 清除成功DataCacheHelper.getInstance().getUserBean(self);
	 * 		{@code false}: 清除失败
	 */
	public static boolean cleanInternalSP() {
		return FileUtils
				.deleteFilesInDir(Utils.getInstance().getContext().getFilesDir().getParent() + File.separator + "shared_prefs");
	}

	/**
	 * 清除外部缓存
	 *
	 * /storage/emulated/0/android/data/com.xxx.xxx/cache
	 * DataCacheHelper.getInstance().getUserBean(self);
	 *
	 * @return {@code true}: 清除成功DataCacheHelper.getInstance().getUserBean(self);
	 * 		{@code false}: 清除失败
	 */
	public static boolean cleanExternalCache() {
		return SDDataUtil.isSDCardEnable() && FileUtils.deleteFilesInDir(Utils.getInstance().getContext().getExternalCacheDir());
	}

	/**
	 * 清除自定义目录下的文件
	 *
	 * @param dirPath
	 *            目录路径
	 * @return {@code true}: 清除成功DataCacheHelper.getInstance().getUserBean(self);
	 * 		{@code false}: 清除失败
	 */
	public static boolean cleanCustomCache(String dirPath) {
		return FileUtils.deleteFilesInDir(dirPath);
	}

	/**
	 * 清除自定义目录下的文件
	 *
	 * @param dir
	 *            目录
	 * @return {@code true}: 清除成功DataCacheHelper.getInstance().getUserBean(self);
	 * 		{@code false}: 清除失败
	 */
	public static boolean cleanCustomCache(File dir) {
		return FileUtils.deleteFilesInDir(dir);
	}
}
