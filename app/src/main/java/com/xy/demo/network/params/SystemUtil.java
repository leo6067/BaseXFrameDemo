package com.xy.demo.network.params;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.List;

public class SystemUtil {
    public static String getPhone() {
        String brand = android.os.Build.BRAND;//手机品牌
        String model = android.os.Build.MODEL;//手机型号
        return brand + " " + model;
    }

    public static final String FACEBOOK_PACKAGE_NAME = "com.facebook.katana";

    public static final String PHONE_MI = "Redmi Note 7";

    /**
     * @return 获取手机android版本
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * @return 获取机型
     */
    public static String getSystemModel() {
        String model = TextUtils.isEmpty(android.os.Build.MODEL) ? "" : android.os.Build.MODEL;

        return model;
    }

    /**
     * @param context
     * @return 判断应用是否在前台显示 true：前台，false：后台
     */
    public static boolean isAppForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        boolean isAppForeground = false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                isAppForeground = appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND || appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
            }
        }
        return isAppForeground;
    }


    /**
     * @param context   Context
     * @param className 界面的类名
     * @return 是否在前台显示
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && !list.isEmpty()) {
            ComponentName cpn = list.get(0).topActivity;
            return className.equals(cpn.getClassName());
        }
        return false;
    }

    /**
     * @param context
     * @param className
     * @return 判断服务是否存在
     */
    public static boolean isServiceExisted(Context context, String className) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (serviceList.isEmpty()) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            ActivityManager.RunningServiceInfo serviceInfo = serviceList.get(i);
            ComponentName serviceName = serviceInfo.service;

            if (serviceName.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param context
     * @param pkgName
     * @return 应用是否安装
     */
    public static boolean checkAppInstalled(Context context, String pkgName) {
        /*if (pkgName == null || pkgName.isEmpty()) {
            return false;
        }
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> info = packageManager.getInstalledPackages(0);
        if (info == null || info.isEmpty())
            return false;
        for (int i = 0; i < info.size(); i++) {
            if (pkgName.equals(info.get(i).packageName)) {
                return true;
            }
        }*/
        return true;
    }


//    public static void initDarkMode() {
//        try {
//            darkMode = SharedUtils.getInt(BWNApplication.applicationContext, "dark_mode", 0);
//            if (darkMode == 0) {
//                int currentNightMode = BWNApplication.applicationContext.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//                isDarkMode = currentNightMode == Configuration.UI_MODE_NIGHT_YES;
//            }
//        } catch (Throwable e) {
//        }
//    }

    /**
     * @param context
     * @return 应用当前显示的模式
     */
    public static int darkMode;

    public static boolean isDarkMode;

    public static boolean isAppDarkMode(Context context) {
        if (darkMode == 1) {
            return false;
        } else if (darkMode == 2) {
            return true;
        }
        return isDarkMode;
    }

    /**
     * @param context
     * @return 返回主题
     */
//    public static int getTheme(Context context) {
//        if (darkMode == 1) {
//            return R.style.Activity_light_theme;
//        } else if (darkMode == 2) {
//            return R.style.Activity_dark_theme;
//        } else {
//            return isDarkMode ? R.style.Activity_dark_theme : R.style.Activity_light_theme;
//        }
//    }


    /**
     * @param context
     * @param intent
     * @return 判断Uri是否有效
     */
    public static boolean isValidIntent(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return !activities.isEmpty();
    }

    /**
     * @param
     * @return 验证是否已在此设备上安装并启用Google Play服务
     */
    public static boolean isCheckGooglePlayServices(Activity activity) {
        // 验证是否已在此设备上安装并启用Google Play服务，以及此设备上安装的旧版本是否为此客户端所需的版本
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity);
        return code == ConnectionResult.SUCCESS;
    }

    private static final String[] NARMAL_PHONE = {"com.android.email", "com.android.email.activity.MessageCompose"};
    private static final String[] MIUI_PHONE = {"com.android.email", "com.kingsoft.mail.compose.ComposeActivity"};
    private static final String[] SAMSUNG_PHONE = {"com.samsung.android.email.provider", "com.samsung.android.email.composer.activity.MessageCompose"};

//    public static void sendEmail(Activity activity, String content, String title) {
//        ScreenSizeUtils.getInstance(activity).copy((Activity) activity, content, false);
//
//        Uri uri = Uri.parse("mailto:" + content);
//        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
//        //  String[] email={content};
//        //intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
//        //intent.putExtra(Intent.EXTRA_SUBJECT, "这是邮件的主题部分"); // 主题
//        //intent.putExtra(Intent.EXTRA_TEXT, "sssssss"); // 正文
//        activity.startActivity(Intent.createChooser(intent, title));
//    }

    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }


    /**
     * 首字母大写(进行字母的ascii编码前移，效率是最高的)
     *
     * @param fieldName 需要转化的字符串
     */
    public static String firstToUpperCase(String fieldName) {
        char[] chars = fieldName.toCharArray();
        chars[0] = toUpperCase(chars[0]);
        return String.valueOf(chars);
    }


    /**
     * 字符转成大写
     *
     * @param c 需要转化的字符
     */
    public static char toUpperCase(char c) {
        if (97 <= c && c <= 122) {
            c ^= 32;
        }
        return c;
    }

    public static float getDisplayMetrics(Activity cx) {

        DisplayMetrics dm = new DisplayMetrics();
        cx.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.density;
      /*  String str = "";

        DisplayMetrics dm = new DisplayMetrics();

        dm = cx.getApplicationContext().getResources().getDisplayMetrics();

        int screenWidth = dm.widthPixels;

        int screenHeight = dm.heightPixels;

        float density = dm.density;

        float xdpi = dm.xdpi;

        float ydpi = dm.ydpi;

        str += "The absolute width:" + String.valueOf(screenWidth) + "pixels\n";

        str += "The absolute heightin:" + String.valueOf(screenHeight)

                + "pixels\n";

        str += "The logical density of the display.:" + String.valueOf(density)

                + "\n";

        str += "X dimension :" + String.valueOf(xdpi) + "pixels per inch\n";

        str += "Y dimension :" + String.valueOf(ydpi) + "pixels per inch\n";

        return str;
*/
    }



    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
//            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
}