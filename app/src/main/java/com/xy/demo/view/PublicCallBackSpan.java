package com.xy.demo.view;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

import com.xy.demo.R;
import com.xy.demo.base.MyApplication;
import com.xy.demo.ui.publics.WebActivity;


public class PublicCallBackSpan extends ClickableSpan {


    // 软件协议
    public static final String NOTIFY = getBaseUrl() + "/site/notify";
    // 隐私协议
    public static final String PRIVACY = getBaseUrl() + "/site/privacy-policy";
    // 用户协议
    public static final String USER = getBaseUrl() + "/site/user-agreement";
    // vip服务协议
    public static final String VIP_SERVICE = getBaseUrl() + "/site/membership-service";
    // 注销协议
    public static final String LOGOFF = getBaseUrl() + "/site/logoff-protocol";
    // MOb协议
    public static final String MOB = "https://www.mob.com/about/policy";

    private final Activity activity;
    private final int flag;
    public boolean isNeedFlag;
    public String title;
    public static final int SPAN_TYPE_ONE = 1;
    public static final int SPAN_TYPE_TOW = 2;
    public static final int SPAN_TYPE_THREE = 3;
    public static final int SPAN_TYPE_FOUR = 4;
    public static final int SPAN_TYPE_FIVE = 5;


    public static String getBaseUrl() {
        Resources resources = MyApplication.application.getResources();
        return resources.getString(resources.getInteger(R.integer.apiEnvironment)==1?R.string.apiUrl:R.string.apiUrl_dev);
    }

    public PublicCallBackSpan(Activity activity, int flag) {
        this.activity = activity;
        this.flag = flag;
    }

    public PublicCallBackSpan(Activity activity, int flag, boolean isNeedFlag) {
        this.activity = activity;
        this.flag = flag;
        this.isNeedFlag = isNeedFlag;
    }

    public PublicCallBackSpan(Activity activity, int flag, String title) {
        this.activity = activity;
        this.flag = flag;
        this.title = title;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(@NonNull View view) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent();
        switch (flag) {
            case 1:
                intent.putExtra("title", "隐私政策");
//                intent.putExtra("title", LanguageUtil.getString(activity, R.string.AboutActivity_PRIVACY));
                intent.putExtra("url", PRIVACY);
                intent.setClass(activity, WebActivity.class);
                break;
            case 2:
//                intent.putExtra("title", LanguageUtil.getString(activity, R.string.AboutActivity_xieyi));
                intent.putExtra("title", "软件协议");
                intent.putExtra("url", USER);
                intent.setClass(activity, WebActivity.class);
                break;
            case 3:
//                intent.setClass(activity, FeedBackPostActivity.class);
                break;
            case 4:
               /* intent.putExtra("title", LanguageUtil.getString(activity, R.string.AboutActivity_VIPFUWUXIEYI));
                intent.putExtra("url", getWebUrl(activity, SPAN_TYPE_FOUR));
                intent.putExtra("flag", isNeedFlag ? "flag" : null);
                intent.setClass(activity, WebViewActivity.class);*/
                break;
            case 5:

                break;

            case 6:

                break;
            case 7:

                break;
            default:
                intent=null;
                return;
        }
        if (intent!=null&&!activity.isFinishing() && !activity.isDestroyed()) {
            intent.putExtra("isCanUseDarkMode", true);
            activity.startActivity(intent);
        }
    }
}
