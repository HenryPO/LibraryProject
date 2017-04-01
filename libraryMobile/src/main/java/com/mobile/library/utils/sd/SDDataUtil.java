package com.mobile.library.utils.sd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.mobile.library.utils.MLog;
import com.mobile.library.utils.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Description sd卡操作类<br>
 * CreateDate 2014-1-10 <br>
 *
 * @author LHY <br>
 */
public class SDDataUtil {
    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 删除文件或者文件夹(所有内容)
     *
     * @param file 文件路径
     */
    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        /**
         * 如果是文件夹的话，必须先把文件夹里的文件删除了才有权限删除文件夹
         */
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
    }

    /**
     * 根据文件后缀名获得对应的MIME类型
     */
    public static String getMIMEType(File file) {
        String type  = "*/*";
        String fName = file.getName();

        // 获取后缀名前的分隔符“.”在fName中的位置
        int dotIndex = fName.lastIndexOf(".");

        if (dotIndex < 0) {
            return type;
        }

		/* 获取文件的后缀名 */
        String end = "";
        try {
            end = new String(fName.substring(dotIndex, fName.length()).toLowerCase().getBytes(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
        if (end == "")
            return type;

        // if (end.equals(".docx")) {
        // return "application/msword";
        // }

        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) {

            if (end.equals(MIME_MapTable[i][0])) {
                type = MIME_MapTable[i][1];
                break;
            }

        }
        return type;

    }

    public static final String[][] MIME_MapTable = {
            // {后缀名， MIME类型}
            {".apk", "application/vnd.android.package-archive"}, {".avi", "video/x-msvideo"},
            {".bmp", "image/bmp"}, {".class", "application/octet-stream"}, {".doc", "application/msword"},
            {".docx", "application/msword"}, {".xlsx", "application/vnd.ms-excel"},
            {".exe", "application/octet-stream"}, {".gif", "image/gif"}, {".gz", "application/x-gzip"},
            {".htm", "text/html"}, {".html", "text/html"}, {".jar", "application/java-archive"},
            {".jpeg", "image/jpeg"}, {".jpg", "image/jpeg"}, {".js", "application/x-javascript"},
            {".log", "text/plain"}, {".m3u", "audio/x-mpegurl"}, {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"}, {".mpeg", "video/mpeg"}, {".mpg", "video/mpeg"}, {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"}, {".msg", "application/vnd.ms-outlook"}, {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"}, {".png", "image/png"}, {".ppt", "application/vnd.ms-powerpoint"},
            {".rar", "application/x-rar-compressed"}, {".rmvb", "audio/x-pn-realaudio"}, {".txt", "text/plain"},
            {".wav", "audio/x-wav"}, {".wma", "audio/x-ms-wma"}, {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"}, {".xml", "text/plain"}, {".z", "application/x-compress"},
            {".zip", "application/zip"}, {"", "*/*"}};

    /**
     * 保存图片
     *
     * @param file   路径
     * @param bm     图片
     * @param format 图片格式
     */
    public static boolean saveBitmap(File file, Bitmap bm, CompressFormat format) {
        String path = file.getParent();
        if (StringUtil.isNullOrEmpty(path))
            return false;
        File filePath = new File(path);

        if (bm == null)
            return false;
        try {
            if (!filePath.exists())
                filePath.mkdirs();

            if (!file.exists())
                file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);
            bm.compress(format, 100, fos);
            fos.flush();
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 保存图片
     *
     * @param file 路径
     * @param bs   图片数据
     */
    public static boolean saveBitmap(File file, byte[] bs) {
        String path     = file.getParent();
        File   filePath = new File(path);

        if (bs == null || bs.length == 0)
            return false;

        try {
            if (!filePath.exists())
                filePath.mkdirs();

            if (!file.exists())
                file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bs);
            fos.flush();
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 保存图片
     *
     * @param filePath 图片路径
     * @param bm       图片
     * @param format   图片格式
     */
    public static boolean saveBitmap(String filePath, Bitmap bm, CompressFormat format) {
        File file = new File(filePath);
        return saveBitmap(file, bm, format);
    }

    /**
     * 保存图片
     *
     * @param filePath 图片路径
     * @param bs       图片数据
     */
    public static boolean saveBitmap(String filePath, byte[] bs) {
        File file = new File(filePath);
        return saveBitmap(file, bs);
    }

    /**
     * 将流写入文件
     *
     * @param filePath 文件路径
     * @param input    输入流
     * @return 是否成功
     * @author lihy
     */
    public static boolean write2SDFromInput(String filePath, InputStream input) {
        File         file   = null;
        OutputStream output = null;
        try {
            // createSDDir(path);
            file = new File(filePath);
            file.createNewFile();
            output = new FileOutputStream(file);
            byte[] buffer = new byte[10 * 1024];

            int length;
            while ((length = (input.read(buffer))) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 删除文件
     *
     * @param filePath 保存的文件的名字
     * @return 是否成功删除文件
     */
    public static boolean deleteFile(String filePath) {
        if (!existsFile(filePath))
            return true;

        File file = new File(filePath);
        return file.delete();
    }

    /**
     * 将Assets文件复制到SD卡
     *
     * @param assetDir assets路径
     * @param dir      文件路径
     * @param context  设定文件
     */
    public static void copyAssets(String assetDir, String dir, Context context) {
        String[] files;
        try {
            files = context.getResources().getAssets().list(assetDir);
        } catch (IOException e1) {
            return;
        }
        File mWorkingPath = new File(dir);
        // if this directory does not exists, make one.
        if (!mWorkingPath.exists()) {
            if (!mWorkingPath.mkdirs()) {
                Log.e("--CopyAssets--", "cannot create directory.");
            }
        }
        for (int i = 0; i < files.length; i++) {
            try {
                String fileName = files[i];
                // we make sure file name not contains '.' to be a folder.
                if (!fileName.contains(".")) {
                    if (0 == assetDir.length()) {
                        SDDataUtil.copyAssets(fileName, dir + "/" + fileName + "/", context);
                    } else {
                        SDDataUtil.copyAssets(assetDir + "/" + fileName, dir + "/" + fileName + "/", context);
                    }
                    continue;
                }
                File outFile = new File(mWorkingPath, fileName);
                if (outFile.exists())
                    outFile.delete();
                InputStream in = null;
                if (0 != assetDir.length())
                    in = context.getResources().getAssets().open(assetDir + "/" + fileName);
                else
                    in = context.getResources().getAssets().open(fileName);
                OutputStream out = new FileOutputStream(outFile);
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int    len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除文件
     *
     * @param file 调用此方法的内容
     * @return 是否成功删除文件
     */
    public static boolean deleteFile(File file) {
        if (!existsFile(file))
            return true;
        return file.delete();
    }

    /**
     * 验证文件是否存在
     *
     * @param filePath 文件路径
     * @return 是否成功
     */
    public static boolean existsFile(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * 验证文件是否存在
     *
     * @param file 文件路径
     * @return 是否成功
     */
    public static boolean existsFile(File file) {
        return file.exists();
    }

    /**
     * 验证SD卡是否存在
     *
     * @return 是否成功
     */
    public static boolean existsSdCard() {
        return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 验证SD卡剩余空间
     *
     * @return 剩余空间单位MB
     */
    public static long getSDFreeSize() {
        // 取得SD卡文件路径
        File   path = Environment.getExternalStorageDirectory();
        StatFs sf   = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        // 返回SD卡空闲大小
        // return freeBlocks * blockSize; // 单位Byte
        // return (freeBlocks * blockSize)/1024; //单位KB
        return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
    }

    /**
     * 获取文件的全路径
     *
     * @param context  请求内容
     * @param filePath 文件地址
     * @return 文件的全路径
     */
    public static String getFileFullPath(Context context, String filePath) {
        return StringUtil.trimEnd(getFileDir(context), '/') + "/" + StringUtil.trimStart(filePath, '/');
    }

    /**
     * 获取文件存储的根目录
     *
     * @param context 当前环境
     * @return 根目录
     */
    public static String getFileDir(Context context) {
        boolean hasSD = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (hasSD)
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        else
            return context.getFilesDir() + "";
    }

    /**
     * 获取文件长度
     *
     * @param path 文件路径
     * @return 文件长度
     */
    public static String getSize(String path) {
        File   file = new File(path);
        float  t    = 1024;
        String size = (file.length() / t) + "";
        return size.substring(0, size.indexOf(".")) + "KB";
    }

    /**
     * 读取SD卡文本
     *
     * @param strFilePath 文件路径
     * @author lihy
     * 2015年6月8日 上午11:45:03
     */
    public static String readTxtFile(String strFilePath) {
        String path    = strFilePath;
        String content = ""; // 文件内容字符串
        // 打开文件
        File file = new File(path);
        // 如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory()) {
            MLog.d("TestFile", "The File doesn't not exist.");
        } else {
            try {
                InputStream       instream    = new FileInputStream(file);
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader    buffreader  = new BufferedReader(inputreader);
                String            line;
                // 分行读取
                while ((line = buffreader.readLine()) != null) {
                    content += line + "\n";
                }
                instream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    /**
     * 读取Asstes文本
     *
     * @param context  当前环境
     * @param filePath 文件路径
     * @author lihy
     * 2015年6月8日 上午11:47:56
     */
    public static String readTxtFileByAssets(Context context, String filePath) {
        String content = ""; // 文件内容字符串
        try {
            InputStream instream = context.getResources().getAssets().open(filePath);
            if (instream != null) {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader    buffreader  = new BufferedReader(inputreader);
                String            line;
                // 分行读取
                while ((line = buffreader.readLine()) != null) {
                    content += line + "\n";
                }
                instream.close();
            }
        } catch (java.io.FileNotFoundException e) {
            Log.d("TestFile", "The File doesn't not exist.");
        } catch (IOException e) {
            Log.d("TestFile", e.getMessage());
        }
        return content;
    }

    /**
     * COPY文件
     *
     * @param srcFile 源文件路径
     * @param desFile 目标路径
     * @return 是否成功
     */
    public static boolean copyToFile(String srcFile, String desFile) {
        File scrfile = new File(srcFile);
        if (scrfile.isFile()) {
            int             length;
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(scrfile);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            File             desfile = new File(desFile);
            FileOutputStream fos     = null;
            try {
                fos = new FileOutputStream(desfile, false);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            length = (int) scrfile.length();
            byte[] b = new byte[length];
            try {
                fis.read(b);
                fis.close();
                fos.write(b);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * COPY文件夹
     *
     * @param sourceDir 源文件路径
     * @param destDir 目标路径
     * @return boolean 是否成功
     */
    public static boolean copyDir(String sourceDir, String destDir) {
        File   sourceFile = new File(sourceDir);
        String tempSource;
        String tempDest;
        String fileName;
        File[] files      = sourceFile.listFiles();
        for (File file : files) {
            fileName = file.getName();
            tempSource = sourceDir + "/" + fileName;
            tempDest = destDir + "/" + fileName;
            if (file.isFile()) {
                copyToFile(tempSource, tempDest);
            } else {
                copyDir(tempSource, tempDest);
            }
        }
        return true;
    }
}
