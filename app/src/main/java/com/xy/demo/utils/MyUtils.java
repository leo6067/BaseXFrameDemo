package com.xy.demo.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author lin
 * @description: 常用工具类
 * @date 2018/10/22 0022
 */
public class MyUtils {

    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("app", "error " + e.getMessage());
        }
        return apiKey;
    }

    public static String getMetaValueInt(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = String.valueOf(metaData.getInt(metaKey));
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return apiKey;
    }

    /**
     * 获取ROM总大小
     *
     * @param context
     * @return
     */
    public static String getFormatRomSpace(Context context) {
        StatFs fs = new StatFs(getInnerSDCardPath(context));
        String romSpace;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            long blockCount = fs.getBlockCountLong();
            long blockSize = fs.getBlockSizeLong();
            long totalSpace = blockSize * blockCount;
            romSpace = Formatter.formatFileSize(context, totalSpace);
        } else {
            long blockCount = fs.getBlockCount();
            long blockSize = fs.getBlockSize();
            long totalSpace = blockSize * blockCount;
            romSpace = Formatter.formatFileSize(context, totalSpace);
        }
        return romSpace;
    }

    public static long getRomSpace(Context context) {
        StatFs fs = new StatFs(getInnerSDCardPath(context));
        long totalSpace;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            long blockCount = fs.getBlockCountLong();
            long blockSize = fs.getBlockSizeLong();
            totalSpace = blockSize * blockCount;
        } else {
            long blockCount = fs.getBlockCount();
            long blockSize = fs.getBlockSize();
            totalSpace = blockSize * blockCount;
        }
        return totalSpace;
    }

    /**
     * 获取SD卡总大小
     *
     * @param context
     * @return
     */
    public static String getFormatSDSpace(Context context) {
        String extPath = getExtSDCardPath(context);
        if (TextUtils.isEmpty(extPath)) {
            Toast.makeText(context, "无SD卡", Toast.LENGTH_SHORT).show();
            return null;
        }
        File path = new File(extPath);
        StatFs fs = new StatFs(path.getPath());
        String sdSpace;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            long blockCount = fs.getBlockCountLong();
            long blockSize = fs.getBlockSizeLong();
            long totalSpace = blockSize * blockCount;
            sdSpace = Formatter.formatFileSize(context, totalSpace);
        } else {
            long blockCount = fs.getBlockCount();
            long blockSize = fs.getBlockSize();
            long totalSpace = blockSize * blockCount;
            sdSpace = Formatter.formatFileSize(context, totalSpace);
        }
        return sdSpace;
    }

    public static long getSDSpace(Context context) {
        String extPath = getExtSDCardPath(context);
        if (TextUtils.isEmpty(extPath)) {
            Toast.makeText(context, "无SD卡", Toast.LENGTH_SHORT).show();
            return 0;
        }
        File path = new File(extPath);
        StatFs fs = new StatFs(path.getPath());
        long totalSpace;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            long blockCount = fs.getBlockCountLong();
            long blockSize = fs.getBlockSizeLong();
            totalSpace = blockSize * blockCount;
        } else {
            long blockCount = fs.getBlockCount();
            long blockSize = fs.getBlockSize();
            totalSpace = blockSize * blockCount;
        }
        return totalSpace;
    }

    /**
     * 获取rom可用大
     * 小
     *
     * @param context
     * @return
     */
    public static String getFormateAvailableRomSpace(Context context) {
        File path = Environment.getDataDirectory();
        StatFs fs = new StatFs(path.getPath());
        String space = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

            long blockCount = fs.getAvailableBlocksLong();
            long blockSize = fs.getBlockSizeLong();
            long totalSpace = blockSize * blockCount;
            space = Formatter.formatFileSize(context, totalSpace);
        } else {
            long blockCount = fs.getAvailableBlocks();
            long blockSize = fs.getBlockSize();
            long totalSpace = blockSize * blockCount;
            space = Formatter.formatFileSize(context, totalSpace);
        }
        return space;
    }

    public static long getAvailableRomSpace() {
        File path = Environment.getDataDirectory();
        StatFs fs = new StatFs(path.getPath());
        long totalSpace;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

            long blockCount = fs.getAvailableBlocksLong();
            long blockSize = fs.getBlockSizeLong();
            totalSpace = blockSize * blockCount;
        } else {
            long blockCount = fs.getAvailableBlocks();
            long blockSize = fs.getBlockSize();
            totalSpace = blockSize * blockCount;
        }
        return totalSpace;
    }

    /**
     * 获取SD卡可用大小
     *
     * @param context
     * @return
     */
    public static String getFormateAvailableSdSpace(Context context) {
        String extPath = getExtSDCardPath(context);
        if (TextUtils.isEmpty(extPath)) {
            Toast.makeText(context, "没有sd卡", Toast.LENGTH_LONG).show();
            return null;
        }
        File path = new File(extPath);
        StatFs fs = new StatFs(path.getPath());
        String space;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            long blockCount = fs.getAvailableBlocksLong();
            long blockSize = fs.getBlockSizeLong();
            long totalSpace = blockSize * blockCount;
            space = Formatter.formatFileSize(context, totalSpace);
        } else {
            long blockCount = fs.getAvailableBlocks();
            long blockSize = fs.getBlockSize();
            long totalSpace = blockSize * blockCount;
            space = Formatter.formatFileSize(context, totalSpace);
        }
        return space;
    }

    /**
     * 获取SD卡可用大小
     *
     * @param context
     * @return
     */
    public static long getAvailableSdSpace(Context context) {
        String extPath = getExtSDCardPath(context);
        if (TextUtils.isEmpty(extPath)) {
            Toast.makeText(context, "没有sd卡", Toast.LENGTH_LONG).show();
            return 0;
        }
        File path = new File(extPath);
        StatFs fs = new StatFs(path.getPath());
        long totalSpace;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            long blockCount = fs.getAvailableBlocksLong();
            long blockSize = fs.getBlockSizeLong();
            totalSpace = blockSize * blockCount;
        } else {
            long blockCount = fs.getAvailableBlocks();
            long blockSize = fs.getBlockSize();
            totalSpace = blockSize * blockCount;
        }
        return totalSpace;
    }

    /**
     * 获取外置SD卡路径
     *
     * @return 应该就一条记录或空
     */
    public static String getExtSDCardPath(Context context) {
        StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        Method getPathsMethod;
        try {
            getPathsMethod = sm.getClass().getMethod("getVolumePaths", new  Class[ 0 ]);
            String[] path = (String[]) getPathsMethod.invoke(sm, new  Object[]{});
            return path[0];
        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
        } catch (IllegalAccessException e) {
//            e.printStackTrace();
        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
        } catch (InvocationTargetException e) {
//            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取内置内存路径
     *
     * @return 应该就一条记录或空
     */
    public static String getInnerSDCardPath(Context context) {
        StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        Method getPathsMethod;
        try {
            getPathsMethod = sm.getClass().getMethod("getVolumePaths", new Class[0]);
            String[] path = (String[]) getPathsMethod.invoke(sm, new Object[]{});
            return path[0];
        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
        } catch (IllegalAccessException e) {
//            e.printStackTrace();
        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
        } catch (InvocationTargetException e) {
//            e.printStackTrace();
        }
        return null;
    }

    public static float getSdSpacePercent(Context context) {
        long maxSDSpace = getSDSpace(context) / 1024;
        long availableSdSpace = getAvailableSdSpace(context) / 1024;
        double x = (((maxSDSpace - availableSdSpace) / (double) maxSDSpace) * 100);
        return new BigDecimal(x).setScale(2, BigDecimal.ROUND_HALF_UP)
                .floatValue();
    }

    /**
     * 获取Android内部存储或外置SD卡路径
     *
     * @param context    上下文
     * @param isRemovale true获取的是外部存储，false获取的是内部存储
     * @return
     */
    public static String getStoragePath(Context context, boolean isRemovale) {
        // 拿到StorageManager
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            // 反射获得StorageVolume
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            // 反射获得StorageManager的getVolumeList方法
            Method getVolumeList = storageManager.getClass().getMethod("getVolumeList");
            // 反射获得StorageVolume的getPath方法
            Method getPath = storageVolumeClazz.getMethod("getPath");
            // 拿到StorageVolume的isRemovable方法
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            // 得到Storage Volume数组
            Object result = getVolumeList.invoke(storageManager);
            final int length = Array.getLength(result);
            // 遍历数组，拿到StorageVolume中的路径mPath
            for (int i = 0; i < length; i++) {
                Object storageVolume = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolume);
                // 判断是否是可移除的，可移除表示是sdcard
                boolean removable = (Boolean) isRemovable.invoke(storageVolume);
                // isRemovale为true返回sdcard路径，false返回内部存储路径
                if (isRemovale == removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        } catch (InvocationTargetException e) {
//            e.printStackTrace();
        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
        } catch (IllegalAccessException e) {
//            e.printStackTrace();
        }
        return null;
    }

    public static long getTotalMemory() {
        String str1 = "/proc/meminfo";
        long memory = 0;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader bufferedReader = new BufferedReader(fr, 8192);
            //这里*1024是转换为单位kB（字节）
            memory = Long.parseLong(bufferedReader.readLine().split("\\s+")[1]) * 1024L;
            bufferedReader.close();
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return memory;
    }

    public static long getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem;
    }

    public static float getMemoryPercent(Context context) {
        long totalMemory = getTotalMemory();
        long availMemory = getAvailMemory(context);
        double x = (((totalMemory - availMemory) / (double) totalMemory) * 100);
        return new BigDecimal(x).setScale(2, BigDecimal.ROUND_HALF_UP)
                .floatValue();
    }

    public static String byte2FitMemorySize(final long byteNum) {
        if (byteNum < 0) {
            return "0";
        } else if (byteNum < 1024) {
            return String.format(Locale.getDefault(), "%.1fB", (double) byteNum);
        } else if (byteNum < 1048576) {
            return String.format(Locale.getDefault(), "%.1fKB", (double) byteNum / 1024);
        } else if (byteNum < 1073741824) {
            return String.format(Locale.getDefault(), "%.1fMB", (double) byteNum / 1048576);
        } else {
            return String.format(Locale.getDefault(), "%.1fGB", (double) byteNum / 1073741824);
        }
    }

    public static String[] byte2FitMemorySize2(final long byteNum) {
        if (byteNum <= 0) {
            return new String[]{"0", "B"};
        } else if (byteNum < 1024) {
            return new String[]{String.format(Locale.getDefault(), "%.1f", (double) byteNum), "B"};
        } else if (byteNum < 1048576) {
            return new String[]{String.format(Locale.getDefault(), "%.1f", (double) byteNum / 1024), "KB"};
        } else if (byteNum < 1073741824) {
            return new String[]{String.format(Locale.getDefault(), "%.1f", (double) byteNum / 1048576), "MB"};
        } else {
            return new String[]{String.format(Locale.getDefault(), "%.1f", (double) byteNum / 1073741824), "GB"};
        }
    }

    /**
     * 月
     */
    private static final int MONTH = 30 * 24 * 60 * 60;
    /**
     * 天
     */
    private static final int DAY = 24 * 60 * 60;

    /**
     * 根据时间戳获取当前时间范围
     *
     * @param timestamp 时间戳 单位为毫秒
     * @param localMediaFolders
     * @return 时间范围
     */
//    public static int getDescriptionTimeFromTimestamp(long timestamp, List<MultiItemEntity> localMediaFolders) {
//        long currentTime = System.currentTimeMillis();
//        // 与现在时间相差秒数
//        long timeGap = (currentTime - timestamp) / 1000;
//        boolean sameDate = isSameDate(longToDate(currentTime), longToDate(timestamp));
//
//        if (localMediaFolders.size() == 5 && sameDate){
//            return 0;
//        }
//
//        if(localMediaFolders.size() != 5 && sameDate){
//            LocalMediaFolder localMediaFolder1 = new LocalMediaFolder();
//            localMediaFolder1.setName("今天");
//            localMediaFolders.add(0,localMediaFolder1);
//            return 0;
//        }
//
//        if (timeGap <  DAY) {
//            //一天内
//            return localMediaFolders.size() == 5 ? 1:0;
//        } else if (timeGap < 7 * DAY) {
//            //一周内
//            return localMediaFolders.size() == 5 ? 2:1;
//        } else if (timeGap < MONTH) {
//            //一个月内
//            return localMediaFolders.size() == 5 ? 3:2;
//        } else {
//            return localMediaFolders.size() == 5 ? 4:3;
//        }
//    }

    /**
     * 通过文件名获取文件图标
     */
//    public static int getFileIconByPath(String path) {
//        path = path.toLowerCase();
//        int iconId = R.drawable.ic_dir;
//        if (path.endsWith(".txt")) {
//            iconId = R.drawable.ic_txt;
//        } else if (path.endsWith(".doc") || path.endsWith(".docx")) {
//            iconId = R.drawable.ic_doc;
//        } else if (path.endsWith(".xls") || path.endsWith(".xlsx")) {
//            iconId = R.drawable.ic_xls;
//        } else if (path.endsWith(".ppt") || path.endsWith(".pptx")) {
//            iconId = R.drawable.ic_ppt;
//        } else if (path.endsWith(".pdf")) {
//            iconId = R.drawable.ic_pdf;
//        } else if (path.endsWith(".apk")) {
//            iconId = R.drawable.ic_item_apk;
//        } else if (path.endsWith(".zip")) {
//            iconId = R.drawable.ic_zip;
//        }
//        return iconId;
//    }

    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    public static Date longToDate(long dateLong){
        Date date = new Date(dateLong);
        return date;
    }

    // 两次点击按钮之间的点击间隔不能少于1000毫秒

    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}
