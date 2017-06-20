package com.mobile.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import com.mobile.library.Utils;

import java.io.File;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * 设备相关工具类
 * 
 * @author lihy
 *
 */
public class DeviceUtils {

	private DeviceUtils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}

	/**
	 * 判断设备是否root
	 *
	 * @return the boolean{@code true}: 是DataCacheHelper.getInstance().getUserBean(self);
	 * 		{@code false}: 否
	 */
	public static boolean isDeviceRooted() {
		String su = "su";
		String[] locations = { "/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/", "/system/bin/failsafe/",
				"/data/local/xbin/", "/data/local/bin/", "/data/local/" };
		for (String location : locations) {
			if (new File(location + su).exists()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取设备系统版本号
	 *
	 * @return 设备系统版本号
	 */
	public static int getSDKVersion() {
		return android.os.Build.VERSION.SDK_INT;
	}

	/**
	 * 获取设备AndroidID
	 *
	 * @return AndroidID
	 */
	@SuppressLint("HardwareIds")
	public static String getAndroidID() {
		return Settings.Secure.getString(Utils.getInstance().getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
	}

	/**
	 * 获取设备MAC地址
	 *
	 * 需添加权限 {@code <uses-permission android:name=
	 * "android.permission.ACCESS_WIFI_STATE"/>}
	 * DataCacheHelper.getInstance().getUserBean(self);
	 *
	 * 需添加权限
	 * {@code <uses-permission android:name="android.permission.INTERNET"/>}
	 * DataCacheHelper.getInstance().getUserBean(self);
	 *
	 * @return MAC地址
	 */
	public static String getMacAddress() {
		String macAddress = getMacAddressByWifiInfo();
		if (!"02:00:00:00:00:00".equals(macAddress)) {
			return macAddress;
		}
		macAddress = getMacAddressByNetworkInterface();
		if (!"02:00:00:00:00:00".equals(macAddress)) {
			return macAddress;
		}
		macAddress = getMacAddressByFile();
		if (!"02:00:00:00:00:00".equals(macAddress)) {
			return macAddress;
		}
		return "please open wifi";
	}

	/**
	 * 获取设备MAC地址
	 *
	 * 需添加权限 {@code <uses-permission android:name=
	 * "android.permission.ACCESS_WIFI_STATE"/>}
	 * DataCacheHelper.getInstance().getUserBean(self);
	 *
	 * @return MAC地址
	 */
	@SuppressLint("HardwareIds")
	private static String getMacAddressByWifiInfo() {
		try {
			WifiManager wifi = (WifiManager) Utils.getInstance().getContext().getSystemService(Context.WIFI_SERVICE);
			if (wifi != null) {
				WifiInfo info = wifi.getConnectionInfo();
				if (info != null)
					return info.getMacAddress();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "02:00:00:00:00:00";
	}

	/**
	 * 获取设备MAC地址
	 *
	 * 需添加权限
	 * {@code <uses-permission android:name="android.permission.INTERNET"/>}
	 * DataCacheHelper.getInstance().getUserBean(self);
	 *
	 * @return MAC地址
	 */
	private static String getMacAddressByNetworkInterface() {
		try {
			List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface ni : nis) {
				if (!ni.getName().equalsIgnoreCase("wlan0"))
					continue;
				byte[] macBytes = ni.getHardwareAddress();
				if (macBytes != null && macBytes.length > 0) {
					StringBuilder res1 = new StringBuilder();
					for (byte b : macBytes) {
						res1.append(String.format("%02x:", b));
					}
					return res1.deleteCharAt(res1.length() - 1).toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "02:00:00:00:00:00";
	}

	/**
	 * 获取设备MAC地址
	 *
	 * @return MAC地址
	 */
	private static String getMacAddressByFile() {
		ShellUtils.CommandResult result = ShellUtils.execCmd("getprop wifi.interface", false);
		if (result.result == 0) {
			String name = result.successMsg;
			if (name != null) {
				result = ShellUtils.execCmd("cat /sys/class/net/" + name + "/address", false);
				if (result.result == 0) {
					if (result.successMsg != null) {
						return result.successMsg;
					}
				}
			}
		}
		return "02:00:00:00:00:00";
	}

	/**
	 * 获取设备厂商
	 *
	 * 如Xiaomi
	 * DataCacheHelper.getInstance().getUserBean(self);
	 *
	 * @return 设备厂商
	 */

	public static String getManufacturer() {
		return Build.MANUFACTURER;
	}

	/**
	 * 获取设备型号
	 *
	 * 如MI2SC
	 * DataCacheHelper.getInstance().getUserBean(self);
	 *
	 * @return 设备型号
	 */
	public static String getModel() {
		String model = Build.MODEL;
		if (model != null) {
			model = model.trim().replaceAll("\\s*", "");
		} else {
			model = "";
		}
		return model;
	}

	/**
	 * 关机
	 *
	 * 需要root权限或者系统权限 {@code <android:sharedUserId="android.uid.system"/>}
	 * DataCacheHelper.getInstance().getUserBean(self);
	 */
	public static void shutdown() {
		ShellUtils.execCmd("reboot -p", true);
		Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
		intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Utils.getInstance().getContext().startActivity(intent);
	}

	/**
	 * 重启
	 *
	 * 需要root权限或者系统权限 {@code <android:sharedUserId="android.uid.system"/>}
	 * DataCacheHelper.getInstance().getUserBean(self);
	 *
	 */
	public static void reboot() {
		ShellUtils.execCmd("reboot", true);
		Intent intent = new Intent(Intent.ACTION_REBOOT);
		intent.putExtra("nowait", 1);
		intent.putExtra("interval", 1);
		intent.putExtra("window", 0);
		Utils.getInstance().getContext().sendBroadcast(intent);
	}

	/**
	 * 重启
	 *
	 * 需系统权限 {@code <android:sharedUserId="android.uid.system"/>}
	 * DataCacheHelper.getInstance().getUserBean(self);
	 *
	 * @param reason
	 *            传递给内核来请求特殊的引导模式，如"recovery"
	 */
	public static void reboot(String reason) {
		PowerManager mPowerManager = (PowerManager) Utils.getInstance().getContext().getSystemService(Context.POWER_SERVICE);
		try {
			mPowerManager.reboot(reason);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重启到recovery
	 *
	 * 需要root权限
	 * DataCacheHelper.getInstance().getUserBean(self);
	 */
	public static void reboot2Recovery() {
		ShellUtils.execCmd("reboot recovery", true);
	}

	/**
	 * 重启到bootloader
	 *
	 * 需要root权限
	 * DataCacheHelper.getInstance().getUserBean(self);
	 */
	public static void reboot2Bootloader() {
		ShellUtils.execCmd("reboot bootloader", true);
	}
}
