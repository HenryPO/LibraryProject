package com.mobile.library.http.bean;


/**
 * 三个参数的键值对
 *
 * @author lihy
 * 2017-03-24
 *
 */
public class HttpPair {

    private String name;
    private String value;

    public HttpPair(String name, String value) {
        this.name = name;
        this.value = value;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
