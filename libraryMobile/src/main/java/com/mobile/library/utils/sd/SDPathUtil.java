package com.mobile.library.utils.sd;

import android.content.Context;

import com.mobile.library.Utils;
import com.mobile.library.utils.IdGeneratorUtil;

import java.io.File;

/**
 * sd卡路径管理
 *
 * @author lhy
 * 2014-10-10 下午3:33:25
 */
public class SDPathUtil {

    /**
     * 项目根目录名称
     */
    private String rootName = Utils.getInstance().getAppRootName();


    /**
     * 获取项目文件根目录
     *
     * @param mContext 当前环境
     * @return 根目录
     */
    public String getRootPath(Context mContext) {
        return SDDataUtil.getFileDir(mContext) + "/" + rootName + "/";
    }

    /**
     * 获取web下载路径
     *
     * @param mContext 当前环境
     * 2014-9-3 下午3:38:21
     * @author lihy
     */
    public String getDownloadsPath(Context mContext) {
        String path = getRootPath(mContext) + "/downloads/";
        File   file = new File(path);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdir();
        if (!file.exists()) {
            file.mkdir();
        }
        return path;
    }

    /**
     * 获取图片缓存路径
     *
     * @param mContext 当前环境
     * @author lihy
     */
    public String getPictureCachePath(Context mContext) {
        String path = getRootPath(mContext) + "/cache/";
        File   file = new File(path);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdir();
        if (!file.exists()) {
            file.mkdir();
        }
        return path;
    }

    /**
     * 获取图片的绝对路径
     *
     * @param mContext 当前环境
     * @return 随机生成的路径
     */
    public String getPicturePath(Context mContext) {
        String path = null;
        try {
            path = getPictureCachePath(mContext) + IdGeneratorUtil.Instance().NextLong();

            File file = new File(path);
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取音频文件缓存目录
     *
     * @param mContext 当前环境
     * @return 随机生成的路径
     * @author lihy
     */
    public String getAudioCachePath(Context mContext) {
        String path = getRootPath(mContext) + "/audio/";
        File   file = new File(path);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdir();
        if (!file.exists()) {
            file.mkdir();
        }
        return path;
    }

    /**
     * 获取视频文件缓存目录
     *
     * @param mContext 当前环境
     * @return 随机生成的路径
     * @author lihy
     */
    public String getVideoCachePath(Context mContext) {
        String path = getRootPath(mContext) + "/video/";
        File   file = new File(path);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdir();
        if (!file.exists()) {
            file.mkdir();
        }
        return path;
    }

    /**
     * 随机生成音频文件路径
     *
     * @param mContext 当前环境
     * @return 随机生成的路径
     * @author lihy
     */
    public String getAudioPath(Context mContext) {
        String path = null;
        try {
            path = getAudioCachePath(mContext) + "m_voice_" + IdGeneratorUtil.Instance().NextLong();
            File file = new File(path);
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 随机生成视频文件路径
     *
     * @param mContext 当前环境
     * @return 随机生成的路径
     * @author lihy
     */
    public String getVideoPath(Context mContext) {
        String path = null;
        try {
            path = getVideoCachePath(mContext) + IdGeneratorUtil.Instance().NextLong();
            File file = new File(path);
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
