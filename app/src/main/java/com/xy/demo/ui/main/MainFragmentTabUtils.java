package com.xy.demo.ui.main;


import android.annotation.SuppressLint;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.xy.demo.R;
import com.xy.demo.ui.main.adapter.MyFragmentPagerAdapter;
import com.xy.demo.view.CustomScrollViewPager;

import java.util.ArrayList;
import java.util.List;



/**
 * 这是主界面底部的tab切换的工具类
 */
public class MainFragmentTabUtils implements RadioGroup.OnCheckedChangeListener {

    public static boolean UserCenterRefarsh;
    public int currentPosition;

    private final MainActivity mainActivity;

    private final CustomScrollViewPager customScrollViewPager;
    private final List<Fragment> fragments = new ArrayList<>();

    public MainFragmentTabUtils(MainActivity activity, CustomScrollViewPager customScrollViewPager, RadioGroup radioGroup) {
        this.mainActivity = activity;
        initFragment();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        this.customScrollViewPager = customScrollViewPager;
        radioGroup.setOnCheckedChangeListener(this);
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(fragmentManager, fragments);
        customScrollViewPager.setAdapter(myFragmentPagerAdapter);
//        EventBus.getDefault().post(new ToStore(VideoIsFirst() ? VIDEO_CONSTANT : BOOK_CONSTANT));
//        customScrollViewPager.post(() -> customScrollViewPager.setOffscreenPageLimit(fragments.size()));
    }

    public boolean USE_BOOK, USE_VIDEO;

    private void initFragment() {
        // 书架
        fragments.add(new HomeFragment());
        fragments.add(new VideoFragment());
        fragments.add(new WelfareFragment());

        // 我的界面
        fragments.add(new MineFragment());

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.main_home:
                IntentFragment(0);
                break;
            case R.id.main_video:
                IntentFragment(1);
//                StatusBarUtil.setStatusWithTheme(mainActivity);
                break;
            case R.id.main_welf:
                IntentFragment(2);
                break;
            case R.id.main_mine:
//                StatusBarUtil.setStatusWithTheme(mainActivity);
                IntentFragment(3);
                break;
        }
    }

    private void IntentFragment(int i) {
//        if (USE_VIDEO) {
//            if (i == (USE_BOOK ? 2 : 1)) {
//                mainActivity.activity_main_RadioGroup.setBackgroundColor(ColorsUtil.getAppBgWhiteOrBlackColor(mainActivity));
//            } else if (currentPosition == (USE_BOOK ? 2 : 1))
//                mainActivity.activity_main_RadioGroup.setBackgroundColor(ColorsUtil.getAppBackGroundColor(mainActivity));
//        }
        currentPosition = i;
        customScrollViewPager.setCurrentItem(i, false);
    }



}