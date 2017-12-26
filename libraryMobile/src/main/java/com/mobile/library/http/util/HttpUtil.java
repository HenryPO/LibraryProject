package com.mobile.library.http.util;

import android.content.Context;

import com.google.gson.Gson;
import com.mobile.library.Utils;
import com.mobile.library.http.bean.HttpPair;
import com.mobile.library.http.cookie.CookiesManager;
import com.mobile.library.utils.MLog;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.FileNameMap;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Http请求管理工具
 *
 * @author lihy
 *         2017-03-24
 */
public class HttpUtil {
    private Context context;

    private OkHttpClient client;
    private long connectionTimeout = 0;
    private long soTimeout = 0;

    public HttpUtil(Context context) {
        this.context = context;
        client = getClient();
    }

    private OkHttpClient getClient() {
        long CONNECTION_TIMEOUT = Utils.getInstance().getHttpConnectionTimeOut();
        long SO_TIMEOUT = Utils.getInstance().getHttpSoTimeOut();
        if (client == null || connectionTimeout != CONNECTION_TIMEOUT || soTimeout != SO_TIMEOUT) {
            client = new OkHttpClient.Builder()
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(SO_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(SO_TIMEOUT, TimeUnit.MILLISECONDS)
                    .cookieJar(new CookiesManager(context))
                    .build();
            connectionTimeout = CONNECTION_TIMEOUT;
            soTimeout = SO_TIMEOUT;
        }
        return client;
    }


    /**
     * get请求
     *
     * @param url  接口地址
     * @param para 接口参数
     * @param cls  返回数据类
     * @param <T>  返回数据类型
     * @return 数据
     * @author lihy
     * 2017-03-24
     */
    public <T> T get(String url, ArrayList<HttpPair> para, Type cls) {
        Gson gson = new Gson();
        T t = null;
        try {
            t = gson.fromJson(get(url, para), cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * get请求
     *
     * @param url  接口地址
     * @param para 接口参数
     * @return 数据
     * @throws IOException
     * @author lihy
     * 2017-03-24
     */
    public String get(String url, ArrayList<HttpPair> para) throws IOException {
        Response response = getResponse(url, para);
        if (response == null) {
            return null;
        }
        int statusCode = response.code();
        MLog.i("HttpStatus=>" + statusCode);
        // 判断请求是否成功
        if (statusCode == HttpURLConnection.HTTP_OK) {
            String body = null;
            body = response.body().string();
            MLog.i("请求服务器端成功=>\n" + body);
            return body;
        }
        return null;
    }

    /**
     * * get请求
     *
     * @param url  接口地址
     * @param para 接口参数
     * @return 数据
     * @throws IOException
     * @author lihy
     * 2017-03-24
     */
    public Response getResponse(String url, ArrayList<HttpPair> para) throws IOException {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(url);
        if (para != null) {
            urlBuilder.append("?");
            for (int i = 0; i < para.size(); i++) {
                HttpPair pair = para.get(i);
                urlBuilder.append(URLEncoder.encode(pair.getName(), "UTF-8")).append('=')
                        .append(URLEncoder.encode(pair.getValue().toString(), "UTF-8"));
                urlBuilder.append('&');
            }
            urlBuilder.delete(urlBuilder.length() - 1, urlBuilder.length());
        }
        MLog.i("getRequest=>", urlBuilder.toString());

        long CONNECTION_TIMEOUT = Utils.getInstance().getHttpConnectionTimeOut();
        long SO_TIMEOUT = Utils.getInstance().getHttpSoTimeOut();
        Request request = new Request.Builder().url(urlBuilder.toString()).build();
        Response response = getClient().newCall(request).execute();
        return response;

    }

    /**
     * post请求
     *
     * @param url  接口地址
     * @param para 接口参数
     * @param cls  返回数据类
     * @param <T>  返回数据类型
     * @return 数据
     * @author lihy
     * 2017-03-24
     */

    public <T> T post(String url, ArrayList<HttpPair> para, Type cls) {
        Gson gson = new Gson();
        T t = null;
        try {
            t = gson.fromJson(post(url, para), cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * post请求
     *
     * @param url_Str 接口地址
     * @param pairs   接口参数
     * @return 数据
     * @author lihy
     * 2017-03-24
     */
    public String post(String url_Str, ArrayList<HttpPair> pairs) throws IOException {
        Response httpResponse = postResponse(url_Str, pairs);
        if (httpResponse == null) {
            return null;
        }
        int statusCode = httpResponse.code();
        MLog.i("HttpStatus+>" + statusCode);
        if (statusCode == HttpURLConnection.HTTP_OK) {
            String body = null;
            body = httpResponse.body().string();
            MLog.i("请求服务器端成功=>\n" + body);
            return body;
        }
        return null;
    }

    /**
     * post请求
     *
     * @param url_Str 接口地址
     * @param pairs   接口参数
     * @return 数据
     * @throws IOException
     * @author lihy
     * 2017-03-24
     */
    public Response postResponse(String url_Str, ArrayList<HttpPair> pairs) throws IOException {
        FormBody.Builder formBody = new FormBody.Builder();
        MLog.v("URL=" + url_Str);
        for (HttpPair pair : pairs) {
            formBody.add(pair.getName(), pair.getValue());
            MLog.v("KEY=" + pair.getName() + "=====VALUE=" + pair.getValue());
        }

        Request request = new Request.Builder().url(url_Str).post(formBody.build()).build();
        Response response = getClient().newCall(request).execute();
        return response;
    }

    /**
     * post请求
     *
     * @param url     接口地址
     * @param xmlData 接口参数
     * @param cls     返回数据类
     * @param <T>     返回数据类型
     * @return 数据
     * @author lihy
     * 2017-03-24
     */
    public <T> T post(String url, String xmlData, Type cls) {
        Gson gson = new Gson();
        T t = null;
        try {
            t = gson.fromJson(post(url, xmlData), cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * post请求
     *
     * @param url_Str 接口地址
     * @param xmlData 接口参数
     * @return 数据
     * @author lihy
     * 2017-03-24
     */
    public String post(String url_Str, String xmlData) throws IOException {
        Response httpResponse = postResponse(url_Str, xmlData);
        if (httpResponse == null) {
            return null;
        }
        int statusCode = httpResponse.code();
        MLog.i("HttpStatus+>" + statusCode);
        if (statusCode == HttpURLConnection.HTTP_OK) {
            String body = null;
            body = httpResponse.body().string();
            MLog.i("请求服务器端成功=>\n" + body);
            return body;
        }
        return null;
    }

    /**
     * post请求
     *
     * @param url_Str 接口地址
     * @param xmlData 接口参数
     * @return 数据
     * @throws IOException
     * @author lihy
     * 2017-03-24
     */
    public Response postResponse(String url_Str, String xmlData) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), xmlData);

        Request request = new Request.Builder().url(url_Str).post(body).build();
        Response response = getClient().newCall(request).execute();

        return response;
    }

    /**
     * post请求
     *
     * @param url_Str  接口地址
     * @param key      文件参数名
     * @param filePath 文件路径
     * @return 数据
     * @throws IOException
     * @author lihy
     * 2017-03-24
     */
    public String postFile(String url_Str, String key, String filePath) throws IOException {
        Response httpResponse = postFileResponse(url_Str, key, filePath);
        if (httpResponse == null) {
            return null;
        }
        int statusCode = httpResponse.code();
        MLog.i("HttpStatus+>" + statusCode);
        if (statusCode == HttpURLConnection.HTTP_OK) {
            String body = null;
            body = httpResponse.body().string();
            MLog.i("请求服务器端成功=>\n" + body);
            return body;
        }
        return null;
    }

    /**
     * post请求
     *
     * @param url_Str  接口地址
     * @param key      文件参数名
     * @param filePath 文件路径
     * @return 数据
     * @throws IOException
     * @author lihy
     * 2017-03-24
     */
    public Response postFileResponse(String url_Str, String key, String filePath)
            throws IOException {
        File file = new File(filePath);
        RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(file.getName())), file);
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(key, file.getName(), fileBody).build();

        Request request = new Request.Builder().url(url_Str).post(requestBody).build();
        Response response = getClient().newCall(request).execute();
        return response;
    }

    /**
     * 辨别文件类型
     *
     * @param path 文件路径
     * @return 文件类型
     * @author lihy
     * 2017-03-24
     */
    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

}
