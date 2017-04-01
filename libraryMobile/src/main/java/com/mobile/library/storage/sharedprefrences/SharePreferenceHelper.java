package com.mobile.library.storage.sharedprefrences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharePreference工具类(通用数据储存)
 *
 * @author lihy
 * 2017-03-24
 */
public class SharePreferenceHelper {
    private int versionNumber;

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    /**
     * 获取通用的SharedPreferences
     *
     * @param context 当前环境
     * @return SharedPreferences
     * @author lihy
     * 2017-03-24
     */
    public SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("common", Activity.MODE_PRIVATE);
        int               version     = preferences.getInt("version", 0);
        if (version < versionNumber) {
            Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            editor.putInt("version", versionNumber);
            editor.commit();
        }
        return preferences;
    }
}
