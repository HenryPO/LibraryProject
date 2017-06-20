package com.mobile.library;

import android.content.Context;

/**
 * author: lihy
 * date: 2017/6/19 0019.
 */

public class Utils {
    private static Utils self;

    /**
     * 最后一次点击时间记录
     */
    private long lastClickTime;
    /**
     * 点击时间间隔
     */
    private long lastClickTimeOffset = 500;
    /**
     * 当前工具类缓存当前应用环境
     */
    private Context context;
    /**
     * 网络请求连接超时时间
     */
    private long httpConnectionTimeOut = 10 * 1000;
    /**
     * 网络请求数据获取超时时间
     */
    private long httpSoTimeOut = 30 * 1000;
    /**
     * 是否是Debug模式
     */
    private boolean isDebug = true;
    /**
     * 是否将日志写入文件
     */
    private boolean isLog2File = true;


    /**
     * 工具类根名称
     */
    private String appRootName = "LibraryMobile";


    public static Utils getInstance() {
        if (self == null) {
            self = new Builder().create();
        }
        return self;
    }

    public void clear() {
        self = null;
    }


    public long getLastClickTime() {
        return lastClickTime;
    }

    public long getLastClickTimeOffset() {
        return lastClickTimeOffset;
    }

    public Context getContext() {
        return context;
    }

    public long getHttpConnectionTimeOut() {
        return httpConnectionTimeOut;
    }

    public long getHttpSoTimeOut() {
        return httpSoTimeOut;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public String getAppRootName() {
        return appRootName;
    }

    public boolean isLog2File() {
        return isLog2File;
    }

    /**
     * 是否是第一次点击判断 （防止按钮短时间内多次点击）
     *
     * @return 是第一次点击
     */
    public synchronized boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < lastClickTimeOffset) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static class Builder {

        public Utils mUtils = new Utils();

        public Builder setLastClickTime(long lastClickTime) {
            mUtils.lastClickTime = lastClickTime;
            return this;
        }

        public Builder setLastClickTimeOffset(long lastClickTimeOffset) {
            mUtils.lastClickTimeOffset = lastClickTimeOffset;
            return this;
        }

        public Builder setContext(Context context) {
            mUtils.context = context;
            return this;
        }

        public Builder setHttpConnectionTimeOut(long httpConnectionTimeOut) {
            mUtils.httpConnectionTimeOut = httpConnectionTimeOut;
            return this;
        }

        public Builder setHttpSoTimeOut(long httpSoTimeOut) {
            mUtils.httpSoTimeOut = httpSoTimeOut;
            return this;
        }

        public Builder setDebug(boolean debug) {
            mUtils.isDebug = debug;
            return this;
        }

        public Builder setAppRootName(String appRootName) {
            mUtils.appRootName = appRootName;
            return this;
        }

        public Builder setLog2File(boolean log2File) {
            mUtils.isLog2File = log2File;
            return this;
        }

        public Utils create() {
            self = mUtils;
            return mUtils;
        }

    }
}
