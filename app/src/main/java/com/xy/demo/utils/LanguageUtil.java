package com.xy.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.xy.demo.MainActivity;
import com.xy.demo.base.Constants;

import com.xy.demo.ui.mine.LanguageActivity;
import com.xy.xframework.base.BaseSharePreference;

import java.util.Locale;

/**
 * 切换语言的工具类
 */
public class LanguageUtil {

    private static String LANGUAGE;


    //如果没有修改过 去系统默认
    public static String getLanguage() {
        LANGUAGE = BaseSharePreference.Companion.getInstance().getString(Constants.SHARE_LANGUAGE, "en");

        if (TextUtils.isEmpty(LANGUAGE)) {
//           return getDefaultLocale(activity).getLanguage();
            return "en";
        }

        return LANGUAGE;
    }


    public static void reFreshLanguage(Locale locale, Activity activity, Class<?> homeClass) {

        if (homeClass == null) {
            LANGUAGE = BaseSharePreference.Companion.getInstance().getString(Constants.SHARE_LANGUAGE, "");
            switch (LANGUAGE) {
                case "":
                    locale = getDefaultLocale(activity);
                    break;
                case "en":
                    locale = Locale.UK;
                    break;
                case "zh":
                    locale = Locale.SIMPLIFIED_CHINESE;
                    break;
                case "tw":
                    locale = Locale.TRADITIONAL_CHINESE;
                    break;
                case "ja":
                    locale = Locale.JAPAN;
                    break;
                case "ko":
                    locale = Locale.KOREA;
                    break;
                case "ar":
                    locale = new Locale("ar", "Arabic");
                    break;
                case "es":
                    locale = new Locale("es", "ES");
                    break;
            }


        }


        if (locale == null) {
            locale = Locale.UK;
        }

        changeLanguage(locale, activity, homeClass);
    }


    public static Locale getDefaultLocale(Activity activity) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = activity.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = activity.getResources().getConfiguration().locale;
        }

        String language = locale.getLanguage();
//        switch (language){
//            case "pt":
//                locale = new Locale("pt", "PT");
//                break;
//            case "th":
//                locale = new Locale("th", "TH");
//                break;
//            case "es":
//                locale = new Locale("es", "ES");
//                break;
//            case "zh":
//                String country = locale.getCountry();
//                if (country.equals("CN")) {
//                    locale = Locale.SIMPLIFIED_CHINESE;
//                } else {
//                    locale = Locale.TRADITIONAL_CHINESE;
//                }
//                break;
//            default:
//                locale = Locale.UK;
//        }

//        Globals.log("xxxxxxxxlocale.getDefaultLocale()+5656++" +locale.getCountry());
//        Globals.log("xxxxxxxxlocale.getDefaultLocale()+++" +locale.getLanguage());
        return locale;

//       return Locale.TRADITIONAL_CHINESE;  //默认繁体中文
    }

    /**
     * 更改语言
     *
     * @param locale
     * @param activity
     * @param homeClass
     */
    private static void changeLanguage(Locale locale, Activity activity, Class<?> homeClass) {
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();

        DisplayMetrics metrics = resources.getDisplayMetrics();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, metrics);
        Locale.setDefault(locale);

        if (locale.getLanguage().equals("zh")) {
            if (locale.getCountry().equals("CN")) {
                LANGUAGE = "en"; //获取系统默认语言 -----这个app如果系统默认中文，强制改英文
            } else {
                LANGUAGE = "tw";
            }
        } else {
            LANGUAGE = locale.getLanguage();
        }
        BaseSharePreference.Companion.getInstance().putString(Constants.SHARE_LANGUAGE, LANGUAGE);

//        Globals.log("xxxxxxxxlocale.getLanguage()" +locale.getLanguage());
//        Globals.log("xxxxxxxxlocale.getLanguage()+++" +locale.getCountry());

        if (homeClass != null) {
            if (homeClass == LanguageActivity.class) {
                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra("SWITCH", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
                activity.finish();
            }
        }

    }

    public static String getCountry(Context context) {
        String country;
        Resources resources = context.getResources();
        //在7.0以上和7.0一下获取国家的方式有点不一样
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //  大于等于24即为7.0及以上执行内容
            country = resources.getConfiguration().getLocales().get(0).getCountry();
        } else {
            //  低于24即为7.0以下执行内容
            country = resources.getConfiguration().locale.getCountry();
        }
        return country;
    }

//    public static String getLanguage(Context context) {
//        Locale locale = context.getResources().getConfiguration().locale;
//        if (locale != null) {
//            return locale.getLanguage();
//        }
//        return "zh";
//    }
//
//
//    public static String recoverLanguage(Activity activity) {
//        String currentLanguage = LanguageUtil.getLanguage(activity);
//        Locale locale1 = activity.getResources().getConfiguration().locale;
//        if (locale1 != null) {
//            if (!locale1.getLanguage().equals("zh")) {
//                if (!locale1.getLanguage().equals(currentLanguage)) {
//                    LanguageUtil.reFreshLanguage(null, activity, null);
//                }
//            } else {
//                if (locale1.getCountry().equals("TW")) {
//                    if (!"tw".equals(currentLanguage)) {
//                        LanguageUtil.reFreshLanguage(null, activity, null);
//                    }
//                } else {
//                    if (!"zh".equals(currentLanguage)) {
//                        LanguageUtil.reFreshLanguage(null, activity, null);
//                    }
//                }
//            }
//        }
//        return currentLanguage;
//    }
}
