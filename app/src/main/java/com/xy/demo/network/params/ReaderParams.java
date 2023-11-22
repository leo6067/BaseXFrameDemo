package com.xy.demo.network.params;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.xy.demo.R;
import com.xy.demo.base.MyApplication;
import com.xy.demo.network.Globals;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import static com.xy.demo.network.params.LanguageUtil.getLANGUAGE2;


public class ReaderParams {

    private final String TAG = ReaderParams.class.getSimpleName();

    private final Map<String, String> nameValuePair = new TreeMap<>(new Comparator<String>() {
        public int compare(String o1, String o2) {
            return o1.compareTo(o2); //用正负表示大小值
        }
    });

    public void destroy() {
        nameValuePair.clear();
    }

    /**
     * 公共参数
     *
     * @param context
     */
    public ReaderParams(Context context) {
        if (context != null) {
            nameValuePair.put("phoneModel", SystemUtil.getPhone());
            nameValuePair.put("invite_code", SharedUtils.getString(context, "invite_code", ""));
            nameValuePair.put("media_source", SharedUtils.getString(context, "media_source", ""));
            nameValuePair.put("osType", UserUtils.getOsType());
            nameValuePair.put("product", UserUtils.getProduct());
            nameValuePair.put("sysVer", UserUtils.getSystemVersion());
            nameValuePair.put("time", (System.currentTimeMillis() / 1000) + "");
            nameValuePair.put("token", UserUtils.getToken(context));
            nameValuePair.put("udid", UserUtils.getUUID(context));
            nameValuePair.put("ver", UserUtils.getAppVersionName(context));
            nameValuePair.put("dark", SystemUtil.isAppDarkMode(context) ? "1" : "0");
            nameValuePair.put("packageName", context.getPackageName());
            nameValuePair.put("marketChannel", UserUtils.getChannelName(context));
            nameValuePair.put("language", getLANGUAGE2(context));
            if (MyApplication.instance.getResources().getBoolean(R.bool.isDebug)) {
                nameValuePair.put("debug", "1");
            }
            nameValuePair.put("sign", UserUtils.MD5(getSortedParams(nameValuePair)));
        }
    }


    // retrofit  body 请求
    public Map<String, String> generateParamsMap() {
        return nameValuePair;
    }

    /**
     * 额外参数另行添加
     *
     * @param key
     * @param value
     */
    public ReaderParams putExtraParams(String key, String value) {
        if (value == null) {
            value = "";
        }
        nameValuePair.put(key, value);
        return this;
    }

    public ReaderParams putExtraParams(String key, long value) {
        nameValuePair.put(key, value + "");
        return this;
    }

    public void removeExtraParams(String key, String value) {
        nameValuePair.remove(key);
    }

    public void removeExtraParams(String key, int value) {
        nameValuePair.remove(key);
    }

    public void putExtraParams(String key, int value) {
        nameValuePair.put(key, value + "");
    }






    /**
     * 生成最终的json参数
     *
     * @return
     */
    public String generateParamsJson() {
        //Log.i("http2",  UserUtils.MD5(getSortedParams(nameValuePair)));
        nameValuePair.put("sign", UserUtils.MD5(getSortedParams(nameValuePair)));
        Globals.log("555555"+JSONObject.toJSONString(nameValuePair) );
        return JSONObject.toJSONString(nameValuePair);
//        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//        String json = gson.toJson(nameValuePair);
//
//        Globals.log("555555"+json);
//        return json;
    }

    /**
     * 参数按照字典顺讯排序
     *
     * @param list
     * @return
     */
    public String getSortedParams(Map<String, String> list) {
        if (list != null && !list.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String key : list.keySet()) {
                stringBuilder.append("&").append(key).append("=").append(list.get(key));
            }
            String str = MyApplication.instance.getResources().getString(R.string.mAppkey)
                    + stringBuilder.substring(1)
                    + MyApplication.instance.getResources().getString(R.string.mAppSecretKey);
            // Log.i("http2--str",  str);
            return str;
        }
        return "";
    }
}
