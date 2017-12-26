package com.mobile.library.utils;

import android.util.Log;

import com.mobile.library.Utils;
import com.mobile.library.utils.sd.SDDataUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日志相关工具类
 *
 * @author lihy
 */
public class MLog {

    private static String tag = Utils.getInstance().getAppRootName();

    private static boolean logSwitch = Utils.getInstance().isDebug();
    private static boolean log2FileSwitch = Utils.getInstance().isLog2File();
    private static char logFilter = 'v';
    private static String dir = SDDataUtil.getFileDir(Utils.getInstance().getContext());

    private MLog() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Verbose日志
     *
     * @param msg 消息
     */
    public static void v(Object msg) {
        log(tag, msg.toString(), null, 'v');
    }

    /**
     * Verbose日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void v(String tag, Object msg) {
        log(tag, msg.toString(), null, 'v');
    }

    /**
     * Verbose日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void v(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'v');
    }

    /**
     * Debug日志
     *
     * @param msg 消息
     */
    public static void d(Object msg) {
        log(tag, msg.toString(), null, 'd');
    }

    /**
     * Debug日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void d(String tag, Object msg) {// 调试信息
        log(tag, msg.toString(), null, 'd');
    }

    /**
     * Debug日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void d(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'd');
    }

    /**
     * Info日志
     *
     * @param msg 消息
     */
    public static void i(Object msg) {
        log(tag, msg.toString(), null, 'i');
    }

    /**
     * Info日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void i(String tag, Object msg) {
        log(tag, msg.toString(), null, 'i');
    }

    /**
     * Info日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void i(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'i');
    }

    /**
     * Warn日志
     *
     * @param msg 消息
     */
    public static void w(Object msg) {
        log(tag, msg.toString(), null, 'w');
    }

    /**
     * Warn日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void w(String tag, Object msg) {
        log(tag, msg.toString(), null, 'w');
    }

    /**
     * Warn日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void w(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'w');
    }

    /**
     * Error日志
     *
     * @param msg 消息
     */
    public static void e(Object msg) {
        log(tag, msg.toString(), null, 'e');
    }

    /**
     * Error日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void e(String tag, Object msg) {
        log(tag, msg.toString(), null, 'e');
    }

    /**
     * Error日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void e(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'e');
    }

    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param tag  标签
     * @param msg  消息
     * @param tr   异常
     * @param type 日志类型
     */
    private static void log(String tag, String msg, Throwable tr, char type) {
        if (logSwitch) {
            if ('e' == type && ('e' == logFilter || 'v' == logFilter)) {
                Log.e(generateTag(tag), msg, tr);
            } else if ('w' == type && ('w' == logFilter || 'v' == logFilter)) {
                Log.w(generateTag(tag), msg, tr);
            } else if ('d' == type && ('d' == logFilter || 'v' == logFilter)) {
                Log.d(generateTag(tag), msg, tr);
            } else if ('i' == type && ('i' == logFilter || 'v' == logFilter)) {
                Log.i(generateTag(tag), msg, tr);
            } else if ('v' == type && ('v' == logFilter)) {
                Log.v(generateTag(tag), msg, tr);
            }
            if (log2FileSwitch) {
                log2File(type, generateTag(tag), msg + '\n' + Log.getStackTraceString(tr));
            }
        }
    }

    /**
     * 打开日志文件并写入日志
     *
     * @param type    日志类型
     * @param tag     标签
     * @param content 内容
     **/
    private synchronized static void log2File(final char type, final String tag, final String content) {
        if (content == null)
            return;
        Date now = new Date();
        String date = new SimpleDateFormat("MM-dd", Locale.getDefault()).format(now);
        final String fullPath = dir + date + ".txt";
        if (!FileUtils.createOrExistsFile(fullPath))
            return;
        String time = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(now);
        final String dateLogContent = time + ":" + type + ":" + tag + ":" + content + '\n';
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(fullPath, true));
                    bw.write(dateLogContent);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    CloseUtils.closeIO(bw);
                }
            }
        }).start();
    }

    /**
     * 产生tag
     *
     * @return tag
     */
    private static String generateTag(String tag) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stacks[4];
        String format = "Tag[" + tag + "] %s[%s, %d]";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        return String.format(format, callerClazzName, caller.getMethodName(), caller.getLineNumber());
    }

    public static void v(Class<?> clazz, String message) {
        log(clazz.getCanonicalName(), message, null, 'v');
    }

    public static void d(Class<?> clazz, String message) {
        log(clazz.getCanonicalName(), message, null, 'd');
    }

    public static void i(Class<?> clazz, String message) {
        log(clazz.getCanonicalName(), message, null, 'i');
    }

    public static void w(Class<?> clazz, String message) {
        log(clazz.getCanonicalName(), message, null, 'w');
    }

    public static void e(Class<?> clazz, String message) {
        log(clazz.getCanonicalName(), message, null, 'e');
    }

}
