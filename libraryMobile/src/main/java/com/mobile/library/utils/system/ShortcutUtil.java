package com.mobile.library.utils.system;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.widget.Toast;

/**
 * 桌面快捷方式工具类
 */
public class ShortcutUtil {
	private static ShortcutUtil self;

	private ShortcutUtil() {
		super();
	}

	public static ShortcutUtil getInstance() {
		if (self == null) {
			self = new ShortcutUtil();
		}
		return self;
	}

	/**
	 * 是否存在快捷方式
	 * @param activity 当前环境
	 * @param appName 应用名称
     * @return 是否存在
     */
	private boolean hasShortcut(Activity activity, String appName) {
		boolean isInstallShortcut = false;
		final ContentResolver cr = activity.getContentResolver();

		int versionLevel = android.os.Build.VERSION.SDK_INT;
		String AUTHORITY = "com.android.launcher2.settings";

		// 2.2以上的系统的文件文件名字是不一样的
		if (versionLevel >= 8) {
			AUTHORITY = "com.android.launcher2.settings";
		} else {
			AUTHORITY = "com.android.launcher.settings";
		}

		final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/favorites?notify=true");
		Cursor c = cr.query(CONTENT_URI, new String[] { "title", "iconResource" }, "title=?", new String[] { appName }, null);

		if (c != null && c.getCount() > 0) {
			isInstallShortcut = true;
		}
		return isInstallShortcut;
	}

	/**
	 * 为程序创建桌面快捷方式
	 * @param activity 当前环境
	 * @param iconId 展示图标
	 * @param clazz 目标activity
	 * @param appName 应用名称
	 */
	public void addShortcut(Activity activity, int iconId, Class<?> clazz, String appName) {
		if (hasShortcut(activity,appName)) {
			Toast.makeText(activity, "快捷方式已经存在", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
		// 设置属性
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);

		// 是否允许重复创建
		shortcut.putExtra("duplicate", false);

		// 设置桌面快捷方式的图标
		Parcelable icon = Intent.ShortcutIconResource.fromContext(activity, iconId);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);

		// 点击快捷方式的操作
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setClass(activity, clazz);

		// 设置启动程序
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);

		// 广播通知桌面去创建
		activity.sendBroadcast(shortcut);

	}
}
