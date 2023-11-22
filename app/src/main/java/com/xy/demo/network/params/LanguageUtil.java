package com.xy.demo.network.params;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

import java.util.Locale;



/**
 * 切换语言的工具类
 */
public class LanguageUtil {

    private static String LANGUAGE;

    @SuppressLint("SimpleDateFormat")
    public static boolean getCheck_status(Context activity) {
        return SharedUtils.getInt(activity, "Check_status", -1) == 1;
    }

    public static String getLANGUAGE2(Context activity) {
        if (!getCheck_status(activity)) {
            return getLANGUAGE(activity);
        } else
            return "en";
    }




    public static String getLANGUAGE(Context activity) {
        if (LANGUAGE == null) {
            LANGUAGE = SharedUtils.getString(activity, "Language", "");
            if (LANGUAGE.equals("")) {
                LANGUAGE = SharedUtils.getString(activity, "LanguageTemp", "");
            }
        }
        if (TextUtils.isEmpty(LANGUAGE)) {
            LANGUAGE = "en";
        }
        return LANGUAGE;
    }

    public static String getString(Context context, int id) {
        if (context != null) {
            String str = "";
            try {
                str = context.getString(id);
            } catch (Exception e) {
                str = context.getResources().getString(id);
            }
            return str;
        }
        return "";
    }

    public static void reFreshLanguage(Locale locale, Activity activity, Class<?> homeClass) {
        if (homeClass == null) {
            LANGUAGE = SharedUtils.getString(activity, "Language", "");
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
                case "th":
                    locale = new Locale("th", "TH");
                    break;
                case "pt":
                    locale = new Locale("pt", "PT");
                    break;
                case "es":
                    locale = new Locale("es", "ES");
                    break;
            }
        }
        if (locale == null) {
            locale = Locale.UK;
        }

        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        changeLanguage(locale, activity, homeClass, resources, configuration);
    }

    @NonNull
    private static Locale getDefaultLocale(Activity activity) {
       /* Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = activity.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = activity.getResources().getConfiguration().locale;
        }
        String language = locale.getLanguage();
        switch (language){
            case "pt":
                locale = new Locale("pt", "PT");
                break;
            case "th":
                locale = new Locale("th", "TH");
                break;
            case "es":
                locale = new Locale("es", "ES");
                break;
            case "zh":
                String country = locale.getCountry();
                if (country.equals("CN")) {
                    locale = Locale.SIMPLIFIED_CHINESE;
                } else {
                    locale = Locale.TRADITIONAL_CHINESE;
                }
                break;
            default:
                locale = Locale.UK;

        }
        return locale;*/

       return Locale.TRADITIONAL_CHINESE;  //默认繁体中文
    }

    /**
     * 更改语言
     *
     * @param locale
     * @param activity
     * @param homeClass
     * @param resources
     * @param configuration
     */
    private static void changeLanguage(Locale locale, Activity activity, Class<?> homeClass, Resources resources, Configuration configuration) {
        DisplayMetrics metrics = resources.getDisplayMetrics();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, metrics);
        Locale.setDefault(locale);
        if (locale.getLanguage().equals("zh")) {
            if (locale.getCountry().equals("CN")) {
                LANGUAGE = "zh";
            } else {
                LANGUAGE = "tw";
            }
        } else {
            LANGUAGE = locale.getLanguage();
        }
        if (homeClass != null) {
            SharedUtils.putString(activity, "LanguageTemp", null);
            SharedUtils.putString(activity, "Language", LANGUAGE);
//            if (homeClass == LanguageSwitchActivity.class) {
//                if (UserUtils.isLogin(activity)) {
//                    HttpUtils.getInstance().sendRequestRequestParams(activity, Api.LoginToSyncLanguage, new ReaderParams(activity).generateParamsJson(), null);
//                }
//                EventBus.getDefault().post(new FinaShActivity(true));
//                Intent intent = new Intent(activity, MainActivity.class);
//                intent.putExtra("SWITCH", true);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                activity.startActivity(intent);
//            }
        } else {
            SharedUtils.putString(activity, "LanguageTemp", LANGUAGE);
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

    public static String getLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        if (locale != null) {
            return locale.getLanguage();
        }
        return "zh";
    }


    public static String recoverLanguage(Activity activity) {
        String currentLanguage = LanguageUtil.getLANGUAGE(activity);
        Locale locale1 = activity.getResources().getConfiguration().locale;
        if (locale1 != null) {
            if (!locale1.getLanguage().equals("zh")) {
                if (!locale1.getLanguage().equals(currentLanguage)) {
                    LanguageUtil.reFreshLanguage(null, activity, null);
                }
            } else {
                if (locale1.getCountry().equals("TW")) {
                    if (!"tw".equals(currentLanguage)) {
                        LanguageUtil.reFreshLanguage(null, activity, null);
                    }
                } else {
                    if (!"zh".equals(currentLanguage)) {
                        LanguageUtil.reFreshLanguage(null, activity, null);
                    }
                }
            }
        }
        return currentLanguage;
    }
}
