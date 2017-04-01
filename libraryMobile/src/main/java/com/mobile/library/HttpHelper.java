package com.mobile.library;

import android.content.Context;

import com.mobile.library.http.bean.HttpPair;
import com.mobile.library.http.util.HttpUtil;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 网络模块外部调用入口
 *
 * @author lihy
 *         2017-04-01
 */
public class HttpHelper {
    private        HttpUtil   httpUtil;
    private static HttpHelper self;

    public HttpHelper(Context context) {
        httpUtil = new HttpUtil(context);
    }

    public static HttpHelper getInstance(Context context) {
        if (self == null) {
            self = new HttpHelper(context);
        }
        return self;
    }

    /**
     * 设置连接超时
     *
     */
    public void setConnectionTimeOut(int time) {
        httpUtil.setConnectionTimeOut(time);
    }

    /**
     * 设置读取超时
     *
     */
    public void setSoTimeOut(int time) {
        httpUtil.setSoTimeOut(time);
    }

    public <T> T get(String url, ArrayList<HttpPair> para, Type cls) {
        return httpUtil.get(url, para, cls);
    }

    public String get(String url, ArrayList<HttpPair> para) throws IOException {
        return httpUtil.get(url, para);
    }

    public Response getResponse(String url, ArrayList<HttpPair> para) throws IOException {
        return httpUtil.getResponse(url, para);
    }

    public <T> T post(String url, ArrayList<HttpPair> para, Type cls) {
        return httpUtil.post(url, para, cls);
    }

    public String post(String url_Str, ArrayList<HttpPair> pairs) throws IOException {
        return httpUtil.post(url_Str, pairs);
    }

    public Response postResponse(String url_Str, ArrayList<HttpPair> pairs) throws IOException {
        return httpUtil.postResponse(url_Str, pairs);
    }

    public <T> T post(String url, String xmlData, Type cls) {
        return httpUtil.post(url, xmlData, cls);
    }

    public String post(String url_Str, String xmlData) throws IOException {
        return httpUtil.post(url_Str, xmlData);
    }

    public Response postResponse(String url_Str, String xmlData) throws IOException {
        return httpUtil.postResponse(url_Str, xmlData);
    }

    public String postFile(String url_Str, String key, String filePath) throws IOException {
        return httpUtil.postFile(url_Str, key, filePath);
    }

    public Response postFileResponse(String url_Str, String key, String filePath) throws IOException {
        return httpUtil.postFileResponse(url_Str, key, filePath);
    }

    public static void main(String[] args) {

    }
}
