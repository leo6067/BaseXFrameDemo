package com.xy.demo.logic.ad;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.xy.demo.base.MyApplication;
import com.xy.demo.network.Globals;
import com.xy.demo.ui.login.LaunchActivity;
import com.xy.demo.ui.main.MainActivity;
import com.xy.xframework.base.AppActivityManager;


/**
 * author: Leo
 * createDate: 2023/11/22 15:46
 */
public class AdManage {
    public InterstitialAd mInterstitialAd;
    public RewardedAd mRewardedAd;
    AdLoader adLoader;


    //初始化 sdk
    public static void initAdMob(Application application) {
        MobileAds.initialize(application,
                new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                    }
                });
    }

    /** Request an ad. */
    public static void loadOpenAd(Activity context) {
         AdRequest request = new AdRequest.Builder().build();
        AppOpenAd.load(
                context, "ca-app-pub-3940256099942544/9257395921", request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(AppOpenAd ad) {
                        // Called when an app open ad has loaded.
                        //当且仅当 是启动页 跟 主页 显示 开屏
                        if (AppActivityManager.getInstance().topActivity(context, LaunchActivity.class.getName()) ||AppActivityManager.getInstance().topActivity(context, MainActivity.class.getName())){
                            ad.show(context);
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        // Called when an app open ad has failed to load.
                        Globals.log("xxxxxxxxAppOpenAdxxxx" + loadAdError.getMessage());
                    }
                });
    }








    // 激励视频
    public void initRewardAd(String adId) {
        AdRequest adRequest = new AdRequest.Builder().build();
        mRewardedAd = null;
        RewardedAd.load(
                MyApplication.instance, adId, adRequest,
                new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(RewardedAd rewardedAd) {
                        Log.e("TAG", "Ad was loaded.");
                        mRewardedAd = rewardedAd;
                    }
                }
        );
    }


    //插页广告

    public void initInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                MyApplication.instance,
                "ca-app-pub-3940256099942544/1033173712",
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        Log.e("TAG", adError.toString());
                        mInterstitialAd = null;
                    }

                    @Override
                    public void onAdLoaded(InterstitialAd interstitialAd) {
                        Log.e("TAG", "Ad was loaded.");
                        mInterstitialAd = interstitialAd;
                    }
                });
    }


    //原生广告

    public void initNativeAd() {
        adLoader = new AdLoader.Builder(MyApplication.instance, "ca-app-pub-3940256099942544/2247696110")

                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        // Handle the failure by logging, altering the UI, and so on.
                    }
                })
                .withNativeAdOptions(
                        new NativeAdOptions.Builder()
                                // Methods in the NativeAdOptions.Builder class can be
                                // used here to specify individual options settings.
                                .build())
                .build();
    }


    //广告展示
    // 横幅广告
    public static void showBannerAd(AdView bannerView) {

        AdRequest adRequest = new AdRequest.Builder().build();
        bannerView.loadAd(adRequest);
        bannerView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }


            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Globals.log("xxxxxxad  onAdFailedToLoad"+loadAdError.getMessage());
            }


            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Globals.log("xxxxxxad  onAdLoaded");
            }
        });
    }


    //激励视频展示   增加参数  奖励领取 类型   fromType  1 阅读页广告奖励  2 签到弹窗 广告奖励  3 任务中心
    public void showRewardAd(Activity activity, int fromType, String advertId) {
        final boolean[] finishWatch = {false};
        if (mRewardedAd == null) {


            return;
        }
        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent();
                if (finishWatch[0]) {
                    //

                    getReward(activity, fromType, advertId);
                    mRewardedAd = null;
                }
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                super.onAdFailedToShowFullScreenContent(adError);

            }

        });

        mRewardedAd.show(activity, new OnUserEarnedRewardListener() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
//                var rewardAmount = rewardItem.amount
                finishWatch[0] = true;
            }
        });
    }


    //插页广告
    public void showInterstitialAd(Activity activity) {
        if (mInterstitialAd == null) {

            return;
        }
        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent();
                mInterstitialAd = null;
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                super.onAdFailedToShowFullScreenContent(adError);
                mInterstitialAd = null;
            }
        });

        mInterstitialAd.show(activity);

    }


    public void showNativeAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        adLoader.loadAds(adRequest, 3);
    }


    public void getReward(Activity activity, int fromType, String advertId) {


    }


}
